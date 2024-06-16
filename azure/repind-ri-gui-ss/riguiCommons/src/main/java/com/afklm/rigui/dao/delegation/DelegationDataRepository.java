package com.afklm.rigui.dao.delegation;

import com.afklm.rigui.entity.delegation.DelegationData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DelegationDataRepository extends JpaRepository<DelegationData, Integer> {

	@Query("select dd from DelegationData dd where dd.delegate.sgin = :gin")
	public List<DelegationData> findDelegatesByGin(@Param("gin") String gin);

	@Query("select dd from DelegationData dd where dd.delegator.sgin = :gin")
	public List<DelegationData> findDelegatorsByGin(@Param("gin") String gin);

	@Query("select count(dd) from DelegationData dd where dd.delegate.sgin = :gin")
	int countDelegateNumberByGin(@Param("gin") String gin);

	@Query("select count(dd) from DelegationData dd where dd.delegator.sgin = :gin")
	int countDelegatorNumberByGin(@Param("gin") String gin);
}
