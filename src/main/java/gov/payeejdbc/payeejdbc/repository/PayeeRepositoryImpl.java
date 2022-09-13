package gov.payeejdbc.payeejdbc.repository;

import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;
import javax.sql.rowset.serial.SerialClob;
import javax.validation.Valid;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

import com.google.gson.Gson;

import gov.payeejdbc.payeejdbc.common.JanAadhar;
import gov.payeejdbc.payeejdbc.common.JanaadhaarMemberId;
import gov.payeejdbc.payeejdbc.exception.PayeeException;
import gov.payeejdbc.payeejdbc.request.EmployeeSaveRequest;
import gov.payeejdbc.payeejdbc.request.JanAadharMember;
import gov.payeejdbc.payeejdbc.respose.JanAadharUserInfoResponse;
import oracle.sql.CLOB;

@ApplicationScoped
public class PayeeRepositoryImpl implements PayeeRepository {
	@Inject
	@RestClient
	private JanAadhar janaadhar;

	@Inject
	@RestClient
	private JanaadhaarMemberId janaadhaarMemberId;

	@Inject
	@Named("dbusers")
	private DataSource ds;

	@Override
	public JSONArray getEmployeeType() throws PayeeException {
		CallableStatement cStmt = null;
		Connection conn = null;
		JSONArray jsonArray = null;
		try {
			conn = ds.getConnection();
			cStmt = conn.prepareCall("{Call MST_PAYEE(?,?,?,?)}");
			cStmt.setString("IMODE", "DD1");
			cStmt.registerOutParameter("OUT_ERR_MSG", java.sql.Types.VARCHAR);
			cStmt.registerOutParameter("OUT_STR", java.sql.Types.CLOB);
			cStmt.registerOutParameter("OUT_REQ_ID", java.sql.Types.INTEGER);
			cStmt.execute();

			Clob clob = (Clob) cStmt.getObject("OUT_STR");
			String cloData = clob.getSubString(1, (int) clob.length());
			jsonArray = new JSONArray(cloData);

		}

		catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {

				if (cStmt != null) {
					cStmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return jsonArray;
	}

	@Override
	public JSONArray getServiceCategory() throws PayeeException {
		CallableStatement cStmt = null;
		Connection conn = null;
		JSONArray jsonArray = null;
		try {
			conn = ds.getConnection();
			cStmt = conn.prepareCall("{Call MST_PAYEE(?,?,?,?)}");
			cStmt.setString("IMODE", "DD2");
			cStmt.registerOutParameter("OUT_ERR_MSG", java.sql.Types.VARCHAR);
			cStmt.registerOutParameter("OUT_STR", java.sql.Types.CLOB);
			cStmt.registerOutParameter("OUT_REQ_ID", java.sql.Types.INTEGER);
			cStmt.execute();

			Clob clob = (Clob) cStmt.getObject("OUT_STR");
			String cloData = clob.getSubString(1, (int) clob.length());
			jsonArray = new JSONArray(cloData);

		}

		catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {

				if (cStmt != null) {
					cStmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return jsonArray;
	}

	@Override
	public JSONArray getSubServiceCategory(Integer serviceCategoryId) throws PayeeException {
		CallableStatement cStmt = null;
		Connection conn = null;
		JSONArray jsonArray = null;
		try {
			conn = ds.getConnection();
			cStmt = conn.prepareCall("{Call MST_PAYEE(?,?,?,?,?)}");
			cStmt.setString("IMODE", "DD3");
			cStmt.setInt("ISRVC_CAT_ID", serviceCategoryId);
			cStmt.registerOutParameter("OUT_ERR_MSG", java.sql.Types.VARCHAR);
			cStmt.registerOutParameter("OUT_STR", java.sql.Types.CLOB);
			cStmt.registerOutParameter("OUT_REQ_ID", java.sql.Types.INTEGER);
			cStmt.execute();

			Clob clob = (Clob) cStmt.getObject("OUT_STR");
			String cloData = clob.getSubString(1, (int) clob.length());
			jsonArray = new JSONArray(cloData);
		}

		catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {

				if (cStmt != null) {
					cStmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return jsonArray;
	}

	@Override
	public Object getJanadharInfo(String janAadharId) throws PayeeException {
//		4807772669
		try {
			String xmlData = "<root><Info><janaadhaarId>" + janAadharId
					+ "</janaadhaarId><enrId></enrId><aadharId></aadharId><scheme>PEN</scheme><infoFlg>PFE</infoFlg><authMode>EKYC</authMode><dateTime>06-09-2022 14:19:12</dateTime></Info></root>";

			// System.out.println(xmlData);
			Response response = janaadhar.JanaadharInfo(xmlData);

			String Xml = response.readEntity(String.class);
			// System.out.println("Testing teh data on jonaadhar "+Xml);
			JSONObject json = XML.toJSONObject(Xml);
			// System.out.println(json);
			JSONObject root = (JSONObject) json.get("root");
			// System.out.println("testing the root element -> "+root);
			String cmsg = String.valueOf(root.get("cmsg"));
			// System.out.println("testing cmsg data -> ......... " + cmsg);
			JSONObject personalInfo = (JSONObject) root.get("personalInfo");
			// System.out.println("testing personal data -> ......... " +
			// personalInfo.toString(4));
			JSONArray member = personalInfo.getJSONArray("member");
			// System.out.println("testing member data -> ......... " + member.toString(4));

			List<JanAadharUserInfoResponse> resultData = new ArrayList<>();

			if (member != null) {

				// Iterating JSON array
				for (int i = 0; i < member.length(); i++) {
					JanAadharUserInfoResponse janAadharUserInfoResponse = new JanAadharUserInfoResponse();
					JSONObject singleMemberData = (JSONObject) member.get(i);

					final String fnameEng = (String) singleMemberData.get("fnameEng");
					janAadharUserInfoResponse.setFNameEng(fnameEng);

					final String mnameEng = (String) singleMemberData.get("mnameEng");
					janAadharUserInfoResponse.setMNameEng(mnameEng);

					final String snameEng = (String) singleMemberData.get("snameEng");
					janAadharUserInfoResponse.setSNameEng(snameEng);

					final String dob = (String) singleMemberData.get("dob");
					janAadharUserInfoResponse.setDob(dob);

					final String ifsc = singleMemberData.getString("ifsc");
					janAadharUserInfoResponse.setIfsc(ifsc);

					final String micr = singleMemberData.getString("micr");
					janAadharUserInfoResponse.setMicr(micr);

					final String panNo = singleMemberData.getString("panNo");
					janAadharUserInfoResponse.setPanNo(panNo);

					final String gender = singleMemberData.getString("gender");
					janAadharUserInfoResponse.setGender(gender);

					final String mobile = singleMemberData.getString("mobile");
					janAadharUserInfoResponse.setMobile(mobile);

					final Long aadhar = (long) singleMemberData.getDouble("aadhar");
					janAadharUserInfoResponse.setAadhar(aadhar);

					final String nameEng = (String) singleMemberData.get("nameEng");
					janAadharUserInfoResponse.setNameEng(nameEng);

					final String nameHnd = (String) singleMemberData.get("nameHnd");
					janAadharUserInfoResponse.setNameHnd(nameHnd);

					final String bankBranch = (String) singleMemberData.get("bankBranch");
					janAadharUserInfoResponse.setBankBranch(bankBranch);

					final String bankName = (String) singleMemberData.get("bankName");
					janAadharUserInfoResponse.setBankName(bankName);

					Integer janMid = singleMemberData.getInt("jan_mid");
					if (janMid == 0) {
						janAadharUserInfoResponse.setJanMid(singleMemberData.getInt("hof_jan_m_id"));
					} else {
						janAadharUserInfoResponse.setJanMid(janMid);
					}

					if (singleMemberData.get("acc") != null || singleMemberData.get("acc") != "") {
						// System.out.println("testing acc -> .. "+ singleMemberData.get("acc"));

						// final Long account = (Long) singleMemberData.get("acc");
						janAadharUserInfoResponse.setAcc(String.valueOf(singleMemberData.get("acc")));
					}

					resultData.add(janAadharUserInfoResponse);

				}
			}
//			 resultData.stream().forEach(data -> {

////				Long aadhar =(Long) data.get("aadhar");
//				System.out.println("testing "+data);
//				JanAadharUserInfoResponse janAadharUserInfoResponse = new JanAadharUserInfoResponse();
////				janAadharUserInfoResponse.setAadhar(data.);
//			});

			// String jsonString = json.toString(5);
			// System.out.println(jsonString);

			return resultData;
		} catch (Exception e) {
			e.printStackTrace();
			final String mes = "Something went Wrong";
			throw new PayeeException(mes);
		}
	}

	@Override
	public Object getMemberByInfo(String memberId) throws PayeeException {
		MultivaluedHashMap<String, String> map = new MultivaluedHashMap<String, String>();
		map.putSingle("janMemId", memberId);
		map.putSingle("scheme", "PEN");
		map.putSingle("authMode", "JOTP");
		map.putSingle("dateTime", "12-09-2022 00:00:00");

		// System.out.println("print map in get value" +map);
		JanAadharMember janAadharMember = new JanAadharMember();
		janAadharMember.setJanMemId(memberId);
		janAadharMember.setScheme("PEN");
		janAadharMember.setAuthMode("JOTP");
		janAadharMember.setDateTime("12-09-2022 00:00:00");
		System.out.println(janAadharMember.toString());

		Response response = janaadhaarMemberId.memberInfo(janAadharMember);
		String Xml = response.readEntity(String.class);
		// System.out.println("testing data memberId " + Xml);
		JSONObject json = XML.toJSONObject(Xml);
		System.out.println("testing data memberId " + json.toString(4));
		return null;
	}

	@SuppressWarnings("null")
	@Override
	public Object employeeSave(@Valid EmployeeSaveRequest employeeSaveRequest) throws PayeeException {
		CallableStatement cStmt = null;
		Connection conn = null;
		JSONObject jsonObject = new JSONObject();
		try {
			//System.out.println("testing By Rakesh  ----->>>>>>>>>>   "+employeeSaveRequest.toString());
			Gson gson = new Gson();
			String jsonData = gson.toJson(employeeSaveRequest);
			System.out.println("clobdata->......   " + jsonData);
//			Clob requestClob = new SerialClob(jsonData.toCharArray());
			conn = ds.getConnection();
			
			cStmt = conn.prepareCall("{Call MST_PAYEE(?,?,?,?,?)}");
			cStmt.setString("IMODE", "SAV");
			Clob stmtClob = conn.createClob();
			stmtClob.setString(1,jsonData);
			System.out.println(stmtClob);
			cStmt.setClob("IPAYEE_DATA",stmtClob);
			cStmt.registerOutParameter("OUT_ERR_MSG", java.sql.Types.VARCHAR);
			cStmt.registerOutParameter("OUT_STR", java.sql.Types.CLOB);
			cStmt.registerOutParameter("OUT_REQ_ID", java.sql.Types.INTEGER);
			cStmt.execute();
			String spCallError = cStmt.getString("OUT_ERR_MSG");
			System.out.println("OUT_REQ_ID-> .......   " + cStmt.getString("OUT_REQ_ID"));
			if(!spCallError.equalsIgnoreCase("SUCESS")) {
				
				jsonObject.put("spError",spCallError);
			}else {
				System.out.println(cStmt.getString("OUT_ERR_MSG"));
				Clob clob = (Clob) cStmt.getObject("OUT_STR");
				String cloData = clob.getSubString(1, (int) clob.length());
				System.out.println("testinhg result " + cloData);
				
			}
			
//		 jsonArray= new JSONArray(cloData);	
		}

		catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {

				if (cStmt != null) {
					cStmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return jsonObject;

	}

}
