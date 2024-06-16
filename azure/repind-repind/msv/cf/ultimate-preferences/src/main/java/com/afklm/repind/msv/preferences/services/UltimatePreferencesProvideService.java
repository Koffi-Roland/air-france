package com.afklm.repind.msv.preferences.services;

import com.afklm.repind.msv.preferences.criteria.IndividualCriteria;
import com.afklm.repind.msv.preferences.entity.Preference;
import com.afklm.repind.msv.preferences.entity.PreferenceData;
import com.afklm.repind.msv.preferences.model.GetUltimatePreferencesModel;
import com.afklm.repind.msv.preferences.model.error.BusinessErrorList;
import com.afklm.repind.msv.preferences.repository.PreferenceRepository;
import com.afklm.repind.msv.preferences.services.exception.ServiceException;
import com.afklm.repind.msv.preferences.wrapper.WrapperProvideUltimatePreferencesResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.afklm.repind.msv.preferences.config.Config.BeanMapper;

@Component
@Slf4j
public class UltimatePreferencesProvideService {

    @Autowired
    private PreferenceRepository preferenceRepository;

    @Autowired
    private BeanMapper mapper;

    /**
     * Create and return provide service
     *
     * @return service
     * @throws ServiceException
     */
    public ResponseEntity<WrapperProvideUltimatePreferencesResponse> provideUltimatePreferencesByGin(IndividualCriteria individualCriteria) throws ServiceException {

        log.info("PROVIDE for " + individualCriteria.getGin());

        final long startTime = new Date().getTime();

        final List<Preference> listPreferences = preferenceRepository.findUltimatePreferencesByGin(individualCriteria.getGin());

        if (listPreferences == null || listPreferences.isEmpty()) {
            throw new ServiceException(BusinessErrorList.API_ULTIMATE_PREFERENCES_NOT_FOUND.getError(), HttpStatus.NOT_FOUND);
        }

        final WrapperProvideUltimatePreferencesResponse wrapperUltimatePreferencesResponse = new WrapperProvideUltimatePreferencesResponse();
        wrapperUltimatePreferencesResponse.extendedPreferences = convertListToModel(listPreferences);
        wrapperUltimatePreferencesResponse.retour = true;
        wrapperUltimatePreferencesResponse.query = new Date().getTime() - startTime + " ms";

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Cache-Control", "no-cache, no-store, max-age=0, must-revalidate");

        return new ResponseEntity<>(wrapperUltimatePreferencesResponse, headers, HttpStatus.OK);
    }


