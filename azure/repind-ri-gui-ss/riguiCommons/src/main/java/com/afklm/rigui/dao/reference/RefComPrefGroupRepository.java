package com.afklm.rigui.dao.reference;

import com.afklm.rigui.entity.reference.RefComPrefGroup;
import com.afklm.rigui.entity.reference.RefComPrefGroupId;
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

}
