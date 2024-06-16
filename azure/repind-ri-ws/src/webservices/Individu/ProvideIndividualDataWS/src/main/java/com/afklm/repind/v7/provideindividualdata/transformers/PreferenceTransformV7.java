package com.afklm.repind.v7.provideindividualdata.transformers;

import com.afklm.soa.stubs.w000410.v2.common.*;
import com.afklm.soa.stubs.w000418.v7.data.ProvideIndividualInformationResponse;
import com.afklm.soa.stubs.w000418.v7.response.PreferenceResponse;
import com.afklm.soa.stubs.w000418.v7.sicindividutype.PreferenceDataV2;
import com.afklm.soa.stubs.w000418.v7.sicindividutype.PreferenceDatasV2;
import com.afklm.soa.stubs.w000418.v7.sicindividutype.PreferenceV2;
import com.airfrance.ref.exception.InvalidParameterException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Class utility to transform a BDM MarketingDataV2 objects into SIC Preferences objects
 * @author 3061561
 */
public class PreferenceTransformV7 {

	public static void transformToPreferenceResponse(MarketingDataV2 marketingData, ProvideIndividualInformationResponse response) throws InvalidParameterException {
		if(response == null){
			throw new InvalidParameterException("response is null");
		}

		if (marketingData != null) {

			if (response.getPreferenceResponse() == null) {
				response.setPreferenceResponse(new PreferenceResponse());
			}

			// Travel Doc from BDM (not stored in RI DB)
			// REPIND-918: desactivate this
//			PreferenceTransformV7.transformTravelDocToPreferenceResponse(marketingData, response);

			// Emergency Contact
			// First we get the Preferences from the RI DB, if the Preferences are already filled, we don't need to get them from the BDM
			// REPIND-1392
			/*
			if (!isPreferenceBlockAlreadyFilled(response, "ECC")) {
				PreferenceTransformV7.transformEmergencyCtcToPreferenceResponse(marketingData, response);
			}

			// Travel Preferences
			// First we get the Preferences from the RI DB, if the Preferences are already filled, we don't need to get them from the BDM
			if (!isPreferenceBlockAlreadyFilled(response, "TPC")) {
				PreferenceTransformV7.transformTravelPrefToPreferenceResponse(marketingData, response);
			}
			*/
			
			// Handicap
			// First we get the Preferences from the RI DB, if the Preferences are already filled, we don't need to get them from the BDM
			if (!isPreferenceBlockAlreadyFilled(response, "HDC")) {
				PreferenceTransformV7.transformHandicapToPreferenceResponse(marketingData, response);
			}
			
			//REPIND-1321: UM from BDM are now totally ignored as we are now using the new feature KID SOLO (Delegation)
			// Tutor UM
			//PreferenceTransformV7.transformTutorUmToPreferenceResponse(marketingData, response);

			// Travel Companion
			// First we get the Preferences from the RI DB, if the Preferences are already filled, we don't need to get them from the BDM
			// REPIND-1392
			/*
			if (!isPreferenceBlockAlreadyFilled(response, "TCC")) {
				PreferenceTransformV7.transformTravelCompanionToPreferenceResponse(marketingData, response);
			}

			// Personal Information
			// First we get the Preferences from the RI DB, if the Preferences are already filled, we don't need to get them from the BDM
			if (!isPreferenceBlockAlreadyFilled(response, "PIC")) {
				PreferenceTransformV7.transformPersonalInfoToPreferenceResponse(marketingData, response);
			}
			*/
		}
	}
	
	private static boolean isPreferenceBlockAlreadyFilled(ProvideIndividualInformationResponse response, String blockName) {
		if (response.getPreferenceResponse() != null) {
			for (PreferenceV2 preference : response.getPreferenceResponse().getPreference()) {
				if (preference.getType().equalsIgnoreCase(blockName)) {
					if (preference.getPreferenceDatas() != null && !preference.getPreferenceDatas().getPreferenceData().isEmpty()) {
						return true;
					}
				}
			}
		}
		return false;
	}

	private static void transformToPreferenceDataResponse(PreferenceDatasV2 preferenceDatas, String key, String value) {
		PreferenceDataV2 preferenceData = new PreferenceDataV2();

		preferenceData.setKey(key);
		preferenceData.setValue(value);

		preferenceDatas.getPreferenceData().add(preferenceData);
	}

