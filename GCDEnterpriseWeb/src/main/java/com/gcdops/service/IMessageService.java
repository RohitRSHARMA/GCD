package com.gcdops.service;

import java.util.List;
import javax.jms.JMSException;
import com.gcdops.framework.GCDOpsException;

/**
 * Interface to support queue and GCD operations
 * @author rohit.sharma
 *
 */
public interface IMessageService {
	

	/**
	 * <p>
	 * This method adds the messages to the Queue.
	 * </p>
	 * @param int1 represents the first message
	 * @param int2 represents the second message
	 * @return String message as SUCCESS or FAILURE 
	 * @throws JMSException
	 */
	public String pushMessageToQueue(Integer int1, Integer int2) throws GCDOpsException,JMSException;
	
	
	/**
	 * <p>
	 * This method consumes the first two messages from the head of the queue, calculates its GCD and returns to the user. 
	 * It then deletes these two messages from the head of the queue
	 * </p>
	 * @return int returns the GCD of the first two messages in the queue
	 * @throws GCDOpsException
	 * @throws JMSException
	 */
	public int getGCDOfFirstTwoQueueMessage() throws GCDOpsException,JMSException;
		
		/**
	     * Returns a list of all the messages ever added to the Queue
	     * @return List<Integer> contains  all the messages ever added to the Queue
	     */
		public List<Integer> getQueueMessages();
		
		
		/**
		 * Returns a list of all the computed greatest common divisors.
		 * @return a list of all the computed greatest common divisors.
		 */
		public List<Integer> gcdList();

		
		/**
		 * Returns a sum of all computed greatest common divisors
		 * @return a int containing sum of all computed greatest common divisors
		 */
		public int gcdSum();

	}

