package com.airfrance.repind.dao.individu;

import com.airfrance.repind.entity.adresse.Telecoms;
import com.airfrance.repind.entity.individu.Individu;
import com.airfrance.repind.entity.role.BusinessRole;
import com.airfrance.repind.entity.role.RoleGP;

import java.util.List;

public interface IndividuRepositoryCustom {

	List<String> getAnIndividualIdentification(String email);

	Integer countIndividualIdentification(String email);

	List<RoleGP> getGPRoleByGIN(String sGIN);
	
	List<BusinessRole> getBusinessRoleByGIN(String sGIN);
	
	List<Individu> findIndividuByExample(int i, int j);

	List<Telecoms> findTelecomsByGINSortedByDate(String sGin);

	Integer getVersionOfIndividual(String gin);
	//Individu findByGinFetchingRelationships(String pGin, List<String> pRelationships, List<String> pSubRelationships);
}
