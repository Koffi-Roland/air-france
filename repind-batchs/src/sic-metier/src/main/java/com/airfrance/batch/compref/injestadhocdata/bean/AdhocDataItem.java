package com.airfrance.batch.compref.injestadhocdata.bean;

import com.airfrance.repind.dto.ws.createupdateindividual.CreateUpdateIndividualRequestDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdhocDataItem {

	private CreateUpdateIndividualRequestDTO individual;
	private String email;
	private String gin;
	private String market;
	private String language;
}
