package com.afklm.rigui.services.helper.resources;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.afklm.rigui.model.individual.ModelExternalIdentifier;
import com.afklm.rigui.model.individual.ModelExternalIdentifierData;
import com.afklm.rigui.entity.external.ExternalIdentifier;
import com.afklm.rigui.entity.external.ExternalIdentifierData;

@Component
public class ExternalIdHelper {
	
	@Autowired
	public DozerBeanMapper dozerBeanMapper;
	
	/**
	 * Given a ExternalIdentifier and a list of ExternalIdentifierData this method will build a ModelExternalIdentifier and return it.
	 * @param externalIdentifier
	 * @param data
	 * @return a ModelExternalIdentifier
	 */
	public ModelExternalIdentifier buildExternalIdentifierModel(ExternalIdentifier externalIdentifier, List<ExternalIdentifierData> data) {
		
		// Convert the entity into a model
		ModelExternalIdentifier modelExternalIdentifier = dozerBeanMapper.map(externalIdentifier, ModelExternalIdentifier.class);
		
		Set<ModelExternalIdentifierData> modelExternalIdentifierDatas = new HashSet<>();
		// Loop through all the external identifier data given in parameter
		for (ExternalIdentifierData externalIdentifierData : data) {
			// Convert the entity into a model and add it to the set of external identifier data set
			modelExternalIdentifierDatas.add(dozerBeanMapper.map(externalIdentifierData, ModelExternalIdentifierData.class));
		}
		// Set the external identifier data manually for the model
		modelExternalIdentifier.setExternalIdentifierData(modelExternalIdentifierDatas);
		
		return modelExternalIdentifier;
		
	}

}
