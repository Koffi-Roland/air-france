package com.afklm.rigui.dao.adresse;

import com.afklm.rigui.entity.adresse.FormalizedAdr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormalizedAdrRepository extends JpaRepository<FormalizedAdr, String> {

}
