package com.airfrance.repind.dao.reference;

import com.airfrance.repind.entity.reference.RefProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefProductRepository extends JpaRepository<RefProduct, Integer> {
	
	@Query("select ref.productId from RefProduct ref where ref.contractType = 'C'")
	List<Integer> getAllCustomerRefProduct();
	
}
