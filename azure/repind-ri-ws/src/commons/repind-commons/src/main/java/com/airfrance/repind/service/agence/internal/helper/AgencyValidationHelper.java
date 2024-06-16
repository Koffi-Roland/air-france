package com.airfrance.repind.service.agence.internal.helper;

import com.airfrance.repind.entity.refTable.*;
import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.type.MediumStatusEnum;
import com.airfrance.ref.type.OuiNonFlagEnum;
import com.airfrance.ref.type.YesNoFlagEnum;
import com.airfrance.repind.dao.agence.AgenceRepository;
import com.airfrance.repind.dto.adresse.PostalAddressDTO;
import com.airfrance.repind.dto.agence.AgenceDTO;
import com.airfrance.repind.dto.agence.MembreReseauDTO;
import com.airfrance.repind.dto.firme.NumeroIdentDTO;
import com.airfrance.repind.dto.firme.SegmentationDTO;
import com.airfrance.repind.dto.firme.SynonymeDTO;
import com.airfrance.repind.dto.profil.ProfilDemarchargeDTO;
import com.airfrance.repind.dto.profil.ProfilFirmeDTO;
import com.airfrance.repind.dto.profil.Profil_mereDTO;
import com.airfrance.repind.dto.reference.ReseauDTO;
import com.airfrance.repind.dto.zone.PmZoneDTO;
import com.airfrance.repind.dto.zone.ZoneCommDTO;
import com.airfrance.repind.dto.zone.ZoneDecoupDTO;
import com.airfrance.repind.dto.zone.ZoneVenteDTO;
import com.airfrance.repind.entity.agence.Agence;
import com.airfrance.repind.entity.firme.NumeroIdent;
import com.airfrance.repind.entity.reference.Pays;
import com.airfrance.repind.entity.zone.enums.PrivilegedLinkEnum;
import com.airfrance.repind.service.firm.internal.NumeroIdentDS;
import com.airfrance.repind.service.internal.unitservice.zone.ZoneCommUS;
import com.airfrance.repind.service.internal.unitservice.zone.ZoneVenteUS;
import com.airfrance.repind.service.reference.internal.RefCountryDS;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTimeComparator;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotNull;
import java.util.*;

public class AgencyValidationHelper {

	private static final DateTimeComparator DTC = DateTimeComparator.getDateOnlyInstance();
	private static final Date TODAY = new Date();
	private static final String REGEX_ID_NUMBER = "[0-9]+";
	private static final String DEFAULT_NETWORK_CODE_INDEPENDENT_NETWORK = "INDE";
	public static final String ACTIVITY_RA2 = "RA2";
	public static final String IDENTIFICATION_NUMBER = ": IDENTIFICATION NUMBER";
	public static final String FICTITIOUS_NUMBER = ": FICTITIOUS NUMBER";
	public static final String MARKET_CHOICE_END_DATE_ERROR = ": MARKET CHOICE END DATE < AGREEMENT START DATE";
	public static final String MARKET_CHOICE = ": MARKET CHOICE";
	public static final String AGREEMENT_DATE = ": AGREEMENT DATE";
	public static final String AGREEMENT_DATE_INCOHERENT_WITH_AGENCY_TYPE = ": AGREEMENT DATE INCOHERENT WITH AGENCY TYPE";
	public static final String CLOSING_DATE = ": CLOSING DATE";
	public static final String STRUCK_OFF_DATE = ": STRUCK-OFF DATE";
	public static final String DISTRIBUTION_CHANNEL_INCOHERENT = ":DISTRIBUTION CHANNEL INCOHERENT";
	public static final String PARENT_AGENCY = ": PARENT AGENCY";
	public static final String USUAL_NAME = ": USUAL NAME";
	public static final String TRADE_NAME = ": TRADE NAME";
	public static final String DEFAULT_SITE = "IHM RA1";
	public static final String SITE_QVI = "QVI";
	public static final String CREATION_SIGNATURE = "AGEN CREA";
	public static final String IATA_ARC_MANDATORY = ": IATA NUMBER SHOULD BE SPECIFIED WITH ARC NUMBER";
	public static final String ARC_AND_IATA_INCOHERENT = ": ARC AND IATA NUMBERS MUST BE THE SAME";

