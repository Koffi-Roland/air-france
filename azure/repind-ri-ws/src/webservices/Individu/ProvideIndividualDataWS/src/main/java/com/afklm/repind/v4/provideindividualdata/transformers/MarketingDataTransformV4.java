package com.afklm.repind.v4.provideindividualdata.transformers;

import com.afklm.soa.stubs.w000410.v2.common.*;
import com.afklm.soa.stubs.w000418.v4.data.ProvideIndividualInformationResponse;
import com.afklm.soa.stubs.w000418.v4.response.MarketingDataResponse;
import com.afklm.soa.stubs.w000418.v4.sicmarketingtype.*;

import java.util.List;





/**
 * Class utility to transform a BDM MarketingDataV2 objects into SIC MarketingInformationV2 objects
 * @author 3061561
 */
public class MarketingDataTransformV4 {

	/**
	 * Transform a BDM MarketingDataV2 object into a SIC MarketingInformationV2 object
	 * @param MarketingDataV2
	 * @return MarketingInformationV2
	 */
	public static void bdm2sic(MarketingDataV2 bdm, ProvideIndividualInformationResponse response) {
		
		if (response.getMarketingDataResponse() == null) {
			MarketingDataResponse marketingDataResponse = new MarketingDataResponse();
			response.setMarketingDataResponse(marketingDataResponse);
			if (response.getMarketingDataResponse().getMarketingInformation() == null) {
				MarketingInformation marketingInformation = new MarketingInformation();
				response.getMarketingDataResponse().setMarketingInformation(marketingInformation);
			}
		}
		
		if (response.getMarketingDataResponse().getMarketingInformation().getHandicap() == null) {
			response.getMarketingDataResponse().getMarketingInformation().setHandicap(transformToHandicap(bdm.getHandicap()));
		}
		if (response.getMarketingDataResponse().getMarketingInformation().getPersonalInformation() == null) {
			response.getMarketingDataResponse().getMarketingInformation().setPersonalInformation(transformToMaccPersonalInformation(bdm.getPersonalInformation()));
		}
		// REPIND-918 : desactivate call to BDM for TravelDoc
		/*
		if (response.getMarketingDataResponse().getMarketingInformation().getApisData() == null) {
			response.getMarketingDataResponse().getMarketingInformation().setApisData(transformToMaccApisData(bdm.getApisDataV2()));
		}
		if (response.getMarketingDataResponse().getMarketingInformation().getTravelDocument() != null && response.getMarketingDataResponse().getMarketingInformation().getTravelDocument().size() == 0) {
			transformToMaccTravelDocument(bdm.getTravelDocumentList(), response.getMarketingDataResponse().getMarketingInformation().getTravelDocument());
		}
		*/
		if (response.getMarketingDataResponse().getMarketingInformation().getTravelPreferences() == null) {
			response.getMarketingDataResponse().getMarketingInformation().setTravelPreferences(transformToMaccTravelPreferences(bdm.getTravelPreferences()));
		}
		if (response.getMarketingDataResponse().getMarketingInformation().getTutorUM() != null && response.getMarketingDataResponse().getMarketingInformation().getTutorUM().size() == 0) {
			transformToMaccTutorUM(bdm.getTutorUMList(), response.getMarketingDataResponse().getMarketingInformation().getTutorUM());
		}
		if (response.getMarketingDataResponse().getMarketingInformation().getTravelCompanion() != null && response.getMarketingDataResponse().getMarketingInformation().getTravelCompanion().size() == 0) {
			transformToMaccTravelCompanion(bdm.getTravelCompanionV2List(), response.getMarketingDataResponse().getMarketingInformation().getTravelCompanion());
		}
		if (response.getMarketingDataResponse().getMarketingInformation().getEmergencyContact() != null && response.getMarketingDataResponse().getMarketingInformation().getEmergencyContact().size() == 0) {
			transformMaccEmergencyContacts(bdm.getEmergencyContactList(), response.getMarketingDataResponse().getMarketingInformation().getEmergencyContact());
		}
	}
	
