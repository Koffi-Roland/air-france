package com.airfrance.repind.service.ws.internal.helpers;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.type.MediumStatusEnum;
import com.airfrance.repind.dto.adresse.adh.NormalisationSoftRequestDTO;
import com.airfrance.repind.dto.adresse.adh.NormalisationSoftResponseDTO;
import com.airfrance.repind.dto.individu.createmodifyindividual.AdressePostaleDTO;
import com.airfrance.repind.dto.individu.createmodifyindividual.CreateModifyIndividualResponseDTO;
import com.airfrance.repind.dto.individu.createmodifyindividual.PostalAddressResponseDTO;
import com.airfrance.repind.dto.individu.createmodifyindividual.SoftComputingResponseDTO;
import com.airfrance.repind.service.adresse.internal.AdresseDS;
import com.airfrance.repind.util.transformer.AddressNormalizationTransformer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AddressNormalizationHelper {

	private Log LOGGER = LogFactory.getLog(AddressNormalizationHelper.class);

	@Autowired
	private AdresseDS adresseDS;
	
	public void setAdresseDS(AdresseDS adresseDS) {
		this.adresseDS = adresseDS;
	}

	public CreateModifyIndividualResponseDTO checkNormalizedAddress(Set<AdressePostaleDTO> adressePostaleSet) throws JrafDomainException{


		CreateModifyIndividualResponseDTO response = new CreateModifyIndividualResponseDTO();
		NormalisationSoftRequestDTO adrPost = new NormalisationSoftRequestDTO();
		NormalisationSoftResponseDTO adrChecked = new NormalisationSoftResponseDTO();

		response.setSuccess(true);
		
		if(adressePostaleSet != null) {
			
			for (AdressePostaleDTO adr : adressePostaleSet) {
				// ONLY CHECKING UNFORCED ADDRESSES (FORCAGE TO N)
				if (("N").equalsIgnoreCase(adr.getIndicAdrNorm()) && adresseDS.isPaysNormalisable(adr.getCodePays())) {

					MediumStatusEnum mediumStatus = MediumStatusEnum.getEnum(adr.getStatutMedium());

					// HISTORIZED AND REMOVED ADDRESSES ARE OUT OF SCOPE
					if(mediumStatus == MediumStatusEnum.HISTORIZED || mediumStatus == MediumStatusEnum.REMOVED) {
						continue;
					}

					adrPost = AddressNormalizationTransformer.dtoTOSoftdto(adr);
					adrChecked = adresseDS.checkNormalizedAddress(adrPost,adr.getCodePays());

					//Modification de la condition du IF pour que l'on puisse utiliser 
					//le mode bouchon de la normalisation d'adresse (Jira : REPIND-422)
					if ((adrChecked.getNumError() != null && adrChecked.getNumError().equalsIgnoreCase("4")) 
							|| (adrChecked.getReturnCode1() != null && adrChecked.getReturnCode1().equals(4))){
							LOGGER.info("Mode Bouchon ON : appel à adhesion direct");

					} else if (adrChecked.getReturnCode1() == null || (adrChecked.getWsErr() != null && adrChecked.getWsErr() == -1)) {

							LOGGER.info("Mauvaise réponse de soft : appel à adhesion direct");

					} else {
						PostalAddressResponseDTO par = new PostalAddressResponseDTO();
						SoftComputingResponseDTO scr = new SoftComputingResponseDTO();

						response.setSuccess(true);

						par = AddressNormalizationTransformer.dtoTOPostal(adr);

						// UPDATE OF RETURNED PARAMETERS - SOFT
						par = AddressNormalizationTransformer.softTOPostal(adrChecked, par);
						scr = AddressNormalizationTransformer.softToError(adrChecked);
						par.setSoftComputingResponse(scr);
						if(response.getPostalAddressResponse() == null) {
							response.setPostalAddressResponse(new HashSet<PostalAddressResponseDTO>());
						}
						response.getPostalAddressResponse().add(par);
					}
				}
			}
		}
		
		return response;	
	}	
}
