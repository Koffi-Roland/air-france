package com.afklm.rigui.services.helper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.afklm.rigui.model.individual.ModelBasicIndividualData;
import com.afklm.rigui.wrapper.searches.WrapperIndividualSearch;
import com.afklm.soa.stubs.w001271.v2.data.SearchIndividualByMulticriteriaResponse;
import com.afklm.soa.stubs.w001271.v2.response.Address;
import com.afklm.soa.stubs.w001271.v2.response.Individual;
import com.afklm.soa.stubs.w001271.v2.response.PostalAddressResponse;
import com.afklm.soa.stubs.w001271.v2.sicindividutype.IndividualInformations;

@Component
public class IndividualSearchHelper {
	
	/**
	 * Given a response from SearchByMulticriteria web service, compute it and returns a WrapperIndividualSearch
	 * @param searchIndividualByMulticriteriaResponse (SearchIndividualByMulticriteriaResponse)
	 * @return a WrapperIndividualSearch object
	 */
	public static WrapperIndividualSearch computeSearchWSResponse(SearchIndividualByMulticriteriaResponse searchIndividualByMulticriteriaResponse) {
		
		WrapperIndividualSearch wrapper = new WrapperIndividualSearch();
		
		List<ModelBasicIndividualData> data = new ArrayList<>();
		
		List<Individual> individuals = searchIndividualByMulticriteriaResponse.getIndividual();
		for (Individual i : individuals) {
			data.add(extractBasicIndividualData(i));
		}
		wrapper.count = individuals.size();
		wrapper.data = data;
		
		return wrapper;
		
	}
	
	/**
	 * Given an Individual, returns the model with all the basic individual data
	 * that are needed in the front-end to display the results
	 * @param i
	 * @return
	 */
	private static ModelBasicIndividualData extractBasicIndividualData(Individual i) {
		IndividualInformations info = i.getIndividualInformations();
		PostalAddressResponse postalAddressResponse = i.getPostalAddressResponse();
		List<Address> addresses = postalAddressResponse.getAddress();
		ModelBasicIndividualData mbid = new ModelBasicIndividualData();
		mbid.setGin(info.getIdentifier());
		mbid.setStatus(info.getStatus());
		mbid.setLastname(info.getLastNameSC());
		mbid.setFirstname(info.getFirstNameSC());
		mbid.setCivility(info.getCivility());
		mbid.setBirthdate(info.getBirthDate());
		mbid.setPostalAddress((addresses.size() > 0) ? addresses.get(0).getPostalAddressContent().getNumberAndStreet() : "");
		return mbid;
	}

}
