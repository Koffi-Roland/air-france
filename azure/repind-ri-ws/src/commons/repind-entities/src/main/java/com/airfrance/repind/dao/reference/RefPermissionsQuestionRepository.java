package com.airfrance.repind.dao.reference;

import com.airfrance.repind.entity.reference.RefPermissionsQuestion;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefPermissionsQuestionRepository extends JpaRepository<RefPermissionsQuestion, Integer> {
		
	List<RefPermissionsQuestion> findAllByOrderById(Pageable pageable);
	
	List<RefPermissionsQuestion> findAllByOrderById();

	@Query("select count(*) from RefPermissions ref where ref.refPermissionsId.questionId = :refPermissionsQuestion")
	Long countRefPermissionsById(@Param("refPermissionsQuestion") RefPermissionsQuestion refPermissionsQuestion);
	
}
