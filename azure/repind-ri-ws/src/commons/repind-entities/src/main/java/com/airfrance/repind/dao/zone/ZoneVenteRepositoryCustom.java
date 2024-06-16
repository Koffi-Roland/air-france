package com.airfrance.repind.dao.zone;

import com.airfrance.repind.entity.zone.ZoneVente;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface ZoneVenteRepositoryCustom {

	public String getZvAlphaForCity(@NotNull final String city, @NotNull final String countryCode);
	
	public List<Long> getHeirarchyGins(ZoneVente zoneVente);
}
