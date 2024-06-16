package com.airfrance.repind.dto.individu;

/*PROTECTED REGION ID(_VDY-cPcREd-Kx8TJdz7fGw i - Tr) ENABLED START*/

//add not generated imports here

import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dto.adresse.EmailTransform;
import com.airfrance.repind.dto.adresse.PostalAddressTransform;
import com.airfrance.repind.dto.adresse.TelecomsDTO;
import com.airfrance.repind.dto.adresse.TelecomsTransform;
import com.airfrance.repind.dto.delegation.DelegationDataTransform;
import com.airfrance.repind.dto.external.ExternalIdentifierDTO;
import com.airfrance.repind.dto.external.ExternalIdentifierTransform;
import com.airfrance.repind.dto.preference.PreferenceDTO;
import com.airfrance.repind.dto.preference.PreferenceTransform;
import com.airfrance.repind.dto.profil.Profil_mereTransform;
import com.airfrance.repind.dto.profil.ProfilsDTO;
import com.airfrance.repind.dto.profil.ProfilsTransform;
import com.airfrance.repind.dto.role.RoleContratsDTO;
import com.airfrance.repind.dto.role.RoleContratsTransform;
import com.airfrance.repind.entity.adresse.Telecoms;
import com.airfrance.repind.entity.external.ExternalIdentifier;
import com.airfrance.repind.entity.individu.CommunicationPreferences;
import com.airfrance.repind.entity.individu.Individu;
import com.airfrance.repind.entity.individu.IndividuLight;
import com.airfrance.repind.entity.individu.MarketLanguage;
import com.airfrance.repind.entity.preference.Preference;
import com.airfrance.repind.fonetik.PhEntree;
import com.airfrance.repind.fonetik.PhonetikInput;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>Title : IndividuTransform.java</p>
 * transformation bo <-> dto pour un(e) Individu
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class IndividuTransform {

    /*PROTECTED REGION ID(_VDY-cPcREd-Kx8TJdz7fGw u var - Tr) ENABLED START*/
	private static Log LOGGER  = LogFactory.getLog(IndividuTransform.class);
	
	private static final String NOT_NULL = "NOTNULL";
	/*PROTECTED REGION END*/
    
    /**
     * private constructor
     */
    private IndividuTransform() {
    }
    
    /**
     * dto -> bo for a Individu
     * @param individuDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static Individu dto2BoLight(IndividuDTO individuDTO) throws JrafDomainException {
        // instanciation du BO
        Individu individu = new Individu();
        dto2BoLight(individuDTO, individu);

        // on retourne le BO
        return individu;
    }

    /**
     * dto -> bo for a individu
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param individuDTO dto
     * @param individu bo
     */
    public static void dto2BoLight(IndividuDTO individuDTO, Individu individu) {
    
        /*PROTECTED REGION ID(dto2BoLight_VDY-cPcREd-Kx8TJdz7fGw) ENABLED START*/
        
        dto2BoLightImpl(individuDTO,individu);
        
        /*PROTECTED REGION END*/
    }
    
    /**
     * dto -> bo for a individu
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param individuDTO dto
     * @param individu bo
     */
    public static void dto2BoLightForUpdate(IndividuDTO individuDTO, Individu individu) {
    
        /*PROTECTED REGION ID(dto2BoLight_VDY-cPcREd-Kx8Tkdz7fGw) ENABLED START*/
        
        dto2BoLightImplForUpdate(individuDTO,individu, true);
        
        /*PROTECTED REGION END*/
    }
    
    /**
     * dto -> bo implementation for a individu
     * @param individuDTO dto
     * @param individu bo
     */
    private static void dto2BoLightImpl(IndividuDTO individuDTO, Individu individu){
    
    	dto2BoLightImplForUpdate(individuDTO, individu, false);
    }

    /**
     * dto -> bo implementation for a individu for update
     * @param individuDTO dto
     * @param individu bo
     */
    private static void dto2BoLightImplForUpdate(IndividuDTO individuDTO, Individu individu, boolean update){
    
        // property of IndividuDTO
        individu.setSgin(individuDTO.getSgin());
        individu.setType(individuDTO.getType());
        individu.setVersion(individuDTO.getVersion());
        individu.setCivilite(individuDTO.getCivilite());
        individu.setMotDePasse(individuDTO.getMotDePasse());
        individu.setNom(individuDTO.getNom());
        individu.setAlias(individuDTO.getAlias());
        individu.setPrenom(individuDTO.getPrenom());
        individu.setSecondPrenom(individuDTO.getSecondPrenom());
        individu.setAliasPrenom(individuDTO.getAliasPrenom());
        individu.setSexe(individuDTO.getSexe());
        individu.setIdentifiantPersonnel(individuDTO.getIdentifiantPersonnel());
        individu.setDateNaissance(individuDTO.getDateNaissance());
        individu.setStatutIndividu(individuDTO.getStatutIndividu());
        individu.setCodeTitre(individuDTO.getCodeTitre());
        individu.setNationalite(individuDTO.getNationalite());
        individu.setAutreNationalite(individuDTO.getAutreNationalite());
        individu.setNonFusionnable(individuDTO.getNonFusionnable());

        if (StringUtils.isNotEmpty(individuDTO.getSiteCreation())) {
        	individu.setSiteCreation(individuDTO.getSiteCreation());
        }
        if (StringUtils.isNotEmpty(individuDTO.getSignatureCreation())) {
        	individu.setSignatureCreation(individuDTO.getSignatureCreation());
        }
        if (individuDTO.getDateCreation() != null) {
        	individu.setDateCreation(individuDTO.getDateCreation());
        }
    	
        individu.setSiteModification(individuDTO.getSiteModification());
        individu.setSignatureModification(individuDTO.getSignatureModification());
        individu.setDateModification(individuDTO.getDateModification());
        individu.setSiteFraudeur(individuDTO.getSiteFraudeur());
        individu.setSignatureFraudeur(individuDTO.getSignatureFraudeur());
        individu.setDateModifFraudeur(individuDTO.getDateModifFraudeur());
        individu.setSiteMotDePasse(individuDTO.getSiteMotDePasse());
        individu.setSignatureMotDePasse(individuDTO.getSignatureMotDePasse());
        individu.setDateModifMotDePasse(individuDTO.getDateModifMotDePasse());
        individu.setFraudeurCarteBancaire(individuDTO.getFraudeurCarteBancaire());
        individu.setTierUtiliseCommePiege(individuDTO.getTierUtiliseCommePiege());
        individu.setAliasNom1(individuDTO.getAliasNom1());
        individu.setAliasNom2(individuDTO.getAliasNom2());
        individu.setAliasPrenom1(individuDTO.getAliasPrenom1());
        individu.setAliasPrenom2(individuDTO.getAliasPrenom2());
        individu.setAliasCivilite1(individuDTO.getAliasCivilite1());
        individu.setAliasCivilite2(individuDTO.getAliasCivilite2());
        individu.setIndicNomPrenom(individuDTO.getIndicNomPrenom());
        individu.setIndicNom(individuDTO.getIndicNom());
        individu.setIndcons(individuDTO.getIndcons());
        individu.setGinFusion(individuDTO.getGinFusion());
        individu.setDateFusion(individuDTO.getDateFusion());
        individu.setProvAmex(individuDTO.getProvAmex());
        individu.setCieGest(individuDTO.getCieGest());
    
    }


    /**
     * bo -> dto for a individu
     * @param pIndividu bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static IndividuDTO bo2DtoLight(Individu pIndividu) throws JrafDomainException {
        // instanciation du DTO
        IndividuDTO individuDTO = new IndividuDTO();
        bo2DtoLight(pIndividu, individuDTO);
        // on retourne le dto
        return individuDTO;
    }


    /**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param individu bo
     * @param individuDTO dto
     */
    public static void bo2DtoLight(
        Individu individu,
        IndividuDTO individuDTO) {

        /*PROTECTED REGION ID(bo2DtoLight_VDY-cPcREd-Kx8TJdz7fGw) ENABLED START*/

        bo2DtoLightImpl(individu, individuDTO);

        /*PROTECTED REGION END*/

    }

    public static IndividuDTO bo2DtoLightWithProfil(Individu pIndividu) throws JrafDomainException {
        // instanciation du DTO
        IndividuDTO individuDTO = new IndividuDTO();
        bo2DtoLightImplWithProfil(pIndividu, individuDTO);
		individuDTO.setNomSC(pIndividu.getNomSC());
		individuDTO.setPrenomSC(pIndividu.getPrenomSC());
        // on retourne le dto
        return individuDTO;
    }

    
    public static void bo2DtoLightImplWithProfil(
        Individu individu,
        IndividuDTO individuDTO) {

        /*PROTECTED REGION ID(bo2DtoLight_VDY-cPcREd-Kx8TJdz7fGw) ENABLED START*/

        bo2DtoLightImpl(individu, individuDTO);
        

		if(individu.getProfils() != null ) {
			try {
				individuDTO.setProfilsdto(ProfilsTransform.bo2DtoLight(individu.getProfils()));
			} catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}

        /*PROTECTED REGION END*/

    }
    /**
     * Transform a business object to DTO. Implementation method
     * @param individu bo
     * @param individuDTO dto
     */
    private static void bo2DtoLightImpl(Individu individu,
        IndividuDTO individuDTO){
    

        // simple properties
        individuDTO.setSgin(individu.getSgin());
        individuDTO.setType(individu.getType());
        individuDTO.setVersion(individu.getVersion());
        individuDTO.setCivilite(individu.getCivilite());
        individuDTO.setMotDePasse(individu.getMotDePasse());
        individuDTO.setNom(individu.getNom());
        individuDTO.setAlias(individu.getAlias());
        individuDTO.setPrenom(individu.getPrenom());
        individuDTO.setSecondPrenom(individu.getSecondPrenom());
        individuDTO.setAliasPrenom(individu.getAliasPrenom());
        individuDTO.setSexe(individu.getSexe());
        individuDTO.setIdentifiantPersonnel(individu.getIdentifiantPersonnel());
        individuDTO.setDateNaissance(individu.getDateNaissance());
        individuDTO.setStatutIndividu(individu.getStatutIndividu());
        individuDTO.setCodeTitre(individu.getCodeTitre());
        individuDTO.setNationalite(individu.getNationalite());
        individuDTO.setAutreNationalite(individu.getAutreNationalite());
        individuDTO.setNonFusionnable(individu.getNonFusionnable());
        individuDTO.setSiteCreation(individu.getSiteCreation());
        individuDTO.setSignatureCreation(individu.getSignatureCreation());
        individuDTO.setDateCreation(individu.getDateCreation());
        individuDTO.setSiteModification(individu.getSiteModification());
        individuDTO.setSignatureModification(individu.getSignatureModification());
        individuDTO.setDateModification(individu.getDateModification());
        individuDTO.setSiteFraudeur(individu.getSiteFraudeur());
        individuDTO.setSignatureFraudeur(individu.getSignatureFraudeur());
        individuDTO.setDateModifFraudeur(individu.getDateModifFraudeur());
        individuDTO.setSiteMotDePasse(individu.getSiteMotDePasse());
        individuDTO.setSignatureMotDePasse(individu.getSignatureMotDePasse());
        individuDTO.setDateModifMotDePasse(individu.getDateModifMotDePasse());
        individuDTO.setFraudeurCarteBancaire(individu.getFraudeurCarteBancaire());
        individuDTO.setTierUtiliseCommePiege(individu.getTierUtiliseCommePiege());
        individuDTO.setAliasNom1(individu.getAliasNom1());
        individuDTO.setAliasNom2(individu.getAliasNom2());
        individuDTO.setAliasPrenom1(individu.getAliasPrenom1());
        individuDTO.setAliasPrenom2(individu.getAliasPrenom2());
        individuDTO.setAliasCivilite1(individu.getAliasCivilite1());
        individuDTO.setAliasCivilite2(individu.getAliasCivilite2());
        individuDTO.setIndicNomPrenom(individu.getIndicNomPrenom());
        individuDTO.setIndicNom(individu.getIndicNom());
        individuDTO.setIndcons(individu.getIndcons());
        individuDTO.setGinFusion(individu.getGinFusion());
        individuDTO.setDateFusion(individu.getDateFusion());
        individuDTO.setProvAmex(individu.getProvAmex());
        individuDTO.setCieGest(individu.getCieGest());
    
    }
    
    /*PROTECTED REGION ID(_VDY-cPcREd-Kx8TJdz7fGw u m - Tr) ENABLED START*/
	
    /**
	 * Transform an Individual with all its links
	 */
	public static void dto2Bo(IndividuDTO individuDTO, Individu individu) throws InvalidParameterException {
		if(individuDTO != null){
			dto2BoLight(individuDTO, individu);
			individu.setNomSC(individuDTO.getNomSC());
			individu.setPrenomSC(individuDTO.getPrenomSC());
			dto2BoLink(individuDTO, individu);
		}
	}

	public static void dto2BoForUpdate(IndividuDTO individuDTO, Individu individu) throws InvalidParameterException {
		dto2BoLightForUpdate(individuDTO, individu);
		individu.setNomSC(individuDTO.getNomSC());
		individu.setPrenomSC(individuDTO.getPrenomSC());
		dto2BoLinkForUpdate(individuDTO, individu);
	}
	
	public static void dto2BoAccount(IndividuDTO individuDTO, Individu individu) throws InvalidParameterException {
		dto2BoLight(individuDTO, individu);
		individu.setNomSC(individuDTO.getNomSC());
		individu.setPrenomSC(individuDTO.getPrenomSC());
		dto2BoLinkAccount(individuDTO, individu);
	}

    /**
	 * Transform an Individual with all its links from ProspectDTO
	 */
/*	
	public static void dto2Bo(ProspectDTO prospectDTO, Individu individu) throws InvalidParameterException {
		dto2BoLight(prospectDTO, individu);
		individu.setNomSC(prospectDTO.getLastNameSC());
		individu.setPrenomSC(prospectDTO.getFirstNameSC());
		dto2BoLink(prospectDTO, individu);
	}
*/	
	
	/**
	 * Transform an Individual with all its links
	 */
	public static void bo2Dto(Individu individu, IndividuDTO individuDTO) throws JrafDomainException {
		bo2Dto(individu, individuDTO, true);
	}
	public static void bo2Dto(Individu individu, IndividuDTO individuDTO, boolean isFBRecognitionActivate) throws JrafDomainException {
		bo2DtoLight(individu, individuDTO);
		individuDTO.setNomSC(individu.getNomSC());
		individuDTO.setPrenomSC(individu.getPrenomSC());
		bo2DtoLink(individu, individuDTO, isFBRecognitionActivate);
	}
	
	public static void bo2DtoForUpdate(Individu individu, IndividuDTO individuDTO) throws JrafDomainException {
		bo2DtoLight(individu, individuDTO);
		individuDTO.setNomSC(individu.getNomSC());
		individuDTO.setPrenomSC(individu.getPrenomSC());
		bo2DtoLinkForUpdate(individu, individuDTO);
	}
	
	public static void bo2DtoAccount(Individu individu, IndividuDTO individuDTO) throws JrafDomainException {
		bo2DtoLight(individu, individuDTO);
		individuDTO.setNomSC(individu.getNomSC());
		individuDTO.setPrenomSC(individu.getPrenomSC());
		bo2DtoLinkAccount(individu, individuDTO);
	}
	
	/**
	 * Transform an Individual with the links needed to a member
	 */
	public static void bo2DtoMember(Individu individu, IndividuDTO individuDTO, boolean isFBRecognitionActivate) throws JrafDomainException {
		bo2DtoLight(individu, individuDTO);
		individuDTO.setNomSC(individu.getNomSC());
		individuDTO.setPrenomSC(individu.getPrenomSC());
		bo2DtoLinkMember(individu, individuDTO, isFBRecognitionActivate);
	}

	/**
	 * Transform an Individual with all its links
	 */
	public static void bo2DtoSearch(Individu individu, IndividuDTO individuDTO) throws JrafDomainException {
		bo2DtoLight(individu, individuDTO);
		if (individu.getNomSC()==null)
			individu.setNomSC(individu.getNom());
		individuDTO.setNomSC(individu.getNomSC());
		if (individu.getPrenomSC()==null)
			individu.setPrenomSC(individu.getPrenom());
		individuDTO.setPrenomSC(individu.getPrenomSC());
		bo2DtoLinkSearch(individu, individuDTO);
	}

	/**
	 * Transform an Individual with all its links
	 */
	public static Individu dto2Bo(IndividuDTO individuDTO) throws JrafDomainException {
		//instanciation du BO
		Individu individu = new Individu();
		dto2Bo(individuDTO, individu);

		// on retourne le BO
		return individu;
	}

	public static Individu dto2BoForUpdate(IndividuDTO individuDTO) throws JrafDomainException {
		//instanciation du BO
		Individu individu = new Individu();
		dto2BoForUpdate(individuDTO, individu);

		// on retourne le BO
		return individu;
	}
	
	public static Individu dto2BoAccount(IndividuDTO individuDTO) throws JrafDomainException {
		//instanciation du BO
		Individu individu = new Individu();
		dto2BoAccount(individuDTO, individu);

		// on retourne le BO
		return individu;
	}

	/**
	 * Transform an Individual with all its links from a ProspectDTO
	 */
/*	
	public static Individu dto2Bo(ProspectDTO prospectDTO) throws JrafDomainException {
		//instanciation du BO
		Individu individu = new Individu();
		dto2Bo(prospectDTO, individu);

		// on retourne le BO
		return individu;
	}
*/

	/**
	 * Transform an Individual with all its links
	 */
	public static IndividuDTO bo2Dto(Individu individu) throws JrafDomainException {
		return bo2Dto(individu, true);
	}
	public static IndividuDTO bo2Dto(Individu individu, boolean isFBRecognitionActivate) throws JrafDomainException {
		// instanciation du DTO
		IndividuDTO individuDTO = new IndividuDTO();
		bo2Dto(individu, individuDTO, isFBRecognitionActivate);
		// on retourne le dto
		return individuDTO;
	}
	
	public static IndividuDTO bo2DtoForUpdate(Individu individu) throws JrafDomainException {
		// instanciation du DTO
		IndividuDTO individuDTO = new IndividuDTO();
		bo2DtoForUpdate(individu, individuDTO);
		// on retourne le dto
		return individuDTO;
	}
	
	/**
	 * Transform an Individual with all its links
	 */
	public static IndividuDTO bo2DtoAccount(Individu individu) throws JrafDomainException {
		// instanciation du DTO
		IndividuDTO individuDTO = new IndividuDTO();
		bo2DtoAccount(individu, individuDTO);
		// on retourne le dto
		return individuDTO;
	}
	
	/**
	 * Transform an Individual with all its links
	 */
	public static IndividuDTO bo2DtoMember(Individu individu) throws JrafDomainException {
		return bo2DtoMember(individu, true);
	}
	public static IndividuDTO bo2DtoMember(Individu individu, boolean isFBRecognitionActivate) throws JrafDomainException {
		// instanciation du DTO
		IndividuDTO individuDTO = new IndividuDTO();
		bo2DtoMember(individu, individuDTO, isFBRecognitionActivate);
		// on retourne le dto
		return individuDTO;
	}
	
	
	public static IndividuDTO bo2DtoSearch(Individu individu) throws JrafDomainException {
		// instanciation du DTO
		IndividuDTO individuDTO = new IndividuDTO();
		bo2DtoSearch(individu, individuDTO);
		// on retourne le dto
		return individuDTO;
	}
	
	/**
	 * Transform an Individual with AccountData and Telecoms links
	 */
	public static IndividuDTO bo2DtoForDelegation(Individu individu) throws JrafDomainException {
		IndividuDTO individuDTO = new IndividuDTO();
		bo2DtoLight(individu, individuDTO);
		bo2DtoLinkForDelegation(individu, individuDTO);
		return individuDTO;
	}

	/**
	 * @param individuDTO
	 * @param individu
	 */
	private static void dto2BoLink(IndividuDTO individuDTO, Individu individu) {
		if(individuDTO.getEmaildto() != null) {
			try {
				individu.setEmail(EmailTransform.dto2Bo(individuDTO.getEmaildto()));
			} catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}
		if(individuDTO.getTelecoms() != null ) {
			try {
				individu.setTelecoms(TelecomsTransform.dto2Bo(individuDTO.getTelecoms()));
			} catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}
		if(individuDTO.getPostaladdressdto() != null ) {
			try {
				individu.setPostaladdress(PostalAddressTransform.dto2Bo(individuDTO.getPostaladdressdto()));
			} catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}
		if(individuDTO.getRolecontratsdto() != null ) {
			try {
				individu.setRolecontrats(RoleContratsTransform.dto2Bo(individuDTO.getRolecontratsdto()));
			} catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}
		if(individuDTO.getProfil_meredto() != null ) {
			try {
				individu.setProfil_mere(Profil_mereTransform.dto2Bo(individuDTO.getProfil_meredto()));
			} catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}
		if(individuDTO.getProfilsdto() != null ) {
			try {
				individu.setProfils(ProfilsTransform.dto2BoLight(individuDTO.getProfilsdto()));
			} catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}
		if(individuDTO.getCommunicationpreferencesdto() != null ) {
			try {
				individu.setCommunicationpreferences(CommunicationPreferencesTransform.dto2Bo(individuDTO.getCommunicationpreferencesdto()));
			} catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}
		if(individuDTO.getPrefilledNumbers() != null ) {
			individu.setPrefilledNumbers(PrefilledNumbersTransform.dto2BoLight(individuDTO.getPrefilledNumbers()));
		}
		if(individuDTO.getDelegateListDTO() != null ) {
			try {
				individu.setDelegateList(DelegationDataTransform.dto2Bo(individuDTO.getDelegateListDTO()));
			} catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}
		if(individuDTO.getDelegatorListDTO() != null ) {
			try {
				individu.setDelegatorList(DelegationDataTransform.dto2Bo(individuDTO.getDelegatorListDTO()));
			} catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}
		if(individuDTO.getAccountdatadto() != null) {
			try {
				individu.setAccountData(AccountDataTransform.dto2BoLight(individuDTO.getAccountdatadto()));
			} catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}
		if(individuDTO.getExternalIdentifierList() != null) {
			try {
				Set<ExternalIdentifier> externalIdSet = new HashSet<ExternalIdentifier>();
				for(ExternalIdentifierDTO externalIdentifierDTOLoop : individuDTO.getExternalIdentifierList()) {
					externalIdSet.add(ExternalIdentifierTransform.dto2BoLight(externalIdentifierDTOLoop));
				}
				individu.setExternalIdentifierList(externalIdSet);
			} catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}
		if(individuDTO.getAlertDTO() != null) {
			try {
				individu.setAlert(AlertTransform.dto2BoLight(individuDTO.getAlertDTO()));
			} catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}
		if(individuDTO.getPreferenceDTO() != null) {
			try {

				Set<Preference> prefSet = new HashSet<Preference>();
				for(PreferenceDTO prefDTOLoop : individuDTO.getPreferenceDTO()) {
					prefSet.add(PreferenceTransform.dto2BoLight(prefDTOLoop));
				}
				individu.setPreference(prefSet);
			} catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}
	}

	private static void dto2BoLinkForUpdate(IndividuDTO individuDTO, Individu individu) {
		if(individuDTO.getEmaildto() != null ) {
			try {
				individu.setEmail(EmailTransform.dto2Bo(individuDTO.getEmaildto()));
			} catch (JrafDomainException e) {
				LOGGER.error(e);
			}
		}
		if(individuDTO.getTelecoms() != null ) {
			try {
				individu.setTelecoms(TelecomsTransform.dto2Bo(individuDTO.getTelecoms()));
			} catch (JrafDomainException e) {
				LOGGER.error(e);
			}
		}
		if(individuDTO.getPostaladdressdto() != null ) {
			try {
				individu.setPostaladdress(PostalAddressTransform.dto2Bo(individuDTO.getPostaladdressdto()));
			} catch (JrafDomainException e) {
				LOGGER.error(e);
			}
		}
		if(individuDTO.getRolecontratsdto() != null ) {
			try {
				individu.setRolecontrats(RoleContratsTransform.dto2Bo(individuDTO.getRolecontratsdto()));
			} catch (JrafDomainException e) {
				LOGGER.error(e);
			}
		}
		if(individuDTO.getProfil_meredto() != null ) {
			try {
				individu.setProfil_mere(Profil_mereTransform.dto2Bo(individuDTO.getProfil_meredto()));
			} catch (JrafDomainException e) {
				LOGGER.error(e);
			}
		}
		if(individuDTO.getProfilsdto() != null ) {
			try {
				individu.setProfils(ProfilsTransform.dto2BoLight(individuDTO.getProfilsdto()));
			} catch (JrafDomainException e) {
				LOGGER.error(e);
			}
		}
		if(individuDTO.getCommunicationpreferencesdto() != null ) {
			try {
				individu.setCommunicationpreferences(CommunicationPreferencesTransform.dto2Bo(individuDTO.getCommunicationpreferencesdto()));
			} catch (JrafDomainException e) {
				LOGGER.error(e);
			}
		}
		if(individuDTO.getPrefilledNumbers() != null ) {
			individu.setPrefilledNumbers(PrefilledNumbersTransform.dto2BoLight(individuDTO.getPrefilledNumbers()));
		}
		if(individuDTO.getDelegateListDTO() != null ) {
			try {
				individu.setDelegateList(DelegationDataTransform.dto2BoLightForUpdate(individuDTO.getDelegateListDTO()));
			} catch (JrafDomainException e) {
				LOGGER.error(e);
			}
		}
		if(individuDTO.getDelegatorListDTO() != null ) {
			try {
				individu.setDelegatorList(DelegationDataTransform.dto2BoLightForUpdate(individuDTO.getDelegatorListDTO()));
			} catch (JrafDomainException e) {
				LOGGER.error(e);
			}
		}
		if(individuDTO.getAccountdatadto() != null) {
			try {
				individu.setAccountData(AccountDataTransform.dto2BoIndividual(individuDTO.getAccountdatadto()));
			} catch (JrafDomainException e) {
				LOGGER.error(e);
			}
		}
		if(individuDTO.getExternalIdentifierList() != null) {
			try {
				Set<ExternalIdentifier> externalIdSet = new HashSet<ExternalIdentifier>();
				for(ExternalIdentifierDTO externalIdentifierDTOLoop : individuDTO.getExternalIdentifierList()) {
					externalIdSet.add(ExternalIdentifierTransform.dto2BoLight(externalIdentifierDTOLoop));
				}
				individu.setExternalIdentifierList(externalIdSet);
			} catch (JrafDomainException e) {
				LOGGER.error(e);
			}
		}
		if(individuDTO.getAlertDTO() != null) {
			try {
				individu.setAlert(AlertTransform.dto2BoLight(individuDTO.getAlertDTO()));
			} catch (JrafDomainException e) {
				LOGGER.error(e);
			}
		}
		if(individuDTO.getPreferenceDTO() != null) {
			try {

				Set<Preference> prefSet = new HashSet<Preference>();
				for(PreferenceDTO prefDTOLoop : individuDTO.getPreferenceDTO()) {
					prefSet.add(PreferenceTransform.dto2BoLight(prefDTOLoop));
				}
				individu.setPreference(prefSet);
			} catch (JrafDomainException e) {
				LOGGER.error(e);
			}
		}
	}
	
	private static void dto2BoLinkAccount(IndividuDTO individuDTO, Individu individu) {
		if(individuDTO.getEmaildto() != null ) {
			try {
				individu.setEmail(EmailTransform.dto2Bo(individuDTO.getEmaildto()));
			} catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}
		if(individuDTO.getTelecoms() != null ) {
			try {
				individu.setTelecoms(TelecomsTransform.dto2Bo(individuDTO.getTelecoms()));
			} catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}
		if(individuDTO.getPostaladdressdto() != null ) {
			try {
				individu.setPostaladdress(PostalAddressTransform.dto2Bo(individuDTO.getPostaladdressdto()));
			} catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}
		if(individuDTO.getRolecontratsdto() != null ) {
			try {
				individu.setRolecontrats(RoleContratsTransform.dto2Bo(individuDTO.getRolecontratsdto()));
			} catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}
		if(individuDTO.getProfil_meredto() != null ) {
			try {
				individu.setProfil_mere(Profil_mereTransform.dto2Bo(individuDTO.getProfil_meredto()));
			} catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}
		if(individuDTO.getProfilsdto() != null ) {
			try {
				individu.setProfils(ProfilsTransform.dto2BoLight(individuDTO.getProfilsdto()));
			} catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}
		if(individuDTO.getCommunicationpreferencesdto() != null ) {
			try {
				individu.setCommunicationpreferences(CommunicationPreferencesTransform.dto2Bo(individuDTO.getCommunicationpreferencesdto()));
			} catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}
		if(individuDTO.getPrefilledNumbers() != null ) {
			individu.setPrefilledNumbers(PrefilledNumbersTransform.dto2BoLight(individuDTO.getPrefilledNumbers()));
		}
		if(individuDTO.getDelegateListDTO() != null ) {
			try {
				individu.setDelegateList(DelegationDataTransform.dto2BoLight(individuDTO.getDelegateListDTO()));
			} catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}
		if(individuDTO.getDelegatorListDTO() != null ) {
			try {
				individu.setDelegatorList(DelegationDataTransform.dto2BoLight(individuDTO.getDelegatorListDTO()));
			} catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}
		if(individuDTO.getAccountdatadto() != null) {
			try {
				individu.setAccountData(AccountDataTransform.dto2BoLightIndividual(individuDTO.getAccountdatadto()));
			} catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}
		if(individuDTO.getExternalIdentifierList() != null) {
			try {
				Set<ExternalIdentifier> externalIdSet = new HashSet<ExternalIdentifier>();
				for(ExternalIdentifierDTO externalIdentifierDTOLoop : individuDTO.getExternalIdentifierList()) {
					externalIdSet.add(ExternalIdentifierTransform.dto2BoLight(externalIdentifierDTOLoop));
				}
				individu.setExternalIdentifierList(externalIdSet);
			} catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}
		if(individuDTO.getAlertDTO() != null) {
			try {
				individu.setAlert(AlertTransform.dto2BoLight(individuDTO.getAlertDTO()));
			} catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}
		if(individuDTO.getPreferenceDTO() != null) {
			try {

				Set<Preference> prefSet = new HashSet<Preference>();
				for(PreferenceDTO prefDTOLoop : individuDTO.getPreferenceDTO()) {
					prefSet.add(PreferenceTransform.dto2BoLight(prefDTOLoop));
				}
				individu.setPreference(prefSet);
			} catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}
	}

	private static void bo2DtoLinkMember(Individu individu, IndividuDTO individuDTO, boolean isFBRecognitionActivate) throws JrafDomainException {
		if(individu.getRolecontrats() != null) {
			try {
				individuDTO.setRolecontratsdto(RoleContratsTransform.bo2Dto(individu.getRolecontrats(), isFBRecognitionActivate));
			} catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}
	}
	private static void bo2DtoLink(Individu individu, IndividuDTO individuDTO) throws JrafDomainException {
		bo2DtoLink(individu, individuDTO, true);
	}
	
	private static void bo2DtoLink(Individu individu, IndividuDTO individuDTO, boolean isFBRecognitionActivate) throws JrafDomainException {
		if(individu.getEmail() != null) {
			try {
				individuDTO.setEmaildto(EmailTransform.bo2Dto(individu.getEmail()));
			} catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}
		if(individu.getTelecoms() != null) {
			try {
				individuDTO.setTelecoms(TelecomsTransform.bo2Dto(individu.getTelecoms()));
			} catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}
		if(individu.getPostaladdress() != null) {
			try {
				individuDTO.setPostaladdressdto(PostalAddressTransform.bo2Dto(individu.getPostaladdress()));
			} catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}
		if(individu.getRolecontrats() != null) {
			try {
				individuDTO.setRolecontratsdto(RoleContratsTransform.bo2Dto(individu.getRolecontrats(), isFBRecognitionActivate));
			} catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}
		if(individu.getProfil_mere() != null) {
			try {
				individuDTO.setProfil_meredto(Profil_mereTransform.bo2Dto(individu.getProfil_mere()));
			} catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}
		if(individu.getProfils() != null) {
			try {
				individuDTO.setProfilsdto(ProfilsTransform.bo2DtoLight(individu.getProfils()));
			} catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}
		if(individu.getCommunicationpreferences() != null) {
			try {
				individuDTO.setCommunicationpreferencesdto(CommunicationPreferencesTransform.bo2Dto(individu.getCommunicationpreferences()));
			} catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}
		if(individu.getPreference() != null) {
			try {
				individuDTO.setPreferenceDTO(PreferenceTransform.bo2Dto(individu.getPreference()));
			} catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}
		if(individu.getPrefilledNumbers() != null) {
			individuDTO.setPrefilledNumbers(PrefilledNumbersTransform.bo2DtoLight(individu.getPrefilledNumbers()));
		}
		if(individu.getDelegateList() != null ) {
			individuDTO.setDelegateListDTO(DelegationDataTransform.bo2DtoLight(individu.getDelegateList()));
		}
		if(individu.getDelegatorList() != null ) {
			individuDTO.setDelegatorListDTO(DelegationDataTransform.bo2DtoLight(individu.getDelegatorList()));
		}
		if(individu.getAccountData() != null) {
			try {
				individuDTO.setAccountdatadto(AccountDataTransform.bo2DtoLight(individu.getAccountData()));
			} catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}
		if(individu.getExternalIdentifierList() != null) {
			try {
				individuDTO.setExternalIdentifierList(ExternalIdentifierTransform.bo2DtoForProvide(new ArrayList<ExternalIdentifier>(individu.getExternalIdentifierList())));
			} catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}
		if(individu.getAlert() != null) {
			try {
				individuDTO.setAlertDTO(AlertTransform.bo2Dto(individu.getAlert()));
			} catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}
		if(individu.getPreference() != null) {
			try {
				individuDTO.setPreferenceDTO(com.airfrance.repind.dto.preference.PreferenceTransform.bo2Dto(individu.getPreference()));
			} catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}
	}
	
	private static void bo2DtoLinkForUpdate(Individu individu, IndividuDTO individuDTO) throws JrafDomainException {
		if(individu.getEmail() != null) {
			try {
				individuDTO.setEmaildto(EmailTransform.bo2Dto(individu.getEmail()));
			} catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}
		if(individu.getTelecoms() != null) {
			try {
				individuDTO.setTelecoms(TelecomsTransform.bo2Dto(individu.getTelecoms()));
			} catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}
		if(individu.getPostaladdress() != null) {
			try {
				individuDTO.setPostaladdressdto(PostalAddressTransform.bo2Dto(individu.getPostaladdress()));
			} catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}
		if(individu.getRolecontrats() != null) {
			try {
				individuDTO.setRolecontratsdto(RoleContratsTransform.bo2Dto(individu.getRolecontrats()));
			} catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		} else {
			individuDTO.setRolecontratsdto(new HashSet<RoleContratsDTO>());
		}
		if(individu.getProfil_mere() != null) {
			try {
				individuDTO.setProfil_meredto(Profil_mereTransform.bo2Dto(individu.getProfil_mere()));
			} catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}
		if(individu.getProfils() != null) {
			try {
				individuDTO.setProfilsdto(ProfilsTransform.bo2DtoLight(individu.getProfils()));
			} catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}
		if(individu.getCommunicationpreferences() != null) {
			try {
				individuDTO.setCommunicationpreferencesdto(CommunicationPreferencesTransform.bo2Dto(individu.getCommunicationpreferences()));
			} catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}
		if(individu.getPreference() != null) {
			try {
				individuDTO.setPreferenceDTO(PreferenceTransform.bo2Dto(individu.getPreference()));
			} catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}
		if(individu.getPrefilledNumbers() != null) {
			individuDTO.setPrefilledNumbers(PrefilledNumbersTransform.bo2DtoLight(individu.getPrefilledNumbers()));
		}
		if(individu.getDelegateList() != null ) {
			individuDTO.setDelegateListDTO(DelegationDataTransform.bo2DtoLightForUpdate(individu.getDelegateList()));
		}
		if(individu.getDelegatorList() != null ) {
			individuDTO.setDelegatorListDTO(DelegationDataTransform.bo2DtoLightForUpdate(individu.getDelegatorList()));
		}
		if(individu.getAccountData() != null) {
			try {
				individuDTO.setAccountdatadto(AccountDataTransform.bo2DtoLightIndividu(individu.getAccountData()));
			} catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}
		if(individu.getExternalIdentifierList() != null) {
			try {
				individuDTO.setExternalIdentifierList(ExternalIdentifierTransform.bo2DtoForProvide(new ArrayList<ExternalIdentifier>(individu.getExternalIdentifierList())));
			} catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}
		if(individu.getAlert() != null) {
			try {
				individuDTO.setAlertDTO(AlertTransform.bo2Dto(individu.getAlert()));
			} catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}
		if(individu.getPreference() != null) {
			try {
				individuDTO.setPreferenceDTO(com.airfrance.repind.dto.preference.PreferenceTransform.bo2Dto(individu.getPreference()));
			} catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}
	}
	
	private static void bo2DtoLinkAccount(Individu individu, IndividuDTO individuDTO) throws JrafDomainException {
		if(individu.getEmail() != null) {
			try {
				individuDTO.setEmaildto(EmailTransform.bo2Dto(individu.getEmail()));
			} catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}
		if(individu.getTelecoms() != null) {
			try {
				individuDTO.setTelecoms(TelecomsTransform.bo2Dto(individu.getTelecoms()));
			} catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}
		if(individu.getPostaladdress() != null) {
			try {
				individuDTO.setPostaladdressdto(PostalAddressTransform.bo2Dto(individu.getPostaladdress()));
			} catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}
		if(individu.getRolecontrats() != null) {
			try {
				individuDTO.setRolecontratsdto(RoleContratsTransform.bo2Dto(individu.getRolecontrats()));
			} catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}
		if(individu.getProfil_mere() != null) {
			try {
				individuDTO.setProfil_meredto(Profil_mereTransform.bo2Dto(individu.getProfil_mere()));
			} catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}
		if(individu.getProfils() != null) {
			try {
				individuDTO.setProfilsdto(ProfilsTransform.bo2DtoLight(individu.getProfils()));
			} catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}
		if(individu.getCommunicationpreferences() != null) {
			try {
				individuDTO.setCommunicationpreferencesdto(CommunicationPreferencesTransform.bo2Dto(individu.getCommunicationpreferences()));
			} catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}
		if(individu.getPreference() != null) {
			try {
				individuDTO.setPreferenceDTO(PreferenceTransform.bo2Dto(individu.getPreference()));
			} catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}
		if(individu.getPrefilledNumbers() != null) {
			individuDTO.setPrefilledNumbers(PrefilledNumbersTransform.bo2DtoLight(individu.getPrefilledNumbers()));
		}
		if(individu.getDelegateList() != null ) {
			individuDTO.setDelegateListDTO(DelegationDataTransform.bo2DtoLight(individu.getDelegateList()));
		}
		if(individu.getDelegatorList() != null ) {
			individuDTO.setDelegatorListDTO(DelegationDataTransform.bo2DtoLight(individu.getDelegatorList()));
		}
		if(individu.getAccountData() != null) {
			try {
				individuDTO.setAccountdatadto(AccountDataTransform.bo2DtoLightIndividu(individu.getAccountData()));
			} catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}
		if(individu.getExternalIdentifierList() != null) {
			try {
				individuDTO.setExternalIdentifierList(ExternalIdentifierTransform.bo2DtoForProvide(new ArrayList<ExternalIdentifier>(individu.getExternalIdentifierList())));
			} catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}
		if(individu.getAlert() != null) {
			try {
				individuDTO.setAlertDTO(AlertTransform.bo2Dto(individu.getAlert()));
			} catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}
		if(individu.getPreference() != null) {
			try {
				individuDTO.setPreferenceDTO(com.airfrance.repind.dto.preference.PreferenceTransform.bo2Dto(individu.getPreference()));
			} catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}
	}
	
	private static void bo2DtoLinkSearch(Individu individu, IndividuDTO individuDTO) throws JrafDomainException {
		if(individu.getEmail() != null) {
			try {
				individuDTO.setEmaildto(EmailTransform.bo2Dto(individu.getEmail()));
			} catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}
		if(individu.getTelecoms() != null) {
			try {
				individuDTO.setTelecoms(TelecomsTransform.bo2DtoValidTelecoms(individu.getTelecoms()));
			} catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}
		if(individu.getPostaladdress() != null) {
			try {
				individuDTO.setPostaladdressdto(PostalAddressTransform.bo2DtoValid(individu.getPostaladdress()));
			} catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}
		if(individu.getRolecontrats() != null) {
			try {
				individuDTO.setRolecontratsdto(RoleContratsTransform.bo2DtoLight(individu.getRolecontrats()));
			} catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}		
		if(individu.getProfils() != null) {
			try {
				// REPIND-266 : Le Code Langue au retour du W001271V02 est toujours vide
				ProfilsDTO pd = ProfilsTransform.bo2DtoLight(individu.getProfils());
				individuDTO.setProfilsdto(pd);
				// On verifie que l on a des donnees "Profil" et que l Individu n'a pas de donnees "Langue" 
				if (pd != null && individuDTO != null && (individuDTO.getCodeLangue() == null || "".equals(individuDTO.getCodeLangue()))) {
					individuDTO.setCodeLangue(pd.getScode_langue());
				}
				
			} catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}
	}
	
	private static void bo2DtoLinkForDelegation(Individu individu, IndividuDTO individuDTO) throws JrafDomainException {
		
		if(individu==null) {
			return;
		}
		
		if(individu.getAccountData()!=null) {
			individuDTO.setAccountdatadto(AccountDataTransform.bo2DtoLight(individu.getAccountData()));
		}
		
		if(individu.getTelecoms()!=null) {
			individuDTO.setTelecoms(TelecomsTransform.bo2DtoLight(individu.getTelecoms()));
		}
		
	}

	/**
	 * This method adds some default phonetic value as calculate by Phonetik input or "NOTNULL" value by default.
	 * FIXME : add a real phonetic value using firstname / lastname
	 * @param individu
	 */
	public static void addDefaultPhonetic(Individu individu) {
		// REPIND-1256 : Add value in case of Traveler Creation 
		if (individu != null) {
			if (individu.getIndcons() == null || "".equals(individu.getIndcons())) {
								
				// On cree la fonction de phonetisation
				PhonetikInput input = new PhonetikInput();
				// NOM et PRENOM  
		    	input.setIdent(individu.getNom() + " " + individu.getPrenom());
		    	PhEntree.Fonetik_Entree(input);
		    	
		    	if (input != null) {		    		
		    		if (!"".equals(input.getIndCons())) {		    		
		    			individu.setIndcons(input.getIndCons());		    		
		    		} else {
		    			individu.setIndcons(NOT_NULL);
		    		}
		    		if (!"".equals(input.getIndict())) {		    		
		    			individu.setIndicNomPrenom(input.getIndict());		    		
		    		} else {
		    			individu.setIndicNomPrenom(NOT_NULL);
		    		}
		    	}
			}
			if (individu.getIndicNom() == null || "".equals(individu.getIndicNom())) {				

				// On cree la fonction de phonetisation
				PhonetikInput input = new PhonetikInput();
				// NOM 
				if (individu.getNom() != null) {
					input.setIdent(individu.getNom());
			    	PhEntree.Fonetik_Entree(input);
			    	
			    	if (input != null) {		    		
			    		if (!"".equals(input.getIndict())) {		    		
			    			individu.setIndicNom(input.getIndict());		    		
			    		} else {
			    			individu.setIndicNom(NOT_NULL);
			    		}
			    	}
				} else {
					individu.setIndicNom(NOT_NULL);
				}
			}
			if (individu.getIndicNomPrenom() == null || "".equals(individu.getIndicNomPrenom())) {				

				// On cree la fonction de phonetisation
				PhonetikInput input = new PhonetikInput();
				// NOM et PRENOM  
		    	input.setIdent(individu.getNom() + " " + individu.getPrenom());
		    	PhEntree.Fonetik_Entree(input);
		    	
		    	if (input != null) {		    		
		    		if (!"".equals(input.getIndCons())) {		    		
		    			individu.setIndcons(input.getIndCons());		    		
		    		} else {
		    			individu.setIndcons(NOT_NULL);
		    		}
		    		if (!"".equals(input.getIndict())) {		    		
		    			individu.setIndicNomPrenom(input.getIndict());		    		
		    		} else {
		    			individu.setIndicNomPrenom(NOT_NULL);
		    		}
		    	}
			}
		}
	}

	/**
	 * This method adds new or old gin to different links.
	 * FIXME : add missing links if needed
	 * @param individu
	 */
	public static void addGinToLink(Individu individu) {

		if (individu.getTelecoms() != null && !individu.getTelecoms().isEmpty()) {
			for (Telecoms tel : individu.getTelecoms()) {
				tel.setSgin(individu.getSgin());
			}
		}


		if (individu.getCommunicationpreferences() != null && !individu.getCommunicationpreferences().isEmpty()) {
			for (CommunicationPreferences compref : individu.getCommunicationpreferences()) {
				if(individu.getSgin() != null && !individu.getSgin().equals("")) {
					compref.setGin(individu.getSgin());
				}
				for (MarketLanguage ml : compref.getMarketLanguage()) {
					if(compref.getComPrefId() != null) {
						ml.setComPrefId(compref.getComPrefId());
					}
				}
			}
		}

		if(individu.getProfils() != null){
			individu.getProfils().setSgin(individu.getSgin());
		}

		if (individu.getAccountData() != null && individu.getAccountData().getIndividu() == null) {
			individu.getAccountData().setIndividu(individu);
		}

	}
	
	/**
     * dto -> bo for a ProspectTelecoms
     * @param prospectTelecomsDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static Telecoms prospectTelecomDto2BoLight(TelecomsDTO prospectTelecomsDTO) throws JrafDomainException {
        // instanciation du BO
        Telecoms prospectTelecoms = new Telecoms();
        prospectTelecomDto2BoLight(prospectTelecomsDTO, prospectTelecoms);

        // on retourne le BO
        return prospectTelecoms;
    }
    
    /**
     * dto -> bo for a prospectTelecoms
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param prospectTelecomsDTO dto
     * @param prospectTelecoms bo
     */
    public static void prospectTelecomDto2BoLight(TelecomsDTO prospectTelecomsDTO, Telecoms prospectTelecoms) {
            
        prospectTelecomDto2BoLightImpl(prospectTelecomsDTO,prospectTelecoms);
        
    }
    
    /**
     * dto -> bo implementation for a prospectTelecoms
     * @param prospectTelecomsDTO dto
     * @param prospectTelecoms bo
     */
    private static void prospectTelecomDto2BoLightImpl(TelecomsDTO prospectTelecomsDTO, Telecoms prospectTelecoms){
    
        // property of ProspectTelecomsDTO
        prospectTelecoms.setSain(prospectTelecomsDTO.getSain());
        prospectTelecoms.setSgin(prospectTelecomsDTO.getSgin());
        prospectTelecoms.setSstatut_medium(prospectTelecomsDTO.getSstatut_medium());
        prospectTelecoms.setScode_medium(prospectTelecomsDTO.getScode_medium());
        prospectTelecoms.setSterminal(prospectTelecomsDTO.getSterminal());
        prospectTelecoms.setCountryCode(prospectTelecomsDTO.getCountryCode());
        prospectTelecoms.setScode_region(prospectTelecomsDTO.getScode_region());
        prospectTelecoms.setSnumero(prospectTelecomsDTO.getSnumero());
        prospectTelecoms.setSindicatif(prospectTelecomsDTO.getSindicatif());
        prospectTelecoms.setSnormalized_country(prospectTelecomsDTO.getSnormalized_country());
        prospectTelecoms.setSnormalized_numero(prospectTelecomsDTO.getSnormalized_numero());
        prospectTelecoms.setDdate_creation(prospectTelecomsDTO.getDdate_creation());
        prospectTelecoms.setSsignature_creation(prospectTelecomsDTO.getSsignature_creation());
        prospectTelecoms.setSsite_creation(prospectTelecomsDTO.getSsite_creation());
        prospectTelecoms.setDdate_modification(prospectTelecomsDTO.getDdate_modification());
        prospectTelecoms.setSsignature_modification(prospectTelecomsDTO.getSsignature_modification());
        prospectTelecoms.setSsite_modification(prospectTelecomsDTO.getSsite_modification());
        prospectTelecoms.setSnorm_nat_phone_number(prospectTelecomsDTO.getSnorm_nat_phone_number());
        prospectTelecoms.setSnorm_nat_phone_number_clean(prospectTelecomsDTO.getSnorm_nat_phone_number_clean());
        prospectTelecoms.setSnorm_inter_country_code(prospectTelecomsDTO.getSnorm_inter_country_code());
        prospectTelecoms.setSnorm_inter_phone_number(prospectTelecomsDTO.getSnorm_inter_phone_number());
        prospectTelecoms.setSnorm_terminal_type_detail(prospectTelecomsDTO.getSnorm_terminal_type_detail());
        prospectTelecoms.setIsnormalized(prospectTelecomsDTO.getIsnormalized());
    
    }
    
    /**
     * bo -> dto for a prospectTelecoms
     * @param pProspectTelecoms bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
	public static TelecomsDTO prospectTelecomBo2DtoLight(Telecoms telecom) {
		// instanciation du DTO
        TelecomsDTO prospectTelecomDTO = new TelecomsDTO();
        prospectTelecomBo2DtoLight(telecom, prospectTelecomDTO);
        // on retourne le dto
        return prospectTelecomDTO;
	}
	
	/**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param prospectTelecoms bo
     * @param prospectTelecomsDTO dto
     */
	public static void prospectTelecomBo2DtoLight(Telecoms telecom, TelecomsDTO prospectTelecomDTO) {

		bo2DtoLightImpl(telecom, prospectTelecomDTO);
		
	}
	
	/**
     * Transform a business object to DTO. Implementation method
     * @param prospectTelecoms bo
     * @param prospectTelecomsDTO dto
     */
	private static void bo2DtoLightImpl(Telecoms telecom, TelecomsDTO prospectTelecomDTO) {
		
		// simple properties
		prospectTelecomDTO.setSain(telecom.getSain());
		prospectTelecomDTO.setSgin(telecom.getSgin());
		prospectTelecomDTO.setSstatut_medium(telecom.getSstatut_medium());
		prospectTelecomDTO.setScode_medium(telecom.getScode_medium());
		prospectTelecomDTO.setSterminal(telecom.getSterminal());
		prospectTelecomDTO.setCountryCode(telecom.getCountryCode());
        prospectTelecomDTO.setScode_region(telecom.getScode_region());
        prospectTelecomDTO.setSnumero(telecom.getSnumero());
        prospectTelecomDTO.setSindicatif(telecom.getSindicatif());
        prospectTelecomDTO.setSnormalized_country(telecom.getSnormalized_country());
        prospectTelecomDTO.setSnormalized_numero(telecom.getSnormalized_numero());
        prospectTelecomDTO.setDdate_creation(telecom.getDdate_creation());
        prospectTelecomDTO.setSsignature_creation(telecom.getSsignature_creation());
        prospectTelecomDTO.setSsite_creation(telecom.getSsite_creation());
        prospectTelecomDTO.setDdate_modification(telecom.getDdate_modification());
        prospectTelecomDTO.setSsignature_modification(telecom.getSsignature_modification());
        prospectTelecomDTO.setSsite_modification(telecom.getSsite_modification());
        prospectTelecomDTO.setSnorm_nat_phone_number(telecom.getSnorm_nat_phone_number());
        prospectTelecomDTO.setSnorm_nat_phone_number_clean(telecom.getSnorm_nat_phone_number_clean());
        prospectTelecomDTO.setSnorm_inter_country_code(telecom.getSnorm_inter_country_code());
        prospectTelecomDTO.setSnorm_inter_phone_number(telecom.getSnorm_inter_phone_number());
        prospectTelecomDTO.setSnorm_terminal_type_detail(telecom.getSnorm_terminal_type_detail());
        prospectTelecomDTO.setIsnormalized(telecom.getIsnormalized());
		
	}
	
	public static Telecoms prospectTelecomDto2Bo(TelecomsDTO prospectTelecomsDTO) throws JrafDomainException {
		if (prospectTelecomsDTO == null) {
    		return null;
    	}
    	
    	Telecoms telecoms = new Telecoms();
        prospectTelecomDto2BoLight(prospectTelecomsDTO, telecoms);
        prospectTelecomDto2BoLink(telecoms, prospectTelecomsDTO);

        return telecoms;
	}
	
	private static void prospectTelecomDto2BoLink(Telecoms telecoms, TelecomsDTO prospectTelecomsDTO) throws JrafDomainException {
		IndividuDTO prospectDTO = prospectTelecomsDTO.getIndividudto();
    	
    	if(prospectDTO!=null) {
    		Individu prospect = IndividuTransform.dto2BoLight(prospectDTO);
			telecoms.setIndividu(prospect);
    	}
	}
	
	
	/**
	 * Transforms a IndividuLight to a DTO.
	 * 
	 * @param individuLight
	 * 
	 * @return
	 */
	public static IndividuDTO bo2DtoLight(IndividuLight individuLight) {

		// Decalre return DTO
		IndividuDTO individuDTO = null;
		
		if (individuLight != null) {
			// Fill DTO with data
			individuDTO = new IndividuDTO();
			individuDTO.setSgin(individuLight.getSgin());
	        individuDTO.setType(individuLight.getType());
	        individuDTO.setVersion(individuLight.getVersion());
	        individuDTO.setCivilite(individuLight.getCivilite());
	        individuDTO.setMotDePasse(individuLight.getMotDePasse());
	        individuDTO.setNom(individuLight.getNom());
	        individuDTO.setAlias(individuLight.getAlias());
	        individuDTO.setPrenom(individuLight.getPrenom());
	        individuDTO.setSecondPrenom(individuLight.getSecondPrenom());
	        individuDTO.setAliasPrenom(individuLight.getAliasPrenom());
	        individuDTO.setSexe(individuLight.getSexe());
	        individuDTO.setIdentifiantPersonnel(individuLight.getIdentifiantPersonnel());
	        individuDTO.setDateNaissance(individuLight.getDateNaissance());
	        individuDTO.setStatutIndividu(individuLight.getStatutIndividu());
	        individuDTO.setCodeTitre(individuLight.getCodeTitre());
	        individuDTO.setNationalite(individuLight.getNationalite());
	        individuDTO.setAutreNationalite(individuLight.getAutreNationalite());
	        individuDTO.setNonFusionnable(individuLight.getNonFusionnable());
	        individuDTO.setSiteCreation(individuLight.getSiteCreation());
	        individuDTO.setSignatureCreation(individuLight.getSignatureCreation());
	        individuDTO.setDateCreation(individuLight.getDateCreation());
	        individuDTO.setSiteModification(individuLight.getSiteModification());
	        individuDTO.setSignatureModification(individuLight.getSignatureModification());
	        individuDTO.setDateModification(individuLight.getDateModification());
	        individuDTO.setSiteFraudeur(individuLight.getSiteFraudeur());
	        individuDTO.setSignatureFraudeur(individuLight.getSignatureFraudeur());
	        individuDTO.setDateModifFraudeur(individuLight.getDateModifFraudeur());
	        individuDTO.setSiteMotDePasse(individuLight.getSiteMotDePasse());
	        individuDTO.setSignatureMotDePasse(individuLight.getSignatureMotDePasse());
	        individuDTO.setDateModifMotDePasse(individuLight.getDateModifMotDePasse());
	        individuDTO.setFraudeurCarteBancaire(individuLight.getFraudeurCarteBancaire());
	        individuDTO.setTierUtiliseCommePiege(individuLight.getTierUtiliseCommePiege());
	        individuDTO.setAliasNom1(individuLight.getAliasNom1());
	        individuDTO.setAliasNom2(individuLight.getAliasNom2());
	        individuDTO.setAliasPrenom1(individuLight.getAliasPrenom1());
	        individuDTO.setAliasPrenom2(individuLight.getAliasPrenom2());
	        individuDTO.setAliasCivilite1(individuLight.getAliasCivilite1());
	        individuDTO.setAliasCivilite2(individuLight.getAliasCivilite2());
	        individuDTO.setIndicNomPrenom(individuLight.getIndicNomPrenom());
	        individuDTO.setIndicNom(individuLight.getIndicNom());
	        individuDTO.setIndcons(individuLight.getIndcons());
	        individuDTO.setGinFusion(individuLight.getGinFusion());
	        individuDTO.setDateFusion(individuLight.getDateFusion());
	        individuDTO.setProvAmex(individuLight.getProvAmex());
	        individuDTO.setCieGest(individuLight.getCieGest());
		}
		
		return individuDTO;
	}

	public static Individu dto2BoMember(IndividuDTO individuDto) throws JrafDomainException {
		// instanciation du DTO
		Individu individu = new Individu();
		dto2BoMember(individuDto, individu);
		// on retourne le dto
		return individu;
	}

	public static void dto2BoMember(IndividuDTO individuDto, Individu individu) throws InvalidParameterException {
		dto2BoLight(individuDto, individu);
		individu.setNomSC(individuDto.getNomSC());
		individu.setPrenomSC(individuDto.getPrenomSC());
		dto2BoLinkMember(individuDto, individu);
	}

	private static void dto2BoLinkMember(IndividuDTO individuDto, Individu individu) {
		if(individuDto.getRolecontratsdto() != null) {
			try {
				individu.setRolecontrats(RoleContratsTransform.dto2Bo(individuDto.getRolecontratsdto()));
			} catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}
	}

	/*PROTECTED REGION END*/
}