	/**
	 * Transform a BDM Handicap object into a SIC MaccHandicap object
	 * @param Handicap
	 * @return MaccHandicap
	 */
	public static MaccHandicap transformToHandicap(Handicap bdm) {
		
		if(bdm==null) {
			return null;
		}
		
		MaccHandicap sic = new MaccHandicap();
		
		sic.setCodeHCP1(bdm.getCodeHCP1());
		sic.setCodeHCP2(bdm.getCodeHCP2());
		sic.setCodeHCP3(bdm.getCodeHCP3());
		sic.setMedaCCFlag(bdm.getMedaCCFlag());
		sic.setMedaCCDateStart(bdm.getMedaCCDateStart());
		sic.setMedaCCDateEnd(bdm.getMedaCCDateEnd());
		sic.setMedaCCAccomp(bdm.getMedaCCAccomp());
		sic.setMedaCCTxt(bdm.getMedaCCTxt());
		sic.setMedaMCFlag(bdm.getMedaMCFlag());
		sic.setMedaMCDateStart(bdm.getMedaMCDateStart());
		sic.setMedaMCDateEnd(bdm.getMedaMCDateEnd());
		sic.setMedaMCAccomp(bdm.getMedaMCAccomp());
		sic.setMedaMCTxt(bdm.getMedaMCTxt());
		sic.setMedaLCFlag(bdm.getMedaLCFlag());
		sic.setMedaLCDateStart(bdm.getMedaLCDateStart());
		sic.setMedaLCDateEnd(bdm.getMedaLCDateEnd());
		sic.setMedaLCAccomp(bdm.getMedaLCAccomp());
		sic.setMedaLCTxt(bdm.getMedaLCTxt());
		sic.setCodeMat1(bdm.getCodeMat1());
		sic.setLength1(bdm.getLength1());
		sic.setWidth1(bdm.getWidth1());
		sic.setHeight1(bdm.getHeight1());
		sic.setLengthPlie1(bdm.getLengthPlie1());
		sic.setWidthPlie1(bdm.getWidthPlie1());
		sic.setHeightPlie1(bdm.getHeightPlie1());
		sic.setWeight1(bdm.getWeight1());
		sic.setCodeMat2(bdm.getCodeMat2());
		sic.setLength2(bdm.getLength2());
		sic.setWidth2(bdm.getWidth2());
		sic.setHeight2(bdm.getHeight2());
		sic.setLengthPlie2(bdm.getLengthPlie2());
		sic.setWidthPlie2(bdm.getWidthPlie2());
		sic.setHeightPlie2(bdm.getHeightPlie2());
		sic.setWeight2(bdm.getWeight2());
		sic.setOtherMat(bdm.getOtherMat());
		sic.setDogGuideFlag(bdm.getDogGuideFlag());
		sic.setDogGuideBreed(bdm.getDogGuideBreed());
		sic.setDogGuideWeight(bdm.getDogGuideWeight());
		sic.setOxygFlag(bdm.getOxygFlag());
		sic.setOxygOutput(bdm.getOxygOutput());
		
		return sic;
	}

	/**
	 * Transform a BDM ApisDataV2 object into a SIC MaccApisDataV2 object
	 * @param ApisDataV2
	 * @return MaccApisDataV2
	 */
	// REPIND-918 : desactivate call to BDM for TravelDoc
	/*
	public static MaccApisData transformToMaccApisData(ApisDataV2 bdm) {
		
		if(bdm==null) {
			return null;
		}
		
		MaccApisData sic = new MaccApisData();
		
		sic.setLastName(bdm.getLastName());
		sic.setFirstName(bdm.getFirstName());
		sic.setDateOfBirth(bdm.getDateOfBirth());
		sic.setGender(bdm.getGender());
		sic.setGreenCardNumber(bdm.getGreenCardNumber());
		sic.setGreenCardExpiryDate(bdm.getGreenCardExpiryDate());
		sic.setCountryOfResidence(bdm.getCountryOfResidence());
		sic.setAddressUS(bdm.getAddressUS());
		sic.setPostCodeUS(bdm.getPostCodeUS());
		sic.setCityUS(bdm.getCityUS());
		sic.setStateUS(bdm.getStateUS());
		sic.setRedressControlNumber(bdm.getRedressControlNumber());
		
		return sic;
	}
	*/

