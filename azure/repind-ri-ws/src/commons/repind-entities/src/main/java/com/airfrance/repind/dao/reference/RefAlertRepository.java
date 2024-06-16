package com.airfrance.repind.dao.reference;

import com.airfrance.repind.entity.reference.RefAlert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefAlertRepository extends JpaRepository<RefAlert, String> {
}
