package com.airfrance.batch.purgemyaccount.model;

import lombok.*;

/**
 * Role contrat physical delete
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RcPhysicalDelete {
    /**
     * rc.ICLE_ROLE
     */
    private Integer icleRole;
}