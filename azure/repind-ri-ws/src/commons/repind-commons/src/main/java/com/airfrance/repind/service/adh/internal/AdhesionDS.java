package com.airfrance.repind.service.adh.internal;

import com.airfrance.ref.exception.AlreadyExistException;
import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.MissingParameterException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;
import com.airfrance.ref.type.MediumCodeEnum;
import com.airfrance.ref.type.MediumStatusEnum;
import com.airfrance.repind.dto.adresse.EmailDTO;
import com.airfrance.repind.dto.adresse.PostalAddressDTO;
import com.airfrance.repind.dto.adresse.TelecomsDTO;
import com.airfrance.repind.dto.adresse.Usage_mediumDTO;
import com.airfrance.repind.dto.individu.IndividuDTO;
import com.airfrance.repind.dto.individu.createmodifyindividual.*;
import com.airfrance.repind.dto.profil.ProfilsDTO;
import com.airfrance.repind.entity.refTable.RefTableCAT_MED;
import com.airfrance.repind.entity.refTable.RefTableREF_STA_MED;
import com.airfrance.repind.service.adresse.internal.EmailDS;
import com.airfrance.repind.service.adresse.internal.PostalAddressDS;
import com.airfrance.repind.service.individu.internal.IndividuDS;
import com.airfrance.repind.util.NormalizedStringUtils;
import com.airfrance.repind.util.SicDateUtils;
import com.airfrance.repind.util.SicStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Service
public class AdhesionDS {

	
    private static final Log log = LogFactory.getLog(AdhesionDS.class);

    @Autowired
    private EmailDS emailDS;
    
    @Autowired
    private IndividuDS individuDS;
    
    @Autowired
    private PostalAddressDS postalAddressDS;
    
    public AdhesionDS() {
    }
	public RequeteDTO prepareRequest(com.airfrance.repind.dto.individu.SignatureDTO signatureDTO) {
		RequeteDTO requeteDTO = new RequeteDTO();
		requeteDTO.setCodeAppliMetier(signatureDTO.getApplicationCode());		
		return requeteDTO;
	}
    
	public void prepareSignature(com.airfrance.repind.dto.individu.SignatureDTO signatureFromWS, CreateModifyIndividualResquestDTO resquestForADH) {
		
		SignatureDTO signatureForADH = new SignatureDTO();
		signatureForADH.setSignature(signatureFromWS.getSignature());
		signatureForADH.setSite(signatureFromWS.getSite());
		signatureForADH.setTypeSignature("M");
		
		resquestForADH.setSignature(signatureForADH);
		
	}
    
    public void prepareCivilian(IndividuDTO individuFromWS, IndividuDTO individuFromDB, CreateModifyIndividualResquestDTO resquestForADH) {
		
		String codeTitreFromWS = individuFromWS.getCodeTitre();
		String codeTitreFromDB = individuFromDB.getCodeTitre();
		
		TitreCivilDTO titrecivil = new TitreCivilDTO();
		resquestForADH.setTitrecivil(titrecivil);
		
		// No civilian in input
		if(StringUtils.isEmpty(codeTitreFromWS)) {
			
			// Take civilian in DB
			if(StringUtils.isNotEmpty(codeTitreFromDB)){
				titrecivil.setCodeTitre(codeTitreFromDB);
			}
			
		}
		
		titrecivil.setCodeTitre(codeTitreFromWS);
		
	}
    
