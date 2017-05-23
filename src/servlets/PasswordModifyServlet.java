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
import services.PasswordManageDaoImp;
import utils.ResponseCommon;
import utils.ResponseDesolve;
import utils.StringUtil;

/**
 * login接口 返回 true/false url:
 * http://localhost:8080/Xing/PasswordModifyServlet?id=123456789&pwd_old=111111&pwd_new=1
 * 
 * @author WangJinXing
 *
 */
public class PasswordModifyServlet extends HttpServlet {

	private PasswordManageDaoImp dao = null;
	private List<LoginDataResponse> lists = null;

	public PasswordModifyServlet() {
		super();
		dao = new PasswordManageDaoImp();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("----------------------");
		String number = request.getParameter("id");
		String password_old = request.getParameter("pwd_old");
		String password_new = request.getParameter("pwd_new");
		String responseStr = null;
		if (StringUtil.isNull(number)||StringUtil.isNull(password_old)||StringUtil.isNull(password_new)) {
			responseStr = ResponseDesolve.getInstance().desolve(ResponseCommon.Code.ERROR_PARAMS,
					ResponseCommon.Msg.ERROR_PARAMS);
		}
		lists=dao.queryById(number);
		System.out.println(lists);
		if (lists==null||lists.size() == 0) {
			// 没有该账号
			responseStr = ResponseDesolve.getInstance().desolve(ResponseCommon.Code.FAILE,
					ResponseCommon.Msg.ERROR_FAILE_LOGIN_NO_USER);
		} else {
			// 账号存在 验证密码
			LoginDataResponse loginResponse = lists.get(0);
			if (loginResponse.getPwd().equals(password_old)) {
				boolean changed = dao.modify(number, password_new);
				if (changed) {
					responseStr = ResponseDesolve.getInstance().desolve(ResponseCommon.Code.SUCCESS,
							ResponseCommon.Msg.SUCCESS_MODIFY);
				} else {
					responseStr = ResponseDesolve.getInstance().desolve(ResponseCommon.Code.FAILE,
							ResponseCommon.Msg.ERROR_FAILE_MODIFY);
				}
			} else {
				responseStr = ResponseDesolve.getInstance().desolve(ResponseCommon.Code.FAILE,
						ResponseCommon.Msg.ERROR_FAILE_LOGIN_ERROR_INFO);
			}
		}
		ResponseDesolve.getInstance().sendResponse(responseStr, request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
