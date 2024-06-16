package com.afklm.repind.searchindividualbymulticriteriaws;

import com.afklm.soa.stubs.w001271.v2.data.SearchIndividualByMulticriteriaRequest;
import com.afklm.soa.stubs.w001271.v2.data.SearchIndividualByMulticriteriaResponse;
import com.afklm.soa.stubs.w001271.v2.request.Contact;
import com.afklm.soa.stubs.w001271.v2.request.Identification;
import com.afklm.soa.stubs.w001271.v2.request.Identity;
import com.afklm.soa.stubs.w001271.v2.response.Individual;
import com.afklm.soa.stubs.w001271.v2.response.*;
import com.afklm.soa.stubs.w001271.v2.siccommontype.Requestor;
import com.afklm.soa.stubs.w001271.v2.sicindividutype.*;
import com.airfrance.repind.dto.adresse.EmailDTO;
import com.airfrance.repind.dto.adresse.PostalAddressDTO;
import com.airfrance.repind.dto.adresse.TelecomsDTO;
import com.airfrance.repind.dto.adresse.Usage_mediumDTO;
import com.airfrance.repind.dto.individu.IndividuDTO;
import com.airfrance.repind.dto.individu.adh.searchindividualbymulticriteria.*;
import com.airfrance.repind.dto.role.RoleContratsDTO;
import com.airfrance.repind.util.SicStringUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Set;


@Slf4j
public class SearchIndividualByMulticriteriaTransform {

	private static final int ERROR_DETAIL_MAX_LENGTH = 255;
	private static final int ERROR_LABEL_MAX_LENGTH = 70;
	private static final int MAX_CONTRACTS_TO_RETURN = 25;
	/**
	 * Private constructor.
	 */
	private SearchIndividualByMulticriteriaTransform() {
		
	}
	
	public static SearchIndividualByMulticriteriaRequestDTO wsdlTOdto(SearchIndividualByMulticriteriaRequest wsdl) {
		SearchIndividualByMulticriteriaRequestDTO requeteSearch = null;
		if (wsdl != null) {
			requeteSearch = new SearchIndividualByMulticriteriaRequestDTO();
			
			requeteSearch.setRequestor(transformRequestorToDto(wsdl.getRequestor()));
			requeteSearch.setIdentity(transformIdentityToDto(wsdl.getIdentity()));
			requeteSearch.setContact(transformContactToDto(wsdl.getContact()));
			requeteSearch.setIdentification(transformIdentificationToDto(wsdl.getIdentification()));
			requeteSearch.setPopulationTargeted(wsdl.getPopulationTargeted());
			requeteSearch.setProcessType(wsdl.getProcessType());
			requeteSearch.setSearchDriving(wsdl.getSearchDriving());
		}
		return requeteSearch;
	}
	
	public static SearchIndividualByMulticriteriaResponse dtoTOwsdl(SearchIndividualByMulticriteriaResponseDTO dto) {
		SearchIndividualByMulticriteriaResponse response = null;
		
		if (dto != null) {
			response = new SearchIndividualByMulticriteriaResponse();
			for (IndividualMulticriteriaDTO individuDTO : dto.getIndividuals()) {
				Individual individuWsdl = dtoTOwsdl(individuDTO.getIndividu());
				// REPIND-1003 : Repair blocker
				if(individuWsdl != null) {
					individuWsdl.setRelevance(individuDTO.getRelevance());
					response.getIndividual().add(individuWsdl);
				}
			}
			if (dto.getIndividuals().size()<21)
				response.setVisaKey(dto.getVisaKey());
			else
				response.setVisaKey("");
		}
		return response;
	}
	
