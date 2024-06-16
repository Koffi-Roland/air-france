package com.airfrance.repind.dto.individu.myaccountcustomerdata;

/*PROTECTED REGION ID(_dxKI8D5kEeChwshMtbvhCA i) ENABLED START*/

// add not generated imports here

import com.airfrance.repind.dto.individu.CommunicationPreferencesDTO;
import com.airfrance.repind.dto.individu.adh.individualinformation.*;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.Set;

/*PROTECTED REGION END*/

/**
 * <p>Title : MyAccountCustomerDataResponseDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class MyAccountCustomerDataResponseDTO  {
        
    /**
     * gin
     */
    private String gin;
        
        
    /**
     * blocadresss09424dto
     */
    private Set<BlocAdressS09424DTO> blocadresss09424dto;
        
        
    /**
     * bloccontrats09424dto
     */
    private Set<BlocContratS09424DTO> bloccontrats09424dto;
        
        
    /**
     * blocemails09424dto
     */
    private Set<BlocEmailS09424DTO> blocemails09424dto;
        
        
    /**
     * bloctelecoms09424dto
     */
    private Set<BlocTelecomS09424DTO> bloctelecoms09424dto;
        
        
    /**
     * communicationpreferencesdto
     */
    private Set<CommunicationPreferencesDTO> communicationpreferencesdto;
        
        
    /**
     * individudto
     */
    private IndividuDTO individudto;
        
        
    /**
     * myaccountdatadto
     */
    private MyAccountDataDTO myaccountdatadto;
        

    /*PROTECTED REGION ID(_dxKI8D5kEeChwshMtbvhCA u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public MyAccountCustomerDataResponseDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pGin gin
     */
    public MyAccountCustomerDataResponseDTO(String pGin) {
        this.gin = pGin;
    }

    /**
     *
     * @return blocadresss09424dto
     */
    public Set<BlocAdressS09424DTO> getBlocadresss09424dto() {
        return this.blocadresss09424dto;
    }

    /**
     *
     * @param pBlocadresss09424dto blocadresss09424dto value
     */
    public void setBlocadresss09424dto(Set<BlocAdressS09424DTO> pBlocadresss09424dto) {
        this.blocadresss09424dto = pBlocadresss09424dto;
    }

    /**
     *
     * @return bloccontrats09424dto
     */
    public Set<BlocContratS09424DTO> getBloccontrats09424dto() {
        return this.bloccontrats09424dto;
    }

    /**
     *
     * @param pBloccontrats09424dto bloccontrats09424dto value
     */
    public void setBloccontrats09424dto(Set<BlocContratS09424DTO> pBloccontrats09424dto) {
        this.bloccontrats09424dto = pBloccontrats09424dto;
    }

    /**
     *
     * @return blocemails09424dto
     */
    public Set<BlocEmailS09424DTO> getBlocemails09424dto() {
        return this.blocemails09424dto;
    }

    /**
     *
     * @param pBlocemails09424dto blocemails09424dto value
     */
    public void setBlocemails09424dto(Set<BlocEmailS09424DTO> pBlocemails09424dto) {
        this.blocemails09424dto = pBlocemails09424dto;
    }

    /**
     *
     * @return bloctelecoms09424dto
     */
    public Set<BlocTelecomS09424DTO> getBloctelecoms09424dto() {
        return this.bloctelecoms09424dto;
    }

    /**
     *
     * @param pBloctelecoms09424dto bloctelecoms09424dto value
     */
    public void setBloctelecoms09424dto(Set<BlocTelecomS09424DTO> pBloctelecoms09424dto) {
        this.bloctelecoms09424dto = pBloctelecoms09424dto;
    }

    /**
     *
     * @return communicationpreferencesdto
     */
    public Set<CommunicationPreferencesDTO> getCommunicationpreferencesdto() {
        return this.communicationpreferencesdto;
    }

    /**
     *
     * @param pCommunicationpreferencesdto communicationpreferencesdto value
     */
    public void setCommunicationpreferencesdto(Set<CommunicationPreferencesDTO> pCommunicationpreferencesdto) {
        this.communicationpreferencesdto = pCommunicationpreferencesdto;
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
     * @return individudto
     */
    public IndividuDTO getIndividudto() {
        return this.individudto;
    }

    /**
     *
     * @param pIndividudto individudto value
     */
    public void setIndividudto(IndividuDTO pIndividudto) {
        this.individudto = pIndividudto;
    }

    /**
     *
     * @return myaccountdatadto
     */
    public MyAccountDataDTO getMyaccountdatadto() {
        return this.myaccountdatadto;
    }

    /**
     *
     * @param pMyaccountdatadto myaccountdatadto value
     */
    public void setMyaccountdatadto(MyAccountDataDTO pMyaccountdatadto) {
        this.myaccountdatadto = pMyaccountdatadto;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_dxKI8D5kEeChwshMtbvhCA) ENABLED START*/
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
            .append("gin", getGin())
            .toString();
    }

    /*PROTECTED REGION ID(_dxKI8D5kEeChwshMtbvhCA u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
