package gov.payeejdbc.payeejdbc.common;

import javax.enterprise.context.Dependent;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import gov.payeejdbc.payeejdbc.request.JanAadharMember;

@RegisterRestClient(baseUri = "https://api.sewadwaar.rajasthan.gov.in/app/live/Janaadhaar/Prod/Service/prodia/janMemIdInfo?client_id=6441fce8-5ca9-41db-a631-2f85bfbcd411")
@Path("/")
@Dependent
public interface JanaadhaarMemberId {

//	@POST JanAadharMember
//	@Path("/")
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	@Produces(MediaType.APPLICATION_XML)
//	public Response memberInfo(MultivaluedMap<String,String> params);
	
	
	@POST 
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_XML)
	public Response memberInfo(@RequestBody JanAadharMember janAadharMember);
}