	public static RequestorDTO transformRequestorToDto(Requestor bo) {
		RequestorDTO dto = null;
		
		if (bo != null) {
			dto = new RequestorDTO();
			if (bo.getApplicationCode() != null) {
				dto.setApplicationCode(bo.getApplicationCode());
			}
			if (bo.getChannel() != null) {
				dto.setChannel(bo.getChannel());
			}
			if (bo.getContext() != null) {
				dto.setContext(bo.getContext());
			}
			if (bo.getIpAddress() != null) {
				dto.setIpAddress(bo.getIpAddress());
			}
			if (bo.getManagingCompany() != null) {
				dto.setManagingCompany(bo.getManagingCompany());
			}
			if (bo.getMatricule() != null) {
				dto.setMatricule(bo.getMatricule());
			}
			if (bo.getOfficeId() != null) {
				dto.setOfficeId(bo.getOfficeId());
			}
			if (bo.getScope() != null) {
				dto.setScope(bo.getScope());
			}
			if (bo.getSignature() != null) {
				dto.setSignature(bo.getSignature());
			}
			if (bo.getSite() != null) {
				dto.setSite(bo.getSite());
			}
			if (bo.getToken() != null) {
				dto.setToken(bo.getToken());
			}
		}
		return dto;
	}
	
	public static IdentityDTO transformIdentityToDto(Identity bo) {
		IdentityDTO dto = null;
		
		if (bo != null) {
			dto = new IdentityDTO();
			if (bo.getBirthday() != null) {
				dto.setBirthday(bo.getBirthday());
			}
			if (bo.getCivility() != null) {
				dto.setCivility(bo.getCivility());
			}
			if (bo.getFirstName() != null) {
				dto.setFirstName(bo.getFirstName());
			}
			if (bo.getFirstNameSearchType() != null) {
				dto.setFirstNameSearchType(bo.getFirstNameSearchType());
			}
			if (bo.getLastName() != null) {
				dto.setLastName(bo.getLastName());
			}
			if (bo.getLastNameSearchType() != null) {
				dto.setLastNameSearchType(bo.getLastNameSearchType());
			}
		}
		
		return dto;
	}
	
	public static ContactDTO transformContactToDto(Contact bo) {
		ContactDTO dto = null;
		
		if (bo != null) {
			dto = new ContactDTO();
			if (bo.getCountryCode() != null) {
				dto.setCountryCode(bo.getCountryCode());
			}
			if (bo.getEmail() != null) {
				// REPIND-1288 : Compare an upper case given Email to a lower case in Database
				// dto.setEmail(bo.getEmail().toLowerCase());
				dto.setEmail(bo.getEmail());
			}
			if (bo.getPhoneNumber() != null) {
				dto.setPhoneNumber(bo.getPhoneNumber());
			}
			if (bo.getPostalAddressBloc() != null) {
				PostalAddressBlocDTO adrDto = new PostalAddressBlocDTO();
				
				if (bo.getPostalAddressBloc().getPostalAddressContent() != null) {
					if (bo.getPostalAddressBloc().getPostalAddressContent().getAdditionalInformation() != null) {
						adrDto.setAdditionalInformation(bo.getPostalAddressBloc().getPostalAddressContent().getAdditionalInformation());
					}
					if (bo.getPostalAddressBloc().getPostalAddressContent().getCity() != null) {
						adrDto.setCity(bo.getPostalAddressBloc().getPostalAddressContent().getCity());
					}
					if (bo.getPostalAddressBloc().getPostalAddressContent().getCitySearchType() != null) {
						adrDto.setCitySearchType(bo.getPostalAddressBloc().getPostalAddressContent().getCitySearchType());
					}
					if (bo.getPostalAddressBloc().getPostalAddressContent().getCountryCode() != null) {
						adrDto.setCountryCode(bo.getPostalAddressBloc().getPostalAddressContent().getCountryCode());
					}
					if (bo.getPostalAddressBloc().getPostalAddressContent().getNumberAndStreet() != null) {
						adrDto.setNumberAndStreet(bo.getPostalAddressBloc().getPostalAddressContent().getNumberAndStreet());
					}
					if (bo.getPostalAddressBloc().getPostalAddressContent().getZipCode() != null) {
						adrDto.setZipCode(bo.getPostalAddressBloc().getPostalAddressContent().getZipCode());
					}
					if (bo.getPostalAddressBloc().getPostalAddressContent().getZipCodeSearchType() != null) {
						adrDto.setZipCodeSearchType(bo.getPostalAddressBloc().getPostalAddressContent().getZipCodeSearchType());
					}
					dto.setPostalAddressBloc(adrDto);
				}
			}
		}
		
		return dto;
	}
	
