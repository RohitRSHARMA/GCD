package com.gcdops.service;

import java.util.List;

import javax.jms.JMSException;
import com.gcdops.dao.IMessageDAO;
import com.gcdops.framework.GCDOpsException;
import com.gcdops.framework.GCDOpsLogger;
import com.gcdops.model.DataStructure;
import com.gcdops.queueoperations.JMSQueueOperator;


/**
 * Class to support queue and GCD operations
 * @author rohit.sharma
 *
 */
public class MessageServiceImpl implements IMessageService {
	
	/*
	 * Logger defined to log messages
	 */
	private static final GCDOpsLogger LOGGER = GCDOpsLogger.getInstance(MessageServiceImpl.class);
	

	//IMessageDAO interface to fetch data from database
	IMessageDAO iMessageDAO;
		
	/**
	 * <p>
	 * This method adds the messages to the Queue.
	 * </p>
	 * @param int1 represents the first message
	 * @param int2 represents the second message
	 * @return String message as SUCCESS or FAILURE 
	 * @throws JMSException
	 */
	public String pushMessageToQueue(Integer int1, Integer int2) throws GCDOpsException,JMSException {
		
		LOGGER.debug("MessageServiceImpl.pushMessageToQueue Method called  Message Received: [" + int1 + "], [" + int2 + "]");
		String status  = JMSQueueOperator.pushMessageToQueue(int1, int2);
		return status;
	}
	
	
	/**
	 * <p>
	 * This method consumes the first two messages from the head of the queue, calculates its GCD and returns to the user. 
	 * It then deletes these two messages from the head of the queue
	 * </p>
	 * @return int returns the GCD of the first two messages in the queue
	 * @throws GCDOpsException
	 * @throws JMSException
	 */
	public int getGCDOfFirstTwoQueueMessage() throws GCDOpsException,JMSException{
		 LOGGER.debug("MessageServiceImpl.getGCDOfFirstTwoQueueMessage Method called");
		int gcd = JMSQueueOperator.getGCDOfFirstTwoQueueMessage();
		return gcd;
		
	}
	
	/**
     * Returns all the messages ever added to the Queue
     * @return List 
     */
	public List<Integer> getQueueMessages(){
		LOGGER.debug("MessageServiceImpl.getQueueMessages Method Returning Queue Message list");
		return DataStructure.getQueueMessageList();
	}


	/**
	 * Returns a list of all the computed greatest common divisors.
	 * @return a list of all the computed greatest common divisors.
	 */
	public List<Integer> gcdList() {
		LOGGER.debug("MessageServiceImpl.gcdList Method Returning a list of all computed GCDs");
		return DataStructure.getGcdlist();
	}

	
	/**
	 * Returns a sum of all computed greatest common divisors
	 * @return a int containing sum of all computed greatest common divisors
	 */
	public int gcdSum() {
		LOGGER.debug("MessageServiceImpl.gcdSum Method Returning a sum of all computed GCDs");
		return DataStructure.getSumOfAllGCD();
	}

}
