package com.afklm.repind.msv.inferred.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.afklm.repind.msv.inferred.entity.RefInfrdStatus;

@Repository
public interface RefInfrdStatusRepository extends JpaRepository<RefInfrdStatus, String> {

	@Query("Select ref.code from RefInfrdStatus ref where ref.code not in ('C', 'I', 'X')")
	List<String> findStatusForUpdate();
	
}