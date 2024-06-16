package com.afklm.repindmsv.tribe.criteria.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;

@Getter
@With
@AllArgsConstructor
@NoArgsConstructor
public class AddMemberCriteria {

    private String tribeId;
    private String gin;
    private String application;
}
