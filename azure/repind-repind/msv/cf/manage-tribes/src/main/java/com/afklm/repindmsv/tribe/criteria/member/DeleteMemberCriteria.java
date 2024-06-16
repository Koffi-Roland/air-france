package com.afklm.repindmsv.tribe.criteria.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;

@Getter
@With
@AllArgsConstructor
@NoArgsConstructor
public class DeleteMemberCriteria {

    private String tribeId;
    private String gin;
    private String status;
    private String application;
}
