package com.afklm.repind.msv.consent.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.afklm.repind.msv.consent.entity.RefConsentTypeDataType;
import com.afklm.repind.msv.consent.entity.RefConsentTypeDataTypeId;

@Repository
public interface RefConsentTypeDataTypeRepository extends JpaRepository<RefConsentTypeDataType, RefConsentTypeDataTypeId> {

	@Query("Select ref.refConsentTypeDataTypeId.consentDataType from RefConsentTypeDataType ref where ref.refConsentTypeDataTypeId.consentType = :consentType")
	List<String> findByType(@Param("consentType") String consentType);
	
}