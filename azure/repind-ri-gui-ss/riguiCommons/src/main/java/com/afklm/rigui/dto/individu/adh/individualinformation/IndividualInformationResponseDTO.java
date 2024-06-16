package com.afklm.rigui.dto.individu.adh.individualinformation;

/*PROTECTED REGION ID(_QNWM0yMUEeCWJOBY8f-ONQ i) ENABLED START*/

// add not generated imports here

import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.Set;

/*PROTECTED REGION END*/

/**
 * <p>Title : IndividualInformationResponseDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class IndividualInformationResponseDTO  {
        
    /**
     * boolRoleGP
     */
    private Boolean boolRoleGP;
        
        
    /**
     * boolFirme
     */
    private Boolean boolFirme;
        
        
    /**
     * boolAgence
     */
    private Boolean boolAgence;
        
        
    /**
     * bool4
     */
    private Boolean bool4;
        
        
    /**
     * bool5
     */
    private Boolean bool5;
        
        
    /**
     * bool6
     */
    private Boolean bool6;
        
        
    /**
     * bool7
     */
    private Boolean bool7;
        
        
    /**
     * bool8
     */
    private Boolean bool8;
        
        
    /**
     * bool9
     */
    private Boolean bool9;
        
        
    /**
     * bool10
     */
    private Boolean bool10;
        
        
    /**
     * gin
     */
    private String gin;
        
        
    /**
     * blocadresss09424
     */
    private Set<BlocAdressS09424DTO> blocadresss09424;
        
        
    /**
     * bloccontrats09424
     */
    private Set<BlocContratS09424DTO> bloccontrats09424;
        
        
    /**
     * blocemails09424
     */
    private Set<BlocEmailS09424DTO> blocemails09424;
        
        
    /**
     * bloctelecoms09424
     */
    private Set<BlocTelecomS09424DTO> bloctelecoms09424;
        
        
    /**
     * exception
     */
    private ExceptionDTO exception;
        
        
    /**
     * individu
     */
    private IndividuDTO individu;
        
        
    /**
     * profilaveccodefonctionvalidesic
     */
    private ProfilAvecCodeFonctionValideSICDTO profilaveccodefonctionvalidesic;
        
        
    /**
     * profileairfrancesic
     */
    private Set<ProfileAirFranceSICDTO> profileairfrancesic;
        

    /*PROTECTED REGION ID(_QNWM0yMUEeCWJOBY8f-ONQ u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public IndividualInformationResponseDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pBoolRoleGP boolRoleGP
     * @param pBoolFirme boolFirme
     * @param pBoolAgence boolAgence
     * @param pBool4 bool4
     * @param pBool5 bool5
     * @param pBool6 bool6
     * @param pBool7 bool7
     * @param pBool8 bool8
     * @param pBool9 bool9
     * @param pBool10 bool10
     * @param pGin gin
     */
    public IndividualInformationResponseDTO(Boolean pBoolRoleGP, Boolean pBoolFirme, Boolean pBoolAgence, Boolean pBool4, Boolean pBool5, Boolean pBool6, Boolean pBool7, Boolean pBool8, Boolean pBool9, Boolean pBool10, String pGin) {
        this.boolRoleGP = pBoolRoleGP;
        this.boolFirme = pBoolFirme;
        this.boolAgence = pBoolAgence;
        this.bool4 = pBool4;
        this.bool5 = pBool5;
        this.bool6 = pBool6;
        this.bool7 = pBool7;
        this.bool8 = pBool8;
        this.bool9 = pBool9;
        this.bool10 = pBool10;
        this.gin = pGin;
    }

    /**
     *
     * @return blocadresss09424
     */
    public Set<BlocAdressS09424DTO> getBlocadresss09424() {
        return this.blocadresss09424;
    }

    /**
     *
     * @param pBlocadresss09424 blocadresss09424 value
     */
    public void setBlocadresss09424(Set<BlocAdressS09424DTO> pBlocadresss09424) {
        this.blocadresss09424 = pBlocadresss09424;
    }

    /**
     *
     * @return bloccontrats09424
     */
    public Set<BlocContratS09424DTO> getBloccontrats09424() {
        return this.bloccontrats09424;
    }

    /**
     *
     * @param pBloccontrats09424 bloccontrats09424 value
     */
    public void setBloccontrats09424(Set<BlocContratS09424DTO> pBloccontrats09424) {
        this.bloccontrats09424 = pBloccontrats09424;
    }

    /**
     *
     * @return blocemails09424
     */
    public Set<BlocEmailS09424DTO> getBlocemails09424() {
        return this.blocemails09424;
    }

    /**
     *
     * @param pBlocemails09424 blocemails09424 value
     */
    public void setBlocemails09424(Set<BlocEmailS09424DTO> pBlocemails09424) {
        this.blocemails09424 = pBlocemails09424;
    }

    /**
     *
     * @return bloctelecoms09424
     */
    public Set<BlocTelecomS09424DTO> getBloctelecoms09424() {
        return this.bloctelecoms09424;
    }

    /**
     *
     * @param pBloctelecoms09424 bloctelecoms09424 value
     */
    public void setBloctelecoms09424(Set<BlocTelecomS09424DTO> pBloctelecoms09424) {
        this.bloctelecoms09424 = pBloctelecoms09424;
    }

    /**
     *
     * @return bool10
     */
    public Boolean getBool10() {
        return this.bool10;
    }

    /**
     *
     * @param pBool10 bool10 value
     */
    public void setBool10(Boolean pBool10) {
        this.bool10 = pBool10;
    }

    /**
     *
     * @return bool4
     */
    public Boolean getBool4() {
        return this.bool4;
    }

    /**
     *
     * @param pBool4 bool4 value
     */
    public void setBool4(Boolean pBool4) {
        this.bool4 = pBool4;
    }

    /**
     *
     * @return bool5
     */
    public Boolean getBool5() {
        return this.bool5;
    }

    /**
     *
     * @param pBool5 bool5 value
     */
    public void setBool5(Boolean pBool5) {
        this.bool5 = pBool5;
    }

    /**
     *
     * @return bool6
     */
    public Boolean getBool6() {
        return this.bool6;
    }

    /**
     *
     * @param pBool6 bool6 value
     */
    public void setBool6(Boolean pBool6) {
        this.bool6 = pBool6;
    }

    /**
     *
     * @return bool7
     */
    public Boolean getBool7() {
        return this.bool7;
    }

    /**
     *
     * @param pBool7 bool7 value
     */
    public void setBool7(Boolean pBool7) {
        this.bool7 = pBool7;
    }

    /**
     *
     * @return bool8
     */
    public Boolean getBool8() {
        return this.bool8;
    }

    /**
     *
     * @param pBool8 bool8 value
     */
    public void setBool8(Boolean pBool8) {
        this.bool8 = pBool8;
    }

    /**
     *
     * @return bool9
     */
    public Boolean getBool9() {
        return this.bool9;
    }

    /**
     *
     * @param pBool9 bool9 value
     */
    public void setBool9(Boolean pBool9) {
        this.bool9 = pBool9;
    }

    /**
     *
     * @return boolAgence
     */
    public Boolean getBoolAgence() {
        return this.boolAgence;
    }

    /**
     *
     * @param pBoolAgence boolAgence value
     */
    public void setBoolAgence(Boolean pBoolAgence) {
        this.boolAgence = pBoolAgence;
    }

    /**
     *
     * @return boolFirme
     */
    public Boolean getBoolFirme() {
        return this.boolFirme;
    }

    /**
     *
     * @param pBoolFirme boolFirme value
     */
    public void setBoolFirme(Boolean pBoolFirme) {
        this.boolFirme = pBoolFirme;
    }

    /**
     *
     * @return boolRoleGP
     */
    public Boolean getBoolRoleGP() {
        return this.boolRoleGP;
    }

    /**
     *
     * @param pBoolRoleGP boolRoleGP value
     */
    public void setBoolRoleGP(Boolean pBoolRoleGP) {
        this.boolRoleGP = pBoolRoleGP;
    }

    /**
     *
     * @return exception
     */
    public ExceptionDTO getException() {
        return this.exception;
    }

    /**
     *
     * @param pException exception value
     */
    public void setException(ExceptionDTO pException) {
        this.exception = pException;
    }

    /**
     *
     * @return gin
     */
    public String getGin() {
        return this.gin;
    }

    /**
     *
     * @param pGin gin value
     */
    public void setGin(String pGin) {
        this.gin = pGin;
    }

    /**
     *
     * @return individu
     */
    public IndividuDTO getIndividu() {
        return this.individu;
    }

    /**
     *
     * @param pIndividu individu value
     */
    public void setIndividu(IndividuDTO pIndividu) {
        this.individu = pIndividu;
    }

    /**
     *
     * @return profilaveccodefonctionvalidesic
     */
    public ProfilAvecCodeFonctionValideSICDTO getProfilaveccodefonctionvalidesic() {
        return this.profilaveccodefonctionvalidesic;
    }

    /**
     *
     * @param pProfilaveccodefonctionvalidesic profilaveccodefonctionvalidesic value
     */
    public void setProfilaveccodefonctionvalidesic(ProfilAvecCodeFonctionValideSICDTO pProfilaveccodefonctionvalidesic) {
        this.profilaveccodefonctionvalidesic = pProfilaveccodefonctionvalidesic;
    }

    /**
     *
     * @return profileairfrancesic
     */
    public Set<ProfileAirFranceSICDTO> getProfileairfrancesic() {
        return this.profileairfrancesic;
    }

    /**
     *
     * @param pProfileairfrancesic profileairfrancesic value
     */
    public void setProfileairfrancesic(Set<ProfileAirFranceSICDTO> pProfileairfrancesic) {
        this.profileairfrancesic = pProfileairfrancesic;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_QNWM0yMUEeCWJOBY8f-ONQ) ENABLED START*/
        result = toStringImpl();
        /*PROTECTED REGION END*/
        return result;
    }

    /**
     *
     * @return object as string
     */
    public String toStringImpl() {
        return new ToStringBuilder(this)
            .append("boolRoleGP", getBoolRoleGP())
            .append("boolFirme", getBoolFirme())
            .append("boolAgence", getBoolAgence())
            .append("bool4", getBool4())
            .append("bool5", getBool5())
            .append("bool6", getBool6())
            .append("bool7", getBool7())
            .append("bool8", getBool8())
            .append("bool9", getBool9())
            .append("bool10", getBool10())
            .append("gin", getGin())
            .toString();
    }

    /*PROTECTED REGION ID(_QNWM0yMUEeCWJOBY8f-ONQ u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
