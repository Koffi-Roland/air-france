
package com.airfrance.repind.firme.searchfirmonmulticriteria.ds;

import com.airfrance.repind.firme.searchfirmonmulticriteria.dto.FirmTypeEnum;
import com.airfrance.repind.firme.searchfirmonmulticriteria.dto.RequestDTO;
import com.airfrance.repind.firme.searchfirmonmulticriteria.dto.SearchResultDTO;
import org.springframework.stereotype.Service;
@Service
public class CalculateRateDS extends AbstractDS implements ICalculateRateDS {

	/* =============================================== */
	/* INSTANCE VARIABLES */
	/* =============================================== */

	/*
	 * Group rates
	 */
	private int rateGroupStrictName = 55;
	private int rateGroupLikeName = 55;
	private int rateGroupCountrycode = 40;
	private int rateGroupZC5 = 100;

	/*
	 * Company rates
	 */
	private int rateCompanyStrictNameCountrycodePhone = 100;
	private int rateCompanyStrictNameCountrycodeEmail = 100;
	private int rateCompanyStrictNameCountrycodeNbandstreetCityZipcode = 100;
	private int rateCompanyStrictNameCountrycodeNbandstreetCity = 100;
	private int rateCompanyStrictNameCountrycodeCityZipcode = 100;
	private int rateCompanyStrictNameCountrycodeIdent = 100;
	private int rateCompanyCountrycode = -1;
	private int rateCompanyZC5 = 100;
	private int rateCompanyStrictName = 55;
	private int rateCompanyLikeName = 55;
	/*
	 * Service rates
	 */
	private int rateServiceStrictNameCountrycodePhone = 80;
	private int rateServiceStrictNameCountrycodeEmail = 80;
	private int rateServiceStrictNameCountrycodeNbandstreetCityZipcode = 80;
	private int rateServiceStrictNameCountrycodeNbandstreetCity = 70;
	private int rateServiceStrictNameCountrycodeCityZipcode = 70;

	private int rateServiceLikeNameCountrycodePhone = 80;
	private int rateServiceLikeNameCountrycodeEmail = 80;
	private int rateServiceLikeNameCountrycodeNbandstreetCityZipcode = 80;
	private int rateServiceLikeNameCountrycodeNbandstreetCity = 70;
	private int rateServiceLikeNameCountrycodeCityZipcode = 70;

	private int rateServicePhone = 70;
	private int rateServiceEmail = 70;
	private int rateServicePhoneIdent = 80;
	private int rateServiceEmailIdent = 80;
	private int rateServiceIdent = 60;
	private int rateServiceCountrycode = -1;
	private int rateServiceZC5 = 100;
	private int rateServiceLikeName = 55;
	private int rateServiceStrictName = 55;

	/*
	 * Firm rates
	 */
	private int rateFirmStrictNameCountrycodePhone = 100;
	private int rateFirmStrictNameCountrycodeEmail = 100;
	private int rateFirmStrictNameCountrycodeNbandstreetCityZipcode = 100;
	private int rateFirmStrictNameCountrycodeNbandstreetCity = 90;
	private int rateFirmStrictNameCountrycodeCityZipcode = 85;
	private int rateFirmStrictNameCountrycodeZC14 = 75;
	private int rateFirmStrictNameCountrycodeZC15 = 80;
	private int rateFirmStrictNameCountrycodeIdent = 70;
	private int rateFirmStrictNameCountrycode = 60;

	private int rateFirmLikeNameCountrycodePhone = 100;
	private int rateFirmLikeNameCountrycodeEmail = 100;
	private int rateFirmLikeNameCountrycodeNbandstreetCityZipcode = 100;
	private int rateFirmLikeNameCountrycodeNbandstreetCity = 85;
	private int rateFirmLikeNameCountrycodeCityZipcode = 70;
	private int rateFirmLikeNameCountrycodeZC14 = 70;
	private int rateFirmLikeNameCountrycodeZC15 = 70;
	private int rateFirmLikeNameCountrycodeIdent = 70;
	
	//V2
	private int rateFirmLikeNameZipLikeCountrycode = 65;
	private int rateFirmLikeNameCityStrictCountrycode = 65;
	private int rateFirmLikeNameZipLikeCityLikeCountrycode = 65;
	private int rateFirmZC12 = 70;
	private int rateFirmNameLikeZC12 = 75;
	//	
	
	
	private int rateFirmLikeNameCountrycode = 60;

	private int rateFirmPhone = 70;
	private int rateFirmEmail = 70;
	private int rateFirmPhoneIdent  = 80;
	private int rateFirmEmailIdent = 80;
	private int rateFirmIdent = 60;
	private int rateFirmCountrycode = -1;
	private int rateFirmZC5 = 100;
	private int rateFirmLikeName = 55;
	private int rateFirmStrictName = 55;
	
	/* =============================================== */
	/* CONSTRUCTORS */
	/* =============================================== */

	public CalculateRateDS() {
		super();
	}

	/* =============================================== */
	/* PUBLIC METHODS */
	/* =============================================== */

	/**
	 * Update a SearchResultDTO instance with its relevance rate according to
	 * its type
	 * 
	 * @param searchResultDTO
	 */
	public void calculateRateByFirmType(SearchResultDTO searchResultDTO,
			RequestDTO requestDTO) {
		int rate = 0;

		if (searchResultDTO != null) {
			if (searchResultDTO.getFirmType().compareTo(FirmTypeEnum.GROUP) == 0) {
				rate = calculateGroupRate(requestDTO);
				searchResultDTO.setRate(rate);
			}
			if (searchResultDTO.getFirmType().compareTo(FirmTypeEnum.COMPANY) == 0) {
				rate = calculateCompanyRate(requestDTO);
				searchResultDTO.setRate(rate);
			}
			if (searchResultDTO.getFirmType().compareTo(FirmTypeEnum.FIRM) == 0) {
				rate = calculateFirmRate(requestDTO);
				searchResultDTO.setRate(rate);
			}
			if (searchResultDTO.getFirmType().compareTo(FirmTypeEnum.SERVICE) == 0) {
				rate = calculateServiceRate(requestDTO);
				searchResultDTO.setRate(rate);
			}
		}
	}

