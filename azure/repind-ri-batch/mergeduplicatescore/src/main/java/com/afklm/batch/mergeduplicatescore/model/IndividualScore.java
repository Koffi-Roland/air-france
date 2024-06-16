package com.afklm.batch.mergeduplicatescore.model;


import lombok.*;

import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class IndividualScore {

    private String gin;

    private double score;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IndividualScore that = (IndividualScore) o;
        return gin.equals(that.gin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gin);
    }
}
