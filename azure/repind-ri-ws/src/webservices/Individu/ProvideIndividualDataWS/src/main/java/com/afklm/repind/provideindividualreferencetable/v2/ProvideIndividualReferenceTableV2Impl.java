package com.afklm.repind.provideindividualreferencetable.v2;

import com.afklm.repind.provideindividualreferencetable.v2.helpers.BusinessExceptionHelper;
import com.afklm.repind.provideindividualreferencetable.v2.transformers.ProvideIndividualReferenceTableTransformV2;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.w001588.v2.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w001588.v2.ProvideIndividualReferenceTableServiceV2;
import com.afklm.soa.stubs.w001588.v2.data.ProvideIndividualReferenceTableRequest;
import com.afklm.soa.stubs.w001588.v2.data.ProvideIndividualReferenceTableResponse;
import com.afklm.soa.stubs.w001588.v2.response.BusinessErrorCodeEnum;
import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.MissingParameterException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.type.TableReferenceEnum;
import com.airfrance.repind.dto.reference.*;
import com.airfrance.repind.entity.reference.Pays;
import com.airfrance.repind.entity.reference.RefPreferenceKeyType;
import com.airfrance.repind.service.reference.internal.*;
import com.airfrance.repind.util.service.PaginationResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.WebService;


@Service("passenger_ProvideIndividualReferenceTable-v02Bean")
@WebService(endpointInterface="com.afklm.soa.stubs.w001588.v2.ProvideIndividualReferenceTableServiceV2", targetNamespace = "http://www.af-klm.com/services/passenger/ProvideIndividualReferenceTableService-v2/wsdl", serviceName = "ProvideIndividualReferenceTableServiceService-v2", portName = "ProvideIndividualReferenceTableService-v2-soap11http")
@Slf4j
public class ProvideIndividualReferenceTableV2Impl implements ProvideIndividualReferenceTableServiceV2 {

	@Autowired
	private BusinessExceptionHelper businessExceptionHelperV2;
	
	@Autowired
	private ReferenceDS referenceDS;
	
	@Autowired
	private RefPaysDS refPaysDS;

	@Autowired
	private RefPreferenceKeyTypeDS refPreferenceKeyTypeDS;
	
	@Autowired
	private RefLanguageDS refLanguageDS;
	
	@Autowired
	private RefPermissionsQuestionDS refPermissionsQuestionDS;
	
	@Autowired
	private RefPermissionsDS refPermissionsDS;
	
	@Autowired
	private RefComPrefDomainDS refComPrefDomainDS;
	
	@Autowired
	private RefComPrefTypeDS refComPrefTypeDS;
	
	@Autowired
	private RefComPrefGTypeDS refComPrefGTypeDS;
	
	@Autowired
	private RefComPrefMediaDS refComPrefMediaDS;

	@Autowired
	private RefComPrefCountryMarketDS refComPrefCountryMarketDS;
	
	@Autowired
	private RefHandicapTypeDS refHandicapTypeDS;
	
	@Autowired
	private RefHandicapCodeDS refHandicapCodeDS;
	
	@Autowired
	private RefHandicapDataKeyDS refHandicapDataKeyDS;
		
