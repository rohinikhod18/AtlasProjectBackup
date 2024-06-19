
package com.currenciesdirect.gtg.compliance.blacklist.dbport;

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
	static final Logger LOG = LoggerFactory.getLogger(AbstractDao.class);

	/** The data source. */
	private DataSource dataSource;

	/** The initial context. */
	private InitialContext initialContext;

	/**
	 * Gets the data source.
	 *
	 * @return the data source
	 * @throws NamingException
	 *             the naming exception
	 */
	public DataSource getDataSource() throws NamingException {

		if (dataSource == null) {
			initialContext = new InitialContext();
			dataSource = (DataSource) initialContext.lookup("java:jboss/datasources/EnterpriseDS");
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
	 */
	protected Connection getConnection() throws SQLException, NamingException {
		if (dataSource == null) {
			getDataSource();
		}
		return dataSource.getConnection();
	}

	/**
	 * Close connection.
	 *
	 * @param con
	 *            the con
	 * @throws SQLException
	 *             the SQL exception
	 */
	protected void closeConnection(Connection con) throws SQLException {
		if (con != null) {
			con.close();
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
	protected void closePrepareStatement(PreparedStatement pstmt) throws SQLException {
		if (pstmt != null) {
			pstmt.close();
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
	protected void closeResultset(ResultSet rset) throws SQLException {
		if (rset != null) {
			rset.close();
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
