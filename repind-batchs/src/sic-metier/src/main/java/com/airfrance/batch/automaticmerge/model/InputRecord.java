package com.airfrance.batch.automaticmerge.model;


import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class InputRecord {

	private String elementDuplicate;

	private Integer nbGins;

	private String gins;

}
