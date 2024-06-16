package com.airfrance.repind.dao.profil;

import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.repind.entity.profil.ProfilContentieux;

public interface IProfilContentieuxDAO {
	
	void persist(ProfilContentieux contentious_profile) throws InvalidParameterException;
}
