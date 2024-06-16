package com.airfrance.repind.dao.reference;

import com.airfrance.repind.entity.reference.RefOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefOwnerRepository extends JpaRepository<RefOwner, Integer> {
	
}
