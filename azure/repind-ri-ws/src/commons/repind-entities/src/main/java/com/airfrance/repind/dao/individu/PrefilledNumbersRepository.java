package com.airfrance.repind.dao.individu;

import com.airfrance.repind.entity.individu.PrefilledNumbers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrefilledNumbersRepository extends JpaRepository<PrefilledNumbers, Integer> {
    /**
     * Find prefilled numbers associated to provided GIN
     */
	@Query(value="select pre from Individu ind "
			+"right outer join ind.prefilledNumbers pre "
			+"where ind.sgin=:gin")
	public List<PrefilledNumbers> findPrefilledNumbers(@Param("gin") String gin);

}
