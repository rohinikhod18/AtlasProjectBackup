
package com.currenciesdirect.gtg.compliance.customcheck.dbport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractDao {

	static final Logger LOG = LoggerFactory.getLogger(AbstractDao.class);

	private DataSource dataSource;

	private InitialContext initialContext;

	/**
	 * @return dataSource
	 */
	public DataSource getDataSource() {

		if (dataSource == null) {
			try {
				initialContext = new InitialContext();
				dataSource = (DataSource) initialContext.lookup("java:jboss/datasources/EnterpriseDS");
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

	protected Connection getConnection() throws Exception {
		Connection conn = null;
		try {
			if (dataSource == null) {
				getDataSource();
			}
			conn = dataSource.getConnection();
		} catch (Exception e) {
			LOG.error("Error in getting database conneciton: ", e);
		}
		return conn;
	}

	protected void closeConnection(Connection con) throws Exception {
		try {
			if (con != null) {
				con.close();
			}
		} catch (Exception _Ex) {
			LOG.error("Error in closing database connection: ", _Ex);
		}
	}

	protected void closePrepareStatement(PreparedStatement pstmt) throws Exception {
		try {
			if (pstmt != null) {
				pstmt.close();
			}
		} catch (Exception _Ex) {
			LOG.error("Error in closing database statement: ", _Ex);
		}
	}

	protected void closeResultset(ResultSet rset) throws Exception {
		try {
			if (rset != null) {
				rset.close();
			}
		} catch (Exception _Ex) {
			LOG.error("Error in closing resultset: ", _Ex);
		}
	}

	protected void beginTransaction(Connection con) throws Exception {
		try {

			con.setAutoCommit(false);
		} catch (Exception _Ex) {
			LOG.error("Error while begin transaction: ", _Ex);
		}

	}

	protected void commitTransaction(Connection con) throws Exception {
		try {

			con.commit();
		} catch (Exception _Ex) {
			LOG.error("Error while committing transaction: ", _Ex);
		}

	}

	protected void transactionRolledBack(Connection con) throws Exception {
		try {

			con.rollback();
		} catch (Exception _Ex) {
			LOG.error("Error while rollBack the transaction: ", _Ex);
		}

	}

}
