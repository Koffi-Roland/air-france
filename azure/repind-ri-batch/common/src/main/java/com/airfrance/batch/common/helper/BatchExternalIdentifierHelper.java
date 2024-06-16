package com.airfrance.batch.common.helper;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.exception.jraf.JrafDomainNoRollbackException;
import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;
import com.airfrance.ref.type.ProcessEnum;
import com.airfrance.repind.dto.external.ExternalIdentifierDTO;
import com.airfrance.repind.dto.external.ExternalIdentifierDataDTO;
import com.airfrance.repind.dto.individu.IndividuDTO;
import com.airfrance.repind.dto.individu.SignatureDTO;
import com.airfrance.repind.dto.reference.RefTypExtIDDTO;
import com.airfrance.repind.service.external.internal.ExternalIdentifierDS;
import com.airfrance.repind.service.individu.internal.IndividuDS;
import com.airfrance.repind.service.reference.internal.RefTypExtIDDS;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("BatchExternalIdentifierHelper")
public class BatchExternalIdentifierHelper {

	private static final String SIGNATURE = "BATCH_QVI";
	private static final String SITE = "QVI";
	
	@Autowired
	private ExternalIdentifierDS eids;
	
	@Autowired
	private IndividuDS indds;

	@Autowired
	private RefTypExtIDDS rteidds;
	
	/**
	 * Method which call external individual only creation and create or update external identifier
	 * @param gin : gin to modify if exists, empty string else
	 * @param external : external to create or update
	 * @param type : external type
	 * @return individual GIN
	 * @throws JrafDomainException
	 */
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public String createOrUpdateIndividualAndExtId(String gin, String external, String type, String airline, String name) throws JrafDomainException {
		IndividuDTO ind = null;
		if (StringUtils.isBlank(gin)) { // Individual not known in RI
			ind = createDefaultIndividual();
			gin = indds.createAnIndividualExternal(ind);
			ind.setSgin(gin);
		} else { // Individual known in RI
			ind = indds.getByGin(gin);
		}
		
		// Get existing ExternalIdentifier if exists
		ExternalIdentifierDTO eidDTO = eids.existExternalIdentifier(external, type);
		
		// UPDATE
		if (eidDTO != null) { // Is in database
			boolean isAlreadyUsedBy = false;
			
			// There is wome data that are missing, we create the new one
			if (eidDTO.getExternalIdentifierDataList() == null) {
				// On cree un nouveau EXTERNAL_IDENTIFIER_DATA car, il est absent
				ExternalIdentifierDataDTO extToUpdate = createExternalDataAirline(airline);
				List<ExternalIdentifierDataDTO> extToUpdateList = new ArrayList<ExternalIdentifierDataDTO>();
				extToUpdateList.add(extToUpdate);				
				eidDTO.setLastSeenDate(new Date());							// On dit qu on l'a vu passer
				eidDTO.setExternalIdentifierDataList(extToUpdateList);
				isAlreadyUsedBy = true;										// Pour ne pas avoir de doublon
				
			} else {
				for (ExternalIdentifierDataDTO extToUpdate : eidDTO.getExternalIdentifierDataList()) {
					extToUpdate.setIdentifierDataId(null); // Reset Id for EI data - evict hibernate error
					// On ne met a jour le USED BY que si il y a un AirLine de defini
					if (!"".equals(airline) && ("USED_BY_" + airline).equals(extToUpdate.getKey())) {						
						eidDTO.setLastSeenDate(new Date());
						extToUpdate.setValue("Y"); 							// Default
						extToUpdate.setModificationDate(new Date());		// On a trouve le SOCIAL
						extToUpdate.setModificationSignature(SIGNATURE);	// On le met a jour
						extToUpdate.setModificationSite(SITE);
						isAlreadyUsedBy = true;
						break;												// On en a trouve qu un et il n y en a qu un seul
					}
				}
			}
			if (!isAlreadyUsedBy) {
				eidDTO.getExternalIdentifierDataList().add(createExternalDataAirline(airline));
				eidDTO.getExternalIdentifierDataList().add(createExternalDataPnmname(name));
			}
		// INSERT
		} else { // Not in DB
			eidDTO = new ExternalIdentifierDTO();
			eidDTO.setGin(gin);
			eidDTO.setIdentifier(external);
			eidDTO.setType(type);
			eidDTO.setLastSeenDate(new Date());
			if (!"".equals(airline)) {
				eidDTO.setExternalIdentifierDataList(new ArrayList<ExternalIdentifierDataDTO>());
				eidDTO.getExternalIdentifierDataList().add(createExternalDataAirline(airline));
				eidDTO.getExternalIdentifierDataList().add(createExternalDataPnmname(name));
			}
		}
		
		SignatureDTO signature = new SignatureDTO();
		signature.setSignature(SIGNATURE);
		signature.setDate(new Date());
		signature.setSite(SITE);
		
		// Create or Update external
		eids.updateExternalIdentifier(ind, eidDTO, signature);

		return gin;
	}
	
