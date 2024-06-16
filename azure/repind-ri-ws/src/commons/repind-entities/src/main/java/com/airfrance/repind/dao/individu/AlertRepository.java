package com.airfrance.repind.dao.individu;

import com.airfrance.repind.entity.individu.Alert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * <p>Title : IAlertDAO.java</p>
 * BO: Alert
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Repository
public interface AlertRepository extends JpaRepository<Alert, Integer> {

	@Query(value = "Select a from Alert a right outer join a.alertdata ad "
			+ "where ad.key='COMPREF_ID' "
			+ "and ad.value=:comPrefId")
	List<Alert> findByComPrefId(@Param("comPrefId") String comPrefId);

	List<Alert> findBySgin(String gin);

	@Query(value = "Select count(1) from Alert a "
			+ "where a.sgin=:gin "
			+ "and a.optIn=:optin")
	int countBySginByOptIn(@Param("gin") String gin, @Param("optin") String optin);
}
