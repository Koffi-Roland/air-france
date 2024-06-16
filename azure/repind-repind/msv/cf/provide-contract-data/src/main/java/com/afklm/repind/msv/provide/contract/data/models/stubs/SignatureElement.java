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
 * This is the model of a signature
 */
public class SignatureElement {
    private LocalDate date;
    private String signature;
    private String site;
}