	/**
	 * Create default individual with few values
	 * @return the new {@link IndividuDTO}
	 */
	private IndividuDTO createDefaultIndividual() {
		IndividuDTO dto = new IndividuDTO();

		dto.setVersion(1);
		dto.setStatutIndividu("V");
		dto.setCivilite("MR");
		dto.setSexe("U");
		dto.setNonFusionnable("N");
		dto.setType(ProcessEnum.E.getCode());
		// Adding signature
		addSignatureToIndividuDTO(dto);

		return dto;
	}
	
	/**
	 * Add default signature to default individual
	 * @param dto individual dto
	 */
	private void addSignatureToIndividuDTO(IndividuDTO dto) {
			dto.setDateCreation(new Date());
			dto.setSignatureCreation(SIGNATURE);
			dto.setSiteCreation(SITE);
			dto.setDateModification(new Date());
			dto.setSignatureModification(SIGNATURE);
			dto.setSiteModification(SITE);
	}
	
	/**
	 * Create default external data with key USED_BY_KL and value to "Y"
	 * @param airline air line
	 * @return ExternalIdentifierDataDTO
	 */
	private ExternalIdentifierDataDTO createExternalDataAirline(String airline) {
		ExternalIdentifierDataDTO tmpEiddata = new ExternalIdentifierDataDTO();
		
		// On ne cree un ExternalData que si le AirLine n'est pas vide
		if (!"".equals(airline)) { 
			tmpEiddata.setKey("USED_BY_" + airline);
			tmpEiddata.setValue("Y");		
			tmpEiddata.setCreationDate(new Date());
			tmpEiddata.setCreationSignature(SIGNATURE);
			tmpEiddata.setCreationSite(SITE);
			tmpEiddata.setModificationDate(new Date());
			tmpEiddata.setModificationSignature(SIGNATURE);
			tmpEiddata.setModificationSite(SITE);
		}
		
		return tmpEiddata;
	}

	/**
	 * Create default external data with key PNM_NAME and the given value
	 * @param pnmName name
	 * @return ExternalIdentifierDataDTO
	 */
	private ExternalIdentifierDataDTO createExternalDataPnmname(String pnmName) {
		ExternalIdentifierDataDTO tmpEiddata = new ExternalIdentifierDataDTO();
		
		// On ne cree un ExternalData que si le AirLine n'est pas vide
		if (!"".equals(pnmName)) { 
			tmpEiddata.setKey("PNM_NAME");
			tmpEiddata.setValue(pnmName);		
			tmpEiddata.setCreationDate(new Date());
			tmpEiddata.setCreationSignature(SIGNATURE);
			tmpEiddata.setCreationSite(SITE);
			tmpEiddata.setModificationDate(new Date());
			tmpEiddata.setModificationSignature(SIGNATURE);
			tmpEiddata.setModificationSite(SITE);
		}
		
		return tmpEiddata;
	}

	
	public List<String> getAllTypeExtId() throws JrafDomainException {
		List<RefTypExtIDDTO> rteIDs = rteidds.findAll();
		List<String> typeExtId = new ArrayList<String>();
		for (RefTypExtIDDTO rte : rteIDs) {
			typeExtId.add(rte.getExtID());
		}
		return typeExtId;
	}
}
