package com.afklm.repind.msv.doctor.attributes.wrapper.role;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WrapperRetrieveDoctorRoleResponse {

    private String roleId;
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy'T'hh:mm:ssZ")
    private Date endDateRole;
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy'T'hh:mm:ssZ")
    private Date optOut;
    private String status;
    private String doctorId;
    private Date lastUpdate;

    private List<String> languages;

    private String speciality;

    private String airLineCode;

    private String signatureSourceCreation;

    private String siteCreation;

    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy'T'hh:mm:ssZ")
    private Date signatureDateCreation;

    private String signatureSourceModification;

    private String siteModification;

    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy'T'hh:mm:ssZ")
    private Date signatureDateModification;

}
