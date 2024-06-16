package com.afklm.repind.msv.provide.contact.data.unittests;

import com.afklm.repind.common.entity.contact.Telecoms;
import com.afklm.repind.common.entity.individual.Individu;
import com.afklm.repind.common.enums.MediumStatusEnum;
import com.afklm.repind.common.enums.SignatureEnum;
import com.afklm.repind.common.enums.TerminalTypeEnum;
import com.afklm.repind.common.exception.phone.NormalizedPhoneNumberException;
import com.afklm.repind.msv.provide.contact.data.transform.TelecomsTransform;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.afklm.soa.stubs.r000347.v1.model.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class TelecomsTransformTest {

    @InjectMocks
    private TelecomsTransform telecomsTransform;
    private final String GIN_TELECOM = "110000019801";

    private final String ain = "71999339";
    private final Integer version = 3;
    private final String codeMedium = "D";
    private final String statutMedium = "V";
    private final String numero = "0622666200";
    private final String terminal = "X";
    private final String codeRegion = "06";
    private final String indicatif = "33";
    private final String signatureModification = "M402925";
    private final String siteModification = "QVI";
    private final Date dateModification = new SimpleDateFormat("yyyy-MM-dd").parse("2022-10-04");
    private final String signatureCreation = "Experian";
    private final String siteCreation = "Valbonne";
    private final Date dateCreation = new SimpleDateFormat("yyyy-MM-dd").parse("2017-09-07");
    private final Integer keyTemp = 25357068;
    private final String normNatPhoneNumber = "06 22 66 62 00";
    private final String normNatPhoneNumberClean = "0622666200";
    private final String normInterCountryCode = "33";
    private final String normInterPhoneNumber = "+33622666200";
    private final String normTerminalTypeDetail = "MOBILE";
    private final String isNormalized = "Y" ;

    public TelecomsTransformTest() throws ParseException {
    }

    @Test
    void boToResponseTest() throws ParseException{
        List<TelecomResponse> telecomResponses = telecomsTransform.boToResponse(buildMockedTelecoms());
        List<Signature> signatureData = buildMockedSignatureData();
        TelecomNormalization telecomNormalization = buildMockedTelecomNormalization();
        TelecomFlags telecomFlags = buildMockedTelecomFlags();
        Telecom telecom = buildMockedTelecom();

        TelecomResponse telecomResponse = telecomResponses.get(0);
        assertEquals(signatureData,telecomResponse.getSignature());
        assertEquals(telecomNormalization,telecomResponse.getTelecomNormalization());
        assertEquals(telecomFlags,telecomResponse.getTelecomFlag());
        assertEquals(telecom,telecomResponse.getTelecom());
    }

    @Test
    void setSignatureDataTest() throws ParseException{
        TelecomResponse responseResult = buildMockedTelecomResponses().get(0);
        Telecoms telecoms = buildMockedTelecoms().get(0);
        TelecomResponse response = new TelecomResponse();

        telecomsTransform.setSignatureData(telecoms,response);
        assertEquals(responseResult,response);
    }

    @Test
    void setTelecomNormalizationTest() throws ParseException{
        TelecomResponse responseResult = buildMockedTelecomResponses().get(1);
        Telecoms telecoms = buildMockedTelecoms().get(0);
        TelecomResponse response = new TelecomResponse();

        telecomsTransform.setTelecomNormalization(telecoms,response);
        assertEquals(responseResult,response);
    }

    @Test
    void setTelecomFlagsTest() throws ParseException{
        TelecomResponse responseResult = buildMockedTelecomResponses().get(2);
        Telecoms telecoms = buildMockedTelecoms().get(0);
        TelecomResponse response = new TelecomResponse();

        telecomsTransform.setTelecomFlags(telecoms,response);
        assertEquals(responseResult,response);
    }

    @Test
    void setTelecomTest() throws ParseException{
        TelecomResponse responseResult = buildMockedTelecomResponses().get(3);
        Telecoms telecoms = buildMockedTelecoms().get(0);
        TelecomResponse response = new TelecomResponse();

        telecomsTransform.setTelecom(telecoms,response);
        assertEquals(responseResult,response);
    }

    @Test
    void isInvalidFixTelecomTest() throws ParseException{
        Telecoms telecoms = buildMockedTelecoms().get(0);

        boolean result = telecomsTransform.isInvalidFixTelecom(telecoms);
        assertEquals(false,result);
    }

    @Test
    void isInvalidMobileTelecomTest() throws ParseException{
        Telecoms telecoms = buildMockedTelecoms().get(0);

        boolean result = telecomsTransform.isInvalidMobileTelecom(telecoms);
        assertEquals(false,result);
    }

    @Test
    void isNoValidNormalizedTelecomTest() throws ParseException{
        Telecoms telecoms = buildMockedTelecoms().get(0);

        boolean result = telecomsTransform.isNoValidNormalizedTelecom(telecoms);
        assertEquals(false,result);
    }

    @Test
    void isNormalizedTest() throws ParseException{
        Telecoms telecoms = buildMockedTelecoms().get(0);

        boolean result = telecomsTransform.isNormalized(telecoms);
        assertEquals(true,result);
    }

    @Test
    void computeIsoCountryCode() throws NormalizedPhoneNumberException {
        String countryCode = normInterCountryCode;
        String phoneNumber = normNatPhoneNumberClean;

        String result = telecomsTransform.computeIsoCountryCode(countryCode,phoneNumber);
        assertEquals("FR",result);
    }

    @Test
    void parsePhoneNumberTest() throws NormalizedPhoneNumberException{
        String regionCode = "FR";
        String phoneNumber = normNatPhoneNumberClean;

        PhoneNumber result = telecomsTransform.parsePhoneNumber(regionCode,phoneNumber);

        PhoneNumber expectedResult = new PhoneNumber();
        expectedResult.setCountryCode(Integer.parseInt(indicatif));
        expectedResult.setNationalNumber(622666200);

        assertEquals(expectedResult,result);
    }

    @Test
    void transformPhoneNumberTest() throws ParseException{
        Telecoms telecoms = buildMockedTelecoms().get(0);

        String result = telecomsTransform.transformPhoneNumber(telecoms);
        assertEquals(numero,result);
    }

    @Test
    void normalizeCountryCodeNumericTest() throws ParseException{
        String countryCode = normInterCountryCode;

        String result = telecomsTransform.normalizeCountryCodeNumeric(countryCode);
        assertEquals("FR",result);
    }

    private List<Telecoms> buildMockedTelecoms() throws ParseException{
        List<Telecoms> telecoms = new ArrayList<>();
        Individu individu = new Individu();
        individu.setGin(GIN_TELECOM);

        Telecoms telecom = new Telecoms();
        telecom.setAin(ain);
        telecom.setVersion(version);
        telecom.setCodeMedium(codeMedium);
        telecom.setStatutMedium(statutMedium);
        telecom.setNumero(numero);
        telecom.setTerminal(terminal);
        telecom.setIndicatif(indicatif);
        telecom.setSignatureCreation(signatureCreation);
        telecom.setSiteCreation(siteCreation);
        telecom.setDateCreation(dateCreation);
        telecom.setSignatureModification(signatureModification);
        telecom.setSiteModification(siteModification);
        telecom.setDateModification(dateModification);
        telecom.setIsNormalized(isNormalized);
        telecom.setCodeRegion(codeRegion);
        telecom.setKeyTemp(keyTemp);
        telecom.setNormNatPhoneNumber(normNatPhoneNumber);
        telecom.setNormNatPhoneNumberClean(normNatPhoneNumberClean);
        telecom.setNormInterCountryCode(normInterCountryCode);
        telecom.setNormInterPhoneNumber(normInterPhoneNumber);
        telecom.setNormTerminalTypeDetail(normTerminalTypeDetail);
        telecom.setIndividu(individu);
        telecoms.add(telecom);

        return telecoms;
    }

    private List<Signature> buildMockedSignatureData() throws ParseException{
        List<Signature> signatures = new ArrayList<>();

        Signature signatureCreation1 = new Signature();
        signatureCreation1.setSignature(signatureCreation);
        signatureCreation1.setSignatureSite(siteCreation);
        signatureCreation1.setSignatureType(SignatureEnum.CREATION.toString());
        signatureCreation1.setDate(dateCreation.toInstant().atOffset(ZoneOffset.UTC));

        Signature signatureModification1 = new Signature();
        signatureModification1.setSignature(signatureModification);
        signatureModification1.setSignatureSite(siteModification);
        signatureModification1.setSignatureType(SignatureEnum.MODIFICATION.toString());
        signatureModification1.setDate(dateModification.toInstant().atOffset(ZoneOffset.UTC));

        signatures.add(signatureCreation1);
        signatures.add(signatureModification1);

        return signatures;
    }

    private TelecomNormalization buildMockedTelecomNormalization() throws ParseException{
        TelecomNormalization telecomNormalization = new TelecomNormalization();

        telecomNormalization.setIsoCountryCode("FR");
        telecomNormalization.setInternationalPhoneNumber(normInterPhoneNumber);

        return telecomNormalization;
    }

    private TelecomFlags buildMockedTelecomFlags() throws ParseException{
        TelecomFlags telecomFlags = new TelecomFlags();

        telecomFlags.setFlagInvalidFixTelecom(false);
        telecomFlags.setFlagInvalidMobileTelecom(false);
        telecomFlags.setFlagNoValidNormalizedTelecom(false);

        return telecomFlags;
    }

    private Telecom buildMockedTelecom() throws ParseException{
        Telecom telecom = new Telecom();

        telecom.setVersion(version);
        telecom.setMediumCode(codeMedium);
        telecom.setMediumStatus(statutMedium);
        telecom.setPhoneNumber(numero);

        return telecom;
    }

    private List<TelecomResponse> buildMockedTelecomResponses() throws ParseException{
        List<TelecomResponse> telecomResponses = new ArrayList<>();

        TelecomResponse responseSignatureData = new TelecomResponse();
        responseSignatureData.setSignature(buildMockedSignatureData());
        telecomResponses.add(responseSignatureData);

        TelecomResponse responseTelecomNormalization = new TelecomResponse();
        responseTelecomNormalization.setTelecomNormalization(buildMockedTelecomNormalization());
        telecomResponses.add(responseTelecomNormalization);

        TelecomResponse responseTelecomFlags = new TelecomResponse();
        responseTelecomFlags.setTelecomFlag(buildMockedTelecomFlags());
        telecomResponses.add(responseTelecomFlags);

        TelecomResponse responseTelecom = new TelecomResponse();
        responseTelecom.setTelecom(buildMockedTelecom());
        telecomResponses.add(responseTelecom);

        return telecomResponses;
    }
}
