package com.airfrance.jraf.batch.individu;

import com.airfrance.jraf.batch.common.BatchArgs;
import com.airfrance.jraf.batch.common.IBatch;
import com.airfrance.jraf.batch.config.WebConfigBatchRepind;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.exception.jraf.JrafDomainNoRollbackException;
import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;
import com.airfrance.repind.dto.individu.AccountDataDTO;
import com.airfrance.repind.dto.individu.SignatureDTO;
import com.airfrance.repind.dto.role.RoleContratsDTO;
import com.airfrance.repind.service.encryption.internal.EncryptionDS;
import com.airfrance.repind.service.individu.internal.AccountDataDS;
import com.airfrance.repind.service.role.internal.RoleDS;
import com.airfrance.repind.util.LoggerUtils;
import com.airfrance.repind.util.PwdContainer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class BatchAddMissingAccountData extends BatchArgs {

	private final static Log log = LogFactory.getLog(BatchAddMissingAccountData.class);

	private final static String FP_TYPE_CONTRAT = "FP";
	private final static String MA_TYPE_CONTRAT = "MA";
	private final static String C_ETAT_CONTRAT = "C";
	private final static String P_ETAT_CONTRAT = "P";
	private final static List<String> ETAT_LIST = Arrays.asList(C_ETAT_CONTRAT, P_ETAT_CONTRAT);

	private final static String V_ACCOUNT_STATUS = "V";
	private static final int ZERO = 0;
	private static final String INIT_RPD = "INIT_RPD";
	private static final String BATCH_QVI = "BATCH_QVI";

	@Autowired
	@Qualifier("roleDS")
	private RoleDS roleDS;

	@Autowired
	@Qualifier("accountDataDS")
	private AccountDataDS myAccountDataDS;

	@Autowired
	@Qualifier("encryptionDS")
	private EncryptionDS encryptionDS;

	private int elementNumber;
	private int newAccountDataNumber;
	private int remainingGinNumber;

	public static void main(String[] args) {
		log.info("Lancement du batch...");

		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(WebConfigBatchRepind.class);
		IBatch batch = (IBatch) ctx.getBean("batchAddMissingAccountData");

		try {
			log.info("Batch execution...");
			batch.execute();
			ctx.close();

		} catch (JrafDomainException | IOException e) {
			log.fatal("Erreur lors de l'execution de BatchAddMissingAccountData");
			log.fatal(e);
			if (ctx != null) {
				ctx.close();
			}
			System.exit(1);
		}

		log.info(BATCH_EXECUTED_SUCCESSFULLY);
	}

	@Override
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void execute() throws JrafDomainException {
		log.info("Getting the list of individual with valid and active FB contract and "
				+ "don't have an account data in ACCOUNT_DATA table");
		List<RoleContratsDTO> roleContratsDTOList = roleDS.findRoleContratsByTypeWithoutAccountData(FP_TYPE_CONTRAT);

		elementNumber = roleContratsDTOList != null ? roleContratsDTOList.size() : ZERO;
		log.info("Number of elements : " + elementNumber);

		if (roleContratsDTOList != null && !roleContratsDTOList.isEmpty()) {
			log.info("Gin list : " + roleContratsDTOList.stream().map(RoleContratsDTO::getGin)
					.reduce((gin1, gin2) -> gin1 + ", " + gin2).get());

			for (RoleContratsDTO roleContratsDTO : roleContratsDTOList) {

				if (!checkIfOtherAccountExists(roleContratsDTO) && !checkIfOtherRoleContratExists(roleContratsDTO)) {
					createAccountData(roleContratsDTO);
					newAccountDataNumber++;
				}
			}

			log.info("Number of new Account Data : " + newAccountDataNumber);
			log.info("Number of remaining GIN : " + remainingGinNumber);
		}
	}

	/**
	 * 
	 * @param roleContratsDTO
	 * @return True if another account exists for the gin given in parameter. False
	 *         otherwise
	 * @throws JrafDomainException
	 */
	private boolean checkIfOtherAccountExists(RoleContratsDTO roleContratsDTO) throws JrafDomainException {
		String gin = roleContratsDTO.getGin();
		int count = myAccountDataDS.countByGin(gin);

		if (count > 0) {
			log.warn("Another account exists for the gin : " + gin);
			remainingGinNumber++;
			return true;
		}

		return false;
	}

	/**
	 * 
	 * @param roleContratsDTO
	 * @return True if another valid 'MA' contract exists for the gin given in
	 *         parameter. False otherwise
	 * @throws JrafDomainException
	 */
	private boolean checkIfOtherRoleContratExists(RoleContratsDTO roleContratsDTO) throws JrafDomainException {
		boolean result = false;

		String gin = roleContratsDTO.getGin();
		List<RoleContratsDTO> roleContratsList = roleDS.findRoleContrats(gin);

		if (roleContratsList != null && !roleContratsList.isEmpty()) {
			result = roleContratsList.stream().anyMatch(BatchAddMissingAccountData::checkIfRoleContratIsValid);

			if (result) {
				log.warn("Another valid 'MA' contract exists for the gin : " + gin);
				remainingGinNumber++;
			}
		}

		return result;
	}

	/**
	 * 
	 * @param roleContratsDTO
	 * @return True if the contrat is 'MA' (type) and 'C' or 'P' (etat) and valid
	 *         (date). False otherwise
	 */
	private static boolean checkIfRoleContratIsValid(RoleContratsDTO roleContratsDTO) {
		boolean valid = MA_TYPE_CONTRAT.equals(roleContratsDTO.getTypeContrat())
				&& ETAT_LIST.contains(roleContratsDTO.getEtat());

		if (valid) {
			LocalDate today = LocalDate.now();
			LocalDate dateDebutValidite = roleContratsDTO.getDateDebutValidite().toInstant()
					.atZone(ZoneId.systemDefault()).toLocalDate();
			LocalDate dateFinValidite = roleContratsDTO.getDateFinValidite() != null
					? roleContratsDTO.getDateFinValidite().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
					: null;

			valid = today.isAfter(dateDebutValidite) && (dateFinValidite == null || today.isBefore(dateFinValidite));
		}

		return valid;
	}

	/**
	 * Create and save the new Account Data
	 * 
	 * @param roleContratsDTO
	 * @throws JrafDomainException
	 */
	private void createAccountData(RoleContratsDTO roleContratsDTO) throws JrafDomainException {
		String numeroContrat = roleContratsDTO.getNumeroContrat();
		String gin = roleContratsDTO.getGin();

		AccountDataDTO accountDataDTO = new AccountDataDTO();
		accountDataDTO.setSgin(gin); // GIN
		accountDataDTO.setFbIdentifier(numeroContrat); // FB ID
		accountDataDTO.setPersonnalizedIdentifier(numeroContrat.substring(2)); // Personalized ID
		accountDataDTO.setStatus(V_ACCOUNT_STATUS); // Status
		accountDataDTO.setPassword(generatePassword(gin)); // Password
		accountDataDTO.setPasswordToChange(ZERO); // Password to change
		accountDataDTO.setNbFailureAuthentification(ZERO); // Nb failure auth
		accountDataDTO.setNbFailureSecretQuestionAns(ZERO); // Nb failure secret question

		// Signature
		SignatureDTO signatureDTO = new SignatureDTO(null, BATCH_QVI, INIT_RPD, Calendar.getInstance().getTime(), null,
				null, null);
		sign(accountDataDTO, signatureDTO);

		// Create Business Role and 'MA' Role Contrat and set Account Identifier
		String accountIdentifier = myAccountDataDS.getMyAccountIdentifier();
		log.info("Create the new Business Role and 'MA' Role Contrat for " + gin);
		roleDS.createMyAccountContract(gin, accountIdentifier, null, signatureDTO);
		accountDataDTO.setAccountIdentifier(accountIdentifier);

		log.info("Create the new account data for " + accountDataDTO.getSgin());
		myAccountDataDS.create(accountDataDTO);
	}

	private void sign(AccountDataDTO accountDataDTO, SignatureDTO signatureDTO) {
		String site = signatureDTO.getSite();
		String signature = signatureDTO.getSignature();
		Date date = signatureDTO.getDate();

		accountDataDTO.setSiteCreation(site); // Creation site
		accountDataDTO.setSignatureCreation(signature); // Creation signature
		accountDataDTO.setDateCreation(date); // Creation date

		accountDataDTO.setSiteModification(site); // Modif site
		accountDataDTO.setSignatureModification(signature); // Modif signature
		accountDataDTO.setDateModification(date); // Modif date
	}

	private String generatePassword(String gin) {
		String generatedInitialPwd = "";

		try {
			PwdContainer containerInitialPwd = encryptionDS.getCryptedGeneratePassword(gin);
			generatedInitialPwd = containerInitialPwd.getCryptedPwd();

		} catch (Exception e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
		}

		return generatedInitialPwd;
	}

	@Override
	protected void parseArgs(String[] args) {
		// Empty method
	}

	@Override
	protected void printHelp() {
		System.out.println("NAME");
		System.out.println("\tAdd Missing AccountData");
		System.out.println("SINCE");
		System.out.println("\tJira : https://jira.devnet.klm.com/browse/REPIND-2162");
	}

	public int getElementNumber() {
		return elementNumber;
	}

	public int getNewAccountDataNumber() {
		return newAccountDataNumber;
	}

	public int getRemainingGinNumber() {
		return remainingGinNumber;
	}
}
