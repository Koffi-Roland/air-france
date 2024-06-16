package com.afklm.repind.msv.provide.identification.data.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
/*
A class used to store data about signature to do some refactor and to have easier data manipulation
 */
public class SignatureElement {
    String site;
    String signature;
    Date date;
}
