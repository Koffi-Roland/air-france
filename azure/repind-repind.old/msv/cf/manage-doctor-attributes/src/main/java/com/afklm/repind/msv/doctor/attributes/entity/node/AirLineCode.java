package com.afklm.repind.msv.doctor.attributes.entity.node;

import lombok.*;
import org.neo4j.ogm.annotation.NodeEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;

@NodeEntity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AirLineCode {

    @Id
    @GeneratedValue
    private Long id;

    private String value;

}
