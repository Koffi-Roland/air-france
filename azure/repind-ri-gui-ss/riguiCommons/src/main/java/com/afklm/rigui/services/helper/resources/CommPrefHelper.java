package com.afklm.rigui.services.helper.resources;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.afklm.rigui.model.individual.ModelCommunicationPreference;
import com.afklm.rigui.model.individual.ModelMarketLanguage;
import com.afklm.rigui.model.individual.ModelSignature;
import com.afklm.rigui.entity.individu.CommunicationPreferences;
import com.afklm.rigui.entity.individu.MarketLanguage;

@Component
public class CommPrefHelper {
	
	@Autowired
	public DozerBeanMapper dozerBeanMapper;
	
	/**
	 * Convert a CommunicationPreferences object to a ModelCommunicationPreference object
	 * @param communicationPreferences
	 * @return a ModelCommunicationPreference
	 */
	public ModelCommunicationPreference buildCommPrefModel(CommunicationPreferences communicationPreferences) {
		return dozerBeanMapper.map(communicationPreferences, ModelCommunicationPreference.class);
	}
	
	/**
	 * Set the signature data of a ModelMarketLanguage according to the data in the associated entity (MarketLanguage)
	 * @param modelMarketLanguage
	 * @param marketLanguage
	 */
	public void setMarketLanguageSignature(ModelMarketLanguage modelMarketLanguage, MarketLanguage marketLanguage) {
		ModelSignature signature = new ModelSignature();
		signature.setCreationDate(marketLanguage.getCreationDate());
		signature.setCreationSignature(marketLanguage.getCreationSignature());
		signature.setCreationSite(marketLanguage.getCreationSite());
		signature.setModificationDate(marketLanguage.getModificationDate());
		signature.setModificationSignature(marketLanguage.getModificationSignature());
		signature.setModificationSite(marketLanguage.getModificationSite());
		modelMarketLanguage.setSignature(signature);
	}

}
