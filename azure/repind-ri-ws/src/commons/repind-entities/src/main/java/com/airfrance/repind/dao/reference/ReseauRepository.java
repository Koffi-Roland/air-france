package com.airfrance.repind.dao.reference;

import com.airfrance.repind.entity.reference.Reseau;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReseauRepository extends JpaRepository<Reseau, String> {
}
