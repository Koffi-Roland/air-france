package com.airfrance.repind.service.individu.internal.it;

import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.airfrance.ref.exception.*;
import com.airfrance.ref.exception.jraf.JrafApplicativeException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.type.ProductTypeEnum;
import com.airfrance.repind.config.WebTestConfig;
import com.airfrance.repind.dao.individu.AccountDataRepository;
import com.airfrance.repind.dto.adresse.EmailDTO;
import com.airfrance.repind.dto.individu.*;
import com.airfrance.repind.dto.individu.enrollmyaccountdata.MyAccountCustomerRequestDTO;
import com.airfrance.repind.dto.individu.enrollmyaccountdata.MyAccountCustomerResponseDTO;
import com.airfrance.repind.dto.individu.provideginforuserid.ProvideGinForUserIdRequestDTO;
import com.airfrance.repind.dto.individu.provideginforuserid.ProvideGinForUserIdResponseDTO;
import com.airfrance.repind.dto.role.RoleContratsDTO;
import com.airfrance.repind.entity.individu.AccountData;
import com.airfrance.repind.entity.individu.Individu;
import com.airfrance.repind.entity.role.RoleContrats;
import com.airfrance.repind.service.adresse.internal.EmailDS;
import com.airfrance.repind.service.individu.internal.AccountDataDS;
import com.airfrance.repind.service.individu.internal.IndividuDS;
import com.airfrance.repind.service.individu.internal.MyAccountDS;
import com.airfrance.repind.service.individu.internal.UsageClientsDS;
import com.airfrance.repind.service.role.internal.RoleDS;
import org.apache.commons.lang.StringUtils;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.*;


