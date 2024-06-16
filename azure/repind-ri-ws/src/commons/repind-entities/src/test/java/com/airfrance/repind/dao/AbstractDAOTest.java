package com.airfrance.repind.dao;

import org.junit.After;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * This class contains parameters and methods necessary for transactions.
 * 
 * @author Ghayth AYARI
 *
 */
public abstract class AbstractDAOTest {

	@Autowired
	JpaTransactionManager myTxManager;

	TransactionStatus status;


	/**
	 * Initilizes a transaction.
	 */
	public void initTransaction() {
		TransactionDefinition def = new DefaultTransactionDefinition();
		status = myTxManager.getTransaction(def);
	}


	/**
	 * Rolls back a transaction. It prints the stacktrace of 
	 * the transmitted exception if it is not null.
	 */
	@After
	public void rollbackTransaction() {

		if (status != null && !status.isCompleted()) {
			myTxManager.rollback(status);
		}
	}

}
