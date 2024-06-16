package com.airfrance.repind.service.internal.unitservice.individu;

/*PROTECTED REGION ID(_q6a0wPcUEd-Kx8TJdz7fGw US i) ENABLED START*/

// add not generated imports here


import com.airfrance.ref.exception.MissingParameterException;
import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.type.IdentifierOptionTypeEnum;
import com.airfrance.repind.dao.individu.IndividuRepository;
import com.airfrance.repind.dao.role.RoleContratsRepository;
import com.airfrance.repind.dto.individu.RequeteHomonymesDTO;
import com.airfrance.repind.dto.individu.adh.searchindividualbymulticriteria.*;
import com.airfrance.repind.dto.individu.provideginforuserid.ProvideGinForUserIdRequestDTO;
import com.airfrance.repind.dto.individu.provideginforuserid.ProvideGinForUserIdResponseDTO;
import com.airfrance.repind.entity.adresse.PostalAddress;
import com.airfrance.repind.entity.individu.Individu;
import com.airfrance.repind.fonetik.PhEntree;
import com.airfrance.repind.fonetik.PhonetikInput;
import com.airfrance.repind.service.adresse.internal.AdresseDS;
import com.airfrance.repind.util.NormalizedStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class IndividuUS {

    /** logger */
    private static final Log log = LogFactory.getLog(IndividuUS.class);
    
    private static final String CONTEXT_DEAD_INDIVIDUAL_FOR_SEARCH = "DECEASED_FOR_AMEX";

    /*PROTECTED REGION ID(_q6a0wPcUEd-Kx8TJdz7fGw u var) ENABLED START*/
    /** the Entity Manager*/
    @PersistenceContext(unitName="entityManagerFactoryRepind")
    private EntityManager entityManager;
    
    @Autowired
    private AdresseDS adresseDS;
    
    @Autowired
    private RoleContratsRepository roleContratsRepository;
    
    /** Reference sur le unit service MyAccountUS **/
    @Autowired
    private MyAccountUS myAccountUS;


	public static final  String _REF_133 = "133";
	public static final  String _REF_323 = "323";
    
    /*PROTECTED REGION END*/
    
    /** references on associated DAOs */
    @Autowired
    private IndividuRepository individuRepository;

    /**
     * Constructeur vide
     */
    public IndividuUS() {
    }


    public IndividuRepository getIndividuRepository() {
		return individuRepository;
	}


	public void setIndividuRepository(IndividuRepository individuRepository) {
		this.individuRepository = individuRepository;
	}


	/*PROTECTED REGION ID(_q6a0wPcUEd-Kx8TJdz7fGw u m) ENABLED START*/
    public List<Individu> creerIndividusByName(RequeteHomonymesDTO requete) throws JrafDaoException {
		List<Individu> result = null;
		
		if (log.isDebugEnabled()) {
			log.debug("creerIndividusByName, ");
			if (requete != null) {
				log.debug("using request : " + requete.toStringImpl());
			}
		}
		// Recherche par email si nom et prenom non renseignes
		if (requete.getNom()==null && requete.getPrenom()==null && requete.getEmail()!=null) 
		{
			result = creerListeIndividusSearchEmailOnly(requete.getEmail(), null);
		} 
		else {
			
			// Si recherche par nom et prenom alors ils sont obligatoires
			if (requete.getNom()==null || requete.getPrenom()==null)
			{
				throw new JrafDaoException(_REF_133);
			}
			
			// Par defaut on fait une recherche stricte (S) et non pas like (L) ou phonetique (P)
			String typeRechNom = requete.getTypeRechNom();
			if(typeRechNom==null || ("").equalsIgnoreCase(typeRechNom))
			{
				typeRechNom = "S";
			}
			String typeRechPrenom = requete.getTypeRechPrenom();
			if(typeRechPrenom==null || ("").equalsIgnoreCase(typeRechPrenom))
			{
				typeRechPrenom = "S";
			}
			String typeRechVille = requete.getTypeRechVille();
			if( typeRechVille==null || ("").equalsIgnoreCase(typeRechVille))
			{
				typeRechVille = "S";
			}
			String typeRechCodePostal = requete.getTypeRechCodePostal();
			if( typeRechCodePostal==null || ("").equalsIgnoreCase(typeRechCodePostal))
			{
				typeRechCodePostal = "S";
			}
			String typeRechTelephone = requete.getTypeRechTelephone();
			if( typeRechTelephone==null || ("").equalsIgnoreCase(typeRechTelephone))
			{
				typeRechTelephone = "S";
			}
	
			PostalAddress adrPostale = new PostalAddress();
			if (requete.getCodePays() != null) {
				adrPostale.setSno_et_rue(requete.getNoRue());
				adrPostale.setScomplement_adresse(requete.getComplementAdresse());
				adrPostale.setSlocalite(requete.getLocalite());
				adrPostale.setScode_postal(requete.getCodePostal());
				adrPostale.setSville(requete.getVille());
				adrPostale.setScode_province(requete.getCodeProvince());
				adrPostale.setScode_pays(requete.getCodePays());
				adrPostale.setSstatut_medium("V");
				adrPostale.setSforcage( "N" );    // Forcage = "N"
			}
	
			// En cas de recherche piloté par le Referentiel, il y a jusqu'à  3 étapes:
			for (int iStep = 1; iStep <= 3; iStep++) {
	
				// Si pilotage Referentiel => on positionne les Types de Recherche Selon l'etape
				if ( !"A".equalsIgnoreCase(requete.getTypeRechIndividus())) {
					switch (iStep) {
					case 1:
						typeRechNom = "S";
						if (requete.getPrenom() != null && requete.getPrenom().length() > 1)
							typeRechPrenom = "S";
						else
							typeRechPrenom = "L";
						typeRechVille = "S";
						if (requete.getCodePostal() != null && requete.getCodePostal().length() > 2)
							typeRechCodePostal = "S";
						else
							typeRechCodePostal = "L";
						typeRechTelephone = "S";
						break;
					case 2:
						typeRechNom = "P";
						if (requete.getPrenom() != null
								&& requete.getPrenom().length() > 1)
							typeRechPrenom = "P";
						else
							typeRechPrenom = "L";
						typeRechVille = "S";
						if (requete.getCodePostal() != null && requete.getCodePostal().length() > 2)
							typeRechCodePostal = "S";
						else
							typeRechCodePostal = "L";
						typeRechTelephone = "S";
						break;
					case 3:
						typeRechNom = "L";
						typeRechPrenom = "L";
						typeRechVille = "L";
						typeRechCodePostal = "L";
						typeRechTelephone = "L";
						break;
					}
				}
	
	            // Recherche Par Telephone
	            if (requete.getTelephone() != null || requete.getEmail() != null)
	            {
					result = creerListeIndividus(requete.getNom(), requete.getPrenom(),
							requete.getCivilite(), requete.getDateNaissance(), requete.getTelephone(),
							//idxContinuite,
							typeRechNom, typeRechPrenom,
							typeRechTelephone,
							//10,
							requete.getEmail());
	            }
	            else 
	            {  // Recherche Par Adresse Postale
	
	            	result = creerListeIndividus(
	                        requete.getNom(),
	                        requete.getPrenom(),
	                        requete.getCivilite(),
	                        requete.getDateNaissance(),
	                        requete.getCodePays(),
	                        //idxContinuite,
	                        adrPostale.getSno_et_rue(),
	                        adrPostale.getScomplement_adresse(),
	                        adrPostale.getSlocalite(),
	                        adrPostale.getScode_postal(),
	                        adrPostale.getSville(),
	                        adrPostale.getScode_province(),
	                        typeRechNom,
	                        typeRechPrenom,
	                        typeRechCodePostal,
	                        typeRechVille
	                        //10 
	                        );                     // Taille de la fenetre de pagination            
	            }
	            // Pas d'étape suivante si on a trouvé qqun ou si Recherche Piloté par l'Appli
	            if ((result != null && result.size()>0) || "A".equalsIgnoreCase(requete.getTypeRechIndividus()))
	                break;
			}
		}
		return result;
	}
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Individu> creerIndividusByNameSearch(SearchIndividualByMulticriteriaRequestDTO requete) throws JrafDaoException, MissingParameterException {
    	
    	if (log.isDebugEnabled()) {
			log.debug("Starting creerIndividusByNameSearch, ");
			if (requete != null) {
				log.debug("using request : " + requete.toStringImpl());
			}
		}
    	
    	//REPIND-1673: We return Dead Individual
    	boolean returnDeadIndividual = false;
    	if (requete.getRequestor() != null && StringUtils.isNotEmpty(requete.getRequestor().getContext()) && CONTEXT_DEAD_INDIVIDUAL_FOR_SEARCH.equalsIgnoreCase(requete.getRequestor().getContext())) {
    		returnDeadIndividual = true;
    	}
    	
    	List<Individu> result = null;

		// controles sur Identity, Contact et AddressBloc
		if (requete.getIdentity() == null){
			IdentityDTO identity = new IdentityDTO();
			requete.setIdentity(identity);
		}
		
		if (requete.getContact() == null){
			ContactDTO contact = new ContactDTO();
			requete.setContact(contact);
		}
		
		// affectation de la structure generale Identity, Contact, AddressBloc
		IdentityDTO identity = requete.getIdentity();
		ContactDTO contact = requete.getContact();
		PostalAddressBlocDTO adresse = contact.getPostalAddressBloc();
		IdentificationDTO identification = requete.getIdentification();
		
		// Si le nom et le prenom ne sont pas renseignes, on utilise le second mode de recherche
		// via email ou telecom
		if (identity.getLastName()==null && identity.getFirstName()==null){
			if (contact.getEmail()!=null) {
				result = creerListeIndividusSearchEmailOnly(contact.getEmail(), requete.getPopulationTargeted());
			} else if (contact.getPhoneNumber()!=null) {				
				result = creerListeIndividusSearchPhoneOnly(contact.getPhoneNumber(), requete.getPopulationTargeted());			
			// REPIND-1264 : Add Search for Social by Type and Identifier
			} else if (identification != null) {
				result = creerListeIndividusSearchIdentificationOnly(identification.getIdentificationType(), identification.getIdentificationValue());
			}
		}
		
		// recherche standard avec nom et prenom en entree
		// une 3eme information est necessaire pour remonter des individus
		// on va donc jouer N fois la requete de recherche, avec N le nombre de parametres supplementaires
		//
		// exemple: Jean Dupont 01/01/1960 jdupont@mail.com
		// on cherchera les Jean Dupont nes le 01/01/1960 puis les Jean Dupont ayant jdupont@mail.com comme email
		// on supprimera les doublons dans le resultat final
		else {
			
			// Controle la presence du nom, prenom et Contact/BirthDate/Social sinon => Exception 133
			// Bug Release V44
			// REPIND-1495 : A Search could not be done by NAME and Social
			controleNomPrenomContactDateDeNaissanceIdentification(identity, contact, identification);
			
			identity.setLastName(NormalizedStringUtils.normalizeString(identity.getLastName()));
			identity.setFirstName(NormalizedStringUtils.normalizeString(identity.getFirstName()));				
			
			// par defaut on fait une recherche stricte (S) et non pas like (L)
			String typeRechNom = identity.getLastNameSearchType();
			if(typeRechNom==null || ("").equalsIgnoreCase(typeRechNom))
			{
				typeRechNom = "S";
			}
			String typeRechPrenom = identity.getFirstNameSearchType();
			if(typeRechPrenom==null || ("").equalsIgnoreCase(typeRechPrenom))
			{
				typeRechPrenom = "S";
			}
	
			// construction de la structure d adresse postale
			PostalAddress adrPostale = new PostalAddress();
			if (adresse != null && adresse.getCountryCode() != null) {
				adrPostale.setSno_et_rue(NormalizedStringUtils.normalizeString(adresse.getNumberAndStreet()));
				adrPostale.setScomplement_adresse(NormalizedStringUtils.normalizeString(adresse.getAdditionalInformation()));
				adrPostale.setSlocalite("");
				adrPostale.setScode_postal(adresse.getZipCode());
				adrPostale.setSville(NormalizedStringUtils.normalizeString(adresse.getCity()));
				adrPostale.setScode_province("");
				adrPostale.setScode_pays(adresse.getCountryCode());
				adrPostale.setSstatut_medium("V");
				adrPostale.setSforcage( "N" );    // Forcage = "N"
			}

			//REPIND-1673: We return Dead Individual
            // recherche par telephone
			// -----------------------
            if (contact.getPhoneNumber() != null && !"".equals(contact.getPhoneNumber()))
            {
				result = creerListeIndividusSearchPhone(identity.getLastName(), identity.getFirstName(),
						identity.getCivility(), contact.getPhoneNumber(),
						typeRechNom, typeRechPrenom,
						//typeRechTelephone,
						"S", identity.getBirthday(), 
						requete.getPopulationTargeted(), returnDeadIndividual);
            }
            
            //REPIND-1673: We return Dead Individual
            // recherche par email
            // -----------------------
            if (contact.getEmail() != null && !"".equals(contact.getEmail()))
            {
            	if (result != null){
            		List<Individu> resultEmail = creerListeIndividusSearchEmail(identity.getLastName(), identity.getFirstName(), identity.getCivility(),
    						typeRechNom, typeRechPrenom, contact.getEmail(), identity.getBirthday(), requete.getPopulationTargeted(), returnDeadIndividual);
            		result.addAll(resultEmail);
            	} else {
            		result = creerListeIndividusSearchEmail(identity.getLastName(), identity.getFirstName(), identity.getCivility(),
    						typeRechNom, typeRechPrenom, contact.getEmail(), identity.getBirthday(), requete.getPopulationTargeted(), returnDeadIndividual);
            	}
				
            }
            
            //REPIND-1673: We return Dead Individual
            // recherche par date de naissance
            // -----------------------
            if (identity.getBirthday() != null)
            {
            	if (result != null){
            		List<Individu> resultBirthday = creerListeIndividusSearchDate(identity.getLastName(), identity.getFirstName(),
    						identity.getCivility(), identity.getBirthday(),
    						typeRechNom, typeRechPrenom, returnDeadIndividual);
            		result.addAll(resultBirthday);
            	} else {
            		result = creerListeIndividusSearchDate(identity.getLastName(), identity.getFirstName(),
    						identity.getCivility(), identity.getBirthday(),
    						typeRechNom, typeRechPrenom, returnDeadIndividual);
            	}
            }
            
            //REPIND-1673: We return Dead Individual
            // recherche par adresses postale
            // -----------------------
            if (contact.getPostalAddressBloc() != null)
            {
            	String typeRechCodePostal ="";
            	String typeRechVille = "";
            	
            	if (adresse != null)
            		typeRechCodePostal = adresse.getZipCodeSearchType();
    			if(typeRechCodePostal==null || ("").equalsIgnoreCase(typeRechCodePostal))
    			{
    				typeRechCodePostal = "L";
    			}
    			
    			if (adresse != null)
    				typeRechVille = adresse.getCitySearchType();
    			if(typeRechVille==null || ("").equalsIgnoreCase(typeRechVille))
    			{
    				typeRechVille = "L";
    			}
    			
            	if (result != null){
            		List<Individu> resultAddress = creerListeIndividusSearchAddress(
                            identity.getLastName(),
                            identity.getFirstName(),
                            identity.getCivility(),
                            adrPostale.getScode_pays(),
                            //idxContinuite,
                            adrPostale.getSno_et_rue(),
                            adrPostale.getScomplement_adresse(),
                            adrPostale.getSlocalite(),
                            adrPostale.getScode_postal(),
                            adrPostale.getSville(),
                            adrPostale.getScode_province(),
                            typeRechNom,
                            typeRechPrenom,
                            typeRechCodePostal,
                            typeRechVille,
                            identity.getBirthday(), 
                            requete.getPopulationTargeted(),
                            returnDeadIndividual
                            //10 
                            );
            		result.addAll(resultAddress);
            	} else {
            		result = creerListeIndividusSearchAddress(
                            identity.getLastName(),
                            identity.getFirstName(),
                            identity.getCivility(),
                            adrPostale.getScode_pays(),
                            //idxContinuite,
                            adrPostale.getSno_et_rue(),
                            adrPostale.getScomplement_adresse(),
                            adrPostale.getSlocalite(),
                            adrPostale.getScode_postal(),
                            adrPostale.getSville(),
                            adrPostale.getScode_province(),
                            typeRechNom,
                            typeRechPrenom,
                            typeRechCodePostal,
                            typeRechVille,
                            identity.getBirthday(),
                            requete.getPopulationTargeted(),
                            returnDeadIndividual
                            //10 
                            );
            	}
            }
            
            // recherche par social
            // -----------------------
            if (identification != null)
            {
            	if (result != null){
            		List<Individu> resultSocial = creerListeIndividusSearchIdentificationName(identification.getIdentificationType(), identification.getIdentificationValue(), identity.getLastName(), identity.getFirstName(), identity.getCivility(), typeRechNom, typeRechPrenom, false);
            		result.addAll(resultSocial);
            	} else {
            		result = creerListeIndividusSearchIdentificationName(identification.getIdentificationType(), identification.getIdentificationValue(), identity.getLastName(), identity.getFirstName(), identity.getCivility(), typeRechNom, typeRechPrenom, true);
            	}
            }  
            
            
            // REPIND-1675 : Add only FirstName / LastName search with lower relevancy
            if (identity != null && 
            		identity.getFirstName() != null && !"".equals(identity.getFirstName()) &&  
            		identity.getLastName() != null && !"".equals(identity.getLastName()) &&
            	identity.getBirthday() == null &&
            	(contact == null || contact.isEmpty()) &&             	
            	identification == null && 
            	result == null 					// To be sure that no query have been done before...
            	) {
            	
            	result = creerListeIndividusSearchName(identity.getLastName(), identity.getFirstName(), identity.getCivility(),
						typeRechNom, typeRechPrenom, requete.getPopulationTargeted(), returnDeadIndividual);
            }
		}
		
		if (result != null){
			HashSet hs = new HashSet();
			hs.addAll(result);
			result.clear();
			result.addAll(hs);
		}
		
		return result;
    }

	public List<Individu> creerListeIndividus(String nom, String prenom, String civilite, Date dateNaissance, String telephone, String typeRechNom, String typeRechPrenom, String typeRechTelephone, String email) throws JrafDaoException {
		return creerListeIndividus(nom, prenom, civilite, dateNaissance, telephone, typeRechNom, typeRechPrenom, typeRechTelephone, email, "A");
	}
    
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Individu> creerListeIndividus(String nom, String prenom, String civilite, Date dateNaissance, String telephone, String typeRechNom, String typeRechPrenom, String typeRechTelephone, String email, String populationTargeted) throws JrafDaoException {
		
		log.info("creerListeIndividus(" + nom + "," + prenom + "," + civilite + "," + dateNaissance + ","
					+ telephone + "," + typeRechNom + "," + typeRechPrenom + "," + typeRechTelephone + "," + email+")");
		
		List<Individu> result = new ArrayList<Individu>();
        try
        {
        	
        	if (nom == null && prenom == null) throw new JrafDaoException(_REF_133);
        	Map parameters = new HashMap();
            // (1) Construction de la REQUETE
            // Correction des nom et prénom contenant une apostrophe
        	if (nom != null) nom = nom.replaceAll("'", "''").toUpperCase();
        	if (prenom != null) prenom = prenom.replaceAll("'", "''").toUpperCase();

        	StringBuilder sSelect = new StringBuilder("SELECT I ");
        	StringBuilder sFrom = new StringBuilder(" FROM Individu as I ");
            
            //recherche par telephone:
            if(telephone != null)
            	sFrom.append("left join I.telecoms as T ");
            
            // ajout de la recherche par email
            if(email != null)
            	sFrom.append("left join I.email as E ");
            	
            StringBuilder sWhere = new StringBuilder(" WHERE ");
            StringBuilder sWhere_email = new StringBuilder();

            // NOM ===========================
            if ("P".equalsIgnoreCase(typeRechNom))
            {
                if ("P".equalsIgnoreCase(typeRechPrenom))
                {
                    String nomPrenomOrigine = nom+" "+prenom;
                	PhonetikInput input = new PhonetikInput();
    		    	input.setIdent(nomPrenomOrigine);
    		    	PhEntree.Fonetik_Entree(input);
                    sWhere.append(" I.indicNomPrenom=").append(":sIndicNomPrenom").append(" ");
                    parameters.put("sIndicNomPrenom", input.getIndict());
                }
                else
                {
                	PhonetikInput input = new PhonetikInput();
    		    	input.setIdent(nom);
    		    	PhEntree.Fonetik_Entree(input);
    		    	sWhere.append(" I.indicNom=").append(":sIndicNom").append(" ");
    		    	parameters.put("sIndicNom", input.getIndict());
                }
            }
            else if("L".equalsIgnoreCase(typeRechNom))
            {
                if ("P".equalsIgnoreCase(typeRechPrenom))
                {
                    // La recherche ne peut être phonétique sur le prénom que si elle l'est aussi sur le nom
                	throw new JrafDaoException(_REF_323);
                }
                sWhere.append(" I.nom LIKE ").append(":nom").append(" ");
                parameters.put("nom", nom + "%");
            }
            else                                      /* typeRechNom=="S" */
            {
                if ("P".equalsIgnoreCase(typeRechPrenom))
                {
                    // La recherche ne peut être phonétique sur le prénom que si elle l'est aussi sur le nom
                	throw new JrafDaoException(_REF_323);
                }
                sWhere.append(" I.nom = ").append(":nom").append(" ");
                parameters.put("nom", nom);
            }

            // PRENOM ===========================
            if ("L".equalsIgnoreCase(typeRechPrenom)) {
                sWhere.append(" AND I.prenom LIKE ").append(":prenom").append(" ");
                parameters.put("prenom",prenom + "%");
                
            } else if ("S".equals(typeRechPrenom)) {
                sWhere.append(" AND I.prenom =").append(":prenom").append(" ");
                parameters.put("prenom", prenom);
            }

            // CIVILITE ===========================
            if ("MR".equalsIgnoreCase(civilite))
                sWhere.append(" AND I.civilite IN ('MR','M.') ");
            else if ("MS".equalsIgnoreCase(civilite))
                sWhere.append(" AND I.civilite IN ('MS','MRS','M.') ");
            else if ("MISS".equalsIgnoreCase(civilite))
                sWhere.append(" AND I.civilite IN ('MISS','MRS','M.') ");
            else if(civilite != null) {
                sWhere.append(" AND I.civilite =").append(":civilite").append(" ");
                parameters.put("civilite", civilite);
            }

            // STATUT INDIVIDU ===========================
            sWhere.append(" AND I.statutIndividu IN ('V','P') ");

            // DATE NAISSANCE ===================
            if (dateNaissance != null)
            {
                sWhere.append(" AND trunc(I.dateNaissance) = trunc(:dateNaissance)) ");
                parameters.put("dateNaissance", dateNaissance);
            }
            
            // Email ===================================
            if(email != null)
            {
            	sWhere_email.append(" AND E.statutMedium <> 'X' ");
            	sWhere_email.append(" AND E.email=").append(":email").append(" ");
            	parameters.put("email", email);
            }
            	

            // TELECOM ===================
            if(telephone != null){
            	sWhere.append(" AND T.sstatut_medium IN ('V','T') ");
            	if ("L".equals(typeRechTelephone)) {
                	sWhere.append(" AND T.snumero like ").append(":telephone").append(" ");
                	parameters.put("telephone", telephone + "%");
            	} else {
                	sWhere.append(" AND T.snumero=").append(":telephone").append(" ");
                	parameters.put("telephone", telephone);
            	}
            }

            // POPULATION TARGETED ===========================
            if (populationTargeted != null && !"".equals(populationTargeted) && !"A".equals(populationTargeted)) {
            	sWhere.append(" AND I.type = :type ");
            	parameters.put("type",populationTargeted);
            }            
            
            String sStmt = sSelect.append(sFrom).append(sWhere).append(sWhere_email).toString();
            log.debug(sStmt);
            
            Query query = getEntityManager().createQuery(sStmt);
            
            //
            // Set query parameter values
            //
            
            for (Object key: parameters.keySet()) {
            	String name = (String) key;
            	Object value = parameters.get(name);
            	query.setParameter(name,value);
            }
            
            result = query.getResultList();

            return result;
        }

        catch(Exception e)
        {
            // Gestion des erreurs applicatives
            throw new JrafDaoException(e);
        }
    	
    }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Individu> creerListeIndividus (String nom, String prenom, String civilite, Date dateNaissance, String pays, String noRue, String compltAdresse, String localite, String codePostal, String ville, String codeProvince, String typeRechNom, String typeRechPrenom, String typeRechCodePostal, String typeRechVille) throws JrafDaoException
	{
		
		log.info("creerListeIndividus(" + nom + " ," + prenom + " ," + civilite + " ," + dateNaissance + " ," + pays
					+ " ," + noRue + " ," + compltAdresse + " ," + localite + " ," + codePostal + " ," + ville + " ,"
					+ codeProvince + " ," + typeRechNom + " ," + typeRechPrenom + " ," + typeRechCodePostal + " ,"
					+ typeRechVille + ") ");

		
		nom = cleanString(nom);
		prenom = cleanString(prenom);
		civilite = cleanString(civilite);
		pays = cleanString(pays);
		noRue = cleanString(noRue);
		compltAdresse = cleanString(compltAdresse);
		localite = cleanString(localite);
		codePostal = cleanString(codePostal);
		ville = cleanString(ville);
		codeProvince = cleanString(codeProvince);
		if (nom == null && prenom == null) throw new JrafDaoException(_REF_133);
		List<Individu> result = new ArrayList<Individu>();
        try
        {
        	Map parameters = new HashMap();
	        // Correction des nom et prénom contenant une apostrophe
	        String sNom = null;
	        if (nom != null) sNom = nom.replaceAll("'", "''").toUpperCase();
	        String sPrenom = null;
	        if (nom != null) sPrenom = prenom.replaceAll("'", "''").toUpperCase();

	        StringBuilder sSelectIndividu = new StringBuilder("SELECT I");
	        StringBuilder sFromIndividu = new StringBuilder(" FROM Individu I ");

	        StringBuilder sSelectIndividuAdrPost = new StringBuilder("SELECT I");
	        StringBuilder sFromIndividuAdrPost = new StringBuilder(" FROM Individu I left join I.postaladdress A ");

	        StringBuilder sWhereCriteresIndividu = new StringBuilder(" WHERE ");
	        StringBuilder sWhereCriteresAdrPost = new StringBuilder();

	        StringBuilder sOrderBy = new StringBuilder();

	        // NOM ===========================
	        if ("P".equalsIgnoreCase(typeRechNom))
	        {
	            if ("P".equalsIgnoreCase(typeRechPrenom))
	            {
                    String nomPrenomOrigine = nom+" "+prenom;
                	PhonetikInput input = new PhonetikInput();
    		    	input.setIdent(nomPrenomOrigine);
    		    	PhEntree.Fonetik_Entree(input);
    		    	sWhereCriteresIndividu.append(" I.indicNomPrenom=").append(":sIndicNomPrenom").append(" ");
    		    	parameters.put("sIndicNomPrenom",input.getIndict());
	            }
	            else
	            {
                	PhonetikInput input = new PhonetikInput();
    		    	input.setIdent(nom);
    		    	PhEntree.Fonetik_Entree(input);
    		    	sWhereCriteresIndividu.append(" I.indicNom=").append(":sIndicNom").append(" ");
    		    	parameters.put("sIndicNom",input.getIndict());
	            }
	        }
	        else if("L".equalsIgnoreCase(typeRechNom))
	        {
	            if ("P".equalsIgnoreCase(typeRechPrenom))
	            {
	                // La recherche ne peut être phonétique sur le prénom que si elle l'est aussi sur le nom
	            	throw new JrafDaoException(_REF_323);
	            }
	            sWhereCriteresIndividu.append(" I.nom LIKE ").append(":nom").append(" ");
	            parameters.put("nom",nom + "%");
	        }
	        else                                      /* typeRechNom=="S" */
	        {
	            if ("P".equalsIgnoreCase(typeRechPrenom))
	            {
	                // La recherche ne peut être phonétique sur le prénom que si elle l'est aussi sur le nom
	            	throw new JrafDaoException(_REF_323);
	            }
	            sWhereCriteresIndividu.append(" I.nom = ").append(":nom").append(" ");
	            parameters.put("nom",nom);
	        }

	        // PRENOM ===========================
	        if ("L".equalsIgnoreCase(typeRechPrenom)) {
	            sWhereCriteresIndividu.append(" AND I.prenom LIKE ").append(":prenom").append(" ");
	            parameters.put("prenom",prenom + "%");
	        } 
	        else if ("S".equalsIgnoreCase(typeRechPrenom)) {
	            sWhereCriteresIndividu.append(" AND I.prenom = ").append(":prenom").append(" ");
	            parameters.put("prenom",prenom);
	        }

	        // DL : Mise en place d'un tri alphabetique sur nom, prenom dans le cas d'une recherche like ou phonetique
	        sOrderBy.append(" ORDER BY ");
	        if ((!"S".equalsIgnoreCase(typeRechNom)) || (!"S".equalsIgnoreCase(typeRechPrenom)))
	        {
	            if (!"S".equalsIgnoreCase(typeRechNom))
	            {
	                sOrderBy.append("I.nom");
	                if (!"S".equals(typeRechPrenom)) sOrderBy.append(", ");
	            }
	            if (!"S".equalsIgnoreCase(typeRechPrenom))
	            {
	                sOrderBy.append("I.prenom");
	            }

	            if (dateNaissance != null)
	            {
	                sOrderBy.append(", I.dateNaissance ASC");
	            }
	        }
	        else
	        {
	            sOrderBy.append(" I.dateNaissance ASC");
	        }

			// CIVILITE ===========================
			if ("MR".equalsIgnoreCase(civilite)) {
				sWhereCriteresIndividu.append(" AND I.civilite IN ('MR','M.') ");
			} else if ("MS".equalsIgnoreCase(civilite)) {
				sWhereCriteresIndividu.append(" AND I.civilite IN ('MS','MISS','MRS','M.') ");
			} else if ("MISS".equalsIgnoreCase(civilite)) {
				sWhereCriteresIndividu.append(" AND I.civilite IN ('MS','MISS','MRS','M.') ");
			} else if ("MRS".equalsIgnoreCase(civilite)) {
				sWhereCriteresIndividu.append(" AND I.civilite IN ('MS','MISS','MRS','M.') ");
			} else if ("M.".equalsIgnoreCase(civilite)) {
				sWhereCriteresIndividu.append(" AND I.civilite IN ('MR','MS','MISS','MRS','M.') ");
			} else if (civilite != null) {
				sWhereCriteresIndividu.append(" AND I.civilite =").append(":civilite").append(" ");
				parameters.put("civilite", civilite);
			}

	        // STATUT INDIVIDU ===========================
	        sWhereCriteresIndividu.append(" AND I.statutIndividu IN ('V','P') ");

	        // DATE NAISSANCE ===================
	        if (dateNaissance != null)
	        {
	            sWhereCriteresIndividu.append(" AND trunc(I.dateNaissance) = trunc(:dateNaissance) ");
	            parameters.put("dateNaissance",dateNaissance);
	        }

	        if (pays != null)
	        {
	            // le code pays est renseinge : les criteres lies a l'adresse postale sont a prendre en compte
	            // ADRESSE POSTALE ===================
	            sWhereCriteresAdrPost.append(" AND A.sstatut_medium IN ('V','T','I')");

	            // CODE PAYS ===================
	            if (pays != null)
	            {
	                sWhereCriteresAdrPost.append(" AND A.scode_pays =").append(":pays").append(" ");
	                parameters.put("pays",pays);
	            }

	            // NÂ° ET RUE ===================
	            if (noRue != null)
	            {
	                String s_NoRue = noRue.replaceAll("'", " ");
	                sWhereCriteresAdrPost.append(" AND A.sno_et_rue =").append(":sNoRue").append(" ");
	                parameters.put("sNoRue",s_NoRue);
	            }

	            // COMPLEMENT D'ADRESSE =============
	            if (compltAdresse != null)
	            {
	                String s_CmpltAdr = compltAdresse.replaceAll("'", " ");
	                sWhereCriteresAdrPost.append(" AND A.scomplement_adresse =").append(":sCmpltAdr").append(" ");
	                parameters.put("sCmpltAdr",s_CmpltAdr);
	            }

	            // LOCALITE =============
	            if (localite != null)
	            {
	                String s_Localite = localite.replaceAll("'", " ");
	                sWhereCriteresAdrPost.append(" AND A.slocalite =").append(":sLocalite").append(" ");
	                parameters.put("sLocalite",s_Localite);
	            }

	            // VILLE ============================
	            if (ville != null)
	            {
	                String s_Ville = ville.replaceAll("'", " ");
	                if ("L".equalsIgnoreCase(typeRechVille))
	                {
	                    sWhereCriteresAdrPost.append(" AND A.sville LIKE ").append(":sVille").append(" ");
	                    parameters.put("sVille",s_Ville + "%");
	                }
	                else
	                {
	                    sWhereCriteresAdrPost.append(" AND A.sville=").append(":sVille").append(" ");
	                    parameters.put("sVille",s_Ville);
	                }
	            }

	            // CODE POSTAL =======================
	            if (codePostal != null)
	            {
	                if ("L".equalsIgnoreCase(typeRechCodePostal))
	                {
	                    sWhereCriteresAdrPost.append(" AND A.scode_postal LIKE ").append(":codePostal").append(" ");
	                    parameters.put("codePostal",codePostal + "%");
	                }
	                else
	                {
	                    sWhereCriteresAdrPost.append(" AND A.scode_postal =").append(":codePostal").append(" ");
	                    parameters.put("codePostal",codePostal);
	                }
	            }

	            // CODE PROVINCE ===================
	            if (codeProvince != null)
	            {
	                sWhereCriteresAdrPost.append(" AND A.scode_province =").append(":codeProvince").append(" ");
	                parameters.put("codeProvince",codeProvince);
	            }
	        }


	        String sStmt;
	        if (pays == null)
	        {
	            // aucun critere lie a l'adresse postale n'est a prendre en compte
	            sStmt = sSelectIndividu.append(sFromIndividu)
	            					.append(sWhereCriteresIndividu).toString();
	        }
	        else
	        {
	            // les individus doivent avoir une adresse associee dans le RI
	            sStmt = sSelectIndividuAdrPost.append(sFromIndividuAdrPost)
	            							.append(sWhereCriteresIndividu)
	            							.append(sWhereCriteresAdrPost).toString();
	        }
	        if (sOrderBy.length() > 0)
	            sStmt += sOrderBy.toString();

	        // Test d'affichage de la clause where
			log.debug("Clause sStmt=" + sStmt + "!!!");
	        
            Query query = getEntityManager().createQuery(sStmt);
            //
            // Set query parameter values
            //
            for (Object key: parameters.keySet()) {
            	String name = (String) key;
            	Object value = parameters.get(name);
            	query.setParameter(name,value);
            }
            
            result = query.getResultList();

	        return result;
	    }

        catch(Exception e)
        {
            // Gestion des erreurs applicatives
            throw new JrafDaoException(e);
        }

	}
	
@SuppressWarnings({ "rawtypes", "unchecked" })
public List<Individu> creerListeIndividusSearch(String nom, String prenom, String civilite, Date dateNaissance, String telephone, String typeRechNom, String typeRechPrenom, String typeRechTelephone, String email) throws JrafDaoException {
		
		log.info("creerListeIndividusSearch(" + nom + ", " + prenom + ", " + civilite + ", " + dateNaissance + ", "
					+ telephone + ", " + typeRechNom + ", " + typeRechPrenom + ", " + typeRechTelephone + ", " + email
					+ " )");
		
		List<Individu> result = new ArrayList<Individu>();
        try
        {
        	
        	if (nom == null && prenom == null) throw new JrafDaoException(_REF_133);
        	Map parameters = new HashMap();
            // (1) Construction de la REQUETE
            // Correction des nom et prénom contenant une apostrophe
        	if (nom != null) nom = nom.replaceAll("'", "''").toUpperCase();
        	if (prenom != null) prenom = prenom.replaceAll("'", "''").toUpperCase();

        	StringBuilder sSelect = new StringBuilder("SELECT I ");
        	StringBuilder sFrom = new StringBuilder(" FROM Individu as I ");
            
            //recherche par telephone:
            if(telephone != null)
            	sFrom.append("left join I.telecoms as T ");
            
            // ajout de la recherche par email
            if(email != null)
            	sFrom.append("left join I.email as E ");
            	
            StringBuilder sWhere = new StringBuilder(" WHERE ");
            StringBuilder sWhere_email = new StringBuilder();

            // NOM ===========================
            if("L".equalsIgnoreCase(typeRechNom))
            {
                sWhere.append(" I.nom LIKE ").append(":nom").append(" ");
                parameters.put("nom", nom + "%");
            }
            else                                      /* typeRechNom=="S" */
            {
                sWhere.append(" I.nom = ").append(":nom").append(" ");
                parameters.put("nom", nom );
            }

			// PRENOM ===========================
			if ("L".equalsIgnoreCase(typeRechPrenom)) {
				sWhere.append(" AND I.prenom LIKE ").append(":prenom").append(" ");
				parameters.put("prenom", prenom + "%");
			} else if ("S".equals(typeRechPrenom)) {
				sWhere.append(" AND I.prenom = ").append(":prenom").append(" ");
				parameters.put("prenom", prenom);
			}
			// CIVILITE ===========================
			if ("MR".equalsIgnoreCase(civilite)) {
				sWhere.append(" AND I.civilite IN ('MR','M.') ");
			} else if ("MS".equalsIgnoreCase(civilite)) {
				sWhere.append(" AND I.civilite IN ('MS','MRS','M.') ");
			} else if ("MISS".equalsIgnoreCase(civilite)) {
				sWhere.append(" AND I.civilite IN ('MISS','MRS','M.') ");
			} else if (civilite != null) {
				sWhere.append(" AND I.civilite =").append(":civilite").append(" ");
				parameters.put("civilite", civilite);
			}
            // STATUT INDIVIDU ===========================
            sWhere.append(" AND I.statutIndividu IN ('V','P') ");

            // DATE NAISSANCE ===================
            if (dateNaissance != null)
	        {
	        	String dateNaissanceStr = formatDate(dateNaissance);
	            sWhere.append(" AND I.dateNaissance = ").append(":dateNaissance").append(" ");
	            parameters.put("dateNaissance",dateNaissanceStr);
	        }
            
            // Email ===================================
            if(email != null)
            {
            	sWhere_email.append(" AND E.email=").append(":email").append(" ");
            	parameters.put("email", email);
            }
            	

            // TELECOM ===================
			if (telephone != null) {
				sWhere.append(" AND T.sstatut_medium IN ('V','T') ");
				if ("L".equals(typeRechTelephone)) {
					sWhere.append(" AND T.snorm_nat_phone_number_clean like ").append(":telephone").append(" ");
					parameters.put("telephone", telephone + "%");
				} else {
					sWhere.append(" AND T.snorm_nat_phone_number_clean=").append(":telephone").append(" ");
					parameters.put("telephone", telephone);
				}
			}

            String sStmt = sSelect.append(sFrom).append(sWhere).append(sWhere_email).toString();
            log.debug(sStmt);
            
            Query query = getEntityManager().createQuery(sStmt);
            //
            // Set query parameter values
            //
            for (Object key: parameters.keySet()) {

            	String name = (String) key;
            	Object value = parameters.get(name);
            	query.setParameter(name,value);
            }
            
            result = query.getResultList();

            return result;
        }

        catch(Exception e)
        {
            // Gestion des erreurs applicatives
            throw new JrafDaoException(e);
        }
    	
    }
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<Individu> creerListeIndividusSearchEmailOnly(String email, String populationTargeted) throws JrafDaoException {
		
		log.info("creerListeIndividusSearchEmailOnly( " + email + ", " + populationTargeted + ")");
		
		List<Individu> result = null;
		Query query = null;
		if (populationTargeted == null || "".equals(populationTargeted) || populationTargeted.equalsIgnoreCase("A")){
			query = getEntityManager().createQuery("select distinct sgin from Email where sgin is not null and email = :email and (statutMedium = 'V' or statutMedium = 'T')");
		}
		else{
			query = getEntityManager().createQuery("select distinct e.sgin from Email e, Individu i where i.sgin = e.sgin and i.sgin is not null and e.sgin is not null and e.email = :email and (e.statutMedium = 'V' or e.statutMedium = 'T') and i.type = :type ");
			query.setParameter("type",populationTargeted);
		}
		query.setParameter("email",email);
		
		List<String> listGin = query.getResultList();
		if (listGin.size()>0) {
			result = new ArrayList<Individu>();
		}else{
			 // TODO : Uniformiser le retour encas de NOT DFOUND
			throw new JrafDaoException("No individus found");
		}
		for (String sGin : listGin) {
			Individu individu = individuRepository.findBySgin(sGin);
			
			// REPIND-651 : Do not catch X (deleted) individual
			if (individu != null && individu.getStatutIndividu() != null && !"X".equals(individu.getStatutIndividu())) {
				result.add(individu);
			}
		}
		return result;
	}

	@SuppressWarnings("deprecation")
	public List<Individu> creerListeIndividusSearchPhoneOnly(String phoneNumber, String populationTargeted) throws JrafDaoException {
		
		log.info("creerListeIndividusSearchPhoneOnly( " + phoneNumber + ", " + populationTargeted + ") ");
		
		List<Individu> result = null;
		Query query = null;
		if (populationTargeted == null || "".equals(populationTargeted) || populationTargeted.equalsIgnoreCase("A")){
			query = getEntityManager().createQuery("select distinct sgin from Telecoms where sgin is not null and (snorm_inter_phone_number = :phoneNumber or snorm_nat_phone_number_clean = :phoneNumber) and (sstatut_medium = 'V' or sstatut_medium = 'T')");
		} else {
			query = getEntityManager().createQuery("select distinct t.sgin from Telecoms t, Individu i where i.sgin = t.sgin and i.sgin is not null and t.sgin is not null and i.type = :type and (t.snorm_inter_phone_number = :phoneNumber or t.snorm_nat_phone_number_clean = :phoneNumber) and (t.sstatut_medium = 'V' or t.sstatut_medium = 'T')");
			query.setParameter("type",populationTargeted);
		}
		query.setParameter("phoneNumber",phoneNumber);
		
		@SuppressWarnings("unchecked")
		List<String> listGin = query.getResultList();
		if (listGin.size()>0) {
			result = new ArrayList<Individu>();
		}else{
			 throw new JrafDaoException("No individus found");
		}
		
		for (String sGin : listGin) {
			Individu individu = individuRepository.findBySgin(sGin);
			// REPIND-651 : Do not catch X (deleted) individual
			if (individu != null && individu.getStatutIndividu() != null && !"X".equals(individu.getStatutIndividu())) {
				result.add(individu);
			}
		}
		return result;
	}

	// REPIND-1264 : Add Search for Social by Type and Identifier
	public List<Individu> creerListeIndividusSearchIdentificationOnly(String type, String identifier) throws JrafDaoException {
		
		log.info("creerListeIndividusSearchIdentificationOnly( " + type + ", " + identifier + ") ");

		// If we search by GIGYA, we must search on SocialNetworkData and ExternalIdentfier
		boolean searchByGigya = ("GIGYA_ID".equals(type));
		
		List<Individu> result = null;
		Query query = null;
		query = getEntityManager().createQuery("select distinct i.sgin from ExternalIdentifier ei, Individu i where ei.gin = i.sgin and i.sgin is not null and ei.gin is not null and ei.type = :type and ei.identifier = :identifier");
		query.setParameter("type", type);
		query.setParameter("identifier", identifier);
		
		@SuppressWarnings("unchecked")
		List<String> listGin = query.getResultList();
		if (listGin.size()>0) {
			result = new ArrayList<>();
		} else {
			if (!searchByGigya) {
				throw new JrafDaoException("No individus found");
			}
		}
		
		for (String sGin : listGin) {
			Individu individu = individuRepository.findBySgin(sGin);
			// REPIND-651 : Do not catch X (deleted) individual
			if (individu != null && individu.getStatutIndividu() != null && !"X".equals(individu.getStatutIndividu())) {
				result.add(individu);
			}
		}
		return result;
	}

	// REPIND-1264 : Add Search for Social by Type and Identifier
	public List<Individu> creerListeIndividusSearchIdentificationName(String type, String identifier, String nom, String prenom, String civilite, String typeRechNom, String typeRechPrenom, boolean launchNotFoundException) throws JrafDaoException {
		return creerListeIndividusSearchIdentificationName(type, identifier, nom, prenom, civilite, typeRechNom, typeRechPrenom, "A", launchNotFoundException);
	}

	public List<Individu> creerListeIndividusSearchIdentificationName(String type, String identifier, String nom, String prenom, String civilite, String typeRechNom, String typeRechPrenom, String populationTargeted, boolean launchNotFoundException) throws JrafDaoException {
		
		log.info("creerListeIndividusSearchIdentificationName( " + type + ", " + identifier + "," + nom + "," + prenom + ") ");

		// If we search by GIGYA, we must search on SocialNetworkData and ExternalIdentfier
		boolean searchByGigya = ("GIGYA_ID".equals(type));
		
		List<Individu> result = null;
		Query query = null;
		StringBuffer sQuery = new StringBuffer();
		Map<String,String> parameters = new HashMap<>();
		
		sQuery.append("select distinct i.sgin from ExternalIdentifier ei, Individu i where ei.gin = i.sgin and i.sgin is not null and ei.gin is not null and ");
		sQuery.append("ei.type = :type and ei.identifier = :identifier and ");
		
		// PRENOM ===========================
		if ("L".equalsIgnoreCase(typeRechPrenom)) {
			sQuery.append("i.prenom LIKE :prenom and ");			
			parameters.put("prenom", prenom + "%");
		} else if ("S".equals(typeRechPrenom)) {
			sQuery.append("i.prenom = :prenom and ");
			parameters.put("prenom", prenom);
		}

		// NOM ===========================
		if ("L".equalsIgnoreCase(typeRechNom)) {
			sQuery.append("i.nom LIKE :nom ");			
			parameters.put("nom", nom + "%");
		} else if ("S".equals(typeRechNom)) {
			sQuery.append("i.nom = :nom ");
			parameters.put("nom", nom);
		}
		
		query = getEntityManager().createQuery(sQuery.toString());
				
        //
        // Set query parameter values
        //
		query.setParameter("type", type);
		query.setParameter("identifier", identifier);
        for (Object key: parameters.keySet()) {
        	String name = (String) key;
        	Object value = parameters.get(name);
        	query.setParameter(name,value);
        }
		
		@SuppressWarnings("unchecked")
		List<String> listGin = query.getResultList();
		if (listGin.size()>0) {
			result = new ArrayList<>();
		} else {
			if (!searchByGigya) {
				if (launchNotFoundException) {							// We aleady have some previous result or not
					throw new JrafDaoException("No individus found");
				} else {
					result = new ArrayList<>();
				}
			}
		}
		
		for (String sGin : listGin) {
			Individu individu = individuRepository.findBySgin(sGin);
			// REPIND-651 : Do not catch X (deleted) individual
			if (individu != null && individu.getStatutIndividu() != null && !"X".equals(individu.getStatutIndividu())) {
				result.add(individu);
			}
		}
		
		
		// Let's search on SOCIAL NETWORK DATA
		if (searchByGigya) {

			// Init au cas ou...
			query = null;
			sQuery = new StringBuffer();
			parameters = new HashMap<>();
			
			sQuery.append("select distinct i.sgin from SocialNetworkData snd, Individu i, AccountData ad where snd.socialNetworkId = ad.socialNetworkId and ad.sgin = i.sgin and i.sgin is not null and "); 
			sQuery.append("ei.type = :type and ei.identifier = :identifier and "); 
			
			// PRENOM ===========================
			if ("L".equalsIgnoreCase(typeRechPrenom)) {
				sQuery.append("i.prenom LIKE :prenom and ");			
				parameters.put("prenom", prenom + "%");
			} else if ("S".equals(typeRechPrenom)) {
				sQuery.append("i.prenom = :prenom and ");
				parameters.put("prenom", prenom);
			}

			// NOM ===========================
			if ("L".equalsIgnoreCase(typeRechNom)) {
				sQuery.append("i.nom LIKE :nom ");			
				parameters.put("nom", nom + "%");
			} else if ("S".equals(typeRechNom)) {
				sQuery.append("i.nom = :nom ");
				parameters.put("nom", nom);
			}
			
			query = getEntityManager().createQuery(sQuery.toString());
					
	        //
	        // Set query parameter values
	        //
			query.setParameter("identifier", identifier);
	        for (Object key: parameters.keySet()) {
	        	String name = (String) key;
	        	Object value = parameters.get(name);
	        	query.setParameter(name,value);
	        }
			
			@SuppressWarnings("unchecked")
			List<String> listGinSocial = query.getResultList();
			
			// On envoi NOT FOUND si il n y avait rien dans ExternalIdentifieyr et Rien dans SocialNetSorkData
			if (listGinSocial.size() == 0 && (result == null || result.size() == 0)) {
				if (launchNotFoundException) {							// We aleady have some previous result or not 
					throw new JrafDaoException("No individus found");
				} else {
					result = new ArrayList<>();
				}
			} else {
				for (String sGin : listGinSocial) {
					Individu individu = individuRepository.findBySgin(sGin);
					// REPIND-651 : Do not catch X (deleted) individual
					if (individu != null && individu.getStatutIndividu() != null && !"X".equals(individu.getStatutIndividu())) {					
						// Si on a pas trouve d'elements dans ExternalIdentifier, on cree la nouvelle liste
						if (result == null) {							
							result = new ArrayList<>();
						}
						result.add(individu);
					}
				}
			}
		}
		return result;
	}
	
	/**
	 * Trim and return null if string is empty
	 * @param str
	 * @return
	 */	
	protected String cleanString(String str) {
		String result = null;
		if (str!=null) {
			str=str.trim();
			if (str.length()>0) {
				result = str;
			}
		}
		return result;
	}
    /**
     * @return EntityManager
     */ 
    public EntityManager getEntityManager() {
        return entityManager;
    }

    /**
     *  @param entityManager EntityManager
     */
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Getter
     * @return IAdresseDS
     */
    public AdresseDS getAdresseDS() {
        return adresseDS;
    }

    /**
     * Setter
     * @param adresseDS the IAdresseDS 
     */
    public void setAdresseDS(AdresseDS adresseDS) {
        this.adresseDS = adresseDS;
    }
    
    /**
     * Getter
     * @return IMyAccountUS
     */
    public MyAccountUS getMyAccountUS() {
        return myAccountUS;
    }

    /**
     * Setter
     * @param myAccountUS the IMyAccountUS 
     */
    public void setMyAccountUS(MyAccountUS myAccountUS) {
        this.myAccountUS = myAccountUS;
    }
    
    public String formatDate(Date date){
    	DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    	String reportDate = df.format(date);
		return reportDate;
    }
    
    public List<Individu> creerListeIndividusSearchPhone(String nom, String prenom, String civilite, String telephone, String typeRechNom, String typeRechPrenom, String typeRechTelephone, Date dateNaissance, boolean returnDeadIndividual) throws JrafDaoException {
    	return creerListeIndividusSearchPhone(nom, prenom, civilite, telephone, typeRechNom, typeRechPrenom, typeRechTelephone, dateNaissance, "A", returnDeadIndividual);
    }
    
@SuppressWarnings({ "unchecked", "rawtypes" })
public List<Individu> creerListeIndividusSearchPhone(String nom, String prenom, String civilite, String telephone, String typeRechNom, String typeRechPrenom, String typeRechTelephone, Date dateNaissance, String populationTargeted, boolean returnDeadIndividual) throws JrafDaoException {
		
		log.info("creerListeIndividusSearchPhone("+nom+","+prenom+","+civilite+","+dateNaissance+","+telephone+","+typeRechNom+","+typeRechPrenom+","+typeRechTelephone+","+populationTargeted);
		
		List<Individu> result = new ArrayList<Individu>();
        try
        {
        	
        	if (nom == null && prenom == null) throw new JrafDaoException(_REF_133);
        	Map parameters = new HashMap();
            // (1) Construction de la REQUETE
            // Correction des nom et prénom contenant une apostrophe
        	if (nom != null) nom = nom.replaceAll("'", "''").toUpperCase();
        	if (prenom != null) prenom = prenom.replaceAll("'", "''").toUpperCase();

        	StringBuilder sSelect = new StringBuilder("SELECT I ");
            StringBuilder sFrom = new StringBuilder(" FROM Individu as I ");
            
            //recherche par telephone:
            if(telephone != null)
            	sFrom.append("left join I.telecoms as T ");
            	
            StringBuilder sWhere = new StringBuilder(" WHERE ");

            // NOM ===========================
            if("L".equalsIgnoreCase(typeRechNom))
            {
                sWhere.append(" I.nom LIKE ").append(":nom").append(" ");
                parameters.put("nom",nom + "%");
            } else {                                      /* typeRechNom=="S" */
                sWhere.append(" I.nom = ").append(":nom").append(" ");
                parameters.put("nom",nom);
            }

            // PRENOM ===========================
            if ("L".equalsIgnoreCase(typeRechPrenom)) {
                sWhere.append(" AND I.prenom LIKE ").append(":prenom").append(" ");
                parameters.put("prenom",prenom + "%");
            } else {
                sWhere.append(" AND I.prenom = ").append(":prenom").append(" ");
                parameters.put("prenom",prenom);
            }

            // CIVILITE ===========================
            if ("MR".equalsIgnoreCase(civilite))
            	sWhere.append(" AND I.civilite IN ('MR','M.') ");
            else if ("MS".equalsIgnoreCase(civilite))
            	sWhere.append(" AND I.civilite IN ('MS','MISS','MRS','M.') ");
            else if ("MISS".equalsIgnoreCase(civilite))
            	sWhere.append(" AND I.civilite IN ('MS','MISS','MRS','M.') ");
            else if ("MRS".equalsIgnoreCase(civilite))
            	sWhere.append(" AND I.civilite IN ('MS','MISS','MRS','M.') ");
            else if ("M.".equalsIgnoreCase(civilite))
            	sWhere.append(" AND I.civilite IN ('MR','MS','MISS','MRS','M.') ");
            else if (civilite != null) {
                sWhere.append(" AND I.civilite =").append(":civilite").append(" ");
                parameters.put("civilite",civilite);
            }

            // STATUT INDIVIDU ===========================
            //REPIND-1673: We return Dead Individual
             if (returnDeadIndividual) {
             	sWhere.append(" AND I.statutIndividu IN ('V','P','D') ");
             } else {
             	sWhere.append(" AND I.statutIndividu IN ('V','P') ");
             }

            // DATE NAISSANCE ===================
            if (dateNaissance != null)
            {
            	sWhere.append(" AND ((trunc(I.dateNaissance) = trunc(:dateNaissance)) OR I.dateNaissance = null)");
                parameters.put("dateNaissance",dateNaissance);
            }
            
            // TELECOM ===================
            if(telephone != null){
            	sWhere.append(" AND T.sstatut_medium IN ('V','T') ");
            	if ("L".equals(typeRechTelephone)) {
                	sWhere.append(" AND T.snorm_nat_phone_number_clean LIKE ").append(":telephone").append(" ");
                	parameters.put("telephone",telephone + "%");
            	} else {
                	sWhere.append(" AND (T.snorm_nat_phone_number_clean=").append(":telephone").append(" ");
                	sWhere.append(" OR T.snorm_inter_phone_number=").append(":telephone").append(" )");
                	parameters.put("telephone",telephone);
            	}
            }

            // POPULATION TARGETED ===========================
            if (populationTargeted != null && !"".equals(populationTargeted) && !"A".equals(populationTargeted)) {
            	sWhere.append(" AND I.type = :type ");
            	parameters.put("type",populationTargeted);
            }
            
            String sStmt = sSelect.append(sFrom).append(sWhere).toString();
            log.debug(sStmt);
            
            Query query = getEntityManager().createQuery(sStmt);
            //
            // Set query parameter values
            //
            for (Object key: parameters.keySet()) {

            	String name = (String) key;
            	Object value = parameters.get(name);
            	query.setParameter(name,value);
            }
            
            result = query.getResultList();

            return result;
        }

        catch(Exception e)
        {
            // Gestion des erreurs applicatives
            throw new JrafDaoException(e);
        }
    	
    }

// Appel par defaut sur tout INDIVIDUS
public List<Individu> creerListeIndividusSearchEmail(String nom, String prenom, String civilite, String typeRechNom, String typeRechPrenom, String email, Date dateNaissance, boolean returnDeadIndividual) throws JrafDaoException {

	return creerListeIndividusSearchEmail(nom, prenom, civilite, typeRechNom, typeRechPrenom, email, dateNaissance, "A", returnDeadIndividual);
}


@SuppressWarnings({ "rawtypes", "unchecked" })
public List<Individu> creerListeIndividusSearchEmail(String nom, String prenom, String civilite, String typeRechNom, String typeRechPrenom, String email, Date dateNaissance, String populationTargeted, boolean returnDeadIndividual) throws JrafDaoException {
	
	log.info("creerListeIndividusSearchEmail("+nom+","+prenom+","+civilite+","+dateNaissance+","+typeRechNom+","+typeRechPrenom+","+email+","+populationTargeted);
	
	List<Individu> result = new ArrayList<Individu>();
    try
    {
    	
    	if (nom == null && prenom == null) throw new JrafDaoException(_REF_133);
    	Map parameters = new HashMap();
        // (1) Construction de la REQUETE
        // Correction des nom et prénom contenant une apostrophe
    	if (nom != null) nom = nom.replaceAll("'", "''").toUpperCase();
    	if (prenom != null) prenom = prenom.replaceAll("'", "''").toUpperCase();

    	StringBuilder sSelect = new StringBuilder("SELECT I ");
        StringBuilder sFrom = new StringBuilder(" FROM Individu as I ");
        
        // ajout de la recherche par email
        if(email != null)
        	sFrom.append("left join I.email as E ");
        	
        StringBuilder sWhere = new StringBuilder(" WHERE ");
        StringBuilder sWhere_email = new StringBuilder();

        // NOM ===========================
        if("L".equalsIgnoreCase(typeRechNom))
        {
            // sWhere.append(" I.nom LIKE '").append(nom).append("%' ");
            sWhere.append(" I.nom LIKE ").append(":nom").append(" ");
            parameters.put("nom",nom + "%");
        }
        else                                      /* typeRechNom=="S" */
        {
            sWhere.append(" I.nom = ").append(":nom").append(" ");
            parameters.put("nom",nom);
        }

        // PRENOM ===========================
			if ("L".equalsIgnoreCase(typeRechPrenom)) {
				sWhere.append(" AND I.prenom LIKE ").append(":prenom").append(" ");
				parameters.put("prenom", prenom+"%");
			} else {
				sWhere.append(" AND I.prenom = ").append(":prenom").append(" ");
				parameters.put("prenom", prenom);
			}

        // CIVILITE ===========================
        if ("MR".equalsIgnoreCase(civilite))
        	sWhere.append(" AND I.civilite IN ('MR','M.') ");
        else if ("MS".equalsIgnoreCase(civilite))
        	sWhere.append(" AND I.civilite IN ('MS','MISS','MRS','M.') ");
        else if ("MISS".equalsIgnoreCase(civilite))
        	sWhere.append(" AND I.civilite IN ('MS','MISS','MRS','M.') ");
        else if ("MRS".equalsIgnoreCase(civilite))
        	sWhere.append(" AND I.civilite IN ('MS','MISS','MRS','M.') ");
        else if ("M.".equalsIgnoreCase(civilite))
        	sWhere.append(" AND I.civilite IN ('MR','MS','MISS','MRS','M.') ");
        else if(civilite != null) {
            sWhere.append(" AND I.civilite =").append(":civilite").append(" ");
            parameters.put("civilite",civilite);
        }

        // STATUT INDIVIDU ===========================
        //REPIND-1673: We return Dead Individual
        if (returnDeadIndividual) {
        	sWhere.append(" AND I.statutIndividu IN ('V','P','D') ");
        } else {
        	sWhere.append(" AND I.statutIndividu IN ('V','P') ");
        }

        // DATE NAISSANCE ===================
        if (dateNaissance != null)
        {
        	sWhere.append(" AND ((trunc(I.dateNaissance) = trunc(:dateNaissance)) OR I.dateNaissance = null)");
            parameters.put("dateNaissance",dateNaissance);
        }
        
        // Email ===================================
        if(email != null)
        {
        	sWhere_email.append(" AND E.statutMedium <> 'X' ");
        	sWhere_email.append(" AND (E.email=").append(":email").append(" ");
        	// REPIND-1288 : Compare Lower to Lower in DB if not found on exact. Impact on Index ? No impact because not the only search parameter 
        	sWhere_email.append(" OR LOWER(E.email)=").append(":email").append(") ");
        	parameters.put("email",email);
        }

        // POPULATION TARGETED ===========================
        if (populationTargeted != null && !"".equals(populationTargeted) && !"A".equals(populationTargeted)) {
        	sWhere.append(" AND I.type = :type ");
        	parameters.put("type",populationTargeted);
        }
        
        String sStmt = sSelect.append(sFrom).append(sWhere).append(sWhere_email).toString();
        log.debug(sStmt);
        
        Query query = getEntityManager().createQuery(sStmt);
        //
        // Set query parameter values
        //
        for (Object key: parameters.keySet()) {

        	String name = (String) key;
        	Object value = parameters.get(name);
        	query.setParameter(name,value);
        }
        
        result = query.getResultList();
        
        return result;
    }

    catch(Exception e)
    {
        // Gestion des erreurs applicatives
        throw new JrafDaoException(e);
    }
	
}

@SuppressWarnings({ "rawtypes", "unchecked" })
public List<Individu> creerListeIndividusSearchName(String nom, String prenom, String civilite, String typeRechNom, String typeRechPrenom, String populationTargeted, boolean returnDeadIndividual) throws JrafDaoException {
	
	log.info("creerListeIndividusSearchName("+nom+","+prenom+","+civilite+","+typeRechNom+","+typeRechPrenom+","+populationTargeted);
	
	List<Individu> result = new ArrayList<>();
    try
    {
    	
    	if (nom == null && prenom == null) throw new JrafDaoException(_REF_133);
    	Map parameters = new HashMap();
        // (1) Construction de la REQUETE
        // Correction des nom et prénom contenant une apostrophe
    	if (nom != null) nom = nom.replaceAll("'", "''").toUpperCase();
    	if (prenom != null) prenom = prenom.replaceAll("'", "''").toUpperCase();

    	StringBuilder sSelect = new StringBuilder("SELECT I ");
        StringBuilder sFrom = new StringBuilder(" FROM Individu as I ");
        
        StringBuilder sWhere = new StringBuilder(" WHERE ");
        StringBuilder sWhere_email = new StringBuilder();

        // NOM ===========================
        if("L".equalsIgnoreCase(typeRechNom))
        {
            // sWhere.append(" I.nom LIKE '").append(nom).append("%' ");
            sWhere.append(" I.nom LIKE ").append(":nom").append(" ");
            parameters.put("nom",nom + "%");
        }
        else                                      /* typeRechNom=="S" */
        {
            sWhere.append(" I.nom = ").append(":nom").append(" ");
            parameters.put("nom",nom);
        }

        // PRENOM ===========================
			if ("L".equalsIgnoreCase(typeRechPrenom)) {
				sWhere.append(" AND I.prenom LIKE ").append(":prenom").append(" ");
				parameters.put("prenom", prenom+"%");
			} else {
				sWhere.append(" AND I.prenom = ").append(":prenom").append(" ");
				parameters.put("prenom", prenom);
			}

        // CIVILITE ===========================
        if ("MR".equalsIgnoreCase(civilite))
        	sWhere.append(" AND I.civilite IN ('MR','M.') ");
        else if ("MS".equalsIgnoreCase(civilite))
        	sWhere.append(" AND I.civilite IN ('MS','MISS','MRS','M.') ");
        else if ("MISS".equalsIgnoreCase(civilite))
        	sWhere.append(" AND I.civilite IN ('MS','MISS','MRS','M.') ");
        else if ("MRS".equalsIgnoreCase(civilite))
        	sWhere.append(" AND I.civilite IN ('MS','MISS','MRS','M.') ");
        else if ("M.".equalsIgnoreCase(civilite))
        	sWhere.append(" AND I.civilite IN ('MR','MS','MISS','MRS','M.') ");
        else if(civilite != null) {
            sWhere.append(" AND I.civilite =").append(":civilite").append(" ");
            parameters.put("civilite",civilite);
        }

        // STATUT INDIVIDU ===========================
        //REPIND-1673: We return Dead Individual
        if (returnDeadIndividual) {
        	sWhere.append(" AND I.statutIndividu IN ('V','P','D') ");
        } else {
        	sWhere.append(" AND I.statutIndividu IN ('V','P') ");
        }

        // POPULATION TARGETED ===========================
        if (populationTargeted != null && !"".equals(populationTargeted) && !"A".equals(populationTargeted)) {
        	sWhere.append(" AND I.type = :type ");
        	parameters.put("type",populationTargeted);
        }
        
        String sStmt = sSelect.append(sFrom).append(sWhere).append(sWhere_email).toString();
        log.debug(sStmt);
        
        Query query = getEntityManager().createQuery(sStmt);
        //
        // Set query parameter values
        //
        for (Object key: parameters.keySet()) {

        	String name = (String) key;
        	Object value = parameters.get(name);
        	query.setParameter(name,value);
        }
        
        result = query.getResultList();
        
        return result;
    }

    catch(Exception e)
    {
        // Gestion des erreurs applicatives
        throw new JrafDaoException(e);
    }
	
}

public List<Individu> creerListeIndividusSearchDate(String nom, String prenom, String civilite, Date dateNaissance, String typeRechNom, String typeRechPrenom, boolean returnDeadIndividual) throws JrafDaoException {
	return creerListeIndividusSearchDate(nom, prenom, civilite, dateNaissance, typeRechNom, typeRechPrenom, "A", returnDeadIndividual);
}

@SuppressWarnings({ "unchecked", "rawtypes" })
public List<Individu> creerListeIndividusSearchDate(String nom, String prenom, String civilite, Date dateNaissance, String typeRechNom, String typeRechPrenom, String populationTargeted, boolean returnDeadIndividual) throws JrafDaoException {
	
	log.info("creerListeIndividusSearchDate("+nom+","+prenom+","+civilite+","+dateNaissance+","+typeRechNom+","+typeRechPrenom+","+populationTargeted);
	
	List<Individu> result = new ArrayList<Individu>();
    try	
    {
    	
    	if (nom == null && prenom == null) throw new JrafDaoException(_REF_133);
    	Map parameters = new HashMap();
        // (1) Construction de la REQUETE
        // Correction des nom et prénom contenant une apostrophe
    	if (nom != null) nom = nom.replaceAll("'", "''").toUpperCase();
    	if (prenom != null) prenom = prenom.replaceAll("'", "''").toUpperCase();

    	StringBuilder sSelect = new StringBuilder("SELECT I ");
        StringBuilder sFrom = new StringBuilder(" FROM Individu as I ");

        StringBuilder sWhere = new StringBuilder(" WHERE ");

        // NOM ===========================
        if("L".equalsIgnoreCase(typeRechNom))
        {
            sWhere.append(" I.nom LIKE ").append(":nom").append(" ");
            parameters.put("nom",nom+"%");
        }
        else                                      /* typeRechNom=="S" */
        {
            sWhere.append(" I.nom = ").append(":nom").append(" ");
            parameters.put("nom",nom);
        }

        // PRENOM ===========================
        if ("L".equalsIgnoreCase(typeRechPrenom)) {
            sWhere.append(" AND I.prenom LIKE ").append(":prenom").append(" ");
            parameters.put("prenom", prenom+"%");
        } else {
            sWhere.append(" AND I.prenom = ").append(":prenom").append(" ");
            parameters.put("prenom", prenom);
        }
        // CIVILITE ===========================
			if ("MR".equalsIgnoreCase(civilite)) {
				sWhere.append(" AND I.civilite IN ('MR','M.') ");
			} else if ("MS".equalsIgnoreCase(civilite)) {
				sWhere.append(" AND I.civilite IN ('MS','MISS','MRS','M.') ");
			} else if ("MISS".equalsIgnoreCase(civilite)) {
				sWhere.append(" AND I.civilite IN ('MS','MISS','MRS','M.') ");
			} else if ("MRS".equalsIgnoreCase(civilite)) {
				sWhere.append(" AND I.civilite IN ('MS','MISS','MRS','M.') ");
			} else if ("M.".equalsIgnoreCase(civilite)) {
				sWhere.append(" AND I.civilite IN ('MR','MS','MISS','MRS','M.') ");
			} else if (civilite != null) {
				sWhere.append(" AND I.civilite =").append(":civilite").append(" ");
				parameters.put("civilite", civilite);
			}

			// STATUT INDIVIDU ===========================
		    //REPIND-1673: We return Dead Individual
            if (returnDeadIndividual) {
            	sWhere.append(" AND I.statutIndividu IN ('V','P','D') ");
            } else {
            	sWhere.append(" AND I.statutIndividu IN ('V','P') ");
            }

        // DATE NAISSANCE ===================
        if (dateNaissance != null)
        {
        	sWhere.append(" AND trunc(I.dateNaissance) = trunc(:dateNaissance) ");
            parameters.put("dateNaissance",dateNaissance);
        }

        // POPULATION TARGETED ===========================
        if (populationTargeted != null && !"".equals(populationTargeted) && !"A".equals(populationTargeted)) {
        	sWhere.append(" AND I.type = :type ");
        	parameters.put("type",populationTargeted);
        }
        
        String sStmt = sSelect.append(sFrom).append(sWhere).toString();
        log.debug(sStmt);
        
        Query query = getEntityManager().createQuery(sStmt);
        //
        // Set query parameter values
        //
        for (Object key: parameters.keySet()) {

        	String name = (String) key;
        	Object value = parameters.get(name);
        	query.setParameter(name,value);
        }
        
        result = query.getResultList();

        return result;
    }

    catch(Exception e)
    {
        // Gestion des erreurs applicatives
        throw new JrafDaoException(e);
    }
	
}

public List<Individu> creerListeIndividusSearchAddress (String nom, String prenom, String civilite, String pays, String noRue, String compltAdresse, String localite, String codePostal, String ville, String codeProvince, String typeRechNom, String typeRechPrenom, String typeRechCodePostal, String typeRechVille, Date dateNaissance, boolean returnDeadIndividual) throws JrafDaoException {
	return creerListeIndividusSearchAddress (nom, prenom, civilite, pays, noRue, compltAdresse, localite, codePostal, ville, codeProvince, typeRechNom, typeRechPrenom, typeRechCodePostal, typeRechVille, dateNaissance, "A", returnDeadIndividual);
}

@SuppressWarnings({ "rawtypes", "unchecked" })
public List<Individu> creerListeIndividusSearchAddress (String nom, String prenom, String civilite, String pays, String noRue, String compltAdresse, String localite, String codePostal, String ville, String codeProvince, String typeRechNom, String typeRechPrenom, String typeRechCodePostal, String typeRechVille, Date dateNaissance, String populationTargeted, boolean returnDeadIndividual) throws JrafDaoException
{
	nom = cleanString(nom);
	prenom = cleanString(prenom);
	civilite = cleanString(civilite);
	pays = cleanString(pays);
	noRue = cleanString(noRue);
	compltAdresse = cleanString(compltAdresse);
	localite = cleanString(localite);
	codePostal = cleanString(codePostal);
	ville = cleanString(ville);
	codeProvince = cleanString(codeProvince);
	if (nom == null && prenom == null) throw new JrafDaoException(_REF_133);
	List<Individu> result = new ArrayList<>();
    try
    {
    	Map parameters = new HashMap();
        // Correction des nom et prenom contenant une apostrophe
        String sNom = null;
        if (nom != null) sNom = nom.replaceAll("'", "''").toUpperCase();
        String sPrenom = null;
        if (nom != null) sPrenom = prenom.replaceAll("'", "''").toUpperCase();

        StringBuilder sSelectIndividu = new StringBuilder("SELECT I");
        StringBuilder sFromIndividu = new StringBuilder(" FROM Individu I ");

        StringBuilder sSelectIndividuAdrPost = new StringBuilder("SELECT I");
        StringBuilder sFromIndividuAdrPost = new StringBuilder(" FROM Individu I left join I.postaladdress A ");

        StringBuilder sWhereCriteresIndividu = new StringBuilder(" WHERE ");
        StringBuilder sWhereCriteresAdrPost = new StringBuilder();

        StringBuilder sOrderBy = new StringBuilder();

        // NOM ===========================
        if("L".equalsIgnoreCase(typeRechNom))
        {
            sWhereCriteresIndividu.append(" I.nom LIKE ").append(":sNom").append(" ");
            parameters.put("sNom",sNom + "%");
        }
        else                                      /* typeRechNom=="S" */
        {
            sWhereCriteresIndividu.append(" I.nom = ").append(":sNom").append(" ");
            parameters.put("sNom",sNom);
        }

        // PRENOM ===========================
        if ("L".equalsIgnoreCase(typeRechPrenom)) {
            sWhereCriteresIndividu.append(" AND I.prenom LIKE ").append(":sPrenom").append(" ");
            parameters.put("sPrenom",sPrenom + "%");
        } else {
            sWhereCriteresIndividu.append(" AND I.prenom = ").append(":sPrenom").append(" ");
            parameters.put("sPrenom",sPrenom);
        }

        // DL : Mise en place d'un tri alphabetique sur nom, prenom dans le cas d'une recherche like ou phonetique
        sOrderBy.append(" ORDER BY ");
        if ((!"S".equalsIgnoreCase(typeRechNom)) || (!"S".equalsIgnoreCase(typeRechPrenom)))
        {
            if (!"S".equalsIgnoreCase(typeRechNom))
            {
                sOrderBy.append("I.nom");
            }
            if (!"S".equalsIgnoreCase(typeRechPrenom))
            {
            	if (!"S".equalsIgnoreCase(typeRechNom)) 
                	sOrderBy.append(", ");
            	// REPIND-1247 : Sort data in order to prevent a erratic send for more than MAX individual found
            	// REPIND-1320 : RollBack - Use ROWID instead of an indexed SGIN
            	// In case of 100 limits line return, we always want the same result
                sOrderBy.append("I.prenom ");
            }
        }
        else
        {
            sOrderBy.append(" I.dateNaissance ASC");
        }

        // CIVILITE ===========================
        if ("MR".equalsIgnoreCase(civilite))
            sWhereCriteresIndividu.append(" AND I.civilite IN ('MR','M.') ");
        else if ("MS".equalsIgnoreCase(civilite))
            sWhereCriteresIndividu.append(" AND I.civilite IN ('MS','MISS','MRS','M.') ");
        else if ("MISS".equalsIgnoreCase(civilite))
            sWhereCriteresIndividu.append(" AND I.civilite IN ('MS','MISS','MRS','M.') ");
        else if ("MRS".equalsIgnoreCase(civilite))
            sWhereCriteresIndividu.append(" AND I.civilite IN ('MS','MISS','MRS','M.') ");
        else if ("M.".equalsIgnoreCase(civilite))
            sWhereCriteresIndividu.append(" AND I.civilite IN ('MR','MS','MISS','MRS','M.') ");
        else if (civilite != null) {
            sWhereCriteresIndividu.append(" AND I.civilite =").append(":civilite").append(" ");
            parameters.put("civilite",civilite);
        }

        // STATUT INDIVIDU ===========================
        //REPIND-1673: We return Dead Individual
        if (returnDeadIndividual) {
        	sWhereCriteresIndividu.append(" AND I.statutIndividu IN ('V','P','D') ");
        } else {
        	sWhereCriteresIndividu.append(" AND I.statutIndividu IN ('V','P') ");
        }

     // DATE NAISSANCE ===================
        if (dateNaissance != null)
        {
        	sWhereCriteresIndividu.append(" AND ((trunc(I.dateNaissance) = trunc(:dateNaissance)) OR I.dateNaissance = null)");
            parameters.put("dateNaissance",dateNaissance);
        }
        
        if (pays != null)
        {
            // le code pays est renseinge : les criteres lies a l'adresse postale sont a prendre en compte
            // ADRESSE POSTALE ===================
            sWhereCriteresAdrPost.append(" AND A.sstatut_medium IN ('V','T','I')");
            
            sWhereCriteresAdrPost.append(" AND A.scode_pays =").append(":pays").append(" ");
            parameters.put("pays",pays);


            // N° ET RUE ===================
            if (noRue != null)
            {
                String s_NoRue = noRue.replaceAll("'", " ");
                sWhereCriteresAdrPost.append(" AND A.sno_et_rue =").append(":sNoRue").append(" ");
                parameters.put("sNoRue",s_NoRue);
            }

            // COMPLEMENT D'ADRESSE =============
            if (compltAdresse != null)
            {
                String s_CmpltAdr = compltAdresse.replaceAll("'", " ");
                sWhereCriteresAdrPost.append(" AND A.scomplement_adresse =").append(":sCmpltAdr").append(" ");
                parameters.put("sCmpltAdr",s_CmpltAdr);
            }

            // LOCALITE =============
            if (localite != null)
            {
                String s_Localite = localite.replaceAll("'", " ");
                sWhereCriteresAdrPost.append(" AND A.slocalite =").append(":sLocalite").append(" ");
                parameters.put("sLocalite",s_Localite);
            }

            // VILLE ============================
            if (ville != null)
            {
                String s_Ville = ville.replaceAll("'", " ");
                if ("L".equalsIgnoreCase(typeRechVille))
                {
                    sWhereCriteresAdrPost.append(" AND A.sville LIKE ").append(":sVille").append(" ");
                    parameters.put("sVille",s_Ville + "%");
                }
                else
                {
                    sWhereCriteresAdrPost.append(" AND A.sville=").append(":sVille").append(" ");
                    parameters.put("sVille",s_Ville);
                }
            }

            // CODE POSTAL =======================
            if (codePostal != null)
            {
                if ("L".equalsIgnoreCase(typeRechCodePostal))
                {
                    sWhereCriteresAdrPost.append(" AND A.scode_postal LIKE ").append(":codePostal").append(" ");
                    parameters.put("codePostal",codePostal + "%");
                }
                else
                {
                    sWhereCriteresAdrPost.append(" AND A.scode_postal =").append(":codePostal").append(" ");
                    parameters.put("codePostal",codePostal);
                }
            }

            // CODE PROVINCE ===================
            if (codeProvince != null)
            {
                sWhereCriteresAdrPost.append(" AND A.scode_province =").append(":codeProvince").append(" ");
                parameters.put("codeProvince",codeProvince);
            }
        }

        // POPULATION TARGETED ===========================
        if (populationTargeted != null && !"".equals(populationTargeted) && !"A".equals(populationTargeted)) {
        	sWhereCriteresIndividu.append(" AND I.type = :type ");
        	parameters.put("type",populationTargeted);
        }            
        
        String sStmt;
        if (pays == null)
        {
            // aucun critere lie a l'adresse postale n'est a prendre en compte
            sStmt = sSelectIndividu.append(sFromIndividu)
            					.append(sWhereCriteresIndividu).toString();
        }
        else
        {
            // les individus doivent avoir une adresse associee dans le RI
            sStmt = sSelectIndividuAdrPost.append(sFromIndividuAdrPost)
            							.append(sWhereCriteresIndividu)
            							.append(sWhereCriteresAdrPost).toString();
        }
        if (sOrderBy.length() > 0)
            sStmt += sOrderBy.toString();

        // Test d'affichage de la clause where
			log.debug("Clause sStmt=" + sStmt + "!!!");
        
        Query query = getEntityManager().createQuery(sStmt);
        //
        // Set query parameter values
        //
        for (Object key: parameters.keySet()) {
        	String name = (String) key;
        	Object value = parameters.get(name);
        	query.setParameter(name,value);
        }
        
        result = query.getResultList();

        return result;
    }

    catch(Exception e)
    {
        // Gestion des erreurs applicatives
        throw new JrafDaoException(e);
    }

}

    /*
     * (non-Javadoc)
     * @see com.airfrance.repind.service.internal.unitservice.individu.IIndividuUS#findGinByIdentifier(java.lang.String, java.lang.String)
     */
    public String findGinByIdentifier(String pIdentifierType, String pIdentifierValue) throws JrafDomainException {
        
        Assert.notNull(pIdentifierType);
        Assert.notNull(pIdentifierValue);
        
        log.info("Call findGinByIdentifier(" + pIdentifierType + "," + pIdentifierValue +")");
                
        String gin = null;
        
        IdentifierOptionTypeEnum identifierType = IdentifierOptionTypeEnum.getEnumMandatory(pIdentifierType);
        switch (identifierType) {

        case FLYING_BLUE:
        case SUSCRIBER:
        case AMEX:
        case SAPHIR:            
        case ALL_CONTRACTS:

            List<Map<String,?>> properties = roleContratsRepository.findSimplePropertiesByNumero(pIdentifierValue);
            if (properties.size() == 1) {
                
                gin = (String) properties.get(0).get("gin");
                
            } else if (properties.size() == 0) {

                String fbNumber = StringUtils.leftPad(pIdentifierValue, 12, "0");
                properties = roleContratsRepository.findSimplePropertiesByNumero(fbNumber);
                if (properties.size() == 1) {
                    
                    gin = (String) properties.get(0).get("gin");
                }
            }
            break;

        case ANY_MYACCOUNT:
            
            ProvideGinForUserIdRequestDTO requestMyAccountDatas = new ProvideGinForUserIdRequestDTO();
            requestMyAccountDatas.setIdentifier(pIdentifierValue);
            requestMyAccountDatas.setIdentifierType("MA");
            
            ProvideGinForUserIdResponseDTO responseForMyAccountDatas = myAccountUS.provideGinForUserId(requestMyAccountDatas);
            if (responseForMyAccountDatas != null) {
                
                gin = responseForMyAccountDatas.getGin();
            }
            break;

        case GIN:
            
            gin = pIdentifierValue;
            break;
            
        default:
            break;
        }

        log.info("findGinByIdentifier returns " + gin);
        
        return gin;
    }

	// REPIND-394 : SearchIndividualByMulticriteria : Retour "Not Found" quand les critères ne sont pas suffisants
    // Bug Release V44 - La date de Naissance est obligatoire et pas seulement le bloc contact
    private void controleNomPrenomContactDateDeNaissanceIdentification(IdentityDTO identity, ContactDTO contact, IdentificationDTO identification) throws JrafDaoException, MissingParameterException {
    	
		// controle la presence du nom et du prenom a minima
		if (identity.getLastName()==null || identity.getFirstName()==null)  
		{
			throw new MissingParameterException("Lastname or Firstname");
		} else {
			// Nom et Prenom non null
			if (
				// REPIND-394 : SearchIndividualByMulticriteria : Retour "Not Found" quand les critères ne sont pas suffisants
				// REPIND-417 : Reception d'un bloc non null mais qui est vide
				(
					(contact == null && identity == null) || (														// contact null ou alors
					contact != null &&
					(contact.getEmail() == null || "".equals(contact.getEmail())) &&			// email null ou vide et
					(contact.getPhoneNumber() == null || "".equals(contact.getPhoneNumber()
				)) &&																			// telecom null ou vide et
					(contact.getPostalAddressBloc() == null ||
				(	// Element de l'Adresse postale null ou vide
					(contact.getPostalAddressBloc().getAdditionalInformation() == null || "".equals(contact.getPostalAddressBloc().getAdditionalInformation())) &&
					(contact.getPostalAddressBloc().getCity() == null || "".equals(contact.getPostalAddressBloc().getCity())) &&
					(contact.getPostalAddressBloc().getCountryCode() == null || "".equals(contact.getPostalAddressBloc().getCountryCode())) &&
					(contact.getPostalAddressBloc().getNumberAndStreet() == null || "".equals(contact.getPostalAddressBloc().getNumberAndStreet())) &&
					(contact.getPostalAddressBloc().getZipCode() == null || "".equals(contact.getPostalAddressBloc().getZipCode())) 
					)	// addr post null ou vide
				)) &&
					identity != null &&									// Date de naissance null
					identity.getBirthday() == null						
				) &&
					// REPIND
					(identification == null || (							// Social ID null
					identification != null &&							
					(identification.getIdentificationType() == null || "".equals(identification.getIdentificationType())) &&
					(identification.getIdentificationValue() == null || "".equals(identification.getIdentificationValue()))
					))
				) {
			} else {
				// Nom et prenom Non Null et Contact ou Birthdate NON NULL
			}
		}
    }

    /*PROTECTED REGION END*/
}
