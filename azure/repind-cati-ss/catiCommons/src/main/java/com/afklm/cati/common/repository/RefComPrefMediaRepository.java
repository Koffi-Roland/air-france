package com.afklm.cati.common.repository;

import com.afklm.cati.common.entity.RefComPrefMedia;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefComPrefMediaRepository extends JpaRepository<RefComPrefMedia, String> {

    List<RefComPrefMedia> findAllByOrderByCodeMedia(Pageable pageable);

    List<RefComPrefMedia> findAllByOrderByCodeMedia();
}
