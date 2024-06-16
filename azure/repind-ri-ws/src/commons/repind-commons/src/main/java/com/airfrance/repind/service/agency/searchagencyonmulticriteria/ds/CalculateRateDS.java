package com.airfrance.repind.service.agency.searchagencyonmulticriteria.ds;

import com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto.RequestDTO;
import com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto.SearchResultDTO;
import org.springframework.stereotype.Service;

@Service("agencyCalculateRateDS")
public class CalculateRateDS extends AbstractDS {

    /*===============================================*/
    /*             INSTANCE VARIABLES                */
    /*===============================================*/

    /*
     * Agency rates
     */
    private int rateAgencyNameStrictAndPhone = 100;
    private int rateAgencyNameStrictEmail = 100;
    private int rateAgencyNameStrictCountrycodeNbandstreetCityZipcode = 100;
    private int rateAgencyNameStrictCountrycodeNbandstreetCity = 90;
    private int rateAgencyNameStrictCountrycodeCityZipcode = 85;
    private int rateAgencyNameStrictZC14 = 70;
    private int rateAgencyNameStrictCountrycodeIdent = 70;
    private int rateAgencyNameStrict = 60;
    private int rateAgencyNameStrictCountrycode = 65;

    private int rateAgencyNameLikeAndPhone = 100;
    private int rateAgencyNameLikeEmail = 100;
    private int rateAgencyNameLikeCountrycodeNbandstreetCityZipcode = 100;
    private int rateAgencyNameLikeCountrycodeNbandstreetCity = 85;
    private int rateAgencyNameLikeCountrycodeCityLikeZipLike = 70;
    private int rateAgencyNameLikeCountrycodeCity = 70;
    
    
    //V2
    private int rateAgencyNameLikeZC14 = 60;
    
    private int rateAgencyNameLikeZipLikeZC = 65;
    private int rateAgencyNameLikeCityLikeZC = 65;
    private int rateAgencyNameLikeCityLikeZipLike = 75;
    private int rateAgencyNameLikeCityLikeZipLikeZC = 80;
    private int rateAgencyNameCountryLikeCityLikeZipLikeZC = 85;
    private int rateAgencyNameLikeZipLikeCountry = 65;
    private int rateAgencyNameLikeZipLike = 55;
    //
    
    
    private int rateAgencyNameLikeCountrycodeIdent = 70;
    
   private int rateAgencyNameLikeCountrycode = 60;
    

    private int rateAgencyPhone = 70;
    private int rateAgencyEmail = 70;
    private int rateAgencyPhoneIdent = 80;
    private int rateAgencyEmailIdent = 80;
    private int rateAgencyIdent = 60;
    private int rateAgencyNameLike = 60;
	



    /*===============================================*/
    /*                CONSTRUCTORS                   */
    /*===============================================*/

    public CalculateRateDS() {
        super();
    }

    /*===============================================*/
    /*               PUBLIC METHODS                  */
    /*===============================================*/


    /**
     * Update a SearchResultDTO instance with its relevance rate
     * according to its type
     *
     * @param searchResultDTO
     */
    public void calculateRate(SearchResultDTO searchResultDTO, RequestDTO requestDTO) {
        int rate = 0;

        if (searchResultDTO != null) {
            rate = calculateAgencyRate(requestDTO);
            searchResultDTO.setRate(rate);
        }
    }

    // V2 of calculateRate
    public void calculateRateV2(SearchResultDTO searchResultDTO, RequestDTO requestDTO) {
        int rate = 0;

        if (searchResultDTO != null) {
            rate = calculateAgencyRateV2(requestDTO);
            searchResultDTO.setRate(rate);
        }
    }

    public int getMaxRate() {
        int maxRate =
                Math.max(rateAgencyNameStrictAndPhone,
                        Math.max(rateAgencyNameStrictEmail,
                                Math.max(rateAgencyNameStrictCountrycodeNbandstreetCityZipcode,
                                        Math.max(rateAgencyNameStrictCountrycodeNbandstreetCity,
                                                Math.max(rateAgencyNameStrictCountrycodeCityZipcode,
                                                        Math.max(rateAgencyNameStrictZC14,
                                                                Math.max(rateAgencyNameStrictCountrycodeIdent,
                                                                        Math.max(rateAgencyNameStrict,
                                                                                Math.max(rateAgencyNameLikeAndPhone,
                                                                                        Math.max(rateAgencyNameLikeEmail,
                                                                                                Math.max(rateAgencyNameLikeCountrycodeNbandstreetCityZipcode,
                                                                                                        Math.max(rateAgencyNameLikeCountrycodeNbandstreetCity,
                                                                                                                Math.max(rateAgencyNameLikeCountrycodeCityLikeZipLike,
                                                                                                                        Math.max(rateAgencyNameLikeZC14,
                                                                                                                                Math.max(rateAgencyNameLikeCountrycodeIdent,
                                                                                                                                        Math.max(rateAgencyNameLike,
                                                                                                                                                Math.max(rateAgencyPhone,
                                                                                                                                                        Math.max(rateAgencyEmail,
                                                                                                                                                                Math.max(rateAgencyPhoneIdent,
                                                                                                                                                                        Math.max(rateAgencyEmailIdent,
                                                                                                                                                                                rateAgencyIdent))))))))))))))))))));

        return maxRate;
    }

