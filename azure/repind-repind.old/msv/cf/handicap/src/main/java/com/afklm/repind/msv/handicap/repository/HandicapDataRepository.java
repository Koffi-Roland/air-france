package com.afklm.repind.msv.handicap.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.afklm.repind.msv.handicap.entity.HandicapData;

@Repository
public interface HandicapDataRepository extends JpaRepository<HandicapData, Long> {

	List<HandicapData> findByHandicapHandicapId(Long id);
		
	@Transactional
	@Modifying
	Long deleteByHandicapHandicapIdAndKey(Long handicapId, String key);
}