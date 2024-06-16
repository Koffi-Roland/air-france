package com.airfrance.repindutf8.dao;

import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.repindutf8.entity.UtfData;
import com.airfrance.repindutf8.service.utf.internal.UtfErrorCode;
import com.airfrance.repindutf8.service.utf.internal.UtfException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.Date;

public class UtfDataRepositoryImpl implements UtfDataRepositoryCustom {

	private static final Log LOG = LogFactory.getLog(UtfDataRepositoryImpl.class);
	
	@Autowired
	private @Lazy UtfDataRepository utfDataRepository;

	@Override
	public long createUtfData(final UtfData utfData, final String signature, final String site) {
		Date date = new Date();
		utfData.setDdateCreation(date);
		utfData.setDdateModification(date);
		utfData.setSsignatureCreation(signature);
		utfData.setSsignatureModification(signature);
		utfData.setSsiteCreation(site);
		utfData.setSsiteModification(site);
		utfDataRepository.saveAndFlush(utfData);
		LOG.debug("UTFDATA - create utfdataid: " + utfData.getUtfDataId());
		return utfData.getUtfDataId();
	}

	@Override
	public void update(final UtfData utfData, final String gin, final String signature, final String site)
			throws UtfException, InvalidParameterException {
		if (utfData == null) {
			throw new InvalidParameterException("UtfData cannot be null");
		}
		final UtfData utfDataDB = utfDataRepository.findById(utfData.getUtfDataId()).orElseThrow(() -> UtfException.generateException(UtfErrorCode.THIS_UTF_DATA_ID_DOESNT_EXIST, gin));
		utfDataDB.setSvalue(utfData.getSvalue());
		utfDataDB.setDdateModification(new Date());
		utfDataDB.setSsignatureModification(signature);
		utfDataDB.setSsiteModification(site);
		utfDataRepository.saveAndFlush(utfDataDB);
		LOG.debug("UTFDATA - update utfDataId " + utfData.getUtfDataId());
	}
}
