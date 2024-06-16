package com.airfrance.repind.service.internal.unitservice.adresse;

import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;
import com.airfrance.ref.type.MediumStatusEnum;
import com.airfrance.repind.dao.adresse.FormalizedAdrRepository;
import com.airfrance.repind.dao.adresse.PostalAddressRepository;
import com.airfrance.repind.dao.adresse.PostalAddressToNormalizeRepository;
import com.airfrance.repind.dao.environnement.VariablesRepository;
import com.airfrance.repind.dao.reference.PaysRepository;
import com.airfrance.repind.dao.reference.RefProvinceRepository;
import com.airfrance.repind.dto.adresse.PostalAddressDTO;
import com.airfrance.repind.dto.adresse.Usage_mediumDTO;
import com.airfrance.repind.entity.adresse.FormalizedAdr;
import com.airfrance.repind.entity.adresse.PostalAddress;
import com.airfrance.repind.entity.adresse.PostalAddressToNormalize;
import com.airfrance.repind.entity.firme.PersonneMorale;
import com.airfrance.repind.entity.refTable.RefTableCAT_MED;
import com.airfrance.repind.entity.refTable.RefTableREF_STA_MED;
import com.airfrance.repind.entity.reference.Pays;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.*;

@Service
public class PostalAddressUS {

	/** logger */
	private static final Log log = LogFactory.getLog(PostalAddressUS.class);

	/* PROTECTED REGION ID(_DES8EKLhEeSXNpATSKyi0Q u var) ENABLED START */
	// add your custom variables here if necessary

	@Autowired
    private ApplicationContext context;
//QAPortType
	/* PROTECTED REGION END */

	/** references on associated DAOs */
	@Autowired
	private FormalizedAdrRepository formalizedAdrRepository;

	/** references on associated DAOs */
	@Autowired
	private PaysRepository paysRepository;

	/** references on associated DAOs */
	@Autowired
	private PostalAddressRepository postalAddressRepository;

	/** references on associated DAOs */
	@Autowired
	private PostalAddressToNormalizeRepository postalAddressToNormalizeRepository;

	/** references on associated DAOs */
	@Autowired
	private RefProvinceRepository refProvinceRepository;

	/** references on associated Repository */
	@Autowired
	private VariablesRepository variablesRepository;

	@Autowired
	private DqeUS dqeUs;
	
	private final String ISI_APP_CODE = "ISI";
	private final String BDC_APP_CODE = "BDC";
	
	public PostalAddressToNormalizeRepository getPostalAddressToNormalizeRepository() {
		return postalAddressToNormalizeRepository;
	}

	public void setPostalAddressToNormalizeRepository(
			PostalAddressToNormalizeRepository postalAddressToNormalizeRepository) {
		this.postalAddressToNormalizeRepository = postalAddressToNormalizeRepository;
	}

	/**
	 * Initialize the CountryCodeMap for the ISO2-ISO3 conversion
	 */
	public PostalAddressUS() {
	}

	public FormalizedAdrRepository getFormalizedAdrRepository() {
		return formalizedAdrRepository;
	}

	public void setFormalizedAdrRepository(FormalizedAdrRepository formalizedAdrRepository) {
		this.formalizedAdrRepository = formalizedAdrRepository;
	}
	
	public PaysRepository getPaysRepository() {
		return paysRepository;
	}

	public void setPaysRepository(PaysRepository paysRepository) {
		this.paysRepository = paysRepository;
	}

	public VariablesRepository getVariablesRepository() {
		return variablesRepository;
	}

	public void setVariablesRepository(VariablesRepository variablesRepository) {
		this.variablesRepository = variablesRepository;
	}

	public PostalAddressRepository getPostalAddressRepository() {
		return postalAddressRepository;
	}

	public void setPostalAddressRepository(PostalAddressRepository postalAddressRepository) {
		this.postalAddressRepository = postalAddressRepository;
	}