	/**
	 * Returns max rate of all scenarios
	 */
	public int getMaxRate() {
		int maxRate = Math
				.max(rateGroupStrictName,
						Math.max(
								rateGroupLikeName,
								Math.max(
										rateGroupCountrycode,
										Math.max(
												rateGroupZC5,
												Math.max(
														rateCompanyStrictNameCountrycodePhone,
														Math.max(
																rateCompanyStrictNameCountrycodeEmail,
																Math.max(
																		rateCompanyStrictNameCountrycodeNbandstreetCityZipcode,
																		Math.max(
																				rateCompanyStrictNameCountrycodeNbandstreetCity,
																				Math.max(
																						rateCompanyStrictNameCountrycodeCityZipcode,
																						Math.max(
																								rateCompanyStrictNameCountrycodeIdent,
																								Math.max(
																										rateCompanyCountrycode,
																										Math.max(
																												rateCompanyZC5,
																												Math.max(
																														rateServiceStrictNameCountrycodePhone,
																														Math.max(
																																rateServiceStrictNameCountrycodeEmail,
																																Math.max(
																																		rateServiceStrictNameCountrycodeNbandstreetCityZipcode,
																																		Math.max(
																																				rateServiceStrictNameCountrycodeNbandstreetCity,
																																				Math.max(
																																						rateServiceStrictNameCountrycodeCityZipcode,
																																						Math.max(
																																								rateServiceLikeNameCountrycodePhone,
																																								Math.max(
																																										rateServiceLikeNameCountrycodeEmail,
																																										Math.max(
																																												rateServiceLikeNameCountrycodeNbandstreetCityZipcode,
																																												Math.max(
																																														rateServiceLikeNameCountrycodeNbandstreetCity,
																																														Math.max(
																																																rateServiceLikeNameCountrycodeCityZipcode,
																																																Math.max(
																																																		rateServicePhone,
																																																		Math.max(
																																																				rateServiceEmail,
																																																				Math.max(
																																																						rateServicePhoneIdent,
																																																						Math.max(
																																																								rateServiceEmailIdent,
																																																								Math.max(
																																																										rateServiceIdent,
																																																										Math.max(
																																																												rateServiceCountrycode,
																																																												Math.max(
																																																														rateServiceZC5,
																																																														Math.max(
																																																																rateFirmStrictNameCountrycodePhone,
																																																																Math.max(
																																																																		rateFirmStrictNameCountrycodeEmail,
																																																																		Math.max(
																																																																				rateFirmStrictNameCountrycodeNbandstreetCityZipcode,
																																																																				Math.max(
																																																																						rateFirmStrictNameCountrycodeNbandstreetCity,
																																																																						Math.max(
																																																																								rateFirmStrictNameCountrycodeCityZipcode,
																																																																								Math.max(
																																																																										rateFirmStrictNameCountrycodeZC14,
																																																																										Math.max(
																																																																												rateFirmStrictNameCountrycodeZC15,
																																																																												Math.max(
																																																																														rateFirmStrictNameCountrycodeIdent,
																																																																														Math.max(
																																																																																rateFirmLikeNameCityStrictCountrycode,
																																																																																	Math.max(
																																																																																			rateFirmLikeNameCountrycodePhone,
																																																																																			Math.max(
																																																																																					rateFirmLikeNameCountrycodeEmail,
																																																																																					Math.max(
																																																																																							rateFirmLikeNameCountrycodeNbandstreetCityZipcode,
																																																																																							Math.max(
																																																																																									rateFirmLikeNameCountrycodeNbandstreetCity,
																																																																																									Math.max(
																																																																																											rateFirmLikeNameCountrycodeCityZipcode,
																																																																																											Math.max(
																																																																																										rateFirmLikeNameCountrycodeZC14,
																																																																																										Math.max(
																																																																																												rateFirmLikeNameCountrycodeZC15,
																																																																																												Math.max(
																																																																																														rateFirmLikeNameCountrycodeIdent,
																																																																																														Math.max(
																																																																																																rateFirmPhone,
																																																																																																Math.max(
																																																																																																		rateFirmEmail,
																																																																																																		Math.max(
																																																																																																				rateFirmPhoneIdent,
																																																																																																				Math.max(
																																																																																																						rateFirmEmailIdent,
																																																																																																						Math.max(
																																																																																																								rateFirmCountrycode,
																																																																																																								Math.max(
																																																																																																										rateFirmZC5,
																																																																																																										rateFirmIdent))))))))))))))))))))))))))))))))))))))))))))))))))));

		return maxRate;

	}

	/**
	 * Returns Group max rate
	 */
	public int getMaxGroupRate() {
		int maxRate = Math.max(
				rateGroupZC5,
				Math.max(rateGroupCountrycode,
						Math.max(rateGroupStrictName, rateGroupLikeName)));
		return maxRate;
	}

	/**
	 * Return Company max rate
	 */
	public int getMaxCompanyRate() {
		int maxRate = Math
				.max(rateCompanyStrictNameCountrycodePhone,
						Math.max(
								rateCompanyStrictNameCountrycodeEmail,
								Math.max(
										rateCompanyStrictNameCountrycodeNbandstreetCityZipcode,
										Math.max(
												rateCompanyStrictNameCountrycodeNbandstreetCity,
												Math.max(
														rateCompanyStrictNameCountrycodeCityZipcode,
														Math.max(
																rateCompanyCountrycode,
																Math.max(
																		rateCompanyZC5,
																		rateCompanyStrictNameCountrycodeIdent)))))));
		return maxRate;
	}

	/**
	 * Returns Service max rate
	 */
	public int getMaxServiceRate() {
		int maxRate = Math
				.max(rateServiceStrictNameCountrycodePhone,
						Math.max(
								rateServiceStrictNameCountrycodeEmail,
								Math.max(
										rateServiceStrictNameCountrycodeNbandstreetCityZipcode,
										Math.max(
												rateServiceStrictNameCountrycodeNbandstreetCity,
												Math.max(
														rateServiceStrictNameCountrycodeCityZipcode,
														Math.max(
																rateServiceLikeNameCountrycodePhone,
																Math.max(
																		rateServiceLikeNameCountrycodeEmail,
																		Math.max(
																				rateServiceLikeNameCountrycodeNbandstreetCityZipcode,
																				Math.max(
																						rateServiceLikeNameCountrycodeNbandstreetCity,
																						Math.max(
																								rateServiceLikeNameCountrycodeCityZipcode,
																								Math.max(
																										rateServicePhone,
																										Math.max(
																												rateServiceEmail,
																												Math.max(
																														rateServicePhoneIdent,
																														Math.max(
																																rateServiceEmailIdent,
																																Math.max(
																																		rateServiceCountrycode,
																																		Math.max(
																																				rateServiceZC5,
																																				rateServiceIdent))))))))))))))));
		return maxRate;
	}

	/**
	 * Returns Firm max rate
	 */
	public int getMaxFirmRate() {
		int maxRate = Math
				.max(rateFirmStrictNameCountrycodePhone,
						Math.max(
								rateFirmStrictNameCountrycodeEmail,
								Math.max(
										rateFirmStrictNameCountrycodeNbandstreetCityZipcode,
										Math.max(
												rateFirmStrictNameCountrycodeNbandstreetCity,
												Math.max(
														rateFirmStrictNameCountrycodeCityZipcode,
														Math.max(
																rateFirmStrictNameCountrycodeZC14,
																Math.max(
																		rateFirmStrictNameCountrycodeZC15,
																		Math.max(
																				rateFirmZC12,
																				Math.max(
																				rateFirmStrictNameCountrycodeIdent,
																				Math.max(
																						rateFirmLikeNameCountrycodePhone,
																						Math.max(
																								rateFirmLikeNameCountrycodeEmail,
																								Math.max(
																										rateFirmLikeNameCountrycodeNbandstreetCityZipcode,
																										Math.max(
																												rateFirmLikeNameCountrycodeNbandstreetCity,
																												Math.max(
																														rateFirmLikeNameCountrycodeCityZipcode,
																														Math.max(
																																rateFirmLikeNameCountrycodeZC14,
																																Math.max(
																																		rateFirmLikeNameCountrycodeZC15,
																																		Math.max(
																																				rateFirmLikeNameCountrycodeIdent,
																																				Math.max(
																																						rateFirmPhone,
																																						Math.max(
																																								rateFirmEmail,
																																								Math.max(
																																										rateFirmPhoneIdent,
																																										Math.max(
																																												rateFirmEmailIdent,
																																												Math.max(
																																														rateFirmCountrycode,
																																														Math.max(
																																																rateFirmZC5,
																																																rateFirmIdent)))))))))))))))))))))));
		return maxRate;
	}

	/* =============================================== */
	/* PRIVATE METHODS */
	/* =============================================== */

	/**
	 * Returns Group relevance rate
	 */
	private int calculateGroupRate(RequestDTO requestDTO) {
		int rate = 0;

		/*
		 * Name Strict
		 */
		if (BuildConditionsDS.isNameStrictConditionSet(requestDTO)) {
			if (rateGroupStrictName > rate) {
				rate = rateGroupStrictName;
			}
		}

		/*
		 * Name Like
		 */
		if (BuildConditionsDS.isNameLikeConditionSet(requestDTO)) {
			if (rateGroupLikeName > rate) {
				rate = rateGroupLikeName;
			}
		}

		/*
		 * Country code
		 */
		if (BuildConditionsDS.isCountryConditionSet(requestDTO)) {
			if (rateGroupCountrycode > rate) {
				rate = rateGroupCountrycode;
			}
		}

		/*
		 * ZC5
		 */
		if ((BuildConditionsDS.isZC1ConditionSet(requestDTO))
				&& (BuildConditionsDS.isZC2ConditionSet(requestDTO))
				&& (BuildConditionsDS.isZC3ConditionSet(requestDTO))
				&& (BuildConditionsDS.isZC4ConditionSet(requestDTO))
				&& (BuildConditionsDS.isZC5ConditionSet(requestDTO))) {
			if (rateGroupZC5 > rate) {
				rate = rateGroupZC5;
			}
		}

		return rate;
	}

