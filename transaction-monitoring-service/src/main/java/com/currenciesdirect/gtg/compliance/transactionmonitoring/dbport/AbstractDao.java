/**
 * 
 */
package com.currenciesdirect.gtg.compliance.transactionmonitoring.dbport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.Logger;

import com.currenciesdirect.gtg.compliance.transactionmonitoring.exception.TransactionMonitoringErrors;
import com.currenciesdirect.gtg.compliance.transactionmonitoring.exception.TransactionMonitoringException;

/**
 * The Class AbstractDao.
 *
 * 
 */

public abstract class AbstractDao {
	
	/** The Constant LOG. */
	private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(AbstractDao.class);
	
	/** The data source. */
	private DataSource dataSource;
	
	/**
	 * Gets the data source.
	 *
	 * @return dataSource
	 */
	public DataSource getDataSource() {
		InitialContext initialContext ;
		if (dataSource== null)
		{
			try {
				initialContext = new InitialContext();
				dataSource=  (DataSource)initialContext.lookup("java:jboss/datasources/ComplianceDS");
			} catch (NamingException e) {
				logDebug(e);
			}
		}
		
		return dataSource;
	}
	
	
	/**
	 * Sets the data source.
	 *
	 * @param dataSource the new data source
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	
	/**
	 * Gets the connection.
	 *
	 * @return the connection
	 * @throws TransactionMonitoringException the transaction monitoring exception
	 */
	protected Connection getConnection() throws TransactionMonitoringException{
		Connection conn = null;
		try{
			if (dataSource== null)
			{
				getDataSource();
			}
			conn = dataSource.getConnection();
		}catch(Exception e){
			LOG.error("Error in getting database conneciton: ", e);
			throw new TransactionMonitoringException(TransactionMonitoringErrors.DATABASE_GENERIC_ERROR);
		}
		return conn;
	}
	
	
	/**
	 * Close connection.
	 *
	 * @param con the con
	 * @throws TransactionMonitoringException the transaction monitoring exception
	 */
	protected void closeConnection(Connection con) throws TransactionMonitoringException {
		try {
			if (con != null) {
				con.close();
			}
		} catch (Exception e) {
			LOG.error("Error in closing database connection: ", e);
			throw new TransactionMonitoringException(TransactionMonitoringErrors.DATABASE_GENERIC_ERROR);
		}
	}
	
	
	/**
	 * Close prepare statement.
	 *
	 * @param pstmt the pstmt
	 * @throws TransactionMonitoringException the transaction monitoring exception
	 */
	protected void closePrepareStatement(PreparedStatement pstmt) throws TransactionMonitoringException {
		try {
			if (pstmt != null) {
				pstmt.close();
			}
		} catch (Exception e) {
			LOG.error("Error in closing database statement: ", e);
			throw new TransactionMonitoringException(TransactionMonitoringErrors.DATABASE_GENERIC_ERROR);
		}
	}
	
	
	/**
	 * Close resultset.
	 *
	 * @param rset the rset
	 * @throws TransactionMonitoringException the transaction monitoring exception
	 */
	protected void closeResultset(ResultSet rset) throws TransactionMonitoringException {
		try {
			if (rset != null) {
				rset.close();
			}
		} catch (Exception e) {
			LOG.error("Error in closing resultset: ", e);
			throw new TransactionMonitoringException(TransactionMonitoringErrors.DATABASE_GENERIC_ERROR);
		}
	}
	
	
	/**
	 * Begin transaction.
	 *
	 * @param con the con
	 * @throws TransactionMonitoringException the transaction monitoring exception
	 */
	protected void beginTransaction(Connection con) throws TransactionMonitoringException
	{
		try{
			
			con.setAutoCommit(false);
		}catch (Exception e) {
			LOG.error("Error while begin transaction: ", e);
			throw new TransactionMonitoringException(TransactionMonitoringErrors.DATABASE_GENERIC_ERROR);
		}
		
	}
	
	
	/**
	 * Transaction committed.
	 *
	 * @param con the con
	 * @throws TransactionMonitoringException the transaction monitoring exception
	 */
	protected void transactionCommitted(Connection con) throws TransactionMonitoringException
	{
		try{
			
			con.commit();
		}catch (Exception e) {
			LOG.error("Error while committing transaction: ", e);
			throw new TransactionMonitoringException(TransactionMonitoringErrors.DATABASE_GENERIC_ERROR);
		}
		
	}
	
	
	/**
	 * Transaction rolled back.
	 *
	 * @param con the con
	 * @throws TransactionMonitoringException the transaction monitoring exception
	 */
	protected void transactionRolledBack(Connection con) throws TransactionMonitoringException
	{
		try{
			
			con.rollback();
		}catch (Exception e) {
			LOG.error("Error while rollBack the transaction: ", e);
			throw new TransactionMonitoringException(TransactionMonitoringErrors.DATABASE_GENERIC_ERROR);
		}
		
	}
	
	/**
	 * Log debug.
	 *
	 * @param exception the exception
	 */
	private void logDebug(Throwable exception) {
		LOG.debug("Error in class FraugsterServiceImpl :", exception);
	}

}
