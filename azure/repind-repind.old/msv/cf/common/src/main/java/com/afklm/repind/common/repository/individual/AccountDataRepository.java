package com.afklm.repind.common.repository.individual;

import com.afklm.repind.common.entity.individual.AccountData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountDataRepository extends JpaRepository<AccountData, Integer> {

    AccountData findAccountDataByGin(String gin);
}
