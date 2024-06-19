/*
 * package com.currenciesdirect.gtg.compliance.compliancesrv.config;
 * 
 * import javax.jms.JMSException; import static
 * com.ibm.msg.client.wmq.common.CommonConstants.WMQ_CM_CLIENT;
 * 
 * import org.springframework.beans.factory.annotation.Value; import
 * org.springframework.context.annotation.Bean; import
 * org.springframework.context.annotation.Configuration; import
 * org.springframework.jms.connection.JmsTransactionManager; import
 * org.springframework.jms.connection.SingleConnectionFactory; import
 * org.springframework.jms.connection.UserCredentialsConnectionFactoryAdapter;
 * import org.springframework.jms.core.JmsTemplate; import
 * org.springframework.jms.listener.DefaultMessageListenerContainer;
 * 
 * import com.currenciesdirect.gtg.compliance.compliancesrv.ibmmq.mqport.
 * PostTransactionMessageListnerImpl; import
 * com.ibm.mq.jms.MQQueueConnectionFactory;
 * 
 * @Configuration public class AtlasWebConfig {
 * 
 *//** The ibm mq channel. */
/*
 * @Value("${ib.mq.channel}") public String ibmMqChannel;
 * 
 *//** The ibm mq port. */
/*
 * @Value("${ibm.mq.port}") public Integer ibmMqPort;
 * 
 *//** The ibm mq host name. */
/*
 * @Value("${ibm.mq.hostName}") public String ibmMqHostName;
 * 
 *//** The ibm mq queue manager. */
/*
 * @Value("${ibm.mq.queueManager}") public String ibmMqQueueManager;
 * 
 *//** The ibm mq username. */
/*
 * @Value("${ibm.mq.username}") public String ibmMqUsername;
 * 
 *//** The ibm mq password. */
/*
 * @Value("${ibm.mq.password}") public String ibmMqPassword;
 * 
 *//** The ibm mq destination. */
/*
 * @Value("${ibm.mq.destination}") public String ibmMqDestination;
 * 
 * @Value("${ibm.mq.maxConcurrentConsumers}") public Integer
 * ibmMqMaxConcurrentConsumers;
 * 
 *//**
	 * Mq queue connection factory.
	 *
	 * @return the MQ queue connection factory
	 * @throws JMSException the JMS exception
	 */
/*
 * @Bean public MQQueueConnectionFactory mqQueueConnectionFactory() throws
 * JMSException { MQQueueConnectionFactory mQQueueConnectionFactory = new
 * MQQueueConnectionFactory();
 * mQQueueConnectionFactory.setChannel(ibmMqChannel);
 * mQQueueConnectionFactory.setPort(ibmMqPort);
 * mQQueueConnectionFactory.setHostName(ibmMqHostName);
 * mQQueueConnectionFactory.setTransportType(WMQ_CM_CLIENT);
 * mQQueueConnectionFactory.setQueueManager(ibmMqQueueManager); return
 * mQQueueConnectionFactory; }
 * 
 *//**
	 * User credentials connection factory adapter.
	 *
	 * @return the user credentials connection factory adapter
	 * @throws JMSException the JMS exception
	 */
/*
 * @Bean public UserCredentialsConnectionFactoryAdapter
 * userCredentialsConnectionFactoryAdapter() throws JMSException {
 * UserCredentialsConnectionFactoryAdapter connectionFactoryAdapter = new
 * UserCredentialsConnectionFactoryAdapter();
 * connectionFactoryAdapter.setUsername(ibmMqUsername);
 * connectionFactoryAdapter.setPassword(ibmMqPassword);
 * connectionFactoryAdapter.setTargetConnectionFactory(mqQueueConnectionFactory(
 * )); return connectionFactoryAdapter; }
 * 
 *//**
	 * Single connection factory.
	 *
	 * @return the single connection factory
	 * @throws JMSException the JMS exception
	 */
/*
 * @Bean public SingleConnectionFactory singleConnectionFactory() throws
 * JMSException { SingleConnectionFactory singleConnectionFactory = new
 * SingleConnectionFactory();
 * singleConnectionFactory.setTargetConnectionFactory(
 * userCredentialsConnectionFactoryAdapter()); return singleConnectionFactory; }
 * 
 *//**
	 * Jms transaction manager.
	 *
	 * @return the jms transaction manager
	 * @throws JMSException the JMS exception
	 */
/*
 * @Bean public JmsTransactionManager jmsTransactionManager() throws
 * JMSException { JmsTransactionManager jmsTransactionManager = new
 * JmsTransactionManager();
 * jmsTransactionManager.setConnectionFactory(singleConnectionFactory()); return
 * jmsTransactionManager; }
 * 
 *//**
	 * Default message listener container.
	 *
	 * @return the default message listener container
	 * @throws JMSException the JMS exception
	 */
/*
 * @Bean public DefaultMessageListenerContainer
 * defaultMessageListenerContainer() throws JMSException {
 * DefaultMessageListenerContainer defaultMessageListenerContainer = new
 * DefaultMessageListenerContainer();
 * defaultMessageListenerContainer.setDestinationName(ibmMqDestination);
 * defaultMessageListenerContainer.setConnectionFactory(singleConnectionFactory(
 * ));
 * defaultMessageListenerContainer.setMessageListener(messageListenerImpl());
 * defaultMessageListenerContainer.setMaxConcurrentConsumers(
 * ibmMqMaxConcurrentConsumers);
 * defaultMessageListenerContainer.setSessionTransacted(true);
 * defaultMessageListenerContainer.setTransactionManager(jmsTransactionManager()
 * ); return defaultMessageListenerContainer;
 * 
 * }
 * 
 *//**
	 * Jms template.
	 *
	 * @return the jms template
	 * @throws JMSException the JMS exception
	 */
/*
 * @Bean public JmsTemplate jmsTemplate() throws JMSException { JmsTemplate
 * jmsTemplate = new JmsTemplate(); jmsTemplate.setExplicitQosEnabled(true);
 * jmsTemplate.setDeliveryPersistent(true);
 * jmsTemplate.setConnectionFactory(singleConnectionFactory()); return
 * jmsTemplate; }
 * 
 *//**
	 * Message listener impl.
	 *
	 * @return the post transaction message listner impl
	 *//*
		 * @Bean public PostTransactionMessageListnerImpl messageListenerImpl() { return
		 * new PostTransactionMessageListnerImpl(); }
		 * 
		 * }
		 */