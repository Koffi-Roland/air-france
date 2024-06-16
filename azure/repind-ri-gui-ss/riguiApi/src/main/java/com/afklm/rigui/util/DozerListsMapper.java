package com.afklm.rigui.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.dozer.Mapper;

/**
 * DozerLists Mapper
 * @author m405991
 *
 * @param <E>
 * @param <B>
 */
public class DozerListsMapper<E extends Serializable, B extends Serializable> {

	/**
	 * Map EntityIterable To Bean List
	 * @param dozerBeanMapper
	 * @param entities
	 * @param targetClass
	 * @return List
	 */
	public List<B> mapEntityIterableToBeanList(Mapper dozerBeanMapper, Iterable<E> entities, Class<B> targetClass){

		List<B> beansList = new ArrayList<B>();
		for(E entity : entities){
			beansList.add(dozerBeanMapper.map(entity, targetClass));
		}
		return beansList;
	}

	/**
	 * Map EntitySet To Bean List
	 * @param dozerBeanMapper
	 * @param entities
	 * @param targetClass
	 * @return List
	 */
	public List<B> mapEntitySetToBeanList(Mapper dozerBeanMapper, Set<ConstraintViolation<?>> entities, Class<B> targetClass){

		List<B> beansList = new ArrayList<B>();
		for(ConstraintViolation<?> entity : entities){
			beansList.add(dozerBeanMapper.map(entity, targetClass));
		}
		return beansList;
	}
}
