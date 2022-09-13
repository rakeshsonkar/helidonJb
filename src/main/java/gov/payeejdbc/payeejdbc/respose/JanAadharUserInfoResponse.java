package gov.payeejdbc.payeejdbc.respose;
import lombok.Data;

@Data
public class JanAadharUserInfoResponse {
	private Integer janMid;
	private String acc;
	private String nameEng;
	private String nameHnd;
	private String gender;
	private String mNameEng;
	private String fNameEng;
	private String sNameEng;
	private String ifsc;
	private Long aadhar;
	private String panNo;
	private String dob;
	private String micr;
	private String mobile;
	private String bankName;
	private String bankBranch;
}
