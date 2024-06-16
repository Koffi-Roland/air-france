package com.afklm.repind.msv.doctor.attributes.entity.node;

import com.afklm.repind.msv.doctor.attributes.entity.relationship.Expert;
import lombok.*;
import org.neo4j.ogm.annotation.NodeEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.Set;

@NodeEntity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Speciality {

    @Id
    @GeneratedValue
    private Long id;

    private String value;
//
//    @Relationship(type="EXPERT" , direction = Relationship.Direction.INCOMING)
//    private Set<Expert> experts = new HashSet<>();

}
