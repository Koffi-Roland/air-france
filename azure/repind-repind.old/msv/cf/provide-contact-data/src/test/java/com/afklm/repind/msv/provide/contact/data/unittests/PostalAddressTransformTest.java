package com.afklm.repind.msv.provide.contact.data.unittests;

import com.afklm.repind.common.entity.contact.PostalAddress;
import com.afklm.repind.common.entity.contact.UsageMedium;
import com.afklm.repind.common.entity.individual.Individu;
import com.afklm.repind.common.enums.SignatureEnum;
import com.afklm.repind.common.enums.YesNoEnum;
import com.afklm.repind.msv.provide.contact.data.transform.PostalAddressTransform;
import com.afklm.soa.stubs.r000347.v1.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneOffset;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class PostalAddressTransformTest {

    @Mock
    PostalAddress postalAddress;
    @InjectMocks
    PostalAddressTransform postalAddressTransform;

    private final String GIN_POSTAL_ADDRESS = "800018352323";

    private final String ain = "40915315";
    private final Integer version = 1;
    private final String noEtRue = "SAMPSOUDOS 8";
    private final String codePostal = "14565";
    private final String ville = "AGIOS STEFANOS";
    private final String codePays = "GR";
    private final String codeMedium = "D";
    private final String statutMedium = "V";
    private final String siteModification = "ISI";
    private final String signatureModification = "Ajout Usage";
    private final Date dateModification = new SimpleDateFormat("yyyy-MM-dd").parse("2007-03-16");
    private final String siteCreation = "WEB/S08920";
    private final String signatureCreation = "WEB";
    private final Date dateCreation = new SimpleDateFormat("yyyy-MM-dd").parse("2004-09-01");
    private final String forcage = "O";
    private final String indAdr = "SANPSUDO";
    private final Integer codeErr = 0;

    private final String rin = "85200511";
    private final String codeApplication = "ISI";
    private final String ainAdr = "40915315";
    private final Integer num = 1;

    public PostalAddressTransformTest() throws ParseException {
    }

    @Test
    void boToResponseTest() throws ParseException{
        List<PostalAddressResponse> postalAddressResponse = postalAddressTransform.boToResponse(buildMockedPostalAddress());
        PostalAddressContent postalAddressContent = buildMockedPostalAddressContent();
        PostalAddressProperties postalAddressProperties = buildMockedPostalAddressProperties();
        List<Signature> signature = buildMockedSignatures();
        List<UsageAddress> usageAddress = buildMockedUsageAddress();

        PostalAddressResponse postalAddressResponse1 = postalAddressResponse.get(0);
        assertEquals(postalAddressContent,postalAddressResponse1.getPostalAddressContent());
        assertEquals(postalAddressProperties,postalAddressResponse1.getPostalAddressProperties());
        assertEquals(signature,postalAddressResponse1.getSignature());
        assertEquals(usageAddress,postalAddressResponse1.getUsageAddress());
    }

    @Test
    void setPostalAddressContentTest() throws ParseException{
        PostalAddressResponse postalAddressResponseResult = buildMockedPostalAddressResponses().get(0);
        PostalAddress postalAddress = buildMockedPostalAddress().get(0);
        PostalAddressResponse postalAddressResponse = new PostalAddressResponse();

        postalAddressTransform.setPostalAddressContent(postalAddress,postalAddressResponse);
        assertEquals(postalAddressResponseResult,postalAddressResponse);
    }

    @Test
    void setPostalAddressPropertiesTest() throws ParseException{
        PostalAddressResponse postalAddressResponseResult = buildMockedPostalAddressResponses().get(1);
        PostalAddress postalAddress = buildMockedPostalAddress().get(0);
        PostalAddressResponse postalAddressResponse = new PostalAddressResponse();

        postalAddressTransform.setPostalAddressProperties(postalAddress,postalAddressResponse);
        assertEquals(postalAddressResponseResult,postalAddressResponse);
    }

    @Test
    void setPostalAddressSignatureTest() throws ParseException{
        PostalAddressResponse postalAddressResponseResult = buildMockedPostalAddressResponses().get(2);
        PostalAddress postalAddress = buildMockedPostalAddress().get(0);
        PostalAddressResponse postalAddressResponse = new PostalAddressResponse();

        postalAddressTransform.setPostalAddressSignature(postalAddress,postalAddressResponse);
        assertEquals(postalAddressResponseResult,postalAddressResponse);
    }

    @Test
    void setUsageAddressTest() throws ParseException{
        PostalAddressResponse postalAddressResponseResult = buildMockedPostalAddressResponses().get(3);
        PostalAddress postalAddress1 = buildMockedPostalAddress().get(0);
        PostalAddressResponse postalAddressResponse = new PostalAddressResponse();

        postalAddressTransform.setUsageAddress(postalAddress1,postalAddressResponse);
        assertEquals(postalAddressResponseResult,postalAddressResponse);
    }

    private List<PostalAddress> buildMockedPostalAddress() throws ParseException{
        List<PostalAddress> postalAddresses = new ArrayList<>();
        Individu individu = new Individu();
        individu.setGin(GIN_POSTAL_ADDRESS);

        PostalAddress postalAddress = new PostalAddress();
        postalAddress.setAin(ain);
        postalAddress.setVersion(version);
        postalAddress.setNoEtRue(noEtRue);
        postalAddress.setCodePostal(codePostal);
        postalAddress.setVille(ville);
        postalAddress.setCodePays(codePays);
        postalAddress.setCodeMedium(codeMedium);
        postalAddress.setStatutMedium(statutMedium);
        postalAddress.setSiteModification(siteModification);
        postalAddress.setSignatureModification(signatureModification);
        postalAddress.setDateModification(dateModification);
        postalAddress.setSiteCreation(siteCreation);
        postalAddress.setSignatureCreation(signatureCreation);
        postalAddress.setDateCreation(dateCreation);
        postalAddress.setForcage(forcage);
        postalAddress.setIndAdr(indAdr);
        postalAddress.setCodErr(codeErr);
        postalAddress.setUsageMedium(buildMockedUsageMediums());
        postalAddress.setIndividu(individu);
        postalAddresses.add(postalAddress);

        return postalAddresses;
    }

    private List<UsageAddress> buildMockedUsageAddress() throws ParseException{
        List<UsageAddress> usageAddresses = new ArrayList<>();

        UsageAddress usageAddress = new UsageAddress();
        usageAddress.setUsageNumber(num.toString());
        usageAddress.setApplicationCode(codeApplication);
        usageAddresses.add(usageAddress);

        return usageAddresses;
    }

    private List<Signature> buildMockedSignatures() throws ParseException{
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

    private PostalAddressProperties buildMockedPostalAddressProperties() throws ParseException{
        PostalAddressProperties postalAddressProperties = new PostalAddressProperties();

        postalAddressProperties.setVersion(version.toString());
        postalAddressProperties.setMediumCode(codeMedium);
        postalAddressProperties.setMediumStatus(statutMedium);
        postalAddressProperties.setIndicAdrNorm(YesNoEnum.getValue(forcage).toBoolean());

        return postalAddressProperties;
    }

    private PostalAddressContent buildMockedPostalAddressContent() throws ParseException{
        PostalAddressContent postalAddressContent = new PostalAddressContent();

        postalAddressContent.setCity(ville);
        postalAddressContent.setZipCode(codePostal);
        postalAddressContent.setCountryCode(codePays);
        postalAddressContent.setNumberAndStreet(noEtRue);

        return postalAddressContent;
    }

    private List<PostalAddressResponse> buildMockedPostalAddressResponses() throws ParseException{
        List<PostalAddressResponse> postalAddressResponses = new ArrayList<>();

        PostalAddressResponse responsePostalAddressContent = new PostalAddressResponse();
        responsePostalAddressContent.setPostalAddressContent(buildMockedPostalAddressContent());
        postalAddressResponses.add(responsePostalAddressContent);

        PostalAddressResponse responsePostalAddressProperties = new PostalAddressResponse();
        responsePostalAddressProperties.setPostalAddressProperties(buildMockedPostalAddressProperties());
        postalAddressResponses.add(responsePostalAddressProperties);

        PostalAddressResponse responseSignature = new PostalAddressResponse();
        responseSignature.setSignature(buildMockedSignatures());
        postalAddressResponses.add(responseSignature);

        PostalAddressResponse responseUsageAddress = new PostalAddressResponse();
        responseUsageAddress.setUsageAddress(buildMockedUsageAddress());
        postalAddressResponses.add(responseUsageAddress);

        return postalAddressResponses;
    }

    private Set<UsageMedium> buildMockedUsageMediums() throws ParseException{
        Set<UsageMedium> usageMedia = new HashSet<UsageMedium>();

        UsageMedium usageMedium = new UsageMedium();
        usageMedium.setRin(rin);
        usageMedium.setCodeApplication(codeApplication);
        usageMedium.setAinAdr(ainAdr);
        usageMedium.setNum(num);
        usageMedia.add(usageMedium);

        return usageMedia;
    }
}
