package com.airfrance.batch.cleanTravelDoc.model;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CleanTravelDocModel {

    @NotNull
    private Long preferenceId;
}
