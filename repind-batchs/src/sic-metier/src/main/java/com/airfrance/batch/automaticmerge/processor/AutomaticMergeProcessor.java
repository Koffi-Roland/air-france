package com.airfrance.batch.automaticmerge.processor;

import com.airfrance.batch.automaticmerge.exception.InputSizeException;
import com.airfrance.batch.automaticmerge.exception.ServiceException;
import com.airfrance.batch.automaticmerge.logger.MergeDuplicateScoreLogger;
import com.airfrance.batch.automaticmerge.model.InputRecord;
import com.airfrance.batch.automaticmerge.model.OutputRecord;
import com.airfrance.batch.automaticmerge.service.AutomaticMergeSummaryService;
import com.airfrance.batch.automaticmerge.service.IndividusDS;
import com.airfrance.batch.common.metric.StatusEnum;
import com.airfrance.batch.common.wrapper.WrapperProvideIndividualScoreResponse;
import com.airfrance.repind.entity.individu.Individu;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.airfrance.batch.automaticmerge.helper.Constant.*;

@Service
@Slf4j
public class AutomaticMergeProcessor implements ItemProcessor<InputRecord, OutputRecord> {

	@Autowired
	private com.airfrance.batch.automaticmerge.service.ScoreService ScoreService;

	@Autowired
	private IndividusDS individusDS;

	@Autowired
	AutomaticMergeSummaryService summaryService;

	@Autowired
	MergeDuplicateScoreLogger logger;

	@Override
	public OutputRecord process(@NotNull InputRecord inputRecords) throws Exception {
		try{
			summaryService.incrementProcessedLinesCounter();

			List<String> inputGins = Arrays.asList(inputRecords.getGins().split(COMMA));

			// step1 : call MS to fetch individual scores
			List<WrapperProvideIndividualScoreResponse> individualScores = ScoreService.fetchScoreByGin(inputGins);

			// step2 : get max score
			double maxScore = getMaxScore(individualScores);

			// step3 : get individus that have the same max score
			List<WrapperProvideIndividualScoreResponse> multipleIndividusTarget = getMultipleIndividusTarget(individualScores, maxScore);

			// step4 : decide who will be next gin target
			String ginTarget = decideGinTarget(multipleIndividusTarget);

			// step 5 : extract && skipped Gins
			List<String> skippedGins = extractSkippedGinsByMs(inputGins, individualScores);
			logSkippedGins(skippedGins);

			// step 6 : create output record
			OutputRecord outputRecord = createOutputRecord(ginTarget, individualScores);

			// step 7 : check if input size contains 1Gin then skip the next step
						// else skip the Gin and continue processing the input list
			int nbRemainingItems = inputGins.size() - skippedGins.size();
			if(nbRemainingItems == 1){
				throw new InputSizeException("[-] Input size contains only 1 Gin", skippedGins, true);
			} else if (!skippedGins.isEmpty()) {
				throw new InputSizeException("[-] Multiple Gins are skipped, but input size with more than 1 Gin", skippedGins, false, outputRecord);
			}

			return outputRecord;
		}catch(InputSizeException e){
			log.error(e.getMessage() + e.getSkippedGins());
			e.getSkippedGins().forEach(skippedGin -> {
				summaryService.incrementSkippedGinsCounter();
				String message = INPUT_SIZE_EXCEPTION +
						COMMA + skippedGin +
						COMMA + PROCESS + END_OF_LINE;
				summaryService.addErrorMessage(message);
			});
			if(e.isShouldSkipLine()) return null; // to avoid sending item to Writer
			return e.getOutputRecord();
		}catch(NoSuchElementException e){
			log.error("[-] No such element exception : {} ", e.getMessage());
			return null;
		}catch(ServiceException e){
			log.error(e.getMessage() + e.getGins());
			e.getGins().forEach(
					gin -> {
						summaryService.incrementSkippedGinsCounter();
						String message = SERVICE_EXCEPTION +
								COMMA + gin +
								COMMA + PROCESS + END_OF_LINE;
						summaryService.addErrorMessage(message);
					}
			);
			return null;
		}catch(Exception e){
			log.error("[-] Exception : {} ", e.getMessage());
			return null;
		}
	}

	@NotNull
	List<WrapperProvideIndividualScoreResponse> getMultipleIndividusTarget(List<WrapperProvideIndividualScoreResponse> individualScores, double maxScore) {
		return individualScores.stream()
				.filter(ind -> ind.getScore() >= maxScore)
				.toList();
	}

	Double getMaxScore(List<WrapperProvideIndividualScoreResponse> individualScores) {
		return individualScores.stream()
				.map(WrapperProvideIndividualScoreResponse::getScore)
				.max(Double::compare)
				.orElseThrow(NoSuchElementException::new);
	}

	OutputRecord createOutputRecord(String ginTarget, List<WrapperProvideIndividualScoreResponse> individualScores) {

		OutputRecord outputRecord = new OutputRecord();
		outputRecord.setGinTarget(ginTarget);
		outputRecord.setGinSource(
				individualScores.stream()
						.map(WrapperProvideIndividualScoreResponse::getGin)
						.filter(gin -> !gin.equals(ginTarget))
						.collect(Collectors.joining(COMMA))
		);
		outputRecord.setMergeDate(new Date());

		return outputRecord;
	}

	/**
	 * When multiple Gins have same max score, then decide based on lastModification date
	 * else then return the only gin that has max value
	 * @param sameMaxIndScore
	 * @return
	 */
	String decideGinTarget(List<WrapperProvideIndividualScoreResponse> sameMaxIndScore){

		if(sameMaxIndScore.size() == 1){
			// case with one max score return it
			return sameMaxIndScore.get(0).getGin();
		}else{
			// case with multiple max score, decide based on last modification date
			return findGinTargetByRecentModificationDate(sameMaxIndScore);
		}
	}

	String findGinTargetByRecentModificationDate(List<WrapperProvideIndividualScoreResponse> sameMaxIndScore) {
		// step1: get list individus sorted by dateModification desc
		List<Individu> individus = sameMaxIndScore.stream()
				.map(ind -> individusDS.getIndividuByGin(ind.getGin()))
				.filter(Objects::nonNull)
				.sorted(Comparator.comparing(Individu::getDateModification).reversed())
				.toList();

		// get the top element of the list
		return individus.get(0).getSgin();
	}

	/**
	 * Sometimes gins are not returned by the MS for many reasons (gin purged, ...)
	 * so this method extract the gins that were skipped by MS
	 * @param inputGins
	 * @param individualScores
	 * @return
	 */
	List<String> extractSkippedGinsByMs(List<String> inputGins, List<WrapperProvideIndividualScoreResponse> individualScores) {
		List<String> outputGinsAfterMsCall = individualScores.stream().map(WrapperProvideIndividualScoreResponse::getGin).toList();
		return inputGins.stream()
				.filter(gin -> !outputGinsAfterMsCall.contains(gin))
				.toList();
	}

	/**
	 * Como will display gins that are skipped and marke them as failed
	 * @param skippedGins
	 */
	private void logSkippedGins(List<String> skippedGins){
		if(!skippedGins.isEmpty()){
			skippedGins.forEach(
					skippedGin -> logger.logComo(skippedGin, GIN_NOT_FOUND, StatusEnum.FAIL )
			);
		}
	}
}

