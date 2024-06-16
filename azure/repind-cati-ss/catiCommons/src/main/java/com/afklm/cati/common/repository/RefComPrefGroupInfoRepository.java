package com.afklm.cati.common.repository;

import com.afklm.cati.common.entity.RefComPrefGroupInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RefComPrefGroupInfoRepository extends JpaRepository<RefComPrefGroupInfo, Integer> {

    @Query("select count(*) from RefComPrefGroup ref where ref.refComPrefGroupId.refComPrefGroupInfo = :refComPrefGroupInfo")
    Long countRefComPrefGroupById(@Param("refComPrefGroupInfo") RefComPrefGroupInfo refComPrefGroupInfo);

}
