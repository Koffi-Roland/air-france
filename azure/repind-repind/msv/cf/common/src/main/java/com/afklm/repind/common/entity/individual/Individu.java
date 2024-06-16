package com.afklm.repind.common.entity.individual;

import com.afklm.repind.common.entity.contact.EmailEntity;
import com.afklm.repind.common.entity.contact.PostalAddress;
import com.afklm.repind.common.entity.contact.Telecoms;
import com.afklm.repind.common.entity.identifier.ExternalIdentifier;
import com.afklm.repind.common.entity.preferences.CommunicationPreferencesEntity;
import com.afklm.repind.common.entity.preferences.PreferenceEntity;
import com.afklm.repind.common.entity.role.RoleContract;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * <p>
 * Title : Individu.java
 * </p>
 * BO Individu
 * <p>
 * Copyright : Copyright (c) 2009
 * </p>
 * <p>
 * Company : AIRFRANCE
 * </p>
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "INDIVIDUS_ALL")
public class Individu implements Serializable {
    /**
     * Determines if a de-serialized file is compatible with this class. Maintainers must change this value if and only if the new version of this class is not compatible with old
     * versions. See Sun docs for <a href=http://java.sun.com/j2se/1.5.0/docs/guide/serialization /serialization/spec/class.html#4100> details. </a> Not necessary to include in
     * first version of the class, but included here as a reminder of its importance.
     */
    private static final long serialVersionUID = 1L;
    private static final Log log = LogFactory.getLog(Individu.class);

    @Id
    @Column(name = "SGIN", length = 12, nullable = false, unique = true, updatable = false)
    private String gin;

    @Version
    @Column(name = "IVERSION", nullable = false)
    private Integer version;

    @Column(name = "SCIVILITE", length = 4, nullable = false)
    private String civilite;

    @Column(name = "SMOT_DE_PASSE", length = 10)
    private String motDePasse;

    @Column(name = "SNOM", length = 35)
    private String nom;

    @Column(name = "SALIAS", length = 35)
    private String alias;

    @Column(name = "SPRENOM", length = 25)
    private String prenom;

    @Column(name = "SSECOND_PRENOM", length = 25)
    private String secondPrenom;

    @Column(name = "SALIAS_PRENOM", length = 25)
    private String aliasPrenom;

    @Column(name = "SSEXE", length = 1, nullable = false)
    private String sexe;

    @Column(name = "SIDENTIFIANT_PERSONNEL", length = 16)
    private String identifiantPersonnel;

    @Column(name = "DDATE_NAISSANCE")
    private Date dateNaissance;

    @Column(name = "SSTATUT_INDIVIDU", length = 1)
    private String statutIndividu;

    @Column(name = "SCODE_TITRE", length = 3)
    private String codeTitre;

    @Column(name = "SNATIONALITE", length = 2)
    private String nationalite;

    @Column(name = "SAUTRE_NATIONALITE", length = 2)
    private String autreNationalite;

    @Column(name = "SNON_FUSIONNABLE", length = 1, nullable = false)
    private String nonFusionnable;

    @Column(name = "SSITE_CREATION", length = 10, nullable = false)
    private String siteCreation;

    @Column(name = "SSIGNATURE_CREATION", length = 16, nullable = false)
    private String signatureCreation;

    @Column(name = "DDATE_CREATION", nullable = false)
    private Date dateCreation;

    @Column(name = "SSITE_MODIFICATION", length = 10)
    private String siteModification;

    @Column(name = "SSIGNATURE_MODIFICATION", length = 16)
    private String signatureModification;

    @Column(name = "DDATE_MODIFICATION")
    private Date dateModification;

    @Column(name = "SSITE_FRAUDEUR", length = 10)
    private String siteFraudeur;

    @Column(name = "SSIGNATURE_FRAUDEUR", length = 16)
    private String signatureFraudeur;

    @Column(name = "DDATE_MOD_FRAUDEUR")
    private Date dateModifFraudeur;

    @Column(name = "SSITE_MOT_PASSE", length = 10)
    private String siteMotDePasse;

    @Column(name = "SSIGNATURE_MOT_PASSE", length = 16)
    private String signatureMotDePasse;

