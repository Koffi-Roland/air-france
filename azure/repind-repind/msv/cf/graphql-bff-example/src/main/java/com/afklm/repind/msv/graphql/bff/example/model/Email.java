package com.afklm.repind.msv.graphql.bff.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * Email
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Email {

    @JsonProperty("version")
    private Integer version;
    @JsonProperty("mediumStatus")
    private String mediumStatus;
    @JsonProperty("mediumCode")
    private String mediumCode;
    @JsonProperty("email")
    private String email;
    @JsonProperty("emailOptin")
    private String emailOptin;
}
