package com.afklm.rigui.dto.adresse;

/*PROTECTED REGION ID(_RpoWEDRWEeCGEoB0vWAi2A i) ENABLED START*/

// add not generated imports here

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;



/*PROTECTED REGION END*/

/**
 * <p>Title : PostalAddressDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class PostalAddressDTO  implements Serializable {
        
    /**
	 * 
	 */
	private static final long serialVersionUID = 5310012744787141983L;


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
     * sraison_sociale
     */
    private String sraison_sociale;
        
        
    /**
     * scomplement_adresse
     */
    private String scomplement_adresse;
        
        
    /**
     * sno_et_rue
     */
    private String sno_et_rue;
        
        
    /**
     * slocalite
     */
    private String slocalite;
        
        
    /**
     * scode_postal
     */
    private String scode_postal;
        
        
    /**
     * sville
     */
    private String sville;
        
        
    /**
     * scode_pays
     */
    private String scode_pays;
        
        
    /**
     * scode_province
     */
    private String scode_province;
        
        
    /**
     * scode_medium
     */
    private String scode_medium;
        
        
    /**
     * sstatut_medium
     */
    private String sstatut_medium;
        
        
    /**
     * ssite_modification
     */
    private String ssite_modification;
        
        
    /**
     * ssignature_modification
     */
    private String ssignature_modification;
        
        
    /**
     * ddate_modification
     */
    private Date ddate_modification;
        
        
    /**
     * ssite_creation
     */
    private String ssite_creation;
        
        
    /**
     * signature_creation
     */
    private String signature_creation;
        
        
    /**
     * ddate_creation
     */
    private Date ddate_creation;
        
        
    /**
     * sforcage
     */
    private String sforcage;
        
        
    /**
     * sdescriptif_complementaire
     */
    private String sdescriptif_complementaire;
        
        
    /**
     * sindadr
     */
    private String sindadr;
        
        
    /**
     * icod_err
     */
    private Integer icod_err;
        
        
    /**
     * icod_warning
     */
    private Integer icod_warning;
        
        
    /**
     * icle_role
     */
    private Integer icle_role;
        
        
    /**
     * ikey_temp
     */
    private Integer ikey_temp;
        
        
    /**
     * ddate_fonctionnel
     */
    private Date ddate_fonctionnel;
        
        
    /**
     * ssite_fonctionnel
     */
    private String ssite_fonctionnel;
        
        
    /**
     * ssignature_fonctionnel
     */
    private String ssignature_fonctionnel;
        
        
    /**
     * scod_err_simple
     */
    private String scod_err_simple;
        
        
    /**
     * scod_err_detaille
     */
    private String scod_err_detaille;
        
        
    /**
     * stype_invalidite
     */
    private String stype_invalidite;
        
        
    /**
     * senvoi_postal
     */
    private String senvoi_postal;
        
        
    /**
     * denvoi_postal
     */
    private Date denvoi_postal;
        
        
    /**
     * scod_app_send
     */
    private String scod_app_send;
        
        
    /**
     * numeroUsage
     */
    private Integer numeroUsage;
        
        
    /**
     * preferee
     */
    private Boolean preferee;
    

        
        
    /**
     * postalAddressesToNormalize
     */
    private Set<PostalAddressToNormalizeDTO> postalAddressesToNormalize;
        
        
    /**
     * usage_mediumdto
     */
    private Set<Usage_mediumDTO> usage_mediumdto;
        

    /*PROTECTED REGION ID(_RpoWEDRWEeCGEoB0vWAi2A u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public PostalAddressDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pSain sain
     * @param pSgin sgin
     * @param pVersion version
     * @param pSraison_sociale sraison_sociale
     * @param pScomplement_adresse scomplement_adresse
     * @param pSno_et_rue sno_et_rue
     * @param pSlocalite slocalite
     * @param pScode_postal scode_postal
     * @param pSville sville
     * @param pScode_pays scode_pays
     * @param pScode_province scode_province
     * @param pScode_medium scode_medium
     * @param pSstatut_medium sstatut_medium
     * @param pSsite_modification ssite_modification
     * @param pSsignature_modification ssignature_modification
     * @param pDdate_modification ddate_modification
     * @param pSsite_creation ssite_creation
     * @param pSignature_creation signature_creation
     * @param pDdate_creation ddate_creation
     * @param pSforcage sforcage
     * @param pSdescriptif_complementaire sdescriptif_complementaire
     * @param pSindadr sindadr
     * @param pIcod_err icod_err
     * @param pIcod_warning icod_warning
     * @param pIcle_role icle_role
     * @param pIkey_temp ikey_temp
     * @param pDdate_fonctionnel ddate_fonctionnel
     * @param pSsite_fonctionnel ssite_fonctionnel
     * @param pSsignature_fonctionnel ssignature_fonctionnel
     * @param pScod_err_simple scod_err_simple
     * @param pScod_err_detaille scod_err_detaille
     * @param pStype_invalidite stype_invalidite
     * @param pSenvoi_postal senvoi_postal
     * @param pDenvoi_postal denvoi_postal
     * @param pScod_app_send scod_app_send
     * @param pNumeroUsage numeroUsage
     * @param pPreferee preferee
     */
    public PostalAddressDTO(String pSain, String pSgin, Integer pVersion, String pSraison_sociale, String pScomplement_adresse, String pSno_et_rue, String pSlocalite, String pScode_postal, String pSville, String pScode_pays, String pScode_province, String pScode_medium, String pSstatut_medium, String pSsite_modification, String pSsignature_modification, Date pDdate_modification, String pSsite_creation, String pSignature_creation, Date pDdate_creation, String pSforcage, String pSdescriptif_complementaire, String pSindadr, Integer pIcod_err, Integer pIcod_warning, Integer pIcle_role, Integer pIkey_temp, Date pDdate_fonctionnel, String pSsite_fonctionnel, String pSsignature_fonctionnel, String pScod_err_simple, String pScod_err_detaille, String pStype_invalidite, String pSenvoi_postal, Date pDenvoi_postal, String pScod_app_send, Integer pNumeroUsage, Boolean pPreferee) {
        this.sain = pSain;
        this.sgin = pSgin;
        this.version = pVersion;
        this.sraison_sociale = pSraison_sociale;
        this.scomplement_adresse = pScomplement_adresse;
        this.sno_et_rue = pSno_et_rue;
        this.slocalite = pSlocalite;
        this.scode_postal = pScode_postal;
        this.sville = pSville;
        this.scode_pays = pScode_pays;
        this.scode_province = pScode_province;
        this.scode_medium = pScode_medium;
        this.sstatut_medium = pSstatut_medium;
        this.ssite_modification = pSsite_modification;
        this.ssignature_modification = pSsignature_modification;
        this.ddate_modification = pDdate_modification;
        this.ssite_creation = pSsite_creation;
        this.signature_creation = pSignature_creation;
        this.ddate_creation = pDdate_creation;
        this.sforcage = pSforcage;
        this.sdescriptif_complementaire = pSdescriptif_complementaire;
        this.sindadr = pSindadr;
        this.icod_err = pIcod_err;
        this.icod_warning = pIcod_warning;
        this.icle_role = pIcle_role;
        this.ikey_temp = pIkey_temp;
        this.ddate_fonctionnel = pDdate_fonctionnel;
        this.ssite_fonctionnel = pSsite_fonctionnel;
        this.ssignature_fonctionnel = pSsignature_fonctionnel;
        this.scod_err_simple = pScod_err_simple;
        this.scod_err_detaille = pScod_err_detaille;
        this.stype_invalidite = pStype_invalidite;
        this.senvoi_postal = pSenvoi_postal;
        this.denvoi_postal = pDenvoi_postal;
        this.scod_app_send = pScod_app_send;
        this.numeroUsage = pNumeroUsage;
        this.preferee = pPreferee;
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
     * @return ddate_fonctionnel
     */
    public Date getDdate_fonctionnel() {
        return this.ddate_fonctionnel;
    }

    /**
     *
     * @param pDdate_fonctionnel ddate_fonctionnel value
     */
    public void setDdate_fonctionnel(Date pDdate_fonctionnel) {
        this.ddate_fonctionnel = pDdate_fonctionnel;
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
     * @return denvoi_postal
     */
    public Date getDenvoi_postal() {
        return this.denvoi_postal;
    }

    /**
     *
     * @param pDenvoi_postal denvoi_postal value
     */
    public void setDenvoi_postal(Date pDenvoi_postal) {
        this.denvoi_postal = pDenvoi_postal;
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
     * @return icod_err
     */
    public Integer getIcod_err() {
        return this.icod_err;
    }

    /**
     *
     * @param pIcod_err icod_err value
     */
    public void setIcod_err(Integer pIcod_err) {
        this.icod_err = pIcod_err;
    }

    /**
     *
     * @return icod_warning
     */
    public Integer getIcod_warning() {
        return this.icod_warning;
    }

    /**
     *
     * @param pIcod_warning icod_warning value
     */
    public void setIcod_warning(Integer pIcod_warning) {
        this.icod_warning = pIcod_warning;
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
     * @return numeroUsage
     */
    public Integer getNumeroUsage() {
        return this.numeroUsage;
    }

    /**
     *
     * @param pNumeroUsage numeroUsage value
     */
    public void setNumeroUsage(Integer pNumeroUsage) {
        this.numeroUsage = pNumeroUsage;
    }



    /**
     *
     * @return postalAddressesToNormalize
     */
    public Set<PostalAddressToNormalizeDTO> getPostalAddressesToNormalize() {
        return this.postalAddressesToNormalize;
    }

    /**
     *
     * @param pPostalAddressesToNormalize postalAddressesToNormalize value
     */
    public void setPostalAddressesToNormalize(Set<PostalAddressToNormalizeDTO> pPostalAddressesToNormalize) {
        this.postalAddressesToNormalize = pPostalAddressesToNormalize;
    }

    /**
     *
     * @return preferee
     */
    public Boolean getPreferee() {
        return this.preferee;
    }

    /**
     *
     * @param pPreferee preferee value
     */
    public void setPreferee(Boolean pPreferee) {
        this.preferee = pPreferee;
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
     * @return scod_app_send
     */
    public String getScod_app_send() {
        return this.scod_app_send;
    }

    /**
     *
     * @param pScod_app_send scod_app_send value
     */
    public void setScod_app_send(String pScod_app_send) {
        this.scod_app_send = pScod_app_send;
    }

    /**
     *
     * @return scod_err_detaille
     */
    public String getScod_err_detaille() {
        return this.scod_err_detaille;
    }

    /**
     *
     * @param pScod_err_detaille scod_err_detaille value
     */
    public void setScod_err_detaille(String pScod_err_detaille) {
        this.scod_err_detaille = pScod_err_detaille;
    }

    /**
     *
     * @return scod_err_simple
     */
    public String getScod_err_simple() {
        return this.scod_err_simple;
    }

    /**
     *
     * @param pScod_err_simple scod_err_simple value
     */
    public void setScod_err_simple(String pScod_err_simple) {
        this.scod_err_simple = pScod_err_simple;
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
     * @return scode_pays
     */
    public String getScode_pays() {
        return this.scode_pays;
    }

    /**
     *
     * @param pScode_pays scode_pays value
     */
    public void setScode_pays(String pScode_pays) {
        this.scode_pays = pScode_pays;
    }

    /**
     *
     * @return scode_postal
     */
    public String getScode_postal() {
        return this.scode_postal;
    }

    /**
     *
     * @param pScode_postal scode_postal value
     */
    public void setScode_postal(String pScode_postal) {
        this.scode_postal = pScode_postal;
    }

    /**
     *
     * @return scode_province
     */
    public String getScode_province() {
        return this.scode_province;
    }

    /**
     *
     * @param pScode_province scode_province value
     */
    public void setScode_province(String pScode_province) {
        this.scode_province = pScode_province;
    }

    /**
     *
     * @return scomplement_adresse
     */
    public String getScomplement_adresse() {
        return this.scomplement_adresse;
    }

    /**
     *
     * @param pScomplement_adresse scomplement_adresse value
     */
    public void setScomplement_adresse(String pScomplement_adresse) {
        this.scomplement_adresse = pScomplement_adresse;
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
     * @return senvoi_postal
     */
    public String getSenvoi_postal() {
        return this.senvoi_postal;
    }

    /**
     *
     * @param pSenvoi_postal senvoi_postal value
     */
    public void setSenvoi_postal(String pSenvoi_postal) {
        this.senvoi_postal = pSenvoi_postal;
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
     * @return signature_creation
     */
    public String getSignature_creation() {
        return this.signature_creation;
    }

    /**
     *
     * @param pSignature_creation signature_creation value
     */
    public void setSignature_creation(String pSignature_creation) {
        this.signature_creation = pSignature_creation;
    }

    /**
     *
     * @return sindadr
     */
    public String getSindadr() {
        return this.sindadr;
    }

    /**
     *
     * @param pSindadr sindadr value
     */
    public void setSindadr(String pSindadr) {
        this.sindadr = pSindadr;
    }

    /**
     *
     * @return slocalite
     */
    public String getSlocalite() {
        return this.slocalite;
    }

    /**
     *
     * @param pSlocalite slocalite value
     */
    public void setSlocalite(String pSlocalite) {
        this.slocalite = pSlocalite;
    }

    /**
     *
     * @return sno_et_rue
     */
    public String getSno_et_rue() {
        return this.sno_et_rue;
    }

    /**
     *
     * @param pSno_et_rue sno_et_rue value
     */
    public void setSno_et_rue(String pSno_et_rue) {
        this.sno_et_rue = pSno_et_rue;
    }

    /**
     *
     * @return sraison_sociale
     */
    public String getSraison_sociale() {
        return this.sraison_sociale;
    }

    /**
     *
     * @param pSraison_sociale sraison_sociale value
     */
    public void setSraison_sociale(String pSraison_sociale) {
        this.sraison_sociale = pSraison_sociale;
    }

    /**
     *
     * @return ssignature_fonctionnel
     */
    public String getSsignature_fonctionnel() {
        return this.ssignature_fonctionnel;
    }

    /**
     *
     * @param pSsignature_fonctionnel ssignature_fonctionnel value
     */
    public void setSsignature_fonctionnel(String pSsignature_fonctionnel) {
        this.ssignature_fonctionnel = pSsignature_fonctionnel;
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
     * @return ssite_fonctionnel
     */
    public String getSsite_fonctionnel() {
        return this.ssite_fonctionnel;
    }

    /**
     *
     * @param pSsite_fonctionnel ssite_fonctionnel value
     */
    public void setSsite_fonctionnel(String pSsite_fonctionnel) {
        this.ssite_fonctionnel = pSsite_fonctionnel;
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
     * @return stype_invalidite
     */
    public String getStype_invalidite() {
        return this.stype_invalidite;
    }

    /**
     *
     * @param pStype_invalidite stype_invalidite value
     */
    public void setStype_invalidite(String pStype_invalidite) {
        this.stype_invalidite = pStype_invalidite;
    }

    /**
     *
     * @return sville
     */
    public String getSville() {
        return this.sville;
    }

    /**
     *
     * @param pSville sville value
     */
    public void setSville(String pSville) {
        this.sville = pSville;
    }

    /**
     *
     * @return usage_mediumdto
     */
    public Set<Usage_mediumDTO> getUsage_mediumdto() {
        return this.usage_mediumdto;
    }

    /**
     *
     * @param pUsage_mediumdto usage_mediumdto value
     */
    public void setUsage_mediumdto(Set<Usage_mediumDTO> pUsage_mediumdto) {
        this.usage_mediumdto = pUsage_mediumdto;
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
        /*PROTECTED REGION ID(toString_RpoWEDRWEeCGEoB0vWAi2A) ENABLED START*/
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
        buffer.append("sraison_sociale=").append(getSraison_sociale());
        buffer.append(",");
        buffer.append("scomplement_adresse=").append(getScomplement_adresse());
        buffer.append(",");
        buffer.append("sno_et_rue=").append(getSno_et_rue());
        buffer.append(",");
        buffer.append("slocalite=").append(getSlocalite());
        buffer.append(",");
        buffer.append("scode_postal=").append(getScode_postal());
        buffer.append(",");
        buffer.append("sville=").append(getSville());
        buffer.append(",");
        buffer.append("scode_pays=").append(getScode_pays());
        buffer.append(",");
        buffer.append("scode_province=").append(getScode_province());
        buffer.append(",");
        buffer.append("scode_medium=").append(getScode_medium());
        buffer.append(",");
        buffer.append("sstatut_medium=").append(getSstatut_medium());
        buffer.append(",");
        buffer.append("ssite_modification=").append(getSsite_modification());
        buffer.append(",");
        buffer.append("ssignature_modification=").append(getSsignature_modification());
        buffer.append(",");
        buffer.append("ddate_modification=").append(getDdate_modification());
        buffer.append(",");
        buffer.append("ssite_creation=").append(getSsite_creation());
        buffer.append(",");
        buffer.append("signature_creation=").append(getSignature_creation());
        buffer.append(",");
        buffer.append("ddate_creation=").append(getDdate_creation());
        buffer.append(",");
        buffer.append("sforcage=").append(getSforcage());
        buffer.append(",");
        buffer.append("sdescriptif_complementaire=").append(getSdescriptif_complementaire());
        buffer.append(",");
        buffer.append("sindadr=").append(getSindadr());
        buffer.append(",");
        buffer.append("icod_err=").append(getIcod_err());
        buffer.append(",");
        buffer.append("icod_warning=").append(getIcod_warning());
        buffer.append(",");
        buffer.append("icle_role=").append(getIcle_role());
        buffer.append(",");
        buffer.append("ikey_temp=").append(getIkey_temp());
        buffer.append(",");
        buffer.append("ddate_fonctionnel=").append(getDdate_fonctionnel());
        buffer.append(",");
        buffer.append("ssite_fonctionnel=").append(getSsite_fonctionnel());
        buffer.append(",");
        buffer.append("ssignature_fonctionnel=").append(getSsignature_fonctionnel());
        buffer.append(",");
        buffer.append("scod_err_simple=").append(getScod_err_simple());
        buffer.append(",");
        buffer.append("scod_err_detaille=").append(getScod_err_detaille());
        buffer.append(",");
        buffer.append("stype_invalidite=").append(getStype_invalidite());
        buffer.append(",");
        buffer.append("senvoi_postal=").append(getSenvoi_postal());
        buffer.append(",");
        buffer.append("denvoi_postal=").append(getDenvoi_postal());
        buffer.append(",");
        buffer.append("scod_app_send=").append(getScod_app_send());
        buffer.append(",");
        buffer.append("numeroUsage=").append(getNumeroUsage());
        buffer.append(",");
        buffer.append("preferee=").append(getPreferee());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_RpoWEDRWEeCGEoB0vWAi2A u m) ENABLED START*/
    public int hashCode() {
        return new HashCodeBuilder(17, 31). // two randomly chosen prime numbers
            // if deriving: appendSuper(super.hashCode()).
            append(sno_et_rue).
            append(scode_postal).
            append(sville).
            append(numeroUsage).
            append(scode_medium).
            append(sstatut_medium).
            append(scode_pays).
            toHashCode();
    }

    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (obj.getClass() != getClass())
            return false;

        PostalAddressDTO rhs = (PostalAddressDTO) obj;
        return new EqualsBuilder().
            // if deriving: appendSuper(super.equals(obj)).
            append(sno_et_rue, rhs.sno_et_rue).
            append(scode_postal, rhs.scode_postal).
            append(sville, rhs.sville).
            append(numeroUsage, rhs.numeroUsage).
            append(scode_medium, rhs.scode_medium).
            append(sstatut_medium, rhs.sstatut_medium).
            append(scode_pays, rhs.scode_pays).
            isEquals();
    }
    /*PROTECTED REGION END*/

}
