package com.airfrance.repind.dao.reference;

import com.airfrance.repind.entity.reference.RefComPrefDgt;
import com.airfrance.repind.entity.reference.RefComPrefDomain;
import com.airfrance.repind.entity.reference.RefComPrefGType;
import com.airfrance.repind.entity.reference.RefComPrefType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefComPrefDgtRepository extends JpaRepository<RefComPrefDgt, Integer>, RefComPrefDgtRepositoryCustom {
	
	@Query("select refcomprefdgt from RefComPrefDgt refcomprefdgt, RefComPrefDomain domain, RefComPrefType comPrefType, RefComPrefGType comPrefGType "
			+ "where domain.codeDomain=refcomprefdgt.domain "
			+ "and domain.codeDomain= :domain "
			+ "and comPrefType.codeType=refcomprefdgt.type.codeType "
			+ "and comPrefType.codeType= :type "
			+ "and comPrefGType.codeGType=refcomprefdgt.groupType.codeGType "
			+ "and comPrefGType.codeGType= :group ")
	List<RefComPrefDgt> findByDGT(@Param("domain") String domain, @Param("group") String groupType, @Param("type") String type);

	Long countByType(RefComPrefType type);
	
	Long countByGroupType(RefComPrefGType groupType);
	
	Long countByDomain(RefComPrefDomain domain);
	
	List<RefComPrefDgt> findByType(RefComPrefType refComPrefType);

	List<RefComPrefDgt> findAllByOrderByRefComPrefDgtId();

	RefComPrefDgt findByDomainAndGroupTypeAndType(RefComPrefDomain domain, RefComPrefGType groupType, RefComPrefType type);
	
}
