package com.currenciesdirect.gtg.compliance.fraugster.core.sessionhandler;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.fraugster.core.FraugsterConcreteDataBuilder;
import com.currenciesdirect.gtg.compliance.fraugster.core.IFraugsterProviderService;
import com.currenciesdirect.gtg.compliance.fraugster.core.domain.FraugsterProviderProperty;
import com.currenciesdirect.gtg.compliance.fraugster.core.domain.FraugsterSessionToken;
import com.currenciesdirect.gtg.compliance.fraugster.exception.FraugsterErrors;
import com.currenciesdirect.gtg.compliance.fraugster.exception.FraugsterException;
import com.currenciesdirect.gtg.compliance.fraugster.fraugsterport.FraugsterPort;
import com.currenciesdirect.gtg.compliance.fraugster.util.Constants;

/**
 * The Class FraugsterSessionHandler.
 */
public class FraugsterSessionHandler {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(FraugsterSessionHandler.class);

	/** The fraugster session handler. */
	private static FraugsterSessionHandler fraugsterSessionHandler = null;

	/** The Constant scheduledExecutorService. */
	public static final ScheduledExecutorService scheduledExecutorService = Executors
			.newSingleThreadScheduledExecutor();

	/** The fraugster provider property. */
	private FraugsterProviderProperty fraugsterProviderProperty = null;

	/** The concrete data builder. */
	private FraugsterConcreteDataBuilder concreteDataBuilder = FraugsterConcreteDataBuilder.getInstance();

	/** The fraugster port. */
	private IFraugsterProviderService fraugsterPort = FraugsterPort.getInstance();

	/** The is refresh in progress.   */
	@SuppressWarnings("squid:S3077")
	private volatile Boolean isRefreshInProgress;
	
	/** The lock object used for obtaining thread locks*/
	private final Object lockObject = new Object();

	/** The session token. */
	private FraugsterSessionToken sessionToken = new FraugsterSessionToken();

	/**
	 * Instantiates a new fraugster session handler.
	 */
	private FraugsterSessionHandler() {

	}

	/**
	 * Gets the single instance of FraugsterSessionHandler.
	 *
	 * @return single instance of FraugsterSessionHandler
	 */
	public static FraugsterSessionHandler getInstance() {
		if (fraugsterSessionHandler == null) {
			fraugsterSessionHandler = new FraugsterSessionHandler();
		}
		return fraugsterSessionHandler;

	}

	/**
	 * Gets the session token.
	 *
	 * @return the session token
	 */
	public FraugsterSessionToken getSessionToken() {
		if(Boolean.FALSE.equals(isRefreshInProgress)){
			return sessionToken;
		}else{
			synchronized (lockObject) {
				return sessionToken;
			}
		}
	}

	/**
	 * Start fraugster session.
	 *
	 * @throws FraugsterException the fraugster exception
	 */
	public void startFraugsterSession() throws FraugsterException {
		try {
			fraugsterProviderProperty = concreteDataBuilder.getProviderInitConfigProperty(Constants.LOGIN_LOGOUT);
			fraugsterSessionHandler.doLogin(false);
			runscheduler();
		} catch (FraugsterException e ) {
			LOG.error("Error in FraugsterSessionHandler class startFraugsterSession() method:", e);
		} catch (Exception e) {
			LOG.error("Error in FraugsterSessionHandler class startFraugsterSession() method:", e);
			throw new FraugsterException(FraugsterErrors.ERROR_WHILE_LOADING_FRAUGSTER_SESSION);
		}

	}

	/**
	 * Runscheduler.
	 */
	public void runscheduler() {
		Integer timeIntervel = fraugsterProviderProperty.getFraugsterInactivityRefreshTime();

		final Runnable startSchedular = () -> {

			LOG.debug("Fraugster  Schedular started");
			try {
				doLogin(true);
			} catch (Exception ce) {
				LOG.error("Error in scheduling", ce);
			}
		};
		scheduledExecutorService.scheduleAtFixedRate(startSchedular, timeIntervel, timeIntervel, TimeUnit.MINUTES);

	}

	/**
	 * Do logout.
	 *
	 * @return true, if successful
	 * @throws FraugsterException the fraugster exception
	 */
	private synchronized boolean doLogout() throws FraugsterException {
		synchronized (lockObject) {
			return fraugsterPort.doLogout(fraugsterProviderProperty, sessionToken);
		}
		

	}

	/**
	 * Do login.
	 *
	 * @param refresh the refresh
	 * @throws FraugsterException the fraugster exception
	 */
	private synchronized void doLogin(boolean refresh) throws FraugsterException {
		try {

			if (!refresh && null != sessionToken.getSessionToken()) {
				return;
			}
			updateSessionToken();
			LOG.warn("Fraugster Session Token: {} ", sessionToken.getSessionToken());
		} catch (FraugsterException e) {
			throw e;
		} catch (Exception e) {
			LOG.error("Error in fraugsterSession handler DoLogin() method :", e);
			throw new FraugsterException(FraugsterErrors.ERROR_WHILE_LOGIN);
		}
	}

	private void updateSessionToken() throws FraugsterException {
		synchronized (lockObject) {
			isRefreshInProgress = Boolean.TRUE;
			if (sessionToken.getSessionToken() != null) {
				doLogout();
			}
			sessionToken = fraugsterPort.doLogin(fraugsterProviderProperty);
			isRefreshInProgress = Boolean.FALSE;
		}
	}

	/**
	 * Stopscheduler.
	 *
	 * @return true, if successful
	 */
	public boolean stopscheduler() {
		boolean result = false;
		scheduledExecutorService.shutdown();
		if (scheduledExecutorService.isShutdown()) {
			result = true;
		}
		return result;
	}

	/**
	 * Repeat login.
	 *
	 * @return the session token
	 * @throws FraugsterException the fraugster exception
	 */
	public FraugsterSessionToken repeatLogin() throws FraugsterException {
		doLogin(true);
		return sessionToken;
	}

}