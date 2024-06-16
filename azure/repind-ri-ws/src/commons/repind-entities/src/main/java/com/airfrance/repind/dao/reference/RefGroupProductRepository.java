package com.airfrance.repind.dao.reference;

import com.airfrance.repind.entity.reference.RefProductComPrefGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * <p>Title : RefComPrefRepository.java</p>
 * <p>Copyright : Copyright (c) 2013</p>
 * <p>Company : AIRFRANCE-KLM</p>
 */
public interface RefGroupProductRepository extends JpaRepository<RefProductComPrefGroup, Integer> {
	

	List<RefProductComPrefGroup> findAll();
	
	@Query("select ref from RefProductComPrefGroup ref where ref.refProductComPrefGroupId.refProduct.productId = :refProduct and ref.refProductComPrefGroupId.refComPrefGroupInfo.id = :refComPrefGroupInfoId")
	RefProductComPrefGroup getRefGroupProductById(@Param("refProduct")Integer refProduct, @Param("refComPrefGroupInfoId")Integer refComPrefGroupInfo);
	
	@Query("select ref from RefProductComPrefGroup ref where ref.refProductComPrefGroupId.refProduct.productId = :refProduct")
	List<RefProductComPrefGroup> getRefGroupProductByProductId(@Param("refProduct")Integer refProduct);
	
	@Query("select count(*) from RefProductComPrefGroup ref where ref.refProductComPrefGroupId.refComPrefGroupInfo.id = :refComPrefGroupInfoId")
	Long countRefProductComPrefGroupByGroupInfoId(@Param("refComPrefGroupInfoId")Integer refComPrefGroupInfo);
}
