package com.airfrance.repind.dao.zone;

import com.airfrance.repind.entity.zone.LienZvZc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/*PROTECTED REGION END*/

/**
 * <p>Title : ILienZvZcDAO.java</p>
 * BO: LienZvZc
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Repository
public interface LienZvZcRepository extends JpaRepository<LienZvZc, Integer> {

	@Query("SELECT li FROM LienZvZc li WHERE (li.dateFermeture is null OR li.dateFermeture >= current_date ) AND li.zoneVente.gin IN (:gins)")
	List<LienZvZc> findActiveByZoneVente(@Param("gins") List<Long> zvGinList);
}
