package gov.payeejdbc.payeejdbc.request;

import lombok.Data;

@Data
public class BankDetail {
	private String bankAccNo;
	private String ifscCode;
	private String bankName;
	private String branchName;
}
