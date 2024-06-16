package com.airfrance.repind.dao.tracking;

import com.airfrance.repind.entity.tracking.TriggerChange;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TriggerChangeRepository extends JpaRepository<TriggerChange, Long> {

	@Query("SELECT tc "
			+ "FROM TriggerChange tc "
			+ "WHERE tc.changeStatus is null AND (tc.siteCreation <> 'BATCH_QVI' or tc.siteCreation is null) AND tc.gin is not null "
			+ "ORDER BY tc.gin")
    List<TriggerChange> findTriggerChange(Pageable pageable);

    
}
