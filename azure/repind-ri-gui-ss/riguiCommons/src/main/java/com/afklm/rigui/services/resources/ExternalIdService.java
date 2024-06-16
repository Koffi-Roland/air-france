package com.afklm.rigui.services.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afklm.rigui.model.individual.ModelExternalIdentifier;
import com.afklm.rigui.services.ServiceException;
import com.afklm.rigui.services.helper.resources.ExternalIdHelper;
import com.afklm.rigui.dao.external.ExternalIdentifierDataRepository;
import com.afklm.rigui.dao.external.ExternalIdentifierRepository;
import com.afklm.rigui.entity.external.ExternalIdentifier;
import com.afklm.rigui.entity.external.ExternalIdentifierData;
import com.sun.xml.ws.api.tx.at.Transactional;

@Service
public class ExternalIdService {
	
	@Autowired
	private ExternalIdentifierRepository externalIdentifierRepository;
	
	@Autowired
	private ExternalIdentifierDataRepository externalIdentifierDataRepository;
	
	@Autowired
	private ExternalIdHelper externalIdHelper;
	
	/**
	 * This method GET all the external identifiers according to the GIN in parameter.
	 * @param gin
	 * @return a list of ModelExternalIdentifier
	 * @throws ServiceException
	 */
	@Transactional
	public List<ModelExternalIdentifier> getAll(String gin) throws ServiceException {
		
		List<ModelExternalIdentifier> modelExternalIdentifiers = new ArrayList<>();
		
		// Fetch all the valid external identifiers of the GIN in the appropriate repository
		List<ExternalIdentifier> externalIdentifiers = externalIdentifierRepository.findByGin(gin);
		
		
		// Loop through all the entities found
		for (ExternalIdentifier externalIdentifier : externalIdentifiers) {
			// Fetch the external identifier data for the current external identifier entity
			List<ExternalIdentifierData> externalIdentifierDatas = externalIdentifierDataRepository.findByExternalIdentifier(externalIdentifier);
			// Using the external identifier and external identifier data entities, build a model to represent external identifier
			modelExternalIdentifiers.add(externalIdHelper.buildExternalIdentifierModel(externalIdentifier, externalIdentifierDatas));
		}
		
		return modelExternalIdentifiers;
		
	}

}
