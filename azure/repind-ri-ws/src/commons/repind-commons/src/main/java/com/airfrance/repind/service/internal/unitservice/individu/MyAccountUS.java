package com.airfrance.repind.service.internal.unitservice.individu;

import com.airfrance.ref.exception.jraf.JrafApplicativeException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dao.individu.AccountDataRepository;
import com.airfrance.repind.dao.role.RoleContratsRepository;
import com.airfrance.repind.dto.individu.WarningDTO;
import com.airfrance.repind.dto.individu.provideginforuserid.ProvideGinForUserIdRequestDTO;
import com.airfrance.repind.dto.individu.provideginforuserid.ProvideGinForUserIdResponseDTO;
import com.airfrance.repind.entity.individu.AccountData;
import com.airfrance.repind.entity.individu.Individu;
import com.airfrance.repind.entity.role.RoleContrats;
import com.airfrance.repind.service.internal.unitservice.role.RoleUS;
import com.airfrance.repind.util.SicStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.*;

@Service
public class MyAccountUS {

	/** logger */
	private static final Log log = LogFactory.getLog(MyAccountUS.class);

	/*PROTECTED REGION ID(_vugpYDNuEeC7FOJn0ffLCA u var) ENABLED START*/
	// add your custom variables here if necessary

	/** Reference sur le unit service RoleContratsUS **/
	@Autowired
	private RoleUS roleContratsUS;
	/*PROTECTED REGION END*/

	/** references on associated DAOs */
	@Autowired
	private AccountDataRepository accountDataRepository;

	/** references on associated DAOs */
	@Autowired
	private RoleContratsRepository roleContratsRepository;

	/**
	 * Constructeur vide
	 */
	public MyAccountUS() {
	}

	
	/*PROTECTED REGION ID(_vugpYDNuEeC7FOJn0ffLCA u m) ENABLED START*/
	// add your custom methods here if necessary

	public AccountDataRepository getAccountDataRepository() {
		return accountDataRepository;
	}


	public void setAccountDataRepository(AccountDataRepository accountDataRepository) {
		this.accountDataRepository = accountDataRepository;
	}


	public RoleContratsRepository getRoleContratsRepository() {
		return roleContratsRepository;
	}

	public void setRoleContratsRepository(RoleContratsRepository roleContratsRepository) {
		this.roleContratsRepository = roleContratsRepository;
	}

	/*
	 * (non-Javadoc)
	 * @see com.airfrance.repind.service.internal.unitservice.individu.IMyAccountUS#provideGinForUserId(com.airfrance.repind.dto.individu.provideginforuserid.ProvideGinForUserIdRequestDTO)
	 */
	