    public Boolean prepareIndividual(IndividuDTO individuFromWS, Boolean erasePayments, IndividuDTO individuFromDB, CreateModifyIndividualResquestDTO resquestForADH) throws InvalidParameterException {
		
		resquestForADH.setIndividu(new InfosIndividuDTO());
		
		// Update individual
		// getIndividuDS().updateIndividual(individu);
		resquestForADH.getIndividu().setNumeroClient(individuFromDB.getSgin());
		resquestForADH.getIndividu().setVersion(individuFromDB.getVersion().toString());
		
		// use individual status from WS if provided
		if(StringUtils.isNotEmpty(individuFromWS.getStatutIndividu())) {
			resquestForADH.getIndividu().setStatut(individuFromWS.getStatutIndividu());
		} 
		// else use the value in database
		else {
			resquestForADH.getIndividu().setStatut(individuFromDB.getStatutIndividu());
		}
		
		// Set sexe
		if(StringUtils.isNotEmpty(individuFromWS.getSexe())) {
			resquestForADH.getIndividu().setSexe(individuFromWS.getSexe());
		}
		
		// Set not fusionnable flag
		if(StringUtils.isNotEmpty(individuFromWS.getNonFusionnable())) {
			resquestForADH.getIndividu().setIndicNonFusion(individuFromWS.getNonFusionnable());
		}
		
		// Set flag third trap
		if(StringUtils.isNotEmpty(individuFromWS.getTierUtiliseCommePiege())) {
			resquestForADH.getIndividu().setIndicTiersPiege(individuFromWS.getTierUtiliseCommePiege());
		}
		
		// If enrollement with "M_" MADAM OR MISTER  change it to "M." for database 
		if (individuFromWS.getCivilite() != null) {
			if ("M_".equals(individuFromWS.getCivilite()) || "M".equals(individuFromWS.getCivilite())) {
				individuFromWS.setCivilite("M.");
				resquestForADH.getIndividu().setCivilite(individuFromWS.getCivilite());
			} else {
				individuFromWS.setCivilite(individuFromWS.getCivilite().trim());
				resquestForADH.getIndividu().setCivilite(individuFromWS.getCivilite());
			}
			
			// *** Delete Payments : if civiliy change ***
			if(individuFromDB.getCivilite() == null || !individuFromDB.getCivilite().equals(individuFromWS.getCivilite())) {
				erasePayments = true;
			}
		}
		
		// Bussiness Rule for LastName and FirstName, Only iso latin caracters
		if (!NormalizedStringUtils.isNormalizableString(individuFromWS.getNomSC())) {
			throw new InvalidParameterException("Invalid character in lastname");
		}
		if (!NormalizedStringUtils.isNormalizableString(individuFromWS.getPrenomSC())	) {
			throw new InvalidParameterException("Invalid character in firstname");
		}
		
		if (individuFromWS.getNomSC() != null) {
			
			individuFromWS.setNomSC(individuFromWS.getNomSC().trim());
			resquestForADH.getIndividu().setNom(NormalizedStringUtils.normalizeString(individuFromWS.getNomSC()));
			individuFromWS.setNom(NormalizedStringUtils.normalizeString(individuFromWS.getNomSC()));
			
			// *** Delete Payments : if last name change ***
			if(individuFromDB.getNomSC() == null || !individuFromDB.getNomSC().equalsIgnoreCase(individuFromWS.getNomSC()))
			{
				erasePayments = true;
			}
			if(individuFromDB.getNom() == null || !individuFromDB.getNom().equalsIgnoreCase(individuFromWS.getNom()))
			{
				erasePayments = true; 
			}
			
		} else {
			individuFromWS.setNomSC(individuFromDB.getNomSC());
			resquestForADH.getIndividu().setNom(NormalizedStringUtils.normalizeString(individuFromDB.getNom()));
			individuFromWS.setNom(NormalizedStringUtils.normalizeString(individuFromDB.getNom()));
		}
		
		if (individuFromWS.getPrenomSC() != null) {
			individuFromWS.setPrenomSC(individuFromWS.getPrenomSC().trim());
			resquestForADH.getIndividu().setPrenom(NormalizedStringUtils.normalizeString(individuFromWS.getPrenomSC()));
			individuFromWS.setPrenom(NormalizedStringUtils.normalizeString(individuFromWS.getPrenomSC()));
			
			// *** Delete Payments : if first name change ***
			if(individuFromDB.getPrenomSC() == null || !individuFromDB.getPrenomSC().equalsIgnoreCase(individuFromWS.getPrenomSC()))
			{
				erasePayments = true;
			}
			else if(individuFromDB.getPrenom() == null || !individuFromDB.getPrenom().equalsIgnoreCase(individuFromWS.getPrenom()))
			{
				erasePayments = true;
			}
		
		} else {
			individuFromWS.setPrenomSC(individuFromDB.getPrenomSC());
			resquestForADH.getIndividu().setPrenom(NormalizedStringUtils.normalizeString(individuFromDB.getPrenom()));
			individuFromWS.setPrenom(NormalizedStringUtils.normalizeString(individuFromDB.getPrenom()));
		}
		
		if (individuFromWS.getDateNaissance() == null) {
			resquestForADH.getIndividu().setDateNaissance(SicDateUtils.computeFrenchDate(individuFromDB.getDateNaissance()));
			
		} else {
			
			resquestForADH.getIndividu().setDateNaissance(SicDateUtils.computeFrenchDate(individuFromWS.getDateNaissance()));
			
			// IM01011342 - Delete payment preferences only if day, month and year from date of birth changes
			if( individuFromDB.getDateNaissance() == null )
			{
				erasePayments = true;
			}
			else
			{
				if( SicDateUtils.computeFrenchDate(individuFromWS.getDateNaissance()).compareTo(
						SicDateUtils.computeFrenchDate(individuFromDB.getDateNaissance())) != 0 )	
				{
					log.info("erasePayments due to a DoB change");
					log.info("DateUtils.computeFrenchDate(individu.getDateNaissance()="+SicDateUtils.computeFrenchDate(individuFromWS.getDateNaissance()));
					log.info("DateUtils.computeFrenchDate(individuBo.getDateNaissance())="+SicDateUtils.computeFrenchDate(individuFromDB.getDateNaissance()));
					erasePayments = true;
				}
			}
		}
		
		// Transfert des autres donnees individu pour eviter un blanchiment lors de l'appel adhesion au S08924
		log.info("individu.getSecondPrenom()="+individuFromWS.getSecondPrenom());
		log.info("individu.getAliasPrenom()="+individuFromWS.getAliasPrenom());
		log.info("individu.getAliasNom1()="+individuFromWS.getAliasNom1());
		log.info("individu.getNationalite()="+individuFromWS.getNationalite());
		log.info("individu.getCodeTitre()="+individuFromWS.getCodeTitre());
		log.info("individuBo.getCodeTitre()="+individuFromDB.getCodeTitre());
		
		if ( individuFromWS.getSecondPrenom() != null )
		{
			resquestForADH.getIndividu().setSecondPrenom(NormalizedStringUtils.normalizeString(individuFromWS.getSecondPrenom()));
		}
		else if(individuFromDB.getSecondPrenom() != null)
			resquestForADH.getIndividu().setSecondPrenom(NormalizedStringUtils.normalizeString(individuFromDB.getSecondPrenom()));
		
		if ( individuFromWS.getAliasPrenom() != null )
		{
			resquestForADH.getIndividu().setAliasPrenom(individuFromWS.getAliasPrenom());
		}
		else if(individuFromDB.getAliasPrenom() != null)
			resquestForADH.getIndividu().setAliasPrenom(NormalizedStringUtils.normalizeString(individuFromDB.getAliasPrenom()));
		
		if ( individuFromWS.getAliasNom1() != null )
		{
			resquestForADH.getIndividu().setAliasNom(NormalizedStringUtils.normalizeString(individuFromWS.getAliasNom1())); // to be checked
		}
		else if(individuFromDB.getAliasNom1() != null)
			resquestForADH.getIndividu().setAliasNom(NormalizedStringUtils.normalizeString(individuFromDB.getAliasNom1()));
		
		setNationalite(resquestForADH.getIndividu(), individuFromWS, individuFromDB); // SIC-41
		setAutreNationalite(resquestForADH.getIndividu(), individuFromWS, individuFromDB); // SIC-41
		
		if ( individuFromWS.getAlias() != null )
		{
			resquestForADH.getIndividu().setAliasNom(NormalizedStringUtils.normalizeString(individuFromWS.getAlias()));
		}
		else if(individuFromDB.getAlias() != null)
			resquestForADH.getIndividu().setAliasNom(NormalizedStringUtils.normalizeString(individuFromDB.getAlias()));
		
		return erasePayments;
	}
    
