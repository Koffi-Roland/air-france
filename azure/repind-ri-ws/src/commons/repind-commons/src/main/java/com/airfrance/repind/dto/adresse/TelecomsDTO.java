package com.airfrance.repind.dto.adresse;

/*PROTECTED REGION ID(_uZqOIDOhEeCokvyNKVv2PQ i) ENABLED START*/

// add not generated imports here

import com.airfrance.ref.type.MediumCodeEnum;
import com.airfrance.ref.type.MediumStatusEnum;
import com.airfrance.ref.type.TerminalTypeEnum;
import com.airfrance.repind.dto.firme.PersonneMoraleDTO;
import com.airfrance.repind.dto.individu.IndividuDTO;
import com.airfrance.repind.dto.individu.SignatureDTO;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.Date;

/*PROTECTED REGION END*/

/**
 * <p>Title : TelecomsDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@SuppressWarnings("serial")
public class TelecomsDTO  implements Serializable {
        
    /**
     * sain
     */
    private String sain;
        
        
    /**
     * sgin
     */
    private String sgin;
        
        
    /**
     * version
     */
    private Integer version;
        
        
    /**
     * scode_medium
     */
    private String scode_medium;
        
        
    /**
     * sstatut_medium
     */
    private String sstatut_medium;
        
        
    /**
     * snumero
     */
    private String snumero;
        
        
    /**
     * sdescriptif_complementaire
     */
    private String sdescriptif_complementaire;
        
        
    /**
     * sterminal
     */
    private String sterminal;
        
        
    /**
     * scode_region
     */
    private String scode_region;
        
        
    /**
     * sindicatif
     */
    private String sindicatif;
        
        
    /**
     * ssignature_modification
     */
    private String ssignature_modification;
        
        
    /**
     * ssite_modification
     */
    private String ssite_modification;
        
        
    /**
     * ddate_modification
     */
    private Date ddate_modification;
        
        
    /**
     * ssignature_creation
     */
    private String ssignature_creation;
        
        
    /**
     * ssite_creation
     */
    private String ssite_creation;
        
        
    /**
     * ddate_creation
     */
    private Date ddate_creation;
        
        
    /**
     * icle_role
     */
    private Integer icle_role;
        
        
    /**
     * ikey_temp
     */
    private Integer ikey_temp;
        
        
    /**
     * snormalized_country
     */
    private String snormalized_country;
        
        
    /**
     * snormalized_numero
     */
    private String snormalized_numero;
        
        
    /**
     * sforcage
     */
    private String sforcage;
        
        
    /**
     * svalidation
     */
    private String svalidation;
        
        
    /**
     * snorm_nat_phone_number
     */
    private String snorm_nat_phone_number;
        
        
    /**
     * snorm_nat_phone_number_clean
     */
    private String snorm_nat_phone_number_clean;
        
        
    /**
     * snorm_inter_country_code
     */
    private String snorm_inter_country_code;
        
        
    /**
     * snorm_inter_phone_number
     */
    private String snorm_inter_phone_number;
        
        
    /**
     * snorm_terminal_type_detail
     */
    private String snorm_terminal_type_detail;
        
        
    /**
     * isnormalized
     */
    private String isnormalized;
        
        
    /**
     * ddate_invalidation
     */
    private Date ddate_invalidation;
        

        
        
    /**
     * individudto
     */
    private IndividuDTO individudto;
        

        

    /*PROTECTED REGION ID(_uZqOIDOhEeCokvyNKVv2PQ u var) ENABLED START*/
    // add your custom variables here if necessary

    /**
     * country code avant normalisation, non present en BD.
     */
    private String countryCode;
    
	/**
	 * ISO country code
	 */
    private String isoCountryCode;
    
    private final int FIRST_VERSION = 1;
    

    /*PROTECTED REGION END*/

    /**
     * personneMorale
     */
    private PersonneMoraleDTO personneMorale;
    
    /** 
     * default constructor 
     */
    public TelecomsDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pSain sain
     * @param pSgin sgin
     * @param pVersion version
     * @param pScode_medium scode_medium
     * @param pSstatut_medium sstatut_medium
     * @param pSnumero snumero
     * @param pSdescriptif_complementaire sdescriptif_complementaire
     * @param pSterminal sterminal
     * @param pScode_region scode_region
     * @param pSindicatif sindicatif
     * @param pSsignature_modification ssignature_modification
     * @param pSsite_modification ssite_modification
     * @param pDdate_modification ddate_modification
     * @param pSsignature_creation ssignature_creation
     * @param pSsite_creation ssite_creation
     * @param pDdate_creation ddate_creation
     * @param pIcle_role icle_role
     * @param pIkey_temp ikey_temp
     * @param pSnormalized_country snormalized_country
     * @param pSnormalized_numero snormalized_numero
     * @param pSforcage sforcage
     * @param pSvalidation svalidation
     * @param pSnorm_nat_phone_number snorm_nat_phone_number
     * @param pSnorm_nat_phone_number_clean snorm_nat_phone_number_clean
     * @param pSnorm_inter_country_code snorm_inter_country_code
     * @param pSnorm_inter_phone_number snorm_inter_phone_number
     * @param pSnorm_terminal_type_detail snorm_terminal_type_detail
     * @param pIsnormalized isnormalized
     * @param pDdate_invalidation ddate_invalidation
     */
    public TelecomsDTO(String pSain, String pSgin, Integer pVersion, String pScode_medium, String pSstatut_medium, String pSnumero, String pSdescriptif_complementaire, String pSterminal, String pScode_region, String pSindicatif, String pSsignature_modification, String pSsite_modification, Date pDdate_modification, String pSsignature_creation, String pSsite_creation, Date pDdate_creation, Integer pIcle_role, Integer pIkey_temp, String pSnormalized_country, String pSnormalized_numero, String pSforcage, String pSvalidation, String pSnorm_nat_phone_number, String pSnorm_nat_phone_number_clean, String pSnorm_inter_country_code, String pSnorm_inter_phone_number, String pSnorm_terminal_type_detail, String pIsnormalized, Date pDdate_invalidation) {
        this.sain = pSain;
        this.sgin = pSgin;
        this.version = pVersion;
        this.scode_medium = pScode_medium;
        this.sstatut_medium = pSstatut_medium;
        this.snumero = pSnumero;
        this.sdescriptif_complementaire = pSdescriptif_complementaire;
        this.sterminal = pSterminal;
        this.scode_region = pScode_region;
        this.sindicatif = pSindicatif;
        this.ssignature_modification = pSsignature_modification;
        this.ssite_modification = pSsite_modification;
        this.ddate_modification = pDdate_modification;
        this.ssignature_creation = pSsignature_creation;
        this.ssite_creation = pSsite_creation;
        this.ddate_creation = pDdate_creation;
        this.icle_role = pIcle_role;
        this.ikey_temp = pIkey_temp;
        this.snormalized_country = pSnormalized_country;
        this.snormalized_numero = pSnormalized_numero;
        this.sforcage = pSforcage;
        this.svalidation = pSvalidation;
        this.snorm_nat_phone_number = pSnorm_nat_phone_number;
        this.snorm_nat_phone_number_clean = pSnorm_nat_phone_number_clean;
        this.snorm_inter_country_code = pSnorm_inter_country_code;
        this.snorm_inter_phone_number = pSnorm_inter_phone_number;
        this.snorm_terminal_type_detail = pSnorm_terminal_type_detail;
        this.isnormalized = pIsnormalized;
        this.ddate_invalidation = pDdate_invalidation;
    }

    /**
     *
     * @return ddate_creation
     */
    public Date getDdate_creation() {
        return this.ddate_creation;
    }

    /**
     *
     * @param pDdate_creation ddate_creation value
     */
    public void setDdate_creation(Date pDdate_creation) {
        this.ddate_creation = pDdate_creation;
    }

    /**
     *
     * @return ddate_invalidation
     */
    public Date getDdate_invalidation() {
        return this.ddate_invalidation;
    }

    /**
     *
     * @param pDdate_invalidation ddate_invalidation value
     */
    public void setDdate_invalidation(Date pDdate_invalidation) {
        this.ddate_invalidation = pDdate_invalidation;
    }

    /**
     *
     * @return ddate_modification
     */
    public Date getDdate_modification() {
        return this.ddate_modification;
    }

    /**
     *
     * @param pDdate_modification ddate_modification value
     */
    public void setDdate_modification(Date pDdate_modification) {
        this.ddate_modification = pDdate_modification;
    }



    /**
     *
     * @return icle_role
     */
    public Integer getIcle_role() {
        return this.icle_role;
    }

    /**
     *
     * @param pIcle_role icle_role value
     */
    public void setIcle_role(Integer pIcle_role) {
        this.icle_role = pIcle_role;
    }

    /**
     *
     * @return ikey_temp
     */
    public Integer getIkey_temp() {
        return this.ikey_temp;
    }

    /**
     *
     * @param pIkey_temp ikey_temp value
     */
    public void setIkey_temp(Integer pIkey_temp) {
        this.ikey_temp = pIkey_temp;
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
     * @return isnormalized
     */
    public String getIsnormalized() {
        return this.isnormalized;
    }

    /**
     *
     * @param pIsnormalized isnormalized value
     */
    public void setIsnormalized(String pIsnormalized) {
        this.isnormalized = pIsnormalized;
    }

    /**
     *
     * @return personneMorale
     */
    public PersonneMoraleDTO getPersonneMorale() {
        return this.personneMorale;
    }

    /**
     *
     * @param pPersonneMorale personneMorale value
     */
    public void setPersonneMorale(PersonneMoraleDTO pPersonneMorale) {
        this.personneMorale = pPersonneMorale;
    }

    /**
     *
     * @return sain
     */
    public String getSain() {
        return this.sain;
    }

    /**
     *
     * @param pSain sain value
     */
    public void setSain(String pSain) {
        this.sain = pSain;
    }

    /**
     *
     * @return scode_medium
     */
    public String getScode_medium() {
        return this.scode_medium;
    }

    /**
     *
     * @param pScode_medium scode_medium value
     */
    public void setScode_medium(String pScode_medium) {
        this.scode_medium = pScode_medium;
    }

    /**
     *
     * @return scode_region
     */
    public String getScode_region() {
        return this.scode_region;
    }

    /**
     *
     * @param pScode_region scode_region value
     */
    public void setScode_region(String pScode_region) {
        this.scode_region = pScode_region;
    }

    /**
     *
     * @return sdescriptif_complementaire
     */
    public String getSdescriptif_complementaire() {
        return this.sdescriptif_complementaire;
    }

    /**
     *
     * @param pSdescriptif_complementaire sdescriptif_complementaire value
     */
    public void setSdescriptif_complementaire(String pSdescriptif_complementaire) {
        this.sdescriptif_complementaire = pSdescriptif_complementaire;
    }

    /**
     *
     * @return sforcage
     */
    public String getSforcage() {
        return this.sforcage;
    }

    /**
     *
     * @param pSforcage sforcage value
     */
    public void setSforcage(String pSforcage) {
        this.sforcage = pSforcage;
    }

    /**
     *
     * @return sgin
     */
    public String getSgin() {
        return this.sgin;
    }

    /**
     *
     * @param pSgin sgin value
     */
    public void setSgin(String pSgin) {
        this.sgin = pSgin;
    }

    /**
     *
     * @return sindicatif
     */
    public String getSindicatif() {
        return this.sindicatif;
    }

    /**
     *
     * @param pSindicatif sindicatif value
     */
    public void setSindicatif(String pSindicatif) {
        this.sindicatif = pSindicatif;
    }

    /**
     *
     * @return snorm_inter_country_code
     */
    public String getSnorm_inter_country_code() {
        return this.snorm_inter_country_code;
    }

    /**
     *
     * @param pSnorm_inter_country_code snorm_inter_country_code value
     */
    public void setSnorm_inter_country_code(String pSnorm_inter_country_code) {
        this.snorm_inter_country_code = pSnorm_inter_country_code;
    }

    /**
     *
     * @return snorm_inter_phone_number
     */
    public String getSnorm_inter_phone_number() {
        return this.snorm_inter_phone_number;
    }

    /**
     *
     * @param pSnorm_inter_phone_number snorm_inter_phone_number value
     */
    public void setSnorm_inter_phone_number(String pSnorm_inter_phone_number) {
        this.snorm_inter_phone_number = pSnorm_inter_phone_number;
    }

    /**
     *
     * @return snorm_nat_phone_number
     */
    public String getSnorm_nat_phone_number() {
        return this.snorm_nat_phone_number;
    }

    /**
     *
     * @param pSnorm_nat_phone_number snorm_nat_phone_number value
     */
    public void setSnorm_nat_phone_number(String pSnorm_nat_phone_number) {
        this.snorm_nat_phone_number = pSnorm_nat_phone_number;
    }

    /**
     *
     * @return snorm_nat_phone_number_clean
     */
    public String getSnorm_nat_phone_number_clean() {
        return this.snorm_nat_phone_number_clean;
    }

    /**
     *
     * @param pSnorm_nat_phone_number_clean snorm_nat_phone_number_clean value
     */
    public void setSnorm_nat_phone_number_clean(String pSnorm_nat_phone_number_clean) {
        this.snorm_nat_phone_number_clean = pSnorm_nat_phone_number_clean;
    }

    /**
     *
     * @return snorm_terminal_type_detail
     */
    public String getSnorm_terminal_type_detail() {
        return this.snorm_terminal_type_detail;
    }

    /**
     *
     * @param pSnorm_terminal_type_detail snorm_terminal_type_detail value
     */
    public void setSnorm_terminal_type_detail(String pSnorm_terminal_type_detail) {
        this.snorm_terminal_type_detail = pSnorm_terminal_type_detail;
    }

    /**
     *
     * @return snormalized_country
     */
    public String getSnormalized_country() {
        return this.snormalized_country;
    }

    /**
     *
     * @param pSnormalized_country snormalized_country value
     */
    public void setSnormalized_country(String pSnormalized_country) {
        this.snormalized_country = pSnormalized_country;
    }

    /**
     *
     * @return snormalized_numero
     */
    public String getSnormalized_numero() {
        return this.snormalized_numero;
    }

    /**
     *
     * @param pSnormalized_numero snormalized_numero value
     */
    public void setSnormalized_numero(String pSnormalized_numero) {
        this.snormalized_numero = pSnormalized_numero;
    }

    /**
     *
     * @return snumero
     */
    public String getSnumero() {
        return this.snumero;
    }

    /**
     *
     * @param pSnumero snumero value
     */
    public void setSnumero(String pSnumero) {
        this.snumero = pSnumero;
    }

    /**
     *
     * @return ssignature_creation
     */
    public String getSsignature_creation() {
        return this.ssignature_creation;
    }

    /**
     *
     * @param pSsignature_creation ssignature_creation value
     */
    public void setSsignature_creation(String pSsignature_creation) {
        this.ssignature_creation = pSsignature_creation;
    }

    /**
     *
     * @return ssignature_modification
     */
    public String getSsignature_modification() {
        return this.ssignature_modification;
    }

    /**
     *
     * @param pSsignature_modification ssignature_modification value
     */
    public void setSsignature_modification(String pSsignature_modification) {
        this.ssignature_modification = pSsignature_modification;
    }

    /**
     *
     * @return ssite_creation
     */
    public String getSsite_creation() {
        return this.ssite_creation;
    }

    /**
     *
     * @param pSsite_creation ssite_creation value
     */
    public void setSsite_creation(String pSsite_creation) {
        this.ssite_creation = pSsite_creation;
    }

    /**
     *
     * @return ssite_modification
     */
    public String getSsite_modification() {
        return this.ssite_modification;
    }

    /**
     *
     * @param pSsite_modification ssite_modification value
     */
    public void setSsite_modification(String pSsite_modification) {
        this.ssite_modification = pSsite_modification;
    }

    /**
     *
     * @return sstatut_medium
     */
    public String getSstatut_medium() {
        return this.sstatut_medium;
    }

    /**
     *
     * @param pSstatut_medium sstatut_medium value
     */
    public void setSstatut_medium(String pSstatut_medium) {
        this.sstatut_medium = pSstatut_medium;
    }

    /**
     *
     * @return sterminal
     */
    public String getSterminal() {
        return this.sterminal;
    }

    /**
     *
     * @param pSterminal sterminal value
     */
    public void setSterminal(String pSterminal) {
        this.sterminal = pSterminal;
    }

    /**
     *
     * @return svalidation
     */
    public String getSvalidation() {
        return this.svalidation;
    }

    /**
     *
     * @param pSvalidation svalidation value
     */
    public void setSvalidation(String pSvalidation) {
        this.svalidation = pSvalidation;
    }

    /**
     *
     * @return version
     */
    public Integer getVersion() {
        return this.version;
    }

    /**
     *
     * @param pVersion version value
     */
    public void setVersion(Integer pVersion) {
        this.version = pVersion;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_uZqOIDOhEeCokvyNKVv2PQ) ENABLED START*/
        result = toStringImpl();
        /*PROTECTED REGION END*/
        return result;
    }

    /**
     *
     * @return object as string
     */
    public String toStringImpl() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(getClass().getName());
        buffer.append("@").append(Integer.toHexString(System.identityHashCode(this)));
        buffer.append("[");
        buffer.append("sain=").append(getSain());
        buffer.append(",");
        buffer.append("sgin=").append(getSgin());
        buffer.append(",");
        buffer.append("version=").append(getVersion());
        buffer.append(",");
        buffer.append("scode_medium=").append(getScode_medium());
        buffer.append(",");
        buffer.append("sstatut_medium=").append(getSstatut_medium());
        buffer.append(",");
        buffer.append("snumero=").append(getSnumero());
        buffer.append(",");
        buffer.append("sdescriptif_complementaire=").append(getSdescriptif_complementaire());
        buffer.append(",");
        buffer.append("sterminal=").append(getSterminal());
        buffer.append(",");
        buffer.append("scode_region=").append(getScode_region());
        buffer.append(",");
        buffer.append("sindicatif=").append(getSindicatif());
        buffer.append(",");
        buffer.append("ssignature_modification=").append(getSsignature_modification());
        buffer.append(",");
        buffer.append("ssite_modification=").append(getSsite_modification());
        buffer.append(",");
        buffer.append("ddate_modification=").append(getDdate_modification());
        buffer.append(",");
        buffer.append("ssignature_creation=").append(getSsignature_creation());
        buffer.append(",");
        buffer.append("ssite_creation=").append(getSsite_creation());
        buffer.append(",");
        buffer.append("ddate_creation=").append(getDdate_creation());
        buffer.append(",");
        buffer.append("icle_role=").append(getIcle_role());
        buffer.append(",");
        buffer.append("ikey_temp=").append(getIkey_temp());
        buffer.append(",");
        buffer.append("snormalized_country=").append(getSnormalized_country());
        buffer.append(",");
        buffer.append("snormalized_numero=").append(getSnormalized_numero());
        buffer.append(",");
        buffer.append("sforcage=").append(getSforcage());
        buffer.append(",");
        buffer.append("svalidation=").append(getSvalidation());
        buffer.append(",");
        buffer.append("snorm_nat_phone_number=").append(getSnorm_nat_phone_number());
        buffer.append(",");
        buffer.append("snorm_nat_phone_number_clean=").append(getSnorm_nat_phone_number_clean());
        buffer.append(",");
        buffer.append("snorm_inter_country_code=").append(getSnorm_inter_country_code());
        buffer.append(",");
        buffer.append("snorm_inter_phone_number=").append(getSnorm_inter_phone_number());
        buffer.append(",");
        buffer.append("snorm_terminal_type_detail=").append(getSnorm_terminal_type_detail());
        buffer.append(",");
        buffer.append("isnormalized=").append(getIsnormalized());
        buffer.append(",");
        buffer.append("ddate_invalidation=").append(getDdate_invalidation());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_uZqOIDOhEeCokvyNKVv2PQ u m) ENABLED START*/

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setIsoCountryCode(String isoCountryCode) {
		this.isoCountryCode = isoCountryCode;
	}

	public String getIsoCountryCode() {
		return isoCountryCode;
	}
	
	public void prepareForCreation(SignatureDTO signatureDTO) {

		Date today = new Date();
    	
    	setDdate_creation(today);
    	setSsignature_creation(signatureDTO.getSignature());
    	setSsite_creation(signatureDTO.getSite());
    	
		setDdate_modification(today);
    	setSsignature_modification(signatureDTO.getSignature());
    	setSsite_modification(signatureDTO.getSite());
    	
    	setVersion(FIRST_VERSION);
	}
	
	public void prepareForUpdate(SignatureDTO signatureDTO, TelecomsDTO telecomDTO) {
		
		Date today = new Date();
		
		setDdate_modification(today);
    	setSsignature_modification(signatureDTO.getSignature());
    	setSsite_modification(signatureDTO.getSite());
		
    	setCountryCode(telecomDTO.getCountryCode());
    	setSnumero(telecomDTO.getSnumero());
    	setSterminal(telecomDTO.getSterminal());
    	setVersion(telecomDTO.getVersion());
    	setSstatut_medium(telecomDTO.getSstatut_medium());
	}

	public void prepareForUpdateWithUsageCode(SignatureDTO signatureDTO, TelecomsDTO telecomDTO) {
				
		prepareForUpdate(signatureDTO, telecomDTO);
    	setScode_medium(telecomDTO.getScode_medium());
	}
	
	public void setDefaultValues() {
    	
		if(StringUtils.isEmpty(scode_medium)) {
			scode_medium = MediumCodeEnum.HOME.toString();
    	}
    	
    	if(StringUtils.isEmpty(sstatut_medium)) {
    		sstatut_medium = MediumStatusEnum.VALID.toString();
    	}
	}
	
	public void nextVersion() {
		version++;
	}
	
	/**
	 * Invalid fixed-line phone
	 */
	public boolean isInvalidFixedLinePhone() {
		return MediumStatusEnum.INVALID.toString().equals(sstatut_medium) && TerminalTypeEnum.FIX.toString().equals(sterminal);
	}
	
	/**
	 * Invalid mobile phone
	 */
	public boolean isInvalidMobilePhone() {
		return MediumStatusEnum.INVALID.toString().equals(sstatut_medium) && TerminalTypeEnum.MOBILE.toString().equals(sterminal);
	}
	
	/**
	 * Valid but not normalized phone
	 */
	public boolean isNoValidNormalizedTelecom() {
		
		boolean noValid = true;

		// REPIND-1287 : Normalized telecom is valid only if telecom is Valid 
		if (MediumStatusEnum.VALID.toString().equals(sstatut_medium)) {
		
			noValid &= MediumStatusEnum.VALID.toString().equals(sstatut_medium);
			noValid &= StringUtils.isEmpty(snorm_inter_country_code);
			noValid &= StringUtils.isEmpty(snorm_inter_phone_number);
			noValid &= StringUtils.isEmpty(snorm_nat_phone_number);
			noValid &= StringUtils.isEmpty(snorm_terminal_type_detail);
		}
		
    	return noValid;
	}
	
	public boolean isNormalized() {
		
		boolean isNormalized = true;
		
		isNormalized &= StringUtils.isNotEmpty(snorm_inter_country_code);
		isNormalized &= StringUtils.isNotEmpty(snorm_inter_phone_number);
		isNormalized &= StringUtils.isNotEmpty(snorm_nat_phone_number);
		isNormalized &= StringUtils.isNotEmpty(snorm_terminal_type_detail);
		
		return isNormalized;
	}
	
    /*PROTECTED REGION END*/

}
