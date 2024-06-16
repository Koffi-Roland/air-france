package com.airfrance.repindutf8.dao;

import com.airfrance.repindutf8.entity.RefUtfDataKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefUtfDataKeyRepository extends JpaRepository<RefUtfDataKey, Long> {
}