    public int getMaxRateForV2() {
        int maxRate =
                Math.max(rateAgencyNameStrictAndPhone,
                        Math.max(rateAgencyNameStrictEmail,
                                Math.max(rateAgencyNameStrictCountrycodeNbandstreetCityZipcode,
                                        Math.max(rateAgencyNameStrictCountrycodeNbandstreetCity,
                                                Math.max(rateAgencyNameStrictCountrycodeCityZipcode,
                                                        Math.max(rateAgencyNameStrictZC14,
                                                                Math.max(rateAgencyNameStrictCountrycodeIdent,
                                                                        Math.max(rateAgencyNameStrict,
                                                                                Math.max(rateAgencyNameLikeAndPhone,
                                                                                        Math.max(rateAgencyNameLikeEmail,
                                                                                                Math.max(rateAgencyNameLikeCountrycodeNbandstreetCityZipcode,
                                                                                                        Math.max(rateAgencyNameLikeCountrycodeNbandstreetCity,
                                                                                                                Math.max(rateAgencyNameLikeCountrycodeCityLikeZipLike,
                                                                                                                        Math.max(rateAgencyNameLikeCountrycodeCity,
                                                                                                                            Math.max(rateAgencyNameLikeZC14,
                                                                                                                                    Math.max(rateAgencyNameLikeCountrycodeIdent,
                                                                                                                                            Math.max(rateAgencyNameLike,
                                                                                                                                            		Math.max(rateAgencyNameLikeCountrycode,
                                                                                                                                            				Math.max(rateAgencyNameLikeCountrycodeCity,
                                                                                                                                            						Math.max(rateAgencyNameLikeCountrycodeCityLikeZipLike,
					                                                                                                                                                    Math.max(rateAgencyPhone,
					                                                                                                                                                            Math.max(rateAgencyEmail,
					                                                                                                                                                                    Math.max(rateAgencyPhoneIdent,
					                                                                                                                                                                            Math.max(rateAgencyEmailIdent,
					                                                                                                                                                                                    rateAgencyIdent))))))))))))))))))))))));

        return maxRate;
    }

    /*===============================================*/
    /*               PRIVATE METHODS                 */
    /*===============================================*/

