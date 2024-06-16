package com.airfrance.repind.dao.firme;

import com.airfrance.repind.entity.firme.GestionPM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <p>Title : IGestionPMDAO.java</p>
 * BO: GestionPM
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Repository
public interface GestionPMRepository extends JpaRepository<GestionPM, Integer>, GestionPMRepositoryCustom {

}
