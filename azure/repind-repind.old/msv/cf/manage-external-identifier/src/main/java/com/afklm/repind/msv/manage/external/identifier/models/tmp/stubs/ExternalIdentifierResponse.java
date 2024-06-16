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
public class ExternalIdentifierResponse {
    private List<ExternalIdentifier> externalIdentifierList;
}
