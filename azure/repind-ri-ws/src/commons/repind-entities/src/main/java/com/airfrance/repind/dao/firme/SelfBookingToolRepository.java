package com.airfrance.repind.dao.firme;

import com.airfrance.repind.entity.firme.SelfBookingTool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SelfBookingToolRepository extends JpaRepository<SelfBookingTool, String> {
		
	/**
	 * Find a SelfBookingTool from Gin Personne Morale
	 * @param pGinPm
	 * @return
	 */
	@Query("select s from SelfBookingTool s where s.personneMorale.gin = :ginPm")
	public SelfBookingTool findByPMGin(@Param("ginPm") String ginPm);
}