	/**
	 * Returns Company relevance rate
	 */
	private int calculateCompanyRate(RequestDTO requestDTO) {
		int rate = 0;
		
		/*
		 * Name(Strict), countryCode and phone
		 */
		if ((BuildConditionsDS.isNameStrictConditionSet(requestDTO))
				&& (BuildConditionsDS.isCountryConditionSet(requestDTO))
				&& (BuildConditionsDS.isPhoneConditionSet(requestDTO))) {
			if (rateCompanyStrictNameCountrycodePhone > rate) {
				rate = rateCompanyStrictNameCountrycodePhone;
			}
		}

		/*
		 * Name(Strict), countryCode and email
		 */
		if ((BuildConditionsDS.isNameStrictConditionSet(requestDTO))
				&& (BuildConditionsDS.isCountryConditionSet(requestDTO))
				&& (BuildConditionsDS.isEmailConditionSet(requestDTO))) {
			if (rateCompanyStrictNameCountrycodeEmail > rate) {
				rate = rateCompanyStrictNameCountrycodeEmail;
			}
		}

		/*
		 * Name, CountryCode, NbAndStreet, city and zip
		 */
		if ((BuildConditionsDS.isNameStrictConditionSet(requestDTO))
				&& (BuildConditionsDS.isCountryConditionSet(requestDTO))
				&& (BuildConditionsDS.isNumberAndStreetConditionSet(requestDTO))
				&& (BuildConditionsDS.isCityStrictConditionSet(requestDTO))
				&& (BuildConditionsDS.isZipStrictConditionSet(requestDTO))) {
			if (rateCompanyStrictNameCountrycodeNbandstreetCityZipcode > rate) {
				rate = rateCompanyStrictNameCountrycodeNbandstreetCityZipcode;
			}
		}

		/*
		 * Name(Strict), CountryCode NbAndStreet and like city
		 */
		if ((BuildConditionsDS.isNameStrictConditionSet(requestDTO))
				&& (BuildConditionsDS.isCountryConditionSet(requestDTO))
				&& (BuildConditionsDS.isNumberAndStreetConditionSet(requestDTO))
				&& ((BuildConditionsDS.isCityLikeConditionSet(requestDTO)) || (BuildConditionsDS
						.isCityStrictConditionSet(requestDTO)))) {
			if (rateCompanyStrictNameCountrycodeNbandstreetCity > rate) {
				rate = rateCompanyStrictNameCountrycodeNbandstreetCity;
			}
		}

		/*
		 * Name(Strict), CountryCode, City And Zip code
		 */
		if ((BuildConditionsDS.isNameStrictConditionSet(requestDTO))
				&& (BuildConditionsDS.isCountryConditionSet(requestDTO))
				&& (BuildConditionsDS.isCityStrictConditionSet(requestDTO))
				&& (BuildConditionsDS.isZipStrictConditionSet(requestDTO))) {
			if (rateCompanyStrictNameCountrycodeCityZipcode > rate) {
				rate = rateCompanyStrictNameCountrycodeCityZipcode;
			}
		}

		/*
		 * Name(Strict), CountryCode and Ident
		 */
		if ((BuildConditionsDS.isNameStrictConditionSet(requestDTO))
				&& (BuildConditionsDS.isCountryConditionSet(requestDTO))
				&& (BuildConditionsDS.isIdentTypeConditionSet(requestDTO))
				&& (BuildConditionsDS.isIdentValueConditionSet(requestDTO))) {
			if (rateCompanyStrictNameCountrycodeIdent > rate) {
				rate = rateCompanyStrictNameCountrycodeIdent;
			}
		}
		
		/*
		 * Name Strict
		 */
		if (BuildConditionsDS.isNameStrictConditionSet(requestDTO)) {
			if (rateCompanyStrictName > rate) {
				rate = rateCompanyStrictName;
			}
		}

		/*
		 * Name Like
		 */
		if (BuildConditionsDS.isNameLikeConditionSet(requestDTO)) {
			if (rateCompanyLikeName > rate) {
				rate = rateCompanyLikeName;
			}
		}

		/*
		 * Country code
		 */
		if (BuildConditionsDS.isCountryConditionSet(requestDTO)) {
			if (rateCompanyCountrycode > rate) {
				rate = rateCompanyCountrycode;
			}
		}

		/*
		 * ZC5
		 */
		if ((BuildConditionsDS.isZC1ConditionSet(requestDTO))
				&& (BuildConditionsDS.isZC2ConditionSet(requestDTO))
				&& (BuildConditionsDS.isZC3ConditionSet(requestDTO))
				&& (BuildConditionsDS.isZC4ConditionSet(requestDTO))
				&& (BuildConditionsDS.isZC5ConditionSet(requestDTO))) {
			if (rateCompanyZC5 > rate) {
				rate = rateCompanyZC5;
			}
		}

		return rate;
	}

