package com.airfrance.repind.service.zone.internal;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dao.zone.ZoneVenteRepository;
import com.airfrance.repind.dto.zone.*;
import com.airfrance.repind.entity.zone.LienIntCpZd;
import com.airfrance.repind.entity.zone.ZoneVente;
import com.airfrance.repind.service.internal.unitservice.zone.ZoneCommUS;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ZoneDS {

	/** logger */
	private static final Log log = LogFactory.getLog(ZoneDS.class);

    @PersistenceContext(unitName="entityManagerFactoryRepind")
    private EntityManager entityManager;

    @Autowired
	private ZoneVenteRepository zoneVenteRepository;

    @Autowired
	private ZoneCommUS zoneCommUS;

    private final int MAX_LEVEL_ZV = 3;

    @Transactional(readOnly=true)
    public List<ZoneDecoupDTO> getZoneByCity(String cityCode, String countryCode) throws JrafDomainException {
        /*PROTECTED REGION ID(_1KnrMDO8EeKT_JQCdHEO1w) ENABLED START*/
    	
    	List<ZoneDecoupDTO> zdList = new ArrayList<ZoneDecoupDTO>();
    	LienIntCpZdDTO dto = new LienIntCpZdDTO();
    	
    	dto.setCodeVille(cityCode);
    	dto.setDateFinLien(null);
    	//dto.setCodePays(countryCode);
    	dto.setUsage("RA");
    	
    	LienIntCpZd bo = LienIntCpZdTransform.dto2BoLight(dto);
    	String query = "SELECT distinct c FROM LienIntCpZd c WHERE c.usage = :custUsage AND c.codeVille = :cityCode AND c.dateFinLien is NULL";
    	Query q = entityManager.createQuery(query);
    	q.setParameter("custUsage", dto.getUsage());
    	q.setParameter("cityCode", dto.getCodeVille());
    	
        
    	List<LienIntCpZd> returnedBo = q.getResultList();
    	
		for (LienIntCpZd currBo : returnedBo) {
			LienIntCpZdDTO returnedDto = new LienIntCpZdDTO();
			LienIntCpZdTransform.bo2Dto(currBo, returnedDto);
			if (returnedDto.getDateFinLien() == null
					|| returnedDto.getDateFinLien().after(new Date())) {

				zdList.add(returnedDto.getZoneDecoup());
			}
		}
    	
    	return zdList;
    }

	@Transactional(readOnly=true)
	public ZoneVenteDTO findSaleZoneByGin(Long gin) {
		ZoneVenteDTO res = null;
    	if (gin != null) {
    		try {
				res = ZoneVenteTransform.bo2DtoLight(zoneVenteRepository.findByGin(gin));
			} catch (JrafDomainException e) {
				log.error(e.getMessage());
			}
		}

    	return res;
	}

	@Transactional(readOnly = true)
	public ZoneCommDTO getZcFromZv(ZoneVenteDTO zv, int level) throws JrafDomainException {
    	// Level can be 0, 1, 2 or 3

		ZoneCommDTO zc = null;
		ZoneVenteDTO zvFound = null;
		if (zv == null || level > MAX_LEVEL_ZV) {
			return null;
		}

		if (zv.getZv0() == null && StringUtils.isNotBlank(zv.getZvAlpha())) {
			// Find by ZVAlpha
			zvFound = getHierarchyZvByZvAlpha(zv);
		}
		else {
			zvFound = getHierarchyByZv(zv);
		}

		if (zvFound != null) {
			zc = zoneCommUS.getByZoneVente(zvFound);
		}

		return zc;
	}

	private ZoneVenteDTO getHierarchyByZv(ZoneVenteDTO zv) throws JrafDomainException {
    	if (zv == null) {
    		return null;
		}

		ZoneVente zvFound = null;

    	Integer zv0 = zv.getZv0();
		Integer zv1 = zv.getZv1();
		Integer zv2 = zv.getZv2();
		Integer zv3 = zv.getZv3();
		String zvAlpha = zv.getZvAlpha();

		if (zv0 != null) {
			if (zv1 != null) {
				if (zv2 != null) {
					if (zv3 != null) {
						// All zv are present, search a matching with the 4 zvs
						zvFound = zoneVenteRepository.findActiveByZv0Zv1Zv2Zv3(zv0, zv1, zv2, zv3);
					}
					else {
						// only ZV3 is empty
						zvFound = zoneVenteRepository.findActiveByZv2(zv0, zv1, zv2);
					}
				}
				else {
					// no ZV2 and no ZV3
					zvFound = zoneVenteRepository.findActiveByZv1(zv0, zv1);
				}
			}
			else {
				// only ZV0
				zvFound = zoneVenteRepository.findActiveByZv0(zv0);
			}
		}

		return zvFound != null ? ZoneVenteTransform.bo2DtoLight(zvFound) : null;
	}

	private ZoneVenteDTO getHierarchyZvByZvAlpha(ZoneVenteDTO zv) throws JrafDomainException {
    	if (zv == null) {
    		return null;
		}
    	return ZoneVenteTransform.bo2DtoLight(zoneVenteRepository.findActiveByZvAlpha(zv.getZvAlpha()));
	}

	/**
	 * Find Zone Vente using the couple Zv0, Zv1, Zv2, Zv3
	 * @param zv0
	 * @param zv1
	 * @param zv2
	 * @param zv3
	 * @return
	 * @throws JrafDomainException
	 */
	@Transactional(readOnly=true)
	public ZoneVenteDTO findZoneVenteByZv0Zv1Zv2Zv3(@NotNull int zv0, @NotNull int zv1, @NotNull int zv2, @NotNull int zv3) throws JrafDomainException {
		List<ZoneVente> resDB = zoneVenteRepository.findAllByZv3(zv0, zv1, zv2, zv3);

		if (!CollectionUtils.isEmpty(resDB)) {
			// get the first result
			return  ZoneVenteTransform.bo2DtoLight(resDB.get(0));
		}
		else  {
			return null;
		}
	}
}
