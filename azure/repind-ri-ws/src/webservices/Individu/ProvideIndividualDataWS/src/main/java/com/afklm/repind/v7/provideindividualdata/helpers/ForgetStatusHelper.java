package com.afklm.repind.v7.provideindividualdata.helpers;

import com.afklm.repind.v7.provideindividualdata.transformers.ProvideIndividualDataTransformV7;
import com.afklm.soa.stubs.w000418.v7.data.ProvideIndividualInformationResponse;
import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.MissingParameterException;
import com.airfrance.ref.exception.NotFoundException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.type.ForgetEnum;
import com.airfrance.repind.dto.individu.ForgottenIndividualDTO;
import com.airfrance.repind.dto.individu.IndividuDTO;
import com.airfrance.repind.dto.individu.adh.individualinformation.IndividualInformationRequestDTO;
import com.airfrance.repind.service.individu.internal.ForgottenIndividualDS;
import com.airfrance.repind.service.individu.internal.IndividuDS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("forgetStatusHelperV7")
public class ForgetStatusHelper {
	private final String F_STATUS = ForgetEnum.STATUS.getCode();       // F
	private final String F_ASKED = ForgetEnum.ASKED.getCode();         // A
	private final String F_CONFIRMED = ForgetEnum.CONFIRMED.getCode(); // C
	private final String F_FORCED = ForgetEnum.FORCED.getCode(); 	   // F
	private final String F_PROCESSED = ForgetEnum.PROCESSED.getCode(); // P
	
	@Autowired
	protected ForgottenIndividualDS mainDS;
	
	@Autowired
	protected IndividuDS individuDS;
	
	@Transactional
	public ProvideIndividualInformationResponse provideIndividualForgetStatus(IndividualInformationRequestDTO requestDTO) throws JrafDomainException {
		checkRequest(requestDTO);
		
		ProvideIndividualInformationResponse response = new ProvideIndividualInformationResponse();
		
		// Get GIN forget status in DB
		List<ForgottenIndividualDTO> listForgottenIndivDTO = mainDS.findByIdentifier(requestDTO.getIdentificationNumber(), requestDTO.getOption());
		ForgottenIndividualDTO fIdentifierFound = null;
		if (listForgottenIndivDTO != null && !listForgottenIndivDTO.isEmpty()) {
			// if multiple gin found ==> Problem on the table forgotten_individual
			if (listForgottenIndivDTO.size() > 1) {
				throw new JrafDomainException("Multiple results found");
			}
			fIdentifierFound = listForgottenIndivDTO.get(0);
		}
		else {
			throw new NotFoundException("No informations found for this context");
		}
		
		// Check in Individual table the status conformity
		IndividuDTO individual = individuDS.getAnyIndividualByGin(requestDTO.getIdentificationNumber());
		checkForgetStatusConformity(fIdentifierFound, individual);
		
		// Transform data received to fill response block
		response.setIndividualResponse(ProvideIndividualDataTransformV7.transformForgetInformationToIndividual(fIdentifierFound));
		response.getWarningResponse().add(ProvideIndividualDataTransformV7.transformForgetInformationToWarning(fIdentifierFound));
		
		return response;
	}

	public void checkForgetStatusConformity(ForgottenIndividualDTO fIdentifierFound, IndividuDTO individual) throws JrafDomainException {
		// individual found --> Status F / Context C or any status / context A 
		if (individual != null) { 
			if (F_STATUS.equalsIgnoreCase(individual.getStatutIndividu()) && !(F_CONFIRMED.equalsIgnoreCase(fIdentifierFound.getContext()) 
					|| F_FORCED.equalsIgnoreCase(fIdentifierFound.getContext()))) {
				throw new JrafDomainException("Forget status don't match with individual status");
			}
			if (!F_STATUS.equalsIgnoreCase(individual.getStatutIndividu()) && !F_ASKED.equalsIgnoreCase(fIdentifierFound.getContext())) {
				// Identifier is identified as forgotten confirmed or purged but individual status is not F in individual table
				throw new JrafDomainException("Forget status don't match with individual status");
			}
		}
		else {
			// individual not found --> identifier context = 'P'
			if (!F_PROCESSED.equalsIgnoreCase(fIdentifierFound.getContext())) {
				throw new JrafDomainException("Forget status don't match with individual status");
			}
		}
	}

	private static void checkRequest(IndividualInformationRequestDTO requestDTO) throws JrafDomainException {
		// TODO Auto-generated method stub
		if (requestDTO == null || requestDTO.getIdentificationNumber() == null) {
			throw new MissingParameterException("Individual indentification is missing");
		}
		if (requestDTO.getOption() != null && !"GIN".equalsIgnoreCase(requestDTO.getOption())) {
			throw new InvalidParameterException("Only individual identifier of type GIN is allowed for this context");
		}
	}
}
