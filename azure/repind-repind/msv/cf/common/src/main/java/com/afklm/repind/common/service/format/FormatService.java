package com.afklm.repind.common.service.format;

import com.afklm.repind.common.constant.RegexConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class FormatService {
    private static final String REGEX_CLEAN_EMAIL = "(\"?[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-][^\\s]*[a-zA-Z0-9]+\\]?)";
    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^["+ RegexConstant.ALL_CHAR +"]+(?:\\.["+ RegexConstant.ALL_CHAR +"]+)*@["+RegexConstant.ALL_CHAR_NO_SPECIAL+RegexConstant.DASH+"]+(?:\\.["+RegexConstant.ALL_CHAR_NO_SPECIAL+RegexConstant.DASH+"]+)+$", Pattern.CASE_INSENSITIVE);
    private static final int MIN_SIZE = 10;
    private static final int MAX_SIZE = 12;

    public boolean isValidMail(String email) {
        if (StringUtils.isEmpty(email)) {
            return false;
        }

        return VALID_EMAIL_ADDRESS_REGEX.matcher(email).find();
    }

    public boolean isValidCIN(String cin) {
        if (StringUtils.isEmpty(cin)) {
            return false;
        }

        return cin.length() >= MIN_SIZE && cin.length() <= MAX_SIZE;
    }

    public String formatCIN(String cin) {
        return StringUtils.leftPad(cin, 12, "0");
    }

    public String normalizeEmail(String email) {
        if (email != null) {
            email = hardTrim(email);
            email = email.trim(); // Delete starting whitespace and ending
            // whitespace
            email = email.toLowerCase(); // Put email in lower case

            return email;
        }

        return null;
    }
    private static String hardTrim(String toBlank) {
        Pattern pattern = Pattern.compile(REGEX_CLEAN_EMAIL);
        Matcher matcher = pattern.matcher(toBlank);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return toBlank;
        }
    }

    /**
     * Transforms the {@link Long} GIN/CIN to a 12 characters String. It adds 0
     * to reach 12 if necessary.
     *
     * @param gin/cin
     * @return
     */
    public String longIdentifierNumberToString(Long cin) {
        return addingZeroToTheLeft(cin.toString(), 12);
    }

    /**
     * Adding @param Lenght zero to @param pStr
     *
     * @param pStr
     * @param pLenght
     * @return
     */
    private String addingZeroToTheLeft(String pStr, int pLenght) {
        return StringUtils.leftPad(pStr, pLenght, "0");
    }
}
