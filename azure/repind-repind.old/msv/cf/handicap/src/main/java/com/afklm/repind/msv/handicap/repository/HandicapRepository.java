package com.afklm.repind.msv.handicap.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.afklm.repind.msv.handicap.entity.Handicap;

@Repository
public interface HandicapRepository extends JpaRepository<Handicap, Long> {

	List<Handicap> findByGin(String gin);
	
	List<Handicap> findByGinAndType(String gin, String type);
	
	List<Handicap> findByGinAndTypeAndCode(String gin, String type, String code);
	
	@Transactional
	@Modifying
	Long deleteByHandicapId(Long id);
}