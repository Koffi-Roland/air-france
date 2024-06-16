package com.airfrance.repind.service.ut;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.config.WebTestConfig;
import com.airfrance.repind.dao.adresse.EmailRepository;
import com.airfrance.repind.dao.individu.AccountDataRepository;
import com.airfrance.repind.dto.adresse.EmailDTO;
import com.airfrance.repind.entity.adresse.Email;
import com.airfrance.repind.service.adresse.internal.EmailDS;
import com.airfrance.repind.service.role.internal.RoleDS;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class EmailDSTest {
	
	private static final Log logger = LogFactory.getLog(EmailDSTest.class);
	
	@Spy
	private RoleDS roleDS;
	
	@Mock
	private AccountDataRepository accountDataRepository;
	
	@Mock
	private EmailRepository emailRepository;
	
	@InjectMocks
	private EmailDS emailDS;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	String ginMock = "400424668522";

	@Test
	public void emailDSTest_missingMediumCode() throws JrafDomainException {
		List<EmailDTO> emailList = new ArrayList<EmailDTO>();
		EmailDTO dto = new EmailDTO();
		dto.setStatutMedium("V");
		dto.setEmail("test@test.fr");
		emailList.add(dto);
		
		Mockito.when(emailRepository.findAll(Mockito.any(Example.class))).thenReturn(new ArrayList<Email>());
		
		try {
			emailDS.updateEmail(ginMock, emailList, null);
			Assert.fail();
		} catch (JrafDomainException e) {
			logger.error(e.getMessage());
			Assert.assertTrue(e.getMessage().contains("Medium code is mandatory"));
		}
	}
}
