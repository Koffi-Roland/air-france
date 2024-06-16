package com.afklm.repind.msv.manage.external.identifier.utils;

import com.afklm.repind.common.entity.identifier.ExternalIdentifier;
import com.afklm.repind.common.entity.identifier.ExternalIdentifierDataEntity;
import com.afklm.repind.msv.manage.external.identifier.models.SignatureElement;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BuildData {
    public static List<ExternalIdentifier> buildExternalIdentifierEntityList() {
        ExternalIdentifier externalIdentifierEntity = new ExternalIdentifier();

        Date date = Date.from(LocalDate.of(2013, 12, 6).atStartOfDay(ZoneId.systemDefault()).toInstant());

        externalIdentifierEntity.setIdentifierId(11656L);
        externalIdentifierEntity.setGin("012345678912");
        externalIdentifierEntity.setType("PNM_ID");
        externalIdentifierEntity.setIdentifier("cust-le");
        externalIdentifierEntity.setDateCreation(date);
        externalIdentifierEntity.setDateModification(date);
        externalIdentifierEntity.setSignatureCreation("RI_TEAM");
        externalIdentifierEntity.setSignatureModification("RI_TEAM");
        externalIdentifierEntity.setSiteCreation("QVI");
        externalIdentifierEntity.setSiteModification("QVI");

        List<ExternalIdentifier> res = new ArrayList<>();
        res.add(externalIdentifierEntity);
        return res;
    }

    public static List<ExternalIdentifierDataEntity> buildListExternalIdentifierDataEntity() {
        ExternalIdentifierDataEntity externalIdentifierDataEntity = new ExternalIdentifierDataEntity();

        Date date = Date.from(LocalDate.of(2013, 12, 6).atStartOfDay(ZoneId.systemDefault()).toInstant());

        externalIdentifierDataEntity.setIdentifierDataId(1L);
        externalIdentifierDataEntity.setIdentifierId(11656L);
        externalIdentifierDataEntity.setKey("key");
        externalIdentifierDataEntity.setValue("value");
        externalIdentifierDataEntity.setDateCreation(date);
        externalIdentifierDataEntity.setDateModification(date);
        externalIdentifierDataEntity.setSignatureCreation("RI_TEAM");
        externalIdentifierDataEntity.setSignatureModification("RI_TEAM");
        externalIdentifierDataEntity.setSiteCreation("QVI");
        externalIdentifierDataEntity.setSiteModification("QVI");

        List<ExternalIdentifierDataEntity> res = new ArrayList<>();
        res.add(externalIdentifierDataEntity);
        return res;
    }

    public static SignatureElement buildSignatureElement() {
        Date date = Date.from(LocalDate.of(2013, 12, 6).atStartOfDay(ZoneId.systemDefault()).toInstant());
        return new SignatureElement("RI_TEAM", "QVI", date, "RI_TEAM", "QVI", date);
    }
}
