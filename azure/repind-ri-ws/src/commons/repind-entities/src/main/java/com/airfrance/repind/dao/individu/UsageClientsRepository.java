package com.airfrance.repind.dao.individu;

import com.airfrance.repind.entity.individu.UsageClients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsageClientsRepository extends JpaRepository<UsageClients, String> {
	
	List<UsageClients> findBySgin(String gin);
	
	@Query("select usa from UsageClients usa where usa.sgin = :gin and usa.scode = :code")
	List<UsageClients> findByGinAndCode(@Param("gin") String gin, @Param("code") String code);
	
}
