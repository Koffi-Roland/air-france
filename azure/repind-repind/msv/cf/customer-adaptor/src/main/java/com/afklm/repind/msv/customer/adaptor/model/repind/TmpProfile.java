package com.afklm.repind.msv.customer.adaptor.model.repind;

import com.afklm.repind.common.entity.contact.EmailEntity;
import com.afklm.repind.common.entity.contact.PostalAddress;
import com.afklm.repind.common.entity.individual.Individu;
import com.afklm.repind.common.entity.role.RoleContract;
import com.afklm.repind.msv.customer.adaptor.model.salesforce.Profiles;
import lombok.*;

import java.io.Serializable;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class TmpProfile implements Serializable {

    private String gin;
    private String firstname;
    private String lastname;
    private String gender;
    private String civility;
    private String birthdate;

    private String preferredDestinationContinent;
    private String preferredDestinationCity;
    private String departureAirportKl;
    private String holidayType;
    private String preferredAirport;
    private String countryOfResidence ;

    private String codeLanguage;

    private String email;
    private String emailStatus;

    private String zipCode;
    private String city;
    private String country;

    private String cin;
    private String fbEnrollmentDate;

    private String myAccountId;
    private String maEnrollmentDate;



    public TmpProfile setIndividusAllFields(Individu individuFromDB) {
        if(individuFromDB != null){
            this.gin =individuFromDB.getGin();
            this.firstname=individuFromDB.getPrenom();
            this.lastname=individuFromDB.getNom();
            this.birthdate=String.valueOf(individuFromDB.getDateNaissance());
            this.civility=individuFromDB.getCivilite();
            this.gender =individuFromDB.getSexe();
        }
       return this;
    }

    public TmpProfile setEmailsFields(EmailEntity emailEntity) {
        if(emailEntity != null){
            this.email =emailEntity.getEmail();
            this.emailStatus=emailEntity.getStatutMedium();
        }
        return this;
    }

    public TmpProfile setAdrPostFields(PostalAddress adrPost) {
        if(adrPost != null){
            this.city =adrPost.getVille();
            this.zipCode=adrPost.getCodePostal();
            this.country=adrPost.getCodePays();
        }
        return this;
    }

    public TmpProfile setRoleContractsFields(RoleContract roleContract) {
        if(roleContract != null){
            if("FP".equals(roleContract.getTypeContrat())){
                this.cin = roleContract.getNumeroContrat();
                this.fbEnrollmentDate = roleContract.getDateCreation().toString();
            }else if("MA".equals(roleContract.getTypeContrat())){
                this.myAccountId = roleContract.getNumeroContrat();
                this.maEnrollmentDate = roleContract.getDateCreation().toString();
            }
        }
        return this;
    }

    public TmpProfile setCodeLanguage(String codeLanguage) {
        if (codeLanguage != null){
            this.codeLanguage = codeLanguage;
        }
        return this;
    }

    public TmpProfile setPreferences(Map<String, String> preferences) {
        if (preferences!=null && !preferences.isEmpty()){
            this.preferredDestinationContinent = preferences.getOrDefault(Profiles.preferred_destination_continent.name(), null);
            this.preferredDestinationCity = preferences.getOrDefault(Profiles.preferred_destination_city.name(), null);
            this.departureAirportKl = preferences.getOrDefault(Profiles.departure_airport_kl.name(), null);
            this.holidayType = preferences.getOrDefault(Profiles.holiday_type.name(), null);
            this.preferredAirport = preferences.getOrDefault(Profiles.preferred_departure_airport.name(), null);
            this.countryOfResidence = preferences.getOrDefault(Profiles.country_residence.name(), null);
        }
        return this;
    }

}

