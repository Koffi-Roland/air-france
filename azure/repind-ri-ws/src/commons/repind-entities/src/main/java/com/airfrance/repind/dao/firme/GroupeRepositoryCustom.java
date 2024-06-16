package com.airfrance.repind.dao.firme;

import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.repind.entity.firme.Groupe;
import org.springframework.stereotype.Repository;

import java.util.List;

/*PROTECTED REGION END*/

/**
 * <p>Title : IGroupeDAO.java</p>
 * BO: Groupe
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Repository
public interface GroupeRepositoryCustom {

	/**
     * @param pGin GIN
     * @return Groupe with collections populated
     */
    Groupe findByGinWithAllCollections(final String pGin) throws JrafDaoException;
    
    /**
     * @param pGin GIN
     * @param scopeToProvide the list of blocs to populate
     * @return Groupe with collections populated
     */
    Groupe findByGinWithAllCollections(final String pGin, List<String> scopeToProvide) throws JrafDaoException;
    /*PROTECTED REGION END*/
}
