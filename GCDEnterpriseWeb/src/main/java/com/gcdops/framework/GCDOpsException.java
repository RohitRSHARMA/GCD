package com.gcdops.framework;



/**
 * 
 * This is the wrapper class for all the exceptions thrown in the application Business layer.
 * It also wraps all the validation error along with other exceptions.
 * @author rohit.sharma
 *
 */
public class GCDOpsException extends Exception{
	
	
	/**
	 * declaring a default serialId
	 */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * Constructor to create ToyRobotException objects. It calls super constructor internally.
	 * It also sets the message into the errorMessage variable
	 * @param errorMessage
	 */
	public GCDOpsException(String errorMessage){
		super(errorMessage);
		this.errorMessage = errorMessage;
	}
	
	/**
	 * Constructor to create ToyRobotException objects. It calls super constructor internally.
	 * It also sets the message into the errorMessage variable
	 * @param errorMessage
	 */
	public GCDOpsException(String errorMessage, Throwable e){
		super(errorMessage, e);
		this.errorMessage = errorMessage;
	}
	
	/**
	 * 
	 * String to hold error message
	 * 
	 */
	private String errorMessage;
	
	/**
	 * 
	 * @return String containing error message
	 */
	public String getErrorMessage() {
		return errorMessage;
	}


	/**
	 * 
	 * @param errorMessage add the error message
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	



}
