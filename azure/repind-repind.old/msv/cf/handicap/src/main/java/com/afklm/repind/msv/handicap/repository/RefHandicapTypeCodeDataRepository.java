package com.afklm.repind.msv.handicap.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.afklm.repind.msv.handicap.entity.RefHandicapTypeCodeData;
import com.afklm.repind.msv.handicap.entity.RefHandicapTypeCodeDataID;

@Repository
public interface RefHandicapTypeCodeDataRepository extends JpaRepository<RefHandicapTypeCodeData, RefHandicapTypeCodeDataID> {

	@Query("select rhtc from RefHandicapTypeCodeData rhtc where rhtc.refHandicapTypeCodeDataID.refHandicapTypeCode.code = :code"
			+ " and rhtc.refHandicapTypeCodeDataID.refHandicapTypeCode.type = :type")
	public List<RefHandicapTypeCodeData> findByTypeAndCode(@Param("type") String type, @Param("code") String code);
	
	@Query("select rhtc from RefHandicapTypeCodeData rhtc where rhtc.refHandicapTypeCodeDataID.refHandicapTypeCode.code = :code"
			+ " and rhtc.refHandicapTypeCodeDataID.refHandicapTypeCode.type = :type and rhtc.refHandicapTypeCodeDataID.key = :key")
	public RefHandicapTypeCodeData findByTypeAndCodeAndKey(@Param("type") String type, @Param("code") String code, @Param("key") String key);
	
}