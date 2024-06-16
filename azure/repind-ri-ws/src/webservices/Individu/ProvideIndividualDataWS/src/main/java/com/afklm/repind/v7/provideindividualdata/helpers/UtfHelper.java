package com.afklm.repind.v7.provideindividualdata.helpers;

import com.afklm.soa.stubs.w000418.v7.response.UtfResponse;
import com.afklm.soa.stubs.w000418.v7.siccommontype.Signature;
import com.afklm.soa.stubs.w000418.v7.sicindividutype.Utf;
import com.afklm.soa.stubs.w000418.v7.sicindividutype.UtfData;
import com.afklm.soa.stubs.w000418.v7.sicindividutype.UtfDatas;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repindutf8.dto.utf.UtfDTO;
import com.airfrance.repindutf8.dto.utf.UtfDataDTO;
import com.airfrance.repindutf8.service.utf.internal.UtfDS;
import com.airfrance.repindutf8.service.utf.internal.UtfException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component("UtfHelper-ProvideV7")
public class UtfHelper {

	@Autowired
	private UtfDS utfDS;

	public UtfDS getUtfDS() {
		return utfDS;
	}

	public void setUtfDS(UtfDS utfDS) {
		this.utfDS = utfDS;
	}

	public UtfResponse getByGin(final String gin) throws JrafDomainException, UtfException {

		final UtfResponse utfResponse = new UtfResponse();
		final List<UtfDTO> listUtfDto = utfDS.checkListValidity(utfDS.findByGin(gin), gin, true);
		if (listUtfDto != null) {
			for (final UtfDTO utfDto : listUtfDto) {
				final Utf utf = new Utf();
				utf.setId(utfDto.getUtfId().toString());
				utf.setType(utfDto.getType());
				utf.getSignature().addAll(getSignature(utfDto));
				utf.setUtfDatas(getUtfDatas(utfDto));
				utfResponse.getUtf().add(utf);
			}
		}
		return utfResponse.getUtf().size() == 0 ? null : utfResponse;
	}

	private List<Signature> getSignature(final UtfDTO utfDto) {
		if (utfDto == null) {
			return null;
		}
		final List<Signature> signatures = new ArrayList<>();
		final Signature signatureCreation = new Signature();
		final Signature signatureModification = new Signature();

		signatureCreation.setDate(utfDto.getDateCreation());
		signatureCreation.setSignature(utfDto.getSignatureCreation());
		signatureCreation.setSignatureType("C");
		signatureCreation.setSignatureSite(utfDto.getSiteCreation());

		signatureModification.setDate(utfDto.getDateModification());
		signatureModification.setSignature(utfDto.getSignatureModification());
		signatureModification.setSignatureType("M");
		signatureModification.setSignatureSite(utfDto.getSiteModification());

		signatures.add(signatureCreation);
		signatures.add(signatureModification);
		return signatures;
	}

	private UtfDatas getUtfDatas(final UtfDTO utfDto) throws UtfException {
		if (utfDto == null || utfDto.getUtfDataDTO() == null) {
			return null;
		}

		final UtfDatas utfDatas = new UtfDatas();
		final Set<UtfDataDTO> setUtfDataDTO = utfDto.getUtfDataDTO();

		for (final UtfDataDTO utfDataDto : setUtfDataDTO) {
			final UtfData utfData = new UtfData();
			utfData.setKey(utfDataDto.getKey());
			utfData.setValue(utfDataDto.getValue());
			utfDatas.getUtfData().add(utfData);
		}

		return utfDatas.getUtfData().size() == 0 ? null : utfDatas;
	}
}
