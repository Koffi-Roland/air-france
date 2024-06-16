package com.airfrance.repind.dao.zone;

/*PROTECTED REGION ID(_BgpHULbOEeCrCZp8iGNNVwDAO DAO I i) ENABLED START*/

// add not generated imports here

import com.airfrance.repind.entity.zone.ZoneComm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/*PROTECTED REGION END*/

/**
 * <p>
 * Title : IZoneCommDAO.java
 * </p>
 * BO: ZoneComm
 * <p>
 * Copyright : Copyright (c) 2009
 * </p>
 * <p>
 * Company : AIRFRANCE
 * </p>
 */
@Repository
public interface ZoneCommRepository extends JpaRepository<ZoneComm, String>, ZoneCommRepositoryCustom {

	@Query("SELECT z FROM ZoneComm z WHERE z.dateFermeture is null and z.zc1 = 'ANN' and z.zc5 is not null")
	List<ZoneComm> getCancellationZones();

	@Query("SELECT zc FROM ZoneComm zc WHERE ( zc.dateFermeture is null OR zc.dateFermeture >= sysdate ) AND zc.zc1 = :pZc1 AND (:pZc2 is null and zc.zc2 is null or zc.zc2 = :pZc2) AND (:pZc3 is null and zc.zc3 is null or zc.zc3 = :pZc3) AND (:pZc4 is null and zc.zc4 is null or zc.zc4 = :pZc4) AND (:pZc5 is null and zc.zc5 is null or zc.zc5 = :pZc5)")
	ZoneComm findActiveByZc1Zc2Zc3Zc4Zc5(@Param("pZc1") String pZc1, @Param("pZc2") String pZc2,
			@Param("pZc3") String pZc3, @Param("pZc4") String pZc4, @Param("pZc5") String pZc5);

	@Query("SELECT zc FROM ZoneComm zc WHERE zc.zc1 = 'ANN' AND zc.zc2 = 'ULA' AND zc.zc3 = 'TION' AND zc.zc4 = :zc1 AND zc.zc5 is not null")
	List<ZoneComm> findCancellation(@Param("zc1") String zc1);

	@Query("SELECT DISTINCT(zc.zc1) FROM ZoneComm zc WHERE zc.zc1 IS NOT NULL AND zc.zc2 IS NULL")
	public List<String> getAllZc1();

	@Query("SELECT DISTINCT(zc.zc2) FROM ZoneComm zc WHERE zc.zc1 =:zc1 AND zc.zc2 IS NOT NULL AND zc.zc3 IS NULL")
	public List<String> getAllZc2(@Param("zc1") String zc1);

	@Query("SELECT DISTINCT(zc.zc3) FROM ZoneComm zc WHERE zc.zc1 =:zc1 AND zc.zc2 =:zc2 AND zc.zc3 IS NOT NULL AND zc.zc4 IS NULL")
	public List<String> getAllZc3(@Param("zc1") String zc1, @Param("zc2") String zc2);

	@Query("SELECT DISTINCT(zc.zc4) FROM ZoneComm zc WHERE zc.zc1 =:zc1 AND zc.zc2 =:zc2 AND zc.zc3 =:zc3 AND zc.zc4 IS NOT NULL AND zc.zc5 IS NULL")
	public List<String> getAllZc4(@Param("zc1") String zc1, @Param("zc2") String zc2, @Param("zc3") String zc3);

	@Query("SELECT DISTINCT(zc.zc5) FROM ZoneComm zc WHERE zc.zc1 =:zc1 AND zc.zc2 =:zc2 AND zc.zc3 =:zc3 AND zc.zc4 =:zc4 AND zc.zc5 IS NOT NULL")
	public List<String> getAllZc5(@Param("zc1") String zc1, @Param("zc2") String zc2, @Param("zc3") String zc3,
			@Param("zc4") String zc4);

	@Query("SELECT zc FROM ZoneComm zc WHERE zc.zc1=:zc1 AND zc.zc2 IS NOT NULL AND zc.zc3 IS NULL")
	public List<ZoneComm> findHierarchicalByZc1(@Param("zc1") String zc1);

	@Query("SELECT zc FROM ZoneComm zc WHERE zc.zc1=:zc1 AND zc.zc2=:zc2 AND zc.zc3 IS NOT NULL AND zc.zc4 IS NULL")
	public List<ZoneComm> findHierarchicalByZc2(@Param("zc1") String zc1, @Param("zc2") String zc2);

	@Query("SELECT zc FROM ZoneComm zc WHERE zc.zc1=:zc1 AND zc.zc2=:zc2 AND zc.zc3=:zc3 AND zc.zc4 IS NOT NULL AND zc.zc5 IS NULL")
	public List<ZoneComm> findHierarchicalByZc3(@Param("zc1") String zc1, @Param("zc2") String zc2,
			@Param("zc3") String zc3);

