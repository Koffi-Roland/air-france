package com.afklm.repind.msv.consent.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.afklm.repind.msv.consent.entity.ConsentData;

@Repository
public interface ConsentDataRepository extends JpaRepository<ConsentData, Long> {

	List<ConsentData> findByConsentDataId(Long id);
	
	List<ConsentData> findByConsentConsentId(Long id);
}