/**
 * <p>Title : MyAccountDSTest.java</p>
 * <p>Description : </p>
 * <p>Copyright : Copyright (c) 2014</p>
 * <p>Company : AIRFRANCE</p>
 */
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class MyAccountDSTest extends MyAccountDS{
	
    /**
     * Service Domain Service
     */
	private MyAccountCustomerRequestDTO enrollData;
	private MyAccountUpdateConnectionDataDTO connectionData;
	private MyAccountUpdateConnectionDataV2DTO connectionDataV2;
	@Autowired
	private AccountDataRepository accountDataRepository;
	// REPFIRM-61 : Probleme de purge des Individus ? 
	// private static final String ACCOUNT_ID = "862272AA";
	private static final String ACCOUNT_ID = "354688MA";
		
	// private static final String GIN = "000000102324";
	// private static final String GIN = "123456789";
	private static final String GIN = "000000001255";
	
	private static final String CIVILITY = "MR";
	private static final String EMAIL_IDENTIFIER = "jiyou.nite@airfrance.fr";
	// private static final String EMAIL_IDENTIFIER = "bruno.mortier@wanadoo.fr";
	
	private static final String FIRSTNAME = "JIYOU";
	private static final String LASTNAME = "NITE";
	private static final String LANGUAGE = "FR";
	private static final boolean OPTIN = false;
	private static final String PASSWORD = "456784565";
	private static final Integer GENERATED_PASSWORD_FLAG = 1;
	private static final String CURRENT_PASSWORD = "0FADFE99F5BD";
	private static final String UPDATE_TYPE = "RI_ID";
	private static final String POINT_OF_SALE = "NCE";
	private static final String WEBSITE = "AF";
	private static final String IP_ADDRESS = "127.0.0.1";
	private static final String SIGNATURE = "CID";
	private static final String SITE = "QVI";
	private static final String CONTRACT_STATUS = "C";
	private static final String CONTRACT_TYPE = "C";
	private static final String PRODUCT_TYPE = "MA";
	private static final String ENCRYPTED_PASSWORD = "571C891A3997719BF79550278B32C36C7A7FC02F";
	private SignatureDTO signatureDTO = null;

	@Test
	public void isSecretAnswerValidInvalidParameter() throws JrafApplicativeException, JrafDomainException, NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeySpecException {
		try {
			isSecretAnswerValid("AAA", "aaA", null, "9");
			Assert.fail();
		}catch(InvalidParameterException e) {}
		
		try {
			isSecretAnswerValid("AAA", "aaA", "toto", null);
			Assert.fail();
		}catch(InvalidParameterException e) {}

	}

	/**
	 * Test when passwordToChange for cid call
	 * @throws JrafDomainException
	 * @throws JrafApplicativeException
	 */
	@Test
	public void testPasswordToChangeForCidCall() throws JrafDomainException, JrafApplicativeException {
		initConnectionDataV2();
		AccountData accountData = accountDataRepository.findBySgin(GIN);
		Assert.assertEquals(accountData.getPasswordToChange(), connectionDataV2.getGeneratedPasswordFlag());
	}
	@Test
	public void isSecretAnswerValidValidNull() throws JrafApplicativeException, JrafDomainException, NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeySpecException {
		boolean result = isSecretAnswerValid(null, null, "toto", "978596587468");
		Assert.assertFalse(result);
	}


    /**
     * Test de la methode testProvideGinForUserId
     */
    @Test
    public void testProvideGinForUserId_FP_NotFound() {
        try {
        
            ProvideGinForUserIdRequestDTO request = new ProvideGinForUserIdRequestDTO();
            request.setIdentifier("2033904401");
            request.setIdentifierType("FP");            
        	
            ProvideGinForUserIdResponseDTO dto = this.provideGinForUserId(request);             
            Assert.assertTrue("NOT FOUND", "001".equals(dto.getReturnCode()));
            
        } catch (JrafDomainException e) {
            Assert.fail("Erreur non prevue");
        }
    }

    /**
     * Test de la methode testProvideGinForUserId
     */
    @Test
    public void testProvideGinForUserId_FP_Found() {
        try {
        
            ProvideGinForUserIdRequestDTO request = new ProvideGinForUserIdRequestDTO();
            request.setIdentifier("2033904400");
            request.setIdentifierType("FP");            
                
            ProvideGinForUserIdResponseDTO dto = this.provideGinForUserId(request);             
            Assert.assertTrue("found", dto.getReturnCode() == null);
            
        } catch (JrafDomainException e) {
            Assert.fail("Erreur non prevue");
        }
    }

    /**
     * Test de la methode testProvideGinForUserId
     */
    @Test
    public void testProvideGinForUserId_AC_Found() {
        try {
            ProvideGinForUserIdRequestDTO request = new ProvideGinForUserIdRequestDTO();
            request.setIdentifier("002033904400");
            request.setIdentifierType("AC");            
                
            ProvideGinForUserIdResponseDTO dto = this.provideGinForUserId(request);             
            Assert.assertTrue("found", dto.getReturnCode() == null);
            Assert.assertTrue("type FP", "FP".equals(dto.getFoundIdentifier()));
            
        } catch (JrafDomainException e) {
            Assert.fail("Erreur non prevue");
        }
    }

	/* 
	 *  Method enrollMyAccountCustomer, Test EmailAlreadyUsed no GIN in request: expect an AlreadyExistException 
	 */
	@Test
	public void testEnrollMyAccountCustomerEmailAlreadyUsed() throws JrafDomainException, SystemException {
		System.out.println("testEnrollMyAccountCustomerEmailAlreadyUsed()");
		initEnrollData();
		enrollData.setGin(null);
		initMockAccountDataDS(1);
		try {
			// call web service : request object previously defined will be populated by these parameters
			this.enrollMyAccountCustomer(enrollData, true, false);
			Assert.fail();
		} catch (AlreadyExistException e) {
			Assert.assertEquals("Email identifier already used", e.getMessage());
		}
	}

	/* 
	 *  Method enrollMyAccountCustomer, Test CustomerContractExist GIN present in request: expect a ContractExistException 
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void testEnrollMyAccountCustomerContractExist() throws JrafDomainException, SystemException {
		System.out.println("testEnrollMyAccountCustomerContractExist()");
		initEnrollData();
		initMockMyAccountDataDS();
		initMockRoleContratsDS(true, false);
		try {
			// call web service : request object previously defined will be populated by these parameters
			this.enrollMyAccountCustomer(enrollData, true, false);
			Assert.fail();
		} catch (ContractExistException e) {
			Assert.assertTrue(StringUtils.contains(e.getMessage(), "Email identifier already used"));
		}
	}

	/* 
	 *  Method enrollMyAccountCustomer, Test EmailAlreadyUsed: expect an ContractExistException 
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void testEnrollMyAccountCustomerEmailAlreadyUsed2() throws JrafDomainException, SystemException {
		System.out.println("testEnrollMyAccountCustomerEmailAlreadyUsed2()");
		initEnrollData();
		initMockEmailDS(false);
		try {
			// call web service : request object previously defined will be populated by these parameters
			this.enrollMyAccountCustomer(enrollData, true, false);
			Assert.fail();
		} catch (ContractExistException e) {
			Assert.assertTrue(StringUtils.contains(e.getMessage(), "Email identifier already used"));
		}
	}

	/* 
	 *  Method enrollMyAccountCustomer, Test update an existing MyAccount with status "A": expect an MyAccountCustomerResponseDTO 
	 *  Ne fonctionne pas
	 *  java.lang.NullPointerException
	 *  at com.airfrance.repind.service.individu.internal.MyAccountDS.updateRoleContractMyAccount(MyAccountDS.java:1719)
	 *	lors de l'appel getMyRoleContratDS().getAbstractDSBusinessRole().findByExample(BusinessRoleTransform.dto2BoLight(businessRoleDTO));
	 *	getMyRoleContratDS().getAbstractDSBusinessRole() est null.
	 */
	@Test
	@Ignore
	public void testEnrollMyAccountCustomerUpdate() throws JrafDomainException, SystemException {
		System.out.println("testEnrollMyAccountCustomerUpdate()");
		initEnrollData();
		initMockMyAccountDataDS();
		initMockUsageClients();
		initMockIndividuDS(3);
		try {
			// call web service : request object previously defined will be populated by these parameters
			MyAccountCustomerResponseDTO result = this.enrollMyAccountCustomer(enrollData, true, false);
			assertResult(result);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	/* 
	 *  Method enrollMyAccountCustomer, Test SignatureDTO is null: expect a MissingParameterException
	 */
	@Test
	public void testEnrollMyAccountCustomerMissingSignatureDTO() throws JrafDomainException, SystemException {
		System.out.println("testEnrollMyAccountCustomerMissingSignatureDTO()");
		initEnrollData();
		enrollData.setSignature(null);
		try {
			// call web service : request object previously defined will be populated by these parameters
			this.enrollMyAccountCustomer(enrollData, true, false);
			Assert.fail();
		} catch (MissingParameterException e) {
			Assert.assertNotNull(e);
		}
	}

	/* 
	 *  Method enrollMyAccountCustomer, Test Missing Signature Site: expect a MissingParameterException
	 */
	@Test
	public void testEnrollMyAccountCustomerMissingSignatureSite() throws JrafDomainException, SystemException {
		System.out.println("testEnrollMyAccountCustomerMissingSignatureSite()");
		initEnrollData();
		enrollData.getSignature().setSite(null);
		try {
			// call web service : request object previously defined will be populated by these parameters
			this.enrollMyAccountCustomer(enrollData, true, false);
			Assert.fail();
		} catch (MissingParameterException e) {
			Assert.assertNotNull(e);
		}
	}

	/* 
	 *  Method enrollMyAccountCustomer, Test Missing Signature from SignatureDTO: expect a MissingParameterException
	 */
	@Test
	public void testEnrollMyAccountCustomerMissingSignatureSignature() throws JrafDomainException, SystemException {
		System.out.println("testEnrollMyAccountCustomerMissingSignatureSignature()");
		initEnrollData();
		enrollData.getSignature().setSignature(null);
		try {
			// call web service : request object previously defined will be populated by these parameters
			this.enrollMyAccountCustomer(enrollData, true, false);
			Assert.fail();
		} catch (MissingParameterException e) {
			Assert.assertNotNull(e);
		}
	}

	/* 
	 *  Method enrollMyAccountCustomer, Test Missing firstname: expect a InvalidParameterException
	 */
	@Test
	public void testEnrollMyAccountCustomerMissingFirstName() throws JrafDomainException, SystemException {
		System.out.println("testEnrollMyAccountCustomerMissingFirstName()");
		initEnrollData();
		enrollData.setFirstName("Šylvain");
		try {
			// call web service : request object previously defined will be populated by these parameters
			this.enrollMyAccountCustomer(enrollData, true, false);
			Assert.fail();
		} catch (InvalidParameterException e) {
			Assert.assertNotNull(e);
		}
	}

	/* 
	 *  Method enrollMyAccountCustomer, Test Missing lastname: expect a InvalidParameterException
	 */
	@Test
	public void testEnrollMyAccountCustomerMissingLastName() throws JrafDomainException, SystemException {
		System.out.println("testEnrollMyAccountCustomerMissingLastName()");
		initEnrollData();
		enrollData.setLastName("®obin");
		try {
			// call web service : request object previously defined will be populated by these parameters
			this.enrollMyAccountCustomer(enrollData, true, false);
			Assert.fail();
		} catch (InvalidParameterException e) {
			Assert.assertNotNull(e);
		}
	}

	/* 
	 *  Method enrollMyAccountCustomer, Test SeveralIndividusFound: expect a SeveralIndividualException 
	 */
	@Test
	public void testEnrollMyAccountCustomerSeveralIndividusFound() throws JrafDomainException, SystemException {
		System.out.println("testEnrollMyAccountCustomerSeveralIndividusFound()");
		initEnrollData();
		initMockSeveralIndividus(true);
		enrollData.setGin(null);
		try {
			// call web service : request object previously defined will be populated by these parameters
			this.enrollMyAccountCustomer(enrollData, true, false);
			Assert.fail();
		} catch (SeveralIndividualException e) {
			Assert.assertNotNull(e);
		}
	}

	/* 
	 *  Method enrollMyAccountCustomer, Test RoleContractFound: expect a ContractExistException 
	 */
	@Test
	public void testEnrollMyAccountCustomerRoleContractFound() throws JrafDomainException, SystemException {
		System.out.println("testEnrollMyAccountCustomerRoleContractFound()");
		initEnrollData();
		initMockSeveralIndividus(false);
		enrollData.setGin(null);
		try {
			// call web service : request object previously defined will be populated by these parameters
			this.enrollMyAccountCustomer(enrollData, true, false);
			Assert.fail();
		} catch (ContractExistException e) {
			Assert.assertNotNull(e);
		}
	}

	/* 
	 *  Method enrollMyAccountCustomer, Test AccountDataCreate: expect a MyAccountCustomerResponseDTO 
	 */
	@Test
	@Ignore
	public void testEnrollMyAccountCustomerAccountDataCreate() throws JrafDomainException, SystemException {
		System.out.println("testEnrollMyAccountCustomerAccountDataCreate()");
		initEnrollData();
		initMockMyAccountDataDS();
		initMockEmailDS(true);
		initMockMyRoleContractDSForCreate(false, true);
		initMockUsageClients();
		initMockIndividuDS(3);
		try {
			// call web service : request object previously defined will be populated by these parameters
			MyAccountCustomerResponseDTO result = this.enrollMyAccountCustomer(enrollData, true, false);
			assertResult(result);
		} catch (Exception e) {
			Assert.fail();
		}
	}

	/* 
	 *  Method enrollMyAccountCustomer, Test WithNoPasswordSet: expect a MyAccountCustomerResponseDTO
	 */
	@Test
	@Ignore
	public void testEnrollMyAccountCustomerWithNoPasswordSet() throws JrafDomainException, SystemException {
		System.out.println("testEnrollMyAccountCustomerWithNoPasswordSet()");
		initEnrollData();
		initMockMyAccountDataDS();
		initMockEmailDS(true);
		initMockUsageClients();
		initMockIndividuDS(1);
		initMockMyRoleContractDSForCreate(false, false);
		try {
			// call web service : request object previously defined will be populated by these parameters
			MyAccountCustomerResponseDTO result = this.enrollMyAccountCustomer(enrollData, true, false);
			assertResult(result);
		} catch (Exception e) {
			Assert.fail();
		}
	}

	/* 
	 *  Method deleteMyAccountCustomerConnectionData, Test deleteMyAccountCustomerConnectionData: expect a String 
	 */
	@Test
	public void testDeleteMyAccountCustomerConnectionData() throws JrafDomainException, SystemException {
		System.out.println("testDeleteMyAccountCustomerConnectionData()");
		initConnectionData();
		initMockDeleteMyAccountCustomerConnectionData(true);
		try {
			// call web service : request object previously defined will be populated by these parameters
			String gin = this.deleteMyAccountCustomerConnectionData(connectionData);
			Assert.assertEquals(GIN, gin);
		} catch (JrafDomainException e) {
			Assert.fail();
		}
	}

	/* 
	 *  Method deleteMyAccountCustomerConnectionData, Test NotFoundException: expect a NotFoundException 
	 */
	@Test
	public void testDeleteMyAccountCustomerConnectionDataNotFoundException() throws JrafDomainException, SystemException {
		System.out.println("testDeleteMyAccountCustomerConnectionDataNotFoundException()");
		initConnectionData();
		initMockDeleteMyAccountCustomerConnectionData(false);
		try {
			// call web service : request object previously defined will be populated by these parameters
			this.deleteMyAccountCustomerConnectionData(connectionData);
			Assert.fail();
		} catch (NotFoundException e) {
			Assert.assertNotNull(e);
		}
	}

	private void initConnectionData() {
		connectionData = new MyAccountUpdateConnectionDataDTO();
		connectionData.setGin(GIN);
		signatureDTO = new SignatureDTO();
		signatureDTO.setIpAddress(IP_ADDRESS);
		signatureDTO.setSignature(SIGNATURE);
		signatureDTO.setSite(SITE);
		connectionData.setSignature(signatureDTO);
	}
	private void initConnectionDataV2() {
		connectionDataV2 = new MyAccountUpdateConnectionDataV2DTO();
		connectionDataV2.setGin(GIN);
		connectionDataV2.setUpdateType(UPDATE_TYPE);
		connectionDataV2.setCurrentPassword(CURRENT_PASSWORD);
		SignatureDTO signatureDTO = new SignatureDTO();
		connectionDataV2.setGeneratedPasswordFlag(GENERATED_PASSWORD_FLAG);
		signatureDTO.setSignature(SIGNATURE);
		signatureDTO.setSite(SITE);
		signatureDTO.setIpAddress(IP_ADDRESS);
		connectionDataV2.setSignature(signatureDTO);
	}

	private void initEnrollData(){
		enrollData = new MyAccountCustomerRequestDTO();
		enrollData.setGin(GIN);
		enrollData.setCivility(CIVILITY.toString());
		enrollData.setEmailIdentifier(EMAIL_IDENTIFIER);
		enrollData.setFirstName(FIRSTNAME);
		enrollData.setLastName(LASTNAME);
		enrollData.setLanguage(LANGUAGE.toString());
		enrollData.setOptIn(OPTIN);
		enrollData.setPassword(PASSWORD);
		enrollData.setPointOfSell(POINT_OF_SALE.toString());
		enrollData.setWebsite(WEBSITE.toString());
		signatureDTO = new SignatureDTO();
		signatureDTO.setIpAddress(IP_ADDRESS);
		signatureDTO.setSignature(SIGNATURE);
		signatureDTO.setSite(SITE);
		enrollData.setSignature(signatureDTO);
	}

	private void initMockEmailDS(Boolean isEmptyEmailDTOList) {
		try {
			this.setEmailDS(EasyMock.createMock(EmailDS.class));
			String currentEmail = EasyMock.anyObject(String.class);
			List<EmailDTO> emailDTOList = new ArrayList<EmailDTO>();
			if(!isEmptyEmailDTOList) {
				EmailDTO emailDTO = new EmailDTO();
				emailDTO.setSgin("111111111111");
				emailDTO.setStatutMedium("V");
				emailDTOList.add(emailDTO);
			}
			// Définition des comportements attendus pour le mock
			EasyMock.expect(this.getEmailDS().findByEmail(currentEmail)).andReturn(emailDTOList);
			// Activation du mock
			EasyMock.replay(this.getEmailDS());
			
			this.setMyRoleContratDS(EasyMock.createMock(RoleDS.class));
			RoleContratsDTO roleContratsDTO = EasyMock.anyObject(RoleContratsDTO.class);
			// Définition des comportements attendus pour le mock
			EasyMock.expect(this.getMyRoleContratDS().countAll(roleContratsDTO)).andReturn(1);
			// Activation du mock
			EasyMock.replay(this.getMyRoleContratDS());
		} catch (JrafDomainException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		
	}

	private void initMockMyAccountDataDS() {
		try {
			this.setMyAccountDataDS(EasyMock.createMock(AccountDataDS.class));
			this.setAccountDataRepository(EasyMock.createMock(AccountDataRepository.class));
			List<AccountData> accountDataList = new ArrayList<AccountData>();
			AccountData addto = new AccountData();
			addto.setSgin(GIN);
			accountDataList.add(addto);
			// Définition des comportements attendus pour le mock
			EasyMock.expect(this.getAccountDataRepository().findAll(EasyMock.anyObject(Example.class))).andReturn(accountDataList);
			EasyMock.expect(this.getAccountDataRepository().findByEmailIdentifier(EasyMock.anyObject(String.class))).andReturn(addto);
			EasyMock.expect(this.getMyAccountDataDS().getMyAccountIdentifier()).andReturn(ACCOUNT_ID);
			AccountDataDTO newAccountDataDTO = EasyMock.anyObject(AccountDataDTO.class);
			this.getMyAccountDataDS().create(newAccountDataDTO);
			EasyMock.expectLastCall();
			AccountDataDTO newAccountDataDTO2 = EasyMock.anyObject(AccountDataDTO.class);
			this.getMyAccountDataDS().update(newAccountDataDTO2);
			EasyMock.expectLastCall();
			// Activation du mock
			EasyMock.replay(this.getAccountDataRepository());
			EasyMock.replay(this.getMyAccountDataDS());
		} catch (JrafDomainException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void initMockRoleContratsDS(boolean isNotEmptyRoleContratsDTOList, boolean isMyAccountCancelled) {
		try {
			RoleContratsDTO roleContratsDTO  = EasyMock.anyObject(RoleContratsDTO.class);
			this.setMyRoleContratDS(EasyMock.createMock(RoleDS.class));
			List<RoleContratsDTO> roleContratsDTOList = new ArrayList<RoleContratsDTO>();
			RoleContratsDTO rcdto = new RoleContratsDTO();
			if(isNotEmptyRoleContratsDTOList) {
				if(isMyAccountCancelled) {
					rcdto.setEtat("A");
				} else {
					rcdto.setEtat("C");
				}
				roleContratsDTOList.add(rcdto);
			}
			// Définition des comportements attendus pour le mock
			EasyMock.expect(this.getMyRoleContratDS().findAll(roleContratsDTO)).andReturn(roleContratsDTOList);
			EasyMock.replay(this.getMyRoleContratDS());
		} catch (JrafDomainException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		

	private void initMockSeveralIndividus(boolean addThirdIndividual) {
		try {
			this.setIndividuDS(EasyMock.createMock(IndividuDS.class));
			List<Individu> individus = new ArrayList<Individu>();
			Individu individu = new Individu();
			individu.setType("I");
			Set<RoleContrats> pRolecontrats = new HashSet<RoleContrats>();
			RoleContrats roleContrats = new RoleContrats();
			roleContrats.setTypeContrat(ProductTypeEnum.MY_ACCOUNT.toString());
			pRolecontrats.add(roleContrats);
			individu.setRolecontrats(pRolecontrats );
			individus.add(individu);
			Individu individu2 = new Individu();
			individus.add(individu2);
			if(addThirdIndividual) {
				Individu individu3 = new Individu();
				individus.add(individu3);
			}
			
			List<IndividuDTO> listIndividuDTO = new ArrayList<IndividuDTO>();
			
			IndividuDTO individuDTO = new IndividuDTO();
			
			individuDTO.setNom("NOM");
			individuDTO.setPrenom("PRENOM");
			
			listIndividuDTO.add(individuDTO);
			
			// Définition des comportements attendus pour le mock
			EasyMock.expect(getIndividuDS().findBy(FIRSTNAME, LASTNAME, EMAIL_IDENTIFIER, false, false)).andReturn(individus);
			EasyMock.expect(getIndividuDS().findProspectByEmail(EMAIL_IDENTIFIER)).andReturn(listIndividuDTO);
			// Activation du mock
			EasyMock.replay(this.getIndividuDS());
		} catch (JrafDomainException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
	
	private void initMockIndividuDS(int initPassword){
		try {
			this.setIndividuDS(EasyMock.createMock(IndividuDS.class));
			IndividuDTO individuDTO = new IndividuDTO();
			Set<RoleContratsDTO> pRolecontratDTOSet = new HashSet<RoleContratsDTO>();
			RoleContratsDTO roleContratDTO = new RoleContratsDTO();
			roleContratDTO.setTypeContrat(ProductTypeEnum.FLYING_BLUE.toString());
			pRolecontratDTOSet.add(roleContratDTO);
			RoleContratsDTO roleContratDTO2 = new RoleContratsDTO();
			roleContratDTO2.setTypeContrat("AX");
			pRolecontratDTOSet.add(roleContratDTO2);
			individuDTO.setSgin(GIN);
			individuDTO.setRolecontratsdto(pRolecontratDTOSet);
			AccountDataDTO pAccountdatadto = null;
			switch(initPassword) {
			case 1: 
				pAccountdatadto = new AccountDataDTO();
				pAccountdatadto.setPassword(null);
				individuDTO.setAccountdatadto(pAccountdatadto);
				break;
			case 2:
				pAccountdatadto = new AccountDataDTO();
				pAccountdatadto.setPassword("WRONGPWD");
				individuDTO.setAccountdatadto(pAccountdatadto);
				break;
			case 3:
				pAccountdatadto = new AccountDataDTO();
				pAccountdatadto.setPassword(ENCRYPTED_PASSWORD);
				individuDTO.setAccountdatadto(pAccountdatadto);
				break;
			}
			// Définition des comportements attendus pour le mock
			EasyMock.expect(this.getIndividuDS().getByGin(GIN)).andReturn(individuDTO);
			// Activation du mock
			EasyMock.replay(this.getIndividuDS());
		} catch (JrafDomainException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void initMockUsageClients() throws InvalidParameterException {
		UsageClientsDS usageClientsDS = EasyMock.createMock(UsageClientsDS.class);
		EasyMock.expectLastCall();
		usageClientsDS.add(EasyMock.<String>anyObject(),EasyMock.<String>anyObject(),EasyMock.<Date>anyObject());
		EasyMock.replay(usageClientsDS);
		ReflectionTestUtils.setField(this, "usageClientsDS", usageClientsDS);
	}
	
	private void initMockMyRoleContractDSForCreate(boolean isNotEmptyRoleContratsDTOList, boolean isMyAccountCancelled) {
		try {
			RoleContratsDTO roleContratsDTO  = EasyMock.anyObject(RoleContratsDTO.class);
			this.setMyRoleContratDS(EasyMock.createMock(RoleDS.class));
			List<RoleContratsDTO> roleContratsDTOList = new ArrayList<RoleContratsDTO>();
			RoleContratsDTO rcdto = new RoleContratsDTO();
			if(isNotEmptyRoleContratsDTOList) {
				if(isMyAccountCancelled) {
					rcdto.setEtat("A");
				} else {
					rcdto.setEtat("C");
				}
				roleContratsDTOList.add(rcdto);
			}
			// Définition des comportements attendus pour le mock
			EasyMock.expect(this.getMyRoleContratDS().findAll(roleContratsDTO)).andReturn(roleContratsDTOList);
			EasyMock.expect(this.getMyRoleContratDS().createMyAccountContract(GIN, ACCOUNT_ID, WEBSITE, signatureDTO)).andReturn(ACCOUNT_ID);
			// Activation du mock
			EasyMock.replay(this.getMyRoleContratDS());
		} catch (JrafDomainException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
		
	private void initMockAccountDataDS(Integer nbAccountToReturn) {
		try {
			this.setMyAccountDataDS(EasyMock.createMock(AccountDataDS.class));
			this.setAccountDataRepository(EasyMock.createMock(AccountDataRepository.class));
			// Définition des comportements attendus pour le mock
			EasyMock.expect(this.getAccountDataRepository().countByEmail(EasyMock.anyObject(String.class))).andReturn(nbAccountToReturn);
			EasyMock.expect(this.getMyAccountDataDS().getMyAccountIdentifier()).andReturn(ACCOUNT_ID);
			AccountDataDTO newAccountDataDTO = EasyMock.anyObject(AccountDataDTO.class);
			this.getMyAccountDataDS().create(newAccountDataDTO);
			EasyMock.expectLastCall();
			AccountDataDTO newAccountDataDTO2 = EasyMock.anyObject(AccountDataDTO.class);
			this.getMyAccountDataDS().update(newAccountDataDTO2);
			EasyMock.expectLastCall();
			// Activation du mock
			EasyMock.replay(this.getAccountDataRepository());
			EasyMock.replay(this.getMyAccountDataDS());

			this.setMyRoleContratDS(EasyMock.createMock(RoleDS.class));
			// Définition des comportements attendus pour le mock
			EasyMock.expect(this.getMyRoleContratDS().createMyAccountContract(GIN, ACCOUNT_ID, WEBSITE, signatureDTO)).andReturn(ACCOUNT_ID);
			// Activation du mock
			EasyMock.replay(this.getMyRoleContratDS());
		} catch (JrafDomainException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
	
	private void assertResult(MyAccountCustomerResponseDTO result){
		Assert.assertEquals(ACCOUNT_ID, result.getAccountID());
		Assert.assertEquals(GIN, result.getGin());
		Assert.assertEquals(ACCOUNT_ID, result.getContractRole());
		Assert.assertEquals(CONTRACT_STATUS, result.getContractStatus());
		Assert.assertEquals(CONTRACT_TYPE, result.getContractType());
		Assert.assertEquals(EMAIL_IDENTIFIER, result.getEmail());
		Assert.assertEquals(PRODUCT_TYPE, result.getProductType());
		Assert.assertTrue(dateCondition(result.getValidityStartDate()));
	}

	private boolean dateCondition(Date date){
		Date now = Calendar.getInstance().getTime();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.setTime(now);
		currentDate.set(Calendar.HOUR_OF_DAY, 0);
		currentDate.set(Calendar.MINUTE, 0);
		currentDate.set(Calendar.SECOND, 0);
		currentDate.set(Calendar.MILLISECOND, 0);

		GregorianCalendar refDate = new GregorianCalendar();
		refDate.setTime(date);
		refDate.set(Calendar.HOUR_OF_DAY, 0);
		refDate.set(Calendar.MINUTE, 0);
		refDate.set(Calendar.SECOND, 0);
		refDate.set(Calendar.MILLISECOND, 0);

		if(currentDate.equals(refDate)) {
			return true;
		}
		return false;
	}

	private void initMockDeleteMyAccountCustomerConnectionData(boolean isNotEmptyAccounDataList){
		try {
			this.setMyAccountDataDS(EasyMock.createMock(AccountDataDS.class));
			this.setAccountDataRepository(EasyMock.createMock(AccountDataRepository.class));

			// Définition des comportements attendus pour le mock
			List<AccountDataDTO> accountDataList = new ArrayList<AccountDataDTO>();
			if(isNotEmptyAccounDataList) {
				AccountDataDTO accountData = new AccountDataDTO();
				accountData.setSgin(GIN);
				accountData.setAccountIdentifier(ACCOUNT_ID);
				accountDataList.add(accountData);
			}
			AccountDataDTO accountDataDTO  = EasyMock.anyObject(AccountDataDTO.class);
			EasyMock.expect(this.getMyAccountDataDS().findByExample(accountDataDTO)).andReturn(accountDataList);
			
			AccountDataDTO newAccountDataDTO2 = EasyMock.anyObject(AccountDataDTO.class);
			this.getMyAccountDataDS().update(newAccountDataDTO2);
			EasyMock.expectLastCall();

			// Activation du mock
			EasyMock.replay(this.getMyAccountDataDS());

			this.setMyRoleContratDS(EasyMock.createMock(RoleDS.class));

			// Définition des comportements attendus pour le mock
			RoleContratsDTO newRoleContratsDTO = EasyMock.anyObject(RoleContratsDTO.class);
			List<RoleContratsDTO> roleContratList = new ArrayList<RoleContratsDTO>();
			RoleContratsDTO roleContrat = new RoleContratsDTO();
			roleContratList.add(roleContrat);
			EasyMock.expect(this.getMyRoleContratDS().findAll(newRoleContratsDTO)).andReturn(roleContratList);

			RoleContratsDTO newRoleContratsDTO2 = EasyMock.anyObject(RoleContratsDTO.class);
			this.getMyRoleContratDS().update(newRoleContratsDTO2);
			EasyMock.expectLastCall();

			// Activation du mock
			EasyMock.replay(this.getMyRoleContratDS());

		} catch (JrafDomainException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
