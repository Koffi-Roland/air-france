package com.airfrance.repind.service.internal.unitservice.adresse;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;
import com.airfrance.repind.dao.adresse.EmailRepository;
import com.airfrance.repind.dto.adresse.EmailDTO;
import com.airfrance.repind.entity.adresse.Email;
import com.airfrance.repind.entity.firme.PersonneMorale;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import static com.airfrance.repind.entity.refTable.RefTableCAT_MED._REF_F;
import static com.airfrance.repind.entity.refTable.RefTableCAT_MED._REF_L;
import static com.airfrance.repind.entity.refTable.RefTableCAT_MED._REF_M;
import static com.airfrance.repind.entity.refTable.RefTableCAT_MED._REF_P;
import static com.airfrance.repind.entity.refTable.RefTableREF_AUTORIS_MAIL._REF_A;
import static com.airfrance.repind.entity.refTable.RefTableREF_AUTORIS_MAIL._REF_N;
import static com.airfrance.repind.entity.refTable.RefTableREF_AUTORIS_MAIL._REF_T;
import static com.airfrance.repind.entity.refTable.RefTableREF_ERREUR._REF_003;
import static com.airfrance.repind.entity.refTable.RefTableREF_ERREUR._REF_100;
import static com.airfrance.repind.entity.refTable.RefTableREF_ERREUR._REF_116;
import static com.airfrance.repind.entity.refTable.RefTableREF_ERREUR._REF_117;
import static com.airfrance.repind.entity.refTable.RefTableREF_ERREUR._REF_132;
import static com.airfrance.repind.entity.refTable.RefTableREF_ERREUR._REF_136;
import static com.airfrance.repind.entity.refTable.RefTableREF_ERREUR._REF_165;
import static com.airfrance.repind.entity.refTable.RefTableREF_ERREUR._REF_220;
import static com.airfrance.repind.entity.refTable.RefTableREF_ERREUR._REF_904;
import static com.airfrance.repind.entity.refTable.RefTableREF_STA_MED._REF_I;
import static com.airfrance.repind.entity.refTable.RefTableREF_STA_MED._REF_V;
import static com.airfrance.repind.entity.refTable.RefTableREF_STA_MED._REF_X;
import static com.airfrance.repind.entity.refTable.RefTableREF_ERREUR._REF_132;
import static com.airfrance.repind.util.SicStringUtils.isValidEmail;
import static org.apache.commons.lang.StringUtils.isEmpty;
import static org.apache.commons.lang.StringUtils.isNotEmpty;

@Service
public class EmailUS {

	/** logger */
	private static final Log log = LogFactory.getLog(EmailUS.class);

	/* PROTECTED REGION ID(_9INH4McREd-5RaDkbS2Gmg u var) ENABLED START */
	// add your custom variables here if necessary
	/* PROTECTED REGION END */

	/** references on associated DAOs */
	@Autowired
	private EmailRepository emailRepository;

	/**
	 * empty constructor
	 */
	public EmailUS() {
	}

	public EmailRepository getEmailRepository() {
		return emailRepository;
	}

	public void setEmailRepository(EmailRepository emailRepository) {
		this.emailRepository = emailRepository;
	}

	/**
	 * search
	 *
	 * @param email
	 *            in String
	 * @return The search as <code>List<EmailDTO></code>
	 * @throws JrafDomainException
	 *             en cas d'exception
	 */
	
	public List<EmailDTO> search(String email) throws JrafDomainException {
		/* PROTECTED REGION ID(_XXNLYMcSEd-5RaDkbS2Gmg) ENABLED START */
		// TODO method search(List<EmailDTO> email) to implement
		return null;
		/* PROTECTED REGION END */
	}

