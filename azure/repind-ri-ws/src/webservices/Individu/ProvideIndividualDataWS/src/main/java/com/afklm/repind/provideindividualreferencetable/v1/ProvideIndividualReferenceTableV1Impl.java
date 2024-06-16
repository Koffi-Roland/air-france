package com.afklm.repind.provideindividualreferencetable.v1;

import com.afklm.repind.provideindividualreferencetable.v1.helpers.BusinessExceptionHelper;
import com.afklm.repind.provideindividualreferencetable.v1.transformers.ProvideIndividualReferenceTableTransformV1;
import com.afklm.repind.provideindividualreferencetable.v1.type.TableNameEnum;
import com.afklm.soa.stubs.w001588.v1.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w001588.v1.ProvideIndividualReferenceTableServiceV1;
import com.afklm.soa.stubs.w001588.v1.data.ProvideIndividualReferenceTableRequest;
import com.afklm.soa.stubs.w001588.v1.data.ProvideIndividualReferenceTableResponse;
import com.afklm.soa.stubs.w001588.v1.response.BusinessErrorCodeEnum;
import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.MissingParameterException;
import com.airfrance.ref.exception.NotFoundException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dto.reference.RefComPrefCountryMarketDTO;
import com.airfrance.repind.dto.reference.RefComPrefDTO;
import com.airfrance.repind.dto.reference.RefGenericCodeLabelsTypeDTO;
import com.airfrance.repind.service.reference.internal.RefLanguageDS;
import com.airfrance.repind.service.reference.internal.RefPaysDS;
import com.airfrance.repind.service.reference.internal.ReferenceDS;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jws.WebService;
import java.util.List;




/**
 * <p>Title : ProvideIndividualDataImpl.java</p>
 * 
 * <p>Copyright : Copyright (c) 2015</p>
 * <p>Company : AIRFRANCE</p>
 * 
 * @author T412211
 */
@WebService(endpointInterface="com.afklm.soa.stubs.w001588.v1.ProvideIndividualReferenceTableServiceV1", targetNamespace = "http://www.af-klm.com/services/passenger/ProvideIndividualReferenceTableService-v1/wsdl", serviceName = "ProvideIndividualReferenceTableServiceService-v1", portName = "ProvideIndividualReferenceTableService-v1-soap11http")
@Slf4j
public class ProvideIndividualReferenceTableV1Impl implements ProvideIndividualReferenceTableServiceV1{

	@Autowired
	protected BusinessExceptionHelper businessExceptionHelperV1;
	
	@Autowired
	protected ReferenceDS referenceDS;
	
	@Autowired
	protected RefPaysDS refPaysDS;
	
	@Autowired
	protected RefLanguageDS refLanguageDS;
		
