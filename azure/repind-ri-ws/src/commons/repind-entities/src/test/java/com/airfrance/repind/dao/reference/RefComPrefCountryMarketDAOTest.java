package com.airfrance.repind.dao.reference;

import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.repind.config.WebSicTestConfig;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
public class RefComPrefCountryMarketDAOTest {
	
	 /** logger */
    private static final Log log = LogFactory.getLog(RefComPrefCountryMarketDAOTest.class);
    
    @Autowired    
    private RefComPrefCountryMarketRepository refComPrefCountryMarketRepository;

	@Test(expected = Test.None.class /* no exception expected */)
	public void testFindMarketByCodePays() throws JrafDaoException {
		List<String> listeMarket = refComPrefCountryMarketRepository.findMarketByCodePays("FR");
		 log.info("results.Market = " + listeMarket.size());
	}

	public RefComPrefCountryMarketRepository getRefComPrefCountryMarketRepository() {
		return refComPrefCountryMarketRepository;
	}

	public void setRefComPrefCountryMarketRepository(RefComPrefCountryMarketRepository refComPrefCountryMarketRepository) {
		this.refComPrefCountryMarketRepository = refComPrefCountryMarketRepository;
	}

}
