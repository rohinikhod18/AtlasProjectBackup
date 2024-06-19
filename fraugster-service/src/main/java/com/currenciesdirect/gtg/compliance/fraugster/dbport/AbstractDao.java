/**
 * 
 */
package com.currenciesdirect.gtg.compliance.fraugster.dbport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.Logger;

import com.currenciesdirect.gtg.compliance.fraugster.exception.FraugsterException;
import com.currenciesdirect.gtg.compliance.fraugster.exception.FraugsterErrors;

/**
 * The Class AbstractDao.
 *
 * @author manish
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
	 * @throws FraugsterException the fraugster exception
	 */
	protected Connection getConnection() throws FraugsterException{
		Connection conn = null;
		try{
			if (dataSource== null)
			{
				getDataSource();
			}
			conn = dataSource.getConnection();
		}catch(Exception e){
			LOG.error("Error in getting database conneciton: ", e);
			throw new FraugsterException(FraugsterErrors.DATABASE_GENERIC_ERROR);
		}
		return conn;
	}
	
	/**
	 * Close connection.
	 *
	 * @param con the con
	 * @throws FraugsterException the fraugster exception
	 */
	protected void closeConnection(Connection con) throws FraugsterException {
		try {
			if (con != null) {
				con.close();
			}
		} catch (Exception e) {
			LOG.error("Error in closing database connection: ", e);
			throw new FraugsterException(FraugsterErrors.DATABASE_GENERIC_ERROR);
		}
	}
	
	/**
	 * Close prepare statement.
	 *
	 * @param pstmt the pstmt
	 * @throws FraugsterException the fraugster exception
	 */
	protected void closePrepareStatement(PreparedStatement pstmt) throws FraugsterException {
		try {
			if (pstmt != null) {
				pstmt.close();
			}
		} catch (Exception e) {
			LOG.error("Error in closing database statement: ", e);
			throw new FraugsterException(FraugsterErrors.DATABASE_GENERIC_ERROR);
		}
	}
	
	/**
	 * Close resultset.
	 *
	 * @param rset the rset
	 * @throws FraugsterException the fraugster exception
	 */
	protected void closeResultset(ResultSet rset) throws FraugsterException {
		try {
			if (rset != null) {
				rset.close();
			}
		} catch (Exception e) {
			LOG.error("Error in closing resultset: ", e);
			throw new FraugsterException(FraugsterErrors.DATABASE_GENERIC_ERROR);
		}
	}
	
	/**
	 * Begin transaction.
	 *
	 * @param con the con
	 * @throws FraugsterException the fraugster exception
	 */
	protected void beginTransaction(Connection con) throws FraugsterException
	{
		try{
			
			con.setAutoCommit(false);
		}catch (Exception e) {
			LOG.error("Error while begin transaction: ", e);
			throw new FraugsterException(FraugsterErrors.DATABASE_GENERIC_ERROR);
		}
		
	}
	
	/**
	 * Transaction committed.
	 *
	 * @param con the con
	 * @throws FraugsterException the fraugster exception
	 */
	protected void transactionCommitted(Connection con) throws FraugsterException
	{
		try{
			
			con.commit();
		}catch (Exception e) {
			LOG.error("Error while committing transaction: ", e);
			throw new FraugsterException(FraugsterErrors.DATABASE_GENERIC_ERROR);
		}
		
	}
	
	/**
	 * Transaction rolled back.
	 *
	 * @param con the con
	 * @throws FraugsterException the fraugster exception
	 */
	protected void transactionRolledBack(Connection con) throws FraugsterException
	{
		try{
			
			con.rollback();
		}catch (Exception e) {
			LOG.error("Error while rollBack the transaction: ", e);
			throw new FraugsterException(FraugsterErrors.DATABASE_GENERIC_ERROR);
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
