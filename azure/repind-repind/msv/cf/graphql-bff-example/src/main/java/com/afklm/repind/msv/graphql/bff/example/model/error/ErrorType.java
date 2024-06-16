package com.afklm.repind.msv.graphql.bff.example.model.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Error Type for an API
 *
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ErrorType {

	ERROR("ERROR"), WARNING("WARNING");

	private String type;


}
