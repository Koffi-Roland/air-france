package com.airfrance.repind.dao.reference;

import com.airfrance.repind.entity.reference.Activite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActiviteRepository extends JpaRepository<Activite, String>{
	public Optional<Activite> findByActivite(String activite);
}