	public static void validateInputData(@NotNull AgenceDTO agencyDto, AgenceRepository agencyRepository,
			RefCountryDS countryService, NumeroIdentDS numberIdDs) throws JrafDomainException {
		// Validate Identification fields
		if (StringUtils.isBlank(agencyDto.getNom())) {
			throw new JrafDaoException(RefTableREF_ERREUR._REF_103);
		}

		if (StringUtils.isBlank(agencyDto.getStatut())) {
			throw new JrafDaoException(RefTableREF_ERREUR._REF_154);
		}

		validateAgencyType(agencyDto);

		// Identification number
		validateIdentificationNumbers(agencyDto, numberIdDs);
		String iataStatus = agencyDto.getStatutIATA();
		if (StringUtils.isNotBlank(iataStatus)
				&& !RefTableREF_STATUT_IATA.instance().estValide(iataStatus, StringUtils.EMPTY)) {
			throw new JrafDaoException(RefTableREF_ERREUR._REF_233);
		}

		validateDistributionChannel(agencyDto);
		validateIdDetails(agencyDto);
		validateMarketChoice(agencyDto);
		validateAddress(agencyDto, countryService);
		validateDates(agencyDto);
		validateSalesType(agencyDto);
		validateParentIata(agencyDto, agencyRepository);
	}

	public static void validateCreationData(@NotNull AgenceDTO agencyDto, ZoneCommUS zcService, ZoneVenteUS zvService)
			throws JrafDomainException {
		validateSynonyms(agencyDto);
		validateNetwork(agencyDto);
		validateZone(agencyDto, zcService, zvService);
		createProfile(agencyDto);
		Set<SegmentationDTO> segmentations = agencyDto.getSegmentations();
		if (CollectionUtils.isEmpty(segmentations) && YesNoFlagEnum.NO.toString().equals(agencyDto.getAgenceRA2())) {
			createMarketChoice(agencyDto);
		}
		validateMemberData(agencyDto);
	}

	public static void validateUpdate(@NotNull AgenceDTO updated, NumeroIdentDS numeroIdentDs, Agence existing)
			throws JrafDomainException {
		validateUpdatedIdentificationNumbers(updated, existing, numeroIdentDs);
	}

	private static void validateUpdatedIdentificationNumbers(@NotNull AgenceDTO updated, Agence existing,
			NumeroIdentDS numeroIdentDs) throws JrafDomainException {
		Set<NumeroIdentDTO> tobeUpdatedIds = updated.getNumerosIdent();
		if (!CollectionUtils.isEmpty(tobeUpdatedIds)) {
			NumeroIdentDTO toBeUpdatedIata = null;
			NumeroIdentDTO toBeUpdatedArc = null;
			for (NumeroIdentDTO identifier : tobeUpdatedIds) {
				String type = identifier.getType();
				if (RefTableREF_TYP_NUMID._REF_IA.equals(type)) {
					toBeUpdatedIata = identifier;
				}
				if (RefTableREF_TYP_NUMID._REF_AR.equals(type)) {
					Set<NumeroIdent> existingIds = existing.getNumerosIdent();
					NumeroIdent existingArc = null;
					for (NumeroIdent existingId : existingIds) {
						if (RefTableREF_TYP_NUMID._REF_AR.equals(existingId.getType())) {
							existingArc = existingId;
						}
					}
					
					// Check if ARC has been modified
					if (existingArc == null && identifier != null || existingArc != null && identifier == null
							|| existingArc != null && identifier != null && !existingArc.getNumero().equals(identifier.getNumero())) {						
						toBeUpdatedArc = identifier;
					}
				}
			}

			// Validate ARC
			if (toBeUpdatedArc != null) {
				validateArcModification(updated, existing, numeroIdentDs, toBeUpdatedIata, toBeUpdatedArc);
			}
		}
	}

	private static void validateArcModification(AgenceDTO updated, Agence existing, NumeroIdentDS numeroIdentDs,
			NumeroIdentDTO toBeUpdatedIata, NumeroIdentDTO toBeUpdatedArc)
			throws JrafDomainException {
		String agencyType = existing.getType();
		validateIdNumber(agencyType, toBeUpdatedArc);

		// If key is present, update it, else create new NumeroIdent
		Long key = toBeUpdatedArc.getKey();
		NumeroIdentDTO arcExisting = null;
		if (key != null) {
			arcExisting = numeroIdentDs.get(key);
		}
		if (arcExisting == null) {
			// Set default values
			toBeUpdatedArc.setStatut(MediumStatusEnum.VALID.toString());
			toBeUpdatedArc.setDateOuverture(TODAY);
		}

		// Validate that ARC and IATA are same
		NumeroIdentDTO existingIata = null;
		if (toBeUpdatedIata == null) {
			// If IATA is not updated, validate with the existing IATA
			List<NumeroIdentDTO> existingIds = numeroIdentDs.findByPm(updated.getGin());
			if (!CollectionUtils.isEmpty(existingIds)) {
				for (NumeroIdentDTO identifier : existingIds) {
					String type = identifier.getType();
					if (RefTableREF_TYP_NUMID._REF_IA.equals(type)) {
						existingIata = identifier;
						break;
					}
				}
			}
		}
		validateArcNumber(toBeUpdatedIata == null ? existingIata : toBeUpdatedIata, toBeUpdatedArc);
		toBeUpdatedArc.setDateModification(TODAY);
	}

