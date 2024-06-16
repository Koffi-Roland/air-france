package com.airfrance.repind.entity.adresse;

/*PROTECTED REGION ID(_o6dz0DOcEeCokvyNKVv2PQ i) ENABLED START*/

// add not generated imports here

import com.airfrance.repind.entity.firme.Fonction;
import com.airfrance.repind.entity.firme.PersonneMorale;
import com.airfrance.repind.entity.individu.Individu;
import com.airfrance.repind.util.Identifiable;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/*PROTECTED REGION END*/


/**
 * <p>Title : Telecoms.java</p>
 * BO Telecoms
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Entity
@Table(name="TELECOMS")
public class Telecoms implements Serializable,Identifiable {

/*PROTECTED REGION ID(serialUID _o6dz0DOcEeCokvyNKVv2PQ) ENABLED START*/
   /**
   * Determines if a de-serialized file is compatible with this class.
   *
   * Maintainers must change this value if and only if the new version
   * of this class is not compatible with old versions. See Sun docs
   * for <a href=http://java.sun.com/j2se/1.5.0/docs/guide/serialization
   * /serialization/spec/class.html#4100> details. </a>
   *
   * Not necessary to include in first version of the class, but
   * included here as a reminder of its importance.
   */
    private static final long serialVersionUID = 1L;


    /**
     * country code avant normalisation, non present en BD.
     */
    @Transient    
    private String countryCode;
   
    /*PROTECTED REGION END*/

            
    /**
     * sain
     */
    @Id
    @GenericGenerator(name="ISEQ_TELECOMS", strategy = "com.airfrance.repind.util.StringSequenceGenerator",
    parameters = { 
    		@Parameter(name = "sequence_name", value = "ISEQ_TELECOMS") 
    })
	@GeneratedValue(generator = "ISEQ_TELECOMS")
    @Column(name="SAIN", length=16, nullable=false, unique=true, updatable=false)
    private String sain;
        
            
    /**
     * sgin
     */
    @Column(name="SGIN", length=12, updatable=false, insertable=false)
    private String sgin;
        
            
    /**
     * version
     */
    @Version
    @Column(name="IVERSION", length=12, nullable=false)
    private Integer version;
        
            
    /**
     * scode_medium
     */
    @Column(name="SCODE_MEDIUM", length=1)
    private String scode_medium;
        
            
    /**
     * sstatut_medium
     */
    @Column(name="SSTATUT_MEDIUM", length=1)
    private String sstatut_medium;
        
            
    /**
     * snumero
     */
    @Column(name="SNUMERO", length=15)
    private String snumero;
        
            
    /**
     * sdescriptif_complementaire
     */
    @Column(name="SDESCRIPTIF_COMPLEMENTAIRE", length=60)
    private String sdescriptif_complementaire;
        
            
    /**
     * sterminal
     */
    @Column(name="STERMINAL", length=1)
    private String sterminal;
        
            
    /**
     * scode_region
     */
    @Column(name="SCODE_REGION", length=4)
    private String scode_region;
        
            
    /**
     * sindicatif
     */
    @Column(name="SINDICATIF", length=4)
    private String sindicatif;
        
            
    /**
     * ssignature_modification
     */
    @Column(name="SSIGNATURE_MODIFICATION", length=16)
    private String ssignature_modification;
        
            
    /**
     * ssite_modification
     */
    @Column(name="SSITE_MODIFICATION", length=10)
    private String ssite_modification;
        
            
    /**
     * ddate_modification
     */
    @Column(name="DDATE_MODIFICATION")
    private Date ddate_modification;
        
            
    /**
     * ssignature_creation
     */
    @Column(name="SSIGNATURE_CREATION", length=16)
    private String ssignature_creation;
        
            
    /**
     * ssite_creation
     */
    @Column(name="SSITE_CREATION", length=10)
    private String ssite_creation;
        
            
    /**
     * ddate_creation
     */
    @Column(name="DDATE_CREATION")
    private Date ddate_creation;
        
    /**
     * sgin_pm
     */
    @Column(name="SGIN_PM", length=12, updatable=false, insertable=false)
    private String sgin_pm;

    /**
     * icle_role
     */
    @Column(name="ICLE_ROLE", length=10)
    private Integer icle_role;

    /**
     * icle_fonction
     */
    @Column(name="ICLE_FONCTION", length=10, updatable=false, insertable=false)
    private Integer icle_fonction;


    /**
     * ikey_temp
     */
    @Column(name="IKEY_TEMP", length=10)
    private Integer ikey_temp;
        
            
    /**
     * snormalized_country
     */
    @Column(name="SNORMALIZED_COUNTRY", length=4)
    private String snormalized_country;
        
            
    /**
     * snormalized_numero
     */
    @Column(name="SNORMALIZED_NUMERO", length=15)
    private String snormalized_numero;
        
            
    /**
     * sforcage
     */
    @Column(name="SFORCAGE", length=1)
    private String sforcage;
        
            
    /**
     * svalidation
     */
    @Column(name="SVALIDATION", length=1)
    private String svalidation;
        
            
    /**
     * snorm_nat_phone_number
     */
    @Column(name="SNORM_NAT_PHONE_NUMBER")
    private String snorm_nat_phone_number;
        
            
    /**
     * snorm_nat_phone_number_clean
     */
    @Column(name="SNORM_NAT_PHONE_NUMBER_CLEAN")
    private String snorm_nat_phone_number_clean;
        
            
    /**
     * snorm_inter_country_code
     */
    @Column(name="SNORM_INTER_COUNTRY_CODE")
    private String snorm_inter_country_code;
        
            
    /**
     * snorm_inter_phone_number
     */
    @Column(name="SNORM_INTER_PHONE_NUMBER")
    private String snorm_inter_phone_number;
        
            
    /**
     * snorm_terminal_type_detail
     */
    @Column(name="SNORM_TERMINAL_TYPE_DETAIL")
    private String snorm_terminal_type_detail;
        
            
    /**
     * isnormalized
     */
    @Column(name="ISNORMALIZED")
    private String isnormalized;
        
            
    /**
     * ddate_invalidation
     */
    @Column(name="DDATE_INVALIDATION")
    private Date ddate_invalidation;
            
    /**
     * fonction
     */
    // * <-> 1
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ICLE_FONCTION",referencedColumnName = "ICLE", nullable=true)
    @ForeignKey(name = "TELECOMS_FONCTION_FK")
    private Fonction fonction;
        
            
    /**
     * individu
     */
    // * <-> 1
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="SGIN", referencedColumnName = "SGIN", nullable=true)
    @ForeignKey(name = "FK_TELECOMS_SGIN_SGIN")
    private Individu individu;


    /**
     * personneMorale
     */
    // * <-> 1
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="SGIN_PM", referencedColumnName = "SGIN", nullable=true)
    @ForeignKey(name = "TEL_PM_FK")
    private PersonneMorale personneMorale;


    /**
     * usage_medium
     */
    // 1 -> *
    @OneToMany()
    @JoinColumn(name="SAIN_TEL", nullable=true)
    @ForeignKey(name = "FK_USAGE_MEDIUMS_TELECOMS")
    private Set<Usage_medium> usage_medium;
        
    /*PROTECTED REGION ID(_o6dz0DOcEeCokvyNKVv2PQ u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /** 
     * default constructor 
     */
    public Telecoms() {
    }
        
    /**
	 * full constructor
	 * 
	 * @param pSain
	 *            sain
	 * @param pSgin
	 *            sgin
	 * @param pVersion
	 *            version
	 * @param pScode_medium
	 *            scode_medium
	 * @param pSstatut_medium
	 *            sstatut_medium
	 * @param pSnumero
	 *            snumero
	 * @param pSdescriptif_complementaire
	 *            sdescriptif_complementaire
	 * @param pSterminal
	 *            sterminal
	 * @param pScode_region
	 *            scode_region
	 * @param pSindicatif
	 *            sindicatif
	 * @param pSsignature_modification
	 *            ssignature_modification
	 * @param pSsite_modification
	 *            ssite_modification
	 * @param pDdate_modification
	 *            ddate_modification
	 * @param pSsignature_creation
	 *            ssignature_creation
	 * @param pSsite_creation
	 *            ssite_creation
	 * @param pDdate_creation
	 *            ddate_creation
     * @param sgin_pm
     *            sgin_pm
	 * @param pIcle_role
	 *            icle_role
	 * @param pIkey_temp
	 *            ikey_temp
	 * @param pSnormalized_country
	 *            snormalized_country
	 * @param pSnormalized_numero
	 *            snormalized_numero
	 * @param pSforcage
	 *            sforcage
	 * @param pSvalidation
	 *            svalidation
	 * @param pSnorm_nat_phone_number
	 *            snorm_nat_phone_number
	 * @param pSnorm_nat_phone_number_clean
	 *            snorm_nat_phone_number_clean
	 * @param pSnorm_inter_country_code
	 *            snorm_inter_country_code
	 * @param pSnorm_inter_phone_number
	 *            snorm_inter_phone_number
	 * @param pSnorm_terminal_type_detail
	 *            snorm_terminal_type_detail
	 * @param pIsnormalized
	 *            isnormalized
	 * @param pDdate_invalidation
	 *            ddate_invalidation
	 */
    public Telecoms(String pSain, String pSgin, Integer pVersion, String pScode_medium, String pSstatut_medium, String pSnumero, String pSdescriptif_complementaire, String pSterminal, String pScode_region, String pSindicatif, String pSsignature_modification, String pSsite_modification, Date pDdate_modification, String pSsignature_creation, String pSsite_creation, Date pDdate_creation, String sgin_pm, Integer pIcle_role, Integer icle_fonction, Integer pIkey_temp, String pSnormalized_country, String pSnormalized_numero, String pSforcage, String pSvalidation, String pSnorm_nat_phone_number, String pSnorm_nat_phone_number_clean, String pSnorm_inter_country_code, String pSnorm_inter_phone_number, String pSnorm_terminal_type_detail, String pIsnormalized, Date pDdate_invalidation) {
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
        this.sgin_pm = sgin_pm;
        this.icle_role = pIcle_role;
        this.icle_fonction = icle_fonction;
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
     * @return fonction
     */
    public Fonction getFonction() {
        return this.fonction;
    }

    /**
     *
     * @param pFonction fonction value
     */
    public void setFonction(Fonction pFonction) {
        this.fonction = pFonction;
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
     * @param pIcle_fonction icle_fonction value
     */
    public void setIcle_fonction(Integer pIcle_fonction) {
        this.icle_fonction = pIcle_fonction;
    }

    /**
     *
     * @return icle_fonction
     */
    public Integer getIcle_fonction() {
        return this.icle_fonction;
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
     * @return individu
     */
    public Individu getIndividu() {
        return this.individu;
    }

    /**
     *
     * @param pIndividu individu value
     */
    public void setIndividu(Individu pIndividu) {
        this.individu = pIndividu;
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
    public PersonneMorale getPersonneMorale() {
        return this.personneMorale;
    }

    /**
     *
     * @param pPersonneMorale personneMorale value
     */
    public void setPersonneMorale(PersonneMorale pPersonneMorale) {
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
     * @return sgin_pm
     */
    public String getSgin_pm() {
        return this.sgin_pm;
    }

    /**
     *
     * @param pSgin_pm sgin_pm value
     */
    public void setSgin_pm(String pSgin_pm) {
        this.sgin_pm = pSgin_pm;
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
     * @return usage_medium
     */
    public Set<Usage_medium> getUsage_medium() {
        return this.usage_medium;
    }

    /**
     *
     * @param pUsage_medium usage_medium value
     */
    public void setUsage_medium(Set<Usage_medium> pUsage_medium) {
        this.usage_medium = pUsage_medium;
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
        /*PROTECTED REGION ID(toString_o6dz0DOcEeCokvyNKVv2PQ) ENABLED START*/
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

     
    
    /*PROTECTED REGION ID(equals hash _o6dz0DOcEeCokvyNKVv2PQ) ENABLED START*/

    /**
     * {@inheritDoc}
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Telecoms other = (Telecoms) obj;

        // TODO: writes or generates equals method
        
        return super.equals(other);
    }
  
    /**
     * {@inheritDoc}
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int result = super.hashCode();

        // TODO: writes or generates hashcode method

        return result;
    }
    
    /*PROTECTED REGION END*/
    
    /**
     * Generated implementation method for hashCode
     * You can stop calling it in the hashCode() generated in protected region if necessary
     * @return hashcode
     */
    private int hashCodeImpl() {
        return super.hashCode();
    }

    /**
     * Generated implementation method for equals
     * You can stop calling it in the equals() generated in protected region if necessary
     * @return if param equals the current object
     * @param obj Object to compare with current
     */
    private boolean equalsImpl(Object obj) {
        return super.equals(obj);
    }

    /*PROTECTED REGION ID(_o6dz0DOcEeCokvyNKVv2PQ u m) ENABLED START*/
    // add your custom methods here if necessary
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCountryCode() {
		return countryCode;
	}
    /*PROTECTED REGION END*/

	@Override
	public String getId() {
		return sain;
	}
    
}