	public static IdentificationDTO transformIdentificationToDto(Identification bo) {
		IdentificationDTO dto = null;
		
		if (bo != null) {
			dto = new IdentificationDTO();
			if (bo.getIdentificationType() != null) {
				dto.setIdentificationType(bo.getIdentificationType());
			}
			if (bo.getIdentificationValue() != null) {
				dto.setIdentificationValue(bo.getIdentificationValue());
			}
		}
		
		return dto;
	}
	
	public static Individual dtoTOwsdl(IndividuDTO dto) {
		Individual individu = null;
		if (dto != null) {
			individu = new Individual();
			IndividualInformations infoIndividu = infoIndividuDtoTOwsdl(dto);
			
			ContractResponse cr = new ContractResponse();
			individu.setContractResponse(cr);
			PostalAddressResponse par = new PostalAddressResponse();
			individu.setPostalAddressResponse(par);
			TelecomResponse tr = new TelecomResponse();
			individu.setTelecomResponse(tr);
			EmailResponse er = new EmailResponse();
			individu.setEmailResponse(er);
			
			individu.setIndividualInformations(infoIndividu);
			
			if (dto.getRolecontratsdto()!=null) {
				contractDtoTOwsdl(individu.getContractResponse().getContract(), dto.getRolecontratsdto());
			}
			
			if (dto.getPostaladdressdto()!=null) {
				adresseDtoTOwsdl(individu.getPostalAddressResponse().getAddress(), dto.getPostaladdressdto());
			}
			
			if (dto.getTelecoms()!=null) {
				telecomDtoTOwsdl(individu.getTelecomResponse().getTelecom(), dto.getTelecoms());
			}
			
			if (dto.getEmaildto()!=null) {
				emailDtoTOwsdl(individu.getEmailResponse().getEmail(), dto.getEmaildto());
			}
		}		
		return individu;
		
	}
	
	public static IndividualInformations infoIndividuDtoTOwsdl(IndividuDTO dto) {
		IndividualInformations infoIndividu = null;
		if (dto != null) {
			infoIndividu = new IndividualInformations();
			infoIndividu.setIdentifier(dto.getSgin());
			infoIndividu.setVersion(dto.getVersion().toString());
			infoIndividu.setFirstNameSC(dto.getPrenom());
			infoIndividu.setLastNameSC(dto.getNom());
			infoIndividu.setGender(dto.getSexe());
			infoIndividu.setPersonalIdentifier(dto.getIdentifiantPersonnel());
			infoIndividu.setBirthDate(dto.getDateNaissance());
			infoIndividu.setNationality(dto.getNationalite());
			infoIndividu.setSecondFirstName(dto.getSecondPrenom());
			infoIndividu.setFlagNoFusion(SicStringUtils.getFrenchBoolean(dto.getNonFusionnable()));
			infoIndividu.setStatus(dto.getStatutIndividu());
			infoIndividu.setFlagThirdTrap(SicStringUtils.getFrenchBoolean(dto.getTierUtiliseCommePiege()));
			try {
				infoIndividu.setCivility(dto.getCivilite());
			} catch (IllegalArgumentException e) {
				log.info("No Civilite to set");
			} catch (NullPointerException e) {
				log.info("No Civilite to set");
			}
			infoIndividu.setFirstNamePseudonym(dto.getAliasPrenom());
			infoIndividu.setLastNamePseudonym(dto.getAliasNom1());
			
			if (dto.getPrenomSC() != null) {
				infoIndividu.setFirstNameSC(dto.getPrenomSC());				
			}
			if (dto.getNomSC() != null) {
				infoIndividu.setLastNameSC(dto.getNomSC());				
			}
			// REPIND-266 : Le Code Langue au retour du W001271V02 est toujours vide
			infoIndividu.setLanguageCode(dto.getCodeLangue());
		}				
		return infoIndividu;
	}
	