    public Boolean prepareEmail(List<EmailDTO> emailListFromWS, Boolean erasePayments, IndividuDTO individuFromDB, boolean isFlyingBlue, CreateModifyIndividualResquestDTO resquestForADH, EmailDTO emailHomeFromDB, EmailDTO emailBusinessFromDB, Set<com.airfrance.repind.dto.individu.createmodifyindividual.EmailDTO> emailSetForADH) throws JrafDomainException, AlreadyExistException {
		
    	// Beware of NullPointerException with request <emailRequest/>
		if (emailListFromWS == null || emailListFromWS.isEmpty() || emailListFromWS.get(0) == null) {
			return erasePayments;
		}
		
		EmailDTO emailDomicile = null;
		EmailDTO emailPro = null;
		
		for (EmailDTO emailBo : individuFromDB.getEmaildto()) {
			if (!RefTableREF_STA_MED._REF_X.equals(emailBo
					.getStatutMedium())
					&& !RefTableREF_STA_MED._REF_H.equals(emailBo
							.getStatutMedium())) {
				if (RefTableCAT_MED._REF_D.equals(emailBo
						.getCodeMedium())) {
					emailDomicile = emailBo;
				} else if (RefTableCAT_MED._REF_P.equals(emailBo
						.getCodeMedium())) {
					emailPro = emailBo;
				}
			}
		}
	
		for (EmailDTO emailDTO : emailListFromWS) {
			
			com.airfrance.repind.dto.individu.createmodifyindividual.EmailDTO emailToUpdate = new com.airfrance.repind.dto.individu.createmodifyindividual.EmailDTO();
			emailToUpdate.setAutoriseMailing(emailDTO.getAutorisationMailing());
			emailToUpdate.setCodeMedium(emailDTO.getCodeMedium());
			emailToUpdate.setEmail(emailDTO.getEmail());
			emailToUpdate.setStatutMedium(checksStatutMedium(emailDTO.getStatutMedium())); // SIC-36
			
			if (RefTableCAT_MED._REF_D.equalsIgnoreCase(emailToUpdate.getCodeMedium()) && emailDomicile != null) 
			{
				
				emailToUpdate.setCleEmail(emailDomicile.getSain());
	
				// *** Delete Payments : if first cimmunication email change ***
				if(emailHomeFromDB == null || !emailToUpdate.getEmail().equals(emailHomeFromDB.getEmail()))
				{
					erasePayments = true;
				}
			} 
			
			else if (RefTableCAT_MED._REF_P.equalsIgnoreCase(emailToUpdate.getCodeMedium())&& emailPro != null) 
			{
				
				emailToUpdate.setCleEmail(emailPro.getSain());
				
				// *** Delete Payments : if first cimmunication email change ***
				if(emailBusinessFromDB == null || !emailToUpdate.getEmail().equals(emailBusinessFromDB.getEmail()))
				{
					erasePayments = true;
				}
			}
			emailSetForADH.add(emailToUpdate);
		}
		
		if (!emailSetForADH.isEmpty()) {
			
			// If Individual is MyAccount member, check if the email is not on FB member
			if (!isFlyingBlue) {
				for (com.airfrance.repind.dto.individu.createmodifyindividual.EmailDTO emailDTO : emailSetForADH) {
					if (!emailDS.emailExist(individuFromDB.getSgin(), emailDTO.getEmail()) && emailDS.isEmailFBMember(emailDTO.getEmail(), individuFromDB.getSgin())) {
						throw new AlreadyExistException("Email is already used");										
					}							
				}
			}
			
			// REPIND-1023 remove email for adhesion S08924
			// resquestForADH.setEmail(emailSetForADH);
		}
		return erasePayments;
	}

    
    public void prepareProfil(IndividuDTO individuFromWS, IndividuDTO individuFromDB, CreateModifyIndividualResquestDTO resquestForADH) {
		
		ProfilAvecCodeFonctionValideDTO profilForADH = new ProfilAvecCodeFonctionValideDTO();
		ProfilsDTO profilFromWS = individuFromWS.getProfilsdto();
		ProfilsDTO profilFromDB = individuFromDB.getProfilsdto();
		
		resquestForADH.setProfil(profilForADH);
		
		// No profil given in input
		if(profilFromWS==null) { 
			
			if (individuFromWS.getCodeLangue() != null) {
				profilForADH.setCodeLangue(individuFromWS.getCodeLangue());
			}
			//AJOUT DU CODE LANGUE EXISTANT SI CODE LANGUE NON RENSEIGNE (fixe d'anno du 26-02-2013)
			else if(profilFromDB!=null && profilFromDB.getScode_langue() !=null){
				profilForADH.setCodeLangue(profilFromDB.getScode_langue());
			}
			
			return;
		}
		
		// This is an update -> set id
		if(profilFromDB!=null) { 
			profilForADH.setCleProfil(SicStringUtils.getIntegerFromString(profilFromDB.getSrin()));
		}

		profilForADH.setCodeDomainePro(profilFromWS.getScode_professionnel());
		profilForADH.setCodeEtudiant(profilFromWS.getSetudiant());
		profilForADH.setCodeFonctionPro(profilFromWS.getScode_fonction());
		profilForADH.setCodeLangue(profilFromWS.getScode_langue());
		profilForADH.setCodeMarital(profilFromWS.getScode_maritale());
		profilForADH.setIndicMailing(profilFromWS.getSmailing_autorise());
//		profilADH.setIndicSolvabilite(pIndicSolvabilite);
//		profilADH.setLibelDomainePro(pLibelDomainePro);
//		profilADH.setLibelFonctionPro(pLibelFonctionPro);
		profilForADH.setNbEnfants(profilFromWS.getInb_enfants());
		profilForADH.setSegmentClient(profilFromWS.getSsegment());
		
	}
    
