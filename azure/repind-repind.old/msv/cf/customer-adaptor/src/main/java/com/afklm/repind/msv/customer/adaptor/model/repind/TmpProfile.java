package com.afklm.repind.msv.customer.adaptor.model.repind;

import com.afklm.repind.common.entity.contact.EmailEntity;
import com.afklm.repind.common.entity.contact.PostalAddress;
import com.afklm.repind.common.entity.individual.Individu;
import com.afklm.repind.common.entity.role.RoleContract;
import lombok.*;

import java.io.Serializable;

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

}

