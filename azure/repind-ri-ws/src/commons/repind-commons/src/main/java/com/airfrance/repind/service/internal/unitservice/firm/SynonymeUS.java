package com.airfrance.repind.service.internal.unitservice.firm;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.exception.jraf.JrafDomainNoRollbackException;
import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;
import com.airfrance.ref.type.MediumStatusEnum;
import com.airfrance.repind.dao.firme.PersonneMoraleRepository;
import com.airfrance.repind.dao.firme.SynonymeRepository;
import com.airfrance.repind.dto.firme.PersonneMoraleDTO;
import com.airfrance.repind.dto.firme.SynonymeDTO;
import com.airfrance.repind.dto.firme.SynonymeTransform;
import com.airfrance.repind.entity.agence.Agence;
import com.airfrance.repind.entity.agence.SearchByIdEnum;
import com.airfrance.repind.entity.firme.PersonneMorale;
import com.airfrance.repind.entity.firme.Synonyme;
import com.airfrance.repind.entity.firme.enums.SynonymTypeEnum;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Service
public class SynonymeUS {

    /** logger */
    private static final Log log = LogFactory.getLog(SynonymeUS.class);

    /*PROTECTED REGION ID(_eNTJsKLhEeSXNpATSKyi0Q u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    @PersistenceContext(unitName = "entityManagerFactoryRepind")
	private EntityManager entityManager;
    
    /** references on associated DAOs */
    @Autowired
    private SynonymeRepository synonymeRepository;
    
    @Autowired
    private PersonneMoraleRepository personneMoraleRepository;

    /**
     * empty constructor
     */
    public SynonymeUS() {
    }
    
    public SynonymeRepository getSynonymeRepository() {
		return synonymeRepository;
	}

	public void setSynonymeRepository(SynonymeRepository synonymeRepository) {
		this.synonymeRepository = synonymeRepository;
	}

	/**
     * @param pType type de synonyme
     * @param pPersonneMorale
     * @return synonyme du type demandé, null sinon
     */
    private Synonyme findByType(SynonymTypeEnum pType, PersonneMorale pPersonneMorale) throws JrafDomainException {
        
        Assert.notNull(pType);
        Assert.notNull(pPersonneMorale);

        Synonyme result = null;

        if (!CollectionUtils.isEmpty(pPersonneMorale.getSynonymes())) {
            
            result = findByType(pType, new ArrayList<Synonyme>(pPersonneMorale.getSynonymes()));
        }

        return result;
    }
    
    /**
     * @param pType type de synonyme
     * @param pSynonymes liste de synonymes
     * @return synonyme du type demandé, null sinon
     */
    private Synonyme findByType(SynonymTypeEnum pType, List<Synonyme> pSynonymes) throws JrafDomainException {
        
        Assert.notNull(pType);
        Assert.notNull(pSynonymes);       
        
        Synonyme result = null;
        
        if (!CollectionUtils.isEmpty(pSynonymes)) {
            
            for (Synonyme syn : pSynonymes) {

                SynonymTypeEnum type = SynonymTypeEnum.fromLiteral(syn.getType());
                if (pType.equals(type)) {                    
                    result = syn;
                }
            }
        }
        
        return result;
    }
    
    /**
     * @param pId type de synonyme
     * @param pPersonneMorale
     * @return synonyme du type demandé, null sinon
     */
    private Synonyme findById(Long pId, PersonneMorale pPersonneMorale) throws JrafDomainException {
        
        Assert.notNull(pId);
        Assert.notNull(pPersonneMorale);        
        
        Synonyme result = null;
        
        if (!CollectionUtils.isEmpty(pPersonneMorale.getSynonymes())) {
            
            for (Synonyme syn : pPersonneMorale.getSynonymes()) {
                
                if (pId.equals(syn.getCle())) {                    
                    result = syn;
                }
            }
        }
        
        return result;
    }
    
