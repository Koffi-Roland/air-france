package com.airfrance.repind.dao.reference;

import com.airfrance.repind.entity.reference.RefFctProOwner;
import com.airfrance.repind.entity.reference.RefFctProOwnerId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefFctProOwnerRepository extends JpaRepository<RefFctProOwner, RefFctProOwnerId> {


    
}
