package com.afklm.batch.detectduplicates.service;

import com.afklm.batch.detectduplicates.repository.SameLastnameNameEmailAndTelecomNoDuplicateRepository;
import com.afklm.batch.detectduplicates.repository.SameLastnameNameEmailAndTelecomRepository;
import com.afklm.batch.detectduplicates.repository.SameLastnameNameEmailOrTelecomRepository;
import com.afklm.batch.detectduplicates.entity.SameLastnameNameEmailAndTelecomEntity;
import com.afklm.batch.detectduplicates.entity.SameLastnameNameEmailAndTelecomNoDuplicateEntity;
import com.afklm.batch.detectduplicates.entity.SameLastnameNameEmailOrTelecomEntity;
import com.airfrance.batch.common.exception.ThreadBusinessProcessException;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@AllArgsConstructor
public class DetectDuplicatesService {

    private static final int CHUNK_SIZE = 10000;

    private JdbcTemplate jdbcTemplateSicOds;
    private FileService fileService;

    private SameLastnameNameEmailAndTelecomNoDuplicateRepository sameLastnameNameEmailAndTelecomNoDuplicateRepository;
    private SameLastnameNameEmailAndTelecomRepository sameLastnameNameEmailAndTelecomRepository;
    private SameLastnameNameEmailOrTelecomRepository sameLastnameNameEmailOrTelecomRepository;
    final int MAX_DETECTED_NBGINS = 250;


    public void launchPlSqlScript(final String relFilePath) throws IOException {
        Instant start = Instant.now();
        log.info(String.format("Launch %s", relFilePath));
        final String sql = fileService.readFile(relFilePath);
        jdbcTemplateSicOds.execute(sql);
        Instant end = Instant.now();
        Duration duration = Duration.between(start, end);
        log.info(String.format("Finish %s => duration : %d sec", relFilePath, duration.getSeconds()));
    }

    public void removeDuplicatesRow() {
        Instant start = Instant.now();
        log.info("Launch removeDuplicatesRow");
        Page<SameLastnameNameEmailAndTelecomEntity> page = sameLastnameNameEmailAndTelecomRepository.findAllByDuplicateIs(true, Pageable.ofSize(CHUNK_SIZE));
        List<SameLastnameNameEmailAndTelecomEntity> list = page.getContent();
        boolean isFinish = false;
        while (!isFinish) {
            String lastLastnameName = null;
            List<SameLastnameNameEmailAndTelecomNoDuplicateEntity> listNoDup = new ArrayList<>();
            for (SameLastnameNameEmailAndTelecomEntity entity : list) {
                if (!entity.getLastnameName().equals(lastLastnameName)) {
                    lastLastnameName = entity.getLastnameName();
                    List<SameLastnameNameEmailAndTelecomEntity> dups = sameLastnameNameEmailAndTelecomRepository
                            .findAllByLastnameNameAndAndTelecomNbGINsIsGreaterThanAndEmailNbGINsIsGreaterThan(entity.getLastnameName(), entity.getTelecomNbGINs(), entity.getEmailNbGINs());
                    if (!dups.isEmpty()) {
                        SameLastnameNameEmailAndTelecomEntity bestEl = searchBestElement(dups);
                        listNoDup.add(toNoDuplicate(bestEl));
                    } else {
                        listNoDup.add(toNoDuplicate(entity));
                    }
                }
            }
            sameLastnameNameEmailAndTelecomNoDuplicateRepository.saveAll(listNoDup);

            isFinish = page.isLast();
            if(!isFinish) {
                page = sameLastnameNameEmailAndTelecomRepository.findAllByDuplicateIs(true, page.nextPageable());
                list = page.getContent();
            }
        }
        Instant end = Instant.now();
        Duration duration = Duration.between(start, end);
        log.info(String.format("Finish removeDuplicatesRow => duration : %d sec", duration.getSeconds()));
    }

