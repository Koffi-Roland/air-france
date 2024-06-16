package com.afklm.repind.msv.handicap.wrapper;

import com.afklm.repind.msv.handicap.model.HandicapModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WrapperHandicapProvideResponse {
	
	private String gin;

	private List<HandicapModel> handicaps;
}