	private static void validateMemberData(@NotNull AgenceDTO agencyDto) throws JrafDaoException {
		String type = agencyDto.getType();
		String countryCode = retrieveCountryCode(agencyDto);

		if (StringUtils.isBlank(agencyDto.getStatutIATA()) && !RefTableREF_TYP_AGEN._REF_0.equals(type)
				&& RefTablePAYS._REF_FR.equals(countryCode)) {
			throw new JrafDaoException(RefTableREF_ERREUR._REF_366);
		}

		if (StringUtils.isBlank(agencyDto.getBsp())) {
			if (RefTableREF_TYP_AGEN._REF_0.equals(type)) {
				agencyDto.setBsp(OuiNonFlagEnum.NON.toString());
			} else {
				agencyDto.setBsp(OuiNonFlagEnum.OUI.toString());
			}
		}

		if (agencyDto.getDateAgrement() == null && (RefTableREF_TYP_AGEN._REF_1.equals(type)
				|| RefTableREF_TYP_AGEN._REF_3.equals(type) || RefTableREF_TYP_AGEN._REF_7.equals(type))) {
			agencyDto.setDateAgrement(agencyDto.getDateDebut());
		}

		if (StringUtils.isBlank(agencyDto.getLocalisation())) {
			agencyDto.setLocalisation(RefTableREF_LOCALISA._REF_V);
		}

		if (StringUtils.isBlank(agencyDto.getActiviteLocal())) {
			agencyDto.setActiviteLocal("633Z");
		}

		if (StringUtils.isBlank(agencyDto.getCodeIndustrie())) {
			agencyDto.setCodeIndustrie(RefTableCODE_INDUS._REF_11);
		}

		if (StringUtils.isBlank(agencyDto.getTypeDemarchage())) {
			agencyDto.setTypeDemarchage(RefTableREF_DEMARCH._REF_V);
		}

		if (StringUtils.isBlank(agencyDto.getTypeAgrement()) && (RefTableREF_TYP_AGEN._REF_1.equals(type)
				|| RefTableREF_TYP_AGEN._REF_3.equals(type) || RefTableREF_TYP_AGEN._REF_7.equals(type))) {
			agencyDto.setTypeAgrement(RefTableREF_TYP_AGRE._REF_I);
		}
	}

	private static void createMarketChoice(@NotNull AgenceDTO agencyDto) {
		Set<SegmentationDTO> segmentations = new HashSet<>();
		agencyDto.setSegmentations(segmentations);
		SegmentationDTO segmentaion = new SegmentationDTO();
		segmentations.add(segmentaion);
		segmentaion.setType(RefTableREF_TYP_SEG_A._REF_SGA);
		segmentaion.setNiveau(RefTableREF_NIV_SEG_A._REF_N);
		segmentaion.setDateEntree(agencyDto.getDateDebut());
	}

	private static void createProfile(@NotNull AgenceDTO agencyDto) {
		if (YesNoFlagEnum.NO.toString().equals(agencyDto.getAgenceRA2())) {
			Set<Profil_mereDTO> profiles = new HashSet<>();
			agencyDto.setProfils(profiles);
			Profil_mereDTO profileMere = new Profil_mereDTO();
			ProfilDemarchargeDTO profileDemarchage = new ProfilDemarchargeDTO();
			profileDemarchage.setProfil(profileMere);
			profileMere.setProfilDemarcharge(profileDemarchage);
			profileMere.setStype("DE");
			profiles.add(profileMere);

			String type = agencyDto.getType();
			if (RefTableREF_TYP_AGEN._REF_1.equals(type) || RefTableREF_TYP_AGEN._REF_4.equals(type)) {
				profileDemarchage.setDemarchage(RefTableREF_DEMARCH._REF_N);
				profileDemarchage.setTypeMailing(RefTableREF_TYP_MAIL._REF_T);
			}
			if (RefTableREF_TYP_AGEN._REF_6.equals(type) || RefTableREF_TYP_AGEN._REF_3.equals(type)) {
				profileDemarchage.setDemarchage(RefTableREF_DEMARCH._REF_N);
				profileDemarchage.setTypeMailing(RefTableREF_TYP_MAIL._REF_N);
			} else {
				profileDemarchage.setDemarchage(RefTableREF_DEMARCH._REF_V);
				profileDemarchage.setTypeMailing(RefTableREF_TYP_MAIL._REF_A);
			}

			String countryCode = retrieveCountryCode(agencyDto);
			if (isInFranceOrDomTomOrMonaco(countryCode)) {
				profileDemarchage.setLangueEcr(RefTableLANGUES._REF_FR);
				profileDemarchage.setLanguePar(RefTableLANGUES._REF_FR);
			} else {
				profileDemarchage.setLangueEcr(RefTableLANGUES._REF_EN);
				profileDemarchage.setLanguePar(RefTableLANGUES._REF_EN);
			}

			ProfilFirmeDTO profileFirm = new ProfilFirmeDTO();
			profileFirm.setNationalite(countryCode);
			agencyDto.setProfilFirme(profileFirm);
		}
	}

