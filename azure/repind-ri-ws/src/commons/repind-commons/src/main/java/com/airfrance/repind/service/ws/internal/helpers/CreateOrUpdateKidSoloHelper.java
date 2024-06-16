package com.airfrance.repind.service.ws.internal.helpers;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dto.individu.IndividuDTO;
import com.airfrance.repind.dto.individu.TelecomDTO;
import com.airfrance.repind.dto.ws.IndividualInformationsDTO;
import com.airfrance.repind.dto.ws.TelecomRequestDTO;
import com.airfrance.repind.dto.ws.createupdateindividual.CreateUpdateIndividualRequestDTO;
import com.airfrance.repind.service.individu.internal.IndividuDS;
import com.airfrance.repindutf8.dto.individu.KidSoloIndividuDTO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CreateOrUpdateKidSoloHelper {
	
	private Log LOGGER = LogFactory.getLog(CreateOrUpdateKidSoloHelper.class);
	
	@Autowired
	private IndividuDS individuDS;
	
	@Autowired
	protected CommunicationPreferencesHelper communicationPreferencesHelper;
	
	
	public void updateIndividual(IndividuDTO individuDTO) throws JrafDomainException{
		individuDS.updateAnKidSoloIndividual(individuDTO);
	}
	
	public String createIndividual(IndividuDTO individuDTO) throws JrafDomainException{
		return individuDS.createAnIndividualKidSolo(individuDTO);
	}
	
	public KidSoloIndividuDTO findIndividual(CreateUpdateIndividualRequestDTO request) throws JrafDomainException {
		
		IndividualInformationsDTO info = request.getIndividualRequestDTO().getIndividualInformationsDTO();
		String lastname = info.getLastNameSC();
		String firstname = info.getFirstNameSC();
		String phoneNumber = "";
		
		List<TelecomRequestDTO> telecoms = request.getTelecomRequestDTO();
		List<TelecomDTO> phoneNumbers = new ArrayList<TelecomDTO>();
		if(telecoms != null && telecoms.size() > 0){
			phoneNumber = telecoms.get(0).getTelecomDTO().getPhoneNumber();
			TelecomDTO tel = new TelecomDTO();
			tel.setNumeroTelecom(phoneNumber);
			phoneNumbers.add(tel);
		}
		
		List<KidSoloIndividuDTO> findIndividu = individuDS.findAnKidSoloIndividual(lastname, firstname, phoneNumbers);
		
		if(findIndividu != null && !findIndividu.isEmpty()) {
			if(findIndividu.size() == 1) {
				return findIndividu.get(0);
			}else{
				
				throw new JrafDomainException("Search individual for process K return multiple results");
			}
		}
		return null;
	}

	public boolean checkMandatoryFields(IndividuDTO individuDTO) {
		if(individuDTO != null){

			// REPIND-1003 : Repair critical
			//BirthDate
			if(individuDTO.getDateNaissance() == null){
				return false;
			}
		}
		return true;
	}
	
	
}
