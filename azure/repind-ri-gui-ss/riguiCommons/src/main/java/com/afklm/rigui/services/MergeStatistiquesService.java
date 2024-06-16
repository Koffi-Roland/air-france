package com.afklm.rigui.services;

import java.util.List;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.afklm.rigui.criteria.merge.MergeStatistiquesCriteria;
import com.afklm.rigui.model.individual.ModelBasicIndividualMergeStatistiquesData;
import com.afklm.rigui.model.individual.requests.ModelMergeStatistiques;
import com.afklm.rigui.repository.MergeStatistiquesRepository;
import com.afklm.rigui.wrapper.searches.WrapperIndividualSearch;

@Service
public class MergeStatistiquesService {

	@Autowired
	private Mapper dozerBeanMapper;

	/** logger */
	private static final Logger LOGGER = LoggerFactory.getLogger(MergeStatistiquesService.class);
	
	@Autowired
	private MergeStatistiquesRepository mergeStatistiquesRepository;

	public WrapperIndividualSearch getMergeStatistiques(MergeStatistiquesCriteria criteria, Pageable pageable) {
		List<ModelMergeStatistiques> listMergeStatistiques = mergeStatistiquesRepository
				.findByGinMergeNotNull(pageable, criteria.getDayInPast());
		WrapperIndividualSearch result = new WrapperIndividualSearch();
		for (ModelMergeStatistiques modelMergeStatistique : listMergeStatistiques) {
			result.data
					.add(dozerBeanMapper.map(modelMergeStatistique, ModelBasicIndividualMergeStatistiquesData.class));
		}
		return result;
	}

	public List<Object[]> getMergeStatistiquesExported() {
		List<Object[]> listMergeStatistiques = mergeStatistiquesRepository.findByGinMergeNotNull();
		return listMergeStatistiques;
	}
}
