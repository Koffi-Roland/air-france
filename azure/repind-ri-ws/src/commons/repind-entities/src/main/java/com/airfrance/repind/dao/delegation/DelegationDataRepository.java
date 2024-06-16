package com.airfrance.repind.dao.delegation;

import com.airfrance.repind.entity.delegation.DelegationData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DelegationDataRepository extends JpaRepository<DelegationData, Integer>, DelegationDataRepositoryCustom {

	@Query("select dd from DelegationData dd where dd.delegate.sgin = :gin")
	public List<DelegationData> findDelegatesByGin(@Param("gin") String gin);

	@Query("select dd from DelegationData dd where dd.delegator.sgin = :gin")
	public List<DelegationData> findDelegatorsByGin(@Param("gin") String gin);
}
