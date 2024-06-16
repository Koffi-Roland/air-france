package com.airfrance.repind.dao.zone;

import com.airfrance.repind.entity.zone.LienIntCpZd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 * Title : ILienIntCpZdDAO.java
 * </p>
 * BO: LienIntCpZd
 * <p>
 * Copyright : Copyright (c) 2009
 * </p>
 * <p>
 * Company : AIRFRANCE
 * </p>
 */

@Repository
public interface LienIntCpZdRepository extends JpaRepository<LienIntCpZd, Long>, LienIntCpZdRepositoryCustom {

	@Query("SELECT lien FROM LienIntCpZd lien WHERE lien.codeVille = :cityCode AND lien.dateDebutLien <= CURRENT_DATE AND (lien.dateFinLien IS NULL OR lien.dateFinLien >= CURRENT_DATE)")
	public Optional<LienIntCpZd> getCurrentLinkByCity(@Param("cityCode") String cityCode);

	@Query("SELECT lien FROM LienIntCpZd lien WHERE lien.codeVille = :cityCode AND lien.dateDebutLien > CURRENT_DATE")
	public List<LienIntCpZd> getFutureLinksByCity(@Param("cityCode") String cityCode);

}
