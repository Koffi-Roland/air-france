package com.airfrance.repind.service.reference.internal;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dao.reference.ErrorRepository;
import com.airfrance.repind.dto.reference.ErrorDTO;
import com.airfrance.repind.dto.reference.ErrorTransform;
import com.airfrance.repind.entity.reference.Error;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Optional;

@Service
public class ErrorDS {

    /** reference sur le dao principal */
    @Autowired
    private ErrorRepository errorRepository;
    //private IErrorDAO mainDao;

    @Transactional(readOnly=true)
    public ErrorDTO get(ErrorDTO dto) throws JrafDomainException {
        return get(dto.getErrorCode());
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly=true)
    public ErrorDTO get(Serializable oid) throws JrafDomainException {
    	Optional<Error> error = errorRepository.findById((String)oid);
        // transformation light bo -> dto
        if (!error.isPresent()) {
        	return null;
        }
        return ErrorTransform.bo2DtoLight(error.get());
    }

    /** 
     * getErrorDetails
     * @param ErrorCode in String
     * @return The getErrorDetails as <code>ErrorDTO</code>
     * @throws JrafDomainException en cas d'exception
     */
    @Deprecated
    @Transactional(readOnly=true)
    public ErrorDTO getErrorDetails(String ErrorCode) throws JrafDomainException {
    	return get(ErrorCode);
    }
    
}
