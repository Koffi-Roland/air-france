package com.airfrance.repind.firme.searchfirmonmulticriteria.dao;

import com.airfrance.repind.entity.firme.PersonneMorale;
import com.airfrance.repind.firme.searchfirmonmulticriteria.dto.BusinessException;
import com.airfrance.repind.firme.searchfirmonmulticriteria.dto.FirmDTO;
import com.airfrance.repind.firme.searchfirmonmulticriteria.dto.RequestDTO;
import com.airfrance.repind.firme.searchfirmonmulticriteria.dto.SearchResultDTO;
import com.airfrance.repind.firme.searchfirmonmulticriteria.dto.builders.SearchResultDTOBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.QueryException;
import org.hibernate.exception.SQLGrammarException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Component
public class SearchFirmDAO extends AbstractDAO implements ISearchFirmDAO 
{
	/*===============================================*/
	/*             INJECTED BEANS                    */
	/*===============================================*/
	
	@Autowired
	private SearchResultDTOBuilder searchResultDTOBuilder = null;
	
	/*===============================================*/
	/*                   LOGGER                      */
	/*===============================================*/
	private static Log LOGGER  = LogFactory.getLog(SearchFirmDAO.class);
	
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
			LOGGER.info("Request building");

			Query query = requestDTO.getSearchHqlQuery();
			
			query.setFirstResult(0);
			query.setMaxResults(requestDTO.getQueryMaxResults());
			
			LOGGER.info("SearchFirmOnMultiCriteria | REQUEST EXECUTION START");
			List<PersonneMorale> searchResults = (List<PersonneMorale>)query.getResultList();
			LOGGER.info("SearchFirmOnMultiCriteria | REQUEST RESULT SIZE: " + searchResults.size());
			LOGGER.info("SearchFirmOnMultiCriteria | REQUEST EXECUTION END");
			
			//RESULT TREATEMENT 
			for(PersonneMorale personneMorale : searchResults)
			{
				SearchResultDTO searchResultDTO = searchResultDTOBuilder.build(personneMorale);
				if(searchResultDTO != null)
				{
					results.add(searchResultDTO);
				}
			}
		}
		catch(SQLGrammarException ex)
		{
			LOGGER.error(ex.getMessage()+ " - QUERY: " + requestDTO.getSearchHqlQuery());
			throwsException("HQL/SQL", ex.getMessage());
		}
		catch(NoResultException ex)
        {
			LOGGER.error(ex.getMessage()+ " - QUERY: " + requestDTO.getSearchHqlQuery());
			throwsException("HQL/SQL", ex.getMessage());
        }
        catch(NonUniqueResultException ex)
        {
        	LOGGER.error(ex.getMessage()+ " - QUERY: " + requestDTO.getSearchHqlQuery());
        	throwsException("HQL/SQL", ex.getMessage());
        }
        catch(IllegalStateException ex)
        {
        	LOGGER.error(ex.getMessage()+ " - QUERY: " + requestDTO.getSearchHqlQuery());
        	throwsException("HQL/SQL", ex.getMessage());
        }
        catch(QueryException ex) 
        {
        	LOGGER.error(ex.getMessage()+ " - QUERY: " + requestDTO.getSearchHqlQuery());
        	throwsException("HQL/SQL", ex.getMessage());
        }
        catch(PersistenceException ex) //  if the query execution exceeds the query timeout value set and the transaction is rolled back
        {
        	LOGGER.error(ex.getMessage()+ " - QUERY: " + requestDTO.getSearchHqlQuery());
        	throwsException("HQL/SQL", ex.getMessage());
        }
        catch(NumberFormatException ex)
        {
        	LOGGER.error(ex.getMessage()+ " - QUERY: " + requestDTO.getSearchHqlQuery());
        	throwsException("HQL/SQL", ex.getMessage());
        }
        catch(Exception ex)
        {
        	LOGGER.error(ex.getMessage()+ " - QUERY: " + requestDTO.getSearchHqlQuery());
        	throwsException("HQL/SQL", ex.getMessage());
        }
		return results;	
	}
	
	
	/**
	 * Read a list of PersonneMorale according to the request's criteria
	 */
	public FirmDTO readFirmByGin(RequestDTO requestDTO, String gin) throws BusinessException 
	{	
		FirmDTO firm = null;
		try
		{
			Query query = getEntityManager().createQuery(requestDTO.getLoadFirmHqlQuery());
            query.setParameter("ginValue", gin);
            LOGGER.info("SearchFirmOnMultiCriteria | READING SINGLE FIRM START");
        	PersonneMorale personneMoraleWrapper = (PersonneMorale)query.getSingleResult();
        	LOGGER.info("SearchFirmOnMultiCriteria | READING SINGLE FIRM END");
        	firm = getFirmBuilder().build(personneMoraleWrapper, requestDTO.getIdentity().getNameType());
		}
		catch(SQLGrammarException ex)
		{
			LOGGER.error(ex.getMessage()+ " - QUERY: " + requestDTO.getLoadFirmHqlQuery());
			throwsException("HQL/SQL", ex.getMessage());
		}
		catch(NoResultException ex)
        {
			LOGGER.error(ex.getMessage()+ " - QUERY: " + requestDTO.getLoadFirmHqlQuery());
			throwsException("HQL/SQL", ex.getMessage());
        }
        catch(NonUniqueResultException ex)
        {
        	LOGGER.error(ex.getMessage()+ " - QUERY: " + requestDTO.getLoadFirmHqlQuery());
        	throwsException("HQL/SQL", ex.getMessage());
        }
        catch(IllegalStateException ex)
        {
        	LOGGER.error(ex.getMessage()+ " - QUERY: " + requestDTO.getLoadFirmHqlQuery());
        	throwsException("HQL/SQL", ex.getMessage());
        }
        catch(QueryException ex) 
        {
        	LOGGER.error(ex.getMessage()+ " - QUERY: " + requestDTO.getLoadFirmHqlQuery());
        	throwsException("HQL/SQL", ex.getMessage());
        }
        catch(PersistenceException ex) //  if the query execution exceeds the query timeout value set and the transaction is rolled back
        {
        	LOGGER.error(ex.getMessage()+ " - QUERY: " + requestDTO.getLoadFirmHqlQuery());
        	throwsException("HQL/SQL", ex.getMessage());
        }
        catch(NumberFormatException ex)
        {
        	LOGGER.error(ex.getMessage()+ " - QUERY: " + requestDTO.getLoadFirmHqlQuery());
        	throwsException("HQL/SQL", ex.getMessage());
        }
        catch(Exception ex)
        {
        	LOGGER.error(ex.getMessage()+ " - QUERY: " + requestDTO.getLoadFirmHqlQuery());
        	throwsException("HQL/SQL", ex.getMessage());
        }
		return firm;	
	}
}