	public ProvideGinForUserIdResponseDTO provideGinForUserId(ProvideGinForUserIdRequestDTO request) throws JrafDomainException {

		String identifierType = request.getIdentifierType();
		String identifierValue = request.getIdentifier();
		ProvideGinForUserIdResponseDTO response = new ProvideGinForUserIdResponseDTO();

		if (MyAccountUS.log.isInfoEnabled()) {
			MyAccountUS.log.info("Call provideGinForUserId with identifierType = " + identifierType + " and identifierValue = " + identifierValue);
		}

		// On verifie que les parametres sont presents
		if (identifierType != null && identifierValue != null) {

			// Dans le cas d'une identification type MyAccount
			// account Number ou Email
			// Recupération du Gin a partir de la table AccountData
			// ou authentification via un social network
			// Recherche du socialNetworkID dans la table accountData
			if ("EM".equals(identifierType) || "MA".equals(identifierType) || "SN".equals(identifierType)) {

				AccountData currentAccount = new AccountData();
				boolean validMail = SicStringUtils.isValidEmail(identifierValue);
				boolean validMyAccount = false;
				boolean bValidFBIdentifier = false;


				try {
					validMyAccount = SicStringUtils.isValidMyAccntIdentifier(identifierValue);
				} catch (JrafApplicativeException e) {
					if (MyAccountUS.log.isInfoEnabled()) {
						MyAccountUS.log.info("provideGinForUserId : MyAccountID not valid");
					}
				}

				try {
					bValidFBIdentifier = SicStringUtils.isValidFbIdentifier(identifierValue);
					if (SicStringUtils.isNumeric(identifierValue) && identifierValue.length() == 10 && !bValidFBIdentifier) {
						long id = Long.parseLong(identifierValue.trim());
						identifierValue = String.format("%012d", id);
						bValidFBIdentifier = SicStringUtils.isValidFbIdentifier(identifierValue);
					}
				} catch (JrafApplicativeException e) {
					if (MyAccountUS.log.isInfoEnabled()) {
						MyAccountUS.log.info("provideGinForUserId : FB identifier not valid");
					}
				}

				// ********************************************
				// Si l'identifiant est un E-mail
				// ********************************************
				if ("EM".equals(identifierType) || validMail) {

					if (MyAccountUS.log.isInfoEnabled()) {
						MyAccountUS.log.info("provideGinForUserId :It's an email : " + identifierValue);
					}
					
					//REPIND-1288: Normalize Email
					identifierValue = SicStringUtils.normalizeEmail(identifierValue); // all emails are in lower case in database

					AccountData searchAccount = new AccountData();
					searchAccount.setEmailIdentifier(identifierValue);

					Object[] fbIdentifierAndGin = accountDataRepository.findFbIdentifierAndGin(searchAccount);
					if (fbIdentifierAndGin != null) {

						String fbIdentifier = (String) fbIdentifierAndGin[0];
						String gin = (String) fbIdentifierAndGin[1];

						if (fbIdentifier != null) {
							// account DTO n'est pas un myAccountPur
							// verification sharedEmail
							if (roleContratsUS.existFrequencePlusContractSharingSameValidEmail(identifierValue, gin)) {

								response.setReturnCode("382"); // EMAIL ALREADY USED BY FLYING BLUE MEMBERS

								if (MyAccountUS.log.isInfoEnabled()) {
									MyAccountUS.log.info("provideGinForUserId returns gin = " + response.getGin() + ", foundIdentifier = " + response.getFoundIdentifier() + ", returnCode = " + response.getReturnCode());
								}
								return response;
							}
						}

					} else {

						if (MyAccountUS.log.isInfoEnabled()) {
							MyAccountUS.log.info("provideGinForUserId : email id not used : " + identifierValue);
						}

						// account DTO n'est pas un myAccountPur
						// verification sharedEmail
						String sGinEmpty = "";
						if (roleContratsUS.existFrequencePlusContractSharingSameValidEmail(identifierValue, sGinEmpty)) {

							response.setReturnCode("382"); // EMAIL ALREADY USED BY FLYING BLUE MEMBERS

							if (MyAccountUS.log.isInfoEnabled()) {
								MyAccountUS.log.info("provideGinForUserId returns gin = " + response.getGin() + ", foundIdentifier = " + response.getFoundIdentifier() + ", returnCode = " + response.getReturnCode());
							}
							return response;
						}
					}
					currentAccount.setEmailIdentifier(identifierValue);

				}
				// ************************************************
				// si l'identifiant est un n° Account sans l'email
				// ************************************************
				else if ("MA".equals(identifierType) && validMyAccount && !validMail) {
					MyAccountUS.log.info("provideGinForUserId : MA : id MyAccount");
					currentAccount.setAccountIdentifier(identifierValue);
				} else if ("MA".equals(identifierType) && bValidFBIdentifier && !validMyAccount && !validMail) {
					MyAccountUS.log.info("provideGinForUserId : MA : id FB identifier");
					currentAccount.setFbIdentifier(identifierValue);
				} else if ("SN".equals(identifierType)) {
					MyAccountUS.log.info("provideGinForUserId : SN : id Social Network identifier");
					currentAccount.setSocialNetworkId(identifierValue);
				}
				// ************************************************
				// sinon c'est un identifiant est un id personnalisé
				// ************************************************
				else {
					// REPIND-1590 : En production, un identifiant personalisé est entre 6 et 16 caractères, un Gigya AF est entre 32 et 79 craractères
					if (identifierValue != null && identifierValue.length() > 20) {
						MyAccountUS.log.info("provideGinForUserId : MA : id Social Network identifier");
						currentAccount.setSocialNetworkId(identifierValue);
					} else {
						MyAccountUS.log.info("provideGinForUserId : MA : personnalized id");
						currentAccount.setPersonnalizedIdentifier(identifierValue);
					}
				}

				// ************************************************
				// LANCEMENT DE LA RECHERCHE
				// ************************************************

				Object[] fbIdentifierAndGin = accountDataRepository.findFbIdentifierAndGin(currentAccount);
				if (fbIdentifierAndGin != null) {

					String fbIdentifier = (String) fbIdentifierAndGin[0];
					String gin = (String) fbIdentifierAndGin[1];

					if (fbIdentifier != null) {

						if (MyAccountUS.log.isInfoEnabled()) {
							MyAccountUS.log.info("provideGinForUserId : FB identifier found : " + fbIdentifier);
						}

						// On vérifie si l'account ne possede pas un contrat FP
						if (!"".equals(fbIdentifier) && fbIdentifier != null) {

							if (MyAccountUS.log.isInfoEnabled()) {
								MyAccountUS.log.info("provideGinForUserId : AccountData : contrat FP : n° : " + fbIdentifier);
							}

							List<String> gins = roleContratsRepository.findGinsByTypeAndNumero("FP", fbIdentifier);

							if (MyAccountUS.log.isInfoEnabled()) {
								MyAccountUS.log.info("contrat MyAccount: verif contrat type FP : " + gins.size() + " entry founds");
							}

							if (!gins.isEmpty()) {
								response.setFoundIdentifier("FP");
							}
						}
					}
					// Sinon c'est un bien un myAccount
					else {

						response.setFoundIdentifier("MA");
					}

					response.setGin(gin);

				} else {

					response.setReturnCode("001"); // NOT FOUND
				}
			}
			// Dans tous les autres cas on cherche le GIN dans RoleContrats
			else {

				// Si c'est un contrat FP, on formate la chaine
				if ("FP".equals(identifierType) && !"".equals(identifierValue.trim())) {

					long id = Long.parseLong(identifierValue.trim());
					identifierValue = String.format("%012d", id);
				}

				if (!"AC".equals(identifierType)) {

					List<String> gins = roleContratsRepository.findGinsByTypeAndNumero(identifierType, identifierValue);
					if (MyAccountUS.log.isInfoEnabled()) {
						MyAccountUS.log.info("provideGinForUserId : " + gins.size() + " entry founds");
					}
					if (!gins.isEmpty()) {
						response.setGin(gins.get(0));
						response.setFoundIdentifier(identifierType);
					} else {
						response.setReturnCode("001"); // NOT FOUND
					}
				} else {

					List<Map<String, ?>> contracts = roleContratsRepository.findSimplePropertiesByNumero(identifierValue);
					if (MyAccountUS.log.isInfoEnabled()) {
						MyAccountUS.log.info("provideGinForUserId : " + contracts.size() + " entry founds");
					}
					if (!contracts.isEmpty()) {
						response.setGin((String) contracts.get(0).get("gin"));
						response.setFoundIdentifier((String) contracts.get(0).get("typeContrat"));
					} else {
						response.setReturnCode("001"); // NOT FOUND
					}
				}
			}
		} else {

			response.setReturnCode("133"); // MISSING PARAMETERS
		}

		if (MyAccountUS.log.isInfoEnabled()) {
			MyAccountUS.log.info("provideGinForUserId returns gin = " + response.getGin() + ", foundIdentifier = " + response.getFoundIdentifier() + ", returnCode = " + response.getReturnCode());
		}

		return response;
	}

