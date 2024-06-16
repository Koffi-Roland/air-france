package com.airfrance.repind.firme.searchfirmonmulticriteria.ds.builders;

import com.airfrance.repind.entity.adresse.Email;
import com.airfrance.repind.entity.adresse.Telecoms;
import com.airfrance.repind.entity.firme.PersonneMorale;
import com.airfrance.repind.entity.zone.ZoneComm;
import com.airfrance.repind.firme.searchfirmonmulticriteria.ds.FindPriorityZcDS;
import com.airfrance.repind.firme.searchfirmonmulticriteria.dto.*;
import com.airfrance.repind.transversal.identifycustomercrossreferential.dto.CommercialZonesCorporateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FirmBuilder {
	
	/*===============================================*/
	/*             INJECTED BEANS                    */
	/*===============================================*/
	@Autowired
	private FirmInformationsBuilder firmInformationsBuilder = null;
	
	@Autowired
	private TelecomBuilder telecomBuilder = null;
	
	@Autowired
	private EmailBuilder emailBuilder = null;
	
	@Autowired
	private FindPriorityZcDS findPriorityZcDSBean = null;
		
	/*===============================================*/
	/*             INSTANCE VARIABLES                */
	/*===============================================*/
	private int correspondanceRate;
	
	/*===============================================*/
	/*                CONSTRUCTORS                   */
	/*===============================================*/
	public FirmBuilder() {
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
	public FirmDTO build(PersonneMorale personneMorale, String typeNameInput) throws BusinessException
	{
		FirmDTO firm = new FirmDTO();
		handleFirmInformations(firm, personneMorale, typeNameInput);
		handleTelecoms(firm, personneMorale);
		handleEmails(firm, personneMorale);
		handleCommercialZoneFirm(firm, personneMorale);
		
		return firm;
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
	private void handleFirmInformations(FirmDTO firm, PersonneMorale personneMorale, String typeNameInput) throws BusinessException
	{
		FirmInformationsDTO firmInformations = firmInformationsBuilder.build(personneMorale, typeNameInput);
		firm.setFirmInformations(firmInformations);
	}
	
	/**
	 * Create Telecoms instance and associate it to the firm
	 * @param firm
	 * @param personneMorale
	 * @throws BusinessException
	 */
	private void handleTelecoms(FirmDTO firm, PersonneMorale personneMorale) throws BusinessException
	{
		if((personneMorale.getTelecoms() != null) && (! personneMorale.getTelecoms().isEmpty()))
		{
			for(Telecoms telecoms : personneMorale.getTelecoms())
			{
				TelecomDTO telecom = telecomBuilder.build(telecoms);
				firm.getTelecoms().add(telecom);
			}
		}
	}
	
	/**
	 * Create Email instance and associate it to the firm
	 * @param firm
	 * @param personneMorale
	 * @throws BusinessException
	 */
	private void handleEmails(FirmDTO firm, PersonneMorale personneMorale) throws BusinessException
	{
		if((personneMorale.getEmails() != null) && (! personneMorale.getEmails().isEmpty()))
		{
			for(Email emailEntity : personneMorale.getEmails())
			{
				EmailDTO email = emailBuilder.build(emailEntity);
				firm.getEmail().add(email);
			}
		}
	}
	
	private void handleCommercialZoneFirm(FirmDTO firm, PersonneMorale personneMorale) throws BusinessException
	{
		if(personneMorale.getPmZones() != null)
		{
			ZoneComm zc = findPriorityZcDSBean.findPriorityZc(personneMorale);

			if(zc != null) {
				CommercialZonesCorporateDTO commercialZonesCorporateDTO = new CommercialZonesCorporateDTO();
				if(zc.getSousType() != null && !zc.getSousType().isEmpty()) {
					commercialZonesCorporateDTO.setZoneSubtype(zc.getSousType());
				}
				if(zc.getNature() != null && !zc.getNature().isEmpty()) {
					commercialZonesCorporateDTO.setNatureZone(zc.getNature());
				}
				if(zc.getZc1() != null && !zc.getZc1().isEmpty()) {
					commercialZonesCorporateDTO.setZc1(zc.getZc1());
				}
				if(zc.getZc2() != null && !zc.getZc2().isEmpty()) {
					commercialZonesCorporateDTO.setZc2(zc.getZc2());
				}
				if(zc.getZc3() != null && !zc.getZc3().isEmpty()) {
					commercialZonesCorporateDTO.setZc3(zc.getZc3());
				}
				if(zc.getZc4() != null && !zc.getZc4().isEmpty()) {
					commercialZonesCorporateDTO.setZc4(zc.getZc4());
				}
				if(zc.getZc5() != null && !zc.getZc5().isEmpty()) {
					commercialZonesCorporateDTO.setZc5(zc.getZc5());
				}
				firm.setCommercialZonesCorporate(commercialZonesCorporateDTO);
			}
		}
	}
}
