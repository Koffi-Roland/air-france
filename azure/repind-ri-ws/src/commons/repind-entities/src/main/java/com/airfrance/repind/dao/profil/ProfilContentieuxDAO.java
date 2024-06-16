package com.airfrance.repind.dao.profil;

import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.repind.dao.AbstractDAO;
import com.airfrance.repind.entity.profil.ProfilContentieux;
public class ProfilContentieuxDAO extends AbstractDAO<ProfilContentieux>  implements IProfilContentieuxDAO {
    public ProfilContentieuxDAO() {
        super(ProfilContentieux.class);
    }

	@Override
	public void persist(ProfilContentieux contentious_profile)
			throws InvalidParameterException {
		// TODO Auto-generated method stub
		
	}
}
