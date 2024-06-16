package com.airfrance.repind.service.profil.internal;

import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.MissingParameterException;
import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.exception.jraf.JrafDomainNoRollbackException;
import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;
import com.airfrance.repind.dao.profil.Profil_afRepository;
import com.airfrance.repind.dao.profil.Profil_mereRepository;
import com.airfrance.repind.dto.profil.Profil_afDTO;
import com.airfrance.repind.dto.profil.Profil_afTransform;
import com.airfrance.repind.dto.profil.Profil_mereDTO;
import com.airfrance.repind.dto.profil.Profil_mereTransform;
import com.airfrance.repind.entity.profil.Profil_af;
import com.airfrance.repind.entity.profil.Profil_mere;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class ProfilMereDS {

    @Autowired
    private Profil_mereRepository profil_mereRepository;
	
    @Autowired
    private Profil_afRepository profil_afRepository;

	public void setProfil_mereRepository(Profil_mereRepository profil_mereRepository) {
		this.profil_mereRepository = profil_mereRepository;
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void deleteProfil_mere(Profil_mereDTO dto) throws JrafDomainException {
		profil_mereRepository.deleteById(dto.getIcle_prf());
	}

	/*PROTECTED REGION ID(_k7Fj4EjcEeaaO77HTw9BUw u m) ENABLED START*/
    // add your custom methods here if necessary
    
    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public String createAProfilMere(Profil_mereDTO dto)throws JrafDomainException {
    	
    	Profil_mere p_mere = new Profil_mere(); 
    	Profil_mereTransform.dto2BoLight(dto, p_mere);
		profil_mereRepository.saveAndFlush(p_mere);
		Profil_mereTransform.dto2BoLink(dto, p_mere);
		profil_afRepository.saveAndFlush(p_mere.getProfil_af());
		return (p_mere.getIcle_prf().toString());
	}

    
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public String updateAProfilAFProfilMere(Profil_mereDTO dto)
			throws JrafDaoException, JrafDomainException {
    	Profil_mere p_mere = profil_mereRepository.getOne(dto.getIcle_prf());
		Profil_af p_af = p_mere.getProfil_af();
		p_mere.setSgin_ind(dto.getSgin_ind());
		p_mere.setStype(dto.getStype());
		Profil_afDTO p_afDTO = dto.getProfil_afdto();
		p_af.setIcle_prf(p_afDTO.getIcle_prf());
		p_af.setSmatricule(p_afDTO.getSmatricule());
		p_af.setSrang(p_afDTO.getSrang());
		p_af.setSadr_notes(p_afDTO.getSadr_notes());
		p_af.setSpasswrd(p_afDTO.getSadr_notes());
		p_af.setSnom_prf_hab(p_afDTO.getSnom_prf_hab());
		p_af.setSfonction(p_afDTO.getSfonction());
		p_af.setSreference_r(p_afDTO.getSreference_r());
		p_af.setStypologie(p_afDTO.getStypologie());
		p_af.setScode_origine(p_afDTO.getScode_origine());
		p_af.setScode_cie(p_afDTO.getScode_cie());
		p_af.setScode_status(p_afDTO.getScode_status());
		profil_mereRepository.saveAndFlush(p_mere);
		
		return p_mere.getIcle_prf().toString();
	}

    @Transactional(readOnly=true)
	public List<Profil_afDTO> getProfilAfByMatricule(String matricule) throws JrafDomainException {
		List<Profil_afDTO> resultList = null;
		
		List<Profil_af> profilList = profil_afRepository.getProfilAfByMatricule(matricule);
		
		if (profilList != null && !profilList.isEmpty()) {
			resultList = new ArrayList<Profil_afDTO>();
			for (Profil_af paf : profilList) {
				Profil_afDTO pafDto = Profil_afTransform.bo2DtoLight(paf);
				resultList.add(pafDto);
			}
		}
		
		return resultList;
	}

    @Transactional(readOnly=true)
    public Set<Profil_mereDTO> findByGin(String gin) throws JrafDomainException {
    	
    	return Profil_mereTransform.bo2Dto(profil_mereRepository.findByGinInd(gin));
    }

    @Transactional(readOnly=true)
    public boolean isAfStaff(String gin) throws InvalidParameterException, MissingParameterException {
    	if(StringUtils.isBlank(gin)) {
    		throw new MissingParameterException("Gin cannot be null");
    	}
    	Profil_mere profil = new Profil_mere();
    	profil.setSgin_ind(gin);
    	profil.setStype("AF");
    	return profil_mereRepository.count(Example.of(profil)) != 0;
    }
}
