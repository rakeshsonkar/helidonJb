package gov.payeejdbc.payeejdbc.request;

import javax.ws.rs.FormParam;

import lombok.Data;

@Data
public class JanAadharMember {
	 
	private String janMemId;
	 
	
	private String scheme;
	 

	private String authMode;
	 
	
	private String dateTime;
}
