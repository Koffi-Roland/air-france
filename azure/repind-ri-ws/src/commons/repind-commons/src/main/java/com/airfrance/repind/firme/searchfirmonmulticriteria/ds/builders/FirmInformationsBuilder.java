package com.airfrance.repind.firme.searchfirmonmulticriteria.ds.builders;

import com.airfrance.repind.entity.adresse.PostalAddress;
import com.airfrance.repind.entity.firme.*;
import com.airfrance.repind.firme.searchfirmonmulticriteria.ds.FindPriorityZcDS;
import com.airfrance.repind.firme.searchfirmonmulticriteria.dto.BusinessException;
import com.airfrance.repind.firme.searchfirmonmulticriteria.dto.FirmInformationsDTO;
import com.airfrance.repind.firme.searchfirmonmulticriteria.dto.FirmNameDTO;
import com.airfrance.repind.firme.searchfirmonmulticriteria.dto.PostalAddressBlocResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FirmInformationsBuilder {
	
	/*===============================================*/
	/*             INJECTED BEANS                    */
	/*===============================================*/
	@Autowired
	private PostalAddressBuilder postalAddressBuilder = null;
	
	@Autowired
	private FindPriorityZcDS findPriorityZcDSBean = null;
	
	
	private static String NAME_TYPE_LEGAL="LEGAL";
	
	private static String NAME_TYPE_TRADE="TRADE";
	
	private static String NAME_TYPE_USUAL="USUAL";
	
	private static String NAME_TYPE_ALL="ALL";
	
	/*===============================================*/
	/*               PUBLIC METHODS                  */
	/*===============================================*/
	
	/**
	 *  build a FirmInformations instance (returned as part of the answer)
	 *   from a PersonneMorale instance (which is a persitent entity) 
	 * @throws BusinessException 
	 */
	public FirmInformationsDTO build(PersonneMorale personneMorale, String typeNameInput) throws BusinessException
	{
		FirmInformationsDTO firmInformations = new FirmInformationsDTO();
		
		//   FIRM KEY 
		if(personneMorale.getGin() != null)
		{
			firmInformations.setFirmKey(personneMorale.getGin());
		}
		
		//   FIRM STATUS 
		if(personneMorale.getStatut() != null)
		{
			firmInformations.setFirmStatus(personneMorale.getStatut());
		}
		
		//   FIRM TYPE 
		handleFirmType(personneMorale, firmInformations);
		
		//   FIRM NAMES  
		handleFirmNames(personneMorale, firmInformations, typeNameInput);
			
		//   POSTAL ADDRESSES   
		handlePostalAddresses(personneMorale, firmInformations);
		
		return firmInformations;
	}
	
	

	/*===============================================*/
	/*               PRIVATE METHODS                 */
	/*===============================================*/
	
	/**
	 * Method that determines the firm type
	 * @param personneMorale
	 * @param firmInformations
	 * @throws BusinessException 
	 */
	private void handleFirmType(PersonneMorale personneMorale, FirmInformationsDTO firmInformations) throws BusinessException
	{
		if(personneMorale.getClass().equals(Groupe.class))
		{
			firmInformations.setFirmType("G");
		}
		else if(personneMorale.getClass().equals(Entreprise.class))
		{
			firmInformations.setFirmType("E");
		}
		else if(personneMorale.getClass().equals(Etablissement.class))
		{
			firmInformations.setFirmType("T");
			if(((Etablissement) personneMorale).getSiret() != null) {
				firmInformations.setSiretNumber(((Etablissement) personneMorale).getSiret());
			}
		}
		else if(personneMorale.getClass().equals(Service.class))
		{
			firmInformations.setFirmType("S");
		}
	}
	
	/**
	 * Method that creates firmNames instances from a PersonneMorale instance
	 * @param personneMorale
	 * @param firmInformations
	 */
	private void handleFirmNames(PersonneMorale personneMorale, FirmInformationsDTO firmInformations, String typeNameInput)
	{
		FirmNameDTO firmName = new FirmNameDTO();
		if((typeNameInput==null || NAME_TYPE_ALL.equalsIgnoreCase(typeNameInput) || NAME_TYPE_LEGAL.equalsIgnoreCase(typeNameInput)) 
				&& personneMorale.getNom() != null)
		{
			firmName.setName(personneMorale.getNom());
			firmName.setNameType(NAME_TYPE_LEGAL);
		}	
		
		if(personneMorale.getSynonymes() != null && !personneMorale.getSynonymes().isEmpty()) {
			for(Synonyme synonyme : personneMorale.getSynonymes()) {
				if(synonyme.getType() != null && synonyme.getType().equals("U")
						&& synonyme.getNom() != null) {
					firmInformations.setUsualName(synonyme.getNom());
					if(NAME_TYPE_USUAL.equalsIgnoreCase(typeNameInput)){
						firmName.setName(synonyme.getNom());
						firmName.setNameType(NAME_TYPE_USUAL);
					}
				}
				else if(synonyme.getType() != null && synonyme.getType().equals("M")
						&& synonyme.getNom() != null && NAME_TYPE_TRADE.equalsIgnoreCase(typeNameInput)){
					firmName.setName(synonyme.getNom());
					firmName.setNameType(NAME_TYPE_TRADE);
				}
			}
		}
		
		firmInformations.setFirmName(firmName);
		// SET Commercial zones
		handleZC(personneMorale, firmName);	
		
		
		//firmName.setNameType(value)
		
	}
	
	/**
	 * Method that read the commercial zones of a PersonneMorale instance
	 * @param personneMorale
	 * @param firmName
	 */
	private void handleZC(PersonneMorale personneMorale, FirmNameDTO firmName)
	{
		if((personneMorale.getPmZones() != null) && (! personneMorale.getPmZones().isEmpty()))
		{
			findPriorityZcDSBean.findPriorityZc(firmName, personneMorale);
		}	
	}
	
	/**
	 * Method that obtains postal addresses from a PersonneMorale instance
	 * @param personneMorale
	 * @param firmInformations
	 */
	private void handlePostalAddresses(PersonneMorale personneMorale, FirmInformationsDTO firmInformations)
	{
		if((personneMorale.getPostalAddresses() != null) && (! personneMorale.getPostalAddresses().isEmpty()))
		{
			for(PostalAddress postalAddress : personneMorale.getPostalAddresses())
			{
				PostalAddressBlocResponseDTO postalAddressBloc  =  postalAddressBuilder.build(postalAddress, firmInformations);
				firmInformations.setPostalAddressBloc(postalAddressBloc);
				break;
			}
		}
	}
}
