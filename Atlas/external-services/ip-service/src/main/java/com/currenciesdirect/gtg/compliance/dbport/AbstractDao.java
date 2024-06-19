/**
 * 
 */
package com.currenciesdirect.gtg.compliance.dbport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.currenciesdirect.gtg.compliance.exception.IpException;
import com.currenciesdirect.gtg.compliance.exception.IpErrors;



/**
 * @author manish
 *
 */

public abstract class AbstractDao {
	
	static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(AbstractDao.class);
	
	private DataSource dataSource;
	
	
	/**
	 * @return dataSource
	 */
	public DataSource getDataSource() {
		
		/** The initial context. */
		private InitialContext initialContext ;
		
		if (dataSource== null)
		{
			try {
				initialContext = new InitialContext();
				dataSource=  (DataSource)initialContext.lookup("java:jboss/datasources/EnterpriseDS");
			} catch (NamingException e) {
				e.printStackTrace();
			}
		}
		
		return dataSource;
	}
	/**
	 * @param dataSource
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	protected Connection getConnection() throws IpException{
		Connection conn = null;
		try{
			if (dataSource== null)
			{
				getDataSource();
			}
			conn = dataSource.getConnection();
		}catch(Exception e){
			LOG.error("Error in getting database conneciton: ", e);
			throw new IpException(IpErrors.DATABASE_GENERIC_ERROR);
		}
		return conn;
	}
	
	protected void closeConnection(Connection con) throws IpException {
		try {
			if (con != null) {
				con.close();
			}
		} catch (Exception _Ex) {
			LOG.error("Error in closing database connection: ", _Ex);
			throw new IpException(IpErrors.DATABASE_GENERIC_ERROR);
		}
	}
	
	protected void closePrepareStatement(PreparedStatement pstmt) throws IpException {
		try {
			if (pstmt != null) {
				pstmt.close();
			}
		} catch (Exception _Ex) {
			LOG.error("Error in closing database statement: ", _Ex);
			throw new IpException(IpErrors.DATABASE_GENERIC_ERROR);
		}
	}
	
	protected void closeResultset(ResultSet rset) throws IpException {
		try {
			if (rset != null) {
				rset.close();
			}
		} catch (Exception _Ex) {
			LOG.error("Error in closing resultset: ", _Ex);
			throw new IpException(IpErrors.DATABASE_GENERIC_ERROR);
		}
	}
	
	protected void beginTransaction(Connection con) throws IpException
	{
		try{
			
			con.setAutoCommit(false);
		}catch (Exception _Ex) {
			LOG.error("Error while begin transaction: ", _Ex);
			throw new IpException(IpErrors.DATABASE_GENERIC_ERROR);
		}
		
	}
	
	protected void transactionCommitted(Connection con) throws IpException
	{
		try{
			
			con.commit();
		}catch (Exception _Ex) {
			LOG.error("Error while committing transaction: ", _Ex);
			throw new IpException(IpErrors.DATABASE_GENERIC_ERROR);
		}
		
	}
	
	protected void transactionRolledBack(Connection con) throws IpException
	{
		try{
			
			con.rollback();
		}catch (Exception _Ex) {
			LOG.error("Error while rollBack the transaction: ", _Ex);
			throw new IpException(IpErrors.DATABASE_GENERIC_ERROR);
		}
		
	}

}
