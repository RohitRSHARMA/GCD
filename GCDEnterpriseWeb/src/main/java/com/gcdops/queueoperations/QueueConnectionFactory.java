package com.gcdops.queueoperations;

import java.util.Properties;

import javax.jms.ConnectionFactory;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.gcdops.framework.GCDOpsLogger;
import com.gcdops.framework.GCDOpsRegistry;


/**
 * This class gets the instance of the ConnectionFactory.
 * @author rohit.sharma
 *
 */
public class QueueConnectionFactory {
	
	/*
	 * Logger defined to log messages
	 */
	private static final GCDOpsLogger LOGGER = GCDOpsLogger.getInstance(QueueConnectionFactory.class);
	
	/**
	 * Stores the ConnectionFactory instance
	 */
	private static ConnectionFactory connectionFactory = null;
	
	
	/**
	 * Stores the Context
	 */
	private static Context context = null; 
	
	
	/**
	 * This method returns the ConnectionFactory initialized once
	 * @return ConnectionFactory
	 * @throws NamingException
	 */
	public static ConnectionFactory getConnectionFactory() throws NamingException {
		if(connectionFactory == null) {
			 LOGGER.debug("Create JNDI Context");
			 Context context = getInitialContext(); 
		     LOGGER.debug("Get connection facory"); 
		     connectionFactory = (ConnectionFactory) context.lookup("ConnectionFactory");
		     return connectionFactory;
		} else {
			return connectionFactory;
		}
	}

	 	
   /**
    * This method returns the Context initialized once.	
    * @return Context
    * @throws NamingException
    */
   public static Context getInitialContext() throws NamingException { 
    	 
    	 if(context == null) {
    		 Properties props = new Properties(); 
             props.setProperty("java.naming.factory.initial",GCDOpsRegistry.getInstance().getProperty("java.naming.factory.initial")); 
             props.setProperty("java.naming.factory.url.pkgs",GCDOpsRegistry.getInstance().getProperty("java.naming.factory.url.pkgs")); 
             props.setProperty("java.naming.provider.url", GCDOpsRegistry.getInstance().getProperty("java.naming.provider.url")); 
             Context context = new InitialContext(props); 
             return context; 
    	 } else {
    		 return context;
    	 }
     }
     
}
