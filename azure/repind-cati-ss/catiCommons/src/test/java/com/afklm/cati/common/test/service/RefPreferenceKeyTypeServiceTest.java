package com.afklm.cati.common.test.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.afklm.cati.common.criteria.RefPreferenceKeyTypeCriteria;
import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.service.RefPreferenceKeyTypeService;
import com.afklm.cati.common.test.spring.TestConfiguration;
import com.afklm.cati.common.exception.jraf.NotFoundException;
import com.afklm.cati.common.exception.jraf.JrafDaoException;
import com.afklm.cati.common.entity.RefPreferenceKeyType;

/**
 * RefPreferenceKeyTypeServiceTest.java
 * @author m430152
 */
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfiguration.class)
@Transactional(value = "transactionManagerSic")
public class RefPreferenceKeyTypeServiceTest extends AbstractServiceTest {

	@Autowired
	private RefPreferenceKeyTypeService refPreferenceKeyTypeService;

	/**
	 * Test to retrieve all refPreferenceKeyType
	 *
	 * @throws JrafDaoException jraf dao exception
	 * @throws CatiCommonsException common exception
	 */
	@Test
	public void testGetAllRefPreferenceKeyType() throws CatiCommonsException, JrafDaoException {
		List<RefPreferenceKeyType> refs = refPreferenceKeyTypeService.getAllRefPreferenceKeyType();
		assertThat(refs.size()).isEqualTo(4);
	}

	/**
	 * Test to retrieve one refPreferenceKeyType
	 * 
	 * @throws CatiCommonsException, JrafDaoException
	 */
	@Test
	public void testGetRefPreferenceKeyType() throws CatiCommonsException, JrafDaoException {
		Integer refPreferenceKeyTypeId = 400;
		Optional<RefPreferenceKeyType> refPreferenceKeyType = refPreferenceKeyTypeService.getRefPreferenceKeyType(refPreferenceKeyTypeId);
		assertThat(refPreferenceKeyType).isNotNull();
		assertThat(refPreferenceKeyType.get().getKey()).isEqualTo("KLMHOUSEWISH1");
		assertThat(refPreferenceKeyType.get().getType()).isEqualTo("GPC");
		assertThat(refPreferenceKeyType.get().getDataType()).isEqualTo("String");
	}

	/**
	 * Test to post one refPreferenceKeyType
	 * 
	 * @throws CatiCommonsException, JrafDaoException
	 */
	@Test
	public void testAddRefPreferenceKeyType() throws CatiCommonsException, JrafDaoException {

		Integer refId;

		RefPreferenceKeyTypeCriteria criteria = new RefPreferenceKeyTypeCriteria("KLMHOUSEWISH1", 99, 1,"String","O",Collections.singletonList("GPC"));
		
		assertEquals(4, refPreferenceKeyTypeService.getAllRefPreferenceKeyType().size());
		
		refPreferenceKeyTypeService.addRefPreferenceKeyType(criteria);
		
		List<RefPreferenceKeyType> result = refPreferenceKeyTypeService.getAllRefPreferenceKeyType();
		
		assertEquals(5, result.size());
		
		boolean sameDataAsCriteriaDetected = result.stream().filter(r -> 
			r.getKey().equals(criteria.getKey())
			&& r.getMaxLength().equals(criteria.getMaxLength())
			&& r.getMinLength().equals(criteria.getMinLength())
			&& r.getDataType().equals(criteria.getDataType())
			&& r.getCondition().equals(criteria.getCondition())
			&& r.getType().equals(criteria.getUniquePreferenceType())).collect(Collectors.toList()).size() == 1;
		
		boolean noDuplicatedRefId = result.stream().map(RefPreferenceKeyType::getRefId).allMatch(new HashSet<>()::add);
		
		
		assertTrue(sameDataAsCriteriaDetected && noDuplicatedRefId);
		
	
	}

	/**
	 * Test to update one refPreferenceKeyType
	 * 
	 * @throws CatiCommonsException, JrafDaoException
	 * @throws NotFoundException 
	 */
	@Test
	public void testUpdateRefPreferenceKeyType() throws CatiCommonsException, JrafDaoException, NotFoundException {

		Integer refId = 400;
		
		RefPreferenceKeyTypeCriteria criteria = new RefPreferenceKeyTypeCriteria(refId, "KLMHOUSEWISH2", 99, 1,"Test","M",Collections.singletonList("GPC"));

		refPreferenceKeyTypeService.updateRefPreferenceKeyType(criteria);
		Optional<RefPreferenceKeyType> refPreferenceKeyTypeResponse = refPreferenceKeyTypeService.getRefPreferenceKeyType(refId);

		assertThat(refPreferenceKeyTypeResponse).isNotNull();
		assertThat(refPreferenceKeyTypeResponse.get().getKey()).isEqualTo("KLMHOUSEWISH2");
		assertThat(refPreferenceKeyTypeResponse.get().getDataType()).isEqualTo("Test");
		assertThat(refPreferenceKeyTypeResponse.get().getCondition()).isEqualTo("M");
	}

	/**
	 * Test to remove one refPreferenceKeyType
	 * 
	 * @throws CatiCommonsException, JrafDaoException
	 */
	@Test
	public void testRemoveRefPreferenceKeyType() throws CatiCommonsException, JrafDaoException {
		Integer refId = 400;
		
		refPreferenceKeyTypeService.removeRefPreferenceKeyType(refId);
		
		Optional<RefPreferenceKeyType> refPreferenceKeyTypeReponse = refPreferenceKeyTypeService.getRefPreferenceKeyType(refId);
		assertThat(refPreferenceKeyTypeReponse).isEmpty();
	}
}