	/**
	 * getIso3Code
	 *
	 * @param scode_pays
	 *            in String
	 * @return The iso3code as <code>String</code>
	 * @throws JrafDomainException
	 *             if exception
	 */
	public String getIso3Code(String scode_pays) throws JrafDaoException {
		String iso3code = "";
		if (scode_pays != null && scode_pays.length() > 0) {
			Optional<Pays> findPays = paysRepository.findById(scode_pays);
			if (findPays.isPresent()) {
				if (findPays.get().getIso3Code() != null && !findPays.get().getIso3Code().equals("")) {
					return findPays.get().getIso3Code();
				}
			}
		}
		return iso3code;
	}

	/**
	 * isPaysNormalisable
	 *
	 * @param codePays
	 *            in String
	 * @return The isPaysNormalisable as <code>Boolean</code>
	 * @throws JrafDomainException
	 *             if exception
	 */
	public Boolean isPaysNormalisable(String codePays) throws JrafDomainException {

		Boolean result = false;
		if (codePays != null && codePays.length() > 0) {
			Optional<Pays> findPays = paysRepository.findById(codePays);
			if (findPays.isPresent()) {
				if (findPays.get().isPaysNormalisable(codePays)) {
					result = true;
				}
			}
		}
		return result;
	}

	/**
	 * A postal address is considered with empty data, if following fields are null
	 * or empty :
	 * - no_et_rue
	 * - complement adresse
	 * - localite
	 * - code postal
	 * - ville
	 * - code pays
	 * - code_province
	 *
	 * @param pa
	 *            Postal address to analyze
	 * @return
	 */
	private boolean isEmptyData(PostalAddress pa) {
		boolean emptyData = false;

		if (StringUtils.isEmpty(pa.getScode_pays()) && StringUtils.isEmpty(pa.getScode_postal())
				&& StringUtils.isEmpty(pa.getScomplement_adresse()) && StringUtils.isEmpty(pa.getSno_et_rue())
				&& StringUtils.isEmpty(pa.getSville())) {
			emptyData = true;
		}

		return emptyData;
	}





	private void save(PostalAddress paToSave) throws JrafDaoException, InvalidParameterException {

		// Formalize address
		FormalizedAdr fAdr = null;
		if (paToSave.getFormalizedAdr() != null) {
			fAdr = paToSave.getFormalizedAdr();

			if (StringUtils.isEmpty(fAdr.getSainAdr())) {
				paToSave.setFormalizedAdrs(new HashSet<FormalizedAdr>());
			}
		}

		// postalAddressToNormalize address
		PostalAddressToNormalize pAToNormalize = null;
		if (paToSave.getPostalAddressToNormalize() != null) {
			pAToNormalize = paToSave.getPostalAddressToNormalize();

			if (StringUtils.isEmpty(pAToNormalize.getSain())) {
				paToSave.setPostalAddressesToNormalize(new HashSet<PostalAddressToNormalize>());
			}
		}

		postalAddressRepository.saveAndFlush(paToSave);

		if (fAdr != null) {
			fAdr.setSainAdr(paToSave.getSain());

			boolean forcage = "O".equals(paToSave.getSforcage());
			if (!forcage) {
				//formalizedAdrRepository.saveAndFlush(fAdr);
			} else {
				//formalizedAdrRepository.delete(fAdr);
			}
		}

		if (pAToNormalize != null) {
			pAToNormalize.setSain(paToSave.getSain());

			boolean forcage = "O".equals(paToSave.getSforcage());
			if (!forcage) {
				postalAddressToNormalizeRepository.saveAndFlush(pAToNormalize);
			} else {
				postalAddressToNormalizeRepository.delete(pAToNormalize);
			}
		}
	}

