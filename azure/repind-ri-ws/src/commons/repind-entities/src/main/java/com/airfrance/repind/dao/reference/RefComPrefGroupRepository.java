package com.airfrance.repind.dao.reference;

import com.airfrance.repind.entity.reference.RefComPrefDgt;
import com.airfrance.repind.entity.reference.RefComPrefGroup;
import com.airfrance.repind.entity.reference.RefComPrefGroupId;
import com.airfrance.repind.entity.reference.RefComPrefGroupInfo;
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
