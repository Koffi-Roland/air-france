
package com.afklm.repindmsv.tribe.entity.node;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.*;

@Getter
@Setter
@NodeEntity
public class Tribe {

    @Id
    @GeneratedValue()
    private UUID id;

    @Property
    private String name;

    @Property
    private String type;

    @Property
    private String status;

    @Relationship(type="MEMBERSHIP", direction = Relationship.Direction.OUTGOING)
    @JsonIgnoreProperties({"tribe", "id"})
    private Set<Member> members;

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


    public Tribe(String name) {
        this.name = name;
    }

    public void addMember(Member member) {
        if (members == null) {
            members = new HashSet<>();
        }
        members.add(member);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tribe tribe = (Tribe) o;
        return id.equals(tribe.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}