	/**
	 * Returns Firm relevance rate
	 */
	private int calculateFirmRate(RequestDTO requestDTO) {

		int rate = 0;

		/*
		 * Name(Strict), countryCode and phone
		 */
		if ((BuildConditionsDS.isNameStrictConditionSet(requestDTO))
				&& (BuildConditionsDS.isCountryConditionSet(requestDTO))
				&& (BuildConditionsDS.isPhoneConditionSet(requestDTO))) {
			if (rateFirmStrictNameCountrycodePhone > rate) {
				rate = rateFirmStrictNameCountrycodePhone;
			}
		}

		/*
		 * Name(Like), countryCode and phone
		 */
		if ((BuildConditionsDS.isNameLikeConditionSet(requestDTO))
				&& (BuildConditionsDS.isCountryConditionSet(requestDTO))
				&& (BuildConditionsDS.isPhoneConditionSet(requestDTO))) {
			if (rateFirmLikeNameCountrycodePhone > rate) {
				rate = rateFirmLikeNameCountrycodePhone;
			}
		}

		/*
		 * Name(Strict), countryCode and email
		 */
		if ((BuildConditionsDS.isNameStrictConditionSet(requestDTO))
				&& (BuildConditionsDS.isCountryConditionSet(requestDTO))
				&& (BuildConditionsDS.isEmailConditionSet(requestDTO))) {
			if (rateFirmStrictNameCountrycodeEmail > rate) {
				rate = rateFirmStrictNameCountrycodeEmail;
			}
		}

		/*
		 * Name(Like), countryCode and email
		 */
		if ((BuildConditionsDS.isNameLikeConditionSet(requestDTO))
				&& (BuildConditionsDS.isCountryConditionSet(requestDTO))
				&& (BuildConditionsDS.isEmailConditionSet(requestDTO))) {
			if (rateFirmLikeNameCountrycodeEmail > rate) {
				rate = rateFirmLikeNameCountrycodeEmail;
			}
		}

		/*
		 * Name(Strict), CountryCode, NbAndStreet, city and zip
		 */
		if ((BuildConditionsDS.isNameStrictConditionSet(requestDTO))
				&& (BuildConditionsDS.isCountryConditionSet(requestDTO))
				&& (BuildConditionsDS.isNumberAndStreetConditionSet(requestDTO))
				&& (BuildConditionsDS.isCityStrictConditionSet(requestDTO))
				&& (BuildConditionsDS.isZipStrictConditionSet(requestDTO))) {
			if (rateFirmStrictNameCountrycodeNbandstreetCityZipcode > rate) {
				rate = rateFirmStrictNameCountrycodeNbandstreetCityZipcode;
			}
		}

		/*
		 * Name(Like), CountryCode, NbAndStreet, city and zip
		 */
		if ((BuildConditionsDS.isNameLikeConditionSet(requestDTO))
				&& (BuildConditionsDS.isCountryConditionSet(requestDTO))
				&& (BuildConditionsDS.isNumberAndStreetConditionSet(requestDTO))
				&& (BuildConditionsDS.isCityLikeConditionSet(requestDTO))
				&& (BuildConditionsDS.isZipLikeConditionSet(requestDTO))) {
			if (rateFirmLikeNameCountrycodeNbandstreetCityZipcode > rate) {
				rate = rateFirmLikeNameCountrycodeNbandstreetCityZipcode;
			}
		}

		/*
		 * Name(Strict), CountryCode NbAndStreet and like city
		 */
		if ((BuildConditionsDS.isNameStrictConditionSet(requestDTO))
				&& (BuildConditionsDS.isCountryConditionSet(requestDTO))
				&& (BuildConditionsDS.isNumberAndStreetConditionSet(requestDTO))
				&& ((BuildConditionsDS.isCityLikeConditionSet(requestDTO)) || (BuildConditionsDS
						.isCityStrictConditionSet(requestDTO)))) {
			if (rateFirmStrictNameCountrycodeNbandstreetCity > rate) {
				rate = rateFirmStrictNameCountrycodeNbandstreetCity;
			}
		}

		/*
		 * Name(Like), CountryCode NbAndStreet and like city
		 */
		if ((BuildConditionsDS.isNameLikeConditionSet(requestDTO))
				&& (BuildConditionsDS.isCountryConditionSet(requestDTO))
				&& (BuildConditionsDS.isNumberAndStreetConditionSet(requestDTO))
				&& ((BuildConditionsDS.isCityLikeConditionSet(requestDTO)) || (BuildConditionsDS
						.isCityLikeConditionSet(requestDTO)))) {
			if (rateFirmLikeNameCountrycodeNbandstreetCity > rate) {
				rate = rateFirmLikeNameCountrycodeNbandstreetCity;
			}
		}

		/*
		 * Name(Strict), Country, City And Zip code
		 */
		if ((BuildConditionsDS.isNameStrictConditionSet(requestDTO))
				&& (BuildConditionsDS.isCountryConditionSet(requestDTO))
				&& (BuildConditionsDS.isCityStrictConditionSet(requestDTO))
				&& (BuildConditionsDS.isZipStrictConditionSet(requestDTO))) {
			if (rateFirmStrictNameCountrycodeCityZipcode > rate) {
				rate = rateFirmStrictNameCountrycodeCityZipcode;
			}
		}

		/*
		 * Name(Like), Country, City And Zip code
		 */
		if ((BuildConditionsDS.isNameLikeConditionSet(requestDTO))
				&& (BuildConditionsDS.isCountryConditionSet(requestDTO))
				&& (BuildConditionsDS.isCityLikeConditionSet(requestDTO))
				&& (BuildConditionsDS.isZipLikeConditionSet(requestDTO))) {
			if (rateFirmLikeNameCountrycodeCityZipcode > rate) {
				rate = rateFirmLikeNameCountrycodeCityZipcode;
			}
		}

		/*
		 * Name(Strict), Country, ZC1 to ZC4
		 */
		if ((BuildConditionsDS.isNameStrictConditionSet(requestDTO))
				&& (BuildConditionsDS.isCountryConditionSet(requestDTO))
				&& (BuildConditionsDS.isZC1ConditionSet(requestDTO))
				&& (BuildConditionsDS.isZC2ConditionSet(requestDTO))
				&& (BuildConditionsDS.isZC3ConditionSet(requestDTO))
				&& (BuildConditionsDS.isZC4ConditionSet(requestDTO))) {
			if (rateFirmStrictNameCountrycodeZC14 > rate) {
				rate = rateFirmStrictNameCountrycodeZC14;
			}
		}

		/*
		 * Name(Like), Country, ZC1 to ZC4
		 */
		if ((BuildConditionsDS.isNameLikeConditionSet(requestDTO))
				&& (BuildConditionsDS.isCountryConditionSet(requestDTO))
				&& (BuildConditionsDS.isZC1ConditionSet(requestDTO))
				&& (BuildConditionsDS.isZC2ConditionSet(requestDTO))
				&& (BuildConditionsDS.isZC3ConditionSet(requestDTO))
				&& (BuildConditionsDS.isZC4ConditionSet(requestDTO))) {
			if (rateFirmLikeNameCountrycodeZC14 > rate) {
				rate = rateFirmLikeNameCountrycodeZC14;
			}
		}

		/*
		 * Name(Like), ZC1 to ZC2
		 */
		if ((BuildConditionsDS.isNameLikeConditionSet(requestDTO))
				&& (BuildConditionsDS.isZC1ConditionSet(requestDTO))
				&& (BuildConditionsDS.isZC2ConditionSet(requestDTO)))
				{
			if (rateFirmNameLikeZC12 > rate) {
				rate = rateFirmNameLikeZC12;
			}
		}

		
		/*
		 * Name(Strict), Country, ZC1 to ZC5
		 */
		if ((BuildConditionsDS.isNameStrictConditionSet(requestDTO))
				&& (BuildConditionsDS.isCountryConditionSet(requestDTO))
				&& (BuildConditionsDS.isZC1ConditionSet(requestDTO))
				&& (BuildConditionsDS.isZC2ConditionSet(requestDTO))
				&& (BuildConditionsDS.isZC3ConditionSet(requestDTO))
				&& (BuildConditionsDS.isZC4ConditionSet(requestDTO))
				&& (BuildConditionsDS.isZC5ConditionSet(requestDTO))) {
			if (rateFirmStrictNameCountrycodeZC15 > rate) {
				rate = rateFirmStrictNameCountrycodeZC15;
			}
		}

		/*
		 * Name(Like), Country, ZC1 to ZC5
		 */
		if ((BuildConditionsDS.isNameLikeConditionSet(requestDTO))
				&& (BuildConditionsDS.isCountryConditionSet(requestDTO))
				&& (BuildConditionsDS.isZC1ConditionSet(requestDTO))
				&& (BuildConditionsDS.isZC2ConditionSet(requestDTO))
				&& (BuildConditionsDS.isZC3ConditionSet(requestDTO))
				&& (BuildConditionsDS.isZC4ConditionSet(requestDTO))
				&& (BuildConditionsDS.isZC5ConditionSet(requestDTO))) {
			if (rateFirmLikeNameCountrycodeZC15 > rate) {
				rate = rateFirmLikeNameCountrycodeZC15;
			}
		}

		/*
		 * Name(Strict), CountryCode and Ident
		 */
		if ((BuildConditionsDS.isNameStrictConditionSet(requestDTO))
				&& (BuildConditionsDS.isCountryConditionSet(requestDTO))
				&& (BuildConditionsDS.isIdentTypeConditionSet(requestDTO))
				&& (BuildConditionsDS.isIdentValueConditionSet(requestDTO))) {
			if (rateFirmStrictNameCountrycodeIdent > rate) {
				rate = rateFirmStrictNameCountrycodeIdent;
			}
		}

		/*
		 * Name(Like), CountryCode and Ident
		 */
		if ((BuildConditionsDS.isNameLikeConditionSet(requestDTO))
				&& (BuildConditionsDS.isCountryConditionSet(requestDTO))
				&& (BuildConditionsDS.isIdentTypeConditionSet(requestDTO))
				&& (BuildConditionsDS.isIdentValueConditionSet(requestDTO))) {
			if (rateFirmLikeNameCountrycodeIdent > rate) {
				rate = rateFirmLikeNameCountrycodeIdent;
			}
		}

		/*
		 * Name(Strict) CountryCode
		 */
		if ((BuildConditionsDS.isNameStrictConditionSet(requestDTO))
				&& (BuildConditionsDS.isCountryConditionSet(requestDTO))) {
			if (rateFirmStrictNameCountrycode > rate) {
				rate = rateFirmStrictNameCountrycode;
			}
		}

		/*
		 * Name(Like) CountryCode
		 */
		if ((BuildConditionsDS.isNameLikeConditionSet(requestDTO))
				&& (BuildConditionsDS.isCountryConditionSet(requestDTO))) {
			if (rateFirmLikeNameCountrycode > rate) {
				rate = rateFirmLikeNameCountrycode;
			}
		}
		
		/*
		 * Name(Like) CountryCode
		 */
		if ((BuildConditionsDS.isNameLikeConditionSet(requestDTO))
				&& (BuildConditionsDS.isCityStrictConditionSet(requestDTO))
				&& (BuildConditionsDS.isCountryConditionSet(requestDTO))) {
			if (rateFirmLikeNameCityStrictCountrycode > rate) {
				rate = rateFirmLikeNameCityStrictCountrycode;
			}
		}
		
		/*
		 * Name(Like) CountryCode
		 */
		if ((BuildConditionsDS.isNameLikeConditionSet(requestDTO))
				&& (BuildConditionsDS.isZipLikeConditionSet(requestDTO))
				&& (BuildConditionsDS.isCountryConditionSet(requestDTO))) {
			if (rateFirmLikeNameZipLikeCountrycode > rate) {
				rate = rateFirmLikeNameZipLikeCountrycode;
			}
		}
		
		/*
		 * Name(Like) City Like Zip Like CountryCode
		 */
		if ((BuildConditionsDS.isNameLikeConditionSet(requestDTO))
				&& (BuildConditionsDS.isZipLikeConditionSet(requestDTO))
				&& (BuildConditionsDS.isCityLikeConditionSet(requestDTO))
				&& (BuildConditionsDS.isCountryConditionSet(requestDTO))) {
			if (rateFirmLikeNameZipLikeCityLikeCountrycode > rate) {
				rate = rateFirmLikeNameZipLikeCityLikeCountrycode;
			}
		}
		
		/*
		 * Name(Strict)
		 */
		if (BuildConditionsDS.isNameStrictConditionSet(requestDTO)) {
			if (rateFirmStrictName > rate) {
				rate = rateFirmStrictName;
			}
		}

		/*
		 * Name(Like)
		 */
		if (BuildConditionsDS.isNameLikeConditionSet(requestDTO)) {
			if (rateFirmLikeName > rate) {
				rate = rateFirmLikeName;
			}
		}

		/*
		 * Phone
		 */
		if (BuildConditionsDS.isPhoneConditionSet(requestDTO)) {
			if (rateFirmPhone > rate) {
				rate = rateFirmPhone;
			}
		}

		/*
		 * Email
		 */
		if (BuildConditionsDS.isEmailConditionSet(requestDTO)) {
			if (rateFirmEmail > rate) {
				rate = rateFirmEmail;
			}
		}

		/*
		 * Phone and Ident
		 */
		if ((BuildConditionsDS.isPhoneConditionSet(requestDTO))
				&& (BuildConditionsDS.isIdentTypeConditionSet(requestDTO))
				&& (BuildConditionsDS.isIdentValueConditionSet(requestDTO))) {
			if (rateFirmPhoneIdent > rate) {
				rate = rateFirmPhoneIdent;
			}
		}

		/*
		 * Email and Ident
		 */
		if ((BuildConditionsDS.isEmailConditionSet(requestDTO))
				&& (BuildConditionsDS.isIdentTypeConditionSet(requestDTO))
				&& (BuildConditionsDS.isIdentValueConditionSet(requestDTO))) {
			if (rateFirmEmailIdent > rate) {
				rate = rateFirmEmailIdent;
			}
		}

		/*
		 * Ident
		 */
		if ((BuildConditionsDS.isIdentTypeConditionSet(requestDTO))
				&& (BuildConditionsDS.isIdentValueConditionSet(requestDTO))) {
			if (rateFirmIdent > rate) {
				rate = rateFirmIdent;
			}
		}

		/*
		 * Country code
		 */
		if (BuildConditionsDS.isCountryConditionSet(requestDTO)) {
			if (rateFirmCountrycode > rate) {
				rate = rateFirmCountrycode;
			}
		}

		/*
		 * ZC5
		 */
		if ((BuildConditionsDS.isZC1ConditionSet(requestDTO))
				&& (BuildConditionsDS.isZC2ConditionSet(requestDTO))
				&& (BuildConditionsDS.isZC3ConditionSet(requestDTO))
				&& (BuildConditionsDS.isZC4ConditionSet(requestDTO))
				&& (BuildConditionsDS.isZC5ConditionSet(requestDTO))) {
			if (rateFirmZC5 > rate) {
				rate = rateFirmZC5;
			}
		}

		return rate;
	}

