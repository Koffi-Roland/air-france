package com.afklm.repind.common.repository.contact;

import com.afklm.repind.common.entity.contact.UsageMedium;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsageMediumRepository extends JpaRepository<UsageMedium, String> {

    List<UsageMedium> findUsageMediumByAinAdr(String ain);
}
