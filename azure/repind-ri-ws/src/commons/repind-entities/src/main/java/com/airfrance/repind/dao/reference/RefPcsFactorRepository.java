package com.airfrance.repind.dao.reference;

import com.airfrance.repind.entity.reference.RefPcsFactor;
import org.springframework.data.jpa.repository.JpaRepository;

@org.springframework.stereotype.Repository
public interface RefPcsFactorRepository extends JpaRepository<RefPcsFactor, String> {
}