    public void prepareTelecom(List<TelecomsDTO> telecomsListFromWS, IndividuDTO individuFromDB, CreateModifyIndividualResquestDTO resquestForADH) throws InvalidParameterException {
		
		if (telecomsListFromWS != null && !telecomsListFromWS.isEmpty()) {
			
			Set<TelecomDTO> listTelecomToUpdate = new HashSet<TelecomDTO>();
			
			for (TelecomsDTO telecomDTO : telecomsListFromWS) {
				
				TelecomDTO telecomToUpdate = new TelecomDTO();
				
				if (telecomDTO.getSnumero()!=null && telecomDTO.getSnumero().length()>0) {		
					
					telecomToUpdate.setCodeMedium(telecomDTO.getScode_medium());
					telecomToUpdate.setNumeroTelecom(telecomDTO.getSnumero());
					telecomToUpdate.setTypeTerminal(telecomDTO.getSterminal());
					
					telecomToUpdate.setStatutMedium(checksStatutMedium(telecomDTO.getSstatut_medium())); // SIC-36
					
					TelecomsDTO telFound = null;
					
					if (telecomDTO.getScode_medium()!=null && telecomDTO.getSterminal()!=null) {
						
						for ( TelecomsDTO telecomBo : individuFromDB.getTelecoms()) {
							
							if (RefTableREF_STA_MED._REF_V.equalsIgnoreCase(telecomBo.getSstatut_medium()) &&
								telecomDTO.getScode_medium().equalsIgnoreCase(telecomBo.getScode_medium()) &&
								telecomDTO.getSterminal().equalsIgnoreCase(telecomBo.getSterminal())) {
								
								if (telFound==null) {
									telFound = telecomBo;
								} else {
									if (telecomBo.getDdate_modification()!=null) {
										if (telFound.getDdate_modification() == null	) {
											telFound = telecomBo;	
										} else if (telFound.getDdate_modification().before(telecomBo.getDdate_modification())) {
											telFound = telecomBo;
										}
									}
								}
							}
						}
					}
					if (telFound!=null) {							
						telecomToUpdate.setCleTelecom(telFound.getSain());
						telecomToUpdate.setVersion(telFound.getVersion());							
					}
					
					for (TelecomDTO telecomDTOForUpdate : listTelecomToUpdate) {
						if (telecomDTOForUpdate.getTypeTerminal().equalsIgnoreCase(telecomToUpdate.getTypeTerminal()) &&
								telecomDTOForUpdate.getCodeMedium().equalsIgnoreCase(telecomToUpdate.getCodeMedium())) {
							throw new InvalidParameterException("Dooblons in telecoms");
						}
					} 
					
					listTelecomToUpdate.add(telecomToUpdate);
					
				} else { // Le numero est vide donc suppression de tous les telecoms de même type
					
					if (telecomDTO.getScode_medium()!=null && telecomDTO.getSterminal()!=null) {
						
						for ( TelecomsDTO telecomBo : individuFromDB.getTelecoms()) {
							if (RefTableREF_STA_MED._REF_V.equalsIgnoreCase(telecomBo.getSstatut_medium()) && 
								telecomBo.getScode_medium().equalsIgnoreCase(telecomDTO.getScode_medium()) &&
								telecomBo.getSterminal().equalsIgnoreCase(telecomDTO.getSterminal())) {
								telecomToUpdate.setCleTelecom(telecomBo.getSain());
								telecomToUpdate.setVersion(telecomBo.getVersion());
								telecomToUpdate.setCodeMedium(telecomBo.getScode_medium());
								telecomToUpdate.setTypeTerminal(telecomBo.getSterminal());
								telecomToUpdate.setNumeroTelecom(telecomBo.getSnumero());
								telecomToUpdate.setStatutMedium(RefTableREF_STA_MED._REF_X);
								listTelecomToUpdate.add(telecomToUpdate);
							}							
						}
					}
				}
			}
			
			if (!listTelecomToUpdate.isEmpty()) {
				resquestForADH.setTelecom(listTelecomToUpdate);
			}
		}
	}
    
