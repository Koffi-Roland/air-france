package com.airfrance.batch.compref.injestadhocdata.logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FilenameUtils;

import com.airfrance.batch.compref.injestadhocdata.property.InjestAdhocDataPropoerty;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InjestAdhocDataLogger {

	private InjestAdhocDataPropoerty property;
	private String outputPath;
	private String inputFileName;

	public InjestAdhocDataLogger(String inputFileName, String outputPath, InjestAdhocDataPropoerty property) {
		this.outputPath = outputPath;
		this.inputFileName = inputFileName;
		this.property = property;
	}

	public void write(String summary, List<String> messages) {
		File csvOutputFile = new File(
				outputPath + FilenameUtils.getBaseName(inputFileName) + property.getOutputFileExtension());
		try {
			synchronized (csvOutputFile) {
				write(csvOutputFile, summary);
				if (CollectionUtils.isNotEmpty(messages)) {
					messages.stream().forEach((message) -> {
						try {
							write(csvOutputFile, message);
						} catch (IOException e) {
							log.error(e.getMessage());
						}
					});
				}
			}
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	public void write(File file, String data) throws IOException {
		try (FileWriter fw = new FileWriter(file, true)) {
			fw.append(data);
			fw.append(System.getProperty("line.separator"));
		}
	}

}
