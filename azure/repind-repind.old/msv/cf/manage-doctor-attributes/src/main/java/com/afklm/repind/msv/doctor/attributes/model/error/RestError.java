package com.afklm.repind.msv.doctor.attributes.model.error;

import lombok.Getter;
import lombok.Setter;
import lombok.With;
import org.springframework.http.HttpStatus;

/**
 * Part of Full rest error
 *
 * @author M312812
 *
 */
public class RestError {

	@Getter @Setter
	private final String code;
	@Getter @Setter @With
	private String description;
	@Getter @Setter @With
	private HttpStatus httpStatus;

	public RestError(String code, String description, HttpStatus httpStatus) {
		this.code = code;
		this.description = description;
		this.httpStatus = httpStatus;
	}

	/**
	 * Default constructor from
	 * 
	 * @param errorMessage
	 */
	public RestError(final ErrorMessage errorMessage , HttpStatus iHttpStatus) {
		this.code = errorMessage.getCode();
		this.description = errorMessage.getDescription();
		this.httpStatus = iHttpStatus;

	}


}