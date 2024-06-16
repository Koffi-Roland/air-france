package com.afklm.repind.msv.manage.individual.identifier.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@JsonInclude
public class WrapperFindGinByIdentifierResponse {

	@ApiModelProperty(required = true, notes = "GIN found in database. If no GIN is found, the value null is returned.")
	private String gin;
}
