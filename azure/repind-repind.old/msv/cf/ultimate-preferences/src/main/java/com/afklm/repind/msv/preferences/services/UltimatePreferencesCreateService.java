package com.afklm.repind.msv.preferences.services;

import com.afklm.repind.msv.preferences.criteria.UltimatePreferencesCriteria;
import com.afklm.repind.msv.preferences.entity.Preference;
import com.afklm.repind.msv.preferences.entity.PreferenceData;
import com.afklm.repind.msv.preferences.model.UltimatePreferencesModel;
import com.afklm.repind.msv.preferences.model.error.BusinessErrorList;
import com.afklm.repind.msv.preferences.model.error.RestError;
import com.afklm.repind.msv.preferences.repository.PreferenceRepository;
import com.afklm.repind.msv.preferences.services.builder.W000442BusinessErrorHandler;
import com.afklm.repind.msv.preferences.services.exception.ServiceException;
import com.afklm.repind.msv.preferences.wrapper.WrapperCreateUltimatePreferencesResponse;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.w000442.v8_0_1.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w000442.v8_0_1.CreateUpdateIndividualServiceV8;
import com.afklm.soa.stubs.w000442.v8_0_1.xsd0.IndividualInformationsV3;
import com.afklm.soa.stubs.w000442.v8_0_1.xsd0.PreferenceDataV2;
import com.afklm.soa.stubs.w000442.v8_0_1.xsd0.PreferenceDatasV2;
import com.afklm.soa.stubs.w000442.v8_0_1.xsd0.PreferenceV2;
import com.afklm.soa.stubs.w000442.v8_0_1.xsd4.RequestorV2;
import com.afklm.soa.stubs.w000442.v8_0_1.xsd6.IndividualRequest;
import com.afklm.soa.stubs.w000442.v8_0_1.xsd6.PreferenceRequest;
import com.afklm.soa.stubs.w000442.v8_0_1.xsd7.CreateUpdateIndividualRequest;
import com.afklm.soa.stubs.w000442.v8_0_1.xsd7.CreateUpdateIndividualResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
@Slf4j
@AllArgsConstructor
public class UltimatePreferencesCreateService {



    private final  CreateUpdateIndividualServiceV8 createOrUpdateService;

    @Autowired
    private PreferenceRepository preferenceRepository;




    public ResponseEntity<WrapperCreateUltimatePreferencesResponse> manageUltimatePreferences(UltimatePreferencesCriteria ultimatePreferencesCriteria) throws ServiceException {

        final long startTime = new Date().getTime();

        boolean isUpdate = "UPDATE".equals(ultimatePreferencesCriteria.getActionCode());
        boolean isDelete = "DELETE".equals(ultimatePreferencesCriteria.getActionCode());

        CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

        ultimatePreferencesCriteria.setActionCode(getActionCode(ultimatePreferencesCriteria));

        // Type of action detected
        logInfo(isUpdate, isDelete, ultimatePreferencesCriteria.getType());

        List<Preference> lpu = getListPreferences(ultimatePreferencesCriteria, isUpdate, isDelete);

        setRequestor(ultimatePreferencesCriteria, request);

        IndividualRequest individualRequest = new IndividualRequest();

        IndividualInformationsV3 individualInformation = new IndividualInformationsV3();
        individualInformation.setIdentifier(ultimatePreferencesCriteria.getGin());
        individualRequest.setIndividualInformations(individualInformation);

        request.setIndividualRequest(individualRequest);

        PreferenceRequest preferenceRequest = new PreferenceRequest();

        PreferenceV2 preference = new PreferenceV2();

        // We MAP the entry TYPE
        preference.setType(mapCriteriaTypeAndPrefType(ultimatePreferencesCriteria.getType()));

        PreferenceDatasV2 preferenceDatas = new PreferenceDatasV2();

        if (ultimatePreferencesCriteria.getData() != null) {

            for (UltimatePreferencesModel ultimatePreference : ultimatePreferencesCriteria.getData()) {

                // Dans le cas ou l"entree est VIDE, on a rien a mapper
                if (ultimatePreference == null || ultimatePreference.getKey() == null) {
                    break;
                }

                PreferenceDataV2 preferenceData = new PreferenceDataV2();

                preferenceData.setKey(getPreferenceDataKey(preference.getType(), ultimatePreference));
                preferenceData.setValue(getPreferenceDataValue(isDelete, ultimatePreference));

                preferenceDatas.getPreferenceData().add(preferenceData);
            }
        }

        preference.setPreferenceDatas(preferenceDatas);

        // We will search on database who is the exact line that must be UPDATED or DELETED
        if (isUpdate) {
            doUpdate(lpu, preference);
        } else if (isDelete) {
            doDelete(lpu, preference);
        }

        preferenceRequest.getPreference().add(preference);

        request.setPreferenceRequest(preferenceRequest);

        try {
            CreateUpdateIndividualResponse res;
            res = createOrUpdateService.createIndividual(request);

            if (res == null) {
                throw new ServiceException(BusinessErrorList.API_ULTIMATE_PREFERENCES_NOT_FOUND.getError(), HttpStatus.BAD_REQUEST);
            }

            final WrapperCreateUltimatePreferencesResponse wrapperUltimatePreferencesResponse = new WrapperCreateUltimatePreferencesResponse();
            wrapperUltimatePreferencesResponse.retour = res.isSuccess();
            wrapperUltimatePreferencesResponse.query = new Date().getTime() - startTime + " ms";

            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            headers.add("Cache-Control", "no-cache, no-store, max-age=0, must-revalidate");

            return new ResponseEntity<>(wrapperUltimatePreferencesResponse, headers, HttpStatus.OK);

        } catch (SystemException e) {
            RestError restError = W000442BusinessErrorHandler.handleSystemException(e);
            throw new ServiceException(restError, HttpStatus.BAD_REQUEST);
        } catch (BusinessErrorBlocBusinessException e) {
            RestError restError = W000442BusinessErrorHandler.handleBusinessErrorException(e);
            throw new ServiceException(restError, HttpStatus.BAD_REQUEST);
        }
    }