	private PostalAddress check(PostalAddress paToCheck, PersonneMorale pm) throws JrafDomainRollbackException {
		// postalAddressSaved : bo used with DAO layer
		PostalAddress postalAddressChecked = null;

		// Actual date
		Date now = new Date();

		// FDD Postal Address RM 2 : Iteration is identified by a unique number for a
		// Company (AIN) and Update only apply if AIN is provided.
		// No AIN sent with valid data will generate a create of this address.
		if (StringUtils.isEmpty(paToCheck.getSain())) {
			// Create address

			// Champs a valoriser par defaut
			paToCheck.setSain(null);
			paToCheck.setVersion(null);

			paToCheck.setSgin(null);// simple pr�caution en attendant de supprimer cet attribut redondant

			paToCheck.setDdate_creation(now); // signature et site ont nomralt �t� sett�s par l'appelant

			paToCheck.setDdate_modification(now); // doit �tre sett� � cause de la r�plication
			paToCheck.setSsignature_modification(paToCheck.getSignature_creation()); // doit �tre sett� � cause de la
			// r�plication
			paToCheck.setSsite_modification(paToCheck.getSsite_creation()); // doit �tre sett� � cause de la r�plication

			// TODO LBN comment renseigne-t-on les champs suivants ?
			paToCheck.setIkey_temp(33939748); // ????
			paToCheck.setCodeAppliSending(null);
			paToCheck.setDenvoi_postal(null);
			paToCheck.setFonction(null);
			paToCheck.setIcle_role(null);

			paToCheck.setSdescriptif_complementaire(null);
			paToCheck.setSenvoi_postal(null);
			paToCheck.setSindadr(null);
			paToCheck.setStype_invalidite(null);

			paToCheck.setDdate_fonctionnel(null);
			paToCheck.setSsignature_fonctionnel(null);
			paToCheck.setSsite_fonctionnel(null);

			paToCheck.setIcod_err(0);

			// Check and normalize address if necessary
			// 'false' to precise that it does not concern the rules about a fonction's
			// address
			dqeUs.normalizeAddress(paToCheck , true , false);

			paToCheck.setPersonneMorale(pm);
			postalAddressChecked = paToCheck;

		} else {

			// Modify address
			// Get the existing postal address to modify
			PostalAddress existingPostalAddress = null;
			List<PostalAddress> lstPaExisting = new ArrayList<>(pm.getPostalAddresses());
			for (PostalAddress existingPA : lstPaExisting) {
				if (existingPA.getSain().trim().equals(paToCheck.getSain())
						|| existingPA.getSain().equals(paToCheck.getSain())) {
					existingPostalAddress = existingPA;

					break;
				}
			}

			if (existingPostalAddress == null) {
				// throw new JrafDomainRollbackException("904"); // REF_ERREUR : INVALID
				// SEQUENCE
				throw new JrafDomainRollbackException("ADDRESS AIN NOT FOUND : " + paToCheck.getSain());
			}
			// On v�rifie que la version en entr�e co�ncide avec la version stock�e
			else if (!existingPostalAddress.getVersion().equals(paToCheck.getVersion())) {

				throw new JrafDomainRollbackException("003");// REF_ERREUR : SIMULTANEOUS UPDATE
			} else {
				// FDD Postal Address RM 3 : AIN sent with blank data will change the status to
				// X, except for localization address:
				// --> ERROR 213 : LOCALISATION ADDRESS REMOVAL NOT ALLOWED
				boolean paToChecIsEmptyData = isEmptyData(paToCheck);
				if (!paToChecIsEmptyData) {
					// Check and normalize address if necessary
					paToCheck.setFormalizedAdr(existingPostalAddress.getFormalizedAdr());
					paToCheck.setPostalAddressToNormalize(existingPostalAddress.getPostalAddressToNormalize());
					// Check and normalize address if necessary
					// false to precise that it does not concern the rules about a fonction's
					// address
					dqeUs.normalizeAddress(paToCheck, true , false);
				}

				// FDD Postal Address RM 7 : Can change a status of an existing address except
				// if it is a localization address:
				// --> ERROR 213 : LOCALISATION ADDRESS REMOVAL NOT ALLOWED
				if (!existingPostalAddress.getSstatut_medium().equals(paToCheck.getSstatut_medium())
						&& "L".equals(existingPostalAddress.getScode_medium())) {
					throw new JrafDomainRollbackException("213"); // REF_ERREUR : LOCALISATION ADDRESS REMOVAL NOT
					// ALLOWED
				}
				// FDD Postal Address RM 5 : Medium Status : Update impossible on iteration with
				// Status H or X : it means that once a status is set to X or H, it cannot be
				// modified,
				// but one can change the data (no rue, code postal, etc) of this address
				else if (("H".equals(existingPostalAddress.getSstatut_medium())
						|| "X".equals(existingPostalAddress.getSstatut_medium()))
						&& !existingPostalAddress.getSstatut_medium().equals(paToCheck.getSstatut_medium())) {
					// TODO LBN : preciser le code erreur : // REF_ERREUR : STATUS X or H CANNOT BE
					// MODIFIED
					throw new JrafDomainRollbackException("ADDRESS MEDIUM STATUS X or H CANNOT BE MODIFIED");
				}
				// Modify the existing address
				else {
					if (!paToChecIsEmptyData) {
						// Cannot change the medium code of an existing address:
						// TODO LBN : preciser le code erreur : // REF_ERREUR : MEDIUM CODE CANNOT BE
						// MODIFIED
//						if (!existingPostalAddress.getScode_medium().equals(paToCheck.getScode_medium())) {
//							throw new JrafDomainRollbackException("ADDRESS MEDIUM CODE CANNOT BE MODIFIED");
//						}

						// FDD Postal Address 8 : When the content of an address (localization or
						// whatever the type of the address) is changed,
						// it is the address referenced by its SAIN that is changed (the previous
						// address is not kept as thought before).

						existingPostalAddress.setSraison_sociale(paToCheck.getSraison_sociale());
						existingPostalAddress.setScomplement_adresse(paToCheck.getScomplement_adresse());
						existingPostalAddress.setSno_et_rue(paToCheck.getSno_et_rue());
						existingPostalAddress.setSlocalite(paToCheck.getSlocalite());
						existingPostalAddress.setScode_postal(paToCheck.getScode_postal());
						existingPostalAddress.setSville(paToCheck.getSville());
						existingPostalAddress.setScode_pays(paToCheck.getScode_pays());
						existingPostalAddress.setScode_province(paToCheck.getScode_province());
						existingPostalAddress.setScode_medium(paToCheck.getScode_medium());
						existingPostalAddress.setSforcage(paToCheck.getSforcage());

						existingPostalAddress.setScod_err_simple(paToCheck.getScod_err_simple());
						existingPostalAddress.setScod_err_detaille(paToCheck.getScod_err_detaille());

						existingPostalAddress.setFormalizedAdr(paToCheck.getFormalizedAdr());
					} else {
						paToCheck.setSstatut_medium("X"); // Data empty => status closed
					}

					existingPostalAddress.setSstatut_medium(paToCheck.getSstatut_medium());

					existingPostalAddress.setDdate_modification(now);
					existingPostalAddress.setSsignature_modification(paToCheck.getSsignature_modification());
					existingPostalAddress.setSsite_modification(paToCheck.getSsite_modification());

					postalAddressChecked = existingPostalAddress;
				}
			}
		}

		// Return the normalized version of the postal address to save
		return postalAddressChecked;
	}

