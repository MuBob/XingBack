package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entitys.LoginDataResponse;
import services.LoginImp;
import utils.CommonUtil;
import utils.ResponseCommon;
import utils.ResponseDesolve;

/**
 * login接口
 * 返回 true/false
 * url: http://localhost:8080/Xing/LoginServlet?id=123456789&pwd=111111
 * @author WangJinXing
 *
 */
public class LoginServlet extends HttpServlet {

	private LoginImp loginDao = null;
	private List<LoginDataResponse> lists = null;
	public LoginServlet() {
		super();
		loginDao = new LoginImp();
		lists=new ArrayList<LoginDataResponse>();
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("----------------------");
		String number = request.getParameter("id");
		String password = request.getParameter("pwd");
		Map<String, Object> data = new HashMap<String, Object>();
		String responseStr=null;
		lists.clear();
		lists.addAll( loginDao.queryById(number));
		System.out.println(lists);
		if(lists.size()==0){
			//没有该账号  创建注册
//			RegisterDaoImp registerDaoImp = new RegisterDaoImp();
//			registerDaoImp.Savedata(number, password);
//			data.put("result", "注册成功");
			responseStr=ResponseDesolve.getInstance().desolve(ResponseCommon.Code.FAILE, ResponseCommon.Msg.ERROR_FAILE_LOGIN_NO_USER);
		}else{
			//账号存在  验证密码
			for(int i=0;i<lists.size();i++){
				LoginDataResponse loginResponse=lists.get(i);
				if(loginResponse.getPwd().equals(password)){
//					data.put("result", "登录成功");
					responseStr=ResponseDesolve.getInstance().desolve(loginResponse, ResponseCommon.Msg.ERROR_FAILE_LOGIN);
					break;
				}else{
					responseStr=ResponseDesolve.getInstance().desolve(ResponseCommon.Code.FAILE, ResponseCommon.Msg.ERROR_FAILE_LOGIN_ERROR_INFO);
//					data.put("result", "登录失败");
				}
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
