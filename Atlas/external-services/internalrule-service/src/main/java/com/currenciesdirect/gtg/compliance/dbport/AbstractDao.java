
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

import com.currenciesdirect.gtg.compliance.exception.InternalRuleErrors;
import com.currenciesdirect.gtg.compliance.exception.InternalRuleException;

/**
 * The Class AbstractDao.
 */
public abstract class AbstractDao {
	public static final Logger LOG = LoggerFactory.getLogger(AbstractDao.class);

	/** The data source. */
	private DataSource dataSource;
	
	private static final String CONNECTION_ERROR = "Error in closing database connection: ";

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
	 * @throws InternalRuleException 
	 */
	protected void closeConnection(Connection con) throws InternalRuleException {
		try {
			if (con != null) {
				con.close();
			}
		} catch (Exception e) {
			LOG.error(CONNECTION_ERROR, e);
			throw new InternalRuleException(InternalRuleErrors.DATABASE_GENERIC_ERROR);
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
	protected void closePrepareStatement(PreparedStatement pstmt) throws InternalRuleException {
		try {
			if (pstmt != null) {
				pstmt.close();
			}
		} catch (Exception e) {
			LOG.error(CONNECTION_ERROR, e);
			throw new InternalRuleException(InternalRuleErrors.DATABASE_GENERIC_ERROR);
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
	protected void closeResultset(ResultSet rset) throws InternalRuleException {
		try {
			if (rset != null) {
				rset.close();
			}
		} catch (Exception e) {
			LOG.error(CONNECTION_ERROR, e);
			throw new InternalRuleException(InternalRuleErrors.DATABASE_GENERIC_ERROR);
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
