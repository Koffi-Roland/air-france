package com.airfrance.repindutf8.dao;

import com.airfrance.repindutf8.entity.Utf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtfRepository extends JpaRepository<Utf, Long>, UtfRepositoryCustom {
	int countBySgin(String sgin);
	int countBySginAndRefUtfTypeScode(String sgin, String stype);
}

