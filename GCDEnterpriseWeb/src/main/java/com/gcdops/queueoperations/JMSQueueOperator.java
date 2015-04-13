package com.gcdops.queueoperations;

import java.util.Enumeration;
import java.util.List;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.NamingException;
import com.gcdops.framework.GCDOpsException;
import com.gcdops.framework.GCDOpsLogger;
import com.gcdops.framework.GCDOpsRegistry;
import com.gcdops.model.DataStructure;
import com.gcdops.util.GCDOpsConstants;
import com.gcdops.util.GCDOpsUtil;


/**
 * This class performs load and retrieve operations on queue.
 * @author rohit.sharma
 *
 */
public class JMSQueueOperator {
	
	/*
	 * Logger defined to log messages
	 */
	private static final GCDOpsLogger LOGGER = GCDOpsLogger.getInstance(JMSQueueOperator.class);
	
	//This object will act as a common lock between the Load and the Fetch operation on queue to avoid the message getting mixed up given the queue is the single resource
	//The lock is necessary because the business requirement says to delete the messages after retrieving them.
	private static Object lock = new Object();
	
		
	/**
	 * <p>
	 * This method adds the messages to the Queue.
	 * </p>
	 * @param int1 represents the first message
	 * @param int2 represents the second message
	 * @return String message as SUCCESS or FAILURE 
	 * @throws JMSException
	 */
	public static String pushMessageToQueue(Integer int1, Integer int2) throws GCDOpsException,JMSException {
		
		synchronized(lock) {
		
			//Create variables to store Connection, Session, MessageProducer
			Connection connection = null;
			Session session = null;
			MessageProducer producer = null;
			 String returnMessage = GCDOpsConstants.JMS_MESSAGE_DELIVERY_SUCCESS;
			 
		     try { 
		    	 
		    	//If any of the passed message is null, throw an error back to the user
		 		if(int1 == null || int2 == null){
		 			throw new GCDOpsException("Either one or both the messages are blank. Please send valid messages");
		 		}
	
		    	 
		    	 LOGGER.debug("JMSQueueOperator.pushMessageToQueue Method Begins  Message Received: [" + int1 + "], [" + int2 + "]");
		    	
		    	    	 
		    	 ConnectionFactory connectionFactory = QueueConnectionFactory.getConnectionFactory();
		         LOGGER.debug("Creating connection"); 
		         connection = connectionFactory.createConnection(); 
		         LOGGER.debug("Creating session"); 
		         session = connection.createSession(false,QueueSession.AUTO_ACKNOWLEDGE);
		         LOGGER.debug("Lookup queue"); 
		         Queue queue = (Queue) QueueConnectionFactory.getInitialContext().lookup(GCDOpsRegistry.getInstance().getProperty("java.naming.queue")); 
		         LOGGER.debug("Start connection"); 
		         connection.start(); 
		         LOGGER.debug("Create producer"); 
		         producer = session.createProducer(queue);
	
		         //Get the list queueMessageList and load the messages in the list as well.
		         List<Integer> queueMessageList = DataStructure.getQueueMessageList();
		         
		         //send the first message
		         LOGGER.debug("Create the first messages"); 
		         Message message= session.createTextMessage(""+int1);
		         producer.send(message);
		         LOGGER.debug("Message one: [" + int1 + "] sent to Queue");
		         queueMessageList.add(int1);
		         
		         //send the second message
		         LOGGER.debug("Create the second messages");
		         message = session.createTextMessage(""+int2);
		         LOGGER.debug("Message two: [" + int2 + "] sent to Queue");
		         producer.send(message);
		         queueMessageList.add(int2);
		           
		     }catch(NamingException namingException) {
		    	 GCDOpsException gcdOpsException = new GCDOpsException("NamingException occured while sending message",namingException);
		    	 LOGGER.error(namingException);
		    	 throw gcdOpsException;
		    	 	    	 
		     } catch(JMSException jmsException) {
		    	  GCDOpsException gcdOpsException = new GCDOpsException("JMSException occured while sending message",jmsException);
		    	  LOGGER.error(jmsException);
		    	  throw gcdOpsException;
		     }catch(GCDOpsException gcdOpsException) {
		    	 LOGGER.error(gcdOpsException);
		    	 throw gcdOpsException;
		     }catch(Exception e){
		    	 GCDOpsException gcdOpsException = new GCDOpsException("Exception occured while sending message",e);
		    	 LOGGER.error(e);
		    	 throw gcdOpsException;
		     }
		     finally {
		         if (connection != null) { 
		             LOGGER.debug("close the connection");
		             connection.close();
		         }
		         if (session != null) { 
		             LOGGER.debug("close the session");
		             session.close();
		         } 
		         if (producer != null) { 
		             LOGGER.debug("close the producer");
		             producer.close();
		         } 
		     } 
		     LOGGER.debug("JMSQueueOperator.pushMessageToQueue Method Ends");
		     return returnMessage;
		}
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
	public static int getGCDOfFirstTwoQueueMessage() throws GCDOpsException,JMSException{
		
			synchronized(lock) {
			
				LOGGER.debug("JMSQueueOperator.getGCDOfFirstTwoQueueMessage Method Begins");
		
			 	//Create variables to store Connection, Session, MessageConsumer
			 	Connection connection = null; 
				Session session =null;
				MessageConsumer consumer=null;
			
			   try {
					 //Variable to hold GCD. Initialized as zero 
					 int gcdInt = 0;
					 
					 //array to hold messages
					 int[] messageArray = new int[2]; 
					 
					 ConnectionFactory connectionFactory = QueueConnectionFactory.getConnectionFactory();
				     LOGGER.debug("Create connection"); 
				     connection = connectionFactory.createConnection(); 
				     LOGGER.debug("Create session"); 
				     session = connection.createSession(true,-1);
				     LOGGER.debug("Lookup queue");
			         Queue queue = (Queue) QueueConnectionFactory.getInitialContext().lookup(GCDOpsRegistry.getInstance().getProperty("java.naming.queue"));
			
			         //Creating browser to browse over the queue
			         QueueBrowser browser = session.createBrowser(queue);
			         Enumeration<?> enum1 = browser.getEnumeration();
			         
			         
			       //counter to ensure the below code runs twice 
			         int i=0;
			         while(enum1.hasMoreElements() && i<2) {
			        	//read the message here from the queue 
			            TextMessage msg = (TextMessage)enum1.nextElement();
			            messageArray[i] = new Integer(msg.getText());
			            //next create a consumer so that the read message can be deleted from the queue
			            consumer = session.createConsumer(queue, "JMSMessageID='" +  msg.getJMSMessageID()  + "'");
			            connection.start();
			            consumer.receive(1000);
			            	//incrementing the counter here	 
			            	i+=1;	 
			         }
			                       
			         
			         //checking the value of counter. There should be at least 2 messages in the Queue to compute the GCD.
			         //Else throw error message saying queue contains just one message.
				     if(i==2){
				    	 gcdInt = GCDOpsUtil.findGCD(messageArray[0], messageArray[1]);
				     } else {
				    	 throw new GCDOpsException("No or only one message found in the Queue. GCD cannot be computed");
				     }
				     
				     	//Add the GCD to the GCDList
				     	DataStructure.getGcdlist().add(gcdInt);
				     	
				     	//Add the GCD calculated to the Sum
				     	Integer sumGCD = DataStructure.getSumOfAllGCD();
				     	Integer newSumGCD = sumGCD.intValue() + gcdInt;
				     	DataStructure.setSumOfAllGCD(newSumGCD);
				     	LOGGER.debug("JMSQueueOperator.getGCDOfFirstTwoQueueMessage Method ends");
				     	return  gcdInt;
				     	
			   } catch(JMSException jmsException) {
		    	  GCDOpsException gcdOpsException = new GCDOpsException("JMSException occured while fetching message",jmsException);
		    	  LOGGER.error(jmsException);
		    	  throw gcdOpsException;
			   }catch(GCDOpsException gcdOpsException) {
			    	 LOGGER.error(gcdOpsException);
			    	 throw gcdOpsException;
			   }catch(Exception e) {
		    	 GCDOpsException gcdOpsException = new GCDOpsException("Exception occured while fetching message",e);
		    	 LOGGER.error(e);
		    	 throw gcdOpsException;
			   } finally {
				   
				   session.commit();
				   	 
				   	if(consumer != null) { 
			             LOGGER.debug("close the consumer");
			             consumer.close();
			         }
				   	if (session != null) { 
			             LOGGER.debug("close the session");
			             session.close();
			         } 
				     if (connection != null) { 
			             LOGGER.debug("close the connection");
			             connection.close();
			         }	         
			         
			   }
			}
	}

    
}