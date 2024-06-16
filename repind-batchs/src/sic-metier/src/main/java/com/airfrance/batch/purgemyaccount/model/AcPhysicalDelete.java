package com.airfrance.batch.purgemyaccount.model;

import lombok.*;

/**
 * MyaPhysicalToDelete
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class AcPhysicalDelete {
    /**
     * ad.ID
     */
    private Integer id;

    /**
     * ad.ACCOUNT_IDENTIFIER
     */
    private String accountIdentifier;
}