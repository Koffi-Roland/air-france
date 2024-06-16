package com.airfrance.repind.firme.searchfirmonmulticriteria.ds;

import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.firme.searchfirmonmulticriteria.dao.SearchFirmDAO;
import com.airfrance.repind.firme.searchfirmonmulticriteria.dto.*;
import com.airfrance.repind.firme.searchfirmonmulticriteria.dto.builders.RequestDTOBuilder;
import com.airfrance.repind.firme.searchfirmonmulticriteria.dto.builders.ResponseDTOBuilder;
import com.airfrance.repind.service.adresse.internal.TelecomDS;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Domain service class to perform search
 * @author t950700
 *
 */
@Service("FirmeSearchFirmDS")
public class SearchFirmDS extends AbstractDS {

	/*===============================================*/
	/*             INJECTED BEANS                    */
	/*===============================================*/
	
	@Autowired
	private RequestDTOBuilder requestDTOBuilder = null;
	
	
	@Autowired
	private SearchFirmDAO searchFirmDAOBean = null;
	
	@Autowired
	private CalculateRateDS calculateRateDSBean = null;
	
	@Autowired
	private CheckMandatoryInputsDS checkMandatoryInputsDSBean = null;
	
	@Autowired
	private BuildQueryDS buildQueryDSBean = null;
	
	@Autowired
	private ObtainTokenDS obtainTokenDSBean = null;
	
	@Autowired
	private TelecomDS telecomDS;
	
	/*===============================================*/
	/*                   LOGGER                      */
	/*===============================================*/
	private static Log LOGGER  = LogFactory.getLog(SearchFirmDS.class);
	
	/*===============================================*/
	/*               PUBLIC METHODS                  */
	/*===============================================*/
	
