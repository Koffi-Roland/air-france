package com.afklm.batch.adrInvalidBarecode.model;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class InputRecord {

	private String numeroContrat;

	private String sain;

	private String dateModification;

}
