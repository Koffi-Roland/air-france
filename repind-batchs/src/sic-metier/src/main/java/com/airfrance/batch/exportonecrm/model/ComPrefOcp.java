package com.airfrance.batch.exportonecrm.model;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ComPrefOcp {
    private String ACTION_TYPE;
    private String ACTION_DATE;
    private String GIN;
    private String ACTION_TABLE;
    private String CONTENT_DATA;
}