package com.airfrance.repind.service.agency.searchagencyonmulticriteria.ds.builder;

import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.repind.entity.adresse.PostalAddress;
import com.airfrance.repind.entity.agence.Agence;
import com.airfrance.repind.entity.firme.NumeroIdent;
import com.airfrance.repind.entity.firme.Synonyme;
import com.airfrance.repind.service.agency.searchagencyonmulticriteria.ds.FindPriorityZcDS;
import com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto.AgencyInformationsDTO;
import com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto.BusinessException;
import com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto.IdentificationDTO;
import com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto.PostalAddressBlocDTO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


@Component
public class AgencyInformationsBuilder {

	/* =============================================== */
	/* INJECTED BEANS */
	/* =============================================== */
	@Autowired
	@Qualifier("AgencyPostalAddressBuilder")
	private PostalAddressBuilder postalAddressBuilder = null;

	@Autowired
	@Qualifier("AgencyFindPriorityZcDS")
	private FindPriorityZcDS findPriorityZcDSBean = null;
	
	private static Log LOGGER  = LogFactory.getLog(AgencyInformationsBuilder.class);
	
	public static final String SYNONYME_USUAL_NAME_TYPE = "U";
	public static final String SYNONYME_COMMERCIAL_NAME_TYPE = "M";

	/* =============================================== */
	/* PUBLIC METHODS */
	/* =============================================== */

	/**
	 * build a AgencyInformations instance (returned as part of the answer) from
	 * a Agence instance (which is a persitent entity)
	 * 
	 * @throws BusinessException
	 */
	public AgencyInformationsDTO build(Agence agence) throws BusinessException {
		AgencyInformationsDTO agencyInformationsDTO = new AgencyInformationsDTO();

		// AGENCY KEY
		if (agence.getGin() != null) {
			agencyInformationsDTO.setAgencyKey(agence.getGin());
		}

		// AGENCY STATUS
		if (agence.getStatut() != null) {
			agencyInformationsDTO.setAgencyStatus(agence.getStatut());
		}

		// AGENCY NAME
		if (agence.getNom() != null) {
			agencyInformationsDTO.setAgencyName(agence.getNom());
		}

		// AGENCY USUAL NAME & COMMERCIAL NAME
		if (agence.getSynonymes() != null && !agence.getSynonymes().isEmpty()) {
			for (Synonyme synonymeLoop : agence.getSynonymes()) {
				// USUAL NAME
				if (synonymeLoop.getType().equals(SYNONYME_USUAL_NAME_TYPE)) {
					agencyInformationsDTO.setAgencyUsualName(synonymeLoop.getNom());
				}

				// COMMERCIAL NAME
				if (synonymeLoop.getType().equals(SYNONYME_COMMERCIAL_NAME_TYPE)) {
					agencyInformationsDTO.setAgencyCommercialName((synonymeLoop.getNom()));
				}
			}
		}

		// AGENCY TYPE
		if (agence.getType() != null) {
			agencyInformationsDTO.setAgencyType(agence.getType());
		}

		// TYPE AGREEMENT ET AGREEMENT NUMBER
		if (agence.getNumerosIdent() != null && !agence.getNumerosIdent().isEmpty()) {
			for (NumeroIdent n : agence.getNumerosIdent()) {
				/*if (("AT".equalsIgnoreCase(n.getType()) || "IA".equalsIgnoreCase(n.getType())
						|| "AG".equalsIgnoreCase(n.getType()))
						&& (n.getDateFermeture() == null || n.getDateFermeture().after(new Date()))) */
				{
					IdentificationDTO idDTO = new IdentificationDTO();
					idDTO.setIdentificationType(n.getType());
					idDTO.setIdentificationValue(n.getNumero());
					agencyInformationsDTO.getIdentityBloc().add(idDTO);
					//agencyInformationsDTO.setTypeAgreement(n.getType());
					//agencyInformationsDTO.setAgreementNumber(n.getNumero());										
					
				}
			}
		}
		else
		{
			LOGGER.warn("WARNING : AGENCY WITHOUT IDENTITY NUMBER" + agence.getGin());
			return null;
		}

		// AGENCY ZC
		try {
			handleZC(agence, agencyInformationsDTO);
		} catch (InvalidParameterException e) {
			LOGGER.error(e);
		}

		// POSTAL ADDRESSES
		handlePostalAddresses(agence, agencyInformationsDTO);

		
		return agencyInformationsDTO;
	}

	/* =============================================== */
	/* PRIVATE METHODS */
	/* =============================================== */

	/**
	 * Method that read the commercial zones of a Agence instance
	 * 
	 * @param agence
	 * @param agencyInformationsDTO
	 * @throws InvalidParameterException
	 */
	private void handleZC(Agence agence, AgencyInformationsDTO agencyInformationsDTO) throws InvalidParameterException {
		if ((agence.getPmZones() != null) && (!agence.getPmZones().isEmpty())) {
			findPriorityZcDSBean.findPriorityZc(agence, agencyInformationsDTO);
		}
	}

	/**
	 * Method that obtains postal addresses from a Agence instance
	 * 
	 * @param agence
	 * @param agencyInformationsDTO
	 */
	private void handlePostalAddresses(Agence agence, AgencyInformationsDTO agencyInformationsDTO) {
		if (agence.getPostalAddresses() != null && !agence.getPostalAddresses().isEmpty()) {
			for (PostalAddress postalAddress : agence.getPostalAddresses()) {
				if (postalAddress.getSstatut_medium().equals("V")
						&& postalAddress.getScode_medium().equals("L")) {
					PostalAddressBlocDTO postalAddressBlocDTO = postalAddressBuilder.build(postalAddress);
					agencyInformationsDTO.setPostalAddressBloc(postalAddressBlocDTO);
					break;
				}
			}
		}
	}
}
