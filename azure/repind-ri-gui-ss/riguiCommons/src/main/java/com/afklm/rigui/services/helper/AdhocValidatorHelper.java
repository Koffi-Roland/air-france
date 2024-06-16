package com.afklm.rigui.services.helper;

import com.afklm.rigui.util.SicStringUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

@Component
public class AdhocValidatorHelper {
    private static final String ALL_CHAR = "a-zA-Z0-9_!#$%&'*+/=?`{|}~^-";
    private static final String ALL_CHAR_NO_SPECIAL = "a-zA-Z0-9";
    private static final String DASH = "-";
    private static final Pattern LATIN_REGEX = Pattern.compile("\\w+$");
    private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[" + AdhocValidatorHelper.ALL_CHAR + "]+(?:\\.[" + AdhocValidatorHelper.ALL_CHAR + "]+)*@[" + AdhocValidatorHelper.ALL_CHAR_NO_SPECIAL + AdhocValidatorHelper.DASH + "]+(?:\\.[" + AdhocValidatorHelper.ALL_CHAR_NO_SPECIAL + AdhocValidatorHelper.DASH + "]+)+$", Pattern.CASE_INSENSITIVE);
    private static final Integer MIN_SIZE_EMAIL = 6;
    private static final Integer MAX_SIZE_EMAIL = 60;

    public boolean isEmailValid(String emailAddress) {
        if (emailAddress == null) {
            return false; // REQUIRED
        }
        return emailAddress.length() >= MIN_SIZE_EMAIL && emailAddress.length() <= MAX_SIZE_EMAIL && VALID_EMAIL_ADDRESS_REGEX.matcher(emailAddress).find();
    }

    private static final Pattern VALID_GIN_REGEX = Pattern.compile("^\\d{12}$");

    public boolean isGINValid(String gin) {
        if (gin == null) {
            return true;  // OPTIONAL
        }
        return VALID_GIN_REGEX.matcher(gin).find();
    }

    private static final Pattern VALID_CIN_REGEX = Pattern.compile("^\\d{10,12}$");

    public boolean isCINValid(String cin) {
        if (cin == null) {
            return true;  // OPTIONAL
        }
        return VALID_CIN_REGEX.matcher(cin).find();
    }

    private static final Integer MIN_SIZE_FIRSTNAME = 1;
    private static final Integer MAX_SIZE_FIRSTNAME = 25;

    public boolean isFirstnameValid(String firstname) {
        if (firstname == null) {
            return true;  // OPTIONAL
        }
        firstname = firstname.trim();
        return firstname.length() >= MIN_SIZE_FIRSTNAME && firstname.length() <= MAX_SIZE_FIRSTNAME && LATIN_REGEX.matcher(firstname).find();
    }

    private static final Integer MIN_SIZE_SURNAME = 1;
    private static final Integer MAX_SIZE_SURNAME = 35;

    public boolean isSurnameValid(String surname) {
        if (surname == null) {
            return true;  // OPTIONAL
        }
        surname = surname.trim();
        return surname.length() >= MIN_SIZE_SURNAME && surname.length() <= MAX_SIZE_SURNAME && LATIN_REGEX.matcher(surname).find();
    }

    private static final List<String> CIVILITIES = List.of("MR", "MS", "M.", "MX");

    public boolean isCivilityValid(String civility) {
        if (civility == null) {
            return true; // OPTIONAL
        }
        return CIVILITIES.contains(civility);
    }

    public boolean isBirthDateValid(String birthdate) {
        if (birthdate == null) {
            return true; // OPTIONAL
        }
        return SicStringUtils.checkDateStringFormat(birthdate, "dd-MM-yyyy");
    }

    private static final Pattern REGEX_COUNTRY_CODE = Pattern.compile("^[A-Z]{2}$");

    public boolean isCountryCodeValid(String countryCode) {
        if (countryCode == null) {
            return true; // OPTIONAL
        }
        return REGEX_COUNTRY_CODE.matcher(countryCode).find();
    }

    private static final Pattern REGEX_LANGUAGE_CODE = Pattern.compile("^[A-Z]{2}$");

    public boolean isLanguageCodeValid(String languageCode) {
        if (languageCode == null) {
            return false; // REQUIRED
        }
        return REGEX_LANGUAGE_CODE.matcher(languageCode).find();
    }

    private static final List<String> SUBSCRIPTION_TYPE_KL = List.of("KL", "KL_PART");
    private static final List<String> SUBSCRIPTION_TYPE_AF = List.of("AF");

    public boolean isSubscriptionTypeValid(String subscriptionType, String airlineCode) {
        if (subscriptionType == null) {
            return false; // REQUIRED
        }
        if (Objects.equals(airlineCode, "AF")) {
            return SUBSCRIPTION_TYPE_AF.contains(subscriptionType);
        } else if (Objects.equals(airlineCode, "KL")) {
            return SUBSCRIPTION_TYPE_KL.contains(subscriptionType);
        } else {
            return false;
        }
    }

    private static final List<String> DOMAINS = List.of("S");

    public boolean isDomainValid(String domain) {
        if (domain == null) {
            return false; // REQUIRED
        }
        return DOMAINS.contains(domain);
    }

    private static final List<String> GROUP_TYPES = List.of("N");

    public boolean isGroupTypeValid(String groupType) {
        if (groupType == null) {
            return false; // REQUIRED
        }
        return GROUP_TYPES.contains(groupType);
    }

    private static final List<String> STATUSES = List.of("Y", "N");

    public boolean isStatusValid(String status) {
        if (status == null) {
            return false; // REQUIRED
        }
        return STATUSES.contains(status);
    }

    private static final Pattern REGEX_SOURCE = Pattern.compile("^[0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_]{1,16}$");

    public boolean isSourceValid(String source) {
        if (source == null) {
            return false; // REQUIRED
        }
        return REGEX_SOURCE.matcher(source).find();
    }

    public boolean isDateOfConsentValid(String dateOfConsent) {
        if (dateOfConsent == null) {
            return false; // REQUIRED
        }
        return SicStringUtils.checkDateStringFormat(dateOfConsent, "dd-MM-yyyy");
    }

    private static final Pattern REGEX_PREFERRED_DEPARTURE_AIRPORT = Pattern.compile("^[A-Z]{3}$");

    public boolean isPreferredDepartureAirport(String preferredDepartureAirport) {
        if (preferredDepartureAirport == null) {
            return true;  // OPTIONAL
        }
        return REGEX_PREFERRED_DEPARTURE_AIRPORT.matcher(preferredDepartureAirport).find();
    }
}