	/*
	 * (non-Javadoc)
	 * @see com.airfrance.repind.service.internal.unitservice.individu.IMyAccountUS#provideGinForUserId(com.airfrance.repind.dto.individu.provideginforuserid.ProvideGinForUserIdRequestDTO)
	 */
	
	public ProvideGinForUserIdResponseDTO provideGinForUserIdV2(ProvideGinForUserIdRequestDTO request) throws JrafDomainException {

		String identifierType = request.getIdentifierType();
		String identifierValue = request.getIdentifier();
		ProvideGinForUserIdResponseDTO response = new ProvideGinForUserIdResponseDTO();

		if (MyAccountUS.log.isInfoEnabled()) {
			MyAccountUS.log.info("Call provideGinForUserId with identifierType = " + identifierType + " and identifierValue = " + identifierValue);
		}

		// On verifie que les parametres sont presents
		if (identifierType != null && identifierValue != null) {

			// Dans le cas d'une identification type MyAccount
			// account Number ou Email
			// Recupération du Gin a partir de la table AccountData
			// ou authentification via un social network
			// Recherche du socialNetworkID dans la table accountData
			if ("EM".equals(identifierType) || "MA".equals(identifierType) || "SN".equals(identifierType)) {

				AccountData currentAccount = new AccountData();
				boolean validMail = SicStringUtils.isValidEmail(identifierValue);
				boolean validMyAccount = false;
				boolean bValidFBIdentifier = false;

				try {
					validMyAccount = SicStringUtils.isValidMyAccntIdentifier(identifierValue);
				} catch (JrafApplicativeException e) {
					if (MyAccountUS.log.isInfoEnabled()) {
						MyAccountUS.log.info("provideGinForUserId : MyAccountID not valid");
					}
				}

				try {
					bValidFBIdentifier = SicStringUtils.isValidFbIdentifier(identifierValue);
					if (SicStringUtils.isNumeric(identifierValue) && identifierValue.length() == 10 && !bValidFBIdentifier) {
						long id = Long.parseLong(identifierValue.trim());
						identifierValue = String.format("%012d", id);
						bValidFBIdentifier = SicStringUtils.isValidFbIdentifier(identifierValue);
					}
				} catch (JrafApplicativeException e) {
					if (MyAccountUS.log.isInfoEnabled()) {
						MyAccountUS.log.info("provideGinForUserId : FB identifier not valid");
					}
				}

				// ********************************************
				// Si l'identifiant est un E-mail
				// ********************************************
				if ("EM".equals(identifierType) || validMail) {

					if (MyAccountUS.log.isInfoEnabled()) {
						MyAccountUS.log.info("provideGinForUserId :It's an email : " + identifierValue);
					}

					//REPIND-1288: Normalize Email
					identifierValue = SicStringUtils.normalizeEmail(identifierValue); // all emails are in lower case in database

					AccountData searchAccount = new AccountData();
					searchAccount.setEmailIdentifier(identifierValue);

					Object[] fbIdentifierAndGin = accountDataRepository.findFbIdentifierAndGinV2(searchAccount);
					if (fbIdentifierAndGin != null) {

						String fbIdentifier = (String) fbIdentifierAndGin[0];
						String gin = (String) fbIdentifierAndGin[1];

						if (fbIdentifier != null) {
							// account DTO n'est pas un myAccountPur
							// verification sharedEmail
							if (roleContratsUS.existFrequencePlusContractSharingSameValidEmail(identifierValue, gin)) {

								response.setReturnCode("382"); // EMAIL ALREADY USED BY FLYING BLUE MEMBERS

								if (MyAccountUS.log.isInfoEnabled()) {
									MyAccountUS.log.info("provideGinForUserId returns gin = " + response.getGin() + ", foundIdentifier = " + response.getFoundIdentifier() + ", returnCode = " + response.getReturnCode());
								}
								return response;
							}
						}

					} else {

						if (MyAccountUS.log.isInfoEnabled()) {
							MyAccountUS.log.info("provideGinForUserId : email id not used : " + identifierValue);
						}

						// account DTO n'est pas un myAccountPur
						// verification sharedEmail
						String sGinEmpty = "";
						if (roleContratsUS.existFrequencePlusContractSharingSameValidEmail(identifierValue, sGinEmpty)) {

							response.setReturnCode("382"); // EMAIL ALREADY USED BY FLYING BLUE MEMBERS

							if (MyAccountUS.log.isInfoEnabled()) {
								MyAccountUS.log.info("provideGinForUserId returns gin = " + response.getGin() + ", foundIdentifier = " + response.getFoundIdentifier() + ", returnCode = " + response.getReturnCode());
							}
							return response;
						}
					}
					currentAccount.setEmailIdentifier(identifierValue);

				}
				// ************************************************
				// si l'identifiant est un n° Account sans l'email
				// ************************************************
				else if ("MA".equals(identifierType) && validMyAccount && !validMail) {
					MyAccountUS.log.info("provideGinForUserId : MA : id MyAccount");
					currentAccount.setAccountIdentifier(identifierValue);
				} else if ("MA".equals(identifierType) && bValidFBIdentifier && !validMyAccount && !validMail) {
					MyAccountUS.log.info("provideGinForUserId : MA : id FB identifier");
					currentAccount.setFbIdentifier(identifierValue);
				} else if ("SN".equals(identifierType)) {
					MyAccountUS.log.info("provideGinForUserId : SN : id Social Network identifier");
					currentAccount.setSocialNetworkId(identifierValue);
				}
				// ************************************************
				// sinon c'est un identifiant est un id personnalisé
				// ************************************************
				else {
					MyAccountUS.log.info("provideGinForUserId : MA : personnalized id");
					currentAccount.setPersonnalizedIdentifier(identifierValue);
				}

				// ************************************************
				// LANCEMENT DE LA RECHERCHE
				// ************************************************

				Object[] fbIdentifierAndGin = accountDataRepository.findFbIdentifierAndGinV2(currentAccount);
				if (fbIdentifierAndGin != null) {

					String fbIdentifier = (String) fbIdentifierAndGin[0];
					String gin = (String) fbIdentifierAndGin[1];

					if (fbIdentifier != null) {

						if (MyAccountUS.log.isInfoEnabled()) {
							MyAccountUS.log.info("provideGinForUserId : FB identifier found : " + fbIdentifier);
						}

						// On vérifie si l'account ne possede pas un contrat FP
						if (!"".equals(fbIdentifier) && fbIdentifier != null) {

							if (MyAccountUS.log.isInfoEnabled()) {
								MyAccountUS.log.info("provideGinForUserId : AccountData : contrat FP : n° : " + fbIdentifier);
							}

							List<String> gins = roleContratsRepository.findGinsByTypeAndNumero("FP", fbIdentifier);

							if (MyAccountUS.log.isInfoEnabled()) {
								MyAccountUS.log.info("contrat MyAccount: verif contrat type FP : " + gins.size() + " entry founds");
							}

							if (!gins.isEmpty()) {
								response.setFoundIdentifier("FP");
							}
						}
					}
					// Sinon c'est un bien un myAccount
					else {

						response.setFoundIdentifier("MA");
					}

					response.setGin(gin);

				} else {

					response.setReturnCode("001"); // NOT FOUND
				}
			}
			// Dans tous les autres cas on cherche le GIN dans RoleContrats
			else {

				// Si c'est un contrat FP, on formate la chaine
				if ("FP".equals(identifierType) && !"".equals(identifierValue.trim())) {
					long id = 0;
					try {
						id  = Long.parseLong(identifierValue.trim());
						identifierValue = String.format("%012d", id);
					} catch(Exception e) {
						response.setReturnCode("932");
						return response;
					}
				}

				if (!"AC".equals(identifierType)) {

					List<String> gins = roleContratsRepository.findGinsByTypeAndNumero(identifierType, identifierValue);
					if (MyAccountUS.log.isInfoEnabled()) {
						MyAccountUS.log.info("provideGinForUserId : " + gins.size() + " entry founds");
					}
					if (!gins.isEmpty()) {
						response.setGin(gins.get(0));
						response.setFoundIdentifier(identifierType);
					} else {
						response.setReturnCode("001"); // NOT FOUND
					}
				} else {

					List<Map<String, ?>> contracts = roleContratsRepository.findSimplePropertiesByNumero(identifierValue);
					if (MyAccountUS.log.isInfoEnabled()) {
						MyAccountUS.log.info("provideGinForUserId : " + contracts.size() + " entry founds");
					}
					if (!contracts.isEmpty()) {
						response.setGin((String) contracts.get(0).get("gin"));
						response.setFoundIdentifier((String) contracts.get(0).get("typeContrat"));
					} else {
						response.setReturnCode("001"); // NOT FOUND
					}
				}
			}
		} else {

			response.setReturnCode("133"); // MISSING PARAMETERS
		}

		if (MyAccountUS.log.isInfoEnabled()) {
			MyAccountUS.log.info("provideGinForUserId returns gin = " + response.getGin() + ", foundIdentifier = " + response.getFoundIdentifier() + ", returnCode = " + response.getReturnCode());
		}

		return response;
	}

	
	public Set<WarningDTO> checkValidAccount(Individu pIndividu) throws JrafDomainException {

		Assert.notNull(pIndividu);

		Set<WarningDTO> warnings = new HashSet<>();

		if (pIndividu.getAccountData() != null) {

			// Warning Managment
			// information sur les warnings
			// On verifie la validité de l'account
			String accountStatus = pIndividu.getAccountData().getStatus();

			// check status
			if ("E".equals(accountStatus)) {

				WarningDTO warning = new WarningDTO();
				warning.setWarningCode("ACCOUNT WITH EXPIRED STATUS");
				warnings.add(warning);

			} else if ("D".equals(accountStatus)) {

				WarningDTO warning = new WarningDTO();
				warning.setWarningCode("ACCOUNT WITH DELETED STATUS");
				warnings.add(warning);
			}

			// check account bloqué
			Integer nbFailureAuthentification = pIndividu.getAccountData().getNbFailureAuthentification();
			if (nbFailureAuthentification != null && nbFailureAuthentification == 3) {

				WarningDTO warning = new WarningDTO();
				warning.setWarningCode("ACCOUNT LOCKED");
				warnings.add(warning);
			}

			// On vérifie la validité de l'individu
			String individuStatus = pIndividu.getStatutIndividu();
			if (individuStatus != null) {

				if ("X".equals(individuStatus)) {

					WarningDTO warning = new WarningDTO();
					warning.setWarningCode("INDIVIDUAL CANCELLED");
					warnings.add(warning);

				} else if (individuStatus.equals("D")) {

					WarningDTO warning = new WarningDTO();
					warning.setWarningCode("INDIVIDUAL DEAD");
					warnings.add(warning);

				} else if ("T".equals(individuStatus) && pIndividu.getGinFusion() != null) {

					WarningDTO warning = new WarningDTO();
					warning.setWarningCode("INDIVIDUAL MERGED");
					warning.setWarningDetails(pIndividu.getGinFusion());
					warnings.add(warning);
				}
			}

			// On verifie la validité du contrat si c'est un flying Blue
			String fbIdentifier = pIndividu.getAccountData().getFbIdentifier();
			if (fbIdentifier != null) {

				Set<RoleContrats> contracts = pIndividu.getRolecontrats();
				if (contracts.size() == 2) {

					for (RoleContrats contract : contracts) {

						String typeContrat = contract.getTypeContrat();
						String etatContrat = contract.getEtat();
						Date datefin = contract.getDateFinValidite();
						Date now = Calendar.getInstance().getTime();

						if ("FP".equals(typeContrat)) {

							if (!"C".equals(etatContrat) && !"P".equals(etatContrat)) {

								WarningDTO warning = new WarningDTO();
								warning.setWarningCode("INVALID FB CONTRACT");
								warnings.add(warning);

							} else if (datefin != null) {

								if (datefin.compareTo(now) < 0) {

									WarningDTO warning = new WarningDTO();
									warning.setWarningCode("CLOSED FB CONTRACT");
									warnings.add(warning);
								}
							}
						}
					}
				}
			}
		}

		return warnings;
	}
	
	public ProvideGinForUserIdResponseDTO provideGinByUserIdNoCheckSharedEmail(ProvideGinForUserIdRequestDTO request) throws JrafDomainException {
		
		ProvideGinForUserIdResponseDTO response = null;
		
		AccountData accountData = accountDataRepository.findByEmailIdentifier(request.getIdentifier());
		
		if (accountData != null) {
			
			response = new ProvideGinForUserIdResponseDTO();
						
			if (accountData.getAccountIdentifier() != null && !accountData.getAccountIdentifier().isEmpty()) response.setFoundIdentifier("MA");
			if (accountData.getFbIdentifier() != null && !accountData.getFbIdentifier().isEmpty()) response.setFoundIdentifier("FP");
			if (accountData.getSgin() != null && !accountData.getSgin().isEmpty()) response.setGin(accountData.getSgin());
			response.setReturnCode(null);
		}
		
		return response;
	}

	/*PROTECTED REGION END*/
}