    @Column(name = "DDATE_MOD_MOT_DE_PASSE")
    private Date dateModifMotDePasse;

    @Column(name = "SFRAUDEUR_CARTE_BANCAIRE", length = 1)
    private String fraudeurCarteBancaire;

    @Column(name = "STIER_UTILISE_COMME_PIEGE", length = 1)
    private String tierUtiliseCommePiege;

    @Column(name = "SALIAS_NOM1", length = 35)
    private String aliasNom1;

    @Column(name = "SALIAS_NOM2", length = 35)
    private String aliasNom2;

    @Column(name = "SALIAS_PRE1", length = 25)
    private String aliasPrenom1;

    @Column(name = "SALIAS_PRE2", length = 25)
    private String aliasPrenom2;

    @Column(name = "SALIAS_CIV1", length = 4)
    private String aliasCivilite1;

    @Column(name = "SALIAS_CIV2", length = 4)
    private String aliasCivilite2;

    @Column(name = "SINDICT_NP", length = 40)
    private String indicNomPrenom;

    @Column(name = "SINDICT_NOM", length = 35)
    private String indicNom;

    @Column(name = "SINDCONS", length = 30)
    private String indcons;

    @Column(name = "SGIN_FUSION", length = 12)
    private String ginFusion;

    @Column(name = "DDATE_FUSION")
    private Date dateFusion;

    @Column(name = "SPROV_AMEX", length = 1)
    private String provAmex;

    @Column(name = "SCIE_GEST", length = 3)
    private String cieGest;

    @Column(name = "SNOM_TYPO", length = 140)
    private String nomTypo1;

    @Column(name = "SPRENOM_TYPO", length = 140)
    private String prenomTypo1;

    @Column(name = "SNOM_TYPO2", length = 140)
    private String nomTypo2;

    @Column(name = "SPRENOM_TYPO2", length = 140)
    private String prenomTypo2;

    @Column(name = "STYPE", length = 1, nullable = false)
    private String type = "I";

    @OneToMany(targetEntity = PostalAddress.class, mappedBy = "individu", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<PostalAddress> postalAddresses;

    @OneToMany(targetEntity = Telecoms.class, mappedBy = "individu", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Telecoms> telecoms;

    @OneToMany(targetEntity = EmailEntity.class, mappedBy = "individu", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<EmailEntity> emails;

    @OneToMany(targetEntity = CommunicationPreferencesEntity.class, mappedBy = "individu", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<CommunicationPreferencesEntity> communicationPreferences;


    @OneToMany(targetEntity = PreferenceEntity.class, mappedBy = "individu", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<PreferenceEntity> preferences;

    @OneToMany(targetEntity = DelegationData.class, mappedBy = "delegator", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<DelegationData> delegateList;

    @OneToMany(targetEntity = DelegationData.class, mappedBy = "delegate", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<DelegationData> delegatorList;

    @OneToMany(targetEntity = ExternalIdentifier.class, mappedBy = "individu", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<ExternalIdentifier> externalIdentifiers;

    @OneToMany(targetEntity = Alert.class, mappedBy = "individu", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Alert> alerts;

    @OneToMany(targetEntity = RoleContract.class, mappedBy = "individu", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<RoleContract> roleContracts;


    /**
     * {@inheritDoc}
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(Object obj) {
        Individu.log.error("this == obj : " + this == obj);
        if (this == obj) {
            return true;
        }
        if(obj == null){
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Individu other = (Individu) obj;

        Individu.log.error("this.gin != null && other.getGin() != null : " + (this.gin != null && other.getGin() != null));
        if (this.gin != null && other.getGin() != null) {
            return this.gin.equals(other.getGin());
        }

        return super.equals(other);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public void setPostalAddress(Set<PostalAddress> postalAddresses) {
        this.postalAddresses = postalAddresses;
        postalAddresses.parallelStream().forEach(pa -> pa.setIndividu(this));
    }

    public void setTelecoms(Set<Telecoms> telecoms) {
        this.telecoms = telecoms;
        telecoms.parallelStream().forEach(tel -> tel.setIndividu(this));
    }

    public void setEmails(Set<EmailEntity> emails) {
        this.emails = emails;
        emails.parallelStream().forEach(email -> email.setIndividu(this));
    }
}
