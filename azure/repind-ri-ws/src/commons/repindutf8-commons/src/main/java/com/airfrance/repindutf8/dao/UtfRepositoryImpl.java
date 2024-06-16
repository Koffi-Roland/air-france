package com.airfrance.repindutf8.dao;

import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.repindutf8.entity.Utf;
import com.airfrance.repindutf8.service.utf.internal.UtfErrorCode;
import com.airfrance.repindutf8.service.utf.internal.UtfException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.Date;

public class UtfRepositoryImpl implements UtfRepositoryCustom {

	private static final Log LOG = LogFactory.getLog(UtfRepositoryImpl.class);

	@Autowired
	private @Lazy UtfRepository utfRepository;
	
	@Override
	public Long createUtf(final Utf utf, final String signature, final String site) throws UtfException, InvalidParameterException {
		Date date = new Date();		
		utf.setDdateCreation(date);
		utf.setDdateModification(date);
		utf.setSsignatureCreation(signature);
		utf.setSsignatureModification(signature);
		utf.setSsiteCreation(site);
		utf.setSsiteModification(site);
		utfRepository.saveAndFlush(utf);
		return utf.getUtfId();
	}

	@Override
	public void updateUtf(final Utf utf, final String signature, final String site) throws UtfException {
		
		final Utf utfDB = utfRepository.findById(utf.getUtfId()).orElseThrow(
				() -> UtfException.generateException(UtfErrorCode.THIS_UTF_ID_DOESNT_EXIST, utf.getSgin()));
		utfDB.setSgin(utf.getSgin());
		utfDB.setSsignatureModification(signature);
		utfDB.setSsiteModification(site);
		utfDB.setDdateModification(new Date());
		utfDB.setRefUtfType(utf.getRefUtfType());
		utfRepository.saveAndFlush(utfDB);
		LOG.debug("Update UTF " + utf.getUtfId());
	}
}
