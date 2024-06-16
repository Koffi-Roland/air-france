package com.airfrance.repindutf8.dao;

import com.airfrance.repindutf8.entity.RefErreur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefErreurRepository extends JpaRepository<RefErreur, Long> {
}
