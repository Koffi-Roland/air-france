package com.airfrance.repind.service.internal.unitservice.zone;

import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.exception.jraf.JrafDomainNoRollbackException;
import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;
import com.airfrance.ref.type.OuiNonFlagEnum;
import com.airfrance.repind.dao.firme.EtablissementRepository;
import com.airfrance.repind.dao.firme.PersonneMoraleRepository;
import com.airfrance.repind.dao.zone.PmZoneRepository;
import com.airfrance.repind.dao.zone.ZoneCommRepository;
import com.airfrance.repind.dao.zone.ZoneVenteRepository;
import com.airfrance.repind.dto.agence.AgenceDTO;
import com.airfrance.repind.dto.zone.PmZoneDTO;
import com.airfrance.repind.dto.zone.PmZoneTransform;
import com.airfrance.repind.dto.zone.ZoneCommDTO;
import com.airfrance.repind.dto.zone.ZoneVenteDTO;
import com.airfrance.repind.entity.agence.Agence;
import com.airfrance.repind.entity.firme.Etablissement;
import com.airfrance.repind.entity.firme.PersonneMorale;
import com.airfrance.repind.entity.firme.enums.LegalPersonStatusEnum;
import com.airfrance.repind.entity.zone.PmZone;
import com.airfrance.repind.entity.zone.ZoneComm;
import com.airfrance.repind.entity.zone.ZoneDecoup;
import com.airfrance.repind.entity.zone.ZoneVente;
import com.airfrance.repind.entity.zone.enums.NatureZoneEnum;
import com.airfrance.repind.entity.zone.enums.PrivilegedLinkEnum;
import com.airfrance.repind.entity.zone.enums.SubtypeZoneEnum;
import com.airfrance.repind.service.agence.internal.helper.AgencyValidationHelper;
import com.airfrance.repind.util.SicDateUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
public class PmZoneUS {

	/** logger */
	private static final Log log = LogFactory.getLog(PmZoneUS.class);

	/* PROTECTED REGION ID(_GnM4AOHgEeS79pPzHY2rFw u var) ENABLED START */
	// add your custom variables here if necessary
	/* PROTECTED REGION END */

	/** Reference on unit service ZoneCommUS **/
	@Autowired
	private ZoneCommUS zoneCommUS;

	/** Reference on unit service ZoneVenteUS **/
	@Autowired
	private ZoneVenteUS zoneVenteUS;

	/** references on associated DAOs */
	@Autowired
	private PmZoneRepository pmZoneRepository;

	/** references on associated DAOs */
	@Autowired
	private ZoneCommRepository zoneCommRepository;

	/** references on associated DAOs */
	@Autowired
	private ZoneVenteRepository zoneVenteRepository;

	/** references on associated DAOs */
	@Autowired
	private EtablissementRepository etablissementRepository;

	/** references on associated DAOs */
	@Autowired
	private PersonneMoraleRepository personneMoraleRepository;

	/** the Entity Manager */
	@PersistenceContext(unitName = "entityManagerFactoryRepind")
	private EntityManager entityManager;

	private final String pSignature = "REORG";
	private static final String SIGNATURE_REMOVE_PM_ZONE = "REMOVE PMZONE";

	/**
	 * empty constructor
	 */
	public PmZoneUS() {
	}

	/**
	 * Getter
	 * 
	 * @return IZoneCommUS
	 */
	public ZoneCommUS getZoneCommUS() {
		return zoneCommUS;
	}

	/**
	 * Setter
	 * 
	 * @param zoneCommUS the IZoneCommUS
	 */
	public void setZoneCommUS(ZoneCommUS zoneCommUS) {
		this.zoneCommUS = zoneCommUS;
	}

	/**
	 * Getter
	 * 
	 * @return IZoneVenteUS
	 */
	public ZoneVenteUS getZoneVenteUS() {
		return zoneVenteUS;
	}

	/**
	 * Setter
	 * 
	 * @param zoneVenteUS the IZoneVenteUS
	 */
	public void setZoneVenteUS(ZoneVenteUS zoneVenteUS) {
		this.zoneVenteUS = zoneVenteUS;
	}

	/* PROTECTED REGION ID(_GnM4AOHgEeS79pPzHY2rFw u m) ENABLED START */
	// add your custom methods here if necessary

	/**
	 * Check start date for creation
	 * 
	 * @param pZoneLink
	 * @throws JrafDomainException
	 */
	private void checkStartDate(PmZone pZoneLink, PersonneMorale pPersonneMorale, ZoneDecoup pZoneDecoup)
			throws JrafDomainException {
		if (pZoneLink == null) {
			throw new JrafDomainRollbackException("ZONE LINK MANDATORY");
		}
		if (pZoneLink.getDateOuverture() == null) {
			throw new JrafDomainRollbackException("INVALID START DATE IN ZONE LINK");
		}
		if (pPersonneMorale == null) {
			throw new JrafDomainRollbackException("LEGAL PERSON MANDATORY");
		}
		if (pZoneDecoup == null) {
			throw new JrafDomainRollbackException("INVALID ZONE IN ZONE LINK");
		}

		// la date d'ouverture du lien ne peut �tre ant�rieure � la date de cr�ation de
		// la firme
		if (pZoneLink.getDateOuverture().before(SicDateUtils.midnight(pPersonneMorale.getDateCreation()))) {

			// TODO LBN cr�er erreur "ZONE LINK START DATE BEFORE LEGAL PERSON CREATION
			// DATE"
			throw new JrafDomainRollbackException("ZONE LINK START DATE BEFORE LEGAL PERSON CREATION DATE");
		}

		// la date d'ouverture du lien ne peut �tre ant�rieure � la date d'ouverture de
		// la zone
		if (pZoneLink.getDateOuverture().before(pZoneDecoup.getDateOuverture())) {

			// TODO LBN cr�er erreur "ZONE LINK START DATE BEFORE ZONE START DATE"
			throw new JrafDomainRollbackException("ZONE LINK START DATE BEFORE ZONE START DATE");
		}
	}

	/**
	 * @param pZoneLink
	 * @throws JrafDomainException
	 */
	private void checkEndDate(PmZone pZoneLink, PersonneMorale pPersonneMorale, ZoneDecoup pZoneDecoup)
			throws JrafDomainException {

		Assert.notNull(pZoneLink);
		Assert.notNull(pZoneLink.getDateFermeture());
		Assert.notNull(pZoneLink.getDateOuverture());
		Assert.notNull(pPersonneMorale);
		Assert.notNull(pZoneDecoup);

		if (pZoneLink.getDateFermeture().before(pZoneLink.getDateOuverture())) {

			// ERREUR 119 - INVALID DATE - ZONE LINK END DATE STRICTLY BEFORE ZONE LINK
			// START DATE
			throw new JrafDomainRollbackException("119");
		}

		Date nowMidnight = SicDateUtils.midnight(new Date());
		if (pZoneLink.getDateFermeture().before(nowMidnight)) {

			// ERREUR 119 - INVALID DATE - intervalle < a date du jour
			throw new JrafDomainRollbackException("119");
		}

		// la date de fermeture du lien ne peut �tre post�rieure � la date de fermeture
		// de la zone (si renseign�e)
		if (pZoneDecoup.getDateFermeture() != null
				&& pZoneLink.getDateFermeture().after(pZoneDecoup.getDateFermeture())) {

			// TODO LBN cr�er erreur "CLOSURE DATE ZONE LINK AFTER ZONE CLOSURE DATE"
			throw new JrafDomainRollbackException("CLOSURE DATE ZONE LINK AFTER ZONE CLOSURE DATE");
		}
	}

	/**
	 * Check status of the firm
	 * 
	 * @param pZoneLink
	 * @throws JrafDomainException
	 */
	private void checkPmStatus(PmZone zcLink, PersonneMorale pPersonneMorale) throws JrafDomainException {

		Assert.notNull(zcLink);
		Assert.notNull(pPersonneMorale);
		Assert.notNull(pPersonneMorale.getGin());
		Assert.isInstanceOf(ZoneComm.class, zcLink.getZoneDecoup());

		ZoneComm zc = (ZoneComm) zcLink.getZoneDecoup();
		NatureZoneEnum nature = NatureZoneEnum.fromLiteral(zc.getNature());
		LegalPersonStatusEnum status = LegalPersonStatusEnum.fromLiteral(pPersonneMorale.getStatut());
		Date now = new Date();
		Date nowMidnight = SicDateUtils.midnight(now);

		// si le statut de la PM est 'P' et qu'on essaye de lui assigner une ZC ANN
		// valide -> error 177
		if (LegalPersonStatusEnum.TEMPORARY.equals(status)) {

			if (NatureZoneEnum.CANCELLATION.equals(nature)
					&& (zcLink.getDateFermeture() == null || zcLink.getDateFermeture().after(nowMidnight))) {

				// ERREUR 177 - INVALID STATUS
				throw new JrafDomainRollbackException("177");
			}

			// si le statut de la PM est 'A' et qu'on essaye de lui assigner une ZC ANN
			// valide -> error 177
		} else if (LegalPersonStatusEnum.ACTIVE.equals(status)) {

			if (NatureZoneEnum.CANCELLATION.equals(nature)
					&& (zcLink.getDateFermeture() == null || zcLink.getDateFermeture().after(nowMidnight))) {

				// ERREUR 177 - INVALID STATUS
				throw new JrafDomainRollbackException("177");
			}

			// si le statut de la PM est 'X' et qu'on essaye de lui assigner une ZC SEC ou
			// ATT valide -> error 177
		} else if (LegalPersonStatusEnum.CLOSED.equals(status)) {

			if ((NatureZoneEnum.STANDBY.equals(nature) || NatureZoneEnum.SECTORISED.equals(nature))
					&& (zcLink.getDateFermeture() == null || zcLink.getDateFermeture().after(nowMidnight))) {

				// ERREUR 177 - INVALID STATUS
				throw new JrafDomainRollbackException("177");
			}
		}
	}

	/**
	 * @param pZoneLink
	 * @throws JrafDomainException
	 */
	private void checkPrivilegedLink(PmZone pZoneLink, PersonneMorale pPersonneMorale) throws JrafDomainException {

		Assert.notNull(pZoneLink);
		Assert.notNull(pPersonneMorale);

		// si la personne morale n'est pas une agence, alors le lien privilegie doit
		// �tre valoris� "O" ou "N"
		if (!Agence.class.equals(pPersonneMorale.getClass())) {

			if (StringUtils.isEmpty(pZoneLink.getLienPrivilegie())) {

				// TODO LBN cr�er erreur "PRIVILEGED ZONE LINK MANDATORY"
				throw new JrafDomainRollbackException("PRIVILEGED ZONE LINK MANDATORY");
			}

			if (PrivilegedLinkEnum.fromLiteral(pZoneLink.getLienPrivilegie()) == null) {

				// TODO LBN cr�er erreur "INVALID PRIVILEGED ZONE LINK"
				throw new JrafDomainRollbackException("INVALID PRIVILEGED ZONE LINK");
			}
		}
	}

