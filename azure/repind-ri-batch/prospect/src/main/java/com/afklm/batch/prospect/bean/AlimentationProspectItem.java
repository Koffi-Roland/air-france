package com.afklm.batch.prospect.bean;

import com.airfrance.repind.dto.ws.createupdateindividual.CreateUpdateIndividualRequestDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AlimentationProspectItem {

    CreateUpdateIndividualRequestDTO requestWS;

    String email;

}
