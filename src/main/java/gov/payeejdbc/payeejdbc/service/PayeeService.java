package gov.payeejdbc.payeejdbc.service;

import javax.validation.Valid;

import org.json.JSONArray;

import gov.payeejdbc.payeejdbc.exception.PayeeException;
import gov.payeejdbc.payeejdbc.request.EmployeeSaveRequest;

public interface PayeeService {
	JSONArray getEmployeeType() throws PayeeException;

	JSONArray getServiceCategory() throws PayeeException;

	JSONArray getSubServiceCategory(String serviceCategory) throws PayeeException;

	Object getJanadharInfo(String janAadharId)throws PayeeException;

	Object getMemberByInfo(String string)throws PayeeException;

	Object employeeSave(@Valid EmployeeSaveRequest employeeSaveRequest)throws PayeeException;
}