    /**
     * Returns Agency relevance rate
     */
    private int calculateAgencyRate(RequestDTO requestDTO) {

        int rate = 0;

        /*
         * Name(Strict) and phone
         */
        if ((BuildConditionsDS.isNameStrictConditionSet(requestDTO))
                && (BuildConditionsDS.isPhoneConditionSet(requestDTO))) {
            if (rateAgencyNameStrictAndPhone > rate) {
                rate = rateAgencyNameStrictAndPhone;
            }
        }

        /*
         * Name(Like) and phone
         */
        if ((BuildConditionsDS.isNameLikeConditionSet(requestDTO))
                && (BuildConditionsDS.isPhoneConditionSet(requestDTO))) {
            if (rateAgencyNameLikeAndPhone > rate) {
                rate = rateAgencyNameLikeAndPhone;
            }
        }

        /*
         * Name(Strict) and email
         */
        if ((BuildConditionsDS.isNameStrictConditionSet(requestDTO))
                && (BuildConditionsDS.isEmailConditionSet(requestDTO))) {
            if (rateAgencyNameStrictEmail > rate) {
                rate = rateAgencyNameStrictEmail;
            }
        }

        /*
         * Name(Like) and email
         */
        if ((BuildConditionsDS.isNameLikeConditionSet(requestDTO))
                && (BuildConditionsDS.isEmailConditionSet(requestDTO))) {
            if (rateAgencyNameLikeEmail > rate) {
                rate = rateAgencyNameLikeEmail;
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
            if (rateAgencyNameStrictCountrycodeNbandstreetCityZipcode > rate) {
                rate = rateAgencyNameStrictCountrycodeNbandstreetCityZipcode;
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
            if (rateAgencyNameLikeCountrycodeNbandstreetCityZipcode > rate) {
                rate = rateAgencyNameLikeCountrycodeNbandstreetCityZipcode;
            }
        }

        /*
         * Name(Strict), CountryCode NbAndStreet and like city
         */
        if ((BuildConditionsDS.isNameStrictConditionSet(requestDTO))
                && (BuildConditionsDS.isCountryConditionSet(requestDTO))
                && (BuildConditionsDS.isNumberAndStreetConditionSet(requestDTO))
                && ((BuildConditionsDS.isCityLikeConditionSet(requestDTO)) || (BuildConditionsDS.isCityStrictConditionSet(requestDTO)))) {
            if (rateAgencyNameStrictCountrycodeNbandstreetCity > rate) {
                rate = rateAgencyNameStrictCountrycodeNbandstreetCity;
            }
        }

        /*
         * Name(Like), CountryCode NbAndStreet and like city
         */
        if ((BuildConditionsDS.isNameLikeConditionSet(requestDTO))
                && (BuildConditionsDS.isCountryConditionSet(requestDTO))
                && (BuildConditionsDS.isNumberAndStreetConditionSet(requestDTO))
                && ((BuildConditionsDS.isCityLikeConditionSet(requestDTO)) || (BuildConditionsDS.isCityLikeConditionSet(requestDTO)))) {
            if (rateAgencyNameLikeCountrycodeNbandstreetCity > rate) {
                rate = rateAgencyNameLikeCountrycodeNbandstreetCity;
            }
        }

        /*
         * Name(Strict), Country, City And Zip code
         */
        if ((BuildConditionsDS.isNameStrictConditionSet(requestDTO))
                && (BuildConditionsDS.isCountryConditionSet(requestDTO))
                && (BuildConditionsDS.isCityStrictConditionSet(requestDTO))
                && (BuildConditionsDS.isZipStrictConditionSet(requestDTO))) {
            if (rateAgencyNameStrictCountrycodeCityZipcode > rate) {
                rate = rateAgencyNameStrictCountrycodeCityZipcode;
            }
        }

        /*
         * Name(Like), Country, City And Zip code
         */
        if ((BuildConditionsDS.isNameLikeConditionSet(requestDTO))
                && (BuildConditionsDS.isCountryConditionSet(requestDTO))
                && (BuildConditionsDS.isCityLikeConditionSet(requestDTO))
                && (BuildConditionsDS.isZipLikeConditionSet(requestDTO))) {
            if (rateAgencyNameLikeCountrycodeCityLikeZipLike > rate) {
                rate = rateAgencyNameLikeCountrycodeCityLikeZipLike;
            }
        }

        /*
         * Name(Strict), ZC1 to ZC4
         */
        if ((BuildConditionsDS.isNameStrictConditionSet(requestDTO))
                && (BuildConditionsDS.isZC1ConditionSet(requestDTO))
                && (BuildConditionsDS.isZC2ConditionSet(requestDTO))
                && (BuildConditionsDS.isZC3ConditionSet(requestDTO))
                && (BuildConditionsDS.isZC4ConditionSet(requestDTO))) {
            if (rateAgencyNameStrictZC14 > rate) {
                rate = rateAgencyNameStrictZC14;
            }
        }

        /*
         * Name(Like), ZC1 to ZC4
         */
        if ((BuildConditionsDS.isNameLikeConditionSet(requestDTO))
                && (BuildConditionsDS.isZC1ConditionSet(requestDTO))
                && (BuildConditionsDS.isZC2ConditionSet(requestDTO))
                && (BuildConditionsDS.isZC3ConditionSet(requestDTO))
                && (BuildConditionsDS.isZC4ConditionSet(requestDTO))) {
            if (rateAgencyNameLikeZC14 > rate) {
                rate = rateAgencyNameLikeZC14;
            }
        }


        /*
         * Name(Strict), CountryCode and Ident
         */
        if ((BuildConditionsDS.isNameStrictConditionSet(requestDTO))
                && (BuildConditionsDS.isCountryConditionSet(requestDTO))
                && (BuildConditionsDS.isIdentTypeConditionSet(requestDTO))
                && (BuildConditionsDS.isIdentValueConditionSet(requestDTO))) {
            if (rateAgencyNameStrictCountrycodeIdent > rate) {
                rate = rateAgencyNameStrictCountrycodeIdent;
            }
        }

        /*
         * Name(Like), CountryCode and Ident
         */
        if ((BuildConditionsDS.isNameLikeConditionSet(requestDTO))
                && (BuildConditionsDS.isCountryConditionSet(requestDTO))
                && (BuildConditionsDS.isIdentTypeConditionSet(requestDTO))
                && (BuildConditionsDS.isIdentValueConditionSet(requestDTO))) {
            if (rateAgencyNameLikeCountrycodeIdent > rate) {
                rate = rateAgencyNameLikeCountrycodeIdent;
            }
        }

        /*
         * Name(Strict), CountryCode
         */
        if ((BuildConditionsDS.isNameStrictConditionSet(requestDTO))
                && (BuildConditionsDS.isCountryConditionSet(requestDTO))) {
            if (rateAgencyNameStrictCountrycode > rate) {
                rate = rateAgencyNameStrictCountrycode;
            }
        }

        /*
         * Name(Like), Zip(Like), CountryCode
         */
        if ((BuildConditionsDS.isNameLikeConditionSet(requestDTO))
                && (BuildConditionsDS.isCountryConditionSet(requestDTO))
                && (BuildConditionsDS.isZipLikeConditionSet(requestDTO))) {
            if (rateAgencyNameLikeZipLikeCountry > rate) {
                rate = rateAgencyNameLikeZipLikeCountry;
            }
        }
        
        if ((BuildConditionsDS.isNameLikeConditionSet(requestDTO))
                && (BuildConditionsDS.isCountryConditionSet(requestDTO))
                && (BuildConditionsDS.isZipLikeConditionSet(requestDTO))) {
            if (rateAgencyNameLikeZipLikeCountry > rate) {
                rate = rateAgencyNameLikeZipLikeCountry;
            }
        }

        /*
         * Name(Strict)
         */
        if (BuildConditionsDS.isNameStrictConditionSet(requestDTO)) {
            if (rateAgencyNameStrict > rate) {
                rate = rateAgencyNameStrict;
            }
        }

        /*
         * Name(Like)
         */
        if (BuildConditionsDS.isNameLikeConditionSet(requestDTO)) {
            if (rateAgencyNameLike > rate) {
                rate = rateAgencyNameLike;
            }
        }

        /*
         * Phone
         */
        if (BuildConditionsDS.isPhoneConditionSet(requestDTO)) {
            if (rateAgencyPhone > rate) {
                rate = rateAgencyPhone;
            }
        }

        /*
         * Email
         */
        if (BuildConditionsDS.isEmailConditionSet(requestDTO)) {
            if (rateAgencyEmail > rate) {
                rate = rateAgencyEmail;
            }
        }

        /*
         * Phone and Ident
         */
        if ((BuildConditionsDS.isPhoneConditionSet(requestDTO))
                && (BuildConditionsDS.isIdentTypeConditionSet(requestDTO))
                && (BuildConditionsDS.isIdentValueConditionSet(requestDTO))) {
            if (rateAgencyPhoneIdent > rate) {
                rate = rateAgencyPhoneIdent;
            }
        }

        /*
         * Email and Ident
         */
        if ((BuildConditionsDS.isEmailConditionSet(requestDTO))
                && (BuildConditionsDS.isIdentTypeConditionSet(requestDTO))
                && (BuildConditionsDS.isIdentValueConditionSet(requestDTO))) {
            if (rateAgencyEmailIdent > rate) {
                rate = rateAgencyEmailIdent;
            }
        }

        /*
         * Ident
         */
        if ((BuildConditionsDS.isIdentTypeConditionSet(requestDTO))
                && (BuildConditionsDS.isIdentValueConditionSet(requestDTO))) {
            if (rateAgencyIdent > rate) {
                rate = rateAgencyIdent;
            }
        }


        return rate;
    }

    // V2 of calculateAgencyRate
    private int calculateAgencyRateV2(RequestDTO requestDTO) {

        int rate = 0;

        /*
         * Name(Strict) and phone
         */
        if((BuildConditionsDS.isNameStrictConditionSet(requestDTO))
                && (BuildConditionsDS.isPhoneConditionSet(requestDTO)))
        {
            if(rateAgencyNameStrictAndPhone > rate)
            {
                rate = rateAgencyNameStrictAndPhone;
            }
        }

        /*
         * Name(Like) and phone
         */
        if((BuildConditionsDS.isNameLikeConditionSet(requestDTO))
                && (BuildConditionsDS.isPhoneConditionSet(requestDTO)))
        {
            if(rateAgencyNameLikeAndPhone > rate)
            {
                rate = rateAgencyNameLikeAndPhone;
            }
        }

        /*
         * Name(Strict) and email
         */
        if((BuildConditionsDS.isNameStrictConditionSet(requestDTO))
                && (BuildConditionsDS.isEmailConditionSet(requestDTO)))
        {
            if(rateAgencyNameStrictEmail > rate)
            {
                rate = rateAgencyNameStrictEmail;
            }
        }

        /*
         * Name(Like) and email
         */
        if((BuildConditionsDS.isNameLikeConditionSet(requestDTO))
                && (BuildConditionsDS.isEmailConditionSet(requestDTO)))
        {
            if(rateAgencyNameLikeEmail > rate)
            {
                rate = rateAgencyNameLikeEmail;
            }
        }

        /*
         * Name(Strict), CountryCode, NbAndStreet, city and zip
         */
        if((BuildConditionsDS.isNameStrictConditionSet(requestDTO))
                && (BuildConditionsDS.isCountryConditionSet(requestDTO))
                && (BuildConditionsDS.isNumberAndStreetConditionSet(requestDTO))
                && (BuildConditionsDS.isCityStrictConditionSet(requestDTO))
                && (BuildConditionsDS.isZipStrictConditionSet(requestDTO)))
        {
            if(rateAgencyNameStrictCountrycodeNbandstreetCityZipcode > rate)
            {
                rate = rateAgencyNameStrictCountrycodeNbandstreetCityZipcode;
            }
        }

        /*
         * Name(Like), CountryCode, NbAndStreet, city and zip
         */
        if((BuildConditionsDS.isNameLikeConditionSet(requestDTO))
                && (BuildConditionsDS.isCountryConditionSet(requestDTO))
                && (BuildConditionsDS.isNumberAndStreetConditionSet(requestDTO))
                && (BuildConditionsDS.isCityLikeConditionSet(requestDTO))
                && (BuildConditionsDS.isZipLikeConditionSet(requestDTO)))
        {
            if(rateAgencyNameLikeCountrycodeNbandstreetCityZipcode > rate)
            {
                rate = rateAgencyNameLikeCountrycodeNbandstreetCityZipcode;
            }
        }

        /*
         * Name(Strict), CountryCode NbAndStreet and like city
         */
        if((BuildConditionsDS.isNameStrictConditionSet(requestDTO))
                && (BuildConditionsDS.isCountryConditionSet(requestDTO))
                && (BuildConditionsDS.isNumberAndStreetConditionSet(requestDTO))
                && ((BuildConditionsDS.isCityLikeConditionSet(requestDTO)) || (BuildConditionsDS.isCityStrictConditionSet(requestDTO))))
        {
            if(rateAgencyNameStrictCountrycodeNbandstreetCity > rate)
            {
                rate = rateAgencyNameStrictCountrycodeNbandstreetCity;
            }
        }

        /*
         * Name(Like), CountryCode NbAndStreet and like city
         */
        if((BuildConditionsDS.isNameLikeConditionSet(requestDTO))
                && (BuildConditionsDS.isCountryConditionSet(requestDTO))
                && (BuildConditionsDS.isNumberAndStreetConditionSet(requestDTO))
                && ((BuildConditionsDS.isCityLikeConditionSet(requestDTO)) || (BuildConditionsDS.isCityLikeConditionSet(requestDTO))))
        {
            if(rateAgencyNameLikeCountrycodeNbandstreetCity > rate)
            {
                rate = rateAgencyNameLikeCountrycodeNbandstreetCity;
            }
        }

        /*
         * Name(Strict), Country, City And Zip code
         */
        if((BuildConditionsDS.isNameStrictConditionSet(requestDTO))
                && (BuildConditionsDS.isCountryConditionSet(requestDTO))
                && (BuildConditionsDS.isCityStrictConditionSet(requestDTO))
                && (BuildConditionsDS.isZipStrictConditionSet(requestDTO)))
        {
            if(rateAgencyNameStrictCountrycodeCityZipcode > rate)
            {
                rate = rateAgencyNameStrictCountrycodeCityZipcode;
            }
        }

        /*
         * Name(Like), Country, City And Zip code
         */
        if((BuildConditionsDS.isNameLikeConditionSet(requestDTO))
                && (BuildConditionsDS.isCountryConditionSet(requestDTO))
                && (BuildConditionsDS.isCityLikeConditionSet(requestDTO))
                && (BuildConditionsDS.isZipLikeConditionSet(requestDTO)))
        {
            if(rateAgencyNameLikeCountrycodeCityLikeZipLike > rate)
            {
                rate = rateAgencyNameLikeCountrycodeCityLikeZipLike;
            }
        }

        /*
         * Name(Like), CountryCode, City
         */
        if((BuildConditionsDS.isNameLikeConditionSet(requestDTO))
                && (BuildConditionsDS.isCountryConditionSet(requestDTO))
                && ((BuildConditionsDS.isCityLikeConditionSet(requestDTO))
                || (BuildConditionsDS.isCityStrictConditionSet(requestDTO))))
        {
            if(rateAgencyNameLikeCountrycodeCity > rate)
            {
                rate = rateAgencyNameLikeCountrycodeCity;
            }
        }

        /*
         * Name(Strict), ZC1 to ZC4
         */
        if((BuildConditionsDS.isNameStrictConditionSet(requestDTO))
                && (BuildConditionsDS.isZC1ConditionSet(requestDTO))
                && (BuildConditionsDS.isZC2ConditionSet(requestDTO))
                && (BuildConditionsDS.isZC3ConditionSet(requestDTO))
                && (BuildConditionsDS.isZC4ConditionSet(requestDTO)))
        {
            if(rateAgencyNameStrictZC14 > rate)
            {
                rate = rateAgencyNameStrictZC14;
            }
        }

        /*
         * Name(Like), ZC1 to ZC4
         */
        if((BuildConditionsDS.isNameLikeConditionSet(requestDTO))
                && (BuildConditionsDS.isZC1ConditionSet(requestDTO))
                && (BuildConditionsDS.isZC2ConditionSet(requestDTO))
                && (BuildConditionsDS.isZC3ConditionSet(requestDTO))
                && (BuildConditionsDS.isZC4ConditionSet(requestDTO)))
        {
            if(rateAgencyNameLikeZC14 > rate)
            {
                rate = rateAgencyNameLikeZC14;
            }
        }
        
        /*
         * Name(Like), ZC1 to ZC4, Zip Like/City Like
         */
        if (BuildConditionsDS.isNameLikeConditionSet(requestDTO)
        		&& BuildConditionsDS.isZipLikeConditionSet(requestDTO)
                && BuildConditionsDS.isZC1ConditionSet(requestDTO)
                && BuildConditionsDS.isZC2ConditionSet(requestDTO)
                && BuildConditionsDS.isZC3ConditionSet(requestDTO)
                && BuildConditionsDS.isZC4ConditionSet(requestDTO))
        {
            if(rateAgencyNameLikeZipLikeZC > rate)
            {
                rate = rateAgencyNameLikeZipLikeZC;
            }
        }
        
        /*
         * Name(Like), ZC1 to ZC4, Zip Like/City Like
         */
        if (BuildConditionsDS.isNameLikeConditionSet(requestDTO)
        		&& BuildConditionsDS.isCityLikeConditionSet(requestDTO)
                && BuildConditionsDS.isZipLikeConditionSet(requestDTO)
                && BuildConditionsDS.isZC1ConditionSet(requestDTO)
                && BuildConditionsDS.isZC2ConditionSet(requestDTO)
                && BuildConditionsDS.isZC3ConditionSet(requestDTO)
                && BuildConditionsDS.isZC4ConditionSet(requestDTO))
        {
            if(rateAgencyNameLikeCityLikeZipLikeZC > rate)
            {
                rate = rateAgencyNameLikeCityLikeZipLikeZC;
            }
        }
        
        /*
         * Name(Like), ZC1 to ZC4, Zip Like/City Like Country
         */
        if (BuildConditionsDS.isNameLikeConditionSet(requestDTO)
        		&& BuildConditionsDS.isCountryConditionSet(requestDTO)
        		&& BuildConditionsDS.isCityLikeConditionSet(requestDTO)
                && BuildConditionsDS.isZipLikeConditionSet(requestDTO)
                && BuildConditionsDS.isZC1ConditionSet(requestDTO)
                && BuildConditionsDS.isZC2ConditionSet(requestDTO)
                && BuildConditionsDS.isZC3ConditionSet(requestDTO)
                && BuildConditionsDS.isZC4ConditionSet(requestDTO))
        {
            if(rateAgencyNameCountryLikeCityLikeZipLikeZC > rate)
            {
                rate = rateAgencyNameCountryLikeCityLikeZipLikeZC;
            }
        }
        
        /*
         * Name(Like), Zip Like/City Like
         */
        if((BuildConditionsDS.isNameLikeConditionSet(requestDTO))
        		&& BuildConditionsDS.isCityLikeConditionSet(requestDTO)
                && BuildConditionsDS.isZipLikeConditionSet(requestDTO))              
        {
            if(rateAgencyNameLikeCityLikeZipLike > rate)
            {
                rate = rateAgencyNameLikeCityLikeZipLike;
            }
        }


        /*
         * Name(Strict), CountryCode and Ident
         */
        if((BuildConditionsDS.isNameStrictConditionSet(requestDTO))
                && (BuildConditionsDS.isCountryConditionSet(requestDTO))
                && (BuildConditionsDS.isIdentTypeConditionSet(requestDTO))
                && (BuildConditionsDS.isIdentValueConditionSet(requestDTO)))
        {
            if(rateAgencyNameStrictCountrycodeIdent > rate)
            {
                rate = rateAgencyNameStrictCountrycodeIdent;
            }
        }

        /*
         * Name(Like), CountryCode and Ident
         */
        if((BuildConditionsDS.isNameLikeConditionSet(requestDTO))
                && (BuildConditionsDS.isCountryConditionSet(requestDTO))
                && (BuildConditionsDS.isIdentTypeConditionSet(requestDTO))
                && (BuildConditionsDS.isIdentValueConditionSet(requestDTO)))
        {
            if(rateAgencyNameLikeCountrycodeIdent > rate)
            {
                rate = rateAgencyNameLikeCountrycodeIdent;
            }
        }

        /*
         * Name(Strict), CountryCode
         */
        if((BuildConditionsDS.isNameStrictConditionSet(requestDTO))
                && (BuildConditionsDS.isCountryConditionSet(requestDTO)))
        {
            if(rateAgencyNameStrictCountrycode > rate)
            {
                rate = rateAgencyNameStrictCountrycode;
            }
        }

        /*
         * Name(Like), CountryCode
         */
        if((BuildConditionsDS.isNameLikeConditionSet(requestDTO))
                && (BuildConditionsDS.isCountryConditionSet(requestDTO)))
        {
            if(rateAgencyNameLikeCountrycode > rate)
            {
                rate = rateAgencyNameLikeCountrycode;
            }
        }

        /*
         * Name(Strict)
         */
        if(BuildConditionsDS.isNameStrictConditionSet(requestDTO))
        {
            if(rateAgencyNameStrict > rate)
            {
                rate = rateAgencyNameStrict;
            }
        }

        /*
         * Name(Like)
         */
        if(BuildConditionsDS.isNameLikeConditionSet(requestDTO))
        {
            if(rateAgencyNameLike > rate)
            {
                rate = rateAgencyNameLike;
            }
        }

        /*
         * Phone
         */
        if(BuildConditionsDS.isPhoneConditionSet(requestDTO))
        {
            if(rateAgencyPhone > rate)
            {
                rate = rateAgencyPhone;
            }
        }

        /*
         * Email
         */
        if(BuildConditionsDS.isEmailConditionSet(requestDTO))
        {
            if(rateAgencyEmail > rate)
            {
                rate = rateAgencyEmail;
            }
        }

        /*
         * Phone and Ident
         */
        if((BuildConditionsDS.isPhoneConditionSet(requestDTO))
                && (BuildConditionsDS.isIdentTypeConditionSet(requestDTO))
                && (BuildConditionsDS.isIdentValueConditionSet(requestDTO)))
        {
            if(rateAgencyPhoneIdent > rate)
            {
                rate = rateAgencyPhoneIdent;
            }
        }

        /*
         * Email and Ident
         */
        if((BuildConditionsDS.isEmailConditionSet(requestDTO))
                && (BuildConditionsDS.isIdentTypeConditionSet(requestDTO))
                && (BuildConditionsDS.isIdentValueConditionSet(requestDTO)))
        {
            if(rateAgencyEmailIdent > rate)
            {
                rate = rateAgencyEmailIdent;
            }
        }

        /*
         * Ident
         */
        if((BuildConditionsDS.isIdentTypeConditionSet(requestDTO))
                && (BuildConditionsDS.isIdentValueConditionSet(requestDTO)))
        {
            if(rateAgencyIdent > rate)
            {
                rate = rateAgencyIdent;
            }
        }
        
        /*
         * Name(Like), Zip(Like), CountryCode
         */
        if ((BuildConditionsDS.isNameLikeConditionSet(requestDTO))
                && (BuildConditionsDS.isCountryConditionSet(requestDTO))
                && (BuildConditionsDS.isZipLikeConditionSet(requestDTO))) {
            if (rateAgencyNameLikeZipLikeCountry > rate) {
                rate = rateAgencyNameLikeZipLikeCountry;
            }
        }


        return rate;
    }


    /*===============================================*/
    /*                  ACCESSORS                    */
    /*===============================================*/

    public int getRateAgencyNameStrictAndPhone() {
        return rateAgencyNameStrictAndPhone;
    }

    public void setRateAgencyNameStrictAndPhone(int rateAgencyNameStrictAndPhone) {
        this.rateAgencyNameStrictAndPhone = rateAgencyNameStrictAndPhone;
    }

    public int getRateAgencyNameStrictEmail() {
        return rateAgencyNameStrictEmail;
    }

    public void setRateAgencyNameStrictEmail(int rateAgencyNameStrictEmail) {
        this.rateAgencyNameStrictEmail = rateAgencyNameStrictEmail;
    }

    public int getRateAgencyNameStrictCountrycodeNbandstreetCityZipcode() {
        return rateAgencyNameStrictCountrycodeNbandstreetCityZipcode;
    }

    public void setRateAgencyNameStrictCountrycodeNbandstreetCityZipcode(
            int rateAgencyNameStrictCountrycodeNbandstreetCityZipcode) {
        this.rateAgencyNameStrictCountrycodeNbandstreetCityZipcode = rateAgencyNameStrictCountrycodeNbandstreetCityZipcode;
    }

    public int getRateAgencyNameStrictCountrycodeNbandstreetCity() {
        return rateAgencyNameStrictCountrycodeNbandstreetCity;
    }

    public void setRateAgencyNameStrictCountrycodeNbandstreetCity(
            int rateAgencyNameStrictCountrycodeNbandstreetCity) {
        this.rateAgencyNameStrictCountrycodeNbandstreetCity = rateAgencyNameStrictCountrycodeNbandstreetCity;
    }

    public int getRateAgencyNameStrictCountrycodeCityZipcode() {
        return rateAgencyNameStrictCountrycodeCityZipcode;
    }

    public void setRateAgencyNameStrictCountrycodeCityZipcode(
            int rateAgencyNameStrictCountrycodeCityZipcode) {
        this.rateAgencyNameStrictCountrycodeCityZipcode = rateAgencyNameStrictCountrycodeCityZipcode;
    }

    public int getRateAgencyNameStrictZC14() {
        return rateAgencyNameStrictZC14;
    }

    public void setRateAgencyNameStrictZC14(int rateAgencyNameStrictZC14) {
        this.rateAgencyNameStrictZC14 = rateAgencyNameStrictZC14;
    }

    public int getRateAgencyNameStrictCountrycodeIdent() {
        return rateAgencyNameStrictCountrycodeIdent;
    }

    public void setRateAgencyNameStrictCountrycodeIdent(
            int rateAgencyNameStrictCountrycodeIdent) {
        this.rateAgencyNameStrictCountrycodeIdent = rateAgencyNameStrictCountrycodeIdent;
    }

    public int getRateAgencyNameStrict() {
        return rateAgencyNameStrict;
    }

    public void setRateAgencyNameStrict(int rateAgencyNameStrict) {
        this.rateAgencyNameStrict = rateAgencyNameStrict;
    }

    public int getRateAgencyNameStrictCountrycode() {
        return rateAgencyNameStrictCountrycode;
    }

    public void setRateAgencyNameStrictCountrycode(
            int rateAgencyNameStrictCountrycode) {
        this.rateAgencyNameStrictCountrycode = rateAgencyNameStrictCountrycode;
    }

    public int getRateAgencyNameLikeAndPhone() {
        return rateAgencyNameLikeAndPhone;
    }

    public void setRateAgencyNameLikeAndPhone(int rateAgencyNameLikeAndPhone) {
        this.rateAgencyNameLikeAndPhone = rateAgencyNameLikeAndPhone;
    }

    public int getRateAgencyNameLikeEmail() {
        return rateAgencyNameLikeEmail;
    }

    public void setRateAgencyNameLikeEmail(int rateAgencyNameLikeEmail) {
        this.rateAgencyNameLikeEmail = rateAgencyNameLikeEmail;
    }

    public int getRateAgencyNameLikeCountrycodeNbandstreetCityZipcode() {
        return rateAgencyNameLikeCountrycodeNbandstreetCityZipcode;
    }

    public void setRateAgencyNameLikeCountrycodeNbandstreetCityZipcode(
            int rateAgencyNameLikeCountrycodeNbandstreetCityZipcode) {
        this.rateAgencyNameLikeCountrycodeNbandstreetCityZipcode = rateAgencyNameLikeCountrycodeNbandstreetCityZipcode;
    }

    public int getRateAgencyNameLikeCountrycodeNbandstreetCity() {
        return rateAgencyNameLikeCountrycodeNbandstreetCity;
    }

    public void setRateAgencyNameLikeCountrycodeNbandstreetCity(
            int rateAgencyNameLikeCountrycodeNbandstreetCity) {
        this.rateAgencyNameLikeCountrycodeNbandstreetCity = rateAgencyNameLikeCountrycodeNbandstreetCity;
    }

    public int getRateAgencyNameLikeCountrycodeCityLikeZipLike() {
        return rateAgencyNameLikeCountrycodeCityLikeZipLike;
    }

    public void setRateAgencyNameLikeCountrycodeCityZipcode(
            int rateAgencyNameLikeCountrycodeCityZipcode) {
        this.rateAgencyNameLikeCountrycodeCityLikeZipLike = rateAgencyNameLikeCountrycodeCityZipcode;
    }

    public int getRateAgencyNameLikeZC14() {
        return rateAgencyNameLikeZC14;
    }

    public void setRateAgencyNameLikeCountrycodeCity(int rateAgencyNameLikeCountrycodeCity) {
        this.rateAgencyNameLikeCountrycodeCity = rateAgencyNameLikeCountrycodeCity;
    }

    public int getRateAgencyNameLikeCountrycodeCity() {
        return this.rateAgencyNameLikeCountrycodeCity;
    }

    public void setRateAgencyNameLikeZC14(int rateAgencyNameLikeZC14) {
        this.rateAgencyNameLikeZC14 = rateAgencyNameLikeZC14;
    }

    public int getRateAgencyNameLikeCountrycodeIdent() {
        return rateAgencyNameLikeCountrycodeIdent;
    }

    public void setRateAgencyNameLikeCountrycodeIdent(
            int rateAgencyNameLikeCountrycodeIdent) {
        this.rateAgencyNameLikeCountrycodeIdent = rateAgencyNameLikeCountrycodeIdent;
    }

    public int getRateAgencyNameLike() {
        return rateAgencyNameLike;
    }

    public void setRateAgencyNameLike(int rateAgencyNameLike) {
        this.rateAgencyNameLike = rateAgencyNameLike;
    }

    public int getRateAgencyNameLikeCountrycode() {
        return rateAgencyNameLikeCountrycode;
    }

    public void setRateAgencyNameLikeCountrycode(int rateAgencyNameLikeCountrycode) {
        this.rateAgencyNameLikeCountrycode = rateAgencyNameLikeCountrycode;
    }

    public int getRateAgencyPhone() {
        return rateAgencyPhone;
    }

    public void setRateAgencyPhone(int rateAgencyPhone) {
        this.rateAgencyPhone = rateAgencyPhone;
    }

    public int getRateAgencyEmail() {
        return rateAgencyEmail;
    }

    public void setRateAgencyEmail(int rateAgencyEmail) {
        this.rateAgencyEmail = rateAgencyEmail;
    }

    public int getRateAgencyPhoneIdent() {
        return rateAgencyPhoneIdent;
    }

    public void setRateAgencyPhoneIdent(int rateAgencyPhoneIdent) {
        this.rateAgencyPhoneIdent = rateAgencyPhoneIdent;
    }

    public int getRateAgencyEmailIdent() {
        return rateAgencyEmailIdent;
    }

    public void setRateAgencyEmailIdent(int rateAgencyEmailIdent) {
        this.rateAgencyEmailIdent = rateAgencyEmailIdent;
    }

    public int getRateAgencyIdent() {
        return rateAgencyIdent;
    }

    public void setRateAgencyIdent(int rateAgencyIdent) {
        this.rateAgencyIdent = rateAgencyIdent;
    }

	public int getRateAgencyNameLikeZipLike() {
		return this.rateAgencyNameLikeZipLike;
	}
	
	 public void setRateAgencyNameLikeZipLike(int rateNameLikeZipLike) {
	        this.rateAgencyNameLikeZipLike = rateNameLikeZipLike;
	 }
	 
	 public int getRateAgencyNameLikeZipLikeCountry() {
			return this.rateAgencyNameLikeZipLikeCountry;
	}
		
	public void setRateAgencyNameLikeZipLikeCountry(int rateNameLikeZipLikeCountry) {
	        this.rateAgencyNameLikeZipLikeCountry = rateNameLikeZipLikeCountry;
	}

	public int getRateAgencyNameLikeZipLikeZC() {
		 return this.rateAgencyNameLikeZipLikeZC;
	}
	
	public void setRateAgencyNameLikeZipLikeZC(int rateAgencyNameLikeZipLikeZC) {
		 this.rateAgencyNameLikeZipLikeZC = rateAgencyNameLikeZipLikeZC;
	}
	
	public int getRateAgencyNameLikeCityLikeZC() {
		 return this.rateAgencyNameLikeCityLikeZC;
	}
	
	public void setRateAgencyNameLikeCityLikeZC(int rateAgencyNameLikeCityLikeZC) {
		 this.rateAgencyNameLikeCityLikeZC = rateAgencyNameLikeCityLikeZC;
	}
	
	
	public int getRateAgencyNameCountryLikeCityLikeZipLikeZC() {
		 return this.rateAgencyNameCountryLikeCityLikeZipLikeZC;
	}
	
	public void setRateAgencyNameCountryLikeCityLikeZipLikeZC(int rate) {
		 this.rateAgencyNameCountryLikeCityLikeZipLikeZC = rate;
	}
	
	public int getRateAgencyNameLikeCityLikeZipLike() {
		 return this.rateAgencyNameLikeCityLikeZipLike;
	}
	
	public void setRateAgencyNameLikeCityLikeZipLike(int rateAgencyNameLikeCityLikeZipLike) {
		 this.rateAgencyNameLikeCityLikeZipLike = rateAgencyNameLikeCityLikeZipLike;
	}
	
	public int getRateAgencyNameLikeCityLikeZipLikeZC() {
		 return this.rateAgencyNameLikeCityLikeZipLikeZC;
	}
	
	public void setRateAgencyNameLikeCityLikeZipLikeZC(int rateAgencyNameLikeCityLikeZipLikeZC) {
		 this.rateAgencyNameLikeCityLikeZipLikeZC = rateAgencyNameLikeCityLikeZipLikeZC;
	}
}
