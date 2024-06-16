package com.airfrance.batch.adrInvalidBarecode.model;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class InputRecord {

	private String numeroContrat;

	private String sain;

	private String dateModification;

}
