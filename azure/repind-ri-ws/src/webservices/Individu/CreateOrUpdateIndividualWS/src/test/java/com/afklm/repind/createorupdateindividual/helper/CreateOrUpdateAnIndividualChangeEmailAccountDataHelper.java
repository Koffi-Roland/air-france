package com.afklm.repind.createorupdateindividual.helper;

import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.repind.dao.adresse.EmailRepository;
import com.airfrance.repind.entity.adresse.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("CreateOrUpdateAnIndividualChangeEmailAccountDataHelper")
public class CreateOrUpdateAnIndividualChangeEmailAccountDataHelper {

	@Autowired
	private EmailRepository emailRepository;

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Rollback(false)
	public void after() throws JrafDaoException {

		List<Email> mails = emailRepository.findBySgin("400509651532");
		for (Email mail : mails) {
			if ("amdaveiga@airfrance.fr".equals(mail.getEmail())) {
				mail.setStatutMedium("V");
				emailRepository.saveAndFlush(mail);
			} else {
				emailRepository.delete(mail);
			}
		}

	}

}
