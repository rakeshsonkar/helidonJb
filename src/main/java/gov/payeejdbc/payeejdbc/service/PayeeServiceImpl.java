package gov.payeejdbc.payeejdbc.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;

import org.json.JSONArray;
import org.json.JSONObject;

import gov.payeejdbc.payeejdbc.exception.PayeeException;
import gov.payeejdbc.payeejdbc.repository.PayeeRepository;
import gov.payeejdbc.payeejdbc.request.EmployeeSaveRequest;


@ApplicationScoped
public class PayeeServiceImpl implements PayeeService {
	@Inject
	private PayeeRepository payeeRepository;

	@Override
	public JSONArray getEmployeeType() throws PayeeException {
		try {
			return payeeRepository.getEmployeeType();
		} catch (Exception e) {
			final String mes = "Something went Wrong";
			throw new PayeeException(mes);
		}

	}

	@Override
	public JSONArray getServiceCategory() throws PayeeException {
		try {

			return payeeRepository.getServiceCategory();
		} catch (Exception e) {
			final String mes = "Something went Wrong";
			throw new PayeeException(mes);
		}
	}

	@Override
	public JSONArray getSubServiceCategory(String serviceCategory) throws PayeeException {
		try {
			
			JSONObject jsonObject = new JSONObject(serviceCategory);
			final  Integer serviceCategoryId =(Integer) jsonObject.get("serviceCategoryId");
			
			return payeeRepository.getSubServiceCategory(serviceCategoryId);
		} catch (Exception e) {
			final String mes = "Something went Wrong";
			throw new PayeeException(mes);
		}
	}

	@Override
	public Object getJanadharInfo(String janAadharId) throws PayeeException {
		try {

			return payeeRepository.getJanadharInfo(janAadharId);
		} catch (Exception e) {
			final String mes = "Something went Wrong";
			throw new PayeeException(mes);
		}

	}

	@Override
	public Object getMemberByInfo(String memberId) throws PayeeException {
		try {

			return payeeRepository.getMemberByInfo(memberId);
		} catch (Exception e) {
			final String mes = "Something went Wrong";
			throw new PayeeException(mes);
		}
	}

	@Override
	public Object employeeSave(@Valid EmployeeSaveRequest employeeSaveRequest) throws PayeeException {
		try {

			return payeeRepository.employeeSave(employeeSaveRequest);
		} catch (Exception e) {
			final String mes = "Something went Wrong";
			throw new PayeeException(mes);
		}
	}
}
