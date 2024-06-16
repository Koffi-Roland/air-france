package com.afklm.repindmsv.tribe.wrapper;

import com.afklm.repindmsv.tribe.model.SignatureModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WrapperRetrieveTribeMemberResponse {

    private String tribeId;
    private String gin;
    private String role;
    private String status;

    private String modificationSignature;
    private String modificationSite;
    private Date modificationDate;
    private String creationSignature;
    private String creationSite;
    private Date creationDate;
}
