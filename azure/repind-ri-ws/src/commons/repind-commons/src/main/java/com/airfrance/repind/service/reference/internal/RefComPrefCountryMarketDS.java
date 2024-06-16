package com.airfrance.repind.service.reference.internal;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.type.TableReferenceEnum;
import com.airfrance.repind.dao.reference.RefComPrefCountryMarketRepository;
import com.airfrance.repind.dto.reference.RefComPrefCountryMarketDTO;
import com.airfrance.repind.dto.reference.RefComPrefCountryMarketTransform;
import com.airfrance.repind.entity.reference.RefComPrefCountryMarket;
import com.airfrance.repind.util.service.PaginationResult;
import com.airfrance.repind.util.service.PaginationUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class RefComPrefCountryMarketDS {
		
    /** logger */
    //private static final Log log = LogFactory.getLog(RefPermissionsQuestionDS.class);
    
    @Autowired
    private RefComPrefCountryMarketRepository refComPrefCountryMarketRepository;

    @Transactional(readOnly=true)
    public PaginationResult<RefComPrefCountryMarketDTO> provideRefComPrefCountryMarketWithPagination(String country, int index, int maxResults) throws JrafDomainException {
    	PaginationResult<RefComPrefCountryMarketDTO> paginationResult = new PaginationResult<RefComPrefCountryMarketDTO>();
    	
    	Long totalResultsFound = null;
    	if (!StringUtils.isEmpty(country)) {
    		totalResultsFound = refComPrefCountryMarketRepository.countByCodePays(country);
		} else {
			totalResultsFound = refComPrefCountryMarketRepository.count();
		}
    	
    	PaginationUtils.getIndexAndMaxResultsForPagination(paginationResult, index, maxResults, TableReferenceEnum.REF_COMPREF_COUNTRY_MARKET, totalResultsFound);
    	
    	List<RefComPrefCountryMarket> listRefComPrefCountryMarket = null;
    	if (!StringUtils.isEmpty(country)) {
    		listRefComPrefCountryMarket = refComPrefCountryMarketRepository.findByCodePaysOrderByCodePays(country, PageRequest.of(paginationResult.getIndex(), paginationResult.getMaxResults()));
		} else {
			listRefComPrefCountryMarket = refComPrefCountryMarketRepository.findAllByOrderByCodePays(PageRequest.of(paginationResult.getIndex(), paginationResult.getMaxResults()));
		}
    	
    	for (RefComPrefCountryMarket refComPrefCountryMarket : listRefComPrefCountryMarket) {
    		paginationResult.getListResults().add(RefComPrefCountryMarketTransform.bo2DtoLight(refComPrefCountryMarket));
    	}
    	
    	return paginationResult;
    }
}
