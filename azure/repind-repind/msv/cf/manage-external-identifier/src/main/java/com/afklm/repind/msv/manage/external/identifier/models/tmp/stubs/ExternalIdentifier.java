package com.afklm.repind.msv.manage.external.identifier.models.tmp.stubs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExternalIdentifier {
    private List<ExternalIdentifierData> externalIdentifierData;
    private String type;
    private String identifier;
    private Signature signature;
}
