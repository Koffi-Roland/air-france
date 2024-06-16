package com.airfrance.batch.automaticmerge.model;


import lombok.*;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutputRecord {

	private String ginTarget;

	private String ginSource;

	private Date mergeDate;

	private String mergeDateAsString;

}
