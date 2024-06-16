package com.afklm.repind.msv.consent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.afklm.repind.msv.consent.entity.RefConsentDefault;
import com.afklm.repind.msv.consent.entity.RefConsentTypeDataTypeId;

@Repository
public interface RefConsentDefaultRepository extends JpaRepository<RefConsentDefault, RefConsentTypeDataTypeId> {
	
}