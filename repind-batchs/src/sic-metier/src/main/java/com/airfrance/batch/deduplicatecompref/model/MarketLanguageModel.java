package com.airfrance.batch.deduplicatecompref.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MarketLanguageModel {

    /**
     * Market language identifier
     */
    private Integer marketLanguageId;

    /**
     * Communication preference identifier
     */
    private Integer comPrefId;

}
