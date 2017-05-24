package servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.LogSignOut;
import entitys.LoginDataResponse;
import entitys.SignOutDataResponse;
import services.LoginDaoImp;
import services.SignOutDaoImp;
import utils.DateUtil;
import utils.Log;
import utils.ResponseCommon;
import utils.ResponseDesolve;
import utils.StringUtil;

/**
 * url: http://localhost:8080/Xing/SignOutServlet?id=123456789&place=dfai&sign=true
 * 
 * @author WangJinXing
 *
 */
public class SignOutServlet extends HttpServlet {

	private LoginDaoImp loginDao = null;
	private SignOutDaoImp signOutDao = null;
	private List<LoginDataResponse> lists = null;

	public SignOutServlet() {
		super();
		loginDao = new LoginDaoImp();
		signOutDao = new SignOutDaoImp();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("----------------------");
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		String id = request.getParameter("id");
		String sign = request.getParameter("sign");
		String time = request.getParameter("time");
		String place = request.getParameter("place");
		String responseStr = null;
		if (StringUtil.isNull(time)) {
			time = DateUtil.getCurrentTime();
		}
		if (StringUtil.isNull(id)) {
			responseStr = ResponseDesolve.getInstance().desolve(ResponseCommon.Code.ERROR_PARAMS,
					ResponseCommon.Msg.ERROR_PARAMS);
		} else {
			Map<String, Object> data = new HashMap<String, Object>();
			lists = loginDao.queryById(id);
			if (lists==null||lists.size() == 0) {
				// 没有该账号 创建注册
				responseStr = ResponseDesolve.getInstance().desolve(ResponseCommon.Code.FAILE,
						ResponseCommon.Msg.ERROR_FAILE_LOGIN_NO_USER);
			} else {
				// 账号存在 验证密码
				if (StringUtil.isNull(sign) || sign.equals("false")) {
					LogSignOut todayBean = signOutDao.hasFirstLogToday(id, time);
					responseStr = ResponseDesolve.getInstance().desolve(new SignOutDataResponse(todayBean),
							ResponseCommon.Msg.ERROR_FAILE_LOGIN_NO_USER);
				} else if (sign.equals("true")) {
					int signCount = signOutDao.signToday(id, place, time);
					Log.d("SignOutServlet", "signCount="+signCount);
					if (signCount > 2) {
						responseStr = ResponseDesolve.getInstance().desolve(ResponseCommon.Code.FAILE,
								ResponseCommon.Msg.FAILE_SIGN_OUT_DUPLIDE);
					} else if (signCount > 0) {
						responseStr = ResponseDesolve.getInstance().desolve(ResponseCommon.Code.SUCCESS,
								ResponseCommon.Msg.SUCCESS_SIGN_OUT);
					} else {
						responseStr = ResponseDesolve.getInstance().desolve(ResponseCommon.Code.FAILE,
								ResponseCommon.Msg.FAILE_SIGN_OUT);
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