	/**
	 * Transform a BDM TravelPreferences object into a SIC MaccTravelPreferencesV2 object
	 * @param TravelPreferences
	 * @return MaccTravelPreferencesV2
	 */
	public static MaccTravelPreferences transformToMaccTravelPreferences(TravelPreferences bdm) {
		
		if(bdm==null) {
			return null;
		}
		
		MaccTravelPreferences sic = new MaccTravelPreferences();
		
		sic.setDepartureAirport(bdm.getDepartureAirport());
		sic.setArrivalAirport(bdm.getArrivalAirport());
		sic.setMeal(bdm.getMeal());
		sic.setSeat(bdm.getSeat());
		sic.setTravelClass(bdm.getTravelClass());
		sic.setBoardingPass(bdm.getBoardingPass());
		sic.setCustomerLeisure(bdm.getCustomerLeisure());
		
		return sic;
	}

	/**
	 * Transform a BDM PersonalInformationV2 object into a SIC MaccPersonalInformationV2 object
	 * @param PersonalInformationV2
	 * @return MaccPersonalInformationV2
	 */
	public static MaccPersonalInformation transformToMaccPersonalInformation(PersonalInformation bdm) {
		
		if(bdm==null) {
			return null;
		}
		
		MaccPersonalInformation sic = new MaccPersonalInformation();
		
		sic.setBlueBizNumber(bdm.getBlueBizNumber());
		sic.setFFPNumber(bdm.getFFPNumber());
		sic.setFFPProgram(bdm.getFFPProgram());
		
		return sic;
	}
	
	/**
	 * Transform a BDM TutorUM list into a SIC MaccTutorUM list
	 * @param TutorUM
	 * @param MaccTutorUM
	 */
	public static void transformToMaccTutorUM(List<TutorUM> bdm, List<MaccTutorUM> sic) {
		
		if(bdm==null) {
			return;
		}
		
		for(TutorUM tutorUM : bdm) {
			sic.add(transformToMaccTutorUM(tutorUM));
		}
		
	}
	
	/**
	 * Transform a BDM TutorUM object into a SIC MaccTutorUM object
	 * @param MaccTutorUM
	 * @return TutorUM
	 */
	public static MaccTutorUM transformToMaccTutorUM(TutorUM bdm) {
		
		if(bdm==null) {
			return null;
		}
		
		MaccTutorUM sic = new MaccTutorUM();
		
		sic.setCivilityTutor(bdm.getCivilityTutor());
		sic.setLastnameTutor(bdm.getLastnameTutor());
		sic.setFirstnameTutor(bdm.getFirstnameTutor());
		sic.setAddressTutor(bdm.getAddressTutor());
		sic.setPostCodeTutor(bdm.getPostCodeTutor());
		sic.setCityTutor(bdm.getCityTutor());
		sic.setCodeProvinceTutor(bdm.getCodeProvinceTutor());
		sic.setCountryCodeTutor(bdm.getCountryCodeTutor());
		sic.setPhoneNumberTutor(bdm.getPhoneNumberTutor());
		sic.setDialingCodePhoneTutor(bdm.getDialingCodePhoneTutor());
		sic.setMobilePhoneTutor(bdm.getMobilePhoneTutor());
		sic.setDialingCodeMobileTutor(bdm.getDialingCodeMobileTutor());
		sic.setOrderTutor(bdm.getOrderTutor());
		
		return sic;
	}
	
