package com.airfrance.repind.dao.reference;

import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.repind.config.WebSicTestConfig;
import com.airfrance.repind.entity.reference.RefOwner;
import com.airfrance.repind.entity.reference.RefProduct;
import com.airfrance.repind.entity.reference.RefProductOwner;
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

import java.util.ArrayList;
import java.util.List;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebSicTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class RefProductOwnerDAOTest {
	
	 /** logger */
    private static final Log log = LogFactory.getLog(RefProductOwnerDAOTest.class);
    
    @Autowired
    private RefProductOwnerRepository refProductOwnerRepository;
	
	@Test
	public void getAssociations() throws JrafDaoException {
		List<RefOwner> owners = new ArrayList<RefOwner>();
		List<RefProduct> products = new ArrayList<RefProduct>();
		RefOwner refOwner1 = new RefOwner();
		RefOwner refOwner2 = new RefOwner();
		RefProduct refProduct1 = new RefProduct();
		RefProduct refProduct2 = new RefProduct();
		
		refProduct1.setProductId(1);
		refProduct2.setProductId(2);
		
		refOwner1.setOwnerId(6);
		refOwner2.setOwnerId(7);
		
		products.add(refProduct1);
		products.add(refProduct2);
		owners.add(refOwner1);
		owners.add(refOwner2);

		List<Integer> productsId = null;
		List<Integer> ownersId = null;
		
		if (owners != null && !owners.isEmpty()) {
			// Get all associations between products and owners
			ownersId = new ArrayList<Integer>();
			for (RefOwner owner : owners) {
				ownersId.add(owner.getOwnerId());
			}
		}
		
		if (products != null && !products.isEmpty()) {
			productsId = new ArrayList<Integer>();
			for (RefProduct product : products) {
				productsId.add(product.getProductId());
			}		
		}

		
		List<RefProductOwner> refProductOwner = refProductOwnerRepository.getAssociationsByOwnersAndProducts(ownersId, productsId);
		//TODO add some in memory database specialized for tests instead of using the Dev database ( -> the data stay the same if we define them in 
		// a in memory DB )
		Assert.assertEquals(1, refProductOwner.size());
	}
}