    public void preparePostalAddress(List<PostalAddressDTO> postalAddressListFromWS, IndividuDTO individuFromDB, boolean isFlyingBlue, CreateModifyIndividualResquestDTO resquestForADH) throws InvalidParameterException, JrafDomainRollbackException {
		
		if(postalAddressListFromWS==null || postalAddressListFromWS.isEmpty()) {
			return;
		}
				
		Set<AdressePostaleDTO> listAdrToUpdate = new HashSet<AdressePostaleDTO>();
		
		// Pour un compte Flying Blue
		if (isFlyingBlue) {
			
			PostalAddressDTO addressDomicile = null;
			Usage_mediumDTO usageDomicile = null;
			boolean isMailingDom = false;
			boolean isComplDom = false;
			PostalAddressDTO addressPro = null;
			Usage_mediumDTO usagePro = null;
			boolean isMailingPro = false;
			boolean isComplPro = false;
			
			// Parcourir les adresses en base
			for (PostalAddressDTO postaleAddressBo : individuFromDB.getPostaladdressdto()) {				
				
				if(!postaleAddressBo.getUsage_mediumdto().iterator().hasNext()){
					continue;
				}
				Usage_mediumDTO usageMediumFromWS = postaleAddressBo.getUsage_mediumdto().iterator().next();			
				
				// extraction des données				
				String applicationCodeFromWS = usageMediumFromWS.getScode_application();				
				
				// Si pas d'usage ET pas statut X ET pas statut H
				if (postaleAddressBo.getUsage_mediumdto() != null && !RefTableREF_STA_MED._REF_X.equals(postaleAddressBo.getSstatut_medium()) && !RefTableREF_STA_MED._REF_H.equals(postaleAddressBo.getSstatut_medium())) {
					
					// Parcourir les usages
					for (Usage_mediumDTO usageBo : postaleAddressBo.getUsage_mediumdto()) {
						
						log.debug("Address : " + postaleAddressBo.getSno_et_rue()
									+ ", " + postaleAddressBo.getSstatut_medium()
									+ ", "+ postaleAddressBo.getScode_medium()
									+ ", " + usageBo.getSrole1());
						
						// Si adresse Domicile
						if (RefTableCAT_MED._REF_D.equals(postaleAddressBo.getScode_medium())) {
							addressDomicile = postaleAddressBo;
							usageDomicile = usageBo;
							
							// L'adresse de domicile est principale
							if ("M".equalsIgnoreCase(usageBo.getSrole1())) {
								isMailingDom = true;
							} 
							// L'adresse pro est secondaire
							else if ("C".equalsIgnoreCase(usageBo.getSrole1())) {
								isComplDom = true;
							}
						} 
							
						// Si adresse Pro
						else if (RefTableCAT_MED._REF_P.equals(postaleAddressBo.getScode_medium())) {
							addressPro = postaleAddressBo;
							usagePro = usageBo;
							
							// L'adresse pro est principale
							if ("M".equalsIgnoreCase(usageBo.getSrole1())) {
								isMailingPro = true;
							} 
							// L'adresse pro est secondaire
							else if ("C".equalsIgnoreCase(usageBo.getSrole1())) {
								isComplPro = true;
							}

						}
						
					}
				}
			}
			
			boolean preferedAddressDetected = false;
			int iAdr = 0;
			AdressePostaleDTO preferedAddress = null;
			AdressePostaleDTO complementaryAddress = null;
			
			// SC 03/01/12 : Si les adresses passées n'ont pas de prefered adr ou qu'il est à false pour
			// les deux adresses alors le WS envoie 2 fois la même adresse au service ADH
			// ce qui donne l'erreur : MAJ CONCURRENTE
			// donc dans ce cas il faut réutiliser ceux stockés en DB
			for (PostalAddressDTO postalAddress : postalAddressListFromWS) {
				iAdr++;
				// Il ne peut y avoir qu'il seul prefered address en entrée
				if (postalAddress.getPreferee() == null || postalAddress.getPreferee() == true) {
					
					// seconde adresse préférée détectée -> erreur
					if (preferedAddressDetected) {
						throw new InvalidParameterException("Multiple prefered address");
					} 
					// adresse preférée détectée
					else { 
						preferedAddressDetected = true;
					}
				}
	
				
			}
			
			// parcourir les adresse postales en entrée du WS
			for (PostalAddressDTO postalAddress : postalAddressListFromWS) {
				
				// pas d'adresse préférée on utilise celle stockée en DB
				if(!preferedAddressDetected) {
					// adresse préférée = domicile
					if (RefTableCAT_MED._REF_D.equals(postalAddress.getScode_medium())) {
						postalAddress.setPreferee(isMailingDom);
					} 
					// adresse préférée = pro
					else if (RefTableCAT_MED._REF_P.equals(postalAddress.getScode_medium())) {
						postalAddress.setPreferee(isMailingPro);								
					}
				}
				
				// transformation adhesion
				AdressePostaleDTO adrPost = AdressePostaleTransform.transformToAdressePostaleDTO(postalAddress);
				
				// statut donné en entrée ou VALIDE si pas renseigné
				adrPost.setStatutMedium(MediumStatusEnum.getEnum(postalAddress.getSstatut_medium()).toString());
				
				if(org.apache.commons.lang.StringUtils.isEmpty(adrPost.getNumeroUsage())) {
					adrPost.setNumeroUsage("1");
				}
				
				PostalAddressDTO databaseAddress = null;
				PostalAddressDTO databaseSecondAddress = null;
				boolean isDatabaseSecondAddressPref = false;
				boolean isDatabaseSecondAddressCompl = false;
				Usage_mediumDTO databaseUsage = null;
				Usage_mediumDTO databaseUsageSecond = null;
				
				// adresse = domicile
				if (RefTableCAT_MED._REF_D.equals(adrPost.getCodeMedium())) {
					databaseAddress = addressDomicile;
					databaseUsage = usageDomicile;
					databaseSecondAddress = addressPro;
					databaseUsageSecond = usagePro;							
					isDatabaseSecondAddressPref = isMailingPro;
					isDatabaseSecondAddressCompl = isComplPro;
				} 
				// adresse = pro
				else if (RefTableCAT_MED._REF_P.equals(adrPost.getCodeMedium())) {
					databaseAddress = addressPro;
					databaseUsage = usagePro;
					databaseSecondAddress = addressDomicile;
					databaseUsageSecond = usageDomicile;
					isDatabaseSecondAddressPref = isMailingDom;
					isDatabaseSecondAddressCompl = isComplDom;
				}
				
				boolean isSetPref = false;
				boolean isSetCompl = false;
				
				// l'adresse existe déjà -> maj
				if (databaseAddress != null) {
					
					// récupération du sain
					if(org.apache.commons.lang.StringUtils.isEmpty(adrPost.getCleAdresse())) {
						adrPost.setCleAdresse(databaseAddress.getSain());
					}
					
					// récupération de la version
					if(adrPost.getVersion()==null) {
						adrPost.setVersion(databaseAddress.getVersion());
					}
					
					// récupération du numéro d'usage
					if(org.apache.commons.lang.StringUtils.isEmpty(adrPost.getNumeroUsage())) {
						adrPost.setNumeroUsage(databaseUsage.getInum().toString());
					}
					
					// calcul de l'adresse préférée
					if (postalAddress.getPreferee() != null ) {
						if (postalAddress.getPreferee() == true) {
							preferedAddress = adrPost;
							isSetPref = true;
						} else {
							complementaryAddress = adrPost;
							isSetCompl = true;
						}
					} else if (databaseUsage.getSrole1() != null) {
						if ("M".equalsIgnoreCase(databaseUsage.getSrole1())) {
							preferedAddress = adrPost;
							isSetPref = true;
						} else {
							complementaryAddress = adrPost;
							isSetCompl = true;
						}
					} else {
						complementaryAddress = adrPost;
						isSetCompl = true;
					}
					
				} 
				// l'adresse n'existe pas déjà en base
				else {							
					if (postalAddress.getPreferee() == null	|| postalAddress.getPreferee() == true) {									
						preferedAddress = adrPost;
						isSetPref = true;
					} else {
						complementaryAddress = adrPost;
						isSetCompl = true;
					}
				}
				
				if (isSetPref && complementaryAddress==null) {
					// Si l'usage Mailing est sur une autre adresse, on permute
					if (databaseSecondAddress!=null && isDatabaseSecondAddressPref) {
						complementaryAddress = AdressePostaleTransform.transformToAdressePostaleDTO(databaseSecondAddress);
						// FB: INUM a 1 si CODE APPLICATION = ISI 
						if("ISI".equals(databaseUsageSecond.getScode_application())){
							complementaryAddress.setNumeroUsage("1");
						}
						else{
							complementaryAddress.setNumeroUsage(databaseUsageSecond.getInum().toString());
						}
					}							
				}
	
				if (isSetCompl && preferedAddress==null) {
					// Si l'usage Compl est sur une autre adresse, on permute
					if (databaseSecondAddress!=null && isDatabaseSecondAddressCompl) {
						preferedAddress = AdressePostaleTransform.transformToAdressePostaleDTO(databaseSecondAddress);
						// FB: INUM a 1 si CODE APPLICATION = ISI 
						if("ISI".equals(databaseUsageSecond.getScode_application())){
							preferedAddress.setNumeroUsage("1");
						}
						else{
							preferedAddress.setNumeroUsage(databaseUsageSecond.getInum().toString());
						}
					}							
				}						
			}
			
			if (preferedAddress!=null) {
				preferedAddress.setRoleadresse(new HashSet<RoleAdresseDTO>());
				preferedAddress.getRoleadresse().add(new RoleAdresseDTO("M"));	
				listAdrToUpdate.add(preferedAddress);
			}
			
			if (complementaryAddress!=null) {
				complementaryAddress.setRoleadresse(new HashSet<RoleAdresseDTO>());
				complementaryAddress.getRoleadresse().add(new RoleAdresseDTO("C"));	
				listAdrToUpdate.add(complementaryAddress);
			}
	
		} // fin flying blue 
		
		// pas flying blue donc myaccount
		else {
			
			if (postalAddressListFromWS.size() > 1) {
				throw new JrafDomainRollbackException("Only one address for My Account customer");
			}
			
			PostalAddressDTO addressMyAccount = null;
			
			// parcourir les adresses en base
			for (PostalAddressDTO postaleAddressBo : individuFromDB.getPostaladdressdto()) {
				
				if (postaleAddressBo.getUsage_mediumdto() != null) {
					
					for (Usage_mediumDTO usageBo : postaleAddressBo.getUsage_mediumdto()) {
						
						log.debug("Address : "
									+ postaleAddressBo
											.getSno_et_rue()
									+ ", "
									+ postaleAddressBo
											.getSstatut_medium()
									+ ", "
									+ postaleAddressBo
											.getScode_medium()
									+ ", " + usageBo.getSrole1());
						
						// récupération de l'adresse myaccount
						addressMyAccount = postaleAddressBo;
						
					}
				}
			}
			
			PostalAddressDTO postalAddressFromWS = postalAddressListFromWS.get(0);
			
			// transform to adhesion
			AdressePostaleDTO adrPost = AdressePostaleTransform.transformToAdressePostaleDTO(postalAddressFromWS);
			
			// statut donné en entrée ou VALIDE si pas renseigné
			adrPost.setStatutMedium(MediumStatusEnum.getEnum(postalAddressFromWS.getSstatut_medium()).toString());
			
			// adresse existante en base
			if (addressMyAccount != null) {
				// copie sain
				adrPost.setCleAdresse(addressMyAccount.getSain());
				// copie version
				adrPost.setVersion(addressMyAccount.getVersion());
			} 
			
			// My Account : INUM à 1 si code application ISI
			if (postalAddressFromWS.getUsage_mediumdto() != null) {
				
				for (Usage_mediumDTO usageBo : postalAddressFromWS.getUsage_mediumdto()) {
					
					if("ISI".equals(usageBo.getScode_application())){
						adrPost.setNumeroUsage("1");
					}					
				}
			}
			listAdrToUpdate.add(adrPost);
		}
		
		resquestForADH.setAdressepostale(listAdrToUpdate);
	}
    
