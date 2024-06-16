package com.afklm.repind.ci.tools.docker.images.cleaner.service;

import com.afklm.repind.ci.tools.docker.images.cleaner.entity.DockerImage;
import com.afklm.repind.ci.tools.docker.images.cleaner.repository.DockerImageRepository;
import com.afklm.repind.ci.tools.docker.images.cleaner.rest.RestTemplateExtended;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class DockerImageService {

    private static final int MONTH_SCOPE = 2;

    private DockerImageRepository dockerImageRepository;
    private RestTemplateExtended harborApi;

    @Transactional
    public void add(String repo, String tags){
        List<DockerImage> dockerImages = new ArrayList<>();
        for(String tag : tags.split(",")){
            DockerImage imageFound = dockerImageRepository.findByRepositoryAndTag(repo, tag);
            if(imageFound != null){
                log.warn("This tag already exist in database");
                return;
            }
            DockerImage dockerImage = new DockerImage();
            dockerImage.setTag(tag);
            dockerImage.setRepository(repo);
            dockerImage.setInsertAt(new Date());
            dockerImages.add(dockerImage);
            log.info("Tag added : " + tag);
        }
        dockerImageRepository.saveAll(dockerImages);
    }

    @Transactional
    public void purge(){
        Date now = new Date();
        Date nowMinusTwoMonth = Date.from(LocalDate.now().minusMonths(MONTH_SCOPE).atStartOfDay(ZoneId.systemDefault()).toInstant());

        List<DockerImage> dockerImages = dockerImageRepository.findAllByInsertAtBetweenAndDeleteAtIsNull(nowMinusTwoMonth, now);

        for(DockerImage dockerImage: dockerImages){
            HttpHeaders headers = harborApi.createHeaders();
            String url = String.format("projects/%s/repositories/%s/artifacts/%s", harborApi.getProject(), dockerImage.getRepository(), dockerImage.getTag());
            try{
                harborApi.exchange(
                        url,
                        HttpMethod.DELETE, new HttpEntity<>(headers), Void.class).getBody();
                log.info(String.format("Tag %s in repo %s deleted", dockerImage.getTag(), dockerImage.getRepository()));
            }catch(HttpClientErrorException ex){
                log.warn(String.format("Tag %s in repo %s not found", dockerImage.getTag(), dockerImage.getRepository()));
            }
            dockerImage.setDeleteAt(new Date());
        }
        dockerImageRepository.saveAll(dockerImages);
    }
}
