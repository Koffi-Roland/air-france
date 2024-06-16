package com.afklm.repind.msv.handicap.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.afklm.repind.msv.handicap.entity.Individual;
import com.afklm.repind.msv.handicap.entity.Handicap;

@Repository
public interface IndividualRepository extends JpaRepository<Handicap, String> {

	@Query("select ind from Individual ind where ind.gin = :gin and ind.status NOT IN ('X', 'F', 'H')")
	Optional<Individual> findIndividualNotDeleted(@Param("gin") String gin);
	
}