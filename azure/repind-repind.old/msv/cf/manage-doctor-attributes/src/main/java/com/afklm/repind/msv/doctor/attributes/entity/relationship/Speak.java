package com.afklm.repind.msv.doctor.attributes.entity.relationship;

import com.afklm.repind.msv.doctor.attributes.entity.node.Language;
import com.afklm.repind.msv.doctor.attributes.entity.node.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.neo4j.ogm.annotation.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;

@RelationshipEntity(type = "SPEAK")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Speak {

    @JsonIgnore
    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    private Role role;

    @EndNode
    private Language language;
}
