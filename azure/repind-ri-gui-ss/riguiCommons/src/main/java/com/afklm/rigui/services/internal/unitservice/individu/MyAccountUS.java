package com.afklm.rigui.services.internal.unitservice.individu;

import com.afklm.rigui.exception.jraf.JrafApplicativeException;
import com.afklm.rigui.exception.jraf.JrafDomainException;
import com.afklm.rigui.dao.individu.AccountDataRepository;
import com.afklm.rigui.dao.role.RoleContratsRepository;
import com.afklm.rigui.dto.individu.WarningDTO;
import com.afklm.rigui.dto.individu.provideginforuserid.ProvideGinForUserIdRequestDTO;
import com.afklm.rigui.dto.individu.provideginforuserid.ProvideGinForUserIdResponseDTO;
import com.afklm.rigui.entity.individu.AccountData;
import com.afklm.rigui.entity.individu.Individu;
import com.afklm.rigui.entity.role.RoleContrats;
import com.afklm.rigui.services.internal.unitservice.role.RoleUS;
import com.afklm.rigui.util.SicStringUtils;
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

	public Set<WarningDTO> checkValidAccount(Individu pIndividu) throws JrafDomainException {

		Assert.notNull(pIndividu,"pIndividu is not null");

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
