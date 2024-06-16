package com.airfrance.repind.service.reference.internal;

import com.airfrance.ref.exception.ContractNotFoundException;
import com.airfrance.ref.exception.contract.RoleContractUnauthorizedOperationException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.config.WebTestConfig;
import com.airfrance.repind.dto.reference.RefOwnerDTO;
import com.airfrance.repind.dto.reference.RefProductDTO;
import com.airfrance.repind.dto.reference.RefProductOwnerDTO;
import com.airfrance.repind.dto.reference.RefProductOwnerIdDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class RefProductOwnerDSTest {

	@Autowired
	private RefProductOwnerDS rpoDS;
	
	@Autowired
	private RefProductDS rpDS;
	
	@Autowired
	private RefOwnerDS roDS;
	
	@Test
	@Rollback(false)
	public void getUsingKeysOnlyTest() throws JrafDomainException {
		// INIT DATA FOR TEST
		RefProductDTO rpDTO = new RefProductDTO();
		rpDTO.setProductId(1);

		RefOwnerDTO roDTO = new RefOwnerDTO();
		roDTO.setOwnerId(1);
		
		RefProductOwnerIdDTO idDto = new RefProductOwnerIdDTO();
		idDto.setRefOwner(roDTO);
		idDto.setRefProduct(rpDTO);
		// Data to insert

		
		RefProductOwnerDTO tmp = rpoDS.get(idDto);

		Assert.assertEquals(new Integer(1), tmp.getId().getRefOwner().getOwnerId());
		Assert.assertEquals(new Integer(1), tmp.getId().getRefProduct().getProductId());
	}
	
	@Test
	@Rollback(false)
	public void areAssociatedTest() throws JrafDomainException {
		
		List<RefProductOwnerDTO> val = rpoDS.getAssociations("FP", null, "OSIRIS", "w00608615");

		Assert.assertNotNull(val);
	}

	
	@Test
	@Rollback(false)
	public void areAssociatedNoAssociationTest() throws JrafDomainException {
		
			List<RefProductOwnerDTO> val = rpoDS.getAssociations("FP", null, "CBS", "w06536507");

			Assert.assertTrue(val.isEmpty());
	}

	@Test
	@Rollback(false)
	public void areAssociatedNoProductTest() {
		
		try {
			rpoDS.getAssociations("TS", "ST", "CBS", "w06536507");
			Assert.fail();
		} catch (JrafDomainException e) {
			Assert.assertTrue(e instanceof ContractNotFoundException);
		}
		

		
	}
	
	@Test
	@Rollback(false)
	public void areAssociatedNoOwnerTest() {
		try {
			rpoDS.getAssociations("FP", null, "TEST", "w00123121");
			Assert.fail();
		} catch (JrafDomainException e) {
			RoleContractUnauthorizedOperationException ruoe = (RoleContractUnauthorizedOperationException) e;
			Assert.assertEquals("300", ruoe.getErrorCode());
		}
	}
	
	@Test
	@Rollback(false)
	public void getNonExistingAssociationTest() throws JrafDomainException {
		// INIT DATA FOR TEST
		RefProductDTO rpDTO = new RefProductDTO();
		rpDTO.setProductId(1);

		RefOwnerDTO roDTO = new RefOwnerDTO();
		roDTO.setOwnerId(3);
		
		RefProductOwnerIdDTO idDto = new RefProductOwnerIdDTO();
		idDto.setRefOwner(roDTO);
		idDto.setRefProduct(rpDTO);

		// Data to insert
		RefProductOwnerDTO tmp = rpoDS.get(idDto);

		Assert.assertNull(tmp);
	}
	
	@Test
	@Rollback(false)
	public void findByProductIdTest() throws JrafDomainException {
		List<RefProductOwnerDTO> rpos = rpoDS.findAllOwnersByProductId(1);
		
		Assert.assertNotNull(rpos);
		Assert.assertEquals(3, rpos.size());
		Assert.assertNotNull(rpos.get(0));
		Assert.assertEquals("OSIRIS", rpos.get(0).getId().getRefOwner().getAppCode());
		Assert.assertEquals("w00608615", rpos.get(0).getId().getRefOwner().getConsumerId());
		Assert.assertNotNull(rpos.get(1));
		Assert.assertEquals("HACHIKO", rpos.get(1).getId().getRefOwner().getAppCode());
		Assert.assertEquals("w00799265", rpos.get(1).getId().getRefOwner().getConsumerId());
		Assert.assertNotNull(rpos.get(2));
		Assert.assertEquals("SIC", rpos.get(2).getId().getRefOwner().getAppCode());
		Assert.assertEquals("w85875644", rpos.get(2).getId().getRefOwner().getConsumerId());
	}
	
	@Test
	@Rollback(false)
	public void findByProductIdNonExistingTest() throws JrafDomainException {
		List<RefProductOwnerDTO> rpos = rpoDS.findAllOwnersByProductId(-1);
		
		Assert.assertNotNull(rpos);
		Assert.assertTrue(rpos.isEmpty());
	}
	
	@Test
	@Rollback(false)
	public void findByProductIdNotExistingOwnerTest() throws JrafDomainException {
		List<RefProductOwnerDTO> rpos = rpoDS.findAllOwnersByProductId(150);
		
		Assert.assertNotNull(rpos);
		Assert.assertTrue(rpos.isEmpty());
	}
}
