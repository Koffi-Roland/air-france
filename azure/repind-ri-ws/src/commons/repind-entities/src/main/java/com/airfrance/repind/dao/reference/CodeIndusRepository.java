package com.airfrance.repind.dao.reference;

import com.airfrance.repind.entity.reference.CodeIndus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CodeIndusRepository extends JpaRepository<CodeIndus, String> {
}