    public Set<AdressePostaleDTO> preparePostalAddress(String gin, List<PostalAddressDTO> postalAddressListFromWS) throws JrafDomainException {
		
		if(postalAddressListFromWS==null || postalAddressListFromWS.isEmpty()) {
			return null;
		}
		
		// vérification préalable des adresses postales
		checkPostalAddressFromWS(gin, postalAddressListFromWS);
		
		Set<AdressePostaleDTO> postalAddressListForADH = new HashSet<AdressePostaleDTO>();
		
		// parcours des adresses fournies en entrée du ws
		for(PostalAddressDTO postalAddressFromWS : postalAddressListFromWS) {			
			
			if(!postalAddressFromWS.getUsage_mediumdto().iterator().hasNext()){
				continue;
			}
			Usage_mediumDTO usageMediumFromWS = postalAddressFromWS.getUsage_mediumdto().iterator().next();
			
			// extraction des données
			MediumCodeEnum mediumCodeFromWS = MediumCodeEnum.getEnumMandatory(postalAddressFromWS.getScode_medium());
			String applicationCodeFromWS = usageMediumFromWS.getScode_application();
			Integer versionFromWS = usageMediumFromWS.getInum();
			
			// recherche de l'adresse en base
			PostalAddressDTO postalAddressFromDB = postalAddressDS.findPostalAddress(gin, mediumCodeFromWS, applicationCodeFromWS, versionFromWS);
			
			AdressePostaleDTO postalAddressForADH = null;
			
			// créer l'adresse adhesion à partir de l'adresse provenant du ws
			postalAddressForADH = AdressePostaleTransform.transformToAdressePostaleDTO(postalAddressFromWS);
			
			// update
			if(postalAddressFromDB!=null) {
				
				// set postal address identifier from db
				postalAddressForADH.setCleAdresse(postalAddressFromDB.getSain());
				
				// set postal address version from db if not given in input
				if(postalAddressForADH.getVersion()==null) {
					postalAddressForADH.setVersion(postalAddressFromDB.getVersion());
				}
			} 			
			
			if("ISI".equals(applicationCodeFromWS)) {
				postalAddressForADH.setNumeroUsage("1");
			}
			
			// ajout aux adresses à passer à adhesion
			postalAddressListForADH.add(postalAddressForADH);
			
		}
		
		return postalAddressListForADH;
		
	}
    
