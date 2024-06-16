package com.airfrance.repind.service.agency.searchagencyonmulticriteria.ds.builder;

import com.airfrance.repind.entity.adresse.Telecoms;
import com.airfrance.repind.entity.agence.Agence;
import com.airfrance.repind.entity.zone.PmZone;
import com.airfrance.repind.entity.zone.ZoneComm;
import com.airfrance.repind.service.agency.searchagencyonmulticriteria.ds.FindPriorityZcDS;
import com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AgencyBuilder {
	
	/*===============================================*/
	/*             INJECTED BEANS                    */
	/*===============================================*/
	@Autowired
	private AgencyInformationsBuilder agencyInformationsBuilder = null;
	
	@Autowired
	@Qualifier("AgencyTelecomBuilder")
	private TelecomBuilder telecomBuilder = null;
	
	@Autowired
	@Qualifier("AgencyEmailBuilder")
	private EmailBuilder emailBuilder = null;
	
	@Autowired
	@Qualifier("AgencyFindPriorityZcDS")
	private FindPriorityZcDS findPriorityZcDSBean = null;
	
	/*===============================================*/
	/*             INSTANCE VARIABLES                */
	/*===============================================*/
	private int correspondanceRate;
	
	/*===============================================*/
	/*                CONSTRUCTORS                   */
	/*===============================================*/
	public AgencyBuilder() {
		super();
	}
	
	/*===============================================*/
	/*                  ACCESSORS                    */
	/*===============================================*/
	public int getCorrespondanceRate() {
		return correspondanceRate;
	}

	public void setCorrespondanceRate(int correspondanceRate) {
		this.correspondanceRate = correspondanceRate;
	}

	/*===============================================*/
	/*               PUBLIC METHODS                  */
	/*===============================================*/
	
	/**
	 * Creates a firm from a personneMorale instance
	 */
	public AgencyDTO build(Agence agence) throws BusinessException
	{
		AgencyDTO agencyDTO = new AgencyDTO();
		handleAgencyInformations(agencyDTO, agence);
		handleTelecoms(agencyDTO, agence);
		handleEmails(agencyDTO, agence);
		handleCommercialZoneAgency(agencyDTO, agence);
		return agencyDTO;
	}
	
	/*===============================================*/
	/*               PRIVATE METHODS                 */
	/*===============================================*/
	
	/**
	 * Create FirmInformations instance and associate it to the firm
	 * @param firm
	 * @param personneMorale
	 * @throws BusinessException
	 */
	private void handleAgencyInformations(AgencyDTO agencyDTO, Agence agence) throws BusinessException
	{
		AgencyInformationsDTO agencyInformationsDTO = agencyInformationsBuilder.build(agence);
		agencyDTO.setAgencyInformationsDTO(agencyInformationsDTO);
	}
	
	/**
	 * Create Telecoms instance and associate it to the firm
	 * @param firm
	 * @param personneMorale
	 * @throws BusinessException
	 */
	private void handleTelecoms(AgencyDTO agencyDTO, Agence agence) throws BusinessException
	{
		if((agence.getTelecoms() != null) && (! agence.getTelecoms().isEmpty()))
		{
			for(Telecoms telecoms : agence.getTelecoms())
			{
				TelecomBlocDTO telecomBlocDTO = telecomBuilder.build(telecoms);
				agencyDTO.getTelecomBlocDTO().add(telecomBlocDTO);
			}
		}
	}
	
	/**
	 * Create Email instance and associate it to the firm
	 * @param firm
	 * @param personneMorale
	 * @throws BusinessException
	 */
	private void handleEmails(AgencyDTO agencyDTO, Agence agence) throws BusinessException
	{
		if((agence.getEmails() != null) && (! agence.getEmails().isEmpty()))
		{
			for(com.airfrance.repind.entity.adresse.Email emailEntity : agence.getEmails())
			{
				EmailDTO emailDTO = emailBuilder.build(emailEntity);
				agencyDTO.getEmailDTO().add(emailDTO);
			}
		}
	}

	
	private void handleCommercialZoneAgency(AgencyDTO agencyDTO, Agence agence) throws BusinessException
	{
		if(agence != null)
		{
			List<PmZone> activeZCList = findPriorityZcDSBean.returnActiveZc(agence);

			if(activeZCList != null && !activeZCList.isEmpty()) {
				for(PmZone pmZone : activeZCList) {
					ZoneComm zc = (ZoneComm)pmZone.getZoneDecoup();
					CommercialZonesDTO commercialZonesDTO = new CommercialZonesDTO();
					if(zc.getSousType() != null && !zc.getSousType().isEmpty()) {
						commercialZonesDTO.setZoneSubtype(zc.getSousType());
					}
					if(zc.getNature() != null && !zc.getNature().isEmpty()) {
						commercialZonesDTO.setNatureZone(zc.getNature());
					}
					if(pmZone.getLienPrivilegie() != null && !pmZone.getLienPrivilegie().isEmpty()) {
						commercialZonesDTO.setPrivilegedLink(pmZone.getLienPrivilegie());
					}
					if(pmZone.getDateOuverture() != null) {
						commercialZonesDTO.setLinkStartDate(pmZone.getDateOuverture());
					}
					if(pmZone.getDateFermeture() != null) {
						commercialZonesDTO.setLinkEndDate(pmZone.getDateFermeture());
					}
					if(zc.getDateOuverture() != null) {
						commercialZonesDTO.setZcStartDate(zc.getDateOuverture());
					}
					if(zc.getDateFermeture() != null) {
						commercialZonesDTO.setZcEndDate(zc.getDateFermeture());
					}
					if(zc.getZc1() != null && !zc.getZc1().isEmpty()) {
						commercialZonesDTO.setZc1(zc.getZc1());
					}
					if(zc.getZc2() != null && !zc.getZc2().isEmpty()) {
						commercialZonesDTO.setZc2(zc.getZc2());
					}
					if(zc.getZc3() != null && !zc.getZc3().isEmpty()) {
						commercialZonesDTO.setZc3(zc.getZc3());
					}
					if(zc.getZc4() != null && !zc.getZc4().isEmpty()) {
						commercialZonesDTO.setZc4(zc.getZc4());
					}
					if(zc.getZc5() != null && !zc.getZc5().isEmpty()) {
						commercialZonesDTO.setZc5(zc.getZc5());
					}
					agencyDTO.getCommercialZonesAgencyDTO().add(commercialZonesDTO);
				}
			}
		}
	}
}
