package com.airfrance.repind.service.internal.unitservice.adresse;


import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;
import com.airfrance.repind.config.WebTestConfig;
import com.airfrance.repind.entity.adresse.Email;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static com.airfrance.repind.entity.refTable.RefTableREF_ERREUR._REF_165;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class EmailUSTest {

	@Autowired
	EmailUS emailUS;
	
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
	private static final String STATUT_MEDIUM = "V";
	private static final String CODE_MEDIUM = "L";
	private static final String STRING_EMAIL = "sylvain@dev.fr";

	@Test
	public void testCheckMandatoryAndValidityWithoutAutorisationMailing() throws JrafDomainException {
		Email email = new Email();
		email.setStatutMedium(STATUT_MEDIUM);
		email.setCodeMedium(CODE_MEDIUM);
		email.setEmail(STRING_EMAIL);
		expectedException.expect(JrafDomainRollbackException.class);
		expectedException.expectMessage(_REF_165);
		emailUS.checkMandatoryAndValidity(email, true);
	}
	
	@Test
	public void testCheckMandatoryAndValidityWithValidAutorisationMailing() throws JrafDomainException {
		Email email = new Email();
		email.setStatutMedium(STATUT_MEDIUM);
		email.setCodeMedium(CODE_MEDIUM);
		email.setEmail(STRING_EMAIL);
		email.setAutorisationMailing("N");
		emailUS.checkMandatoryAndValidity(email, true);
	}
	
	@Test
	public void testCheckMandatoryAndValidityWithInvalidAutorisationMailing() throws JrafDomainException {
		Email email = new Email();
		email.setStatutMedium(STATUT_MEDIUM);
		email.setCodeMedium(CODE_MEDIUM);
		email.setEmail(STRING_EMAIL);
		email.setAutorisationMailing("K");
		
		expectedException.expect(JrafDomainRollbackException.class);
		expectedException.expectMessage(_REF_165);
		emailUS.checkMandatoryAndValidity(email, true);
	}
}
