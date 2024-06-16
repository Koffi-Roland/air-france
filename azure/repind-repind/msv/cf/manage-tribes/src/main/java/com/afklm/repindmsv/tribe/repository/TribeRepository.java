package com.afklm.repindmsv.tribe.repository;

import com.afklm.repindmsv.tribe.entity.node.Tribe;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TribeRepository extends Neo4jRepository<Tribe, UUID> {

    @Query("MATCH(t:Tribe {id : $0})-[r:MEMBERSHIP]-(m:Member{ gin:$1}) DELETE r")
    void detachRelationMemberShip(String id, String gin);

    @Query("MATCH(t:Tribe {id : $0})-[r:MEMBERSHIP]-(m:Member{ status:$1}) RETURN t, collect(r), collect(m)")
    Optional<Tribe> retrieveRelationMemberShip(String id, String status);

    @Query("MATCH(t:Tribe {id : $0})-[r:MEMBERSHIP]-(m:Member) RETURN t, collect(r), collect(m)")
    Optional<Tribe> retrieveRelationMemberShip(String id);

    @Query("MATCH (t:Tribe) where t.id=$0 RETURN t")
    Optional<Tribe> getById(String id);

    @Query("MATCH (t:Tribe)-[r:MEMBERSHIP]-(n:Member) where n.gin=$0 and  n.status=$1 RETURN collect(n), collect(r), t")
    Optional<Tribe> retrieveMemberWithValidatedStatus(String gin, String status);




}
