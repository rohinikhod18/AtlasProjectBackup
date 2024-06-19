
package com.currenciesdirect.gtg.compliance.compliancesrv.dbport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.InternalProcessingCode;

/**
 * The Class AbstractDao.
 */
public abstract class AbstractDao  {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(AbstractDao.class);
	

	/** The data source. */
	private DataSource dataSource;

	/**
	 * Gets the data source.
	 *
	 * @return the data source
	 * @throws NamingException
	 *             the naming exception
	 */
	public DataSource getDataSource() throws NamingException {
		/** The initial context. */
		InitialContext initialContext;

		if (dataSource == null) {
			initialContext = new InitialContext();
			dataSource = (DataSource) initialContext.lookup("java:jboss/datasources/ComplianceDS");
		}

		return dataSource;
	}

	/**
	 * Sets the data source.
	 *
	 * @param dataSource
	 *            the new data source
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * Gets the connection.
	 *
	 * @return the connection
	 * @throws SQLException
	 *             the SQL exception
	 * @throws NamingException
	 *             the naming exception
	 * 
	 *             when readonly requested, SET isolation level to
	 *             TRANSACTION_READ_UNCOMMITTED so that records are not locked
	 *             for SELECT transactions.
	 */
	@SuppressWarnings("squid:S2095")
	protected Connection getConnection(Boolean isReadOnly) throws ComplianceException {
		Connection con;
		if (dataSource == null) {
			try {
				getDataSource();
			} catch (NamingException e) {
				throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
			}
		}
		try {
			con = dataSource.getConnection();
			if (Boolean.TRUE.equals(isReadOnly))
				con.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
			else
				con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			return con;
		} catch (SQLException e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		}
	}

	/**
	 * Close connection.
	 *
	 * @param con
	 *            the con
	 * @throws SQLException
	 *             the SQL exception
	 */
	protected void closeConnection(Connection con) throws ComplianceException {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
			}
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
	protected void closePrepareStatement(PreparedStatement pstmt) throws ComplianceException {
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
			}
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
	protected void closeResultset(ResultSet rset) throws ComplianceException {
		if (rset != null) {
			try {
				if (!rset.isClosed()) {
					rset.close();
				}
			} catch (SQLException e) {
				throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
			}
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
	protected void beginTransaction(Connection con) throws ComplianceException {
		try {
			con.setAutoCommit(false);
		} catch (SQLException e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		}
	}

	/**
	 * Commit transaction.
	 *
	 * @param con
	 *            the con
	 * @throws SQLException
	 *             the SQL exception
	 */
	protected void commitTransaction(Connection con) throws ComplianceException {
		try {
			con.commit();
		} catch (SQLException e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		}
	}

	/**
	 * Transaction rolled back.
	 *
	 * @param con
	 *            the con
	 * @throws SQLException
	 *             the SQL exception
	 */
	protected void transactionRolledBack(Connection con) throws ComplianceException {
		try {
			con.rollback();
		} catch (SQLException e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		}

	}
}