    /**
     * Add to model
     *
     * @return service
     * @throws ServiceException
     */
    public List<GetUltimatePreferencesModel> convertListToModel(List<Preference> listPreferences) {

        List<GetUltimatePreferencesModel> listPreferencesModel = new ArrayList<>();

        for (Preference preferences : listPreferences) {
            if (preferences != null && preferences.getPreferenceData() != null) {
                List<PreferenceData> lpd = preferences.getPreferenceData();

                for (PreferenceData pd : lpd) {
                    String errorSubTypeNotSupp = "SUBTYPE not supported for '" + preferences.getType() + "' : '" + pd.getKey() + "'";

                    GetUltimatePreferencesModel gupm = new GetUltimatePreferencesModel();

                    gupm.setValue(pd.getValue());

                    String subType = null;
                    switch (preferences.getType()) {
                        case "UTS":
                            gupm.setPreferenceId("TA_SERVICING");

                            String[][] uts = new String[][]{
                                    {"CUSTOMERDETAILS", "CUSTOMER_DETAILS"},
                                    {"CUSTOMERNOTES", "CUSTOMER_NOTES"},
                                    {"CALLBYNAME", "CALL_BY_NAME"},
                                    {"DISCRETION", "DISCRETION"},
                                    {"PERSONNALASSISTANT", "PERSONNAL_ASSISTANT"}
                            };
                            Map<String, String> utsKeys = Stream.of(uts).collect(Collectors.toMap(data -> data[0], data -> data[1]));

                            subType = utsKeys.getOrDefault(pd.getKey().toUpperCase(), null);
                            break;
                        case "UFB":
                            gupm.setPreferenceId("FOOD_BEVERAGE");
                            String[][] ufb = new String[][]{
                                    {"WELCOMEDRINK", "WELCOME_DRINK"},
                                    {"WINESBEVERAGES", "WINES_BEVERAGES"},
                                    {"SPECIALMEALPAIDMEALS", "SPECIAL_MEAL_PAID_MEALS"},
                                    {"MEALPREFERENCES", "MEAL_PREFERENCES"},
                                    {"FOODDRINKSTOAVOID", "FOOD_DRINKS_TO_AVOID"}
                            };
                            Map<String, String> ufbKeys = Stream.of(ufb).collect(Collectors.toMap(data -> data[0], data -> data[1]));

                            subType = ufbKeys.getOrDefault(pd.getKey().toUpperCase(), null);
                            break;
                        case "UOB":
                            gupm.setPreferenceId("ON_BOARD");

                            String[][] uob = new String[][]{
                                    {"EXTRACOMFORTAMENITIES", "EXTRA_COMFORT_AMENITIES"},
                                    {"ONBOARDSERVICE", "ON_BOARD_SERVICE"},
                                    {"SLEEPRYTHM", "SLEEP_RYTHM"},
                                    {"CUSTOMERDELIGHTER", "CUSTOMER_DELIGHTER"}
                            };
                            Map<String, String> uobKeys = Stream.of(uob).collect(Collectors.toMap(data -> data[0], data -> data[1]));

                            subType = uobKeys.getOrDefault(pd.getKey().toUpperCase(), null);
                            break;
                        case "UST":
                            gupm.setPreferenceId("SEATING");

                            String[][] ust = new String[][]{
                                    {"SEATNUMBERBYAIRCRAFTCABINALLERGYSEAT", "SEAT_NUMBER_BY_AIRCRAFT_CABIN_ALLERGY_SEAT"},
                                    {"ROWAISLEWINDOW", "ROW_AISLE_WINDOW"},
                                    {"CABINSEATINGNIGHTANDDAY", "CABIN_SEATING_NIGHT_AND_DAY"},
                                    {"PAIDSEATUPGRADE", "PAID_SEAT_UPGRADE"}
                            };
                            Map<String, String> ustKeys = Stream.of(ust).collect(Collectors.toMap(data -> data[0], data -> data[1]));

                            subType = ustKeys.getOrDefault(pd.getKey().toUpperCase(), null);
                            break;
                        case "ULO":
                            gupm.setPreferenceId("LOUNGES");

                            String[][] ulo = new String[][]{
                                    {"SPECIFICATTENTION", "SPECIFIC_ATTENTION"},
                                    {"COMFORTAMENITIES", "COMFORT_AMENITIES"},
                                    {"ACTIVITYPATTERN", "ACTIVITY_PATTERN"},
                                    {"PAID_SEAT_UPGRADE", "PAIDSEATUPGRADE"}
                            };
                            Map<String, String> keys = Stream.of(ulo).collect(Collectors.toMap(data -> data[0], data -> data[1]));

                            subType = keys.getOrDefault(pd.getKey().toUpperCase(), null);
                            break;
                        case "UCO":
                            gupm.setPreferenceId("COMMUNICATION");
                            String[][] uco = new String[][]{
                                    {"PREFERREDSPOKENLANGUAGE", "PREFERRED_SPOKEN_LANGUAGE"},
                                    {"TRAVELASSISTANT", "TRAVEL_ASSISTANT"},
                                    {"CHECKINCHANNEL", "CHECK_IN_CHANNEL"},
                                    {"PREFERREDCOMMUNICATIONCHANNEL", "PREFERRED_COMMUNICATION_CHANNEL"}
                            };
                            Map<String, String> ucoKeys = Stream.of(uco).collect(Collectors.toMap(data -> data[0], data -> data[1]));

                            subType = ucoKeys.getOrDefault(pd.getKey().toUpperCase(), null);
                            break;
                        case "UTF":
                            gupm.setPreferenceId("TO_FROM_AIRPORT");

                            String[][] utf = new String[][]{
                                    {"OTHERS", "OTHERS"},
                                    {"TAXI", "TAXI"}
                            };
                            Map<String, String> utfKeys = Stream.of(utf).collect(Collectors.toMap(data -> data[0], data -> data[1]));

                            subType = utfKeys.getOrDefault(pd.getKey().toUpperCase(), null);
                            break;
                        case "UPM":
                            gupm.setPreferenceId("PRESS_MEDIA");

                            String[][] upm = new String[][]{
                                    {"NEWSPAPER", "NEWSPAPER"}
                            };
                            Map<String, String> upmKeys = Stream.of(upm).collect(Collectors.toMap(data -> data[0], data -> data[1]));

                            subType = upmKeys.getOrDefault(pd.getKey().toUpperCase(), null);
                            break;
                        case "UMU":
                            gupm.setPreferenceId("MILES_USAGE");

                            String[][] umu = new String[][]{
                                    {"UPGRADESSERVICESINMILES", "UPGRADES_SERVICES_IN_MILES"},
                                    {"AWARDTICKET", "AWARD_TICKET"},
                                    {"PARTNERS", "PARTNERS"},
                                    {"SHOPPING", "SHOPPING"}
                            };
                            Map<String, String> umuKeys = Stream.of(umu).collect(Collectors.toMap(data -> data[0], data -> data[1]));

                            subType = umuKeys.getOrDefault(pd.getKey().toUpperCase(), null);
                            break;
                        case "ULS":
                            gupm.setPreferenceId("LEISURE");

                            String[][] uls = new String[][]{
                                    {"SPORTACTIVITY", "SPORT_ACTIVITY"},
                                    {"CULTURALACTIVITY", "CULTURAL_ACTIVITY"},
                                    {"MUSICMOVIES", "MUSIC_MOVIES"}
                            };
                            Map<String, String> ulsKeys = Stream.of(uls).collect(Collectors.toMap(data -> data[0], data -> data[1]));

                            subType = ulsKeys.getOrDefault(pd.getKey().toUpperCase(), null);
                            break;
                        case "UFD":
                            gupm.setPreferenceId("FAVORITE_DESTINATION");
                            String[][] ufd = new String[][]{
                                    {"COUNTRIES", "COUNTRIES"},
                                    {"CITY", "CITY"},
                                    {"AIRPORT", "AIRPORT"},
                                    {"HOTELS", "HOTELS"},
                                    {"AWARD", "AWARD"}
                            };
                            Map<String, String> ufdKeys = Stream.of(ufd).collect(Collectors.toMap(data -> data[0], data -> data[1]));

                            subType = ufdKeys.getOrDefault(pd.getKey().toUpperCase(), null);
                            break;
                        default:
                            log.error("TYPE not supported : '" + preferences.getType() + "'");
                            break;
                    }

                    if (subType == null) {
                        log.error(errorSubTypeNotSupp);
                    }
                    gupm.setSubPreferenceId(subType);

                    listPreferencesModel.add(gupm);
                }
            }
        }

        return listPreferencesModel;
    }
}
