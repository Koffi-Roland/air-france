package com.afklm.rigui.dao.reference;

import com.afklm.rigui.entity.reference.Error;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ErrorRepository extends JpaRepository<Error, String> {
}
