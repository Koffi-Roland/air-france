package com.airfrance.repindutf8.dto.utf;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repindutf8.entity.RefUtfDataKey;
import com.airfrance.repindutf8.entity.UtfData;
import com.airfrance.repindutf8.entity.Utf;

public final class UtfDataTransform {

	public static UtfDataDTO bo2DtoLight(final UtfData pUtfData) throws JrafDomainException {
		// instanciation du DTO
		final UtfDataDTO utfDataDTO = new UtfDataDTO();
		UtfDataTransform.bo2DtoLight(pUtfData, utfDataDTO);
		// on retourne le dto
		return utfDataDTO;
	}

	/**
	 * Transform a business object to DTO. "light method". calls bo2DtoLightImpl in
	 * a protected region so the user can override this without losing benefit of
	 * generation if attributes vary in future
	 * 
	 * @param utfData
	 *            bo
	 * @param utfDataDTO
	 *            dto
	 */
	public static void bo2DtoLight(final UtfData utfData, final UtfDataDTO utfDataDTO) {

		/* PROTECTED REGION ID(bo2DtoLight_oWasAEQ3EeeGNOeccGyf5g) ENABLED START */

		UtfDataTransform.bo2DtoLightImpl(utfData, utfDataDTO);

		/* PROTECTED REGION END */

	}

	/**
	 * Transform a business object to DTO. Implementation method
	 * 
	 * @param utfData
	 *            bo
	 * @param utfDataDTO
	 *            dto
	 */
	private static void bo2DtoLightImpl(final UtfData utfData, final UtfDataDTO utfDataDTO) {

		// simple properties
		utfDataDTO.setUtfDataId(utfData.getUtfDataId());
		utfDataDTO.setKey(utfData.getRefUtfDataKey().getScode());
		utfDataDTO.setValue(utfData.getSvalue());
		utfDataDTO.setDateCreation(utfData.getDdateCreation());
		utfDataDTO.setSiteCreation(utfData.getSsiteCreation());
		utfDataDTO.setSignatureCreation(utfData.getSsignatureCreation());
		utfDataDTO.setDateModification(utfData.getDdateModification());
		utfDataDTO.setSiteModification(utfData.getSsiteModification());
		utfDataDTO.setSignatureModification(utfData.getSsignatureModification());

	}

	/**
	 * dto -> bo for a UtfData
	 * 
	 * @param utfDataDTO
	 *            dto
	 * @return bo
	 * @throws JrafDomainException
	 *             if the DTO type is not supported
	 */
	public static UtfData dto2BoLight(final UtfDataDTO utfDataDTO) throws JrafDomainException {
		// instanciation du BO
		final UtfData utfData = new UtfData();
		UtfDataTransform.dto2BoLight(utfDataDTO, utfData);

		// on retourne le BO
		return utfData;
	}

	/**
	 * dto -> bo for a utfData calls dto2BoLightImpl in a protected region so the
	 * user can override this without losing benefit of generation if attributes
	 * vary in future
	 * 
	 * @param utfDataDTO
	 *            dto
	 * @param utfData
	 *            bo
	 */
	public static void dto2BoLight(final UtfDataDTO utfDataDTO, final UtfData utfData) {

		/* PROTECTED REGION ID(dto2BoLight_oWasAEQ3EeeGNOeccGyf5g) ENABLED START */

		UtfDataTransform.dto2BoLightImpl(utfDataDTO, utfData);

		/* PROTECTED REGION END */
	}

	/**
	 * dto -> bo implementation for a utfData
	 * 
	 * @param utfDataDTO
	 *            dto
	 * @param utfData
	 *            bo
	 */
	private static void dto2BoLightImpl(final UtfDataDTO utfDataDTO, final UtfData utfData) {

		RefUtfDataKey key = new RefUtfDataKey();
		key.setScode(utfDataDTO.getKey());
		if(utfDataDTO.getUtfDataId() != null) {
			utfData.setUtfDataId(utfDataDTO.getUtfDataId());
		}
		utfData.setRefUtfDataKey(key);
		utfData.setSvalue(utfDataDTO.getValue());
		utfData.setDdateCreation(utfDataDTO.getDateCreation());
		utfData.setSsiteCreation(utfDataDTO.getSiteCreation());
		utfData.setSsignatureCreation(utfDataDTO.getSignatureCreation());
		utfData.setDdateModification(utfDataDTO.getDateModification());
		utfData.setSsiteModification(utfDataDTO.getSiteModification());
		utfData.setSsignatureModification(utfDataDTO.getSignatureModification());
		final Utf utf = new Utf();
		utf.setUtfId(utfDataDTO.getUtfId());
		utfData.setUtf(utf);
	}

	private UtfDataTransform() {
	}
}
