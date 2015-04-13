package com.gcdops.dao;

import java.util.List;

import com.gcdops.framework.GCDOpsLogger;
import com.gcdops.model.DataStructure;

/**
 * DAO Class to fetch messages from Database
 * @author rohit.sharma
 *
 */
public class MessageDAOImpl implements IMessageDAO {
	
	/*
	 * Logger defined to log messages
	 */
	private static final GCDOpsLogger LOGGER = GCDOpsLogger.getInstance(MessageDAOImpl.class);
	
	
	/**
     * Returns all the messages ever added to the Queue
     * @return List 
     */
	public List<Integer> getQueueMessages(){
		LOGGER.debug("MessageDAOImpl.getQueueMessages Method Returning Queue Message list");
				return DataStructure.getQueueMessageList();
	}


	/**
	 * Returns a list of all the computed greatest common divisors.
	 * @return a list of all the computed greatest common divisors.
	 */
	public List<Integer> gcdList() {
		return DataStructure.getGcdlist();		
	}

	
	/**
	 * Returns a sum of all computed greatest common divisors
	 * @return a int containing sum of all computed greatest common divisors
	 */
	public int gcdSum() {
		return DataStructure.getSumOfAllGCD();
	}
	

}