    private String getPreferenceDataValue(boolean isDelete, UltimatePreferencesModel ultimatePreference) {
        if (isDelete) {
            return null;
        } else {
            return ultimatePreference.getValue();
        }
    }

    private String getActionCode(UltimatePreferencesCriteria ultimatePreferencesCriteria) {
        if (ultimatePreferencesCriteria.getActionCode() == null ||
                "".equals(ultimatePreferencesCriteria.getActionCode()) ||
                (!"UPDATE".equalsIgnoreCase(ultimatePreferencesCriteria.getActionCode()) && !"DELETE".equalsIgnoreCase(ultimatePreferencesCriteria.getActionCode()))) {
            return "CREATE";
        } else {
            return ultimatePreferencesCriteria.getActionCode();
        }
    }

    private List<Preference> getListPreferences(UltimatePreferencesCriteria ultimatePreferencesCriteria, boolean isUpdate, boolean isDelete) throws ServiceException {
        List<Preference> prefs = null;
        if (isUpdate || isDelete) {
            prefs = preferenceRepository.findUltimatePreferencesByGin(ultimatePreferencesCriteria.getGin());

            if (prefs == null || prefs.isEmpty()) {
                throw new ServiceException(BusinessErrorList.API_ULTIMATE_PREFERENCES_NOT_FOUND.getError(), HttpStatus.NOT_FOUND);
            }
        }
        return prefs;
    }

    private String mapCriteriaTypeAndPrefType(String criteriaType) {
        switch (criteriaType) {
            case "TA_SERVICING":
                return "UTS";
            case "FOOD_BEVERAGE":
                return "UFB";
            case "ON_BOARD":
                return "UOB";
            case "SEATING":
                return "UST";
            case "LOUNGES":
                return "ULO";
            case "COMMUNICATION":
                return "UCO";
            case "TO_FROM_AIRPORT":
                return "UTF";
            case "PRESS_MEDIA":
                return "UPM";
            case "MILES_USAGE":
                return "UMU";
            case "LEISURE":
                return "ULS";
            case "FAVORITE_DESTINATION":
                return "UFD";
            default:
                log.error("TYPE not supported : '" + criteriaType + "'");
                return null;
        }
    }

    private void logInfo(boolean isUpdate, boolean isDelete, String filledType) {
        if (isUpdate) {
            log.info("UPDATE for " + filledType);
        } else if (isDelete) {
            log.info("DELETE for " + filledType);
        } else {
            log.info("CREATE for " + filledType);
        }
    }

