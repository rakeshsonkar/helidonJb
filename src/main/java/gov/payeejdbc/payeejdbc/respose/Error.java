package gov.payeejdbc.payeejdbc.respose;

import lombok.Data;

@Data
public class Error {
	private String code;
	private String description;
	
	public static Error create(final String code,final String description) {
		Error error =new Error();
		error.setCode(code);
		error.setDescription(description);
		return error;
	}
}