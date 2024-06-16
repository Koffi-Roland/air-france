package com.airfrance.repind.firme.searchfirmonmulticriteria.ds;

import com.airfrance.repind.entity.firme.PersonneMorale;
import com.airfrance.repind.entity.zone.PmZone;
import com.airfrance.repind.entity.zone.ZoneComm;
import com.airfrance.repind.firme.searchfirmonmulticriteria.dto.FirmNameDTO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class FindPriorityZcDS extends AbstractDS implements IFindPriorityZcDS {

	/*===============================================*/
	/*                   LOGGER                      */
	/*===============================================*/
	private static Log LOGGER  = LogFactory.getLog(FindPriorityZcDS.class);
	
	/*===============================================*/
	/*                   PUBLIC METHODS              */
	/*===============================================*/
	public void findPriorityZc(FirmNameDTO firmName, PersonneMorale personneMorale) 
	{	
		ZoneComm priorityZc = null;
		boolean hasPrivilege = false;
		Date priorityZcUpdateDate = null;
		Date currentDate = new Date();
		long currentDateTime = currentDate.getTime();

		if((firmName != null) && (personneMorale != null))
		{
			if((personneMorale.getPmZones() != null) && (! personneMorale.getPmZones().isEmpty()))
			{
				for(PmZone pmZone : personneMorale.getPmZones())
				{
					if(pmZone.getZoneDecoup() != null)
					{
						if(pmZone.getZoneDecoup().getClass().equals(ZoneComm.class))
						{
							ZoneComm zc = (ZoneComm)pmZone.getZoneDecoup();
							if(priorityZc == null)
							{
								priorityZc = zc;
								if((pmZone.getLienPrivilegie() != null) && (pmZone.getLienPrivilegie().equalsIgnoreCase("o")
										&& (pmZone.getDateFermeture() == null || pmZone.getDateFermeture().getTime() > currentDateTime)))
								{
									hasPrivilege = true;
								}
								if(zc.getDateMaj() != null) 
								{
									priorityZcUpdateDate = zc.getDateMaj();
								}
							}
							else if((pmZone.getLienPrivilegie() != null) 
									&& (pmZone.getLienPrivilegie().equalsIgnoreCase("o"))
									&& (pmZone.getDateFermeture() == null || pmZone.getDateFermeture().getTime() > currentDateTime)
									&& (hasPrivilege == false))
							{
								priorityZc = zc;
								hasPrivilege = true;
								if(zc.getDateMaj() != null) 
								{
									priorityZcUpdateDate = zc.getDateMaj();
								}
							}	
							else if((pmZone.getLienPrivilegie() != null) 
									&& (pmZone.getLienPrivilegie().equalsIgnoreCase("o"))
									&& (pmZone.getDateFermeture() == null || pmZone.getDateFermeture().getTime() > currentDateTime)
									&& (hasPrivilege == true))
							{
								if((zc.getDateMaj() != null) 
										&& (priorityZcUpdateDate != null)
										&& (zc.getDateMaj().after(priorityZcUpdateDate)))
								{
									priorityZc = zc;
									hasPrivilege = true;
									priorityZcUpdateDate = zc.getDateMaj();
								}
								else if((zc.getDateMaj() != null) 
										&& (priorityZcUpdateDate == null))
								{
									priorityZc = zc;
									hasPrivilege = true;
									priorityZcUpdateDate = zc.getDateMaj();
								}
							}
							else if((pmZone.getLienPrivilegie() != null) 
									&& (pmZone.getLienPrivilegie().equalsIgnoreCase("n"))
									&& (pmZone.getDateFermeture() == null || pmZone.getDateFermeture().getTime() > currentDateTime)
									&& (hasPrivilege == false))
							{
								if((zc.getDateMaj() != null) && (priorityZcUpdateDate != null))
								{
									if(zc.getDateMaj().after(priorityZcUpdateDate))
									{
										priorityZc = zc;
										priorityZcUpdateDate = zc.getDateMaj();
									}
								}
								if((zc.getDateMaj() != null) && (priorityZcUpdateDate == null))
								{
									priorityZc = zc;
									priorityZcUpdateDate = zc.getDateMaj();
								}
							}
						}
					}
				}
			}
			if(priorityZc != null)
			{
				firmName.setCommercialZone(fromZcToString(priorityZc));
			}
		}
	}
	
	public ZoneComm findPriorityZc(PersonneMorale personneMorale) 
	{	
		ZoneComm priorityZc = null;
		boolean hasPrivilege = false;
		Date priorityZcUpdateDate = null;
		
		if(personneMorale != null)
		{
			if((personneMorale.getPmZones() != null) && (! personneMorale.getPmZones().isEmpty()))
			{
				for(PmZone pmZone : personneMorale.getPmZones())
				{
					if(pmZone.getZoneDecoup() != null)
					{
						if(pmZone.getZoneDecoup().getClass().equals(ZoneComm.class))
						{
							ZoneComm zc = (ZoneComm)pmZone.getZoneDecoup();
							if(priorityZc == null)
							{
								priorityZc = zc;
								if((pmZone.getLienPrivilegie() != null) && (pmZone.getLienPrivilegie().equalsIgnoreCase("o")))
								{
									hasPrivilege = true;
								}
								if(zc.getDateMaj() != null) 
								{
									priorityZcUpdateDate = zc.getDateMaj();
								}
							}
							else if((pmZone.getLienPrivilegie() != null) 
									&& (pmZone.getLienPrivilegie().equalsIgnoreCase("o"))
									&& (hasPrivilege == false))
							{
								priorityZc = zc;
								hasPrivilege = true;
								if(zc.getDateMaj() != null) 
								{
									priorityZcUpdateDate = zc.getDateMaj();
								}
							}	
							else if((pmZone.getLienPrivilegie() != null) 
									&& (pmZone.getLienPrivilegie().equalsIgnoreCase("o"))
									&& (hasPrivilege == true))
							{
								if((zc.getDateMaj() != null) 
										&& (priorityZcUpdateDate != null)
										&& (zc.getDateMaj().after(priorityZcUpdateDate)))
								{
									priorityZc = zc;
									hasPrivilege = true;
									priorityZcUpdateDate = zc.getDateMaj();
								}
								else if((zc.getDateMaj() != null) 
										&& (priorityZcUpdateDate == null))
								{
									priorityZc = zc;
									hasPrivilege = true;
									priorityZcUpdateDate = zc.getDateMaj();
								}
							}
							else if((pmZone.getLienPrivilegie() != null) 
									&& (pmZone.getLienPrivilegie().equalsIgnoreCase("n"))
									&& (hasPrivilege == false))
							{
								if((zc.getDateMaj() != null) && (priorityZcUpdateDate != null))
								{
									if(zc.getDateMaj().after(priorityZcUpdateDate))
									{
										priorityZc = zc;
										priorityZcUpdateDate = zc.getDateMaj();
									}
								}
								if((zc.getDateMaj() != null) && (priorityZcUpdateDate == null))
								{
									priorityZc = zc;
									priorityZcUpdateDate = zc.getDateMaj();
								}
							}
						}
					}
				}
			}
		}
		if(priorityZc == null)
		{
			LOGGER.info("No priority ZC found");
		}
		if((priorityZc != null)
				&&	(priorityZc.getDateMaj() == null))
		{
			LOGGER.info("priorityZc.getDateMaj() == null");
		}
		return priorityZc;
	}
	
	private String fromZcToString(ZoneComm zoneCommerciale)
	{
		StringBuffer zcBuffer = new StringBuffer("");
		if(zoneCommerciale.getZc1() != null)
		{
			zcBuffer.append(zoneCommerciale.getZc1());
		}
		if(zoneCommerciale.getZc2() != null)
		{
			zcBuffer.append(zoneCommerciale.getZc2());
		}
		if(zoneCommerciale.getZc3() != null)
		{
			zcBuffer.append(zoneCommerciale.getZc3());
		}
		if(zoneCommerciale.getZc4() != null)
		{
			zcBuffer.append(zoneCommerciale.getZc4());
		}
		if(zoneCommerciale.getZc5() != null)
		{
			zcBuffer.append(zoneCommerciale.getZc5());
		}
		return zcBuffer.toString();
	}

}
