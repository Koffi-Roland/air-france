package com.afklm.repind.msv.provide.contact.data.transform;

import com.afklm.repind.common.entity.contact.Telecoms;
import com.afklm.repind.common.enums.MediumStatusEnum;
import com.afklm.repind.common.enums.SignatureEnum;
import com.afklm.repind.common.enums.TerminalTypeEnum;
import com.afklm.repind.common.exception.phone.NormalizedPhoneNumberException;
import com.afklm.repind.common.exception.phone.InvalidCountryCodeException;
import com.afklm.repind.common.exception.phone.TooShortPhoneNumberException;
import com.afklm.repind.common.exception.phone.TooLongPhoneNumberException;
import com.afklm.repind.common.exception.phone.InvalidPhoneNumberException;
import com.afklm.soa.stubs.r000347.v1.model.TelecomResponse;
import com.afklm.soa.stubs.r000347.v1.model.Signature;
import com.afklm.soa.stubs.r000347.v1.model.TelecomNormalization;
import com.afklm.soa.stubs.r000347.v1.model.Telecom;
import com.afklm.soa.stubs.r000347.v1.model.TelecomFlags;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class TelecomsTransform {

    public List<TelecomResponse> boToResponse(List<Telecoms> telecomsBo) {
        List<TelecomResponse> telecomResponses = new ArrayList<>();

        if(telecomsBo != null) {
            for(Telecoms telecom : telecomsBo) {
                TelecomResponse response = new TelecomResponse();
                setSignatureData(telecom, response);
                setTelecomNormalization(telecom, response);
                setTelecomFlags(telecom, response);
                setTelecom(telecom, response);
                telecomResponses.add(response);
            }
        }

        return telecomResponses;
    }

    public void setSignatureData(Telecoms telecomBo, TelecomResponse telecomResponse) {
        List<Signature> signatures = new ArrayList<>();

        Signature signatureCreation = new Signature();
        signatureCreation.setDate(telecomBo.getDateCreation().toInstant().atOffset(ZoneOffset.UTC));
        signatureCreation.setSignature(telecomBo.getSignatureCreation());
        signatureCreation.setSignatureSite(telecomBo.getSiteCreation());
        signatureCreation.setSignatureType(SignatureEnum.CREATION.toString());
        signatures.add(signatureCreation);

        Signature signatureModification = new Signature();
        signatureModification.setDate(telecomBo.getDateModification().toInstant().atOffset(ZoneOffset.UTC));
        signatureModification.setSignature(telecomBo.getSignatureModification());
        signatureModification.setSignatureSite(telecomBo.getSiteModification());
        signatureModification.setSignatureType(SignatureEnum.MODIFICATION.toString());
        signatures.add(signatureModification);

        telecomResponse.setSignature(signatures);
    }

    public void setTelecomNormalization(Telecoms telecomBO, TelecomResponse telecomResponse)  {
        TelecomNormalization telecomNormalization = new TelecomNormalization();
        telecomNormalization.setInternationalPhoneNumber(telecomBO.getNormInterPhoneNumber());

        if(isNormalized(telecomBO)) {
            String countryCode = telecomBO.getNormInterCountryCode();
            String phoneNumber = telecomBO.getNormNatPhoneNumberClean();
            String isoCountryCode = "";

            try {
                isoCountryCode = computeIsoCountryCode(countryCode, phoneNumber);
                if (countryCode != null && "262".equals(countryCode)) {
                    isoCountryCode = "RE";
                }

                telecomNormalization.setIsoCountryCode(isoCountryCode);
            } catch (Exception e) {
                log.info(String.valueOf(e));
                log.error("Can't compute iso coutry code " + isoCountryCode + " for telecoms " + telecomBO.getAin());
            }
        }

        telecomResponse.setTelecomNormalization(telecomNormalization);
    }

    public void setTelecomFlags(Telecoms telecoms, TelecomResponse telecomResponse) {
        TelecomFlags telecomFlags = new TelecomFlags();

        telecomFlags.setFlagInvalidFixTelecom(isInvalidFixTelecom(telecoms));

        telecomFlags.setFlagInvalidMobileTelecom(isInvalidMobileTelecom(telecoms));

        telecomFlags.setFlagNoValidNormalizedTelecom(isNoValidNormalizedTelecom(telecoms));

        telecomResponse.setTelecomFlag(telecomFlags);
    }

    public void setTelecom(Telecoms telecoms, TelecomResponse telecomResponse) {
        Telecom ws = new Telecom();
        ws.setCountryCode(telecoms.getCountryCode());
        ws.setMediumStatus(telecoms.getStatutMedium());
        ws.setMediumCode(telecoms.getCodeMedium());
        ws.setPhoneNumber(transformPhoneNumber(telecoms));
        ws.setVersion(telecoms.getVersion());
        telecomResponse.setTelecom(ws);
    }

    public boolean isInvalidFixTelecom(Telecoms telecoms) {
        return MediumStatusEnum.INVALID.toString().equals(telecoms.getStatutMedium())
                && TerminalTypeEnum.FIX.toString().equals(telecoms.getTerminal());
    }

    public boolean isInvalidMobileTelecom(Telecoms telecoms) {
        return MediumStatusEnum.INVALID.toString().equals(telecoms.getStatutMedium())
                && TerminalTypeEnum.MOBILE.toString().equals(telecoms.getTerminal());
    }

    public boolean isNoValidNormalizedTelecom(Telecoms telecoms) {
        boolean noValid = true;
        if (MediumStatusEnum.VALID.toString().equals(telecoms.getStatutMedium())) {
            noValid &= MediumStatusEnum.VALID.toString().equals(telecoms.getStatutMedium());
            noValid &= StringUtils.isEmpty(telecoms.getNormInterCountryCode());
            noValid &= StringUtils.isEmpty(telecoms.getNormInterPhoneNumber());
            noValid &= StringUtils.isEmpty(telecoms.getNormNatPhoneNumber());
            noValid &= StringUtils.isEmpty(telecoms.getNormTerminalTypeDetail());
        }

        return noValid;
    }

    public boolean isNormalized(Telecoms telecom) {
        boolean isNormalized = true;
        isNormalized &= StringUtils.isNotEmpty(telecom.getNormInterCountryCode());
        isNormalized &= StringUtils.isNotEmpty(telecom.getNormInterPhoneNumber());
        isNormalized &= StringUtils.isNotEmpty(telecom.getNormNatPhoneNumber());
        isNormalized &= StringUtils.isNotEmpty(telecom.getNormTerminalTypeDetail());
        return isNormalized;
    }

    public String computeIsoCountryCode(String countryCode, String phoneNumber) throws NormalizedPhoneNumberException {
        if (StringUtils.isEmpty(countryCode) && StringUtils.isEmpty(phoneNumber)) {
            return null;
        } else {
            String regionCode = normalizeCountryCodeNumeric(countryCode);
            PhoneNumber phoneNumberResult = parsePhoneNumber(regionCode, phoneNumber);
            PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
            return phoneNumberUtil.getRegionCodeForNumber(phoneNumberResult);
        }
    }

    public PhoneNumber parsePhoneNumber(String regionCode, String phoneNumber) throws NormalizedPhoneNumberException {
        PhoneNumber phoneNumberResult = null;

        try {
            PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
            phoneNumberResult = phoneNumberUtil.parse(phoneNumber, regionCode);
            return phoneNumberResult;
        } catch (NumberParseException var5) {
            log.info(String.valueOf(var5));
            switch(var5.getErrorType()) {
                case INVALID_COUNTRY_CODE:
                    throw new InvalidCountryCodeException(regionCode);
                case NOT_A_NUMBER:
                case TOO_SHORT_AFTER_IDD:
                    throw new InvalidPhoneNumberException(phoneNumber);
                case TOO_LONG:
                    throw new TooLongPhoneNumberException(phoneNumber);
                case TOO_SHORT_NSN:
                    throw new TooShortPhoneNumberException(phoneNumber);
                default:
                    throw new NormalizedPhoneNumberException("Unexpected error type: " + var5.getErrorType());
            }
        }
    }

    public String transformPhoneNumber(Telecoms telecoms) {
        String phoneNumber = "";
        if(StringUtils.isNotEmpty(telecoms.getNormNatPhoneNumberClean())) {
            phoneNumber = telecoms.getNormNatPhoneNumberClean();
        } else {
            phoneNumber = telecoms.getNumero();

            if (StringUtils.isNotEmpty(telecoms.getCodeRegion())) {
                phoneNumber = telecoms.getCodeRegion() + phoneNumber;
            }
        }
        return phoneNumber;
    }

    public String normalizeCountryCodeNumeric(String countryCode) {
        int countryCodeNumber = Integer.parseInt(countryCode);
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        return phoneNumberUtil.getRegionCodeForCountryCode(countryCodeNumber);
    }
}
