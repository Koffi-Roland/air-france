package com.airfrance.repindutf8.dao;

import com.airfrance.repindutf8.entity.RefUtfKeyType;
import com.airfrance.repindutf8.entity.RefUtfKeyTypeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefUtfKeyTypeRepository extends JpaRepository<RefUtfKeyType, RefUtfKeyTypeId> {
	boolean existsByIdStype(String stype);
	boolean existsByIdStypeAndIdSkey(String stype, String skey);
}
