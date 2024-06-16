package com.airfrance.repind.dao.reference;

import com.airfrance.repind.entity.reference.RefTypeEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefTypeEventRepository extends JpaRepository<RefTypeEvent, String> {
}
