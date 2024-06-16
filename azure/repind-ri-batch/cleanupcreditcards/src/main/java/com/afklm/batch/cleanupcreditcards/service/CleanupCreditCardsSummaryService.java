package com.afklm.batch.cleanupcreditcards.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
@Setter
@Getter
public class CleanupCreditCardsSummaryService {
	public static final String PROCESSED_SUCCESSFULLY = "Processed successfully : ";
	public static final String DELETE_FAILED = "Delete failed : ";
	public static final String DELETE_SUCCESS = "Delete successfully : ";
	public static final String NEWLINE = " \n";

	private final AtomicInteger processed = new AtomicInteger();
	private final AtomicInteger deleteSuccess = new AtomicInteger();
	private final AtomicInteger deleteFailed = new AtomicInteger();

	public void incrementProcessedCounter() {
		processed.incrementAndGet();
	}

	public void incrementDeleteSuccessCounter() {
		deleteSuccess.incrementAndGet();
	}


	public void incrementDeleteFailedCounter() {
		deleteFailed.incrementAndGet();
	}


	public String printCounter() {
		return (new StringBuilder(PROCESSED_SUCCESSFULLY).append(processed)
				.append(NEWLINE).append(DELETE_SUCCESS).append(deleteSuccess).append(NEWLINE)
				.append(DELETE_FAILED).append(deleteFailed).append(NEWLINE)).toString();
	}

}
