package com.airfrance.repind.transversal.identifycustomercrossreferential.ds;

import com.airfrance.repind.transversal.identifycustomercrossreferential.dto.RequestDTO;
import org.springframework.stereotype.Service;

/**
 * Set request's technical parameters (page size, max results,...)
 * @author t950700
 *
 */
@Service("IdentifyBuildQueryDS")
public class BuildQueryDS extends AbstractDS{

	/*==========================================*/
	/*                                          */
	/*           INSTANCE VARIABLES             */
	/*                                          */
	/*==========================================*/
	
	private int queryMaxResults = 100;
	private int queryPageSize = 20;
	private String queryMembers = "SELECT M FROM Membre M INNER JOIN M.individu AS individu WITH (individu.sgin = :ginValue) INNER JOIN FETCH M.personneMorale LEFT JOIN FETCH M.fonctions AS f LEFT JOIN FETCH f.emails LEFT JOIN FETCH f.telecoms WHERE (f.dateFinValidite is null or f.dateFinValidite>current_date ) AND (M.dateFinValidite is null OR M.dateFinValidite>current_date) AND (M.dateDebutValidite<current_date)";
	
	
	/*==========================================*/
	/*                                          */
	/*              PUBLIC METHODS              */
	/*                                          */
	/*==========================================*/
	
	

	/**
	 * Set request's technical parameters (page size, max results,...)
	 */
	public void buildQuery(RequestDTO requestDTO)
	{
		if(requestDTO != null)
		{
			requestDTO.setQueryMaxResults(this.queryMaxResults);
			requestDTO.setQueryPageSize(this.queryPageSize);
			requestDTO.setQueryMembers(this.queryMembers);
		}
	}
	
	/*==========================================*/
	/*                                          */
	/*                ACCESSORS                 */
	/*                                          */
	/*==========================================*/
	

	public int getQueryMaxResults() {
		return queryMaxResults;
	}
	
	public void setQueryMaxResults(int queryMaxResults) {
		this.queryMaxResults = queryMaxResults;
	}
	
	public int getQueryPageSize() {
		return queryPageSize;
	}
	
	public void setQueryPageSize(int queryPageSize) {
		this.queryPageSize = queryPageSize;
	}
	
	public String getQueryMembers() {
		return queryMembers;
	}

	public void setQueryMembers(String queryMembers) {
		this.queryMembers = queryMembers;
	}
	
}
