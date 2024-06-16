package com.airfrance.repind.firme.searchfirmonmulticriteria.ds;

import com.airfrance.repind.firme.searchfirmonmulticriteria.dto.RequestDTO;
import com.airfrance.repind.firme.searchfirmonmulticriteria.dto.SearchResultDTO;


public interface ICalculateRateDS {

	/*
	 * Calculates rates
	 */
	public void calculateRateByFirmType(SearchResultDTO searchResultDTO, RequestDTO requestDTO);
	
	/*
	 * Max rates
	 */
	public int getMaxRate();
	public int getMaxGroupRate();
	public int getMaxCompanyRate();
	public int getMaxServiceRate();
	public int getMaxFirmRate();
	
	/*
	 * Group rates
	 */
	public int getRateGroupStrictName();
	public int getRateGroupLikeName();
	public int getRateGroupCountrycode();
	public int getRateGroupZC5();
	
	/*
	 * Company rates
	 */
	public int getRateCompanyStrictNameCountrycodePhone();
	public int getRateCompanyStrictNameCountrycodeEmail();
	public int getRateCompanyStrictNameCountrycodeNbandstreetCityZipcode();
	public int getRateCompanyStrictNameCountrycodeNbandstreetCity();
	public int getRateCompanyStrictNameCountrycodeCityZipcode();
	public int getRateCompanyStrictNameCountrycodeIdent();
	public int getRateCompanyCountrycode();
	public int getRateCompanyZC5();
	public int getRateCompanyStrictName();
	public int getRateCompanyLikeName();

	/*
	 * Service rates
	 */
	public int getRateServiceStrictNameCountrycodePhone();
	public int getRateServiceStrictNameCountrycodeEmail();
	public int getRateServiceStrictNameCountrycodeNbandstreetCityZipcode();
	public int getRateServiceStrictNameCountrycodeNbandstreetCity();
	public int getRateServiceStrictNameCountrycodeCityZipcode();
	public int getRateServiceLikeNameCountrycodePhone();
	public int getRateServiceLikeNameCountrycodeEmail();
	public int getRateServiceLikeNameCountrycodeNbandstreetCityZipcode();
	public int getRateServiceLikeNameCountrycodeNbandstreetCity();
	public int getRateServiceLikeNameCountrycodeCityZipcode();
	public int getRateServicePhone();
	public int getRateServiceEmail();
	public int getRateServicePhoneIdent();
	public int getRateServiceEmailIdent();
	public int getRateServiceIdent();
	public int getRateServiceCountrycode();
	public int getRateServiceZC5();
	public int getRateServiceStrictName();
	public int getRateServiceLikeName();

	/*
	 * Firm rates
	 */
	public int getRateFirmStrictNameCountrycodePhone();
	public int getRateFirmStrictNameCountrycodeEmail();
	public int getRateFirmStrictNameCountrycodeNbandstreetCityZipcode();
	public int getRateFirmStrictNameCountrycodeNbandstreetCity();
	public int getRateFirmStrictNameCountrycodeCityZipcode();
	public int getRateFirmStrictNameCountrycodeZC14();
	public int getRateFirmStrictNameCountrycodeZC15();
	public int getRateFirmStrictNameCountrycodeIdent();
	public int getRateFirmStrictNameCountrycode();
	public int getRateFirmLikeNameCountrycodePhone();
	public int getRateFirmLikeNameCountrycodeEmail();
	public int getRateFirmLikeNameCountrycodeNbandstreetCityZipcode();
	public int getRateFirmLikeNameCountrycodeNbandstreetCity();
	public int getRateFirmLikeNameCountrycodeCityZipcode();
	public int getRateFirmLikeNameCountrycodeZC14();
	public int getRateFirmLikeNameCountrycodeZC15();
	public int getRateFirmLikeNameCountrycodeIdent();
	public int getRateFirmLikeNameCountrycode();
	public int getRateFirmPhone();
	public int getRateFirmEmail();
	public int getRateFirmPhoneIdent();
	public int getRateFirmEmailIdent();
	public int getRateFirmIdent();
	public int getRateFirmCountrycode();
	public int getRateFirmZC5();
	public int getRateFirmStrictName();
	public int getRateFirmLikeName();
	
}
