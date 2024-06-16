package com.airfrance.repind.dao.zone;

import com.airfrance.repind.entity.zone.PmZone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/*PROTECTED REGION END*/

/**
 * <p>Title : IPmZoneDAO.java</p>
 * BO: PmZone
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Repository
public interface PmZoneRepository extends JpaRepository<PmZone, Long>, PmZoneRepositoryCustom {

	/**
	 * Retrieves the active zone links (sales or commercial) for specified zone gin
	 * and legal entity gin
	 * 
	 * @param iginZone the zone gin
	 * @param sgin     personne morale gin
	 * @return the active zone links (sales or commercial) for specified zone gin
	 *         and legal entity gin
	 */
	@Query("SELECT pz FROM PmZone pz WHERE pz.zoneDecoup.gin =:iginZone AND pz.personneMorale.gin =:sgin AND (pz.dateFermeture is null OR pz.dateFermeture >= sysdate)")
	List<PmZone> findAllActiveByIginZonePm(@Param("iginZone") Long iginZone, @Param("sgin") String sgin);
	
	/**
	 * Retrieves the active zone links (sales or commercial) for specified zone gin
	 * 
	 * @param iginZone the zone gin
	 * @return the active zone links (sales or commercial) for specified zone gin
	 *         and legal entity gin
	 */
	@Query("SELECT COUNT(1) FROM PmZone pz WHERE pz.zoneDecoup.gin =:iginZone AND (pz.dateFermeture IS NULL OR pz.dateFermeture >= sysdate)")
	Integer findAllActiveByIginZone(@Param("iginZone") Long iginZone);

	@Query("SELECT pz FROM PmZone pz WHERE pz.personneMorale.gin =:sgin AND (pz.dateFermeture is null OR pz.dateFermeture >= sysdate) AND pz.zoneDecoup.statut = 'A'")
	List<PmZone> findAllActiveByGinPm(@Param("sgin") String sgin);

	@Query(value = "SELECT pz.* "
			+ "FROM PM_ZONE pz "
			+ "INNER JOIN ZONE_DECOUP zd ON pz.IGIN_ZONE = zd.IGIN "
			+ "INNER JOIN ZONE_VENTE zv ON pz.IGIN_ZONE = zv.IGIN "
			+ "WHERE pz.SGIN =:sgin AND (pz.DDATE_FERMETURE is null OR pz.DDATE_FERMETURE >= sysdate) AND zd.SSTATUT = 'A' AND pz.SLIEN_PRIVILEGIE = 'O'", nativeQuery = true)
	List<PmZone> findAllActiveZvLinksByPmGin(@Param("sgin") String sgin);

	@Query(value = "SELECT pz.* "
			+ "FROM PM_ZONE pz "
			+ "INNER JOIN ZONE_DECOUP zd ON pz.IGIN_ZONE = zd.IGIN "
			+ "INNER JOIN ZONE_COMM zc ON pz.IGIN_ZONE = zc.IGIN "
			+ "WHERE pz.SGIN =:sgin AND (pz.DDATE_FERMETURE is null OR pz.DDATE_FERMETURE >= sysdate) AND zd.SSTATUT = 'A' AND pz.SLIEN_PRIVILEGIE = 'O'", nativeQuery = true)
	List<PmZone> findAllActiveZcLinksByPmGin(@Param("sgin") String sgin);

	/**
	 * Retrieves the active zone links (sales or commercial) for specified zone gin
	 * and legal entity gin
	 *
	 * @param iginZone the zone gin
	 * @param sgin     personne morale gin
	 * @return the active zone links (sales or commercial) for specified zone gin
	 *         and legal entity gin
	 */
	@Query("SELECT pz FROM PmZone pz WHERE pz.zoneDecoup.gin =:iginZone AND pz.personneMorale.gin =:sgin")
	List<PmZone> findAllByIginZonePm(@Param("iginZone") Long iginZone, @Param("sgin") String sgin);
}
