package com.airfrance.repind.service.individu.internal.it;

import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.repind.config.WebRepindTestConfig;
import com.airfrance.repind.dao.AbstractDAOTest;
import com.airfrance.repind.dao.StaticTestEntitiesGenerator;
import com.airfrance.repind.dao.individu.IndividuRepository;
import com.airfrance.repind.entity.individu.Individu;
import com.airfrance.repind.service.internal.unitservice.individu.IndividuUS;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

/**
 *
 * select * from INDIVIDUS_ALL individu0_ where (individu0_.SNOM like 'SERR%')
 * and (individu0_.SPRENOM like 'DIDIER') and (individu0_.SSTATUT_INDIVIDU in
 * ('V' , 'P'));
 * 
 */
@ActiveProfiles("test")
@SpringBootTest(classes = WebRepindTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class IndividuUSTest extends AbstractDAOTest {

	@Autowired
	private IndividuUS individuUS;

	@Autowired
	private IndividuRepository individuRepository;

	@Test
	public void testListeIndividuStrict() {

		// Generate a test individual
		Individu individu = StaticTestEntitiesGenerator.createTestIndividu();

		initTransaction();
		try {
			// Find other individuals having the same information
			List<Individu> readIndividuList = individuUS.creerListeIndividusSearch(individu.getNom().toUpperCase(),
					individu.getPrenom().toUpperCase(), null, null, null, "S", "S", null, null);
			int foundIndividualsNumber = 0;
			if (readIndividuList != null) {
				foundIndividualsNumber = readIndividuList.size();
			}

			// Save the generated individual in the DB
			individuRepository.saveAndFlush(individu);

			// Get the list of the individuals having the same first and last names
			List<Individu> listeIndividu = individuUS.creerListeIndividusSearch(individu.getNom().toUpperCase(),
					individu.getPrenom().toUpperCase(), null, null, null, "S", "S", null, null);
			assertEquals((foundIndividualsNumber + 1), listeIndividu.size());

		} catch (Exception e) {
			e.printStackTrace();
			fail(StaticTestEntitiesGenerator.NO_EXCEPTION_TO_BE_THROWN);
		}
	}


	@Test
	public void testListeIndividuLike() throws JrafDaoException {

		// Generate a test individual
		Individu individu = StaticTestEntitiesGenerator.createTestIndividu();

		initTransaction();
		try {
			// Check if there are some other guys having the same information before test
			List<Individu> listeIndividu = individuUS.creerListeIndividusSearch(individu.getNom().toUpperCase(),
					individu.getPrenom().toUpperCase(), null, null, null, "L", "L", null, null);
			int foundIndividualsNumber = listeIndividu == null ? 0 : listeIndividu.size();

			// Save the generated individual in the DB
			individuRepository.saveAndFlush(individu);

			// Get the list of the individuals having first and last names like
			// our individual
			List<Individu> listeIndividu1 = individuUS.creerListeIndividusSearch(individu.getNom().toUpperCase(),
					individu.getPrenom().toUpperCase(), null, null, null, "L", "L", null, null);
			assertTrue(listeIndividu1.size() > foundIndividualsNumber);

			// Search again with some letters missing
			String testLastName = individu.getNom().substring(0, 4).toUpperCase();
			String testFirstName = individu.getPrenom().substring(0, 4).toUpperCase();
			List<Individu> listeIndividu2 = individuUS.creerListeIndividusSearch(testLastName, testFirstName, null, null, null, "L",
					"L", null, null);
			assertTrue(listeIndividu2.size() >= listeIndividu1.size());

		} catch (Exception e) {
			e.printStackTrace();
			fail(StaticTestEntitiesGenerator.NO_EXCEPTION_TO_BE_THROWN);
		}
	}

}