	@Query("SELECT zc FROM ZoneComm zc WHERE zc.zc1=:zc1 AND zc.zc2=:zc2 AND zc.zc3=:zc3 AND zc.zc4=:zc4 AND zc.zc5 IS NOT NULL")
	public List<ZoneComm> findHierarchicalByZc4(@Param("zc1") String zc1, @Param("zc2") String zc2,
			@Param("zc3") String zc3, @Param("zc4") String zc4);

	@Query("SELECT zc FROM ZoneComm zc WHERE zc.zc1=:zc1")
	public List<ZoneComm> findAllByZc1(@Param("zc1") String zc1);

	@Query("SELECT zc FROM ZoneComm zc WHERE zc.zc1=:zc1 AND zc.zc2=:zc2")
	public List<ZoneComm> findAllByZc2(@Param("zc1") String zc1, @Param("zc2") String zc2);

	@Query("SELECT zc FROM ZoneComm zc WHERE zc.zc1=:zc1 AND zc.zc2=:zc2 AND zc.zc3=:zc3")
	public List<ZoneComm> findAllByZc3(@Param("zc1") String zc1, @Param("zc2") String zc2, @Param("zc3") String zc3);

	@Query("SELECT zc FROM ZoneComm zc WHERE zc.zc1=:zc1 AND zc.zc2=:zc2 AND zc.zc3=:zc3 AND zc.zc4=:zc4")
	public List<ZoneComm> findAllByZc4(@Param("zc1") String zc1, @Param("zc2") String zc2, @Param("zc3") String zc3,
			@Param("zc4") String zc4);

	@Query("SELECT zc FROM ZoneComm zc WHERE zc.zc1=:zc1 AND zc.zc2=:zc2 AND zc.zc3=:zc3 AND zc.zc4=:zc4 AND zc.zc5=:zc5")
	public List<ZoneComm> findAllByZc5(@Param("zc1") String zc1, @Param("zc2") String zc2, @Param("zc3") String zc3,
			@Param("zc4") String zc4, @Param("zc5") String zc5);

	@Query("SELECT zc FROM ZoneComm zc WHERE zc.gin=:gin")
	public ZoneComm findByGin(@Param("gin") Long gin);

	@Query("SELECT zc FROM ZoneComm zc WHERE zc.zc1=:zc1 AND zc.zc2 IS NULL AND (zc.dateFermeture IS NULL OR zc.dateFermeture >= sysdate)")
	public ZoneComm findActiveByZc1(@Param("zc1") String zc1);

	@Query("SELECT zc FROM ZoneComm zc WHERE zc.zc1=:zc1 AND zc.zc2=:zc2 AND zc.zc3 IS NULL AND (zc.dateFermeture IS NULL OR zc.dateFermeture >= sysdate)")
	public ZoneComm findActiveByZc2(@Param("zc1") String zc1, @Param("zc2") String zc2);

	@Query("SELECT zc FROM ZoneComm zc WHERE zc.zc1=:zc1 AND zc.zc2=:zc2 AND zc.zc3=:zc3 AND zc.zc4 IS NULL AND (zc.dateFermeture IS NULL OR zc.dateFermeture >= sysdate)")
	public ZoneComm findActiveByZc3(@Param("zc1") String zc1, @Param("zc2") String zc2, @Param("zc3") String zc3);

	@Query("SELECT zc FROM ZoneComm zc WHERE zc.zc1=:zc1 AND zc.zc2=:zc2 AND zc.zc3=:zc3 AND zc.zc4=:zc4 AND zc.zc5 IS NULL AND (zc.dateFermeture IS NULL OR zc.dateFermeture >= sysdate)")
	public ZoneComm findActiveByZc4(@Param("zc1") String zc1, @Param("zc2") String zc2, @Param("zc3") String zc3,
			@Param("zc4") String zc4);

	@Query("SELECT zc FROM ZoneComm zc WHERE zc.zc1=:zc1 AND zc.zc2=:zc2 AND zc.zc3=:zc3 AND zc.zc4=:zc4 AND zc.zc5=:zc5 AND (zc.dateFermeture IS NULL OR zc.dateFermeture >= sysdate)")
	public ZoneComm findActiveByZc5(@Param("zc1") String zc1, @Param("zc2") String zc2, @Param("zc3") String zc3,
			@Param("zc4") String zc4, @Param("zc5") String zc5);