	/* PROTECTED REGION ID(_9INH4McREd-5RaDkbS2Gmg u m) ENABLED START */
	// add your custom methods here if necessary
	/**
	 * <ul>
	 * 		<li>Check the following mandatory email fields and throw a specific exception :</li>
	 * 			<ul>
	 * 				<li>codeMedium</li>
	 * 				<li>emailAddress</li>
	 * 				<li>statutMedium</li>
	 * 				<li>sain</li>
	 * 				<li>autorisationMailing</li>
	 * 			</ul>
	 * </ul>
	 * @param email cannot be null
	 * @param onlyLocalisationForFonction
	 * @throws JrafDomainRollbackException
	 */
	protected void checkMandatoryAndValidity(Email email, boolean onlyLocalisationForFonction) throws JrafDomainRollbackException {

		String codeMedium = email.getCodeMedium();
		if (isEmpty(codeMedium)) {
			// REF_ERREUR : MEDIUM
			throw new JrafDomainRollbackException("117");
		}

		if (!onlyLocalisationForFonction) {
			if (!"F".equals(codeMedium) && !"L".equals(codeMedium)
					&& !"M".equals(codeMedium) && !"P".equals(codeMedium))
			{
				// REF_ERREUR : INVALID MEDIUM CODE
				throw new JrafDomainRollbackException("116");
			}
		} else {
			if (!"L".equals(codeMedium))
			{
				// REF_ERREUR : INVALID MEDIUM CODE
				throw new JrafDomainRollbackException("116");
			}
		}

		String emailAddress = email.getEmail();
		String statutMedium = email.getStatutMedium();
		if (isEmpty(emailAddress)
				|| (isNotEmpty(emailAddress) && isEmpty(statutMedium))) {
			throw new JrafDomainRollbackException("100");
		}

		String sain = email.getSain();
		if (isNotEmpty(sain)
				&& !"I".equals(statutMedium)
				&& !"V".equals(statutMedium)) {
			throw new JrafDomainRollbackException("136");
		}

		if (isEmpty(sain)
				&& !"V".equals(statutMedium)) {
			throw new JrafDomainRollbackException("136");
		}

		String autorisationMailing = email.getAutorisationMailing(); 
		if (isEmpty(autorisationMailing)
				|| (!"A".equals(autorisationMailing)
						&& !"N".equals(autorisationMailing)
						&& !"T".equals(autorisationMailing))) {
			throw new JrafDomainRollbackException("165");
		}

		if (!isValidEmail(emailAddress)) {
			// REF_ERREUR : INVALID MAILING
			throw new JrafDomainRollbackException("220");
		}
	}

	public void createOrUpdateOrDelete(List<Email> pEmails,
									   PersonneMorale pPersonneMorale) throws JrafDomainException {

		Assert.notNull(pEmails);
		Assert.notNull(pPersonneMorale);
		Assert.notNull(pPersonneMorale.getGin());

		// Initialize PersonneMorale Chiffres
		if (pPersonneMorale.getEmails() == null) {
			pPersonneMorale.setEmails(new HashSet<Email>());
		}

		List<Email> emailsToCreate = new ArrayList<>();
		// List<Email> emailsToRemove = new ArrayList<Email>();
		List<Email> emailsToUpdate = new ArrayList<>();

		// Check Validity of all Emails in the list
		for (Email email : pEmails) {
			Email emailToSave = check(email, pPersonneMorale);

			if (StringUtils.isEmpty(emailToSave.getSain())) {
				emailsToCreate.add(emailToSave);
			} else {
				emailsToUpdate.add(emailToSave);
			}
		}

		// Check Rule for global postal addresses
		// List to check the global RM
		List<Email> emails = new ArrayList<>(pPersonneMorale.getEmails());
		// emails.removeAll(emailsToRemove);
		emails.addAll(emailsToCreate);

		// REPFIRM-606: Only V status are counted
		// Only one address (status V)
		// ERROR 132 "MAXIMUM NUMBER OF ADDRESS REACHED"
		int nbEmailVMax = 1;
		for (Email email : emails) {
			if (_REF_V.equals(email.getStatutMedium())) {
				nbEmailVMax--;
			}

			if (nbEmailVMax < 0)
			{
				throw new JrafDomainRollbackException(_REF_132); // REF_ERREUR :
				// MAXIMUM
				// NUMBER OF
				// ADDRESS
				// REACHED
			}
		}

		// Enregistrement en base
		for (Email email : emailsToCreate) {
			emailRepository.saveAndFlush(email);
		}
		for (Email email : emailsToUpdate) {
			emailRepository.saveAndFlush(email);
		}

	}

