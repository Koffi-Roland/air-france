package com.afklm.repind.v8.createorupdateindividualws.transformers;

import com.afklm.soa.stubs.w000413.v2.common.*;
import com.afklm.soa.stubs.w000442.v8.request.PreferenceRequest;
import com.afklm.soa.stubs.w000442.v8.siccommontype.RequestorV2;
import com.afklm.soa.stubs.w000442.v8.sicindividutype.PreferenceDataV2;
import com.afklm.soa.stubs.w000442.v8.sicindividutype.PreferenceV2;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dto.preference.PreferenceDTO;
import com.airfrance.repind.dto.preference.PreferenceDataDTO;
import com.airfrance.repind.util.SicDateUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PreferenceTransform {
	
	public static List<PreferenceDTO> transformToPreferenceDTO(PreferenceRequest ws, RequestorV2 requestor) {
		if (ws == null) {
			return null;
		}
		
		List<PreferenceDTO> dtoList = new ArrayList<>();
		if (ws.getPreference() != null) {
			for (PreferenceV2 pref : ws.getPreference()) {
				PreferenceDTO prefDTO = new PreferenceDTO();
				if (pref.getId() != null) {
					prefDTO.setPreferenceId(Long.valueOf(pref.getId()));
				}
				if (pref.getType() != null) {
					prefDTO.setType(pref.getType());
				}
				if (pref.getLink() != null) {
					prefDTO.setLink(Integer.valueOf(pref.getLink()));
				}
				
				if (pref.getPreferenceDatas() != null && pref.getPreferenceDatas().getPreferenceData() != null && !pref.getPreferenceDatas().getPreferenceData().isEmpty()) {
					Set<PreferenceDataDTO> prefDataDTOList = new HashSet<>();
					for (PreferenceDataV2 prefData : pref.getPreferenceDatas().getPreferenceData()) {
						PreferenceDataDTO prefDataDTO = new PreferenceDataDTO();
						if (prefData.getKey() != null) {
							prefDataDTO.setKey(prefData.getKey());
						}
						if (prefData.getValue() != null) {
							prefDataDTO.setValue(prefData.getValue());
						}
						
						prefDataDTOList.add(prefDataDTO);
					}
					prefDTO.setPreferenceDataDTO(prefDataDTOList);
				}
				dtoList.add(prefDTO);
			}
		}
		return dtoList;
	}

	public static MarketingDataV2 transformToBDM(PreferenceRequest preferenceRequest) throws JrafDomainException {
		if (preferenceRequest != null && preferenceRequest.getPreference() != null && !preferenceRequest.getPreference().isEmpty()) {
			
			MarketingDataV2 marketingData = new MarketingDataV2();
			
			for (PreferenceV2 preference : preferenceRequest.getPreference()) {
				
				// Emergency Contact
				PreferenceTransform.transformPreferenceRequestToEmergencyCtc(preference, marketingData);
				
				// Travel Preferences
				PreferenceTransform.transformPreferenceRequestToTravelPreference(preference, marketingData);
				
				// Handicap
				PreferenceTransform.transformPreferenceRequestToHandicap(preference, marketingData);
				
				// Tutor UM
				PreferenceTransform.transformPreferenceRequestToTutorUm(preference, marketingData);
				
				// Travel Companion
				PreferenceTransform.transformPreferenceRequestToTravelCompanion(preference, marketingData);
				
				// Personal Information
				PreferenceTransform.transformPreferenceRequestToPersonalInfo(preference, marketingData);
			}
			return marketingData;
		} else {
			return null;
		}
	}

	private static void transformPreferenceRequestToEmergencyCtc(PreferenceV2 preference, MarketingDataV2 marketingData) {
		if (preference.getType() != null && "ECC".equalsIgnoreCase(preference.getType())) {
			List<EmergencyContact> emergencyContactList = marketingData.getEmergencyContactList();
			EmergencyContact emergencyContact = new EmergencyContact();

			if (preference.getPreferenceDatas() != null 
				&& preference.getPreferenceDatas().getPreferenceData() != null 
				&& !preference.getPreferenceDatas().getPreferenceData().isEmpty()) {
				
				for (PreferenceDataV2 prefData : preference.getPreferenceDatas().getPreferenceData()) {
					if (prefData.getKey() != null && "email".equalsIgnoreCase(prefData.getKey())) {
						emergencyContact.setEmail(prefData.getValue());
					}
					if (prefData.getKey() != null && "phoneNumber".equalsIgnoreCase(prefData.getKey())) {
						emergencyContact.setPhoneNumber(prefData.getValue());
					}
					if (prefData.getKey() != null && "firstName".equalsIgnoreCase(prefData.getKey())) {
						emergencyContact.setFirstName(prefData.getValue());
					}
					if (prefData.getKey() != null && "lastName".equalsIgnoreCase(prefData.getKey())) {
						emergencyContact.setLastName(prefData.getValue());
					}
				}
			}
			emergencyContactList.add(emergencyContact);
		}
	}

	private static void transformPreferenceRequestToTravelPreference(PreferenceV2 preference, MarketingDataV2 marketingData) {
		if (preference.getType() != null && "TPC".equalsIgnoreCase(preference.getType())) {
			TravelPreferences travelPreference = marketingData.getTravelPreferences();
			if (travelPreference == null) {
				travelPreference = new TravelPreferences();
				marketingData.setTravelPreferences(travelPreference);
			}
			if (preference.getPreferenceDatas() != null 
				&& preference.getPreferenceDatas().getPreferenceData() != null 
				&& !preference.getPreferenceDatas().getPreferenceData().isEmpty()) {
				
				for (PreferenceDataV2 prefData : preference.getPreferenceDatas().getPreferenceData()) {
					if (prefData.getKey() != null && "departureCity".equalsIgnoreCase(prefData.getKey())) {
						travelPreference.setDepartureCity(prefData.getValue());
					}
					if (prefData.getKey() != null && "departureAirport".equalsIgnoreCase(prefData.getKey())) {
						travelPreference.setDepartureAirport(prefData.getValue());
					}
					if (prefData.getKey() != null && "arrivalCity".equalsIgnoreCase(prefData.getKey())) {
						travelPreference.setArrivalCity(prefData.getValue());
					}
					if (prefData.getKey() != null && "arrivalAirport".equalsIgnoreCase(prefData.getKey())) {
						travelPreference.setArrivalAirport(prefData.getValue());
					}
					if (prefData.getKey() != null && "meal".equalsIgnoreCase(prefData.getKey())) {
						travelPreference.setMeal(prefData.getValue());
					}
					if (prefData.getKey() != null && "seat".equalsIgnoreCase(prefData.getKey())) {
						travelPreference.setSeat(prefData.getValue());
					}
					if (prefData.getKey() != null && "travelClass".equalsIgnoreCase(prefData.getKey())) {
						travelPreference.setTravelClass(prefData.getValue());
					}
					if (prefData.getKey() != null && "boardingPass".equalsIgnoreCase(prefData.getKey())) {
						travelPreference.setBoardingPass(prefData.getValue());
					}
					if (prefData.getKey() != null && "customerLeisure".equalsIgnoreCase(prefData.getKey())) {
						travelPreference.setCustomerLeisure(prefData.getValue());
					}
				}
			}
		}
	}

	private static void transformPreferenceRequestToHandicap(PreferenceV2 preference, MarketingDataV2 marketingData) throws JrafDomainException {
		if (preference.getType() != null && "HDC".equalsIgnoreCase(preference.getType())) {
			Handicap handicap = marketingData.getHandicap();
			if (handicap == null) {
				handicap = new Handicap();
				marketingData.setHandicap(handicap);
			}
			if (preference.getPreferenceDatas() != null 
				&& preference.getPreferenceDatas().getPreferenceData() != null 
				&& !preference.getPreferenceDatas().getPreferenceData().isEmpty()) {
				
				for (PreferenceDataV2 prefData : preference.getPreferenceDatas().getPreferenceData()) {
					if (prefData.getKey() != null && "CodeHCP1".equalsIgnoreCase(prefData.getKey())) {
						handicap.setCodeHCP1(prefData.getValue());
					}
					if (prefData.getKey() != null && "CodeHCP2".equalsIgnoreCase(prefData.getKey())) {
						handicap.setCodeHCP2(prefData.getValue());
					}
					if (prefData.getKey() != null && "CodeHCP3".equalsIgnoreCase(prefData.getKey())) {
						handicap.setCodeHCP3(prefData.getValue());
					}
					
					if (prefData.getKey() != null && "MedaCCFlag".equalsIgnoreCase(prefData.getKey())) {
						handicap.setMedaCCFlag(prefData.getValue());
					}
					if (prefData.getKey() != null && "MedaCCDateStart".equalsIgnoreCase(prefData.getKey())) {
						handicap.setMedaCCDateStart(SicDateUtils.convertStringToDateDDMMYYYY(prefData.getValue()));
					}
					if (prefData.getKey() != null && "MedaCCDateEnd".equalsIgnoreCase(prefData.getKey())) {
						handicap.setMedaCCDateEnd(SicDateUtils.convertStringToDateDDMMYYYY(prefData.getValue()));
					}
					if (prefData.getKey() != null && "MedaCCAccomp".equalsIgnoreCase(prefData.getKey())) {
						handicap.setMedaCCAccomp(prefData.getValue());
					}
					if (prefData.getKey() != null && "MedaCCTxt".equalsIgnoreCase(prefData.getKey())) {
						handicap.setMedaCCTxt(prefData.getValue());
					}
					
					if (prefData.getKey() != null && "MedaMCFlag".equalsIgnoreCase(prefData.getKey())) {
						handicap.setMedaMCFlag(prefData.getValue());
					}
					if (prefData.getKey() != null && "MedaMCDateStart".equalsIgnoreCase(prefData.getKey())) {
						handicap.setMedaMCDateStart(SicDateUtils.convertStringToDateDDMMYYYY(prefData.getValue()));
					}
					if (prefData.getKey() != null && "MedaMCDateEnd".equalsIgnoreCase(prefData.getKey())) {
						handicap.setMedaMCDateEnd(SicDateUtils.convertStringToDateDDMMYYYY(prefData.getValue()));
					}
					if (prefData.getKey() != null && "MedaMCAccomp".equalsIgnoreCase(prefData.getKey())) {
						handicap.setMedaMCAccomp(prefData.getValue());
					}
					if (prefData.getKey() != null && "MedaMCTxt".equalsIgnoreCase(prefData.getKey())) {
						handicap.setMedaMCTxt(prefData.getValue());
					}
					
					if (prefData.getKey() != null && "MedaLCFlag".equalsIgnoreCase(prefData.getKey())) {
						handicap.setMedaLCFlag(prefData.getValue());
					}
					if (prefData.getKey() != null && "MedaLCDateStart".equalsIgnoreCase(prefData.getKey())) {
						handicap.setMedaLCDateStart(SicDateUtils.convertStringToDateDDMMYYYY(prefData.getValue()));
					}
					if (prefData.getKey() != null && "MedaLCDateEnd".equalsIgnoreCase(prefData.getKey())) {
						handicap.setMedaLCDateEnd(SicDateUtils.convertStringToDateDDMMYYYY(prefData.getValue()));
					}
					if (prefData.getKey() != null && "MedaLCAccomp".equalsIgnoreCase(prefData.getKey())) {
						handicap.setMedaLCAccomp(prefData.getValue());
					}
					if (prefData.getKey() != null && "MedaLCTxt".equalsIgnoreCase(prefData.getKey())) {
						handicap.setMedaLCTxt(prefData.getValue());
					}
					
					if (prefData.getKey() != null && "CodeMat1".equalsIgnoreCase(prefData.getKey())) {
						handicap.setCodeMat1(prefData.getValue());
					}
					if (prefData.getKey() != null && "Length1".equalsIgnoreCase(prefData.getKey())) {
						handicap.setLength1(Integer.valueOf(prefData.getValue()));
					}
					if (prefData.getKey() != null && "Width1".equalsIgnoreCase(prefData.getKey())) {
						handicap.setWidth1(Integer.valueOf(prefData.getValue()));
					}
					if (prefData.getKey() != null && "Height1".equalsIgnoreCase(prefData.getKey())) {
						handicap.setHeight1(Integer.valueOf(prefData.getValue()));
					}
					if (prefData.getKey() != null && "LengthPlie1".equalsIgnoreCase(prefData.getKey())) {
						handicap.setLengthPlie1(Integer.valueOf(prefData.getValue()));
					}
					if (prefData.getKey() != null && "WidthPlie1".equalsIgnoreCase(prefData.getKey())) {
						handicap.setWidthPlie1(Integer.valueOf(prefData.getValue()));
					}
					if (prefData.getKey() != null && "HeightPlie1".equalsIgnoreCase(prefData.getKey())) {
						handicap.setHeightPlie1(Integer.valueOf(prefData.getValue()));
					}
					if (prefData.getKey() != null && "Weight1".equalsIgnoreCase(prefData.getKey())) {
						handicap.setWeight1(Integer.valueOf(prefData.getValue()));
					}
					
					if (prefData.getKey() != null && "CodeMat2".equalsIgnoreCase(prefData.getKey())) {
						handicap.setCodeMat2(prefData.getValue());
					}
					if (prefData.getKey() != null && "Length2".equalsIgnoreCase(prefData.getKey())) {
						handicap.setLength2(Integer.valueOf(prefData.getValue()));
					}
					if (prefData.getKey() != null && "Width2".equalsIgnoreCase(prefData.getKey())) {
						handicap.setWidth2(Integer.valueOf(prefData.getValue()));
					}
					if (prefData.getKey() != null && "Height2".equalsIgnoreCase(prefData.getKey())) {
						handicap.setHeight2(Integer.valueOf(prefData.getValue()));
					}
					if (prefData.getKey() != null && "LengthPlie2".equalsIgnoreCase(prefData.getKey())) {
						handicap.setLengthPlie2(Integer.valueOf(prefData.getValue()));
					}
					if (prefData.getKey() != null && "WidthPlie2".equalsIgnoreCase(prefData.getKey())) {
						handicap.setWidthPlie2(Integer.valueOf(prefData.getValue()));
					}
					if (prefData.getKey() != null && "HeightPlie2".equalsIgnoreCase(prefData.getKey())) {
						handicap.setHeightPlie2(Integer.valueOf(prefData.getValue()));
					}
					if (prefData.getKey() != null && "Weight2".equalsIgnoreCase(prefData.getKey())) {
						handicap.setWeight2(Integer.valueOf(prefData.getValue()));
					}
					
					if (prefData.getKey() != null && "OtherMat".equalsIgnoreCase(prefData.getKey())) {
						handicap.setOtherMat(prefData.getValue());
					}
					if (prefData.getKey() != null && "DogGuideFlag".equalsIgnoreCase(prefData.getKey())) {
						handicap.setDogGuideFlag(prefData.getValue());
					}
					if (prefData.getKey() != null && "DogGuideBreed".equalsIgnoreCase(prefData.getKey())) {
						handicap.setDogGuideBreed(prefData.getValue());
					}
					if (prefData.getKey() != null && "DogGuideWeight".equalsIgnoreCase(prefData.getKey())) {
						handicap.setDogGuideWeight(Integer.valueOf(prefData.getValue()));
					}
					if (prefData.getKey() != null && "OxygFlag".equalsIgnoreCase(prefData.getKey())) {
						handicap.setOxygFlag(prefData.getValue());
					}
					if (prefData.getKey() != null && "OxygOutput".equalsIgnoreCase(prefData.getKey())) {
						handicap.setOxygOutput(Integer.valueOf(prefData.getValue()));
					}
				}
			}
		}
	}

	private static void transformPreferenceRequestToTutorUm(PreferenceV2 preference, MarketingDataV2 marketingData) {
		if (preference.getType() != null && "TUM".equalsIgnoreCase(preference.getType())) {
			List<TutorUM> tutorUMList = marketingData.getTutorUMList();
			TutorUM tutorUM = new TutorUM();

			if (preference.getPreferenceDatas() != null 
				&& preference.getPreferenceDatas().getPreferenceData() != null 
				&& !preference.getPreferenceDatas().getPreferenceData().isEmpty()) {
				
				for (PreferenceDataV2 prefData : preference.getPreferenceDatas().getPreferenceData()) {
					if (prefData.getKey() != null && "civilityTutor".equalsIgnoreCase(prefData.getKey())) {
						tutorUM.setCivilityTutor(prefData.getValue());
					}
					if (prefData.getKey() != null && "lastnameTutor".equalsIgnoreCase(prefData.getKey())) {
						tutorUM.setLastnameTutor(prefData.getValue());
					}
					if (prefData.getKey() != null && "firstnameTutor".equalsIgnoreCase(prefData.getKey())) {
						tutorUM.setFirstnameTutor(prefData.getValue());
					}
					if (prefData.getKey() != null && "addressTutor".equalsIgnoreCase(prefData.getKey())) {
						tutorUM.setAddressTutor(prefData.getValue());
					}
					if (prefData.getKey() != null && "postCodeTutor".equalsIgnoreCase(prefData.getKey())) {
						tutorUM.setPostCodeTutor(prefData.getValue());
					}
					if (prefData.getKey() != null && "cityTutor".equalsIgnoreCase(prefData.getKey())) {
						tutorUM.setCityTutor(prefData.getValue());
					}
					if (prefData.getKey() != null && "codeProvinceTutor".equalsIgnoreCase(prefData.getKey())) {
						tutorUM.setCodeProvinceTutor(prefData.getValue());
					}
					if (prefData.getKey() != null && "countryCodeTutor".equalsIgnoreCase(prefData.getKey())) {
						tutorUM.setCountryCodeTutor(prefData.getValue());
					}
					if (prefData.getKey() != null && "phoneNumberTutor".equalsIgnoreCase(prefData.getKey())) {
						tutorUM.setPhoneNumberTutor(prefData.getValue());
					}
					if (prefData.getKey() != null && "dialingCodePhoneTutor".equalsIgnoreCase(prefData.getKey())) {
						tutorUM.setDialingCodePhoneTutor(prefData.getValue());
					}
					if (prefData.getKey() != null && "mobilePhoneTutor".equalsIgnoreCase(prefData.getKey())) {
						tutorUM.setMobilePhoneTutor(prefData.getValue());
					}
					if (prefData.getKey() != null && "dialingCodeMobileTutor".equalsIgnoreCase(prefData.getKey())) {
						tutorUM.setDialingCodeMobileTutor(prefData.getValue());
					}
					if (prefData.getKey() != null && "orderTutor".equalsIgnoreCase(prefData.getKey())) {
						tutorUM.setOrderTutor(Integer.valueOf(prefData.getValue()));
					}
				}
			}
			tutorUMList.add(tutorUM);
		}
	}

	private static void transformPreferenceRequestToTravelCompanion(PreferenceV2 preference, MarketingDataV2 marketingData) throws JrafDomainException {
		if (preference.getType() != null && "TCC".equalsIgnoreCase(preference.getType())) {
			List<TravelCompanionV2> travelCompanionList = marketingData.getTravelCompanionV2List();
			TravelCompanionV2 travelCompanion = new TravelCompanionV2();
			
			if (preference.getPreferenceDatas() != null 
				&& preference.getPreferenceDatas().getPreferenceData() != null 
				&& !preference.getPreferenceDatas().getPreferenceData().isEmpty()) {
				
				for (PreferenceDataV2 prefData : preference.getPreferenceDatas().getPreferenceData()) {
					PersonalInformation tcpi = travelCompanion.getTCPersonalInformation();
					
					if (prefData.getKey() != null && "civility".equalsIgnoreCase(prefData.getKey())) {
						travelCompanion.setCivility(prefData.getValue());
					}
					if (prefData.getKey() != null && "lastname".equalsIgnoreCase(prefData.getKey())) {
						travelCompanion.setLastName(prefData.getValue());
					}
					if (prefData.getKey() != null && "firstname".equalsIgnoreCase(prefData.getKey())) {
						travelCompanion.setFirstName(prefData.getValue());
					}
					if (prefData.getKey() != null && "dateOfBirth".equalsIgnoreCase(prefData.getKey())) {
						travelCompanion.setDateOfBirth(SicDateUtils.convertStringToDateDDMMYYYY(prefData.getValue()));
					}
					if (prefData.getKey() != null && "email".equalsIgnoreCase(prefData.getKey())) {
						travelCompanion.setEmail(prefData.getValue());
					}
					if (prefData.getKey() != null && "blueBizNumber".equalsIgnoreCase(prefData.getKey())) {
						if (tcpi == null) {
							tcpi = new PersonalInformation();
							tcpi.setBlueBizNumber(prefData.getValue());
							travelCompanion.setTCPersonalInformation(tcpi);
						} else {
							tcpi.setBlueBizNumber(prefData.getValue());
						}
					}
					if (prefData.getKey() != null && "fFPNumber".equalsIgnoreCase(prefData.getKey())) {
						if (tcpi == null) {
							tcpi = new PersonalInformation();
							tcpi.setFFPNumber(prefData.getValue());
							travelCompanion.setTCPersonalInformation(tcpi);
						} else {
							tcpi.setFFPNumber(prefData.getValue());
						}
					}
					if (prefData.getKey() != null && "fFPProgram".equalsIgnoreCase(prefData.getKey())) {
						if (tcpi == null) {
							tcpi = new PersonalInformation();
							tcpi.setFFPProgram(prefData.getValue());
							travelCompanion.setTCPersonalInformation(tcpi);
						} else {
							tcpi.setFFPProgram(prefData.getValue());
						}
					}
				}
			}
			travelCompanionList.add(travelCompanion);
		}
	}

	private static void transformPreferenceRequestToPersonalInfo(PreferenceV2 preference, MarketingDataV2 marketingData) {
		if (preference.getType() != null && "PIC".equalsIgnoreCase(preference.getType())) {
			PersonalInformation personalInfo = marketingData.getPersonalInformation();
			if (personalInfo == null) {
				personalInfo = new PersonalInformation();
				marketingData.setPersonalInformation(personalInfo);
			}
			if (preference.getPreferenceDatas() != null 
				&& preference.getPreferenceDatas().getPreferenceData() != null 
				&& !preference.getPreferenceDatas().getPreferenceData().isEmpty()) {
				
				for (PreferenceDataV2 prefData : preference.getPreferenceDatas().getPreferenceData()) {
					if (prefData.getKey() != null && "blueBizNumber".equalsIgnoreCase(prefData.getKey())) {
						personalInfo.setBlueBizNumber(prefData.getValue());
					}
					if (prefData.getKey() != null && "fFPNumber".equalsIgnoreCase(prefData.getKey())) {
						personalInfo.setFFPNumber(prefData.getValue());
					}
					if (prefData.getKey() != null && "fFPProgram".equalsIgnoreCase(prefData.getKey())) {
						personalInfo.setFFPProgram(prefData.getValue());
					}
				}
			}	
		}		
	}	
}
