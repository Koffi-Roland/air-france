package com.airfrance.repind.dao.reference;

import com.airfrance.repind.entity.reference.RefProductComPrefGroup;
import com.airfrance.repind.entity.reference.RefProductComPrefGroupId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefProductComPrefGroupRepository extends JpaRepository<RefProductComPrefGroup, RefProductComPrefGroupId> {
	
	@Query("select refProductComPrefGroup from RefProductComPrefGroup refProductComPrefGroup "
			+ "where refProductComPrefGroup.refProductComPrefGroupId.refProduct.productId = :productId")
	public List<RefProductComPrefGroup> getAllRefComPrefGroupByProductId(@Param("productId") Integer productId);
	
}
