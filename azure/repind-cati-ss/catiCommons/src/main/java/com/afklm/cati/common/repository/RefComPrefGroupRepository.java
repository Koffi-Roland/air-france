package com.afklm.cati.common.repository;


import com.afklm.cati.common.entity.RefComPrefDgt;
import com.afklm.cati.common.entity.RefComPrefGroup;
import com.afklm.cati.common.entity.RefComPrefGroupId;
import com.afklm.cati.common.entity.RefComPrefGroupInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefComPrefGroupRepository extends JpaRepository<RefComPrefGroup, RefComPrefGroupId> {

    @Query("select refComPrefGroup from RefComPrefGroup refComPrefGroup "
            + "where refComPrefGroup.refComPrefGroupId.refComPrefGroupInfo.id = :groupInfoId")
    List<RefComPrefGroup> getAllRefComPrefGroupByGroupInfo(@Param("groupInfoId") Integer groupInfoId);

    @Query("select ref.refComPrefGroupId.refComPrefDgt.refComPrefDgtId from RefComPrefGroup ref where ref.refComPrefGroupId.refComPrefGroupInfo = :refComPrefGroupInfo")
    List<Integer> getRefComPrefDgtByRefComPrefGroupInfo(@Param("refComPrefGroupInfo") RefComPrefGroupInfo refComPrefGroupInfo);

    @Query("select ref.refComPrefGroupId from RefComPrefGroup ref where ref.refComPrefGroupId.refComPrefGroupInfo = :refComPrefGroupInfo")
    RefComPrefGroupId getRefComPrefGroupIdByRefComPrefDgt(RefComPrefDgt refComPrefDgt);
}
