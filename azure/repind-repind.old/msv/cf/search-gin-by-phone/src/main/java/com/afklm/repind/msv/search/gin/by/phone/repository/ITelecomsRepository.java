package com.afklm.repind.msv.search.gin.by.phone.repository;

import com.afklm.repind.msv.search.gin.by.phone.entity.Telecoms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ITelecomsRepository extends JpaRepository<Telecoms, String>  {
    Collection<Telecoms> findBySnormInterPhoneNumberAndSstatutMediumInAndScodeMediumIn(String iPhone , Collection<String> iStatus , Collection<String> iScodes);
}
