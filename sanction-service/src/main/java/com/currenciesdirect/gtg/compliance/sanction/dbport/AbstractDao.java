/*******************************************************************************
 * 
 * Copyright 2017 Currencies Direct Ltd, United Kingdom
 * 
 * Compliance: AbstractDao.java
 ******************************************************************************/
package com.currenciesdirect.gtg.compliance.sanction.dbport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.currenciesdirect.gtg.compliance.sanction.exception.SanctionErrors;
import com.currenciesdirect.gtg.compliance.sanction.exception.SanctionException;

/**
 * The Class AbstractDao.
 */
public abstract class AbstractDao {

	private DataSource configDataSource;

	/**
	 * Gets the data source.
	 *
	 * @return the data source
	 * @throws NamingException
	 *             the naming exception
	 */
	public DataSource getConfigDataSource() throws NamingException {
		/** The initial context. */
		InitialContext initialContext;
		if (configDataSource == null) {
			initialContext = new InitialContext();
			configDataSource = (DataSource) initialContext.lookup("java:jboss/datasources/ComplianceDS");
		}

		return configDataSource;
	}

	/**
	 * Sets the data source.
	 *
	 * @param dataSource
	 *            the new data source
	 */
	public void setDataSource(DataSource dataSource) {
		this.configDataSource = dataSource;
	}

	/**
	 * Gets the connection.
	 *
	 * @return the connection
	 * @throws SQLException
	 *             the SQL exception
	 * @throws NamingException
	 *             the naming exception
	 */
	protected Connection getConnection() throws SQLException, NamingException {
		if (configDataSource == null) {
			getConfigDataSource();
		}
		return configDataSource.getConnection();
	}

	/**
	 * Close connection.
	 *
	 * @param con
	 *            the con
	 * @throws SQLException
	 *             the SQL exception
	 */
	protected void closeConnection(Connection con) throws SanctionException {
		try {
			if (con != null) {
				con.close();
			}
		} catch (Exception e) {
			throw new SanctionException(SanctionErrors.ERROR_WHILE_FETCHING_DATA_FROM_DB_TO_CACHE, e);
		}
	}

	/**
	 * Close prepare statement.
	 *
	 * @param pstmt
	 *            the pstmt
	 * @throws SQLException
	 *             the SQL exception
	 */
	protected void closePrepareStatement(PreparedStatement pstmt) throws SanctionException {
		try {
			if (pstmt != null) {
				pstmt.close();
			}
		} catch (Exception e) {
			throw new SanctionException(SanctionErrors.ERROR_WHILE_FETCHING_DATA_FROM_DB_TO_CACHE, e);
		}
	}

	/**
	 * Close resultset.
	 *
	 * @param rset
	 *            the rset
	 * @throws SQLException
	 *             the SQL exception
	 */
	protected void closeResultset(ResultSet rset) throws SanctionException {
		try {
			if (rset != null) {
				rset.close();
			}
		} catch (Exception e) {
			throw new SanctionException(SanctionErrors.ERROR_WHILE_FETCHING_DATA_FROM_DB_TO_CACHE, e);
		}
	}

	/**
	 * Begin transaction.
	 *
	 * @param con
	 *            the con
	 * @throws SQLException
	 *             the SQL exception
	 */
	protected void beginTransaction(Connection con) throws SQLException {
		con.setAutoCommit(false);
	}

	/**
	 * Commit transaction.
	 *
	 * @param con
	 *            the con
	 * @throws SQLException
	 *             the SQL exception
	 */
	protected void commitTransaction(Connection con) throws SQLException {
		con.commit();
	}

	/**
	 * Transaction rolled back.
	 *
	 * @param con
	 *            the con
	 * @throws SQLException
	 *             the SQL exception
	 */
	protected void transactionRolledBack(Connection con) throws SQLException {
		con.rollback();

	}

}
