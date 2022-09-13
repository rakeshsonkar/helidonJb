package gov.payeejdbc.payeejdbc.controller;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.ValidationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.json.JSONArray;
import org.json.JSONObject;
import gov.payeejdbc.payeejdbc.exception.PayeeException;
import gov.payeejdbc.payeejdbc.request.EmployeeSaveRequest;
import gov.payeejdbc.payeejdbc.respose.ApiResponse;
import gov.payeejdbc.payeejdbc.respose.Error;
import gov.payeejdbc.payeejdbc.service.PayeeService;



@Path("/")
@RequestScoped
public class PayeeController {
	
	
	@Inject
	private PayeeService payeeService;
	
	
	
	@Path("/getEmployeeType")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEmployeeType() {
		try {
		JSONArray jsonArray =  payeeService.getEmployeeType();
			return Response.ok(ApiResponse.success(jsonArray.toList())).build();
		} catch (PayeeException e) {
			System.out.println(e);
			final String errorCode = "internal Server Error";
			final Error error = Error.create(errorCode, e.getMessage());
			return Response.status(Response.Status.BAD_REQUEST).entity(ApiResponse.error(error)).build();
		}
	}
	
	@Path("/getServiceCategory")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response getServiceCategory() {
		try {
			JSONArray jsonArray =payeeService.getServiceCategory();
			return Response.ok(ApiResponse.success(jsonArray.toList())).build();
		} catch (PayeeException e) {
			System.out.println(e);
			final String errorCode = "internal Server Error";
			final Error error = Error.create(errorCode, e.getMessage());
			return Response.status(Response.Status.BAD_REQUEST).entity(ApiResponse.error(error)).build();
		}
	}
	
	@Path("/getSubServiceCategory")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSubServiceCategory(@RequestBody String serviceCategory) {
		try {
			JSONArray jsonArray = payeeService.getSubServiceCategory(serviceCategory);
			return Response.ok(ApiResponse.success(jsonArray.toList())).build();
		} catch (PayeeException e) {
			System.out.println(e);
			final String errorCode = "internal Server Error";
			final Error error = Error.create(errorCode, e.getMessage());
			return Response.status(Response.Status.BAD_REQUEST).entity(ApiResponse.error(error)).build();
		}
	}
	
	@Path("/getJanadharInfo")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response getJanadharInfo(@RequestBody String janAadhar) {
		try {
			JSONObject jsonObject = new JSONObject(janAadhar);
//			final   janAadharId = jsonObject.get("janAadharId");
		return Response.ok(ApiResponse.success(payeeService.getJanadharInfo((String) jsonObject.get("janAadharId")))).build();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
			final String errorCode = "internal Server Error";
			final Error error = Error.create(errorCode, e.getMessage());
			return Response.status(Response.Status.BAD_REQUEST).entity(ApiResponse.error(error)).build();
		}
	}
	@Path("/getMemberByInfo")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMemberByInfo(@RequestBody String MemberId) {
		try {
			JSONObject jsonObject = new JSONObject(MemberId);
//			final   janAadharId = jsonObject.get("janAadharId");
		return Response.ok(ApiResponse.success(payeeService.getMemberByInfo((String) jsonObject.get("MemberId")))).build();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
			final String errorCode = "internal Server Error";
			final Error error = Error.create(errorCode, e.getMessage());
			return Response.status(Response.Status.BAD_REQUEST).entity(ApiResponse.error(error)).build();
		}
	}
	
	@Path("/empSave")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response employeeSave( @Valid EmployeeSaveRequest employeeSaveRequest){ 
		try {
			
			System.out.println("Input Data -> ......."+employeeSaveRequest.toString());
			
		JSONObject result=(JSONObject) payeeService.employeeSave(employeeSaveRequest);
			
		return Response.ok(ApiResponse.success(result.toMap())).build();
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
			final String errorCode = "internal Server Error";
			final Error error = Error.create(errorCode, e.getMessage());
			return Response.status(Response.Status.BAD_REQUEST).entity(ApiResponse.error(error)).build();
		}
	}
}