	public static void contractDtoTOwsdl(List<Contract> list, Set<RoleContratsDTO> in) {
		int nb_contracts_add = 0;
		if (in != null) {
			for (RoleContratsDTO contratsDTO : in) {
				Contract contrat = new Contract();
				contrat.setContractNumber(contratsDTO.getNumeroContrat());
				try {
					contrat.setContractType(contratsDTO.getTypeContrat());
				} catch (IllegalArgumentException e) {
					log.info("No Contract Type to set");
				} catch (NullPointerException e) {
					log.info("No Contract Type to set");
				}
				//contrat.setProductType(contratsDTO.getBusinessroledto().getType());
				contrat.setProductSubType(contratsDTO.getSousType());
				contrat.setCompanyContractType(contratsDTO.getCodeCompagnie());
				if (contratsDTO.getVersion() != null) {
					// REPIND-988 : W001271 do not sent contract with iversion number that exceeded 2 digits
					contrat.setVersion(SicStringUtils.truncToTwoRightChar(contratsDTO.getVersion().toString()));
				}
				contrat.setContractStatus(contratsDTO.getEtat());
				contrat.setValidityStartDate(contratsDTO.getDateDebutValidite());
				contrat.setValidityEndDate(contratsDTO.getDateFinValidite());
				contrat.setTierLevel(contratsDTO.getTier());
				contrat.setProductFamilly(contratsDTO.getFamilleProduit());
				contrat.setIata(contratsDTO.getAgenceIATA());
				contrat.setOriginCompany(contratsDTO.getCodeCompagnie());
				contrat.setAdhesionSource(contratsDTO.getSourceAdhesion());
				if (contratsDTO.getSoldeMiles()!=null) contrat.setMilesBalance(contratsDTO.getSoldeMiles().toString());
				if (contratsDTO.getMilesQualif()!=null) contrat.setQualifyingMiles(contratsDTO.getMilesQualif().toString());
				if (contratsDTO.getMilesQualifPrec()!=null) contrat.setQualifyingHistMiles(contratsDTO.getMilesQualifPrec().toString());
				if (contratsDTO.getSegmentsQualif()!=null) contrat.setQualifyingFlights(contratsDTO.getSegmentsQualif().toString());
				if (contratsDTO.getSegmentsQualifPrec()!=null) contrat.setQualifyingHistFlights(contratsDTO.getSegmentsQualifPrec().toString());
				
				//REPIND-1500: WSDL is limited to 25, we do not add more contracts after this limit in order to not crash
				if (nb_contracts_add < MAX_CONTRACTS_TO_RETURN) {
					list.add(contrat);
					nb_contracts_add++;
				}
			}
		}
	}
	
