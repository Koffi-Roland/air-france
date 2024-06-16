package com.afklm.cati.common.service.impl;

import com.afklm.cati.common.criteria.PaysCriteria;
import com.afklm.cati.common.entity.Pays;
import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.model.ModelPays;
import com.afklm.cati.common.repository.PaysRepository;
import com.afklm.cati.common.service.PaysService;
import com.afklm.cati.common.exception.jraf.JrafDaoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaysImpl implements PaysService {

    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(PaysImpl.class);

    @Autowired
    private PaysRepository paysRepository;

    @Override
    public List<ModelPays> getAllPays() throws CatiCommonsException, JrafDaoException {
        return paysRepository.findAll().stream().map(entity -> mapPaysEntityToResource(entity)).collect(Collectors.toList());
    }

    @Override
    public Optional<ModelPays> getPays(String codePays) throws CatiCommonsException, JrafDaoException {
        return paysRepository.findById(codePays).map(entity -> mapPaysEntityToResource(entity));
    }

    @Transactional
    @Override
    public void updatePays(PaysCriteria paysCriteria) throws CatiCommonsException, JrafDaoException {
        String pays = paysCriteria.getCodePays();
        String isNormalisable = paysCriteria.getNormalisable();
        try {
            paysRepository.updateNormalisableByCodePays(isNormalisable, pays);
            String msg = "Pays updated : codePays '" + pays + "' , normalisable '" + isNormalisable + "'";
            LOGGER.info(msg);
        } catch (Exception e) {
            String msg = "Cannot update Pays of id " + pays;
            LOGGER.error(msg, e);
            throw new CatiCommonsException("Cannot update Pays of id " + pays);
        }
    }

    @Override
    public void addPays(Pays pays) throws CatiCommonsException, JrafDaoException {
        //TODO
        return;
    }

    @Override
    public void removePays(String codePays) throws CatiCommonsException, JrafDaoException {
        //TODO
    }

    /**
     * transform Pays entity to PaysResource POJO
     *
     * @param entity
     * @return
     */
    private ModelPays mapPaysEntityToResource(Pays entity) {
        return new ModelPays(
                entity.getCodePays(),
                entity.getNormalisable(),
                entity.getLibellePays(),
                entity.getCodeIata(),
                entity.getLibellePaysEn(),
                entity.getCodeGestionCP(),
                entity.getCodeCapitale(),
                entity.getFormatAdr(),
                entity.getIformatAdr(),
                entity.getForcage(),
                entity.getIso3Code());
    }

}
