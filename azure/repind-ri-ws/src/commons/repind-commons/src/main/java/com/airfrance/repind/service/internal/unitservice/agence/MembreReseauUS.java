package com.airfrance.repind.service.internal.unitservice.agence;

import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.util.UList;
import com.airfrance.repind.dao.agence.MembreReseauRepository;
import com.airfrance.repind.dao.reference.ReseauRepository;
import com.airfrance.repind.entity.agence.Agence;
import com.airfrance.repind.entity.agence.MembreReseau;
import com.airfrance.repind.entity.reference.Reseau;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MembreReseauUS {

	/** references on associated DAOs */
	@Autowired
	private MembreReseauRepository membreReseauRepository;

	/** Reseau dao */
	@Autowired
	private ReseauRepository reseauRepository;

	/** the Entity Manager */
	@PersistenceContext(unitName = "entityManagerFactoryRepind")
	private EntityManager entityManager;

	/**
	 * empty constructor
	 */
	public MembreReseauUS() {
	}

	public void createOrUpdateOrDeleteNetworkLink(List<MembreReseau> listLMembreReseau, Agence agence)
			throws JrafDomainException {
		if (!UList.isNullOrEmpty(listLMembreReseau)) {
			for (MembreReseau membreReseau : listLMembreReseau) {
				chooseActionCUD(membreReseau, agence);

			}
		}
	}

	public int countSubNetworks(MembreReseau membreReseau) throws JrafDaoException {
		return membreReseauRepository.countSubNetworks(membreReseau);
	}

	public List<MembreReseau> getParentLinkNetwork(String pGinValue, String pCodeChild) throws JrafDomainException {
		return membreReseauRepository.getParentLinkNetwork(pGinValue, pCodeChild);
	}

	public List<MembreReseau> getLinkNetworksByType(String gin, String networkType) throws JrafDaoException {
		return membreReseauRepository.getLinkNetworksByTypeAndCode(gin, networkType);
	}

	/**
	 * Choosing to create, update or delete create code network without ain update
	 * ain and code network delete only ain
	 * 
	 * @param membreReseau
	 * @param agenceRA
	 * @return
	 * @throws JrafDaoException
	 * @throws JrafDomainException
	 */
	private void chooseActionCUD(MembreReseau membreReseau, Agence agenceRA) throws JrafDomainException {
		if (membreReseau.getReseau() != null && membreReseau.getReseau().getCode() != null) {
			Reseau reseau = reseauRepository.getOne(membreReseau.getReseau().getCode());
			createOrUpdateLinkNetwork(membreReseau, reseau, agenceRA);
		} else {
			deleteLinkNetwork(membreReseau);
		}

	}

	/**
	 * Delete link if exists
	 * 
	 * @param membreReseau
	 * @throws JrafDomainException
	 */
	private void deleteLinkNetwork(MembreReseau membreReseau) throws JrafDomainException {
		Optional<MembreReseau> membreReseauToDelete = membreReseauRepository.findById(membreReseau.getCle());
		if (membreReseauToDelete.isPresent()) {
			membreReseauRepository.delete(membreReseauToDelete.get());
		}
	}

	/**
	 * Create if ain doesn't exists (current date if date not mentionned) update if
	 * ain and code network exists (only end date)
	 * 
	 * @param membreReseau
	 * @param agenceRA
	 * @param reseau
	 * @throws JrafDomainException
	 * @throws JrafDaoException
	 */
	private void createOrUpdateLinkNetwork(MembreReseau membreReseau, Reseau reseau, Agence agenceRA)
			throws JrafDomainException {
		membreReseau.setReseau(reseau);
		membreReseau.setAgence(agenceRA);

		if (membreReseau.getCle() == null) {
			createMembreReseau(membreReseau, reseau, agenceRA);
		} else {
			updateMembreReseau(membreReseau);
		}
	}

	private void createMembreReseau(MembreReseau membreReseau, Reseau reseau, Agence agenceRA)
			throws JrafDomainException {
		List<MembreReseau> listExistingLinks = membreReseauRepository.getLinkNetworksByTypeAndCode(agenceRA.getGin(),
				reseau.getType());

		if (!CollectionUtils.isEmpty(listExistingLinks)) {

			for (MembreReseau existingMembreReseau : listExistingLinks) {
				Date existingEndDate = existingMembreReseau.getDateFin();
				Date existingStartDate = existingMembreReseau.getDateDebut();
				Date startDate = membreReseau.getDateDebut();
				if (existingStartDate.before(startDate) && (existingEndDate == null || existingEndDate.after(startDate)
						|| compareSameDate(startDate, existingEndDate))) {
					Calendar cal = Calendar.getInstance();
					cal.setTime(startDate);
					cal.add(Calendar.DATE, -1);
					Date dateFin = cal.getTime();
					existingMembreReseau.setDateFin(dateFin);
					updateMembreReseau(existingMembreReseau);
				}
			}
		}

		if (membreReseau.getDateDebut() == null) {
			membreReseau.setDateDebut(new Date());
		}
		membreReseauRepository.saveAndFlush(membreReseau);
	}

	private boolean compareSameDate(Date startDateSubNetwork, Date startLinkNetwork) {
		Calendar calLinkNetwork = Calendar.getInstance();
		Calendar calLinkSubNetwork = Calendar.getInstance();
		calLinkNetwork.setTime(startLinkNetwork);
		calLinkSubNetwork.setTime(startDateSubNetwork);

		return calLinkNetwork.get(Calendar.YEAR) == calLinkSubNetwork.get(Calendar.YEAR)
				&& calLinkNetwork.get(Calendar.DAY_OF_YEAR) == calLinkSubNetwork.get(Calendar.DAY_OF_YEAR);
	}

	private void updateMembreReseau(MembreReseau membreReseau) throws JrafDomainException {
		Optional<MembreReseau> tmp = membreReseauRepository.findById(membreReseau.getCle());

		if (tmp.isPresent()) {
			MembreReseau membreReseauToUpdate = tmp.get();
			membreReseauToUpdate.setDateFin(membreReseau.getDateFin());
			membreReseauToUpdate.setDateDebut(membreReseau.getDateDebut());
			membreReseauToUpdate.setReseau(membreReseau.getReseau());
			membreReseauRepository.save(membreReseauToUpdate);
		}
	}

	public MembreReseauRepository getMembreReseauRepository() {
		return membreReseauRepository;
	}

	public void setMembreReseauRepository(MembreReseauRepository membreReseauRepository) {
		this.membreReseauRepository = membreReseauRepository;
	}

}
