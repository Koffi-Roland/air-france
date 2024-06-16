package com.airfrance.batch.common.entity.kpi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "HISTORIZATION_KPI")
public class HistorizationKPIEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ISEQ_ID_HISTORIZATION_KPI")
    @SequenceGenerator(name = "ISEQ_ID_HISTORIZATION_KPI", sequenceName = "ISEQ_ID_HISTORIZATION_KPI",
            allocationSize = 1)
    private int id;
    @Column(name = "KPI")
    private String kpi;
    @Column(name = "LABEL")
    private String label;
    @Column(name = "VALUE")
    private int value;
    @Column(name = "OPTIONAL_DATA")
    private String optionalData;
    @Column(name = "DDATE")
    private Date date;
}
