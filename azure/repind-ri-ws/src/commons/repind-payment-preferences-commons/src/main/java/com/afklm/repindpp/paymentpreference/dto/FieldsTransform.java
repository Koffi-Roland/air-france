package com.afklm.repindpp.paymentpreference.dto;

/*PROTECTED REGION ID(_YtXqkFriEeCAX-eiAOZ-9g i - Tr) ENABLED START*/

// add not generated imports here
import com.afklm.repindpp.paymentpreference.entity.Fields;
import com.airfrance.ref.exception.jraf.JrafDomainException;


/*PROTECTED REGION END*/

/**
 * <p>Title : FieldsTransform.java</p>
 * transformation bo <-> dto pour un(e) Fields
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class FieldsTransform {

    /*PROTECTED REGION ID(_YtXqkFriEeCAX-eiAOZ-9g u var - Tr) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /**
     * Constructeur prive
     */
    private FieldsTransform() {
    }
    /**
     * dto -> bo pour une Fields
     * @param fieldsDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static Fields dto2BoLight(FieldsDTO fieldsDTO) throws JrafDomainException {
        // instanciation du BO
        Fields fields = new Fields();
        dto2BoLight(fieldsDTO, fields);
        
        // on retourne le BO
        return fields;
    }

    /**
     * dto -> bo pour une fields
     * @param fieldsDTO dto
     * @param fields bo
     */
    public static void dto2BoLight(FieldsDTO fieldsDTO, Fields fields) {
        // property of FieldsDTO
        fields.setFieldId(fieldsDTO.getFieldId());
        fields.setPaymentFieldCode(fieldsDTO.getPaymentFieldCode());
        fields.setPaymentFieldPreference(fieldsDTO.getPaymentFieldPreference());
    }

    /**
     * bo -> dto pour une fields
     * @param fields bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static FieldsDTO bo2DtoLight(Fields fields) throws JrafDomainException {
        // instanciation du DTO
        FieldsDTO fieldsDTO = new FieldsDTO();
        bo2DtoLight(fields, fieldsDTO);
        // on retourne le dto
        return fieldsDTO;
    }
    

    /**
     * Transforme un business object en DTO.
     * Methode dite light.
     * @param fields bo
     * @param fieldsDTO dto
     */
    public static void bo2DtoLight(
        Fields fields,
        FieldsDTO fieldsDTO) {


        // simple properties
        fieldsDTO.setFieldId(fields.getFieldId());
        fieldsDTO.setPaymentFieldCode(fields.getPaymentFieldCode());
        fieldsDTO.setPaymentFieldPreference(fields.getPaymentFieldPreference());


    }
    
    /*PROTECTED REGION ID(_YtXqkFriEeCAX-eiAOZ-9g u m - Tr) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
}
