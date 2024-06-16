package com.afklm.repind.msv.provide.identification.data.models;

import com.afklm.repind.common.entity.individual.Individu;
import com.afklm.repind.common.entity.individual.UsageClientEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
/*
 * A class used to refactor some code and make data manipulation easier
 */
public class IdentificationTransformModel {
    private Individu individu;
    private List<UsageClientEntity> usagesClient;
    private String languageCode;
}
