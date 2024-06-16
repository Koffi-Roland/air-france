package com.afklm.batch.mergeduplicatescore.model;


import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OutputRecord {

	private String ginTarget;

	private String ginSource;

	private Date mergeDate;

	private String mergeDateAsString;

}
