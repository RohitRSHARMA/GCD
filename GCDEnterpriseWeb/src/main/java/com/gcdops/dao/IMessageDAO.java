package com.gcdops.dao;

import java.util.List;

/**
 * Interface to fetch messages from Database
 * @author rohit.sharma
 *
 */
public interface IMessageDAO {
	
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