	public void createOrUpdateOrDelete(List<PostalAddress> pPostalAddresses, PersonneMorale pPersonneMorale)
			throws JrafDomainException {

		Assert.notNull(pPostalAddresses);
		Assert.notNull(pPersonneMorale);
		Assert.notNull(pPersonneMorale.getGin());

		// Initialize PersonneMorale PostalAddresses
		if (pPersonneMorale.getPostalAddresses() == null) {
			pPersonneMorale.setPostalAddresses(new HashSet<PostalAddress>());
		}

		List<PostalAddress> postalAddressesToCreate = new ArrayList<>();
		List<PostalAddress> postalAddressesToUpdate = new ArrayList<>();

		// Check Validity of all postalAddresses in the list
		for (PostalAddress postalAddress : pPostalAddresses) {
			PostalAddress paToSave = check(postalAddress, pPersonneMorale);

			if (paToSave.getSain() == null) {
				postalAddressesToCreate.add(paToSave);
			} else {
				postalAddressesToUpdate.add(paToSave);
			}
		}

		// Check Rule for global postal addresses
		// Unicity of an address must be checked on the Medium Code (whatever the Medium
		// Status).
		// Unicity and existence is checked on the valid localization address.
		// List to check the global RM
		List<PostalAddress> postalAddresses = new ArrayList<>(pPersonneMorale.getPostalAddresses());
		postalAddresses.addAll(postalAddressesToCreate);

		int foundValidLocalization = 0;

		Map<String, Integer> foundMediumCodeAddress = new HashMap<>();
		for (PostalAddress postalAddress : postalAddresses) {
			Integer nb = 0;
			if (foundMediumCodeAddress.containsKey(postalAddress.getScode_medium())) {
				nb = foundMediumCodeAddress.get(postalAddress.getScode_medium());
			}

			// REPFIRM-606: Only V status are counted
			if (RefTableREF_STA_MED._REF_V.equals(postalAddress.getSstatut_medium())) {
				nb++;
			}

			if (nb > 1) {
				throw new JrafDomainRollbackException("138"); // REF_ERREUR : VALID ADDRESS ALREADY FILLED
			}

			foundMediumCodeAddress.put(postalAddress.getScode_medium(), nb);

			// Specific count => unicity and existence on valid localization address
			if (RefTableCAT_MED._REF_L.equals(postalAddress.getScode_medium())) {
				foundValidLocalization = nb;
			}
		}

		// FDD Postal Address RM 1 : There must always be a valid localization address
		// for a firm (PM type = 'T').
		if (foundValidLocalization == 0) {
			// TODO LBN : preciser le code erreur : VALID LOCALIZATION ADDRESS IS MANDATORY
			// En attendant: faut-il utiliser REF_ERREUR n�167 : ADDRESS MANDATORY ???
			throw new JrafDomainRollbackException("VALID LOCALIZATION ADDRESS IS MANDATORY");
		}

		// Enregistrement en base
		for (PostalAddress postalAddress : postalAddressesToCreate) {
			save(postalAddress);
		}
		for (PostalAddress postalAddress : postalAddressesToUpdate) {
			save(postalAddress);
		}
	}
	

