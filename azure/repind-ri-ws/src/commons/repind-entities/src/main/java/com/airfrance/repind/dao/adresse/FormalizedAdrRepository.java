package com.airfrance.repind.dao.adresse;

import com.airfrance.repind.entity.adresse.FormalizedAdr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormalizedAdrRepository extends JpaRepository<FormalizedAdr, String> {

}