    private void doUpdate(List<Preference> lpu, PreferenceV2 preference) throws ServiceException {
        for (Preference p : lpu) {                                                // Recherche pour l UPDATE des champs en entrée
            if (p.getType().equals(preference.getType())) {                    // On cherche le TYPE associé
                // It is an UPDATE on a non existing SubPreference ?
                if (preference.getId() == null) {
                    log.info("UPDATE on CREATE for " + preference.getType() + " detected");
                    preference.setId(p.getPreferenceId().toString());            // On stocke l'ID pour declencher la CREATION
                } else {
                    log.info("UPDATE on " + preference.getId() + " detected");
                    findIdInPreferenceDataForUpdateMode(p, preference);
                }
            }
        }

        // On UPDATE mode if no found in database, we stop it
        if (preference.getId() == null) {
            throw new ServiceException(BusinessErrorList.API_NOTHING_TO_UPDATE.getError(), HttpStatus.BAD_REQUEST);
        }
    }

    private void findIdInPreferenceDataForUpdateMode(Preference p, PreferenceV2 preference) {
        for (PreferenceData pd : p.getPreferenceData()) {
            // On verifie le premiere element de la liste en entrée avec ce qu il y a en base de données
            if ((pd.getKey().toUpperCase()).equals(preference.getPreferenceDatas().getPreferenceData().get(0).getKey())) {

                preference.setId(p.getPreferenceId().toString());        // On stocke l'ID pour declencher la MAJ
                log.info("UPDATE on " + preference.getId() + " detected !");
            }
        }
    }

    private void doDelete(List<Preference> lpu, PreferenceV2 preference) throws ServiceException {
        boolean isDeletedAll = false;
        for (Preference p : lpu) {                                                // Recherche pour l UPDATE des champs en entrée
            if (p.getType().equals(preference.getType())) {                        // On cherche le TYPE associé

                // We already choose to delete all type
                if (isDeletedAll) {
                    break;
                }

                log.info("DELETE on " + p.getPreferenceId() + " checked...");
                isDeletedAll = setPrefId(p, preference);
            }
        }

        // On DELETE mode if no found in database, we stop it
        if (preference.getId() == null && !isDeletedAll) {
            throw new ServiceException(BusinessErrorList.API_NOTHING_TO_DELETE.getError(), HttpStatus.BAD_REQUEST);
        }
    }

    private boolean setPrefId(Preference p, PreferenceV2 preference) {
        boolean isDeletedAll = false;
        // REPIND-1765 : Delete all Pref for a TYPE
        if (p.getPreferenceData() == null
                || preference.getPreferenceDatas() == null
                || preference.getPreferenceDatas().getPreferenceData() == null
                || preference.getPreferenceDatas().getPreferenceData().isEmpty()) {
            preference.setPreferenceDatas(null);
            if (p.getPreferenceId() != null) {
                preference.setId(p.getPreferenceId().toString());
            }

            log.info("DELETE all " + preference.getType());
            isDeletedAll = true;
        } else {
            isDeletedAll = findIdInPreferenceDataForDeleteMode(p, preference);
        }

        return isDeletedAll;
    }

    private boolean findIdInPreferenceDataForDeleteMode(Preference p, PreferenceV2 preference) {
        boolean isDeletedAll = false;
        for (PreferenceData pd : p.getPreferenceData()) {

            // On verifie le premiere element de la liste en entrée avec ce qu il y a en base de données
            if ((pd.getKey().toUpperCase()).equals(preference.getPreferenceDatas().getPreferenceData().get(0).getKey())) {

                preference.setId(p.getPreferenceId().toString());        // On stocke l'ID pour declencher la MAJ
                log.info("DELETE on " + preference.getId() + " detected !");

                // REPIND-1765 : Check if there is only one value and delete the father on PREF level
                if (p.getPreferenceData().size() == 1) {
                    // Delete all the PREF and not only the Sub Pref
                    preference.setPreferenceDatas(null);
                    if (p.getPreferenceId() != null) {
                        preference.setId(p.getPreferenceId().toString());
                    }
                    log.info("DELETE all " + preference.getType() + " cause only one...");
                    isDeletedAll = true;
                    break;
                }
            }
        }
        return isDeletedAll;
    }

