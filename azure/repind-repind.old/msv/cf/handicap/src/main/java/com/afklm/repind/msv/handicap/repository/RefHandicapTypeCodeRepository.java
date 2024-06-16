package com.afklm.repind.msv.handicap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.afklm.repind.msv.handicap.entity.RefHandicapTypeCode;

@Repository
public interface RefHandicapTypeCodeRepository extends JpaRepository<RefHandicapTypeCode, Integer> {

	public Long countByTypeAndCode(String type, String code);
	
}