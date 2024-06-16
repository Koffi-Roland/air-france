package com.afklm.cati.common.test.service;

import com.afklm.cati.common.dto.RefPcsFactorDTO;
import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.model.ModelRefPcsFactor;
import com.afklm.cati.common.service.RefPcsFactorService;
import com.afklm.cati.common.test.spring.TestConfiguration;
import com.afklm.cati.common.exception.jraf.JrafDaoException;
import com.afklm.cati.common.entity.RefPcsFactor;
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
 * RefPcsFactorServiceTest.java
 */
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfiguration.class)
@Transactional(value = "transactionManagerSic")
public class RefPcsFactorServiceTest extends AbstractServiceTest {

	@Autowired
	private RefPcsFactorService refPcsFactorService;

	/**
	 * Test to retrieve all refPcsFactorData
	 *
	 * @throws JrafDaoException jraf dao exception
	 * @throws CatiCommonsException common exception
	 */
	@Test
	public void testGetAllRefPcsFactorData() throws CatiCommonsException, JrafDaoException {
		List<ModelRefPcsFactor> refs = refPcsFactorService.getAllPcsFactor();
		assertThat(refs.size()).isEqualTo(2);
	}

	/**
	 * Test to retrieve one refPcsFactorData
	 * 
	 * @throws CatiCommonsException, JrafDaoException
	 */
	@Test
	public void testGetRefPcsFactorData() throws CatiCommonsException, JrafDaoException {
		String refPcsFatorCode = "C";
		Optional<RefPcsFactor> refPcsFator = refPcsFactorService.getRefPcsFactor(refPcsFatorCode);
		assertThat(refPcsFator).isNotNull();
		assertThat(refPcsFator.get().getLibelle()).isEqualTo("CONTRACT");
	}

	/**
	 * Test to post one refPcsFactorData
	 * 
	 * @throws CatiCommonsException, JrafDaoException
	 */
	@Test
	public void testAddRefPcsFactorData() throws CatiCommonsException, JrafDaoException {

		String code = "T";
		RefPcsFactorDTO refPcsFactorDTO = new RefPcsFactorDTO();
		refPcsFactorDTO.setCode(code);
		refPcsFactorDTO.setLibelle("Test");
		refPcsFactorDTO.setFactor(0);
		refPcsFactorDTO.setMaxPoints(0);

		refPcsFactorService.addRefPcsFactor(refPcsFactorDTO);
		Optional<RefPcsFactor> refPcsFactorResponse = refPcsFactorService.getRefPcsFactor(code);
		assertThat(refPcsFactorResponse).isNotNull();
	}

	/**
	 * Test to update one refPcsFactorData
	 * 
	 * @throws CatiCommonsException, JrafDaoException
	 */
	@Test
	public void testUpdateRefPcsFactorData() throws CatiCommonsException, JrafDaoException {

		String code = "C";
		RefPcsFactor refPcsFactor = new RefPcsFactor();
		refPcsFactor.setCode(code);
		refPcsFactor.setLibelle("Test");

		refPcsFactorService.updateRefPcsFactor(refPcsFactor);
		Optional<RefPcsFactor> refPcsFactorResponse = refPcsFactorService.getRefPcsFactor(code);

		assertThat(refPcsFactorResponse).isNotNull();
		assertThat(refPcsFactorResponse.get().getLibelle()).isEqualTo("Test");
	}

	/**
	 * Test to remove one refPcsFactorData
	 * 
	 * @throws CatiCommonsException, JrafDaoException
	 */
	@Test
	public void testRemoveRefPcsFactorData() throws CatiCommonsException, JrafDaoException {
		String code = "NC";
		refPcsFactorService.removeRefPcsFactor(code);
		Optional<RefPcsFactor> refPcsFactorResponse = refPcsFactorService.getRefPcsFactor(code);
		assertThat(refPcsFactorResponse).isEmpty();
	}
}
