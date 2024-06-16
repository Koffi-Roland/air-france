package com.airfrance.repind.dao.reference;

import com.airfrance.repind.entity.reference.RefProductOwner;
import com.airfrance.repind.entity.reference.RefProductOwnerId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefProductOwnerRepository extends JpaRepository<RefProductOwner, RefProductOwnerId> {
	
	@Query("select ref from RefProductOwner ref "
			+ "where ref.id.refOwner.ownerId IN (:ownersId) "
			+ "and ref.id.refProduct.productId IN (:productsId)")
    List<RefProductOwner> getAssociationsByOwnersAndProducts(@Param("ownersId") List<Integer> ownersId, @Param("productsId") List<Integer> productsId);

	@Query("select ref from RefProductOwner ref "
			+ "where ref.id.refOwner.ownerId IN (:ownersId)")
    List<RefProductOwner> getAssociationsByOwners(@Param("ownersId") List<Integer> ownersId);

	@Query("select ref from RefProductOwner ref "
			+ "where ref.id.refProduct.productId IN (:productsId)")
    List<RefProductOwner> getAssociationsByProducts(@Param("productsId") List<Integer> productsId);

}