	/**
	 * Transform a BDM TravelCompanionV2 list into a SIC MaccTravelCompanionV2 list
	 * @param MaccTravelCompanionV2
	 * @param TravelCompanionV2
	 */
	public static void transformToMaccTravelCompanion(List<TravelCompanionV2> bdm, List<MaccTravelCompanion> sic) {
		
		if(bdm==null) {
			return;
		}
		
		for(TravelCompanionV2 travelCompanion : bdm) {
			sic.add(transformToMaccTravelCompanion(travelCompanion));
		}
	}
	
	/**
	 * Transform a BDM TravelCompanionV2 object into a SIC MaccTravelCompanionV2 object
	 * @param TravelCompanionV2
	 * @return MaccTravelCompanionV2
	 */
	public static MaccTravelCompanion transformToMaccTravelCompanion(TravelCompanionV2 bdm) {
		
		if(bdm==null) {
			return null;
		}
		
		MaccTravelCompanion sic = new MaccTravelCompanion();
		
		sic.setCivility(bdm.getCivility());
		sic.setFirstName(bdm.getFirstName());
		sic.setLastName(bdm.getLastName());
		sic.setDateOfBirth(bdm.getDateOfBirth());
		sic.setEmail(bdm.getEmail());
		sic.setPersonalInformation(MarketingDataTransformV4.transformToMaccPersonalInformation(bdm.getTCPersonalInformation()));
		
		return sic;
	}
	
	/**
	 * Transform a BDM TravelDocument list into a SIC MaccTravelDocument list
	 * @param TravelDocument
	 * @param MaccTravelDocument
	 */
	// REPIND-918 : desactivate call to BDM for TravelDoc
	/*
	public static void transformToMaccTravelDocument(List<TravelDocument> bdm, List<MaccTravelDocument> sic) {
		
		if(bdm==null) {
			return;
		}
		
		for(TravelDocument travelDocument : bdm) {
			sic.add(transformToMaccTravelDocument(travelDocument));
		}
		
	}
	*/
	
	/**
	 * Transform a BDM TravelDocument object into a SIC MaccTravelDocument object
	 * @param TravelDocument
	 * @return MaccTravelDocument
	 */
	// REPIND-918 : desactivate call to BDM for TravelDoc
	/*
	public static MaccTravelDocument transformToMaccTravelDocument(TravelDocument bdm) {
		
		if(bdm==null) {
			return null;
		}
		
		MaccTravelDocument sic = new MaccTravelDocument();
		
		sic.setType(bdm.getType());
		sic.setNumber(bdm.getNumber());
		sic.setExpirationDate(bdm.getExpirationDate());
		sic.setNationality(bdm.getNationality());
		sic.setIssueDate(bdm.getIssueDate());
		sic.setCountryIssued(bdm.getCountryIssued());
		
		return sic;
	}
	*/
	
	/**
	 * Transform a BDM EmergencyContact list into a SIC MaccEmergencyContacts list
	 * @param MaccEmergencyContacts
	 * @param EmergencyContact
	 */
	public static void transformMaccEmergencyContacts(List<EmergencyContact> bdm, List<MaccEmergencyContacts> sic) {
		
		if(bdm==null) {
			return;
		}
		
		for(EmergencyContact emergencyContacts : bdm) {
			sic.add(transformMaccEmergencyContacts(emergencyContacts));
		}
		
	}
	
	/**
	 * Transform a BDM EmergencyContact object into a SIC MaccEmergencyContacts object
	 * @param EmergencyContact
	 * @return MaccEmergencyContacts
	 */
	public static MaccEmergencyContacts transformMaccEmergencyContacts(EmergencyContact bdm) {
		
		if(bdm==null) {
			return null;
		}
		
		MaccEmergencyContacts sic = new MaccEmergencyContacts();
		
		sic.setEmail(bdm.getEmail());
		sic.setPhoneNumber(bdm.getPhoneNumber());
		sic.setLastName(bdm.getLastName());
		sic.setFirstName(bdm.getFirstName());
		
		return sic;
	}
} 
