package com.airfrance.repind.dao.firme;

import com.airfrance.repind.entity.firme.Segmentation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SegmentationRepository extends JpaRepository<Segmentation, Integer> {
	
	/**
	 * Find list of Segmentation from Gin Personne Morale with Pagination
	 * @param pGinPm
	 * @return
	 */
	@Query("select s from Segmentation s where s.personneMorale.gin = :ginPm")
	public List<Segmentation> findByPMGin(@Param("ginPm") String ginPm, Pageable pageable);
	
	/**
	 * Find list of Segmentation from Gin Personne Morale
	 * @param pGinPm
	 * @return
	 */
	@Query("select s from Segmentation s where s.personneMorale.gin = :ginPm")
	public List<Segmentation> findByPMGin(@Param("ginPm") String ginPm);
}
