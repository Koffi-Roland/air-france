package com.airfrance.repind.dao.firme;

/*PROTECTED REGION ID(_ZJUfQLbCEeCrCZp8iGNNVwDAO DAO I i) ENABLED START*/

// add not generated imports here

import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.repind.entity.firme.Entreprise;
import org.springframework.stereotype.Repository;

import java.util.List;

/*PROTECTED REGION END*/

/**
 * <p>Title : IEntrepriseDAO.java</p>
 * BO: Entreprise
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Repository
public interface EntrepriseRepositoryCustom {

    /**
     * @param pGin GIN
     * @return Entreprise with collections populated
     */
    Entreprise findByGinWithAllCollections(final String pGin) throws JrafDaoException;
    
    /**
     * @param pGin GIN
     * @param scopeToProvide the list of blocs to populate
     * @return Entreprise with collections populated
     */
    Entreprise findByGinWithAllCollections(final String pGin, List<String> scopeToProvide) throws JrafDaoException;
    
    /**
     * Recherche d'une Entreprise par son numéro SIREN
     * @param pSiren numéro SIREN
     * @return Entreprise trouvée, null sinon
     * @throws JrafDaoException
     */
    Entreprise findUniqueBySiren(final String pSiren) throws JrafDaoException;
    
    /**
     * Recherche une Entreprise par son numéro SIREN (non unique dans la base repind !!!)
     * @param pSiren numéro SIREN
     * @return la 1ère Entreprise trouvée, null sinon
     * @throws JrafDaoException
     */
    Entreprise findAnyBySiren(final String pSiren) throws JrafDaoException;
    
    /**
     * Find gin of closed enterprise
     * @return list of gin of closed enterprise
     * @throws JrafDaoException
     */
    public List<String> findClosed() throws JrafDaoException;    
    
    /**
     * Find gin of closed enterprise that are not SGIN_PERE for an Agency
     * @return list of gin of closed enterprise that are not SGIN_PERE for an Agency
     * @throws JrafDaoException
     */
    public List<String> findClosedNotSginPereOnAgency() throws JrafDaoException;
    /*PROTECTED REGION END*/
}
