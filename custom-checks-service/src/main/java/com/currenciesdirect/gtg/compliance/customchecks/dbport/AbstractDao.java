
package com.currenciesdirect.gtg.compliance.customchecks.dbport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.customchecks.exception.CustomChecksErrors;
import com.currenciesdirect.gtg.compliance.customchecks.exception.CustomChecksException;

/**
 * The Class AbstractDao.
 */
public abstract class AbstractDao {
	private static final Logger LOG = LoggerFactory.getLogger(AbstractDao.class);
	private DataSource ccDataSource;

	/**
	 * Gets the data source.
	 *
	 * @return the data source
	 * @throws NamingException
	 *             the naming exception
	 */
	public DataSource getDataSource() throws NamingException {
		InitialContext initialContext;
		if (ccDataSource == null) {
			initialContext = new InitialContext();
			ccDataSource = (DataSource) initialContext.lookup("java:jboss/datasources/ComplianceDS");
		}
		return ccDataSource;
	}

	/**
	 * Sets the data source.
	 *
	 * @param dataSource
	 *            the new data source
	 */
	public void setDataSource(DataSource dataSource) {
		this.ccDataSource = dataSource;
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
		if (ccDataSource == null) {
			getDataSource();
		}
		return ccDataSource.getConnection();
	}

	/**
	 * Close connection.
	 *
	 * @param ccConnection
	 *            the con
	 * @throws SQLException
	 *             the SQL exception
	 */
	protected void closeConnection(Connection ccConnection) throws CustomChecksException {
		if (ccConnection != null) {
			try {
				ccConnection.close();
			} catch (SQLException e) {
				LOG.error("" , e);
				throw new CustomChecksException(CustomChecksErrors.ERROR_WHILE_DATABASE_CONNECTION, e);
			}
		}
}

	/**
	 * Close prepare statement.
	 *
	 * @param ccPstmt
	 *            the pstmt
	 * @throws SQLException
	 *             the SQL exception
	 */
	protected void closePrepareStatement(PreparedStatement ccPstmt) throws CustomChecksException {
		if (ccPstmt != null) {
			try {
				ccPstmt.close();
			} catch (SQLException e) {
				LOG.error("" , e);
				throw new CustomChecksException(CustomChecksErrors.ERROR_WHILE_DATABASE_QUERY_EXECUTION, e);
			}
		}
	}

	/**
	 * Close resultset.
	 *
	 * @param ccRset
	 *            the rset
	 * @throws SQLException
	 *             the SQL exception
	 */
	protected void closeResultset(ResultSet ccRset) throws CustomChecksException {
		if (ccRset != null) {
			try {
				ccRset.close();
			} catch (SQLException e) {
				LOG.error("" , e);
				throw new CustomChecksException(CustomChecksErrors.ERROR_WHILE_DATABASE_QUERY_EXECUTION, e);
			}
		}
	}

	/**
	 * Begin transaction.
	 *
	 * @param ccConnection
	 *            the con
	 * @throws SQLException
	 *             the SQL exception
	 */
	protected void beginTransaction(Connection ccConnection) throws SQLException {
		ccConnection.setAutoCommit(false);
	}

	/**
	 * Commit transaction.
	 *
	 * @param ccConnection
	 *            the con
	 * @throws SQLException
	 *             the SQL exception
	 */
	protected void commitTransaction(Connection ccConnection) throws SQLException {
		ccConnection.commit();
	}

	/**
	 * Transaction rolled back.
	 *
	 * @param ccConnection
	 *            the con
	 * @throws SQLException
	 *             the SQL exception
	 */
	protected void transactionRolledBack(Connection ccConnection) throws SQLException {
		ccConnection.rollback();

	}

}