	private static String retrieveCountryCode(AgenceDTO newAgency) {
		String countryCode = null;
		for (PostalAddressDTO address : newAgency.getPostalAddresses()) {
			if (RefTableCAT_MED._REF_L.equals(address.getScode_medium())) {
				countryCode = address.getScode_pays();
				break;
			}
		}
		return countryCode;
	}

	private static void validateZone(@NotNull AgenceDTO agencyDto, ZoneCommUS zcService, ZoneVenteUS zvService)
			throws JrafDomainException {
		Set<PmZoneDTO> pmZones = agencyDto.getPmZones();
		if (CollectionUtils.isEmpty(pmZones)) {
			throw new JrafDaoException(RefTableREF_ERREUR._REF_346);
		}

		ZoneCommDTO zc = null;
		ZoneVenteDTO zv = null;
		for (PmZoneDTO pmz : pmZones) {
			Date start = agencyDto.getDateDebut();
			pmz.setDateOuverture(start);
			pmz.setSignature(agencyDto.getSignatureCreation());
			pmz.setDateModif(start);
			ZoneDecoupDTO zd = pmz.getZoneDecoup();
			if (zd instanceof ZoneCommDTO && PrivilegedLinkEnum.YES.toLiteral().equals(pmz.getLienPrivilegie())) {
				ZoneCommDTO zcIn = (ZoneCommDTO) zd;
				zc = zcService.findActiveByZc1Zc2Zc3Zc4Zc5(zcIn.getZc1(), zcIn.getZc2(), zcIn.getZc3(), zcIn.getZc4(),
						zcIn.getZc5());

				if (zc != null) {					
					zcIn.setGin(zc.getGin());
				}
			} else if (zd instanceof ZoneVenteDTO
					&& PrivilegedLinkEnum.YES.toLiteral().equals(pmz.getLienPrivilegie())) {
				ZoneVenteDTO zvIn = (ZoneVenteDTO) zd;
				zv = zvService.findActiveByZv0Zv1Zv2Zv3(zvIn.getZv0(), zvIn.getZv1(), zvIn.getZv2(), zvIn.getZv3());

				if (zv != null) {					
					zvIn.setGin(zv.getGin());
				}
			}
		}
		if (zc == null) {
			throw new JrafDaoException(RefTableREF_ERREUR._REF_927);
		}
		if (zv == null) {
			throw new JrafDaoException(RefTableREF_ERREUR._REF_926);
		}
	}

	private static void validateNetwork(@NotNull AgenceDTO agencyDto) throws JrafDaoException {
		Set<MembreReseauDTO> memberNetworks = agencyDto.getReseaux();
		Date start = agencyDto.getDateAgrement() != null ? agencyDto.getDateAgrement() : agencyDto.getDateDebut();
		if (!CollectionUtils.isEmpty(memberNetworks)) {
			for (MembreReseauDTO memberNetwork : memberNetworks) {
				ReseauDTO network = memberNetwork.getReseau();
				if (network != null) {
					if (StringUtils.isBlank(network.getCode())) {
						network.setCode(DEFAULT_NETWORK_CODE_INDEPENDENT_NETWORK);
					}
					if (memberNetwork.getDateDebut() != null) {
						start = memberNetwork.getDateDebut();
					}
					network.setDateCreation(start);
				} else {
					throw new JrafDaoException(RefTableREF_ERREUR._REF_307);
				}
			}
		} else if (YesNoFlagEnum.NO.toString().equals(agencyDto.getAgenceRA2())) {
			List<PostalAddressDTO> addresses = agencyDto.getPostalAddresses();
			boolean inFrenchRegion = locatedInFrenchRegion(addresses);
			if (inFrenchRegion) {
				MembreReseauDTO memberNetwork = new MembreReseauDTO();
				ReseauDTO network = new ReseauDTO();
				memberNetwork.setReseau(network);
				network.setCode(DEFAULT_NETWORK_CODE_INDEPENDENT_NETWORK);
				network.setDateCreation(start);
				memberNetwork.setDateDebut(start);
				memberNetworks = new HashSet<>();
				memberNetworks.add(memberNetwork);
				agencyDto.setReseaux(memberNetworks);
			}
		}
	}

