package com.afklm.repind.msv.graphql.bff.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;
/**
 * Signature
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Signature {

    @JsonProperty("signatureType")
    private String signatureType;
    @JsonProperty("signatureSite")
    private String signatureSite;
    @JsonProperty("signature")
    private String signature;
    @JsonProperty("date")
    @DateTimeFormat(
            iso = DateTimeFormat.ISO.DATE_TIME
    )
    private OffsetDateTime date;
}
