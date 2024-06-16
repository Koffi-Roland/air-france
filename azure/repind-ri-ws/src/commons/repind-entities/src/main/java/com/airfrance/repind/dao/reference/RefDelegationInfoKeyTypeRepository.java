package com.airfrance.repind.dao.reference;

import com.airfrance.repind.entity.reference.RefDelegationInfoKeyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefDelegationInfoKeyTypeRepository extends JpaRepository<RefDelegationInfoKeyType, Integer> {
	
	@Query("SELECT ref from RefDelegationInfoKeyType ref WHERE ref.type = :type and ref.key = :key")
	public List<RefDelegationInfoKeyType> refKeyAndTypeExist(@Param("type") String type, @Param("key") String key);
}
