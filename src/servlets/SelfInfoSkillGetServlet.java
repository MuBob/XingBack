package servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entitys.SelfInfoBaseDataResponse;
import entitys.SelfInfoRecordDataResponse;
import entitys.SelfInfoSkillDataResponse;
import services.SelfInfoRecordDaoImp;
import services.SelfInfoSkillDaoImp;
import utils.Log;
import utils.ResponseCommon;
import utils.ResponseDesolve;

/**
 * url: http://localhost:8080/Xing/SelfInfoSkillGetServlet?id=123456789
 */
public class SelfInfoSkillGetServlet extends HttpServlet {

	private SelfInfoSkillDaoImp infoDao = null;
	private SelfInfoSkillDataResponse queryResult = null;
	
	public SelfInfoSkillGetServlet(){
		super();
		infoDao = new SelfInfoSkillDaoImp();
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("----------------------");
		String uid = request.getParameter("id");
		Map<String, Object> data = new HashMap<String, Object>();
		String responseStr=null;
		queryResult = infoDao.queryById(uid);
		Log.i("SelfInfoSkillGetServlet", "queryResult="+queryResult);
		if(queryResult==null||queryResult.getMap()==null){
			responseStr=ResponseDesolve.getInstance().desolve(ResponseCommon.Code.FAILE, ResponseCommon.Msg.ERROR_FAILE_LOGIN_NO_USER);
		}else{
			responseStr=ResponseDesolve.getInstance().desolve(queryResult, ResponseCommon.Msg.ERROR_NORMAL);
		}
		ResponseDesolve.getInstance().sendResponse(responseStr, request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
