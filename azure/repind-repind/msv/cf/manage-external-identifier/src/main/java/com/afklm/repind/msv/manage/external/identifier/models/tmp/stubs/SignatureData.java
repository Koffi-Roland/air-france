package com.afklm.repind.msv.manage.external.identifier.models.tmp.stubs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SignatureData {
    private String site;
    private String signature;
    private Date date;
}
