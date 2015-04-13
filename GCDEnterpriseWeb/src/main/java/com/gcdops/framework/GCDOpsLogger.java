package com.gcdops.framework;

import org.apache.log4j.Logger;

/**
 * This class is responsible for logging and provides various methods to cope with logging requirement.
 * Project specific additional logic can be coded in this class.
 *  
 * @author rohit.sharma
 * @see Logger
 */
public class GCDOpsLogger {
	

		//Holds the logger instance
		private Logger log = null;
		
		
		/**
		 * Factory method to obtain logger for a particular class.
		 * @param clazz Class for which Logger is requested
		 * @return instance of ToyRobotLogger
		 */
		public static final GCDOpsLogger getInstance(Class<?> clazz) {
			return new GCDOpsLogger(clazz);
		}

		/**
		 * Restrict access to default constructor to this class. Direct object creation in external classes is discouraged per
		 * design. Use getInstance(Class clazz) method instead.
		 * 
		 */
		private GCDOpsLogger() {
		}

		/**
		 * Restrict access of argument constructor to local class. Factory method of this class shall instantiate objects per
		 * request
		 * 
		 * @param clazz Class for which log
		 */
		private GCDOpsLogger(Class<?> clazz) {
			log = Logger.getLogger(clazz);
		}
		
		
		/**
		 * This method logs message at debug level if the debug level is enabled
		 * @param obj Any arbitrary object
		 */
		public void debug(Object obj)	{
		    if(log.isDebugEnabled()){
		    	log.debug(obj);
		    }
		}
		
				
		/**
		 * This method logs message at info level if the info level is enabled
		 * @param obj Any arbitary object
		 */
		public void info(Object obj)	{
		    if(log.isInfoEnabled()){
		    	log.info(obj);
		    }
		}
		
		/**
		 * This method logs message at warn level
		 * @param obj Any arbitary object
		 */
		public void warn(Object obj)	{
		       	log.warn(obj);
		    }
		

		/**
		 * This method logs message and error at warn level
		 * @param obj Any arbitary object
		 */
		public void warn(Object obj,Throwable t)	{
		       	log.warn(obj, t);
		    }
		
		
		/**
		 * This method logs message at error level
		 * @param obj Any arbitary object
		 */
		public void error(Object obj)	{
		       	log.error(obj);
		    }
		

		/**
		 * This method logs message and error at error level
		 * @param obj Any arbitary object
		 */
		public void error(Object obj,Throwable t)	{
		       	log.error(obj, t);
		    }
		
}
		

		

