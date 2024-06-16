package com.airfrance.repind.dao.profil;

import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.repind.config.WebSicTestConfig;
import com.airfrance.repind.entity.profil.Profils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebSicTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class ProfilsDAOTest {
	
    /** logger */
    private static final Log log = LogFactory.getLog(ProfilsDAOTest.class);
	
    @Autowired
	private ProfilsRepository profilsRepository;

	@Test(expected = Test.None.class /* no exception expected */)
	public void testfindCodePays() throws JrafDaoException {
		String gin = "110000014901";
		Optional<Profils> profil = profilsRepository.findById(gin);
		if (profil.isPresent()) {
			log.info("results.mailing_autorise = " + profil.get().getSmailing_autorise());
			log.info("results.code_langue = " + profil.get().getScode_langue());
		} else {
			log.error("No profil found for gin : " + gin);
		}
	}

	public ProfilsRepository getProfilsRepository() {
		return profilsRepository;
	}

	public void setProfilsRepository(ProfilsRepository profilsRepository) {
		this.profilsRepository = profilsRepository;
	}
	
}
