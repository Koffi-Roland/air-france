package com.airfrance.repind.dao.tracking;

import com.airfrance.repind.entity.preference.Preference;
import com.airfrance.repind.entity.tracking.TriggerChangeIndividus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TriggerChangeIndividusRepository extends JpaRepository<TriggerChangeIndividus, Long>, TriggerChangeIndividusRepositoryCustom {

    @Transactional
    @Query(nativeQuery = true, value ="UPDATE TRIGGER_CHANGE_INDIVIDUS  set CHANGE_STATUS = ?1 WHERE id IN (?2)")
    @Modifying
    void updateChangeStatusIn(String status, List<Long> tcIdList);

    @Query("select t from TriggerChangeIndividus t "
            + "where t.gin = :gin "
            + "and t.changeTable = :changeTable "
            + "and t.changeType = :changeType "
            + "and t.changeStatus = :changeStatus ")
    List<TriggerChangeIndividus> findByGinAndChangeTableAndChangeTypeAndChangeStatus(@Param("gin") String gin,
                                                                                     @Param("changeTable") String changeTable,
                                                                                     @Param("changeType") String changeType,
                                                                                     @Param("changeStatus") String changeStatus);
}