	/**
	 * Returns Service relevance rate
	 */
	private int calculateServiceRate(RequestDTO requestDTO) {

		int rate = 0;

		/*
		 * Name(Strict), countryCode and phone
		 */
		if ((BuildConditionsDS.isNameStrictConditionSet(requestDTO))
				&& (BuildConditionsDS.isCountryConditionSet(requestDTO))
				&& (BuildConditionsDS.isPhoneConditionSet(requestDTO))) {
			if (rateServiceStrictNameCountrycodePhone > rate) {
				rate = rateServiceStrictNameCountrycodePhone;
			}
		}

		/*
		 * Name(Like), countryCode and phone
		 */
		if ((BuildConditionsDS.isNameLikeConditionSet(requestDTO))
				&& (BuildConditionsDS.isCountryConditionSet(requestDTO))
				&& (BuildConditionsDS.isPhoneConditionSet(requestDTO))) {
			if (rateServiceLikeNameCountrycodePhone > rate) {
				rate = rateServiceLikeNameCountrycodePhone;
			}
		}

		/*
		 * Name(Strict), countryCode and email
		 */
		if ((BuildConditionsDS.isNameStrictConditionSet(requestDTO))
				&& (BuildConditionsDS.isCountryConditionSet(requestDTO))
				&& (BuildConditionsDS.isEmailConditionSet(requestDTO))) {
			if (rateServiceStrictNameCountrycodeEmail > rate) {
				rate = rateServiceStrictNameCountrycodeEmail;
			}
		}

		/*
		 * Name(Like), countryCode and email
		 */
		if ((BuildConditionsDS.isNameLikeConditionSet(requestDTO))
				&& (BuildConditionsDS.isCountryConditionSet(requestDTO))
				&& (BuildConditionsDS.isEmailConditionSet(requestDTO))) {
			if (rateServiceLikeNameCountrycodeEmail > rate) {
				rate = rateServiceLikeNameCountrycodeEmail;
			}
		}

		/*
		 * Name(Strict), CountryCode, NbAndStreet, city and zip
		 */
		if ((BuildConditionsDS.isNameStrictConditionSet(requestDTO))
				&& (BuildConditionsDS.isCountryConditionSet(requestDTO))
				&& (BuildConditionsDS.isNumberAndStreetConditionSet(requestDTO))
				&& (BuildConditionsDS.isCityStrictConditionSet(requestDTO))
				&& (BuildConditionsDS.isZipStrictConditionSet(requestDTO))) {
			if (rateServiceStrictNameCountrycodeNbandstreetCityZipcode > rate) {
				rate = rateServiceStrictNameCountrycodeNbandstreetCityZipcode;
			}
		}

		/*
		 * Name(Like), CountryCode, NbAndStreet, city and zip
		 */
		if ((BuildConditionsDS.isNameLikeConditionSet(requestDTO))
				&& (BuildConditionsDS.isCountryConditionSet(requestDTO))
				&& (BuildConditionsDS.isNumberAndStreetConditionSet(requestDTO))
				&& (BuildConditionsDS.isCityLikeConditionSet(requestDTO))
				&& (BuildConditionsDS.isZipLikeConditionSet(requestDTO))) {
			if (rateServiceLikeNameCountrycodeNbandstreetCityZipcode > rate) {
				rate = rateServiceLikeNameCountrycodeNbandstreetCityZipcode;
			}
		}

		/*
		 * Name(Strict), CountryCode NbAndStreet and like city
		 */
		if ((BuildConditionsDS.isNameStrictConditionSet(requestDTO))
				&& (BuildConditionsDS.isCountryConditionSet(requestDTO))
				&& (BuildConditionsDS.isNumberAndStreetConditionSet(requestDTO))
				&& ((BuildConditionsDS.isCityLikeConditionSet(requestDTO)) || (BuildConditionsDS
						.isCityStrictConditionSet(requestDTO)))) {
			if (rateServiceStrictNameCountrycodeNbandstreetCity > rate) {
				rate = rateServiceStrictNameCountrycodeNbandstreetCity;
			}
		}

		/*
		 * Name(Like), CountryCode NbAndStreet and like city
		 */
		if ((BuildConditionsDS.isNameLikeConditionSet(requestDTO))
				&& (BuildConditionsDS.isCountryConditionSet(requestDTO))
				&& (BuildConditionsDS.isNumberAndStreetConditionSet(requestDTO))
				&& ((BuildConditionsDS.isCityLikeConditionSet(requestDTO)) || (BuildConditionsDS
						.isCityLikeConditionSet(requestDTO)))) {
			if (rateServiceLikeNameCountrycodeNbandstreetCity > rate) {
				rate = rateServiceLikeNameCountrycodeNbandstreetCity;
			}
		}

		/*
		 * Name(Strict), Country, City And Zip code
		 */
		if ((BuildConditionsDS.isNameStrictConditionSet(requestDTO))
				&& (BuildConditionsDS.isCountryConditionSet(requestDTO))
				&& (BuildConditionsDS.isCityStrictConditionSet(requestDTO))
				&& (BuildConditionsDS.isZipStrictConditionSet(requestDTO))) {
			if (rateServiceStrictNameCountrycodeCityZipcode > rate) {
				rate = rateServiceStrictNameCountrycodeCityZipcode;
			}
		}

		/*
		 * Name(Like), Country, City And Zip code
		 */
		if ((BuildConditionsDS.isNameLikeConditionSet(requestDTO))
				&& (BuildConditionsDS.isCountryConditionSet(requestDTO))
				&& (BuildConditionsDS.isCityLikeConditionSet(requestDTO))
				&& (BuildConditionsDS.isZipLikeConditionSet(requestDTO))) {
			if (rateServiceLikeNameCountrycodeCityZipcode > rate) {
				rate = rateServiceLikeNameCountrycodeCityZipcode;
			}
		}

		/*
		 * Phone
		 */
		if (BuildConditionsDS.isPhoneConditionSet(requestDTO)) {
			if (rateServicePhone > rate) {
				rate = rateServicePhone;
			}
		}

		/*
		 * Email
		 */
		if (BuildConditionsDS.isEmailConditionSet(requestDTO)) {
			if (rateServiceEmail > rate) {
				rate = rateServiceEmail;
			}
		}

		/*
		 * Phone and Ident
		 */
		if ((BuildConditionsDS.isPhoneConditionSet(requestDTO))
				&& (BuildConditionsDS.isIdentTypeConditionSet(requestDTO))
				&& (BuildConditionsDS.isIdentValueConditionSet(requestDTO))) {
			if (rateServicePhoneIdent > rate) {
				rate = rateServicePhoneIdent;
			}
		}

		/*
		 * Email and Ident
		 */
		if ((BuildConditionsDS.isEmailConditionSet(requestDTO))
				&& (BuildConditionsDS.isIdentTypeConditionSet(requestDTO))
				&& (BuildConditionsDS.isIdentValueConditionSet(requestDTO))) {
			if (rateServiceEmailIdent > rate) {
				rate = rateServiceEmailIdent;
			}
		}

		/*
		 * Ident
		 */
		if ((BuildConditionsDS.isIdentTypeConditionSet(requestDTO))
				&& (BuildConditionsDS.isIdentValueConditionSet(requestDTO))) {
			if (rateServiceIdent > rate) {
				rate = rateServiceIdent;
			}
		}

		/*
		 * Name Strict
		 */
		if (BuildConditionsDS.isNameStrictConditionSet(requestDTO)) {
			if (rateServiceStrictName > rate) {
				rate = rateServiceStrictName;
			}
		}

		/*
		 * Name Like
		 */
		if (BuildConditionsDS.isNameLikeConditionSet(requestDTO)) {
			if (rateServiceLikeName > rate) {
				rate = rateServiceLikeName;
			}
		}

		/*
		 * Country code
		 */
		if (BuildConditionsDS.isCountryConditionSet(requestDTO)) {
			if (rateServiceCountrycode > rate) {
				rate = rateServiceCountrycode;
			}
		}

		/*
		 * ZC5
		 */
		if ((BuildConditionsDS.isZC1ConditionSet(requestDTO))
				&& (BuildConditionsDS.isZC2ConditionSet(requestDTO))
				&& (BuildConditionsDS.isZC3ConditionSet(requestDTO))
				&& (BuildConditionsDS.isZC4ConditionSet(requestDTO))
				&& (BuildConditionsDS.isZC5ConditionSet(requestDTO))) {
			if (rateServiceZC5 > rate) {
				rate = rateServiceZC5;
			}
		}

		return rate;
	}

