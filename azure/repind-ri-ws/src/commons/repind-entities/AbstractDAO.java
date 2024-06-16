package com.airfrance.repind.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.NonUniqueResultException;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Projections;
import org.springframework.transaction.annotation.Transactional;

import com.airfrance.ref.exception.InvalidParameterException;

public abstract class AbstractDAO<T extends Serializable> {
	private final Class<T> persistentClass;
	
	@PersistenceContext(unitName = "entityManagerFactoryRepind")
	private EntityManager entityManager;

	public AbstractDAO(final Class<T> persistentClass) {
		this.persistentClass = persistentClass;
	}
	
	private void checkNullParameter(final T o) throws InvalidParameterException{
		if(null == o) throw new InvalidParameterException("entity must not be null");
	}
	
	@Transactional
	@SuppressWarnings("unchecked")
	public List<T> findAll(final T entity) throws InvalidParameterException{
		checkNullParameter(entity);
	    Example ex = Example.create(entity);
	    Session session = (Session) entityManager.getDelegate();	 
	    return session.createCriteria(entity.getClass()).add(ex).list();
	}
	
	@Transactional
	@SuppressWarnings("unchecked")
	public List<T> findAll(){
	    Session session = (Session) entityManager.getDelegate();	 
	    return session.createCriteria(persistentClass).list();
	}
	
	@Transactional
	public long countAll(final T entity) throws InvalidParameterException{
		checkNullParameter(entity);
	    Example ex = Example.create(entity);
	    Session session = (Session) entityManager.getDelegate();	 
	    return (long) session.createCriteria(entity.getClass()).add(ex).setProjection(Projections.rowCount()).uniqueResult();
	}
	
	@Transactional
	public T findById(final Serializable id ){
		return (T) entityManager.find(persistentClass, id);
	}
	@Transactional
	@SuppressWarnings("unchecked")
	public T findByExample(final T entity) throws InvalidParameterException, NonUniqueResultException {
		checkNullParameter(entity);
    	Example ex = Example.create(entity);
    	Session session = (Session) entityManager.getDelegate();
    	T result = (T) session.createCriteria(entity.getClass()).add(ex).uniqueResult();
    	return result;
	 }
	@Transactional
	 public void removeById(final Serializable id) throws InvalidParameterException {
		T entity = entityManager.find(persistentClass, id);
		remove(entity);
	 }
	@Transactional
	 public void remove(final T entity) throws InvalidParameterException {
		checkNullParameter(entity);
	    entityManager.remove(entity);
    	entityManager.flush();
	 }
	@Transactional
	 public void create(final T entity) throws InvalidParameterException {
		checkNullParameter(entity); 	
    	entityManager.persist(entity);
    	entityManager.flush();
	 }
	@Transactional
	public void update(final T entity) throws InvalidParameterException {
		checkNullParameter(entity);
    	entityManager.merge(entity);
    	entityManager.flush();
	 }

	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}
