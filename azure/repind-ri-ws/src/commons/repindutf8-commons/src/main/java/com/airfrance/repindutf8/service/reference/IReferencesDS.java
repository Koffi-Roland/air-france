package com.airfrance.repindutf8.service.reference;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repindutf8.dto.reference.RefErreurDTO;
import com.airfrance.repindutf8.service.reference.internal.ReferencesDS;

import java.util.List;
public interface IReferencesDS{
    
    /** 
     * callError
     * @param numError in String
     * @param details in String
     * @return The callError as <code>String</code>
     * @throws JrafDomainException en cas d'exception
     * @see ReferencesDS#callError(String numError, String details)
     */
    String callError(String numError, String details) throws JrafDomainException;

	List<RefErreurDTO> findByExample(RefErreurDTO dto) throws JrafDomainException;
}
