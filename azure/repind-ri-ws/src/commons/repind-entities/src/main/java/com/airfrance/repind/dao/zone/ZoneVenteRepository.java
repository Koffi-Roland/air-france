package com.airfrance.repind.dao.zone;

import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.repind.entity.zone.ZoneVente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/*PROTECTED REGION END*/

/**
 * <p>
 * Title : IZoneVenteDAO.java
 * </p>
 * BO: ZoneVente
 * <p>
 * Copyright : Copyright (c) 2009
 * </p>
 * <p>
 * Company : AIRFRANCE
 * </p>
 */
@Repository
public interface ZoneVenteRepository extends JpaRepository<ZoneVente, Integer>, ZoneVenteRepositoryCustom {

	@Query("SELECT zv FROM ZoneVente zv WHERE ( zv.dateFermeture is null OR zv.dateFermeture >= sysdate ) AND zv.zv0 = :param1 AND zv.zv1 = :param2 AND zv.zv2 = :param3 AND zv.zv3 = :param4")
	public ZoneVente findActiveByZv0Zv1Zv2Zv3(@Param("param1") Integer pZv0, @Param("param2") Integer pZv1,
			@Param("param3") Integer pZv2, @Param("param4") Integer pZv3) throws JrafDaoException;

	@Query("SELECT zv FROM ZoneVente zv WHERE ( zv.dateFermeture is null OR zv.dateFermeture >= sysdate ) AND zv.zvAlpha = :pZvAlpha ")
	public ZoneVente findActiveByZvAlpha(@Param("pZvAlpha") String pZvAlpha);

	@Query("SELECT DISTINCT(zv) FROM ZoneVente zv WHERE zv.zv0 IS NOT NULL AND zv.zv1 IS NULL")
	public List<ZoneVente> getAllZv0();

	@Query("SELECT DISTINCT(zv) FROM ZoneVente zv WHERE zv.zv0 =:zv0 AND zv.zv1 IS NOT NULL AND zv.zv2 IS NULL")
	public List<ZoneVente> getAllZv1(@Param("zv0") Integer zv0);

	@Query("SELECT DISTINCT(zv) FROM ZoneVente zv WHERE zv.zv0 =:zv0 AND zv.zv1 =:zv1 AND zv.zv2 IS NOT NULL AND zv.zv3 IS NULL")
	public List<ZoneVente> getAllZv2(@Param("zv0") Integer zv0, @Param("zv1") Integer zv1);

	@Query("SELECT DISTINCT(zv) FROM ZoneVente zv WHERE zv.zv0 =:zv0 AND zv.zv1 =:zv1 AND zv.zv2 =:zv2 AND zv.zv3 IS NOT NULL")
	public List<ZoneVente> getAllZv3(@Param("zv0") Integer zv0, @Param("zv1") Integer zv1, @Param("zv2") Integer zv2);

	@Query("SELECT zv FROM ZoneVente zv WHERE zv.zv0=:zv0 AND zv.zv1 IS NOT NULL AND zv.zv2 IS NULL")
	public List<ZoneVente> findHierarchicalByZv0(@Param("zv0") Integer zv0);

	@Query("SELECT zv FROM ZoneVente zv WHERE zv.zv0=:zv0 AND zv.zv1=:zv1 AND zv.zv2 IS NOT NULL AND zv.zv3 IS NULL")
	public List<ZoneVente> findHierarchicalByZv1(@Param("zv0") Integer zv0, @Param("zv1") Integer zv1);

	@Query("SELECT zv FROM ZoneVente zv WHERE zv.zv0=:zv0 AND zv.zv1=:zv1 AND zv.zv2=:zv2 AND zv.zv3 IS NOT NULL")
	public List<ZoneVente> findHierarchicalByZv2(@Param("zv0") Integer zv0, @Param("zv1") Integer zv1,
			@Param("zv2") Integer zv2);

	@Query("SELECT zv FROM ZoneVente zv WHERE zv.zv0=:zv0")
	public List<ZoneVente> findAllByZv0(@Param("zv0") Integer zv0);

	@Query("SELECT zv FROM ZoneVente zv WHERE zv.zv0=:zv0 AND zv.zv1=:zv1")
	public List<ZoneVente> findAllByZv1(@Param("zv0") Integer zv0, @Param("zv1") Integer zv1);

	@Query("SELECT zv FROM ZoneVente zv WHERE zv.zv0=:zv0 AND zv.zv1=:zv1 AND zv.zv2=:zv2")
	public List<ZoneVente> findAllByZv2(@Param("zv0") Integer zv0, @Param("zv1") Integer zv1, @Param("zv2") Integer zv2);

