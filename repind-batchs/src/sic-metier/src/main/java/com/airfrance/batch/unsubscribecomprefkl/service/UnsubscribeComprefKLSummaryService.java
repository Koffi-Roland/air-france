package com.airfrance.batch.unsubscribecomprefkl.service;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


@Service
@Setter
@Getter
@Slf4j
public class UnsubscribeComprefKLSummaryService {

	private static final String METRIC_FILE_NAME = "metric_results.txt";
	private static final String REJECTED_FILE_NAME = "reject_inputs.txt";

	private final AtomicInteger nbProcessedLines = new AtomicInteger();

	private final AtomicInteger nbSuccessComprefUnsub = new AtomicInteger();
	private final AtomicInteger nbComprefNotFound = new AtomicInteger();
	private final AtomicInteger nbEmptyMandatoryFields = new AtomicInteger();
	private final AtomicInteger nbIncorrectData = new AtomicInteger();
	private final AtomicInteger nbTechnicalError = new AtomicInteger();

	private final List<String> message = new ArrayList<>();

	public void incrementNbProcessedLines() {
		nbProcessedLines.incrementAndGet();
	}
	public void incrementNbSuccessComprefUnsub() {
		nbSuccessComprefUnsub.incrementAndGet();
	}
	public void incrementNbComprefNotFound() {
		nbComprefNotFound.incrementAndGet();
	}
	public void incrementNbEmptyMandatoryFields() {
		nbEmptyMandatoryFields.incrementAndGet();
	}
	public void incrementNbIncorrectData() {
		nbIncorrectData.incrementAndGet();
	}
	public void incrementNbTechnicalError() {nbTechnicalError.incrementAndGet();}

	public void addErrorMessage(String error) {
		message.add(error);
	}

	/**
	 * This method generate metrics file, that will be sent by email.
	 * This file is deleted from the server, you can check the corresponding sh file.
	 * @param outputPath path
	 */
	public void generateMetricFile(String outputPath) {
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
		String dDay = sdf.format(new Date());
		String explanation = "*INCORRECT_DATA  =  ACTION_INDEX not equal to U, length of CAUSE_INDEX exceeds 7 chars, COM_TYPE not KL or KL_PART";
		try {

			File metricFile = new File(Paths.get(outputPath, METRIC_FILE_NAME).toString());
			try (BufferedWriter bfwMetricTxt = new BufferedWriter(new FileWriter(metricFile, false))) {
				int nbProcessed = Math.max(0, this.getNbProcessedLines().get() - 1);
				bfwMetricTxt.write("COMMUNICATION_PREFERENCES Unsubscribe: " + dDay +
						"\nTOTAL OF PROCESSED LINES:" + nbProcessed +
						"\nTOTAL OF SUCCESS COMPREF UNSUBSCRIBE:" + this.getNbSuccessComprefUnsub().get() +
						"\nTOTAL OF COMPREF_NOT_FOUND:" + this.getNbComprefNotFound().get() +
						"\nTOTAL OF EMPTY_MANDATORY_FIELDS:" + this.getNbEmptyMandatoryFields().get() +
						"\nTOTAL OF INCORRECT_DATA*:" + this.getNbIncorrectData().get() +
						"\nTOTAL OF TECHNICAL_ERRORS:" + this.getNbTechnicalError().get()+"\n\n"+
						explanation
				);
			}
			log.info("[+] Successfully generated metric file in location : {}", metricFile);
		} catch (IOException e) {
			log.error("[-] Error while generating metric file", e);
		}
	}

	/**
	 * This method generate a file with list of input fields that couldn't be invalidated.
	 * It will be later sent as attachment in the email, and deleted from the server.
	 * You can check the corresponding sh file for this batch
	 * @param outputPath path
	 */
	public void writeListToFile(String outputPath) {
		File metricFile = new File(Paths.get(outputPath, REJECTED_FILE_NAME).toString());
		try (BufferedWriter bfwriter = new BufferedWriter(new FileWriter(metricFile, true))) {
			message.forEach(msg -> {
				try {
					bfwriter.write(msg);
				} catch (IOException e) {
					log.error("[-] Error while writing to logfile: {}", e.getMessage());
				}
			});
		}catch (IOException e){
			log.error("[-] Error while creating logfile: {} ", e.getMessage());
		}
	}

	public void createOutputFolder(Path path) {
		try{

			// if folder not exists, create it.
			if (!Files.exists(path)) {
				Files.createDirectories(path);
			}
		}catch (Exception e){
			log.error("[-] error while creating following directory - {}", path);
		}

	}

}
