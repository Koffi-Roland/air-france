package com.airfrance.repind.dao.firme;

import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.repind.entity.firme.GestionPM;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>Title : IGestionPMDAO.java</p>
 * BO: GestionPM
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Repository
public interface GestionPMRepositoryCustom {
	List<GestionPM> findByPMGereeGin(String gin, Integer firstResultIndex, Integer maxResults) throws JrafDaoException;
}