	private static boolean locatedInFrenchRegion(List<PostalAddressDTO> addresses) {
		boolean inFrenchRegion = false;
		for (PostalAddressDTO address : addresses) {
			if (RefTableCAT_MED._REF_L.equals(address.getScode_medium())) {
				inFrenchRegion = isInFranceOrDomTomOrMonaco(address.getScode_pays());
				break;
			}
		}
		return inFrenchRegion;
	}

	private static void validateSynonyms(AgenceDTO agencyDto) throws JrafDaoException {
		Set<SynonymeDTO> synonyms = agencyDto.getSynonymes();
		boolean tradeNamePresent = false;
		boolean usualNamePresent = false;
		if (CollectionUtils.isEmpty(synonyms)) {
			throw new JrafDaoException(RefTableREF_ERREUR._REF_133 + TRADE_NAME);
		} else {
			for (SynonymeDTO synonym : synonyms) {
				if (RefTableREF_SYNONYME._REF_M.equals(synonym.getType())) {
					tradeNamePresent = true;
					updateSynomyn(synonym);
				} else if (RefTableREF_SYNONYME._REF_U.equals(synonym.getType())) {
					usualNamePresent = true;
					updateSynomyn(synonym);
				}
			}
		}

		if (!tradeNamePresent) {
			throw new JrafDaoException(RefTableREF_ERREUR._REF_133 + TRADE_NAME);
		}

		if (!usualNamePresent) {
			throw new JrafDaoException(RefTableREF_ERREUR._REF_133 + USUAL_NAME);
		}
	}

	private static void updateSynomyn(SynonymeDTO synonym) {
		synonym.setStatut(MediumStatusEnum.VALID.toString());
		synonym.setDateModificationSnom(TODAY);
	}

	private static void validateParentIata(@NotNull AgenceDTO agencyDto, AgenceRepository agencyRepository)
			throws JrafDaoException {
		String parentIata = agencyDto.getNumeroIATAMere();
		if (StringUtils.isNotBlank(parentIata)) {
			String validatedId = getValidatedId(parentIata);
			if (CollectionUtils.isEmpty(agencyRepository.findByNumero(validatedId))) {
				throw new JrafDaoException(RefTableREF_ERREUR._REF_001 + PARENT_AGENCY);
			}
		}
	}

	private static void validateSalesType(@NotNull AgenceDTO agencyDto) throws JrafDaoException {
		String salesType = agencyDto.getTypeVente();
		if (StringUtils.isBlank(salesType)) {
			throw new JrafDaoException(RefTableREF_ERREUR._REF_310);

		}
		String compatibleSalesType = RefTableREF_DOMAINE.instance().getCodePere(agencyDto.getDomaine());
		if (StringUtils.isBlank(compatibleSalesType)) {
			throw new JrafDaoException(RefTableREF_ERREUR._REF_310);
		}

		if (!compatibleSalesType.equals(salesType)) {
			throw new JrafDaoException(RefTableREF_ERREUR._REF_310 + DISTRIBUTION_CHANNEL_INCOHERENT);

		}
	}

	private static void validateDates(@NotNull AgenceDTO agencyDto) throws JrafDaoException {
		if (agencyDto.getDateDebut() == null) {
			agencyDto.setDateDebut(new Date());
		}
		String status = agencyDto.getStatut();
		Date radiation = agencyDto.getDateRadiation();
		if (radiation == null && RefTableREF_STATUTPM._REF_R.equals(status)) {
			throw new JrafDaoException(RefTableREF_ERREUR._REF_119 + STRUCK_OFF_DATE);
		}

		Date closing = agencyDto.getDateFin();
		if (closing == null && RefTableREF_STATUTPM._REF_X.equals(status)) {
			throw new JrafDaoException(RefTableREF_ERREUR._REF_119 + CLOSING_DATE);
		}

		Date agreement = agencyDto.getDateAgrement();
		if ((RefTableREF_TYP_AGEN._REF_0.equals(agencyDto.getType())|| RefTableREF_TYP_AGEN._REF_9.equals(agencyDto.getType())) && agreement != null) {
			throw new JrafDaoException(RefTableREF_ERREUR._REF_119 + AGREEMENT_DATE_INCOHERENT_WITH_AGENCY_TYPE);
		} else if ((!RefTableREF_TYP_AGEN._REF_0.equals(agencyDto.getType()) && !RefTableREF_TYP_AGEN._REF_9.equals(agencyDto.getType()) ) && agreement == null) {
			throw new JrafDaoException(RefTableREF_ERREUR._REF_119 + AGREEMENT_DATE);
		}

		Date start = agencyDto.getDateDebut();
		if (DTC.compare(agreement, start) < 0) {
			throw new JrafDaoException(RefTableREF_ERREUR._REF_119 + AGREEMENT_DATE);
		}

		if (radiation != null && DTC.compare(radiation, agreement) < 0) {
			throw new JrafDaoException(RefTableREF_ERREUR._REF_119 + STRUCK_OFF_DATE);
		}

		if (closing != null && DTC.compare(closing, start) < 0) {
			throw new JrafDaoException(RefTableREF_ERREUR._REF_266);
		}
	}

