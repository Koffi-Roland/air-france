package com.afklm.repindpp.paymentpreference.dto;

/*PROTECTED REGION ID(_RzGAEFriEeCAX-eiAOZ-9g i - Tr) ENABLED START*/

// add not generated imports here

import com.afklm.repindpp.paymentpreference.entity.Fields;
import com.afklm.repindpp.paymentpreference.entity.PaymentDetails;
import com.airfrance.ref.exception.jraf.JrafDomainException;

import java.util.HashSet;
import java.util.Set;

/*PROTECTED REGION END*/

/**
 * <p>Title : PaymentDetailsTransform.java</p>
 * transformation bo <-> dto pour un(e) PaymentDetails
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class PaymentDetailsTransform {

    /*PROTECTED REGION ID(_RzGAEFriEeCAX-eiAOZ-9g u var - Tr) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /**
     * Constructeur prive
     */
    private PaymentDetailsTransform() {
    }
    /**
     * dto -> bo pour une PaymentDetails
     * @param paymentsDetailsDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static PaymentDetails dto2BoLight(PaymentsDetailsDTO paymentsDetailsDTO) throws JrafDomainException {
        // instanciation du BO
        PaymentDetails paymentDetails = new PaymentDetails();
        dto2BoLight(paymentsDetailsDTO, paymentDetails);
        
        // on retourne le BO
        return paymentDetails;
    }

    /**
     * dto -> bo pour une paymentDetails
     * @param paymentsDetailsDTO dto
     * @param paymentDetails bo
     */
    public static void dto2BoLight(PaymentsDetailsDTO paymentsDetailsDTO, PaymentDetails paymentDetails) {
        // property of PaymentsDetailsDTO
        paymentDetails.setPaymentId(paymentsDetailsDTO.getPaymentId());
        paymentDetails.setGin(paymentsDetailsDTO.getGin());
        paymentDetails.setVersion(paymentsDetailsDTO.getVersion());
        paymentDetails.setPaymentType(paymentsDetailsDTO.getPaymentType());
        paymentDetails.setPointOfSell(paymentsDetailsDTO.getPointOfSell());
        paymentDetails.setCarrier(paymentsDetailsDTO.getCarrier());
        paymentDetails.setDateCreation(paymentsDetailsDTO.getDateCreation());
        paymentDetails.setSignatureCreation(paymentsDetailsDTO.getSignatureCreation());
        paymentDetails.setSiteCreation(paymentsDetailsDTO.getSiteCreation());
        paymentDetails.setChangingDate(paymentsDetailsDTO.getChangingDate());
        paymentDetails.setChangingSignature(paymentsDetailsDTO.getChangingSignature());
        paymentDetails.setChangingSite(paymentsDetailsDTO.getChangingSite());
        paymentDetails.setPaymentGroup(paymentsDetailsDTO.getPaymentGroup());
        paymentDetails.setPaymentMethod(paymentsDetailsDTO.getPaymentMethod());
        paymentDetails.setPreferred(paymentsDetailsDTO.getPreferred());
        paymentDetails.setCorporate(paymentsDetailsDTO.getCorporate());
        paymentDetails.setIpAdresse(paymentsDetailsDTO.getIpAdresse());
        paymentDetails.setIsTokenized(paymentsDetailsDTO.getIsTokenized());
        paymentDetails.setCardName(paymentsDetailsDTO.getCardName());
    }

    /**
     * bo -> dto pour une paymentDetails
     * @param paymentDetails bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static PaymentsDetailsDTO bo2DtoLight(PaymentDetails paymentDetails) throws JrafDomainException {
        // instanciation du DTO
        PaymentsDetailsDTO paymentsDetailsDTO = new PaymentsDetailsDTO();
        bo2DtoLight(paymentDetails, paymentsDetailsDTO);
        // on retourne le dto
        return paymentsDetailsDTO;
    }
    

    /**
     * Transforme un business object en DTO.
     * Methode dite light.
     * @param paymentDetails bo
     * @param paymentsDetailsDTO dto
     */
    public static void bo2DtoLight(
        PaymentDetails paymentDetails,
        PaymentsDetailsDTO paymentsDetailsDTO) {


        // simple properties
        paymentsDetailsDTO.setPaymentId(paymentDetails.getPaymentId());
        paymentsDetailsDTO.setGin(paymentDetails.getGin());
        paymentsDetailsDTO.setVersion(paymentDetails.getVersion());
        paymentsDetailsDTO.setPaymentType(paymentDetails.getPaymentType());
        paymentsDetailsDTO.setPointOfSell(paymentDetails.getPointOfSell());
        paymentsDetailsDTO.setCarrier(paymentDetails.getCarrier());
        paymentsDetailsDTO.setDateCreation(paymentDetails.getDateCreation());
        paymentsDetailsDTO.setSignatureCreation(paymentDetails.getSignatureCreation());
        paymentsDetailsDTO.setSiteCreation(paymentDetails.getSiteCreation());
        paymentsDetailsDTO.setChangingDate(paymentDetails.getChangingDate());
        paymentsDetailsDTO.setChangingSignature(paymentDetails.getChangingSignature());
        paymentsDetailsDTO.setChangingSite(paymentDetails.getChangingSite());
        paymentsDetailsDTO.setPaymentGroup(paymentDetails.getPaymentGroup());
        paymentsDetailsDTO.setPaymentMethod(paymentDetails.getPaymentMethod());
        paymentsDetailsDTO.setPreferred(paymentDetails.getPreferred());
        paymentsDetailsDTO.setCorporate(paymentDetails.getCorporate());
        paymentsDetailsDTO.setIpAdresse(paymentDetails.getIpAdresse());
        paymentsDetailsDTO.setIsTokenized(paymentDetails.getIsTokenized());
        paymentsDetailsDTO.setCardName(paymentDetails.getCardName());
    }
    
    /*PROTECTED REGION ID(_RzGAEFriEeCAX-eiAOZ-9g u m - Tr) ENABLED START*/
    
    public static void dto2BoLink(PaymentsDetailsDTO paymentsDetailsDTO,PaymentDetails paymentDetails){
    	Set<Fields> pFields = new HashSet<Fields>();
    	for (FieldsDTO fieldDTO : paymentsDetailsDTO.getFieldsdto()) {
    		Fields field = new Fields();
    		field.setPaymentFieldCode(fieldDTO.getPaymentFieldCode());
    		field.setPaymentFieldPreference(fieldDTO.getPaymentFieldPreference());
    		field.setPaymentdetails(paymentDetails);
    		pFields.add(field);
		}
    	paymentDetails.setFields(pFields);
    }
    
    
    public static void bo2DtoLink(PaymentDetails paymentDetails, PaymentsDetailsDTO paymentsDetailsDTO){
    	Set<FieldsDTO> pFieldsDTO = new HashSet<FieldsDTO>();
    	for(Fields fields : paymentDetails.getFields()){
    		FieldsDTO field = new FieldsDTO();
    		field.setPaymentFieldCode(fields.getPaymentFieldCode());
    		field.setPaymentFieldPreference(fields.getPaymentFieldPreference());
    		field.setPaymentsdetailsdto(paymentsDetailsDTO);
    		pFieldsDTO.add(field);
    	}
    	paymentsDetailsDTO.setFieldsdto(pFieldsDTO);
    }
    /*PROTECTED REGION END*/
}
