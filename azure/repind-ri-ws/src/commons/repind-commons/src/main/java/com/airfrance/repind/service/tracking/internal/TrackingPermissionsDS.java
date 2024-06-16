package com.airfrance.repind.service.tracking.internal;

import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.exception.jraf.JrafDomainNoRollbackException;
import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;
import com.airfrance.repind.dao.tracking.TrackingPermissionsRepository;
import com.airfrance.repind.dto.compref.PermissionDTO;
import com.airfrance.repind.dto.compref.PermissionsDTO;
import com.airfrance.repind.dto.tracking.TrackingPermissionsDTO;
import com.airfrance.repind.dto.tracking.TrackingPermissionsTransform;
import com.airfrance.repind.entity.tracking.TrackingPermissions;
import com.airfrance.repind.service.individu.internal.IndividuDS;
import com.airfrance.repind.service.reference.internal.RefPermissionsQuestionDS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class TrackingPermissionsDS {
		
    /** logger */
    //private static final Log log = LogFactory.getLog(RefPermissionsQuestionDS.class);
    
    @Autowired
    private TrackingPermissionsRepository trackingPermissionsRepository;
    
    @Autowired
	private IndividuDS individuDS;
    
    @Autowired
	private RefPermissionsQuestionDS refPermissionsQuestionDS;

    @Transactional(readOnly=true)
	public TrackingPermissionsDTO getById(Integer id) throws JrafDomainException {
		
		Optional<TrackingPermissions> entity = trackingPermissionsRepository.findById(id);
		
		if (!entity.isPresent()) {
			return null;
		}
		
		return TrackingPermissionsTransform.bo2Dto(entity.get());
	}
    
    public int getByGin(String gin) throws JrafDomainException {
    	List<TrackingPermissions> results = trackingPermissionsRepository.findBySgin_sgin(gin);
    	return results.size();
    }
	
    
    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void create(TrackingPermissionsDTO trackingPermissionsDTO) throws JrafDomainException {
				
		TrackingPermissions entity = TrackingPermissionsTransform.dto2Bo(trackingPermissionsDTO);
		
		trackingPermissionsRepository.saveAndFlush(entity);
		
		TrackingPermissionsTransform.bo2Dto(entity, trackingPermissionsDTO);
	}
	
    
    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void update(TrackingPermissionsDTO trackingPermissionsDTO) throws JrafDomainException {
		
		TrackingPermissionsDTO trackingPermissionsDTOfromDB = getById(trackingPermissionsDTO.getId());
		
		if (trackingPermissionsDTOfromDB == null) {
			throw new InvalidParameterException("entity does not exists");
		}
		
		TrackingPermissionsTransform.updateDto(trackingPermissionsDTO, trackingPermissionsDTOfromDB);
		
		TrackingPermissions entity = TrackingPermissionsTransform.dto2Bo(trackingPermissionsDTOfromDB);
		
		trackingPermissionsRepository.saveAndFlush(entity);
	}
	
    
    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void delete(TrackingPermissionsDTO trackingPermissionsDTO) throws JrafDomainException {	
    	trackingPermissionsRepository.deleteById(trackingPermissionsDTO.getId());
	}
    
    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void tracking(PermissionsDTO permissionsDTO) throws JrafDomainException {
    	
    	for (PermissionDTO permissionDTO : permissionsDTO.getPermission()) {
    		TrackingPermissionsDTO trackingPermissionDTO = new TrackingPermissionsDTO();
    		trackingPermissionDTO.setConsent((permissionDTO.getAnswer() == true) ? "Y" : "N");
    		trackingPermissionDTO.setDateConsent(new Date());
    		trackingPermissionDTO.setDateCreation(new Date());
    		trackingPermissionDTO.setDateModification(new Date());
    		trackingPermissionDTO.setPermissionQuestionId(refPermissionsQuestionDS.getById(Integer.parseInt(permissionDTO.getPermissionId())));
    		trackingPermissionDTO.setSgin(individuDS.getByGin(permissionsDTO.getGin()));
    		trackingPermissionDTO.setSignatureCreation(permissionsDTO.getRequestorDTO().getSignature());
    		trackingPermissionDTO.setSignatureModification(permissionsDTO.getRequestorDTO().getSignature());
    		trackingPermissionDTO.setSiteCreation(permissionsDTO.getRequestorDTO().getSite());
    		trackingPermissionDTO.setSiteModification(permissionsDTO.getRequestorDTO().getSite());
    		
    		this.create(trackingPermissionDTO);
    	}
    	
	}
}
