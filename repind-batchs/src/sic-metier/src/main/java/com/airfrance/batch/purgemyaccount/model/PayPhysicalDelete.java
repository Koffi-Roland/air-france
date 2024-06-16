package com.airfrance.batch.purgemyaccount.model;

import lombok.*;

/**
 * Payment preferences physical delete
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PayPhysicalDelete {
    /**
     * pay.GIN
     */
    private String gin;
}
