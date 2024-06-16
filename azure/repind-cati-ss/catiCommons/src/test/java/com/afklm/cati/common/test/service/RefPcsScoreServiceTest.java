package com.afklm.cati.common.test.service;

import com.afklm.cati.common.dto.RefPcsScoreDTO;
import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.model.ModelRefPcsScore;
import com.afklm.cati.common.service.RefPcsScoreService;
import com.afklm.cati.common.test.spring.TestConfiguration;
import com.afklm.cati.common.exception.jraf.JrafDaoException;
import com.afklm.cati.common.entity.RefPcsScore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * RefPcsScoreServiceTest.java
 */
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfiguration.class)
@Transactional(value = "transactionManagerSic")
public class RefPcsScoreServiceTest extends AbstractServiceTest {

	@Autowired
	private RefPcsScoreService refPcsScoreService;

	/**
	 * Test to retrieve all refPcsScoreData
	 *
	 * @throws JrafDaoException jraf dao exception
	 * @throws CatiCommonsException common exception
	 */
	@Test
	public void testGetAllRefPcsScoreData() throws CatiCommonsException, JrafDaoException {
		String codeFactor = "C";
		List<ModelRefPcsScore> refs = refPcsScoreService.getPcsScoreByFactorCode(codeFactor);
		assertThat(refs.size()).isEqualTo(2);
	}

	/**
	 * Test to retrieve one refPcsScoreData
	 * 
	 * @throws CatiCommonsException, JrafDaoException
	 */
	@Test
	public void testGetRefPcsScoreData() throws CatiCommonsException, JrafDaoException {
		String refPcsScoreCode = "FP";
		Optional<RefPcsScore> refPcsScore = refPcsScoreService.getRefPcsScore(refPcsScoreCode);
		assertThat(refPcsScore).isNotNull();
		assertThat(refPcsScore.get().getLibelle()).isEqualTo("FLYING BLUE");
	}

	/**
	 * Test to post one refPcsScoreData
	 * 
	 * @throws CatiCommonsException, JrafDaoException
	 */
	@Test
	public void testAddRefPcsScoreData() throws CatiCommonsException, JrafDaoException {

		String code = "T";
		RefPcsScoreDTO refPcsScoreDTO = new RefPcsScoreDTO();
		refPcsScoreDTO.setCode(code);
		refPcsScoreDTO.setCodeFactor("C");
		refPcsScoreDTO.setLibelle("Test");
		refPcsScoreDTO.setScore(0);

		refPcsScoreService.addRefPcsScore(refPcsScoreDTO);
		Optional<RefPcsScore> refPcsScoreResponse = refPcsScoreService.getRefPcsScore(code);
		assertThat(refPcsScoreResponse).isNotNull();
	}

	/**
	 * Test to update one refPcsScoreData
	 * 
	 * @throws CatiCommonsException, JrafDaoException
	 */
	@Test
	public void testUpdateRefPcsScoreData() throws CatiCommonsException, JrafDaoException {

		String code = "FN";
		RefPcsScore refPcsScore = new RefPcsScore();
		refPcsScore.setCode(code);
		refPcsScore.setCodeFactor("NC");
		refPcsScore.setLibelle("FIRST NAME");
		refPcsScore.setScore(50);

		refPcsScoreService.updateRefPcsScore(refPcsScore);
		Optional<RefPcsScore> refPcsScoreResponse = refPcsScoreService.getRefPcsScore(code);

		assertThat(refPcsScoreResponse).isNotNull();
		assertThat(refPcsScoreResponse.get().getScore()).isEqualTo(50);
	}

	/**
	 * Test to remove one refPcsScoreData
	 * 
	 * @throws CatiCommonsException, JrafDaoException
	 */
	@Test
	public void testRemoveRefPcsScoreData() throws CatiCommonsException, JrafDaoException {
		String code = "LN";
		refPcsScoreService.removeRefPcsScore(code);
		Optional<RefPcsScore> refPcsScoreResponse = refPcsScoreService.getRefPcsScore(code);
		assertThat(refPcsScoreResponse).isEmpty();
	}
}
