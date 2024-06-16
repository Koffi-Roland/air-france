package com.afklm.repind.msv.inferred.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.afklm.repind.msv.inferred.entity.RefInfrdKeyType;
import com.afklm.repind.msv.inferred.entity.RefInfrdKeyTypeId;

@Repository
public interface RefInfrdKeyTypeRepository extends JpaRepository<RefInfrdKeyType, RefInfrdKeyTypeId> {

	@Query("Select ref.refInfrdKeyTypeId.key from RefInfrdKeyType ref where ref.refInfrdKeyTypeId.type = :type")
	List<String> findByType(@Param("type") String type);
	
}