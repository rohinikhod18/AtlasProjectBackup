/**
 * 
 */
package com.currenciesdirect.gtg.compliance.kyc.dbport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.kyc.exception.KYCErrors;
import com.currenciesdirect.gtg.compliance.kyc.exception.KYCException;

/**
 * The Class AbstractDao.
 *
 * @author manish
 */

public abstract class AbstractDao {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(AbstractDao.class);

	/** The data source. */
	private DataSource dataSource;

	/**
	 * Gets the data source.
	 *
	 * @return dataSource
	 */
	public DataSource getDataSource() {
		InitialContext initialContext;
		if (dataSource == null) {
			try {
				initialContext = new InitialContext();
				dataSource = (DataSource) initialContext.lookup("java:jboss/datasources/ComplianceDS");
			} catch (NamingException e) {
				LOG.error("Error in abstarct dao getDataSource", e);
			}
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
	 * @throws KYCException
	 *             the KYC exception
	 */
	protected Connection getConnection() throws KYCException {
		Connection conn = null;
		try {
			if (dataSource == null) {
				getDataSource();
			}
			conn = dataSource.getConnection();
		} catch (Exception e) {
			throw new KYCException(KYCErrors.DATABASE_GENERIC_ERROR, e);
		}
		return conn;
	}

	/**
	 * Close connection.
	 *
	 * @param con
	 *            the con
	 * @throws KYCException
	 *             the KYC exception
	 */
	protected void closeConnection(Connection con) throws KYCException {
		try {
			if (con != null) {
				con.close();
			}
		} catch (Exception ex) {
			throw new KYCException(KYCErrors.DATABASE_GENERIC_ERROR, ex);
		}
	}

	/**
	 * Close prepare statement.
	 *
	 * @param pstmt
	 *            the pstmt
	 * @throws KYCException
	 *             the KYC exception
	 */
	protected void closePrepareStatement(PreparedStatement pstmt) throws KYCException {
		try {
			if (pstmt != null) {
				pstmt.close();
			}
		} catch (Exception ex) {
			throw new KYCException(KYCErrors.DATABASE_GENERIC_ERROR, ex);
		}
	}

	/**
	 * Close resultset.
	 *
	 * @param rset
	 *            the rset
	 * @throws KYCException
	 *             the KYC exception
	 */
	protected void closeResultset(ResultSet rset) throws KYCException {
		try {
			if (rset != null) {
				rset.close();
			}
		} catch (Exception ex) {
			throw new KYCException(KYCErrors.DATABASE_GENERIC_ERROR, ex);
		}
	}

	/**
	 * Begin transaction.
	 *
	 * @param con
	 *            the con
	 * @throws KYCException
	 *             the KYC exception
	 */
	protected void beginTransaction(Connection con) throws KYCException {
		try {

			con.setAutoCommit(false);
		} catch (Exception ex) {
			throw new KYCException(KYCErrors.DATABASE_GENERIC_ERROR, ex);
		}

	}

	/**
	 * Transaction committed.
	 *
	 * @param con
	 *            the con
	 * @throws KYCException
	 *             the KYC exception
	 */
	protected void transactionCommitted(Connection con) throws KYCException {
		try {

			con.commit();
		} catch (Exception ex) {
			throw new KYCException(KYCErrors.DATABASE_GENERIC_ERROR, ex);
		}

	}

	/**
	 * Transaction rolled back.
	 *
	 * @param con
	 *            the con
	 * @throws KYCException
	 *             the KYC exception
	 */
	protected void transactionRolledBack(Connection con) throws KYCException {
		try {

			con.rollback();
		} catch (Exception ex) {
			throw new KYCException(KYCErrors.DATABASE_GENERIC_ERROR, ex);
		}

	}

}
