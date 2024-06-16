package com.airfrance.repind.entity.firme.enums;

import java.util.Optional;

public enum PersonneMoraleTypeEnum {

    /**
     * Entreprise
     */
    ENTREPRISE("E"),

    /**
     * Groupe
     */
    GROUPE("G"),

    /**
     * Agence
     */
    AGENCE("A"),

    /**
     * Etablissement
     */
    ETABLISSEMENT("T");

    private String code;

    PersonneMoraleTypeEnum(String code) {
        this.code = code;
    }

    /**
     * From code optional.
     *
     * @param code the code
     * @return the optional
     */
    public static Optional<PersonneMoraleTypeEnum> fromCode(String code) {
        for (PersonneMoraleTypeEnum arg : PersonneMoraleTypeEnum.values()) {
            if (arg.code.equals(code)) {
                return Optional.of(arg);
            }
        }
        return Optional.empty();
    }
}