    private void setNationalite(InfosIndividuDTO individuForADH, IndividuDTO individuFromWS, IndividuDTO individuFromDB) {
    	   	
    	if (org.apache.commons.lang.StringUtils.isNotEmpty(individuFromWS.getNationalite())) {
    		individuForADH.setNationalite(individuFromWS.getNationalite());
		}
		else if(org.apache.commons.lang.StringUtils.isNotEmpty(individuFromDB.getNationalite())) {
			individuForADH.setNationalite(individuFromDB.getNationalite());
		}
    	
    }
    
    private void setAutreNationalite(InfosIndividuDTO individuForADH, IndividuDTO individuFromWS, IndividuDTO individuFromDB) {
    	
    	if(org.apache.commons.lang.StringUtils.isNotEmpty(individuFromWS.getAutreNationalite())) {
    		individuForADH.setAutreNationalite(individuFromWS.getAutreNationalite());
		} 
    	else if(org.apache.commons.lang.StringUtils.isNotEmpty(individuFromDB.getNationalite())) {
			individuForADH.setAutreNationalite(individuFromDB.getAutreNationalite());
		}
    	
    }
    
    private String checksStatutMedium(String statutMedium) {
    	
    	if(org.apache.commons.lang.StringUtils.isEmpty(statutMedium)) {
			return RefTableREF_STA_MED._REF_V;
		} 
    	
    	return statutMedium;
    }
    
