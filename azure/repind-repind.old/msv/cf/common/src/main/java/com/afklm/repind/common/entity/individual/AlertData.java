package com.afklm.repind.common.entity.individual;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ALERT_DATA")
public class AlertData {

    private static final long serialVersionUID = 1L;

    /**
     * alertDataId
     */
    @Id
    @Column(name = "ALERT_DATA_ID", length = 12, nullable = false, unique = true)
    @SequenceGenerator(name = "SEQ_ALERT_DATA", sequenceName = "SEQ_ALERT_DATA",
            allocationSize = 1)
    @GeneratedValue(generator = "SEQ_ALERT_DATA")
    private Integer alertDataId;

    /**
     * key
     */
    @Column(name = "KEY")
    private String key;

    /**
     * value
     */
    @Column(name = "VALUE")
    private String value;

    /**
     * alert
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ALERT_ID")
    private Alert alert;
}
