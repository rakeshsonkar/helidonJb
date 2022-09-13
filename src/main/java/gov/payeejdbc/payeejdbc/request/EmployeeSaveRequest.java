package gov.payeejdbc.payeejdbc.request;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class EmployeeSaveRequest {
	
	@NotNull(message = "please provide janAadharId")
	private Long janAadharId;
	@NotNull
	private Long memberId;
	@NotNull
	private Integer employeeType;
	@NotNull
	private Integer serviceCategoryId;
	@NotNull
	private Integer subServiceCategory;
	
	@NotNull
	private String  empName;
	@NotNull
	private String fname;
	@NotNull
	private String mname;
	
	private Integer appomentOrderNo;
	
	@NotNull
    private String dob;
	
	@NotEmpty
    private String mobile;
    @NotNull
    private String aadhaar;
    
	private BankDetail bank;
}