    private String getPreferenceDataKey(String prefType, UltimatePreferencesModel ultimatePreference) {
        String errorSubTypeNotSupp = "SUBTYPE not supported for '" + prefType + "' : '" + ultimatePreference.getKey() + "'";

        String result;
        switch (prefType) {
            case "UTS":
                String[][] uts = new String[][]{
                        {"CUSTOMER_DETAILS", "CUSTOMERDETAILS"},
                        {"CUSTOMER_NOTES", "CUSTOMERNOTES"},
                        {"CALL_BY_NAME", "CALLBYNAME"},
                        {"DISCRETION", "DISCRETION"},
                        {"PERSONNAL_ASSISTANT", "PERSONNALASSISTANT"}
                };
                Map<String, String> utsKeys = Stream.of(uts).collect(Collectors.toMap(data -> data[0], data -> data[1]));

                result = utsKeys.getOrDefault(ultimatePreference.getKey().toUpperCase(), null);
                break;
            case "UFB":
                String[][] ufb = new String[][]{
                        {"WELCOME_DRINK", "WELCOMEDRINK"},
                        {"WINES_BEVERAGES", "WINESBEVERAGES"},
                        {"SPECIAL_MEAL_PAID_MEALS", "SPECIALMEALPAIDMEALS"},
                        {"MEAL_PREFERENCES", "MEALPREFERENCES"},
                        {"FOOD_DRINKS_TO_AVOID", "FOODDRINKSTOAVOID"}
                };
                Map<String, String> ufbKeys = Stream.of(ufb).collect(Collectors.toMap(data -> data[0], data -> data[1]));

                result = ufbKeys.getOrDefault(ultimatePreference.getKey().toUpperCase(), null);
                break;
            case "UOB":
                String[][] uob = new String[][]{
                        {"EXTRA_COMFORT_AMENITIES", "EXTRACOMFORTAMENITIES"},
                        {"ON_BOARD_SERVICE", "ONBOARDSERVICE"},
                        {"SLEEP_RYTHM", "SLEEPRYTHM"},
                        {"CUSTOMER_DELIGHTER", "CUSTOMERDELIGHTER"}
                };
                Map<String, String> uobKeys = Stream.of(uob).collect(Collectors.toMap(data -> data[0], data -> data[1]));

                result = uobKeys.getOrDefault(ultimatePreference.getKey().toUpperCase(), null);
                break;
            case "UST":
                String[][] ust = new String[][]{
                        {"SEAT_NUMBER_BY_AIRCRAFT_CABIN_ALLERGY_SEAT", "SEATNUMBERBYAIRCRAFTCABINALLERGYSEAT"},
                        {"ROW_AISLE_WINDOW", "ROWAISLEWINDOW"},
                        {"CABIN_SEATING_NIGHT_AND_DAY", "CABINSEATINGNIGHTANDDAY"},
                        {"PAID_SEAT_UPGRADE", "PAIDSEATUPGRADE"}
                };
                Map<String, String> ustKeys = Stream.of(ust).collect(Collectors.toMap(data -> data[0], data -> data[1]));

                result = ustKeys.getOrDefault(ultimatePreference.getKey().toUpperCase(), null);
                break;
            case "ULO":
                String[][] ulo = new String[][]{
                        {"SPECIFIC_ATTENTION", "SPECIFICATTENTION"},
                        {"COMFORT_AMENITIES", "COMFORTAMENITIES"},
                        {"ACTIVITY_PATTERN", "ACTIVITYPATTERN"},
                        {"PAID_SEAT_UPGRADE", "PAIDSEATUPGRADE"}
                };
                Map<String, String> keys = Stream.of(ulo).collect(Collectors.toMap(data -> data[0], data -> data[1]));

                result = keys.getOrDefault(ultimatePreference.getKey().toUpperCase(), null);
                break;
            case "UCO":
                String[][] uco = new String[][]{
                        {"PREFERRED_SPOKEN_LANGUAGE", "PREFERREDSPOKENLANGUAGE"},
                        {"TRAVEL_ASSISTANT", "TRAVELASSISTANT"},
                        {"CHECK_IN_CHANNEL", "CHECKINCHANNEL"},
                        {"PREFERRED_COMMUNICATION_CHANNEL", "PREFERREDCOMMUNICATIONCHANNEL"}
                };
                Map<String, String> ucoKeys = Stream.of(uco).collect(Collectors.toMap(data -> data[0], data -> data[1]));

                result = ucoKeys.getOrDefault(ultimatePreference.getKey().toUpperCase(), null);
                break;
            case "UTF":
                String[][] utf = new String[][]{
                        {"OTHERS", "OTHERS"},
                        {"TAXI", "TAXI"}
                };
                Map<String, String> utfKeys = Stream.of(utf).collect(Collectors.toMap(data -> data[0], data -> data[1]));

                result = utfKeys.getOrDefault(ultimatePreference.getKey().toUpperCase(), null);
                break;
            case "UPM":
                String[][] upm = new String[][]{
                        {"NEWSPAPER", "NEWSPAPER"}
                };
                Map<String, String> upmKeys = Stream.of(upm).collect(Collectors.toMap(data -> data[0], data -> data[1]));

                result = upmKeys.getOrDefault(ultimatePreference.getKey().toUpperCase(), null);
                break;
            case "UMU":
                String[][] umu = new String[][]{
                        {"UPGRADES_SERVICES_IN_MILES", "UPGRADESSERVICESINMILES"},
                        {"AWARD_TICKET", "AWARDTICKET"},
                        {"PARTNERS", "PARTNERS"},
                        {"SHOPPING", "SHOPPING"}
                };
                Map<String, String> umuKeys = Stream.of(umu).collect(Collectors.toMap(data -> data[0], data -> data[1]));

                result = umuKeys.getOrDefault(ultimatePreference.getKey().toUpperCase(), null);
                break;
            case "ULS":
                String[][] uls = new String[][]{
                        {"SPORT_ACTIVITY", "SPORTACTIVITY"},
                        {"CULTURAL_ACTIVITY", "CULTURALACTIVITY"},
                        {"MUSIC_MOVIES", "MUSICMOVIES"}
                };
                Map<String, String> ulsKeys = Stream.of(uls).collect(Collectors.toMap(data -> data[0], data -> data[1]));

                result = ulsKeys.getOrDefault(ultimatePreference.getKey().toUpperCase(), null);
                break;
            case "UFD":
                String[][] ufd = new String[][]{
                        {"COUNTRIES", "COUNTRIES"},
                        {"CITY", "CITY"},
                        {"AIRPORT", "AIRPORT"},
                        {"HOTELS", "HOTELS"},
                        {"AWARD", "AWARD"}
                };
                Map<String, String> ufdKeys = Stream.of(ufd).collect(Collectors.toMap(data -> data[0], data -> data[1]));

                result = ufdKeys.getOrDefault(ultimatePreference.getKey().toUpperCase(), null);
                break;
            default:
                log.error("TYPE not supported : '" + prefType + "'");
                return null;
        }

        if (result == null) {
            log.error(errorSubTypeNotSupp);
        }
        return result;
    }

    private void setRequestor(UltimatePreferencesCriteria ultimatePreferencesCriteria, CreateUpdateIndividualRequest request){
        if (ultimatePreferencesCriteria.getRequestor() != null) {

            RequestorV2 requestor = new RequestorV2();
            requestor.setApplicationCode(ultimatePreferencesCriteria.getRequestor().getApplication());
            requestor.setChannel(ultimatePreferencesCriteria.getRequestor().getChannel());
            requestor.setIpAddress(ultimatePreferencesCriteria.getRequestor().getIpAddress());
            requestor.setMatricule(ultimatePreferencesCriteria.getRequestor().getMatricule());
            requestor.setSignature(ultimatePreferencesCriteria.getRequestor().getSignature());
            requestor.setSite(ultimatePreferencesCriteria.getRequestor().getSite());
            requestor.setOfficeId(ultimatePreferencesCriteria.getRequestor().getOfficeId());
            requestor.setManagingCompany(ultimatePreferencesCriteria.getRequestor().getCompany());

            requestor.setContext(null);
            requestor.setLoggedGin(ultimatePreferencesCriteria.getGin());

            request.setRequestor(requestor);
        }
    }
}
