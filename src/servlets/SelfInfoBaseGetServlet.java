package servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entitys.LoginDataResponse;
import entitys.SelfInfoBaseDataResponse;
import services.SelfInfoBaseDaoImp;
import utils.ResponseCommon;
import utils.ResponseDesolve;

/**
 * url: http://localhost:8080/Xing/SelfInfoBaseGetServlet?id=123456789
 */
public class SelfInfoBaseGetServlet extends HttpServlet {

	private SelfInfoBaseDaoImp baseInfoDao = null;
	private List<SelfInfoBaseDataResponse> lists = null;
	private String mpassword = null;
	private Boolean flag;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("----------------------");
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		String uid = request.getParameter("id");
		Map<String, Object> data = new HashMap<String, Object>();
		String responseStr=null;
		baseInfoDao = new SelfInfoBaseDaoImp();
		lists = baseInfoDao.queryById(uid);
		System.out.println(lists);
		if(lists==null||lists.size()==0){
			responseStr=ResponseDesolve.getInstance().desolve(ResponseCommon.Code.FAILE, ResponseCommon.Msg.ERROR_FAILE_LOGIN_NO_USER);
		}else{
			//�˺Ŵ���  ��֤����
			for(int i=0;i<lists.size();i++){
				SelfInfoBaseDataResponse infoResponse=lists.get(i);
				responseStr=ResponseDesolve.getInstance().desolve(infoResponse, ResponseCommon.Msg.ERROR_NORMAL);
			}
		}
//		System.out.println(data);
//		CommonUtil.renderJson(response, data);
		ResponseDesolve.getInstance().sendResponse(responseStr, request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
