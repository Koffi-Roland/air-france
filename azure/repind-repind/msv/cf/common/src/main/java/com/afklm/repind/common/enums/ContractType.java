package com.afklm.repind.common.enums;

import java.util.Arrays;

/**
 * This class allow us to check which contract type we're dealing with and also remind us which acronym stand for which contract
 */
public enum ContractType {
    ROLE_CONTRACT("C"),
    ROLE_UCCR("U"),
    ROLE_TRAVELERS("T"),
    CONTRACT_DOCTOR("D");

    private final String acronym;

    ContractType(String acronym){
        this.acronym = acronym;
    }

    @Override
    public String toString() {
        return this.acronym;
    }

    public static ContractType fromLabel(String label){
        return Arrays.stream(ContractType.values()).filter(e -> e.acronym.equals(label)).findFirst().orElse(null);
    }
}
