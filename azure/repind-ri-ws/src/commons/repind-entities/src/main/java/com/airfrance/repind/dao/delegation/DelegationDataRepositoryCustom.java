package com.airfrance.repind.dao.delegation;

import com.airfrance.repind.entity.delegation.DelegationData;
import com.airfrance.repind.entity.individu.Individu;

import java.util.List;

public interface DelegationDataRepositoryCustom {
	
	public List<DelegationData> findDelegator(String gin);
	public List<DelegationData> findDelegate(String gin);
	public DelegationData findDelegation(DelegationData delegation);
	public boolean isDelegationTypeValid(String type);
	public int getDelegateNumberByGin(Individu i);
	public int getDelegatorNumberByGin(Individu i);
}
