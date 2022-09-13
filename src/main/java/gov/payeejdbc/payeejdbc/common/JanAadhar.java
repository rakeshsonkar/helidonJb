package gov.payeejdbc.payeejdbc.common;

import javax.enterprise.context.Dependent;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(baseUri = "https://api.sewadwaar.rajasthan.gov.in/app/live/Janaadhaar/Prod/Service/Info/Fetch?client_id=bb44fa23-96a5-467c-86c0-4b635bc82113")
@Path("/")
@Dependent
public interface JanAadhar {

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public Response JanaadharInfo(@RequestBody String data);
}
