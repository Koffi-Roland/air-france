package com.airfrance.repind.dao.zone;

import com.airfrance.repind.entity.zone.IntervalleCodesPostaux;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IntervalleCodesPostauxRepository extends JpaRepository<IntervalleCodesPostaux, Long> {
	
	public List<IntervalleCodesPostaux> findByCodePaysAndCodePostalDebutAndCodePostalFin(String codePays, String codePostalDebut, String codePostalFin);
}
