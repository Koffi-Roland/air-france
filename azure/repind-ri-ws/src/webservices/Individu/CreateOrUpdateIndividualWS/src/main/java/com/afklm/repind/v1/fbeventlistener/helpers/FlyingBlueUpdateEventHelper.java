package com.afklm.repind.v1.fbeventlistener.helpers;

import com.afklm.repind.utils.FbLevelDetailTypeEnum;
import com.afklm.soa.stubs.w001815.v2.xsd1.CurrentTierAttribute;
import com.afklm.soa.stubs.w001815.v2.xsd1.FBLevelStructure;
import com.afklm.soa.stubs.w001815.v2.xsd1.FBNContractUpdateEvent;
import com.airfrance.repind.dto.individu.ContractDataDTO;
import com.airfrance.repind.dto.individu.ContractV2DTO;
import com.airfrance.repind.dto.ws.RequestorDTO;
import com.airfrance.repind.dto.ws.createupdaterolecontract.v1.ContractRequestDTO;
import com.airfrance.repind.dto.ws.createupdaterolecontract.v1.CreateUpdateRoleContractRequestDTO;
import com.airfrance.repind.service.individu.internal.IndividuDS;
import com.airfrance.repind.util.SicStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 
 * @author t838155
 *
 */
@Service("flyingBlueUpdateEventHelper")
public class FlyingBlueUpdateEventHelper {

	@Autowired
	protected IndividuDS individuDS;



	/**
	 * Transform input data
	 * @param contract {@link FBNContractUpdateEvent}
	 * @return {@link CreateUpdateRoleContractRequestDTO}
	 */
	public CreateUpdateRoleContractRequestDTO transformFBNewContractUpdateEventToRequest(FBNContractUpdateEvent contract, String site,
			String signature){
		
		CreateUpdateRoleContractRequestDTO result = new CreateUpdateRoleContractRequestDTO();
		result.setGin(SicStringUtils.addingZeroToTheLeft(String.valueOf(contract.getIndividualBlock().getGin()), 12));

		ContractRequestDTO contractRequestDTO = new ContractRequestDTO();
		ContractV2DTO contractV2 = new ContractV2DTO();
		contractV2.setCompanyCode("AF");
		contractV2.setContractNumber(SicStringUtils.addingZeroToTheLeft(String.valueOf(contract.getIndividualBlock().getCin()), 12));
		
		
		// REPIND-1312 : Switch to FBN listenning in order to keep up to date ROLE_CONTRACT 
		switch(contract.getContractBlock().getContractStatus()){
		case "V":		// Valid
			contractV2.setContractStatus("C");		// Confirmé 
			break;
		case "C":		// Cancelled
			contractV2.setContractStatus("A");		// Annulé
			break;
		case "S":		// Suspended
			contractV2.setContractStatus("S");		// Suspendu 
		}

		contractV2.setContractType("C");
		contractV2.setProductType("FP");
		
		// Where is the StartValidityDate ?
		// Cissé Modou : Il y a pas actuellement ce champ mais cela peut se calculer via l’identification (comme le fb level est une combinaison du tier et des eventuels attributes)
		if (contract.getFbIdentificationBlock() != null) {
			
			// Pour la ‘calculer’ donc, prendre la start date du tribe (tier ou attribut de rang 1 dans la fb level structure)
			for (FBLevelStructure fbls : contract.getFbIdentificationBlock().getFbLevelStructure()) {
			
				// contract. getFbIdentificationBlock ().getFbLevelStrucure ()[fblevelRanking = 1] et il y aura 2 cas possibles :
				if (fbls.getFbLevelDetailRanking() == 1) {

				    // fblevelType = ‘TIER’ alors start validity date = contract.getTierAndSubtierBlock().getCurrentTierCharacteristics().getCurrentStartValidityDate()
					if (FbLevelDetailTypeEnum.TIER.value().equalsIgnoreCase(fbls.getFbLevelDetailType())) {
						
						if (contract.getTierAndSubtierBlock() != null && contract.getTierAndSubtierBlock().getCurrentTierCharacteristics() != null) {
							contractV2.setValidityStartDate(contract.getTierAndSubtierBlock().getCurrentTierCharacteristics().getCurrentStartValidityDate());
						}

					} else if (FbLevelDetailTypeEnum.ATTRIBUTE.value().equalsIgnoreCase(fbls.getFbLevelDetailType())) {
						
						for (CurrentTierAttribute ta : contract.getAttributesBlock().getCurrentTierAttribute()) {
						
							if (fbls.getFbLevelDetailValue().equalsIgnoreCase(ta.getCurrentAttributeCode())) {
							
								contractV2.setValidityStartDate(ta.getCurrentAttributeStartValidityDate());
							}
						}
					}
					// Pareil que pour le fb level code ci-après, on envisage de l’inclure dans la prochaine version.
				}
			}
		}
		
		contractV2.setValidityEndDate(contract.getFbIdentificationBlock().getFbLevelEndValidityDate());

		contractRequestDTO.setContract(contractV2);
		result.setContractRequest(contractRequestDTO);

		List<ContractDataDTO> contractData = result.getContractRequest().getContractData();
		
		//set tierLevel and memberType
		//set signature
		RequestorDTO requestor = new RequestorDTO();
		requestor.setSite(site);
		requestor.setSignature(signature);
		
		result.setRequestor(requestor);



		return result;
	}

}
