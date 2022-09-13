package gov.payeejdbc.payeejdbc.repository;
import javax.validation.Valid;

import org.json.JSONArray;
import gov.payeejdbc.payeejdbc.exception.PayeeException;
import gov.payeejdbc.payeejdbc.request.EmployeeSaveRequest;


public interface PayeeRepository {
	JSONArray getEmployeeType() throws PayeeException;

	JSONArray getServiceCategory() throws PayeeException;

	JSONArray getSubServiceCategory(Integer serviceCategoryId) throws PayeeException;

	Object getJanadharInfo(String janAadharId)  throws PayeeException;

	Object getMemberByInfo(String memberId)throws PayeeException;

	Object employeeSave(@Valid EmployeeSaveRequest employeeSaveRequest)throws PayeeException;
}