	/* =============================================== */
	/* ACCESSORS */
	/* =============================================== */

	public int getRateGroupStrictName() {
		return rateGroupStrictName;
	}

	public void setRateGroupStrictName(int rateGroupStrictName) {
		this.rateGroupStrictName = rateGroupStrictName;
	}

	public int getRateGroupLikeName() {
		return rateGroupLikeName;
	}

	public void setRateGroupLikeName(int rateGroupLikeName) {
		this.rateGroupLikeName = rateGroupLikeName;
	}

	public int getRateCompanyStrictNameCountrycodePhone() {
		return rateCompanyStrictNameCountrycodePhone;
	}

	public void setRateCompanyStrictNameCountrycodePhone(
			int rateCompanyStrictNameCountrycodePhone) {
		this.rateCompanyStrictNameCountrycodePhone = rateCompanyStrictNameCountrycodePhone;
	}

	public int getRateCompanyStrictNameCountrycodeEmail() {
		return rateCompanyStrictNameCountrycodeEmail;
	}

	public void setRateCompanyStrictNameCountrycodeEmail(
			int rateCompanyStrictNameCountrycodeEmail) {
		this.rateCompanyStrictNameCountrycodeEmail = rateCompanyStrictNameCountrycodeEmail;
	}

	public int getRateCompanyStrictNameCountrycodeNbandstreetCityZipcode() {
		return rateCompanyStrictNameCountrycodeNbandstreetCityZipcode;
	}

	public void setRateCompanyStrictNameCountrycodeNbandstreetCityZipcode(
			int rateCompanyStrictNameCountrycodeNbandstreetCityZipcode) {
		this.rateCompanyStrictNameCountrycodeNbandstreetCityZipcode = rateCompanyStrictNameCountrycodeNbandstreetCityZipcode;
	}

	public int getRateCompanyStrictNameCountrycodeNbandstreetCity() {
		return rateCompanyStrictNameCountrycodeNbandstreetCity;
	}

	public void setRateCompanyStrictNameCountrycodeNbandstreetCity(
			int rateCompanyStrictNameCountrycodeNbandstreetCity) {
		this.rateCompanyStrictNameCountrycodeNbandstreetCity = rateCompanyStrictNameCountrycodeNbandstreetCity;
	}

	public int getRateCompanyStrictNameCountrycodeCityZipcode() {
		return rateCompanyStrictNameCountrycodeCityZipcode;
	}

	public void setRateCompanyStrictNameCountrycodeCityZipcode(
			int rateCompanyStrictNameCountrycodeCityZipcode) {
		this.rateCompanyStrictNameCountrycodeCityZipcode = rateCompanyStrictNameCountrycodeCityZipcode;
	}

	public int getRateCompanyStrictNameCountrycodeIdent() {
		return rateCompanyStrictNameCountrycodeIdent;
	}

	public void setRateCompanyStrictNameCountrycodeIdent(
			int rateCompanyStrictNameCountrycodeIdent) {
		this.rateCompanyStrictNameCountrycodeIdent = rateCompanyStrictNameCountrycodeIdent;
	}

	public int getRateServiceStrictNameCountrycodePhone() {
		return rateServiceStrictNameCountrycodePhone;
	}

	public void setRateServiceStrictNameCountrycodePhone(
			int rateServiceStrictNameCountrycodePhone) {
		this.rateServiceStrictNameCountrycodePhone = rateServiceStrictNameCountrycodePhone;
	}

	public int getRateServiceStrictNameCountrycodeEmail() {
		return rateServiceStrictNameCountrycodeEmail;
	}

	public void setRateServiceStrictNameCountrycodeEmail(
			int rateServiceStrictNameCountrycodeEmail) {
		this.rateServiceStrictNameCountrycodeEmail = rateServiceStrictNameCountrycodeEmail;
	}

	public int getRateServiceStrictNameCountrycodeNbandstreetCityZipcode() {
		return rateServiceStrictNameCountrycodeNbandstreetCityZipcode;
	}

	public void setRateServiceStrictNameCountrycodeNbandstreetCityZipcode(
			int rateServiceStrictNameCountrycodeNbandstreetCityZipcode) {
		this.rateServiceStrictNameCountrycodeNbandstreetCityZipcode = rateServiceStrictNameCountrycodeNbandstreetCityZipcode;
	}

	public int getRateServiceStrictNameCountrycodeNbandstreetCity() {
		return rateServiceStrictNameCountrycodeNbandstreetCity;
	}

