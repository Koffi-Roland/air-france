package com.airfrance.repind.dao.individu;

import com.airfrance.repind.entity.individu.AlertData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * <p>Title : IAlertDataDAO.java</p>
 * BO: AlertData
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Repository
public interface AlertDataRepository extends JpaRepository<AlertData, Integer> {
	
	@Query("select ad from AlertData ad where ad.alert.alertId = :alertId")
	Set<AlertData> findByAlert(@Param("alertId") Integer alert);
	
}
