/**
 * 
 */
package com.currenciesdirect.gtg.compliance.authentication.dbport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.annotation.Resource;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.currenciesdirect.gtg.compliance.authentication.exception.AuthenticationException;
import com.currenciesdirect.gtg.compliance.authentication.exception.Errorname;



/**
 * @author manish
 *
 */

public abstract class AbstractDao {
	
	static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory
			.getLogger(AbstractDao.class);
	
	private DataSource dataSource;
	
	private InitialContext initialContext ;
	
	/**
	 * @return dataSource
	 */
	public DataSource getDataSource() {
		
		if (dataSource== null)
		{
			try {
				initialContext = new InitialContext();
				dataSource=  (DataSource)initialContext.lookup("java:jboss/datasources/ComplianceDS");
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
	
	protected Connection getConnection() throws AuthenticationException{
		Connection conn = null;
		try{
			if (dataSource== null)
			{
				getDataSource();
			}
			conn = dataSource.getConnection();
		}catch(Exception e){
			LOG.error("Error in getting database conneciton: ", e);
			throw new AuthenticationException(Errorname.DATABASE_GENERIC_ERROR,e);
		}
		return conn;
	}
	
	protected void closeConnection(Connection con) throws AuthenticationException {
		try {
			if (con != null) {
				con.close();
			}
		} catch (Exception _Ex) {
			LOG.error("Error in closing database connection: ", _Ex);
			throw new AuthenticationException(Errorname.DATABASE_GENERIC_ERROR,_Ex);
		}
	}
	
	protected void closePrepareStatement(PreparedStatement pstmt) throws AuthenticationException {
		try {
			if (pstmt != null) {
				pstmt.close();
			}
		} catch (Exception _Ex) {
			LOG.error("Error in closing database statement: ", _Ex);
			throw new AuthenticationException(Errorname.DATABASE_GENERIC_ERROR,_Ex);
		}
	}
	
	protected void closeResultset(ResultSet rset) throws AuthenticationException {
		try {
			if (rset != null) {
				rset.close();
			}
		} catch (Exception _Ex) {
			LOG.error("Error in closing resultset: ", _Ex);
			throw new AuthenticationException(Errorname.DATABASE_GENERIC_ERROR,_Ex);
		}
	}
	
	protected void beginTransaction(Connection con) throws AuthenticationException
	{
		try{
			
			con.setAutoCommit(false);
		}catch (Exception _Ex) {
			LOG.error("Error while begin transaction: ", _Ex);
			throw new AuthenticationException(Errorname.DATABASE_GENERIC_ERROR,_Ex);
		}
		
	}
	
	protected void transactionCommitted(Connection con) throws AuthenticationException
	{
		try{
			
			con.commit();
		}catch (Exception _Ex) {
			LOG.error("Error while committing transaction: ", _Ex);
			throw new AuthenticationException(Errorname.DATABASE_GENERIC_ERROR,_Ex);
		}
		
	}
	
	protected void transactionRolledBack(Connection con) throws AuthenticationException
	{
		try{
			
			con.rollback();
		}catch (Exception _Ex) {
			LOG.error("Error while rollBack the transaction: ", _Ex);
			throw new AuthenticationException(Errorname.DATABASE_GENERIC_ERROR,_Ex);
		}
		
	}

}