	/**
	 * Create DTO beans and call the business method searchFirm (below)
	 */
	public ResponseDTO searchFirm(RequestDTO requestDTO) throws BusinessException {
		LOGGER.info("SearchFirmOnMultiCriteria | NORMALIZING PHONE NUMBER");
		normalizePhoneNumber(requestDTO);
		LOGGER.info("SearchFirmOnMultiCriteria | BUILDING QUERY");
		buildQueryDSBean.buildQuery(requestDTO);
		LOGGER.info("SearchFirmOnMultiCriteria | SEARCH START");
		ResponseDTO responseDTO = executeSearch(requestDTO);
		LOGGER.info("SearchFirmOnMultiCriteria | SEARCH END AND TOKEN HANDLING");
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
		try {
			obtainTokenDSBean.readToken(requestDTO, responseDTO);
		} catch (InvalidParameterException e) {
			LOGGER.error(e);
		}
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
		 * Check mandatory inputs
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
	
	/**
	 * Checks mandatory inputs, process firm searching, and calculate correspondence rate
	 */
	private ResponseDTO executeSearchV2(RequestDTO requestDTO) throws BusinessException 
	{
		ResponseDTO responseDTO = null;
		
		/* 
		 * Check mandatory inputs
		 */
		checkMandatoryInputsDSBean.checkMandatoryInputsV2(requestDTO);
		
		
		/*
		 * Execute the search according to process type
		 */
		if(BuildConditionsDS.isProcessTypeAutoConditionSet(requestDTO))
		{
			responseDTO = executeAutomaticSearchV2(requestDTO);
		}
		if(BuildConditionsDS.isProcessTypeManualConditionSet(requestDTO))
		{
			responseDTO = executeManualSearchV2(requestDTO);
		}
		
		
		return responseDTO;
	}

	
	
	/**
	 * Executes an automatic search: 
	 *         All the search scenarios will be executed until
	 *         the first highest relevance is reached
	 *       
	 *    
	 * @param requestDTO
	 * @return
	 * @throws BusinessException
	 */
	private ResponseDTO executeAutomaticSearch(RequestDTO requestDTO) throws BusinessException
	{
		LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH START");
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
			Set<SearchResultDTO> resultsSet = null;
			
			if(BuildConditionsDS.isFirmTypeAllConditionSet(requestDTO))
			{
				resultsSet = executeAutomaticSearchForAll(requestDTO);
			}
			else if(BuildConditionsDS.isFirmTypeGroupsConditionSet(requestDTO))
			{
				resultsSet = executeAutomaticSearchForGroups(requestDTO);
			}
			else if(BuildConditionsDS.isFirmTypeCompaniesConditionSet(requestDTO))
			{
				resultsSet = executeAutomaticSearchForCompanies(requestDTO);
			}
			else if(BuildConditionsDS.isFirmTypeServicesConditionSet(requestDTO))
			{
				resultsSet = executeAutomaticSearchForServices(requestDTO);
			}
			else if(BuildConditionsDS.isFirmTypeFirmsConditionSet(requestDTO))
			{
				resultsSet = executeAutomaticSearchForFirms(requestDTO);
			}
			
	
			if( resultsSet != null && !resultsSet.isEmpty())
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
						FirmDTO firm = searchFirmDAOBean.readFirmByGin(requestDTO, result.getGin());
			    		if(firm != null)
			    		{
			    			firm.setRelevance(result.getRate());
			    			responseDTO.getFirms().add(firm);
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

	private ResponseDTO executeAutomaticSearchV2(RequestDTO requestDTO) throws BusinessException
	{
		LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH START");
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
			Set<SearchResultDTO> resultsSet = null;
			
			String sFirmType = "A";
			if (BuildConditionsDS.isFirmTypeAllConditionSet(requestDTO))
				sFirmType = "A";
			else if (BuildConditionsDS.isFirmTypeFirmsConditionSet(requestDTO))
				sFirmType = "T";
			else if (BuildConditionsDS.isFirmTypeGroupsConditionSet(requestDTO))
				sFirmType = "G";
			else if (BuildConditionsDS.isFirmTypeCompaniesConditionSet(requestDTO))
				sFirmType = "E";
			else if (BuildConditionsDS.isFirmTypeServicesConditionSet(requestDTO))
				sFirmType = "S";
				
			resultsSet = executeAutomaticSearchV2(requestDTO, sFirmType);				
			
	
			if( resultsSet != null && !resultsSet.isEmpty())
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
						FirmDTO firm = searchFirmDAOBean.readFirmByGin(requestDTO, result.getGin());
			    		if(firm != null)
			    		{
			    			firm.setRelevance(result.getRate());
			    			responseDTO.getFirms().add(firm);
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
				responseDTO.setReturnCode(getNoResultsReturnCode());
				responseDTO.setReturnMessage(getNoResultsReturnMessage());	
			}
		}
		
		return responseDTO;
	}


	/**
	 * Executes an automatic search for firmType == ALL
	 * @param requestDTO
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	private Set<SearchResultDTO> executeAutomaticSearchForAll(RequestDTO requestDTO) throws BusinessException
	{
		LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH ALL");
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
			
			/*
			 * Needed for automatic search
			 * As soon as a best relevance rate
			 *  is reached, the search operation 
			 *  ends
			 */
			boolean isBestRateReached = false;
						
			rateCounterLoop:
			for(int rateCounter = maxRate; rateCounter >= 0; rateCounter -= 1)
			{
				if(isBestRateReached)
				{
					break rateCounterLoop;
				}
				
				/*
				 * GROUPS
				 */
				if(rateCounter == calculateRateDSBean.getRateGroupStrictName())
				{
					List<RequestDTO> subRequestDTO_NameList = requestDTOBuilder.build_Name(requestDTO, "G");
					for(RequestDTO subRequestDTO_Name : subRequestDTO_NameList)
					{
						if(subRequestDTO_Name != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH ALL | GROUP-Name SUBQUERY");
							results = searchGins(subRequestDTO_Name);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_Name);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateGroupLikeName())
				{
					List<RequestDTO> subRequestDTO_NameList = requestDTOBuilder.build_Name(requestDTO, "G");
					for(RequestDTO subRequestDTO_Name : subRequestDTO_NameList)
					{
						if(subRequestDTO_Name != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH ALL | GROUP-Name SUBQUERY");
							results = searchGins(subRequestDTO_Name);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_Name);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateGroupCountrycode())
				{
					RequestDTO subRequestDTO_Countrycode = requestDTOBuilder.build_Countrycode(requestDTO, "G");
					if(subRequestDTO_Countrycode != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH ALL | SERVICE-Countrycode SUBQUERY");
						results = searchGins(subRequestDTO_Countrycode);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_Countrycode);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateGroupZC5())
				{
					RequestDTO subRequestDTO_ZC5 = requestDTOBuilder.build_ZC5(requestDTO, "G");
					if(subRequestDTO_ZC5 != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH ALL | SERVICE-ZC5 SUBQUERY");
						results = searchGins(subRequestDTO_ZC5);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_ZC5);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
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
				 * COMPANIES
				 */
				if(rateCounter == calculateRateDSBean.getRateCompanyStrictName() || rateCounter == calculateRateDSBean.getRateCompanyLikeName())
				{
					List<RequestDTO> subRequestDTO_NameList = requestDTOBuilder.build_Name(requestDTO, "E");
					for(RequestDTO subRequestDTO_Name : subRequestDTO_NameList)
					{
						if(subRequestDTO_Name != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH ALL | COMPAGNY-Name SUBQUERY");
							results = searchGins(subRequestDTO_Name);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_Name);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				if(rateCounter == calculateRateDSBean.getRateCompanyStrictNameCountrycodePhone())
				{
					List<RequestDTO> subRequestDTO_NameAndPhoneList = requestDTOBuilder.build_NameCountrycodeAndPhone(requestDTO, "E");
					for(RequestDTO subRequestDTO_NameAndPhone : subRequestDTO_NameAndPhoneList)
					{
						if(subRequestDTO_NameAndPhone != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH ALL | COMPANY-NameAndPhone SUBQUERY");
							results = searchGins(subRequestDTO_NameAndPhone);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameAndPhone);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateCompanyStrictNameCountrycodeEmail())
				{
					List<RequestDTO> subRequestDTO_NameAndEmailList = requestDTOBuilder.build_NameCountrycodeAndEmail(requestDTO, "E");
					for(RequestDTO subRequestDTO_NameAndEmail : subRequestDTO_NameAndEmailList)
					{
						if(subRequestDTO_NameAndEmail != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH ALL | COMPANY-NameCountrycodeAndEmail SUBQUERY");
							results = searchGins(subRequestDTO_NameAndEmail);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameAndEmail);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				if(rateCounter == calculateRateDSBean.getRateCompanyStrictNameCountrycodeNbandstreetCityZipcode())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeNbandstreetCityZipcodeList = requestDTOBuilder.build_NameCountrycodeNbandstreetCityZipcode(requestDTO, "E");
					for(RequestDTO subRequestDTO_NameCountrycodeNbandstreetCityZipcode : subRequestDTO_NameCountrycodeNbandstreetCityZipcodeList)
					{
						if(subRequestDTO_NameCountrycodeNbandstreetCityZipcode != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH ALL | COMPANY-NameCountrycodeNbandstreetCityZipcode SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycodeNbandstreetCityZipcode);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycodeNbandstreetCityZipcode);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				if(rateCounter == calculateRateDSBean.getRateCompanyStrictNameCountrycodeNbandstreetCity())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeNbandstreetCityList = requestDTOBuilder.build_NameCountrycodeNbandstreetCity(requestDTO, "E");
					for(RequestDTO subRequestDTO_NameCountrycodeNbandstreetCity : subRequestDTO_NameCountrycodeNbandstreetCityList)
					{
						if(subRequestDTO_NameCountrycodeNbandstreetCity != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH ALL | COMPANY-NameCountrycodeNbandstreetCity SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycodeNbandstreetCity);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycodeNbandstreetCity);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				if(rateCounter == calculateRateDSBean.getRateCompanyStrictNameCountrycodeCityZipcode())
				{
					List<RequestDTO> subRequestDTO_NameCountryCityAndZipList = requestDTOBuilder.build_NameCountrycodeCityAndZip(requestDTO, "E");
					for(RequestDTO subRequestDTO_NameCountryCityAndZip : subRequestDTO_NameCountryCityAndZipList)
					{
						if(subRequestDTO_NameCountryCityAndZip != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH ALL | COMPANY-NameCountryCityAndZip SUBQUERY");
							results = searchGins(subRequestDTO_NameCountryCityAndZip);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountryCityAndZip);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				if(rateCounter == calculateRateDSBean.getRateCompanyStrictNameCountrycodeIdent())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeIdentList = requestDTOBuilder.build_NameCountryAndIdent(requestDTO, "E");
					for(RequestDTO subRequestDTO_NameCountrycodeIdent : subRequestDTO_NameCountrycodeIdentList)
					{
						if(subRequestDTO_NameCountrycodeIdent != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH ALL | COMPANY-NameCountrycodeIdent SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycodeIdent);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycodeIdent);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateCompanyCountrycode())
				{
					RequestDTO subRequestDTO_Countrycode = requestDTOBuilder.build_Countrycode(requestDTO, "E");
					if(subRequestDTO_Countrycode != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH ALL | SERVICE-Countrycode SUBQUERY");
						results = searchGins(subRequestDTO_Countrycode);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_Countrycode);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateCompanyZC5())
				{
					RequestDTO subRequestDTO_ZC5 = requestDTOBuilder.build_ZC5(requestDTO, "E");
					if(subRequestDTO_ZC5 != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH ALL | SERVICE-ZC5 SUBQUERY");
						results = searchGins(subRequestDTO_ZC5);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_ZC5);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
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
				 * SERVICES
				 */
				if(rateCounter == calculateRateDSBean.getRateServiceStrictName() || rateCounter == calculateRateDSBean.getRateServiceLikeName())
				{
					List<RequestDTO> subRequestDTO_NameList = requestDTOBuilder.build_Name(requestDTO, "S");
					for(RequestDTO subRequestDTO_Name : subRequestDTO_NameList)
					{
						if(subRequestDTO_Name != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH ALL | SERVICE-Name SUBQUERY");
							results = searchGins(subRequestDTO_Name);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_Name);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				if(rateCounter == calculateRateDSBean.getRateServiceStrictNameCountrycodePhone())
				{
					List<RequestDTO> subRequestDTO_NameAndPhoneList = requestDTOBuilder.build_NameCountrycodeAndPhone(requestDTO, "S");
					for(RequestDTO subRequestDTO_NameAndPhone : subRequestDTO_NameAndPhoneList)
					{
						if(subRequestDTO_NameAndPhone != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH ALL | SERVICE-NameAndPhone SUBQUERY");
							results = searchGins(subRequestDTO_NameAndPhone);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameAndPhone);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateServiceLikeNameCountrycodePhone())
				{
					List<RequestDTO> subRequestDTO_NameAndPhoneList = requestDTOBuilder.build_NameCountrycodeAndPhone(requestDTO, "S");
					for(RequestDTO subRequestDTO_NameAndPhone : subRequestDTO_NameAndPhoneList)
					{
						if(subRequestDTO_NameAndPhone != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH ALL | SERVICE-NameAndPhone SUBQUERY");
							results = searchGins(subRequestDTO_NameAndPhone);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameAndPhone);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateServiceStrictNameCountrycodeEmail())
				{
					List<RequestDTO> subRequestDTO_NameAndEmailList = requestDTOBuilder.build_NameCountrycodeAndEmail(requestDTO, "S");
					for(RequestDTO subRequestDTO_NameAndEmail : subRequestDTO_NameAndEmailList)
					{
						if(subRequestDTO_NameAndEmail != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH ALL | SERVICE-NameAndEmail SUBQUERY");
							results = searchGins(subRequestDTO_NameAndEmail);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameAndEmail);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateServiceLikeNameCountrycodeEmail())
				{
					List<RequestDTO> subRequestDTO_NameAndEmailList = requestDTOBuilder.build_NameCountrycodeAndEmail(requestDTO, "S");
					for(RequestDTO subRequestDTO_NameAndEmail : subRequestDTO_NameAndEmailList)
					{
						if(subRequestDTO_NameAndEmail != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH ALL | SERVICE-NameAndEmail SUBQUERY");
							results = searchGins(subRequestDTO_NameAndEmail);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameAndEmail);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateServiceStrictNameCountrycodeNbandstreetCityZipcode())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeNbandstreetCityZipcodeList = requestDTOBuilder.build_NameCountrycodeNbandstreetCityZipcode(requestDTO, "S");
					for(RequestDTO subRequestDTO_NameCountrycodeNbandstreetCityZipcode : subRequestDTO_NameCountrycodeNbandstreetCityZipcodeList)
					{
						if(subRequestDTO_NameCountrycodeNbandstreetCityZipcode != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH ALL | SERVICE-NameCountrycodeNbandstreetCityZipcode SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycodeNbandstreetCityZipcode);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycodeNbandstreetCityZipcode);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateServiceLikeNameCountrycodeNbandstreetCityZipcode())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeNbandstreetCityZipcodeList = requestDTOBuilder.build_NameCountrycodeNbandstreetCityZipcode(requestDTO, "S");
					for(RequestDTO subRequestDTO_NameCountrycodeNbandstreetCityZipcode : subRequestDTO_NameCountrycodeNbandstreetCityZipcodeList)
					{
						if(subRequestDTO_NameCountrycodeNbandstreetCityZipcode != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH ALL | SERVICE-NameCountrycodeNbandstreetCityZipcode SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycodeNbandstreetCityZipcode);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycodeNbandstreetCityZipcode);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateServiceStrictNameCountrycodeNbandstreetCity())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeNbandstreetCityList = requestDTOBuilder.build_NameCountrycodeNbandstreetCity(requestDTO, "S");
					for(RequestDTO subRequestDTO_NameCountrycodeNbandstreetCity : subRequestDTO_NameCountrycodeNbandstreetCityList)
					{
						if(subRequestDTO_NameCountrycodeNbandstreetCity != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH ALL | SERVICE-NameCountrycodeNbandstreetCity SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycodeNbandstreetCity);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycodeNbandstreetCity);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateServiceLikeNameCountrycodeNbandstreetCity())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeNbandstreetCityList = requestDTOBuilder.build_NameCountrycodeNbandstreetCity(requestDTO, "S");
					for(RequestDTO subRequestDTO_NameCountrycodeNbandstreetCity : subRequestDTO_NameCountrycodeNbandstreetCityList)
					{
						if(subRequestDTO_NameCountrycodeNbandstreetCity != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH ALL | SERVICE-NameCountrycodeNbandstreetCity SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycodeNbandstreetCity);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycodeNbandstreetCity);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateServiceStrictNameCountrycodeCityZipcode())
				{
					List<RequestDTO> subRequestDTO_NameCountryCityAndZipList = requestDTOBuilder.build_NameCountrycodeCityAndZip(requestDTO, "S");
					for(RequestDTO subRequestDTO_NameCountryCityAndZip : subRequestDTO_NameCountryCityAndZipList)
					{
						if(subRequestDTO_NameCountryCityAndZip != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH ALL | SERVICE-NameCountryCityAndZip SUBQUERY");
							results = searchGins(subRequestDTO_NameCountryCityAndZip);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountryCityAndZip);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateServiceLikeNameCountrycodeCityZipcode())
				{
					List<RequestDTO> subRequestDTO_NameCountryCityAndZipList = requestDTOBuilder.build_NameCountrycodeCityAndZip(requestDTO, "S");
					for(RequestDTO subRequestDTO_NameCountryCityAndZip : subRequestDTO_NameCountryCityAndZipList)
					{
						if(subRequestDTO_NameCountryCityAndZip != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH ALL | SERVICE-NameCountryCityAndZip SUBQUERY");
							results = searchGins(subRequestDTO_NameCountryCityAndZip);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountryCityAndZip);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateServicePhone())
				{
					RequestDTO subRequestDTO_Phone = requestDTOBuilder.build_Phone(requestDTO, "S");
					if(subRequestDTO_Phone != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH ALL | SERVICE-Phone SUBQUERY");
						results = searchGins(subRequestDTO_Phone);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_Phone);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
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
				if(rateCounter == calculateRateDSBean.getRateServiceEmail())
				{
					RequestDTO subRequestDTO_Email = requestDTOBuilder.build_Email(requestDTO, "S");
					if(subRequestDTO_Email != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH ALL | SERVICE-Email SUBQUERY");
						results = searchGins(subRequestDTO_Email);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_Email);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
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
				if(rateCounter == calculateRateDSBean.getRateServicePhoneIdent())
				{
					RequestDTO subRequestDTO_PhoneAndIdent = requestDTOBuilder.build_PhoneAndIdent(requestDTO, "S");
					if(subRequestDTO_PhoneAndIdent != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH ALL | SERVICE-PhoneAndIdent SUBQUERY");
						results = searchGins(subRequestDTO_PhoneAndIdent);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_PhoneAndIdent);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
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
				if(rateCounter == calculateRateDSBean.getRateServiceEmailIdent())
				{
					RequestDTO subRequestDTO_EmailAndIdent = requestDTOBuilder.build_EmailAndIdent(requestDTO, "S");
					if(subRequestDTO_EmailAndIdent != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH ALL | SERVICE-EmailAndIdent SUBQUERY");
						results = searchGins(subRequestDTO_EmailAndIdent);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_EmailAndIdent);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
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
				if(rateCounter == calculateRateDSBean.getRateServiceIdent())
				{
					RequestDTO subRequestDTO_Ident = requestDTOBuilder.build_Ident(requestDTO, "S");
					if(subRequestDTO_Ident != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH ALL | SERVICE-Ident SUBQUERY");
						results = searchGins(subRequestDTO_Ident);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_Ident);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateServiceCountrycode())
				{
					RequestDTO subRequestDTO_Countrycode = requestDTOBuilder.build_Countrycode(requestDTO, "S");
					if(subRequestDTO_Countrycode != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH ALL | SERVICE-Countrycode SUBQUERY");
						results = searchGins(subRequestDTO_Countrycode);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_Countrycode);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateServiceZC5())
				{
					RequestDTO subRequestDTO_ZC5 = requestDTOBuilder.build_ZC5(requestDTO, "S");
					if(subRequestDTO_ZC5 != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH ALL | SERVICE-ZC5 SUBQUERY");
						results = searchGins(subRequestDTO_ZC5);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_ZC5);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
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
				 * FIRMS
				 */
				if(rateCounter == calculateRateDSBean.getRateFirmStrictName() || rateCounter == calculateRateDSBean.getRateFirmLikeName())
				{
					List<RequestDTO> subRequestDTO_NameList = requestDTOBuilder.build_Name(requestDTO, "T");
					for(RequestDTO subRequestDTO_Name : subRequestDTO_NameList)
					{
						if(subRequestDTO_Name != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH ALL | FIRM-Name SUBQUERY");
							results = searchGins(subRequestDTO_Name);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_Name);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				if(rateCounter == calculateRateDSBean.getRateFirmStrictNameCountrycodePhone())
				{
					List<RequestDTO> subRequestDTO_NameAndPhoneList = requestDTOBuilder.build_NameCountrycodeAndPhone(requestDTO, "T");
					for(RequestDTO subRequestDTO_NameAndPhone : subRequestDTO_NameAndPhoneList)
					{
						if(subRequestDTO_NameAndPhone != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH ALL | FIRM-NameAndPhone SUBQUERY");
							results = searchGins(subRequestDTO_NameAndPhone);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameAndPhone);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateFirmLikeNameCountrycodePhone())
				{
					List<RequestDTO> subRequestDTO_NameAndPhoneList = requestDTOBuilder.build_NameCountrycodeAndPhone(requestDTO, "T");
					for(RequestDTO subRequestDTO_NameAndPhone : subRequestDTO_NameAndPhoneList)
					{
						if(subRequestDTO_NameAndPhone != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH ALL | FIRM-NameAndPhone SUBQUERY");
							results = searchGins(subRequestDTO_NameAndPhone);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameAndPhone);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateFirmStrictNameCountrycodeEmail())
				{
					List<RequestDTO> subRequestDTO_NameAndEmailList = requestDTOBuilder.build_NameCountrycodeAndEmail(requestDTO, "T");
					for(RequestDTO subRequestDTO_NameAndEmail : subRequestDTO_NameAndEmailList)
					{
						if(subRequestDTO_NameAndEmail != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH ALL | FIRM-NameCountrycodeAndEmail SUBQUERY");
							results = searchGins(subRequestDTO_NameAndEmail);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameAndEmail);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateFirmLikeNameCountrycodeEmail())
				{
					List<RequestDTO> subRequestDTO_NameAndEmailList = requestDTOBuilder.build_NameCountrycodeAndEmail(requestDTO, "T");
					for(RequestDTO subRequestDTO_NameAndEmail : subRequestDTO_NameAndEmailList)
					{
						if(subRequestDTO_NameAndEmail != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH ALL | FIRM-NameCountrycodeAndEmail SUBQUERY");
							results = searchGins(subRequestDTO_NameAndEmail);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameAndEmail);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateFirmStrictNameCountrycodeNbandstreetCityZipcode())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeNbandstreetCityZipcodeList = requestDTOBuilder.build_NameCountrycodeNbandstreetCityZipcode(requestDTO, "T");
					for(RequestDTO subRequestDTO_NameCountrycodeNbandstreetCityZipcode : subRequestDTO_NameCountrycodeNbandstreetCityZipcodeList)
					{
						if(subRequestDTO_NameCountrycodeNbandstreetCityZipcode != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH ALL | FIRM-NameCountrycodeNbandstreetCityZipcode SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycodeNbandstreetCityZipcode);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycodeNbandstreetCityZipcode);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateFirmLikeNameCountrycodeNbandstreetCityZipcode())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeNbandstreetCityZipcodeList = requestDTOBuilder.build_NameCountrycodeNbandstreetCityZipcode(requestDTO, "T");
					for(RequestDTO subRequestDTO_NameCountrycodeNbandstreetCityZipcode : subRequestDTO_NameCountrycodeNbandstreetCityZipcodeList)
					{
						if(subRequestDTO_NameCountrycodeNbandstreetCityZipcode != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH ALL | FIRM-NameCountrycodeNbandstreetCityZipcode SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycodeNbandstreetCityZipcode);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycodeNbandstreetCityZipcode);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateFirmStrictNameCountrycodeNbandstreetCity())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeNbandstreetCityList = requestDTOBuilder.build_NameCountrycodeNbandstreetCity(requestDTO, "T");
					for(RequestDTO subRequestDTO_NameCountrycodeNbandstreetCity : subRequestDTO_NameCountrycodeNbandstreetCityList)
					{
						if(subRequestDTO_NameCountrycodeNbandstreetCity != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH ALL | FIRM-NameCountrycodeNbandstreetCity SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycodeNbandstreetCity);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycodeNbandstreetCity);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateFirmLikeNameCountrycodeNbandstreetCity())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeNbandstreetCityList = requestDTOBuilder.build_NameCountrycodeNbandstreetCity(requestDTO, "T");
					for(RequestDTO subRequestDTO_NameCountrycodeNbandstreetCity : subRequestDTO_NameCountrycodeNbandstreetCityList)
					{
						if(subRequestDTO_NameCountrycodeNbandstreetCity != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH ALL | FIRM-NameCountrycodeNbandstreetCity SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycodeNbandstreetCity);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycodeNbandstreetCity);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateFirmStrictNameCountrycodeCityZipcode())
				{
					List<RequestDTO> subRequestDTO_NameCountryCityAndZipList = requestDTOBuilder.build_NameCountrycodeCityAndZip(requestDTO, "T");
					for(RequestDTO subRequestDTO_NameCountryCityAndZip : subRequestDTO_NameCountryCityAndZipList)
					{
						if(subRequestDTO_NameCountryCityAndZip != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH ALL | FIRM-NameCountryCityAndZip SUBQUERY");
							results = searchGins(subRequestDTO_NameCountryCityAndZip);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountryCityAndZip);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateFirmLikeNameCountrycodeCityZipcode())
				{
					List<RequestDTO> subRequestDTO_NameCountryCityAndZipList = requestDTOBuilder.build_NameCountrycodeCityAndZip(requestDTO, "T");
					for(RequestDTO subRequestDTO_NameCountryCityAndZip : subRequestDTO_NameCountryCityAndZipList)
					{
						if(subRequestDTO_NameCountryCityAndZip != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH ALL | FIRM-NameCountryCityAndZip SUBQUERY");
							results = searchGins(subRequestDTO_NameCountryCityAndZip);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountryCityAndZip);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateFirmStrictNameCountrycodeZC14())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeZC14List = requestDTOBuilder.build_NameCountrycodeZC14(requestDTO, "T");
					for(RequestDTO subRequestDTO_NameCountrycodeZC14 : subRequestDTO_NameCountrycodeZC14List)
					{
						if(subRequestDTO_NameCountrycodeZC14 != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH ALL | FIRM-NameCountrycodeZC14 SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycodeZC14);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycodeZC14);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateFirmLikeNameCountrycodeZC14())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeZC14List = requestDTOBuilder.build_NameCountrycodeZC14(requestDTO, "T");
					for(RequestDTO subRequestDTO_NameCountrycodeZC14 : subRequestDTO_NameCountrycodeZC14List)
					{
						if(subRequestDTO_NameCountrycodeZC14 != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH ALL | FIRM-NameCountrycodeZC14 SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycodeZC14);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycodeZC14);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateFirmStrictNameCountrycodeZC15())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeZC15List = requestDTOBuilder.build_NameCountrycodeZC15(requestDTO, "T");
					for(RequestDTO subRequestDTO_NameCountrycodeZC15 : subRequestDTO_NameCountrycodeZC15List)
					{
						if(subRequestDTO_NameCountrycodeZC15 != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH ALL | FIRM-NameCountrycodeZC15 SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycodeZC15);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycodeZC15);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateFirmLikeNameCountrycodeZC15())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeZC15List = requestDTOBuilder.build_NameCountrycodeZC15(requestDTO, "T");
					for(RequestDTO subRequestDTO_NameCountrycodeZC15 : subRequestDTO_NameCountrycodeZC15List)
					{
						if(subRequestDTO_NameCountrycodeZC15 != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH ALL | FIRM-NameCountrycodeZC15 SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycodeZC15);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycodeZC15);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateFirmStrictNameCountrycodeIdent())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeIdentList = requestDTOBuilder.build_NameCountryAndIdent(requestDTO, "T");
					for(RequestDTO subRequestDTO_NameCountrycodeIdent : subRequestDTO_NameCountrycodeIdentList)
					{
						if(subRequestDTO_NameCountrycodeIdent != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH ALL | FIRM-NameCountrycodeIdent SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycodeIdent);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycodeIdent);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateFirmLikeNameCountrycodeIdent())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeIdentList = requestDTOBuilder.build_NameCountryAndIdent(requestDTO, "T");
					for(RequestDTO subRequestDTO_NameCountrycodeIdent : subRequestDTO_NameCountrycodeIdentList)
					{
						if(subRequestDTO_NameCountrycodeIdent != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH ALL | FIRM-NameCountrycodeIdent SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycodeIdent);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycodeIdent);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateFirmStrictNameCountrycode())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeList = requestDTOBuilder.build_NameCountry(requestDTO, "T");
					for(RequestDTO subRequestDTO_NameCountrycode : subRequestDTO_NameCountrycodeList)
					{
						if(subRequestDTO_NameCountrycode != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH ALL | FIRM-NameCountrycode SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycode);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycode);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateFirmLikeNameCountrycode())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeList = requestDTOBuilder.build_NameCountry(requestDTO, "T");
					for(RequestDTO subRequestDTO_NameCountrycode : subRequestDTO_NameCountrycodeList)
					{
						if(subRequestDTO_NameCountrycode != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH ALL | FIRM-NameCountrycode SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycode);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycode);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateFirmPhone())
				{
					RequestDTO subRequestDTO_Phone = requestDTOBuilder.build_Phone(requestDTO, "T");
					if(subRequestDTO_Phone != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH ALL | FIRM-Phone SUBQUERY");
						results = searchGins(subRequestDTO_Phone);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_Phone);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
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
				if(rateCounter == calculateRateDSBean.getRateFirmEmail())
				{
					RequestDTO subRequestDTO_Email = requestDTOBuilder.build_Email(requestDTO, "T");
					if(subRequestDTO_Email != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH ALL | FIRM-Email SUBQUERY");
						results = searchGins(subRequestDTO_Email);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_Email);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
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
				if(rateCounter == calculateRateDSBean.getRateFirmPhoneIdent())
				{
					RequestDTO subRequestDTO_PhoneAndIdent = requestDTOBuilder.build_PhoneAndIdent(requestDTO, "T");
					if(subRequestDTO_PhoneAndIdent != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH ALL | FIRM-PhoneAndIdent SUBQUERY");
						results = searchGins(subRequestDTO_PhoneAndIdent);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_PhoneAndIdent);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
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
				if(rateCounter == calculateRateDSBean.getRateFirmEmailIdent())
				{
					RequestDTO subRequestDTO_EmailAndIdent = requestDTOBuilder.build_EmailAndIdent(requestDTO, "T");
					if(subRequestDTO_EmailAndIdent != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH ALL | FIRM-EmailAndIdent SUBQUERY");
						results = searchGins(subRequestDTO_EmailAndIdent);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_EmailAndIdent);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
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
				if(rateCounter == calculateRateDSBean.getRateFirmIdent())
				{
					RequestDTO subRequestDTO_Ident = requestDTOBuilder.build_Ident(requestDTO, "T");
					if(subRequestDTO_Ident != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH ALL | FIRM-Ident SUBQUERY");
						results = searchGins(subRequestDTO_Ident);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_Ident);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateFirmCountrycode())
				{
					RequestDTO subRequestDTO_Countrycode = requestDTOBuilder.build_Countrycode(requestDTO, "T");
					if(subRequestDTO_Countrycode != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH ALL | SERVICE-Countrycode SUBQUERY");
						results = searchGins(subRequestDTO_Countrycode);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_Countrycode);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateFirmZC5())
				{
					RequestDTO subRequestDTO_ZC5 = requestDTOBuilder.build_ZC5(requestDTO, "T");
					if(subRequestDTO_ZC5 != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH ALL | SERVICE-ZC5 SUBQUERY");
						results = searchGins(subRequestDTO_ZC5);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_ZC5);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
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

	
	/**
	 * Executes an automatic search for firms
	 * @param requestDTO
	 * @return
	 * @throws BusinessException
	 */
	private Set<SearchResultDTO> executeAutomaticSearchForFirms(RequestDTO requestDTO) throws BusinessException
	{
		LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH FIRMS");
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
			int maxRate = calculateRateDSBean.getMaxFirmRate();
						
			rateCounterLoop:
			for(int rateCounter = maxRate; rateCounter >= 0; rateCounter -= 1)
			{
				if(isBestRateReached)
				{
					break rateCounterLoop;
				}

				if(rateCounter == calculateRateDSBean.getRateFirmStrictName() || rateCounter == calculateRateDSBean.getRateFirmLikeName())
				{
					List<RequestDTO> subRequestDTO_NameList = requestDTOBuilder.build_Name(requestDTO, "T");
					for(RequestDTO subRequestDTO_Name : subRequestDTO_NameList)
					{
						if(subRequestDTO_Name != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH FIRMS | FIRM-Name SUBQUERY");
							results = searchGins(subRequestDTO_Name);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_Name);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				if(rateCounter == calculateRateDSBean.getRateFirmStrictNameCountrycodePhone())
				{
					List<RequestDTO> subRequestDTO_NameAndPhoneList = requestDTOBuilder.build_NameCountrycodeAndPhone(requestDTO, "T");
					for(RequestDTO subRequestDTO_NameAndPhone : subRequestDTO_NameAndPhoneList)
					{
						if(subRequestDTO_NameAndPhone != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH FIRMS | NameAndPhone SUBQUERY");
							results = searchGins(subRequestDTO_NameAndPhone);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameAndPhone);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateFirmLikeNameCountrycodePhone())
				{
					List<RequestDTO> subRequestDTO_NameAndPhoneList = requestDTOBuilder.build_NameCountrycodeAndPhone(requestDTO, "T");
					for(RequestDTO subRequestDTO_NameAndPhone : subRequestDTO_NameAndPhoneList)
					{
						if(subRequestDTO_NameAndPhone != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH FIRMS | NameAndPhone SUBQUERY");
							results = searchGins(subRequestDTO_NameAndPhone);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameAndPhone);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateFirmStrictNameCountrycodeEmail())
				{
					List<RequestDTO> subRequestDTO_NameAndEmailList = requestDTOBuilder.build_NameCountrycodeAndEmail(requestDTO, "T");
					for(RequestDTO subRequestDTO_NameAndEmail : subRequestDTO_NameAndEmailList)
					{
						if(subRequestDTO_NameAndEmail != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH FIRMS | NameAndEmail SUBQUERY");
							results = searchGins(subRequestDTO_NameAndEmail);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameAndEmail);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateFirmLikeNameCountrycodeEmail())
				{
					List<RequestDTO> subRequestDTO_NameAndEmailList = requestDTOBuilder.build_NameCountrycodeAndEmail(requestDTO, "T");
					for(RequestDTO subRequestDTO_NameAndEmail : subRequestDTO_NameAndEmailList)
					{
						if(subRequestDTO_NameAndEmail != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH FIRMS | NameAndEmail SUBQUERY");
							results = searchGins(subRequestDTO_NameAndEmail);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameAndEmail);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateFirmStrictNameCountrycodeNbandstreetCityZipcode())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeNbandstreetCityZipcodeList = requestDTOBuilder.build_NameCountrycodeNbandstreetCityZipcode(requestDTO, "T");
					for(RequestDTO subRequestDTO_NameCountrycodeNbandstreetCityZipcode : subRequestDTO_NameCountrycodeNbandstreetCityZipcodeList)
					{
						if(subRequestDTO_NameCountrycodeNbandstreetCityZipcode != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH FIRMS | NameCountrycodeNbandstreetCityZipcode SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycodeNbandstreetCityZipcode);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycodeNbandstreetCityZipcode);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateFirmLikeNameCountrycodeNbandstreetCityZipcode())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeNbandstreetCityZipcodeList = requestDTOBuilder.build_NameCountrycodeNbandstreetCityZipcode(requestDTO, "T");
					for(RequestDTO subRequestDTO_NameCountrycodeNbandstreetCityZipcode : subRequestDTO_NameCountrycodeNbandstreetCityZipcodeList)
					{
						if(subRequestDTO_NameCountrycodeNbandstreetCityZipcode != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH FIRMS | NameCountrycodeNbandstreetCityZipcode SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycodeNbandstreetCityZipcode);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycodeNbandstreetCityZipcode);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateFirmStrictNameCountrycodeNbandstreetCity())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeNbandstreetCityList = requestDTOBuilder.build_NameCountrycodeNbandstreetCity(requestDTO, "T");
					for(RequestDTO subRequestDTO_NameCountrycodeNbandstreetCity : subRequestDTO_NameCountrycodeNbandstreetCityList)
					{
						if(subRequestDTO_NameCountrycodeNbandstreetCity != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH FIRMS | NameCountrycodeNbandstreetCity SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycodeNbandstreetCity);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycodeNbandstreetCity);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateFirmLikeNameCountrycodeNbandstreetCity())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeNbandstreetCityList = requestDTOBuilder.build_NameCountrycodeNbandstreetCity(requestDTO, "T");
					for(RequestDTO subRequestDTO_NameCountrycodeNbandstreetCity : subRequestDTO_NameCountrycodeNbandstreetCityList)
					{
						if(subRequestDTO_NameCountrycodeNbandstreetCity != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH FIRMS | NameCountrycodeNbandstreetCity SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycodeNbandstreetCity);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycodeNbandstreetCity);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateFirmStrictNameCountrycodeCityZipcode())
				{
					List<RequestDTO> subRequestDTO_NameCountryCityAndZipList = requestDTOBuilder.build_NameCountrycodeCityAndZip(requestDTO, "T");
					for(RequestDTO subRequestDTO_NameCountryCityAndZip : subRequestDTO_NameCountryCityAndZipList)
					{
						if(subRequestDTO_NameCountryCityAndZip != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH FIRMS | NameCountryCityAndZip SUBQUERY");
							results = searchGins(subRequestDTO_NameCountryCityAndZip);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountryCityAndZip);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateFirmLikeNameCountrycodeCityZipcode())
				{
					List<RequestDTO> subRequestDTO_NameCountryCityAndZipList = requestDTOBuilder.build_NameCountrycodeCityAndZip(requestDTO, "T");
					for(RequestDTO subRequestDTO_NameCountryCityAndZip : subRequestDTO_NameCountryCityAndZipList)
					{
						if(subRequestDTO_NameCountryCityAndZip != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH FIRMS | NameCountryCityAndZip SUBQUERY");
							results = searchGins(subRequestDTO_NameCountryCityAndZip);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountryCityAndZip);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateFirmStrictNameCountrycodeZC14())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeZC14List = requestDTOBuilder.build_NameCountrycodeZC14(requestDTO, "T");
					for(RequestDTO subRequestDTO_NameCountrycodeZC14 : subRequestDTO_NameCountrycodeZC14List)
					{
						if(subRequestDTO_NameCountrycodeZC14 != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH FIRMS | NameCountrycodeZC14 SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycodeZC14);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycodeZC14);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateFirmLikeNameCountrycodeZC14())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeZC14List = requestDTOBuilder.build_NameCountrycodeZC14(requestDTO, "T");
					for(RequestDTO subRequestDTO_NameCountrycodeZC14 : subRequestDTO_NameCountrycodeZC14List)
					{
						if(subRequestDTO_NameCountrycodeZC14 != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH FIRMS | NameCountrycodeZC14 SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycodeZC14);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycodeZC14);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateFirmStrictNameCountrycodeZC15())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeZC15List = requestDTOBuilder.build_NameCountrycodeZC15(requestDTO, "T");
					for(RequestDTO subRequestDTO_NameCountrycodeZC15 : subRequestDTO_NameCountrycodeZC15List)
					{
						if(subRequestDTO_NameCountrycodeZC15 != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH FIRMS | NameCountrycodeZC15 SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycodeZC15);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycodeZC15);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateFirmLikeNameCountrycodeZC15())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeZC15List = requestDTOBuilder.build_NameCountrycodeZC15(requestDTO, "T");
					for(RequestDTO subRequestDTO_NameCountrycodeZC15 : subRequestDTO_NameCountrycodeZC15List)
					{
						if(subRequestDTO_NameCountrycodeZC15 != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH FIRMS | NameCountrycodeZC15 SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycodeZC15);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycodeZC15);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateFirmStrictNameCountrycodeIdent())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeIdentList = requestDTOBuilder.build_NameCountryAndIdent(requestDTO, "T");
					for(RequestDTO subRequestDTO_NameCountrycodeIdent : subRequestDTO_NameCountrycodeIdentList)
					{
						if(subRequestDTO_NameCountrycodeIdent != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH FIRMS | NameCountrycodeIdent SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycodeIdent);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycodeIdent);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateFirmLikeNameCountrycodeIdent())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeIdentList = requestDTOBuilder.build_NameCountryAndIdent(requestDTO, "T");
					for(RequestDTO subRequestDTO_NameCountrycodeIdent : subRequestDTO_NameCountrycodeIdentList)
					{
						if(subRequestDTO_NameCountrycodeIdent != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH FIRMS | NameCountrycodeIdent SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycodeIdent);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycodeIdent);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateFirmLikeNameCityStrictCountrycode())
				{
					RequestDTO subRequestDTO_NameLikeCityLikeCountrycodeList = requestDTOBuilder.build_NameLikeCityStrictCountry(requestDTO, "T");
					if(subRequestDTO_NameLikeCityLikeCountrycodeList != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH FIRMS | NameLikeCityLikeCountrycode SUBQUERY");
						results = searchGins(subRequestDTO_NameLikeCityLikeCountrycodeList);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameLikeCityLikeCountrycodeList);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateFirmLikeNameCityStrictZipLikeCountrycode())
				{
					RequestDTO subRequestDTO_NameLikeCityLikeCountrycodeList = requestDTOBuilder.build_NameLikeCityStrictZipLikeCountry(requestDTO, "T");
					if(subRequestDTO_NameLikeCityLikeCountrycodeList != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH FIRMS | NameLikeCityLikeCountrycode SUBQUERY");
						results = searchGins(subRequestDTO_NameLikeCityLikeCountrycodeList);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameLikeCityLikeCountrycodeList);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateFirmLikeNameZipLikeCountrycode())
				{
					RequestDTO subRequestDTO_NameLikeCityLikeCountrycodeList = requestDTOBuilder.build_NameLikeZipLikeCountry(requestDTO, "T");
					if(subRequestDTO_NameLikeCityLikeCountrycodeList != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH FIRMS | NameLikeCityLikeCountrycode SUBQUERY");
						results = searchGins(subRequestDTO_NameLikeCityLikeCountrycodeList);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameLikeCityLikeCountrycodeList);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
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
				
				
				if(rateCounter == calculateRateDSBean.getRateFirmStrictNameCountrycode())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeList = requestDTOBuilder.build_NameCountry(requestDTO, "T");
					for(RequestDTO subRequestDTO_NameCountrycode : subRequestDTO_NameCountrycodeList)
					{
						if(subRequestDTO_NameCountrycode != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH FIRMS | NameCountrycode SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycode);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycode);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateFirmLikeNameCountrycode())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeList = requestDTOBuilder.build_NameCountry(requestDTO, "T");
					for(RequestDTO subRequestDTO_NameCountrycode : subRequestDTO_NameCountrycodeList)
					{
						if(subRequestDTO_NameCountrycode != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH FIRMS | NameCountrycode SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycode);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycode);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateFirmPhone())
				{
					RequestDTO subRequestDTO_Phone = requestDTOBuilder.build_Phone(requestDTO, "T");
					if(subRequestDTO_Phone != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH FIRMS | Phone SUBQUERY");
						results = searchGins(subRequestDTO_Phone);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_Phone);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
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
				if(rateCounter == calculateRateDSBean.getRateFirmEmail())
				{
					RequestDTO subRequestDTO_Email = requestDTOBuilder.build_Email(requestDTO, "T");
					if(subRequestDTO_Email != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH FIRMS | Email SUBQUERY");
						results = searchGins(subRequestDTO_Email);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_Email);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
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
				if(rateCounter == calculateRateDSBean.getRateFirmPhoneIdent())
				{
					RequestDTO subRequestDTO_PhoneAndIdent = requestDTOBuilder.build_PhoneAndIdent(requestDTO, "T");
					if(subRequestDTO_PhoneAndIdent != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH FIRMS | PhoneAndIdent SUBQUERY");
						results = searchGins(subRequestDTO_PhoneAndIdent);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_PhoneAndIdent);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
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
				if(rateCounter == calculateRateDSBean.getRateFirmEmailIdent())
				{
					RequestDTO subRequestDTO_EmailAndIdent = requestDTOBuilder.build_EmailAndIdent(requestDTO, "T");
					if(subRequestDTO_EmailAndIdent != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH FIRMS | EmailAndIdent SUBQUERY");
						results = searchGins(subRequestDTO_EmailAndIdent);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_EmailAndIdent);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
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
				if(rateCounter == calculateRateDSBean.getRateFirmIdent())
				{
					RequestDTO subRequestDTO_Ident = requestDTOBuilder.build_Ident(requestDTO, "T");
					if(subRequestDTO_Ident != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH FIRMS | Ident SUBQUERY");
						results = searchGins(subRequestDTO_Ident);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_Ident);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateFirmCountrycode())
				{
					RequestDTO subRequestDTO_Countrycode = requestDTOBuilder.build_Countrycode(requestDTO, "T");
					if(subRequestDTO_Countrycode != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH ALL | SERVICE-Countrycode SUBQUERY");
						results = searchGins(subRequestDTO_Countrycode);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_Countrycode);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateFirmZC5())
				{
					RequestDTO subRequestDTO_ZC5 = requestDTOBuilder.build_ZC5(requestDTO, "T");
					if(subRequestDTO_ZC5 != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH ALL | SERVICE-ZC5 SUBQUERY");
						results = searchGins(subRequestDTO_ZC5);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_ZC5);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
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
	
	
	private Set<SearchResultDTO> executeAutomaticSearchV2(RequestDTO requestDTO, String sFirmType) throws BusinessException
	{
		LOGGER.info("SearchFirmOnMultiCriteriaV2 | AUTOMATIC SEARCH ");
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
			int maxRate = calculateRateDSBean.getMaxFirmRate();
						
			rateCounterLoop:
			for(int rateCounter = maxRate; rateCounter >= 0; rateCounter -= 1)
			{
				if(isBestRateReached)
				{
					break rateCounterLoop;
				}

				if(rateCounter == calculateRateDSBean.getRateFirmStrictName() || rateCounter == calculateRateDSBean.getRateFirmLikeName())
				{
					List<RequestDTO> subRequestDTO_NameList = requestDTOBuilder.build_Name(requestDTO, sFirmType);
					for(RequestDTO subRequestDTO_Name : subRequestDTO_NameList)
					{
						if(subRequestDTO_Name != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH FIRMS | FIRM-Name SUBQUERY");
							results = searchGins(subRequestDTO_Name);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_Name);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				if(rateCounter == calculateRateDSBean.getRateFirmStrictNameCountrycodePhone())
				{
					List<RequestDTO> subRequestDTO_NameAndPhoneList = requestDTOBuilder.build_NameCountrycodeAndPhone(requestDTO, sFirmType);
					for(RequestDTO subRequestDTO_NameAndPhone : subRequestDTO_NameAndPhoneList)
					{
						if(subRequestDTO_NameAndPhone != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH FIRMS | NameAndPhone SUBQUERY");
							results = searchGins(subRequestDTO_NameAndPhone);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameAndPhone);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateFirmLikeNameCountrycodePhone())
				{
					List<RequestDTO> subRequestDTO_NameAndPhoneList = requestDTOBuilder.build_NameCountrycodeAndPhone(requestDTO, sFirmType);
					for(RequestDTO subRequestDTO_NameAndPhone : subRequestDTO_NameAndPhoneList)
					{
						if(subRequestDTO_NameAndPhone != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH FIRMS | NameAndPhone SUBQUERY");
							results = searchGins(subRequestDTO_NameAndPhone);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameAndPhone);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateFirmStrictNameCountrycodeEmail())
				{
					List<RequestDTO> subRequestDTO_NameAndEmailList = requestDTOBuilder.build_NameCountrycodeAndEmail(requestDTO, sFirmType);
					for(RequestDTO subRequestDTO_NameAndEmail : subRequestDTO_NameAndEmailList)
					{
						if(subRequestDTO_NameAndEmail != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH FIRMS | NameAndEmail SUBQUERY");
							results = searchGins(subRequestDTO_NameAndEmail);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameAndEmail);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateFirmLikeNameCountrycodeEmail())
				{
					List<RequestDTO> subRequestDTO_NameAndEmailList = requestDTOBuilder.build_NameCountrycodeAndEmail(requestDTO, sFirmType);
					for(RequestDTO subRequestDTO_NameAndEmail : subRequestDTO_NameAndEmailList)
					{
						if(subRequestDTO_NameAndEmail != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH FIRMS | NameAndEmail SUBQUERY");
							results = searchGins(subRequestDTO_NameAndEmail);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameAndEmail);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateFirmStrictNameCountrycodeNbandstreetCityZipcode())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeNbandstreetCityZipcodeList = requestDTOBuilder.build_NameCountrycodeNbandstreetCityZipcode(requestDTO, sFirmType);
					for(RequestDTO subRequestDTO_NameCountrycodeNbandstreetCityZipcode : subRequestDTO_NameCountrycodeNbandstreetCityZipcodeList)
					{
						if(subRequestDTO_NameCountrycodeNbandstreetCityZipcode != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH FIRMS | NameCountrycodeNbandstreetCityZipcode SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycodeNbandstreetCityZipcode);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycodeNbandstreetCityZipcode);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateFirmLikeNameCountrycodeNbandstreetCityZipcode())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeNbandstreetCityZipcodeList = requestDTOBuilder.build_NameCountrycodeNbandstreetCityZipcode(requestDTO, sFirmType);
					for(RequestDTO subRequestDTO_NameCountrycodeNbandstreetCityZipcode : subRequestDTO_NameCountrycodeNbandstreetCityZipcodeList)
					{
						if(subRequestDTO_NameCountrycodeNbandstreetCityZipcode != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH FIRMS | NameCountrycodeNbandstreetCityZipcode SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycodeNbandstreetCityZipcode);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycodeNbandstreetCityZipcode);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateFirmStrictNameCountrycodeNbandstreetCity())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeNbandstreetCityList = requestDTOBuilder.build_NameCountrycodeNbandstreetCity(requestDTO, sFirmType);
					for(RequestDTO subRequestDTO_NameCountrycodeNbandstreetCity : subRequestDTO_NameCountrycodeNbandstreetCityList)
					{
						if(subRequestDTO_NameCountrycodeNbandstreetCity != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH FIRMS | NameCountrycodeNbandstreetCity SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycodeNbandstreetCity);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycodeNbandstreetCity);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateFirmLikeNameCountrycodeNbandstreetCity())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeNbandstreetCityList = requestDTOBuilder.build_NameCountrycodeNbandstreetCity(requestDTO, sFirmType);
					for(RequestDTO subRequestDTO_NameCountrycodeNbandstreetCity : subRequestDTO_NameCountrycodeNbandstreetCityList)
					{
						if(subRequestDTO_NameCountrycodeNbandstreetCity != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH FIRMS | NameCountrycodeNbandstreetCity SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycodeNbandstreetCity);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycodeNbandstreetCity);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateFirmStrictNameCountrycodeCityZipcode())
				{
					List<RequestDTO> subRequestDTO_NameCountryCityAndZipList = requestDTOBuilder.build_NameCountrycodeCityAndZip(requestDTO, sFirmType);
					for(RequestDTO subRequestDTO_NameCountryCityAndZip : subRequestDTO_NameCountryCityAndZipList)
					{
						if(subRequestDTO_NameCountryCityAndZip != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH FIRMS | NameCountryCityAndZip SUBQUERY");
							results = searchGins(subRequestDTO_NameCountryCityAndZip);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountryCityAndZip);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateFirmLikeNameCountrycodeCityZipcode())
				{
					List<RequestDTO> subRequestDTO_NameCountryCityAndZipList = requestDTOBuilder.build_NameCountrycodeCityAndZip(requestDTO, sFirmType);
					for(RequestDTO subRequestDTO_NameCountryCityAndZip : subRequestDTO_NameCountryCityAndZipList)
					{
						if(subRequestDTO_NameCountryCityAndZip != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH FIRMS | NameCountryCityAndZip SUBQUERY");
							results = searchGins(subRequestDTO_NameCountryCityAndZip);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountryCityAndZip);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateFirmStrictNameCountrycodeZC14())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeZC14List = requestDTOBuilder.build_NameCountrycodeZC14(requestDTO, sFirmType);
					for(RequestDTO subRequestDTO_NameCountrycodeZC14 : subRequestDTO_NameCountrycodeZC14List)
					{
						if(subRequestDTO_NameCountrycodeZC14 != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH FIRMS | NameCountrycodeZC14 SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycodeZC14);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycodeZC14);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateFirmLikeNameCountrycodeZC14())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeZC14List = requestDTOBuilder.build_NameCountrycodeZC14(requestDTO, sFirmType);
					for(RequestDTO subRequestDTO_NameCountrycodeZC14 : subRequestDTO_NameCountrycodeZC14List)
					{
						if(subRequestDTO_NameCountrycodeZC14 != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH FIRMS | NameCountrycodeZC14 SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycodeZC14);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycodeZC14);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateFirmStrictNameCountrycodeZC15())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeZC15List = requestDTOBuilder.build_NameCountrycodeZC15(requestDTO, sFirmType);
					for(RequestDTO subRequestDTO_NameCountrycodeZC15 : subRequestDTO_NameCountrycodeZC15List)
					{
						if(subRequestDTO_NameCountrycodeZC15 != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH FIRMS | NameCountrycodeZC15 SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycodeZC15);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycodeZC15);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateFirmLikeNameCountrycodeZC15())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeZC15List = requestDTOBuilder.build_NameCountrycodeZC15(requestDTO, sFirmType);
					for(RequestDTO subRequestDTO_NameCountrycodeZC15 : subRequestDTO_NameCountrycodeZC15List)
					{
						if(subRequestDTO_NameCountrycodeZC15 != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH FIRMS | NameCountrycodeZC15 SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycodeZC15);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycodeZC15);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateFirmStrictNameCountrycodeIdent())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeIdentList = requestDTOBuilder.build_NameCountryAndIdent(requestDTO, sFirmType);
					for(RequestDTO subRequestDTO_NameCountrycodeIdent : subRequestDTO_NameCountrycodeIdentList)
					{
						if(subRequestDTO_NameCountrycodeIdent != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH FIRMS | NameCountrycodeIdent SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycodeIdent);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycodeIdent);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateFirmLikeNameCountrycodeIdent())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeIdentList = requestDTOBuilder.build_NameCountryAndIdent(requestDTO, sFirmType);
					for(RequestDTO subRequestDTO_NameCountrycodeIdent : subRequestDTO_NameCountrycodeIdentList)
					{
						if(subRequestDTO_NameCountrycodeIdent != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH FIRMS | NameCountrycodeIdent SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycodeIdent);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycodeIdent);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateFirmLikeNameCityStrictCountrycode())
				{
					RequestDTO subRequestDTO_NameLikeCityLikeCountrycodeList = requestDTOBuilder.build_NameLikeCityStrictCountry(requestDTO, sFirmType);
					if(subRequestDTO_NameLikeCityLikeCountrycodeList != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH FIRMS | NameLikeCityLikeCountrycode SUBQUERY");
						results = searchGins(subRequestDTO_NameLikeCityLikeCountrycodeList);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameLikeCityLikeCountrycodeList);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateFirmLikeNameCityStrictZipLikeCountrycode())
				{
					RequestDTO subRequestDTO_NameLikeCityLikeCountrycodeList = requestDTOBuilder.build_NameLikeCityStrictZipLikeCountry(requestDTO, sFirmType);
					if(subRequestDTO_NameLikeCityLikeCountrycodeList != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH FIRMS | NameLikeCityLikeCountrycode SUBQUERY");
						results = searchGins(subRequestDTO_NameLikeCityLikeCountrycodeList);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameLikeCityLikeCountrycodeList);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateFirmLikeNameZipLikeCountrycode())
				{
					RequestDTO subRequestDTO_NameLikeCityLikeCountrycodeList = requestDTOBuilder.build_NameLikeZipLikeCountry(requestDTO, sFirmType);
					if(subRequestDTO_NameLikeCityLikeCountrycodeList != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH FIRMS | NameLikeCityLikeCountrycode SUBQUERY");
						results = searchGins(subRequestDTO_NameLikeCityLikeCountrycodeList);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameLikeCityLikeCountrycodeList);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateFirmLikeNameCityLikeZipLikeCountrycode())
				{
					RequestDTO subRequestDTO_NameLikeCityLikeCountrycodeList = requestDTOBuilder.build_NameLikeCityLikeZipLikeCountry(requestDTO, sFirmType);
					if(subRequestDTO_NameLikeCityLikeCountrycodeList != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH FIRMS | NameLikeCityLikeCountrycode SUBQUERY");
						results = searchGins(subRequestDTO_NameLikeCityLikeCountrycodeList);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameLikeCityLikeCountrycodeList);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
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
				
				
				if(rateCounter == calculateRateDSBean.getRateFirmStrictNameCountrycode())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeList = requestDTOBuilder.build_NameCountry(requestDTO, sFirmType);
					for(RequestDTO subRequestDTO_NameCountrycode : subRequestDTO_NameCountrycodeList)
					{
						if(subRequestDTO_NameCountrycode != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH FIRMS | NameCountrycode SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycode);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycode);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateFirmLikeNameCountrycode())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeList = requestDTOBuilder.build_NameCountry(requestDTO, sFirmType);
					for(RequestDTO subRequestDTO_NameCountrycode : subRequestDTO_NameCountrycodeList)
					{
						if(subRequestDTO_NameCountrycode != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH FIRMS | NameCountrycode SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycode);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycode);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateFirmPhone())
				{
					RequestDTO subRequestDTO_Phone = requestDTOBuilder.build_Phone(requestDTO, sFirmType);
					if(subRequestDTO_Phone != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH FIRMS | Phone SUBQUERY");
						results = searchGins(subRequestDTO_Phone);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_Phone);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
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
				if(rateCounter == calculateRateDSBean.getRateFirmEmail())
				{
					RequestDTO subRequestDTO_Email = requestDTOBuilder.build_Email(requestDTO, sFirmType);
					if(subRequestDTO_Email != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH FIRMS | Email SUBQUERY");
						results = searchGins(subRequestDTO_Email);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_Email);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
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
				if(rateCounter == calculateRateDSBean.getRateFirmPhoneIdent())
				{
					RequestDTO subRequestDTO_PhoneAndIdent = requestDTOBuilder.build_PhoneAndIdent(requestDTO, sFirmType);
					if(subRequestDTO_PhoneAndIdent != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH FIRMS | PhoneAndIdent SUBQUERY");
						results = searchGins(subRequestDTO_PhoneAndIdent);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_PhoneAndIdent);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
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
				if(rateCounter == calculateRateDSBean.getRateFirmEmailIdent())
				{
					RequestDTO subRequestDTO_EmailAndIdent = requestDTOBuilder.build_EmailAndIdent(requestDTO, sFirmType);
					if(subRequestDTO_EmailAndIdent != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH FIRMS | EmailAndIdent SUBQUERY");
						results = searchGins(subRequestDTO_EmailAndIdent);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_EmailAndIdent);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
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
				if(rateCounter == calculateRateDSBean.getRateFirmIdent())
				{
					RequestDTO subRequestDTO_Ident = requestDTOBuilder.build_Ident(requestDTO, sFirmType);
					if(subRequestDTO_Ident != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH FIRMS | Ident SUBQUERY");
						results = searchGins(subRequestDTO_Ident);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_Ident);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateFirmCountrycode())
				{
					RequestDTO subRequestDTO_Countrycode = requestDTOBuilder.build_Countrycode(requestDTO, sFirmType);
					if(subRequestDTO_Countrycode != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH ALL | SERVICE-Countrycode SUBQUERY");
						results = searchGins(subRequestDTO_Countrycode);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_Countrycode);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateFirmZC5())
				{
					RequestDTO subRequestDTO_ZC5 = requestDTOBuilder.build_ZC5(requestDTO, sFirmType);
					if(subRequestDTO_ZC5 != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH ALL | SERVICE-ZC5 SUBQUERY");
						results = searchGins(subRequestDTO_ZC5);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_ZC5);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateFirmZC1_2())
				{
					RequestDTO subRequestDTO_ZC = requestDTOBuilder.build_ZC1_2(requestDTO, sFirmType);
					if(subRequestDTO_ZC != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH ALL | SERVICE-ZC1-2 SUBQUERY");
						results = searchGins(subRequestDTO_ZC);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_ZC);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
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
	
	
	/**
	 * Executes an automatic search for Services
	 * @param requestDTO
	 * @return
	 * @throws BusinessException
	 */
	private Set<SearchResultDTO> executeAutomaticSearchForServices(RequestDTO requestDTO) throws BusinessException
	{
		LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH SERVICES");
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
			int maxRate = calculateRateDSBean.getMaxServiceRate();
						
			rateCounterLoop:
			for(int rateCounter = maxRate; rateCounter >= 0; rateCounter -= 1)
			{
				if(isBestRateReached)
				{
					break rateCounterLoop;
				}
				if(rateCounter == calculateRateDSBean.getRateServiceStrictName() || rateCounter == calculateRateDSBean.getRateServiceLikeName())
				{
					List<RequestDTO> subRequestDTO_NameList = requestDTOBuilder.build_Name(requestDTO, "S");
					for(RequestDTO subRequestDTO_Name : subRequestDTO_NameList)
					{
						if(subRequestDTO_Name != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH SERVICES | SERVICE-Name SUBQUERY");
							results = searchGins(subRequestDTO_Name);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_Name);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				if(rateCounter == calculateRateDSBean.getRateServiceStrictNameCountrycodePhone())
				{
					List<RequestDTO> subRequestDTO_NameAndPhoneList = requestDTOBuilder.build_NameCountrycodeAndPhone(requestDTO, "S");
					for(RequestDTO subRequestDTO_NameAndPhone : subRequestDTO_NameAndPhoneList)
					{
						if(subRequestDTO_NameAndPhone != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH SERVICES | NameCountrycodeAndPhone SUBQUERY");
							results = searchGins(subRequestDTO_NameAndPhone);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameAndPhone);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateServiceLikeNameCountrycodePhone())
				{
					List<RequestDTO> subRequestDTO_NameAndPhoneList = requestDTOBuilder.build_NameCountrycodeAndPhone(requestDTO, "S");
					for(RequestDTO subRequestDTO_NameAndPhone : subRequestDTO_NameAndPhoneList)
					{
						if(subRequestDTO_NameAndPhone != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH SERVICES | NameCountrycodeAndPhone SUBQUERY");
							results = searchGins(subRequestDTO_NameAndPhone);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameAndPhone);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateServiceStrictNameCountrycodeEmail())
				{
					List<RequestDTO> subRequestDTO_NameAndEmailList = requestDTOBuilder.build_NameCountrycodeAndEmail(requestDTO, "S");
					for(RequestDTO subRequestDTO_NameAndEmail : subRequestDTO_NameAndEmailList)
					{
						if(subRequestDTO_NameAndEmail != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH SERVICES | NameCountrycodeAndEmail SUBQUERY");
							results = searchGins(subRequestDTO_NameAndEmail);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameAndEmail);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateServiceLikeNameCountrycodeEmail())
				{
					List<RequestDTO> subRequestDTO_NameAndEmailList = requestDTOBuilder.build_NameCountrycodeAndEmail(requestDTO, "S");
					for(RequestDTO subRequestDTO_NameAndEmail : subRequestDTO_NameAndEmailList)
					{
						if(subRequestDTO_NameAndEmail != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH SERVICES | NameCountrycodeAndEmail SUBQUERY");
							results = searchGins(subRequestDTO_NameAndEmail);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameAndEmail);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateServiceStrictNameCountrycodeNbandstreetCityZipcode())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeNbandstreetCityZipcodeList = requestDTOBuilder.build_NameCountrycodeNbandstreetCityZipcode(requestDTO, "S");
					for(RequestDTO subRequestDTO_NameCountrycodeNbandstreetCityZipcode : subRequestDTO_NameCountrycodeNbandstreetCityZipcodeList)
					{
						if(subRequestDTO_NameCountrycodeNbandstreetCityZipcode != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH SERVICES | NameCountrycodeNbandstreetCityZipcode SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycodeNbandstreetCityZipcode);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycodeNbandstreetCityZipcode);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateServiceLikeNameCountrycodeNbandstreetCityZipcode())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeNbandstreetCityZipcodeList = requestDTOBuilder.build_NameCountrycodeNbandstreetCityZipcode(requestDTO, "S");
					for(RequestDTO subRequestDTO_NameCountrycodeNbandstreetCityZipcode : subRequestDTO_NameCountrycodeNbandstreetCityZipcodeList)
					{
						if(subRequestDTO_NameCountrycodeNbandstreetCityZipcode != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH SERVICES | NameCountrycodeNbandstreetCityZipcode SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycodeNbandstreetCityZipcode);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycodeNbandstreetCityZipcode);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateServiceStrictNameCountrycodeNbandstreetCity())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeNbandstreetCityList = requestDTOBuilder.build_NameCountrycodeNbandstreetCity(requestDTO, "S");
					for(RequestDTO subRequestDTO_NameCountrycodeNbandstreetCity : subRequestDTO_NameCountrycodeNbandstreetCityList)
					{
						if(subRequestDTO_NameCountrycodeNbandstreetCity != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH SERVICES | NameCountrycodeNbandstreetCity SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycodeNbandstreetCity);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycodeNbandstreetCity);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateServiceLikeNameCountrycodeNbandstreetCity())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeNbandstreetCityList = requestDTOBuilder.build_NameCountrycodeNbandstreetCity(requestDTO, "S");
					for(RequestDTO subRequestDTO_NameCountrycodeNbandstreetCity : subRequestDTO_NameCountrycodeNbandstreetCityList)
					{
						if(subRequestDTO_NameCountrycodeNbandstreetCity != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH SERVICES | NameCountrycodeNbandstreetCity SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycodeNbandstreetCity);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycodeNbandstreetCity);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateServiceStrictNameCountrycodeCityZipcode())
				{
					List<RequestDTO> subRequestDTO_NameCountryCityAndZipList = requestDTOBuilder.build_NameCountrycodeCityAndZip(requestDTO, "S");
					for(RequestDTO subRequestDTO_NameCountryCityAndZip : subRequestDTO_NameCountryCityAndZipList)
					{
						if(subRequestDTO_NameCountryCityAndZip != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH SERVICES | NameCountrycodeCityAndZip SUBQUERY");
							results = searchGins(subRequestDTO_NameCountryCityAndZip);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountryCityAndZip);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateServiceLikeNameCountrycodeCityZipcode())
				{
					List<RequestDTO> subRequestDTO_NameCountryCityAndZipList = requestDTOBuilder.build_NameCountrycodeCityAndZip(requestDTO, "S");
					for(RequestDTO subRequestDTO_NameCountryCityAndZip : subRequestDTO_NameCountryCityAndZipList)
					{
						if(subRequestDTO_NameCountryCityAndZip != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH SERVICES | NameCountrycodeCityAndZip SUBQUERY");
							results = searchGins(subRequestDTO_NameCountryCityAndZip);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountryCityAndZip);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateServicePhone())
				{
					RequestDTO subRequestDTO_Phone = requestDTOBuilder.build_Phone(requestDTO, "S");
					if(subRequestDTO_Phone != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH SERVICES | Phone SUBQUERY");
						results = searchGins(subRequestDTO_Phone);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_Phone);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
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
				if(rateCounter == calculateRateDSBean.getRateServiceEmail())
				{
					RequestDTO subRequestDTO_Email = requestDTOBuilder.build_Email(requestDTO, "S");
					if(subRequestDTO_Email != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH SERVICES | Email SUBQUERY");
						results = searchGins(subRequestDTO_Email);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_Email);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
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
				if(rateCounter == calculateRateDSBean.getRateServicePhoneIdent())
				{
					RequestDTO subRequestDTO_PhoneAndIdent = requestDTOBuilder.build_PhoneAndIdent(requestDTO, "S");
					if(subRequestDTO_PhoneAndIdent != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH SERVICES | PhoneAndIdent SUBQUERY");
						results = searchGins(subRequestDTO_PhoneAndIdent);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_PhoneAndIdent);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
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
				if(rateCounter == calculateRateDSBean.getRateServiceEmailIdent())
				{
					RequestDTO subRequestDTO_EmailAndIdent = requestDTOBuilder.build_EmailAndIdent(requestDTO, "S");
					if(subRequestDTO_EmailAndIdent != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH SERVICES | EmailAndIdent SUBQUERY");
						results = searchGins(subRequestDTO_EmailAndIdent);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_EmailAndIdent);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
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
				if(rateCounter == calculateRateDSBean.getRateServiceIdent())
				{
					RequestDTO subRequestDTO_Ident = requestDTOBuilder.build_Ident(requestDTO, "S");
					if(subRequestDTO_Ident != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH SERVICES | Ident SUBQUERY");
						results = searchGins(subRequestDTO_Ident);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_Ident);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateServiceCountrycode())
				{
					RequestDTO subRequestDTO_Countrycode = requestDTOBuilder.build_Countrycode(requestDTO, "S");
					if(subRequestDTO_Countrycode != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH ALL | SERVICE-Countrycode SUBQUERY");
						results = searchGins(subRequestDTO_Countrycode);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_Countrycode);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateServiceZC5())
				{
					RequestDTO subRequestDTO_ZC5 = requestDTOBuilder.build_ZC5(requestDTO, "S");
					if(subRequestDTO_ZC5 != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH ALL | SERVICE-ZC5 SUBQUERY");
						results = searchGins(subRequestDTO_ZC5);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_ZC5);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
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
	
	
	/**
	 * Executes an automatic search for companies
	 * @param requestDTO
	 * @return
	 * @throws BusinessException
	 */
	private Set<SearchResultDTO> executeAutomaticSearchForCompanies(RequestDTO requestDTO) throws BusinessException
	{
		LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH COMPANIES");
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
			int maxRate = calculateRateDSBean.getMaxCompanyRate();
						
			rateCounterLoop:
			for(int rateCounter = maxRate; rateCounter >= 0; rateCounter -= 1)
			{
				if(isBestRateReached)
				{
					break rateCounterLoop;
				}
				
				if(rateCounter == calculateRateDSBean.getRateCompanyStrictName() || rateCounter == calculateRateDSBean.getRateCompanyLikeName())
				{
					List<RequestDTO> subRequestDTO_NameList = requestDTOBuilder.build_Name(requestDTO, "E");
					for(RequestDTO subRequestDTO_Name : subRequestDTO_NameList)
					{
						if(subRequestDTO_Name != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH ALL | COMPAGNY-Name SUBQUERY");
							results = searchGins(subRequestDTO_Name);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_Name);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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

				if(rateCounter == calculateRateDSBean.getRateCompanyStrictNameCountrycodePhone())
				{
					List<RequestDTO> subRequestDTO_NameAndPhoneList = requestDTOBuilder.build_NameCountrycodeAndPhone(requestDTO, "E");
					for(RequestDTO subRequestDTO_NameAndPhone : subRequestDTO_NameAndPhoneList)
					{
						if(subRequestDTO_NameAndPhone != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH COMPANIES | NameCountrycodeAndPhone SUBQUERY");
							results = searchGins(subRequestDTO_NameAndPhone);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameAndPhone);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateCompanyStrictNameCountrycodeEmail())
				{
					List<RequestDTO> subRequestDTO_NameAndEmailList = requestDTOBuilder.build_NameCountrycodeAndEmail(requestDTO, "E");
					for(RequestDTO subRequestDTO_NameAndEmail : subRequestDTO_NameAndEmailList)
					{
						if(subRequestDTO_NameAndEmail != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH COMPANIES | NameCountrycodeAndEmail SUBQUERY");
							results = searchGins(subRequestDTO_NameAndEmail);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameAndEmail);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				if(rateCounter == calculateRateDSBean.getRateCompanyStrictNameCountrycodeNbandstreetCityZipcode())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeNbandstreetCityZipcodeList = requestDTOBuilder.build_NameCountrycodeNbandstreetCityZipcode(requestDTO, "E");
					for(RequestDTO subRequestDTO_NameCountrycodeNbandstreetCityZipcode : subRequestDTO_NameCountrycodeNbandstreetCityZipcodeList)
					{
						if(subRequestDTO_NameCountrycodeNbandstreetCityZipcode != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH COMPANIES | NameCountrycodeNbandstreetCityZipcode SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycodeNbandstreetCityZipcode);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycodeNbandstreetCityZipcode);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				if(rateCounter == calculateRateDSBean.getRateCompanyStrictNameCountrycodeNbandstreetCity())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeNbandstreetCityList = requestDTOBuilder.build_NameCountrycodeNbandstreetCity(requestDTO, "E");
					for(RequestDTO subRequestDTO_NameCountrycodeNbandstreetCity : subRequestDTO_NameCountrycodeNbandstreetCityList)
					{
						if(subRequestDTO_NameCountrycodeNbandstreetCity != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH COMPANIES | NameCountrycodeNbandstreetCity SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycodeNbandstreetCity);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycodeNbandstreetCity);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				if(rateCounter == calculateRateDSBean.getRateCompanyStrictNameCountrycodeCityZipcode())
				{
					List<RequestDTO> subRequestDTO_NameCountryCityAndZipList = requestDTOBuilder.build_NameCountrycodeCityAndZip(requestDTO, "E");
					for(RequestDTO subRequestDTO_NameCountryCityAndZip : subRequestDTO_NameCountryCityAndZipList)
					{
						if(subRequestDTO_NameCountryCityAndZip != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH COMPANIES | NameCountrycodeCityAndZip SUBQUERY");
							results = searchGins(subRequestDTO_NameCountryCityAndZip);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountryCityAndZip);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				if(rateCounter == calculateRateDSBean.getRateCompanyStrictNameCountrycodeIdent())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeIdentList = requestDTOBuilder.build_NameCountryAndIdent(requestDTO, "E");
					for(RequestDTO subRequestDTO_NameCountrycodeIdent : subRequestDTO_NameCountrycodeIdentList)
					{
						if(subRequestDTO_NameCountrycodeIdent != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH COMPANIES | NameCountryAndIdent SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycodeIdent);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycodeIdent);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateCompanyCountrycode())
				{
					RequestDTO subRequestDTO_Countrycode = requestDTOBuilder.build_Countrycode(requestDTO, "E");
					if(subRequestDTO_Countrycode != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH ALL | SERVICE-Countrycode SUBQUERY");
						results = searchGins(subRequestDTO_Countrycode);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_Countrycode);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateCompanyZC5())
				{
					RequestDTO subRequestDTO_ZC5 = requestDTOBuilder.build_ZC5(requestDTO, "E");
					if(subRequestDTO_ZC5 != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH ALL | SERVICE-ZC5 SUBQUERY");
						results = searchGins(subRequestDTO_ZC5);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_ZC5);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
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
	
	
	/**
	 * Executes an automatic search for Groups
	 * @param requestDTO
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	private Set<SearchResultDTO> executeAutomaticSearchForGroups(RequestDTO requestDTO) throws BusinessException
	{
		LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH GROUPS");
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
			int maxRate = calculateRateDSBean.getMaxGroupRate();
						
			rateCounterLoop:
			for(int rateCounter = maxRate; rateCounter >= 0; rateCounter -= 1)
			{
				if(isBestRateReached)
				{
					break rateCounterLoop;
				}
				
				if(rateCounter == calculateRateDSBean.getRateGroupStrictName())
				{
					List<RequestDTO> subRequestDTO_NameList = requestDTOBuilder.build_Name(requestDTO, "G");
					for(RequestDTO subRequestDTO_Name : subRequestDTO_NameList)
					{
						if(subRequestDTO_Name != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH GROUPS | Name SUBQUERY");
							results = searchGins(subRequestDTO_Name);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_Name);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateGroupLikeName())
				{
					List<RequestDTO> subRequestDTO_NameList = requestDTOBuilder.build_Name(requestDTO, "G");
					for(RequestDTO subRequestDTO_Name : subRequestDTO_NameList)
					{
						if(subRequestDTO_Name != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH GROUPS | Name SUBQUERY");
							results = searchGins(subRequestDTO_Name);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_Name);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateGroupCountrycode())
				{
					RequestDTO subRequestDTO_Countrycode = requestDTOBuilder.build_Countrycode(requestDTO, "G");
					if(subRequestDTO_Countrycode != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH ALL | SERVICE-Countrycode SUBQUERY");
						results = searchGins(subRequestDTO_Countrycode);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_Countrycode);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
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
				
				if(rateCounter == calculateRateDSBean.getRateGroupZC5())
				{
					RequestDTO subRequestDTO_ZC5 = requestDTOBuilder.build_ZC5(requestDTO, "G");
					if(subRequestDTO_ZC5 != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH ALL | SERVICE-ZC5 SUBQUERY");
						results = searchGins(subRequestDTO_ZC5);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_ZC5);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
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
	
	
	/**
	 * Executes a manual search: 
	 *  - 14 consecutive scenarios until the indicated 
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
	 */
	private ResponseDTO executeManualSearch(RequestDTO requestDTO) throws BusinessException
	{
		LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH START");
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
			Set<SearchResultDTO> resultsSet = null;
			
			if(BuildConditionsDS.isFirmTypeAllConditionSet(requestDTO))
			{
				resultsSet = executeManualSearchForAll(requestDTO);
			}
			else if(BuildConditionsDS.isFirmTypeGroupsConditionSet(requestDTO))
			{
				resultsSet = executeManualSearchForGroups(requestDTO);
			}
			else if(BuildConditionsDS.isFirmTypeCompaniesConditionSet(requestDTO))
			{
				resultsSet = executeManualSearchForCompanies(requestDTO);
			}
			else if(BuildConditionsDS.isFirmTypeServicesConditionSet(requestDTO))
			{
				resultsSet = executeManualSearchForServices(requestDTO);
			}
			else if(BuildConditionsDS.isFirmTypeFirmsConditionSet(requestDTO))
			{
				resultsSet = executeManualSearchForFirms(requestDTO);
			}
			
			if( resultsSet != null && !resultsSet.isEmpty())
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
						FirmDTO firm = searchFirmDAOBean.readFirmByGin(requestDTO, result.getGin());
			    		if(firm != null)
			    		{
			    			firm.setRelevance(result.getRate());
			    			responseDTO.getFirms().add(firm);
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
	
	private ResponseDTO executeManualSearchV2(RequestDTO requestDTO) throws BusinessException
	{
		LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH START");
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
			Set<SearchResultDTO> resultsSet = null;
			
			if(BuildConditionsDS.isFirmTypeAllConditionSet(requestDTO))
			{
				resultsSet = executeManualSearchForAll(requestDTO);
			}
			else if(BuildConditionsDS.isFirmTypeGroupsConditionSet(requestDTO))
			{
				resultsSet = executeManualSearchForGroups(requestDTO);
			}
			else if(BuildConditionsDS.isFirmTypeCompaniesConditionSet(requestDTO))
			{
				resultsSet = executeManualSearchForCompanies(requestDTO);
			}
			else if(BuildConditionsDS.isFirmTypeServicesConditionSet(requestDTO))
			{
				resultsSet = executeManualSearchForServices(requestDTO);
			}
			else if(BuildConditionsDS.isFirmTypeFirmsConditionSet(requestDTO))
			{
				resultsSet = executeManualSearchForFirms(requestDTO);
			}
			
			if( resultsSet != null && !resultsSet.isEmpty())
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
						FirmDTO firm = searchFirmDAOBean.readFirmByGin(requestDTO, result.getGin());
			    		if(firm != null)
			    		{
			    			firm.setRelevance(result.getRate());
			    			responseDTO.getFirms().add(firm);
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
				responseDTO.setReturnCode(getNoResultsReturnCode());
				responseDTO.setReturnMessage(getNoResultsReturnMessage());				
			}
		}
			
		return responseDTO;
	}	
	
	
	/**
	 * Executes a manual search for firmType == ALL
	 * @param requestDTO
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	private Set<SearchResultDTO> executeManualSearchForAll(RequestDTO requestDTO) throws BusinessException
	{
		LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH ALL");
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
				 * GROUPS
				 */
				if(rateCounter == calculateRateDSBean.getRateGroupStrictName())
				{
					List<RequestDTO> subRequestDTO_NameList = requestDTOBuilder.build_Name(requestDTO, "G");
					for(RequestDTO subRequestDTO_Name : subRequestDTO_NameList)
					{
						if(subRequestDTO_Name != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH ALL | GROUP-Name SUBQUERY");
							results = searchGins(subRequestDTO_Name);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_Name);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
									{
										break rateCounterLoop;
									}
								}
							}
						}
					}
				}
				
				if(rateCounter == calculateRateDSBean.getRateGroupLikeName())
				{
					List<RequestDTO> subRequestDTO_NameList = requestDTOBuilder.build_Name(requestDTO, "G");
					for(RequestDTO subRequestDTO_Name : subRequestDTO_NameList)
					{
						if(subRequestDTO_Name != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH ALL | GROUP-Name SUBQUERY");
							results = searchGins(subRequestDTO_Name);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_Name);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
									{
										break rateCounterLoop;
									}
								}
							}
						}
					}
				}
				
				if(rateCounter == calculateRateDSBean.getRateGroupCountrycode())
				{
					RequestDTO subRequestDTO_Countrycode = requestDTOBuilder.build_Countrycode(requestDTO, "G");
					if(subRequestDTO_Countrycode != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH ALL | SERVICE-Countrycode SUBQUERY");
						results = searchGins(subRequestDTO_Countrycode);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_Countrycode);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
								{
									break rateCounterLoop;
								}
							}
						}
					}
				}
				
				if(rateCounter == calculateRateDSBean.getRateGroupZC5())
				{
					RequestDTO subRequestDTO_ZC5 = requestDTOBuilder.build_ZC5(requestDTO, "G");
					if(subRequestDTO_ZC5 != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH ALL | SERVICE-ZC5 SUBQUERY");
						results = searchGins(subRequestDTO_ZC5);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_ZC5);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
								{
									break rateCounterLoop;
								}
							}
						}
					}
				}
				
				/*
				 * COMPANIES
				 */
				if(rateCounter == calculateRateDSBean.getRateCompanyStrictName() || rateCounter == calculateRateDSBean.getRateCompanyLikeName())
				{
					List<RequestDTO> subRequestDTO_NameList = requestDTOBuilder.build_Name(requestDTO, "E");
					for(RequestDTO subRequestDTO_Name : subRequestDTO_NameList)
					{
						if(subRequestDTO_Name != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH ALL | COMPAGNY-Name SUBQUERY");
							results = searchGins(subRequestDTO_Name);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_Name);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
									{
										break rateCounterLoop;
									}
								}
							}
						}
					}
				}
				if(rateCounter == calculateRateDSBean.getRateCompanyStrictNameCountrycodePhone())
				{
					List<RequestDTO> subRequestDTO_NameAndPhoneList = requestDTOBuilder.build_NameCountrycodeAndPhone(requestDTO, "E");
					for(RequestDTO subRequestDTO_NameAndPhone : subRequestDTO_NameAndPhoneList)
					{
						if(subRequestDTO_NameAndPhone != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH ALL | COMPANY-NameCountrycodeAndPhone SUBQUERY");
							results = searchGins(subRequestDTO_NameAndPhone);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameAndPhone);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
									{
										break rateCounterLoop;
									}
								}
							}
						}
					}
				}
				
				if(rateCounter == calculateRateDSBean.getRateCompanyStrictNameCountrycodeEmail())
				{
					List<RequestDTO> subRequestDTO_NameAndEmailList = requestDTOBuilder.build_NameCountrycodeAndEmail(requestDTO, "E");
					for(RequestDTO subRequestDTO_NameAndEmail : subRequestDTO_NameAndEmailList)
					{
						if(subRequestDTO_NameAndEmail != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH ALL | COMPANY-NameCountrycodeAndEmail SUBQUERY");
							results = searchGins(subRequestDTO_NameAndEmail);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameAndEmail);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
									{
										break rateCounterLoop;
									}
								}
							}
						}
					}
				}
				if(rateCounter == calculateRateDSBean.getRateCompanyStrictNameCountrycodeNbandstreetCityZipcode())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeNbandstreetCityZipcodeList = requestDTOBuilder.build_NameCountrycodeNbandstreetCityZipcode(requestDTO, "E");
					for(RequestDTO subRequestDTO_NameCountrycodeNbandstreetCityZipcode : subRequestDTO_NameCountrycodeNbandstreetCityZipcodeList)
					{
						if(subRequestDTO_NameCountrycodeNbandstreetCityZipcode != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH ALL | COMPANY-NameCountrycodeNbandstreetCityZipcode SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycodeNbandstreetCityZipcode);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycodeNbandstreetCityZipcode);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
									{
										break rateCounterLoop;
									}
								}
							}
						}
					}
				}
				if(rateCounter == calculateRateDSBean.getRateCompanyStrictNameCountrycodeNbandstreetCity())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeNbandstreetCityList = requestDTOBuilder.build_NameCountrycodeNbandstreetCity(requestDTO, "E");
					for(RequestDTO subRequestDTO_NameCountrycodeNbandstreetCity : subRequestDTO_NameCountrycodeNbandstreetCityList)
					{
						if(subRequestDTO_NameCountrycodeNbandstreetCity != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH ALL | COMPANY-NameCountrycodeNbandstreetCity SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycodeNbandstreetCity);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycodeNbandstreetCity);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
									{
										break rateCounterLoop;
									}
								}
							}
						}
					}
				}
				if(rateCounter == calculateRateDSBean.getRateCompanyStrictNameCountrycodeCityZipcode())
				{
					List<RequestDTO> subRequestDTO_NameCountryCityAndZipList = requestDTOBuilder.build_NameCountrycodeCityAndZip(requestDTO, "E");
					for(RequestDTO subRequestDTO_NameCountryCityAndZip : subRequestDTO_NameCountryCityAndZipList)
					{
						if(subRequestDTO_NameCountryCityAndZip != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH ALL | COMPANY-NameCountrycodeCityAndZip SUBQUERY");
							results = searchGins(subRequestDTO_NameCountryCityAndZip);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountryCityAndZip);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
									{
										break rateCounterLoop;
									}
								}
							}
						}
					}
				}
				if(rateCounter == calculateRateDSBean.getRateCompanyStrictNameCountrycodeIdent())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeIdentList = requestDTOBuilder.build_NameCountryAndIdent(requestDTO, "E");
					for(RequestDTO subRequestDTO_NameCountrycodeIdent : subRequestDTO_NameCountrycodeIdentList)
					{
						if(subRequestDTO_NameCountrycodeIdent != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH ALL | COMPANY-NameCountryAndIdent SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycodeIdent);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycodeIdent);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
									{
										break rateCounterLoop;
									}
								}
							}
						}
					}
				}
				
				if(rateCounter == calculateRateDSBean.getRateCompanyCountrycode())
				{
					RequestDTO subRequestDTO_Countrycode = requestDTOBuilder.build_Countrycode(requestDTO, "E");
					if(subRequestDTO_Countrycode != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH ALL | SERVICE-Countrycode SUBQUERY");
						results = searchGins(subRequestDTO_Countrycode);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_Countrycode);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
								{
									break rateCounterLoop;
								}
							}
						}
					}
				}
				
				if(rateCounter == calculateRateDSBean.getRateCompanyZC5())
				{
					RequestDTO subRequestDTO_ZC5 = requestDTOBuilder.build_ZC5(requestDTO, "E");
					if(subRequestDTO_ZC5 != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH ALL | SERVICE-ZC5 SUBQUERY");
						results = searchGins(subRequestDTO_ZC5);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_ZC5);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
								{
									break rateCounterLoop;
								}
							}
						}
					}
				}
				
				/*
				 * SERVICES
				 */
				if(rateCounter == calculateRateDSBean.getRateServiceStrictName() || rateCounter == calculateRateDSBean.getRateServiceLikeName())
				{
					List<RequestDTO> subRequestDTO_NameList = requestDTOBuilder.build_Name(requestDTO, "S");
					for(RequestDTO subRequestDTO_Name : subRequestDTO_NameList)
					{
						if(subRequestDTO_Name != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH ALL | SERVICE-Name SUBQUERY");
							results = searchGins(subRequestDTO_Name);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_Name);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
									{
										break rateCounterLoop;
									}
								}
							}
						}
					}
				}
				if(rateCounter == calculateRateDSBean.getRateServiceStrictNameCountrycodePhone())
				{
					List<RequestDTO> subRequestDTO_NameAndPhoneList = requestDTOBuilder.build_NameCountrycodeAndPhone(requestDTO, "S");
					for(RequestDTO subRequestDTO_NameAndPhone : subRequestDTO_NameAndPhoneList)
					{
						if(subRequestDTO_NameAndPhone != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH ALL | SERVICE-NameCountrycodeAndPhone SUBQUERY");
							results = searchGins(subRequestDTO_NameAndPhone);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameAndPhone);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
									{
										break rateCounterLoop;
									}
								}
							}
						}
					}
				}
				
				if(rateCounter == calculateRateDSBean.getRateServiceLikeNameCountrycodePhone())
				{
					List<RequestDTO> subRequestDTO_NameAndPhoneList = requestDTOBuilder.build_NameCountrycodeAndPhone(requestDTO, "S");
					for(RequestDTO subRequestDTO_NameAndPhone : subRequestDTO_NameAndPhoneList)
					{
						if(subRequestDTO_NameAndPhone != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH ALL | SERVICE-NameCountrycodeAndPhone SUBQUERY");
							results = searchGins(subRequestDTO_NameAndPhone);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameAndPhone);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
									{
										break rateCounterLoop;
									}
								}
							}
						}
					}
				}
				
				if(rateCounter == calculateRateDSBean.getRateServiceStrictNameCountrycodeEmail())
				{
					List<RequestDTO> subRequestDTO_NameAndEmailList = requestDTOBuilder.build_NameCountrycodeAndEmail(requestDTO, "S");
					for(RequestDTO subRequestDTO_NameAndEmail : subRequestDTO_NameAndEmailList)
					{
						if(subRequestDTO_NameAndEmail != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH ALL | SERVICE-NameCountrycodeAndEmail SUBQUERY");
							results = searchGins(subRequestDTO_NameAndEmail);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameAndEmail);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
									{
										break rateCounterLoop;
									}
								}
							}
						}
					}
				}
				
				if(rateCounter == calculateRateDSBean.getRateServiceLikeNameCountrycodeEmail())
				{
					List<RequestDTO> subRequestDTO_NameAndEmailList = requestDTOBuilder.build_NameCountrycodeAndEmail(requestDTO, "S");
					for(RequestDTO subRequestDTO_NameAndEmail : subRequestDTO_NameAndEmailList)
					{
						if(subRequestDTO_NameAndEmail != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH ALL | SERVICE-NameCountrycodeAndEmail SUBQUERY");
							results = searchGins(subRequestDTO_NameAndEmail);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameAndEmail);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
									{
										break rateCounterLoop;
									}
								}
							}
						}
					}
				}
				
				if(rateCounter == calculateRateDSBean.getRateServiceStrictNameCountrycodeNbandstreetCityZipcode())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeNbandstreetCityZipcodeList = requestDTOBuilder.build_NameCountrycodeNbandstreetCityZipcode(requestDTO, "S");
					for(RequestDTO subRequestDTO_NameCountrycodeNbandstreetCityZipcode : subRequestDTO_NameCountrycodeNbandstreetCityZipcodeList)
					{
						if(subRequestDTO_NameCountrycodeNbandstreetCityZipcode != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH ALL | SERVICE-NameCountrycodeNbandstreetCityZipcode SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycodeNbandstreetCityZipcode);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycodeNbandstreetCityZipcode);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
									{
										break rateCounterLoop;
									}
								}
							}
						}
					}
				}
				
				if(rateCounter == calculateRateDSBean.getRateServiceLikeNameCountrycodeNbandstreetCityZipcode())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeNbandstreetCityZipcodeList = requestDTOBuilder.build_NameCountrycodeNbandstreetCityZipcode(requestDTO, "S");
					for(RequestDTO subRequestDTO_NameCountrycodeNbandstreetCityZipcode : subRequestDTO_NameCountrycodeNbandstreetCityZipcodeList)
					{
						if(subRequestDTO_NameCountrycodeNbandstreetCityZipcode != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH ALL | SERVICE-NameCountrycodeNbandstreetCityZipcode SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycodeNbandstreetCityZipcode);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycodeNbandstreetCityZipcode);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
									{
										break rateCounterLoop;
									}
								}
							}
						}
					}
				}
				
				if(rateCounter == calculateRateDSBean.getRateServiceStrictNameCountrycodeNbandstreetCity())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeNbandstreetCityList = requestDTOBuilder.build_NameCountrycodeNbandstreetCity(requestDTO, "S");
					for(RequestDTO subRequestDTO_NameCountrycodeNbandstreetCity : subRequestDTO_NameCountrycodeNbandstreetCityList)
					{
						if(subRequestDTO_NameCountrycodeNbandstreetCity != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH ALL | SERVICE-NameCountrycodeNbandstreetCity SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycodeNbandstreetCity);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycodeNbandstreetCity);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
									{
										break rateCounterLoop;
									}
								}
							}
						}
					}
				}
				
				if(rateCounter == calculateRateDSBean.getRateServiceLikeNameCountrycodeNbandstreetCity())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeNbandstreetCityList = requestDTOBuilder.build_NameCountrycodeNbandstreetCity(requestDTO, "S");
					for(RequestDTO subRequestDTO_NameCountrycodeNbandstreetCity : subRequestDTO_NameCountrycodeNbandstreetCityList)
					{
						if(subRequestDTO_NameCountrycodeNbandstreetCity != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH ALL | SERVICE-NameCountrycodeNbandstreetCity SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycodeNbandstreetCity);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycodeNbandstreetCity);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
									{
										break rateCounterLoop;
									}
								}
							}
						}
					}
				}
				
				if(rateCounter == calculateRateDSBean.getRateServiceStrictNameCountrycodeCityZipcode())
				{
					List<RequestDTO> subRequestDTO_NameCountryCityAndZipList = requestDTOBuilder.build_NameCountrycodeCityAndZip(requestDTO, "S");
					for(RequestDTO subRequestDTO_NameCountryCityAndZip : subRequestDTO_NameCountryCityAndZipList)
					{
						if(subRequestDTO_NameCountryCityAndZip != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH ALL | SERVICE-NameCountrycodeCityAndZip SUBQUERY");
							results = searchGins(subRequestDTO_NameCountryCityAndZip);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountryCityAndZip);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
									{
										break rateCounterLoop;
									}
								}
							}
						}
					}
				}
				
				if(rateCounter == calculateRateDSBean.getRateServiceLikeNameCountrycodeCityZipcode())
				{
					List<RequestDTO> subRequestDTO_NameCountryCityAndZipList = requestDTOBuilder.build_NameCountrycodeCityAndZip(requestDTO, "S");
					for(RequestDTO subRequestDTO_NameCountryCityAndZip : subRequestDTO_NameCountryCityAndZipList)
					{
						if(subRequestDTO_NameCountryCityAndZip != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH ALL | SERVICE-NameCountrycodeCityAndZip SUBQUERY");
							results = searchGins(subRequestDTO_NameCountryCityAndZip);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountryCityAndZip);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
									{
										break rateCounterLoop;
									}
								}
							}
						}
					}
				}
				
				if(rateCounter == calculateRateDSBean.getRateServicePhone())
				{
					RequestDTO subRequestDTO_Phone = requestDTOBuilder.build_Phone(requestDTO, "S");
					if(subRequestDTO_Phone != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH ALL | SERVICE-Phone SUBQUERY");
						results = searchGins(subRequestDTO_Phone);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_Phone);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
								{
									break rateCounterLoop;
								}
							}
						}
					}
				}
				if(rateCounter == calculateRateDSBean.getRateServiceEmail())
				{
					RequestDTO subRequestDTO_Email = requestDTOBuilder.build_Email(requestDTO, "S");
					if(subRequestDTO_Email != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH ALL | SERVICE-Email SUBQUERY");
						results = searchGins(subRequestDTO_Email);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_Email);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
								{
									break rateCounterLoop;
								}
							}
						}
					}
				}
				if(rateCounter == calculateRateDSBean.getRateServicePhoneIdent())
				{
					RequestDTO subRequestDTO_PhoneAndIdent = requestDTOBuilder.build_PhoneAndIdent(requestDTO, "S");
					if(subRequestDTO_PhoneAndIdent != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH ALL | SERVICE-PhoneAndIdent SUBQUERY");
						results = searchGins(subRequestDTO_PhoneAndIdent);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_PhoneAndIdent);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
								{
									break rateCounterLoop;
								}
							}
						}
					}
				}
				if(rateCounter == calculateRateDSBean.getRateServiceEmailIdent())
				{
					RequestDTO subRequestDTO_EmailAndIdent = requestDTOBuilder.build_EmailAndIdent(requestDTO, "S");
					if(subRequestDTO_EmailAndIdent != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH ALL | SERVICE-EmailAndIdent SUBQUERY");
						results = searchGins(subRequestDTO_EmailAndIdent);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_EmailAndIdent);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
								{
									break rateCounterLoop;
								}
							}
						}
					}
				}
				if(rateCounter == calculateRateDSBean.getRateServiceIdent())
				{
					RequestDTO subRequestDTO_Ident = requestDTOBuilder.build_Ident(requestDTO, "S");
					if(subRequestDTO_Ident != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH ALL | SERVICE-Ident SUBQUERY");
						results = searchGins(subRequestDTO_Ident);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_Ident);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
								{
									break rateCounterLoop;
								}
							}
						}
					}
				}
				
				if(rateCounter == calculateRateDSBean.getRateServiceCountrycode())
				{
					RequestDTO subRequestDTO_Countrycode = requestDTOBuilder.build_Countrycode(requestDTO, "S");
					if(subRequestDTO_Countrycode != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH ALL | SERVICE-Countrycode SUBQUERY");
						results = searchGins(subRequestDTO_Countrycode);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_Countrycode);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
								{
									break rateCounterLoop;
								}
							}
						}
					}
				}
				
				if(rateCounter == calculateRateDSBean.getRateServiceZC5())
				{
					RequestDTO subRequestDTO_ZC5 = requestDTOBuilder.build_ZC5(requestDTO, "S");
					if(subRequestDTO_ZC5 != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH ALL | SERVICE-ZC5 SUBQUERY");
						results = searchGins(subRequestDTO_ZC5);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_ZC5);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
								{
									break rateCounterLoop;
								}
							}
						}
					}
				}
				
				/*
				 * FIRMS
				 */
				if(rateCounter == calculateRateDSBean.getRateFirmStrictName() || rateCounter == calculateRateDSBean.getRateFirmLikeName())
				{
					List<RequestDTO> subRequestDTO_NameList = requestDTOBuilder.build_Name(requestDTO, "T");
					for(RequestDTO subRequestDTO_Name : subRequestDTO_NameList)
					{
						if(subRequestDTO_Name != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH FIRMS | FIRM-Name SUBQUERY");
							results = searchGins(subRequestDTO_Name);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_Name);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
									{
										break rateCounterLoop;
									}
								}
							}
						}
					}
				}
				if(rateCounter == calculateRateDSBean.getRateFirmStrictNameCountrycodePhone())
				{
					List<RequestDTO> subRequestDTO_NameAndPhoneList = requestDTOBuilder.build_NameCountrycodeAndPhone(requestDTO, "T");
					for(RequestDTO subRequestDTO_NameAndPhone : subRequestDTO_NameAndPhoneList)
					{
						if(subRequestDTO_NameAndPhone != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH ALL | FIRM-NameCountrycodeAndPhone SUBQUERY");
							results = searchGins(subRequestDTO_NameAndPhone);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameAndPhone);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
									{
										break rateCounterLoop;
									}
								}
							}
						}
					}
				}
				
				if(rateCounter == calculateRateDSBean.getRateFirmLikeNameCountrycodePhone())
				{
					List<RequestDTO> subRequestDTO_NameAndPhoneList = requestDTOBuilder.build_NameCountrycodeAndPhone(requestDTO, "T");
					for(RequestDTO subRequestDTO_NameAndPhone : subRequestDTO_NameAndPhoneList)
					{
						if(subRequestDTO_NameAndPhone != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH ALL | FIRM-NameCountrycodeAndPhone SUBQUERY");
							results = searchGins(subRequestDTO_NameAndPhone);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameAndPhone);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
									{
										break rateCounterLoop;
									}
								}
							}
						}
					}
				}
				
				if(rateCounter == calculateRateDSBean.getRateFirmStrictNameCountrycodeEmail())
				{
					List<RequestDTO> subRequestDTO_NameAndEmailList = requestDTOBuilder.build_NameCountrycodeAndEmail(requestDTO, "T");
					for(RequestDTO subRequestDTO_NameAndEmail : subRequestDTO_NameAndEmailList)
					{
						if(subRequestDTO_NameAndEmail != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH ALL | FIRM-NameCountrycodeAndEmail SUBQUERY");
							results = searchGins(subRequestDTO_NameAndEmail);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameAndEmail);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
									{
										break rateCounterLoop;
									}
								}
							}
						}
					}
				}
				
				if(rateCounter == calculateRateDSBean.getRateFirmLikeNameCountrycodeEmail())
				{
					List<RequestDTO> subRequestDTO_NameAndEmailList = requestDTOBuilder.build_NameCountrycodeAndEmail(requestDTO, "T");
					for(RequestDTO subRequestDTO_NameAndEmail : subRequestDTO_NameAndEmailList)
					{
						if(subRequestDTO_NameAndEmail != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH ALL | FIRM-NameCountrycodeAndEmail SUBQUERY");
							results = searchGins(subRequestDTO_NameAndEmail);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameAndEmail);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
									{
										break rateCounterLoop;
									}
								}
							}
						}
					}
				}
				
				if(rateCounter == calculateRateDSBean.getRateFirmStrictNameCountrycodeNbandstreetCityZipcode())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeNbandstreetCityZipcodeList = requestDTOBuilder.build_NameCountrycodeNbandstreetCityZipcode(requestDTO, "T");
					for(RequestDTO subRequestDTO_NameCountrycodeNbandstreetCityZipcode : subRequestDTO_NameCountrycodeNbandstreetCityZipcodeList)
					{
						if(subRequestDTO_NameCountrycodeNbandstreetCityZipcode != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH ALL | FIRM-NameCountrycodeNbandstreetCityZipcode SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycodeNbandstreetCityZipcode);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycodeNbandstreetCityZipcode);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
									{
										break rateCounterLoop;
									}
								}
							}
						}
					}
				}
				
				if(rateCounter == calculateRateDSBean.getRateFirmLikeNameCountrycodeNbandstreetCityZipcode())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeNbandstreetCityZipcodeList = requestDTOBuilder.build_NameCountrycodeNbandstreetCityZipcode(requestDTO, "T");
					for(RequestDTO subRequestDTO_NameCountrycodeNbandstreetCityZipcode : subRequestDTO_NameCountrycodeNbandstreetCityZipcodeList)
					{
						if(subRequestDTO_NameCountrycodeNbandstreetCityZipcode != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH ALL | FIRM-NameCountrycodeNbandstreetCityZipcode SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycodeNbandstreetCityZipcode);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycodeNbandstreetCityZipcode);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
									{
										break rateCounterLoop;
									}
								}
							}
						}
					}
				}
				
				if(rateCounter == calculateRateDSBean.getRateFirmStrictNameCountrycodeNbandstreetCity())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeNbandstreetCityList = requestDTOBuilder.build_NameCountrycodeNbandstreetCity(requestDTO, "T");
					for(RequestDTO subRequestDTO_NameCountrycodeNbandstreetCity : subRequestDTO_NameCountrycodeNbandstreetCityList)
					{
						if(subRequestDTO_NameCountrycodeNbandstreetCity != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH ALL | FIRM-NameCountrycodeNbandstreetCity SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycodeNbandstreetCity);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycodeNbandstreetCity);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
									{
										break rateCounterLoop;
									}
								}
							}
						}
					}
				}
				
				if(rateCounter == calculateRateDSBean.getRateFirmLikeNameCountrycodeNbandstreetCity())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeNbandstreetCityList = requestDTOBuilder.build_NameCountrycodeNbandstreetCity(requestDTO, "T");
					for(RequestDTO subRequestDTO_NameCountrycodeNbandstreetCity : subRequestDTO_NameCountrycodeNbandstreetCityList)
					{
						if(subRequestDTO_NameCountrycodeNbandstreetCity != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH ALL | FIRM-NameCountrycodeNbandstreetCity SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycodeNbandstreetCity);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycodeNbandstreetCity);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
									{
										break rateCounterLoop;
									}
								}
							}
						}
					}
				}
				
				if(rateCounter == calculateRateDSBean.getRateFirmStrictNameCountrycodeCityZipcode())
				{
					List<RequestDTO> subRequestDTO_NameCountryCityAndZipList = requestDTOBuilder.build_NameCountrycodeCityAndZip(requestDTO, "T");
					for(RequestDTO subRequestDTO_NameCountryCityAndZip : subRequestDTO_NameCountryCityAndZipList)
					{
						if(subRequestDTO_NameCountryCityAndZip != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH ALL | FIRM-NameCountrycodeCityAndZip SUBQUERY");
							results = searchGins(subRequestDTO_NameCountryCityAndZip);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountryCityAndZip);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
									{
										break rateCounterLoop;
									}
								}
							}
						}
					}
				}
				
				if(rateCounter == calculateRateDSBean.getRateFirmLikeNameCountrycodeCityZipcode())
				{
					List<RequestDTO> subRequestDTO_NameCountryCityAndZipList = requestDTOBuilder.build_NameCountrycodeCityAndZip(requestDTO, "T");
					for(RequestDTO subRequestDTO_NameCountryCityAndZip : subRequestDTO_NameCountryCityAndZipList)
					{
						if(subRequestDTO_NameCountryCityAndZip != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH ALL | FIRM-NameCountrycodeCityAndZip SUBQUERY");
							results = searchGins(subRequestDTO_NameCountryCityAndZip);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountryCityAndZip);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
									{
										break rateCounterLoop;
									}
								}
							}
						}
					}
				}
				
				if(rateCounter == calculateRateDSBean.getRateFirmStrictNameCountrycodeZC14())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeZC14List = requestDTOBuilder.build_NameCountrycodeZC14(requestDTO, "T");
					for(RequestDTO subRequestDTO_NameCountrycodeZC14 : subRequestDTO_NameCountrycodeZC14List)
					{
						if(subRequestDTO_NameCountrycodeZC14 != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH ALL | FIRM-NameCountrycodeZC14 SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycodeZC14);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycodeZC14);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
									{
										break rateCounterLoop;
									}
								}
							}
						}
					}
				}
				
				if(rateCounter == calculateRateDSBean.getRateFirmLikeNameCountrycodeZC14())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeZC14List = requestDTOBuilder.build_NameCountrycodeZC14(requestDTO, "T");
					for(RequestDTO subRequestDTO_NameCountrycodeZC14 : subRequestDTO_NameCountrycodeZC14List)
					{
						if(subRequestDTO_NameCountrycodeZC14 != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH ALL | FIRM-NameCountrycodeZC14 SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycodeZC14);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycodeZC14);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
									{
										break rateCounterLoop;
									}
								}
							}
						}
					}
				}
				
				if(rateCounter == calculateRateDSBean.getRateFirmStrictNameCountrycodeZC15())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeZC15List = requestDTOBuilder.build_NameCountrycodeZC15(requestDTO, "T");
					for(RequestDTO subRequestDTO_NameCountrycodeZC15 : subRequestDTO_NameCountrycodeZC15List)
					{
						if(subRequestDTO_NameCountrycodeZC15 != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH ALL | FIRM-NameCountrycodeZC15 SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycodeZC15);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycodeZC15);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
									{
										break rateCounterLoop;
									}
								}
							}
						}
					}
				}
				
				if(rateCounter == calculateRateDSBean.getRateFirmLikeNameCountrycodeZC15())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeZC15List = requestDTOBuilder.build_NameCountrycodeZC15(requestDTO, "T");
					for(RequestDTO subRequestDTO_NameCountrycodeZC15 : subRequestDTO_NameCountrycodeZC15List)
					{
						if(subRequestDTO_NameCountrycodeZC15 != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH ALL | FIRM-NameCountrycodeZC15 SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycodeZC15);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycodeZC15);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
									{
										break rateCounterLoop;
									}
								}
							}
						}
					}
				}
				
				if(rateCounter == calculateRateDSBean.getRateFirmStrictNameCountrycodeIdent())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeIdentList = requestDTOBuilder.build_NameCountryAndIdent(requestDTO, "T");
					for(RequestDTO subRequestDTO_NameCountrycodeIdent : subRequestDTO_NameCountrycodeIdentList)
					{
						if(subRequestDTO_NameCountrycodeIdent != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH ALL | FIRM-NameCountrycodeIdent SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycodeIdent);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycodeIdent);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
									{
										break rateCounterLoop;
									}
								}
							}
						}
					}
				}
				
				if(rateCounter == calculateRateDSBean.getRateFirmLikeNameCountrycodeIdent())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeIdentList = requestDTOBuilder.build_NameCountryAndIdent(requestDTO, "T");
					for(RequestDTO subRequestDTO_NameCountrycodeIdent : subRequestDTO_NameCountrycodeIdentList)
					{
						if(subRequestDTO_NameCountrycodeIdent != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH ALL | FIRM-NameCountrycodeIdent SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycodeIdent);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycodeIdent);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
									{
										break rateCounterLoop;
									}
								}
							}
						}
					}
				}
				
				if(rateCounter == calculateRateDSBean.getRateFirmStrictNameCountrycode())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeList = requestDTOBuilder.build_NameCountry(requestDTO, "T");
					for(RequestDTO subRequestDTO_NameCountrycode : subRequestDTO_NameCountrycodeList)
					{
						if(subRequestDTO_NameCountrycode != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH ALL | FIRM-NameCountrycode SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycode);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycode);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
									{
										break rateCounterLoop;
									}
								}
							}
						}
					}
				}
				
				if(rateCounter == calculateRateDSBean.getRateFirmLikeNameCountrycode())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeList = requestDTOBuilder.build_NameCountry(requestDTO, "T");
					for(RequestDTO subRequestDTO_NameCountrycode : subRequestDTO_NameCountrycodeList)
					{
						if(subRequestDTO_NameCountrycode != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH ALL | FIRM-NameCountrycode SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycode);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycode);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
									{
										break rateCounterLoop;
									}
								}
							}
						}
					}
				}
				
				if(rateCounter == calculateRateDSBean.getRateFirmPhone())
				{
					RequestDTO subRequestDTO_Phone = requestDTOBuilder.build_Phone(requestDTO, "T");
					if(subRequestDTO_Phone != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH ALL | FIRM-Phone SUBQUERY");
						results = searchGins(subRequestDTO_Phone);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_Phone);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
								{
									break rateCounterLoop;
								}
							}
						}
					}
				}
				if(rateCounter == calculateRateDSBean.getRateFirmEmail())
				{
					RequestDTO subRequestDTO_Email = requestDTOBuilder.build_Email(requestDTO, "T");
					if(subRequestDTO_Email != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH ALL | FIRM-Email SUBQUERY");
						results = searchGins(subRequestDTO_Email);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_Email);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
								{
									break rateCounterLoop;
								}
							}
						}
					}
				}
				if(rateCounter == calculateRateDSBean.getRateFirmPhoneIdent())
				{
					RequestDTO subRequestDTO_PhoneAndIdent = requestDTOBuilder.build_PhoneAndIdent(requestDTO, "T");
					if(subRequestDTO_PhoneAndIdent != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH ALL | FIRM-PhoneAndIdent SUBQUERY");
						results = searchGins(subRequestDTO_PhoneAndIdent);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_PhoneAndIdent);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
								{
									break rateCounterLoop;
								}
							}
						}
					}
				}
				if(rateCounter == calculateRateDSBean.getRateFirmEmailIdent())
				{
					RequestDTO subRequestDTO_EmailAndIdent = requestDTOBuilder.build_EmailAndIdent(requestDTO, "T");
					if(subRequestDTO_EmailAndIdent != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH ALL | FIRM-EmailAndIdent SUBQUERY");
						results = searchGins(subRequestDTO_EmailAndIdent);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_EmailAndIdent);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
								{
									break rateCounterLoop;
								}
							}
						}
					}
				}
				if(rateCounter == calculateRateDSBean.getRateFirmIdent())
				{
					RequestDTO subRequestDTO_Ident = requestDTOBuilder.build_Ident(requestDTO, "T");
					if(subRequestDTO_Ident != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH ALL | FIRM-Ident SUBQUERY");
						results = searchGins(subRequestDTO_Ident);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_Ident);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
								{
									break rateCounterLoop;
								}
							}
						}
					}
				}
				
				if(rateCounter == calculateRateDSBean.getRateFirmCountrycode())
				{
					RequestDTO subRequestDTO_Countrycode = requestDTOBuilder.build_Countrycode(requestDTO, "T");
					if(subRequestDTO_Countrycode != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH ALL | SERVICE-Countrycode SUBQUERY");
						results = searchGins(subRequestDTO_Countrycode);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_Countrycode);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
								{
									break rateCounterLoop;
								}
							}
						}
					}
				}
				
				if(rateCounter == calculateRateDSBean.getRateFirmZC5())
				{
					RequestDTO subRequestDTO_ZC5 = requestDTOBuilder.build_ZC5(requestDTO, "T");
					if(subRequestDTO_ZC5 != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH ALL | SERVICE-ZC5 SUBQUERY");
						results = searchGins(subRequestDTO_ZC5);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_ZC5);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
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

	
	/**
	 * Executes a manual search for firms
	 * @param requestDTO
	 * @return
	 * @throws BusinessException
	 */
	private Set<SearchResultDTO> executeManualSearchForFirms(RequestDTO requestDTO) throws BusinessException
	{
		LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH FIRMS");
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
			int maxRate = calculateRateDSBean.getMaxFirmRate();
						
			rateCounterLoop:
			for(int rateCounter = maxRate; rateCounter >= 0; rateCounter -= 1)
			{
				if(rateCounter == calculateRateDSBean.getRateFirmStrictName() || rateCounter == calculateRateDSBean.getRateFirmLikeName())
				{
					List<RequestDTO> subRequestDTO_NameList = requestDTOBuilder.build_Name(requestDTO, "T");
					for(RequestDTO subRequestDTO_Name : subRequestDTO_NameList)
					{
						if(subRequestDTO_Name != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH FIRMS | FIRM-Name SUBQUERY");
							results = searchGins(subRequestDTO_Name);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_Name);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
									{
										break rateCounterLoop;
									}
								}
							}
						}
					}
				}
				if(rateCounter == calculateRateDSBean.getRateFirmStrictNameCountrycodePhone())
				{
					List<RequestDTO> subRequestDTO_NameAndPhoneList = requestDTOBuilder.build_NameCountrycodeAndPhone(requestDTO, "T");
					for(RequestDTO subRequestDTO_NameAndPhone : subRequestDTO_NameAndPhoneList)
					{
						if(subRequestDTO_NameAndPhone != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH FIRMS | FIRM-NameCountrycodeAndPhone SUBQUERY");
							results = searchGins(subRequestDTO_NameAndPhone);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameAndPhone);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
									{
										break rateCounterLoop;
									}
								}
							}
						}
					}
				}
				
				if(rateCounter == calculateRateDSBean.getRateFirmLikeNameCountrycodePhone())
				{
					List<RequestDTO> subRequestDTO_NameAndPhoneList = requestDTOBuilder.build_NameCountrycodeAndPhone(requestDTO, "T");
					for(RequestDTO subRequestDTO_NameAndPhone : subRequestDTO_NameAndPhoneList)
					{
						if(subRequestDTO_NameAndPhone != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH FIRMS | FIRM-NameCountrycodeAndPhone SUBQUERY");
							results = searchGins(subRequestDTO_NameAndPhone);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameAndPhone);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
									{
										break rateCounterLoop;
									}
								}
							}
						}
					}
				}
				
				if(rateCounter == calculateRateDSBean.getRateFirmStrictNameCountrycodeEmail())
				{
					List<RequestDTO> subRequestDTO_NameAndEmailList = requestDTOBuilder.build_NameCountrycodeAndEmail(requestDTO, "T");
					for(RequestDTO subRequestDTO_NameAndEmail : subRequestDTO_NameAndEmailList)
					{
						if(subRequestDTO_NameAndEmail != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH FIRMS | FIRM-NameCountrycodeAndEmail SUBQUERY");
							results = searchGins(subRequestDTO_NameAndEmail);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameAndEmail);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
									{
										break rateCounterLoop;
									}
								}
							}
						}
					}
				}
				
				if(rateCounter == calculateRateDSBean.getRateFirmLikeNameCountrycodeEmail())
				{
					List<RequestDTO> subRequestDTO_NameAndEmailList = requestDTOBuilder.build_NameCountrycodeAndEmail(requestDTO, "T");
					for(RequestDTO subRequestDTO_NameAndEmail : subRequestDTO_NameAndEmailList)
					{
						if(subRequestDTO_NameAndEmail != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH FIRMS | FIRM-NameCountrycodeAndEmail SUBQUERY");
							results = searchGins(subRequestDTO_NameAndEmail);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameAndEmail);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
									{
										break rateCounterLoop;
									}
								}
							}
						}
					}
				}
				
				if(rateCounter == calculateRateDSBean.getRateFirmStrictNameCountrycodeNbandstreetCityZipcode())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeNbandstreetCityZipcodeList = requestDTOBuilder.build_NameCountrycodeNbandstreetCityZipcode(requestDTO, "T");
					for(RequestDTO subRequestDTO_NameCountrycodeNbandstreetCityZipcode : subRequestDTO_NameCountrycodeNbandstreetCityZipcodeList)
					{
						if(subRequestDTO_NameCountrycodeNbandstreetCityZipcode != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH FIRMS | FIRM-NameCountrycodeNbandstreetCityZipcode SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycodeNbandstreetCityZipcode);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycodeNbandstreetCityZipcode);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
									{
										break rateCounterLoop;
									}
								}
							}
						}
					}
				}
				
				if(rateCounter == calculateRateDSBean.getRateFirmLikeNameCountrycodeNbandstreetCityZipcode())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeNbandstreetCityZipcodeList = requestDTOBuilder.build_NameCountrycodeNbandstreetCityZipcode(requestDTO, "T");
					for(RequestDTO subRequestDTO_NameCountrycodeNbandstreetCityZipcode : subRequestDTO_NameCountrycodeNbandstreetCityZipcodeList)
					{
						if(subRequestDTO_NameCountrycodeNbandstreetCityZipcode != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH FIRMS | FIRM-NameCountrycodeNbandstreetCityZipcode SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycodeNbandstreetCityZipcode);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycodeNbandstreetCityZipcode);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
									{
										break rateCounterLoop;
									}
								}
							}
						}
					}
				}
				
				if(rateCounter == calculateRateDSBean.getRateFirmStrictNameCountrycodeNbandstreetCity())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeNbandstreetCityList = requestDTOBuilder.build_NameCountrycodeNbandstreetCity(requestDTO, "T");
					for(RequestDTO subRequestDTO_NameCountrycodeNbandstreetCity : subRequestDTO_NameCountrycodeNbandstreetCityList)
					{
						if(subRequestDTO_NameCountrycodeNbandstreetCity != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH FIRMS | FIRM-NameCountrycodeNbandstreetCity SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycodeNbandstreetCity);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycodeNbandstreetCity);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
									{
										break rateCounterLoop;
									}
								}
							}
						}
					}
				}
				
				if(rateCounter == calculateRateDSBean.getRateFirmLikeNameCountrycodeNbandstreetCity())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeNbandstreetCityList = requestDTOBuilder.build_NameCountrycodeNbandstreetCity(requestDTO, "T");
					for(RequestDTO subRequestDTO_NameCountrycodeNbandstreetCity : subRequestDTO_NameCountrycodeNbandstreetCityList)
					{
						if(subRequestDTO_NameCountrycodeNbandstreetCity != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH FIRMS | FIRM-NameCountrycodeNbandstreetCity SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycodeNbandstreetCity);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycodeNbandstreetCity);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
									{
										break rateCounterLoop;
									}
								}
							}
						}
					}
				}
				
				if(rateCounter == calculateRateDSBean.getRateFirmStrictNameCountrycodeCityZipcode())
				{
					List<RequestDTO> subRequestDTO_NameCountryCityAndZipList = requestDTOBuilder.build_NameCountrycodeCityAndZip(requestDTO, "T");
					for(RequestDTO subRequestDTO_NameCountryCityAndZip : subRequestDTO_NameCountryCityAndZipList)
					{
						if(subRequestDTO_NameCountryCityAndZip != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH FIRMS | FIRM-NameCountrycodeCityAndZip SUBQUERY");
							results = searchGins(subRequestDTO_NameCountryCityAndZip);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountryCityAndZip);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
									{
										break rateCounterLoop;
									}
								}
							}
						}
					}
				}
				
				if(rateCounter == calculateRateDSBean.getRateFirmLikeNameCountrycodeCityZipcode())
				{
					List<RequestDTO> subRequestDTO_NameCountryCityAndZipList = requestDTOBuilder.build_NameCountrycodeCityAndZip(requestDTO, "T");
					for(RequestDTO subRequestDTO_NameCountryCityAndZip : subRequestDTO_NameCountryCityAndZipList)
					{
						if(subRequestDTO_NameCountryCityAndZip != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH FIRMS | FIRM-NameCountrycodeCityAndZip SUBQUERY");
							results = searchGins(subRequestDTO_NameCountryCityAndZip);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountryCityAndZip);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
									{
										break rateCounterLoop;
									}
								}
							}
						}
					}
				}
				
				if(rateCounter == calculateRateDSBean.getRateFirmStrictNameCountrycodeZC14())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeZC14List = requestDTOBuilder.build_NameCountrycodeZC14(requestDTO, "T");
					for(RequestDTO subRequestDTO_NameCountrycodeZC14 : subRequestDTO_NameCountrycodeZC14List)
					{
						if(subRequestDTO_NameCountrycodeZC14 != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH FIRMS | FIRM-NameCountrycodeZC14 SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycodeZC14);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycodeZC14);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
									{
										break rateCounterLoop;
									}
								}
							}
						}
					}
				}
				
				if(rateCounter == calculateRateDSBean.getRateFirmLikeNameCountrycodeZC14())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeZC14List = requestDTOBuilder.build_NameCountrycodeZC14(requestDTO, "T");
					for(RequestDTO subRequestDTO_NameCountrycodeZC14 : subRequestDTO_NameCountrycodeZC14List)
					{
						if(subRequestDTO_NameCountrycodeZC14 != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH FIRMS | FIRM-NameCountrycodeZC14 SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycodeZC14);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycodeZC14);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
									{
										break rateCounterLoop;
									}
								}
							}
						}
					}
				}
				
				if(rateCounter == calculateRateDSBean.getRateFirmStrictNameCountrycodeZC15())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeZC15List = requestDTOBuilder.build_NameCountrycodeZC15(requestDTO, "T");
					for(RequestDTO subRequestDTO_NameCountrycodeZC15 : subRequestDTO_NameCountrycodeZC15List)
					{
						if(subRequestDTO_NameCountrycodeZC15 != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH FIRMS | FIRM-NameCountrycodeZC15 SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycodeZC15);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycodeZC15);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
									{
										break rateCounterLoop;
									}
								}
							}
						}
					}
				}
				
				if(rateCounter == calculateRateDSBean.getRateFirmLikeNameCountrycodeZC15())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeZC15List = requestDTOBuilder.build_NameCountrycodeZC15(requestDTO, "T");
					for(RequestDTO subRequestDTO_NameCountrycodeZC15 : subRequestDTO_NameCountrycodeZC15List)
					{
						if(subRequestDTO_NameCountrycodeZC15 != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH FIRMS | FIRM-NameCountrycodeZC15 SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycodeZC15);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycodeZC15);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
									{
										break rateCounterLoop;
									}
								}
							}
						}
					}
				}
				
				if(rateCounter == calculateRateDSBean.getRateFirmStrictNameCountrycodeIdent())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeIdentList = requestDTOBuilder.build_NameCountryAndIdent(requestDTO, "T");
					for(RequestDTO subRequestDTO_NameCountrycodeIdent : subRequestDTO_NameCountrycodeIdentList)
					{
						if(subRequestDTO_NameCountrycodeIdent != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH FIRMS | FIRM-NameCountryAndIdent SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycodeIdent);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycodeIdent);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
									{
										break rateCounterLoop;
									}
								}
							}
						}
					}
				}
				
				if(rateCounter == calculateRateDSBean.getRateFirmLikeNameCountrycodeIdent())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeIdentList = requestDTOBuilder.build_NameCountryAndIdent(requestDTO, "T");
					for(RequestDTO subRequestDTO_NameCountrycodeIdent : subRequestDTO_NameCountrycodeIdentList)
					{
						if(subRequestDTO_NameCountrycodeIdent != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH FIRMS | FIRM-NameCountryAndIdent SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycodeIdent);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycodeIdent);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
									{
										break rateCounterLoop;
									}
								}
							}
						}
					}
				}
				
				if(rateCounter == calculateRateDSBean.getRateFirmStrictNameCountrycode())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeList = requestDTOBuilder.build_NameCountry(requestDTO, "T");
					for(RequestDTO subRequestDTO_NameCountrycode : subRequestDTO_NameCountrycodeList)
					{
						if(subRequestDTO_NameCountrycode != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH FIRMS | FIRM-NameCountrycode SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycode);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycode);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
									{
										break rateCounterLoop;
									}
								}
							}
						}
					}
				}
				
				if(rateCounter == calculateRateDSBean.getRateFirmLikeNameCountrycode())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeList = requestDTOBuilder.build_NameCountry(requestDTO, "T");
					for(RequestDTO subRequestDTO_NameCountrycode : subRequestDTO_NameCountrycodeList)
					{
						if(subRequestDTO_NameCountrycode != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH FIRMS | FIRM-NameCountrycode SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycode);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycode);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
									{
										break rateCounterLoop;
									}
								}
							}
						}
					}
				}
				
				if(rateCounter == calculateRateDSBean.getRateFirmPhone())
				{
					RequestDTO subRequestDTO_Phone = requestDTOBuilder.build_Phone(requestDTO, "T");
					if(subRequestDTO_Phone != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH FIRMS | FIRM-Phone SUBQUERY");
						results = searchGins(subRequestDTO_Phone);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_Phone);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
								{
									break rateCounterLoop;
								}
							}
						}
					}
				}
				if(rateCounter == calculateRateDSBean.getRateFirmEmail())
				{
					RequestDTO subRequestDTO_Email = requestDTOBuilder.build_Email(requestDTO, "T");
					if(subRequestDTO_Email != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH FIRMS | FIRM-Email SUBQUERY");
						results = searchGins(subRequestDTO_Email);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_Email);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
								{
									break rateCounterLoop;
								}
							}
						}
					}
				}
				if(rateCounter == calculateRateDSBean.getRateFirmPhoneIdent())
				{
					RequestDTO subRequestDTO_PhoneAndIdent = requestDTOBuilder.build_PhoneAndIdent(requestDTO, "T");
					if(subRequestDTO_PhoneAndIdent != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH FIRMS | FIRM-PhoneAndIdent SUBQUERY");
						results = searchGins(subRequestDTO_PhoneAndIdent);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_PhoneAndIdent);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
								{
									break rateCounterLoop;
								}
							}
						}
					}
				}
				if(rateCounter == calculateRateDSBean.getRateFirmEmailIdent())
				{
					RequestDTO subRequestDTO_EmailAndIdent = requestDTOBuilder.build_EmailAndIdent(requestDTO, "T");
					if(subRequestDTO_EmailAndIdent != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH FIRMS | FIRM-EmailAndIdent SUBQUERY");
						results = searchGins(subRequestDTO_EmailAndIdent);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_EmailAndIdent);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
								{
									break rateCounterLoop;
								}
							}
						}
					}
				}
				if(rateCounter == calculateRateDSBean.getRateFirmIdent())
				{
					RequestDTO subRequestDTO_Ident = requestDTOBuilder.build_Ident(requestDTO, "T");
					if(subRequestDTO_Ident != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH FIRMS | FIRM-Ident SUBQUERY");
						results = searchGins(subRequestDTO_Ident);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_Ident);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
								{
									break rateCounterLoop;
								}
							}
						}
					}
				}
				
				if(rateCounter == calculateRateDSBean.getRateFirmCountrycode())
				{
					RequestDTO subRequestDTO_Countrycode = requestDTOBuilder.build_Countrycode(requestDTO, "T");
					if(subRequestDTO_Countrycode != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH ALL | SERVICE-Countrycode SUBQUERY");
						results = searchGins(subRequestDTO_Countrycode);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_Countrycode);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
								{
									break rateCounterLoop;
								}
							}
						}
					}
				}
				
				if(rateCounter == calculateRateDSBean.getRateFirmZC5())
				{
					RequestDTO subRequestDTO_ZC5 = requestDTOBuilder.build_ZC5(requestDTO, "T");
					if(subRequestDTO_ZC5 != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH ALL | SERVICE-ZC5 SUBQUERY");
						results = searchGins(subRequestDTO_ZC5);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_ZC5);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
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
	
	
	/**
	 * Executes a manual search for Services
	 * @param requestDTO
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	private Set<SearchResultDTO> executeManualSearchForServices(RequestDTO requestDTO) throws BusinessException
	{
		LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH SERVICES");
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
			int maxRate = calculateRateDSBean.getMaxServiceRate();
						
			rateCounterLoop:
			for(int rateCounter = maxRate; rateCounter >= 0; rateCounter -= 1)
			{
				if(rateCounter == calculateRateDSBean.getRateServiceStrictName() || rateCounter == calculateRateDSBean.getRateServiceLikeName())
				{
					List<RequestDTO> subRequestDTO_NameList = requestDTOBuilder.build_Name(requestDTO, "S");
					for(RequestDTO subRequestDTO_Name : subRequestDTO_NameList)
					{
						if(subRequestDTO_Name != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH SERVICES | SERVICE-Name SUBQUERY");
							results = searchGins(subRequestDTO_Name);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_Name);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
									{
										break rateCounterLoop;
									}
								}
							}
						}
					}
				}
				if(rateCounter == calculateRateDSBean.getRateServiceStrictNameCountrycodePhone())
				{
					List<RequestDTO> subRequestDTO_NameAndPhoneList = requestDTOBuilder.build_NameCountrycodeAndPhone(requestDTO, "S");
					for(RequestDTO subRequestDTO_NameAndPhone : subRequestDTO_NameAndPhoneList)
					{
						if(subRequestDTO_NameAndPhone != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH SERVICES | NameCountrycodeAndPhone SUBQUERY");
							results = searchGins(subRequestDTO_NameAndPhone);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameAndPhone);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
									{
										break rateCounterLoop;
									}
								}
							}
						}
					}
				}
				
				if(rateCounter == calculateRateDSBean.getRateServiceLikeNameCountrycodePhone())
				{
					List<RequestDTO> subRequestDTO_NameAndPhoneList = requestDTOBuilder.build_NameCountrycodeAndPhone(requestDTO, "S");
					for(RequestDTO subRequestDTO_NameAndPhone : subRequestDTO_NameAndPhoneList)
					{
						if(subRequestDTO_NameAndPhone != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH SERVICES | NameCountrycodeAndPhone SUBQUERY");
							results = searchGins(subRequestDTO_NameAndPhone);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameAndPhone);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
									{
										break rateCounterLoop;
									}
								}
							}
						}
					}
				}
				
				if(rateCounter == calculateRateDSBean.getRateServiceStrictNameCountrycodeEmail())
				{
					List<RequestDTO> subRequestDTO_NameAndEmailList = requestDTOBuilder.build_NameCountrycodeAndEmail(requestDTO, "S");
					for(RequestDTO subRequestDTO_NameAndEmail : subRequestDTO_NameAndEmailList)
					{
						if(subRequestDTO_NameAndEmail != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH SERVICES | NameCountrycodeAndEmail SUBQUERY");
							results = searchGins(subRequestDTO_NameAndEmail);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameAndEmail);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
									{
										break rateCounterLoop;
									}
								}
							}
						}
					}
				}
				
				if(rateCounter == calculateRateDSBean.getRateServiceLikeNameCountrycodeEmail())
				{
					List<RequestDTO> subRequestDTO_NameAndEmailList = requestDTOBuilder.build_NameCountrycodeAndEmail(requestDTO, "S");
					for(RequestDTO subRequestDTO_NameAndEmail : subRequestDTO_NameAndEmailList)
					{
						if(subRequestDTO_NameAndEmail != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH SERVICES | NameCountrycodeAndEmail SUBQUERY");
							results = searchGins(subRequestDTO_NameAndEmail);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameAndEmail);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
									{
										break rateCounterLoop;
									}
								}
							}
						}
					}
				}
				
				if(rateCounter == calculateRateDSBean.getRateServiceStrictNameCountrycodeNbandstreetCityZipcode())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeNbandstreetCityZipcodeList = requestDTOBuilder.build_NameCountrycodeNbandstreetCityZipcode(requestDTO, "S");
					for(RequestDTO subRequestDTO_NameCountrycodeNbandstreetCityZipcode : subRequestDTO_NameCountrycodeNbandstreetCityZipcodeList)
					{
						if(subRequestDTO_NameCountrycodeNbandstreetCityZipcode != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH SERVICES | NameCountrycodeNbandstreetCityZipcode SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycodeNbandstreetCityZipcode);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycodeNbandstreetCityZipcode);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
									{
										break rateCounterLoop;
									}
								}
							}
						}
					}
				}
				
				if(rateCounter == calculateRateDSBean.getRateServiceLikeNameCountrycodeNbandstreetCityZipcode())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeNbandstreetCityZipcodeList = requestDTOBuilder.build_NameCountrycodeNbandstreetCityZipcode(requestDTO, "S");
					for(RequestDTO subRequestDTO_NameCountrycodeNbandstreetCityZipcode : subRequestDTO_NameCountrycodeNbandstreetCityZipcodeList)
					{
						if(subRequestDTO_NameCountrycodeNbandstreetCityZipcode != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH SERVICES | NameCountrycodeNbandstreetCityZipcode SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycodeNbandstreetCityZipcode);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycodeNbandstreetCityZipcode);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
									{
										break rateCounterLoop;
									}
								}
							}
						}
					}
				}
				
				if(rateCounter == calculateRateDSBean.getRateServiceStrictNameCountrycodeNbandstreetCity())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeNbandstreetCityList = requestDTOBuilder.build_NameCountrycodeNbandstreetCity(requestDTO, "S");
					for(RequestDTO subRequestDTO_NameCountrycodeNbandstreetCity : subRequestDTO_NameCountrycodeNbandstreetCityList)
					{
						if(subRequestDTO_NameCountrycodeNbandstreetCity != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH SERVICES | NameCountrycodeNbandstreetCity SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycodeNbandstreetCity);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycodeNbandstreetCity);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
									{
										break rateCounterLoop;
									}
								}
							}
						}
					}
				}
				
				if(rateCounter == calculateRateDSBean.getRateServiceLikeNameCountrycodeNbandstreetCity())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeNbandstreetCityList = requestDTOBuilder.build_NameCountrycodeNbandstreetCity(requestDTO, "S");
					for(RequestDTO subRequestDTO_NameCountrycodeNbandstreetCity : subRequestDTO_NameCountrycodeNbandstreetCityList)
					{
						if(subRequestDTO_NameCountrycodeNbandstreetCity != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH SERVICES | NameCountrycodeNbandstreetCity SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycodeNbandstreetCity);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycodeNbandstreetCity);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
									{
										break rateCounterLoop;
									}
								}
							}
						}
					}
				}
				
				if(rateCounter == calculateRateDSBean.getRateServiceStrictNameCountrycodeCityZipcode())
				{
					List<RequestDTO> subRequestDTO_NameCountryCityAndZipList = requestDTOBuilder.build_NameCountrycodeCityAndZip(requestDTO, "S");
					for(RequestDTO subRequestDTO_NameCountryCityAndZip : subRequestDTO_NameCountryCityAndZipList)
					{
						if(subRequestDTO_NameCountryCityAndZip != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH SERVICES | NameCountrycodeCityAndZip SUBQUERY");
							results = searchGins(subRequestDTO_NameCountryCityAndZip);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountryCityAndZip);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
									{
										break rateCounterLoop;
									}
								}
							}
						}
					}
				}
				
				if(rateCounter == calculateRateDSBean.getRateServiceLikeNameCountrycodeCityZipcode())
				{
					List<RequestDTO> subRequestDTO_NameCountryCityAndZipList = requestDTOBuilder.build_NameCountrycodeCityAndZip(requestDTO, "S");
					for(RequestDTO subRequestDTO_NameCountryCityAndZip : subRequestDTO_NameCountryCityAndZipList)
					{
						if(subRequestDTO_NameCountryCityAndZip != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH SERVICES | NameCountrycodeCityAndZip SUBQUERY");
							results = searchGins(subRequestDTO_NameCountryCityAndZip);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountryCityAndZip);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
									{
										break rateCounterLoop;
									}
								}
							}
						}
					}
				}
				
				
				if(rateCounter == calculateRateDSBean.getRateFirmLikeNameCityLikeZipLikeCountrycode())
				{
					RequestDTO subRequestDTO_NameLikeCityLikeCountrycodeList = requestDTOBuilder.build_NameLikeCityLikeZipLikeCountry(requestDTO, "T");
					if(subRequestDTO_NameLikeCityLikeCountrycodeList != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | AUTOMATIC SEARCH FIRMS | NameLikeCityLikeCountrycode SUBQUERY");
						results = searchGins(subRequestDTO_NameLikeCityLikeCountrycodeList);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameLikeCityLikeCountrycodeList);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
								{
									break rateCounterLoop;
								}
							}
							
						}
					}
				}
				if(rateCounter == calculateRateDSBean.getRateServicePhone())
				{
					RequestDTO subRequestDTO_Phone = requestDTOBuilder.build_Phone(requestDTO, "S");
					if(subRequestDTO_Phone != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH SERVICES | Phone SUBQUERY");
						results = searchGins(subRequestDTO_Phone);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_Phone);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
								{
									break rateCounterLoop;
								}
							}
						}
					}
				}
				if(rateCounter == calculateRateDSBean.getRateServiceEmail())
				{
					RequestDTO subRequestDTO_Email = requestDTOBuilder.build_Email(requestDTO, "S");
					if(subRequestDTO_Email != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH SERVICES | Email SUBQUERY");
						results = searchGins(subRequestDTO_Email);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_Email);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
								{
									break rateCounterLoop;
								}
							}
						}
					}
				}
				if(rateCounter == calculateRateDSBean.getRateServicePhoneIdent())
				{
					RequestDTO subRequestDTO_PhoneAndIdent = requestDTOBuilder.build_PhoneAndIdent(requestDTO, "S");
					if(subRequestDTO_PhoneAndIdent != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH SERVICES | PhoneAndIdent SUBQUERY");
						results = searchGins(subRequestDTO_PhoneAndIdent);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_PhoneAndIdent);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
								{
									break rateCounterLoop;
								}
							}
						}
					}
				}
				if(rateCounter == calculateRateDSBean.getRateServiceEmailIdent())
				{
					RequestDTO subRequestDTO_EmailAndIdent = requestDTOBuilder.build_EmailAndIdent(requestDTO, "S");
					if(subRequestDTO_EmailAndIdent != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH SERVICES | EmailAndIdent SUBQUERY");
						results = searchGins(subRequestDTO_EmailAndIdent);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_EmailAndIdent);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
								{
									break rateCounterLoop;
								}
							}
						}
					}
				}
				if(rateCounter == calculateRateDSBean.getRateServiceIdent())
				{
					RequestDTO subRequestDTO_Ident = requestDTOBuilder.build_Ident(requestDTO, "S");
					if(subRequestDTO_Ident != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH SERVICES | Ident SUBQUERY");
						results = searchGins(subRequestDTO_Ident);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_Ident);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
								{
									break rateCounterLoop;
								}
							}
						}
					}
				}
				
				if(rateCounter == calculateRateDSBean.getRateServiceCountrycode())
				{
					RequestDTO subRequestDTO_Countrycode = requestDTOBuilder.build_Countrycode(requestDTO, "S");
					if(subRequestDTO_Countrycode != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH ALL | SERVICE-Countrycode SUBQUERY");
						results = searchGins(subRequestDTO_Countrycode);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_Countrycode);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
								{
									break rateCounterLoop;
								}
							}
						}
					}
				}
				
				if(rateCounter == calculateRateDSBean.getRateServiceZC5())
				{
					RequestDTO subRequestDTO_ZC5 = requestDTOBuilder.build_ZC5(requestDTO, "S");
					if(subRequestDTO_ZC5 != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH ALL | SERVICE-ZC5 SUBQUERY");
						results = searchGins(subRequestDTO_ZC5);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_ZC5);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
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
	
	
	/**
	 * Executes a manual search for companies
	 * @param requestDTO
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	private Set<SearchResultDTO> executeManualSearchForCompanies(RequestDTO requestDTO) throws BusinessException
	{
		LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH COMPANIES");
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
			int maxRate = calculateRateDSBean.getMaxCompanyRate();
						
			rateCounterLoop:
			for(int rateCounter = maxRate; rateCounter >= 0; rateCounter -= 1)
			{
				if(rateCounter == calculateRateDSBean.getRateCompanyStrictName() || rateCounter == calculateRateDSBean.getRateCompanyLikeName())
				{
					List<RequestDTO> subRequestDTO_NameList = requestDTOBuilder.build_Name(requestDTO, "E");
					for(RequestDTO subRequestDTO_Name : subRequestDTO_NameList)
					{
						if(subRequestDTO_Name != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH COMPANIES | COMPAGNY-Name SUBQUERY");
							results = searchGins(subRequestDTO_Name);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_Name);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
									{
										break rateCounterLoop;
									}
								}
							}
						}
					}
				}
				if(rateCounter == calculateRateDSBean.getRateCompanyStrictNameCountrycodePhone())
				{
					List<RequestDTO> subRequestDTO_NameAndPhoneList = requestDTOBuilder.build_NameCountrycodeAndPhone(requestDTO, "E");
					for(RequestDTO subRequestDTO_NameAndPhone : subRequestDTO_NameAndPhoneList)
					{
						if(subRequestDTO_NameAndPhone != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH COMPANIES | NameCountrycodeAndPhone SUBQUERY");
							results = searchGins(subRequestDTO_NameAndPhone);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameAndPhone);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
									{
										break rateCounterLoop;
									}
								}
							}
						}
					}
				}
				
				if(rateCounter == calculateRateDSBean.getRateCompanyStrictNameCountrycodeEmail())
				{
					List<RequestDTO> subRequestDTO_NameAndEmailList = requestDTOBuilder.build_NameCountrycodeAndEmail(requestDTO, "E");
					for(RequestDTO subRequestDTO_NameAndEmail : subRequestDTO_NameAndEmailList)
					{
						if(subRequestDTO_NameAndEmail != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH COMPANIES | NameCountrycodeAndEmail SUBQUERY");
							results = searchGins(subRequestDTO_NameAndEmail);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameAndEmail);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
									{
										break rateCounterLoop;
									}
								}
							}
						}
					}
				}
				if(rateCounter == calculateRateDSBean.getRateCompanyStrictNameCountrycodeNbandstreetCityZipcode())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeNbandstreetCityZipcodeList = requestDTOBuilder.build_NameCountrycodeNbandstreetCityZipcode(requestDTO, "E");
					for(RequestDTO subRequestDTO_NameCountrycodeNbandstreetCityZipcode : subRequestDTO_NameCountrycodeNbandstreetCityZipcodeList)
					{
						if(subRequestDTO_NameCountrycodeNbandstreetCityZipcode != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH COMPANIES | NameCountrycodeNbandstreetCityZipcode SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycodeNbandstreetCityZipcode);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycodeNbandstreetCityZipcode);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
									{
										break rateCounterLoop;
									}
								}
							}
						}
					}
				}
				if(rateCounter == calculateRateDSBean.getRateCompanyStrictNameCountrycodeNbandstreetCity())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeNbandstreetCityList = requestDTOBuilder.build_NameCountrycodeNbandstreetCity(requestDTO, "E");
					for(RequestDTO subRequestDTO_NameCountrycodeNbandstreetCity : subRequestDTO_NameCountrycodeNbandstreetCityList)
					{
						if(subRequestDTO_NameCountrycodeNbandstreetCity != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH COMPANIES | NameCountrycodeNbandstreetCity SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycodeNbandstreetCity);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycodeNbandstreetCity);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
									{
										break rateCounterLoop;
									}
								}
							}
						}
					}
				}
				if(rateCounter == calculateRateDSBean.getRateCompanyStrictNameCountrycodeCityZipcode())
				{
					List<RequestDTO> subRequestDTO_NameCountryCityAndZipList = requestDTOBuilder.build_NameCountrycodeCityAndZip(requestDTO, "E");
					for(RequestDTO subRequestDTO_NameCountryCityAndZip : subRequestDTO_NameCountryCityAndZipList)
					{
						if(subRequestDTO_NameCountryCityAndZip != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH COMPANIES | NameCountrycodeCityAndZip SUBQUERY");
							results = searchGins(subRequestDTO_NameCountryCityAndZip);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountryCityAndZip);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
									{
										break rateCounterLoop;
									}
								}
							}
						}
					}
				}
				if(rateCounter == calculateRateDSBean.getRateCompanyStrictNameCountrycodeIdent())
				{
					List<RequestDTO> subRequestDTO_NameCountrycodeIdentList = requestDTOBuilder.build_NameCountryAndIdent(requestDTO, "E");
					for(RequestDTO subRequestDTO_NameCountrycodeIdent : subRequestDTO_NameCountrycodeIdentList)
					{
						if(subRequestDTO_NameCountrycodeIdent != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH COMPANIES | NameCountrycodeIdent SUBQUERY");
							results = searchGins(subRequestDTO_NameCountrycodeIdent);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_NameCountrycodeIdent);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
									{
										break rateCounterLoop;
									}
								}
							}
						}
					}
				}
				
				if(rateCounter == calculateRateDSBean.getRateCompanyCountrycode())
				{
					RequestDTO subRequestDTO_Countrycode = requestDTOBuilder.build_Countrycode(requestDTO, "E");
					if(subRequestDTO_Countrycode != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH ALL | SERVICE-Countrycode SUBQUERY");
						results = searchGins(subRequestDTO_Countrycode);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_Countrycode);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
								{
									break rateCounterLoop;
								}
							}
						}
					}
				}
				
				if(rateCounter == calculateRateDSBean.getRateCompanyZC5())
				{
					RequestDTO subRequestDTO_ZC5 = requestDTOBuilder.build_ZC5(requestDTO, "E");
					if(subRequestDTO_ZC5 != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH ALL | SERVICE-ZC5 SUBQUERY");
						results = searchGins(subRequestDTO_ZC5);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_ZC5);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
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
	
	
	/**
	 * Executes a manual search for Groups
	 * @param requestDTO
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	private Set<SearchResultDTO> executeManualSearchForGroups(RequestDTO requestDTO) throws BusinessException
	{
		LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH GROUPS");
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
			int maxRate = calculateRateDSBean.getMaxGroupRate();
						
			rateCounterLoop:
			for(int rateCounter = maxRate; rateCounter >= 0; rateCounter -= 1)
			{
				
				
				if(rateCounter == calculateRateDSBean.getRateGroupStrictName())
				{
					List<RequestDTO> subRequestDTO_NameList = requestDTOBuilder.build_Name(requestDTO, "G");
					for(RequestDTO subRequestDTO_Name : subRequestDTO_NameList)
					{
						if(subRequestDTO_Name != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH GROUPS | Name SUBQUERY");
							results = searchGins(subRequestDTO_Name);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_Name);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
									{
										break rateCounterLoop;
									}
								}
							}
						}
					}
				}
				
				if(rateCounter == calculateRateDSBean.getRateGroupLikeName())
				{
					List<RequestDTO> subRequestDTO_NameList = requestDTOBuilder.build_Name(requestDTO, "G");
					for(RequestDTO subRequestDTO_Name : subRequestDTO_NameList)
					{
						if(subRequestDTO_Name != null)
						{
							LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH GROUPS | Name SUBQUERY");
							results = searchGins(subRequestDTO_Name);
							if(results != null)
							{
								for(SearchResultDTO result : results)
								{
									calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_Name);
									resultsSet.add(result);
									if(resultsSet.size() >= maxResults)
									{
										break rateCounterLoop;
									}
								}
							}
						}
					}
				}
				
				if(rateCounter == calculateRateDSBean.getRateGroupCountrycode())
				{
					RequestDTO subRequestDTO_Countrycode = requestDTOBuilder.build_Countrycode(requestDTO, "G");
					if(subRequestDTO_Countrycode != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH ALL | SERVICE-Countrycode SUBQUERY");
						results = searchGins(subRequestDTO_Countrycode);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_Countrycode);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
								{
									break rateCounterLoop;
								}
							}
						}
					}
				}
				
				if(rateCounter == calculateRateDSBean.getRateGroupZC5())
				{
					RequestDTO subRequestDTO_ZC5 = requestDTOBuilder.build_ZC5(requestDTO, "G");
					if(subRequestDTO_ZC5 != null)
					{
						LOGGER.info("SearchFirmOnMultiCriteria | MANUAL SEARCH ALL | SERVICE-ZC5 SUBQUERY");
						results = searchGins(subRequestDTO_ZC5);
						if(results != null)
						{
							for(SearchResultDTO result : results)
							{
								calculateRateDSBean.calculateRateByFirmType(result, subRequestDTO_ZC5);
								resultsSet.add(result);
								if(resultsSet.size() >= maxResults)
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
	
	
	/**
	 * Get Firms' gins results
	 * @param requestDTO
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	private List<SearchResultDTO> searchGins(RequestDTO requestDTO) throws BusinessException
	{
		List<SearchResultDTO> results = null;
		results = searchFirmDAOBean.searchGinsAndTypes(requestDTO);
		if(results != null)
		{
			LOGGER.info("SearchFirmOnMultiCriteria | SUBQUERY RESULT SIZE | " + results.size());
		}
		return results;
	}
	
	
	/*==========================================*/
	/*                 ACCESSORS                */
	/*==========================================*/
	
	
	public RequestDTOBuilder getRequestDTOBuilder() {
		return requestDTOBuilder;
	}
	
	
	public void setRequestDTOBuilder(RequestDTOBuilder requestDTOBuilder) {
		this.requestDTOBuilder = requestDTOBuilder;
	}
	
	
	public SearchFirmDAO getSearchFirmDAOBean() {
		return searchFirmDAOBean;
	}
	
	
	public void setSearchFirmDAOBean(SearchFirmDAO searchFirmDAOBean) {
		this.searchFirmDAOBean = searchFirmDAOBean;
	}
	
	
	public CalculateRateDS getCalculateRateDSBean() {
		return calculateRateDSBean;
	}
	
	
	public void setCalculateRateDSBean(CalculateRateDS calculateRateDSBean) {
		this.calculateRateDSBean = calculateRateDSBean;
	}
	
	
	public ICheckMandatoryInputsDS getCheckMandatoryInputsDSBean() {
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


	public ResponseDTO searchFirmV2(RequestDTO requestDTO) throws BusinessException {
		
		checkInputsV2(requestDTO);
		
		LOGGER.info("SearchFirmOnMultiCriteria | NORMALIZING PHONE NUMBER");
		normalizePhoneNumber(requestDTO);
		LOGGER.info("SearchFirmOnMultiCriteria | BUILDING QUERY");
		buildQueryDSBean.buildQueryV2(requestDTO);
		LOGGER.info("SearchFirmOnMultiCriteria | SEARCH START");
		ResponseDTO responseDTO = executeSearchV2(requestDTO);
		LOGGER.info("SearchFirmOnMultiCriteria | SEARCH END AND TOKEN HANDLING");
		handleToken(requestDTO, responseDTO);
		
		return responseDTO;
	}


	private void checkInputsV2(RequestDTO requestDTO) throws BusinessException {
		
		if (!BuildConditionsDS.isIndexConditionSet(requestDTO))
		{
			throwsException(getIncoherentIndexCode(), getIncoherentIndexMessage());
		}
		
		if(!BuildConditionsDS.isProcessTypeConditionSet(requestDTO))
		{
			throwsException(getMissingProcessTypeCode(), getMissingProcessTypeMessage());
		}			
		
		checkMandatoryInputsDSBean.handleMissingNameSearchType(requestDTO);
		checkMandatoryInputsDSBean.handleMissingFirmType(requestDTO);
		checkMandatoryInputsDSBean.handleMissingIdentValue(requestDTO);
		checkMandatoryInputsDSBean.handleIncoherentFirmAndIdentType(requestDTO);
		checkMandatoryInputsDSBean.handleMissingRequestor(requestDTO);
		checkMandatoryInputsDSBean.handleMissingParametersAccordingFirmType(requestDTO);
		checkMandatoryInputsDSBean.handleMissingZc(requestDTO);
		
	}
}
