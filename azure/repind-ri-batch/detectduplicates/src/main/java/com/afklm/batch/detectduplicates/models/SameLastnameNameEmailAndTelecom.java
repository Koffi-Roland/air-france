package com.afklm.batch.detectduplicates.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SameLastnameNameEmailAndTelecom implements Serializable {
    private String nomPrenom;
    private String telecom;
    private String telecomGINs;
    private int telecomNbContract;
    private int telecomNbGINs;
    private String email;
    private String emailGINs;
    private int emailNbContract;
    private int emailNbGINs;
}
