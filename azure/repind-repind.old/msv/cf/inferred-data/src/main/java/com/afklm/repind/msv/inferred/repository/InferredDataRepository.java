package com.afklm.repind.msv.inferred.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.afklm.repind.msv.inferred.entity.InferredData;

@Repository
public interface InferredDataRepository extends JpaRepository<InferredData, Long> {

	List<InferredData> findByInferredInferredId(Long id);
	
}