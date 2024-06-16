package com.afklm.cati.common.repository;

import com.afklm.cati.common.entity.RefPcsScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RefPcsScoreRepository extends JpaRepository<RefPcsScore, String> {
    List<RefPcsScore> findByCodeFactor(String codeFactor);
}
