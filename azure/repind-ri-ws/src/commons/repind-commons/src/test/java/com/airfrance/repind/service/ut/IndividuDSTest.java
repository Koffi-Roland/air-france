package com.airfrance.repind.service.ut;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.config.WebTestConfig;
import com.airfrance.repind.dto.individu.IndividuDTO;
import com.airfrance.repind.service.individu.internal.IndividuDS;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class IndividuDSTest {

	@Autowired
	private IndividuDS individuDS;


	/*
	 * REPIND-1490: crisis event
	 */
	@Test
	public void testIsTypeH(){
		IndividuDTO individuDTO = new IndividuDTO();
		individuDTO.setType("H");
		Assert.assertTrue(individuDS.isStatutNotReturned(individuDTO));
	}
	
	/*
	 * REPIND-952: forgetMe
	 */
	@Test
	public void testIsStatutF(){
		IndividuDTO individuDTO = new IndividuDTO();
		individuDTO.setStatutIndividu("F");
		Assert.assertTrue(individuDS.isStatutNotReturned(individuDTO));
	}
	
	/*
	 * REPIND-952: forgetMe
	 */
	@Test
	public void testIsNotStatutF(){
		IndividuDTO individuDTO = new IndividuDTO();
		individuDTO.setStatutIndividu("V");
		Assert.assertFalse(individuDS.isStatutNotReturned(individuDTO));
	}
	
	/*
	 * REPIND-984: kidSolo
	 */
	@Test
	public void testIsStatutNotProvideWithF(){
		IndividuDTO individuDTO = new IndividuDTO();
		individuDTO.setStatutIndividu("F");
		Assert.assertTrue(individuDS.isStatutNotReturned(individuDTO));
	}
	
	/*
	 * REPIND-984: kidSolo
	 */
	@Test
	public void testIsStatutNotProvideWithK(){
		IndividuDTO individuDTO = new IndividuDTO();
		individuDTO.setType("K");
		Assert.assertTrue(individuDS.isIndividualNotProvide(individuDTO));
	}
	
	@Test
	public void testIsExistingWithNull(){
		try{
			individuDS.isExisting(null);
		}catch(JrafDomainException e){
			Assert.assertTrue(true);
		}
	}
	
	@Test
	public void testIsExistingWithEmpty(){
		try{
			individuDS.isExisting("");
		}catch(JrafDomainException e){
			Assert.assertTrue(true);
		}
	}
}
