package com.gcdops.webservice;

import java.util.List;

import javax.jms.JMSException;

import com.gcdops.framework.GCDOpsException;
import com.gcdops.service.IMessageService;
import com.gcdops.service.MessageServiceImpl;

/**
 * <p>
 * This is a Soap Web service utility for Queue operations and 
 * GCD retrieval operations
 * </p>
 * @author rohit.sharma
 *
 */
public class GcdOpsSoapService{
	private IMessageService messageService;
	
	/**
	 * <p>
	 * This method consumes the first two messages from the head of the queue, calculates its GCD and returns to the user. 
	 * It then deletes these two messages from the head of the queue
	 * </p>
	 * @return int returns the GCD of the first two messages in the queue
	 * @throws GCDOpsException
	 * @throws JMSException
	 */
	public Integer gcd () throws GCDOpsException, JMSException
	{		
		int gcd =0;
		messageService = new MessageServiceImpl();
	    gcd = messageService.getGCDOfFirstTwoQueueMessage();		
		return gcd;
	}

	/**
	 * Returns a list of all the computed greatest common divisors.
	 * @return a list of all the computed greatest common divisors.
	 */
	public Integer[] gcdList(){
		
		
		messageService = new MessageServiceImpl();
		List<Integer> gcdList = messageService.gcdList();
				
		Integer[] gcdListArray;
		if(gcdList!=null && !gcdList.isEmpty()){
			gcdListArray = gcdList.toArray(new Integer[gcdList.size()]);
		}
		else {
			gcdListArray = new Integer[0];
		}
		
		return gcdListArray;
	}
	
	/**
	 * Returns a sum of all computed greatest common divisors
	 * @return a int containing sum of all computed greatest common divisors
	 */
	public int gcdSum() {		
		messageService = new MessageServiceImpl();
		return messageService.gcdSum();
	}


}
