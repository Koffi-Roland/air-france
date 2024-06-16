package com.afklm.repindmsv.tribe.entity.node;

import java.util.Date;

import lombok.*;
import org.neo4j.ogm.annotation.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Relationship;

@NodeEntity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Member {

    @Id
    @GeneratedValue
    Long id;

    @Property
    private String gin;

    @Property
    String status;

    @Property
    private String role;

    private Tribe tribe;

    @Property
    private String modificationSignature;

    @Property
    private String modificationSite;

    @Property
    private Date modificationDate;

    @Property
    private String creationSignature;

    @Property
    private String creationSite;

    @Property
    private Date creationDate;
}