    private void checkPostalAddressFromWS(String gin, List<PostalAddressDTO> postalAddressListFromWS) throws JrafDomainException {
		
		// pas plus d'une adresse pour un MyAccount pur
		if(individuDS.isPureMyAccount(gin) && postalAddressListFromWS.size()>1) {
			throw new InvalidParameterException("No more than 1 postal address allowed for pure MYA");
		}
		
		// parcours des adresses fournies en entrée du ws
		for(PostalAddressDTO postalAddressFromWS : postalAddressListFromWS) {
			
			// récupération des usages
			Set<Usage_mediumDTO> usageMediumListFromWS = postalAddressFromWS.getUsage_mediumdto();
			
			// pas d'usage -> erreur
			if(usageMediumListFromWS==null || usageMediumListFromWS.isEmpty()) {
				throw new MissingParameterException("1 usage is mandatory for 1 postal address");
			}
			
			// plus d'un usage -> erreur
			if(usageMediumListFromWS.size()>1) {
				throw new InvalidParameterException("No more than 1 usage for 1 postal address");
			}
			
		}
		
	}
    
    public void patchRequestMYA(RequeteDTO requeteDTO) {
		
		// nothing to do
		if(requeteDTO==null) {
			return;
		}
		
		// convert MYA into MAC
		if("MYA".equals(requeteDTO.getCodeAppliMetier())) {
			requeteDTO.setCodeAppliMetier("MAC");
		}
		
	}

	public void patchRequestFB(RequeteDTO requeteDTO, boolean isFlyingBlue) {
		
		// nothing to do
		if(requeteDTO==null) {
			return;
		}
		
		// application code is filled -> nothing to do
		if(StringUtils.isNotEmpty(requeteDTO.getCodeAppliMetier())) {
			return;
		}
			
		// individual is a FB member -> ISI
		if(isFlyingBlue) {
			requeteDTO.setCodeAppliMetier("ISI");
		}
		// else individual is a MYA member -> MAC
		else {
			requeteDTO.setCodeAppliMetier("RPD");
		}
		
	}
	
    
}
