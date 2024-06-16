package com.afklm.repind.msv.graphql.bff.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Email Response
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailResponse {

    @JsonProperty("email")
    private Email email;
    @JsonProperty("signature")
    private @Valid List<Signature> signature = new ArrayList<>();
}
