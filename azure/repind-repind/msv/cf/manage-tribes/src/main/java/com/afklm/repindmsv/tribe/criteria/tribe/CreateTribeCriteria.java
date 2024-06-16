package com.afklm.repindmsv.tribe.criteria.tribe;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;

@Getter
@With
@AllArgsConstructor
@NoArgsConstructor
public class CreateTribeCriteria {

    private String name;

    private String type;

    private String manager;

    private String application;

}
