package com.airfrance.repind.service.reference.internal;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dao.reference.PaysRepository;
import com.airfrance.repind.dao.reference.RefVilleIataRepository;
import com.airfrance.repind.dao.reference.RefVillesAnnexesRepository;
import com.airfrance.repind.entity.reference.Pays;
import com.airfrance.repind.entity.reference.RefVilleIata;
import com.airfrance.repind.entity.reference.RefVillesAnnexes;
import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.domain.Example;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class RefCityDSTest {

	private RefCityDS ds;
	private RefVilleIataRepository refVilleIataRepository;
	private RefVillesAnnexesRepository refVillesAnnexesRepository;
	private PaysRepository paysRepository;
	
	private final static String CITY_1 = "SOUTHFIELD";
	private final static String COUNTRY_1 = "US";
	private final static String IATACODE_1 = "CVG";
	private final static String COUNTRY_CAPITOL_1 = "WAS";
	
	@Before
	public void setUp() throws Exception {

		ds = new RefCityDS();
				
		refVilleIataRepository = createMock(RefVilleIataRepository.class);
		refVillesAnnexesRepository = createMock(RefVillesAnnexesRepository.class);
		paysRepository = createMock(PaysRepository.class);
		
		ds.setRefVilleIataRepository(refVilleIataRepository);
		ds.setPaysRepository(paysRepository);
		ds.setRefVillesAnnexesRepository(refVillesAnnexesRepository);
		
	}
	
	@After
	public void tearDown() throws Exception {
		EasyMock.reset(refVilleIataRepository);
		EasyMock.reset(refVillesAnnexesRepository);
		EasyMock.reset(paysRepository);
	}

	
	/**
	 * Scenario: 
	 * look for a cityLabel not contained in VilleIata table but contained exactly in VilleAnnexes so I expect:
	 *  <li>1 call to PaysDao to get the country bo
	 *  <li>1 call to RefVilleIataDao strict
	 *  <li>1 call to RefVillesAnnexesDao strict
	 *  <li> the call returns the cityCode contained in RefVillesAnnexes table
	 * 
	 * In order to work, you need to define equals and hashCode methods of the JPA entities passed to the daos.
	 * @see com.airfrance.repind.entity.reference.RefVilleIata
	 * 
	 * @throws JrafDomainException
	 */
	@Test
	public void testGetCityCode() throws JrafDomainException {
		
		//Record the expected responses
		RefVilleIata boIata = new RefVilleIata();
		RefVillesAnnexes boAnn = new RefVillesAnnexes();
		Pays pays = new Pays();
		pays.setCodePays(COUNTRY_1);
		boIata.setLibelle(CITY_1);
		boIata.setPays(pays);
		boAnn.setLibelle(CITY_1);
		boAnn.setPays(pays);
		
		expect((paysRepository.findById(COUNTRY_1))).andReturn(Optional.of(pays));
		List<RefVilleIata> listVille = null;
		expect(refVilleIataRepository.findAll(Example.of(boIata))).andReturn(listVille);
		
		List<RefVillesAnnexes> retAnnexes = new ArrayList<RefVillesAnnexes>();
		retAnnexes.add(new RefVillesAnnexes("1234",CITY_1,CITY_1,IATACODE_1,null,null,null,null));
		expect(refVillesAnnexesRepository.findAll(Example.of(boAnn))).andReturn(retAnnexes);
		
		
		EasyMock.replay(refVilleIataRepository);
		EasyMock.replay(refVillesAnnexesRepository);
		EasyMock.replay(paysRepository);
		
		assertEquals(IATACODE_1, ds.getCityCode(CITY_1, COUNTRY_1));
		
		EasyMock.verify(paysRepository);
		EasyMock.verify(refVilleIataRepository);
		EasyMock.verify(refVillesAnnexesRepository);
		
	}
	
	
	
	/**
	 * Scenario
	 * 
	 * look for the city capitol given a country code
	 * expected 1 call to PaysDao
	 * 
	 * @throws JrafDomainException
	 */
	@Test
	public void testGetCountryCapitol() throws JrafDomainException {
		
		Pays pays = new Pays();
		Pays retPays = new Pays();
		pays.setCodePays(COUNTRY_1);
		retPays.setCodePays(COUNTRY_1);
		retPays.setCodeCapitale(COUNTRY_CAPITOL_1);
				
		expect((paysRepository.findById(COUNTRY_1))).andReturn(Optional.of(retPays));
		
		EasyMock.replay(paysRepository); //Register easymock scenario
		
		
		assertEquals(COUNTRY_CAPITOL_1, ds.getCountryCapitol(COUNTRY_1));
		EasyMock.verify(paysRepository);
	}

	/**
	 * Test method for
	 * {@link RefCityDS#getUniqueCityCode(String, String)}
	 * 
	 * @throws JrafDomainException
	 */
	@Test
	public void testGetUniqueCityCode() throws JrafDomainException {
		List<RefVilleIata> cityCodes = new ArrayList<>();
		RefVilleIata city1 = new RefVilleIata();
		city1.setScodeVille(CITY_1);
		cityCodes.add(city1);
		expect(refVilleIataRepository.findByCityCountry(CITY_1, COUNTRY_1)).andStubReturn(cityCodes);
		EasyMock.replay(refVilleIataRepository);

		String response = ds.getUniqueCityCode(CITY_1, COUNTRY_1);
		assertNotNull(response);
	}
}
