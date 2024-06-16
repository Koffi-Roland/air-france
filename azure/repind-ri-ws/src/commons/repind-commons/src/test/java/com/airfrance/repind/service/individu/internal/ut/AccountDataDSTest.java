package com.airfrance.repind.service.individu.internal.ut;

import com.airfrance.ref.exception.AlreadyExistException;
import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.NotFoundException;
import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.type.MediumStatusEnum;
import com.airfrance.repind.config.WebTestConfig;
import com.airfrance.repind.dao.individu.AccountDataRepository;
import com.airfrance.repind.dto.adresse.EmailDTO;
import com.airfrance.repind.dto.individu.SignatureDTO;
import com.airfrance.repind.entity.individu.AccountData;
import com.airfrance.repind.service.adresse.internal.EmailDS;
import com.airfrance.repind.service.individu.internal.AccountDataDS;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class AccountDataDSTest {
	
	

	@Test(expected=InvalidParameterException.class)
    public void updateLoginEmailMissingGin() throws NotFoundException, JrafDaoException, AlreadyExistException, JrafDomainException {
    	AccountDataDS accountDataDS = new AccountDataDS();
    	List<EmailDTO> emails = new ArrayList<EmailDTO>();
    	EmailDTO email = new EmailDTO();
    	email.setStatutMedium(MediumStatusEnum.VALID.toString());
    	emails.add(email);
    	accountDataDS.updateLoginEmail("", emails, null, null);
    }

	@Test(expected = Test.None.class /* no exception expected */)
    public void updateLoginEmailNoEmailValidDoNothing() throws NotFoundException, JrafDaoException, AlreadyExistException, JrafDomainException {
    	AccountDataDS accountDataDS = new AccountDataDS();
    	accountDataDS.updateLoginEmail("", null, null, null);
    	accountDataDS.updateLoginEmail("", new ArrayList<EmailDTO>(), null, null);
    	List<EmailDTO> emails = new ArrayList<EmailDTO>();
    	EmailDTO email = new EmailDTO();
    	emails.add(email);
    	accountDataDS.updateLoginEmail("", emails, null, null);
    }

	@Test(expected = Test.None.class /* no exception expected */)
	public void updateLoginEmailDoNothingIfAccountDataNull() throws NotFoundException, AlreadyExistException, JrafDomainException {
		AccountDataDS accountDataDS = new AccountDataDS();
		AccountDataRepository accountDataRepository = mock(AccountDataRepository.class);
		AccountData accountData = null;
		when(accountDataRepository.findBySgin("0")).thenReturn(accountData);
		List<EmailDTO> emails = new ArrayList<EmailDTO>();
    	EmailDTO email = new EmailDTO();
    	email.setStatutMedium(MediumStatusEnum.VALID.toString());
    	emails.add(email);
    	ReflectionTestUtils.setField(accountDataDS, "accountDataRepository", accountDataRepository);
    	accountDataDS.updateLoginEmail("0", emails, null, null);
	}
	
	
	@Test
	public void updateLoginEmailLoginModified() throws NotFoundException, AlreadyExistException, JrafDomainException {
		AccountDataDS accountDataDS = new AccountDataDS();
		AccountDataRepository accountDataRepository = mock(AccountDataRepository.class);
		EmailDS emailDS = mock(EmailDS.class);
		AccountData accountData = new AccountData();
		when(accountDataRepository.findBySgin("0")).thenReturn(accountData);
		when(accountDataRepository.countWhereEmailIdentifierAndNotGin("0", "toto@toto.fr")).thenReturn(0);
		when(emailDS.emailExist("0", "toto@toto.fr")).thenReturn(true);
		List<EmailDTO> emails = new ArrayList<EmailDTO>();
    	EmailDTO email = new EmailDTO();
    	email.setStatutMedium(MediumStatusEnum.VALID.toString());
    	email.setEmail("toto@toto.fr");
    	emails.add(email);
    	ReflectionTestUtils.setField(accountDataDS, "emailDS", emailDS);
    	ReflectionTestUtils.setField(accountDataDS, "accountDataRepository", accountDataRepository);
    	SignatureDTO signature = new SignatureDTO();
    	signature.setSignature("toto");
    	signature.setSite("qvi");
    	accountDataDS.updateLoginEmail("0", emails, signature, null);
    	Assert.assertEquals("toto@toto.fr", accountData.getEmailIdentifier());
    	Assert.assertEquals("toto", accountData.getSignatureModification());
    	Assert.assertEquals("qvi", accountData.getSiteModification());
    	Assert.assertNotNull(accountData.getDateModification());
    	Assert.assertNull(accountData.getStatus());
    	Assert.assertNull(accountData.getAccountUpgradeDate());
	}
	
	
	@Test
	public void updateLoginEmailUpdateStatus() throws NotFoundException, AlreadyExistException, JrafDomainException {
		AccountDataDS accountDataDS = new AccountDataDS();
		AccountDataRepository accountDataRepository = mock(AccountDataRepository.class);
		EmailDS emailDS = mock(EmailDS.class);
		AccountData accountData = new AccountData();
		when(accountDataRepository.findBySgin("0")).thenReturn(accountData);
		when(accountDataRepository.countWhereEmailIdentifierAndNotGin("0", "toto@toto.fr")).thenReturn(0);
		when(emailDS.emailExist("0", "toto@toto.fr")).thenReturn(true);
		List<EmailDTO> emails = new ArrayList<EmailDTO>();
    	EmailDTO email = new EmailDTO();
    	email.setStatutMedium(MediumStatusEnum.VALID.toString());
    	email.setEmail("toto@toto.fr");
    	emails.add(email);
    	ReflectionTestUtils.setField(accountDataDS, "emailDS", emailDS);
    	ReflectionTestUtils.setField(accountDataDS, "accountDataRepository", accountDataRepository);
    	SignatureDTO signature = new SignatureDTO();
    	signature.setSignature("toto");
    	signature.setSite("qvi");
    	accountDataDS.updateLoginEmail("0", emails, signature, "U");
    	Assert.assertEquals("U", accountData.getStatus());
    	Assert.assertNotNull(accountData.getAccountUpgradeDate());
	}
	
	
	@Test
	public void updateLoginEmailLoginDeleted() throws NotFoundException, AlreadyExistException, JrafDomainException {
		AccountDataDS accountDataDS = new AccountDataDS();
		AccountDataRepository accountDataRepository = mock(AccountDataRepository.class);
		EmailDS emailDS = mock(EmailDS.class);
		AccountData accountData = new AccountData();
		accountData.setFbIdentifier("1234");
		accountData.setEmailIdentifier("toto@toto.fr");
		when(accountDataRepository.findBySgin("0")).thenReturn(accountData);
		when(accountDataRepository.countWhereEmailIdentifierAndNotGin("0", "toto@toto.fr")).thenReturn(1);
		when(emailDS.emailExist("0", "toto@toto.fr")).thenReturn(true);
		List<EmailDTO> emails = new ArrayList<EmailDTO>();
    	EmailDTO email = new EmailDTO();
    	email.setStatutMedium(MediumStatusEnum.VALID.toString());
    	email.setEmail("toto@toto.fr");
    	emails.add(email);
    	ReflectionTestUtils.setField(accountDataDS, "emailDS", emailDS);
    	ReflectionTestUtils.setField(accountDataDS, "accountDataRepository", accountDataRepository);
    	SignatureDTO signature = new SignatureDTO();
    	signature.setSignature("toto");
    	signature.setSite("qvi");
    	accountDataDS.updateLoginEmail("0", emails, signature, null);
    	Assert.assertEquals("", accountData.getEmailIdentifier());
    	Assert.assertEquals("toto", accountData.getSignatureModification());
    	Assert.assertEquals("qvi", accountData.getSiteModification());
    	Assert.assertNotNull(accountData.getDateModification());
    	Assert.assertNull(accountData.getStatus());
    	Assert.assertNull(accountData.getAccountUpgradeDate());

	}
}