	public static void adresseDtoTOwsdl(List<Address> list, List<PostalAddressDTO> in) {
		if (in != null) {
			for (PostalAddressDTO adresseDTO : in) {

				Address adresse = new Address();
				
				PostalAddressProperties addressProperties = new PostalAddressProperties();
				addressProperties.setMediumCode(adresseDTO.getScode_medium());
				addressProperties.setMediumStatus(adresseDTO.getSstatut_medium());
				
				if (adresseDTO.getVersion()!=null) 
					addressProperties.setVersion(adresseDTO.getVersion().toString());
				
				if ("O".equalsIgnoreCase(adresseDTO.getSforcage()))
					addressProperties.setIndicAdrNorm(true);
				else
					addressProperties.setIndicAdrNorm(false);
				
				PostalAddressContent adresseContent = new PostalAddressContent();
				adresseContent.setCorporateName(SicStringUtils.replaceNonPrintableChars(adresseDTO.getSraison_sociale()));
				adresseContent.setCity(SicStringUtils.replaceNonPrintableChars(adresseDTO.getSville()));
				adresseContent.setAdditionalInformation(SicStringUtils.replaceNonPrintableChars(adresseDTO.getScomplement_adresse()));
				adresseContent.setNumberAndStreet(SicStringUtils.replaceNonPrintableChars(adresseDTO.getSno_et_rue()));
				adresseContent.setDistrict(SicStringUtils.replaceNonPrintableChars(adresseDTO.getScode_province()));
				adresseContent.setZipCode(SicStringUtils.replaceNonPrintableChars(adresseDTO.getScode_postal()));
				adresseContent.setCountryCode(SicStringUtils.replaceNonPrintableChars(adresseDTO.getScode_pays()));
				adresseContent.setStateCode(SicStringUtils.replaceNonPrintableChars(adresseDTO.getScode_province()));
				
				adresse.setPostalAddressProperties(addressProperties);
				adresse.setPostalAddressContent(adresseContent);
				
				//We check if usage is available for Address and add them to the block returned
				if (adresseDTO.getUsage_mediumdto() != null && adresseDTO.getUsage_mediumdto().size() > 0) {
					for (Usage_mediumDTO usageMedium : adresseDTO.getUsage_mediumdto()) {
						UsageAddress usageAddress = new UsageAddress();
						usageAddress.setAddressRoleCode(usageMedium.getSrole1());
						usageAddress.setApplicationCode(usageMedium.getScode_application());
						usageAddress.setUsageNumber(Integer.toString(usageMedium.getInum()));
						adresse.getUsageAddress().add(usageAddress);
					}
				}
				list.add(adresse);
			}
		}
	}
	
	public static void telecomDtoTOwsdl(List<com.afklm.soa.stubs.w001271.v2.response.Telecom> list, Set<TelecomsDTO> in) {
		if (in != null) {
			for (TelecomsDTO telecomsDTO : in) {
				com.afklm.soa.stubs.w001271.v2.response.Telecom telecom = new com.afklm.soa.stubs.w001271.v2.response.Telecom();
				if (telecomsDTO.getVersion()!=null) telecom.setVersion(telecomsDTO.getVersion().toString());
				telecom.setMediumStatus(telecomsDTO.getSstatut_medium());
				
				// TODO: mediumCode dans TelecomResponse?
				telecom.setMediumCode(telecomsDTO.getScode_medium());
				telecom.setTerminalType(telecomsDTO.getSterminal());
				telecom.setCountryCode(telecomsDTO.getSindicatif());
				telecom.setPhoneNumber(telecomsDTO.getSnumero());

				// TODO: Normalization des telecoms?
				
				list.add(telecom);
			}
		}
	}

	public static void emailDtoTOwsdl(List<Email> out, Set<EmailDTO> in) {
		if (in != null) {
			for (EmailDTO emailDTO : in ) {

				if("V".equals(emailDTO.getStatutMedium()) || "T".equals(emailDTO.getStatutMedium())){

					Email email = new Email();
					if(emailDTO.getVersion()!=null) email.setVersion(SicStringUtils.replaceNonPrintableChars(emailDTO.getVersion().toString()));
					email.setMediumStatus(emailDTO.getStatutMedium());
					email.setMediumCode(emailDTO.getCodeMedium());
					email.setEmail(SicStringUtils.replaceNonPrintableChars(emailDTO.getEmail()));
					out.add(email);
				}

			}
		}
	}
	
	// REPIND-1286 : Test if identification is NULL or EMPTY
	public static boolean isNullOrEmpty(Identification identification) {
		if (identification == null) {
			return true;
		} else {
			if (
					(identification.getIdentificationType() == null || "".equals(identification.getIdentificationType())) &&
					(identification.getIdentificationValue() == null || "".equals(identification.getIdentificationValue()))
				) { 
				return true;
			} else {
				return false;
			}
		}		
	}
}
