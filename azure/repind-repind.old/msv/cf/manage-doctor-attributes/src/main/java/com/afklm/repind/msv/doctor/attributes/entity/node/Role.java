package com.afklm.repind.msv.doctor.attributes.entity.node;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.neo4j.ogm.annotation.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@NodeEntity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    private String roleId;

    @Relationship(type="SPEAK")
    private Set<Language> languages = new HashSet<>();

    @Relationship(type="EXPERT", direction = Relationship.Direction.OUTGOING)
    private Speciality speciality;

    @Relationship(type="APPROVAL_BY", direction = Relationship.Direction.OUTGOING)
    private AirLineCode airLineCode;

    @Property
    private String gin;

    @Property
    private Date endDateRole;

    @Property
    private String doctorStatus;

    @Property
    private String doctorId;

    @Property
    private Date optOut;

    @Property
    private Date lastUpdate;

    @Property
    private String signatureSourceCreation;

    @Property
    private String siteCreation;

    @Property
    private Date signatureDateCreation;

    @Property
    private String signatureSourceModification;

    @Property
    private String siteModification;

    @Property
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
