package com.afklm.repind.msv.mapping.services;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.afklm.repind.msv.mapping.criteria.MappingLanguageCriteria;
import com.afklm.repind.msv.mapping.criteria.MappingTableForContextCriteria;
import com.afklm.repind.msv.mapping.entity.RefMarketCountryLanguage;
import com.afklm.repind.msv.mapping.model.MappingLanguageModel;
import com.afklm.repind.msv.mapping.model.error.BusinessErrorList;
import com.afklm.repind.msv.mapping.repository.RefMarketCountryLanguageRepository;
import com.afklm.repind.msv.mapping.services.exception.ServiceException;
import com.afklm.repind.msv.mapping.wrapper.WrapperMappingTableForContext;

import com.afklm.repind.msv.mapping.config.Config.BeanMapper;

@Component
public class MappingLanguageService {

	@Autowired
	private RefMarketCountryLanguageRepository refMarketCountryLanguageRepository;

	@Autowired
	private BeanMapper mapper;

	/**
	 * Map a LANGUAGE KLM code on an AF code  
	 *
	 * @return service
	 * @throws ServiceException 
	 */
	public MappingLanguageModel provideMappingFromLanguage(MappingLanguageCriteria mappingLanguageCriteria)
			throws ServiceException {
		
		RefMarketCountryLanguage ref;
		if (mappingLanguageCriteria.isIsoLanguage()) {
			ref = refMarketCountryLanguageRepository.findByContextLanguageIsoAndMarket(
					mappingLanguageCriteria.getContext(), mappingLanguageCriteria.getLanguage(),
					mappingLanguageCriteria.getMarket());
		} else {
			ref = refMarketCountryLanguageRepository.findByContextLanguageNoneIsoAndMarket(
					mappingLanguageCriteria.getContext(), mappingLanguageCriteria.getLanguage(),
					mappingLanguageCriteria.getMarket());
		}

		if (ref == null) {
			throw new ServiceException(BusinessErrorList.API_LANGUAGE_MAPPING_NOT_FOUND.getError()
					.setDescription("Context : " + mappingLanguageCriteria.getContext() + " Market : "
							+ mappingLanguageCriteria.getMarket() + " Language : "
							+ mappingLanguageCriteria.getLanguage()),
					HttpStatus.NOT_FOUND);
		}

		return mapper.refMarketCountryLanguageToMappingLanguageModel(ref);
	}

	public List<WrapperMappingTableForContext> provideMappingTableByContext(
			MappingTableForContextCriteria mappingTableForContextCriteria)
			throws ServiceException {

		List<WrapperMappingTableForContext> response = new LinkedList<>();
		if (mappingTableForContextCriteria.isAllContexts()) {
			response = getAllContextTable();
		} else {
			response.add(this.getTableByContext(mappingTableForContextCriteria.getContext()));
		}
		return response;

	}

	/**
	 * Method to return all data from refTable for all context
	 * 
	 * @return
	 * @throws ServiceException
	 */
	private List<WrapperMappingTableForContext> getAllContextTable() throws ServiceException {
		List<RefMarketCountryLanguage> refmarketCountryLanguage = refMarketCountryLanguageRepository
				.findAllGroupByContext();
		
		if (refmarketCountryLanguage.isEmpty()) {
			throw new ServiceException(
					BusinessErrorList.API_REF_MAPPING_NOT_FOUND.getError().setDescription("Context : ALLCONTEXTS"),
					HttpStatus.NOT_FOUND);
		}

		List<WrapperMappingTableForContext> result = new LinkedList<>();

		// We took the first context returned by DB
		String contextInProgress = refmarketCountryLanguage.get(0).getRefMarketCountryLanguageId().getScodeContext();
		List<MappingLanguageModel> mappingLanguages = new LinkedList<>();

		for(RefMarketCountryLanguage ref : refmarketCountryLanguage) {
			//If context from ref is different from contextInProgress
			if (!contextInProgress.equals(ref.getRefMarketCountryLanguageId().getScodeContext())) {
				//We save into the result to list for the context (object WrapperMappingTableForContext)
				result.add(new WrapperMappingTableForContext(contextInProgress, mappingLanguages));
				//We prepare the list to saved all mappingTables
				mappingLanguages = new LinkedList<>();
			}
			//We store the last context associated to the last language added
			contextInProgress = ref.getRefMarketCountryLanguageId().getScodeContext();
			//We add the language to the list
			mappingLanguages.add(transformMappingLanguageModelFromRefMarketCountryLanguageForContextTable(ref));
		}

		//Don't forget to add to result the last list of language associated to a context
		result.add(new WrapperMappingTableForContext(contextInProgress, mappingLanguages));

		return result;

	}

	/**
	 * Method to return all data from refTable for 1 context
	 * 
	 * @param context
	 * @return
	 * @throws ServiceException
	 */
	private WrapperMappingTableForContext getTableByContext(String context) throws ServiceException {
		List<RefMarketCountryLanguage> refmarketCountryLanguage = refMarketCountryLanguageRepository
				.findByContext(context);

		if (refmarketCountryLanguage.isEmpty()) {
			throw new ServiceException(
					BusinessErrorList.API_REF_MAPPING_NOT_FOUND.getError()
							.setDescription("Context : " + context),
					HttpStatus.NOT_FOUND);
		}

		List<MappingLanguageModel> mappingLanguages = new LinkedList<>();
		for (RefMarketCountryLanguage ref : refmarketCountryLanguage) {
			//We add the language to the list
			mappingLanguages.add(transformMappingLanguageModelFromRefMarketCountryLanguageForContextTable(ref));
		}

		return new WrapperMappingTableForContext(context, mappingLanguages);
	}

	/**
	 * Method to transform RefMarketCountryLanguage to MappingLanguageModel for
	 * ContextTable only (provideMappingLanguageTable from Controller)
	 * 
	 * @param ref
	 * @return
	 */
	private MappingLanguageModel transformMappingLanguageModelFromRefMarketCountryLanguageForContextTable(
			RefMarketCountryLanguage ref) {
		//We store the last context associated to the last language added
		MappingLanguageModel model = mapper.refMarketCountryLanguageToMappingLanguageModel(ref);
		// We delete useless context in this object because it's returned in parent
		model.setContext(null);
		return model;
	}
}