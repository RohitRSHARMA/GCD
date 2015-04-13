# GCD
GCDApplication

/*
 * $Author: Rohit Sharma
 * $Revision: 1.0
 * $Date: 13-Apr-2015
 */
 

 =========================================================================================
 0. Table of Contents
 1. Introduction
 2. Assumptions
 3. Limitations
 4. Prerequisite
 5. Build and Deploy Instructions
 6. Test instructions 
 
 
 1. Introduction
 ==================================================================================================================================================================
   This application lets you add and retreive messages from the queue. The messages added are integer numbers. This appication gives you 5 webservices to perform 
   different operations.

   REST webservices: 
   a) public String push(int i1, int i2);  This API pushes the values into a JMS queue
   b) public List<Integer> list();  This API returns a lit of all elements ever added to the queue


   SOAP webservices
   c) public int gcd(); returns the greatest common divisor* of the two integers at the head of the queue. It further deletes these integers from the head of the queue and
      these are replaced by the next two in line.
   d) public List<Integer> gcdList(); returns a list of all the computed greatest common divisors 
   e) public int gcdSum();returns the sum of all computed greatest common divisors from a database.


 2. Assumptions
 ==================================================================================================================================================================
   It has been assumed that the messages being pushed or pulled by different concurrent users cannot be mixed with other messages, i.e. if a user is pushing 2 integers
   then they cannot be separated by some integer being pushed by other user OR a push operation cannot be run in parrallel with a pull operation as it may leave queue with 
   just one message and give un desired results. Similarly pulls cannot be run in parallel as it may leave queue with undesired results. 
   Thus the push and gcs operation have been synchronized on a single static operation so that at any given time only open operation can be performed.
   While this may leave the application very slow, however it acheives the atomicity of the messages by not mixing them with others.
   however if the requirement says that the order of the messages is not important then we can do away with the synchronization code.


 3. Limitations
 ==================================================================================================================================================================
    The application does not support server outage for list(), gcdList() and gcdSum() APIs. i.e. the list of all elements ever added to the queue. the list of all
    computed gcds, and the sum of all cumputed gcds will be lost when the server does down.
    The reason is that these have not been persisted to the Database because of the unavalaibility of a database server. 
    Once a database is available these can be persisted in a transaction. 



  
4. Prerequisite
====================================================================================================================================================================
  4.1. The following software are required to be installed on system for this application to run.
         a. JDK 1.7 
	 b. jboss-5.1.0.GA
	 c. Apache maven 3.2.5
	 d. curl-7.33.0-win64-ssl-sspi (You should see curl.exe when you download this)  - This is used to test REST webservices from command prompt. You may user other 
	    tools to test the webservices 	
	 
  4.2. The following Environment(System) variables need to be set on the system.
     a. JAVA_HOME: jdk home folder path. For e.g. C:\<INSTALLATION_DIR>\jdk1.7
     b. M2_HOME: Maven home folder path. For e.g. C:\<INSTALLATION_DIR>\apache-maven-3.2.5
     c. PAth: Append the JAVA_HOME and M2_HOME bin folders in the Path system variable.  For e.g. ;%M2_HOME%\bin;%JAVA_HOME%\bin

  
5. Build and Deploy Instructions	
=====================================================================================================================================================================
   MAVEN build is still pending and is not being delivered as part of the deliverables.
   The project has been checked-in to GITHUB. The same can be imported in eclipse and run against jboss server by deploying the same internally.

   5.1 Configure  jboss-5.1.0.GA as a server inside eclipse.	   

   5.2 Import both GCDEnterprise and GCDEnterpriseWeb project in eclipse

   5.3 Make sure the following properties are properly set.
       a. GCDEnterpriseWeb\src\main\resources\gcdops.properties
             - java.naming.queue=/queue/DLQ  , the queue currently used is the default jboss queue named DLQ. 
		please ensure the same exists under the following file. <jboss-5.1.0.GA>\server\default\deploy\messaging\destinations-service.xml
		If it doesnt exists then the following entry needs to be added to this file.
			<mbean code="org.jboss.jms.server.destination.QueueService"
			      name="jboss.messaging.destination:service=Queue,name=DLQ"
			      xmbean-dd="xmdesc/Queue-xmbean.xml">
			      <depends optional-attribute-name="ServerPeer">jboss.messaging:service=ServerPeer</depends>
			      <depends>jboss.messaging:service=PostOffice</depends>
			</mbean>

	     - java.naming.provider.url=localhost:1099  the server name and the port is correct. 	 	

   5.4 Seelct the GCDEnterprise application and Run it on the configured Jboss server.   	
 
	
6. Test Instructions		
=========================================================================================================================================================================  
	a. REST webservices

	   Open command prompt to curl installation directory.
       
	   Now use the following commands to test the application using following CURLS
	   

 		   
	   a. push: this API pushes messages to the queue. 
	      NOTE: The path configured for this webservices is gcd	

		curl -X POST -i -H "Content-type: text/html" http://localhost:8080/GCDEnterpriseWeb/api/gcd/14/15


           b. list: this API gets the list of all integers ever added to the queue.
		NOTE: The path configured for this webservices is gcd
		
		curl -X GET -i http://localhost:8080/GCDEnterpriseWeb/api/gcd/list

	
       b. SOAP sebservices.
	  The 3 SOAP APIs can be tested by any SOAP client like SOAPUI using the wsld provided. The WSDL is present under   

	  GCDEnterpriseWeb\WebContent\wsdl\GcdOpsSoapService.wsdl
