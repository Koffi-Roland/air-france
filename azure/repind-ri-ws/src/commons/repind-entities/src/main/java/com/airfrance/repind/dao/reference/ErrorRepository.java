package com.airfrance.repind.dao.reference;

import com.airfrance.repind.entity.reference.Error;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ErrorRepository extends JpaRepository<Error, String> {
}