	public void setRateServiceStrictNameCountrycodeNbandstreetCity(
			int rateServiceStrictNameCountrycodeNbandstreetCity) {
		this.rateServiceStrictNameCountrycodeNbandstreetCity = rateServiceStrictNameCountrycodeNbandstreetCity;
	}

	public int getRateServiceStrictNameCountrycodeCityZipcode() {
		return rateServiceStrictNameCountrycodeCityZipcode;
	}

	public void setRateServiceStrictNameCountrycodeCityZipcode(
			int rateServiceStrictNameCountrycodeCityZipcode) {
		this.rateServiceStrictNameCountrycodeCityZipcode = rateServiceStrictNameCountrycodeCityZipcode;
	}

	public int getRateServiceLikeNameCountrycodePhone() {
		return rateServiceLikeNameCountrycodePhone;
	}

	public void setRateServiceLikeNameCountrycodePhone(
			int rateServiceLikeNameCountrycodePhone) {
		this.rateServiceLikeNameCountrycodePhone = rateServiceLikeNameCountrycodePhone;
	}

	public int getRateServiceLikeNameCountrycodeEmail() {
		return rateServiceLikeNameCountrycodeEmail;
	}

	public void setRateServiceLikeNameCountrycodeEmail(
			int rateServiceLikeNameCountrycodeEmail) {
		this.rateServiceLikeNameCountrycodeEmail = rateServiceLikeNameCountrycodeEmail;
	}

	public int getRateServiceLikeNameCountrycodeNbandstreetCityZipcode() {
		return rateServiceLikeNameCountrycodeNbandstreetCityZipcode;
	}

	public void setRateServiceLikeNameCountrycodeNbandstreetCityZipcode(
			int rateServiceLikeNameCountrycodeNbandstreetCityZipcode) {
		this.rateServiceLikeNameCountrycodeNbandstreetCityZipcode = rateServiceLikeNameCountrycodeNbandstreetCityZipcode;
	}

	public int getRateServiceLikeNameCountrycodeNbandstreetCity() {
		return rateServiceLikeNameCountrycodeNbandstreetCity;
	}

	public void setRateServiceLikeNameCountrycodeNbandstreetCity(
			int rateServiceLikeNameCountrycodeNbandstreetCity) {
		this.rateServiceLikeNameCountrycodeNbandstreetCity = rateServiceLikeNameCountrycodeNbandstreetCity;
	}

	public int getRateServiceLikeNameCountrycodeCityZipcode() {
		return rateServiceLikeNameCountrycodeCityZipcode;
	}

	public void setRateServiceLikeNameCountrycodeCityZipcode(
			int rateServiceLikeNameCountrycodeCityZipcode) {
		this.rateServiceLikeNameCountrycodeCityZipcode = rateServiceLikeNameCountrycodeCityZipcode;
	}

	public int getRateServicePhone() {
		return rateServicePhone;
	}

	public void setRateServicePhone(int rateServicePhone) {
		this.rateServicePhone = rateServicePhone;
	}

	public int getRateServiceEmail() {
		return rateServiceEmail;
	}

	public void setRateServiceEmail(int rateServiceEmail) {
		this.rateServiceEmail = rateServiceEmail;
	}

	public int getRateServicePhoneIdent() {
		return rateServicePhoneIdent;
	}

	public void setRateServicePhoneIdent(int rateServicePhoneIdent) {
		this.rateServicePhoneIdent = rateServicePhoneIdent;
	}

	public int getRateServiceEmailIdent() {
		return rateServiceEmailIdent;
	}

	public void setRateServiceEmailIdent(int rateServiceEmailIdent) {
		this.rateServiceEmailIdent = rateServiceEmailIdent;
	}

	public int getRateServiceIdent() {
		return rateServiceIdent;
	}

	public void setRateServiceIdent(int rateServiceIdent) {
		this.rateServiceIdent = rateServiceIdent;
	}

	public int getRateFirmStrictNameCountrycodePhone() {
		return rateFirmStrictNameCountrycodePhone;
	}

	public void setRateFirmStrictNameCountrycodePhone(
			int rateFirmStrictNameCountrycodePhone) {
		this.rateFirmStrictNameCountrycodePhone = rateFirmStrictNameCountrycodePhone;
	}

	public int getRateFirmStrictNameCountrycodeEmail() {
		return rateFirmStrictNameCountrycodeEmail;
	}

	public void setRateFirmStrictNameCountrycodeEmail(
			int rateFirmStrictNameCountrycodeEmail) {
		this.rateFirmStrictNameCountrycodeEmail = rateFirmStrictNameCountrycodeEmail;
	}

	public int getRateFirmStrictNameCountrycodeNbandstreetCityZipcode() {
		return rateFirmStrictNameCountrycodeNbandstreetCityZipcode;
	}

	public void setRateFirmStrictNameCountrycodeNbandstreetCityZipcode(
			int rateFirmStrictNameCountrycodeNbandstreetCityZipcode) {
		this.rateFirmStrictNameCountrycodeNbandstreetCityZipcode = rateFirmStrictNameCountrycodeNbandstreetCityZipcode;
	}

	public int getRateFirmStrictNameCountrycodeNbandstreetCity() {
		return rateFirmStrictNameCountrycodeNbandstreetCity;
	}

	public void setRateFirmStrictNameCountrycodeNbandstreetCity(
			int rateFirmStrictNameCountrycodeNbandstreetCity) {
		this.rateFirmStrictNameCountrycodeNbandstreetCity = rateFirmStrictNameCountrycodeNbandstreetCity;
	}

	public int getRateFirmStrictNameCountrycodeCityZipcode() {
		return rateFirmStrictNameCountrycodeCityZipcode;
	}

	public void setRateFirmStrictNameCountrycodeCityZipcode(
			int rateFirmStrictNameCountrycodeCityZipcode) {
		this.rateFirmStrictNameCountrycodeCityZipcode = rateFirmStrictNameCountrycodeCityZipcode;
	}

	public int getRateFirmStrictNameCountrycodeZC14() {
		return rateFirmStrictNameCountrycodeZC14;
	}

	public void setRateFirmStrictNameCountrycodeZC14(
			int rateFirmStrictNameCountrycodeZC14) {
		this.rateFirmStrictNameCountrycodeZC14 = rateFirmStrictNameCountrycodeZC14;
	}

	public int getRateFirmStrictNameCountrycodeZC15() {
		return rateFirmStrictNameCountrycodeZC15;
	}

	public void setRateFirmStrictNameCountrycodeZC15(
			int rateFirmStrictNameCountrycodeZC15) {
		this.rateFirmStrictNameCountrycodeZC15 = rateFirmStrictNameCountrycodeZC15;
	}

	public int getRateFirmStrictNameCountrycodeIdent() {
		return rateFirmStrictNameCountrycodeIdent;
	}

	public void setRateFirmStrictNameCountrycodeIdent(
			int rateFirmStrictNameCountrycodeIdent) {
		this.rateFirmStrictNameCountrycodeIdent = rateFirmStrictNameCountrycodeIdent;
	}

	public int getRateFirmStrictNameCountrycode() {
		return rateFirmStrictNameCountrycode;
	}

	public void setRateFirmStrictNameCountrycode(
			int rateFirmStrictNameCountrycode) {
		this.rateFirmStrictNameCountrycode = rateFirmStrictNameCountrycode;
	}

	public int getRateFirmLikeNameCountrycodePhone() {
		return rateFirmLikeNameCountrycodePhone;
	}

	public void setRateFirmLikeNameCountrycodePhone(
			int rateFirmLikeNameCountrycodePhone) {
		this.rateFirmLikeNameCountrycodePhone = rateFirmLikeNameCountrycodePhone;
	}

	public int getRateFirmLikeNameCountrycodeEmail() {
		return rateFirmLikeNameCountrycodeEmail;
	}

	public void setRateFirmLikeNameCountrycodeEmail(
			int rateFirmLikeNameCountrycodeEmail) {
		this.rateFirmLikeNameCountrycodeEmail = rateFirmLikeNameCountrycodeEmail;
	}

	public int getRateFirmLikeNameCountrycodeNbandstreetCityZipcode() {
		return rateFirmLikeNameCountrycodeNbandstreetCityZipcode;
	}

