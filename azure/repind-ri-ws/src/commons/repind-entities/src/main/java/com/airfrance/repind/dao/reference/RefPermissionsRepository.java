package com.airfrance.repind.dao.reference;

import com.airfrance.repind.entity.reference.RefComPrefDgt;
import com.airfrance.repind.entity.reference.RefPermissions;
import com.airfrance.repind.entity.reference.RefPermissionsId;
import com.airfrance.repind.entity.reference.RefPermissionsQuestion;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefPermissionsRepository extends JpaRepository<RefPermissions, RefPermissionsId> {

	@Query("select permissions from RefPermissions permissions "
			+ "where permissions.refPermissionsId.questionId.id = :permissionQuestionId")
	List<RefPermissions> getAllComPrefDGTByPermissionsQuestionId (@Param("permissionQuestionId") int permissionQuestionId);

	@Query("select count(distinct refPermissions.refPermissionsId.questionId.id) from RefPermissions refPermissions")
	Long countPermissions();

	@Query("select distinct refPermissions.refPermissionsId.questionId.id from RefPermissions refPermissions "
			+ "order by refPermissions.refPermissionsId.questionId.id")
	List<Integer> providePermissionsQuestionIdWithPagination(Pageable pageable);

	@Query("select refPermissions from RefPermissions refPermissions "
			+ "where refPermissions.refPermissionsId.questionId.id in (:listPermissionsId)")
	List<RefPermissions> providePermissionsWithPagination(@Param("listPermissionsId") List<Integer> listPermissionsId);

	@Query("select ref.refPermissionsId.refComPrefDgt.refComPrefDgtId from RefPermissions ref where ref.refPermissionsId.questionId = :refPermissionsQuestion")
	List<Integer> getRefComPrefDgtByRefPermissionsQuestion(@Param("refPermissionsQuestion") RefPermissionsQuestion refPermissionsQuestion);
	
	@Query("select ref.refPermissionsId from RefPermissions ref where ref.refPermissionsId.questionId = :refPermissionsQuestion")
	RefPermissionsId getRefPermissionsIdByRefComPrefDgt(RefComPrefDgt refComPrefDgt);
	
}
