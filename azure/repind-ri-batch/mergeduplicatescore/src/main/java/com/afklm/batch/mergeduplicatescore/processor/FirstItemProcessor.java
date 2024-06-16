package com.afklm.batch.mergeduplicatescore.processor;

import com.afklm.batch.mergeduplicatescore.exception.InputSizeException;
import com.afklm.batch.mergeduplicatescore.logger.MergeDuplicateScoreLogger;
import com.afklm.batch.mergeduplicatescore.model.IndividualScore;
import com.afklm.batch.mergeduplicatescore.model.InputRecord;
import com.afklm.batch.mergeduplicatescore.model.OutputRecord;
import com.afklm.batch.mergeduplicatescore.service.IScoreService;
import com.afklm.batch.mergeduplicatescore.service.IndividusDS;
import com.afklm.batch.mergeduplicatescore.service.MergeDuplicateScoreSummaryService;
import com.airfrance.batch.common.metric.StatusEnum;

import com.airfrance.repind.entity.individu.Individu;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.*;
import java.util.stream.Collectors;

import static com.afklm.batch.mergeduplicatescore.helper.Constant.*;

@Service
@Slf4j
public class FirstItemProcessor implements ItemProcessor<InputRecord, OutputRecord> {

	@Autowired
	private IScoreService scoreProvider;

	@Autowired
	private IndividusDS individusDS;

	@Autowired
	MergeDuplicateScoreSummaryService summaryService;

	@Autowired
	MergeDuplicateScoreLogger logger;

	@Override
	public OutputRecord process(InputRecord inputRecords) throws Exception {
		try{
			summaryService.incrementProcessedLinesCounter();

			List<String> inputGins = Arrays.asList(inputRecords.getGins().split(COMMA));

			// step1 : call MS to fetch individual scores
			List<IndividualScore> individualScores = scoreProvider.fetchScoreByGin(inputGins);

			// step2 : get max score
			double maxScore = getMaxScore(individualScores);

			// step3 : get individus that have the same max score
			List<IndividualScore> multipleIndividusTarget = getMultipleIndividusTarget(individualScores, maxScore);

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
			summaryService.incrementFailedLinesCounter();
			e.getSkippedGins().forEach(skippedGin -> {
				summaryService.incrementSkippedGinsCounter();
				StringBuilder message = new StringBuilder(INPUT_SIZE_EXCEPTION)
						.append(COMMA).append(e.getSkippedGins())
						.append(COMMA).append(PROCESS).append(END_OF_LINE);
				summaryService.addErrorMessage(message.toString());
			});
			if(e.isShouldSkipLine()) return null; // to avoid sending item to Writer
			return e.getOutputRecord();
		}catch(WebClientResponseException | IllegalArgumentException e){
			log.error("[-] Error when calling ProvideIndividualScore MS : {}", e.getMessage());
			summaryService.incrementFailedLinesCounter();
			StringBuilder message = new StringBuilder(WEB_CLIENT_ERROR)
					.append(COMMA).append(e.getMessage())
					.append(COMMA).append(PROCESS).append(END_OF_LINE);
			summaryService.addErrorMessage(message.toString());
			return null; // to avoid sending item to Writer
		}catch(NoSuchElementException e){
			log.error("[-] No such element exception : {} ", e.getMessage());
			summaryService.incrementFailedLinesCounter();
			return null;
		}catch(Exception e){
			log.error("[-] Exception : {} ", e.getMessage());
			summaryService.incrementFailedLinesCounter();
			return null;
		}
	}

	@NotNull
	private List<IndividualScore> getMultipleIndividusTarget(List<IndividualScore> individualScores, double maxScore) {
		return individualScores.stream()
				.filter(ind -> ind.getScore() >= maxScore)
				.toList();
	}

	private Double getMaxScore(List<IndividualScore> individualScores) {
		return individualScores.stream()
				.map(IndividualScore::getScore)
				.max(Double::compare)
				.orElseThrow(NoSuchElementException::new);
	}

	private  OutputRecord createOutputRecord(String ginTarget, List<IndividualScore> individualScores) {

		OutputRecord outputRecord = new OutputRecord();
		outputRecord.setGinTarget(ginTarget);
		outputRecord.setGinSource(
				individualScores.stream()
						.filter(ind-> !ind.getGin().equals(ginTarget))
						.map(IndividualScore::getGin)
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
	private String decideGinTarget(List<IndividualScore> sameMaxIndScore){

		if(sameMaxIndScore.size() == 1){
			// case with one max score return it
			return sameMaxIndScore.get(0).getGin();
		}else{
			// case with multiple max score, decide based on last modification date
			return findGinTargetByRecentModificationDate(sameMaxIndScore);
		}
	}

	private String findGinTargetByRecentModificationDate(List<IndividualScore> sameMaxIndScore) {
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
	 *
	 * @param inputGins input gins
	 * @param individualScores individual scores
	 * @return  List of string
	 */
	private List<String> extractSkippedGinsByMs(List<String> inputGins, List<IndividualScore> individualScores) {
		List<String> outputGinsAfterMsCall = individualScores.stream().map(IndividualScore::getGin).toList();
		return inputGins.stream()
				.filter(gin -> !outputGinsAfterMsCall.contains(gin))
				.toList();
	}

	/**
	 * Como will display gins that are skipped and marked them as failed
	 *
	 * @param skippedGins skip gins
	 */
	private void logSkippedGins(List<String> skippedGins){
		if(!skippedGins.isEmpty()){
			skippedGins.forEach(
					skippedGin -> logger.logComo(skippedGin, GIN_NOT_FOUND, StatusEnum.FAIL )
			);
		}
	}
}