	private static void transformPersonalInfoToPreferenceResponse(MarketingDataV2 marketingData, ProvideIndividualInformationResponse response) {
		String idPreference = "0";
		String typePreference = "PIC";    // PersonalInformationContent

		if (marketingData.getPersonalInformation() != null) {
			PersonalInformation persoInfo = marketingData.getPersonalInformation();
			PreferenceV2 preferencePersoInfo = new PreferenceV2();
			PreferenceDatasV2 preferenceDatas = new PreferenceDatasV2();

			preferencePersoInfo.setId(idPreference);
			preferencePersoInfo.setType(typePreference);
			preferencePersoInfo.setPreferenceDatas(preferenceDatas);

			response.getPreferenceResponse().getPreference().add(preferencePersoInfo);

			if (persoInfo.getBlueBizNumber() != null) {
				PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "blueBizNumber", persoInfo.getBlueBizNumber());
			}
			if (persoInfo.getFFPNumber() != null) {
				PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "fFPNumber", persoInfo.getFFPNumber());
			}
			if (persoInfo.getFFPProgram() != null) {
				PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "fFPProgram", persoInfo.getFFPProgram());
			}
		}
	}

	private static void transformTravelCompanionToPreferenceResponse(MarketingDataV2 marketingData, ProvideIndividualInformationResponse response) {
		int idPreference = 0;
		String typePreference = "TCC";    // TravelCompanionContent

		if (marketingData.getTravelCompanionV2List() != null && !marketingData.getTravelCompanionV2List().isEmpty()) {
			List<TravelCompanionV2> travelCompanionList = marketingData.getTravelCompanionV2List();

			for (TravelCompanionV2 travelCompanion : travelCompanionList) {
				PreferenceV2 preferenceTravelCompanion = new PreferenceV2();
				PreferenceDatasV2 preferenceDatas = new PreferenceDatasV2();

				preferenceTravelCompanion.setId(Integer.toString(idPreference));
				preferenceTravelCompanion.setType(typePreference);
				preferenceTravelCompanion.setPreferenceDatas(preferenceDatas);;

				response.getPreferenceResponse().getPreference().add(preferenceTravelCompanion);

				if (travelCompanion.getCivility() != null) {
					PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "civility", travelCompanion.getCivility());
				}
				if (travelCompanion.getFirstName() != null) {
					PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "firstname", travelCompanion.getFirstName());
				}
				if (travelCompanion.getLastName() != null) {
					PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "lastname", travelCompanion.getLastName());
				}
				if (travelCompanion.getDateOfBirth() != null) {
					PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "dateOfBirth", PreferenceTransformV7.convertDateToString(travelCompanion.getDateOfBirth()));
				}
				if (travelCompanion.getEmail() != null) {
					PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "email", travelCompanion.getEmail());
				}
				if (travelCompanion.getTCPersonalInformation() != null) {
					PersonalInformation tcpi = travelCompanion.getTCPersonalInformation();

					if (tcpi.getBlueBizNumber() != null) {
						PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "blueBizNumber", tcpi.getBlueBizNumber());
					}
					if (tcpi.getFFPNumber() != null) {
						PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "fFPNumber", tcpi.getFFPNumber());
					}
					if (tcpi.getFFPProgram() != null) {
						PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "fFPProgram", tcpi.getFFPProgram());
					}
				}
				idPreference++;
			}
		}
	}

	private static void transformTutorUmToPreferenceResponse(MarketingDataV2 marketingData, ProvideIndividualInformationResponse response) {
		int idPreference = 0;
		String typePreference = "TUM";  // TutorUM Content

		if (marketingData.getTutorUMList() != null && !marketingData.getTutorUMList().isEmpty()) {
			List<TutorUM> tutorUMList = marketingData.getTutorUMList();

			for (TutorUM tutorUM : tutorUMList) {
				PreferenceV2 preferenceTutorUM = new PreferenceV2();
				PreferenceDatasV2 preferenceDatas = new PreferenceDatasV2();

				preferenceTutorUM.setId(Integer.toString(idPreference));
				preferenceTutorUM.setType(typePreference);
				preferenceTutorUM.setPreferenceDatas(preferenceDatas);

				response.getPreferenceResponse().getPreference().add(preferenceTutorUM);

				if (tutorUM.getCivilityTutor() != null) {
					PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "civilityTutor", tutorUM.getCivilityTutor());
				}
				if (tutorUM.getLastnameTutor() != null) {
					PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "lastnameTutor", tutorUM.getLastnameTutor());
				}
				if (tutorUM.getFirstnameTutor() != null) {
					PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "firstnameTutor", tutorUM.getFirstnameTutor());
				}
				if (tutorUM.getAddressTutor() != null) {
					PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "addressTutor", tutorUM.getAddressTutor());
				}
				if (tutorUM.getPostCodeTutor() != null) {
					PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "postCodeTutor", tutorUM.getPostCodeTutor());
				}
				if (tutorUM.getCityTutor() != null) {
					PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "cityTutor", tutorUM.getCityTutor());
				}
				if (tutorUM.getCodeProvinceTutor() != null) {
					PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "codeProvinceTutor", tutorUM.getCodeProvinceTutor());
				}
				if (tutorUM.getCountryCodeTutor() != null) {
					PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "countryCodeTutor", tutorUM.getCountryCodeTutor());
				}
				if (tutorUM.getPhoneNumberTutor() != null) {
					PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "phoneNumberTutor", tutorUM.getPhoneNumberTutor());
				}
				if (tutorUM.getDialingCodePhoneTutor() != null) {
					PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "dialingCodePhoneTutor", tutorUM.getDialingCodePhoneTutor());
				}
				if (tutorUM.getMobilePhoneTutor() != null) {
					PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "mobilePhoneTutor", tutorUM.getMobilePhoneTutor());
				}
				if (tutorUM.getDialingCodeMobileTutor() != null) {
					PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "dialingCodeMobileTutor", tutorUM.getDialingCodeMobileTutor());
				}
				if (tutorUM.getOrderTutor() != null) {
					PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "orderTutor", Integer.toString(tutorUM.getOrderTutor()));
				}
				idPreference++;
			}
		}
	}

	private static void transformHandicapToPreferenceResponse(MarketingDataV2 marketingData, ProvideIndividualInformationResponse response) {
		String idPreference = "0";
		String typePreference = "HDC";    // Handicap Content

		if (marketingData.getHandicap() != null) {
			Handicap handicap = marketingData.getHandicap();
			PreferenceV2 preferenceHandicap = new PreferenceV2();
			PreferenceDatasV2 preferenceDatas = new PreferenceDatasV2();

			preferenceHandicap.setId(idPreference);
			preferenceHandicap.setType(typePreference);
			preferenceHandicap.setPreferenceDatas(preferenceDatas);

			response.getPreferenceResponse().getPreference().add(preferenceHandicap);

			if (handicap.getCodeHCP1() != null) {
				PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "CodeHCP1", handicap.getCodeHCP1());
			}

			if (handicap.getCodeHCP2() != null) {
				PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "CodeHCP2", handicap.getCodeHCP2());
			}

			if (handicap.getCodeHCP3() != null) {
				PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "CodeHCP3", handicap.getCodeHCP3());
			}

			if (handicap.getMedaCCFlag() != null) {
				PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "MedaCCFlag", handicap.getMedaCCFlag());
			}

			if (handicap.getMedaCCDateStart() != null) {
				PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "MedaCCDateStart", PreferenceTransformV7.convertDateToString(handicap.getMedaCCDateStart()));
			}

			if (handicap.getMedaCCDateEnd() != null) {
				PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "MedaCCDateEnd", PreferenceTransformV7.convertDateToString(handicap.getMedaCCDateEnd()));
			}

			if (handicap.getMedaCCAccomp() != null) {
				PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "MedaCCAccomp", handicap.getMedaCCAccomp());
			}

			if (handicap.getMedaCCTxt() != null) {
				PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "MedaCCTxt", handicap.getMedaCCTxt());
			}

			if (handicap.getMedaMCFlag() != null) {
				PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "MedaMCFlag", handicap.getMedaMCFlag());
			}

			if (handicap.getMedaMCDateStart() != null) {
				PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "MedaMCDateStart", PreferenceTransformV7.convertDateToString(handicap.getMedaMCDateStart()));
			}

			if (handicap.getMedaMCDateEnd() != null) {
				PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "MedaMCDateEnd", PreferenceTransformV7.convertDateToString(handicap.getMedaMCDateEnd()));
			}

			if (handicap.getMedaMCAccomp() != null) {
				PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "MedaMCAccomp", handicap.getMedaMCAccomp());
			}

			if (handicap.getMedaMCTxt() != null) {
				PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "MedaMCTxt", handicap.getMedaMCTxt());
			}

			if (handicap.getMedaLCFlag() != null) {
				PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "MedaLCFlag", handicap.getMedaLCFlag());
			}

			if (handicap.getMedaLCDateStart() != null) {
				PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "MedaLCDateStart", PreferenceTransformV7.convertDateToString(handicap.getMedaLCDateStart()));
			}

			if (handicap.getMedaLCDateEnd() != null) {
				PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "MedaLCDateEnd", PreferenceTransformV7.convertDateToString(handicap.getMedaLCDateEnd()));
			}

			if (handicap.getMedaLCAccomp() != null) {
				PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "MedaLCAccomp", handicap.getMedaLCAccomp());
			}

			if (handicap.getMedaLCTxt() != null) {
				PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "MedaLCTxt", handicap.getMedaLCTxt());
			}

			if (handicap.getCodeMat1() != null) {
				PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "CodeMat1", handicap.getCodeMat1());
			}

			if (handicap.getLength1() != null) {
				PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "Length1", Integer.toString(handicap.getLength1()));
			}

			if (handicap.getWidth1() != null) {
				PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "Width1", Integer.toString(handicap.getWidth1()));
			}

			if (handicap.getHeight1() != null) {
				PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "Height1", Integer.toString(handicap.getHeight1()));
			}

			if (handicap.getLengthPlie1() != null) {
				PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "LengthPlie1", Integer.toString(handicap.getLengthPlie1()));
			}

			if (handicap.getWidthPlie1() != null) {
				PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "WidthPlie1", Integer.toString(handicap.getWidthPlie1()));
			}

			if (handicap.getHeightPlie1() != null) {
				PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "HeightPlie1", Integer.toString(handicap.getHeightPlie1()));
			}

			if (handicap.getWeight1() != null) {
				PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "Weight1", Integer.toString(handicap.getWeight1()));
			}

			if (handicap.getCodeMat2() != null) {
				PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "CodeMat2", handicap.getCodeMat2());
			}

			if (handicap.getLength2() != null) {
				PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "Length2", Integer.toString(handicap.getLength2()));
			}

			if (handicap.getWidth2() != null) {
				PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "Width2", Integer.toString(handicap.getWidth2()));
			}

			if (handicap.getHeight2() != null) {
				PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "Height2", Integer.toString(handicap.getHeight2()));
			}

			if (handicap.getLengthPlie2() != null) {
				PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "LengthPlie2", Integer.toString(handicap.getLengthPlie2()));
			}

			if (handicap.getWidthPlie2() != null) {
				PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "WidthPlie2", Integer.toString(handicap.getWidthPlie2()));
			}

			if (handicap.getHeightPlie2() != null) {
				PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "HeightPlie2", Integer.toString(handicap.getHeightPlie2()));
			}

			if (handicap.getWeight2() != null) {
				PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "Weight2", Integer.toString(handicap.getWeight2()));
			}

			if (handicap.getOtherMat() != null) {
				PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "OtherMat", handicap.getOtherMat());
			}

			if (handicap.getDogGuideFlag() != null) {
				PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "DogGuideFlag", handicap.getDogGuideFlag());
			}

			if (handicap.getDogGuideBreed() != null) {
				PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "DogGuideBreed", handicap.getDogGuideBreed());
			}

			if (handicap.getDogGuideWeight() != null) {
				PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "DogGuideWeight", Integer.toString(handicap.getDogGuideWeight()));
			}

			if (handicap.getOxygFlag() != null) {
				PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "OxygFlag", handicap.getOxygFlag());
			}

			if (handicap.getOxygOutput() != null) {
				PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "OxygOutput", Integer.toString(handicap.getOxygOutput()));
			}
		}
	}

	private static void transformTravelPrefToPreferenceResponse(MarketingDataV2 marketingData, ProvideIndividualInformationResponse response) {
		String idPreference = "0";
		String typePreference = "TPC";    // Travel Preference Content

		if (marketingData.getTravelPreferences() != null) {
			TravelPreferences travelPref = marketingData.getTravelPreferences();
			PreferenceV2 preferenceTravelPref = new PreferenceV2();
			PreferenceDatasV2 preferenceDatas = new PreferenceDatasV2();

			preferenceTravelPref.setId(idPreference);
			preferenceTravelPref.setType(typePreference);
			preferenceTravelPref.setPreferenceDatas(preferenceDatas);

			response.getPreferenceResponse().getPreference().add(preferenceTravelPref);

			if (travelPref.getDepartureCity() != null) {
				PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "departureCity", travelPref.getDepartureCity());
			}
			if (travelPref.getDepartureAirport() != null) {
				PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "departureAirport", travelPref.getDepartureAirport());
			}
			if (travelPref.getArrivalCity() != null) {
				PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "arrivalCity", travelPref.getArrivalCity());
			}
			if (travelPref.getArrivalAirport() != null) {
				PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "arrivalAirport", travelPref.getArrivalAirport());
			}
			if (travelPref.getMeal() != null) {
				PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "meal", travelPref.getMeal());
			}
			if (travelPref.getSeat() != null) {
				PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "seat", travelPref.getSeat());
			}
			if (travelPref.getTravelClass() != null) {
				PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "travelClass", travelPref.getTravelClass());
			}
			if (travelPref.getBoardingPass() != null) {
				PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "boardingPass", travelPref.getBoardingPass());
			}
			if (travelPref.getCustomerLeisure() != null) {
				PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "customerLeisure", travelPref.getCustomerLeisure());
			}
		}
	}

	private static void transformEmergencyCtcToPreferenceResponse(MarketingDataV2 marketingData, ProvideIndividualInformationResponse response) {
		int idPreference = 0;
		String typePreference = "ECC";    // Emergency Contact Content

		if (marketingData.getEmergencyContactList() != null) {
			List<EmergencyContact> emergencyCtcList = marketingData.getEmergencyContactList();

			for (EmergencyContact emergencyCtc : emergencyCtcList) {
				PreferenceV2 preferenceEmergencyCtc = new PreferenceV2();
				PreferenceDatasV2 preferenceDatas = new PreferenceDatasV2();

				preferenceEmergencyCtc.setId(Integer.toString(idPreference));
				preferenceEmergencyCtc.setType(typePreference);
				preferenceEmergencyCtc.setPreferenceDatas(preferenceDatas);

				response.getPreferenceResponse().getPreference().add(preferenceEmergencyCtc);

				if (emergencyCtc.getEmail() != null) {
					PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "email", emergencyCtc.getEmail());
				}
				if (emergencyCtc.getPhoneNumber() != null) {
					PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "phoneNumber", emergencyCtc.getPhoneNumber());
				}
				if (emergencyCtc.getLastName() != null) {
					PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "lastName", emergencyCtc.getLastName());
				}
				if (emergencyCtc.getFirstName() != null) {
					PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "firstName", emergencyCtc.getFirstName());
				}

				idPreference++;
			}
		}
	}

	// REPIND-918: Desactivate this method
	/*
	private static void transformTravelDocToPreferenceResponse(MarketingDataV2 marketingData, ProvideIndividualInformationResponse response) {
		// Determine if Travel Doc already found in RI DB
		boolean foundTravelDoc = false;
		int idPreference = 0;
		String typePreference = "TDC";    // Travel Document Content
		
		// Check if no travel doc found in RI DB
		if (response.getPreferenceResponse().getPreference() != null ) {
			for (PreferenceV2 pref : response.getPreferenceResponse().getPreference()) {
				if (pref.getType() != null && typePreference.equalsIgnoreCase(pref.getType())) {
					foundTravelDoc = true;
					break;
				}
			}
		}
		
		// Transform only if no Travel Doc not found in RI and exists in BDM
		if (marketingData.getTravelDocumentList() != null && !foundTravelDoc) {
			List<TravelDocument> travelDocList = marketingData.getTravelDocumentList();

			for (TravelDocument travelDoc : travelDocList) {
				PreferenceV2 preferenceTravelDoc = new PreferenceV2();
				PreferenceDatasV2 preferenceDatas = new PreferenceDatasV2();

//				preferenceTravelDoc.setId(Integer.toString(idPreference));
				preferenceTravelDoc.setType(typePreference);
				preferenceTravelDoc.setPreferenceDatas(preferenceDatas);

				response.getPreferenceResponse().getPreference().add(preferenceTravelDoc);

				if (travelDoc.getType() != null) {
					PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "type", travelDoc.getType());
				}
				if (travelDoc.getNumber() != null) {
					PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "number", travelDoc.getNumber());
				}
				if (travelDoc.getExpirationDate() != null) {
					PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "expirationDate", PreferenceTransformV7.convertDateToString(travelDoc.getExpirationDate()));
				}
				if (travelDoc.getNationality() != null) {
					PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "nationality", travelDoc.getNationality());
				}
				if (travelDoc.getIssueDate() != null) {
					PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "issueDate", PreferenceTransformV7.convertDateToString(travelDoc.getIssueDate()));
				}
				if (travelDoc.getCountryIssued() != null) {
					PreferenceTransformV7.transformToPreferenceDataResponse(preferenceDatas, "countryIssued", travelDoc.getCountryIssued());
				}

				idPreference++;
			}
		}
	}
	*/

	private static String convertDateToString(Date date) {
		try {
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			return df.format(date);
		}
		catch (Exception e) {
			return "Invalid Date";
		}
	}
}
