package com.airfrance.repind.transversal.identifycustomercrossreferential.dao;


import com.airfrance.repind.entity.firme.Membre;
import com.airfrance.repind.transversal.identifycustomercrossreferential.dto.*;
import com.airfrance.repind.transversal.identifycustomercrossreferential.dto.builders.CorporateDTOBuilder;
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
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Component
public class IdentifyCustomerCrossReferentialDAO extends AbstractDAO 
{
	/*==========================================*/
	/*                                          */
	/*           INJECTED BEANS                 */
	/*                                          */
	/*==========================================*/

	/*
	 * Creates CorporateDTO instance from a member entity
	 */
	@Autowired
	CorporateDTOBuilder corporateDTOBuilder = null;

	/*==========================================*/
	/*                                          */
	/*                LOGGER                    */
	/*                                          */
	/*==========================================*/
	
	private static Log LOGGER  = LogFactory.getLog(IdentifyCustomerCrossReferentialDAO.class);
	
	/**
	 * Read Individual corporates
	 * @param requestDTO
	 * @param responseDTO
	 * @return
	 * @throws BusinessExceptionDTO 
	 */
	public Set<CorporateDTO> readIndividualCorporates(RequestDTO requestDTO, ResponseDTO responseDTO) throws BusinessExceptionDTO
	{
		Set<CorporateDTO> individualCorporates = new TreeSet<CorporateDTO>();
		try
		{
			if((responseDTO != null)
					&&	(responseDTO.getCustomers() != null)
					&&	(responseDTO.getCustomers().isEmpty() == false)
					&&	(responseDTO.getCustomers().get(0).getIndividual() != null)
					&&	(responseDTO.getCustomers().get(0).getIndividual().getIndividualInformations() != null)
					&&	(responseDTO.getCustomers().get(0).getIndividual().getIndividualInformations().getIndividualKey() != null))
			{
				//SEARCH TREATEMENT     
				Query query = getEntityManager().createQuery(requestDTO.getQueryMembers());
		        query.setParameter("ginValue", responseDTO.getCustomers().get(0).getIndividual().getIndividualInformations().getIndividualKey());
		        query.setFirstResult(0);
				query.setMaxResults(requestDTO.getQueryMaxResults());
			}
		}
		catch(SQLGrammarException ex)
		{
			LOGGER.error(ex.getMessage()+ " - QUERY: " + requestDTO.getQueryMembers());
			throwException("HQL/SQL", ex.getMessage());
		}
		catch(NoResultException ex)
        {
			LOGGER.error(ex.getMessage()+ " - QUERY: " + requestDTO.getQueryMembers());
			throwException("HQL/SQL", ex.getMessage());
        }
        catch(NonUniqueResultException ex)
        {
        	LOGGER.error(ex.getMessage()+ " - QUERY: " + requestDTO.getQueryMembers());
        	throwException("HQL/SQL", ex.getMessage());
        }
        catch(IllegalStateException ex)
        {
        	LOGGER.error(ex.getMessage()+ " - QUERY: " + requestDTO.getQueryMembers());
        	throwException("HQL/SQL", ex.getMessage());
        }
        catch(QueryException ex) 
        {
        	LOGGER.error(ex.getMessage()+ " - QUERY: " + requestDTO.getQueryMembers());
        	throwException("HQL/SQL", ex.getMessage());
        }
        catch(PersistenceException ex) //  if the query execution exceeds the query timeout value set and the transaction is rolled back
        {
        	LOGGER.error(ex.getMessage()+ " - QUERY: " + requestDTO.getQueryMembers());
        	throwException("HQL/SQL", ex.getMessage());
        }
        catch(NumberFormatException ex)
        {
        	LOGGER.error(ex.getMessage()+ " - QUERY: " + requestDTO.getQueryMembers());
        	throwException("HQL/SQL", ex.getMessage());
        }
        catch(Exception ex)
        {
        	LOGGER.error(ex.getMessage()+ " - QUERY: " + requestDTO.getQueryMembers());
        	throwException("HQL/SQL", ex.getMessage());
        }
        
		return individualCorporates;
	}

	/**
	 * Read Individual agencies
	 * @param requestDTO
	 * @param responseDTO
	 * @return
	 * @throws BusinessExceptionDTO
	 */

	public Set<AgencyDTO> readIndividualAgencies(RequestDTO requestDTO, ResponseDTO responseDTO) throws BusinessExceptionDTO
	{
		Set<AgencyDTO> individualAgencies = new TreeSet<AgencyDTO>();
		try
		{
			if((responseDTO != null)
					&&	(responseDTO.getCustomers() != null)
					&&	(responseDTO.getCustomers().isEmpty() == false)
					&&	(responseDTO.getCustomers().get(0).getIndividual() != null)
					&&	(responseDTO.getCustomers().get(0).getIndividual().getIndividualInformations() != null)
					&&	(responseDTO.getCustomers().get(0).getIndividual().getIndividualInformations().getIndividualKey() != null))
			{
				//SEARCH TREATEMENT
				Query query = getEntityManager().createQuery(requestDTO.getQueryMembers());
				query.setParameter("ginValue", responseDTO.getCustomers().get(0).getIndividual().getIndividualInformations().getIndividualKey());
				query.setFirstResult(0);
				query.setMaxResults(requestDTO.getQueryMaxResults());
				List<Membre> members = (List<Membre>)query.getResultList();
				if((members != null)
						&&	(!members.isEmpty()))
				{
					for(Membre member : members)
					{
						AgencyDTO agencyDTO = corporateDTOBuilder.buildAgency(member);
						if(agencyDTO != null)
						{
							individualAgencies.add(agencyDTO);
						}

					}
				}
			}
		}
		catch(SQLGrammarException ex)
		{
			LOGGER.error(ex.getMessage()+ " - QUERY: " + requestDTO.getQueryMembers());
			throwException("HQL/SQL", ex.getMessage());
		}
		catch(NoResultException ex)
		{
			LOGGER.error(ex.getMessage()+ " - QUERY: " + requestDTO.getQueryMembers());
			throwException("HQL/SQL", ex.getMessage());
		}
		catch(NonUniqueResultException ex)
		{
			LOGGER.error(ex.getMessage()+ " - QUERY: " + requestDTO.getQueryMembers());
			throwException("HQL/SQL", ex.getMessage());
		}
		catch(IllegalStateException ex)
		{
			LOGGER.error(ex.getMessage()+ " - QUERY: " + requestDTO.getQueryMembers());
			throwException("HQL/SQL", ex.getMessage());
		}
		catch(QueryException ex)
		{
			LOGGER.error(ex.getMessage()+ " - QUERY: " + requestDTO.getQueryMembers());
			throwException("HQL/SQL", ex.getMessage());
		}
		catch(PersistenceException ex) //  if the query execution exceeds the query timeout value set and the transaction is rolled back
		{
			LOGGER.error(ex.getMessage()+ " - QUERY: " + requestDTO.getQueryMembers());
			throwException("HQL/SQL", ex.getMessage());
		}
		catch(NumberFormatException ex)
		{
			LOGGER.error(ex.getMessage()+ " - QUERY: " + requestDTO.getQueryMembers());
			throwException("HQL/SQL", ex.getMessage());
		}
		catch(Exception ex)
		{
			LOGGER.error(ex.getMessage()+ " - QUERY: " + requestDTO.getQueryMembers());
			throwException("HQL/SQL", ex.getMessage());
		}

		return individualAgencies;
	}

}