    public void removeDuplicatesRowForEmailOrTelecom() {
        Instant start = Instant.now();
        log.info("Launch removeDuplicatesRowForEmailOrTelecom");
        Page<SameLastnameNameEmailOrTelecomEntity> page = sameLastnameNameEmailOrTelecomRepository.findAllByDuplicateIs(true, Pageable.ofSize(CHUNK_SIZE));
        List<SameLastnameNameEmailOrTelecomEntity> list = page.getContent();
        boolean isFinish = false;
        while (!isFinish) {
            String lastLastnameName = null;
            List<SameLastnameNameEmailAndTelecomNoDuplicateEntity> listNoDup = new ArrayList<>();
            for (SameLastnameNameEmailOrTelecomEntity entity : list) {
                if (!entity.getLastnameName().equals(lastLastnameName)) {
                    lastLastnameName = entity.getLastnameName();
                    List<SameLastnameNameEmailOrTelecomEntity> dups = sameLastnameNameEmailOrTelecomRepository
                            .findAllByLastnameNameAndNbGINsGreaterThan(entity.getLastnameName(), entity.getNbGINs());
                    if (!dups.isEmpty()) {
                        SameLastnameNameEmailOrTelecomEntity bestEl = getBestElement(dups);
                        listNoDup.add(toNoDuplicate(bestEl));
                    } else {
                        listNoDup.add(toNoDuplicate(entity));
                    }
                }
            }
            sameLastnameNameEmailAndTelecomNoDuplicateRepository.saveAll(listNoDup);
            isFinish = page.isLast();
            if(!isFinish) {
                page = sameLastnameNameEmailOrTelecomRepository.findAllByDuplicateIs(true, page.nextPageable());
                list = page.getContent();
            }
        }
        Instant end = Instant.now();
        Duration duration = Duration.between(start, end);
        log.info(String.format("Finish removeDuplicatesRowForEmailOrTelecom => duration : %d sec", duration.getSeconds()));
    }

    public void writeResultFile(String path, String outputFile) throws InterruptedException {
        Instant start = Instant.now();
        ExecutorService executor = Executors.newCachedThreadPool();
        Page<SameLastnameNameEmailAndTelecomNoDuplicateEntity> page = sameLastnameNameEmailAndTelecomNoDuplicateRepository.findAll(Pageable.ofSize(CHUNK_SIZE));
        List<SameLastnameNameEmailAndTelecomNoDuplicateEntity> list = page.getContent();
        boolean isFinish = false;
        while (!isFinish) {
            List<SameLastnameNameEmailAndTelecomNoDuplicateEntity> finalList = list;
            executor.execute(() -> {
                StringBuilder str = new StringBuilder();
                for(SameLastnameNameEmailAndTelecomNoDuplicateEntity entity: finalList){
                    if(entity.getNbGINs() > MAX_DETECTED_NBGINS){
                        splitOutputByMultipleLines(str, entity);
                    }else {
                        str.append(toCsvRow(entity));
                    }
                }
                try {
                    fileService.writeInFile(path, outputFile, str.toString());
                } catch (IOException e) {
                    throw new ThreadBusinessProcessException(e.getMessage());
                }
            });

            isFinish = page.isLast();
            if(!isFinish){
                page = sameLastnameNameEmailAndTelecomNoDuplicateRepository.findAll(page.nextPageable());
                list = page.getContent();
            }
        }
        executor.shutdown();
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
        Instant end = Instant.now();
        Duration duration = Duration.between(start, end);
        log.info(String.format("Finish writeResultFile => duration : %d sec", duration.getSeconds()));
    }

    /**
     * if the output entity have more than 300 NbGins duplicated, we will split result by multiple
     * line of 300. This limitation is because output file will be merged in another batch that can't processes more than
     * this limit per MS call. ( we use 250NbGins for safety)
     * @param str
     * @param entity
     */
    private void splitOutputByMultipleLines(StringBuilder str, SameLastnameNameEmailAndTelecomNoDuplicateEntity entity) {
        String[] parts = entity.getGins().split(",");
        int nbgins = entity.getNbGINs();
        int startidx = 0;
        while(nbgins > 0){
            int actualNbGins = Math.min(nbgins, MAX_DETECTED_NBGINS);
            List<String> tmpl = Arrays.asList(parts);
            List<String> out = tmpl.subList(startidx, startidx+actualNbGins);
            SameLastnameNameEmailAndTelecomNoDuplicateEntity subentity =
                    new SameLastnameNameEmailAndTelecomNoDuplicateEntity(entity.getLastnameName(), entity.getElementDuplicate(),
                            String.join(",", out), entity.getNbContract(), actualNbGins);
            startidx += MAX_DETECTED_NBGINS;
            nbgins -= MAX_DETECTED_NBGINS;
            str.append(toCsvRow(subentity));
        }
    }

