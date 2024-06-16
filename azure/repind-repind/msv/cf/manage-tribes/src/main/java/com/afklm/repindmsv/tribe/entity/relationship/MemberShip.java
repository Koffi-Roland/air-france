package com.afklm.repindmsv.tribe.entity.relationship;

import com.afklm.repindmsv.tribe.entity.node.Member;
import com.afklm.repindmsv.tribe.entity.node.Tribe;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;

@RelationshipEntity(type = "MEMBERSHIP")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberShip {

    @JsonIgnore
    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    private Tribe tribe;

    @EndNode
    private Member member;

}