	public ProvideIndividualReferenceTableResponse provideIndividualReferenceTable(ProvideIndividualReferenceTableRequest request) throws BusinessErrorBlocBusinessException {
		
		ProvideIndividualReferenceTableResponse response = null;

		try {

			checkInputs(request);
			
			if(TableNameEnum.REF_COMPREF.toString().equals(request.getTableName())){
				List<RefComPrefDTO> listeRefComprefDTO=referenceDS.provideComPref(request.getDomain(), request.getLanguage(), request.getMarket());
				response=ProvideIndividualReferenceTableTransformV1.transformRefComPrefToProvideIndividualReferenceTable(listeRefComprefDTO);
			}
			else if(TableNameEnum.REF_COMPREF_DOMAIN.toString().equals(request.getTableName())){
				// List<RefComPrefDomainDTO> listeRefComprefDomainDTO=referenceDS.provideComPrefDomain();
				// response=ProvideIndividualReferenceTableTransformV1.transformRefComPrefDomainToProvideIndividualReferenceTable(listeRefComprefDomainDTO);
				// Fill Generic structure
				List<RefGenericCodeLabelsTypeDTO> listeRefGenericComprefDomainDTO=referenceDS.provideGenericComPrefDomain();
				response=ProvideIndividualReferenceTableTransformV1.transformRefComPrefTypeToProvideIndividualReferenceTable(listeRefGenericComprefDomainDTO);				
			}
			else if(TableNameEnum.REF_COMPREF_TYPE.toString().equals(request.getTableName())){
				// List<RefComPrefTypeDTO> listeRefComprefTypeDTO=referenceDS.provideComPrefType();
				// response=ProvideIndividualReferenceTableTransformV1.transformRefComPrefTypeToProvideIndividualReferenceTable(listeRefComprefTypeDTO);
				// Fill Generic structure
				List<RefGenericCodeLabelsTypeDTO> listeRefGenericComprefTypeDTO=referenceDS.provideGenericComPrefType();
				response=ProvideIndividualReferenceTableTransformV1.transformRefComPrefTypeToProvideIndividualReferenceTable(listeRefGenericComprefTypeDTO);
			}
			else if(TableNameEnum.REF_COMPREF_GTYPE.toString().equals(request.getTableName())){
				// List<RefComPrefGTypeDTO> listeRefComprefGTypeDTO=referenceDS.provideComPrefGType();
				// response=ProvideIndividualReferenceTableTransformV1.transformRefComPrefGTypeToProvideIndividualReferenceTable(listeRefComprefGTypeDTO);
				// Fill Generic structure
				List<RefGenericCodeLabelsTypeDTO> listeRefGenericComprefGTypeDTO=referenceDS.provideGenericComPrefGType();
				response=ProvideIndividualReferenceTableTransformV1.transformRefComPrefTypeToProvideIndividualReferenceTable(listeRefGenericComprefGTypeDTO);
			}
			else if(TableNameEnum.REF_COMPREF_MEDIA.toString().equals(request.getTableName())){
				// List<RefComPrefMediaDTO> listeRefComprefMediaDTO=referenceDS.provideComPrefMedia();
				// response=ProvideIndividualReferenceTableTransformV1.transformRefComPrefMediaToProvideIndividualReferenceTable(listeRefComprefMediaDTO);
				// Fill Generic structure
				List<RefGenericCodeLabelsTypeDTO> listeRefGenericComprefMediaDTO=referenceDS.provideGenericComPrefMedia();
				response=ProvideIndividualReferenceTableTransformV1.transformRefComPrefTypeToProvideIndividualReferenceTable(listeRefGenericComprefMediaDTO);
			}
			else if(TableNameEnum.REF_COMPREF_COUNTRY_MARKET.toString().equals(request.getTableName())){
				List<RefComPrefCountryMarketDTO> listeRefComprefDomainDTO=referenceDS.provideComPrefCountryMarket(request.getCountry());
				response=ProvideIndividualReferenceTableTransformV1.transformRefComPrefCountryMarketToProvideIndividualReferenceTable(listeRefComprefDomainDTO);
			}
			else if(TableNameEnum.REF_SEXE.toString().equals(request.getTableName())){
				List<RefGenericCodeLabelsTypeDTO> listeRefGenericComprefDomainDTO=referenceDS.provideGenericSimpleQuery("REF_SEXE");
				response=ProvideIndividualReferenceTableTransformV1.transformRefComPrefTypeToProvideIndividualReferenceTable(listeRefGenericComprefDomainDTO);				
			}
			else if(TableNameEnum.REF_CIVILITE.toString().equals(request.getTableName())){
				List<RefGenericCodeLabelsTypeDTO> listeRefGenericComprefDomainDTO=referenceDS.provideGenericSimpleQuery("REF_CIVILITE");
				response=ProvideIndividualReferenceTableTransformV1.transformRefComPrefTypeToProvideIndividualReferenceTable(listeRefGenericComprefDomainDTO);				
			}
			else if(TableNameEnum.REF_CODE_TITRE.toString().equals(request.getTableName())){
				List<RefGenericCodeLabelsTypeDTO> listeRefGenericComprefDomainDTO=referenceDS.provideGenericSimpleQuery("REF_CODE_TITRE");
				response=ProvideIndividualReferenceTableTransformV1.transformRefComPrefTypeToProvideIndividualReferenceTable(listeRefGenericComprefDomainDTO);				
			}
			else if(TableNameEnum.REF_STATUT_INDIVIDU.toString().equals(request.getTableName())){
				List<RefGenericCodeLabelsTypeDTO> listeRefGenericComprefDomainDTO=referenceDS.provideGenericSimpleQuery("REF_STATUT_INDIVIDU");
				response=ProvideIndividualReferenceTableTransformV1.transformRefComPrefTypeToProvideIndividualReferenceTable(listeRefGenericComprefDomainDTO);				
			}
			else if(TableNameEnum.REF_PERMISSIONS_QUESTION.toString().equals(request.getTableName())){
				List<RefGenericCodeLabelsTypeDTO> listeRefGenericComprefDomainDTO=referenceDS.provideGenericPermissionsQuestion();
				response=ProvideIndividualReferenceTableTransformV1.transformRefComPrefTypeToProvideIndividualReferenceTable(listeRefGenericComprefDomainDTO);				
			}
			else if(TableNameEnum.PAYS.toString().equals(request.getTableName())){
				List<RefGenericCodeLabelsTypeDTO> listeRefGenericComprefDomainDTO=referenceDS.provideGenericSimpleQuery("PAYS");
				response=ProvideIndividualReferenceTableTransformV1.transformRefComPrefTypeToProvideIndividualReferenceTable(listeRefGenericComprefDomainDTO);				
			}
			else if(TableNameEnum.LANGUES.toString().equals(request.getTableName())){
				List<RefGenericCodeLabelsTypeDTO> listeRefGenericComprefDomainDTO=referenceDS.provideGenericSimpleQuery("LANGUES");
				response=ProvideIndividualReferenceTableTransformV1.transformRefComPrefTypeToProvideIndividualReferenceTable(listeRefGenericComprefDomainDTO);				
			}
			// REPIND-1743 : Add Ultimate preference BDM and PickList			
			else if(TableNameEnum.REF_BDM_COUNTRIES.toString().equals(request.getTableName())){
				List<RefGenericCodeLabelsTypeDTO> listeRefGenericComprefDomainDTO=referenceDS.provideGenericSimpleQuery("REF_BDM_COUNTRIES");
				response=ProvideIndividualReferenceTableTransformV1.transformRefComPrefTypeToProvideIndividualReferenceTable(listeRefGenericComprefDomainDTO);				
			}
			else if(TableNameEnum.REF_BDM_LANGUAGES.toString().equals(request.getTableName())){
				List<RefGenericCodeLabelsTypeDTO> listeRefGenericComprefDomainDTO=referenceDS.provideGenericSimpleQuery("REF_BDM_LANGUAGES");
				response=ProvideIndividualReferenceTableTransformV1.transformRefComPrefTypeToProvideIndividualReferenceTable(listeRefGenericComprefDomainDTO);				
			}
			else if(TableNameEnum.REF_BDM_MEALS.toString().equals(request.getTableName())){
				List<RefGenericCodeLabelsTypeDTO> listeRefGenericComprefDomainDTO=referenceDS.provideGenericSimpleQuery("REF_BDM_MEALS");
				response=ProvideIndividualReferenceTableTransformV1.transformRefComPrefTypeToProvideIndividualReferenceTable(listeRefGenericComprefDomainDTO);				
			}
			else if(TableNameEnum.REF_BDM_SEATS.toString().equals(request.getTableName())){
				List<RefGenericCodeLabelsTypeDTO> listeRefGenericComprefDomainDTO=referenceDS.provideGenericSimpleQuery("REF_BDM_SEATS");
				response=ProvideIndividualReferenceTableTransformV1.transformRefComPrefTypeToProvideIndividualReferenceTable(listeRefGenericComprefDomainDTO);				
			}
			else if(TableNameEnum.REF_CHANNEL_CHECKIN.toString().equals(request.getTableName())){
				List<RefGenericCodeLabelsTypeDTO> listeRefGenericComprefDomainDTO=referenceDS.provideGenericSimpleQuery("REF_CHANNEL_CHECKIN");
				response=ProvideIndividualReferenceTableTransformV1.transformRefComPrefTypeToProvideIndividualReferenceTable(listeRefGenericComprefDomainDTO);				
			}
			else if(TableNameEnum.REF_CHANNEL_COMMUNICATION.toString().equals(request.getTableName())){
				List<RefGenericCodeLabelsTypeDTO> listeRefGenericComprefDomainDTO=referenceDS.provideGenericSimpleQuery("REF_CHANNEL_COMMUNICATION");
				response=ProvideIndividualReferenceTableTransformV1.transformRefComPrefTypeToProvideIndividualReferenceTable(listeRefGenericComprefDomainDTO);				
			}
			else if(TableNameEnum.REF_COMFORT.toString().equals(request.getTableName())){
				List<RefGenericCodeLabelsTypeDTO> listeRefGenericComprefDomainDTO=referenceDS.provideGenericSimpleQuery("REF_COMFORT");
				response=ProvideIndividualReferenceTableTransformV1.transformRefComPrefTypeToProvideIndividualReferenceTable(listeRefGenericComprefDomainDTO);				
			}
			else if(TableNameEnum.REF_COMFORT_PAID.toString().equals(request.getTableName())){
				List<RefGenericCodeLabelsTypeDTO> listeRefGenericComprefDomainDTO=referenceDS.provideGenericSimpleQuery("REF_COMFORT_PAID");
				response=ProvideIndividualReferenceTableTransformV1.transformRefComPrefTypeToProvideIndividualReferenceTable(listeRefGenericComprefDomainDTO);				
			}
			else if(TableNameEnum.REF_CULTURAL.toString().equals(request.getTableName())){
				List<RefGenericCodeLabelsTypeDTO> listeRefGenericComprefDomainDTO=referenceDS.provideGenericSimpleQuery("REF_CULTURAL");
				response=ProvideIndividualReferenceTableTransformV1.transformRefComPrefTypeToProvideIndividualReferenceTable(listeRefGenericComprefDomainDTO);				
			}
			else if(TableNameEnum.REF_DRINK_BEVERAGES.toString().equals(request.getTableName())){
				List<RefGenericCodeLabelsTypeDTO> listeRefGenericComprefDomainDTO=referenceDS.provideGenericSimpleQuery("REF_DRINK_BEVERAGES");
				response=ProvideIndividualReferenceTableTransformV1.transformRefComPrefTypeToProvideIndividualReferenceTable(listeRefGenericComprefDomainDTO);				
			}
			else if(TableNameEnum.REF_LP_DRINK_BEVERAGES.toString().equals(request.getTableName())){
				List<RefGenericCodeLabelsTypeDTO> listeRefGenericComprefDomainDTO=referenceDS.provideGenericSimpleQuery("REF_LP_DRINK_BEVERAGES");
				response=ProvideIndividualReferenceTableTransformV1.transformRefComPrefTypeToProvideIndividualReferenceTable(listeRefGenericComprefDomainDTO);
			}
			else if(TableNameEnum.REF_DRINK_WELCOME.toString().equals(request.getTableName())){
				List<RefGenericCodeLabelsTypeDTO> listeRefGenericComprefDomainDTO=referenceDS.provideGenericSimpleQuery("REF_DRINK_WELCOME");
				response=ProvideIndividualReferenceTableTransformV1.transformRefComPrefTypeToProvideIndividualReferenceTable(listeRefGenericComprefDomainDTO);				
			}
			else if(TableNameEnum.REF_MEALS.toString().equals(request.getTableName())){
				List<RefGenericCodeLabelsTypeDTO> listeRefGenericComprefDomainDTO=referenceDS.provideGenericSimpleQuery("REF_MEALS");
				response=ProvideIndividualReferenceTableTransformV1.transformRefComPrefTypeToProvideIndividualReferenceTable(listeRefGenericComprefDomainDTO);				
			}
			else if(TableNameEnum.REF_LP_MEALS.toString().equals(request.getTableName())){
				List<RefGenericCodeLabelsTypeDTO> listeRefGenericComprefDomainDTO=referenceDS.provideGenericSimpleQuery("REF_LP_MEALS");
				response=ProvideIndividualReferenceTableTransformV1.transformRefComPrefTypeToProvideIndividualReferenceTable(listeRefGenericComprefDomainDTO);
			}
			else if(TableNameEnum.REF_MEALS_PAID.toString().equals(request.getTableName())){
				List<RefGenericCodeLabelsTypeDTO> listeRefGenericComprefDomainDTO=referenceDS.provideGenericSimpleQuery("REF_MEALS_PAID");
				response=ProvideIndividualReferenceTableTransformV1.transformRefComPrefTypeToProvideIndividualReferenceTable(listeRefGenericComprefDomainDTO);				
			}
			else if(TableNameEnum.REF_READING.toString().equals(request.getTableName())){
				List<RefGenericCodeLabelsTypeDTO> listeRefGenericComprefDomainDTO=referenceDS.provideGenericSimpleQuery("REF_READING");
				response=ProvideIndividualReferenceTableTransformV1.transformRefComPrefTypeToProvideIndividualReferenceTable(listeRefGenericComprefDomainDTO);				
			}
			else if(TableNameEnum.REF_SEATS.toString().equals(request.getTableName())){
				List<RefGenericCodeLabelsTypeDTO> listeRefGenericComprefDomainDTO=referenceDS.provideGenericSimpleQuery("REF_SEATS");
				response=ProvideIndividualReferenceTableTransformV1.transformRefComPrefTypeToProvideIndividualReferenceTable(listeRefGenericComprefDomainDTO);				
			}
			else if(TableNameEnum.REF_HANDICAP_TYPE.toString().equals(request.getTableName())){
				List<RefGenericCodeLabelsTypeDTO> listeRefGenericComprefDomainDTO=referenceDS.provideGenericSimpleQuery("REF_HANDICAP_TYPE");
				response=ProvideIndividualReferenceTableTransformV1.transformRefComPrefTypeToProvideIndividualReferenceTable(listeRefGenericComprefDomainDTO);				
			}
			else if(TableNameEnum.REF_HANDICAP_CODE.toString().equals(request.getTableName())){
				List<RefGenericCodeLabelsTypeDTO> listeRefGenericComprefDomainDTO=referenceDS.provideGenericSimpleQuery("REF_HANDICAP_CODE");
				response=ProvideIndividualReferenceTableTransformV1.transformRefComPrefTypeToProvideIndividualReferenceTable(listeRefGenericComprefDomainDTO);				
			}
			else if(TableNameEnum.REF_HANDICAP_DATA_KEY.toString().equals(request.getTableName())){
				List<RefGenericCodeLabelsTypeDTO> listeRefGenericComprefDomainDTO=referenceDS.provideGenericSimpleQuery("REF_HANDICAP_DATA_KEY");
				response=ProvideIndividualReferenceTableTransformV1.transformRefComPrefTypeToProvideIndividualReferenceTable(listeRefGenericComprefDomainDTO);				
			}
			else if(TableNameEnum.REF_ETAT_ROLE_CTR.toString().equals(request.getTableName())){
				List<RefGenericCodeLabelsTypeDTO> listeRefGenericComprefDomainDTO=referenceDS.provideGenericSimpleQuery("REF_ETAT_ROLE_CTR");
				response=ProvideIndividualReferenceTableTransformV1.transformRefComPrefTypeToProvideIndividualReferenceTable(listeRefGenericComprefDomainDTO);				
			}
			else if(TableNameEnum.REF_AUTORIS_MAIL.toString().equals(request.getTableName())){
				List<RefGenericCodeLabelsTypeDTO> listeRefGenericComprefDomainDTO=referenceDS.provideGenericSimpleQuery("REF_AUTORIS_MAIL");
				response=ProvideIndividualReferenceTableTransformV1.transformRefComPrefTypeToProvideIndividualReferenceTable(listeRefGenericComprefDomainDTO);				
			}
			else if(TableNameEnum.REF_NIV_TIER_FB.toString().equals(request.getTableName())){
				List<RefGenericCodeLabelsTypeDTO> listeRefGenericComprefDomainDTO=referenceDS.provideGenericSimpleQuery("REF_NIV_TIER_FB");
				response=ProvideIndividualReferenceTableTransformV1.transformRefComPrefTypeToProvideIndividualReferenceTable(listeRefGenericComprefDomainDTO);				
			}
			else if(TableNameEnum.REF_PREFERENCE_TYPE.toString().equals(request.getTableName())){
				List<RefGenericCodeLabelsTypeDTO> listeRefGenericComprefDomainDTO=referenceDS.provideGenericSimpleQuery("REF_PREFERENCE_TYPE");
				response=ProvideIndividualReferenceTableTransformV1.transformRefComPrefTypeToProvideIndividualReferenceTable(listeRefGenericComprefDomainDTO);				
			}
			else if(TableNameEnum.REF_PREFERENCE_DATA_KEY.toString().equals(request.getTableName())){
				List<RefGenericCodeLabelsTypeDTO> listeRefGenericComprefDomainDTO=referenceDS.provideGenericSimpleQuery("REF_PREFERENCE_DATA_KEY");
				response=ProvideIndividualReferenceTableTransformV1.transformRefComPrefTypeToProvideIndividualReferenceTable(listeRefGenericComprefDomainDTO);				
			}
			else if(TableNameEnum.DOM_PRO.toString().equals(request.getTableName())){
				List<RefGenericCodeLabelsTypeDTO> listeRefGenericComprefDomainDTO=referenceDS.provideGenericSimpleQuery("DOM_PRO");
				response=ProvideIndividualReferenceTableTransformV1.transformRefComPrefTypeToProvideIndividualReferenceTable(listeRefGenericComprefDomainDTO);				
			}
			// REPIND-1811 : Add REF CONSENT table for DWH and CAPI
			else if(TableNameEnum.REF_CONSENT_TYPE.toString().equals(request.getTableName())){
				List<RefGenericCodeLabelsTypeDTO> listeRefGenericComprefDomainDTO=referenceDS.provideGenericSimpleQuery("REF_CONSENT_TYPE");
				response=ProvideIndividualReferenceTableTransformV1.transformRefComPrefTypeToProvideIndividualReferenceTable(listeRefGenericComprefDomainDTO);				
			}
			else if(TableNameEnum.REF_CONSENT_DATA_TYPE.toString().equals(request.getTableName())){
				List<RefGenericCodeLabelsTypeDTO> listeRefGenericComprefDomainDTO=referenceDS.provideGenericSimpleQuery("REF_CONSENT_DATA_TYPE");
				response=ProvideIndividualReferenceTableTransformV1.transformRefComPrefTypeToProvideIndividualReferenceTable(listeRefGenericComprefDomainDTO);				
			}
			else if(TableNameEnum.REF_CONSENT_TYPE_DATA_TYPE.toString().equals(request.getTableName())){
				List<RefGenericCodeLabelsTypeDTO> listeRefGenericComprefDomainDTO=referenceDS.provideGenericSimpleQuery("REF_CONSENT_TYPE_DATA_TYPE");
				response=ProvideIndividualReferenceTableTransformV1.transformRefComPrefTypeToProvideIndividualReferenceTable(listeRefGenericComprefDomainDTO);				
			}
		}
		// ERROR 001 : NOT FOUND
		catch(NotFoundException e) {
			// REPIND-255 : Suppression du log ERROR car c'est un cas normal
			log.info("provideIndividualReferenceTable : {}",e.getMessage());
			businessExceptionHelperV1.throwBusinessException(BusinessErrorCodeEnum.ERROR_001, e.getMessage());
		}
		// ERROR 133 : MISSING PARAMETER
		catch(MissingParameterException e) {
			log.error("provideIndividualReferenceTable ERROR", e);
			businessExceptionHelperV1.throwBusinessException(BusinessErrorCodeEnum.ERROR_133, e.getMessage());
		}
		// ERROR 932 : INVALID PARAMETER
		catch(InvalidParameterException e) {
			log.error("provideIndividualReferenceTable ERROR", e);
			businessExceptionHelperV1.throwBusinessException(BusinessErrorCodeEnum.ERROR_932, e.getMessage());
		}
		// ERROR 905 : TECHNICAL ERROR
		catch (JrafDomainException e) {
			log.error("provideIndividualReferenceTable ERROR", e);
			businessExceptionHelperV1.throwBusinessException(BusinessErrorCodeEnum.ERROR_905, e.getMessage());
		}
		
		//La nullité est déjà testé dans le check, on retourne juste l'info
		// REPIND-1398 : Test SONAR NPE
		if (response != null) {
			response.setTableName(request.getTableName());
		}
		
		return response;
	}

	private void checkInputs(ProvideIndividualReferenceTableRequest request) throws MissingParameterException, InvalidParameterException, JrafDomainException {
		if(StringUtils.isEmpty(request.getTableName())) {
			throw new MissingParameterException("table name is mandatory");
		}
		if(!TableNameEnum.exist(request.getTableName())){
			throw new InvalidParameterException("Table not found");
		}
		
		if(request.getDomain() != null && !referenceDS.existCodeDomain(request.getDomain())){
			throw new InvalidParameterException("Domain not found");
		}
		if(request.getLanguage() != null && !refLanguageDS.isValidLanguageCode(request.getLanguage())){
			throw new InvalidParameterException("Language not found");
		}
		if(request.getCountry() != null && !refPaysDS.codePaysIsValide(request.getCountry())){
			throw new InvalidParameterException("Country not found");
		}
		if(request.getMarket() != null){
			if(!request.getMarket().matches("[a-zA-Z]*") || request.getMarket().length()>3)
			throw new InvalidParameterException("Market not found");
		}
		
	}

}
