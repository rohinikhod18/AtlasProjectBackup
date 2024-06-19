
package com.currenciesdirect.gtg.compliance.dbport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class AbstractDao.
 */
public abstract class AbstractDao {
	
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
	 * @throws SQLException the SQL exception
	 * @throws NamingException the naming exception
	 * 
	 * when readonly requested, SET isolation level to TRANSACTION_READ_UNCOMMITTED
	 * so that records are not locked for SELECT transactions.
	 */
	@SuppressWarnings("squid:S2095")
	protected Connection getConnection(Boolean isReadOnly) throws SQLException, NamingException {
		if (dataSource == null) {
			getDataSource();
		}
		Connection con = dataSource.getConnection();
		if (Boolean.TRUE.equals(isReadOnly))
			con.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
		else
			con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
		return con;
	}
	/**
	 * Close connection.
	 *
	 * @param con
	 *            the con
	 * @throws SQLException
	 *             the SQL exception
	 */
	protected void closeConnection(Connection con) {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				LOG.error("" , e);
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
	protected void closePrepareStatement(PreparedStatement pstmt){
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				LOG.error("" , e);
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
	protected void closeResultset(ResultSet rset){
		if (rset != null) {
			try {
				rset.close();
			} catch (SQLException e) {
				LOG.error("" , e);
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
	protected void transactionRolledBack(Connection con){
		try {
			con.rollback();
		} catch (SQLException e) {
			LOG.error("" , e);
		}

	}
	
}
