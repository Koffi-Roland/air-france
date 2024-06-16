package com.afklm.repind.msv.doctor.attributes.entity;

import com.afklm.repind.msv.doctor.attributes.model.AirLineCode;
import com.afklm.repind.msv.doctor.attributes.model.Language;
import com.afklm.repind.msv.doctor.attributes.model.Speciality;
import com.azure.spring.data.cosmos.core.mapping.Container;
import com.azure.spring.data.cosmos.core.mapping.GeneratedValue;
import com.azure.spring.data.cosmos.core.mapping.PartitionKey;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Container(containerName = "doctors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue
    @PartitionKey
    private String id;

    @JsonProperty("roleId")
    private String roleId;

    @JsonProperty("languages")
    private Set<Language> languages = new HashSet<>();

    @JsonProperty("speciality")
    private Speciality speciality;

    @JsonProperty("approvedBy")
    private AirLineCode airLineCode;

    @JsonProperty("gin")
    private String gin;

    @JsonProperty("endDateRole")
    private Date endDateRole;

    @JsonProperty("doctorStatus")
    private String doctorStatus;

    @JsonProperty("doctorId")
    private String doctorId;

    @JsonProperty("optOut")
    private Date optOut;

    @JsonProperty("lastUpdate")
    private Date lastUpdate;

    @JsonProperty("signatureSourceCreation")
    private String signatureSourceCreation;

    @JsonProperty("siteCreation")
    private String siteCreation;

    @JsonProperty("signatureDateCreation")
    private Date signatureDateCreation;

    @JsonProperty("signatureSourceModification")
    private String signatureSourceModification;

    @JsonProperty("siteModification")
    private String siteModification;

    @JsonProperty("signatureDateModification")
    private Date signatureDateModification;


    public void addLanguages(Collection<Language> iLanguages){
        if(iLanguages != null){
            languages.addAll(iLanguages);
        }
    }

    public void addLanguage(Language iLanguage){
        if(iLanguage != null){
            languages.add(iLanguage);
        }
    }
}