    private String toCsvRow(SameLastnameNameEmailAndTelecomNoDuplicateEntity entity){
        return String.format("%s;%d;%s%n", entity.getElementDuplicate(), entity.getNbGINs(), entity.getGins());
    }

    private SameLastnameNameEmailAndTelecomNoDuplicateEntity toNoDuplicate(SameLastnameNameEmailAndTelecomEntity entity) {
        SameLastnameNameEmailAndTelecomNoDuplicateEntity noDup = new SameLastnameNameEmailAndTelecomNoDuplicateEntity();
        noDup.setLastnameName(entity.getLastnameName());
        noDup.setElementDuplicate(entity.getEmail() + "," + entity.getTelecom());
        noDup.setGins(entity.getEmailNbGINs() > entity.getTelecomNbGINs() ? entity.getEmailGINs() : entity.getTelecomGINs());
        noDup.setNbContract(entity.getEmailNbGINs() > entity.getTelecomNbGINs() ? entity.getEmailNbContract() : entity.getTelecomNbContract());
        noDup.setNbGINs(entity.getEmailNbGINs() > entity.getTelecomNbGINs() ? entity.getEmailNbGINs() : entity.getTelecomNbGINs());
        return noDup;
    }

    private SameLastnameNameEmailAndTelecomNoDuplicateEntity toNoDuplicate(SameLastnameNameEmailOrTelecomEntity entity) {
        SameLastnameNameEmailAndTelecomNoDuplicateEntity noDup = new SameLastnameNameEmailAndTelecomNoDuplicateEntity();
        noDup.setLastnameName(entity.getLastnameName());
        noDup.setElementDuplicate(entity.getElementDuplicate());
        noDup.setGins(entity.getGins());
        noDup.setNbContract(entity.getNbContract());
        noDup.setNbGINs(entity.getNbGINs());
        return noDup;
    }

    private SameLastnameNameEmailAndTelecomEntity searchBestElement(List<SameLastnameNameEmailAndTelecomEntity> entities) {
        int maxTelNbGINs = entities.get(0).getTelecomNbGINs();
        int maxEmailNbGINs = entities.get(0).getEmailNbGINs();
        SameLastnameNameEmailAndTelecomEntity result = entities.get(0);
        for (SameLastnameNameEmailAndTelecomEntity entity : entities) {
            if (entity.getTelecomNbGINs() > maxTelNbGINs && entity.getEmailNbGINs() > maxEmailNbGINs) {
                result = entity;
                maxEmailNbGINs = result.getEmailNbGINs();
                maxTelNbGINs = result.getTelecomNbGINs();
            }
        }
        return result;
    }

    private SameLastnameNameEmailOrTelecomEntity getBestElement(List<SameLastnameNameEmailOrTelecomEntity> entities) {
        int maxNbGINs = entities.get(0).getNbGINs();
        SameLastnameNameEmailOrTelecomEntity result = entities.get(0);
        for (SameLastnameNameEmailOrTelecomEntity entity : entities) {
            if (entity.getNbGINs() > maxNbGINs) {
                result = entity;
                maxNbGINs = result.getNbGINs();
            }
        }
        return result;
    }

    /**
     * This method group individuals by NOMPRENOM
     * and keep only those with same NOMPRENOM repeated more than once
     * + a field of is_checked let us know if we already checked all its duplicates
     * + when all elements are checked we recreate the table again from zero
     * @throws IOException
     */
    public void selectDuplicatedCandidatesByNomPrenom() throws IOException {
        String query = "select count(*) from DQ_MGE_SAME_NOMPRE_CANDIDATES where is_checked=0 ";
        boolean createTable = false;
        final int LIMIT_TO_SYNC_WITH_INDALLS = 0;
        try {
            int count = jdbcTemplateSicOds.queryForObject(query, Integer.class);
            if (count <= LIMIT_TO_SYNC_WITH_INDALLS) {
                createTable = true;
            }
        }catch(Exception e){
            log.info("Inside method exception for SelectDuplicatedCandidates [Table not found]");
            createTable = true;
        }
        if (createTable) {
            launchPlSqlScript("metier/scripts/SQL/DETECT_DUPLICATES/table_same_nomprenom_candidates.sql");
        }
    }

}
