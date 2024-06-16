package com.airfrance.repind.dto.profil;

/*PROTECTED REGION ID(_6dhK8LdeEeCrCZp8iGNNVw i - Tr) ENABLED START*/

// add not generated imports here

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.entity.profil.ProfilFirme;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/*PROTECTED REGION END*/

/**
 * <p>Title : ProfilFirmeTransform.java</p>
 * transformation bo <-> dto pour un(e) ProfilFirme
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class ProfilFirmeTransform {

    /*PROTECTED REGION ID(_6dhK8LdeEeCrCZp8iGNNVw u var - Tr) ENABLED START*/
    // add your custom variables here if necessary
	
	private static Log LOGGER  = LogFactory.getLog(ProfilFirmeTransform.class);
	
    /*PROTECTED REGION END*/
    
    /**
     * private constructor
     */
    private ProfilFirmeTransform() {
    }
    /**
     * dto -> bo for a ProfilFirme
     * @param profilFirmeDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static ProfilFirme dto2BoLight(ProfilFirmeDTO profilFirmeDTO) throws JrafDomainException {
        // instanciation du BO
        ProfilFirme profilFirme = new ProfilFirme();
        dto2BoLight(profilFirmeDTO, profilFirme);

        // on retourne le BO
        return profilFirme;
    }
    
    /**
     * dto -> bo for a ProfilFirme
     * @param profilFirmeDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static ProfilFirme dto2Bo(ProfilFirmeDTO profilFirmeDTO) throws JrafDomainException {
        // instanciation du BO
        ProfilFirme profilFirme = new ProfilFirme();
        dto2Bo(profilFirmeDTO, profilFirme);

        // on retourne le BO
        return profilFirme;
    }

    /**
     * dto -> bo for a profilFirme
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param profilFirmeDTO dto
     * @param profilFirme bo
     */
    public static void dto2BoLight(ProfilFirmeDTO profilFirmeDTO, ProfilFirme profilFirme) {
    
        /*PROTECTED REGION ID(dto2BoLight_6dhK8LdeEeCrCZp8iGNNVw) ENABLED START*/
        
        dto2BoLightImpl(profilFirmeDTO,profilFirme);
        
        /*PROTECTED REGION END*/
    }
    
    /**
     * dto -> bo for a profilFirme
     * calls dto2BoImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param profilFirmeDTO dto
     * @param profilFirme bo
     */
    public static void dto2Bo(ProfilFirmeDTO profilFirmeDTO, ProfilFirme profilFirme) throws JrafDomainException{
    
        /*PROTECTED REGION ID(dto2BoLight_6dhK8LdeEeCrCZp8iGNNVw) ENABLED START*/
        
        dto2BoImpl(profilFirmeDTO,profilFirme);
        
        /*PROTECTED REGION END*/
    }
    
    /**
     * dto -> bo implementation for a profilFirme
     * @param profilFirmeDTO dto
     * @param profilFirme bo
     */
    private static void dto2BoLightImpl(ProfilFirmeDTO profilFirmeDTO, ProfilFirme profilFirme){
    
        // property of ProfilFirmeDTO
        profilFirme.setGin(profilFirmeDTO.getGin());
        profilFirme.setExporting(profilFirmeDTO.getExporting());
        profilFirme.setMailing(profilFirmeDTO.getMailing());
        profilFirme.setCodeInseeEmp(profilFirmeDTO.getCodeInseeEmp());
        profilFirme.setDefautPaiement(profilFirmeDTO.getDefautPaiement());
        profilFirme.setInterdictionVente(profilFirmeDTO.getInterdictionVente());
        profilFirme.setLangueEcrite(profilFirmeDTO.getLangueEcrite());
        profilFirme.setNationalite(profilFirmeDTO.getNationalite());
        profilFirme.setNombreEmploye(profilFirmeDTO.getNombreEmploye());
        profilFirme.setImporting(profilFirmeDTO.getImporting());
        profilFirme.setLangueParlee(profilFirmeDTO.getLangueParlee());
        profilFirme.setNiveauSegmentation(profilFirmeDTO.getNiveauSegmentation());
        profilFirme.setTypeClient(profilFirmeDTO.getTypeClient());
        profilFirme.setTypeSegmentation(profilFirmeDTO.getTypeSegmentation());
        profilFirme.setCodGrpTie(profilFirmeDTO.getCodGrpTie());
        profilFirme.setCodTypTie(profilFirmeDTO.getCodTypTie());
    
    }

    /**
     * dto -> bo implementation for a profilFirme
     * @param profilFirmeDTO dto
     * @param profilFirme bo
     */
    private static void dto2BoImpl(ProfilFirmeDTO profilFirmeDTO, ProfilFirme profilFirme) throws JrafDomainException{
    
        // property of ProfilFirmeDTO
        profilFirme.setGin(profilFirmeDTO.getGin());
        profilFirme.setExporting(profilFirmeDTO.getExporting());
        profilFirme.setMailing(profilFirmeDTO.getMailing());
        profilFirme.setCodeInseeEmp(profilFirmeDTO.getCodeInseeEmp());
        profilFirme.setDefautPaiement(profilFirmeDTO.getDefautPaiement());
        profilFirme.setInterdictionVente(profilFirmeDTO.getInterdictionVente());
        profilFirme.setLangueEcrite(profilFirmeDTO.getLangueEcrite());
        profilFirme.setNationalite(profilFirmeDTO.getNationalite());
        profilFirme.setNombreEmploye(profilFirmeDTO.getNombreEmploye());
        profilFirme.setImporting(profilFirmeDTO.getImporting());
        profilFirme.setLangueParlee(profilFirmeDTO.getLangueParlee());
        profilFirme.setNiveauSegmentation(profilFirmeDTO.getNiveauSegmentation());
        profilFirme.setTypeClient(profilFirmeDTO.getTypeClient());
        profilFirme.setTypeSegmentation(profilFirmeDTO.getTypeSegmentation());
        profilFirme.setCodGrpTie(profilFirmeDTO.getCodGrpTie());
        profilFirme.setCodTypTie(profilFirmeDTO.getCodTypTie());

    }

    /**
     * bo -> dto for a profilFirme
     * @param pProfilFirme bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static ProfilFirmeDTO bo2DtoLight(ProfilFirme pProfilFirme) throws JrafDomainException {
        // instanciation du DTO
        ProfilFirmeDTO profilFirmeDTO = new ProfilFirmeDTO();
        bo2DtoLight(pProfilFirme, profilFirmeDTO);
        // on retourne le dto
        return profilFirmeDTO;
    }


    /**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param profilFirme bo
     * @param profilFirmeDTO dto
     */
    public static void bo2DtoLight(
        ProfilFirme profilFirme,
        ProfilFirmeDTO profilFirmeDTO) {

        /*PROTECTED REGION ID(bo2DtoLight_6dhK8LdeEeCrCZp8iGNNVw) ENABLED START*/

        bo2DtoLightImpl(profilFirme, profilFirmeDTO);

        /*PROTECTED REGION END*/

    }
    
    /**
     * Transform a business object to DTO. Implementation method
     * @param profilFirme bo
     * @param profilFirmeDTO dto
     */
    private static void bo2DtoLightImpl(ProfilFirme profilFirme,
        ProfilFirmeDTO profilFirmeDTO){
    

        // simple properties
        profilFirmeDTO.setGin(profilFirme.getGin());
        profilFirmeDTO.setExporting(profilFirme.getExporting());
        profilFirmeDTO.setMailing(profilFirme.getMailing());
        profilFirmeDTO.setCodeInseeEmp(profilFirme.getCodeInseeEmp());
        profilFirmeDTO.setDefautPaiement(profilFirme.getDefautPaiement());
        profilFirmeDTO.setInterdictionVente(profilFirme.getInterdictionVente());
        profilFirmeDTO.setLangueEcrite(profilFirme.getLangueEcrite());
        profilFirmeDTO.setNationalite(profilFirme.getNationalite());
        profilFirmeDTO.setNombreEmploye(profilFirme.getNombreEmploye());
        profilFirmeDTO.setImporting(profilFirme.getImporting());
        profilFirmeDTO.setLangueParlee(profilFirme.getLangueParlee());
        profilFirmeDTO.setNiveauSegmentation(profilFirme.getNiveauSegmentation());
        profilFirmeDTO.setTypeClient(profilFirme.getTypeClient());
        profilFirmeDTO.setTypeSegmentation(profilFirme.getTypeSegmentation());
        profilFirmeDTO.setCodGrpTie(profilFirme.getCodGrpTie());
        profilFirmeDTO.setCodTypTie(profilFirme.getCodTypTie());
    
    }
    
    /*PROTECTED REGION ID(_6dhK8LdeEeCrCZp8iGNNVw u m - Tr) ENABLED START*/
    // add your custom methods here if necessary    
    /*PROTECTED REGION END*/
}

