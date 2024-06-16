package com.afklm.rigui.dto.ws;

import javax.xml.bind.annotation.XmlSchemaType;
import java.io.Serializable;
import java.util.Date;

public class SignatureDTO implements Serializable {

    private String signatureType;

    private String signatureSite;

    private String signature;

    @XmlSchemaType(name = "dateTime")
    private Date date;

    public SignatureDTO() {
    }

    public SignatureDTO(String signatureType, String signatureSite, String signature, Date date) {
        this.signatureType = signatureType;
        this.signatureSite = signatureSite;
        this.signature = signature;
        this.date = date;
    }

    public String getSignatureType() {
        return signatureType;
    }

    public void setSignatureType(String signatureType) {
        this.signatureType = signatureType;
    }

    public String getSignatureSite() {
        return signatureSite;
    }

    public void setSignatureSite(String signatureSite) {
        this.signatureSite = signatureSite;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
