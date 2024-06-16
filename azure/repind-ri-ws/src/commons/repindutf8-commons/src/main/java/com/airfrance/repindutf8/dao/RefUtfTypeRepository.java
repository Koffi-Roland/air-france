package com.airfrance.repindutf8.dao;

import com.airfrance.repindutf8.entity.RefUtfType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefUtfTypeRepository extends JpaRepository<RefUtfType, String> {
}
