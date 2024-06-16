package com.afklm.repind.msv.mapping.criteria;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

import com.afklm.repind.msv.mapping.model.error.BusinessErrorList;
import com.afklm.repind.msv.mapping.services.exception.ServiceException;

/**
 * Exceptional event criteria POJO
 *
 * @author t412211
 *
 */
public class MappingLanguageCriteria {

    /*
     * Market
     */
	private String market;
    /*
	 * Language to map
	 */
	private String language;
	/*
	 * Context
	 */
	private String context;

	private boolean isIsoLanguage;

	public MappingLanguageCriteria(String market, String language, String context, boolean isIsoLanguage)
			throws ServiceException {
		this.setContext(context);
		this.setLanguage(language);
		this.setMarket(market);
		this.setIsoLanguage(isIsoLanguage);
	}

	/**
	 * @return the market
	 */
	public String getMarket() {
		return market;
	}

	/**
	 * @param market
	 *            the market to set
	 * @throws ServiceException
	 */
	public void setMarket(String market) throws ServiceException {
		if ((StringUtils.isAlphanumeric(market) && market.length() > CriteriaHelper.MIN_LENGTH
				&& market.length() < CriteriaHelper.MARKET_MAX_LENGTH)) {
			this.market = market;
		} else {
			throw new ServiceException(BusinessErrorList.API_MARKET_VIOLATION.getError(),
					HttpStatus.PRECONDITION_FAILED);
		}
	}

	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * @param language
	 *            the language to set
	 * @throws ServiceException
	 */
	public void setLanguage(String language) throws ServiceException {
		if ((StringUtils.isAlphanumeric(language) && language.length() > CriteriaHelper.MIN_LENGTH
				&& language.length() < CriteriaHelper.LANGUAGE_MAX_LENGTH)) {
			this.language = language;
		} else {
			throw new ServiceException(BusinessErrorList.API_LANGUAGE_VIOLATION.getError(),
					HttpStatus.PRECONDITION_FAILED);
		}
	}

	/**
	 * @return the context
	 */
	public String getContext() {
		return context;
	}

	/**
	 * @param context
	 *            the context to set
	 * @throws ServiceException
	 */
	public void setContext(String context) throws ServiceException {
		if ((StringUtils.isAlphanumeric(context) && context.length() > CriteriaHelper.MIN_LENGTH
				&& context.length() < CriteriaHelper.CONTEXT_MAX_LENGTH)) {
			this.context = context;
		} else {
			throw new ServiceException(BusinessErrorList.API_CONTEXT_VIOLATION_LANGUAGE.getError(),
					HttpStatus.PRECONDITION_FAILED);
		}
	}

	/**
	 * @return the isIsoLanguage
	 */
	public boolean isIsoLanguage() {
		return isIsoLanguage;
	}

	/**
	 * @param isIsoLanguage
	 *            the isIsoLanguage to set
	 */
	public void setIsoLanguage(boolean isIsoLanguage) {
		this.isIsoLanguage = isIsoLanguage;
	}
}
