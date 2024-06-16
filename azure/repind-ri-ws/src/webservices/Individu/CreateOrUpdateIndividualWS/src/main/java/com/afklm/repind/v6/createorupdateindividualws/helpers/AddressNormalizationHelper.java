package com.afklm.repind.v6.createorupdateindividualws.helpers;

import com.afklm.repind.v6.createorupdateindividualws.transformers.IndividuRequestTransformV6;
import com.afklm.soa.stubs.w000442.v6.data.CreateUpdateIndividualResponse;
import com.afklm.soa.stubs.w000442.v6.response.PostalAddressResponse;
import com.afklm.soa.stubs.w000442.v6.softcomputingtype.SoftComputingResponse;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.type.MediumStatusEnum;
import com.airfrance.repind.dto.adresse.adh.NormalisationSoftRequestDTO;
import com.airfrance.repind.dto.adresse.adh.NormalisationSoftResponseDTO;
import com.airfrance.repind.dto.individu.createmodifyindividual.AdressePostaleDTO;
import com.airfrance.repind.service.adresse.internal.AdresseDS;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service("addressNormalizationHelperV6")
@Slf4j
public class AddressNormalizationHelper {

	@Autowired
	private AdresseDS adresseDS;
			
	public CreateUpdateIndividualResponse checkNormalizedAddress(Set<AdressePostaleDTO> adressePostaleSet) throws JrafDomainException{
		
		
		CreateUpdateIndividualResponse response = new CreateUpdateIndividualResponse();
		NormalisationSoftRequestDTO adrPost = new NormalisationSoftRequestDTO();
		NormalisationSoftResponseDTO adrChecked = new NormalisationSoftResponseDTO();

		response.setSuccess(true);

		for (AdressePostaleDTO adr : adressePostaleSet) {
		// ONLY CHECKING UNFORCED ADDRESSES (FORCAGE TO N)
			if (("N").equalsIgnoreCase(adr.getIndicAdrNorm()) && adresseDS.isPaysNormalisable(adr.getCodePays())) {
	
				MediumStatusEnum mediumStatus = MediumStatusEnum.getEnum(adr.getStatutMedium());
				
				// HISTORIZED AND REMOVED ADDRESSES ARE OUT OF SCOPE
				if(mediumStatus == MediumStatusEnum.HISTORIZED || mediumStatus == MediumStatusEnum.REMOVED) {
					continue;
				}
				
				adrPost = IndividuRequestTransformV6.dtoTOSoftdto(adr);
				adrChecked = adresseDS.checkNormalizedAddress(adrPost,adr.getCodePays());
				
				//Modification de la condition du IF pour que l'on puisse utiliser 
				//le mode bouchon de la normalisation d'adresse (Jira : REPIND-422)				
				if ((adrChecked.getNumError() != null && adrChecked.getNumError().equalsIgnoreCase("4")) 
						|| (adrChecked.getReturnCode1() != null && adrChecked.getReturnCode1().equals(4))){
					
						log.info("Mode Bouchon ON : appel à adhesion direct");
					
				} else if (adrChecked.getReturnCode1() == null || (adrChecked.getWsErr() != null && adrChecked.getWsErr() == -1)) {
					
						log.info("Mauvaise réponse de soft : appel à adhesion direct");
					
				} else {
						
						boolean isAddressOk = false;
		
						PostalAddressResponse par = new PostalAddressResponse();
						SoftComputingResponse scr = new SoftComputingResponse();
		
						// IF RETURN CODE IS 0 OR 1 AND FIELDS ARE NOT MODIFIED THEN POSTAL ADDRESS OK
		
						if (adrChecked.getReturnCode1() != null && (adrChecked.getReturnCode1() == 0))
							isAddressOk = true;
						
						if (adrChecked.getReturnCode1() != null && (adrChecked.getReturnCode1() == 1)) {
						
						if (((adrChecked.getCountry() == null || ("").equals(adrChecked
								.getCountry())) && (adr.getCodePays() == null || ("")
								.equals(adr.getCodePays())))
								|| (adrChecked.getCountry() != null && adrChecked
										.getCountry().equalsIgnoreCase(
												adr.getCodePays()))
								&& (((adrChecked.getAdrComplement() == null || ("")
										.equals(adrChecked.getAdrComplement())) && (adr
										.getComplAdr() == null || ("")
										.equals(adr.getComplAdr()))) || (adrChecked
										.getAdrComplement() != null && adrChecked
										.getAdrComplement().equalsIgnoreCase(
												adr.getComplAdr())))
								&& (((adrChecked.getCityR() == null || ("").equals(adrChecked
										.getCityR())) && (adr.getVille() == null || ("")
										.equals(adr.getVille()))) || (adrChecked
										.getCityR() != null && adrChecked.getCityR()
										.equalsIgnoreCase(adr.getVille())))
								&& (((adrChecked.getLocality() == null || ("")
										.equals(adrChecked.getLocality())) && (adr
										.getLieuDit() == null || ("")
										.equals(adr.getLieuDit()))) || (adrChecked
										.getLocality() != null && adrChecked
										.getLocality().equalsIgnoreCase(
												adr.getLieuDit())))
								&& (((adrChecked.getNumAndStreet() == null || ("")
										.equals(adrChecked.getNumAndStreet())) && (adr
										.getNumeroRue() == null || ("")
										.equals(adr.getNumeroRue()))) || (adrChecked
										.getNumAndStreet() != null && adrChecked
										.getNumAndStreet().equalsIgnoreCase(
												adr.getNumeroRue())))
								&& (((adrChecked.getZipCode() == null || ("")
										.equals(adrChecked.getZipCode())) && (adr
										.getCodePostal() == null || ("")
										.equals(adr.getCodePostal()))) || (adrChecked
										.getZipCode() != null && adrChecked
										.getZipCode().equalsIgnoreCase(
												adr.getCodePostal())))
								&& (((adrChecked.getState() == null || ("").equals(adrChecked
										.getState())) && (adr
										.getCodeProvince() == null || ("")
										.equals(adr.getCodeProvince()))) || (adrChecked
										.getState() != null && adrChecked.getState()
										.equalsIgnoreCase(
												adr.getCodeProvince())))) {
							
								isAddressOk = true;
		
							}
						}
		
						// IF ONE OT THE ADDRESS HAS NOT BEEN NORMALIZED THEN WE RETURN FALSE
						if (!isAddressOk) {
							response.setSuccess(false);
						}
		
						par = IndividuRequestTransformV6.dtoTOPostal(adr);
						
						// UPDATE OF RETURNED PARAMETERS - SOFT
						par = IndividuRequestTransformV6.softTOPostal(adrChecked, par);
						scr = IndividuRequestTransformV6.softToError(adrChecked);
						par.setSoftComputingResponse(scr);
						response.getPostalAddressResponse().add(par);
					}
				}
			}
			return response;	
		}	
	}
