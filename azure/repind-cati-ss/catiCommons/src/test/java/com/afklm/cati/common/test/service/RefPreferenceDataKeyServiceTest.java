package com.afklm.cati.common.test.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import com.afklm.cati.common.dto.RefPreferenceDataKeyDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.service.RefPreferenceDataKeyService;
import com.afklm.cati.common.test.spring.TestConfiguration;
import com.afklm.cati.common.exception.jraf.JrafDaoException;
import com.afklm.cati.common.entity.RefPreferenceDataKey;

/**
 * RefPreferenceDataKeyServiceTest.java
 * @author m430152
 */
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfiguration.class)
@Transactional(value = "transactionManagerSic")
public class RefPreferenceDataKeyServiceTest extends AbstractServiceTest {

	@Autowired
	private RefPreferenceDataKeyService refPreferenceDataKeyService;

	/**
	 * Test to retrieve all refPreferenceDataKey
	 *
	 * @throws JrafDaoException jraf dao exception
	 * @throws CatiCommonsException common exception
	 */
	@Test
	public void testGetAllRefPreferenceDataKey() throws CatiCommonsException, JrafDaoException {
		List<RefPreferenceDataKey> refs = refPreferenceDataKeyService.getAllRefPreferenceDataKey();
		assertThat(refs.size()).isEqualTo(4);
	}

	/**
	 * Test to retrieve one refPreferenceDataKey
	 * 
	 * @throws CatiCommonsException, JrafDaoException
	 */
	@Test
	public void testGetRefPreferenceDataKey() throws CatiCommonsException, JrafDaoException {
		String refPreferenceDataKeyCode = "KLMHOUSEWISH1";
		Optional<RefPreferenceDataKey> refPreferenceDataKey = refPreferenceDataKeyService.getRefPreferenceDataKey(refPreferenceDataKeyCode);
		assertThat(refPreferenceDataKey.get()).isNotNull();
		assertThat(refPreferenceDataKey.get().getLibelleFr()).isEqualTo("Maisons miniature de KLM");
		assertThat(refPreferenceDataKey.get().getNormalizedKey()).isEqualTo("KLMHouseWish1");
	}

	/**
	 * Test to post one refPreferenceDataKey
	 * 
	 * @throws CatiCommonsException, JrafDaoException
	 */
	@Test
	public void testAddRefPreferenceDataKey() throws CatiCommonsException, JrafDaoException {

		String code = "T";
		RefPreferenceDataKeyDTO pDataKeyDTO = new RefPreferenceDataKeyDTO();
		pDataKeyDTO.setCode(code);
		pDataKeyDTO.setLibelleFr("Test");
		pDataKeyDTO.setLibelleEn("Test");
		pDataKeyDTO.setNormalizedKey("t");

		refPreferenceDataKeyService.addRefPreferenceDataKey(pDataKeyDTO);
		Optional<RefPreferenceDataKey> refPreferenceDataKeyResponse = refPreferenceDataKeyService.getRefPreferenceDataKey(code);

		assertThat(refPreferenceDataKeyResponse).isNotNull();
	}

	/**
	 * Test to update one refPreferenceDataKey
	 * 
	 * @throws CatiCommonsException, JrafDaoException
	 */
	@Test
	public void testUpdateRefPreferenceDataKey() throws CatiCommonsException, JrafDaoException {

		String code = "KLMHOUSEWISH1";
		
		RefPreferenceDataKey pDataKey = new RefPreferenceDataKey();
		pDataKey.setCode(code);
		pDataKey.setLibelleFr("Test");
		pDataKey.setLibelleEn("Test");
		pDataKey.setNormalizedKey("t");

		refPreferenceDataKeyService.updateRefPreferenceDataKey(pDataKey);
		Optional<RefPreferenceDataKey> refPreferenceDataKeyResponse = refPreferenceDataKeyService.getRefPreferenceDataKey(code);

		assertThat(refPreferenceDataKeyResponse).isNotNull();
		assertThat(refPreferenceDataKeyResponse.get().getLibelleFr()).isEqualTo("Test");
		assertThat(refPreferenceDataKeyResponse.get().getNormalizedKey()).isEqualTo("t");
	}

	/**
	 * Test to remove one refPreferenceDataKey
	 * 
	 * @throws CatiCommonsException, JrafDaoException
	 */
	@Test
	public void testRemoveRefPreferenceDataKey() throws CatiCommonsException, JrafDaoException {
		String code = "KLMHOUSEWISH1";
		refPreferenceDataKeyService.removeRefPreferenceDataKey(code);
		Optional<RefPreferenceDataKey> refPreferenceDataKeyResponse = refPreferenceDataKeyService.getRefPreferenceDataKey(code);
		assertThat(refPreferenceDataKeyResponse).isEmpty();
	}
}
