package com.airfrance.repind.dao.firme;

import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.repind.entity.firme.Chiffre;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * <p>Title : IChiffreDAO.java</p>
 * BO: Chiffre
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Repository
public interface ChiffreRepositoryCustom {
	/**
	 * Finds all numbers associated to the moral person
	 * having The GIN gin. Returned numbers are given from the
	 * firstResultIndex and are in maximum maxResults.
	 * 
	 * @param gin
	 * @param firstResultIndex
	 * @param endIndex
	 * 
	 * @return null if no results found.
	 * 
	 * @throws JrafDaoException if a technical exception is fired.
	 */
	List<Chiffre> findByPMGin(String gin, Integer firstResultIndex, Integer maxResults) throws JrafDaoException;

    /*PROTECTED REGION ID(_ULViULbCEeCrCZp8iGNNVwDAO - IDAO) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
}
