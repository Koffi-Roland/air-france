package com.airfrance.batch.common.wrapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class WrapperProvideIndividualScoreResponse {

    private String gin;
    private double score;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WrapperProvideIndividualScoreResponse that = (WrapperProvideIndividualScoreResponse) o;
        return gin.equals(that.gin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gin);
    }
}

