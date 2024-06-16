package com.afklm.repind.msv.inferred.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.afklm.repind.msv.inferred.entity.Inferred;

@Repository
public interface InferredRepository extends JpaRepository<Inferred, Long> {

	List<Inferred> findByGin(String gin);
	
}