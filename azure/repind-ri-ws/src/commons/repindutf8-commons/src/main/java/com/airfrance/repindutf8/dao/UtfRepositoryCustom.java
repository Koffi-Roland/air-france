package com.airfrance.repindutf8.dao;

import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.repindutf8.service.utf.internal.UtfException;
import com.airfrance.repindutf8.entity.Utf;

public interface UtfRepositoryCustom {
	public Long createUtf(final Utf utf, final String signature, final String site) throws UtfException, InvalidParameterException;
	public void updateUtf(final Utf utf, final String signature, final String site) throws UtfException;
}
