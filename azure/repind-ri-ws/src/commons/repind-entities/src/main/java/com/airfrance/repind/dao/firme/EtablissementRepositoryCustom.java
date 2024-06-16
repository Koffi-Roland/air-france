package com.airfrance.repind.dao.firme;

import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.repind.entity.firme.Etablissement;
import com.airfrance.repind.entity.zone.ZoneComm;
import com.airfrance.repind.scope.FirmScope;

import java.util.List;

public interface EtablissementRepositoryCustom {
	
	public Etablissement findByGinWithAllCollectionsFusion(final String pGin, List<String> scopeToProvide) throws JrafDaoException;
	
	public Etablissement findByGinWithAllCollections(final String pGin, List<String> scopeToProvide) throws JrafDaoException;
	
	public List<Etablissement> findEtablissementByExample(final Etablissement etablissement, int i, int j);
	
	public List<Etablissement> findByVille(final String pVille) throws JrafDaoException;
	
	public List<Etablissement> findByNumeroIdent(final String pTypeIdent, final String pNumeroIdent) throws JrafDaoException;
	
	public List<Etablissement> findByNumeroIdentBis(final String pTypeIdent, final String pNumeroIdent) throws JrafDaoException;
	
	public List<Etablissement> findByNumeroContrat(String pNumeroContrat) throws JrafDaoException;
	
	public List<Etablissement> findBySiret(String pSiret) throws JrafDaoException;
	
	public boolean existWithSameSiret(Etablissement pEtablissement) throws JrafDaoException;
	
	public Etablissement findUniqueBySiret(String pSiret) throws JrafDaoException;
	
	public List<String> findMerged() throws JrafDaoException;
	
	public List<String> findMergedNotSginPereOnAgency() throws JrafDaoException;
	
	public List<String> findClosed() throws JrafDaoException;
	
	public List<String> findClosedNotSginPereOnAgency() throws JrafDaoException;
	
	public List<String> findActPend() throws JrafDaoException;
	
	public List<String> findActPendNotSginPereOnAgency() throws JrafDaoException;
	
	public List<String> findClosedOrMergedInCancellationZone() throws JrafDaoException;
	
	public List<String> findClosedOrMergedInCancellationZoneNotSginPereOnAgency() throws JrafDaoException;
	
	public Etablissement findByGinWithPmZoneAndMembre(String ginValue) throws JrafDaoException;
	
	public Etablissement findByGinWithPmZone(String ginValue) throws JrafDaoException;
	
	public Etablissement findByGinWithBusinessRole(String ginValue) throws JrafDaoException;
	
	public Etablissement findByGinWithEnfant(String ginValue) throws JrafDaoException;
    
    public Etablissement findByGinWithPmZoneMembreEnfant(String ginValue) throws JrafDaoException;

    public List<Etablissement> findByNameWithAddressesUsingJpqlBadly(final String pName, final int pIndex, final int pMaxLines) throws JrafDaoException;

    public List<Etablissement> findByNameWithAddressesUsingJpqlProperly(final String pName, final int pIndex, final int pMaxLines) throws JrafDaoException;

    public List<Etablissement> findByNameWithAddressesUsingCriteriaBadly(final String pName, final int pIndex, final int pMaxLines) throws JrafDaoException;

    public List<Etablissement> findByNameWithAddressesUsingCriteriaProperly(final String pName, final int pIndex, final int pMaxLines) throws JrafDaoException;
    
    public boolean addZoneDecoupToEtablissement(Etablissement pEtablissement, ZoneComm pZoneComm);

	public Etablissement findByGinUsingScopes(String gin, List<FirmScope> scopes) throws JrafDaoException;
	
	public void refresh(Etablissement etablissement);
}
