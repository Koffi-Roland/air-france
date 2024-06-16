package com.airfrance.repindutf8.dto.reference;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repindutf8.entity.RefErreur;
public final class RefErreurTransform {

    private RefErreurTransform() {
    }
    /**
     * dto -> bo for a RefErreur
     * @param refErreurDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static RefErreur dto2BoLight(RefErreurDTO refErreurDTO) throws JrafDomainException {
        // instanciation du BO
        RefErreur refErreur = new RefErreur();
        dto2BoLight(refErreurDTO, refErreur);

        // on retourne le BO
        return refErreur;
    }

    /**
     * dto -> bo for a refErreur
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param refErreurDTO dto
     * @param refErreur bo
     */
    public static void dto2BoLight(RefErreurDTO refErreurDTO, RefErreur refErreur) {
    
        /*PROTECTED REGION ID(dto2BoLight_Ackc8IJTEeKhdftDNws56g) ENABLED START*/
        
        dto2BoLightImpl(refErreurDTO,refErreur);
        
        /*PROTECTED REGION END*/
    }
    
    /**
     * dto -> bo implementation for a refErreur
     * @param refErreurDTO dto
     * @param refErreur bo
     */
    private static void dto2BoLightImpl(RefErreurDTO refErreurDTO, RefErreur refErreur){
    
        // property of RefErreurDTO
        refErreur.setRefErreurId(refErreurDTO.getRefErreurId());
        refErreur.setScode(refErreurDTO.getScode());
        refErreur.setSlibelle(refErreurDTO.getLibelle());
        refErreur.setSlibelleEn(refErreurDTO.getLibelleEn());
    
    }

    /**
     * bo -> dto for a refErreur
     * @param pRefErreur bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static RefErreurDTO bo2DtoLight(RefErreur pRefErreur) throws JrafDomainException {
        // instanciation du DTO
        RefErreurDTO refErreurDTO = new RefErreurDTO();
        bo2DtoLight(pRefErreur, refErreurDTO);
        // on retourne le dto
        return refErreurDTO;
    }


    /**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param refErreur bo
     * @param refErreurDTO dto
     */
    public static void bo2DtoLight(
        RefErreur refErreur,
        RefErreurDTO refErreurDTO) {

        /*PROTECTED REGION ID(bo2DtoLight_Ackc8IJTEeKhdftDNws56g) ENABLED START*/

        bo2DtoLightImpl(refErreur, refErreurDTO);

        /*PROTECTED REGION END*/

    }
    
    /**
     * Transform a business object to DTO. Implementation method
     * @param refErreur bo
     * @param refErreurDTO dto
     */
    private static void bo2DtoLightImpl(RefErreur refErreur,
        RefErreurDTO refErreurDTO){
    

        // simple properties
        refErreurDTO.setRefErreurId(refErreur.getRefErreurId());
        refErreurDTO.setScode(refErreur.getScode());
        refErreurDTO.setLibelle(refErreur.getSlibelle());
        refErreurDTO.setLibelleEn(refErreur.getSlibelleEn());
    
    }
   
}

