package com.airfrance.repind.firme.searchfirmonmulticriteria.ds;


import com.airfrance.repind.entity.firme.PersonneMorale;
import com.airfrance.repind.entity.zone.ZoneComm;
import com.airfrance.repind.firme.searchfirmonmulticriteria.dto.FirmNameDTO;

public interface IFindPriorityZcDS {
	public void findPriorityZc(FirmNameDTO firmName, PersonneMorale personneMorale);
	
	public ZoneComm findPriorityZc(PersonneMorale personneMorale) ;
}