	public boolean isUsedBy(PostalAddressDTO addressToCheck, String appCode) {
		boolean result = false;
		
		if (addressToCheck != null && addressToCheck.getUsage_mediumdto() != null) {
			for (Usage_mediumDTO usage : addressToCheck.getUsage_mediumdto()) {
				if (appCode.equalsIgnoreCase(usage.getScode_application())) {
					result = true;
					break;
				}
			}
		}
		return result;
	}

	public void prepareNewBdcAddressUsage(PostalAddressDTO newAddress, PostalAddressDTO oldAddress) {
		// Historize old address if no ISI usage 
		if (isUsedBy(oldAddress, ISI_APP_CODE)) {
			// Remove BDC usage only
			for (Usage_mediumDTO usage: oldAddress.getUsage_mediumdto()) {
				if (BDC_APP_CODE.equalsIgnoreCase(usage.getScode_application())) {
					oldAddress.getUsage_mediumdto().remove(usage);
				}
			}
		}
		else {
			oldAddress.setNumeroUsage(null);
			oldAddress.getUsage_mediumdto().clear();
			oldAddress.setSstatut_medium(MediumStatusEnum.HISTORIZED.toString());
		}
	}

	public void prepareNewIsiAddressUsage(PostalAddressDTO newAddress, PostalAddressDTO oldAddress) {
		
		// Same code medium = historize old address if no BDC usage 
		if (newAddress.getScode_medium().equalsIgnoreCase(oldAddress.getScode_medium())) {
			if (isUsedBy(oldAddress, BDC_APP_CODE)) {
				// Remove ISI usage only as BDC usage exists
				for (Usage_mediumDTO usage: oldAddress.getUsage_mediumdto()) {
					if (ISI_APP_CODE.equalsIgnoreCase(usage.getScode_application())) {
						oldAddress.getUsage_mediumdto().remove(usage);
					}
				}
			}
			else {
				oldAddress.setNumeroUsage(null);
				oldAddress.getUsage_mediumdto().clear();
				oldAddress.setSstatut_medium(MediumStatusEnum.HISTORIZED.toString());
			}
		}
		else { // switch usage to complementary as they have different medium code
			for (Usage_mediumDTO umd: oldAddress.getUsage_mediumdto()) {
				if (ISI_APP_CODE.equalsIgnoreCase(umd.getScode_application())) {
					umd.setSrole1("C");
					umd.setInum(01);
				}
			}
		}
	}
	/* PROTECTED REGION END */


}
