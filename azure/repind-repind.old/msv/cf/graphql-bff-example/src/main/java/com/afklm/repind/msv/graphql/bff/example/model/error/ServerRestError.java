package com.afklm.repind.msv.graphql.bff.example.model.error;

import lombok.*;

import java.io.Serializable;

/**
 * Part of Full serer rest error
 *
 * @author T176991
 *
 */
@Getter
@Setter
@With
@AllArgsConstructor
@NoArgsConstructor
public class ServerRestError implements Serializable {

	private  String status;

	private RestError restError;

	@Override
	public String toString() {
		return "ServerRestError{" +
				"status='" + status + '\'' +
				", restError=" + restError +
				'}';
	}
}
