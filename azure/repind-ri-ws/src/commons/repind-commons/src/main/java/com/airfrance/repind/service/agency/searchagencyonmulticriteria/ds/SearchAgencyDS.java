package com.airfrance.repind.service.agency.searchagencyonmulticriteria.ds;

import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.service.adresse.internal.TelecomDS;
import com.airfrance.repind.service.agency.searchagencyonmulticriteria.dao.SearchAgencyDAO;
import com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto.*;
import com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto.builders.RequestDTOBuilder;
import com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto.builders.ResponseDTOBuilder;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Service("AgencySearchAgencyDS")
public class SearchAgencyDS extends AbstractDS {

	/*===============================================*/
	/*             INJECTED BEANS                    */
	/*===============================================*/
	
	@Autowired
	@Qualifier("AgencyRequestDTOBuilder")
	private RequestDTOBuilder requestDTOBuilder = null;
	
	@Autowired
	private SearchAgencyDAO searchAgencyDAOBean = null;
	
	@Autowired
	@Qualifier("agencyCalculateRateDS")
	private CalculateRateDS calculateRateDSBean = null;
	
	@Autowired
	@Qualifier("AgencyCheckMandatoryInputsDS")
	private CheckMandatoryInputsDS checkMandatoryInputsDSBean = null;
	
	@Autowired
	@Qualifier("AgenceBuildQueryDS")
	private BuildQueryDS buildQueryDSBean = null;
	
	@Autowired
	@Qualifier("AgenceBuildQueryOldDS")
	private BuildQueryOldDS buildQueryOldDSBean = null;

	@Autowired
	@Qualifier("agencyObtainTokenDS")
	private ObtainTokenDS obtainTokenDSBean = null;
	
	@Autowired
	private TelecomDS telecomDS;

	/*===============================================*/
	/*                   LOGGER                      */
	/*===============================================*/
	private static Log LOGGER  = LogFactory.getLog(SearchAgencyDS.class);
			
	/*===============================================*/
	/*               PUBLIC METHODS                  */
	/*===============================================*/
	
	/**
	 * Create DTO beans and call the business method searchFirm (below)
	 */
	public ResponseDTO searchAgency(RequestDTO requestDTO) throws BusinessException {
		LOGGER.info("SearchAgencyOnMultiCriteria | NORMALIZING PHONE NUMBER");
		normalizePhoneNumber(requestDTO);
		LOGGER.info("SearchAgencyOnMultiCriteria | BUILDING QUERY");
		
		buildQueryDSBean.buildQuery(requestDTO);
		
		LOGGER.info("SearchAgencyOnMultiCriteria | SEARCH START");
		ResponseDTO responseDTO = executeSearch(requestDTO);
		LOGGER.info("SearchAgencyOnMultiCriteria | SEARCH END AND TOKEN HANDLING");
		handleToken(requestDTO, responseDTO);
		return responseDTO;
	}
	
	/**
     * Create DTO beans and call the business method searchFirm (below)
     */
    public ResponseDTO searchAgencyV2(RequestDTO requestDTO) throws BusinessException, SystemException {
        LOGGER.info("SearchAgencyOnMultiCriteria (v2) | NORMALIZING PHONE NUMBER");
        normalizePhoneNumber(requestDTO);
        LOGGER.info("SearchAgencyOnMultiCriteria (v2) | BUILDING QUERY");

        // Version 2.0
        // Setting nameType to B (default) if not set in request...
        if (requestDTO.getIdentity() != null
                && !BuildConditionsDS.isNameTypeConditionSet(requestDTO))
            requestDTO.getIdentity().setNameType("B");

		if (BuildConditionsDS.isProcessTypeOldConditionSet(requestDTO)) {
			buildQueryOldDSBean.buildOldQuery(requestDTO);
		} else {
			buildQueryDSBean.buildQueryV2(requestDTO);
		}

        LOGGER.info("SearchAgencyOnMultiCriteria (v2) | SEARCH START");
        ResponseDTO responseDTO = executeSearchV2(requestDTO);
        LOGGER.info("SearchAgencyOnMultiCriteria (v2) | SEARCH END AND TOKEN HANDLING");
        handleToken(requestDTO, responseDTO);
        return responseDTO;
    }
    
	/*===============================================*/
	/*               PRIVATE METHODS                 */
	/*===============================================*/
	
	/**
	 * Add a token to the response 
	 *  if the conditions match:
	 *   - There is no more than X results (X=rates.properties/token.limit)
	 *   - The search has been done on the name basis
	 */
	private void handleToken(RequestDTO requestDTO, ResponseDTO responseDTO) {
		obtainTokenDSBean.readToken(requestDTO, responseDTO);
	}
	
	/**
	 * Get a normalized phone number from country/PhoneNb inputs
	 * @param requestDTO
	 */
	private void normalizePhoneNumber(RequestDTO requestDTO) 
	{
		if((BuildConditionsDS.isPhoneConditionSet(requestDTO))
				&&	(BuildConditionsDS.isPhoneCountryConditionSet(requestDTO)))
		{
			com.airfrance.repind.dto.adresse.TelecomsDTO phoneNumberDTO = new com.airfrance.repind.dto.adresse.TelecomsDTO();
			phoneNumberDTO.setCountryCode(requestDTO.getContacts().getPhoneBloc().getCountry().toUpperCase());
			phoneNumberDTO.setSnumero(requestDTO.getContacts().getPhoneBloc().getPhoneNumber());
			
			com.airfrance.repind.dto.adresse.TelecomsDTO normalizedPN;
			try 
			{
				normalizedPN = telecomDS.normalizePhoneNumber(phoneNumberDTO);
				requestDTO.getContacts().getPhoneBloc().setCountry(normalizedPN.getSnorm_inter_country_code());
				requestDTO.getContacts().getPhoneBloc().setPhoneNumber(normalizedPN.getSnorm_nat_phone_number_clean());
			} 
			catch (JrafDomainException e) {
				// Our goal is not to handle a JrafDomainException instance
				// If this exception is caught, this means that we couldn't get a normalized phoneNbr
				// And this shouldn't stop the search task
				LOGGER.info("SearchFirmOnMultiCriteria | telecom notmalization exception: " + e.getMessage());
			}
			catch (Exception e) {
				LOGGER.info("SearchFirmOnMultiCriteria | telecom notmalization exception: " + e.getMessage());
			}
		}
	}
	
	/**
	 * Checks mandatory inputs, process firm searching, and calculate correspondence rate
	 */
	private ResponseDTO executeSearch(RequestDTO requestDTO) throws BusinessException 
	{
		ResponseDTO responseDTO = null;
		
		/* 
		 * Initialize the request
		 */
		checkMandatoryInputsDSBean.checkMandatoryInputs(requestDTO);
		
		/*
		 * Execute the search according to process type
		 */
		if(BuildConditionsDS.isProcessTypeAutoConditionSet(requestDTO))
		{
			responseDTO = executeAutomaticSearch(requestDTO);
		}
		
		if(BuildConditionsDS.isProcessTypeManualConditionSet(requestDTO))
		{
			responseDTO = executeManualSearch(requestDTO);
		}
		
		
		return responseDTO;
	}


    private ResponseDTO executeSearchV2(RequestDTO requestDTO) throws BusinessException, SystemException {
        ResponseDTO responseDTO = null;

        /*
         * Initialize the request
         */
        checkMandatoryInputsDSBean.checkMandatoryInputs(requestDTO);

        /*
         * Execute the search according to process type
         */
        if (BuildConditionsDS.isProcessTypeAutoConditionSet(requestDTO)) {
            responseDTO = executeAutomaticSearchV2(requestDTO);
        }

        if (BuildConditionsDS.isProcessTypeManualConditionSet(requestDTO)) {
            responseDTO = executeManualSearchV2(requestDTO);
        }

		if (BuildConditionsDS.isProcessTypeOldConditionSet(requestDTO)) {
			responseDTO = executeOldSearchV2(requestDTO);
		}


        return responseDTO;
    }
	
	/**
	 * Executes an automatic search: 
	 *         All the search scenarios(23) will be executed until
	 *         the first highest relevance is reached
	 *       
	 *    
	 * @param requestDTO
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	private ResponseDTO executeAutomaticSearch(RequestDTO requestDTO) throws BusinessException
	{
		/*
		 * Returned response instantiation
		 */
		ResponseDTO responseDTO = ResponseDTOBuilder.build();
		
		if(requestDTO != null)
		{
			/*
			 * Application parameters: 
			 *   - index
			 *   - page size
			 *   - max allowed results for a manual search
			 */
			int index = requestDTO.getQueryIndex();
			int pageSize = requestDTO.getQueryPageSize();		
			
			/*
			 * The list of results
			 */
			List<SearchResultDTO> resultsList = new ArrayList<SearchResultDTO>();
			
			/*
			 * The list per page
			 */
			List<SearchResultDTO> returnedList = new ArrayList<SearchResultDTO>();
			
			/*
			 * Found gins and correspondent rates
			 *  Search operation
			 */
			Set<SearchResultDTO> resultsSet = executeAutomaticSearchForAgencies(requestDTO);
			

			if(!resultsSet.isEmpty())
			{
				/*
				 * Calculating first and last element in the page
				 */
				int firstElementInPage = (index - 1) * pageSize;
				int lastElementInPage = (index * pageSize);
				
				if((firstElementInPage + 1) > resultsSet.size())
				{
					throwsException(getIncoherentIndexCode(), getIncoherentIndexMessage());
				}
				else
				{
					resultsList.addAll(resultsSet);
					/*
					 * Loading gins' firms
					 */
					int minOrder = firstElementInPage;
					int maxOrder = Math.min(lastElementInPage, resultsList.size());
					
					returnedList = resultsList.subList(minOrder, maxOrder);
					for(SearchResultDTO result : returnedList)
					{
						AgencyDTO agency = searchAgencyDAOBean.readAgencyByGin(requestDTO, result.getGin());
			    		if(agency != null)
			    		{
			    			agency.setRelevance(result.getRate());
			    			responseDTO.getAgencies().add(agency);
			    		}
					}
					
					/*
					 * Updating continuity fields
					 *   - first index
					 *   - last index
					 *   - maxResults
					 */
					responseDTO.setFirstIndex(firstElementInPage + 1);
					responseDTO.setMaxResult(maxOrder);
					responseDTO.setTotalNumber(resultsList.size());
					
					/*
					 * Return code and message
					 */
					responseDTO.setReturnCode(getSuccessReturnCode());
					responseDTO.setReturnMessage(getSuccessReturnMessage());
				}
			}
			else
			{
				/*
				 * If no results, we throw a business exception
				 */
				throwsException(getNoResultsReturnCode(), getNoResultsReturnMessage());
			}
		}
			
