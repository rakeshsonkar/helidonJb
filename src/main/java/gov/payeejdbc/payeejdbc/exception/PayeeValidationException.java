package gov.payeejdbc.payeejdbc.exception;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.json.JSONObject;

import com.google.common.collect.Iterables;

import gov.payeejdbc.payeejdbc.respose.ApiResponse;

@Provider
public class PayeeValidationException implements ExceptionMapper<ConstraintViolationException> {
    @Override
    public Response toResponse(ConstraintViolationException exception) {
        Set<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();
        JSONObject jSONObject = new  JSONObject();
        constraintViolations.stream().forEach(data->{
        	 String name = Iterables.getLast(data.getPropertyPath()).getName();
        	 String message = data.getMessage();
        	 jSONObject.put(name,message);
        });
       
//        List<String> errorMessages = constraintViolations.stream()
//                .map(constraintViolation -> {
//                    String name = Iterables.getLast(constraintViolation.getPropertyPath()).getName();
//                    
//                    
//                    return name + " " + constraintViolation.getMessage();
//                })
//                .collect(Collectors.toList());
//        
     // System.out.println("errordata -> ...   "+ jSONObject.toString(0));
        
        return Response
                .status(Response.Status.BAD_REQUEST)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(ApiResponse.errordata(jSONObject.toMap()))
                .build();
    }
}