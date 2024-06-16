package com.airfrance.repind.dao.firme;

import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.repind.entity.firme.PersonneMorale;

import java.util.List;

public interface PersonneMoraleRepositoryCustom {
	
	public List<PersonneMorale> findClosedServicesNotModifiedSince(int numberOfDays);
	
	public PersonneMorale findPMByOptions(String optionType, String optionValue) throws JrafDaoException;
	
	public List<PersonneMorale> findByNumeroContrat(String pNumeroContrat) throws JrafDaoException;
	
	public void deleteByPMList(List<String> pLstGinPersonneMorale) throws JrafDaoException;
	
	public void deleteCascadeByGin(String gin) throws JrafDaoException;
	
	public List<PersonneMorale> findByParentGin(String gin, Integer firstResultIndex, Integer maxResults) throws JrafDaoException;
	
	public void refresh(PersonneMorale personneMorale);
}
