package com.airfrance.repind.service.agency.searchagencyonmulticriteria.dao;

import com.airfrance.repind.entity.agence.Agence;
import com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto.AgencyDTO;
import com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto.BusinessException;
import com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto.RequestDTO;
import com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto.SearchResultDTO;
import com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto.builders.SearchResultDTOBuilder;
import org.hibernate.QueryException;
import org.hibernate.exception.SQLGrammarException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Component
public class SearchAgencyDAO extends AbstractDAO {

	/*===============================================*/
	/*             INJECTED BEANS                    */
	/*===============================================*/
	@Autowired
	@Qualifier("AgencySearchResultDTOBuilder")
	private SearchResultDTOBuilder searchResultDTOBuilder = null;
	
	/*===============================================*/
	/*               PUBLIC METHODS                  */
	/*===============================================*/
	
	/**
	 * Returns a list of gins/firmTypes correspondent
	 *  to search result
	 */
	@SuppressWarnings("unchecked")
	public List<SearchResultDTO> searchGinsAndTypes(RequestDTO requestDTO) throws BusinessException 
	{	
		List<SearchResultDTO> results = new ArrayList<SearchResultDTO>();
		try
		{	
			//SEARCH TREATEMENT                  
			getLog().info("Request building");
			Query query = requestDTO.getSearchHqlQuery();
			query.setFirstResult(0);
			query.setMaxResults(requestDTO.getQueryMaxResults());
			
			getLog().info("Request execution LIST OF AGENCIES - START");
			List<Agence> searchResults = (List<Agence>)query.getResultList();
			getLog().info(searchResults.size());
			getLog().info("Request execution LIST OF AGENCIES - END");
			
			//RESULT TREATEMENT 
			for(Agence agence : searchResults)
			{
				SearchResultDTO searchResultDTO = searchResultDTOBuilder.build(agence);
				if(searchResultDTO != null)
				{
					results.add(searchResultDTO);
				}
			}
		}
		catch(SQLGrammarException ex)
		{
			getLog().info(ex.getMessage()+ " - QUERY: " + requestDTO.getLoadAgencyHqlQuery());
			throwsException("HQL/SQL", ex.getMessage());
		}
		catch(NoResultException ex)
        {
			getLog().info(ex.getMessage()+ " - QUERY: " + requestDTO.getLoadAgencyHqlQuery());
			throwsException("HQL/SQL", ex.getMessage());
        }
        catch(NonUniqueResultException ex)
        {
        	getLog().info(ex.getMessage()+ " - QUERY: " + requestDTO.getLoadAgencyHqlQuery());
        	throwsException("HQL/SQL", ex.getMessage());
        }
        catch(IllegalStateException ex)
        {
        	getLog().info(ex.getMessage()+ " - QUERY: " + requestDTO.getLoadAgencyHqlQuery());
        	throwsException("HQL/SQL", ex.getMessage());
        }
        catch(QueryException ex) 
        {
        	getLog().info(ex.getMessage()+ " - QUERY: " + requestDTO.getLoadAgencyHqlQuery());
        	throwsException("HQL/SQL", ex.getMessage());
        }
        catch(PersistenceException ex) //  if the query execution exceeds the query timeout value set and the transaction is rolled back
        {
        	getLog().info(ex.getMessage()+ " - QUERY: " + requestDTO.getLoadAgencyHqlQuery());
        	throwsException("HQL/SQL", ex.getMessage());
        }
        catch(NumberFormatException ex)
        {
        	getLog().info(ex.getMessage()+ " - QUERY COUNT EXCEPTION: " + requestDTO.getLoadAgencyHqlQuery());
        	throwsException("HQL/SQL", ex.getMessage());
        }
        catch(Exception ex)
        {
        	getLog().info(ex.getMessage()+ " - EXCEPTION: " + requestDTO.getCountHqlQuery());
        	throwsException("HQL/SQL", ex.getMessage());
        }
		return results;	
	}
	
	
	/**
	 * Read a list of PersonneMorale according to the request's criteria
	 */
	public AgencyDTO readAgencyByGin(RequestDTO requestDTO, String gin) throws BusinessException 
	{	
		AgencyDTO agencyDTO = null;
		try
		{
			Query query = getEntityManager().createQuery(requestDTO.getLoadAgencyHqlQuery());
            query.setParameter("ginValue", gin);
        	getLog().info("Request execution SINGLE AGENCY - START");
        	Agence agenceWrapper = (Agence)query.getSingleResult();
        	getLog().info("Request execution SINGLE AGENCY - END");
        	agencyDTO = getAgencyBuilder().build(agenceWrapper);
		}
		catch(SQLGrammarException ex)
		{
			getLog().info(ex.getMessage()+ " - QUERY: " + requestDTO.getLoadAgencyHqlQuery());
			throwsException("HQL/SQL", ex.getMessage());
		}
		catch(NoResultException ex)
        {
			getLog().info(ex.getMessage()+ " - QUERY: " + requestDTO.getLoadAgencyHqlQuery());
			throwsException("HQL/SQL", ex.getMessage());
        }
        catch(NonUniqueResultException ex)
        {
        	getLog().info(ex.getMessage()+ " - QUERY: " + requestDTO.getLoadAgencyHqlQuery());
        	throwsException("HQL/SQL", ex.getMessage());
        }
        catch(IllegalStateException ex)
        {
        	getLog().info(ex.getMessage()+ " - QUERY: " + requestDTO.getLoadAgencyHqlQuery());
        	throwsException("HQL/SQL", ex.getMessage());
        }
        catch(QueryException ex) 
        {
        	getLog().info(ex.getMessage()+ " - QUERY: " + requestDTO.getLoadAgencyHqlQuery());
        	throwsException("HQL/SQL", ex.getMessage());
        }
        catch(PersistenceException ex) //  if the query execution exceeds the query timeout value set and the transaction is rolled back
        {
        	getLog().info(ex.getMessage()+ " - QUERY: " + requestDTO.getLoadAgencyHqlQuery());
        	throwsException("HQL/SQL", ex.getMessage());
        }
        catch(NumberFormatException ex)
        {
        	getLog().info(ex.getMessage()+ " - QUERY COUNT EXCEPTION: " + requestDTO.getLoadAgencyHqlQuery());
        	throwsException("HQL/SQL", ex.getMessage());
        }
        catch(Exception ex)
        {
        	getLog().info(ex.getMessage()+ " - EXCEPTION: " + requestDTO.getCountHqlQuery());
        	throwsException("HQL/SQL", ex.getMessage());
        }
		return agencyDTO;	
	}
}
