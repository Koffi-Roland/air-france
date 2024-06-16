package com.airfrance.repindutf8.dao;

import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.repindutf8.entity.UtfData;
import com.airfrance.repindutf8.service.utf.internal.UtfException;

public interface UtfDataRepositoryCustom {
	public long createUtfData(final UtfData utfData, final String signature, final String site);
	public void update(final UtfData utfData, final String gin, final String signature, final String site)
			throws UtfException, InvalidParameterException;
}