	/**
	 * A pmZone is considered with empty data, if following fields are null or empty
	 * : - date fermeture - date ouverture - lien privilegie
	 * 
	 * @param pmZone PmZone to analyse
	 * @return
	 */
	private boolean isEmptyData(PmZone pmZone) {

		// ATTENTION : si on veut mettre � null la dateFermeture d'un lien secondaire,
		// il faut renseigner dateOuverture et/ou lienPrivilegie car sinon, on pensera �
		// une suppression
		return pmZone.getDateFermeture() == null && pmZone.getDateOuverture() == null
				&& StringUtils.isEmpty(pmZone.getLienPrivilegie());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.airfrance.repind.service.internal.unitservice.firm.IPmZoneUS#
	 * createOrUpdateOrDeleteZcLinks(List, PersonneMorale)
	 */
	public void createOrUpdateOrDeleteZcLinks(List<PmZone> pZcLinks, PersonneMorale pPersonneMorale)
			throws JrafDomainException {

		Assert.notNull(pZcLinks);
		Assert.notNull(pPersonneMorale);
		Assert.notNull(pPersonneMorale.getGin());
		for (PmZone zcLink : pZcLinks) {
			Assert.notNull(zcLink);
			Assert.notNull(zcLink.getZoneDecoup());
			Assert.isInstanceOf(ZoneComm.class, zcLink.getZoneDecoup());
		}

		if (pPersonneMorale.getPmZones() == null) {
			pPersonneMorale.setPmZones(new HashSet<PmZone>());
		}

		List<PmZone> zcLinksToCreate = new ArrayList<PmZone>();
		Map<Long, PmZone> zcLinksToUpdate = new HashMap<Long, PmZone>();
		Map<Long, PmZone> zcLinksToDelete = new HashMap<Long, PmZone>();
		int countPrivilegedLinkToCreate = 0;

		// aujourd'hui
		Date now = new Date();

		// aujourd'hui minuit
		Date nowMidnight = SicDateUtils.midnight(now);

		for (PmZone zcLink : pZcLinks) {

			if (zcLink.getCle() == null) {

				// Lien � cr�er
				// --------------

				// ZC1 � ZC5 sont requis car on ne peut cr�er de liens qu'avec des ZC de niveau
				// 5
				if (!zoneCommUS.checkZc1ToZc5((ZoneComm) zcLink.getZoneDecoup())) {

					// ERREUR 257 - INCOMPLETE COMMERCIAL ZONE - ZC1�5 Obligatoires
					throw new JrafDomainRollbackException("257");
				}

				// recherche de la ZC
				try {
					String zc1 = ((ZoneComm) zcLink.getZoneDecoup()).getZc1();
					String zc2 = ((ZoneComm) zcLink.getZoneDecoup()).getZc2();
					String zc3 = ((ZoneComm) zcLink.getZoneDecoup()).getZc3();
					String zc4 = ((ZoneComm) zcLink.getZoneDecoup()).getZc4();
					String zc5 = ((ZoneComm) zcLink.getZoneDecoup()).getZc5();
					ZoneComm zc = zoneCommRepository.findActiveByZc1Zc2Zc3Zc4Zc5(zc1, zc2, zc3, zc4, zc5);
					zcLink.setZoneDecoup(zc);
				} catch (NoResultException e) {
					// ERREUR 346 - ZONE NOT FOUND
					throw new JrafDomainRollbackException("346"); // TODO CEP/MBE il faudrait pr�ciser ZC
				} catch (NonUniqueResultException e) {
					// ERREUR 346 - ZONE NOT FOUND
					throw new JrafDomainRollbackException("346"); // TODO CEP/MBE il faudrait pr�ciser ZC
				}

				// si la date d'ouverture est vide, on l'initialise � aujourd'hui minuit
				// sinon, on l'initialise � minuit de la m�me date
				if (zcLink.getDateOuverture() == null) {
					zcLink.setDateOuverture(nowMidnight);
				} else {
					zcLink.setDateOuverture(SicDateUtils.midnight(zcLink.getDateOuverture()));
				}
				checkStartDate(zcLink, pPersonneMorale, zcLink.getZoneDecoup());

				// si la date de fermeture est renseign�e, on l'initialise � minuit de la m�me
				// date
				if (zcLink.getDateFermeture() != null) {

					zcLink.setDateFermeture(SicDateUtils.midnight(zcLink.getDateFermeture()));
					checkEndDate(zcLink, pPersonneMorale, zcLink.getZoneDecoup());
				}

				checkPrivilegedLink(zcLink, pPersonneMorale);

				// si le nouveau lien � cr�er est un lien privil�gi�
				if (PrivilegedLinkEnum.YES.equals(PrivilegedLinkEnum.fromLiteral(zcLink.getLienPrivilegie()))) {

					countPrivilegedLinkToCreate++;

					// on ne peut cr�er qu'un seul lien privil�gi� � la fois
					if (countPrivilegedLinkToCreate > 1) {

						// TODO LBN cr�er erreur "CANNOT CREATE MORE THAN ONE PRIVILEGED ZC LINK"
						throw new JrafDomainRollbackException("CANNOT CREATE MORE THAN ONE PRIVILEGED ZC LINK");
					}

					// la date de fermeture du lien privil�gi� � cr�er ne doit pas etre renseign�e
					if (zcLink.getDateFermeture() != null) {

						// TODO LBN cr�er erreur "CANNOT CREATE PRIVILEGED ZC LINK WITH END DATE"
						throw new JrafDomainRollbackException("CANNOT CREATE PRIVILEGED ZC LINK WITH END DATE");
					}

					// on ferme le(s) lien(s) privil�gi�(s) dont l'intervalle comprend la date
					// d'ouverture du lien privil�gi� � cr�er
					// on change la date de fermeture au jour précédent la date d'ouverture de la
					// nouvelle ZC
					// au(x) lien(s) privilégié(s) ayant une date d'ouverture antérieure à la date
					// d'ouverture de la nouvelle
					// et dont la date de fermeture n'est pas encore atteinte
					// on supprime le(s) lien(s) privil�gi�(s) qui sont futur au lien privil�gi� �
					// cr�er
					for (PmZone existingPrivilegedZcLink : pPersonneMorale.fetchPrivilegedCommercialZoneLinks()) {

						if (existingPrivilegedZcLink.getDateOuverture().compareTo(zcLink.getDateOuverture()) < 0
								&& (existingPrivilegedZcLink.getDateFermeture() == null
										|| existingPrivilegedZcLink.getDateFermeture().compareTo(now) >= 0)) {

							Calendar cal = Calendar.getInstance();
							cal.setTime(zcLink.getDateOuverture());
							cal.add(Calendar.DAY_OF_MONTH, -1);
							existingPrivilegedZcLink.setDateFermeture(cal.getTime());
							// existingPrivilegedZcLink.setOrigine(null);// si origine est renseign�, on met
							// � null (demande de LBN)
							existingPrivilegedZcLink.setSignature(zcLink.getSignature());
							existingPrivilegedZcLink.setDateModif(now);
							zcLinksToUpdate.put(existingPrivilegedZcLink.getCle(), existingPrivilegedZcLink);

						} else if (existingPrivilegedZcLink.getDateOuverture()
								.compareTo(zcLink.getDateOuverture()) >= 0) {

							zcLinksToDelete.put(existingPrivilegedZcLink.getCle(), existingPrivilegedZcLink);
						}

						// si la ZC à ajouter est une future, toute future existant déjà doit être
						// supprimée
						if (zcLink.getDateOuverture().compareTo(nowMidnight) > 0
								&& existingPrivilegedZcLink.getDateOuverture().compareTo(nowMidnight) > 0) {
							zcLinksToDelete.put(existingPrivilegedZcLink.getCle(), existingPrivilegedZcLink);
						}

					}

					// on supprime physiquement les plus anciens liens ZC privil�gi�s et historis�s
					// (inactifs)
					// pour n'en conserver qu'un max
					List<PmZone> pastPrivilegedZoneLinks = new ArrayList<PmZone>(
							pPersonneMorale.fetchPastPrivilegedCommercialZoneLinks());
					if (!pastPrivilegedZoneLinks.isEmpty()) {

						// on trie la liste par date de fermeture la plus r�cente � la plus ancienne
						Collections.sort(pastPrivilegedZoneLinks, new Comparator<PmZone>() {
							public int compare(PmZone link1, PmZone link2) {
								return -link1.getDateFermeture().compareTo(link2.getDateFermeture());
							}
						});

						// date de fermeture la plus r�cente
						Date dateFermetureLapLusRecente = pastPrivilegedZoneLinks.get(0).getDateFermeture();

						// on retire les liens qui ont pour date de fermeture =
						// dateFermetureLapLusRecente
						for (Iterator<PmZone> it = pastPrivilegedZoneLinks.iterator(); it.hasNext();) {

							if (dateFermetureLapLusRecente.equals(it.next().getDateFermeture())) {
								it.remove(); // on retire le lien de la liste
							} else {
								break; // inutile d'aller plus loin �tant donn� que la liste est tri�e par date de
										// fermeture desc
							}
						}

						// les liens restants sont � d�truire
						for (PmZone inactivePrivilegedZoneLink : pastPrivilegedZoneLinks) {

							zcLinksToDelete.put(inactivePrivilegedZoneLink.getCle(), inactivePrivilegedZoneLink);
						}
					}
				}

				zcLink.setDateModif(now); // � cause des r�plications qui s'appuient sur dateModif (defect 54)
				zcLink.setOrigine(null);
				zcLink.setPersonneMorale(pPersonneMorale);

				zcLinksToCreate.add(zcLink);

			} else {

				// Lien � modifier ou supprimer
				// ------------------------------

				PmZone linkTrouve = pPersonneMorale.fetchZoneLinkById(zcLink.getCle());
				if (linkTrouve != null && ZoneComm.class.equals(linkTrouve.getZoneDecoup().getClass())) {

					// il n'est pas possible de modfier un lien privil�gi� (en effet, il faut passer
					// par la cr�ation pour fermer celui existant et en cr�er un nouveau)
					if (PrivilegedLinkEnum.YES.equals(PrivilegedLinkEnum.fromLiteral(linkTrouve.getLienPrivilegie()))) {

						// TODO LBN cr�er erreur "CANNOT UPDATE PRIVILEGED ZC LINK"
						throw new JrafDomainRollbackException("CANNOT UPDATE PRIVILEGED ZC LINK");
					}

					// ATTENTION : si on veut mettre � null la dateFermeture d'un lien secondaire,
					// il faut renseigner dateOuverture et/ou lienPrivilegie car sinon, on pensera �
					// une suppression
					if (!isEmptyData(zcLink)) {

						// on ne peut modifier que la date de fermeture => c'est donc la seule donn�e �
						// contr�ler
						if (zcLink.getDateFermeture() != null) {

							zcLink.setDateFermeture(SicDateUtils.midnight(zcLink.getDateFermeture()));
							checkEndDate(zcLink, pPersonneMorale, zcLink.getZoneDecoup());
						}

						linkTrouve.setDateFermeture(zcLink.getDateFermeture());
						linkTrouve.setOrigine(null); // on force � null (demande de LBN)
						linkTrouve.setDateModif(now);
						linkTrouve.setSignature(zcLink.getSignature());

						zcLinksToUpdate.put(zcLink.getCle(), linkTrouve);

					} else {

						// Suppression du lien
						zcLinksToDelete.put(zcLink.getCle(), linkTrouve);
					}
				} else {

					// Erreur 338 - ZONE LINK NOT FOUND
					throw new JrafDomainRollbackException("338"); // TODO il faudrait pr�ciser qu'on traite des ZC
				}
			}
		}

		// on fait un 1er bilan des liens ZC
		// -----------------------------------

		// si la PM est un �tablissement et qu'on n'a aucun lien ZC,
		// alors si statut A ou P :
		// on cr�e un lien privil�gi� actif avec une ZC d'attente
		// si statut X :
		// on cr�e un lien privil�gi� actif avec une ZC d'annulation

		if (Etablissement.class.equals(pPersonneMorale.getClass())) {

			List<PmZone> commercialZoneLinks = new ArrayList<PmZone>(pPersonneMorale.fetchCommercialZoneLinks());
			commercialZoneLinks.removeAll(zcLinksToDelete.values());
			commercialZoneLinks.addAll(zcLinksToCreate);

			if (commercialZoneLinks.isEmpty()) {

				ZoneComm commercialZone = null;

				if (LegalPersonStatusEnum.ACTIVE.equals(LegalPersonStatusEnum.fromLiteral(pPersonneMorale.getStatut()))
						|| LegalPersonStatusEnum.TEMPORARY
								.equals(LegalPersonStatusEnum.fromLiteral(pPersonneMorale.getStatut()))) {

					commercialZone = zoneCommUS.findZone(pPersonneMorale.fetchValidLocalisationPostalAddress(),
							NatureZoneEnum.STANDBY);

					if (commercialZone == null) {

						commercialZone = zoneCommUS.findTrashZone();
					}

				} else if (LegalPersonStatusEnum.CLOSED
						.equals(LegalPersonStatusEnum.fromLiteral(pPersonneMorale.getStatut()))) {

					commercialZone = zoneCommUS.findZone(pPersonneMorale.fetchValidLocalisationPostalAddress(),
							NatureZoneEnum.CANCELLATION);
				}

				if (commercialZone != null) {

					PmZone zcLink = new PmZone();

					zcLink.setCle(null);
					zcLink.setDateFermeture(null);
					zcLink.setDateModif(now); // � cause des r�plications qui s'appuient sur dateModif (defect 54)
					zcLink.setDateOuverture(nowMidnight);
					zcLink.setLienPrivilegie(PrivilegedLinkEnum.YES.toLiteral());
					zcLink.setOrigine(null);
					zcLink.setPersonneMorale(pPersonneMorale);
					zcLink.setSignature(pPersonneMorale.getSignatureModification()); // TODO CEP/MBE � revoir car cela
																						// d�pend si on est en cr�ation
																						// ou modification de PM
					zcLink.setZoneDecoup(commercialZone);

					zcLinksToCreate.add(zcLink);
				}
			}
		}

		// on fait un 2e bilan des liens ZC
		// ----------------------------------

		List<PmZone> commercialZoneLinks = new ArrayList<PmZone>(pPersonneMorale.fetchCommercialZoneLinks());
		commercialZoneLinks.removeAll(zcLinksToDelete.values());
		commercialZoneLinks.addAll(zcLinksToCreate);

		int countActivePrivilegedLink = 0;
		int countPrivilegedLinkWithoutEndDate = 0;
		for (PmZone zcLink : commercialZoneLinks) {

			ZoneComm zc = (ZoneComm) zcLink.getZoneDecoup();

			// si les ZC li�es ne sont pas toutes de niveau 5, on sort
			if (!zoneCommUS.checkZc1ToZc5(zc)) {

				// ERREUR 257 - INCOMPLETE COMMERCIAL ZONE - ZC1�5 Obligatoires
				throw new JrafDomainRollbackException("257"); // TODO CEP/MBE il faudrait �galt pr�ciser la cl� du lien
			}

			// si le lien est privil�gi�
			if (PrivilegedLinkEnum.YES.equals(PrivilegedLinkEnum.fromLiteral(zcLink.getLienPrivilegie()))) {

				// Check selon le statut de la PM
				checkPmStatus(zcLink, pPersonneMorale);

				if (zcLink.getDateOuverture().compareTo(nowMidnight) <= 0 && (zcLink.getDateFermeture() == null
						|| zcLink.getDateFermeture().compareTo(nowMidnight) >= 0)) {

					countActivePrivilegedLink++;
				}

				if (zcLink.getDateFermeture() == null) {

					countPrivilegedLinkWithoutEndDate++;
				}

				// il doit �tre de sous-type "FV" ou "TO"
				if (!SubtypeZoneEnum.SALES_FORCES.equals(SubtypeZoneEnum.fromLiteral(zc.getSousType()))
						&& !SubtypeZoneEnum.TOUR_OPERATOR.equals(SubtypeZoneEnum.fromLiteral(zc.getSousType()))) {

					// ERREUR 328 - INVALID SUBTYPE ZONE - FV (ou TO) pour lien privil�gi�
					throw new JrafDomainRollbackException("328");
				}
			}
		}

		// Au moins un lien privilegie actif
		if (countActivePrivilegedLink == 0) {

			// ERREUR 171 - COMMERCIAL ZONE MANDATORY - cr�ez un lien privil�gi� valide (ZC)
			throw new JrafDomainRollbackException("171");

		} else if (countActivePrivilegedLink > 1) {

			log.warn(String.format("Legal person %s has %d active priviledged ZC links", pPersonneMorale.getGin(),
					countActivePrivilegedLink));
		}

		// Au moins un lien privil�gi� sans date de fin
		if (countPrivilegedLinkWithoutEndDate == 0) {

			// ERREUR 171 - COMMERCIAL ZONE MANDATORY - cr�ez le lien privil�gi� suivant
			// (ZC)
			throw new JrafDomainRollbackException("171");

		} else if (countPrivilegedLinkWithoutEndDate > 1) {

			log.warn(String.format("Legal person %s has %d priviledged ZC links whithout end date",
					pPersonneMorale.getGin(), countPrivilegedLinkWithoutEndDate));
		}

		// Si pour une m�me ZC, il existe :
		// - un lien privil�gi�
		// - un autre lien (privil�gi� ou non)
		// - les 2 liens se recouvrent
		// Alors, il y doublon
		commercialZoneLinks = new ArrayList<PmZone>(pPersonneMorale.fetchCommercialZoneLinks());
		commercialZoneLinks.removeAll(zcLinksToDelete.values());
		commercialZoneLinks.addAll(zcLinksToCreate);
		for (PmZone pmZone1 : commercialZoneLinks) {

			for (PmZone pmZone2 : commercialZoneLinks) {

				if (pmZone2.equals(pmZone1)) {
					continue;
				}

				if ((pmZone1.getCle() == null && pmZone2.getCle() == null // les 2 cl�s sont null
						|| pmZone1.getCle() != null && !pmZone1.getCle().equals(pmZone2.getCle())
						|| pmZone2.getCle() != null && !pmZone2.getCle().equals(pmZone1.getCle())) // ou les 2 cl�s sont
																									// valoris�es mais
																									// differentes

						&& pmZone1.getZoneDecoup() != null && pmZone2.getZoneDecoup() != null
						&& pmZone1.getZoneDecoup().getGin().equals(pmZone2.getZoneDecoup().getGin()) // m�me ZC

						&& (PrivilegedLinkEnum.YES.equals(PrivilegedLinkEnum.fromLiteral(pmZone1.getLienPrivilegie()))
								|| PrivilegedLinkEnum.YES
										.equals(PrivilegedLinkEnum.fromLiteral(pmZone2.getLienPrivilegie()))) // au
																												// moins
																												// un
																												// des 2
																												// liens
																												// est
																												// privil�gi�

						&& pmZone1.getDateOuverture().compareTo(pmZone2.getDateOuverture()) <= 0
						&& (pmZone1.getDateFermeture() == null
								|| pmZone1.getDateFermeture().compareTo(pmZone2.getDateOuverture()) >= 0)) // les 2
																											// liens se
																											// recouvrent
				{
					// IM02171364 - Error message in this case will not be anymore 'INVALID DATE',
					// but 'LINK ALREADY EXISTS'

					// ERREUR 119 - INVALID DATE - Liens en Doublons Existants
					// throw new JrafDomainRollbackException("119");

					// ERREUR 244 - LINK ALREADY EXISTS - LINK ALREADY EXISTS
					throw new JrafDomainRollbackException("244");
				}
			}
		}

		// Si origine est renseign� parmi les liens existants qu'on n'update pas, on met
		// � null (demande de LBN)
		for (PmZone commercialZoneLink : pPersonneMorale.fetchCommercialZoneLinks()) {

			if (!zcLinksToUpdate.containsKey(commercialZoneLink.getCle()) && commercialZoneLink.getOrigine() != null) {

				commercialZoneLink.setOrigine(null);
				commercialZoneLink.setDateModif(now);
				commercialZoneLink.setSignature(pPersonneMorale.getSignatureModification()); // TODO CEP/MBE � revoir
																								// car cela d�pend si on
																								// est en cr�ation ou
																								// modification de PM
				zcLinksToUpdate.put(commercialZoneLink.getCle(), commercialZoneLink);
			}
		}

		// Enregistrement en base
		for (PmZone link : zcLinksToCreate) {
			pmZoneRepository.saveAndFlush(link);
		}
		for (PmZone link : zcLinksToUpdate.values()) {
			pmZoneRepository.saveAndFlush(link);
		}
		for (PmZone link : zcLinksToDelete.values()) {
			pmZoneRepository.delete(link);
		}
		personneMoraleRepository.refresh(pPersonneMorale);
	}

	public void transferInCancellationZone(PersonneMorale pPersonneMorale) throws JrafDomainException {
		// When closing a firm, it must be transferred into a cancellation zone
		// and if the firm had a SIRET number, then this number is deleted from
		// database.

		Assert.notNull(pPersonneMorale);
		Assert.notNull(pPersonneMorale.getGin());

		// aujourd'hui
		Date now = new Date();

		// aujourd'hui minuit
		Date nowMidnight = SicDateUtils.midnight(now);

		PmZone zcLinkToCreate = new PmZone();

		Map<Long, PmZone> zcLinksToDelete = new HashMap<Long, PmZone>();
		Map<Long, PmZone> zcLinksToUpdate = new HashMap<Long, PmZone>();

		for (PmZone existingPrivilegedZcLink : pPersonneMorale.fetchActivePrivilegedCommercialZoneLinks()) {

			ZoneComm zoneCommAnnulation = zoneCommUS
					.findCorrespondingCancellationZone((ZoneComm) existingPrivilegedZcLink.getZoneDecoup());

			// If the cancellation zone does not exist
			// or that the cancellation zone has a closing date (set to not null or in the
			// past): error 346
			if (zoneCommAnnulation == null || (zoneCommAnnulation.getDateFermeture() != null
					&& zoneCommAnnulation.getDateFermeture().before(nowMidnight))) {
				throw new JrafDomainRollbackException("346: ZONE NOT FOUND : Cancellation zone does not exist");
			}

			// nouveau lien li� � la zone d'annulation
			zcLinkToCreate.setCle(null);
			zcLinkToCreate.setDateFermeture(null);
			zcLinkToCreate.setDateModif(null);
			zcLinkToCreate.setDateOuverture(nowMidnight);
			zcLinkToCreate.setLienPrivilegie(PrivilegedLinkEnum.YES.toLiteral());
			zcLinkToCreate.setOrigine(null);
			zcLinkToCreate.setPersonneMorale(pPersonneMorale);
			// FIXME signature � ne pas renseigner ici car il faut affecter la signature de
			// la requ�te
			// correspondant et non celle de la PM
			zcLinkToCreate.setSignature(pPersonneMorale.getSignatureModification()); // TODO CEP/MBE � revoir car cela
																						// d�pend si on est en cr�ation
																						// ou modification de PM
			zcLinkToCreate.setZoneDecoup(zoneCommAnnulation);

			Calendar cal = Calendar.getInstance();
			cal.setTime(zcLinkToCreate.getDateOuverture());
			cal.add(Calendar.DAY_OF_MONTH, -1);
			existingPrivilegedZcLink.setDateFermeture(cal.getTime());
			existingPrivilegedZcLink.setOrigine(null); // si origine est renseign�, on met � null (demande de LBN)
			// FIXME signature � ne pas renseigner ici car il faut affecter la signature de
			// la requ�te
			// correspondant et non celle de la PM
			existingPrivilegedZcLink.setSignature(pPersonneMorale.getSignatureModification());
			existingPrivilegedZcLink.setDateModif(now);

			// Si le lien de la zone a �t� cr�e puis supprim�e le jour-m�me
			// La date de fermeture se retrouve avant la date d'ouverture.
			// On le supprime donc physiquement
			if (existingPrivilegedZcLink.getDateOuverture().after(existingPrivilegedZcLink.getDateFermeture())) {
				zcLinksToDelete.put(existingPrivilegedZcLink.getCle(), existingPrivilegedZcLink);
			} else {
				zcLinksToUpdate.put(existingPrivilegedZcLink.getCle(), existingPrivilegedZcLink);
			}
			// On met le SIRET � null
			if (Etablissement.class.equals(pPersonneMorale.getClass())) {
				((Etablissement) pPersonneMorale).setSiret(null);
			}
		}

		// Enregistrements en base

		pmZoneRepository.saveAndFlush(zcLinkToCreate);

		if (Etablissement.class.equals(pPersonneMorale.getClass())) {
			etablissementRepository.saveAndFlush((Etablissement) pPersonneMorale);
		}

		for (PmZone link : zcLinksToDelete.values()) {
			pmZoneRepository.delete(link);
		}

		for (PmZone link : zcLinksToUpdate.values()) {
			pmZoneRepository.saveAndFlush(link);
		}

	}

	public void transferInStandbyZone(PersonneMorale pPersonneMorale) throws JrafDomainException {

		Assert.notNull(pPersonneMorale);
		Assert.notNull(pPersonneMorale.getGin());

		// aujourd'hui
		Date now = new Date();

		// aujourd'hui minuit
		Date nowMidnight = SicDateUtils.midnight(now);

		PmZone zcLinkToCreate = new PmZone();

		Map<Long, PmZone> zcLinksToDelete = new HashMap<Long, PmZone>();
		Map<Long, PmZone> zcLinksToUpdate = new HashMap<Long, PmZone>();

		for (PmZone existingPrivilegedZcLink : pPersonneMorale.fetchActivePrivilegedCommercialZoneLinks()) {

			ZoneComm zoneCommStandby = zoneCommUS.findZone(pPersonneMorale.fetchValidLocalisationPostalAddress(),
					NatureZoneEnum.STANDBY);

			// If the standby zone does not exist
			// put in trash zone
			if (zoneCommStandby == null) {

				zoneCommStandby = zoneCommUS.findTrashZone();

			} else {

				// or that the standby zone has a closing date (set to not null or in the past):
				// error 346
				if (zoneCommStandby == null || (zoneCommStandby.getDateFermeture() != null
						&& zoneCommStandby.getDateFermeture().before(nowMidnight))) {
					throw new JrafDomainRollbackException("346: ZONE NOT FOUND : Standby zone does not exist");
				}
			}

			// nouveau lien li� � la zone d'attente
			zcLinkToCreate.setCle(null);
			zcLinkToCreate.setDateFermeture(null);
			zcLinkToCreate.setDateModif(null);
			zcLinkToCreate.setDateOuverture(nowMidnight);
			zcLinkToCreate.setLienPrivilegie(PrivilegedLinkEnum.YES.toLiteral());
			zcLinkToCreate.setOrigine(null);
			zcLinkToCreate.setPersonneMorale(pPersonneMorale);
			// FIXME signature � ne pas renseigner ici car il faut affecter la signature de
			// la requ�te
			// correspondant et non celle de la PM
			zcLinkToCreate.setSignature(pPersonneMorale.getSignatureModification()); // TODO CEP/MBE � revoir car cela
																						// d�pend si on est en cr�ation
																						// ou modification de PM
			zcLinkToCreate.setZoneDecoup(zoneCommStandby);

			Calendar cal = Calendar.getInstance();
			cal.setTime(zcLinkToCreate.getDateOuverture());
			cal.add(Calendar.DAY_OF_MONTH, -1);
			existingPrivilegedZcLink.setDateFermeture(cal.getTime());
			existingPrivilegedZcLink.setOrigine(null); // si origine est renseign�, on met � null (demande de LBN)
			// FIXME signature � ne pas renseigner ici car il faut affecter la signature de
			// la requ�te
			// correspondant et non celle de la PM
			existingPrivilegedZcLink.setSignature(pPersonneMorale.getSignatureModification());
			existingPrivilegedZcLink.setDateModif(now);

			// Si le lien de la zone a �t� cr�e puis supprim�e le jour-m�me
			// La date de fermeture se retrouve avant la date d'ouverture.
			// On le supprime donc physiquement
			if (existingPrivilegedZcLink.getDateOuverture().after(existingPrivilegedZcLink.getDateFermeture())) {
				zcLinksToDelete.put(existingPrivilegedZcLink.getCle(), existingPrivilegedZcLink);
			} else {
				zcLinksToUpdate.put(existingPrivilegedZcLink.getCle(), existingPrivilegedZcLink);
			}

		}

		// Enregistrements en base

		pmZoneRepository.saveAndFlush(zcLinkToCreate);

		for (PmZone link : zcLinksToDelete.values()) {
			pmZoneRepository.delete(link);
		}

		for (PmZone link : zcLinksToUpdate.values()) {
			pmZoneRepository.saveAndFlush(link);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.airfrance.repind.service.internal.unitservice.firm.IPmZoneUS#
	 * createOrUpdateOrDeleteZvLinks(List, PersonneMorale)
	 */
	/**
	 * This method has a suspected bug, it calls fetchPrivilegedCommercialZoneLinks
	 * and closes ZC links instead of ZV links Use createOrUpdateOrDeleteZvLinksV2
	 * instead
	 * 
	 * @param pZvLinks
	 * @param pPersonneMorale
	 * @throws JrafDomainException
	 */
	@Deprecated
	public void createOrUpdateOrDeleteZvLinks(List<PmZone> pZvLinks, PersonneMorale pPersonneMorale)
			throws JrafDomainException {

		Assert.notNull(pZvLinks);
		Assert.notNull(pPersonneMorale);
		Assert.notNull(pPersonneMorale.getGin());
		for (PmZone zvLink : pZvLinks) {
			Assert.notNull(zvLink);
			Assert.notNull(zvLink.getZoneDecoup());
			Assert.isInstanceOf(ZoneVente.class, zvLink.getZoneDecoup());
		}

		if (pPersonneMorale.getPmZones() == null) {
			pPersonneMorale.setPmZones(new HashSet<PmZone>());
		}

		List<PmZone> zvLinksToCreate = new ArrayList<PmZone>();
		Map<Long, PmZone> zvLinksToDelete = new HashMap<Long, PmZone>();
		Map<Long, PmZone> zvLinksToUpdate = new HashMap<Long, PmZone>();
		int countPrivilegedLink = 0;

		// aujourd'hui
		Date now = new Date();

		// aujourd'hui minuit
		Date nowMidnight = SicDateUtils.midnight(now);

		for (PmZone zvLink : pZvLinks) {

			if (zvLink.getCle() == null) {

				// Lien � cr�er
				// --------------

				// Specif agency
				if (Agence.class.equals(pPersonneMorale.getClass())) {

					// ZV0 � ZV3 sont requis car on ne peut cr�er de liens qu'avec des ZV de niveau
					// 3
					if (!zoneVenteUS.checkZv0ToZv3((ZoneVente) zvLink.getZoneDecoup())) {

						// ERREUR 258 - INCOMPLETE SALES ZONE - ZV0�3 Obligatoires
						throw new JrafDomainRollbackException("258");
					}
				}

				// recherche de la ZV
				try {
					Integer zv0 = ((ZoneVente) zvLink.getZoneDecoup()).getZv0();
					Integer zv1 = ((ZoneVente) zvLink.getZoneDecoup()).getZv1();
					Integer zv2 = ((ZoneVente) zvLink.getZoneDecoup()).getZv2();
					Integer zv3 = ((ZoneVente) zvLink.getZoneDecoup()).getZv3();
					ZoneVente zv = zoneVenteRepository.findActiveByZv0Zv1Zv2Zv3(zv0, zv1, zv2, zv3);
					zvLink.setZoneDecoup(zv);
				} catch (NoResultException e) {
					// ERREUR 346 - ZONE NOT FOUND
					throw new JrafDomainRollbackException("346"); // TODO CEP/MBE il faudrait pr�ciser ZV
				} catch (NonUniqueResultException e) {
					// ERREUR 346 - ZONE NOT FOUND
					throw new JrafDomainRollbackException("346"); // TODO CEP/MBE il faudrait pr�ciser ZV
				}

				// si la date d'ouverture est vide, on l'initialise � aujourd'hui minuit
				// sinon, on l'initialise � minuit de la m�me date
				if (zvLink.getDateOuverture() == null) {
					zvLink.setDateOuverture(nowMidnight);
				} else {
					zvLink.setDateOuverture(SicDateUtils.midnight(zvLink.getDateOuverture()));
				}
				checkStartDate(zvLink, pPersonneMorale, zvLink.getZoneDecoup());

				// si la date de fermeture est renseign�e, on l'initialise � minuit de la m�me
				// date
				if (zvLink.getDateFermeture() != null) {

					zvLink.setDateFermeture(SicDateUtils.midnight(zvLink.getDateFermeture()));
					checkEndDate(zvLink, pPersonneMorale, zvLink.getZoneDecoup());
				}

				checkPrivilegedLink(zvLink, pPersonneMorale);

				// si le nouveau lien � cr�er est un lien privil�gi�
				if (PrivilegedLinkEnum.YES.equals(PrivilegedLinkEnum.fromLiteral(zvLink.getLienPrivilegie()))) {

					countPrivilegedLink++;

					// on ne peut cr�er qu'un seul lien privil�gi� � la fois
					if (countPrivilegedLink > 1) {

						// TODO LBN cr�er erreur "CANNOT CREATE MORE THAN ONE PRIVILEGED ZV LINK"
						throw new JrafDomainRollbackException("CANNOT CREATE MORE THAN ONE PRIVILEGED ZV LINK");
					}

					// la date de fermeture du lien privil�gi� � cr�er ne doit pas etre renseign�e
					if (zvLink.getDateFermeture() != null) {

						// TODO LBN cr�er erreur "CANNOT CREATE PRIVILEGED ZV LINK WITH END DATE"
						throw new JrafDomainRollbackException("CANNOT CREATE PRIVILEGED ZV LINK WITH END DATE");
					}

					// on ferme le(s) lien(s) privil�gi�(s) dont l'intervalle comprend la date
					// d'ouverture du lien privil�gi� � cr�er
					// on supprime le(s) lien(s) privil�gi�(s) qui sont futur au lien privil�gi� �
					// cr�er
					for (PmZone existingPrivilegedZcLink : pPersonneMorale.fetchPrivilegedCommercialZoneLinks()) {

						if (existingPrivilegedZcLink.getDateOuverture().compareTo(zvLink.getDateOuverture()) < 0
								&& (existingPrivilegedZcLink.getDateFermeture() == null || existingPrivilegedZcLink
										.getDateFermeture().compareTo(zvLink.getDateOuverture()) >= 0)) {

							Calendar cal = Calendar.getInstance();
							cal.setTime(zvLink.getDateOuverture());
							cal.add(Calendar.DAY_OF_MONTH, -1);
							existingPrivilegedZcLink.setDateFermeture(cal.getTime());
							existingPrivilegedZcLink.setOrigine(null);// si origine est renseign�, on met � null
																		// (demande de LBN)
							existingPrivilegedZcLink.setSignature(zvLink.getSignature());
							existingPrivilegedZcLink.setDateModif(now);
							zvLinksToUpdate.put(existingPrivilegedZcLink.getCle(), existingPrivilegedZcLink);

						} else if (existingPrivilegedZcLink.getDateOuverture()
								.compareTo(zvLink.getDateOuverture()) >= 0) {

							zvLinksToDelete.put(existingPrivilegedZcLink.getCle(), existingPrivilegedZcLink);
						}
					}

					// on supprime physiquement les plus anciennes ZV Priv histo (inactive)
					// pour n'en conserver qu'une max
					PmZone pmZvInactDateFermetureRecente = null;
					for (PmZone existingPrivilegedInactiveZvLink : pPersonneMorale
							.fetchPastPrivilegedSalesZoneLinks()) {

						if (pmZvInactDateFermetureRecente != null && pmZvInactDateFermetureRecente.getDateFermeture()
								.before(existingPrivilegedInactiveZvLink.getDateFermeture())) {
							zvLinksToDelete.put(pmZvInactDateFermetureRecente.getCle(), pmZvInactDateFermetureRecente);
							pmZvInactDateFermetureRecente = existingPrivilegedInactiveZvLink;
						}

						if (pmZvInactDateFermetureRecente == null) { // Init premiere occurence
							pmZvInactDateFermetureRecente = existingPrivilegedInactiveZvLink;
						}
					}
				}

				zvLink.setDateModif(now); // � cause des r�plications qui s'appuient sur dateModif (defect 54)
				zvLink.setOrigine(null);
				zvLink.setPersonneMorale(pPersonneMorale);

				zvLinksToCreate.add(zvLink);

			} else {

				// Lien � modifier
				// -----------------

				PmZone linkTrouve = pPersonneMorale.fetchZoneLinkById(zvLink.getCle());
				if (linkTrouve != null && ZoneVente.class.equals(linkTrouve.getZoneDecoup().getClass())) {

					// il n'est pas possible de modfier un lien privil�gi� (en effet, il faut passer
					// par la cr�ation pour fermer celui existant et en cr�er un nouveau)
					if (PrivilegedLinkEnum.YES.equals(PrivilegedLinkEnum.fromLiteral(linkTrouve.getLienPrivilegie()))) {

						// TODO LBN cr�er erreur "CANNOT UPDATE PRIVILEGED ZV LINK"
						throw new JrafDomainRollbackException("CANNOT UPDATE PRIVILEGED ZV LINK");
					}

					if (!isEmptyData(zvLink)) {

						// on ne peut modifier que la date de fermeture => c'est donc la seule donn�e �
						// contr�ler
						if (zvLink.getDateFermeture() != null) {

							zvLink.setDateFermeture(SicDateUtils.midnight(zvLink.getDateFermeture()));
							checkEndDate(zvLink, pPersonneMorale, zvLink.getZoneDecoup());
						}
						linkTrouve.setDateFermeture(zvLink.getDateFermeture());

						// si origine est renseign� dans les liens existants, on met � null (demande de
						// LBN)
						linkTrouve.setOrigine(null);

						linkTrouve.setDateModif(now);
						linkTrouve.setSignature(zvLink.getSignature());
						zvLinksToUpdate.put(zvLink.getCle(), linkTrouve);
					} else {

						zvLinksToDelete.put(zvLink.getCle(), linkTrouve);
					}
				} else {

					// Erreur 338 - ZONE LINK NOT FOUND
					throw new JrafDomainRollbackException("338"); // TODO il faudrait pr�ciser qu'on traite des ZV
				}
			}
		}

		// on fait un 1er bilan des liens ZV
		// -----------------------------------

		// Pas de ZV par defaut si absence de ZV

		// on fait un 2e bilan des liens ZV
		// ----------------------------------

		List<PmZone> saleZoneLinks = new ArrayList<PmZone>(pPersonneMorale.fetchSalesZoneLinks());
		saleZoneLinks.removeAll(zvLinksToDelete.values());
		saleZoneLinks.addAll(zvLinksToCreate);

		int cntPrivilegedLink = 0;
		for (PmZone zvLink : saleZoneLinks) {

			ZoneVente zv = (ZoneVente) zvLink.getZoneDecoup();

			// Specif Agency
			if (Agence.class.equals(pPersonneMorale.getClass())) {

				// si les ZV li�es ne sont pas toutes de niveau 3, on sort
				if (!zoneVenteUS.checkZv0ToZv3(zv)) {

					// ERREUR 258 - INCOMPLETE SALES ZONE
					throw new JrafDomainRollbackException("258"); // TODO CEP/MBE il faudrait �galt pr�ciser la cl� du
																	// lien
				}
			}

			// si le lien est privil�gi�, il doit �tre de sous-type "FV"
			if (PrivilegedLinkEnum.YES.equals(PrivilegedLinkEnum.fromLiteral(zvLink.getLienPrivilegie()))) {

				cntPrivilegedLink++;
				if (!SubtypeZoneEnum.SALES_FORCES.equals(SubtypeZoneEnum.fromLiteral(zv.getSousType()))) {

					// ERREUR 328 - INVALID SUBTYPE ZONE
					throw new JrafDomainRollbackException("328"); // TODO CEP/MBE il faudrait �galt pr�ciser la cl� du
																	// lien
				}
			}
		}

		// Specif Agency
		if (Agence.class.equals(pPersonneMorale.getClass())) {

			// Au moins un lien privilegie actif
			if (cntPrivilegedLink == 0) {

				// ERREUR 926 - SALES ZONE MANDATORY
				throw new JrafDomainRollbackException("926");
			}
		}

		// on fait un dernier bilan des liens ZC concernant les doublons
		// Vu avec LBN et Herve Valadon
		// Si doublon sur zone privilegie ou secondaire en recouvrement de perirode =>
		// Exception Existe deja
		// ----------------------------------
		saleZoneLinks = new ArrayList<PmZone>(pPersonneMorale.fetchSalesZoneLinks());
		saleZoneLinks.removeAll(zvLinksToDelete.values());
		saleZoneLinks.addAll(zvLinksToCreate);
		for (PmZone pmZone1 : saleZoneLinks) {

			for (PmZone pmZone2 : saleZoneLinks) {

				if (((pmZone1.getCle() == null ^ pmZone2.getCle() == null) // l'une ou l'auttre des 2 cle null (pas les
																			// deux)
						|| (pmZone1.getCle() != null && !pmZone1.getCle().equals(pmZone2.getCle()))) // ou les 2 cles
																										// valorisees
																										// mais
																										// differentes
						&& pmZone1.getZoneDecoup() != null && pmZone2.getZoneDecoup() != null
						&& pmZone1.getZoneDecoup().getGin().equals(pmZone2.getZoneDecoup().getGin()) // Meme Zone
						&& pmZone1.getLienPrivilegie().equals(pmZone2.getLienPrivilegie()) // meme type de lien
						&& pmZone1.getDateOuverture().before(pmZone2.getDateOuverture())
						&& (pmZone1.getDateFermeture() == null
								|| !pmZone1.getDateFermeture().before(pmZone2.getDateOuverture()))) { // pmZone1.getDateFermeture()
																										// >=
																										// pmZone2.getDateOuverture()

					// TODO LBN cr�er erreur "DUPLICATE PRIVILEDGED SALES ZONE"
					throw new JrafDomainRollbackException("DUPLICATE PRIVILEDGED SALES ZONE");
				}
			}
		}

		// si origine est renseign� dans les liens existants qu'on n'update pas, on met
		// � null (demande de LBN)
		for (PmZone salesZoneLink : pPersonneMorale.fetchSalesZoneLinks()) {

			if (!zvLinksToUpdate.containsKey(salesZoneLink.getCle()) && salesZoneLink.getOrigine() != null) {

				salesZoneLink.setOrigine(null);
				salesZoneLink.setDateModif(now);
				salesZoneLink.setSignature(pPersonneMorale.getSignatureModification());
				zvLinksToUpdate.put(salesZoneLink.getCle(), salesZoneLink);
			}
		}

		// TODO LBN : quel est l'impact du statut PM (actif, temporaire, ferm�, ...) sur
		// les liens ZV ?

		// Enregistrement en base
		for (PmZone link : zvLinksToCreate) {
			pmZoneRepository.saveAndFlush(link);
		}
		for (PmZone link : zvLinksToUpdate.values()) {
			pmZoneRepository.saveAndFlush(link);
		}
		for (PmZone link : zvLinksToDelete.values()) {
			pmZoneRepository.delete(link);
		}
	}

	public void createOrUpdateOrDeleteZcLinksFictiveAgency(List<PmZone> pZcLinks, PersonneMorale pPersonneMorale)
			throws JrafDomainException {

		Assert.notNull(pZcLinks);
		Assert.notNull(pPersonneMorale);
		Assert.notNull(pPersonneMorale.getGin());
		for (PmZone zcLink : pZcLinks) {
			Assert.notNull(zcLink);
			Assert.notNull(zcLink.getZoneDecoup());
			Assert.isInstanceOf(ZoneComm.class, zcLink.getZoneDecoup());
		}

		if (pPersonneMorale.getPmZones() == null) {
			pPersonneMorale.setPmZones(new HashSet<PmZone>());
		}

		List<PmZone> zcLinksToCreate = new ArrayList<PmZone>();
		Map<Long, PmZone> zcLinksToUpdate = new HashMap<Long, PmZone>();
		Map<Long, PmZone> zcLinksToDelete = new HashMap<Long, PmZone>();
		int countPrivilegedLinkToCreate = 0;

		// aujourd'hui
		Date now = new Date();

		// aujourd'hui minuit
		Date nowMidnight = SicDateUtils.midnight(now);

		for (PmZone zcLink : pZcLinks) {

			// recherche de la ZC
			try {
				String zc1 = ((ZoneComm) zcLink.getZoneDecoup()).getZc1();
				String zc2 = ((ZoneComm) zcLink.getZoneDecoup()).getZc2();
				String zc3 = ((ZoneComm) zcLink.getZoneDecoup()).getZc3();
				String zc4 = ((ZoneComm) zcLink.getZoneDecoup()).getZc4();
				String zc5 = ((ZoneComm) zcLink.getZoneDecoup()).getZc5();
				ZoneComm zc = zoneCommRepository.findActiveByZc1Zc2Zc3Zc4Zc5(zc1, zc2, zc3, zc4, zc5);
				zcLink.setZoneDecoup(zc);
			} catch (NoResultException e) {
				// ERREUR 346 - ZONE NOT FOUND
				throw new JrafDomainRollbackException("346");
			} catch (NonUniqueResultException e) {
				// ERREUR 346 - ZONE NOT FOUND
				throw new JrafDomainRollbackException("346");
			}

			if (zcLink.getCle() == null) {

				// Lien ï¿½ crï¿½er
				// --------------

				// ZC1 ï¿½ ZC5 sont requis car on ne peut crï¿½er de liens qu'avec des ZC de
				// niveau 5
				if (!zoneCommUS.checkZc1ToZc5((ZoneComm) zcLink.getZoneDecoup())) {

					// ERREUR 257 - INCOMPLETE COMMERCIAL ZONE - ZC1ï¿½5 Obligatoires
					throw new JrafDomainRollbackException("257");
				}

				// si la date d'ouverture est vide, on l'initialise ï¿½ aujourd'hui minuit
				// sinon, on l'initialise ï¿½ minuit de la mï¿½me date
				if (zcLink.getDateOuverture() == null) {
					zcLink.setDateOuverture(nowMidnight);
				} else {
					zcLink.setDateOuverture(SicDateUtils.midnight(zcLink.getDateOuverture()));
				}
				checkStartDate(zcLink, pPersonneMorale, zcLink.getZoneDecoup());

				// si la date de fermeture est renseignï¿½e, on l'initialise ï¿½ minuit de la
				// mï¿½me date
				if (zcLink.getDateFermeture() != null) {

					zcLink.setDateFermeture(SicDateUtils.midnight(zcLink.getDateFermeture()));
					checkEndDate(zcLink, pPersonneMorale, zcLink.getZoneDecoup());
				}

				checkPrivilegedLink(zcLink, pPersonneMorale);

				// si le nouveau lien ï¿½ crï¿½er est un lien privilï¿½giï¿½
				if (PrivilegedLinkEnum.YES.equals(PrivilegedLinkEnum.fromLiteral(zcLink.getLienPrivilegie()))) {

					countPrivilegedLinkToCreate++;

					// on ne peut crï¿½er qu'un seul lien privilï¿½giï¿½ ï¿½ la fois
					if (countPrivilegedLinkToCreate > 1) {

						// TODO LBN crï¿½er erreur "CANNOT CREATE MORE THAN ONE PRIVILEGED ZC LINK"
						throw new JrafDomainRollbackException("CANNOT CREATE MORE THAN ONE PRIVILEGED ZC LINK");
					}

					// la date de fermeture du lien privilï¿½giï¿½ ï¿½ crï¿½er ne doit pas etre
					// renseignï¿½e
					if (zcLink.getDateFermeture() != null) {

						// TODO LBN crï¿½er erreur "CANNOT CREATE PRIVILEGED ZC LINK WITH END DATE"
						throw new JrafDomainRollbackException("CANNOT CREATE PRIVILEGED ZC LINK WITH END DATE");
					}

					// on ferme le(s) lien(s) privilï¿½giï¿½(s) dont l'intervalle comprend la date
					// d'ouverture du lien privilï¿½giï¿½ ï¿½ crï¿½er
					// on supprime le(s) lien(s) privilï¿½giï¿½(s) qui sont futur au lien
					// privilï¿½giï¿½ ï¿½ crï¿½er
					for (PmZone existingPrivilegedZcLink : pPersonneMorale.fetchPrivilegedCommercialZoneLinks()) {

						if (existingPrivilegedZcLink.getDateOuverture().compareTo(zcLink.getDateOuverture()) < 0
								&& (existingPrivilegedZcLink.getDateFermeture() == null || existingPrivilegedZcLink
										.getDateFermeture().compareTo(zcLink.getDateOuverture()) >= 0)) {

							Calendar cal = Calendar.getInstance();
							cal.setTime(zcLink.getDateOuverture());
							cal.add(Calendar.DAY_OF_MONTH, -1);
							existingPrivilegedZcLink.setDateFermeture(cal.getTime());
							existingPrivilegedZcLink.setOrigine(null);// si origine est renseignï¿½, on met ï¿½ null
																		// (demande de LBN)
							existingPrivilegedZcLink.setSignature(zcLink.getSignature());
							existingPrivilegedZcLink.setDateModif(now);
							zcLinksToUpdate.put(existingPrivilegedZcLink.getCle(), existingPrivilegedZcLink);

						} else if (existingPrivilegedZcLink.getDateOuverture()
								.compareTo(zcLink.getDateOuverture()) >= 0) {

							zcLinksToDelete.put(existingPrivilegedZcLink.getCle(), existingPrivilegedZcLink);
						}
					}

					// on supprime physiquement les plus anciens liens ZC privilï¿½giï¿½s et
					// historisï¿½s (inactifs)
					// pour n'en conserver qu'un max
					List<PmZone> pastPrivilegedZoneLinks = new ArrayList<PmZone>(
							pPersonneMorale.fetchPastPrivilegedCommercialZoneLinks());
					if (!pastPrivilegedZoneLinks.isEmpty()) {

						// on trie la liste par date de fermeture la plus rï¿½cente ï¿½ la plus ancienne
						Collections.sort(pastPrivilegedZoneLinks, new Comparator<PmZone>() {
							public int compare(PmZone link1, PmZone link2) {
								return -link1.getDateFermeture().compareTo(link2.getDateFermeture());
							}
						});

						// date de fermeture la plus rï¿½cente
						Date dateFermetureLapLusRecente = pastPrivilegedZoneLinks.get(0).getDateFermeture();

						// on retire les liens qui ont pour date de fermeture =
						// dateFermetureLapLusRecente
						for (Iterator<PmZone> it = pastPrivilegedZoneLinks.iterator(); it.hasNext();) {

							if (dateFermetureLapLusRecente.equals(it.next().getDateFermeture())) {
								it.remove(); // on retire le lien de la liste
							} else {
								break; // inutile d'aller plus loin ï¿½tant donnï¿½ que la liste est triï¿½e par date
										// de fermeture desc
							}
						}

						// les liens restants sont ï¿½ dï¿½truire
						for (PmZone inactivePrivilegedZoneLink : pastPrivilegedZoneLinks) {

							zcLinksToDelete.put(inactivePrivilegedZoneLink.getCle(), inactivePrivilegedZoneLink);
						}
					}
				}

				zcLink.setDateModif(now); // ï¿½ cause des rï¿½plications qui s'appuient sur dateModif (defect 54)
				zcLink.setOrigine(null);
				zcLink.setPersonneMorale(pPersonneMorale);

				zcLinksToCreate.add(zcLink);

			} else {

				// Lien ï¿½ modifier ou supprimer
				// ------------------------------

				PmZone linkTrouve = pPersonneMorale.fetchZoneLinkById(zcLink.getCle());
				if (linkTrouve != null && ZoneComm.class.equals(linkTrouve.getZoneDecoup().getClass())) {

					// il n'est pas possible de modfier un lien privilï¿½giï¿½ (en effet, il faut
					// passer par la crï¿½ation pour fermer celui existant et en crï¿½er un nouveau)
					/*
					 * if (PrivilegedLinkEnum.YES.equals(PrivilegedLinkEnum.fromLiteral(linkTrouve.
					 * getLienPrivilegie()))) {
					 * 
					 * // TODO LBN crï¿½er erreur "CANNOT UPDATE PRIVILEGED ZC LINK" throw new
					 * JrafDomainRollbackException("CANNOT UPDATE PRIVILEGED ZC LINK"); }
					 */

					// ATTENTION : si on veut mettre ï¿½ null la dateFermeture d'un lien secondaire,
					// il faut renseigner dateOuverture et/ou lienPrivilegie car sinon, on pensera
					// ï¿½ une suppression
					if (!isEmptyData(zcLink)) {

						// on ne peut modifier que la date de fermeture => c'est donc la seule donnï¿½e
						// ï¿½ contrï¿½ler
						if (zcLink.getDateFermeture() != null) {

							zcLink.setDateFermeture(SicDateUtils.midnight(zcLink.getDateFermeture()));
							checkEndDate(zcLink, pPersonneMorale, zcLink.getZoneDecoup());
						}

						linkTrouve.setDateFermeture(zcLink.getDateFermeture());
						linkTrouve.setOrigine(null); // on force ï¿½ null (demande de LBN)
						linkTrouve.setDateModif(now);
						linkTrouve.setSignature(zcLink.getSignature());

						zcLinksToUpdate.put(zcLink.getCle(), linkTrouve);

					} else {

						// Suppression du lien
						zcLinksToDelete.put(zcLink.getCle(), linkTrouve);
					}
				} else {

					// Erreur 338 - ZONE LINK NOT FOUND
					throw new JrafDomainRollbackException("338"); // TODO il faudrait prï¿½ciser qu'on traite des ZC
				}
			}
		}

		// on fait un 1er bilan des liens ZC
		// -----------------------------------

		// si la PM est un ï¿½tablissement et qu'on n'a aucun lien ZC,
		// alors si statut A ou P :
		// on crï¿½e un lien privilï¿½giï¿½ actif avec une ZC d'attente
		// si statut X :
		// on crï¿½e un lien privilï¿½giï¿½ actif avec une ZC d'annulation

		if (Etablissement.class.equals(pPersonneMorale.getClass())) {

			List<PmZone> commercialZoneLinks = new ArrayList<PmZone>(pPersonneMorale.fetchCommercialZoneLinks());
			commercialZoneLinks.removeAll(zcLinksToDelete.values());
			commercialZoneLinks.addAll(zcLinksToCreate);

			if (commercialZoneLinks.isEmpty()) {

				ZoneComm commercialZone = null;

				if (LegalPersonStatusEnum.ACTIVE.equals(LegalPersonStatusEnum.fromLiteral(pPersonneMorale.getStatut()))
						|| LegalPersonStatusEnum.TEMPORARY
								.equals(LegalPersonStatusEnum.fromLiteral(pPersonneMorale.getStatut()))) {

					commercialZone = zoneCommUS.findZone(pPersonneMorale.fetchValidLocalisationPostalAddress(),
							NatureZoneEnum.STANDBY);

					if (commercialZone == null) {

						commercialZone = zoneCommUS.findTrashZone();
					}

				} else if (LegalPersonStatusEnum.CLOSED
						.equals(LegalPersonStatusEnum.fromLiteral(pPersonneMorale.getStatut()))) {

					commercialZone = zoneCommUS.findZone(pPersonneMorale.fetchValidLocalisationPostalAddress(),
							NatureZoneEnum.CANCELLATION);
				}

				if (commercialZone != null) {

					PmZone zcLink = new PmZone();

					zcLink.setCle(null);
					zcLink.setDateFermeture(null);
					zcLink.setDateModif(now); // ï¿½ cause des rï¿½plications qui s'appuient sur dateModif (defect 54)
					zcLink.setDateOuverture(nowMidnight);
					zcLink.setLienPrivilegie(PrivilegedLinkEnum.YES.toLiteral());
					zcLink.setOrigine(null);
					zcLink.setPersonneMorale(pPersonneMorale);
					zcLink.setSignature(pPersonneMorale.getSignatureModification()); // TODO CEP/MBE ï¿½ revoir car cela
																						// dï¿½pend si on est en
																						// crï¿½ation ou modification de
																						// PM
					zcLink.setZoneDecoup(commercialZone);

					zcLinksToCreate.add(zcLink);
				}
			}
		}

		// on fait un 2e bilan des liens ZC
		// ----------------------------------

		List<PmZone> commercialZoneLinks = new ArrayList<PmZone>(pPersonneMorale.fetchCommercialZoneLinks());
		commercialZoneLinks.removeAll(zcLinksToDelete.values());
		commercialZoneLinks.addAll(zcLinksToCreate);

		int countActivePrivilegedLink = 0;
		int countPrivilegedLinkWithoutEndDate = 0;
		for (PmZone zcLink : commercialZoneLinks) {

			ZoneComm zc = (ZoneComm) zcLink.getZoneDecoup();

			// si les ZC liï¿½es ne sont pas toutes de niveau 5, on sort
			if (!zoneCommUS.checkZc1ToZc5(zc)) {

				// ERREUR 257 - INCOMPLETE COMMERCIAL ZONE - ZC1ï¿½5 Obligatoires
				throw new JrafDomainRollbackException("257"); // TODO CEP/MBE il faudrait ï¿½galt prï¿½ciser la clï¿½ du
																// lien
			}

			// si le lien est privilï¿½giï¿½
			if (PrivilegedLinkEnum.YES.equals(PrivilegedLinkEnum.fromLiteral(zcLink.getLienPrivilegie()))) {

				// Check selon le statut de la PM
				checkPmStatus(zcLink, pPersonneMorale);

				if (zcLink.getDateOuverture().compareTo(nowMidnight) <= 0 && (zcLink.getDateFermeture() == null
						|| zcLink.getDateFermeture().compareTo(nowMidnight) >= 0)) {

					countActivePrivilegedLink++;
				}

				if (zcLink.getDateFermeture() == null) {

					countPrivilegedLinkWithoutEndDate++;
				}

				// il doit ï¿½tre de sous-type "FV" ou "TO"
				if (!SubtypeZoneEnum.SALES_FORCES.equals(SubtypeZoneEnum.fromLiteral(zc.getSousType()))
						&& !SubtypeZoneEnum.TOUR_OPERATOR.equals(SubtypeZoneEnum.fromLiteral(zc.getSousType()))) {

					// ERREUR 328 - INVALID SUBTYPE ZONE - FV (ou TO) pour lien privilï¿½giï¿½
					throw new JrafDomainRollbackException("328");
				}
			}
		}

		// Au moins un lien privilegie actif
		if (countActivePrivilegedLink == 0) {

			// ERREUR 171 - COMMERCIAL ZONE MANDATORY - crï¿½ez un lien privilï¿½giï¿½
			// valide (ZC)
			throw new JrafDomainRollbackException("171");

		} else if (countActivePrivilegedLink > 1) {

			log.warn(String.format("Legal person %s has %d active priviledged ZC links", pPersonneMorale.getGin(),
					countActivePrivilegedLink));
		}

		// Au moins un lien privilï¿½giï¿½ sans date de fin
		if (countPrivilegedLinkWithoutEndDate == 0) {

			// ERREUR 171 - COMMERCIAL ZONE MANDATORY - crï¿½ez le lien privilï¿½giï¿½
			// suivant (ZC)
			throw new JrafDomainRollbackException("171");

		} else if (countPrivilegedLinkWithoutEndDate > 1) {

			log.warn(String.format("Legal person %s has %d priviledged ZC links whithout end date",
					pPersonneMorale.getGin(), countPrivilegedLinkWithoutEndDate));
		}

		// Si pour une mï¿½me ZC, il existe :
		// - un lien privilï¿½giï¿½
		// - un autre lien (privilï¿½giï¿½ ou non)
		// - les 2 liens se recouvrent
		// Alors, il y doublon
		commercialZoneLinks = new ArrayList<PmZone>(pPersonneMorale.fetchCommercialZoneLinks());
		commercialZoneLinks.removeAll(zcLinksToDelete.values());
		commercialZoneLinks.addAll(zcLinksToCreate);
		for (PmZone pmZone1 : commercialZoneLinks) {

			for (PmZone pmZone2 : commercialZoneLinks) {

				if (pmZone2.equals(pmZone1)) {
					continue;
				}

				if ((pmZone1.getCle() == null && pmZone2.getCle() == null // les 2 clï¿½s sont null
						|| pmZone1.getCle() != null && !pmZone1.getCle().equals(pmZone2.getCle())
						|| pmZone2.getCle() != null && !pmZone2.getCle().equals(pmZone1.getCle())) // ou les 2 clï¿½s
																									// sont valorisï¿½es
																									// mais differentes

						&& pmZone1.getZoneDecoup() != null && pmZone2.getZoneDecoup() != null
						&& pmZone1.getZoneDecoup().getGin().equals(pmZone2.getZoneDecoup().getGin()) // mï¿½me ZC

						&& (PrivilegedLinkEnum.YES.equals(PrivilegedLinkEnum.fromLiteral(pmZone1.getLienPrivilegie()))
								|| PrivilegedLinkEnum.YES
										.equals(PrivilegedLinkEnum.fromLiteral(pmZone2.getLienPrivilegie()))) // au
																												// moins
																												// un
																												// des 2
																												// liens
																												// est
																												// privilï¿½giï¿½

						&& pmZone1.getDateOuverture().compareTo(pmZone2.getDateOuverture()) <= 0
						&& (pmZone1.getDateFermeture() == null
								|| pmZone1.getDateFermeture().compareTo(pmZone2.getDateOuverture()) >= 0)) // les 2
																											// liens se
																											// recouvrent
				{
					// IM02171364 - Error message in this case will not be anymore 'INVALID DATE',
					// but 'LINK ALREADY EXISTS'

					// ERREUR 119 - INVALID DATE - Liens en Doublons Existants
					// throw new JrafDomainRollbackException("119");

					// ERREUR 244 - LINK ALREADY EXISTS - LINK ALREADY EXISTS
					throw new JrafDomainRollbackException("244");
				}
			}
		}

		// Si origine est renseignï¿½ parmi les liens existants qu'on n'update pas, on
		// met ï¿½ null (demande de LBN)
		for (PmZone commercialZoneLink : pPersonneMorale.fetchCommercialZoneLinks()) {

			if (!zcLinksToUpdate.containsKey(commercialZoneLink.getCle()) && commercialZoneLink.getOrigine() != null) {

				commercialZoneLink.setOrigine(null);
				commercialZoneLink.setDateModif(now);
				commercialZoneLink.setSignature(pPersonneMorale.getSignatureModification()); // TODO CEP/MBE ï¿½ revoir
																								// car cela dï¿½pend si
																								// on est en crï¿½ation
																								// ou modification de PM
				zcLinksToUpdate.put(commercialZoneLink.getCle(), commercialZoneLink);
			}
		}

		// Enregistrement en base
		for (PmZone link : zcLinksToCreate) {
			pmZoneRepository.saveAndFlush(link);
		}
		for (PmZone link : zcLinksToUpdate.values()) {
			pmZoneRepository.saveAndFlush(link);
		}
		for (PmZone link : zcLinksToDelete.values()) {
			pmZoneRepository.delete(link);
		}
		personneMoraleRepository.refresh(pPersonneMorale);
	}

	public void checkAndUpdateZonesCom(PersonneMorale pPersonneMorale) throws JrafDomainException {
		// On vérifie que la pm ne possède pas plus d'une ZC active privilégiée
		List<ZoneComm> zonesPvAct = zoneCommRepository.findActivePvValidZc(pPersonneMorale);

		if (zonesPvAct != null && zonesPvAct.size() > 1) {

			Map<Long, PmZone> zcLinksToUpdate = new HashMap<Long, PmZone>();

			int nbZCpvActives = zonesPvAct.size();

			Date currentDate = new Date();
			Date currentDateMidnight = SicDateUtils.midnight(currentDate);
			Calendar cal = Calendar.getInstance();
			cal.setTime(currentDateMidnight);
			cal.add(Calendar.DAY_OF_MONTH, -1);

			boolean oneLinkWithEndDate = false;
			boolean oneLinkWithoutEndDate = false;

			Date dateDebutLienPlusRecente = null;
			Date dateModificationPlusRecente = null;

			for (PmZone pmZone : pPersonneMorale.getPmZones()) {

				if (pmZone.getZoneDecoup() != null && zonesPvAct.contains(pmZone.getZoneDecoup())) {
					// Si la ZC n'est plus active on ferme le lien
					if ((pmZone.getZoneDecoup() != null && (pmZone.getZoneDecoup().getDateFermeture() != null
							&& pmZone.getZoneDecoup().getDateFermeture().compareTo(currentDate) < 0))) {
						// Si date ouverture est avant date d'aujd - 1 jour, on set cette date de fin,
						// sinon on set à la date ouverture
						if (pmZone.getDateOuverture().compareTo(cal.getTime()) < 0) {
							pmZone.setDateFermeture(cal.getTime());
							pmZone.setDateModif(currentDate);
							pmZone.setSignature(pSignature);
							zcLinksToUpdate.put(pmZone.getCle(), pmZone);
						} else {
							pmZone.setDateFermeture(pmZone.getDateOuverture());
							pmZone.setDateModif(currentDate);
							pmZone.setSignature(pSignature);
							zcLinksToUpdate.put(pmZone.getCle(), pmZone);
						}
						nbZCpvActives--;
					}
					// Business rules sur les ZC si pls liens non bornés :
					// la ZC privilegiee retournee est celle qui a la date de debut de lien la plus
					// recente
					// on vérifie aussi que la date d'ouverture est dans le passé (pour ne pas
					// sélectionner une ZC future)
					else if (pmZone.getZoneDecoup().getClass().equals(ZoneComm.class)
							&& "O".equals(pmZone.getLienPrivilegie())
							&& pmZone.getDateOuverture().compareTo(currentDate) < 0) {
						if (dateDebutLienPlusRecente == null) {
							dateDebutLienPlusRecente = pmZone.getDateOuverture();
							// si la date d'ouverture de cette pmZone est plus récente on l'assigne
						} else if (pmZone.getDateOuverture().compareTo(dateDebutLienPlusRecente) > 0) {
							dateDebutLienPlusRecente = pmZone.getDateOuverture();
						}
						if (dateModificationPlusRecente == null) {
							dateModificationPlusRecente = pmZone.getDateModif();
							// si la date d'ouverture de cette pmZone est plus récente on l'assigne
						} else if (pmZone.getDateModif().compareTo(dateModificationPlusRecente) > 0) {
							dateModificationPlusRecente = pmZone.getDateModif();
						}

						if (pmZone.getDateFermeture() != null && pmZone.getDateFermeture().compareTo(currentDate) > 0) {
							oneLinkWithEndDate = true;
						} else if (pmZone.getDateFermeture() == null) {
							oneLinkWithoutEndDate = true;
						}
					}
				}
			}

			// Si il y a dans la liste des liens ZC bornes (avec donc une date de fermeture
			// de lien dans le futur)
			// et des liens non bornes (avec donc une date de fermeture de lien non
			// renseignee, a null),
			// on change la date de fin de lien de tous les liens bornes a J-1 si c'est
			// possible
			// (cad si J-1 > date ouverture du lien), sinon a la date ouverture du lien.
			// désactivé pour permettre l'ajout d'une ZC primaire dans le futur
			/*
			 * if(nbZCpvActives>1){
			 * 
			 * if(oneLinkWithEndDate && oneLinkWithoutEndDate){ for(PmZone pmZone :
			 * pPersonneMorale.getPmZones()){
			 * if(pmZone.getZoneDecoup().getClass().equals(ZoneComm.class) &&
			 * "O".equals(pmZone.getLienPrivilegie())){ // On supprime les ZC actives pv qui
			 * ont une date de fermeture future if(pmZone.getDateFermeture()!=null ){
			 * pmZone.setDateFermeture(cal.getTime()); pmZone.setDateModif(currentDate);
			 * pmZone.setSignature(pSignature); zcLinksToUpdate.put(pmZone.getCle(),
			 * pmZone); nbZCpvActives--; } } } } }
			 */
			// Si nbZCpvActives>1, il y a pls zc actives privilégiées,
			// on ne remonte que celle qui a la date de lien d'ouverture le plus récent
			if (nbZCpvActives > 1) {
				for (PmZone pmZone : pPersonneMorale.getPmZones()) {
					if (pmZone.getZoneDecoup() != null && zonesPvAct.contains(pmZone.getZoneDecoup())) {
						if (pmZone.getZoneDecoup().getClass().equals(ZoneComm.class)
								&& "O".equals(pmZone.getLienPrivilegie())) {
							// On supprime les ZC actives pv qui ont une date d'ouverture antérieure à la
							// plus récente (et active)
							// mais on garde les autres (future ZC)
							if (pmZone.getDateOuverture() == null
									|| pmZone.getDateOuverture().compareTo(dateDebutLienPlusRecente) < 0) {
								if (pmZone.getDateOuverture().compareTo(cal.getTime()) < 0) {
									pmZone.setDateFermeture(cal.getTime());
									pmZone.setDateModif(currentDate);
									pmZone.setSignature(pSignature);
									zcLinksToUpdate.put(pmZone.getCle(), pmZone);
								} else {
									pmZone.setDateFermeture(pmZone.getDateOuverture());
									pmZone.setDateModif(currentDate);
									pmZone.setSignature(pSignature);
									zcLinksToUpdate.put(pmZone.getCle(), pmZone);
								}
								nbZCpvActives--;
							}
						}
					}
				}
			}
			// Si nbZCpvActives>1, il y a encore pls zc actives privilégiées,
			// on ne remonte que celle qui a la date de modification la plus récente
			// mais on fait une exception en cas de ZC future
			if (nbZCpvActives > 1) {
				for (PmZone pmZone : pPersonneMorale.getPmZones()) {
					if (pmZone.getZoneDecoup() != null && zonesPvAct.contains(pmZone.getZoneDecoup())) {
						// On cherche la date de modification la plus récente
						if (pmZone.getZoneDecoup().getClass().equals(ZoneComm.class)
								&& "O".equals(pmZone.getLienPrivilegie())
								&& pmZone.getDateOuverture().compareTo(currentDate) < 0) {
							if (pmZone.getDateModif() == null
									|| pmZone.getDateModif().compareTo(dateModificationPlusRecente) != 0) {
								if (pmZone.getDateOuverture().compareTo(cal.getTime()) < 0) {
									pmZone.setDateFermeture(cal.getTime());
									pmZone.setDateModif(currentDate);
									pmZone.setSignature(pSignature);
									zcLinksToUpdate.put(pmZone.getCle(), pmZone);
								} else {
									pmZone.setDateFermeture(pmZone.getDateOuverture());
									pmZone.setDateModif(currentDate);
									pmZone.setSignature(pSignature);
									zcLinksToUpdate.put(pmZone.getCle(), pmZone);
								}
								nbZCpvActives--;
							}
						}
					}
				}
			}

			// Si nbZCpvActives>1, il y a encore pls zc actives privilégiées,
			// on n'en remonte qu'une aléatoire
//        	if(nbZCpvActives>1){
//        		int onlyOneZCPvActive =0;
//        		for(PmZone pmZone : pPersonneMorale.getPmZones()){
//            		PmZoneDTO pmZoneDTO = iterator4.next();
//            		if(pmZoneDTO.getZoneDecoup().getClass().equals(ZoneCommDTO.class) && "O".equals(pmZoneDTO.getLienPrivilegie())){
//	            		if(onlyOneZCPvActive >0){
//	            			iterator4.remove();
//	            			nbZCpvActives--;
//	            		} else{
//	            			onlyOneZCPvActive++;
//	            		}
//            		}
//            	}
//        	}	

			// Enregistrement en base

			for (PmZone link : zcLinksToUpdate.values()) {
				pmZoneRepository.saveAndFlush(link);
			}

		}
	}

	public void createZcLinkWithAgency(PmZone pmZoneComm, Agence agency) throws JrafDomainException {
		if (!pmZoneComm.getZoneDecoup().getClass().equals(ZoneComm.class))
			throw new JrafDomainException("Invalid ZoneDecoup != ZoneComm");

		ZoneComm zoneComm = null;
		List<ZoneComm> results = zoneCommRepository.findAll(Example.of((ZoneComm) pmZoneComm.getZoneDecoup()));
		if (results != null && results.size() > 0)
			zoneComm = results.get(0);
		else
			throw new JrafDomainException("346 - ZoneComm not found");

		pmZoneComm.setZoneDecoup(zoneComm);

		pmZoneRepository.saveAndFlush(pmZoneComm);
	}

	public void createZvLinkWithAgency(PmZone pmZoneVente, Agence agency) throws JrafDomainException {
		if (!pmZoneVente.getZoneDecoup().getClass().equals(ZoneVente.class))
			throw new JrafDomainException("Invalid ZoneDecoup != ZoneVente");

		ZoneVente zoneVente = null;
		List<ZoneVente> results = zoneVenteRepository.findAll(Example.of((ZoneVente) pmZoneVente.getZoneDecoup()));
		if (results != null && results.size() > 0)
			zoneVente = results.get(0);
		else
			throw new JrafDomainException("173 - ZoneVente not found");

		pmZoneVente.setZoneDecoup(zoneVente);

		pmZoneRepository.saveAndFlush(pmZoneVente);
	}

	@Transactional
	public ZoneComm checkZcValidity(PmZoneDTO pmZoneCommDTO) throws JrafDomainException {
		if (!pmZoneCommDTO.getZoneDecoup().getClass().equals(ZoneCommDTO.class))
			throw new JrafDomainException("Invalid ZoneDecoup != ZoneComm");

		PmZone pmZoneComm = PmZoneTransform.dto2Bo(pmZoneCommDTO);
		List<ZoneComm> results = zoneCommRepository.findAll(Example.of((ZoneComm) pmZoneComm.getZoneDecoup()));
		boolean bValidFound = false;
		if (results != null && results.size() > 0) {
			for (ZoneComm zc : results) {
				if (zc.getDateFermeture() == null || zc.getDateFermeture().after(new Date())) {
					bValidFound = true;
					return zc;
				}
			}

			if (!bValidFound)
				throw new JrafDomainException("119 - ZoneComm invalid Date");
		} else
			throw new JrafDomainException("346 - ZoneComm not found");

		return null;
	}

	@Transactional
	public ZoneVente checkZvValidity(PmZoneDTO pmSaleZoneDTO) throws JrafDomainException {
		if (!pmSaleZoneDTO.getZoneDecoup().getClass().equals(ZoneVenteDTO.class))
			throw new JrafDomainException("Invalid ZoneDecoup != ZoneVente");

		PmZone pmZoneVente = PmZoneTransform.dto2Bo(pmSaleZoneDTO);
		List<ZoneVente> results = zoneVenteRepository.findAll(Example.of((ZoneVente) pmZoneVente.getZoneDecoup()));
		boolean bValidFound = false;
		if (results != null && results.size() > 0) {
			for (ZoneVente zv : results) {
				if (zv.getDateFermeture() == null || zv.getDateFermeture().after(new Date())) {
					bValidFound = true;
					return zv;
				}
			}

			if (!bValidFound)
				throw new JrafDomainException("119 - ZoneVente invalid Date");
		} else
			throw new JrafDomainException("173 - ZoneVente not found");

		return null;

	}

	public void setEndDate(PmZone pmZone, AgenceDTO agencyDTO) {

		Date currentDate = new Date();
		Date currentDateMidnight = SicDateUtils.midnight(currentDate);
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentDateMidnight);
		cal.add(Calendar.DAY_OF_MONTH, -1);

		if (pmZone.getDateOuverture().compareTo(cal.getTime()) < 0) {
			pmZone.setDateFermeture(cal.getTime());
			pmZone.setDateModif(currentDate);
			pmZone.setSignature(agencyDTO.getSignatureModification());
		} else {
			pmZone.setDateFermeture(pmZone.getDateOuverture());
			pmZone.setDateModif(currentDate);
			pmZone.setSignature(agencyDTO.getSignatureModification());
		}

	}

	@Transactional
	public PmZone updatePmZoneFictiveAgency(PmZoneDTO pmZoneDTO, AgenceDTO agencyDTO) throws JrafDomainException {

		Optional<PmZone> pmZoneOpt = pmZoneRepository.findById(pmZoneDTO.getCle());

		if (!pmZoneOpt.isPresent() || !agencyDTO.getGin().equals(pmZoneOpt.get().getPersonneMorale().getGin())) {
			throw new JrafDomainException("346 - Zone not found");
		}

		PmZone pmZone = pmZoneOpt.get();

		if (!agencyDTO.getGin().equals(pmZone.getPersonneMorale().getGin())) {
			throw new JrafDomainException("346 - Zone not found");
		}

		setEndDate(pmZone, agencyDTO);

		return pmZoneRepository.saveAndFlush(pmZone);

	}

	@Transactional
	public void createZcLinkWithAgencyDTO(PmZoneDTO pmZoneCommDTO, AgenceDTO agencyDTO) throws JrafDomainException {

		if (!pmZoneCommDTO.getZoneDecoup().getClass().equals(ZoneCommDTO.class)) {
			throw new JrafDomainException("Invalid ZoneDecoup != ZoneComm");
		}

		pmZoneCommDTO.setPersonneMorale(agencyDTO);
		ZoneComm zoneComm = null;
		PmZone pmZoneComm = PmZoneTransform.dto2Bo(pmZoneCommDTO);
		List<ZoneComm> results = zoneCommRepository.findAll(Example.of((ZoneComm) pmZoneComm.getZoneDecoup()));
		boolean bValidFound = false;
		if (results != null && results.size() > 0) {
			for (ZoneComm zc : results) {
				if (zc.getDateFermeture() == null || zc.getDateFermeture().after(agencyDTO.getDateCreation())) {
					bValidFound = true;
					zoneComm = zc;
				}
			}

			if (!bValidFound)
				throw new JrafDomainException("119 - ZoneComm invalid Date");
		} else {
			throw new JrafDomainException("346 - ZoneComm not found");
		}

		if (PrivilegedLinkEnum.YES.equals(PrivilegedLinkEnum.fromLiteral(pmZoneCommDTO.getLienPrivilegie()))) {

			List<PmZone> listPmZoneComm = pmZoneRepository.findByPMGin(agencyDTO.getGin());

			for (PmZone pmZoneToUpdate : listPmZoneComm) {

				if (ZoneComm.class.equals(pmZoneToUpdate.getZoneDecoup().getClass())) {

					if (PrivilegedLinkEnum.YES
							.equals(PrivilegedLinkEnum.fromLiteral(pmZoneToUpdate.getLienPrivilegie()))
							&& (pmZoneToUpdate.getDateFermeture() == null
									|| pmZoneToUpdate.getDateFermeture().after(pmZoneComm.getDateOuverture()))) {

						setEndDate(pmZoneToUpdate, agencyDTO);
						pmZoneRepository.saveAndFlush(pmZoneToUpdate);

					}
				}
			}
		}

		pmZoneComm.setZoneDecoup(zoneComm);
		pmZoneComm.setDateModif(new Date());
		pmZoneComm.setSignature(agencyDTO.getSignatureModification());

		pmZoneRepository.saveAndFlush(pmZoneComm);
	}

	@Transactional
	public void createZvLinkWithAgencyDTO(PmZoneDTO pmZoneVenteDTO, AgenceDTO agencyDTO) throws JrafDomainException {
		if (!pmZoneVenteDTO.getZoneDecoup().getClass().equals(ZoneVenteDTO.class))
			throw new JrafDomainException("Invalid ZoneDecoup != ZoneVente");

		pmZoneVenteDTO.setPersonneMorale(agencyDTO);
		ZoneVente zoneVente = null;
		PmZone pmZoneVente = PmZoneTransform.dto2Bo(pmZoneVenteDTO);
		List<ZoneVente> results = zoneVenteRepository.findAll(Example.of((ZoneVente) pmZoneVente.getZoneDecoup()));
		boolean bValidFound = false;
		if (results != null && results.size() > 0) {
			for (ZoneVente zv : results) {
				if (zv.getDateFermeture() == null || zv.getDateFermeture().after(agencyDTO.getDateCreation())) {
					bValidFound = true;
					zoneVente = zv;
				}
			}

			if (!bValidFound)
				throw new JrafDomainException("119 - ZoneVente invalid Date");

			/*
			 * if (zoneVente.getDateFermeture() != null &&
			 * zoneVente.getDateFermeture().before(agencyDTO.getDateCreation())) { throw new
			 * JrafDomainException("119 - ZoneVente invalid Date"); }
			 */
		} else {
			throw new JrafDomainException("173 - ZoneVente not found");
		}

		if (PrivilegedLinkEnum.YES.equals(PrivilegedLinkEnum.fromLiteral(pmZoneVente.getLienPrivilegie()))) {

			List<PmZone> listPmZoneVente = pmZoneRepository.findByPMGin(agencyDTO.getGin());

			for (PmZone pmZoneToUpdate : listPmZoneVente) {

				if (ZoneVente.class.equals(pmZoneToUpdate.getZoneDecoup().getClass())) {

					if (PrivilegedLinkEnum.YES
							.equals(PrivilegedLinkEnum.fromLiteral(pmZoneToUpdate.getLienPrivilegie()))
							&& (pmZoneToUpdate.getDateFermeture() == null
									|| pmZoneToUpdate.getDateFermeture().after(pmZoneVente.getDateOuverture()))) {

						setEndDate(pmZoneToUpdate, agencyDTO);
						pmZoneRepository.saveAndFlush(pmZoneToUpdate);

					}
				}
			}
		}

		pmZoneVente.setZoneDecoup(zoneVente);
		pmZoneVente.setDateModif(new Date());
		pmZoneVente.setSignature(agencyDTO.getSignatureModification());

		pmZoneRepository.saveAndFlush(pmZoneVente);

	}

	@Transactional(rollbackFor = JrafDomainException.class)
	public void managePmZoneForFictiveAgency(List<PmZoneDTO> pmZonesDTOs, AgenceDTO currentAgencyDTO)
			throws JrafDomainException {

		// Saving PmZonesDTOs...
		for (PmZoneDTO pmZoneDTO : pmZonesDTOs) {

			// Link with a ZoneVente
			if (pmZoneDTO.getZoneDecoup().getClass().equals(ZoneVenteDTO.class)) {
				if (pmZoneDTO.getCle() != null) {
					updatePmZoneFictiveAgency(pmZoneDTO, currentAgencyDTO);
				} else {
					createZvLinkWithAgencyDTO(pmZoneDTO, currentAgencyDTO);
				}
			}

			// Link with a ZoneComm
			if (pmZoneDTO.getZoneDecoup().getClass().equals(ZoneCommDTO.class)) {

				if (pmZoneDTO.getCle() != null) {
					updatePmZoneFictiveAgency(pmZoneDTO, currentAgencyDTO);
				} else {
					createZcLinkWithAgencyDTO(pmZoneDTO, currentAgencyDTO);
				}
			}
		}
	}

	@Transactional
	public void deleteZoneLinkWithAgencyDTO(PmZoneDTO zv) throws JrafDomainException {
		pmZoneRepository.delete(PmZoneTransform.dto2Bo(zv));
	}

	@Transactional
	public boolean deleteZoneLinkById(@NotNull final Long id) throws JrafDomainException {
		try {
			PmZone pmZone = pmZoneRepository.getOne(id);
			PersonneMorale pm = pmZone.getPersonneMorale();
			pm.setSignatureModification(SIGNATURE_REMOVE_PM_ZONE);
			Date today = new Date();
			pm.setDateModification(today);

			// Delete zone link
			pmZoneRepository.deleteById(id);

			// Update agency
			personneMoraleRepository.saveAndFlush(pm);
		} catch (Exception e) {
			return false;
		}
		return true;

	}

	/**
	 * Retrieves the active zone links (sales or commercial) for specified zone gin
	 * and legal entity gin
	 * 
	 * @param iginZone the zone gin
	 * @param sgin     personne morale gin
	 * @return the active zone links (sales or commercial) for specified zone gin
	 *         and legal entity gin
	 * @throws JrafDomainException
	 */
	public List<PmZoneDTO> findAllActiveByIginZonePm(@NotNull Long iginZone, @NotNull String sgin)
			throws JrafDomainException {
		List<PmZone> pmZoneList = pmZoneRepository.findAllActiveByIginZonePm(iginZone, sgin);
		List<PmZoneDTO> result = new ArrayList<>();
		if (pmZoneList != null) {
			for (PmZone pmZone : pmZoneList) {
				result.add(PmZoneTransform.bo2Dto(pmZone));
			}
		}
		return result;
	}

	/**
	 * Creates a Commercial zone link using the following rules:
	 * <ul>
	 * If privileged zone is being created:
	 * <li>New privileged zone created</li>
	 * <li>Non-priviledged zone (for the same gin) is deleted</li>
	 * <li>Active priviledged zone's end date is updated to one day before new
	 * zone's start date</li>
	 * <li>Future priviledged zones are deleted</li>
	 * </ul>
	 * <ul>
	 * If non-privileged zone is being created:
	 * <li>Existng non-priviledge (same gin) zone is deleted</li>
	 * <li>New non-priviledge zone is created</li>
	 * </ul>
	 * 
	 * @param pmZoneDto
	 * @since REPIND-2054 Migration BatchLienAgenceZcZV
	 * @throws JrafDomainException
	 */
	@Transactional
	public void createAgenceZoneCommLink(PmZoneDTO pmZoneDto) throws JrafDomainException {
		PmZone pmZoneToCreate = PmZoneTransform.dto2Bo(pmZoneDto);
		populatePersonneMorale(pmZoneToCreate);
		PersonneMorale pmTobeUpdated = pmZoneToCreate.getPersonneMorale();

		Map<Long, PmZone> pmZonesToUpdate = new HashMap<>();
		Map<Long, PmZone> pmZonesToDelete = new HashMap<>();

		if (PrivilegedLinkEnum.YES.toLiteral().equals(pmZoneToCreate.getLienPrivilegie())) {
			alterZoneLinks(pmZoneToCreate, pmTobeUpdated.fetchPrivilegedCommercialZoneLinks(), pmZonesToUpdate,
					pmZonesToDelete);
			deleteNonPriviledgedZoneCommLink(pmZoneToCreate, pmTobeUpdated, pmZonesToDelete);
		} else {
			deleteNonPriviledgedZoneCommLink(pmZoneToCreate, pmTobeUpdated, pmZonesToDelete);
		}

		// DB changes
		pmZoneRepository.saveAndFlush(pmZoneToCreate);
		for (PmZone pmZone : pmZonesToUpdate.values()) {
			pmZoneRepository.saveAndFlush(pmZone);
		}
		for (PmZone pmZone : pmZonesToDelete.values()) {
			pmZoneRepository.delete(pmZone);
		}
		personneMoraleRepository.refresh(pmTobeUpdated);
	}

	/**
	 * Creates a Sales zone link using the following rules:
	 * <ul>
	 * <li>New privileged zone created</li>
	 * <li>Active priviledged zone's end date is updated to one day before new
	 * zone's start date</li>
	 * <li>Future priviledged zones are deleted</li>
	 * </ul>
	 * 
	 * @param pmZoneDto
	 * @since REPIND-2054 Migration BatchLienAgenceZcZV
	 * @throws JrafDomainException
	 */
	@Transactional
	public void createAgenceZoneVenteLink(PmZoneDTO pmZoneDto) throws JrafDomainException {
		PmZone pmZoneToCreate = PmZoneTransform.dto2Bo(pmZoneDto);
		populatePersonneMorale(pmZoneToCreate);
		PersonneMorale pmTobeUpdated = pmZoneToCreate.getPersonneMorale();

		Map<Long, PmZone> pmZonesToUpdate = new HashMap<>();
		Map<Long, PmZone> pmZonesToDelete = new HashMap<>();

		alterZoneLinks(pmZoneToCreate, pmTobeUpdated.fetchSalesZoneLinks(), pmZonesToUpdate, pmZonesToDelete);

		// DB changes
		pmZoneRepository.saveAndFlush(pmZoneToCreate);
		for (PmZone pmZone : pmZonesToUpdate.values()) {
			pmZoneRepository.saveAndFlush(pmZone);
		}
		for (PmZone pmZone : pmZonesToDelete.values()) {
			pmZoneRepository.delete(pmZone);
		}
		personneMoraleRepository.refresh(pmTobeUpdated);
	}

	public List<PmZoneDTO> findAllActiveByGinPm(@NotNull String sgin) throws JrafDomainException {
		List<PmZone> pmZoneList = this.pmZoneRepository.findAllActiveByGinPm(sgin);
		List<PmZoneDTO> result = new ArrayList();
		if (pmZoneList != null) {
			Iterator pzlLoop = pmZoneList.iterator();

			while (pzlLoop.hasNext()) {
				PmZone pmZone = (PmZone) pzlLoop.next();
				result.add(PmZoneTransform.bo2Dto(pmZone));
			}
		}

		return result;
	}

	private void deleteNonPriviledgedZoneCommLink(PmZone pmZoneToCreate, PersonneMorale pmTobeUpdated,
			Map<Long, PmZone> pmZonesToDelete) {
		for (PmZone existing : pmTobeUpdated.fetchNonPrivilegedCommercialZoneLinks()) {
			// If the zone is exacly same, then delete it
			if (existing.getZoneDecoup().getGin().equals(pmZoneToCreate.getZoneDecoup().getGin())) {
				pmZonesToDelete.put(existing.getCle(), existing);
				break;
			}
		}
	}

	private void alterZoneLinks(PmZone pmZoneToCreate, List<PmZone> existingPmZones, Map<Long, PmZone> pmZonesToUpdate,
			Map<Long, PmZone> pmZonesToDelete) {
		Date today = new Date();
		Date endDate = DateUtils.addDays(pmZoneToCreate.getDateOuverture(), -1);
		for (PmZone existing : existingPmZones) {
			// (Start date of new link to be created - 1 day)
			if (existing.getDateOuverture().compareTo(pmZoneToCreate.getDateOuverture()) < 0
					&& (existing.getDateFermeture() == null || existing.getDateFermeture().compareTo(endDate) > 0)) {
				existing.setDateFermeture(endDate);
				existing.setSignature(pmZoneToCreate.getSignature());
				existing.setDateModif(today);
				pmZonesToUpdate.put(existing.getCle(), existing);
			}
			// Delete PmZone that would be active in future
			else if (existing.getDateOuverture().compareTo(pmZoneToCreate.getDateOuverture()) >= 0) {
				pmZonesToDelete.put(existing.getCle(), existing);
			}
			// If new PmZone to be created is in the future, delete all zones that would be
			// active in future
			if (existing.getDateOuverture().compareTo(today) > 0
					&& pmZoneToCreate.getDateOuverture().compareTo(today) > 0) {
				pmZonesToDelete.put(existing.getCle(), existing);
			}
		}
	}

	private void populatePersonneMorale(PmZone pmZoneToCreate) {
		PersonneMorale pmLight = pmZoneToCreate.getPersonneMorale();

		// Fetch the fully loaded Personne morale for processing
		Optional<PersonneMorale> pmSearch = personneMoraleRepository.findById(pmLight.getGin());
		PersonneMorale personneMorale = null;
		if (pmSearch.isPresent()) {
			personneMorale = pmSearch.get();
			personneMorale.setSignatureModification(pmLight.getSignatureModification());
			personneMorale.setSiteModification(pmLight.getSiteModification());
			pmZoneToCreate.setPersonneMorale(personneMorale);
		}
	}

	public void createOrUpdateOrDeleteZvLinksV2(List<PmZone> pZvLinks, PersonneMorale pPersonneMorale)
			throws JrafDomainException {

		Assert.notNull(pZvLinks);
		Assert.notNull(pPersonneMorale);
		Assert.notNull(pPersonneMorale.getGin());
		for (PmZone zvLink : pZvLinks) {
			Assert.notNull(zvLink);
			Assert.notNull(zvLink.getZoneDecoup());
			Assert.isInstanceOf(ZoneVente.class, zvLink.getZoneDecoup());
		}

		if (pPersonneMorale.getPmZones() == null) {
			pPersonneMorale.setPmZones(new HashSet<PmZone>());
		}

		List<PmZone> zvLinksToCreate = new ArrayList<PmZone>();
		Map<Long, PmZone> zvLinksToDelete = new HashMap<Long, PmZone>();
		Map<Long, PmZone> zvLinksToUpdate = new HashMap<Long, PmZone>();
		int countPrivilegedLink = 0;

		// aujourd'hui
		Date now = new Date();

		// aujourd'hui minuit
		Date nowMidnight = SicDateUtils.midnight(now);

		for (PmZone zvLink : pZvLinks) {

			if (zvLink.getCle() == null) {

				// Lien � cr�er
				// --------------

				// Specif agency
				if (Agence.class.equals(pPersonneMorale.getClass())) {

					// ZV0 � ZV3 sont requis car on ne peut cr�er de liens qu'avec des ZV de niveau
					// 3
					if (!zoneVenteUS.checkZv0ToZv3((ZoneVente) zvLink.getZoneDecoup())) {

						// ERREUR 258 - INCOMPLETE SALES ZONE - ZV0�3 Obligatoires
						throw new JrafDomainRollbackException("258");
					}
				}

				// recherche de la ZV
				try {
					Integer zv0 = ((ZoneVente) zvLink.getZoneDecoup()).getZv0();
					Integer zv1 = ((ZoneVente) zvLink.getZoneDecoup()).getZv1();
					Integer zv2 = ((ZoneVente) zvLink.getZoneDecoup()).getZv2();
					Integer zv3 = ((ZoneVente) zvLink.getZoneDecoup()).getZv3();
					ZoneVente zv = zoneVenteRepository.findActiveByZv0Zv1Zv2Zv3(zv0, zv1, zv2, zv3);
					zvLink.setZoneDecoup(zv);
				} catch (NoResultException e) {
					// ERREUR 346 - ZONE NOT FOUND
					throw new JrafDomainRollbackException("346"); // TODO CEP/MBE il faudrait pr�ciser ZV
				} catch (NonUniqueResultException e) {
					// ERREUR 346 - ZONE NOT FOUND
					throw new JrafDomainRollbackException("346"); // TODO CEP/MBE il faudrait pr�ciser ZV
				}

				// si la date d'ouverture est vide, on l'initialise � aujourd'hui minuit
				// sinon, on l'initialise � minuit de la m�me date
				if (zvLink.getDateOuverture() == null) {
					zvLink.setDateOuverture(nowMidnight);
				} else {
					zvLink.setDateOuverture(SicDateUtils.midnight(zvLink.getDateOuverture()));
				}
				checkStartDate(zvLink, pPersonneMorale, zvLink.getZoneDecoup());

				// si la date de fermeture est renseign�e, on l'initialise � minuit de la m�me
				// date
				if (zvLink.getDateFermeture() != null) {

					zvLink.setDateFermeture(SicDateUtils.midnight(zvLink.getDateFermeture()));
					checkEndDate(zvLink, pPersonneMorale, zvLink.getZoneDecoup());
				}

				checkPrivilegedLink(zvLink, pPersonneMorale);

				// si le nouveau lien � cr�er est un lien privil�gi�
				if (PrivilegedLinkEnum.YES.equals(PrivilegedLinkEnum.fromLiteral(zvLink.getLienPrivilegie()))) {

					countPrivilegedLink++;

					// on ne peut cr�er qu'un seul lien privil�gi� � la fois
					if (countPrivilegedLink > 1) {

						// TODO LBN cr�er erreur "CANNOT CREATE MORE THAN ONE PRIVILEGED ZV LINK"
						throw new JrafDomainRollbackException("CANNOT CREATE MORE THAN ONE PRIVILEGED ZV LINK");
					}

					// la date de fermeture du lien privil�gi� � cr�er ne doit pas etre renseign�e
					if (zvLink.getDateFermeture() != null) {

						// TODO LBN cr�er erreur "CANNOT CREATE PRIVILEGED ZV LINK WITH END DATE"
						throw new JrafDomainRollbackException("CANNOT CREATE PRIVILEGED ZV LINK WITH END DATE");
					}

					// Close links that start before or end after the new link start date
					// Delete links that start after the new link start date
					for (PmZone existingPrivilegedZvLink : pPersonneMorale.fetchSalesZoneLinks()) {

						if (existingPrivilegedZvLink.getDateOuverture().compareTo(zvLink.getDateOuverture()) < 0
								&& (existingPrivilegedZvLink.getDateFermeture() == null || existingPrivilegedZvLink
										.getDateFermeture().compareTo(zvLink.getDateOuverture()) >= 0)) {

							Calendar cal = Calendar.getInstance();
							cal.setTime(zvLink.getDateOuverture());
							cal.add(Calendar.DAY_OF_MONTH, -1);
							existingPrivilegedZvLink.setDateFermeture(cal.getTime());
							existingPrivilegedZvLink.setOrigine(null);
							existingPrivilegedZvLink.setSignature(zvLink.getSignature());
							existingPrivilegedZvLink.setDateModif(now);
							zvLinksToUpdate.put(existingPrivilegedZvLink.getCle(), existingPrivilegedZvLink);

						} else if (existingPrivilegedZvLink.getDateOuverture()
								.compareTo(zvLink.getDateOuverture()) >= 0) {

							zvLinksToDelete.put(existingPrivilegedZvLink.getCle(), existingPrivilegedZvLink);
						}
					}

					// on supprime physiquement les plus anciennes ZV Priv histo (inactive)
					// pour n'en conserver qu'une max
					PmZone pmZvInactDateFermetureRecente = null;
					for (PmZone existingPrivilegedInactiveZvLink : pPersonneMorale
							.fetchPastPrivilegedSalesZoneLinks()) {

						if (pmZvInactDateFermetureRecente != null && pmZvInactDateFermetureRecente.getDateFermeture()
								.before(existingPrivilegedInactiveZvLink.getDateFermeture())) {
							zvLinksToDelete.put(pmZvInactDateFermetureRecente.getCle(), pmZvInactDateFermetureRecente);
							pmZvInactDateFermetureRecente = existingPrivilegedInactiveZvLink;
						}

						if (pmZvInactDateFermetureRecente == null) { // Init premiere occurence
							pmZvInactDateFermetureRecente = existingPrivilegedInactiveZvLink;
						}
					}
				}

				zvLink.setDateModif(now); // � cause des r�plications qui s'appuient sur dateModif (defect 54)
				zvLink.setOrigine(null);
				zvLink.setPersonneMorale(pPersonneMorale);

				zvLinksToCreate.add(zvLink);

			} else {

				// Lien � modifier
				// -----------------

				PmZone linkTrouve = pPersonneMorale.fetchZoneLinkById(zvLink.getCle());
				if (linkTrouve != null && ZoneVente.class.equals(linkTrouve.getZoneDecoup().getClass())) {

					// il n'est pas possible de modfier un lien privil�gi� (en effet, il faut passer
					// par la cr�ation pour fermer celui existant et en cr�er un nouveau)
					if (PrivilegedLinkEnum.YES.equals(PrivilegedLinkEnum.fromLiteral(linkTrouve.getLienPrivilegie()))) {

						// TODO LBN cr�er erreur "CANNOT UPDATE PRIVILEGED ZV LINK"
						throw new JrafDomainRollbackException("CANNOT UPDATE PRIVILEGED ZV LINK");
					}

					if (!isEmptyData(zvLink)) {

						// on ne peut modifier que la date de fermeture => c'est donc la seule donn�e �
						// contr�ler
						if (zvLink.getDateFermeture() != null) {

							zvLink.setDateFermeture(SicDateUtils.midnight(zvLink.getDateFermeture()));
							checkEndDate(zvLink, pPersonneMorale, zvLink.getZoneDecoup());
						}
						linkTrouve.setDateFermeture(zvLink.getDateFermeture());

						// si origine est renseign� dans les liens existants, on met � null (demande de
						// LBN)
						linkTrouve.setOrigine(null);

						linkTrouve.setDateModif(now);
						linkTrouve.setSignature(zvLink.getSignature());
						zvLinksToUpdate.put(zvLink.getCle(), linkTrouve);
					} else {

						zvLinksToDelete.put(zvLink.getCle(), linkTrouve);
					}
				} else {

					// Erreur 338 - ZONE LINK NOT FOUND
					throw new JrafDomainRollbackException("338"); // TODO il faudrait pr�ciser qu'on traite des ZV
				}
			}
		}

		// on fait un 1er bilan des liens ZV
		// -----------------------------------

		// Pas de ZV par defaut si absence de ZV

		// on fait un 2e bilan des liens ZV
		// ----------------------------------

		List<PmZone> saleZoneLinks = new ArrayList<PmZone>(pPersonneMorale.fetchSalesZoneLinks());
		saleZoneLinks.removeAll(zvLinksToDelete.values());
		saleZoneLinks.addAll(zvLinksToCreate);

		int cntPrivilegedLink = 0;
		for (PmZone zvLink : saleZoneLinks) {

			ZoneVente zv = (ZoneVente) zvLink.getZoneDecoup();

			// Specif Agency
			if (Agence.class.equals(pPersonneMorale.getClass())) {

				// si les ZV li�es ne sont pas toutes de niveau 3, on sort
				if (!zoneVenteUS.checkZv0ToZv3(zv)) {

					// ERREUR 258 - INCOMPLETE SALES ZONE
					throw new JrafDomainRollbackException("258"); // TODO CEP/MBE il faudrait �galt pr�ciser la cl� du
																	// lien
				}
			}

			// si le lien est privil�gi�, il doit �tre de sous-type "FV"
			if (PrivilegedLinkEnum.YES.equals(PrivilegedLinkEnum.fromLiteral(zvLink.getLienPrivilegie()))) {

				cntPrivilegedLink++;
				if (!SubtypeZoneEnum.SALES_FORCES.equals(SubtypeZoneEnum.fromLiteral(zv.getSousType()))) {

					// ERREUR 328 - INVALID SUBTYPE ZONE
					throw new JrafDomainRollbackException("328"); // TODO CEP/MBE il faudrait �galt pr�ciser la cl� du
																	// lien
				}
			}
		}

		// Specif Agency
		if (Agence.class.equals(pPersonneMorale.getClass())) {

			// Au moins un lien privilegie actif
			if (cntPrivilegedLink == 0) {

				// ERREUR 926 - SALES ZONE MANDATORY
				throw new JrafDomainRollbackException("926");
			}
		}

		// on fait un dernier bilan des liens ZV concernant les doublons
		// Vu avec LBN et Herve Valadon
		// Si doublon sur zone privilegie ou secondaire en recouvrement de perirode =>
		// Exception Existe deja
		// ----------------------------------
		saleZoneLinks = new ArrayList<PmZone>(pPersonneMorale.fetchSalesZoneLinks());
		saleZoneLinks.removeAll(zvLinksToDelete.values());
		saleZoneLinks.addAll(zvLinksToCreate);
		for (PmZone pmZone1 : saleZoneLinks) {

			for (PmZone pmZone2 : saleZoneLinks) {

				if (((pmZone1.getCle() == null ^ pmZone2.getCle() == null) // l'une ou l'auttre des 2 cle null (pas les
																			// deux)
						|| (pmZone1.getCle() != null && !pmZone1.getCle().equals(pmZone2.getCle()))) // ou les 2 cles
																										// valorisees
																										// mais
																										// differentes
						&& pmZone1.getZoneDecoup() != null && pmZone2.getZoneDecoup() != null
						&& pmZone1.getZoneDecoup().getGin().equals(pmZone2.getZoneDecoup().getGin()) // Meme Zone
						&& pmZone1.getLienPrivilegie().equals(pmZone2.getLienPrivilegie()) // meme type de lien
						&& pmZone1.getDateOuverture().before(pmZone2.getDateOuverture())
						&& (pmZone1.getDateFermeture() == null
								|| !pmZone1.getDateFermeture().before(pmZone2.getDateOuverture()))) { // pmZone1.getDateFermeture()
																										// >=
																										// pmZone2.getDateOuverture()

					// TODO LBN cr�er erreur "DUPLICATE PRIVILEDGED SALES ZONE"
					throw new JrafDomainRollbackException("DUPLICATE PRIVILEDGED SALES ZONE");
				}
			}
		}

		// si origine est renseign� dans les liens existants qu'on n'update pas, on met
		// � null (demande de LBN)
		for (PmZone salesZoneLink : pPersonneMorale.fetchSalesZoneLinks()) {

			if (!zvLinksToUpdate.containsKey(salesZoneLink.getCle()) && salesZoneLink.getOrigine() != null) {

				salesZoneLink.setOrigine(null);
				salesZoneLink.setDateModif(now);
				salesZoneLink.setSignature(pPersonneMorale.getSignatureModification());
				zvLinksToUpdate.put(salesZoneLink.getCle(), salesZoneLink);
			}
		}

		// TODO LBN : quel est l'impact du statut PM (actif, temporaire, ferm�, ...) sur
		// les liens ZV ?

		// Enregistrement en base
		for (PmZone link : zvLinksToCreate) {
			pmZoneRepository.saveAndFlush(link);
		}
		for (PmZone link : zvLinksToUpdate.values()) {
			pmZoneRepository.saveAndFlush(link);
		}
		for (PmZone link : zvLinksToDelete.values()) {
			pmZoneRepository.delete(link);
		}
		personneMoraleRepository.refresh(pPersonneMorale);
	}

	/**
	 * Retrieves pmZone list by PM gin
	 *
	 * @param sgin personne morale gin
	 * @return pmZoneList
	 * @throws JrafDomainException
	 */
	@Transactional(readOnly = true)
	public List<PmZoneDTO> findPmZoneByZoneGin(@NotNull Long iginZone, @NotNull String sgin)
			throws JrafDomainException {
		List<PmZone> pmZoneList = pmZoneRepository.findAllByIginZonePm(iginZone, sgin);
		List<PmZoneDTO> result = new ArrayList<>();
		if (pmZoneList != null) {
			for (PmZone pmZone : pmZoneList) {
				result.add(PmZoneTransform.bo2Dto(pmZone));
			}
		}
		return result;
	}

	public Map<String, List<PmZone>> prepareCreateOrUpdateOrDeleteRa2ZvLinks(List<PmZone> pZvLinks,
			PersonneMorale pPersonneMorale) throws JrafDomainException {
		Assert.notNull(pZvLinks);
		Assert.notNull(pPersonneMorale);
		Assert.notNull(pPersonneMorale.getGin());
		for (PmZone zvLink : pZvLinks) {
			Assert.notNull(zvLink);
			Assert.notNull(zvLink.getZoneDecoup());
			Assert.isInstanceOf(ZoneVente.class, zvLink.getZoneDecoup());
		}

		if (pPersonneMorale.getPmZones() == null) {
			pPersonneMorale.setPmZones(new HashSet<>());
		}

		List<PmZone> zvLinksToCreate = new ArrayList<>();
		Map<Long, PmZone> zvLinksToDelete = new HashMap<>();
		Map<Long, PmZone> zvLinksToUpdate = new HashMap<>();
		int countPrivilegedLink = 0;

		// aujourd'hui
		Date now = new Date();

		// aujourd'hui minuit
		Date nowMidnight = SicDateUtils.midnight(now);

		for (PmZone zvLink : pZvLinks) {

			if (zvLink.getCle() == null) {

				// Lien � cr�er
				// --------------

				// Specif agency
				if (Agence.class.equals(pPersonneMorale.getClass())
						&& !zoneVenteUS.checkZv0ToZv3((ZoneVente) zvLink.getZoneDecoup())) {

					// ZV0 � ZV3 sont requis car on ne peut cr�er de liens qu'avec des ZV de niveau
					// 3
					// ERREUR 258 - INCOMPLETE SALES ZONE - ZV0�3 Obligatoires
					throw new JrafDomainException("258");
				}

				// recherche de la ZV
				try {
					Integer zv0 = ((ZoneVente) zvLink.getZoneDecoup()).getZv0();
					Integer zv1 = ((ZoneVente) zvLink.getZoneDecoup()).getZv1();
					Integer zv2 = ((ZoneVente) zvLink.getZoneDecoup()).getZv2();
					Integer zv3 = ((ZoneVente) zvLink.getZoneDecoup()).getZv3();
					ZoneVente zv = zoneVenteRepository.findActiveByZv0Zv1Zv2Zv3(zv0, zv1, zv2, zv3);
					zvLink.setZoneDecoup(zv);
				} catch (NoResultException | NonUniqueResultException e) {
					// ERREUR 346 - ZONE NOT FOUND
					throw new JrafDomainException("346");
				}

				// si la date d'ouverture est vide, on l'initialise � aujourd'hui minuit
				// sinon, on l'initialise � minuit de la m�me date
				if (zvLink.getDateOuverture() == null) {
					zvLink.setDateOuverture(nowMidnight);
				} else {
					zvLink.setDateOuverture(SicDateUtils.midnight(zvLink.getDateOuverture()));
				}
				checkStartDateRa2(zvLink, pPersonneMorale, zvLink.getZoneDecoup());

				// si la date de fermeture est renseign�e, on l'initialise � minuit de la m�me
				// date
				if (zvLink.getDateFermeture() != null) {

					zvLink.setDateFermeture(SicDateUtils.midnight(zvLink.getDateFermeture()));
					checkEndDateRa2(zvLink, pPersonneMorale, zvLink.getZoneDecoup());
				}

				checkPrivilegedLinkRa2(zvLink, pPersonneMorale);

				// si le nouveau lien � cr�er est un lien privil�gi�
				if (PrivilegedLinkEnum.YES.equals(PrivilegedLinkEnum.fromLiteral(zvLink.getLienPrivilegie()))) {

					countPrivilegedLink++;

					// on ne peut cr�er qu'un seul lien privil�gi� � la fois
					if (countPrivilegedLink > 1) {

						throw new JrafDomainException("CANNOT CREATE MORE THAN ONE PRIVILEGED ZV LINK");
					}

					// la date de fermeture du lien privil�gi� � cr�er ne doit pas etre renseign�e
					if (zvLink.getDateFermeture() != null) {

						throw new JrafDomainException("CANNOT CREATE PRIVILEGED ZV LINK WITH END DATE");
					}

					// on ferme le(s) lien(s) privil�gi�(s) dont l'intervalle comprend la date
					// d'ouverture du lien privil�gi� � cr�er
					// on supprime le(s) lien(s) privil�gi�(s) qui sont futur au lien privil�gi� �
					// cr�er
					for (PmZone existingPrivilegedZcLink : pPersonneMorale.fetchPrivilegedCommercialZoneLinks()) {

						if (existingPrivilegedZcLink.getDateOuverture().compareTo(zvLink.getDateOuverture()) < 0
								&& (existingPrivilegedZcLink.getDateFermeture() == null || existingPrivilegedZcLink
										.getDateFermeture().compareTo(zvLink.getDateOuverture()) >= 0)) {

							Calendar cal = Calendar.getInstance();
							cal.setTime(zvLink.getDateOuverture());
							cal.add(Calendar.DAY_OF_MONTH, -1);
							existingPrivilegedZcLink.setDateFermeture(cal.getTime());
							existingPrivilegedZcLink.setOrigine(null);// si origine est renseign�, on met � null
							// (demande de LBN)
							existingPrivilegedZcLink.setSignature(zvLink.getSignature());
							existingPrivilegedZcLink.setDateModif(now);
							zvLinksToUpdate.put(existingPrivilegedZcLink.getCle(), existingPrivilegedZcLink);

						} else if (existingPrivilegedZcLink.getDateOuverture()
								.compareTo(zvLink.getDateOuverture()) >= 0) {

							zvLinksToDelete.put(existingPrivilegedZcLink.getCle(), existingPrivilegedZcLink);
						}
					}

					// on supprime physiquement les plus anciennes ZV Priv histo (inactive)
					// pour n'en conserver qu'une max
					PmZone pmZvInactDateFermetureRecente = null;
					for (PmZone existingPrivilegedInactiveZvLink : pPersonneMorale
							.fetchPastPrivilegedSalesZoneLinks()) {

						if (pmZvInactDateFermetureRecente != null && pmZvInactDateFermetureRecente.getDateFermeture()
								.before(existingPrivilegedInactiveZvLink.getDateFermeture())) {
							zvLinksToDelete.put(pmZvInactDateFermetureRecente.getCle(), pmZvInactDateFermetureRecente);
							pmZvInactDateFermetureRecente = existingPrivilegedInactiveZvLink;
						}

						if (pmZvInactDateFermetureRecente == null) { // Init premiere occurence
							pmZvInactDateFermetureRecente = existingPrivilegedInactiveZvLink;
						}
					}
				}

				zvLink.setDateModif(now);
				zvLink.setOrigine(null);
				zvLink.setPersonneMorale(pPersonneMorale);

				zvLinksToCreate.add(zvLink);

			} else {

				// Lien � modifier
				// -----------------

				PmZone linkTrouve = pPersonneMorale.fetchZoneLinkById(zvLink.getCle());
				if (linkTrouve != null && ZoneVente.class.equals(linkTrouve.getZoneDecoup().getClass())) {

					if (!isEmptyData(zvLink)) {

						// on ne peut modifier que la date de fermeture => c'est donc la seule donn�e �
						// contr�ler
						if (zvLink.getDateFermeture() != null) {

							zvLink.setDateFermeture(SicDateUtils.midnight(zvLink.getDateFermeture()));
							checkEndDateRa2(zvLink, pPersonneMorale, zvLink.getZoneDecoup());
						}
						linkTrouve.setDateFermeture(zvLink.getDateFermeture());

						// si origine est renseign� dans les liens existants, on met � null (demande de
						// LBN)
						linkTrouve.setOrigine(null);

						linkTrouve.setDateModif(now);
						linkTrouve.setSignature(zvLink.getSignature());
						zvLinksToUpdate.put(zvLink.getCle(), linkTrouve);
					} else {

						zvLinksToDelete.put(zvLink.getCle(), linkTrouve);
					}
				} else {

					// Erreur 338 - ZONE LINK NOT FOUND
					throw new JrafDomainException("338");
				}
			}
		}

		// on fait un 1er bilan des liens ZV
		// -----------------------------------

		// Pas de ZV par defaut si absence de ZV

		// on fait un 2e bilan des liens ZV
		// ----------------------------------

		List<PmZone> saleZoneLinks = new ArrayList<>(pPersonneMorale.fetchSalesZoneLinks());
		saleZoneLinks.removeAll(zvLinksToDelete.values());
		saleZoneLinks.addAll(zvLinksToCreate);

		int cntPrivilegedLink = 0;
		for (PmZone zvLink : saleZoneLinks) {

			ZoneVente zv = (ZoneVente) zvLink.getZoneDecoup();

			// Specif Agency
			if (Agence.class.equals(pPersonneMorale.getClass())) {

				// si les ZV li�es ne sont pas toutes de niveau 3, on sort
				if (!zoneVenteUS.checkZv0ToZv3(zv)) {

					// ERREUR 258 - INCOMPLETE SALES ZONE
					throw new JrafDomainException("258");
				}
			}

			// si le lien est privil�gi�, il doit �tre de sous-type "FV"
			if (PrivilegedLinkEnum.YES.equals(PrivilegedLinkEnum.fromLiteral(zvLink.getLienPrivilegie()))) {

				cntPrivilegedLink++;
				if (!SubtypeZoneEnum.SALES_FORCES.equals(SubtypeZoneEnum.fromLiteral(zv.getSousType()))) {

					// ERREUR 328 - INVALID SUBTYPE ZONE
					throw new JrafDomainException("328");
				}
			}
		}

		// Specif Agency
		if (Agence.class.equals(pPersonneMorale.getClass()) && cntPrivilegedLink == 0) {

			// Au moins un lien privilegie actif
			// ERREUR 926 - SALES ZONE MANDATORY
			throw new JrafDomainException("926");
		}

		// on fait un dernier bilan des liens ZC concernant les doublons
		// Vu avec LBN et Herve Valadon
		// Si doublon sur zone privilegie ou secondaire en recouvrement de perirode =>
		// Exception Existe deja
		// ----------------------------------
		saleZoneLinks = new ArrayList<>(pPersonneMorale.fetchSalesZoneLinks());
		saleZoneLinks.removeAll(zvLinksToDelete.values());
		saleZoneLinks.addAll(zvLinksToCreate);
		/*
		 * for (PmZone pmZone1 : saleZoneLinks) {
		 *
		 * for (PmZone pmZone2 : saleZoneLinks) {
		 *
		 * if (((pmZone1.getCle() == null ^ pmZone2.getCle() == null) // l'une ou
		 * l'auttre des 2 cle null (pas les // deux) || (pmZone1.getCle() != null &&
		 * !pmZone1.getCle().equals(pmZone2.getCle()))) // ou les 2 cles // valorisees
		 * // mais // differentes && pmZone1.getZoneDecoup() != null &&
		 * pmZone2.getZoneDecoup() != null &&
		 * pmZone1.getZoneDecoup().getGin().equals(pmZone2.getZoneDecoup().getGin()) //
		 * Meme Zone && pmZone1.getLienPrivilegie().equals(pmZone2.getLienPrivilegie())
		 * // meme type de lien &&
		 * pmZone1.getDateOuverture().before(pmZone2.getDateOuverture()) &&
		 * (pmZone1.getDateFermeture() == null ||
		 * !pmZone1.getDateFermeture().before(pmZone2.getDateOuverture()))) { //
		 * pmZone1.getDateFermeture() // >= // pmZone2.getDateOuverture()
		 *
		 * throw new JrafDomainException("DUPLICATE PRIVILEDGED SALES ZONE"); } } }
		 */

		// si origine est renseign� dans les liens existants qu'on n'update pas, on met
		// � null (demande de LBN)
		for (PmZone salesZoneLink : pPersonneMorale.fetchSalesZoneLinks()) {

			if (!zvLinksToUpdate.containsKey(salesZoneLink.getCle()) && salesZoneLink.getOrigine() != null) {

				salesZoneLink.setOrigine(null);
				salesZoneLink.setDateModif(now);
				salesZoneLink.setSignature(pPersonneMorale.getSignatureModification());
				zvLinksToUpdate.put(salesZoneLink.getCle(), salesZoneLink);
			}
		}

		Map<String, List<PmZone>> pmZoneMap = new HashedMap();

		pmZoneMap.put("zvLinksToCreate", zvLinksToCreate);
		pmZoneMap.put("zvLinksToUpdate", new ArrayList<PmZone>(zvLinksToUpdate.values()));
		pmZoneMap.put("zvLinksToDelete", new ArrayList<PmZone>(zvLinksToDelete.values()));
		return pmZoneMap;
	}

	/**
	 * Check start date for creation
	 *
	 * @param pZoneLink
	 * @throws JrafDomainException
	 */
	private void checkStartDateRa2(PmZone pZoneLink, PersonneMorale pPersonneMorale, ZoneDecoup pZoneDecoup)
			throws JrafDomainException {
		if (pZoneLink == null) {
			throw new JrafDomainException("ZONE LINK MANDATORY");
		}
		if (pZoneLink.getDateOuverture() == null) {
			throw new JrafDomainException("INVALID START DATE IN ZONE LINK");
		}
		if (pPersonneMorale == null) {
			throw new JrafDomainException("LEGAL PERSON MANDATORY");
		}
		if (pZoneDecoup == null) {
			throw new JrafDomainException("INVALID ZONE IN ZONE LINK");
		}

		// la date d'ouverture du lien ne peut �tre ant�rieure � la date de cr�ation de
		// la firme
		if (pZoneLink.getDateOuverture().before(SicDateUtils.midnight(pPersonneMorale.getDateCreation()))) {

			throw new JrafDomainException("ZONE LINK START DATE BEFORE LEGAL PERSON CREATION DATE");
		}

		// la date d'ouverture du lien ne peut �tre ant�rieure � la date d'ouverture de
		// la zone
		if (pZoneLink.getDateOuverture().before(pZoneDecoup.getDateOuverture())) {

			throw new JrafDomainException("ZONE LINK START DATE BEFORE ZONE START DATE");
		}
	}

	/**
	 * @param pZoneLink
	 * @throws JrafDomainException
	 */
	private void checkEndDateRa2(PmZone pZoneLink, PersonneMorale pPersonneMorale, ZoneDecoup pZoneDecoup)
			throws JrafDomainException {

		Assert.notNull(pZoneLink);
		Assert.notNull(pZoneLink.getDateFermeture());
		Assert.notNull(pZoneLink.getDateOuverture());
		Assert.notNull(pPersonneMorale);
		Assert.notNull(pZoneDecoup);

		if (pZoneLink.getDateFermeture().before(pZoneLink.getDateOuverture())) {

			// ERREUR 119 - INVALID DATE - ZONE LINK END DATE STRICTLY BEFORE ZONE LINK
			// START DATE
			throw new JrafDomainException("119");
		}

		Date nowMidnight = SicDateUtils.midnight(new Date());
		if (pZoneLink.getDateFermeture().before(nowMidnight)) {

			// ERREUR 119 - INVALID DATE - intervalle < a date du jour
			throw new JrafDomainException("119");
		}

		// la date de fermeture du lien ne peut �tre post�rieure � la date de fermeture
		// de la zone (si renseign�e)
		if (pZoneDecoup.getDateFermeture() != null
				&& pZoneLink.getDateFermeture().after(pZoneDecoup.getDateFermeture())) {

			throw new JrafDomainException("CLOSURE DATE ZONE LINK AFTER ZONE CLOSURE DATE");
		}
	}

	/**
	 * @param pZoneLink
	 * @throws JrafDomainException
	 */
	private void checkPrivilegedLinkRa2(PmZone pZoneLink, PersonneMorale pPersonneMorale) throws JrafDomainException {

		Assert.notNull(pZoneLink);
		Assert.notNull(pPersonneMorale);

		// si la personne morale n'est pas une agence, alors le lien privilegie doit
		// �tre valoris� "O" ou "N"
		if (!Agence.class.equals(pPersonneMorale.getClass())) {

			if (StringUtils.isEmpty(pZoneLink.getLienPrivilegie())) {

				throw new JrafDomainException("PRIVILEGED ZONE LINK MANDATORY");
			}

			if (PrivilegedLinkEnum.fromLiteral(pZoneLink.getLienPrivilegie()) == null) {

				throw new JrafDomainException("INVALID PRIVILEGED ZONE LINK");
			}
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.airfrance.sic.service.internal.unitservice.firm.IPmZoneUS#
	 * createOrUpdateOrDeleteRa2ZcLinks(List, PersonneMorale)
	 */
	@Transactional(rollbackFor = JrafDomainException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public Map<String, List<PmZone>> prepareCreateOrUpdateOrDeleteRa2ZcLinks(List<PmZone> pZcLinks,
			PersonneMorale pPersonneMorale) throws JrafDomainException {

		Assert.notNull(pZcLinks);
		Assert.notNull(pPersonneMorale);
		Assert.notNull(pPersonneMorale.getGin());
		for (PmZone zcLink : pZcLinks) {
			Assert.notNull(zcLink);
			Assert.notNull(zcLink.getZoneDecoup());
			Assert.isInstanceOf(ZoneComm.class, zcLink.getZoneDecoup());
		}

		if (pPersonneMorale.getPmZones() == null) {
			pPersonneMorale.setPmZones(new HashSet<PmZone>());
		}

		List<PmZone> zcLinksToCreate = new ArrayList<>();
		Map<Long, PmZone> zcLinksToUpdate = new HashMap<>();
		Map<Long, PmZone> zcLinksToDelete = new HashMap<>();
		int countPrivilegedLinkToCreate = 0;

		// aujourd'hui
		Date now = new Date();

		// aujourd'hui minuit
		Date nowMidnight = SicDateUtils.midnight(now);

		for (PmZone zcLink : pZcLinks) {

			if (zcLink.getCle() == null) {

				// Lien � cr�er
				// --------------

				// ZC1 � ZC5 sont requis car on ne peut cr�er de liens qu'avec des ZC de niveau
				// 5
				if (!zoneCommUS.checkZc1ToZc5((ZoneComm) zcLink.getZoneDecoup())) {

					// ERREUR 257 - INCOMPLETE COMMERCIAL ZONE - ZC1�5 Obligatoires
					throw new JrafDomainException("257");
				}

				// recherche de la ZC
				try {
					String zc1 = ((ZoneComm) zcLink.getZoneDecoup()).getZc1();
					String zc2 = ((ZoneComm) zcLink.getZoneDecoup()).getZc2();
					String zc3 = ((ZoneComm) zcLink.getZoneDecoup()).getZc3();
					String zc4 = ((ZoneComm) zcLink.getZoneDecoup()).getZc4();
					String zc5 = ((ZoneComm) zcLink.getZoneDecoup()).getZc5();
					ZoneComm zc = zoneCommRepository.findActiveByZc1Zc2Zc3Zc4Zc5(zc1, zc2, zc3, zc4, zc5);
					zcLink.setZoneDecoup(zc);
				} catch (NoResultException | NonUniqueResultException e) {
					// ERREUR 346 - ZONE NOT FOUND
					throw new JrafDomainException("346");
				}

				// si la date d'ouverture est vide, on l'initialise � aujourd'hui minuit
				// sinon, on l'initialise � minuit de la m�me date
				if (zcLink.getDateOuverture() == null) {
					zcLink.setDateOuverture(nowMidnight);
				} else {
					zcLink.setDateOuverture(SicDateUtils.midnight(zcLink.getDateOuverture()));
				}
				checkStartDateRa2(zcLink, pPersonneMorale, zcLink.getZoneDecoup());

				// si la date de fermeture est renseign�e, on l'initialise � minuit de la m�me
				// date
				if (zcLink.getDateFermeture() != null) {

					zcLink.setDateFermeture(SicDateUtils.midnight(zcLink.getDateFermeture()));
					checkEndDateRa2(zcLink, pPersonneMorale, zcLink.getZoneDecoup());
				}

				checkPrivilegedLinkRa2(zcLink, pPersonneMorale);

				// si le nouveau lien � cr�er est un lien privil�gi�
				if (PrivilegedLinkEnum.YES.equals(PrivilegedLinkEnum.fromLiteral(zcLink.getLienPrivilegie()))) {

					countPrivilegedLinkToCreate++;

					// on ne peut cr�er qu'un seul lien privil�gi� � la fois
					if (countPrivilegedLinkToCreate > 1) {
						throw new JrafDomainException("CANNOT CREATE MORE THAN ONE PRIVILEGED ZC LINK");
					}

					// la date de fermeture du lien privil�gi� � cr�er ne doit pas etre renseign�e
					if (zcLink.getDateFermeture() != null) {
						throw new JrafDomainException("CANNOT CREATE PRIVILEGED ZC LINK WITH END DATE");
					}

					// on ferme le(s) lien(s) privil�gi�(s) dont l'intervalle comprend la date
					// d'ouverture du lien privil�gi� � cr�er
					// on change la date de fermeture au jour précédent la date d'ouverture de la
					// nouvelle ZC
					// au(x) lien(s) privilégié(s) ayant une date d'ouverture antérieure à la date
					// d'ouverture de la nouvelle
					// et dont la date de fermeture n'est pas encore atteinte
					// on supprime le(s) lien(s) privil�gi�(s) qui sont futur au lien privil�gi� �
					// cr�er
					for (PmZone existingPrivilegedZcLink : pPersonneMorale.fetchPrivilegedCommercialZoneLinks()) {

						if (existingPrivilegedZcLink.getDateOuverture().compareTo(zcLink.getDateOuverture()) < 0
								&& (existingPrivilegedZcLink.getDateFermeture() == null
										|| existingPrivilegedZcLink.getDateFermeture().compareTo(now) >= 0)) {

							Calendar cal = Calendar.getInstance();
							cal.setTime(zcLink.getDateOuverture());
							cal.add(Calendar.DAY_OF_MONTH, -1);
							existingPrivilegedZcLink.setDateFermeture(cal.getTime());
							existingPrivilegedZcLink.setSignature(zcLink.getSignature());
							existingPrivilegedZcLink.setDateModif(now);
							zcLinksToUpdate.put(existingPrivilegedZcLink.getCle(), existingPrivilegedZcLink);

						} else if (existingPrivilegedZcLink.getDateOuverture()
								.compareTo(zcLink.getDateOuverture()) >= 0) {

							zcLinksToDelete.put(existingPrivilegedZcLink.getCle(), existingPrivilegedZcLink);
						}

						// si la ZC à ajouter est une future, toute future existant déjà doit être
						// supprimée
						if (zcLink.getDateOuverture().compareTo(nowMidnight) > 0
								&& existingPrivilegedZcLink.getDateOuverture().compareTo(nowMidnight) > 0) {
							zcLinksToDelete.put(existingPrivilegedZcLink.getCle(), existingPrivilegedZcLink);
						}

					}

					// on supprime physiquement les plus anciens liens ZC privil�gi�s et historis�s
					// (inactifs)
					// pour n'en conserver qu'un max
					List<PmZone> pastPrivilegedZoneLinks = new ArrayList<>(
							pPersonneMorale.fetchPastPrivilegedCommercialZoneLinks());
					if (!pastPrivilegedZoneLinks.isEmpty()) {

						// on trie la liste par date de fermeture la plus r�cente � la plus ancienne
						Collections.sort(pastPrivilegedZoneLinks, new Comparator<PmZone>() {
							public int compare(PmZone link1, PmZone link2) {
								return -link1.getDateFermeture().compareTo(link2.getDateFermeture());
							}
						});

						// date de fermeture la plus r�cente
						Date dateFermetureLapLusRecente = pastPrivilegedZoneLinks.get(0).getDateFermeture();

						// on retire les liens qui ont pour date de fermeture =
						// dateFermetureLapLusRecente
						for (Iterator<PmZone> it = pastPrivilegedZoneLinks.iterator(); it.hasNext();) {

							if (dateFermetureLapLusRecente.equals(it.next().getDateFermeture())) {
								it.remove(); // on retire le lien de la liste
							} else {
								break; // inutile d'aller plus loin �tant donn� que la liste est tri�e par date de
								// fermeture desc
							}
						}

						// les liens restants sont � d�truire
						for (PmZone inactivePrivilegedZoneLink : pastPrivilegedZoneLinks) {

							zcLinksToDelete.put(inactivePrivilegedZoneLink.getCle(), inactivePrivilegedZoneLink);
						}
					}
				}

				zcLink.setDateModif(now);
				zcLink.setOrigine(null);
				zcLink.setPersonneMorale(pPersonneMorale);

				zcLinksToCreate.add(zcLink);

			} else {

				// Lien � modifier ou supprimer
				// ------------------------------

				PmZone linkTrouve = pPersonneMorale.fetchZoneLinkById(zcLink.getCle());
				if (linkTrouve != null && ZoneComm.class.equals(linkTrouve.getZoneDecoup().getClass())) {

					// ATTENTION : si on veut mettre � null la dateFermeture d'un lien secondaire,
					// il faut renseigner dateOuverture et/ou lienPrivilegie car sinon, on pensera �
					// une suppression
					if (!isEmptyData(zcLink)) {

						// on ne peut modifier que la date de fermeture => c'est donc la seule donn�e �
						// contr�ler
						if (zcLink.getDateFermeture() != null) {

							zcLink.setDateFermeture(SicDateUtils.midnight(zcLink.getDateFermeture()));
							checkEndDateRa2(zcLink, pPersonneMorale, zcLink.getZoneDecoup());
						}

						linkTrouve.setDateFermeture(zcLink.getDateFermeture());
						linkTrouve.setOrigine(null); // on force � null (demande de LBN)
						linkTrouve.setDateModif(now);
						linkTrouve.setSignature(zcLink.getSignature());

						zcLinksToUpdate.put(zcLink.getCle(), linkTrouve);

					} else {

						// Suppression du lien
						zcLinksToDelete.put(zcLink.getCle(), linkTrouve);
					}
				} else {

					// Erreur 338 - ZONE LINK NOT FOUND
					throw new JrafDomainException("338");
				}
			}
		}

		// on fait un 1er bilan des liens ZC
		// -----------------------------------

		// si la PM est un �tablissement et qu'on n'a aucun lien ZC,
		// alors si statut A ou P :
		// on cr�e un lien privil�gi� actif avec une ZC d'attente
		// si statut X :
		// on cr�e un lien privil�gi� actif avec une ZC d'annulation

		if (Etablissement.class.equals(pPersonneMorale.getClass())) {

			List<PmZone> commercialZoneLinks = new ArrayList<>(pPersonneMorale.fetchCommercialZoneLinks());
			commercialZoneLinks.removeAll(zcLinksToDelete.values());
			commercialZoneLinks.addAll(zcLinksToCreate);

			if (commercialZoneLinks.isEmpty()) {

				ZoneComm commercialZone = null;

				if (LegalPersonStatusEnum.ACTIVE.equals(LegalPersonStatusEnum.fromLiteral(pPersonneMorale.getStatut()))
						|| LegalPersonStatusEnum.TEMPORARY
								.equals(LegalPersonStatusEnum.fromLiteral(pPersonneMorale.getStatut()))) {

					commercialZone = zoneCommUS.findZone(pPersonneMorale.fetchValidLocalisationPostalAddress(),
							NatureZoneEnum.STANDBY);

					if (commercialZone == null) {

						commercialZone = zoneCommUS.findTrashZone();
					}

				} else if (LegalPersonStatusEnum.CLOSED
						.equals(LegalPersonStatusEnum.fromLiteral(pPersonneMorale.getStatut()))) {

					commercialZone = zoneCommUS.findZone(pPersonneMorale.fetchValidLocalisationPostalAddress(),
							NatureZoneEnum.CANCELLATION);
				}

				if (commercialZone != null) {

					PmZone zcLink = new PmZone();

					zcLink.setCle(null);
					zcLink.setDateFermeture(null);
					zcLink.setDateModif(now);
					zcLink.setDateOuverture(nowMidnight);
					zcLink.setLienPrivilegie(PrivilegedLinkEnum.YES.toLiteral());
					zcLink.setOrigine(null);
					zcLink.setPersonneMorale(pPersonneMorale);
					zcLink.setSignature(pPersonneMorale.getSignatureModification());
					zcLink.setZoneDecoup(commercialZone);

					zcLinksToCreate.add(zcLink);
				}
			}
		}

		// on fait un 2e bilan des liens ZC
		// ----------------------------------

		List<PmZone> commercialZoneLinks = new ArrayList<>(pPersonneMorale.fetchCommercialZoneLinks());
		commercialZoneLinks.removeAll(zcLinksToDelete.values());
		commercialZoneLinks.addAll(zcLinksToCreate);

		int countActivePrivilegedLink = 0;
		int countPrivilegedLinkWithoutEndDate = 0;
		for (PmZone zcLink : commercialZoneLinks) {

			ZoneComm zc = (ZoneComm) zcLink.getZoneDecoup();

			// si les ZC li�es ne sont pas toutes de niveau 5, on sort
			if (!zoneCommUS.checkZc1ToZc5(zc)) {

				// ERREUR 257 - INCOMPLETE COMMERCIAL ZONE - ZC1�5 Obligatoires
				throw new JrafDomainException("257");
			}

			// si le lien est privil�gi�
			if (PrivilegedLinkEnum.YES.equals(PrivilegedLinkEnum.fromLiteral(zcLink.getLienPrivilegie()))) {

				// Check selon le statut de la PM
				checkPmStatusRa2(zcLink, pPersonneMorale);

				if (zcLink.getDateOuverture().compareTo(nowMidnight) <= 0 && (zcLink.getDateFermeture() == null
						|| zcLink.getDateFermeture().compareTo(nowMidnight) >= 0)) {

					countActivePrivilegedLink++;
				}

				if (zcLink.getDateFermeture() == null) {

					countPrivilegedLinkWithoutEndDate++;
				}

				// il doit �tre de sous-type "FV" ou "TO"
				if (!SubtypeZoneEnum.SALES_FORCES.equals(SubtypeZoneEnum.fromLiteral(zc.getSousType()))
						&& !SubtypeZoneEnum.TOUR_OPERATOR.equals(SubtypeZoneEnum.fromLiteral(zc.getSousType()))) {

					// ERREUR 328 - INVALID SUBTYPE ZONE - FV (ou TO) pour lien privil�gi�
					throw new JrafDomainException("328");
				}
			}
		}

		// Au moins un lien privilegie actif
		if (countActivePrivilegedLink == 0) {

			// ERREUR 171 - COMMERCIAL ZONE MANDATORY - cr�ez un lien privil�gi� valide (ZC)
			throw new JrafDomainException("171");

		} else if (countActivePrivilegedLink > 1) {

			log.warn(String.format("Legal person %s has %d active priviledged ZC links", pPersonneMorale.getGin(),
					countActivePrivilegedLink));
		}

		// Au moins un lien privil�gi� sans date de fin
		if (countPrivilegedLinkWithoutEndDate == 0) {

			// ERREUR 171 - COMMERCIAL ZONE MANDATORY - cr�ez le lien privil�gi� suivant
			// (ZC)
			throw new JrafDomainException("171");

		} else if (countPrivilegedLinkWithoutEndDate > 1) {

			log.warn(String.format("Legal person %s has %d priviledged ZC links whithout end date",
					pPersonneMorale.getGin(), countPrivilegedLinkWithoutEndDate));
		}

		// Si pour une m�me ZC, il existe :
		// - un lien privil�gi�
		// - un autre lien (privil�gi� ou non)
		// - les 2 liens se recouvrent
		// Alors, il y doublon
		commercialZoneLinks = new ArrayList<>(pPersonneMorale.fetchCommercialZoneLinks());
		commercialZoneLinks.removeAll(zcLinksToDelete.values());
		commercialZoneLinks.addAll(zcLinksToCreate);
		for (PmZone pmZone1 : commercialZoneLinks) {

			for (PmZone pmZone2 : commercialZoneLinks) {

				if (pmZone2.equals(pmZone1)) {
					continue;
				}

				if ((pmZone1.getCle() == null && pmZone2.getCle() == null // les 2 cl�s sont null
						|| pmZone1.getCle() != null && !pmZone1.getCle().equals(pmZone2.getCle())
						|| pmZone2.getCle() != null && !pmZone2.getCle().equals(pmZone1.getCle())) // ou les 2 cl�s sont
						// valoris�es mais
						// differentes

						&& pmZone1.getZoneDecoup() != null && pmZone2.getZoneDecoup() != null
						&& pmZone1.getZoneDecoup().getGin().equals(pmZone2.getZoneDecoup().getGin()) // m�me ZC

						&& (PrivilegedLinkEnum.YES.equals(PrivilegedLinkEnum.fromLiteral(pmZone1.getLienPrivilegie()))
								|| PrivilegedLinkEnum.YES
										.equals(PrivilegedLinkEnum.fromLiteral(pmZone2.getLienPrivilegie()))) // au
						// moins
						// un
						// des 2
						// liens
						// est
						// privil�gi�

						&& pmZone1.getDateOuverture().compareTo(pmZone2.getDateOuverture()) <= 0
						&& (pmZone1.getDateFermeture() == null
								|| pmZone1.getDateFermeture().compareTo(pmZone2.getDateOuverture()) >= 0)) // les 2
				// liens se
				// recouvrent
				{

					// ERREUR 244 - LINK ALREADY EXISTS - LINK ALREADY EXISTS
					throw new JrafDomainException("244");
				}
			}
		}

		// Si origine est renseign� parmi les liens existants qu'on n'update pas, on met
		// � null (demande de LBN)
		for (PmZone commercialZoneLink : pPersonneMorale.fetchCommercialZoneLinks()) {

			if (!zcLinksToUpdate.containsKey(commercialZoneLink.getCle()) && commercialZoneLink.getOrigine() != null) {

				commercialZoneLink.setOrigine(null);
				commercialZoneLink.setDateModif(now);
				commercialZoneLink.setSignature(pPersonneMorale.getSignatureModification());
				zcLinksToUpdate.put(commercialZoneLink.getCle(), commercialZoneLink);
			}
		}

		Map<String, List<PmZone>> pmZoneMap = new HashedMap();

		pmZoneMap.put("zcLinksToCreate", zcLinksToCreate);
		pmZoneMap.put("zcLinksToUpdate", new ArrayList<PmZone>(zcLinksToUpdate.values()));
		pmZoneMap.put("zcLinksToDelete", new ArrayList<PmZone>(zcLinksToDelete.values()));
		return pmZoneMap;

	}

	/**
	 * Check status of the firm
	 *
	 * @param pZoneLink
	 * @throws JrafDomainException
	 */
	private void checkPmStatusRa2(PmZone zcLink, PersonneMorale pPersonneMorale) throws JrafDomainException {

		Assert.notNull(zcLink);
		Assert.notNull(pPersonneMorale);
		Assert.notNull(pPersonneMorale.getGin());
		Assert.isInstanceOf(ZoneComm.class, zcLink.getZoneDecoup());

		ZoneComm zc = (ZoneComm) zcLink.getZoneDecoup();
		NatureZoneEnum nature = NatureZoneEnum.fromLiteral(zc.getNature());
		LegalPersonStatusEnum status = LegalPersonStatusEnum.fromLiteral(pPersonneMorale.getStatut());
		Date now = new Date();
		Date nowMidnight = SicDateUtils.midnight(now);

		// si le statut de la PM est 'P' et qu'on essaye de lui assigner une ZC ANN
		// valide -> error 177
		if (LegalPersonStatusEnum.TEMPORARY.equals(status)) {

			if (NatureZoneEnum.CANCELLATION.equals(nature)
					&& (zcLink.getDateFermeture() == null || zcLink.getDateFermeture().after(nowMidnight))) {

				// ERREUR 177 - INVALID STATUS
				throw new JrafDomainException("177");
			}

			// si le statut de la PM est 'A' et qu'on essaye de lui assigner une ZC ANN
			// valide -> error 177
		} else if (LegalPersonStatusEnum.ACTIVE.equals(status)) {

			if (NatureZoneEnum.CANCELLATION.equals(nature)
					&& (zcLink.getDateFermeture() == null || zcLink.getDateFermeture().after(nowMidnight))) {

				// ERREUR 177 - INVALID STATUS
				throw new JrafDomainException("177");
			}

			// si le statut de la PM est 'X' et qu'on essaye de lui assigner une ZC SEC ou
			// ATT valide -> error 177
		} else if (LegalPersonStatusEnum.CLOSED.equals(status)) {

			if ((NatureZoneEnum.STANDBY.equals(nature) || NatureZoneEnum.SECTORISED.equals(nature))
					&& (zcLink.getDateFermeture() == null || zcLink.getDateFermeture().after(nowMidnight))) {

				// ERREUR 177 - INVALID STATUS
				throw new JrafDomainException("177");
			}
		}
	}

	@Transactional(rollbackFor = JrafDomainException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void createOrUpdateOrDeleteRa2ZvLinks(Map<String, List<PmZone>> pmZoneMap, PersonneMorale pPersonneMorale) {

		// Enregistrement en base
		if (CollectionUtils.isNotEmpty(pmZoneMap.get("zvLinksToCreate"))) {
			for (PmZone link : pmZoneMap.get("zvLinksToCreate")) {
				pmZoneRepository.saveAndFlush(link);
			}
		}

		if (CollectionUtils.isNotEmpty(pmZoneMap.get("zvLinksToUpdate"))) {
			for (PmZone link : pmZoneMap.get("zvLinksToUpdate")) {
				pmZoneRepository.saveAndFlush(link);
			}
		}

		if (CollectionUtils.isNotEmpty(pmZoneMap.get("zvLinksToDelete"))) {
			for (PmZone link : pmZoneMap.get("zvLinksToDelete")) {
				pmZoneRepository.delete(link);
			}
		}

		personneMoraleRepository.refresh(pPersonneMorale);

	}

	@Transactional(rollbackFor = JrafDomainException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void createOrUpdateOrDeleteRa2ZcLinks(Map<String, List<PmZone>> pmZoneMap, PersonneMorale pPersonneMorale) {

		// Enregistrement en base

		if (CollectionUtils.isNotEmpty(pmZoneMap.get("zcLinksToCreate"))) {
			for (PmZone link : pmZoneMap.get("zcLinksToCreate")) {
				pmZoneRepository.saveAndFlush(link);
			}
		}

		if (CollectionUtils.isNotEmpty(pmZoneMap.get("zcLinksToUpdate"))) {
			for (PmZone link : pmZoneMap.get("zcLinksToUpdate")) {
				pmZoneRepository.saveAndFlush(link);
			}
		}

		if (CollectionUtils.isNotEmpty(pmZoneMap.get("zcLinksToDelete"))) {
			for (PmZone link : pmZoneMap.get("zcLinksToDelete")) {
				pmZoneRepository.delete(link);
			}
		}

		personneMoraleRepository.refresh(pPersonneMorale);
	}

	/**
	 * Retrieves all agencies linked to the given ZV AND the future links of the
	 * returned agencies.
	 *
	 * @return the links retrieved
	 * @throws JrafDomainException
	 */
	public List<PmZone> findAgencyZvLinks(int zv0, int zv1, int zv2, int zv3, LocalDate date) {
		return pmZoneRepository.findAgencyZvLinks(zv0, zv1, zv2, zv3, date);
	}

	/**
	 * Retrieves all agencies linked to the given ZC AND the future links of the
	 * returned agencies.
	 *
	 * @return the links retrieved
	 * @throws JrafDomainException
	 */
	public List<PmZone> findAgencyZcLinks(String zc1, String zc2, String zc3, String zc4, String zc5, LocalDate date) {
		return pmZoneRepository.findAgencyZcLinks(zc1, zc2, zc3, zc4, zc5, date);
	}

	/**
	 * Retrieves all firms linked to the given ZC AND the future links of the
	 * returned firms.
	 *
	 * @return the links retrieved
	 * @throws JrafDomainException
	 */
	public List<PmZone> findFirmZcLinks(String zc1, String zc2, String zc3, String zc4, String zc5, LocalDate date) {
		return pmZoneRepository.findFirmZcLinks(zc1, zc2, zc3, zc4, zc5, date);
	}

	/**
	 * Create a new link and close the current one.
	 *
	 * @param createLink the link to be created
	 * @return the new link
	 * @throws JrafDomainException
	 */
	@Transactional(rollbackFor = { JrafDomainRollbackException.class,
			JrafDaoException.class }, noRollbackFor = JrafDomainNoRollbackException.class)
	public PmZone createPmZoneLink(@NotNull final PmZone createLink) {
        createLink.setLienPrivilegie(OuiNonFlagEnum.OUI.toString());
		Date closeDate = new DateTime(createLink.getDateOuverture()).minusDays(1).toDate();
		List<PmZone> openLinks = new ArrayList<>();
		Optional<PmZone> currentLink = Optional.empty();
		List<PmZone> futureLinks = new ArrayList<>();
		Date today = new Date();
		
		if (createLink.getZoneDecoup() instanceof ZoneVente) {
			createLink.setZoneDecoup(entityManager.find(ZoneVente.class, createLink.getZoneDecoup().getGin()));
			openLinks = pmZoneRepository.findAllActiveZvLinksByPmGin(createLink.getPersonneMorale().getGin());
		} else if (createLink.getZoneDecoup() instanceof ZoneComm) {
			createLink.setZoneDecoup(entityManager.find(ZoneComm.class, createLink.getZoneDecoup().getGin()));
			openLinks = pmZoneRepository.findAllActiveZcLinksByPmGin(createLink.getPersonneMorale().getGin());
		}

		for (PmZone link : openLinks) {
			if (link.getDateOuverture().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
					.isAfter(LocalDate.now())) {
				futureLinks.add(link);
			} else {
				currentLink = Optional.of(link);
			}
		}
		
		createLink.setPersonneMorale(entityManager.find(PersonneMorale.class, createLink.getPersonneMorale().getGin()));

		// WOPA-788: Update pers_morale so that the pm_zone update is sent via generic replication
		PersonneMorale pm = createLink.getPersonneMorale();
		pm.setSignatureModification(createLink.getSignature());
		pm.setDateModification(today);
		pm.setSiteModification(AgencyValidationHelper.SITE_QVI);

		PmZone saved = pmZoneRepository.saveAndFlush(createLink);
		
		currentLink.ifPresent(link -> {
			link.setDateFermeture(closeDate);
			link.setDateModif(today);
			link.setSignature(createLink.getSignature());
			pmZoneRepository.save(link);
		});

		futureLinks.forEach(link -> {
			pmZoneRepository.delete(link);
		});
		
		personneMoraleRepository.saveAndFlush(pm);
		
		return saved;
	}
}