	@Query("SELECT DISTINCT(zc.zc1) FROM ZoneComm zc WHERE zc.zc1 IS NOT NULL AND zc.zc2 IS NULL AND (:subtype IS NULL OR zc.sousType = :subtype) AND (:active IS NULL OR (:active = TRUE AND zc.dateFermeture IS NULL OR zc.dateFermeture >= sysdate) OR (:active = FALSE AND zc.dateFermeture < sysdate))")
	public List<String> getAllZc1(@Param("active") Boolean active, @Param("subtype") String subtype);

	@Query("SELECT DISTINCT(zc.zc2) FROM ZoneComm zc WHERE zc.zc1 =:zc1 AND zc.zc2 IS NOT NULL AND zc.zc3 IS NULL AND (:subtype IS NULL OR zc.sousType = :subtype) AND (:active IS NULL OR (:active = TRUE AND zc.dateFermeture IS NULL OR zc.dateFermeture >= sysdate) OR (:active = FALSE AND zc.dateFermeture < sysdate))")
	public List<String> getAllZc2(@Param("zc1") String zc1, @Param("active") Boolean active, @Param("subtype") String subtype);

	@Query("SELECT DISTINCT(zc.zc3) FROM ZoneComm zc WHERE zc.zc1 =:zc1 AND zc.zc2 =:zc2 AND zc.zc3 IS NOT NULL AND zc.zc4 IS NULL AND (:subtype IS NULL OR zc.sousType = :subtype) AND (:active IS NULL OR (:active = TRUE AND zc.dateFermeture IS NULL OR zc.dateFermeture >= sysdate) OR (:active = FALSE AND zc.dateFermeture < sysdate))")
	public List<String> getAllZc3(@Param("zc1") String zc1, @Param("zc2") String zc2, @Param("active") Boolean active, @Param("subtype") String subtype);

	@Query("SELECT DISTINCT(zc.zc4) FROM ZoneComm zc WHERE zc.zc1 =:zc1 AND zc.zc2 =:zc2 AND zc.zc3 =:zc3 AND zc.zc4 IS NOT NULL AND zc.zc5 IS NULL AND (:subtype IS NULL OR zc.sousType = :subtype) AND (:active IS NULL OR (:active = TRUE AND zc.dateFermeture IS NULL OR zc.dateFermeture >= sysdate) OR (:active = FALSE AND zc.dateFermeture < sysdate))")
	public List<String> getAllZc4(@Param("zc1") String zc1, @Param("zc2") String zc2, @Param("zc3") String zc3,
			@Param("active") Boolean active, @Param("subtype") String subtype);

	@Query("SELECT DISTINCT(zc.zc5) FROM ZoneComm zc WHERE zc.zc1 =:zc1 AND zc.zc2 =:zc2 AND zc.zc3 =:zc3 AND zc.zc4 =:zc4 AND zc.zc5 IS NOT NULL AND (:subtype IS NULL OR zc.sousType = :subtype) AND (:active IS NULL OR (:active = TRUE AND zc.dateFermeture IS NULL OR zc.dateFermeture >= sysdate) OR (:active = FALSE AND zc.dateFermeture < sysdate))")
	public List<String> getAllZc5(@Param("zc1") String zc1, @Param("zc2") String zc2, @Param("zc3") String zc3,
			@Param("zc4") String zc4, @Param("active") Boolean active, @Param("subtype") String subtype);

	/**
	 * Get ZCs with the ikey of a PersonMorale.
	 * The ZC must be active.
	 * @param ginPM
	 * @return a list of zc
	 */
	@Query("select zc " +
			"from ZoneComm zc " +
			"join PmZone pz on zc.gin=pz.zoneDecoup.gin " +
			"where pz.lienPrivilegie='O' and (pz.dateFermeture is null or pz.dateFermeture > sysdate) " +
			"and pz.personneMorale.gin=:ginPM")
	List<ZoneComm> getZoneCommByGinOfPM(@Param("ginPM") String ginPM);

	/**
	 * Get ZCs with the ikey of a Fonction.
	 * The ZC must be active.
	 * @param cle_fonction
	 * @return a list of zc
	 */
	@Query("select zc " +
			"from ZoneComm zc " +
			"join PmZone pz on zc.gin=pz.zoneDecoup.gin " +
			"where pz.lienPrivilegie='O' and (pz.dateFermeture is null or pz.dateFermeture > sysdate) " +
			"and (pz.dateOuverture is null or pz.dateOuverture <= sysdate) " +
			"and pz.personneMorale.gin in " +
			"(select m.personneMorale.gin from Membre m join Fonction f on f.membre.key=m.key where f.cle=:cle_fonction) ")
	List<ZoneComm> getZoneCommByGinOfFonction(@Param("cle_fonction") Integer cle_fonction);
}
