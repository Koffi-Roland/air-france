package com.afklm.repind.ci.tools.docker.images.cleaner.repository;

import com.afklm.repind.ci.tools.docker.images.cleaner.entity.DockerImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface DockerImageRepository extends JpaRepository<DockerImage, Long> {
    DockerImage findByRepositoryAndTag(String repository, String tag);
    List<DockerImage> findAllByInsertAtBetweenAndDeleteAtIsNull(Date min, Date max);
}

