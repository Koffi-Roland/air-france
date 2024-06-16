package com.airfrance.batch.deduplicatecompref.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CommunicationPreferencesModel {

    /**
     * Communication preferences identifier
     */
    private Integer comPrefId;

    /**
     * Individual identifier
     */
    private String gin;

    /**
     * Communication type
     */
    private String comType;

}
