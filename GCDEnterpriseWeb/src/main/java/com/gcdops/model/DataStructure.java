package com.gcdops.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class holds the Queue messages and gcd data added and retrieved from queue
 * @author rohit.sharma
 *
 */
public class DataStructure {

		
	/**
	 * List to hold messages that are ever added to the Queue
	 */
	private static List<Integer> queueMessageList = new ArrayList<Integer>(); 
	
	
	/**
	 * List to hold all the computed greatest common divisors
	 */
	private static List<Integer> gcdlist = new ArrayList<Integer>(); 
	
	
	/**
	 * This integer stores the sum of all the GCDs calculated
	 */
	private static Integer sumOfAllGCD = new Integer("0");


	/**
	 * Returns List of all Queue Messages ever added to the Queue
	 * @return List 
	 */
	public static List<Integer> getQueueMessageList() {
		return queueMessageList;
	}


	/**
	 * Returns a list holding all the computed GCDs
	 * @return returns a list of GCDs
	 */
	public static List<Integer> getGcdlist() {
		return gcdlist;
	}





	/**
	 * returns the sum of all the GCDs calculated
	 * @return
	 */
	public static Integer getSumOfAllGCD() {
		return sumOfAllGCD;
	}


	public static void setSumOfAllGCD(Integer sumOfAllGCD) {
		DataStructure.sumOfAllGCD = sumOfAllGCD;
	}
	
	
	

	
	
}
