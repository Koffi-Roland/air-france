package com.afklm.rigui.dao.external;

import com.afklm.rigui.entity.external.ExternalIdentifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExternalIdentifierRepository extends JpaRepository<ExternalIdentifier, Long> {
	public List<ExternalIdentifier> findByGin(String gin);

}
