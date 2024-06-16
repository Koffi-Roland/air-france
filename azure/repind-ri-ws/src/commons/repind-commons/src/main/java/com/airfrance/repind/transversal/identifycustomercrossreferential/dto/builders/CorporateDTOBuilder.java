package com.airfrance.repind.transversal.identifycustomercrossreferential.dto.builders;

import com.airfrance.repind.entity.adresse.Email;
import com.airfrance.repind.entity.adresse.Telecoms;
import com.airfrance.repind.entity.adresse.enums.MediumStatusEnum;
import com.airfrance.repind.entity.agence.Agence;
import com.airfrance.repind.entity.firme.Entreprise;
import com.airfrance.repind.entity.firme.Etablissement;
import com.airfrance.repind.entity.firme.Fonction;
import com.airfrance.repind.entity.firme.Membre;
import com.airfrance.repind.transversal.identifycustomercrossreferential.dto.*;
import com.airfrance.repind.util.ConstantValues;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class CorporateDTOBuilder {
	
	
	/*==========================================*/
	/*                                          */
	/*             PUBLIC METHODS               */
	/*                                          */
	/*==========================================*/
	
	/**
	 * Builds CorporateDTO instance from read member entity
	 * @param member
	 * @return
	 */
	public CorporateDTO buildCorporate(Membre member)
	{
		CorporateDTO corporateDTO = null;
		if((member.getPersonneMorale() != null)
				&&	((member.getPersonneMorale() instanceof Entreprise)
						||
						(member.getPersonneMorale() instanceof Etablissement)
					)
		)
		{
			if(member.getPersonneMorale().getGin() != null)
			{
				/*
				 * CorporateInformations
				 */
				corporateDTO = new CorporateDTO();
				CorporateInformationsDTO corporateInformationsDTO = new CorporateInformationsDTO();
				corporateInformationsDTO.setCorporateKey(member.getPersonneMorale().getGin());
				corporateDTO.setCorporateInformations(corporateInformationsDTO);
				
				List<MemberCorporateDTO> listMembersCorporate = new ArrayList<MemberCorporateDTO>();
				
				/*
				 * Members
				 */
				if(member.getFonctions() != null && !member.getFonctions().isEmpty())
				{

					for(Fonction fonction : member.getFonctions()) {
						MemberCorporateDTO memberCorporateDTO = new MemberCorporateDTO();
						memberCorporateDTO.setJobTitle(fonction.getFonction());
						Set<Email> emailList = fonction.getEmails();
						if(emailList != null && !emailList.isEmpty()) {
							List<EmailCorporateMemberDTO> emailCorporateMemberList = new ArrayList<EmailCorporateMemberDTO>();
							int indexEmail = 0;
							for(Email email : emailList) {
								if(indexEmail < ConstantValues.INDEX_MEMBER_EMAIL && MediumStatusEnum.VALID.toLiteral().equals(email.getStatutMedium())) {
									EmailCorporateMemberDTO emailCorporateMemberDTO = new EmailCorporateMemberDTO();
									if(email.getEmail() != null && !email.getEmail().isEmpty()) {
										emailCorporateMemberDTO.setEmail(email.getEmail());
									}
									if(email.getCodeMedium() != null && !email.getCodeMedium().isEmpty()) {
										emailCorporateMemberDTO.setMediumCode(email.getCodeMedium());
									}
									emailCorporateMemberList.add(emailCorporateMemberDTO);
									indexEmail++;
								}
							}
							memberCorporateDTO.setEmailCorporateMember(emailCorporateMemberList);
						}
						Set<Telecoms> telecomsList = fonction.getTelecoms();
						if(telecomsList != null && !telecomsList.isEmpty()) {
							List<TelecomCorporateMemberDTO> telecomCorporateMemberDTOList = new ArrayList<TelecomCorporateMemberDTO>();
							int indexTel = 0;
							for(Telecoms telecom : telecomsList) {
								if(indexTel < ConstantValues.INDEX_MEMBER_TELECOM && MediumStatusEnum.VALID.toLiteral().equals(telecom.getSstatut_medium())) {
									TelecomCorporateMemberDTO telecomCorporateMemberDTO = new TelecomCorporateMemberDTO();
									if(telecom.getCountryCode() != null && !telecom.getCountryCode().isEmpty()) {
										telecomCorporateMemberDTO.setCountryCode(telecom.getCountryCode());
									}
									if(telecom.getScode_medium() != null && !telecom.getScode_medium().isEmpty()) {
										telecomCorporateMemberDTO.setMediumCode(telecom.getScode_medium());
									}
									if(telecom.getSnumero() != null && !telecom.getSnumero().isEmpty()) {
										telecomCorporateMemberDTO.setPhoneNumber(telecom.getSnumero());
									}
									if(telecom.getSnorm_inter_phone_number() != null && !telecom.getSnorm_inter_phone_number().isEmpty()) {
										telecomCorporateMemberDTO.setInternationalNormalizedPhoneNumber(telecom.getSnorm_inter_phone_number());
									}
									if(telecom.getSterminal() != null && !telecom.getSterminal().isEmpty()) {
										telecomCorporateMemberDTO.setTerminalType(telecom.getSterminal());
									}
									telecomCorporateMemberDTOList.add(telecomCorporateMemberDTO);
									indexTel++;
								}
							}
							memberCorporateDTO.setTelecomCorporateMember(telecomCorporateMemberDTOList);
						}
						listMembersCorporate.add(memberCorporateDTO);
					}
				}
				
				corporateDTO.setMemberCorporate(listMembersCorporate);
			}
		}
		
		return corporateDTO;
	}
	
	
	/**
	 * Builds AgencyDTO instance from read member entity
	 * @param member
	 * @return
	 */
	public AgencyDTO buildAgency(Membre member)
	{
		AgencyDTO agencyDTO = null;
		if((member.getPersonneMorale() != null)
				&&	(member.getPersonneMorale() instanceof Agence))
		{
			if(member.getPersonneMorale().getGin() != null)
			{
				/*
				 * CorporateInformations
				 */
				agencyDTO = new AgencyDTO();
				AgencyInformationsDTO agencyInformationsDTO = new AgencyInformationsDTO();
				agencyInformationsDTO.setAgencyKey(member.getPersonneMorale().getGin());
				agencyDTO.setAgencyInformations(agencyInformationsDTO);

				List<MemberAgencyDTO> listMembersAgency = new ArrayList<MemberAgencyDTO>();

				/*
				 * Members
				 */
				if(member.getFonctions() != null && !member.getFonctions().isEmpty()) {
					for(Fonction fonction : member.getFonctions()) {
						MemberAgencyDTO memberAgencyDTO = new MemberAgencyDTO();
						memberAgencyDTO.setJobTitle(fonction.getFonction());
						Set<Email> emailList = fonction.getEmails();
						if(emailList != null && !emailList.isEmpty()) {
							List<EmailAgencyMemberDTO> emailAgencyMemberList = new ArrayList<EmailAgencyMemberDTO>();
							int indexEmail = 0;
							for(Email email : emailList) {
								if(indexEmail < ConstantValues.INDEX_MEMBER_EMAIL && MediumStatusEnum.VALID.toLiteral().equals(email.getStatutMedium())) {
									EmailAgencyMemberDTO emailAgencyMemberDTO = new EmailAgencyMemberDTO();
									if(email.getEmail() != null && !email.getEmail().isEmpty()) {
										emailAgencyMemberDTO.setEmail(email.getEmail());
									}
									if(email.getCodeMedium() != null && !email.getCodeMedium().isEmpty()) {
										emailAgencyMemberDTO.setMediumCode(email.getCodeMedium());
									}
									emailAgencyMemberList.add(emailAgencyMemberDTO);
									indexEmail++;
								}
							}
							memberAgencyDTO.setEmailAgencyMember(emailAgencyMemberList);
						}
						Set<Telecoms> telecomsList = fonction.getTelecoms();
						if(telecomsList != null && !telecomsList.isEmpty()) {
							List<TelecomAgencyMemberDTO> telecomAgencyMemberList = new ArrayList<TelecomAgencyMemberDTO>();
							int indexTel = 0;
							for(Telecoms telecom : telecomsList) {
								if(indexTel < ConstantValues.INDEX_MEMBER_TELECOM && MediumStatusEnum.VALID.toLiteral().equals(telecom.getSstatut_medium())) {
									TelecomAgencyMemberDTO telecomAgencyMemberDTO = new TelecomAgencyMemberDTO();
									if(telecom.getCountryCode() != null && !telecom.getCountryCode().isEmpty()) {
										telecomAgencyMemberDTO.setCountryCode(telecom.getCountryCode());
									}
									if(telecom.getScode_medium() != null && !telecom.getScode_medium().isEmpty()) {
										telecomAgencyMemberDTO.setMediumCode(telecom.getScode_medium());
									}
									if(telecom.getSnumero() != null && !telecom.getSnumero().isEmpty()) {
										telecomAgencyMemberDTO.setPhoneNumber(telecom.getSnumero());
									}
									if(telecom.getSnorm_inter_phone_number() != null && !telecom.getSnorm_inter_phone_number().isEmpty()) {
										telecomAgencyMemberDTO.setInternationalNormalizedPhoneNumber(telecom.getSnorm_inter_phone_number());
									}
									if(telecom.getSterminal() != null && !telecom.getSterminal().isEmpty()) {
										telecomAgencyMemberDTO.setTerminalType(telecom.getSterminal());
									}
									telecomAgencyMemberList.add(telecomAgencyMemberDTO);
									indexTel++;
								}
							}
							memberAgencyDTO.setTelecomAgencyMember(telecomAgencyMemberList);
						}
						listMembersAgency.add(memberAgencyDTO);
					}
					agencyDTO.setMemberAgency(listMembersAgency);
				}
			}
		}
		
		return agencyDTO;
	}
}