	public void setRateFirmLikeNameCountrycodeNbandstreetCityZipcode(
			int rateFirmLikeNameCountrycodeNbandstreetCityZipcode) {
		this.rateFirmLikeNameCountrycodeNbandstreetCityZipcode = rateFirmLikeNameCountrycodeNbandstreetCityZipcode;
	}

	public int getRateFirmLikeNameCountrycodeNbandstreetCity() {
		return rateFirmLikeNameCountrycodeNbandstreetCity;
	}

	public void setRateFirmLikeNameCountrycodeNbandstreetCity(
			int rateFirmLikeNameCountrycodeNbandstreetCity) {
		this.rateFirmLikeNameCountrycodeNbandstreetCity = rateFirmLikeNameCountrycodeNbandstreetCity;
	}

	public int getRateFirmLikeNameCountrycodeCityZipcode() {
		return rateFirmLikeNameCountrycodeCityZipcode;
	}

	public void setRateFirmLikeNameCountrycodeCityZipcode(
			int rateFirmLikeNameCountrycodeCityZipcode) {
		this.rateFirmLikeNameCountrycodeCityZipcode = rateFirmLikeNameCountrycodeCityZipcode;
	}

	public int getRateFirmLikeNameCountrycodeZC14() {
		return rateFirmLikeNameCountrycodeZC14;
	}

	public void setRateFirmLikeNameCountrycodeZC14(
			int rateFirmLikeNameCountrycodeZC14) {
		this.rateFirmLikeNameCountrycodeZC14 = rateFirmLikeNameCountrycodeZC14;
	}

	public int getRateFirmLikeNameCountrycodeZC15() {
		return rateFirmLikeNameCountrycodeZC15;
	}

	public void setRateFirmLikeNameCountrycodeZC15(
			int rateFirmLikeNameCountrycodeZC15) {
		this.rateFirmLikeNameCountrycodeZC15 = rateFirmLikeNameCountrycodeZC15;
	}

	public int getRateFirmLikeNameCountrycodeIdent() {
		return rateFirmLikeNameCountrycodeIdent;
	}

	public void setRateFirmLikeNameCountrycodeIdent(
			int rateFirmLikeNameCountrycodeIdent) {
		this.rateFirmLikeNameCountrycodeIdent = rateFirmLikeNameCountrycodeIdent;
	}

	public int getRateFirmLikeNameCountrycode() {
		return rateFirmLikeNameCountrycode;
	}

	public void setRateFirmLikeNameCountrycode(int rateFirmLikeNameCountrycode) {
		this.rateFirmLikeNameCountrycode = rateFirmLikeNameCountrycode;
	}
	
	
	public int getRateFirmLikeNameCityStrictCountrycode() {
		return rateFirmLikeNameCityStrictCountrycode;
	}

	public void setRateFirmLikeNameCityStrictCountrycode(int rateFirmLikeNameCityStrictCountrycode) {
		this.rateFirmLikeNameCityStrictCountrycode = rateFirmLikeNameCityStrictCountrycode;
	}
	
	public int getRateFirmLikeNameZipLikeCountrycode() {
		return rateFirmLikeNameZipLikeCountrycode ;
	}

	public void setRateFirmLikeNameZipLikeCountrycode(int rateFirmLikeNameCityStrictZipLikeCountrycode) {
		this.rateFirmLikeNameZipLikeCountrycode = rateFirmLikeNameCityStrictZipLikeCountrycode;
	}
	
	public int getRateFirmLikeNameCityStrictZipLikeCountrycode() {
		return rateFirmLikeNameZipLikeCountrycode ;
	}

	public void setRateFirmLikeNameCityLikeZipLikeCountrycode(int rateFirmLikeNameZipLikeCityLikeCountrycode) {
		this.rateFirmLikeNameZipLikeCityLikeCountrycode = rateFirmLikeNameZipLikeCityLikeCountrycode;
	}

	public int getRateFirmLikeNameCityLikeZipLikeCountrycode() {
		return rateFirmLikeNameZipLikeCityLikeCountrycode ;
	}
	public int getRateFirmPhone() {
		return rateFirmPhone;
	}

	public void setRateFirmPhone(int rateFirmPhone) {
		this.rateFirmPhone = rateFirmPhone;
	}

	public int getRateFirmEmail() {
		return rateFirmEmail;
	}

	public void setRateFirmEmail(int rateFirmEmail) {
		this.rateFirmEmail = rateFirmEmail;
	}

	public int getRateFirmPhoneIdent() {
		return rateFirmPhoneIdent;
	}

	public void setRateFirmPhoneIdent(int rateFirmPhoneIdent) {
		this.rateFirmPhoneIdent = rateFirmPhoneIdent;
	}

	public int getRateFirmEmailIdent() {
		return rateFirmEmailIdent;
	}

	public void setRateFirmEmailIdent(int rateFirmEmailIdent) {
		this.rateFirmEmailIdent = rateFirmEmailIdent;
	}

	public int getRateFirmIdent() {
		return rateFirmIdent;
	}

	public void setRateFirmIdent(int rateFirmIdent) {
		this.rateFirmIdent = rateFirmIdent;
	}

	public int getRateGroupCountrycode() {
		return rateGroupCountrycode;
	}

	public void setRateGroupCountrycode(int rateGroupCountrycode) {
		this.rateGroupCountrycode = rateGroupCountrycode;
	}

	public int getRateCompanyCountrycode() {
		return rateCompanyCountrycode;
	}

	public void setRateCompanyCountrycode(int rateCompanyCountrycode) {
		this.rateCompanyCountrycode = rateCompanyCountrycode;
	}

	public int getRateServiceCountrycode() {
		return rateServiceCountrycode;
	}

	public void setRateServiceCountrycode(int rateServiceCountrycode) {
		this.rateServiceCountrycode = rateServiceCountrycode;
	}

	public int getRateFirmCountrycode() {
		return rateFirmCountrycode;
	}

	public void setRateFirmCountrycode(int rateFirmCountrycode) {
		this.rateFirmCountrycode = rateFirmCountrycode;
	}

	public int getRateGroupZC5() {
		return rateGroupZC5;
	}

	public void setRateGroupZC5(int rateGroupZC5) {
		this.rateGroupZC5 = rateGroupZC5;
	}

	public int getRateCompanyZC5() {
		return rateCompanyZC5;
	}

	public void setRateCompanyZC5(int rateCompanyZC5) {
		this.rateCompanyZC5 = rateCompanyZC5;
	}

	public int getRateServiceZC5() {
		return rateServiceZC5;
	}

	public void setRateServiceZC5(int rateServiceZC5) {
		this.rateServiceZC5 = rateServiceZC5;
	}

	public int getRateFirmZC5() {
		return rateFirmZC5;
	}

	public void setRateFirmZC5(int rateFirmZC5) {
		this.rateFirmZC5 = rateFirmZC5;
	}
	
	public int getRateFirmZC1_2() {
		return rateFirmZC12;
	}

	public void setRateFirmZC1_2(int rateFirmZC) {
		this.rateFirmZC12 = rateFirmZC;
	}

	public int getRateCompanyStrictName() {
		return rateCompanyStrictName;
	}

	public void setRateCompanyStrictName(int rateCompanyStrictName) {
		this.rateCompanyStrictName = rateCompanyStrictName;
	}

	public int getRateCompanyLikeName() {
		return rateCompanyLikeName;
	}

	public void setRateCompanyLikeName(int rateCompanyLikeName) {
		this.rateCompanyLikeName = rateCompanyLikeName;
	}

	public int getRateServiceLikeName() {
		return rateServiceLikeName;
	}

	public void setRateServiceLikeName(int rateServiceLikeName) {
		this.rateServiceLikeName = rateServiceLikeName;
	}

	public int getRateServiceStrictName() {
		return rateServiceStrictName;
	}

	public void setRateServiceStrictName(int rateServiceStrictName) {
		this.rateServiceStrictName = rateServiceStrictName;
	}

	public int getRateFirmLikeName() {
		return rateFirmLikeName;
	}

	public void setRateFirmLikeName(int rateFirmLikeName) {
		this.rateFirmLikeName = rateFirmLikeName;
	}

	public int getRateFirmStrictName() {
		return rateFirmStrictName;
	}

	public void setRateFirmStrictName(int rateFirmStrictName) {
		this.rateFirmStrictName = rateFirmStrictName;
	}

	
}
