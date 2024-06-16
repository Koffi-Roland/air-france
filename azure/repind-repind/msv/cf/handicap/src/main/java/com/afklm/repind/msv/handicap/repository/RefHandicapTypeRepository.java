package com.afklm.repind.msv.handicap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.afklm.repind.msv.handicap.entity.RefHandicapType;

@Repository
public interface RefHandicapTypeRepository extends JpaRepository<RefHandicapType, Integer> {
	
}