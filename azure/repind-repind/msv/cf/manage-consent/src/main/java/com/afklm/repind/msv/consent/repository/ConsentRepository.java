package com.afklm.repind.msv.consent.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.afklm.repind.msv.consent.entity.Consent;

@Repository
public interface ConsentRepository extends JpaRepository<Consent, Long> {

	Consent findByConsentDataConsentDataId(Long id);
	
	List<Consent> findByGin(String gin);
	
}