		return responseDTO;
	}
	
	
	/**
	 * Executes an automatic search for firms
	 * @param requestDTO
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	private Set<SearchResultDTO> executeAutomaticSearchForAgencies(RequestDTO requestDTO) throws BusinessException
	{
		/*
		 * Result set for unique ordered values
		 */
		Set<SearchResultDTO> resultsSet = new TreeSet<SearchResultDTO>();
		
		if(requestDTO != null)
		{
			/*
			 * Application parameters: 
			 *   - max allowed results for a manual search
			 */
			int maxResults = requestDTO.getQueryMaxResults();
			
			/*
			 * results container
			 */
			List<SearchResultDTO> results = null;		
			
			/*
			 * Needed for automatic search
			 * As soon as a best relevance rate
			 *  is reached, the search operation 
			 *  ends
			 */
			boolean isBestRateReached = false;
			
			/*
			 * Max possible relevance rate
			 */
			int maxRate = calculateRateDSBean.getMaxRate();
						
			rateCounterLoop:
			for(int rateCounter = maxRate; rateCounter >= 0; rateCounter -= 1)
			{
				if(isBestRateReached)
				{
					break rateCounterLoop;
				}
				
				/*
				 * Search scenario: Name(Strict) and Phone
				 */
				if(rateCounter == calculateRateDSBean.getRateAgencyNameStrictAndPhone())
				{
					RequestDTO subRequestDTO_NameAndPhone = requestDTOBuilder.build_NameAndPhone(requestDTO);
					if(subRequestDTO_NameAndPhone != null)
					{
						results = searchGins(subRequestDTO_NameAndPhone);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRate(result, subRequestDTO_NameAndPhone);
								resultsSet.add(result);
								if(resultsSet.size() > maxResults)
								{
									break rateCounterLoop;
								}
							}
							
							if(results.size() > 0)
							{
								isBestRateReached = true;
							}
						}
					}
				}
				
				/*
				 * Search scenario: Name(Like) and Phone
				 */
				if(rateCounter == calculateRateDSBean.getRateAgencyNameLikeAndPhone())
				{
					RequestDTO subRequestDTO_NameAndPhone = requestDTOBuilder.build_NameAndPhone(requestDTO);
					if(subRequestDTO_NameAndPhone != null)
					{
						results = searchGins(subRequestDTO_NameAndPhone);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRate(result, subRequestDTO_NameAndPhone);
								resultsSet.add(result);
								if(resultsSet.size() > maxResults)
								{
									break rateCounterLoop;
								}
							}
							
							if(results.size() > 0)
							{
								isBestRateReached = true;
							}
						}
					}
				}
				
				/*
				 * Search scenario: Name(Strict) and Email
				 */
				if(rateCounter == calculateRateDSBean.getRateAgencyNameStrictEmail())
				{
					RequestDTO subRequestDTO_NameAndEmail = requestDTOBuilder.build_NameAndEmail(requestDTO);
					if(subRequestDTO_NameAndEmail != null)
					{
						results = searchGins(subRequestDTO_NameAndEmail);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRate(result, subRequestDTO_NameAndEmail);
								resultsSet.add(result);
								if(resultsSet.size() > maxResults)
								{
									break rateCounterLoop;
								}
							}
							
							if(results.size() > 0)
							{
								isBestRateReached = true;
							}
						}
					}
				}
				
				/*
				 * Search scenario: Name(Like) and Email
				 */
				if(rateCounter == calculateRateDSBean.getRateAgencyNameLikeEmail())
				{
					RequestDTO subRequestDTO_NameAndEmail = requestDTOBuilder.build_NameAndEmail(requestDTO);
					if(subRequestDTO_NameAndEmail != null)
					{
						results = searchGins(subRequestDTO_NameAndEmail);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRate(result, subRequestDTO_NameAndEmail);
								resultsSet.add(result);
								if(resultsSet.size() > maxResults)
								{
									break rateCounterLoop;
								}
							}
							
							if(results.size() > 0)
							{
								isBestRateReached = true;
							}
						}
					}
				}
				
				/*
				 * Search scenario: Name(Strict), Countrycode, Nbandstreet, City, Zipcode
				 */
				if(rateCounter == calculateRateDSBean.getRateAgencyNameStrictCountrycodeNbandstreetCityZipcode())
				{
					RequestDTO subRequestDTO_NameCountrycodeNbandstreetCityZipcode = requestDTOBuilder.build_NameCountrycodeNbandstreetCityZipcode(requestDTO);
					if(subRequestDTO_NameCountrycodeNbandstreetCityZipcode != null)
					{
						results = searchGins(subRequestDTO_NameCountrycodeNbandstreetCityZipcode);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRate(result, subRequestDTO_NameCountrycodeNbandstreetCityZipcode);
								resultsSet.add(result);
								if(resultsSet.size() > maxResults)
								{
									break rateCounterLoop;
								}
							}
							
							if(results.size() > 0)
							{
								isBestRateReached = true;
							}
						}
					}
				}
				
				/*
				 * Search scenario: Name(Like), Countrycode, Nbandstreet, City, Zipcode
				 */
				if(rateCounter == calculateRateDSBean.getRateAgencyNameLikeCountrycodeNbandstreetCityZipcode())
				{
					RequestDTO subRequestDTO_NameCountrycodeNbandstreetCityZipcode = requestDTOBuilder.build_NameCountrycodeNbandstreetCityZipcode(requestDTO);
					if(subRequestDTO_NameCountrycodeNbandstreetCityZipcode != null)
					{
						results = searchGins(subRequestDTO_NameCountrycodeNbandstreetCityZipcode);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRate(result, subRequestDTO_NameCountrycodeNbandstreetCityZipcode);
								resultsSet.add(result);
								if(resultsSet.size() > maxResults)
								{
									break rateCounterLoop;
								}
							}
							
							if(results.size() > 0)
							{
								isBestRateReached = true;
							}
						}
					}
				}
				
				/*
				 * Search scenario: Name(Strict), Countrycode, Nbandstreet, City
				 */
				if(rateCounter == calculateRateDSBean.getRateAgencyNameStrictCountrycodeNbandstreetCity())
				{
					RequestDTO subRequestDTO_NameCountrycodeNbandstreetCity = requestDTOBuilder.build_NameCountrycodeNbandstreetCity(requestDTO);
					if(subRequestDTO_NameCountrycodeNbandstreetCity != null)
					{
						results = searchGins(subRequestDTO_NameCountrycodeNbandstreetCity);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRate(result, subRequestDTO_NameCountrycodeNbandstreetCity);
								resultsSet.add(result);
								if(resultsSet.size() > maxResults)
								{
									break rateCounterLoop;
								}
							}
							
							if(results.size() > 0)
							{
								isBestRateReached = true;
							}
						}
					}
				}
				
				/*
				 * Search scenario: Name(Like), Countrycode, Nbandstreet, City
				 */
				if(rateCounter == calculateRateDSBean.getRateAgencyNameLikeCountrycodeNbandstreetCity())
				{
					RequestDTO subRequestDTO_NameCountrycodeNbandstreetCity = requestDTOBuilder.build_NameCountrycodeNbandstreetCity(requestDTO);
					if(subRequestDTO_NameCountrycodeNbandstreetCity != null)
					{
						results = searchGins(subRequestDTO_NameCountrycodeNbandstreetCity);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRate(result, subRequestDTO_NameCountrycodeNbandstreetCity);
								resultsSet.add(result);
								if(resultsSet.size() > maxResults)
								{
									break rateCounterLoop;
								}
							}
							
							if(results.size() > 0)
							{
								isBestRateReached = true;
							}
						}
					}
				}
				
				/*
				 * Search scenario: Name(Strict), Countrycode, City, ZipCode
				 */
				if(rateCounter == calculateRateDSBean.getRateAgencyNameStrictCountrycodeCityZipcode())
				{
					RequestDTO subRequestDTO_NameCountryCityAndZip = requestDTOBuilder.build_NameCountrycodeCityStrictZipStrict(requestDTO);
					if(subRequestDTO_NameCountryCityAndZip != null)
					{
						results = searchGins(subRequestDTO_NameCountryCityAndZip);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRate(result, subRequestDTO_NameCountryCityAndZip);
								resultsSet.add(result);
								if(resultsSet.size() > maxResults)
								{
									break rateCounterLoop;
								}
							}
							
							if(results.size() > 0)
							{
								isBestRateReached = true;
							}
						}
					}
				}
				
			
				
				/*
				 * Search scenario: Name(Strict), ZC1 to 4
				 */
				if(rateCounter == calculateRateDSBean.getRateAgencyNameStrictZC14())
				{
					RequestDTO subRequestDTO_NameCountrycodeZC14 = requestDTOBuilder.build_NameZC15(requestDTO);
					if(subRequestDTO_NameCountrycodeZC14 != null)
					{
						results = searchGins(subRequestDTO_NameCountrycodeZC14);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRate(result, subRequestDTO_NameCountrycodeZC14);
								resultsSet.add(result);
								if(resultsSet.size() > maxResults)
								{
									break rateCounterLoop;
								}
							}
							
							if(results.size() > 0)
							{
								isBestRateReached = true;
							}
						}
					}
				}
				
				/*
				 * Search scenario: Name(Like), ZC1 to 4
				 */
				if(rateCounter == calculateRateDSBean.getRateAgencyNameLikeZC14())
				{
					RequestDTO subRequestDTO_NameCountrycodeZC14 = requestDTOBuilder.build_NameZC15(requestDTO);
					if(subRequestDTO_NameCountrycodeZC14 != null)
					{
						results = searchGins(subRequestDTO_NameCountrycodeZC14);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRate(result, subRequestDTO_NameCountrycodeZC14);
								resultsSet.add(result);
								if(resultsSet.size() > maxResults)
								{
									break rateCounterLoop;
								}
							}
							
							if(results.size() > 0)
							{
								isBestRateReached = true;
							}
						}
					}
				}
				
				/*
				 * Search scenario: Name(Strict), CountryCode, Ident
				 */
				if(rateCounter == calculateRateDSBean.getRateAgencyNameStrictCountrycodeIdent())
				{
					RequestDTO subRequestDTO_NameCountrycodeIdent = requestDTOBuilder.build_NameCountrycodeAndIdent(requestDTO);
					if(subRequestDTO_NameCountrycodeIdent != null)
					{
						results = searchGins(subRequestDTO_NameCountrycodeIdent);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRate(result, subRequestDTO_NameCountrycodeIdent);
								resultsSet.add(result);
								if(resultsSet.size() > maxResults)
								{
									break rateCounterLoop;
								}
							}
							
							if(results.size() > 0)
							{
								isBestRateReached = true;
							}
						}
					}
				}
				
				/*
				 * Search scenario: Name(Like), CountryCode, Ident
				 */
				if(rateCounter == calculateRateDSBean.getRateAgencyNameLikeCountrycodeIdent())
				{
					RequestDTO subRequestDTO_NameCountrycodeIdent = requestDTOBuilder.build_NameCountrycodeAndIdent(requestDTO);
					if(subRequestDTO_NameCountrycodeIdent != null)
					{
						results = searchGins(subRequestDTO_NameCountrycodeIdent);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRate(result, subRequestDTO_NameCountrycodeIdent);
								resultsSet.add(result);
								if(resultsSet.size() > maxResults)
								{
									break rateCounterLoop;
								}
							}
							
							if(results.size() > 0)
							{
								isBestRateReached = true;
							}
						}
					}
				}
				
				/*
				 * Search scenario: Name(Strict), CountryCode
				 */
				if(rateCounter == calculateRateDSBean.getRateAgencyNameStrictCountrycode())
				{
					RequestDTO subRequestDTO_NameCountrycode = requestDTOBuilder.build_NameAndCountrycode(requestDTO);
					if(subRequestDTO_NameCountrycode != null)
					{
						results = searchGins(subRequestDTO_NameCountrycode);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRate(result, subRequestDTO_NameCountrycode);
								resultsSet.add(result);
								if(resultsSet.size() > maxResults)
								{
									break rateCounterLoop;
								}
							}
							
							if(results.size() > 0)
							{
								isBestRateReached = true;
							}
						}
					}
				}
				
				/*
				 * Search scenario: Name(Like), CountryCode
				 */
				if(rateCounter == calculateRateDSBean.getRateAgencyNameLikeCountrycode())
				{
					RequestDTO subRequestDTO_NameCountrycode = requestDTOBuilder.build_NameAndCountrycode(requestDTO);
					if(subRequestDTO_NameCountrycode != null)
					{
						results = searchGins(subRequestDTO_NameCountrycode);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRate(result, subRequestDTO_NameCountrycode);
								resultsSet.add(result);
								if(resultsSet.size() > maxResults)
								{
									break rateCounterLoop;
								}
							}
							
							if(results.size() > 0)
							{
								isBestRateReached = true;
							}
						}
					}
				}
				
				/*
				 * Search scenario: Name(Strict)
				 */
				if(rateCounter == calculateRateDSBean.getRateAgencyNameStrict())
				{
					RequestDTO subRequestDTO_Name = requestDTOBuilder.build_Name(requestDTO);
					if(subRequestDTO_Name != null)
					{
						results = searchGins(subRequestDTO_Name);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRate(result, subRequestDTO_Name);
								resultsSet.add(result);
								if(resultsSet.size() > maxResults)
								{
									break rateCounterLoop;
								}
							}
							
							if(results.size() > 0)
							{
								isBestRateReached = true;
							}
						}
					}
				}
				
				/*
				 * Search scenario: Name(Like)
				 */
				if(rateCounter == calculateRateDSBean.getRateAgencyNameLike())
				{
					RequestDTO subRequestDTO_Name = requestDTOBuilder.build_Name(requestDTO);
					if(subRequestDTO_Name != null)
					{
						results = searchGins(subRequestDTO_Name);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRate(result, subRequestDTO_Name);
								resultsSet.add(result);
								if(resultsSet.size() > maxResults)
								{
									break rateCounterLoop;
								}
							}
							
							if(results.size() > 0)
							{
								isBestRateReached = true;
							}
						}
					}
				}
				
				/*
				 * Search scenario: Phone
				 */
				if(rateCounter == calculateRateDSBean.getRateAgencyPhone())
				{
					RequestDTO subRequestDTO_Phone = requestDTOBuilder.build_Phone(requestDTO);
					if(subRequestDTO_Phone != null)
					{
						results = searchGins(subRequestDTO_Phone);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRate(result, subRequestDTO_Phone);
								resultsSet.add(result);
								if(resultsSet.size() > maxResults)
								{
									break rateCounterLoop;
								}
							}
							
							if(results.size() > 0)
							{
								isBestRateReached = true;
							}
						}
					}
				}
				
				/*
				 * Search scenario: Email
				 */
				if(rateCounter == calculateRateDSBean.getRateAgencyEmail())
				{
					RequestDTO subRequestDTO_Email = requestDTOBuilder.build_Email(requestDTO);
					if(subRequestDTO_Email != null)
					{
						results = searchGins(subRequestDTO_Email);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRate(result, subRequestDTO_Email);
								resultsSet.add(result);
								if(resultsSet.size() > maxResults)
								{
									break rateCounterLoop;
								}
							}
							
							if(results.size() > 0)
							{
								isBestRateReached = true;
							}
						}
					}
				}
				
				/*
				 * Search scenario: Phone, Ident
				 */
				if(rateCounter == calculateRateDSBean.getRateAgencyPhoneIdent())
				{
					RequestDTO subRequestDTO_PhoneAndIdent = requestDTOBuilder.build_PhoneAndIdent(requestDTO);
					if(subRequestDTO_PhoneAndIdent != null)
					{
						results = searchGins(subRequestDTO_PhoneAndIdent);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRate(result, subRequestDTO_PhoneAndIdent);
								resultsSet.add(result);
								if(resultsSet.size() > maxResults)
								{
									break rateCounterLoop;
								}
							}
							
							if(results.size() > 0)
							{
								isBestRateReached = true;
							}
						}
					}
				}
				
				/*
				 * Search scenario: Email, Ident
				 */
				if(rateCounter == calculateRateDSBean.getRateAgencyEmailIdent())
				{
					RequestDTO subRequestDTO_EmailAndIdent = requestDTOBuilder.build_EmailAndIdent(requestDTO);
					if(subRequestDTO_EmailAndIdent != null)
					{
						results = searchGins(subRequestDTO_EmailAndIdent);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRate(result, subRequestDTO_EmailAndIdent);
								resultsSet.add(result);
								if(resultsSet.size() > maxResults)
								{
									break rateCounterLoop;
								}
							}
							
							if(results.size() > 0)
							{
								isBestRateReached = true;
							}
						}
					}
				}
				
				/*
				 * Search scenario: Ident
				 */
				if(rateCounter == calculateRateDSBean.getRateAgencyIdent())
				{
					RequestDTO subRequestDTO_Ident = requestDTOBuilder.build_Ident(requestDTO);
					if(subRequestDTO_Ident != null)
					{
						results = searchGins(subRequestDTO_Ident);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRate(result, subRequestDTO_Ident);
								resultsSet.add(result);
								if(resultsSet.size() > maxResults)
								{
									break rateCounterLoop;
								}
							}
							
							if(results.size() > 0)
							{
								isBestRateReached = true;
							}
						}
					}
				}
			}	
		}
			
		return resultsSet;
	}

	// V2 of executeAutomaticSearch
	@Transactional(readOnly = true)
	public ResponseDTO executeOldSearchV2(RequestDTO requestDTO) throws BusinessException, SystemException {
		ResponseDTO responseDTO = ResponseDTOBuilder.build();

		String typeNom = "R";
		List<String> results = new ArrayList<String>();
		if (requestDTO.getCountHqlQuery().getParameterValue("type") != null) {
			typeNom = (String) requestDTO.getCountHqlQuery().getParameterValue("type");
			if (buildQueryOldDSBean.getNextTypeNomSearch(typeNom) == null) {
				typeNom = "R"; //If typeNom is not a valid value, set R by default
			}
		}

		//Old adh process do a lot of search by changing each time the typeName
		while (results.isEmpty() && StringUtils.isNotBlank(typeNom)) {
			if ("R".equals(typeNom)) {
				results = requestDTO.getSearchHqlQuery().getResultList();
			} else {
				results = requestDTO.getCountHqlQuery().getResultList();
			}
			typeNom = buildQueryOldDSBean.getNextTypeNomSearch(typeNom);
			requestDTO.getCountHqlQuery().setParameter("type", typeNom);
		}

		int index = requestDTO.getQueryIndex();
		int pageSize = requestDTO.getQueryPageSize();

		if (results.isEmpty()) {
			/*
			 * If no results, we throw a business exception
			 */
			throwsException(getNoResultsReturnCode(), getNoResultsReturnMessage());
		} else {
			int firstElementInPage = (index - 1) * pageSize;
			int lastElementInPage = (index * pageSize);
			int minOrder = firstElementInPage;
			int maxOrder = Math.min(lastElementInPage, results.size());
			int totalNumber = results.size();

			results = results.subList(minOrder, maxOrder);
			for (String result : results) {
				AgencyDTO agency = searchAgencyDAOBean.readAgencyByGin(requestDTO, result);
				if (agency != null && agency.getAgencyInformationsDTO() != null) {
					agency.setRelevance(100);
					responseDTO.getAgencies().add(agency);
				} else {
					LOGGER.info(agency);
				}
			}
			/*
			 * Updating continuity fields - first index - last index -
			 * maxResults
			 */
			responseDTO.setFirstIndex(firstElementInPage + 1);
			responseDTO.setMaxResult(maxOrder);
			responseDTO.setTotalNumber(totalNumber);

			/*
			 * Return code and message
			 */
			responseDTO.setReturnCode(getSuccessReturnCode());
			responseDTO.setReturnMessage(getSuccessReturnMessage());
		}

		return responseDTO;
	}
	
	// V2 of executeAutomaticSearch
    private ResponseDTO executeAutomaticSearchV2(RequestDTO requestDTO) throws BusinessException, SystemException {
        /*
         * Returned response instantiation
         */
        ResponseDTO responseDTO = ResponseDTOBuilder.build();

        if (requestDTO != null) {
            /*
             * Application parameters:
             *   - index
             *   - page size
             *   - max allowed results for a manual search
             */
            int index = requestDTO.getQueryIndex();
            int pageSize = requestDTO.getQueryPageSize();

            /*
             * The list of results
             */
            List<SearchResultDTO> resultsList = new ArrayList<SearchResultDTO>();

            /*
             * The list per page
             */
            List<SearchResultDTO> returnedList = new ArrayList<SearchResultDTO>();

            /*
             * Found gins and correspondent rates
             *  Search operation
             */
            Set<SearchResultDTO> resultsSet = executeAutomaticSearchForAgenciesV2(requestDTO);


            if (!resultsSet.isEmpty()) {
                /*
                 * Calculating first and last element in the page
                 */
                int firstElementInPage = (index - 1) * pageSize;
                int lastElementInPage = (index * pageSize);

                if ((firstElementInPage + 1) > resultsSet.size()) {
                    throwsException(getIncoherentIndexCode(), getIncoherentIndexMessage());
                } else {
                    resultsList.addAll(resultsSet);
                    /*
                     * Loading gins' firms
                     */
                    int minOrder = firstElementInPage;
                    int maxOrder = Math.min(lastElementInPage, resultsList.size());

                    returnedList = resultsList.subList(minOrder, maxOrder);
                    for (SearchResultDTO result : returnedList) {
                        AgencyDTO agency = searchAgencyDAOBean.readAgencyByGin(requestDTO, result.getGin());
                        if (agency != null && agency.getAgencyInformationsDTO() != null) {
                            agency.setRelevance(result.getRate());
                            responseDTO.getAgencies().add(agency);
                        }
                        else
                        {
                        	LOGGER.info(agency);
                        }
                    }

                    /*
                     * Updating continuity fields
                     *   - first index
                     *   - last index
                     *   - maxResults
                     */
                    responseDTO.setFirstIndex(firstElementInPage + 1);
                    responseDTO.setMaxResult(maxOrder);
                    responseDTO.setTotalNumber(resultsList.size());

                    /*
                     * Return code and message
                     */
                    responseDTO.setReturnCode(getSuccessReturnCode());
                    responseDTO.setReturnMessage(getSuccessReturnMessage());
                }
            } else {
                /*
                 * If no results, we throw a business exception
                 */
                throwsException(getNoResultsReturnCode(), getNoResultsReturnMessage());
            }
        }

        return responseDTO;
    }

	// V2 of executeAutomaticSearchForAgencies
    private Set<SearchResultDTO> executeAutomaticSearchForAgenciesV2(RequestDTO requestDTO) throws BusinessException, SystemException {
        /*
         * Result set for unique ordered values
         */
        Set<SearchResultDTO> resultsSet = new TreeSet<SearchResultDTO>();

        if (requestDTO != null) {
            /*
             * Application parameters:
             *   - max allowed results for a manual search
             */
            int maxResults = requestDTO.getQueryMaxResults();

            /*
             * results container
             */
            List<SearchResultDTO> results = null;

            /*
             * Needed for automatic search
             * As soon as a best relevance rate
             *  is reached, the search operation
             *  ends
             */
            boolean isBestRateReached = false;

            /*
             * Max possible relevance rate
             */
            int maxRate = calculateRateDSBean.getMaxRateForV2();

            rateCounterLoop:
            for (int rateCounter = maxRate; rateCounter >= 0; rateCounter -= 1) {
                if (isBestRateReached) {
                    break rateCounterLoop;
                }

                /*
                 * Search scenario: Name(Strict) and Phone
                 */
                if (rateCounter == calculateRateDSBean.getRateAgencyNameStrictAndPhone()) {
                    RequestDTO subRequestDTO_NameAndPhone = requestDTOBuilder.build_NameAndPhone(requestDTO);
                    if (subRequestDTO_NameAndPhone != null) {
                        results = searchGinsV2(subRequestDTO_NameAndPhone);
                        if (results != null) {
                            for (SearchResultDTO result : results) {
                                calculateRateDSBean.calculateRateV2(result, subRequestDTO_NameAndPhone);
                                resultsSet.add(result);
                                if (resultsSet.size() > maxResults) {
                                    break rateCounterLoop;
                                }
                            }

                            if (results.size() > 0) {
                                isBestRateReached = true;
                            }
                        }
                    }
                }

                /*
                 * Search scenario: Name(Like) and Phone
                 */
                if (rateCounter == calculateRateDSBean.getRateAgencyNameLikeAndPhone()) {
                    RequestDTO subRequestDTO_NameAndPhone = requestDTOBuilder.build_NameAndPhone(requestDTO);
                    if (subRequestDTO_NameAndPhone != null) {
                        results = searchGinsV2(subRequestDTO_NameAndPhone);
                        if (results != null) {
                            for (SearchResultDTO result : results) {
                                calculateRateDSBean.calculateRateV2(result, subRequestDTO_NameAndPhone);
                                resultsSet.add(result);
                                if (resultsSet.size() > maxResults) {
                                    break rateCounterLoop;
                                }
                            }

                            if (results.size() > 0) {
                                isBestRateReached = true;
                            }
                        }
                    }
                }

                /*
                 * Search scenario: Name(Strict) and Email
                 */
                if (rateCounter == calculateRateDSBean.getRateAgencyNameStrictEmail()) {
                    RequestDTO subRequestDTO_NameAndEmail = requestDTOBuilder.build_NameAndEmail(requestDTO);
                    if (subRequestDTO_NameAndEmail != null) {
                        results = searchGinsV2(subRequestDTO_NameAndEmail);
                        if (results != null) {
                            for (SearchResultDTO result : results) {
                                calculateRateDSBean.calculateRateV2(result, subRequestDTO_NameAndEmail);
                                resultsSet.add(result);
                                if (resultsSet.size() > maxResults) {
                                    break rateCounterLoop;
                                }
                            }

                            if (results.size() > 0) {
                                isBestRateReached = true;
                            }
                        }
                    }
                }

                /*
                 * Search scenario: Name(Like) and Email
                 */
                if (rateCounter == calculateRateDSBean.getRateAgencyNameLikeEmail()) {
                    RequestDTO subRequestDTO_NameAndEmail = requestDTOBuilder.build_NameAndEmail(requestDTO);
                    if (subRequestDTO_NameAndEmail != null) {
                        results = searchGinsV2(subRequestDTO_NameAndEmail);
                        if (results != null) {
                            for (SearchResultDTO result : results) {
                                calculateRateDSBean.calculateRateV2(result, subRequestDTO_NameAndEmail);
                                resultsSet.add(result);
                                if (resultsSet.size() > maxResults) {
                                    break rateCounterLoop;
                                }
                            }

                            if (results.size() > 0) {
                                isBestRateReached = true;
                            }
                        }
                    }
                }

                /*
                 * Search scenario: Name(Strict), Countrycode, Nbandstreet, City, Zipcode
                 */
                if (rateCounter == calculateRateDSBean.getRateAgencyNameStrictCountrycodeNbandstreetCityZipcode()) {
                    RequestDTO subRequestDTO_NameCountrycodeNbandstreetCityZipcode = requestDTOBuilder.build_NameCountrycodeNbandstreetCityZipcode(requestDTO);
                    if (subRequestDTO_NameCountrycodeNbandstreetCityZipcode != null) {
                        results = searchGinsV2(subRequestDTO_NameCountrycodeNbandstreetCityZipcode);
                        if (results != null) {
                            for (SearchResultDTO result : results) {
                                calculateRateDSBean.calculateRateV2(result, subRequestDTO_NameCountrycodeNbandstreetCityZipcode);
                                resultsSet.add(result);
                                if (resultsSet.size() > maxResults) {
                                    break rateCounterLoop;
                                }
                            }

                            if (results.size() > 0) {
                                isBestRateReached = true;
                            }
                        }
                    }
                }

                /*
                 * Search scenario: Name(Like), Countrycode, Nbandstreet, City, Zipcode
                 */
                if (rateCounter == calculateRateDSBean.getRateAgencyNameLikeCountrycodeNbandstreetCityZipcode()) {
                    RequestDTO subRequestDTO_NameCountrycodeNbandstreetCityZipcode = requestDTOBuilder.build_NameCountrycodeNbandstreetCityZipcode(requestDTO);
                    if (subRequestDTO_NameCountrycodeNbandstreetCityZipcode != null) {
                        results = searchGinsV2(subRequestDTO_NameCountrycodeNbandstreetCityZipcode);
                        if (results != null) {
                            for (SearchResultDTO result : results) {
                                calculateRateDSBean.calculateRateV2(result, subRequestDTO_NameCountrycodeNbandstreetCityZipcode);
                                resultsSet.add(result);
                                if (resultsSet.size() > maxResults) {
                                    break rateCounterLoop;
                                }
                            }

                            if (results.size() > 0) {
                                isBestRateReached = true;
                            }
                        }
                    }
                }

                /*
                 * Search scenario: Name(Strict), Countrycode, Nbandstreet, City
                 */
                if (rateCounter == calculateRateDSBean.getRateAgencyNameStrictCountrycodeNbandstreetCity()) {
                    RequestDTO subRequestDTO_NameCountrycodeNbandstreetCity = requestDTOBuilder.build_NameCountrycodeNbandstreetCity(requestDTO);
                    if (subRequestDTO_NameCountrycodeNbandstreetCity != null) {
                        results = searchGinsV2(subRequestDTO_NameCountrycodeNbandstreetCity);
                        if (results != null) {
                            for (SearchResultDTO result : results) {
                                calculateRateDSBean.calculateRateV2(result, subRequestDTO_NameCountrycodeNbandstreetCity);
                                resultsSet.add(result);
                                if (resultsSet.size() > maxResults) {
                                    break rateCounterLoop;
                                }
                            }

                            if (results.size() > 0) {
                                isBestRateReached = true;
                            }
                        }
                    }
                }

                /*
                 * Search scenario: Name(Like), Countrycode, Nbandstreet, City
                 */
                if (rateCounter == calculateRateDSBean.getRateAgencyNameLikeCountrycodeNbandstreetCity()) {
                    RequestDTO subRequestDTO_NameCountrycodeNbandstreetCity = requestDTOBuilder.build_NameCountrycodeNbandstreetCity(requestDTO);
                    if (subRequestDTO_NameCountrycodeNbandstreetCity != null) {
                        results = searchGinsV2(subRequestDTO_NameCountrycodeNbandstreetCity);
                        if (results != null) {
                            for (SearchResultDTO result : results) {
                                calculateRateDSBean.calculateRateV2(result, subRequestDTO_NameCountrycodeNbandstreetCity);
                                resultsSet.add(result);
                                if (resultsSet.size() > maxResults) {
                                    break rateCounterLoop;
                                }
                            }

                            if (results.size() > 0) {
                                isBestRateReached = true;
                            }
                        }
                    }
                }

                /*
                 * Search scenario: Name(Strict), Countrycode, City, ZipCode
                 */
                if (rateCounter == calculateRateDSBean.getRateAgencyNameStrictCountrycodeCityZipcode()) {
                    RequestDTO subRequestDTO_NameCountryCityAndZip = requestDTOBuilder.build_NameCountrycodeCityStrictZipStrict(requestDTO);
                    if (subRequestDTO_NameCountryCityAndZip != null) {
                        results = searchGinsV2(subRequestDTO_NameCountryCityAndZip);
                        if (results != null) {
                            for (SearchResultDTO result : results) {
                                calculateRateDSBean.calculateRateV2(result, subRequestDTO_NameCountryCityAndZip);
                                resultsSet.add(result);
                                if (resultsSet.size() > maxResults) {
                                    break rateCounterLoop;
                                }
                            }

                            if (results.size() > 0) {
                                isBestRateReached = true;
                            }
                        }
                    }
                }

                /*
                 * Search scenario: Name(Like), Countrycode, City, ZipCode
                 */
                if (rateCounter == calculateRateDSBean.getRateAgencyNameLikeCountrycodeCityLikeZipLike()) { //70
                    RequestDTO subRequestDTO_NameCountryCityAndZip = requestDTOBuilder.build_NameLikeCountryCityLikeZipLike(requestDTO);
                    if (subRequestDTO_NameCountryCityAndZip != null) {
                        results = searchGinsV2(subRequestDTO_NameCountryCityAndZip);
                        if (results != null) {
                            for (SearchResultDTO result : results) {
                                calculateRateDSBean.calculateRateV2(result, subRequestDTO_NameCountryCityAndZip);
                                resultsSet.add(result);
                                if (resultsSet.size() > maxResults) {
                                    break rateCounterLoop;
                                }
                            }

                            if (results.size() > 0) {
                                isBestRateReached = true;
                            }
                        }
                    }
                }
                
                if (rateCounter == calculateRateDSBean.getRateAgencyNameLikeZipLikeCountry()) { //Name Like & Zip Like
                    RequestDTO subRequestDTO_NameCountryCityAndZip = requestDTOBuilder.build_NameCountrycodeZipLike(requestDTO);
                    if (subRequestDTO_NameCountryCityAndZip != null) {
                        results = searchGinsV2(subRequestDTO_NameCountryCityAndZip);
                        if (results != null) {
                            for (SearchResultDTO result : results) {
                                calculateRateDSBean.calculateRateV2(result, subRequestDTO_NameCountryCityAndZip);
                                resultsSet.add(result);
                                if (resultsSet.size() > maxResults) {
                                    break rateCounterLoop;
                                }
                            }

                            if (results.size() > 0) {
                                isBestRateReached = true;
                            }
                        }
                    }
                }

               
                if (rateCounter == calculateRateDSBean.getRateAgencyNameLikeZipLikeCountry()) { //65
                    RequestDTO subRequestDTO_NameCountrycodeCity = requestDTOBuilder.build_NameCountrycodeCityLike(requestDTO);
                    if (subRequestDTO_NameCountrycodeCity != null) {
                        results = searchGinsV2(subRequestDTO_NameCountrycodeCity);
                        if (results != null) {
                            for (SearchResultDTO result : results) {
                                calculateRateDSBean.calculateRateV2(result, subRequestDTO_NameCountrycodeCity);
                                resultsSet.add(result);
                                if (resultsSet.size() > maxResults) {
                                    break rateCounterLoop;
                                }
                            }

                            if (results.size() > 0) {
                                isBestRateReached = true;
                            }
                        }
                    }
                }
                
                if (rateCounter == calculateRateDSBean.getRateAgencyNameLikeZipLike()) { //Name Like & Zip Like 55%
                    RequestDTO subRequestDTO_NameCountryCityAndZip = requestDTOBuilder.build_NameLikeZipLike(requestDTO);
                    if (subRequestDTO_NameCountryCityAndZip != null) {
                        results = searchGinsV2(subRequestDTO_NameCountryCityAndZip);
                        if (results != null) {
                            for (SearchResultDTO result : results) {
                                calculateRateDSBean.calculateRateV2(result, subRequestDTO_NameCountryCityAndZip);
                                resultsSet.add(result);
                                if (resultsSet.size() > maxResults) {
                                    break rateCounterLoop;
                                }
                            }

                            if (results.size() > 0) {
                                isBestRateReached = true;
                            }
                        }
                    }
                }

               
                if (rateCounter == calculateRateDSBean.getRateAgencyNameLikeCityLikeZipLike()) { 
                    RequestDTO subRequestDTO_NameCountrycodeCity = requestDTOBuilder.build_NameLikeCityLike(requestDTO);
                    if (subRequestDTO_NameCountrycodeCity != null) {
                        results = searchGinsV2(subRequestDTO_NameCountrycodeCity);
                        if (results != null) {
                            for (SearchResultDTO result : results) {
                                calculateRateDSBean.calculateRateV2(result, subRequestDTO_NameCountrycodeCity);
                                resultsSet.add(result);
                                if (resultsSet.size() > maxResults) {
                                    break rateCounterLoop;
                                }
                            }

                            if (results.size() > 0) {
                                isBestRateReached = true;
                            }
                        }
                    }
                }
                
                if (rateCounter == calculateRateDSBean.getRateAgencyNameLikeCountrycodeCity()) { //70
                    RequestDTO subRequestDTO_NameCountrycodeCity = requestDTOBuilder.build_NameCountrycodeCityStrict(requestDTO);
                    if (subRequestDTO_NameCountrycodeCity != null) {
                        results = searchGinsV2(subRequestDTO_NameCountrycodeCity);
                        if (results != null) {
                            for (SearchResultDTO result : results) {
                                calculateRateDSBean.calculateRateV2(result, subRequestDTO_NameCountrycodeCity);
                                resultsSet.add(result);
                                if (resultsSet.size() > maxResults) {
                                    break rateCounterLoop;
                                }
                            }

                            if (results.size() > 0) {
                                isBestRateReached = true;
                            }
                        }
                    }
                }

                /*
                 * Search scenario: Name(Strict), ZC1 to 4
                 */
                if (rateCounter == calculateRateDSBean.getRateAgencyNameStrictZC14()) {
                    RequestDTO subRequestDTO_NameCountrycodeZC14 = requestDTOBuilder.build_NameZC15(requestDTO);
                    if (subRequestDTO_NameCountrycodeZC14 != null) {
                        results = searchGinsV2(subRequestDTO_NameCountrycodeZC14);
                        if (results != null) {
                            for (SearchResultDTO result : results) {
                                calculateRateDSBean.calculateRateV2(result, subRequestDTO_NameCountrycodeZC14);
                                resultsSet.add(result);
                                if (resultsSet.size() > maxResults) {
                                    break rateCounterLoop;
                                }
                            }

                            if (results.size() > 0) {
                                isBestRateReached = true;
                            }
                        }
                    }
                }

                /*
                 * Search scenario: Name(Like), ZC1 to 4
                 */
                if (rateCounter == calculateRateDSBean.getRateAgencyNameLikeZC14()) {
                    RequestDTO build_NameZC15 = requestDTOBuilder.build_NameZC15(requestDTO);
                    if (build_NameZC15 != null) {
                        results = searchGinsV2(build_NameZC15);
                        if (results != null) {
                            for (SearchResultDTO result : results) {
                                calculateRateDSBean.calculateRateV2(result, build_NameZC15);
                                resultsSet.add(result);
                                if (resultsSet.size() > maxResults) {
                                    break rateCounterLoop;
                                }
                            }

                            if (results.size() > 0) {
                                isBestRateReached = true;
                            }
                        }
                    }
                }
                
                /*
                 * Search scenario: Name(Like), ZC1 to 4, City Like
                 */
                if (rateCounter ==  calculateRateDSBean.getRateAgencyNameLikeCityLikeZC()) {
                    RequestDTO build_NameCityZC = requestDTOBuilder.build_NameCityZC(requestDTO);
                    if (build_NameCityZC != null) {
                        results = searchGinsV2(build_NameCityZC);
                        if (results != null) {
                            for (SearchResultDTO result : results) {
                                calculateRateDSBean.calculateRateV2(result, build_NameCityZC);
                                resultsSet.add(result);
                                if (resultsSet.size() > maxResults) {
                                    break rateCounterLoop;
                                }
                            }

                            if (results.size() > 0) {
                                isBestRateReached = true;
                            }
                        }
                    }
                }
                
                /*
                 * Search scenario: Name(Like), ZC1 to 4, Zip Like
                 */
                if (rateCounter == calculateRateDSBean.getRateAgencyNameLikeZipLikeZC()) {
                    RequestDTO build_NameZipZC = requestDTOBuilder.build_NameZipZC(requestDTO);
                    if (build_NameZipZC != null) {
                        results = searchGinsV2(build_NameZipZC);
                        if (results != null) {
                            for (SearchResultDTO result : results) {
                                calculateRateDSBean.calculateRateV2(result, build_NameZipZC);
                                resultsSet.add(result);
                                if (resultsSet.size() > maxResults) {
                                    break rateCounterLoop;
                                }
                            }

                            if (results.size() > 0) {
                                isBestRateReached = true;
                            }
                        }
                    }
                }

                /*
                 * Search scenario: Name(Like), ZC1 to 4, Zip Like
                 */
                if (rateCounter == calculateRateDSBean.getRateAgencyNameLikeCityLikeZipLikeZC()) {
                    RequestDTO build_NameCityZipZC = requestDTOBuilder.build_NameCityZipZC(requestDTO);
                    if (build_NameCityZipZC != null) {
                        results = searchGinsV2(build_NameCityZipZC);
                        if (results != null) {
                            for (SearchResultDTO result : results) {
                                calculateRateDSBean.calculateRateV2(result, build_NameCityZipZC);
                                resultsSet.add(result);
                                if (resultsSet.size() > maxResults) {
                                    break rateCounterLoop;
                                }
                            }

                            if (results.size() > 0) {
                                isBestRateReached = true;
                            }
                        }
                    }
                }

                /*
                 * Search scenario: Name(Strict), CountryCode, Ident
                 */
                if (rateCounter == calculateRateDSBean.getRateAgencyNameStrictCountrycodeIdent()) {
                    RequestDTO subRequestDTO_NameCountrycodeIdent = requestDTOBuilder.build_NameCountrycodeAndIdent(requestDTO);
                    if (subRequestDTO_NameCountrycodeIdent != null) {
                        results = searchGinsV2(subRequestDTO_NameCountrycodeIdent);
                        if (results != null) {
                            for (SearchResultDTO result : results) {
                                calculateRateDSBean.calculateRateV2(result, subRequestDTO_NameCountrycodeIdent);
                                resultsSet.add(result);
                                if (resultsSet.size() > maxResults) {
                                    break rateCounterLoop;
                                }
                            }

                            if (results.size() > 0) {
                                isBestRateReached = true;
                            }
                        }
                    }
                }

                /*
                 * Search scenario: Name(Like), CountryCode, Ident
                 */
                if (rateCounter == calculateRateDSBean.getRateAgencyNameLikeCountrycodeIdent()) {
                    RequestDTO subRequestDTO_NameCountrycodeIdent = requestDTOBuilder.build_NameCountrycodeAndIdent(requestDTO);
                    if (subRequestDTO_NameCountrycodeIdent != null) {
                        results = searchGinsV2(subRequestDTO_NameCountrycodeIdent);
                        if (results != null) {
                            for (SearchResultDTO result : results) {
                                calculateRateDSBean.calculateRateV2(result, subRequestDTO_NameCountrycodeIdent);
                                resultsSet.add(result);
                                if (resultsSet.size() > maxResults) {
                                    break rateCounterLoop;
                                }
                            }

                            if (results.size() > 0) {
                                isBestRateReached = true;
                            }
                        }
                    }
                }

                /*
                 * Search scenario: Name(Strict), CountryCode
                 */
                if (rateCounter == calculateRateDSBean.getRateAgencyNameStrictCountrycode()) {
                    RequestDTO subRequestDTO_NameCountrycode = requestDTOBuilder.build_NameStrictCountrycode(requestDTO);
                    if (subRequestDTO_NameCountrycode != null) {
                        results = searchGinsV2(subRequestDTO_NameCountrycode);
                        if (results != null) {
                            for (SearchResultDTO result : results) {
                                calculateRateDSBean.calculateRateV2(result, subRequestDTO_NameCountrycode);
                                resultsSet.add(result);
                                if (resultsSet.size() > maxResults) {
                                    break rateCounterLoop;
                                }
                            }

                            if (results.size() > 0) {
                                isBestRateReached = true;
                            }
                        }
                    }
                }

                /*
                 * Search scenario: Name(Like), CountryCode
                 */
                if (rateCounter == calculateRateDSBean.getRateAgencyNameLikeCountrycode()) {
                    RequestDTO subRequestDTO_NameCountrycode = requestDTOBuilder.build_NameLikeCountrycode(requestDTO);
                    if (subRequestDTO_NameCountrycode != null) {
                        results = searchGinsV2(subRequestDTO_NameCountrycode);
                        if (results != null) {
                            for (SearchResultDTO result : results) {
                                calculateRateDSBean.calculateRateV2(result, subRequestDTO_NameCountrycode);
                                resultsSet.add(result);
                                if (resultsSet.size() > maxResults) {
                                    break rateCounterLoop;
                                }
                            }

                            if (results.size() > 0) {
                                isBestRateReached = true;
                            }
                        }
                    }
                }
                
                /*
                 * Search scenario: Name(Like), Zip(Like), CountryCode
                 */
                if (rateCounter == calculateRateDSBean.getRateAgencyNameLikeZipLikeCountry()) {
                    RequestDTO subRequestDTO_NameZipCountrycode = requestDTOBuilder.build_NameZipCountrycode(requestDTO);
                    if (subRequestDTO_NameZipCountrycode != null) {
                        results = searchGinsV2(subRequestDTO_NameZipCountrycode);
                        if (results != null) {
                            for (SearchResultDTO result : results) {
                                calculateRateDSBean.calculateRateV2(result, subRequestDTO_NameZipCountrycode);
                                resultsSet.add(result);
                                if (resultsSet.size() > maxResults) {
                                    break rateCounterLoop;
                                }
                            }

                            if (results.size() > 0) {
                                isBestRateReached = true;
                            }
                        }
                    }
                }

                /*
                 * Search scenario: Name(Strict)
                 */
                if (rateCounter == calculateRateDSBean.getRateAgencyNameStrict()) {
                    RequestDTO subRequestDTO_Name = requestDTOBuilder.build_Name(requestDTO);
                    if (subRequestDTO_Name != null) {
                        results = searchGinsV2(subRequestDTO_Name);
                        if (results != null) {
                            for (SearchResultDTO result : results) {
                                calculateRateDSBean.calculateRateV2(result, subRequestDTO_Name);
                                resultsSet.add(result);
                                if (resultsSet.size() > maxResults) {
                                    break rateCounterLoop;
                                }
                            }

                            if (results.size() > 0) {
                                isBestRateReached = true;
                            }
                        }
                    }
                }
                
                /*
                 * Search scenario: Name(Like) Zip(Like)
                 */
                if (rateCounter == calculateRateDSBean.getRateAgencyNameLikeZipLikeCountry()) {
                    RequestDTO subRequestDTO_Name = requestDTOBuilder.build_NameLikeZipLike(requestDTO);
                    if (subRequestDTO_Name != null) {
                        results = searchGinsV2(subRequestDTO_Name);
                        if (results != null) {
                            for (SearchResultDTO result : results) {
                                calculateRateDSBean.calculateRateV2(result, subRequestDTO_Name);
                                resultsSet.add(result);
                                if (resultsSet.size() > maxResults) {
                                    break rateCounterLoop;
                                }
                            }

                            if (results.size() > 0) {
                                isBestRateReached = true;
                            }
                        }
                    }
                }

                /*
                 * Search scenario: Name(Like)
                 */
                if (rateCounter == calculateRateDSBean.getRateAgencyNameLike()) {
                    RequestDTO subRequestDTO_Name = requestDTOBuilder.build_Name(requestDTO);
                    if (subRequestDTO_Name != null) {
                        results = searchGinsV2(subRequestDTO_Name);
                        if (results != null) {
                            for (SearchResultDTO result : results) {
                                calculateRateDSBean.calculateRateV2(result, subRequestDTO_Name);
                                resultsSet.add(result);
                                if (resultsSet.size() > maxResults) {
                                    break rateCounterLoop;
                                }
                            }

                            if (results.size() > 0) {
                                isBestRateReached = true;
                            }
                        }
                    }
                }

                /*
                 * Search scenario: Phone
                 */
                if (rateCounter == calculateRateDSBean.getRateAgencyPhone()) {
                    RequestDTO subRequestDTO_Phone = requestDTOBuilder.build_Phone(requestDTO);
                    if (subRequestDTO_Phone != null) {
                        results = searchGinsV2(subRequestDTO_Phone);
                        if (results != null) {
                            for (SearchResultDTO result : results) {
                                calculateRateDSBean.calculateRateV2(result, subRequestDTO_Phone);
                                resultsSet.add(result);
                                if (resultsSet.size() > maxResults) {
                                    break rateCounterLoop;
                                }
                            }

                            if (results.size() > 0) {
                                isBestRateReached = true;
                            }
                        }
                    }
                }

                /*
                 * Search scenario: Email
                 */
                if (rateCounter == calculateRateDSBean.getRateAgencyEmail()) {
                    RequestDTO subRequestDTO_Email = requestDTOBuilder.build_Email(requestDTO);
                    if (subRequestDTO_Email != null) {
                        results = searchGinsV2(subRequestDTO_Email);
                        if (results != null) {
                            for (SearchResultDTO result : results) {
                                calculateRateDSBean.calculateRateV2(result, subRequestDTO_Email);
                                resultsSet.add(result);
                                if (resultsSet.size() > maxResults) {
                                    break rateCounterLoop;
                                }
                            }

                            if (results.size() > 0) {
                                isBestRateReached = true;
                            }
                        }
                    }
                }

                /*
                 * Search scenario: Phone, Ident
                 */
                if (rateCounter == calculateRateDSBean.getRateAgencyPhoneIdent()) {
                    RequestDTO subRequestDTO_PhoneAndIdent = requestDTOBuilder.build_PhoneAndIdent(requestDTO);
                    if (subRequestDTO_PhoneAndIdent != null) {
                        results = searchGinsV2(subRequestDTO_PhoneAndIdent);
                        if (results != null) {
                            for (SearchResultDTO result : results) {
                                calculateRateDSBean.calculateRateV2(result, subRequestDTO_PhoneAndIdent);
                                resultsSet.add(result);
                                if (resultsSet.size() > maxResults) {
                                    break rateCounterLoop;
                                }
                            }

                            if (results.size() > 0) {
                                isBestRateReached = true;
                            }
                        }
                    }
                }

                /*
                 * Search scenario: Email, Ident
                 */
                if (rateCounter == calculateRateDSBean.getRateAgencyEmailIdent()) {
                    RequestDTO subRequestDTO_EmailAndIdent = requestDTOBuilder.build_EmailAndIdent(requestDTO);
                    if (subRequestDTO_EmailAndIdent != null) {
                        results = searchGinsV2(subRequestDTO_EmailAndIdent);
                        if (results != null) {
                            for (SearchResultDTO result : results) {
                                calculateRateDSBean.calculateRateV2(result, subRequestDTO_EmailAndIdent);
                                resultsSet.add(result);
                                if (resultsSet.size() > maxResults) {
                                    break rateCounterLoop;
                                }
                            }

                            if (results.size() > 0) {
                                isBestRateReached = true;
                            }
                        }
                    }
                }

                /*
                 * Search scenario: Ident
                 */
                if (rateCounter == calculateRateDSBean.getRateAgencyIdent()) {
                    RequestDTO subRequestDTO_Ident = requestDTOBuilder.build_Ident(requestDTO);
                    if (subRequestDTO_Ident != null) {
                        results = searchGinsV2(subRequestDTO_Ident);
                        if (results != null) {
                            for (SearchResultDTO result : results) {
                                calculateRateDSBean.calculateRateV2(result, subRequestDTO_Ident);
                                resultsSet.add(result);
                                if (resultsSet.size() > maxResults) {
                                    break rateCounterLoop;
                                }
                            }

                            if (results.size() > 0) {
                                isBestRateReached = true;
                            }
                        }
                    }
                }
            }
        }

        return resultsSet;
    }


	/**
	 * Executes a manual search: 
	 *  - 23 consecutive scenarios until the indicated 
	 *  	      maximum result (requestDTO.getQueryMaxResults() => HQL.properties /query.maxResults) 
	 *     is reached
	 *     
	 *       
	 *    Each result scenario returns a set of gins
	 *    The found gins are stored in a common Set<String>. And the correspondent
	 *    firm is loaded and added to the response.
	 *    
	 *    If no results are found, a business exception is throwed (001 - "NO RESULTS FOUND")
	 *    If the index(page) is beyond maxResults, a business exception is throwed (133 - "INDEX BEYOND RESULTS LIMITS")
	 *    
	 * @param requestDTO
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	private ResponseDTO executeManualSearch(RequestDTO requestDTO) throws BusinessException
	{
		/*
		 * Returned response instantiation
		 */
		ResponseDTO responseDTO = ResponseDTOBuilder.build();
		
		if(requestDTO != null)
		{
			/*
			 * Application parameters: 
			 *   - index
			 *   - page size
			 *   - max allowed results for a manual search
			 */
			int index = requestDTO.getQueryIndex();
			int pageSize = requestDTO.getQueryPageSize();		
			
			/*
			 * The list of results
			 */
			List<SearchResultDTO> resultsList = new ArrayList<SearchResultDTO>();
			
			/*
			 * The list per page
			 */
			List<SearchResultDTO> returnedList = new ArrayList<SearchResultDTO>();
			
			/*
			 * Found gins and correspondent rates
			 *  Search operation
			 */
			Set<SearchResultDTO> resultsSet = executeManualSearchForAgencies(requestDTO);
			
			if(!resultsSet.isEmpty())
			{
				/*
				 * Calculating first and last element in the page
				 */
				int firstElementInPage = (index - 1) * pageSize;
				int lastElementInPage = (index * pageSize);
				
				if((firstElementInPage + 1) > resultsSet.size())
				{
					throwsException(getIncoherentIndexCode(), getIncoherentIndexMessage());
				}
				else
				{
					resultsList.addAll(resultsSet);
					/*
					 * Loading gins' firms
					 */
					int minOrder = firstElementInPage;
					int maxOrder = Math.min(lastElementInPage, resultsList.size());

					
					returnedList = resultsList.subList(minOrder, maxOrder);
					for(SearchResultDTO result : returnedList)
					{
						AgencyDTO agency = searchAgencyDAOBean.readAgencyByGin(requestDTO, result.getGin());
                        if (agency != null && agency.getAgencyInformationsDTO() != null) {
			    			agency.setRelevance(result.getRate());
			    			responseDTO.getAgencies().add(agency);
			    		}
					}
					
					/*
					 * Updating continuity fields
					 *   - first index
					 *   - last index
					 *   - maxResults
					 */
					responseDTO.setFirstIndex(firstElementInPage + 1);
					responseDTO.setMaxResult(maxOrder);
					responseDTO.setTotalNumber(resultsList.size());
					
					/*
					 * Return code and message
					 */
					responseDTO.setReturnCode(getSuccessReturnCode());
					responseDTO.setReturnMessage(getSuccessReturnMessage());
				}
			}
			else
			{
				/*
				 * If no results, we throw a business exception
				 */
				throwsException(getNoResultsReturnCode(), getNoResultsReturnMessage());
			}
		}
			
		return responseDTO;
	}	
	
	private ResponseDTO executeManualSearchV2(RequestDTO requestDTO) throws BusinessException, SystemException
	{
		/*
		 * Returned response instantiation
		 */
		ResponseDTO responseDTO = ResponseDTOBuilder.build();
		
		if(requestDTO != null)
		{
			/*
			 * Application parameters: 
			 *   - index
			 *   - page size
			 *   - max allowed results for a manual search
			 */
			int index = requestDTO.getQueryIndex();
			int pageSize = requestDTO.getQueryPageSize();		
			
			/*
			 * The list of results
			 */
			List<SearchResultDTO> resultsList = new ArrayList<SearchResultDTO>();
			
			/*
			 * The list per page
			 */
			List<SearchResultDTO> returnedList = new ArrayList<SearchResultDTO>();
			
			/*
			 * Found gins and correspondent rates
			 *  Search operation
			 */
			Set<SearchResultDTO> resultsSet = executeManualSearchForAgenciesV2(requestDTO);
			
			if(!resultsSet.isEmpty())
			{
				/*
				 * Calculating first and last element in the page
				 */
				int firstElementInPage = (index - 1) * pageSize;
				int lastElementInPage = (index * pageSize);
				
				if((firstElementInPage + 1) > resultsSet.size())
				{
					throwsException(getIncoherentIndexCode(), getIncoherentIndexMessage());
				}
				else
				{
					resultsList.addAll(resultsSet);
					/*
					 * Loading gins' firms
					 */
					int minOrder = firstElementInPage;
					int maxOrder = Math.min(lastElementInPage, resultsList.size());

					
					returnedList = resultsList.subList(minOrder, maxOrder);
					for(SearchResultDTO result : returnedList)
					{
						AgencyDTO agency = searchAgencyDAOBean.readAgencyByGin(requestDTO, result.getGin());
			    		if(agency != null && agency.getAgencyInformationsDTO() != null)
			    		{
			    			agency.setRelevance(result.getRate());
			    			responseDTO.getAgencies().add(agency);
			    		}
					}
					
					/*
					 * Updating continuity fields
					 *   - first index
					 *   - last index
					 *   - maxResults
					 */
					responseDTO.setFirstIndex(firstElementInPage + 1);
					responseDTO.setMaxResult(maxOrder);
					responseDTO.setTotalNumber(resultsList.size());
					
					/*
					 * Return code and message
					 */
					responseDTO.setReturnCode(getSuccessReturnCode());
					responseDTO.setReturnMessage(getSuccessReturnMessage());
				}
			}
			else
			{
				/*
				 * If no results, we throw a business exception
				 */
				throwsException(getNoResultsReturnCode(), getNoResultsReturnMessage());
			}
		}
			
		return responseDTO;
	}	
	

	/**
	 * Executes a manual search for firms
	 *  => 23 search scenarios are executed until the max size result is reached
	 *  
	 * @param requestDTO
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	private Set<SearchResultDTO> executeManualSearchForAgencies(RequestDTO requestDTO) throws BusinessException
	{
		/*
		 * Result set for unique ordered values
		 */
		Set<SearchResultDTO> resultsSet = new TreeSet<SearchResultDTO>();
		
		if(requestDTO != null)
		{
			/*
			 * Application parameters: 
			 *   - max allowed results for a manual search
			 */
			int maxResults = requestDTO.getQueryMaxResults();
			
			/*
			 * results container
			 */
			List<SearchResultDTO> results = null;		
			
			/*
			 * Max possible relevance rate
			 */
			int maxRate = calculateRateDSBean.getMaxRate();
						
			rateCounterLoop:
			for(int rateCounter = maxRate; rateCounter >= 0; rateCounter -= 1)
			{
				/*
				 * Search scenario: Name(Strict) and Phone
				 */
				if(rateCounter == calculateRateDSBean.getRateAgencyNameStrictAndPhone())
				{
					RequestDTO subRequestDTO_NameAndPhone = requestDTOBuilder.build_NameAndPhone(requestDTO);
					if(subRequestDTO_NameAndPhone != null)
					{
						results = searchGins(subRequestDTO_NameAndPhone);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRate(result, subRequestDTO_NameAndPhone);
								resultsSet.add(result);
								if(resultsSet.size() > maxResults)
								{
									break rateCounterLoop;
								}
							}
						}
					}
				}
				
				/*
				 * Search scenario: Name(Like) and Phone
				 */
				if(rateCounter == calculateRateDSBean.getRateAgencyNameLikeAndPhone())
				{
					RequestDTO subRequestDTO_NameAndPhone = requestDTOBuilder.build_NameAndPhone(requestDTO);
					if(subRequestDTO_NameAndPhone != null)
					{
						results = searchGins(subRequestDTO_NameAndPhone);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRate(result, subRequestDTO_NameAndPhone);
								resultsSet.add(result);
								if(resultsSet.size() > maxResults)
								{
									break rateCounterLoop;
								}
							}
						}
					}
				}
				
				/*
				 * Search scenario: Name(Strict) and Email
				 */
				if(rateCounter == calculateRateDSBean.getRateAgencyNameStrictEmail())
				{
					RequestDTO subRequestDTO_NameAndEmail = requestDTOBuilder.build_NameAndEmail(requestDTO);
					if(subRequestDTO_NameAndEmail != null)
					{
						results = searchGins(subRequestDTO_NameAndEmail);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRate(result, subRequestDTO_NameAndEmail);
								resultsSet.add(result);
								if(resultsSet.size() > maxResults)
								{
									break rateCounterLoop;
								}
							}
						}
					}
				}
				
				/*
				 * Search scenario: Name(Like) and Email
				 */
				if(rateCounter == calculateRateDSBean.getRateAgencyNameLikeEmail())
				{
					RequestDTO subRequestDTO_NameAndEmail = requestDTOBuilder.build_NameAndEmail(requestDTO);
					if(subRequestDTO_NameAndEmail != null)
					{
						results = searchGins(subRequestDTO_NameAndEmail);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRate(result, subRequestDTO_NameAndEmail);
								resultsSet.add(result);
								if(resultsSet.size() > maxResults)
								{
									break rateCounterLoop;
								}
							}
						}
					}
				}
				
				/*
				 * Search scenario: Name(Strict), Countrycode, Nbandstreet, City, Zipcode
				 */
				if(rateCounter == calculateRateDSBean.getRateAgencyNameStrictCountrycodeNbandstreetCityZipcode())
				{
					RequestDTO subRequestDTO_NameCountrycodeNbandstreetCityZipcode = requestDTOBuilder.build_NameCountrycodeNbandstreetCityZipcode(requestDTO);
					if(subRequestDTO_NameCountrycodeNbandstreetCityZipcode != null)
					{
						results = searchGins(subRequestDTO_NameCountrycodeNbandstreetCityZipcode);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRate(result, subRequestDTO_NameCountrycodeNbandstreetCityZipcode);
								resultsSet.add(result);
								if(resultsSet.size() > maxResults)
								{
									break rateCounterLoop;
								}
							}
						}
					}
				}
				
				/*
				 * Search scenario: Name(Like), Countrycode, Nbandstreet, City, Zipcode
				 */
				if(rateCounter == calculateRateDSBean.getRateAgencyNameLikeCountrycodeNbandstreetCityZipcode())
				{
					RequestDTO subRequestDTO_NameCountrycodeNbandstreetCityZipcode = requestDTOBuilder.build_NameCountrycodeNbandstreetCityZipcode(requestDTO);
					if(subRequestDTO_NameCountrycodeNbandstreetCityZipcode != null)
					{
						results = searchGins(subRequestDTO_NameCountrycodeNbandstreetCityZipcode);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRate(result, subRequestDTO_NameCountrycodeNbandstreetCityZipcode);
								resultsSet.add(result);
								if(resultsSet.size() > maxResults)
								{
									break rateCounterLoop;
								}
							}
						}
					}
				}
				
				/*
				 * Search scenario: Name(Strict), Countrycode, Nbandstreet, City
				 */
				if(rateCounter == calculateRateDSBean.getRateAgencyNameStrictCountrycodeNbandstreetCity())
				{
					RequestDTO subRequestDTO_NameCountrycodeNbandstreetCity = requestDTOBuilder.build_NameCountrycodeNbandstreetCity(requestDTO);
					if(subRequestDTO_NameCountrycodeNbandstreetCity != null)
					{
						results = searchGins(subRequestDTO_NameCountrycodeNbandstreetCity);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRate(result, subRequestDTO_NameCountrycodeNbandstreetCity);
								resultsSet.add(result);
								if(resultsSet.size() > maxResults)
								{
									break rateCounterLoop;
								}
							}
						}
					}
				}
				
				/*
				 * Search scenario: Name(Like), Countrycode, Nbandstreet, City
				 */
				if(rateCounter == calculateRateDSBean.getRateAgencyNameLikeCountrycodeNbandstreetCity())
				{
					RequestDTO subRequestDTO_NameCountrycodeNbandstreetCity = requestDTOBuilder.build_NameCountrycodeNbandstreetCity(requestDTO);
					if(subRequestDTO_NameCountrycodeNbandstreetCity != null)
					{
						results = searchGins(subRequestDTO_NameCountrycodeNbandstreetCity);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRate(result, subRequestDTO_NameCountrycodeNbandstreetCity);
								resultsSet.add(result);
								if(resultsSet.size() > maxResults)
								{
									break rateCounterLoop;
								}
							}
						}
					}
				}
				
				/*
				 * Search scenario: Name(Strict), Countrycode, City, ZipCode
				 */
				if(rateCounter == calculateRateDSBean.getRateAgencyNameStrictCountrycodeCityZipcode())
				{
					RequestDTO subRequestDTO_NameCountryCityAndZip = requestDTOBuilder.build_NameCountrycodeCityStrictZipStrict(requestDTO);
					if(subRequestDTO_NameCountryCityAndZip != null)
					{
						results = searchGins(subRequestDTO_NameCountryCityAndZip);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRate(result, subRequestDTO_NameCountryCityAndZip);
								resultsSet.add(result);
								if(resultsSet.size() > maxResults)
								{
									break rateCounterLoop;
								}
							}
						}
					}
				}
				
			
				
				/*
				 * Search scenario: Name(Strict), ZC1 to 4
				 */
				if(rateCounter == calculateRateDSBean.getRateAgencyNameStrictZC14())
				{
					RequestDTO subRequestDTO_NameCountrycodeZC14 = requestDTOBuilder.build_NameZC15(requestDTO);
					if(subRequestDTO_NameCountrycodeZC14 != null)
					{
						results = searchGins(subRequestDTO_NameCountrycodeZC14);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRate(result, subRequestDTO_NameCountrycodeZC14);
								resultsSet.add(result);
								if(resultsSet.size() > maxResults)
								{
									break rateCounterLoop;
								}
							}
						}
					}
				}
				
				/*
				 * Search scenario: Name(Like), ZC1 to 4
				 */
				if(rateCounter == calculateRateDSBean.getRateAgencyNameLikeZC14())
				{
					RequestDTO subRequestDTO_NameCountrycodeZC14 = requestDTOBuilder.build_NameZC15(requestDTO);
					if(subRequestDTO_NameCountrycodeZC14 != null)
					{
						results = searchGins(subRequestDTO_NameCountrycodeZC14);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRate(result, subRequestDTO_NameCountrycodeZC14);
								resultsSet.add(result);
								if(resultsSet.size() > maxResults)
								{
									break rateCounterLoop;
								}
							}
						}
					}
				}
				
				 /*
                 * Search scenario: Name(Like), ZC1 to 4, City Like
                 */
                if (rateCounter ==  75){ //calculateRateDSBean.getRateAgencyNameLikeCityLikeZC()) {
                    RequestDTO subRequestDTO_NameCountrycodeZC14 = requestDTOBuilder.build_NameCityZC(requestDTO);
                    if (subRequestDTO_NameCountrycodeZC14 != null) {
                        results = searchGins(subRequestDTO_NameCountrycodeZC14);
                        if (results != null) {
                            for (SearchResultDTO result : results) {
                                calculateRateDSBean.calculateRateV2(result, subRequestDTO_NameCountrycodeZC14);
                                resultsSet.add(result);
                                if (resultsSet.size() > maxResults) {
                                    break rateCounterLoop;
                                }
                            }                          
                        }
                    }
                }
                
                /*
                 * Search scenario: Name(Like), ZC1 to 4, Zip Like
                 */
                if (rateCounter == 75) { //calculateRateDSBean.getRateAgencyNameLikeZipLikeZC()) {
                    RequestDTO subRequestDTO_NameCountrycodeZC14 = requestDTOBuilder.build_NameZipZC(requestDTO);
                    if (subRequestDTO_NameCountrycodeZC14 != null) {
                        results = searchGins(subRequestDTO_NameCountrycodeZC14);
                        if (results != null) {
                            for (SearchResultDTO result : results) {
                                calculateRateDSBean.calculateRateV2(result, subRequestDTO_NameCountrycodeZC14);
                                resultsSet.add(result);
                                if (resultsSet.size() > maxResults) {
                                    break rateCounterLoop;
                                }
                            }
                        }
                    }
                }

				
				/*
				 * Search scenario: Name(Strict), country code, Ident
				 */
				if(rateCounter == calculateRateDSBean.getRateAgencyNameStrictCountrycodeIdent())
				{
					RequestDTO subRequestDTO_NameCountrycodeIdent = requestDTOBuilder.build_NameCountrycodeAndIdent(requestDTO);
					if(subRequestDTO_NameCountrycodeIdent != null)
					{
						results = searchGins(subRequestDTO_NameCountrycodeIdent);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRate(result, subRequestDTO_NameCountrycodeIdent);
								resultsSet.add(result);
								if(resultsSet.size() > maxResults)
								{
									break rateCounterLoop;
								}
							}
						}
					}
				}
				
				/*
				 * Search scenario: Name(Like), country code, Ident
				 */
				if(rateCounter == calculateRateDSBean.getRateAgencyNameLikeCountrycodeIdent())
				{
					RequestDTO subRequestDTO_NameCountrycodeIdent = requestDTOBuilder.build_NameCountrycodeAndIdent(requestDTO);
					if(subRequestDTO_NameCountrycodeIdent != null)
					{
						results = searchGins(subRequestDTO_NameCountrycodeIdent);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRate(result, subRequestDTO_NameCountrycodeIdent);
								resultsSet.add(result);
								if(resultsSet.size() > maxResults)
								{
									break rateCounterLoop;
								}
							}
						}
					}
				}
				
				/*
				 * Search scenario: Name(Strict), country code
				 */
				if(rateCounter == calculateRateDSBean.getRateAgencyNameStrictCountrycode())
				{
					RequestDTO subRequestDTO_NameCountrycode = requestDTOBuilder.build_NameAndCountrycode(requestDTO);
					if(subRequestDTO_NameCountrycode != null)
					{
						results = searchGins(subRequestDTO_NameCountrycode);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRate(result, subRequestDTO_NameCountrycode);
								resultsSet.add(result);
								if(resultsSet.size() > maxResults)
								{
									break rateCounterLoop;
								}
							}
						}
					}
				}
				
				/*
				 * Search scenario: Name(Like), country code
				 */
				if(rateCounter == calculateRateDSBean.getRateAgencyNameLikeCountrycode())
				{
					RequestDTO subRequestDTO_NameCountrycode = requestDTOBuilder.build_NameAndCountrycode(requestDTO);
					if(subRequestDTO_NameCountrycode != null)
					{
						results = searchGins(subRequestDTO_NameCountrycode);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRate(result, subRequestDTO_NameCountrycode);
								resultsSet.add(result);
								if(resultsSet.size() > maxResults)
								{
									break rateCounterLoop;
								}
							}
						}
					}
				}
				
				/*
				 * Search scenario: Name(Strict)
				 */
				if(rateCounter == calculateRateDSBean.getRateAgencyNameStrict())
				{
					RequestDTO subRequestDTO_Name = requestDTOBuilder.build_Name(requestDTO);
					if(subRequestDTO_Name != null)
					{
						results = searchGins(subRequestDTO_Name);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRate(result, subRequestDTO_Name);
								resultsSet.add(result);
								if(resultsSet.size() > maxResults)
								{
									break rateCounterLoop;
								}
							}
						}
					}
				}
				
				/*
				 * Search scenario: Name(Like)
				 */
				if(rateCounter == calculateRateDSBean.getRateAgencyNameLike())
				{
					RequestDTO subRequestDTO_Name = requestDTOBuilder.build_Name(requestDTO);
					if(subRequestDTO_Name != null)
					{
						results = searchGins(subRequestDTO_Name);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRate(result, subRequestDTO_Name);
								resultsSet.add(result);
								if(resultsSet.size() > maxResults)
								{
									break rateCounterLoop;
								}
							}
						}
					}
				}
				
				/*
				 * Search scenario: Phone
				 */
				if(rateCounter == calculateRateDSBean.getRateAgencyPhone())
				{
					RequestDTO subRequestDTO_Phone = requestDTOBuilder.build_Phone(requestDTO);
					if(subRequestDTO_Phone != null)
					{
						results = searchGins(subRequestDTO_Phone);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRate(result, subRequestDTO_Phone);
								resultsSet.add(result);
								if(resultsSet.size() > maxResults)
								{
									break rateCounterLoop;
								}
							}
						}
					}
				}
				
				/*
				 * Search scenario: Email
				 */
				if(rateCounter == calculateRateDSBean.getRateAgencyEmail())
				{
					RequestDTO subRequestDTO_Email = requestDTOBuilder.build_Email(requestDTO);
					if(subRequestDTO_Email != null)
					{
						results = searchGins(subRequestDTO_Email);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRate(result, subRequestDTO_Email);
								resultsSet.add(result);
								if(resultsSet.size() > maxResults)
								{
									break rateCounterLoop;
								}
							}
						}
					}
				}
				
				/*
				 * Search scenario: Phone, ident
				 */
				if(rateCounter == calculateRateDSBean.getRateAgencyPhoneIdent())
				{
					RequestDTO subRequestDTO_PhoneAndIdent = requestDTOBuilder.build_PhoneAndIdent(requestDTO);
					if(subRequestDTO_PhoneAndIdent != null)
					{
						results = searchGins(subRequestDTO_PhoneAndIdent);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRate(result, subRequestDTO_PhoneAndIdent);
								resultsSet.add(result);
								if(resultsSet.size() > maxResults)
								{
									break rateCounterLoop;
								}
							}
						}
					}
				}
				
				/*
				 * Search scenario: Email, Ident
				 */
				if(rateCounter == calculateRateDSBean.getRateAgencyEmailIdent())
				{
					RequestDTO subRequestDTO_EmailAndIdent = requestDTOBuilder.build_EmailAndIdent(requestDTO);
					if(subRequestDTO_EmailAndIdent != null)
					{
						results = searchGins(subRequestDTO_EmailAndIdent);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRate(result, subRequestDTO_EmailAndIdent);
								resultsSet.add(result);
								if(resultsSet.size() > maxResults)
								{
									break rateCounterLoop;
								}
							}
						}
					}
				}
				
				/*
				 * Search scenario: Ident
				 */
				if(rateCounter == calculateRateDSBean.getRateAgencyIdent())
				{
					RequestDTO subRequestDTO_Ident = requestDTOBuilder.build_Ident(requestDTO);
					if(subRequestDTO_Ident != null)
					{
						results = searchGins(subRequestDTO_Ident);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRate(result, subRequestDTO_Ident);
								resultsSet.add(result);
								if(resultsSet.size() > maxResults)
								{
									break rateCounterLoop;
								}
							}
						}
					}
				}
			}	
		}
			
		return resultsSet;
	}	
	
	
	 // V2 of executeAutomaticSearchForAgencies
    private Set<SearchResultDTO> executeManualSearchForAgenciesV2(RequestDTO requestDTO) throws BusinessException, SystemException {
        /*
         * Result set for unique ordered values
         */
        Set<SearchResultDTO> resultsSet = new TreeSet<SearchResultDTO>();

        if (requestDTO != null) {
            /*
             * Application parameters:
             *   - max allowed results for a manual search
             */
            int maxResults = requestDTO.getQueryMaxResults();

            /*
             * results container
             */
            List<SearchResultDTO> results = null;

            /*
             * Needed for automatic search
             * As soon as a best relevance rate
             *  is reached, the search operation
             *  ends
             */
            boolean isBestRateReached = false;

            /*
             * Max possible relevance rate
             */
            int maxRate = calculateRateDSBean.getMaxRateForV2();

            rateCounterLoop:
            for (int rateCounter = maxRate; rateCounter >= 0; rateCounter -= 1) {
                if (isBestRateReached) {
                    break rateCounterLoop;
                }

                /*
                 * Search scenario: Name(Strict) and Phone
                 */
                if (rateCounter == calculateRateDSBean.getRateAgencyNameStrictAndPhone()) {
                    RequestDTO subRequestDTO_NameAndPhone = requestDTOBuilder.build_NameAndPhone(requestDTO);
                    if (subRequestDTO_NameAndPhone != null) {
                        results = searchGinsV2(subRequestDTO_NameAndPhone);
                        if (results != null) {
                            for (SearchResultDTO result : results) {
                                calculateRateDSBean.calculateRateV2(result, subRequestDTO_NameAndPhone);
                                resultsSet.add(result);
                                if (resultsSet.size() > maxResults) {
                                    break rateCounterLoop;
                                }
                            }                          
                        }
                    }
                }

                /*
                 * Search scenario: Name(Like) and Phone
                 */
                if (rateCounter == calculateRateDSBean.getRateAgencyNameLikeAndPhone()) {
                    RequestDTO subRequestDTO_NameAndPhone = requestDTOBuilder.build_NameAndPhone(requestDTO);
                    if (subRequestDTO_NameAndPhone != null) {
                        results = searchGinsV2(subRequestDTO_NameAndPhone);
                        if (results != null) {
                            for (SearchResultDTO result : results) {
                                calculateRateDSBean.calculateRateV2(result, subRequestDTO_NameAndPhone);
                                resultsSet.add(result);
                                if (resultsSet.size() > maxResults) {
                                    break rateCounterLoop;
                                }
                            }
                           
                        }
                    }
                }

                /*
                 * Search scenario: Name(Strict) and Email
                 */
                if (rateCounter == calculateRateDSBean.getRateAgencyNameStrictEmail()) {
                    RequestDTO subRequestDTO_NameAndEmail = requestDTOBuilder.build_NameAndEmail(requestDTO);
                    if (subRequestDTO_NameAndEmail != null) {
                        results = searchGinsV2(subRequestDTO_NameAndEmail);
                        if (results != null) {
                            for (SearchResultDTO result : results) {
                                calculateRateDSBean.calculateRateV2(result, subRequestDTO_NameAndEmail);
                                resultsSet.add(result);
                                if (resultsSet.size() > maxResults) {
                                    break rateCounterLoop;
                                }
                            }

                           
                        }
                    }
                }

                /*
                 * Search scenario: Name(Like) and Email
                 */
                if (rateCounter == calculateRateDSBean.getRateAgencyNameLikeEmail()) {
                    RequestDTO subRequestDTO_NameAndEmail = requestDTOBuilder.build_NameAndEmail(requestDTO);
                    if (subRequestDTO_NameAndEmail != null) {
                        results = searchGinsV2(subRequestDTO_NameAndEmail);
                        if (results != null) {
                            for (SearchResultDTO result : results) {
                                calculateRateDSBean.calculateRateV2(result, subRequestDTO_NameAndEmail);
                                resultsSet.add(result);
                                if (resultsSet.size() > maxResults) {
                                    break rateCounterLoop;
                                }
                            }

                           
                        }
                    }
                }

                /*
                 * Search scenario: Name(Strict), Countrycode, Nbandstreet, City, Zipcode
                 */
                if (rateCounter == calculateRateDSBean.getRateAgencyNameStrictCountrycodeNbandstreetCityZipcode()) {
                    RequestDTO subRequestDTO_NameCountrycodeNbandstreetCityZipcode = requestDTOBuilder.build_NameCountrycodeNbandstreetCityZipcode(requestDTO);
                    if (subRequestDTO_NameCountrycodeNbandstreetCityZipcode != null) {
                        results = searchGinsV2(subRequestDTO_NameCountrycodeNbandstreetCityZipcode);
                        if (results != null) {
                            for (SearchResultDTO result : results) {
                                calculateRateDSBean.calculateRateV2(result, subRequestDTO_NameCountrycodeNbandstreetCityZipcode);
                                resultsSet.add(result);
                                if (resultsSet.size() > maxResults) {
                                    break rateCounterLoop;
                                }
                            }

                           
                        }
                    }
                }

                /*
                 * Search scenario: Name(Like), Countrycode, Nbandstreet, City, Zipcode
                 */
                if (rateCounter == calculateRateDSBean.getRateAgencyNameLikeCountrycodeNbandstreetCityZipcode()) {
                    RequestDTO subRequestDTO_NameCountrycodeNbandstreetCityZipcode = requestDTOBuilder.build_NameCountrycodeNbandstreetCityZipcode(requestDTO);
                    if (subRequestDTO_NameCountrycodeNbandstreetCityZipcode != null) {
                        results = searchGinsV2(subRequestDTO_NameCountrycodeNbandstreetCityZipcode);
                        if (results != null) {
                            for (SearchResultDTO result : results) {
                                calculateRateDSBean.calculateRateV2(result, subRequestDTO_NameCountrycodeNbandstreetCityZipcode);
                                resultsSet.add(result);
                                if (resultsSet.size() > maxResults) {
                                    break rateCounterLoop;
                                }
                            }

                           
                        }
                    }
                }

                /*
                 * Search scenario: Name(Strict), Countrycode, Nbandstreet, City
                 */
                if (rateCounter == calculateRateDSBean.getRateAgencyNameStrictCountrycodeNbandstreetCity()) {
                    RequestDTO subRequestDTO_NameCountrycodeNbandstreetCity = requestDTOBuilder.build_NameCountrycodeNbandstreetCity(requestDTO);
                    if (subRequestDTO_NameCountrycodeNbandstreetCity != null) {
                        results = searchGinsV2(subRequestDTO_NameCountrycodeNbandstreetCity);
                        if (results != null) {
                            for (SearchResultDTO result : results) {
                                calculateRateDSBean.calculateRateV2(result, subRequestDTO_NameCountrycodeNbandstreetCity);
                                resultsSet.add(result);
                                if (resultsSet.size() > maxResults) {
                                    break rateCounterLoop;
                                }
                            }

                           
                        }
                    }
                }

                /*
                 * Search scenario: Name(Like), Countrycode, Nbandstreet, City
                 */
                if (rateCounter == calculateRateDSBean.getRateAgencyNameLikeCountrycodeNbandstreetCity()) {
                    RequestDTO subRequestDTO_NameCountrycodeNbandstreetCity = requestDTOBuilder.build_NameCountrycodeNbandstreetCity(requestDTO);
                    if (subRequestDTO_NameCountrycodeNbandstreetCity != null) {
                        results = searchGinsV2(subRequestDTO_NameCountrycodeNbandstreetCity);
                        if (results != null) {
                            for (SearchResultDTO result : results) {
                                calculateRateDSBean.calculateRateV2(result, subRequestDTO_NameCountrycodeNbandstreetCity);
                                resultsSet.add(result);
                                if (resultsSet.size() > maxResults) {
                                    break rateCounterLoop;
                                }
                            }

                          
                        }
                    }
                }

                /*
                 * Search scenario: Name(Strict), Countrycode, City, ZipCode
                 */
                if (rateCounter == calculateRateDSBean.getRateAgencyNameStrictCountrycodeCityZipcode()) {
                    RequestDTO subRequestDTO_NameCountryCityAndZip = requestDTOBuilder.build_NameCountrycodeCityStrictZipStrict(requestDTO);
                    if (subRequestDTO_NameCountryCityAndZip != null) {
                        results = searchGinsV2(subRequestDTO_NameCountryCityAndZip);
                        if (results != null) {
                            for (SearchResultDTO result : results) {
                                calculateRateDSBean.calculateRateV2(result, subRequestDTO_NameCountryCityAndZip);
                                resultsSet.add(result);
                                if (resultsSet.size() > maxResults) {
                                    break rateCounterLoop;
                                }
                            }

                        }
                    }
                }

                /*
                 * Search scenario: Name(Like), Countrycode, City, ZipCode
                 */
                if (rateCounter == calculateRateDSBean.getRateAgencyNameLikeCountrycodeCityLikeZipLike()) { //70
                    RequestDTO subRequestDTO_NameCountryCityAndZip = requestDTOBuilder.build_NameLikeCountryCityLikeZipLike(requestDTO);
                    if (subRequestDTO_NameCountryCityAndZip != null) {
                        results = searchGinsV2(subRequestDTO_NameCountryCityAndZip);
                        if (results != null) {
                            for (SearchResultDTO result : results) {
                                calculateRateDSBean.calculateRateV2(result, subRequestDTO_NameCountryCityAndZip);
                                resultsSet.add(result);
                                if (resultsSet.size() > maxResults) {
                                    break rateCounterLoop;
                                }
                            }

                           
                        }
                    }
                }

                /*
                 * Search scenario: Name(Like), Countrycode, City, ZipCode
                 */
                if (rateCounter == calculateRateDSBean.getRateAgencyNameLikeZipLikeCountry()) { //Name Like & Zip Like & country
                    RequestDTO subRequestDTO_NameCountryCityAndZip = requestDTOBuilder.build_NameCountrycodeZipLike(requestDTO);
                    if (subRequestDTO_NameCountryCityAndZip != null) {
                        results = searchGinsV2(subRequestDTO_NameCountryCityAndZip);
                        if (results != null) {
                            for (SearchResultDTO result : results) {
                                calculateRateDSBean.calculateRateV2(result, subRequestDTO_NameCountryCityAndZip);
                                resultsSet.add(result);
                                if (resultsSet.size() > maxResults) {
                                    break rateCounterLoop;
                                }
                            }

                            if (results.size() > 0) {
                                isBestRateReached = true;
                            }
                        }
                    }
                }
               
                if (rateCounter == calculateRateDSBean.getRateAgencyNameLikeZipLikeCountry()) { //65 Name Like & City Like & cou,ntry
                    RequestDTO subRequestDTO_NameCountrycodeCity = requestDTOBuilder.build_NameCountrycodeCityLike(requestDTO);
                    if (subRequestDTO_NameCountrycodeCity != null) {
                        results = searchGinsV2(subRequestDTO_NameCountrycodeCity);
                        if (results != null) {
                            for (SearchResultDTO result : results) {
                                calculateRateDSBean.calculateRateV2(result, subRequestDTO_NameCountrycodeCity);
                                resultsSet.add(result);
                                if (resultsSet.size() > maxResults) {
                                    break rateCounterLoop;
                                }
                            }

                            
                        }
                    }
                }
                
                if (rateCounter == calculateRateDSBean.getRateAgencyNameLikeZipLike()) { //Name Like & Zip Like
                    RequestDTO subRequestDTO_NameCountryCityAndZip = requestDTOBuilder.build_NameLikeZipLike(requestDTO);
                    if (subRequestDTO_NameCountryCityAndZip != null) {
                        results = searchGinsV2(subRequestDTO_NameCountryCityAndZip);
                        if (results != null) {
                            for (SearchResultDTO result : results) {
                                calculateRateDSBean.calculateRateV2(result, subRequestDTO_NameCountryCityAndZip);
                                resultsSet.add(result);
                                if (resultsSet.size() > maxResults) {
                                    break rateCounterLoop;
                                }
                            }

                            if (results.size() > 0) {
                                isBestRateReached = true;
                            }
                        }
                    }
                }
               
                if (rateCounter == calculateRateDSBean.getRateAgencyNameLikeZipLike()) { //55 Name Like & City Like
                    RequestDTO subRequestDTO_NameCountrycodeCity = requestDTOBuilder.build_NameLikeCityLike(requestDTO);
                    if (subRequestDTO_NameCountrycodeCity != null) {
                        results = searchGinsV2(subRequestDTO_NameCountrycodeCity);
                        if (results != null) {
                            for (SearchResultDTO result : results) {
                                calculateRateDSBean.calculateRateV2(result, subRequestDTO_NameCountrycodeCity);
                                resultsSet.add(result);
                                if (resultsSet.size() > maxResults) {
                                    break rateCounterLoop;
                                }
                            }

                            
                        }
                    }
                }
                
                if (rateCounter == calculateRateDSBean.getRateAgencyNameLikeCountrycodeCity()) { //70
                    RequestDTO subRequestDTO_NameCountrycodeCity = requestDTOBuilder.build_NameCountrycodeCityStrict(requestDTO);
                    if (subRequestDTO_NameCountrycodeCity != null) {
                        results = searchGinsV2(subRequestDTO_NameCountrycodeCity);
                        if (results != null) {
                            for (SearchResultDTO result : results) {
                                calculateRateDSBean.calculateRateV2(result, subRequestDTO_NameCountrycodeCity);
                                resultsSet.add(result);
                                if (resultsSet.size() > maxResults) {
                                    break rateCounterLoop;
                                }
                            }

                           
                        }
                    }
                }

                /*
                 * Search scenario: Name(Strict), ZC1 to 4
                 */
                if (rateCounter == calculateRateDSBean.getRateAgencyNameStrictZC14()) {
                    RequestDTO subRequestDTO_NameCountrycodeZC14 = requestDTOBuilder.build_NameZC15(requestDTO);
                    if (subRequestDTO_NameCountrycodeZC14 != null) {
                        results = searchGinsV2(subRequestDTO_NameCountrycodeZC14);
                        if (results != null) {
                            for (SearchResultDTO result : results) {
                                calculateRateDSBean.calculateRateV2(result, subRequestDTO_NameCountrycodeZC14);
                                resultsSet.add(result);
                                if (resultsSet.size() > maxResults) {
                                    break rateCounterLoop;
                                }
                            }

                          
                        }
                    }
                }

                /*
                 * Search scenario: Name(Like), ZC1 to 4
                 */
                if (rateCounter == calculateRateDSBean.getRateAgencyNameLikeZC14()) {
                    RequestDTO subRequestDTO_NameCountrycodeZC14 = requestDTOBuilder.build_NameZC15(requestDTO);
                    if (subRequestDTO_NameCountrycodeZC14 != null) {
                        results = searchGinsV2(subRequestDTO_NameCountrycodeZC14);
                        if (results != null) {
                            for (SearchResultDTO result : results) {
                                calculateRateDSBean.calculateRateV2(result, subRequestDTO_NameCountrycodeZC14);
                                resultsSet.add(result);
                                if (resultsSet.size() > maxResults) {
                                    break rateCounterLoop;
                                }
                            }

                            
                        }
                    }
                }
                
                /*
                 * Search scenario: Name(Like), ZC1 to 4, City Like
                 */
                if (rateCounter ==  calculateRateDSBean.getRateAgencyNameLikeCityLikeZC()) {
                    RequestDTO build_NameCityZC = requestDTOBuilder.build_NameCityZC(requestDTO);
                    if (build_NameCityZC != null) {
                        results = searchGinsV2(build_NameCityZC);
                        if (results != null) {
                            for (SearchResultDTO result : results) {
                                calculateRateDSBean.calculateRateV2(result, build_NameCityZC);
                                resultsSet.add(result);
                                if (resultsSet.size() > maxResults) {
                                    break rateCounterLoop;
                                }
                            }

                        }
                    }
                }
                
                /*
                 * Search scenario: Name(Like), ZC1 to 4, Zip Like
                 */
                if (rateCounter == calculateRateDSBean.getRateAgencyNameLikeZipLikeZC()) {
                    RequestDTO build_NameZipZC = requestDTOBuilder.build_NameZipZC(requestDTO);
                    if (build_NameZipZC != null) {
                        results = searchGinsV2(build_NameZipZC);
                        if (results != null) {
                            for (SearchResultDTO result : results) {
                                calculateRateDSBean.calculateRateV2(result, build_NameZipZC);
                                resultsSet.add(result);
                                if (resultsSet.size() > maxResults) {
                                    break rateCounterLoop;
                                }
                            }

                        }
                    }
                }

                /*
                 * Search scenario: Name(Like), ZC1 to 4, Zip Like
                 */
                if (rateCounter == calculateRateDSBean.getRateAgencyNameLikeCityLikeZipLikeZC()) {
                    RequestDTO build_NameCityZipZC = requestDTOBuilder.build_NameCityZipZC(requestDTO);
                    if (build_NameCityZipZC != null) {
                        results = searchGinsV2(build_NameCityZipZC);
                        if (results != null) {
                            for (SearchResultDTO result : results) {
                                calculateRateDSBean.calculateRateV2(result, build_NameCityZipZC);
                                resultsSet.add(result);
                                if (resultsSet.size() > maxResults) {
                                    break rateCounterLoop;
                                }
                            }

                           
                        }
                    }
                }

                /*
                 * Search scenario: Name(Strict), CountryCode, Ident
                 */
                if (rateCounter == calculateRateDSBean.getRateAgencyNameStrictCountrycodeIdent()) {
                    RequestDTO subRequestDTO_NameCountrycodeIdent = requestDTOBuilder.build_NameCountrycodeAndIdent(requestDTO);
                    if (subRequestDTO_NameCountrycodeIdent != null) {
                        results = searchGinsV2(subRequestDTO_NameCountrycodeIdent);
                        if (results != null) {
                            for (SearchResultDTO result : results) {
                                calculateRateDSBean.calculateRateV2(result, subRequestDTO_NameCountrycodeIdent);
                                resultsSet.add(result);
                                if (resultsSet.size() > maxResults) {
                                    break rateCounterLoop;
                                }
                            }

                            
                        }
                    }
                }

                /*
                 * Search scenario: Name(Like), CountryCode, Ident
                 */
                if (rateCounter == calculateRateDSBean.getRateAgencyNameLikeCountrycodeIdent()) {
                    RequestDTO subRequestDTO_NameCountrycodeIdent = requestDTOBuilder.build_NameCountrycodeAndIdent(requestDTO);
                    if (subRequestDTO_NameCountrycodeIdent != null) {
                        results = searchGinsV2(subRequestDTO_NameCountrycodeIdent);
                        if (results != null) {
                            for (SearchResultDTO result : results) {
                                calculateRateDSBean.calculateRateV2(result, subRequestDTO_NameCountrycodeIdent);
                                resultsSet.add(result);
                                if (resultsSet.size() > maxResults) {
                                    break rateCounterLoop;
                                }
                            }

                           
                        }
                    }
                }

                /*
                 * Search scenario: Name(Strict), CountryCode
                 */
                if (rateCounter == calculateRateDSBean.getRateAgencyNameStrictCountrycode()) {
                    RequestDTO subRequestDTO_NameCountrycode = requestDTOBuilder.build_NameStrictCountrycode(requestDTO);
                    if (subRequestDTO_NameCountrycode != null) {
                        results = searchGinsV2(subRequestDTO_NameCountrycode);
                        if (results != null) {
                            for (SearchResultDTO result : results) {
                                calculateRateDSBean.calculateRateV2(result, subRequestDTO_NameCountrycode);
                                resultsSet.add(result);
                                if (resultsSet.size() > maxResults) {
                                    break rateCounterLoop;
                                }
                            }

                           
                        }
                    }
                }

                /*
                 * Search scenario: Name(Like), CountryCode
                 */
                if (rateCounter == calculateRateDSBean.getRateAgencyNameLikeCountrycode()) {
                    RequestDTO subRequestDTO_NameCountrycode = requestDTOBuilder.build_NameLikeCountrycode(requestDTO);
                    if (subRequestDTO_NameCountrycode != null) {
                        results = searchGinsV2(subRequestDTO_NameCountrycode);
                        if (results != null) {
                            for (SearchResultDTO result : results) {
                                calculateRateDSBean.calculateRateV2(result, subRequestDTO_NameCountrycode);
                                resultsSet.add(result);
                                if (resultsSet.size() > maxResults) {
                                    break rateCounterLoop;
                                }
                            }

                           
                        }
                    }
                }
                
                /*
                 * Search scenario: Name(Like), Zip(Like), CountryCode
                 */
                if (rateCounter == calculateRateDSBean.getRateAgencyNameLikeZipLikeCountry()) {
                    RequestDTO subRequestDTO_NameZipCountrycode = requestDTOBuilder.build_NameZipCountrycode(requestDTO);
                    if (subRequestDTO_NameZipCountrycode != null) {
                        results = searchGinsV2(subRequestDTO_NameZipCountrycode);
                        if (results != null) {
                            for (SearchResultDTO result : results) {
                                calculateRateDSBean.calculateRateV2(result, subRequestDTO_NameZipCountrycode);
                                resultsSet.add(result);
                                if (resultsSet.size() > maxResults) {
                                    break rateCounterLoop;
                                }
                            }

                            
                        }
                    }
                }

                /*
                 * Search scenario: Name(Strict)
                 */
                if (rateCounter == calculateRateDSBean.getRateAgencyNameStrict()) {
                    RequestDTO subRequestDTO_Name = requestDTOBuilder.build_Name(requestDTO);
                    if (subRequestDTO_Name != null) {
                        results = searchGinsV2(subRequestDTO_Name);
                        if (results != null) {
                            for (SearchResultDTO result : results) {
                                calculateRateDSBean.calculateRateV2(result, subRequestDTO_Name);
                                resultsSet.add(result);
                                if (resultsSet.size() > maxResults) {
                                    break rateCounterLoop;
                                }
                            }

                            
                        }
                    }
                }
                
                /*
                 * Search scenario: Name(Like) Zip(Like)
                 */
                if (rateCounter == calculateRateDSBean.getRateAgencyNameLikeZipLike()) {
                    RequestDTO subRequestDTO_Name = requestDTOBuilder.build_NameLikeZipLike(requestDTO);
                    if (subRequestDTO_Name != null) {
                        results = searchGinsV2(subRequestDTO_Name);
                        if (results != null) {
                            for (SearchResultDTO result : results) {
                                calculateRateDSBean.calculateRateV2(result, subRequestDTO_Name);
                                resultsSet.add(result);
                                if (resultsSet.size() > maxResults) {
                                    break rateCounterLoop;
                                }
                            }

                           
                        }
                    }
                }

                /*
                 * Search scenario: Name(Like)
                 */
                if (rateCounter == calculateRateDSBean.getRateAgencyNameLike()) {
                    RequestDTO subRequestDTO_Name = requestDTOBuilder.build_Name(requestDTO);
                    if (subRequestDTO_Name != null) {
                        results = searchGinsV2(subRequestDTO_Name);
                        if (results != null) {
                            for (SearchResultDTO result : results) {
                                calculateRateDSBean.calculateRateV2(result, subRequestDTO_Name);
                                resultsSet.add(result);
                                if (resultsSet.size() > maxResults) {
                                    break rateCounterLoop;
                                }
                            }

                            
                        }
                    }
                }

                /*
                 * Search scenario: Phone
                 */
                if (rateCounter == calculateRateDSBean.getRateAgencyPhone()) {
                    RequestDTO subRequestDTO_Phone = requestDTOBuilder.build_Phone(requestDTO);
                    if (subRequestDTO_Phone != null) {
                        results = searchGinsV2(subRequestDTO_Phone);
                        if (results != null) {
                            for (SearchResultDTO result : results) {
                                calculateRateDSBean.calculateRateV2(result, subRequestDTO_Phone);
                                resultsSet.add(result);
                                if (resultsSet.size() > maxResults) {
                                    break rateCounterLoop;
                                }
                            }

                           
                        }
                    }
                }

                /*
                 * Search scenario: Email
                 */
                if (rateCounter == calculateRateDSBean.getRateAgencyEmail()) {
                    RequestDTO subRequestDTO_Email = requestDTOBuilder.build_Email(requestDTO);
                    if (subRequestDTO_Email != null) {
                        results = searchGinsV2(subRequestDTO_Email);
                        if (results != null) {
                            for (SearchResultDTO result : results) {
                                calculateRateDSBean.calculateRateV2(result, subRequestDTO_Email);
                                resultsSet.add(result);
                                if (resultsSet.size() > maxResults) {
                                    break rateCounterLoop;
                                }
                            }

                           
                        }
                    }
                }

                /*
                 * Search scenario: Phone, Ident
                 */
                if (rateCounter == calculateRateDSBean.getRateAgencyPhoneIdent()) {
                    RequestDTO subRequestDTO_PhoneAndIdent = requestDTOBuilder.build_PhoneAndIdent(requestDTO);
                    if (subRequestDTO_PhoneAndIdent != null) {
                        results = searchGinsV2(subRequestDTO_PhoneAndIdent);
                        if (results != null) {
                            for (SearchResultDTO result : results) {
                                calculateRateDSBean.calculateRateV2(result, subRequestDTO_PhoneAndIdent);
                                resultsSet.add(result);
                                if (resultsSet.size() > maxResults) {
                                    break rateCounterLoop;
                                }
                            }

                        }
                    }
                }

                /*
                 * Search scenario: Email, Ident
                 */
                if (rateCounter == calculateRateDSBean.getRateAgencyEmailIdent()) {
                    RequestDTO subRequestDTO_EmailAndIdent = requestDTOBuilder.build_EmailAndIdent(requestDTO);
                    if (subRequestDTO_EmailAndIdent != null) {
                        results = searchGinsV2(subRequestDTO_EmailAndIdent);
                        if (results != null) {
                            for (SearchResultDTO result : results) {
                                calculateRateDSBean.calculateRateV2(result, subRequestDTO_EmailAndIdent);
                                resultsSet.add(result);
                                if (resultsSet.size() > maxResults) {
                                    break rateCounterLoop;
                                }
                            }

                            
                        }
                    }
                }

                /*
                 * Search scenario: Ident
                 */
                if (rateCounter == calculateRateDSBean.getRateAgencyIdent()) {
                    RequestDTO subRequestDTO_Ident = requestDTOBuilder.build_Ident(requestDTO);
                    if (subRequestDTO_Ident != null) {
                        results = searchGinsV2(subRequestDTO_Ident);
                        if (results != null) {
                            for (SearchResultDTO result : results) {
                                calculateRateDSBean.calculateRateV2(result, subRequestDTO_Ident);
                                resultsSet.add(result);
                                if (resultsSet.size() > maxResults) {
                                    break rateCounterLoop;
                                }
                            }

                           
                        }
                    }
                }
            }
        }

        return resultsSet;
    }
	
	/**
	 * Get Agency' gins results
	 * @param requestDTO
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	private List<SearchResultDTO> searchGins(RequestDTO requestDTO) throws BusinessException
	{
		List<SearchResultDTO> results = null;
		buildQueryDSBean.buildQuery(requestDTO);
		results = searchAgencyDAOBean.searchGinsAndTypes(requestDTO);
		return results;
	}
	
    // V2 of searchGins
    private List<SearchResultDTO> searchGinsV2(RequestDTO requestDTO) throws BusinessException, SystemException {
        List<SearchResultDTO> results = null;
        buildQueryDSBean.buildQueryV2(requestDTO);
        results = searchAgencyDAOBean.searchGinsAndTypes(requestDTO);
        return results;
    }
	
	
	
	/*===============================================*/
	/*                  ACCESSORS                    */
	/*===============================================*/

	
	public RequestDTOBuilder getRequestDTOBuilder() {
		return requestDTOBuilder;
	}
	
	
	public void setRequestDTOBuilder(RequestDTOBuilder requestDTOBuilder) {
		this.requestDTOBuilder = requestDTOBuilder;
	}
		
	public SearchAgencyDAO getSearchAgencyDAOBean() {
		return searchAgencyDAOBean;
	}
	
	
	public void setSearchAgencyDAOBean(SearchAgencyDAO searchAgencyDAOBean) {
		this.searchAgencyDAOBean = searchAgencyDAOBean;
	}
	
	
	public CalculateRateDS getCalculateRateDSBean() {
		return calculateRateDSBean;
	}
	
	
	public void setCalculateRateDSBean(CalculateRateDS calculateRateDSBean) {
		this.calculateRateDSBean = calculateRateDSBean;
	}
	
	
	public CheckMandatoryInputsDS getCheckMandatoryInputsDSBean() {
		return checkMandatoryInputsDSBean;
	}
	
	public void setCheckMandatoryInputsDSBean(
			CheckMandatoryInputsDS checkMandatoryInputsDSBean) {
		this.checkMandatoryInputsDSBean = checkMandatoryInputsDSBean;
	}
	
	public BuildQueryDS getBuildQueryDSBean() {
		return buildQueryDSBean;
	}
	
	public void setBuildQueryDSBean(BuildQueryDS buildQueryDSBean) {
		this.buildQueryDSBean = buildQueryDSBean;
	}
	
	public ObtainTokenDS getObtainTokenDSBean() {
		return obtainTokenDSBean;
	}
	
	public void setObtainTokenDSBean(ObtainTokenDS obtainTokenDSBean) {
		this.obtainTokenDSBean = obtainTokenDSBean;
	}


}
