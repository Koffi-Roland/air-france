package com.afklm.cati.common.test.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import com.afklm.cati.common.dto.RefPreferenceTypeDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.service.RefPreferenceTypeService;
import com.afklm.cati.common.test.spring.TestConfiguration;
import com.afklm.cati.common.exception.jraf.JrafDaoException;
import com.afklm.cati.common.entity.RefPreferenceType;

/**
 * RefPreferenceTypeServiceTest.java
 * @author m430152
 */
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfiguration.class)
@Transactional(value = "transactionManagerSic")
public class RefPreferenceTypeServiceTest extends AbstractServiceTest {

	@Autowired
	private RefPreferenceTypeService refPreferenceTypeService;

	/**
	 * Test to retrieve all refPreferenceType
	 *
	 * @throws JrafDaoException jraf dao exception
	 * @throws CatiCommonsException common exception
	 */
	@Test
	public void testGetAllRefPreferenceType() throws CatiCommonsException, JrafDaoException {
		List<RefPreferenceType> refs = refPreferenceTypeService.getAllRefPreferenceType();
		assertThat(refs.size()).isEqualTo(4);
	}

	/**
	 * Test to retrieve one refPreferenceType
	 * 
	 * @throws CatiCommonsException, JrafDaoException
	 */
	@Test
	public void testGetRefPreferenceType() throws CatiCommonsException, JrafDaoException {
		String refPreferenceTypeCode = "GPC";
		Optional<RefPreferenceType> refPreferenceType = refPreferenceTypeService.getRefPreferenceType(refPreferenceTypeCode);
		assertThat(refPreferenceType).isNotNull();
		assertThat(refPreferenceType.get().getLibelleFR()).isEqualTo("Les Preferences cadeaux");
	}

	/**
	 * Test to post one refPreferenceType
	 * 
	 * @throws CatiCommonsException, JrafDaoException
	 */
	@Test
	public void testAddRefPreferenceType() throws CatiCommonsException, JrafDaoException {

		String code = "T";
		RefPreferenceTypeDTO pTypeDTO = new RefPreferenceTypeDTO();
		pTypeDTO.setCode(code);
		pTypeDTO.setLibelleFR("Test");
		pTypeDTO.setLibelleEN("Test");

		refPreferenceTypeService.addRefPreferenceType(pTypeDTO);
		Optional<RefPreferenceType> refPreferenceTypeResponse = refPreferenceTypeService.getRefPreferenceType(code);
		assertThat(refPreferenceTypeResponse).isNotNull();
	}

	/**
	 * Test to update one refPreferenceType
	 * 
	 * @throws CatiCommonsException, JrafDaoException
	 */
	@Test
	public void testUpdateRefPreferenceType() throws CatiCommonsException, JrafDaoException {

		String code = "GPC";
		RefPreferenceType pType = new RefPreferenceType();
		pType.setCode(code);
		pType.setLibelleFR("Test");
		pType.setLibelleEN("Test");

		refPreferenceTypeService.updateRefPreferenceType(pType);
		Optional<RefPreferenceType> refPreferenceTypeResponse = refPreferenceTypeService.getRefPreferenceType(code);

		assertThat(refPreferenceTypeResponse).isNotNull();
		assertThat(refPreferenceTypeResponse.get().getLibelleFR()).isEqualTo("Test");
	}

	/**
	 * Test to remove one refPreferenceType
	 * 
	 * @throws CatiCommonsException, JrafDaoException
	 */
	@Test
	public void testRemoveRefPreferenceType() throws CatiCommonsException, JrafDaoException {
		String code = "ECC";
		refPreferenceTypeService.removeRefPreferenceType(code);
		Optional<RefPreferenceType> refPreferenceTypeResponse = refPreferenceTypeService.getRefPreferenceType(code);
		assertThat(refPreferenceTypeResponse).isEmpty();
	}
}
