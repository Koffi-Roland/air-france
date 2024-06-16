package com.airfrance.repind.dao.adresse;

import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.repind.entity.adresse.Email;

public interface EmailRepositoryCustom {

	/**
	 * invalidOnEmail
	 *
	 * @param bo
	 *            email in Email
	 * @throws JrafDaoException
	 * @see com.airfrance.repind.dao.adresse.EmailDAO#invalidOnEmail(Email email)
	 */
	public void invalidOnEmail(Email email) throws JrafDaoException;
	
	public boolean isGinEmailExist(String gin, String email);

}
