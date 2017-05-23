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

import db.LogSignIn;
import entitys.LoginDataResponse;
import entitys.SignInDataResponse;
import services.LoginImp;
import services.SignManageDaoImp;
import utils.DateUtil;
import utils.ResponseCommon;
import utils.ResponseDesolve;
import utils.StringUtil;

/**
 * url: http://localhost:8080/Xing/SignInServlet?id=123456789&place=dfai&sign=true
 * 
 * @author WangJinXing
 *
 */
public class SignInServlet extends HttpServlet {

	private LoginImp loginDao = null;
	private SignManageDaoImp signInDao = null;
	private List<LoginDataResponse> lists = null;

	public SignInServlet() {
		super();
		loginDao = new LoginImp();
		signInDao = new SignManageDaoImp();
		lists = new ArrayList<LoginDataResponse>();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("----------------------");
		String id = request.getParameter("id");
		String sign = request.getParameter("sign");
		String time = request.getParameter("time");
		String place = request.getParameter("place");
		String responseStr = null;
		if (StringUtil.isNull(time)) {
			time = DateUtil.getCurrentTime();
		}
		if (StringUtil.isNull(id) || StringUtil.isNull(place)) {
			responseStr = ResponseDesolve.getInstance().desolve(ResponseCommon.Code.FAILE,
					ResponseCommon.Msg.ERROR_FAILE_LOGIN_NO_USER);
		} else {
			Map<String, Object> data = new HashMap<String, Object>();
			lists.clear();
			lists.addAll(loginDao.queryById(id));
			System.out.println(lists);
			if (lists.size() == 0) {
				// 没有该账号 创建注册
				responseStr = ResponseDesolve.getInstance().desolve(ResponseCommon.Code.FAILE,
						ResponseCommon.Msg.ERROR_FAILE_LOGIN_NO_USER);
			} else {
				// 账号存在 验证密码
				if (StringUtil.isNull(sign) || sign.equals("false")) {
					LogSignIn todayBean = signInDao.hasFirstLogToday(id, time);
					responseStr = ResponseDesolve.getInstance().desolve(new SignInDataResponse(todayBean),
							ResponseCommon.Msg.ERROR_FAILE_LOGIN_NO_USER);
				} else if (sign.equals("true")) {
					int signCount = signInDao.signInToday(id, place, time);
					if (signCount > 2) {
						responseStr = ResponseDesolve.getInstance().desolve(ResponseCommon.Code.FAILE,
								ResponseCommon.Msg.FAILE_SIGN_IN_DUPLIDE);
					} else if (signCount > 0) {
						responseStr = ResponseDesolve.getInstance().desolve(ResponseCommon.Code.SUCCESS,
								ResponseCommon.Msg.SUCCESS_SIGN_IN);
					} else {
						responseStr = ResponseDesolve.getInstance().desolve(ResponseCommon.Code.FAILE,
								ResponseCommon.Msg.FAILE_SIGN_IN);
					}
				} else {
					responseStr = ResponseDesolve.getInstance().desolve(ResponseCommon.Code.ERROR_PARAMS,
							ResponseCommon.Msg.ERROR_PARAMS);
				}
			}
		}
		ResponseDesolve.getInstance().sendResponse(responseStr, request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
