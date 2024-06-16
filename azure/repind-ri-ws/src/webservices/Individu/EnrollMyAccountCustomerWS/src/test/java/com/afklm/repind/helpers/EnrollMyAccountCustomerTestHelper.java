package com.afklm.repind.helpers;

import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.w000431.v2.BusinessException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dao.adresse.EmailRepository;
import com.airfrance.repind.dao.individu.AccountDataRepository;
import com.airfrance.repind.dao.individu.IndividuRepository;
import com.airfrance.repind.dao.individu.UsageClientsRepository;
import com.airfrance.repind.dao.profil.ProfilsRepository;
import com.airfrance.repind.dao.role.BusinessRoleRepository;
import com.airfrance.repind.dao.role.RoleContratsRepository;
import com.airfrance.repind.entity.adresse.Email;
import com.airfrance.repind.entity.individu.AccountData;
import com.airfrance.repind.entity.individu.Individu;
import com.airfrance.repind.entity.individu.UsageClients;
import com.airfrance.repind.entity.profil.Profils;
import com.airfrance.repind.entity.role.BusinessRole;
import com.airfrance.repind.entity.role.RoleContrats;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Optional;

@Service
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/config/application-context-spring-test.xml")
@Ignore
public class EnrollMyAccountCustomerTestHelper {

	
    /** the Entity Manager*/
    @PersistenceContext(unitName="entityManagerFactoryRepind")
    private EntityManager entityManager;

	
	@Autowired
	private UsageClientsRepository usageClientsRepository;
	
	@Autowired
	private ProfilsRepository profilsRepository;
	
	@Autowired
	private IndividuRepository individuRepository;
	
	@Autowired
	private EmailRepository emailRepository;
	
	@Autowired
	private RoleContratsRepository roleContratsRepository;
	
	@Autowired
	private AccountDataRepository accountDataRepository;
	
	@Autowired
	private BusinessRoleRepository businessRoleRepository;

	/**
	 * 
	 * Delete all the following data created during the Enroll phase:
	 * Individu, Email, Usage Client, Role Contract, Business Role
	 * Accound data rb, Profil, Account data
	 * 
	 * @param gin of Individu created for the test
	 * @throws BusinessException
	 * @throws SystemException
	 * @throws JrafDomainException
	 */
	@Transactional
	@Rollback(false)
	public void deleteDataAfterTest(String gin) throws JrafDomainException {
		
		if (gin != null) {
			Individu individu = individuRepository.findBySgin(gin);
			
			if (individu != null) {
				
				for (Email email : emailRepository.findEmail(gin)) {
					//individu.getEmail().remove(email);
					emailRepository.delete(email);
				}
				
				UsageClients usage = new UsageClients();
				usage.setSgin(gin);
				for (UsageClients usageClient : usageClientsRepository.findBySgin(gin)) {
					usageClientsRepository.delete(usageClient);
				}

				
				for (RoleContrats roleContrat : roleContratsRepository.findRoleContrats(gin)) {
					// NPE on UnitTest !! 
					if (individu.getRolecontrats() != null) {
						individu.getRolecontrats().remove(roleContrat);
					}
					roleContratsRepository.delete(roleContrat);
					
					BusinessRole businessRole = businessRoleRepository.findByCleRole(roleContrat.getCleRole());
					businessRoleRepository.delete(businessRole);
				}
				
				Optional<Profils> profil = profilsRepository.findById(gin);
				if (profil.isPresent()) {
					individu.setProfils(null);
					profilsRepository.delete(profil.get());
				}
				
				AccountData accountData = accountDataRepository.findBySgin(gin);
				if (accountData != null) {
					individu.setAccountData(null);
					accountDataRepository.delete(accountData);
				}

				individuRepository.delete(individu);
			}
				
		}
	}

	/**
	 * Change the ROBUST_PASSWORD_ERROR_ACTIVATED
	 * 
	 * @param newData
	 */
	@Transactional
	@Rollback(true)
	public void setRobustPasswordActivated(String newData) {
		Query qry = this.entityManager.createNativeQuery(
				"UPDATE SIC2.ENV_VAR set ENVVALUE = :newData WHERE ENVKEY = 'ROBUST_PASSWORD_AUTHENTICATE_ACTIVATED'");
		qry.setParameter("newData", newData);
		qry.executeUpdate();
	}
	
}