    /**
     * @param pSynonyme
     * @throws JrafDomainException
     */
    private void check(Synonyme pSynonyme, PersonneMorale pPersonneMorale) throws JrafDomainException {
        
        Assert.notNull(pSynonyme);
        
        if (StringUtils.isEmpty(pSynonyme.getType())) {
            
            // Erreur 218 - SYNONYM TYPE MANDATORY
            throw new JrafDomainRollbackException("218");
        }
        
        //TODO MBE : dans Synonyme, convertir le type en enum
        if (SynonymTypeEnum.fromLiteral(pSynonyme.getType()) == null){
            
            // Erreur 219 - INVALID SYNONYM TYPE
            throw new JrafDomainRollbackException("219");
        }
        
        if (StringUtils.isEmpty(pSynonyme.getNom())) {
            
            // TODO LBN créer erreur "SYNONYM NAME MANDATORY"
            throw new JrafDomainRollbackException("SYNONYM NAME MANDATORY");
        }

		// SI LE SYNONYME EST UN SYNONYME CREEE PAR DEFAUT,
		// ON SAUTE LES VERIFICATIONS HABITUELLES
		// ADH: S09611
		if (pSynonyme.getStatut() != null && pSynonyme.getStatut().equals("DEFAULT")) {
			pSynonyme.setStatut("V");
			return;
		}

		// SPECIFIQUE: AGENCE
        if (Agence.class.equals(pPersonneMorale.getClass())
                && (!SynonymTypeEnum.USUAL_NAME.equals(SynonymTypeEnum.fromLiteral(pSynonyme.getType()))
                		&& !SynonymTypeEnum.TRADE_NAME.equals(SynonymTypeEnum.fromLiteral(pSynonyme.getType())))) {
            
            // TODO LBN créer erreur "SYNONYM TYPE NOT ALLOWED"
            throw new JrafDomainRollbackException("SYNONYM TYPE NOT ALLOWED");
        }
    }
    
    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void createOrUpdateOrDelete(List<Synonyme> pSynonymes, String pPersMoraleGIN) throws JrafDomainException {
		if (pSynonymes != null && pSynonymes.size() > 0) {

			if (pPersMoraleGIN.isEmpty()) {
				throw new JrafDomainException("GIN IS MANDATORY");
			}

			PersonneMorale pPersMorale = null;

			pPersMorale = personneMoraleRepository.findPMByOptions(SearchByIdEnum.GIN.toString(), pPersMoraleGIN);
			if (pPersMorale == null) {
				throw new JrafDomainException("UNABLE TO FIND (PM) : " + pPersMoraleGIN);
			}

			this.createOrUpdateOrDelete(pSynonymes, pPersMorale);
		}
	}
    
    /*
     * (non-Javadoc)
     * @see com.airfrance.repind.service.internal.unitservice.firm.ISynonymeUS#createOrUpdateOrDelete(List, PersonneMorale)
     */
    public void createOrUpdateOrDelete(List<Synonyme> pSynonymes, PersonneMorale pPersonneMorale) throws JrafDomainException {
        
    	Assert.notNull(pSynonymes);
		Assert.notNull(pPersonneMorale);
		Assert.notNull(pPersonneMorale.getGin());
		for (Synonyme synonyme : pSynonymes) {
			Assert.notNull(synonyme);
		}

		if (pPersonneMorale.getSynonymes() == null) {
			pPersonneMorale.setSynonymes(new HashSet<Synonyme>());
		}

		List<Synonyme> synonymesToCreate = new ArrayList<Synonyme>();
		Map<Long, Synonyme> synonymesToUpdate = new HashMap<Long, Synonyme>();
		Map<Long, Synonyme> synonymesToDelete = new HashMap<Long, Synonyme>();

		for (Synonyme synonyme : pSynonymes) {

			if (synonyme.getCle() == null) {

				check(synonyme, pPersonneMorale);

				Synonyme synonymeTrouve = findByType(SynonymTypeEnum.fromLiteral(synonyme.getType()), pPersonneMorale);
				if (synonymeTrouve == null) {

					synonyme.setPersonneMorale(pPersonneMorale);
					synonyme.setStatut("V"); // TODO LBN : colonne inexploitée
												// ds les RG mais je renseigne
												// car non nullable en base =>
												// colonne à supprimer
					synonymesToCreate.add(synonyme);
				} else {

					// TODO LBN créer erreur "SYNONYM ALREADY EXISTS"
					throw new JrafDomainRollbackException("SYNONYM ALREADY EXISTS");
				}
			} else {

				Synonyme synonymeTrouve = findById(synonyme.getCle(), pPersonneMorale);
				if (synonymeTrouve != null) {

					if (!StringUtils.isEmpty(synonyme.getType())) {

						check(synonyme, pPersonneMorale);

						if (synonyme.getType().equals(synonymeTrouve.getType())) {

							if (!synonymeTrouve.getNom().equals(synonyme.getNom())) {

								Date now = new Date();
								synonymeTrouve.setDateModificationSnom(now);
								synonymeTrouve.setNom(synonyme.getNom());
								synonymesToUpdate.put(synonyme.getCle(), synonymeTrouve);
							}
						} else {

							// TODO LBN créer erreur "SYNONYM TYPE UPDATE NOT
							// ALLOWED"
							throw new JrafDomainRollbackException("SYNONYM TYPE UPDATE NOT ALLOWED");
						}
					} else {

						// spécifique Agence
						if (Agence.class.equals(pPersonneMorale.getClass()) 
								&& (!SynonymTypeEnum.USUAL_NAME.equals(SynonymTypeEnum.fromLiteral(synonyme.getType()))
										&& !SynonymTypeEnum.TRADE_NAME.equals(SynonymTypeEnum.fromLiteral(synonyme.getType())))) {

							// TODO LBN créer erreur "SYNONYM TYPE NOT ALLOWED"
							throw new JrafDomainRollbackException("SYNONYM TYPE NOT ALLOWED");
						}

						synonymesToDelete.put(synonyme.getCle(), synonymeTrouve);
					}
				} else {

					// TODO LBN créer erreur "SYNONYM NOT FOUND"
					throw new JrafDomainRollbackException("SYNONYM NOT FOUND");
				}
			}
		}

		List<Synonyme> synonymes = new ArrayList<Synonyme>(pPersonneMorale.getSynonymes());
		synonymes.removeAll(synonymesToDelete.values());
		synonymes.addAll(synonymesToCreate);

		// RG - si pas de marque commerciale, on en crée une avec la raison
		// sociale
		if (!Agence.class.equals(pPersonneMorale.getClass())) {

			Synonyme marqueCommerciale = findByType(SynonymTypeEnum.TRADE_NAME, synonymes);
			if (marqueCommerciale == null) {

				if (StringUtils.isEmpty(pPersonneMorale.getNom())) {

					// TODO LBN créer nouvelle erreur : XXX - BUSINESS NAME
					// MANDATORY
					throw new JrafDomainRollbackException("BUSINESS NAME MANDATORY");
				}

				marqueCommerciale = new Synonyme(null, "V", pPersonneMorale.getNom(),
						SynonymTypeEnum.TRADE_NAME.toLiteral(), null);
				marqueCommerciale.setPersonneMorale(pPersonneMorale);
				synonymesToCreate.add(marqueCommerciale);
			}

			// RG - si pas de nom usuel, on en crée un avec la marque
			// commerciale
			Synonyme nomUsuel = findByType(SynonymTypeEnum.USUAL_NAME, synonymes);
			if (nomUsuel == null) {

				nomUsuel = new Synonyme(null, "V", marqueCommerciale.getNom(), SynonymTypeEnum.USUAL_NAME.toLiteral(),
						null);
				nomUsuel.setPersonneMorale(pPersonneMorale);
				synonymesToCreate.add(nomUsuel);
			}
		}

        // Enregistrement en base
        for (Synonyme synonyme : synonymesToCreate) {
        	synonymeRepository.saveAndFlush(synonyme);
        }
        for (Synonyme synonyme : synonymesToUpdate.values()) {
        	synonymeRepository.saveAndFlush(synonyme);
        }
        for (Synonyme synonyme : synonymesToDelete.values()) {
        	synonymeRepository.delete(synonyme);
        }
    }

