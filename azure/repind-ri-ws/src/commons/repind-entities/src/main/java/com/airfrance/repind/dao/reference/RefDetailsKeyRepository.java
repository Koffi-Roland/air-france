package com.airfrance.repind.dao.reference;

import com.airfrance.repind.entity.reference.RefDetailsKey;
import com.airfrance.repind.entity.reference.RefTypeEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefDetailsKeyRepository extends JpaRepository<RefDetailsKey, String> {    
        
    @Query("SELECT rdk FROM RefDetailsKey rdk WHERE rdk.typeEvent = :type ORDER BY rdk.code")
    List<RefDetailsKey> findRefDetailsKeyByTypeEvent(@Param("type") RefTypeEvent type);
}
