package com.afklm.batch.mergeduplicatescore.logger;

import com.airfrance.batch.common.metric.ProcessTypeEnum;
import com.airfrance.batch.common.metric.ProcessingMetricDTO;
import com.airfrance.batch.common.metric.StatusEnum;
import com.airfrance.batch.common.utils.ProcessingMetricLogger;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static com.afklm.batch.mergeduplicatescore.helper.Constant.TRIGGER;

@Component
@Slf4j
@AllArgsConstructor
public class MergeDuplicateScoreLogger {

	private String outputPath;
	private String fileName;

	public void writeListToFile(List<String> messages) {
		try(FileWriter fileWriter = new FileWriter( new File(outputPath, fileName), true)){
			messages.forEach(msg -> {
				try {
					fileWriter.write(msg);
				} catch (IOException e) {
					log.error("[-] Error while writing to logfile: {}", e);
				}
			});
		}catch (IOException e){
			log.error("[-] Error while creating logfile: {}", e);
		}
	}

	public void logComo(@NonNull String gin, String message, StatusEnum status){
		ProcessingMetricLogger
				.log(ProcessingMetricDTO.builder()
						.withTrigger(TRIGGER)
						.withProcessType(ProcessTypeEnum.AUTOMERGE)
						.withGin(gin)
						.withMessage(message)
						.withStatus(status)
						.build()
				);
	}

}
