package com.afklm.repind.msv.automatic.merge.spring.rest.resources;

import java.io.Serializable;

public class RefPreferenceDataKey implements Serializable {

    private static final long serialVersionUID = 1L;

    private String code;

    private String libelleFR;

    private String libelleEN;

    private String normalizedKey;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelleFR() {
        return libelleFR;
    }

    public void setLibelleFR(String libelleFR) {
        this.libelleFR = libelleFR;
    }

    public String getLibelleEN() {
        return libelleEN;
    }

    public void setLibelleEN(String libelleEN) {
        this.libelleEN = libelleEN;
    }

    public String getNormalizedKey() {
        return normalizedKey;
    }

    public void setNormalizedKey(String normalizedKey) {
        this.normalizedKey = normalizedKey;
    }

}
