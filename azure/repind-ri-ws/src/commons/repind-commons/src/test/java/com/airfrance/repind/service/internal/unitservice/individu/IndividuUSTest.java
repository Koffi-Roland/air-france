package com.airfrance.repind.service.internal.unitservice.individu;

import com.airfrance.ref.exception.MissingParameterException;
import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.ref.type.IdentifierOptionTypeEnum;
import com.airfrance.repind.config.WebTestConfig;
import com.airfrance.repind.dao.individu.IndividuRepository;
import com.airfrance.repind.dto.individu.RequeteHomonymesDTO;
import com.airfrance.repind.dto.individu.adh.searchindividualbymulticriteria.*;
import com.airfrance.repind.dto.individu.provideginforuserid.ProvideGinForUserIdRequestDTO;
import com.airfrance.repind.dto.individu.provideginforuserid.ProvideGinForUserIdResponseDTO;
import com.airfrance.repind.entity.individu.Individu;
import com.airfrance.repind.entity.refTable.RefTableREF_ERREUR;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * Title : EtablissementDSTest.java
 * </p>
 * <p>
 * Description :
 * </p>
 * <p>
 * Copyright : Copyright (c) 2014
 * </p>
 * <p>
 * Company : AIRFRANCE
 * </p>
 */
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class IndividuUSTest extends IndividuUS {

	private static final String EMAIL = "test@airfrance.fr";
	private static final String FIRSTNAME = "sylvain";
	private static final String LASTNAME = "nicolas";
	private static final String GIN = "124578452168";
	private static final String PHONE = "0966332201";
	/**
	 * Individu Unit Service
	 */
	@Autowired
	private IndividuUS individuUS;

	@Autowired
	private IndividuRepository individuRepository;
	
	private MyAccountUS myAccountUSMock;
	
	@Autowired
	private IndividuUS individuUSMock;
	
	@Before
	public void setUp() {
		myAccountUSMock = Mockito.mock(MyAccountUS.class);
		individuUSMock.setMyAccountUS(myAccountUSMock);
	}

	/**
	 * Test de la methode findGinByIdentifier
	 */
	@Test
	@Transactional
	public void testFindGinByIdentifier1() throws Exception {
		String identifierType = IdentifierOptionTypeEnum.GIN.toString();
		String identifierValue = "12345";
		String gin = individuUS.findGinByIdentifier(identifierType, identifierValue);
		Assert.assertEquals("12345", gin);
	}

	/**
	 * Test de la methode findGinByIdentifier
	 */
	@Test
	@Transactional
	public void testFindGinByIdentifier2() throws Exception {
		String identifierType = IdentifierOptionTypeEnum.FLYING_BLUE.toString();
		String identifierValue = "2083541643";
		String gin = individuUS.findGinByIdentifier(identifierType, identifierValue);
		Assert.assertEquals("400368703835", gin);
	}

	/**
	 * Test de la methode findGinByIdentifier
	 */
	@Test
	@Transactional
	public void testFindGinByIdentifier3() throws Exception {
		String identifierType = IdentifierOptionTypeEnum.FLYING_BLUE.toString();
		String identifierValue = "002083541643";
		String gin = individuUS.findGinByIdentifier(identifierType, identifierValue);
		Assert.assertEquals("400368703835", gin);
	}

	/**
	 * Test de la methode findGinByIdentifier
	 */
	@Test
	@Transactional
	public void testFindGinByIdentifier4() throws Exception {
		ProvideGinForUserIdResponseDTO response = new ProvideGinForUserIdResponseDTO();
		response.setFoundIdentifier("FP");
		response.setGin("400368703835");
		
		Mockito.doReturn(response).when(myAccountUSMock).provideGinForUserId(Mockito.any(ProvideGinForUserIdRequestDTO.class));
		
		String identifierType = IdentifierOptionTypeEnum.ANY_MYACCOUNT.toString();
		String identifierValue = "2083541643";
		String gin = individuUSMock.findGinByIdentifier(identifierType, identifierValue);
		Assert.assertEquals("400368703835", gin);
	}

	/**
	 * Test de la methode findGinByIdentifier
	 */
	@Test
	@Transactional
	public void testFindGinByIdentifier5() throws Exception {
		ProvideGinForUserIdResponseDTO response = new ProvideGinForUserIdResponseDTO();
		response.setFoundIdentifier("FP");
		response.setGin("400368703835");
		
		Mockito.doReturn(response).when(myAccountUSMock).provideGinForUserId(Mockito.any(ProvideGinForUserIdRequestDTO.class));
		
		String identifierType = IdentifierOptionTypeEnum.ANY_MYACCOUNT.toString();
		String identifierValue = "370668AD";
		String gin = individuUSMock.findGinByIdentifier(identifierType, identifierValue);
		Assert.assertEquals("400368703835", gin);
	}

	@Test
	public void testCreerIndividusByNameWithEmailOnly() throws JrafDaoException {
		RequeteHomonymesDTO requete = new RequeteHomonymesDTO();
		requete.setEmail(EMAIL);
		mockEntityManager();
		mockIndividuDAO();
		List<Individu> result = this.creerIndividusByName(requete);
		Assert.assertNotNull(result);
	}

	@Test
	public void testCreerIndividusByNameWithFirstNameAndLastName() throws JrafDaoException {
		RequeteHomonymesDTO requete = new RequeteHomonymesDTO();
		requete.setPrenom(FIRSTNAME);
		requete.setNom(LASTNAME);
		requete.setTelephone(PHONE);
		mockEntityManager();
		List<Individu> result = this.creerIndividusByName(requete);
		Assert.assertNotNull(result);
	}

	@Test
	public void testCreerIndividusByNameWithFirstNameAndLastNameAndPhone() throws JrafDaoException {
		RequeteHomonymesDTO requete = new RequeteHomonymesDTO();
		requete.setPrenom(FIRSTNAME);
		requete.setNom(LASTNAME);
		requete.setEmail(EMAIL);
		mockEntityManager();
		List<Individu> result = this.creerIndividusByName(requete);
		Assert.assertNotNull(result);
	}

	@Test
	public void testCreerIndividusByNameWithFirstNameAndLastNameAndEmail() throws JrafDaoException {
		RequeteHomonymesDTO requete = new RequeteHomonymesDTO();
		requete.setPrenom(FIRSTNAME);
		requete.setNom(LASTNAME);
		mockEntityManager();
		List<Individu> result = this.creerIndividusByName(requete);
		Assert.assertNotNull(result);
	}

	@Test(expected = Test.None.class /* no exception expected */)
	public void testCreerIndividusByNameErrorMissingParameter() {
		RequeteHomonymesDTO requete = new RequeteHomonymesDTO();
		try {
			this.creerIndividusByName(requete);
		} catch (JrafDaoException e) {
			e.getMessage().equals(RefTableREF_ERREUR._REF_133);
		}
	}

	@Test
	public void testCreerIndividusByNameSearchWithEmail() throws JrafDaoException, MissingParameterException {
		SearchIndividualByMulticriteriaRequestDTO request = new SearchIndividualByMulticriteriaRequestDTO();
		RequestorDTO requestor = new RequestorDTO();
		requestor.setSite("QVI");
		requestor.setSignature("TEST");
		request.setRequestor(requestor);
		
		ContactDTO contact = new ContactDTO();
		contact.setEmail(EMAIL);
		request.setContact(contact);
		mockEntityManager();
		mockIndividuDAO();
		List<Individu> result = this.creerIndividusByNameSearch(request);
		Assert.assertNotNull(result);
	}

	@Test
	public void testCreerIndividusByNameSearchWithPhone() throws JrafDaoException, MissingParameterException {
		SearchIndividualByMulticriteriaRequestDTO request = new SearchIndividualByMulticriteriaRequestDTO();
		RequestorDTO requestor = new RequestorDTO();
		requestor.setSite("QVI");
		requestor.setSignature("TEST");
		request.setRequestor(requestor);
		
		ContactDTO contact = new ContactDTO();
		contact.setPhoneNumber(PHONE);
		request.setContact(contact);
		mockEntityManager();
		mockIndividuDAO();
		List<Individu> result = this.creerIndividusByNameSearch(request);
		Assert.assertNotNull(result);
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testCreerIndividusByNameSearchWithFirstNameAndLastNameAndPhone() throws JrafDaoException, MissingParameterException {
		SearchIndividualByMulticriteriaRequestDTO request = new SearchIndividualByMulticriteriaRequestDTO();
		RequestorDTO requestor = new RequestorDTO();
		requestor.setSite("QVI");
		requestor.setSignature("TEST");
		request.setRequestor(requestor);
		
		IdentityDTO identity = new IdentityDTO();
		identity.setFirstName(FIRSTNAME);
		identity.setLastName(LASTNAME);
		request.setIdentity(identity);
		ContactDTO contact = new ContactDTO();
		contact.setPhoneNumber(PHONE);
		request.setContact(contact);
		mockEntityManager();
		List<Individu> result = this.creerIndividusByNameSearch(request);
		Assert.assertNotNull(result);
	}

	@Test
	@Transactional
	@Rollback(true)
	//TODO verif utilisation du mockEntityManager qui genere un NPE (idem ci dessus)
	public void testCreerIndividusByNameSearchWithFirstNameAndLastNameAndEmail() throws JrafDaoException, MissingParameterException {
		SearchIndividualByMulticriteriaRequestDTO request = new SearchIndividualByMulticriteriaRequestDTO();
		RequestorDTO requestor = new RequestorDTO();
		requestor.setSite("QVI");
		requestor.setSignature("TEST");
		request.setRequestor(requestor);
		
		IdentityDTO identity = new IdentityDTO();
		identity.setFirstName(FIRSTNAME);
		identity.setLastName(LASTNAME);
		request.setIdentity(identity);
		ContactDTO contact = new ContactDTO();
		contact.setEmail(EMAIL);
		request.setContact(contact);
		mockEntityManager();
		List<Individu> result = this.creerIndividusByNameSearch(request);
		Assert.assertNotNull(result);
	}

	@Test
	public void testCreerIndividusByNameSearchWithFirstNameAndLastNameAndAddress() throws JrafDaoException {
		SearchIndividualByMulticriteriaRequestDTO request = new SearchIndividualByMulticriteriaRequestDTO();
		RequestorDTO requestor = new RequestorDTO();
		requestor.setSite("QVI");
		requestor.setSignature("TEST");
		request.setRequestor(requestor);
		
		IdentityDTO identity = new IdentityDTO();
		identity.setFirstName(FIRSTNAME);
		identity.setLastName(LASTNAME);
		request.setIdentity(identity);
		ContactDTO contact = new ContactDTO();
		PostalAddressBlocDTO postalAddressBloc = new PostalAddressBlocDTO();
		contact.setPostalAddressBloc(postalAddressBloc);
		request.setContact(contact);
		mockEntityManager();
		// List<Individu> result = this.creerIndividusByNameSearch(request);
		// Assert.assertNotNull(result);
		
		try {
		List<Individu> result = this.creerIndividusByNameSearch(request);
			Assert.assertNotNull(result);
		} catch (Exception ex) {
			Assert.assertEquals("Missing parameter exception: Contact or Birthday", ex.getMessage());
		}
	}

	@Test
	@Transactional
	@Rollback(true)
	//TODO verifier comportement avec le mockEntityManager
	public void testCreerIndividusByNameSearchWithFirstNameAndLastNameAndBirthDate() throws JrafDaoException {
		SearchIndividualByMulticriteriaRequestDTO request = new SearchIndividualByMulticriteriaRequestDTO();
		RequestorDTO requestor = new RequestorDTO();
		requestor.setSite("QVI");
		requestor.setSignature("TEST");
		request.setRequestor(requestor);
		
		IdentityDTO identity = new IdentityDTO();
		identity.setFirstName(FIRSTNAME);
		identity.setLastName(LASTNAME);
		identity.setBirthday(GregorianCalendar.getInstance().getTime());
		request.setIdentity(identity);
		mockEntityManager();
		try {
		List<Individu> result = this.creerIndividusByNameSearch(request);
			Assert.assertNotNull(result);
		} catch (Exception ex) {
			Assert.fail("La recherche doit pouvoir renvoyer quelque chose");
		}
	}

    @Test
    public void testCreerIndividusByNameSearchWithAllFields() throws JrafDaoException, MissingParameterException {
    	SearchIndividualByMulticriteriaRequestDTO request = new SearchIndividualByMulticriteriaRequestDTO();
    	RequestorDTO requestor = new RequestorDTO();
		requestor.setSite("QVI");
		requestor.setSignature("TEST");
		request.setRequestor(requestor);
		
    	IdentityDTO identity = new IdentityDTO();
    	identity.setFirstName(FIRSTNAME);
    	identity.setLastName(LASTNAME);
    	identity.setBirthday(GregorianCalendar.getInstance().getTime());
		request.setIdentity(identity);
		ContactDTO contact = new ContactDTO();
		contact.setEmail(EMAIL);
		contact.setPhoneNumber(PHONE);
		PostalAddressBlocDTO postalAddressBloc = new PostalAddressBlocDTO();
		contact.setPostalAddressBloc(postalAddressBloc);
		request.setContact(contact);
		mockEntityManagerForAllFields();
		List<Individu> result = this.creerIndividusByNameSearch(request);
		Assert.assertNotNull(result);
	}

	@Test(expected = Test.None.class /* no exception expected */)
	public void testCreerIndividusByNameSearchErrorMissingParameter() throws MissingParameterException {
		SearchIndividualByMulticriteriaRequestDTO request = new SearchIndividualByMulticriteriaRequestDTO();
		RequestorDTO requestor = new RequestorDTO();
		requestor.setSite("QVI");
		requestor.setSignature("TEST");
		request.setRequestor(requestor);
		
		try {
			this.creerIndividusByNameSearch(request);
		} catch (JrafDaoException e) {
			e.getMessage().equals(RefTableREF_ERREUR._REF_133);
		}
	}

	private Individu createIndividu() {
		Individu individu = new Individu();
		individu.setSgin("");
		individu.setVersion(0);
		individu.setCivilite("");
		individu.setMotDePasse("");
		individu.setNom("");
		individu.setAlias("");
		individu.setPrenom("");
		individu.setSecondPrenom("");
		individu.setAliasPrenom("");
		individu.setSexe("");
		individu.setIdentifiantPersonnel("");
		individu.setDateNaissance(GregorianCalendar.getInstance().getTime());
		individu.setStatutIndividu("");
		individu.setCodeTitre("");
		individu.setNationalite("");
		individu.setAutreNationalite("");
		individu.setNonFusionnable("");
		individu.setSiteCreation("");
		individu.setSignatureCreation("");
		individu.setDateCreation(GregorianCalendar.getInstance().getTime());
		individu.setSiteModification("");
		individu.setSignatureModification("");
		individu.setDateModification(GregorianCalendar.getInstance().getTime());
		individu.setSiteFraudeur("");
		individu.setSignatureFraudeur("");
		individu.setDateModifFraudeur(GregorianCalendar.getInstance().getTime());
		individu.setSiteMotDePasse("");
		individu.setSignatureMotDePasse("");
		individu.setDateModifMotDePasse(GregorianCalendar.getInstance().getTime());
		individu.setFraudeurCarteBancaire("");
		individu.setTierUtiliseCommePiege("");
		individu.setAliasNom1("");
		individu.setAliasNom2("");
		individu.setAliasPrenom1("");
		individu.setAliasPrenom2("");
		individu.setAliasCivilite1("");
		individu.setAliasCivilite2("");
		individu.setIndicNomPrenom("");
		individu.setIndicNom("");
		individu.setIndcons("");
		individu.setGinFusion("");
		individu.setDateFusion(null);
		individu.setProvAmex("");
		individu.setCieGest("");
		return individu;
	}

	private Query createQuery() {
		Query query = new Query() {
			@Override
			public Query setParameter(int arg0, Calendar arg1, TemporalType arg2) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Query setParameter(int arg0, Date arg1, TemporalType arg2) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Query setParameter(String arg0, Calendar arg1, TemporalType arg2) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Query setParameter(String arg0, Date arg1, TemporalType arg2) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Query setParameter(int arg0, Object arg1) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Query setParameter(String arg0, Object arg1) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Query setMaxResults(int arg0) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Query setHint(String arg0, Object arg1) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Query setFlushMode(FlushModeType arg0) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Query setFirstResult(int arg0) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Object getSingleResult() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public List getResultList() {
				List<String> list = new ArrayList<String>();
				list.add(GIN);
				return list;
			}

			@Override
			public int executeUpdate() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public int getMaxResults() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public int getFirstResult() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public Map<String, Object> getHints() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public <T> Query setParameter(Parameter<T> param, T value) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Query setParameter(Parameter<Calendar> param, Calendar value, TemporalType temporalType) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Query setParameter(Parameter<Date> param, Date value, TemporalType temporalType) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Set<Parameter<?>> getParameters() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Parameter<?> getParameter(String name) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public <T> Parameter<T> getParameter(String name, Class<T> type) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Parameter<?> getParameter(int position) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public <T> Parameter<T> getParameter(int position, Class<T> type) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public boolean isBound(Parameter<?> param) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public <T> T getParameterValue(Parameter<T> param) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Object getParameterValue(String name) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Object getParameterValue(int position) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public FlushModeType getFlushMode() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Query setLockMode(LockModeType lockMode) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public LockModeType getLockMode() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public <T> T unwrap(Class<T> cls) {
				// TODO Auto-generated method stub
				return null;
			}
		};
		return query;
	}

	private void mockEntityManager() {
		this.setEntityManager(EasyMock.createNiceMock(EntityManager.class));
		Query query = createQuery();
		String str = EasyMock.anyObject(String.class);
		EasyMock.expect(this.getEntityManager().createQuery(str)).andReturn(query);
		EasyMock.replay(this.getEntityManager());
	}

	private void mockEntityManagerForAllFields() {
		this.setEntityManager(EasyMock.createNiceMock(EntityManager.class));
		Query query = createQuery();
		String str = EasyMock.anyObject(String.class);
		EasyMock.expect(this.getEntityManager().createQuery(str)).andReturn(query);
		Query query2 = createQuery();
		String str2 = EasyMock.anyObject(String.class);
		EasyMock.expect(this.getEntityManager().createQuery(str2)).andReturn(query2);
		Query query3 = createQuery();
		String str3 = EasyMock.anyObject(String.class);
		EasyMock.expect(this.getEntityManager().createQuery(str3)).andReturn(query3);
		Query query4 = createQuery();
		String str4 = EasyMock.anyObject(String.class);
		EasyMock.expect(this.getEntityManager().createQuery(str4)).andReturn(query4);
		EasyMock.replay(this.getEntityManager());
	}

	private void mockIndividuDAO() throws JrafDaoException {
		this.setIndividuRepository(EasyMock.createNiceMock(IndividuRepository.class));
		Individu individu = createIndividu();
		EasyMock.expect(this.getIndividuRepository().findBySgin(GIN)).andReturn(individu);
		EasyMock.replay(this.getIndividuRepository());
	}

	@Test(expected = Test.None.class /* no exception expected */)
	public void creerListeIndividus_By_NomPrenomTest() throws JrafDaoException {
		String nom = "COLOMBO";
		String prenom = "SERGIO";
		String civilite = "";
		Date dateNaissance = null; // = new Date(1948, 7, 1);
		String pays = "";
		String noRue = "";
		String compltAdresse = "";
		String localite = "";
		String codePostal = "";
		String ville = "";
		String codeProvince = "";
		String typeRechNom = "S";
		String typeRechPrenom = "S";
		String typeRechCodePostal = "";
		String typeRechVille = "";
		// We just test that the SQL request doesn't crash
		individuUS.creerListeIndividus(nom, prenom, civilite, dateNaissance, pays, noRue,
				compltAdresse, localite, codePostal, ville, codeProvince, typeRechNom, typeRechPrenom,
				typeRechCodePostal, typeRechVille);
	}

	@Test
	public void creerListeIndividus_By_CiviliteNomPrenomTest() throws JrafDaoException {

		String nom = "COLOMBO";
		String prenom = "SERGIO";
		String civilite = "MR";
		Date dateNaissance = null;
		String pays = "";
		String noRue = "";
		String compltAdresse = "";
		String localite = "";
		String codePostal = "";
		String ville = "";
		String codeProvince = "";
		String typeRechNom = "S";
		String typeRechPrenom = "S";
		String typeRechCodePostal = "";
		String typeRechVille = "";

		// We just test if the SQL request doesn't crash
		List<Individu> listIndividu = individuUS.creerListeIndividus(nom, prenom, civilite, dateNaissance, pays, noRue,
				compltAdresse, localite, codePostal, ville, codeProvince, typeRechNom, typeRechPrenom,
				typeRechCodePostal, typeRechVille);

		Assert.assertNotNull(listIndividu);
	}

	@Test
	public void creerListeIndividus_By_CiviliteNomPrenomDateNaissanceTest() throws JrafDaoException, ParseException {

		String nom = "COLOMBO";
		String prenom = "SERGIO";
		String civilite = "MR";
		Date dateNaissance = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse("01/07/1948 00:00:00");
		String pays = "";
		String noRue = "";
		String compltAdresse = "";
		String localite = "";
		String codePostal = "";
		String ville = "";
		String codeProvince = "";
		String typeRechNom = "S";
		String typeRechPrenom = "S";
		String typeRechCodePostal = "";
		String typeRechVille = "";

		List<Individu> listIndividu = individuUS.creerListeIndividus(nom, prenom, civilite, dateNaissance, pays, noRue,
				compltAdresse, localite, codePostal, ville, codeProvince, typeRechNom, typeRechPrenom,
				typeRechCodePostal, typeRechVille);

		Assert.assertNotNull(listIndividu);
		Assert.assertEquals(1, listIndividu.size());

		Assert.assertNotNull(listIndividu.get(0));

		Assert.assertNotNull(listIndividu.get(0).getSgin());
		Assert.assertEquals("110000017701", listIndividu.get(0).getSgin());
	}

	@Test
	@Transactional
	public void creerListeIndividus_By_CiviliteNomPrenomDateNaissancePaysTest()
			throws JrafDaoException, ParseException {

		String nom = "COLOMBO";
		String prenom = "SERGIO";
		String civilite = "MR";
		Date dateNaissance = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse("01/07/1948 00:00:00");
		String pays = "BR";
		String noRue = "";
		String compltAdresse = "";
		String localite = "";
		String codePostal = "";
		String ville = "";
		String codeProvince = "";
		String typeRechNom = "S";
		String typeRechPrenom = "S";
		String typeRechCodePostal = "S";
		String typeRechVille = "S";

		List<Individu> listIndividu = individuUS.creerListeIndividus(nom, prenom, civilite, dateNaissance, pays, noRue,
				compltAdresse, localite, codePostal, ville, codeProvince, typeRechNom, typeRechPrenom,
				typeRechCodePostal, typeRechVille);

		Assert.assertNotNull(listIndividu);
		Assert.assertEquals(1, listIndividu.size());

		Assert.assertNotNull(listIndividu.get(0));

		Assert.assertNotNull(listIndividu.get(0).getSgin());
		Assert.assertEquals("110000017701", listIndividu.get(0).getSgin());
	}

	@Test
	@Transactional
	public void creerListeIndividus_By_CiviliteNomPrenomDateNaissancePaysVilleTest()
			throws JrafDaoException, ParseException {

		String nom = "COLOMBO";
		String prenom = "SERGIO";
		String civilite = "MR";
		Date dateNaissance = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse("01/07/1948 00:00:00");
		String pays = "BR";
		String noRue = "";
		String compltAdresse = "";
		String localite = "";
		String codePostal = "";
		String ville = "RIO DE JANEIRO";
		String codeProvince = "";
		String typeRechNom = "S";
		String typeRechPrenom = "S";
		String typeRechCodePostal = "S";
		String typeRechVille = "S";

		List<Individu> listIndividu = individuUS.creerListeIndividus(nom, prenom, civilite, dateNaissance, pays, noRue,
				compltAdresse, localite, codePostal, ville, codeProvince, typeRechNom, typeRechPrenom,
				typeRechCodePostal, typeRechVille);

		Assert.assertNotNull(listIndividu);
		Assert.assertEquals(1, listIndividu.size());

		Assert.assertNotNull(listIndividu.get(0));

		Assert.assertNotNull(listIndividu.get(0).getSgin());
		Assert.assertEquals("110000017701", listIndividu.get(0).getSgin());
	}

	@Test
	@Transactional
	public void creerListeIndividus_By_CiviliteNomPrenomDateNaissancePaysVilleComplTest()
			throws JrafDaoException, ParseException {

		String nom = "COLOMBO";
		String prenom = "SERGIO";
		String civilite = "MR";
		Date dateNaissance = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse("01/07/1948 00:00:00");
		String pays = "BR";
		String noRue = "";
		String compltAdresse = "ITANHANGA";
		String localite = "";
		String codePostal = "";
		String ville = "RIO DE JANEIRO";
		String codeProvince = "";
		String typeRechNom = "S";
		String typeRechPrenom = "S";
		String typeRechCodePostal = "S";
		String typeRechVille = "S";

		/*
		 * String pays = "FR"; String noRue = ""; String compltAdresse =
		 * "ITANHANGA"; String localite = ""; String codePostal = "21360-060";
		 * String ville = "RIO DE JANEIRO"; String codeProvince = ""; String
		 * typeRechNom = "S"; String typeRechPrenom = "S"; String
		 * typeRechCodePostal = "S"; String typeRechVille = "S";
		 */
		List<Individu> listIndividu = individuUS.creerListeIndividus(nom, prenom, civilite, dateNaissance, pays, noRue,
				compltAdresse, localite, codePostal, ville, codeProvince, typeRechNom, typeRechPrenom,
				typeRechCodePostal, typeRechVille);

		Assert.assertNotNull(listIndividu);
		Assert.assertEquals(1, listIndividu.size());

		Assert.assertNotNull(listIndividu.get(0));

		Assert.assertNotNull(listIndividu.get(0).getSgin());
		Assert.assertEquals("110000017701", listIndividu.get(0).getSgin());
	}

	@Test
	@Transactional
	public void creerListeIndividus_By_CiviliteNomPrenomDateNaissancePaysVilleComplCodePostalTest()
			throws JrafDaoException, ParseException {

		String nom = "COLOMBO";
		String prenom = "SERGIO";
		String civilite = "MR";
		Date dateNaissance = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse("01/07/1948 00:00:00");
		String pays = "BR";
		String noRue = "";
		String compltAdresse = "ITANHANGA";
		String localite = "";
		String codePostal = "22641-160";
		String ville = "RIO DE JANEIRO";
		String codeProvince = "";
		String typeRechNom = "S";
		String typeRechPrenom = "S";
		String typeRechCodePostal = "S";
		String typeRechVille = "S";

		List<Individu> listIndividu = individuUS.creerListeIndividus(nom, prenom, civilite, dateNaissance, pays, noRue,
				compltAdresse, localite, codePostal, ville, codeProvince, typeRechNom, typeRechPrenom,
				typeRechCodePostal, typeRechVille);

		Assert.assertNotNull(listIndividu);
		Assert.assertEquals(1, listIndividu.size());

		Assert.assertNotNull(listIndividu.get(0));

		Assert.assertNotNull(listIndividu.get(0).getSgin());
		Assert.assertEquals("110000017701", listIndividu.get(0).getSgin());
	}

	@Test
	@Transactional
	public void creerListeIndividus_By_CiviliteNomPrenomDateNaissancePaysVilleComplCodePostal_On_LikeTest()
			throws JrafDaoException, ParseException {

		String nom = "COLOMBO";
		String prenom = "SERGIO";
		String civilite = "MR";
		Date dateNaissance = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse("01/07/1948 00:00:00");
		String pays = "BR";
		String noRue = "";
		String compltAdresse = "ITANHANGA";
		String localite = "";
		String codePostal = "22641";
		String ville = "RIO DE JANEIRO";
		String codeProvince = "";
		String typeRechNom = "S";
		String typeRechPrenom = "S";
		String typeRechCodePostal = "L";
		String typeRechVille = "S";

		List<Individu> listIndividu = individuUS.creerListeIndividus(nom, prenom, civilite, dateNaissance, pays, noRue,
				compltAdresse, localite, codePostal, ville, codeProvince, typeRechNom, typeRechPrenom,
				typeRechCodePostal, typeRechVille);

		Assert.assertNotNull(listIndividu);
		Assert.assertEquals(1, listIndividu.size());

		Assert.assertNotNull(listIndividu.get(0));

		Assert.assertNotNull(listIndividu.get(0).getSgin());
		Assert.assertEquals("110000017701", listIndividu.get(0).getSgin());
	}

	@Test
	@Transactional
	public void creerListeIndividus_By_CiviliteNomPrenomDateNaissancePaysVilleComplCodePostal_On_LikeKOTest()
			throws JrafDaoException, ParseException {

		String nom = "COLOMBO";
		String prenom = "SERGIO";
		String civilite = "MR";
		Date dateNaissance = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse("01/07/1948 00:00:00");
		String pays = "BR";
		String noRue = "";
		String compltAdresse = "ITANHANGA";
		String localite = "";
		String codePostal = "641-160";
		String ville = "RIO DE JANEIRO";
		String codeProvince = "";
		String typeRechNom = "S";
		String typeRechPrenom = "S";
		String typeRechCodePostal = "L";
		String typeRechVille = "S";

		List<Individu> listIndividu = individuUS.creerListeIndividus(nom, prenom, civilite, dateNaissance, pays, noRue,
				compltAdresse, localite, codePostal, ville, codeProvince, typeRechNom, typeRechPrenom,
				typeRechCodePostal, typeRechVille);

		Assert.assertNotNull(listIndividu);
		Assert.assertEquals(0, listIndividu.size());
	}

	@Test
	@Transactional
	public void creerIndividusByNameSearch_By_EmailTest() throws JrafDaoException, ParseException, MissingParameterException {

		SearchIndividualByMulticriteriaRequestDTO searchIndividualByMulticriteriaRequestDTO = new SearchIndividualByMulticriteriaRequestDTO();
		
		RequestorDTO requestor = new RequestorDTO();
		requestor.setSite("QVI");
		requestor.setSignature("TEST");
		searchIndividualByMulticriteriaRequestDTO.setRequestor(requestor);

		ContactDTO contact = new ContactDTO();
		contact.setEmail("donald_finnigan@hotmail.co.uk");
		searchIndividualByMulticriteriaRequestDTO.setContact(contact);

		List<Individu> listIndividu = individuUS.creerIndividusByNameSearch(searchIndividualByMulticriteriaRequestDTO);

		Assert.assertNotNull(listIndividu);
		Assert.assertEquals(1, listIndividu.size());

		Assert.assertNotNull(listIndividu.get(0));

		Assert.assertNotNull(listIndividu.get(0).getSgin());
		Assert.assertEquals("400323328612", listIndividu.get(0).getSgin());
	}

	@Test
	@Transactional
	public void creerIndividusByNameSearch_By_PhoneTest() throws JrafDaoException, ParseException, MissingParameterException {

		SearchIndividualByMulticriteriaRequestDTO searchIndividualByMulticriteriaRequestDTO = new SearchIndividualByMulticriteriaRequestDTO();
		RequestorDTO requestor = new RequestorDTO();
		requestor.setSite("QVI");
		requestor.setSignature("TEST");
		searchIndividualByMulticriteriaRequestDTO.setRequestor(requestor);
		
		ContactDTO contact = new ContactDTO();
		contact.setPhoneNumber("+13472450015");
		searchIndividualByMulticriteriaRequestDTO.setContact(contact);
		// We just test if the SQL request doesn't crash
		List<Individu> listIndividu = individuUS.creerIndividusByNameSearch(searchIndividualByMulticriteriaRequestDTO);
		Assert.assertNotNull(listIndividu);
	}

	@Test
	@Transactional
	@Ignore    // Test du searchIndividualByMulticriteria (n'a pas lui de figurer dans le common car necessite appel au stub w001271 : REPIND-185)
	public void creerIndividusByNameSearch_By_NomPrenomEmail_On_LikeTest() throws JrafDaoException, ParseException, MissingParameterException {

		SearchIndividualByMulticriteriaRequestDTO searchIndividualByMulticriteriaRequestDTO = new SearchIndividualByMulticriteriaRequestDTO();
		IdentityDTO identity = new IdentityDTO();
		identity.setFirstName("DON");
		identity.setFirstNameSearchType("L");
		identity.setLastName("FINN");
		identity.setLastNameSearchType("L");
		searchIndividualByMulticriteriaRequestDTO.setIdentity(identity);
		ContactDTO contact = new ContactDTO();
		contact.setEmail("donald_finnigan@hotmail.co.uk");
		searchIndividualByMulticriteriaRequestDTO.setContact(contact);

		List<Individu> listIndividu = individuUS.creerIndividusByNameSearch(searchIndividualByMulticriteriaRequestDTO);

		Assert.assertNotNull(listIndividu);
		Assert.assertEquals(1, listIndividu.size());

		Assert.assertNotNull(listIndividu.get(0));

		Assert.assertNotNull(listIndividu.get(0).getSgin());
		Assert.assertEquals("400323328612", listIndividu.get(0).getSgin());
	}

	@Test
	@Transactional
	@Ignore    // Test du searchIndividualByMulticriteria (n'a pas lui de figurer dans le common car necessite appel au stub w001271 : REPIND-185)
	public void creerIndividusByNameSearch_By_NomPrenomEmail_On_LikeKOTest() throws JrafDaoException, ParseException, MissingParameterException {

		SearchIndividualByMulticriteriaRequestDTO searchIndividualByMulticriteriaRequestDTO = new SearchIndividualByMulticriteriaRequestDTO();
		IdentityDTO identity = new IdentityDTO();
		identity.setFirstName("ON");
		identity.setFirstNameSearchType("L");
		identity.setLastName("INN");
		identity.setLastNameSearchType("L");
		searchIndividualByMulticriteriaRequestDTO.setIdentity(identity);
		ContactDTO contact = new ContactDTO();
		contact.setEmail("donald_finnigan@hotmail.co.uk");
		searchIndividualByMulticriteriaRequestDTO.setContact(contact);

		List<Individu> listIndividu = individuUS.creerIndividusByNameSearch(searchIndividualByMulticriteriaRequestDTO);

		// LIKE is on right so nobody match, this is normal
		Assert.assertNotNull(listIndividu);
		Assert.assertEquals(0, listIndividu.size());
	}

	@Test
	@Transactional
	public void creerListeIndividus_Without_NomTypeSearch() throws JrafDaoException, ParseException {

		String phoneNumber = "";
		String typeSearchPhone = "";
		String lastName = "INN";
		String firstName = "ON";
		String civility = "MR";
		Date birthDate = null; // = new Date(1948, 7, 1);
		String typeSearchLName = null;
		String typeSearchFName = null;

		List<Individu> listIndividu = individuUS.creerListeIndividus(lastName, firstName, civility, birthDate,
				phoneNumber, typeSearchLName, typeSearchFName, typeSearchPhone, "");
		Assert.assertEquals(0, listIndividu.size());

	}

	@Test
	@Transactional
	public void creerListeIndividus_NomTypeSearchAndPrenomTypeSearch_case1() throws JrafDaoException, ParseException {

		String phoneNumber = "";
		String typeSearchPhone = "";
		String lastName = "INN";
		String firstName = "ON";
		String civility = "MR";
		Date birthDate = null; // = new Date(1948, 7, 1);
		String typeSearchLName = "P";
		String typeSearchFName = "P";

		List<Individu> listIndividu = individuUS.creerListeIndividus(lastName, firstName, civility, birthDate,
				phoneNumber, typeSearchLName, typeSearchFName, typeSearchPhone, "");
		Assert.assertEquals(0, listIndividu.size());

	}

	@Test(expected = JrafDaoException.class)
	@Transactional
	public void creerListeIndividus_NomTypeSearchAndPrenomTypeSearch_case2() throws JrafDaoException, ParseException {

		String phoneNumber = "";
		String typeSearchPhone = "";
		String lastName = "INN";
		String firstName = "ON";
		String civility = "MR";
		Date birthDate = null; // = new Date(1948, 7, 1);
		String typeSearchLName = "L";
		String typeSearchFName = "P";

		List<Individu> listIndividu = individuUS.creerListeIndividus(lastName, firstName, civility, birthDate,
				phoneNumber, typeSearchLName, typeSearchFName, typeSearchPhone, "");
		Assert.assertEquals(0, listIndividu.size());

	}

	@Test(expected = JrafDaoException.class)
	@Transactional
	public void creerListeIndividus_NomTypeSearchAndPrenomTypeSearch_case4() throws JrafDaoException, ParseException {

		String phoneNumber = "";
		String typeSearchPhone = "";
		String lastName = "INN";
		String firstName = "ON";
		String civility = "MR";
		Date birthDate = null; // = new Date(1948, 7, 1);
		String typeSearchLName = "L";
		String typeSearchFName = "P";

		List<Individu> listIndividu = individuUS.creerListeIndividus(lastName, firstName, civility, birthDate,
				phoneNumber, typeSearchLName, typeSearchFName, typeSearchPhone, "");
		Assert.assertEquals(0, listIndividu.size());

	}

	@Test(expected = JrafDaoException.class)
	@Transactional
	public void creerListeIndividus_NomTypeSearchAndPrenomTypeSearch_case3() throws JrafDaoException, ParseException {

		String phoneNumber = "";
		String typeSearchPhone = "";
		String lastName = "INN";
		String firstName = "ON";
		String civility = "MR";
		Date birthDate = null; // = new Date(1948, 7, 1);
		String typeSearchLName = null;
		String typeSearchFName = "P";

		List<Individu> listIndividu = individuUS.creerListeIndividus(lastName, firstName, civility, birthDate,
				phoneNumber, typeSearchLName, typeSearchFName, typeSearchPhone, "");
		Assert.assertEquals(0, listIndividu.size());

	}

	@Test
	@Transactional
	public void creerListeIndividus_NomTypeSearchAndPrenomTypeSearch_case5() throws JrafDaoException, ParseException {

		String phoneNumber = "";
		String typeSearchPhone = "";
		String lastName = "INN";
		String firstName = "ON";
		String civility = "MR";
		Date birthDate = null; // = new Date(1948, 7, 1);
		String typeSearchLName = null;
		String typeSearchFName = "L";

		List<Individu> listIndividu = individuUS.creerListeIndividus(lastName, firstName, civility, birthDate,
				phoneNumber, typeSearchLName, typeSearchFName, typeSearchPhone, "");
		Assert.assertEquals(0, listIndividu.size());

	}

	@Test
	@Transactional
	public void creerListeIndividus_NomTypeSearchAndPrenomTypeSearch_case6() throws JrafDaoException, ParseException {

		String phoneNumber = "";
		String typeSearchPhone = "";
		String lastName = "INN";
		String firstName = "ON";
		String civility = "MR";
		Date birthDate = null; // = new Date(1948, 7, 1);
		String typeSearchLName = null;
		String typeSearchFName = "S";

		List<Individu> listIndividu = individuUS.creerListeIndividus(lastName, firstName, civility, birthDate,
				phoneNumber, typeSearchLName, typeSearchFName, typeSearchPhone, "");
		Assert.assertEquals(0, listIndividu.size());

	}

	@Test
	@Transactional
	public void creerListeIndividus_PhoneTypeSearch() throws JrafDaoException, ParseException {

		String phoneNumber = "";
		String typeSearchPhone = "L";
		String lastName = "INN";
		String firstName = "ON";
		String civility = "MR";
		Date birthDate = null; // = new Date(1948, 7, 1);
		String typeSearchLName = null;
		String typeSearchFName = null;
		String email = null;

		List<Individu> listIndividu = individuUS.creerListeIndividus(lastName, firstName, civility, birthDate,
				phoneNumber, typeSearchLName, typeSearchFName, typeSearchPhone, email);
		Assert.assertEquals(7, listIndividu.size());

	}

	@Test(expected = JrafDaoException.class)
	@Transactional
	public void creerIndividusByName_EmptyRequest() throws JrafDaoException, ParseException {
		RequeteHomonymesDTO requete = new RequeteHomonymesDTO();
		List<Individu> listIndividu = individuUS.creerIndividusByName(requete);
		Assert.assertNull(listIndividu);

	}

	@Test
	@Transactional
	public void creerIndividusByName_EmailSearch() throws JrafDaoException, ParseException {
		RequeteHomonymesDTO requete = new RequeteHomonymesDTO();
		requete.setEmail("thierry-martin@9online.fr");
		List<Individu> listIndividu = individuUS.creerIndividusByName(requete);
		Assert.assertNotNull(listIndividu);

	}
	
	
	@Test
	@Transactional
	public void creerIndividusByName_NormalisationSoft() throws JrafDaoException, ParseException {
		RequeteHomonymesDTO requete = new RequeteHomonymesDTO();
		requete.setNom("INN");
		requete.setPrenom("ON");
		
		requete.setNoRue("LADEIRA ASCURRAS 115 A");
		requete.setComplementAdresse("");
		requete.setLocalite("RIO DE JANEIRO");
		requete.setCodePostal("20031-201");
		requete.setVille("20031 RJ");
		requete.setCodePays("BR");
		
		List<Individu> listIndividu = individuUS.creerIndividusByName(requete);
		Assert.assertNotNull(listIndividu);

	}
	
	@Transactional
	@Rollback(true)
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)
	public void creerIndividusByName_Normalisation() throws JrafDaoException, ParseException {
		RequeteHomonymesDTO requete = new RequeteHomonymesDTO();
		requete.setNom("BENTANJI");
		requete.setPrenom("MOHAMED AMINE");
		
		requete.setNoRue("23 RUE MORERE");
		requete.setComplementAdresse("CHEZ FAYCAL FILALI ANSARI");
		requete.setLocalite("");
		requete.setCodePostal("75014");
		requete.setVille("PARIS");
		requete.setCodePays("FR");
		
		List<Individu> listIndividu = individuUS.creerIndividusByName(requete);
		Assert.assertNotNull(listIndividu);

	}
	
	

}