	private static void validateMarketChoice(@NotNull AgenceDTO agencyDto) throws JrafDaoException {
		Set<SegmentationDTO> segmentations = agencyDto.getSegmentations();
		Date agreementDate = agencyDto.getDateAgrement();
		if (!CollectionUtils.isEmpty(segmentations)) {
			for (SegmentationDTO segmentationDto : segmentations) {
				Date startDate = segmentationDto.getDateEntree();
				if (startDate == null) {
					startDate = agencyDto.getDateDebut();
					segmentationDto.setDateEntree(startDate);
				}
				Date endDate = segmentationDto.getDateSortie();
				if (endDate != null) {
					if (DTC.compare(endDate, startDate) < 0) {
						throw new JrafDaoException(RefTableREF_ERREUR._REF_266 + MARKET_CHOICE);
					}

					if (agreementDate != null && DTC.compare(endDate, agreementDate) < 0) {
						throw new JrafDaoException(RefTableREF_ERREUR._REF_119 + MARKET_CHOICE_END_DATE_ERROR);
					}
				}
				String type = segmentationDto.getType();
				if (StringUtils.isBlank(type)) {
					throw new JrafDaoException(RefTableREF_ERREUR._REF_255);
				}
				if (!RefTableREF_TYP_SEG._REF_SGA.equals(type)) {
					throw new JrafDaoException(RefTableREF_ERREUR._REF_234);
				}

				String level = segmentationDto.getNiveau();
				if (StringUtils.isBlank(level)) {
					segmentationDto.setNiveau(RefTableREF_NIV_SEG_A._REF_D);
				} else if (!RefTableREF_NIV_SEG_A.instance().estValide(level, StringUtils.EMPTY)) {
					throw new JrafDaoException(RefTableREF_ERREUR._REF_153);
				}
			}
		}
	}

	private static void validateAddress(@NotNull AgenceDTO agencyDto, RefCountryDS countryService)
			throws JrafDaoException {
		List<PostalAddressDTO> addresses = agencyDto.getPostalAddresses();
		if (CollectionUtils.isEmpty(addresses)) {
			throw new JrafDaoException(RefTableREF_ERREUR._REF_167);
		}
		PostalAddressDTO localisation = null;
		for (PostalAddressDTO address : addresses) {
			if (RefTableCAT_MED._REF_L.equals(address.getScode_medium())) {
				localisation = address;
				break;
			}
		}
		if (localisation == null) {
			throw new JrafDaoException(RefTableREF_ERREUR._REF_167);
		}

		localisation.setVersion(1);
		String status = localisation.getSstatut_medium();
		if (StringUtils.isBlank(status)) {
			localisation.setSstatut_medium(RefTableREF_STA_MED._REF_V);
		} else if (!RefTableREF_STA_MED.instance().estValide(status, StringUtils.EMPTY)) {
			throw new JrafDaoException(RefTableREF_ERREUR._REF_136);
		}
		if (StringUtils.isBlank(localisation.getSforcage())) {
			localisation.setSforcage(OuiNonFlagEnum.NON.toString());
		}
		String countryCode = localisation.getScode_pays();
		if (StringUtils.isBlank(localisation.getScode_postal()) && StringUtils.isBlank(localisation.getSno_et_rue())
				&& StringUtils.isBlank(countryCode) && StringUtils.isBlank(localisation.getSville())
				&& !RefTableREF_STA_MED._REF_X.equals(status)) {
			throw new JrafDaoException(RefTableREF_ERREUR._REF_178);

		}
		if (!RefTablePAYS.instance().estValide(countryCode, StringUtils.EMPTY)
				|| RefTablePAYS._REF_M1.equals(countryCode)) {
			throw new JrafDaoException(RefTableREF_ERREUR._REF_131);
		}

		String isoCityCode = agencyDto.getCodeVilleIso();
		if (StringUtils.isBlank(isoCityCode)) {
			Pays country = countryService.get(countryCode);
			if (country != null) {
				agencyDto.setCodeVilleIso(country.getCodeCapitale());
			}
		}
		// Update address signature
		String signature = agencyDto.getSignatureCreation();
		String siteSignature = agencyDto.getSiteCreation();
		Date creationDate = agencyDto.getDateCreation();
		localisation.setDdate_creation(creationDate);
		localisation.setSignature_creation(signature);
		localisation.setSsite_creation(siteSignature);
		localisation.setDdate_modification(creationDate);
		localisation.setSsignature_modification(signature);
		localisation.setSsite_modification(siteSignature);
		localisation.setIcod_err(0);
	}

