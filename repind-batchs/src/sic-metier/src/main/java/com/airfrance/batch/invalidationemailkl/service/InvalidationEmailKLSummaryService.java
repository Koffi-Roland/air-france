package com.airfrance.batch.invalidationemailkl.service;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;
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
public class InvalidationEmailKLSummaryService {

	private static final String METRIC_FILE_NAME = "metric_results.txt";
	private static final String REJECTED_FILE_NAME = "reject_inputs.txt";

	private final AtomicInteger nbProcessedLines = new AtomicInteger();

	private final AtomicInteger nbSuccessEmailInvalid = new AtomicInteger();
	private final AtomicInteger nbEmailNotFound = new AtomicInteger();
	private final AtomicInteger nbEmptyMandatoryFields = new AtomicInteger();
	private final AtomicInteger nbTechnicalError = new AtomicInteger();

	private final List<String> message = new ArrayList<>();

	public void incrementNbProcessedLines() {
		nbProcessedLines.incrementAndGet();
	}
	public void incrementNbSuccessEmailInvalid() {
		nbSuccessEmailInvalid.incrementAndGet();
	}
	public void incrementNbEmailNotFound() {
		nbEmailNotFound.incrementAndGet();
	}
	public void incrementNbEmptyMandatoryFields() {
		nbEmptyMandatoryFields.incrementAndGet();
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
		try {

			File metricFile = new File(Paths.get(outputPath, METRIC_FILE_NAME).toString());
			try (BufferedWriter bfwMetricTxt = new BufferedWriter(new FileWriter(metricFile, false))) {
				int nbProcessed = Math.max(0, this.getNbProcessedLines().get() - 1);
				bfwMetricTxt.write("Emails INVALIDATION: " + dDay +
						"\nTOTAL OF PROCESSED LINES:" + nbProcessed +
						"\nTOTAL OF SUCCESS EMAIL INVALIDATION:" + this.getNbSuccessEmailInvalid().get() +
						"\nTOTAL OF EMAILS NOT FOUND:" + this.getNbEmailNotFound().get() +
						"\nTOTAL OF EMPTY MANDATORY FIELDS:" + this.getNbEmptyMandatoryFields().get() +
						"\nTOTAL OF TECHNICAL ERRORS:" + this.getNbTechnicalError().get()+"\n\n"
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

}
