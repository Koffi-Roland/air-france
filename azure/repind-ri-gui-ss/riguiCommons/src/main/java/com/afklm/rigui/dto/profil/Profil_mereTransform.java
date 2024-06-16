package com.afklm.rigui.dto.profil;

/*PROTECTED REGION ID(_S9GWwDUfEeCq6pHdxM8RnQ i - Tr) ENABLED START*/

// add not generated imports here

import com.afklm.rigui.exception.jraf.JrafDomainException;
import com.afklm.rigui.entity.profil.Profil_mere;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashSet;
import java.util.Set;

/*PROTECTED REGION END*/

/**
 * <p>Title : Profil_mereTransform.java</p>
 * transformation bo <-> dto pour un(e) Profil_mere
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class Profil_mereTransform {

    /*PROTECTED REGION ID(_S9GWwDUfEeCq6pHdxM8RnQ u var - Tr) ENABLED START*/
	private static Log LOGGER  = LogFactory.getLog(Profil_mereTransform.class);
	/*PROTECTED REGION END*/

	/**
	 * private constructor
	 */
	private Profil_mereTransform() {
	}
	/**
	 * dto -> bo for a Profil_mere
	 * @param profil_mereDTO dto
	 * @return bo
	 * @throws JrafDomainException if the DTO type is not supported
	 */
	public static Profil_mere dto2BoLight(Profil_mereDTO profil_mereDTO) throws JrafDomainException {
		// instanciation du BO
		Profil_mere profil_mere = new Profil_mere();
		dto2BoLight(profil_mereDTO, profil_mere);

		// on retourne le BO
		return profil_mere;
	}

	/**
	 * dto -> bo for a profil_mere
	 * calls dto2BoLightImpl in a protected region so the user can override this without
	 * losing benefit of generation if attributes vary in future
	 * @param profil_mereDTO dto
	 * @param profil_mere bo
	 */
	public static void dto2BoLight(Profil_mereDTO profil_mereDTO, Profil_mere profil_mere) {

		/*PROTECTED REGION ID(dto2BoLight_S9GWwDUfEeCq6pHdxM8RnQ) ENABLED START*/

		dto2BoLightImpl(profil_mereDTO,profil_mere);

		/*PROTECTED REGION END*/
	}

	/**
	 * dto -> bo implementation for a profil_mere
	 * @param profil_mereDTO dto
	 * @param profil_mere bo
	 */
	private static void dto2BoLightImpl(Profil_mereDTO profil_mereDTO, Profil_mere profil_mere){

		// property of Profil_mereDTO
		profil_mere.setIcle_prf(profil_mereDTO.getIcle_prf());
		profil_mere.setSgin_pm(profil_mereDTO.getSgin_pm());
		profil_mere.setIcle_role(profil_mereDTO.getIcle_role());
		profil_mere.setIcle_banq(profil_mereDTO.getIcle_banq());
		profil_mere.setIcle_fact(profil_mereDTO.getIcle_fact());
		profil_mere.setIcle_financ(profil_mereDTO.getIcle_financ());
		profil_mere.setSgin_ind(profil_mereDTO.getSgin_ind());
		profil_mere.setStype(profil_mereDTO.getStype());

	}

	/**
	 * bo -> dto for a profil_mere
	 * @param pProfil_mere bo
	 * @throws JrafDomainException if the DTO type is not supported
	 * @return dto
	 */
	public static Profil_mereDTO bo2DtoLight(Profil_mere pProfil_mere) throws JrafDomainException {
		// instanciation du DTO
		Profil_mereDTO profil_mereDTO = new Profil_mereDTO();
		bo2DtoLight(pProfil_mere, profil_mereDTO);
		// on retourne le dto
		return profil_mereDTO;
	}


	/**
	 * Transform a business object to DTO.
	 * "light method".
	 * calls bo2DtoLightImpl in a protected region so the user can override this without
	 * losing benefit of generation if attributes vary in future
	 * @param profil_mere bo
	 * @param profil_mereDTO dto
	 */
	public static void bo2DtoLight(
			Profil_mere profil_mere,
			Profil_mereDTO profil_mereDTO) {

		/*PROTECTED REGION ID(bo2DtoLight_S9GWwDUfEeCq6pHdxM8RnQ) ENABLED START*/

		bo2DtoLightImpl(profil_mere, profil_mereDTO);

		/*PROTECTED REGION END*/

	}

	/**
	 * Transform a business object to DTO. Implementation method
	 * @param profil_mere bo
	 * @param profil_mereDTO dto
	 */
	private static void bo2DtoLightImpl(Profil_mere profil_mere,
			Profil_mereDTO profil_mereDTO){


		// simple properties
		profil_mereDTO.setIcle_prf(profil_mere.getIcle_prf());
		profil_mereDTO.setSgin_pm(profil_mere.getSgin_pm());
		profil_mereDTO.setIcle_role(profil_mere.getIcle_role());
		profil_mereDTO.setIcle_banq(profil_mere.getIcle_banq());
		profil_mereDTO.setIcle_fact(profil_mere.getIcle_fact());
		profil_mereDTO.setIcle_financ(profil_mere.getIcle_financ());
		profil_mereDTO.setSgin_ind(profil_mere.getSgin_ind());
		profil_mereDTO.setStype(profil_mere.getStype());

	}

	/*PROTECTED REGION ID(_S9GWwDUfEeCq6pHdxM8RnQ u m - Tr) ENABLED START*/
	/**
	 * Ask both method : dto2BoLight & dto2BoLink
	 * @param profil_mereDTO
	 * @param profil_mere
	 */
	public static void dto2Bo(Profil_mereDTO profil_mereDTO, Profil_mere profil_mere) {
		dto2BoLight(profil_mereDTO, profil_mere);
		dto2BoLink(profil_mereDTO, profil_mere);
	}

	/**
	 * Ask both method : bo2DtoLight & bo2DtoLink
	 * @param profil_mere
	 * @param profil_mereDTO
	 */
	public static void bo2Dto(Profil_mere profil_mere, Profil_mereDTO profil_mereDTO) {
		bo2DtoLight(profil_mere, profil_mereDTO);
		bo2DtoLink(profil_mere, profil_mereDTO);
	}

	/**
	 * @param profil_mere
	 * @param profil_mereDTO
	 */
	public static void bo2DtoLink(Profil_mere profil_mere, Profil_mereDTO profil_mereDTO) {

		try {
			if(profil_mere.getProfilDemarchage() != null) {
				profil_mereDTO.setProfilDemarcharge(ProfilDemarchageTransform.bo2DtoLight(profil_mere.getProfilDemarchage()));
			}
		} catch (JrafDomainException e) {
			if(LOGGER.isErrorEnabled()) {
				LOGGER.error(e);
			}
		}
		
		try {
			if(profil_mere.getProfilContentieux() != null) {
				profil_mereDTO.setProfilContentieux(ProfilContentieuxTransform.bo2DtoLight(profil_mere.getProfilContentieux()));
			}
		} catch (JrafDomainException e) {
			if(LOGGER.isErrorEnabled()) {
				LOGGER.error(e);
			}
		}

		// Lien avec profil_af 
		if(profil_mere.getProfil_af() != null)
		{
			try
			{
				profil_mereDTO.setProfil_afdto(Profil_afTransform.bo2DtoLight(profil_mere.getProfil_af()));
			}
			catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}


	}

	/**
	 * @param profil_mereDTO
	 * @param profil_mere
	 */
	public static void dto2BoLink(Profil_mereDTO profil_mereDTO,
			Profil_mere profil_mere) {
		try {
			if (profil_mereDTO.getProfilDemarcharge() != null) {

				profil_mere.setProfilDemarchage(ProfilDemarchageTransform
						.dto2BoLight(profil_mereDTO.getProfilDemarcharge()));

			}
		} catch (JrafDomainException e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(e);
			}
		}
		
		if (profil_mereDTO.getProfilContentieux() != null) {

			profil_mere.setProfilContentieux(ProfilContentieuxTransform
					.dto2BoLight(profil_mereDTO.getProfilContentieux()));

		}

		// Lien avec Profil AF
		if (profil_mereDTO.getProfil_afdto()!= null) {
			try 
			{
				profil_mere.setProfil_af(Profil_afTransform.dto2BoLight(profil_mereDTO.getProfil_afdto()));
				profil_mere.getProfil_af().setProfil_mere(profil_mere);
			} catch (JrafDomainException e) {
				if (LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}

	}


	/**
	 * @param listProfil_mere
	 * @return
	 * @throws JrafDomainException
	 */
	public static Set<Profil_mereDTO> bo2Dto(Set<Profil_mere> listProfil_meres) throws JrafDomainException {
		if(listProfil_meres != null) {
			Set<Profil_mereDTO> listProfil_meresDTO = new HashSet<Profil_mereDTO>();
			for(Profil_mere profil_mere : listProfil_meres) {
				Profil_mereDTO profil_mereDTO = new Profil_mereDTO();
				bo2Dto(profil_mere, profil_mereDTO);
				listProfil_meresDTO.add(profil_mereDTO);
			}
			return listProfil_meresDTO;
		} else {
			return null;
		}
	}

	public static Set<Profil_mereDTO> bo2DtoLight(Set<Profil_mere> listProfil_meres) throws JrafDomainException {

		if (listProfil_meres != null) {

			Set<Profil_mereDTO> listProfil_meresDTO = new HashSet<Profil_mereDTO>();
			for (Profil_mere profil_mere : listProfil_meres) {

				Profil_mereDTO profil_mereDTO = new Profil_mereDTO();
				bo2DtoLight(profil_mere, profil_mereDTO);
				listProfil_meresDTO.add(profil_mereDTO);
			}
			return listProfil_meresDTO;

		} else {

			return null;
		}
	}

	/**
	 * @param listProfil_meresDTO
	 * @return
	 * @throws JrafDomainException
	 */
	public static Set<Profil_mere> dto2Bo(Set<Profil_mereDTO> listProfil_meresDTO) throws JrafDomainException {
		if(listProfil_meresDTO != null) {
			Set<Profil_mere> listProfil_meres = new HashSet<Profil_mere>();
			for(Profil_mereDTO profil_meredto : listProfil_meresDTO) {
				Profil_mere profil_mere = new Profil_mere();


				dto2Bo(profil_meredto, profil_mere);
				listProfil_meres.add(profil_mere);
			}
			return listProfil_meres;
		} else {
			return null;
		}
	}
	
	
	
	/*PROTECTED REGION END*/
	
}