	private static boolean isInFranceOrDomTom(String countryCode) {
		return (RefTablePAYS._REF_FR.equals(countryCode) || RefTablePAYS._REF_GP.equals(countryCode)
				|| RefTablePAYS._REF_MQ.equals(countryCode) || RefTablePAYS._REF_RE.equals(countryCode)
				|| RefTablePAYS._REF_GF.equals(countryCode) || RefTablePAYS._REF_MF.equals(countryCode)
				|| RefTablePAYS._REF_BL.equals(countryCode));
	}

	private static boolean isInFranceOrDomTomOrMonaco(String countryCode) {
		return (isInFranceOrDomTom(countryCode) || RefTablePAYS._REF_MC.equals(countryCode));
	}

	private static void validateIdDetails(@NotNull AgenceDTO agencyDto) throws JrafDaoException {
		String location = agencyDto.getLocalisation();
		if (StringUtils.isNotBlank(location)
				&& !RefTableREF_LOCALISA.instance().estValide(location, StringUtils.EMPTY)) {
			throw new JrafDaoException(RefTableREF_ERREUR._REF_222);
		}
		String implantType = agencyDto.getInfra();
		if (StringUtils.isNotBlank(implantType)
				&& !RefTableREF_TYP_INFRA.instance().estValide(implantType, StringUtils.EMPTY)) {
			throw new JrafDaoException(RefTableREF_ERREUR._REF_223);
		}
		String salesType = agencyDto.getTypeVente();
		if (StringUtils.isNotBlank(salesType)
				&& !RefTableREF_TYP_VENT.instance().estValide(salesType, StringUtils.EMPTY)) {
			throw new JrafDaoException(RefTableREF_ERREUR._REF_226);
		}
		String aggrementType = agencyDto.getTypeAgrement();
		if (StringUtils.isNotBlank(aggrementType)
				&& !RefTableREF_TYP_AGRE.instance().estValide(aggrementType, StringUtils.EMPTY)) {
			throw new JrafDaoException(RefTableREF_ERREUR._REF_227);
		}

		String activity = agencyDto.getActiviteLocal();
		boolean ra1Agency = YesNoFlagEnum.NO.toString().equals(agencyDto.getAgenceRA2());
		if (ra1Agency && ACTIVITY_RA2.equals(activity) || !ra1Agency && !ACTIVITY_RA2.equals(activity)) {
			throw new JrafDaoException(RefTableREF_ERREUR._REF_160);
		}
	}

	private static void validateDistributionChannel(@NotNull AgenceDTO agencyDto) throws JrafDaoException {
		String distributionChannel = agencyDto.getDomaine();
		String subDistributionChannel = agencyDto.getSousDomaine();
		if (StringUtils.isBlank(distributionChannel)) {
			throw new JrafDaoException(RefTableREF_ERREUR._REF_309);
		} else if (!RefTableREF_DOMAINE.instance().estValide(distributionChannel, StringUtils.EMPTY)) {
			throw new JrafDaoException(RefTableREF_ERREUR._REF_229);
		}
		if (StringUtils.isNotBlank(subDistributionChannel)
				&& !RefTableREF_SS_DOMAINE.instance().estValide(subDistributionChannel, StringUtils.EMPTY)) {
			throw new JrafDaoException(RefTableREF_ERREUR._REF_230);
		}
	}

	private static void validateAgencyType(@NotNull AgenceDTO agencyDto) throws JrafDaoException {
		String type = agencyDto.getType();
		if (StringUtils.isBlank(type)) {
			throw new JrafDaoException(RefTableREF_ERREUR._REF_221);
		} else if (!RefTableREF_TYP_AGEN.instance().estValide(type, StringUtils.EMPTY)) {
			throw new JrafDaoException(RefTableREF_ERREUR._REF_228);
		}
	}