	private Email check(Email emailToCheck, PersonneMorale pPersonneMorale) throws JrafDomainRollbackException {

		// chiffreSaved : bo used with DAO layer
		Email emailChecked = null;

		// Actual date
		Date now = new Date();

		// No ain sent with valid data will generate a create of this email
		if (StringUtils.isEmpty(emailToCheck.getSain())) {

			// Creation
			//----------

			checkMandatoryAndValidity(emailToCheck, false);

			emailToCheck.setDateCreation(now); // siteCreation et signatureCreation ont normalt �t� sett�s par l'appelant

			emailToCheck.setDateModification(now); // doit �tre sett� � cause de la r�plication
			emailToCheck.setSignatureModification(emailToCheck.getSignatureCreation()); // doit �tre sett� � cause de la r�plication
			emailToCheck.setSiteModification(emailToCheck.getSiteCreation()); // doit �tre sett� � cause de la r�plication

			emailToCheck.setFonction(null);
			emailToCheck.setCleRole(null);
			emailToCheck.setPersonneMorale(pPersonneMorale);

			emailChecked = emailToCheck;
		} else {

			// modify or delete
			//------------------

			// Get the existing email
			Email existingEmail = null;
			List<Email> lstEmailExisting = new ArrayList<>(pPersonneMorale.getEmails());
			for (Email existingE : lstEmailExisting) {
				if (existingE.getSain().trim().equals(emailToCheck.getSain()) || existingE.getSain().equals(emailToCheck.getSain())) {
					existingEmail = existingE;

					lstEmailExisting.remove(existingE); // remove the email to
					// modify from the list
					// of existing email
					break;
				}
			}

			if (existingEmail == null) {

				throw new JrafDomainRollbackException(_REF_904); // SAIN DOES NOT EXIST
			}

			// On v�rifie que la version en entr�e co�ncide avec la version stock�e
			else if (!existingEmail.getVersion().equals(emailToCheck.getVersion())) {

				throw new JrafDomainRollbackException(_REF_003);

			} else if (!StringUtils.isEmpty(emailToCheck.getCodeMedium())
					&& !StringUtils.isEmpty(emailToCheck.getStatutMedium())
					&& !StringUtils.isEmpty(emailToCheck.getEmail())) {

				// Modify
				//---------

				checkMandatoryAndValidity(emailToCheck, false);

				existingEmail.setDateModification(now);
				existingEmail.setSignatureModification(emailToCheck.getSignatureModification());
				existingEmail.setSiteModification(emailToCheck.getSiteModification());

				existingEmail.setAutorisationMailing(emailToCheck.getAutorisationMailing());
				existingEmail.setCodeMedium(emailToCheck.getCodeMedium());
				existingEmail.setDescriptifComplementaire(emailToCheck.getDescriptifComplementaire());
				existingEmail.setEmail(emailToCheck.getEmail());
				existingEmail.setStatutMedium(emailToCheck.getStatutMedium());

				emailChecked = existingEmail;
			} else {

				// Suppression
				//-------------

				existingEmail.setStatutMedium(_REF_X); // flag to delete with status = X

				existingEmail.setDateModification(now);
				existingEmail.setSignatureModification(emailToCheck.getSignatureModification());
				existingEmail.setSiteModification(emailToCheck.getSiteModification());

				emailChecked = existingEmail;
			}
		}

		return emailChecked;
	}

}


