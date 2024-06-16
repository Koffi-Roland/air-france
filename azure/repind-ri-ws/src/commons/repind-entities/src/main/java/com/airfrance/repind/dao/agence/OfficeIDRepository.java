package com.airfrance.repind.dao.agence;

import com.airfrance.repind.entity.agence.OfficeID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfficeIDRepository extends JpaRepository<OfficeID, Long> {
	
	List<OfficeID> findByCodeGDSAndOfficeID(String codeGDS, String officeID);
	
	List<OfficeID> findByCodeGDSAndOfficeIDIgnoreCase(String codeGDS, String officeID);
	
	List<OfficeID> findByCodeGDSAndOfficeIDAndAgenceGin(String codeGDS, String officeID, String gin);

	List<OfficeID> findByAgenceGinOrderByRowNum(String gin);

	List<OfficeID> findByAgenceGin(String gin);
	
}
