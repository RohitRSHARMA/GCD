package com.gcdops.webservice;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.gcdops.service.IMessageService;
import com.gcdops.service.MessageServiceImpl;

@Path("/gcd")
public class GcdOpsRESTService {
	
	private IMessageService messageService;
	
		//curl -X POST -i -H "Content-type: text/html" http://localhost:8080/GCDEnterpriseWeb/api/gcd/14/15
		//Method to push two integers to a Queue
		@POST
		@Consumes(MediaType.TEXT_HTML)
		@Path("/{firstInt}/{secondInt}")		
		public Response push(@javax.ws.rs.PathParam("firstInt") Integer i1, @javax.ws.rs.PathParam("secondInt") Integer i2) throws Exception
		{
			messageService = new MessageServiceImpl();
			String res = messageService.pushMessageToQueue(i1, i2);
			return Response.status(new Integer(201).intValue()).entity(res).build(); 
			
		} 
		
		//curl -X GET -i http://localhost:8080/GCDEnterpriseWeb/api/gcd/list
		//lists all the messages added to the queue
		@GET
		@Path("/list")
		@Produces(MediaType.APPLICATION_JSON)		
		public Integer[] list() throws Exception
		{
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
		
		
}
