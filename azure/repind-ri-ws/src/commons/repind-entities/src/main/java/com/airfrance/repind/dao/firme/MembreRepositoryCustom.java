package com.airfrance.repind.dao.firme;

import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.entity.firme.Membre;
import com.airfrance.repind.entity.firme.MembreLight;

import java.util.Date;
import java.util.List;

public interface MembreRepositoryCustom {
	
	public List<Membre> findByPersonneMorale(String pGinPm, String pGinIndividu, String linkType, String sortType, String lastName, String jobTitle, List<String> fbTier, Boolean memberStatusFilter, Boolean jobStatusFilter, Integer index) throws JrafDomainException;
	
	public long countByPersonneMorale(String pGinPm, String pGinIndividu, String linkType, String sortType, String lastName, String jobTitle, List<String> fbTier, Boolean memberStatusFilter, Boolean jobStatusFilter) throws JrafDomainException;
	
	public List<Membre> findMember(Integer ain, Boolean jobStatusFilter)throws JrafDomainException;
	
	public List<MembreLight> findByMultiCriteria(String individualGin, String legalPersonGin, List<String> legalPersonTypes, String jobTitle, String client, String contact, Boolean isClientOrContact, Date memberEndDate, Integer membershipFirstResultIndex, Integer membershipMaxResult) throws JrafDaoException;
	
	public int getNumberFirmsOrAgenciesLinkedByGin(String gin, boolean firms) throws JrafDaoException;
	
	public void refresh(Membre membre);
	
}
