package com.afklm.cati.common.repository;

import com.afklm.cati.common.entity.RefPreferenceKeyType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefPreferenceKeyTypeRepository extends JpaRepository<RefPreferenceKeyType, Integer> {

    List<RefPreferenceKeyType> findByType(String type);

    @Query("select ref.key from RefPreferenceKeyType ref "
            + "where ref.condition = 'M' "
            + "and ref.type = :type ")
    List<String> getMandatoryKeyForType(@Param("type") String type);

    @Query(value = "SELECT COUNT(*) FROM REF_PREFERENCE_KEY_TYPE refPrefKT, REF_PREFERENCE_TYPE refPrefT WHERE refPrefKT.STYPE = refPrefT.SCODE AND refPrefT.SCODE = :type", nativeQuery = true)
    Long countByType(@Param("type") String type);

    @Query(value = "SELECT COUNT(*) FROM REF_PREFERENCE_KEY_TYPE refPrefKT, REF_PREFERENCE_DATA_KEY refPrefDT WHERE refPrefKT.SKEY = refPrefDT.SCODE AND refPrefKT.SKEY = :key", nativeQuery = true)
    Long countByKey(@Param("key") String key);

    @Query("select distinct refPreferenceKeyType from RefPreferenceKeyType refPreferenceKeyType order by type, refid")
    public List<RefPreferenceKeyType> provideRefPreferenceKeyTypeWithPagination(Pageable pageable);

    List<RefPreferenceKeyType> findAllByOrderByRefId();

}
