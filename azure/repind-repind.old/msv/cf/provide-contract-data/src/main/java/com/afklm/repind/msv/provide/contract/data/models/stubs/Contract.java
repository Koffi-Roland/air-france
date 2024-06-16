package com.afklm.repind.msv.provide.contract.data.models.stubs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
/*
 * This is the model of a contract
 */
public class Contract {
    private String companyCode;
    private String contractNumber;
    private String contractStatus;
    private String contractType;
    private String contractSubType;
    private String corporateEnvironmentID;
    private String iataCode;
    private String matchingRecognition;
    private LocalDate lastRecognitionDate;
    private SignatureElement signatureCreation;
    private SignatureElement signatureModification;
    private LocalDate validityEndDate;
    private LocalDate validityStartDate;
}
