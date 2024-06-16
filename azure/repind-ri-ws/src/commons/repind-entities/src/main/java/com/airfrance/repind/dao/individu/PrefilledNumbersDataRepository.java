package com.airfrance.repind.dao.individu;

import com.airfrance.repind.entity.individu.PrefilledNumbersData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrefilledNumbersDataRepository extends JpaRepository<PrefilledNumbersData, Integer> {
}
