package com.afklm.rigui.entity.adresse;

/*PROTECTED REGION ID(_NGxTEDRPEeCGEoB0vWAi2A i) ENABLED START*/

// add not generated imports here

import com.afklm.rigui.util.Identifiable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/*PROTECTED REGION END*/


/**
 * <p>Title : PostalAddress.java</p>
 * BO PostalAddress
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Entity


@Table(name="ADR_POST")
public class PostalAddress implements Serializable, Identifiable {

	/*PROTECTED REGION ID(serialUID _NGxTEDRPEeCGEoB0vWAi2A) ENABLED START*/
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
	/** logger */
	private static final Log log = LogFactory.getLog(PostalAddress.class);
	/*PROTECTED REGION END*/


	/**
	 * sain
	 */
	@Id
	@GenericGenerator(name="ISEQ_ADR_POSTS", strategy = "com.afklm.rigui.util.StringSequenceGenerator",
			parameters = {
					@Parameter(name = "sequence_name", value = "ISEQ_ADR_POSTS")
			})
	@GeneratedValue(generator = "ISEQ_ADR_POSTS")
	@Column(name="SAIN", length=16, nullable=false, unique=true, updatable=false)
	private String sain;


	/**
	 * sgin
	 */
	@Column(name="SGIN", length=12, updatable=false)
	private String sgin;


	/**
	 * version
	 */
	@Version
	@Column(name="IVERSION", length=12, nullable=false)
	private Integer version;


	/**
	 * sraison_sociale
	 */
	@Column(name="SRAISON_SOCIALE", length=42)
	private String sraison_sociale;


	/**
	 * scomplement_adresse
	 */
	@Column(name="SCOMPLEMENT_ADRESSE", length=42)
	private String scomplement_adresse;


	/**
	 * sno_et_rue
	 */
	@Column(name="SNO_ET_RUE", length=42)
	private String sno_et_rue;


	/**
	 * slocalite
	 */
	@Column(name="SLOCALITE", length=42)
	private String slocalite;


	/**
	 * scode_postal
	 */
	@Column(name="SCODE_POSTAL", length=10)
	private String scode_postal;


	/**
	 * sville
	 */
	@Column(name="SVILLE", length=32)
	private String sville;


	/**
	 * scode_pays
	 */
	@Column(name="SCODE_PAYS", length=2)
	private String scode_pays;


	/**
	 * scode_province
	 */
	@Column(name="SCODE_PROVINCE", length=2)
	private String scode_province;


	/**
	 * scode_medium
	 */
	@Column(name="SCODE_MEDIUM", length=1, nullable=false)
	private String scode_medium;


	/**
	 * sstatut_medium
	 */
	@Column(name="SSTATUT_MEDIUM", length=1, nullable=false)
	private String sstatut_medium;


	/**
	 * ssite_modification
	 */
	@Column(name="SSITE_MODIFICATION", length=10)
	private String ssite_modification;


	/**
	 * ssignature_modification
	 */
	@Column(name="SSIGNATURE_MODIFICATION", length=16)
	private String ssignature_modification;


	/**
	 * ddate_modification
	 */
	@Column(name="DDATE_MODIFICATION")
	private Date ddate_modification;


	/**
	 * ssite_creation
	 */
	@Column(name="SSITE_CREATION", length=10, nullable=false)
	private String ssite_creation;


	/**
	 * signature_creation
	 */
	@Column(name="SSIGNATURE_CREATION", length=16, nullable=false)
	private String signature_creation;


	/**
	 * ddate_creation
	 */
	@Column(name="DDATE_CREATION", nullable=false)
	private Date ddate_creation;


	/**
	 * sforcage
	 */
	@Column(name="SFORCAGE", length=1)
	private String sforcage;


	/**
	 * sdescriptif_complementaire
	 */
	@Column(name="SDESCRIPTIF_COMPLEMENTAIRE", length=60)
	private String sdescriptif_complementaire;


	/**
	 * sindadr
	 */
	@Column(name="SINDADR", length=30)
	private String sindadr;


	/**
	 * icod_err
	 */
	@Column(name="ICOD_ERR", nullable=false)
	private Integer icod_err;


	/**
	 * icod_warning
	 */
	@Column(name="ICOD_WARNING")
	private Integer icod_warning;


	/**
	 * icle_role
	 */
	@Column(name="ICLE_ROLE", length=10)
	private Integer icle_role;


	/**
	 * ikey_temp
	 */
	@Column(name="IKEY_TEMP", length=10)
	private Integer ikey_temp;


	/**
	 * ddate_fonctionnel
	 */
	@Column(name="DDATE_FONCTIONNEL")
	private Date ddate_fonctionnel;


	/**
	 * ssite_fonctionnel
	 */
	@Column(name="SSITE_FONCTIONNEL", length=10)
	private String ssite_fonctionnel;


	/**
	 * ssignature_fonctionnel
	 */
	@Column(name="SSIGNATURE_FONCTIONNEL", length=16)
	private String ssignature_fonctionnel;


	/**
	 * scod_err_simple
	 */
	@Column(name="SCOD_ERR_SIMPLE", length=2)
	private String scod_err_simple;


	/**
	 * scod_err_detaille
	 */
	@Column(name="SCOD_ERR_DETAILLE", length=16)
	private String scod_err_detaille;


	/**
	 * stype_invalidite
	 */
	@Column(name="STYPE_INVALIDITE", length=1)
	private String stype_invalidite;


	/**
	 * senvoi_postal
	 */
	@Column(name="SENVOI_POSTAL", length=1)
	private String senvoi_postal;


	/**
	 * denvoi_postal
	 */
	@Column(name="DENVOI_POSTAL")
	private Date denvoi_postal;


	/**
	 * scod_app_send
	 */
	@Column(name="SCODE_APP_SEND", length=4)
	private String scod_app_send;


	/**
	 * reprocessError
	 */
	@Transient
	private String reprocessError;


	/**
	 * reprocessTime
	 */
	@Transient
	private Date reprocessTime;


	/**
	 * reprocessAppliId
	 */
	@Transient
	private String reprocessAppliId;


	/**
	 * toBeReprocessed
	 */
	@Transient
	private boolean toBeReprocessed;


	/**
	 * codeAppliSending
	 */
	@Transient
	private String codeAppliSending;


	/**
	 * formalizedAdrList
	 */
	@Transient
	private Set<String> formalizedAdrList;




	/**
	 * formalizedAdr
	 */
	// 1 -> *
	@OneToMany(mappedBy = "postalAddress")
	//@JoinColumn(name="SAIN_ADR", nullable=true, foreignKey = @javax.persistence.ForeignKey(name = "FK_FORMALIZED_ADR_ADR_POST"))
	private Set<FormalizedAdr> formalizedAdr;



	/**
	 * postalAddressesToNormalize
	 */
	// 1 <-> * 
	@OneToMany(mappedBy="postalAddress")
	private Set<PostalAddressToNormalize> postalAddressesToNormalize;


	/**
	 * usage_medium
	 */
	// 1 -> *
	@OneToMany()
	@JoinColumn(name="SAIN_ADR", nullable=true, foreignKey = @javax.persistence.ForeignKey(name = "FK_USAGE_MEDIUMS_ADR_POST"))
	private Set<Usage_medium> usage_medium;

	/**
	 * default constructor 
	 */
	public PostalAddress() {
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
	 * @param pReprocessError reprocessError
	 * @param pReprocessTime reprocessTime
	 * @param pReprocessAppliId reprocessAppliId
	 * @param pToBeReprocessed toBeReprocessed
	 * @param pCodeAppliSending codeAppliSending
	 * @param pFormalizedAdrList formalizedAdrList
	 */
	public PostalAddress(String pSain, String pSgin, Integer pVersion, String pSraison_sociale, String pScomplement_adresse, String pSno_et_rue, String pSlocalite, String pScode_postal, String pSville, String pScode_pays, String pScode_province, String pScode_medium, String pSstatut_medium, String pSsite_modification, String pSsignature_modification, Date pDdate_modification, String pSsite_creation, String pSignature_creation, Date pDdate_creation, String pSforcage, String pSdescriptif_complementaire, String pSindadr, Integer pIcod_err, Integer pIcod_warning, Integer pIcle_role, Integer pIkey_temp, Date pDdate_fonctionnel, String pSsite_fonctionnel, String pSsignature_fonctionnel, String pScod_err_simple, String pScod_err_detaille, String pStype_invalidite, String pSenvoi_postal, Date pDenvoi_postal, String pScod_app_send, String pReprocessError, Date pReprocessTime, String pReprocessAppliId, boolean pToBeReprocessed, String pCodeAppliSending, Set<String> pFormalizedAdrList) {
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
		this.reprocessError = pReprocessError;
		this.reprocessTime = pReprocessTime;
		this.reprocessAppliId = pReprocessAppliId;
		this.toBeReprocessed = pToBeReprocessed;
		this.codeAppliSending = pCodeAppliSending;
		this.formalizedAdrList = pFormalizedAdrList;
	}

	/**
	 *
	 * @return codeAppliSending
	 */
	public String getCodeAppliSending() {
		return this.codeAppliSending;
	}

	/**
	 *
	 * @param pCodeAppliSending codeAppliSending value
	 */
	public void setCodeAppliSending(String pCodeAppliSending) {
		this.codeAppliSending = pCodeAppliSending;
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
	 * @return formalizedAdr
	 */
	public Set<FormalizedAdr> getFormalizedAdrs() {
		return this.formalizedAdr;
	}

	/**
	 *
	 * @param pFormalizedAdr formalizedAdr value
	 */
	public void setFormalizedAdrs(Set<FormalizedAdr> pFormalizedAdr) {
		this.formalizedAdr = pFormalizedAdr;
	}

	/**
	 *
	 * @return formalizedAdrList
	 */
	public Set<String> getFormalizedAdrList() {
		return this.formalizedAdrList;
	}

	/**
	 *
	 * @param pFormalizedAdrList formalizedAdrList value
	 */
	public void setFormalizedAdrList(Set<String> pFormalizedAdrList) {
		this.formalizedAdrList = pFormalizedAdrList;
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
	 * @return postalAddressesToNormalize
	 */
	public Set<PostalAddressToNormalize> getPostalAddressesToNormalize() {
		return this.postalAddressesToNormalize;
	}

	/**
	 *
	 * @param pPostalAddressesToNormalize postalAddressesToNormalize value
	 */
	public void setPostalAddressesToNormalize(Set<PostalAddressToNormalize> pPostalAddressesToNormalize) {
		this.postalAddressesToNormalize = pPostalAddressesToNormalize;
	}

	/**
	 *
	 * @return reprocessAppliId
	 */
	public String getReprocessAppliId() {
		return this.reprocessAppliId;
	}

	/**
	 *
	 * @param pReprocessAppliId reprocessAppliId value
	 */
	public void setReprocessAppliId(String pReprocessAppliId) {
		this.reprocessAppliId = pReprocessAppliId;
	}

	/**
	 *
	 * @return reprocessError
	 */
	public String getReprocessError() {
		return this.reprocessError;
	}

	/**
	 *
	 * @param pReprocessError reprocessError value
	 */
	public void setReprocessError(String pReprocessError) {
		this.reprocessError = pReprocessError;
	}

	/**
	 *
	 * @return reprocessTime
	 */
	public Date getReprocessTime() {
		return this.reprocessTime;
	}

	/**
	 *
	 * @param pReprocessTime reprocessTime value
	 */
	public void setReprocessTime(Date pReprocessTime) {
		this.reprocessTime = pReprocessTime;
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
	 * @return toBeReprocessed
	 */
	public boolean getToBeReprocessed() {
		return this.toBeReprocessed;
	}

	/**
	 *
	 * @param pToBeReprocessed toBeReprocessed value
	 */
	public void setToBeReprocessed(boolean pToBeReprocessed) {
		this.toBeReprocessed = pToBeReprocessed;
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
		/*PROTECTED REGION ID(toString_NGxTEDRPEeCGEoB0vWAi2A) ENABLED START*/
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
		buffer.append("reprocessError=").append(getReprocessError());
		buffer.append(",");
		buffer.append("reprocessTime=").append(getReprocessTime());
		buffer.append(",");
		buffer.append("reprocessAppliId=").append(getReprocessAppliId());
		buffer.append(",");
		buffer.append("toBeReprocessed=").append(getToBeReprocessed());
		buffer.append(",");
		buffer.append("codeAppliSending=").append(getCodeAppliSending());
		buffer.append(",");
		buffer.append("formalizedAdrList=").append(getFormalizedAdrList());
		buffer.append("]");
		return buffer.toString();
	}

	/**
	 * setValeursNormalisation
	 * @param codErrSimple in String
	 * @param codErrDetaille in String
	 * @param validityValue in String
	 * @param forcedValue in String
	 */
	public void setValeursNormalisation(String codErrSimple, String codErrDetaille, String validityValue, String forcedValue) {
		/*PROTECTED REGION ID(_SL7T4HxtEeCAmbGwtfTi3Q) ENABLED START*/
		// Set Normalisation information
		scod_err_simple = codErrSimple;
		scod_err_detaille = codErrDetaille;
		sforcage = forcedValue;
		sstatut_medium = validityValue;
		// TODO
		if (validityValue.equalsIgnoreCase("INVALID"))
			stype_invalidite = "I";        // Invalidity set by Internal normalisation tool (RT, batch)
		else
			stype_invalidite = "";         // Empty, because the address is not set to Invalide

		log.debug( "    Invalidite Type = '" + stype_invalidite + "'");
		log.debug( "    Forcage Adresse = '" + sforcage + "'");
		/*PROTECTED REGION END*/
	}

	/**
	 * setValeursAdrNormalisation
	 * @param adrComp in String
	 * @param numStreet in String
	 * @param locality in String
	 * @param zipCode in String
	 * @param city in String
	 * @param state in String
	 * @param country in String
	 */
	public void setValeursAdrNormalisation(String adrComp, String numStreet, String locality, String zipCode, String city, String state, String country) {
		/*PROTECTED REGION ID(_oe94IHxtEeCAmbGwtfTi3Q) ENABLED START*/
		log.debug( "AdressePostale::SetValeursAdrNormalisation (and functionnal signature)");

		// Set address values with normalised address values
		scomplement_adresse = adrComp;
		sno_et_rue = numStreet;
		slocalite = locality;
		scode_postal = zipCode;
		sville = city;
		scode_province = state;
		scode_pays = country;

		// Update functionnal signature
		String sSite = getSiteFunctionnal();
		String sSignaAgent = getSignaAgentFunctionnal();
		Date currentTime = new Date();
		ssite_fonctionnel = sSite;
		ssignature_fonctionnel = sSignaAgent;
		setDdate_fonctionnel(currentTime);

		log.debug( "    - sSite " + sSite);
		log.debug( "    - sSignaAgent " + sSignaAgent);
		/*PROTECTED REGION END*/
	}

	/**
	 * getSiteFunctionnal
	 * @return The getSiteFunctionnal as <code>String</code>
	 */
	public String getSiteFunctionnal() {
		/*PROTECTED REGION ID(_9NmYcHxvEeCAmbGwtfTi3Q) ENABLED START*/
		if (ssite_fonctionnel != null && ssite_fonctionnel.length()>0)
		{
			return ssite_fonctionnel;
		}
		else
		if (ssite_creation != null && ssite_creation.length()>0)
		{
			return ssite_creation;
		}
		return null;
		/*PROTECTED REGION END*/
	}

	/**
	 * getSignaAgentFunctionnal
	 * @return The getSignaAgentFunctionnal as <code>String</code>
	 */
	public String getSignaAgentFunctionnal() {
		/*PROTECTED REGION ID(_BVOVIHxwEeCAmbGwtfTi3Q) ENABLED START*/
		if (ssignature_fonctionnel != null && ssignature_fonctionnel.length()>0)
		{
			return ssignature_fonctionnel;
		}
		else
		if (signature_creation != null && signature_creation.length()>0)
		{
			return signature_creation;
		}

		return null;
		/*PROTECTED REGION END*/
	}

	/**
	 * isTheSameFunctionnalAdr
	 * @param adresse in PostalAddress
	 * @return The isTheSameFunctionnalAdr as <code>boolean</code>
	 */
	public boolean isTheSameFunctionnalAdr(PostalAddress adresse) {
		/*PROTECTED REGION ID(_m2xDAH1kEeCAmbGwtfTi3Q) ENABLED START*/
		boolean result = false;
		if (LocalCompareCleanChaines(adresse.getScomplement_adresse(), getScomplement_adresse()) &&
				LocalCompareCleanChaines(adresse.getSno_et_rue(), getSno_et_rue()) &&
				LocalCompareCleanChaines(adresse.getSlocalite(), getSlocalite()) &&
				LocalCompareCleanChaines(adresse.getScode_postal(), getScode_postal()) &&
				LocalCompareCleanChaines(adresse.getSville(), getSville()) &&
				LocalCompareCleanChaines(adresse.getScode_province(), getScode_province()) &&
				LocalCompareCleanChaines(adresse.getScode_pays(), getScode_pays()))
		{
			result = true;
		}
		return result;
		/*PROTECTED REGION END*/
	}

	/**
	 * LocalCompareCleanChaines
	 * @param chaine1 in String
	 * @param chaine2 in String
	 * @return The LocalCompareCleanChaines as <code>boolean</code>
	 */
	public boolean LocalCompareCleanChaines(String chaine1, String chaine2) {
		/*PROTECTED REGION ID(_3HCVsX-XEeCV0v4ujvP1cA) ENABLED START*/
		boolean bRet = false;

		if (chaine1 == null && chaine2 == null) {
			bRet = true;
		} else if (chaine1 != null && chaine2 != null){
			// clean sChaine1
			String sChaineClean1;
			String sChaineTmp1 = chaine1.trim();
			sChaineClean1 = sChaineTmp1.replaceAll("[\\x00\\x0A\\x0D\\x1A]", "");

			// clean chaine2
			String sChaineClean2;
			String sChaineTmp2 = chaine2.trim();
			sChaineClean2 = sChaineTmp2.replaceAll("[\\x00\\x0A\\x0D\\x1A]", "");
			if (sChaineClean1.equals(sChaineClean2))
			{
				bRet = true;
			}
			else
			{
				bRet = false;
			}
		}
		return bRet;
		/*PROTECTED REGION END*/
	}

	/**
	 * hasOnlySecondaryUsage
	 * @return The hasOnlySecondaryUsage as <code>boolean</code>
	 */
	public boolean hasOnlySecondaryUsage() {
		/*PROTECTED REGION ID(_QN09UIHwEeCtut40RvtPWA) ENABLED START*/
		boolean bRet = false;

		int nbUsages = getUsage_medium().size();
		int nbSecUsages = countSecondaryUsageNb();

		// Control the twice numbers
		if (nbUsages == nbSecUsages && nbUsages > 0)
		{
			bRet = true;
		}
		return bRet;
		/*PROTECTED REGION END*/
	}

	/**
	 * countSecondaryUsageNb
	 * @return The countSecondaryUsageNb as <code>int</code>
	 */
	public int countSecondaryUsageNb() {
		/*PROTECTED REGION ID(_qQWEAIHwEeCtut40RvtPWA) ENABLED START*/
		int nbSecUsages = 0;
		for (Usage_medium usageCre : getUsage_medium()) {
			String sMetierTemp = usageCre.getScode_application();
			// iterator on secondary usage list in order to find usages
			for (String sSecMetier : (List<String>)getSecondaryUsageList()) {
				if (sMetierTemp.equalsIgnoreCase(sSecMetier))
				{
					nbSecUsages++;
				}
			}
		}
		return nbSecUsages;
		/*PROTECTED REGION END*/
	}

	/**
	 * getSecondaryUsageList
	 * @return The getSecondaryUsageList as <code>List</code>
	 */
	public List getSecondaryUsageList() {
		/*PROTECTED REGION ID(_9jUUEIHxEeCtut40RvtPWA) ENABLED START*/
		List<String> listAppliMetierSecondaire = new ArrayList<String>();
		return listAppliMetierSecondaire;
		/*PROTECTED REGION END*/
	}



	/**
	 * existeUsage
	 * @param metier in String
	 * @param numUsage in int
	 * @return The existeUsage as <code>boolean</code>
	 */
	public boolean existeUsage(String metier, int numUsage) {
		/*PROTECTED REGION ID(_ptJY4IIHEeCtut40RvtPWA) ENABLED START*/
		boolean bRet = false;
		Usage_medium usage = getUsage (metier, numUsage);
		if (usage != null) bRet = true;
		return bRet;
		/*PROTECTED REGION END*/
	}

	/**
	 * existeUsageIsis
	 * @param type in String
	 * @return The existeUsageIsis as <code>boolean</code>
	 */


	/**
	 * getUsage
	 * @param metier in String
	 * @param numUsage in int
	 * @return The getUsage as <code>Usage_medium</code>
	 */
	public Usage_medium getUsage(String metier, int numUsage) {
		/*PROTECTED REGION ID(_nVh8EIIZEeCtut40RvtPWA) ENABLED START*/
		Usage_medium usage = null;
		if (log.isDebugEnabled())
			log.debug("getUsage : " + metier + " et " + numUsage);
		for (Usage_medium usageCre : getUsage_medium()) {
			if (usageCre.getScode_application().equalsIgnoreCase(metier) &&
					usageCre.getInum().equals(numUsage))
			{
				log.debug("AdressePostale : GetUsage : Usage trouve !!!");
				usage = usageCre;
				break;
			}
		}
		return usage;
		/*PROTECTED REGION END*/
	}



	/*PROTECTED REGION ID(equals hash _NGxTEDRPEeCGEoB0vWAi2A) ENABLED START*/

	/**
	 * {@inheritDoc}
	 * @see Object#equals(Object)
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
		final PostalAddress other = (PostalAddress) obj;

		// TODO: writes or generates equals method

		return super.equals(other);
	}

	/**
	 * {@inheritDoc}
	 * @see Object#hashCode()
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

	/*PROTECTED REGION ID(_NGxTEDRPEeCGEoB0vWAi2A u m) ENABLED START*/
	// add your custom methods here if necessary

	/**
	 *
	 * @return postalAddressesToNormalize
	 */
	public PostalAddressToNormalize getPostalAddressToNormalize() {

		if (this.postalAddressesToNormalize == null || this.postalAddressesToNormalize.isEmpty()) {
			return null;
		}
		return new ArrayList<PostalAddressToNormalize>(postalAddressesToNormalize).get(0);
	}

	/**
	 *
	 * @param pPostalAddressesToNormalize
	 *            postalAddressesToNormalize value
	 */
	public void setPostalAddressToNormalize(PostalAddressToNormalize pPostalAddressToNormalize) {

		if (this.postalAddressesToNormalize == null) {
			this.postalAddressesToNormalize = new HashSet<PostalAddressToNormalize>();
		} else {
			this.postalAddressesToNormalize.clear();
		}
		this.postalAddressesToNormalize.add(pPostalAddressToNormalize);
	}

	/**
	 *
	 * @return postalAddressesToNormalize
	 */
	public FormalizedAdr getFormalizedAdr() {

		if (this.formalizedAdr == null || this.formalizedAdr.isEmpty()) {
			return null;
		}
		return new ArrayList<FormalizedAdr>(formalizedAdr).get(0);
	}

	/**
	 *
	 * @param pPostalAddressesToNormalize
	 *            postalAddressesToNormalize value
	 */
	public void setFormalizedAdr(FormalizedAdr pFormalizedAdr) {

		if (this.formalizedAdr == null) {
			this.formalizedAdr = new HashSet<FormalizedAdr>();
		} else {
			this.formalizedAdr.clear();
		}
		this.formalizedAdr.add(pFormalizedAdr);
	}

	@Override
	public String getId() {
		return sain;
	}


	/*PROTECTED REGION END*/

}
