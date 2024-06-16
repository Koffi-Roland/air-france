package com.afklm.repind.msv.provide.contact.data.transform;

import com.afklm.repind.common.entity.contact.PostalAddress;
import com.afklm.repind.common.entity.contact.UsageMedium;
import com.afklm.repind.common.enums.YesNoEnum;
import com.afklm.repind.common.enums.SignatureEnum;
import org.apache.commons.lang3.StringUtils;
import com.afklm.soa.stubs.r000347.v1.model.PostalAddressResponse;
import com.afklm.soa.stubs.r000347.v1.model.PostalAddressContent;
import com.afklm.soa.stubs.r000347.v1.model.PostalAddressProperties;
import com.afklm.soa.stubs.r000347.v1.model.Signature;
import com.afklm.soa.stubs.r000347.v1.model.UsageAddress;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class PostalAddressTransform {

    public List<PostalAddressResponse> boToResponse(List<PostalAddress> postalAddressesBo) {
        List<PostalAddressResponse> postalAddressResponse  = new ArrayList<>();

        if(!postalAddressesBo.isEmpty()) {
            for(PostalAddress address : postalAddressesBo) {
                PostalAddressResponse response = new PostalAddressResponse();
                setPostalAddressContent(address, response);
                setPostalAddressProperties(address, response);
                setPostalAddressSignature(address, response);
                setUsageAddress(address, response);
                postalAddressResponse.add(response);
            }
        }

        return postalAddressResponse;
    }

    public void setPostalAddressContent(PostalAddress address, PostalAddressResponse response) {
        PostalAddressContent content = new PostalAddressContent();
        content.setCity(address.getVille());
        content.setZipCode(address.getCodePostal());
        content.setCountryCode(address.getCodePays());
        content.setDistrict(address.getLocalite());
        content.setStateCode(address.getCodeProvince());
        content.setAdditionalInformation(address.getComplementAdresse());
        content.setCorporateName(address.getRaisonSociale());
        content.setNumberAndStreet(address.getNoEtRue());
        response.setPostalAddressContent(content);
    }

    public void setPostalAddressProperties(PostalAddress address, PostalAddressResponse response) {
        PostalAddressProperties properties = new PostalAddressProperties();

        if(StringUtils.isNotEmpty(address.getForcage())) {
            properties.setIndicAdrNorm(YesNoEnum.getValue(address.getForcage()).toBoolean());
        }

        properties.setMediumCode(address.getCodeMedium());
        properties.setMediumStatus(address.getStatutMedium());
        properties.setVersion(address.getVersion().toString());

        response.setPostalAddressProperties(properties);
    }

    public void setPostalAddressSignature(PostalAddress address, PostalAddressResponse response) {
        List<Signature> signatures = new ArrayList<>();

        Signature signatureCreation = new Signature();
        signatureCreation.setDate(address.getDateCreation().toInstant().atOffset(ZoneOffset.UTC));
        signatureCreation.setSignature(address.getSignatureCreation());
        signatureCreation.setSignatureSite(address.getSiteCreation());
        signatureCreation.setSignatureType(SignatureEnum.CREATION.toString());
        signatures.add(signatureCreation);

        Signature signatureModification = new Signature();
        signatureModification.setDate(address.getDateModification().toInstant().atOffset(ZoneOffset.UTC));
        signatureModification.setSignature(address.getSignatureModification());
        signatureModification.setSignatureSite(address.getSiteModification());
        signatureModification.setSignatureType(SignatureEnum.MODIFICATION.toString());
        signatures.add(signatureModification);

        response.setSignature(signatures);
    }

    public void setUsageAddress(PostalAddress address, PostalAddressResponse response) {
        Set<UsageMedium> usages = address.getUsageMedium();
        List<UsageAddress> usageAddresses = new ArrayList<>();

        for(UsageMedium usage : usages) {
            UsageAddress usageAddress = new UsageAddress();

            usageAddress.setAddressRoleCode(usage.getRole1());
            usageAddress.setApplicationCode(usage.getCodeApplication());
            usageAddress.setUsageNumber(usage.getNum().toString());

            usageAddresses.add(usageAddress);
        }

        response.setUsageAddress(usageAddresses);
    }

}