	@Override
	public ProvideIndividualReferenceTableResponse provideIndividualReferenceTable(ProvideIndividualReferenceTableRequest request) throws BusinessErrorBlocBusinessException, SystemException {
		
		ProvideIndividualReferenceTableResponse response = null;

		try {
			checkInputs(request);

			int index = 1;
			int maxResults = 0;
			
			if (request.getPagination() != null) {
				index = request.getPagination().getIndex();
				maxResults = request.getPagination().getMaxResults();
			}
			
			if (TableReferenceEnum.REF_COMPREF.toString().equals(request.getTableName())) {
				PaginationResult<RefComPrefDTO> result = referenceDS.provideRefComPrefWithPagination(request.getDomain(), request.getLanguage(), request.getMarket(), index, maxResults);
				response = ProvideIndividualReferenceTableTransformV2.transformRefComPrefToProvideIndividualReferenceTable(result, TableReferenceEnum.REF_COMPREF);
			}
			else if (TableReferenceEnum.REF_COMPREF_DOMAIN.toString().equals(request.getTableName())) {
				PaginationResult<RefComPrefDomainDTO> result = refComPrefDomainDS.provideComPrefDomainWithPagination(index, maxResults);
				response = ProvideIndividualReferenceTableTransformV2.transformRefComPrefDomainToProvideIndividualReferenceTable(result, TableReferenceEnum.REF_COMPREF_DOMAIN);
			}
			else if (TableReferenceEnum.REF_COMPREF_TYPE.toString().equals(request.getTableName())) {
				PaginationResult<RefComPrefTypeDTO> result = refComPrefTypeDS.provideComPrefTypeWithPagination(index, maxResults);
				response = ProvideIndividualReferenceTableTransformV2.transformRefComPrefTypeToProvideIndividualReferenceTable(result, TableReferenceEnum.REF_COMPREF_TYPE);
			}
			else if (TableReferenceEnum.REF_COMPREF_GTYPE.toString().equals(request.getTableName())) {
				PaginationResult<RefComPrefGTypeDTO> result = refComPrefGTypeDS.provideComPrefGTypeWithPagination(index, maxResults);
				response = ProvideIndividualReferenceTableTransformV2.transformRefComPrefGTypeToProvideIndividualReferenceTable(result, TableReferenceEnum.REF_COMPREF_GTYPE);
			}
			else if (TableReferenceEnum.REF_COMPREF_MEDIA.toString().equals(request.getTableName())) {
				PaginationResult<RefComPrefMediaDTO> result = refComPrefMediaDS.provideComPrefMediaWithPagination(index, maxResults);
				response = ProvideIndividualReferenceTableTransformV2.transformRefComPrefMediaToProvideIndividualReferenceTable(result, TableReferenceEnum.REF_COMPREF_MEDIA);
			}
			else if (TableReferenceEnum.REF_COMPREF_COUNTRY_MARKET.toString().equals(request.getTableName())) {
				PaginationResult<RefComPrefCountryMarketDTO> result = refComPrefCountryMarketDS.provideRefComPrefCountryMarketWithPagination(request.getCountry(), index, maxResults);
				response = ProvideIndividualReferenceTableTransformV2.transformRefComPrefCountryMarketToProvideIndividualReferenceTable(result, TableReferenceEnum.REF_COMPREF_COUNTRY_MARKET);
			} 
			else if (TableReferenceEnum.REF_PERMISSIONS_QUESTION.toString().equals(request.getTableName())) {
				PaginationResult<RefPermissionsQuestionDTO> result = refPermissionsQuestionDS.providePermissionsQuestionWithPagination(index, maxResults);
				response = ProvideIndividualReferenceTableTransformV2.transformRefPermissionsQuestionToProvideIndividualReferenceTable(result, TableReferenceEnum.REF_PERMISSIONS_QUESTION);
			}
			else if (TableReferenceEnum.REF_PERMISSIONS.toString().equals(request.getTableName())) {
				PaginationResult<RefPermissionsDTO> result = refPermissionsDS.providePermissionsWithPagination(index, maxResults);
				response = ProvideIndividualReferenceTableTransformV2.transformRefPermissionsToProvideIndividualReferenceTable(result, TableReferenceEnum.REF_PERMISSIONS);
			}
			else if (TableReferenceEnum.PAYS.toString().equals(request.getTableName())) {
				PaginationResult<Pays> result = refPaysDS.providePaysWithPagination(index, maxResults);
				response = ProvideIndividualReferenceTableTransformV2.transformPaysToProvideIndividualReferenceTable(result, TableReferenceEnum.PAYS);
			}
			else if (TableReferenceEnum.REF_PREFERENCE_KEY_TYPE.toString().equals(request.getTableName())) {
				PaginationResult<RefPreferenceKeyType> result = refPreferenceKeyTypeDS.provideRefpreferenceKeyTypeWithPagination(index, maxResults);
				response = ProvideIndividualReferenceTableTransformV2.transformRefPreferenceKeyTypeToProvideIndividualReferenceTable(result, TableReferenceEnum.REF_PREFERENCE_KEY_TYPE);
			}
			else if (TableReferenceEnum.REF_HANDICAP_TYPE.toString().equals(request.getTableName())) {
				PaginationResult<RefHandicapTypeDTO> result = refHandicapTypeDS.provideHandicapTypeWithPagination(index, maxResults);
				response = ProvideIndividualReferenceTableTransformV2.transformRefHandicapTypeToProvideIndividualReferenceTable(result, TableReferenceEnum.REF_HANDICAP_TYPE);
			}
			else if (TableReferenceEnum.REF_HANDICAP_CODE.toString().equals(request.getTableName())) {
				PaginationResult<RefHandicapCodeDTO> result = refHandicapCodeDS.provideHandicapCodeWithPagination(index, maxResults);
				response = ProvideIndividualReferenceTableTransformV2.transformRefHandicapCodeToProvideIndividualReferenceTable(result, TableReferenceEnum.REF_HANDICAP_CODE);
			}
			else if (TableReferenceEnum.REF_HANDICAP_DATA_KEY.toString().equals(request.getTableName())) {
				PaginationResult<RefHandicapDataKeyDTO> result = refHandicapDataKeyDS.provideHandicapDataKeyWithPagination(index, maxResults);
				response = ProvideIndividualReferenceTableTransformV2.transformRefHandicapDataKeyToProvideIndividualReferenceTable(result, TableReferenceEnum.REF_HANDICAP_DATA_KEY);
			}
			else if (TableReferenceEnum.REF_LP_MEALS.toString().equals(request.getTableName())) {
				PaginationResult<RefHandicapDataKeyDTO> result = refHandicapDataKeyDS.provideHandicapDataKeyWithPagination(index, maxResults);
				response = ProvideIndividualReferenceTableTransformV2.transformRefHandicapDataKeyToProvideIndividualReferenceTable(result, TableReferenceEnum.REF_LP_MEALS);
			}
			else if (TableReferenceEnum.REF_LP_DRINK_BEVERAGES.toString().equals(request.getTableName())) {
				PaginationResult<RefHandicapDataKeyDTO> result = refHandicapDataKeyDS.provideHandicapDataKeyWithPagination(index, maxResults);
				response = ProvideIndividualReferenceTableTransformV2.transformRefHandicapDataKeyToProvideIndividualReferenceTable(result, TableReferenceEnum.REF_LP_DRINK_BEVERAGES);
			}
		}
		// ERROR 133 : MISSING PARAMETER
		catch(MissingParameterException e) {
			log.error("provideIndividualReferenceTable ERROR", e);
			businessExceptionHelperV2.throwBusinessException(BusinessErrorCodeEnum.ERROR_133, e.getMessage());
		}
		// ERROR 932 : INVALID PARAMETER
		catch(InvalidParameterException e) {
			log.error("provideIndividualReferenceTable ERROR", e);
			businessExceptionHelperV2.throwBusinessException(BusinessErrorCodeEnum.ERROR_932, e.getMessage());
		}
		// ERROR 905 : TECHNICAL ERROR
		catch (JrafDomainException e) {
			log.error("provideIndividualReferenceTable ERROR", e);
			businessExceptionHelperV2.throwBusinessException(BusinessErrorCodeEnum.ERROR_905, e.getMessage());
		}
				
		return response;
	}

	private void checkInputs(ProvideIndividualReferenceTableRequest request) throws MissingParameterException, InvalidParameterException, JrafDomainException {
		if (StringUtils.isEmpty(request.getTableName())) {
			throw new MissingParameterException("Table name is mandatory");
		}
		
		if (!TableReferenceEnum.isExist(request.getTableName())){
			throw new InvalidParameterException("Table not found");
		}
		
		if (request.getDomain() != null && !referenceDS.existCodeDomain(request.getDomain())) {
			throw new InvalidParameterException("Domain not found");
		}
		
		if (request.getLanguage() != null && !refLanguageDS.isValidLanguageCode(request.getLanguage())) {
			throw new InvalidParameterException("Language not found");
		}
		
		if (request.getCountry() != null && !refPaysDS.codePaysIsValide(request.getCountry())) {
			throw new InvalidParameterException("Country not found");
		}
		
		if (request.getMarket() != null) {
			if (!request.getMarket().matches("[a-zA-Z]*") || request.getMarket().length()>3) {
				throw new InvalidParameterException("Market not found");
			}
		}
	}
}
