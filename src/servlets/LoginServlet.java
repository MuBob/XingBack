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
import services.LoginDaoImp;
import utils.CommonUtil;
import utils.ResponseCommon;
import utils.ResponseDesolve;
import utils.StringUtil;

/**
 * login�ӿ� ���� true/false url:
 * http://localhost:8080/Xing/LoginServlet?id=123456789&pwd=111111
 * 
 * @author WangJinXing
 *
 */
public class LoginServlet extends HttpServlet {

	private LoginDaoImp loginDao = null;
	private List<LoginDataResponse> lists = null;

	public LoginServlet() {
		super();
		loginDao = new LoginDaoImp();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("----------------------");
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		String number = request.getParameter("id");
		String password = request.getParameter("pwd");
		Map<String, Object> data = new HashMap<String, Object>();
		String responseStr = null;
		if (StringUtil.isNull(number) || StringUtil.isNull(password)) {
			responseStr = ResponseDesolve.getInstance().desolve(ResponseCommon.Code.ERROR_PARAMS,
					ResponseCommon.Msg.ERROR_PARAMS);
		} else {

			lists = loginDao.queryById(number);
			System.out.println(lists);
			if (lists == null || lists.size() == 0) {
				// û�и��˺� ����ע��
				// RegisterDaoImp registerDaoImp = new RegisterDaoImp();
				// registerDaoImp.Savedata(number, password);
				// data.put("result", "ע��ɹ�");
				responseStr = ResponseDesolve.getInstance().desolve(ResponseCommon.Code.FAILE,
						ResponseCommon.Msg.ERROR_FAILE_LOGIN_NO_USER);
			} else {
				// �˺Ŵ��� ��֤����
				for (int i = 0; i < lists.size(); i++) {
					LoginDataResponse loginResponse = lists.get(i);
					if (loginResponse.getPwd().equals(password)) {
						// data.put("result", "��¼�ɹ�");
						responseStr = ResponseDesolve.getInstance().desolve(loginResponse,
								ResponseCommon.Msg.ERROR_FAILE_LOGIN);
						break;
					} else {
						responseStr = ResponseDesolve.getInstance().desolve(ResponseCommon.Code.FAILE,
								ResponseCommon.Msg.ERROR_FAILE_LOGIN_ERROR_INFO);
						// data.put("result", "��¼ʧ��");
					}
				}
			}
		}
		// System.out.println(data);
		// CommonUtil.renderJson(response, data);
		ResponseDesolve.getInstance().sendResponse(responseStr, request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
