package com.airfrance.batch.compref.injestadhocdata.service;

import static com.airfrance.batch.compref.injestadhocdata.helper.Constant.NEWLINE;
import static com.airfrance.batch.compref.injestadhocdata.helper.Constant.PROCESSED_SUCCESSFULLY;
import static com.airfrance.batch.compref.injestadhocdata.helper.Constant.PROCESSING_FAILED;
import static com.airfrance.batch.compref.injestadhocdata.helper.Constant.TOTAL;
import static com.airfrance.batch.compref.injestadhocdata.helper.Constant.VALIDATION_FAILED;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.Setter;

@Service
@Setter
@Getter
public class InjestAdhocDataSummaryService {

	private final AtomicInteger processed = new AtomicInteger();
	private final AtomicInteger validationFailed = new AtomicInteger();
	private final AtomicInteger total = new AtomicInteger();
	private final AtomicInteger failed = new AtomicInteger();
	private final List<String> message = new ArrayList<>();

	public void incrementProcessedCounter() {
		processed.incrementAndGet();
	}

	public void incrementValidationCounter() {
		validationFailed.incrementAndGet();
	}

	public void incrementTotalCounter() {
		total.incrementAndGet();
	}

	public void incrementFailedCounter() {
		failed.incrementAndGet();
	}

	public void addErrorMessage(String error) {
		message.add(error);
	}

	public List<String> getErrorMessage() {
		return message;
	}

	public String printCounter() {
		return (new StringBuilder(TOTAL).append(total).append(NEWLINE).append(PROCESSED_SUCCESSFULLY).append(processed)
				.append(NEWLINE).append(VALIDATION_FAILED).append(validationFailed).append(NEWLINE)
				.append(PROCESSING_FAILED).append(failed).append(NEWLINE)).toString();
	}

}