	/**
	 * Creates new Synonyme
	 * 
	 * @param synonymeDto the synonyme to be created
	 * @return the new created {@code SynonymeDTO}
	 * @throws JrafDomainException
	 */
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public SynonymeDTO create(SynonymeDTO synonymeDto) throws JrafDomainException {
		Synonyme synonyme = SynonymeTransform.dto2BoLight(synonymeDto);
		SynonymeTransform.dto2BoLink(entityManager, synonymeDto, synonyme);
		return SynonymeTransform.bo2DtoLight(synonymeRepository.saveAndFlush(synonyme));
	}

	/**
	 * Create or Update Synonyme
	 *
	 * @param synonymeDto the synonyme to be created
	 * @return the created or updated{@code SynonymeDTO}
	 * @throws JrafDomainException
	 */
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public SynonymeDTO createOrUpdate(SynonymeDTO synonymeDto) throws JrafDomainException {
		SynonymeDTO response = null;
		String pType = synonymeDto.getType();
		PersonneMoraleDTO pmDto = synonymeDto.getPersonneMorale();
		String name = synonymeDto.getNom();

		if (pType == null || pmDto == null) {
			return response;
		}
		// Check existing synonyme in DB
		Set<SynonymeDTO> dtoListFromDB = findValidByTypeAndGin(pType, pmDto.getGin());

		if (dtoListFromDB != null && !dtoListFromDB.isEmpty()) {
			for (SynonymeDTO dtoFromDB : dtoListFromDB) {
				if (name != null && !name.equals(dtoFromDB.getNom())) { // change of name
					dtoFromDB.setNom(name);
					dtoFromDB.setStatut(MediumStatusEnum.VALID.toString());
					dtoFromDB.setDateModificationSnom(new Date());
					dtoFromDB.setPersonneMorale(pmDto);

					response = create(dtoFromDB);
				}
			}
		}
		else {
			response = create(synonymeDto);
		}
		return response;
	}

	public Set<SynonymeDTO> findValidByTypeAndGin(String sType, String sGin) throws JrafDomainException {
		Set<SynonymeDTO> response = null;
		Set<Synonyme> synFromDB = synonymeRepository.findValidByTypeAndGin(sType, sGin);

		if (!CollectionUtils.isEmpty(synFromDB)) {
			response = SynonymeTransform.bo2Dto(synFromDB);
		}

		return response;
	}

	/*PROTECTED REGION END*/
}
