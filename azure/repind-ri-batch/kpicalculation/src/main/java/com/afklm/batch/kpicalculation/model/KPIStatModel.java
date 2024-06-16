package com.afklm.batch.kpicalculation.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KPIStatModel {
    private String label;
    private int value;
    private String kpi;
    private String optionalData;

    public KPIStatModel() {
    }

    public KPIStatModel(String label, Long value) {
        this.label = label;
        this.value = value.intValue();
        this.optionalData = null;
    }

    public KPIStatModel(String label, int value, String kpi) {
        this.label = label;
        this.value = value;
        this.kpi = kpi;
        this.optionalData = null;
    }

    public KPIStatModel(String label, int value, String kpi, String optionalData) {
        this.label = label;
        this.value = value;
        this.kpi = kpi;
        this.optionalData = optionalData;
    }
}
