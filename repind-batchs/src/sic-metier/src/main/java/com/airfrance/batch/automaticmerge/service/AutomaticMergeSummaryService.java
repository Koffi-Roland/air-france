package com.airfrance.batch.automaticmerge.service;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
public class AutomaticMergeSummaryService {

	private final AtomicInteger processedLines = new AtomicInteger();
	private final AtomicInteger nbMerged = new AtomicInteger();
	private final AtomicInteger failedMergeInDB = new AtomicInteger();
	private final AtomicInteger skippedGins = new AtomicInteger();
	private final AtomicInteger skipLineError = new AtomicInteger();
	private final List<String> message = new ArrayList<>();

	public void incrementProcessedLinesCounter() {
		processedLines.incrementAndGet();
	}

	public void incrementNbMerged() {
		nbMerged.incrementAndGet();
	}

	public void incrementFailedMergeInDB() {
		failedMergeInDB.incrementAndGet();
	}

	public void incrementSkippedGinsCounter() {
		skippedGins.incrementAndGet();
	}
	public void incrementSkipLineError() {
		skipLineError.incrementAndGet();
	}

	public void addErrorMessage(String error) {
		message.add(error);
	}

	public void generateMetricFile(String outputPath, String metricFileName) {

		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
		String dDay = sdf.format(new Date());
		try {

			File metricFile = new File(Paths.get(outputPath, metricFileName).toString());
			try (BufferedWriter bfwMetricTxt = new BufferedWriter(new FileWriter(metricFile, true))) {
				bfwMetricTxt.write("\n\nDATE OF MERGE: " + dDay +
						"\nTOTAL OF PROCESSED LINE:" + this.getProcessedLines().get() +
						"\nTOTAL OF SUCCESS MERGE:" + this.getNbMerged().get() +
						"\nTOTAL OF FAILED MERGE IN DB :" + this.getFailedMergeInDB().get() +
						"\nTOTAL OF SKIPPED LINE READ ( when we can't parse a line and extract GINS) :" + this.getSkipLineError().get() +
						"\nTOTAL OF SKIPPED GINS (#gins skipped do not exist in RI DB):" + this.getSkippedGins().get()
				);
			}
			log.info("[+] Successfully generated metric file in location : {}", metricFile);
		} catch (IOException e) {
			log.error("[-] Error while generating metric file", e);
		}
	}

}
