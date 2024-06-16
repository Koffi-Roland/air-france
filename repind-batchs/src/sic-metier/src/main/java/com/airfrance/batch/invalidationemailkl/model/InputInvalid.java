package com.airfrance.batch.invalidationemailkl.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Builder
public class InputInvalid {
    private String actionIndex;
    private String comReturnCodeIndex;
    private String contactTypeIndex;
    private String contactIndex;
    private String causeIndex;
}