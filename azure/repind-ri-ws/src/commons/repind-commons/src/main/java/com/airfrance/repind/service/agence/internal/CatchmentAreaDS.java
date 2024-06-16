package com.airfrance.repind.service.agence.internal;

import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.repind.dao.agence.CatchmentAreaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class CatchmentAreaDS {

	/** logger */
//	private static final Log log = LogFactory.getLog(MembreReseauDS.class);

	/** the Entity Manager */
	@PersistenceContext(unitName = "entityManagerFactoryRepind")
	private EntityManager entityManager;

  	@Autowired
    private CatchmentAreaRepository catchmentAreaRepository;

	public String findCatchmentAreaByPostalCode(String sPostalCode) throws JrafDaoException {
		return catchmentAreaRepository.findCatchmentAreaByPostalCode(sPostalCode);
	}

	public String findCatchmentAreaByDeptCode(String sDeptCode) throws JrafDaoException {
		return catchmentAreaRepository.findCatchmentAreaByDeptCode(sDeptCode);
	}
	
}
