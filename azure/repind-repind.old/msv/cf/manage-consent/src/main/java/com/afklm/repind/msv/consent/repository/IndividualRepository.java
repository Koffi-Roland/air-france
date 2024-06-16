package com.afklm.repind.msv.consent.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.afklm.repind.msv.consent.entity.Consent;
import com.afklm.repind.msv.consent.entity.Individual;

@Repository
public interface IndividualRepository extends JpaRepository<Individual, String> {

	@Query("select ind from Individual ind where ind.gin = :gin and ind.status NOT IN ('X', 'F', 'H')")
	Optional<Individual> findIndividualNotDeleted(@Param("gin") String gin);
	
}