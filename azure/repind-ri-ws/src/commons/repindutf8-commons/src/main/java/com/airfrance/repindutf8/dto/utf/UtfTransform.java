package com.airfrance.repindutf8.dto.utf;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repindutf8.entity.RefUtfType;
import com.airfrance.repindutf8.entity.Utf;
import com.airfrance.repindutf8.entity.UtfData;

import java.util.HashSet;
import java.util.Set;

public final class UtfTransform {

	public static UtfDTO bo2Dto(final Utf pUtf) throws JrafDomainException {
		// instanciation du DTO
		final UtfDTO utfDTO = new UtfDTO();
		UtfTransform.bo2Dto(pUtf, utfDTO);
		// on retourne le dto
		return utfDTO;
	}

	/**
	 * Transform a business object to DTO. Implementation method
	 * 
	 * @param utf
	 *            bo
	 * @param utfDTO
	 *            dto
	 * @throws JrafDomainException
	 */
	private static void bo2Dto(final Utf utf, final UtfDTO utfDTO) throws JrafDomainException {

		UtfTransform.bo2DtoLightImpl(utf, utfDTO);

		final Set<UtfDataDTO> utfDataDtos = new HashSet<>();
		if (utf.getUtfDatas() == null) {
			return;
		}
		for (final UtfData utfData : utf.getUtfDatas()) {
			utfDataDtos.add(UtfDataTransform.bo2DtoLight(utfData));
		}
		utfDTO.setUtfDataDTO(utfDataDtos);

	}

	/**
	 * bo -> dto for a utf
	 * 
	 * @param pUtf
	 *            bo
	 * @throws JrafDomainException
	 *             if the DTO type is not supported
	 * @return dto
	 */
	public static UtfDTO bo2DtoLight(final Utf pUtf) throws JrafDomainException {
		// instanciation du DTO
		final UtfDTO utfDTO = new UtfDTO();
		UtfTransform.bo2DtoLight(pUtf, utfDTO);
		// on retourne le dto
		return utfDTO;
	}

	/**
	 * Transform a business object to DTO. "light method". calls bo2DtoLightImpl in
	 * a protected region so the user can override this without losing benefit of
	 * generation if attributes vary in future
	 * 
	 * @param utf
	 *            bo
	 * @param utfDTO
	 *            dto
	 */
	public static void bo2DtoLight(final Utf utf, final UtfDTO utfDTO) {

		/* PROTECTED REGION ID(bo2DtoLight_PnEdYEQ3EeeGNOeccGyf5g) ENABLED START */

		UtfTransform.bo2DtoLightImpl(utf, utfDTO);

		/* PROTECTED REGION END */

	}

	/**
	 * Transform a business object to DTO. Implementation method
	 * 
	 * @param utf
	 *            bo
	 * @param utfDTO
	 *            dto
	 */
	private static void bo2DtoLightImpl(final Utf utf, final UtfDTO utfDTO) {

		// simple properties
		utfDTO.setUtfId(utf.getUtfId());
		utfDTO.setGin(utf.getSgin());
		utfDTO.setType(utf.getRefUtfType().getScode());
		utfDTO.setDateCreation(utf.getDdateCreation());
		utfDTO.setSiteCreation(utf.getSsiteCreation());
		utfDTO.setSignatureCreation(utf.getSsignatureCreation());
		utfDTO.setDateModification(utf.getDdateModification());
		utfDTO.setSiteModification(utf.getSsiteModification());
		utfDTO.setSignatureModification(utf.getSsignatureModification());

	}

	/**
	 * dto -> bo for a Utf
	 * 
	 * @param utfDTO
	 *            dto
	 * @return bo
	 * @throws JrafDomainException
	 *             if the DTO type is not supported
	 */
	public static Utf dto2Bo(final UtfDTO utfDTO) throws JrafDomainException {
		// instanciation du BO
		final Utf utf = new Utf();
		UtfTransform.dto2Bo(utfDTO, utf);

		// on retourne le BO
		return utf;
	}

	/**
	 * dto -> bo implementation for a utf
	 * 
	 * @param utfDTO
	 *            dto
	 * @param utf
	 *            bo
	 * @throws JrafDomainException
	 */
	private static void dto2Bo(final UtfDTO utfDTO, final Utf utf) throws JrafDomainException {

		// property of UtfDTO

		UtfTransform.dto2BoLightImpl(utfDTO, utf);
		final Set<UtfData> utfData = new HashSet<>();
		if (utfDTO.getUtfDataDTO() == null) {
			return;
		}
		for (final UtfDataDTO utfDto : utfDTO.getUtfDataDTO()) {
			utfData.add(UtfDataTransform.dto2BoLight(utfDto));
		}
		utf.setUtfDatas(utfData);

	}

	/**
	 * dto -> bo for a Utf
	 * 
	 * @param utfDTO
	 *            dto
	 * @return bo
	 * @throws JrafDomainException
	 *             if the DTO type is not supported
	 */
	public static Utf dto2BoLight(final UtfDTO utfDTO) throws JrafDomainException {
		// instanciation du BO
		final Utf utf = new Utf();
		UtfTransform.dto2BoLight(utfDTO, utf);

		// on retourne le BO
		return utf;
	}

	/**
	 * dto -> bo for a utf calls dto2BoLightImpl in a protected region so the user
	 * can override this without losing benefit of generation if attributes vary in
	 * future
	 * 
	 * @param utfDTO
	 *            dto
	 * @param utf
	 *            bo
	 */
	public static void dto2BoLight(final UtfDTO utfDTO, final Utf utf) {

		/* PROTECTED REGION ID(dto2BoLight_PnEdYEQ3EeeGNOeccGyf5g) ENABLED START */

		UtfTransform.dto2BoLightImpl(utfDTO, utf);

		/* PROTECTED REGION END */
	}

	/**
	 * dto -> bo implementation for a utf
	 * 
	 * @param utfDTO
	 *            dto
	 * @param utf
	 *            bo
	 */
	private static void dto2BoLightImpl(final UtfDTO utfDTO, final Utf utf) {

		// property of UtfDTO
		utf.setUtfId(utfDTO.getUtfId());
		utf.setSgin(utfDTO.getGin());
		RefUtfType refUtfType = new RefUtfType();
		refUtfType.setScode(utfDTO.getType());
		utf.setRefUtfType(refUtfType);
		utf.setDdateCreation(utfDTO.getDateCreation());
		utf.setSsiteCreation(utfDTO.getSiteCreation());
		utf.setSsignatureCreation(utfDTO.getSignatureCreation());
		utf.setDdateModification(utfDTO.getDateModification());
		utf.setSsiteModification(utfDTO.getSiteModification());
		utf.setSsignatureModification(utfDTO.getSignatureModification());

	}

	/**
	 * private constructor
	 */
	private UtfTransform() {
	}

	/* PROTECTED REGION ID(_PnEdYEQ3EeeGNOeccGyf5g u m - Tr) ENABLED START */
	// add your custom methods here if necessary
	/* PROTECTED REGION END */
}