	private static void validateIdentificationNumbers(AgenceDTO agencyDto, NumeroIdentDS numberIdDs)
			throws JrafDomainException {
		Set<NumeroIdentDTO> identifiers = agencyDto.getNumerosIdent();
		String agencyType = agencyDto.getType();
		Date creationDate = agencyDto.getDateAgrement();
		NumeroIdentDTO iata = null;
		NumeroIdentDTO arc = null;
		NumeroIdentDTO fictitious = null;
		if (!CollectionUtils.isEmpty(identifiers)) {
			for (NumeroIdentDTO identifier : identifiers) {
				String type = identifier.getType();
				validateIdNumber(agencyType, identifier);
				validateExistingIdNumber(numberIdDs, identifier);

				// Set default values
				identifier.setStatut(MediumStatusEnum.VALID.toString());
				identifier.setDateOuverture(creationDate);

				if (RefTableREF_TYP_NUMID._REF_IA.equals(type)) {
					iata = identifier;
				}
				if (RefTableREF_TYP_NUMID._REF_AR.equals(type)) {
					arc = identifier;
				}
				if (RefTableREF_TYP_NUMID._REF_AG.equals(type)) {
					fictitious = identifier;
				}
			}
			
			if (RefTableREF_TYP_AGEN._REF_0.equals(agencyType) && fictitious == null) {
				throw new JrafDaoException(RefTableREF_ERREUR._REF_305);
			} else if (!RefTableREF_TYP_AGEN._REF_0.equals(agencyType) && iata == null && arc == null) {
				throw new JrafDaoException(RefTableREF_ERREUR._REF_184);
			}
			
			validateArcNumber(iata, arc);
		} else if (!RefTableREF_TYP_AGEN._REF_9.equals(agencyType)){
			throw new JrafDaoException(RefTableREF_ERREUR._REF_133 + IDENTIFICATION_NUMBER);
		}
	}

	private static void validateArcNumber(NumeroIdentDTO iata, NumeroIdentDTO arc) throws JrafDaoException {
		// If ARC is specified, IATA must be specified as well
		if (arc != null) {
			if (iata == null) {
				throw new JrafDaoException(RefTableREF_ERREUR._REF_184 + IATA_ARC_MANDATORY);
			} else if (!iata.getNumero().equals(arc.getNumero())) {
				throw new JrafDaoException(RefTableREF_ERREUR._REF_344 + ARC_AND_IATA_INCOHERENT);
			}
		}
	}

	private static void validateIdNumber(String agencyType, NumeroIdentDTO identifier)
			throws JrafDomainException {
		// Validate type
		validateIdNumberType(identifier, agencyType);

		// Validate number
		String number = identifier.getNumero();
		if ((StringUtils.isBlank(number) || number.length() < 7 || number.length() > 8)
				&& !(RefTableREF_TYP_NUMID._REF_SR.equals(identifier.getType()) && number.length() == 9)) {
			throw new JrafDaoException(RefTableREF_ERREUR._REF_359);
		}

		if (!number.matches(REGEX_ID_NUMBER)) {
			throw new JrafDaoException(RefTableREF_ERREUR._REF_351);
		}

		// Set validated number
		identifier.setNumero(getValidatedId(number));
	}

	private static void validateExistingIdNumber(NumeroIdentDS numberIdDs, NumeroIdentDTO identifier)
			throws JrafDomainException {
		List<String> typeList = new ArrayList<>();
		typeList.add(RefTableREF_TYP_NUMID._REF_AR);
		typeList.add(RefTableREF_TYP_NUMID._REF_IA);
		typeList.add(RefTableREF_TYP_NUMID._REF_AG);
		Map<String, NumeroIdentDTO> numeroIdents = numberIdDs.findActiveByNumeroType(identifier.getNumero(), typeList);
		if (!CollectionUtils.isEmpty(numeroIdents)) {
			if (numeroIdents.get(RefTableREF_TYP_NUMID._REF_AR) != null) {
				throw new JrafDaoException(RefTableREF_ERREUR._REF_342);
			} else if (numeroIdents.get(RefTableREF_TYP_NUMID._REF_IA) != null) {
				throw new JrafDaoException(RefTableREF_ERREUR._REF_330);
			} else {
				throw new JrafDaoException(RefTableREF_ERREUR._REF_388 + FICTITIOUS_NUMBER);
			}
		}
	}

	private static void validateIdNumberType(NumeroIdentDTO identifier, String agencyType) throws JrafDaoException {
		String type = identifier.getType();
		if (StringUtils.isBlank(type) || !(RefTableREF_TYP_NUMID._REF_IA.equals(type)
				|| RefTableREF_TYP_NUMID._REF_AR.equals(type) || RefTableREF_TYP_NUMID._REF_AG.equals(type)
				|| RefTableREF_TYP_NUMID._REF_SR.equals(type) || RefTableREF_TYP_NUMID._REF_AN.equals(type))) {
			throw new JrafDaoException(RefTableREF_ERREUR._REF_216);
		}
	}

	private static String getValidatedId(String number) throws JrafDaoException {
		// Validate CHECK DIGIT
		if (number.length() == 7) {
			String checkDigit = String.valueOf(Integer.parseInt(number) % 7);
			return number.concat(checkDigit);
		} else if (number.length() == 8) {
			String checkDigit = String.valueOf(Integer.parseInt(number.substring(0, 7)) % 7);
			if (!checkDigit.equals(number.substring(7, 8))) {
				throw new JrafDaoException(RefTableREF_ERREUR._REF_923);
			}
		}
		return number;
	}

	private AgencyValidationHelper() {
		// Utility class
	}
}
