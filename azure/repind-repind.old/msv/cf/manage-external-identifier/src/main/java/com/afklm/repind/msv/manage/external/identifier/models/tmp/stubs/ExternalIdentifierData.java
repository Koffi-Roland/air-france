package com.afklm.repind.msv.manage.external.identifier.models.tmp.stubs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExternalIdentifierData {
    private String key;
    private String value;
    private Signature signature;
}
