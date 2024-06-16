package com.afklm.rigui.services.reference.internal;

import com.afklm.rigui.exception.jraf.JrafDomainException;
import com.afklm.rigui.dao.reference.RefPreferenceTypeRepository;
import com.afklm.rigui.dto.reference.RefPreferenceTypeDTO;
import com.afklm.rigui.dto.reference.RefPreferenceTypeTransform;
import com.afklm.rigui.entity.reference.RefPreferenceType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class RefPreferenceTypeDS {

    /** logger */
    private static final Log log = LogFactory.getLog(RefPreferenceTypeDS.class);

    @Autowired
    private RefPreferenceTypeRepository refPreferenceTypeRepository;

    @Transactional(readOnly=true)
    public RefPreferenceTypeDTO get(RefPreferenceTypeDTO dto) throws JrafDomainException {
        return get(dto.getCode());
    }

    @Transactional(readOnly=true)
    public RefPreferenceTypeDTO get(String id) throws JrafDomainException {
    	Optional<RefPreferenceType> refPreferenceType = refPreferenceTypeRepository.findById(id);
        if (!refPreferenceType.isPresent()) {
        	return null;
        }
        return RefPreferenceTypeTransform.bo2DtoLight(refPreferenceType.get());
    }
    
    public RefPreferenceTypeRepository getRefPreferenceTypeRepository() {
		return refPreferenceTypeRepository;
	}

	public void setRefPreferenceTypeRepository(RefPreferenceTypeRepository refPreferenceTypeRepository) {
		this.refPreferenceTypeRepository = refPreferenceTypeRepository;
	}

    /**
     * 
     * @return All preference type from data base
     */
    @Transactional(readOnly=true)
	public List<RefPreferenceTypeDTO> getAll() {

		Function<RefPreferenceType, RefPreferenceTypeDTO> transformToDto = ref -> {
			try {
				return RefPreferenceTypeTransform.bo2DtoLight(ref);

			} catch (JrafDomainException e) {
				 log.error(e.getMessage());
				 return null;
			}
		};

		List<RefPreferenceTypeDTO> result = refPreferenceTypeRepository.findAll().stream().map(transformToDto)
				.collect(Collectors.toList());
		return result;
	}
}