	@Query("SELECT zv FROM ZoneVente zv WHERE zv.zv0=:zv0 AND zv.zv1=:zv1 AND zv.zv2=:zv2 AND zv.zv3=:zv3")
	public List<ZoneVente> findAllByZv3(@Param("zv0") Integer zv0, @Param("zv1") Integer zv1, @Param("zv2") Integer zv2,
			@Param("zv3") Integer zv3);
	
	@Query("SELECT zv FROM ZoneVente zv WHERE zv.gin=:gin")
	public ZoneVente findByGin(@Param("gin") Long gin);
	
	@Query("SELECT zv FROM ZoneVente zv WHERE zv.zv0=:zv0 AND zv.zv1 IS NULL AND ( zv.dateFermeture IS NULL OR zv.dateFermeture >= sysdate )")
	public ZoneVente findActiveByZv0(@Param("zv0") Integer zv0);

	@Query("SELECT zv FROM ZoneVente zv WHERE zv.zv0=:zv0 AND zv.zv1=:zv1 AND zv.zv2 IS NULL AND ( zv.dateFermeture IS NULL OR zv.dateFermeture >= sysdate )")
	public ZoneVente findActiveByZv1(@Param("zv0") Integer zv0, @Param("zv1") Integer zv1);

	@Query("SELECT zv FROM ZoneVente zv WHERE zv.zv0=:zv0 AND zv.zv1=:zv1 AND zv.zv2=:zv2 AND zv.zv3 IS NULL AND ( zv.dateFermeture IS NULL OR zv.dateFermeture >= sysdate )")
	public ZoneVente findActiveByZv2(@Param("zv0") Integer zv0, @Param("zv1") Integer zv1, @Param("zv2") Integer zv2);

	@Query("SELECT zv FROM ZoneVente zv WHERE zv.zv0=:zv0 AND zv.zv1=:zv1 AND zv.zv2=:zv2 AND zv.zv3=:zv3 AND ( zv.dateFermeture IS NULL OR zv.dateFermeture >= sysdate )")
	public ZoneVente findActiveByZv3(@Param("zv0") Integer zv0, @Param("zv1") Integer zv1, @Param("zv2") Integer zv2,
			@Param("zv3") Integer zv3);
	
	@Query("SELECT DISTINCT(zv) FROM ZoneVente zv WHERE zv.zv0 IS NOT NULL AND zv.zv1 IS NULL AND (:active IS NULL OR (:active = TRUE AND zv.dateFermeture IS NULL OR zv.dateFermeture >= sysdate) OR (:active = FALSE AND zv.dateFermeture < sysdate))")
	public List<ZoneVente> getAllZv0(@Param("active") Boolean active);

	@Query("SELECT DISTINCT(zv) FROM ZoneVente zv WHERE zv.zv0 =:zv0 AND zv.zv1 IS NOT NULL AND zv.zv2 IS NULL AND (:active IS NULL OR (:active = TRUE AND zv.dateFermeture IS NULL OR zv.dateFermeture >= sysdate) OR (:active = FALSE AND zv.dateFermeture < sysdate))")
	public List<ZoneVente> getAllZv1(@Param("zv0") Integer zv0, @Param("active") Boolean active);

	@Query("SELECT DISTINCT(zv) FROM ZoneVente zv WHERE zv.zv0 =:zv0 AND zv.zv1 =:zv1 AND zv.zv2 IS NOT NULL AND zv.zv3 IS NULL AND (:active IS NULL OR (:active = TRUE AND zv.dateFermeture IS NULL OR zv.dateFermeture >= sysdate) OR (:active = FALSE AND zv.dateFermeture < sysdate))")
	public List<ZoneVente> getAllZv2(@Param("zv0") Integer zv0, @Param("zv1") Integer zv1, @Param("active") Boolean active);

	@Query("SELECT DISTINCT(zv) FROM ZoneVente zv WHERE zv.zv0 =:zv0 AND zv.zv1 =:zv1 AND zv.zv2 =:zv2 AND zv.zv3 IS NOT NULL AND (:active IS NULL OR (:active = TRUE AND zv.dateFermeture IS NULL OR zv.dateFermeture >= sysdate) OR (:active = FALSE AND zv.dateFermeture < sysdate))")
	public List<ZoneVente> getAllZv3(@Param("zv0") Integer zv0, @Param("zv1") Integer zv1, @Param("zv2") Integer zv2, @Param("active") Boolean active);

}
