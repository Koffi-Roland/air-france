package com.afklm.cati.common.repository;

import com.afklm.cati.common.entity.RefPcsFactor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefPcsFactorRepository extends JpaRepository<RefPcsFactor, String> {
}
