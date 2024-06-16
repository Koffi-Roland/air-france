package com.airfrance.batch.adrInvalidBarecode.model;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class OutputRecord {

	private String numeroContrat;

	private String sain;

	private String dateModification;

	private String message;

}
