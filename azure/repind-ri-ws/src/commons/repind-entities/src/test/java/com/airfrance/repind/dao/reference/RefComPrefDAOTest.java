package com.airfrance.repind.dao.reference;

import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.repind.config.WebSicTestConfig;
import com.airfrance.repind.entity.reference.RefComPref;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;




@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebSicTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class RefComPrefDAOTest {
	
	 /** logger */
    private static final Log log = LogFactory.getLog(RefComPrefDAOTest.class);
    
    @Autowired
    private RefComPrefRepository refComPrefRepository;
    
    @Test
    public void testFindComPerfByMarket() throws JrafDaoException {
    	List<RefComPref> comPref = refComPrefRepository.findComPerfByMarket("FR", "F");
    	Assert.assertEquals(3, comPref.size());
    }
    
    @Test
    public void testFindComPrefByDomainComTypeComGroupType() throws JrafDaoException {
    	List<RefComPref> comPref = refComPrefRepository.findComPrefByDomainComTypeComGroupType("F", "FB_ESS", "N");
    	Assert.assertEquals(248, comPref.size());
    }
    
    @Test
    public void testFindComPerfByMarketComTypeComGType() throws JrafDaoException {
    	List<RefComPref> comPref = refComPrefRepository.findComPerfByMarketComTypeComGType("FR", "F", "FB_ESS", "N");
    	Assert.assertEquals(1, comPref.size());
    }
        	
	@Test
	public void testFindMarketByCodePaysFBESS() throws JrafDaoException {
		List<RefComPref> comPref = refComPrefRepository.findComPerfByMarketComType("FR", "F","FB_ESS");
		for (RefComPref com : comPref) {
			log.info("results.Market = " +com.getComType().getCodeType());
		}
		
		Assert.assertEquals(1, comPref.size());
	}
	
	@Test
	public void testFindMarketByCodePaysExcludeFBESS() throws JrafDaoException {
		List<RefComPref> comPref = refComPrefRepository.findComPerfByAllMarkets("FR", "F","FB_ESS","*");
		log.info(comPref.size());
		for (RefComPref com : comPref) {
			log.info("results.Market = " +com.getComType().getCodeType());
			log.info("results.Market = " +com.getMarket());
		}
		
		Assert.assertEquals(6, comPref.size());
	}
	
	@Test
	public void testFindComPerfByMarketComTypeLanguage() throws JrafDaoException {
		List<RefComPref> comPref = refComPrefRepository.findComPrefByMarketComTypeLanguage("FR", "S","KL_IFLY","M","DE");
		for (RefComPref com : comPref) {
			log.info("results.Market = " +com.getComType().getCodeType());
			log.info("results.Market = " +com.getMarket());
		}
		
		Assert.assertEquals(1, comPref.size());
	}

	public RefComPrefRepository getRefComPrefRepository() {
		return refComPrefRepository;
	}

	public void setRefComPrefRepository(RefComPrefRepository refComPrefRepository) {
		this.refComPrefRepository = refComPrefRepository;
	}
	

}
