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
import utils.Log;
import utils.ResponseCommon;
import utils.ResponseDesolve;
import utils.StringUtil;

/**
 * url:
 * http://localhost:8080/Xing/SelfInfoBaseModifyServlet?id=123456789&birthday=2017年2月2日
 */
public class SelfInfoBaseModifyServlet extends HttpServlet {

	private SelfInfoBaseDaoImp baseInfoDao = null;
	private List<SelfInfoBaseDataResponse> lists = null;
	private String mpassword = null;
	private Boolean flag;

	public SelfInfoBaseModifyServlet() {
		super();
		baseInfoDao = new SelfInfoBaseDaoImp();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("----------------------");
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		String uid = request.getParameter("id");
		String name = request.getParameter("name");
		String sex = request.getParameter("sex");
		String idCard = request.getParameter("idCard");
		String birthday = request.getParameter("birthday");
		String email = request.getParameter("email");
		Map requestMap = request.getParameterMap();
		Map<String, Object> data = new HashMap<String, Object>();
		String responseStr = null;
		if (StringUtil.isNull(uid)) {
			responseStr = ResponseDesolve.getInstance().desolve(ResponseCommon.Code.ERROR_PARAMS,
					ResponseCommon.Msg.ERROR_PARAMS);
		} else {
		lists = baseInfoDao.queryById(uid);
		if (lists == null || lists.size() == 0) {
			responseStr = ResponseDesolve.getInstance().desolve(ResponseCommon.Code.FAILE,
					ResponseCommon.Msg.ERROR_FAILE_LOGIN_NO_USER);
		} else {
			// 账号存在 验证密码
			SelfInfoBaseDataResponse queryBean = lists.get(0);
			String[] params = { StringUtil.isNull(name) ? queryBean.getName() : name,
					StringUtil.isNull(sex) ? queryBean.getSex() : sex,
					StringUtil.isNull(idCard) ? queryBean.getIdCard() : idCard,
					StringUtil.isNull(birthday) ? queryBean.getBirthday() : birthday,
					StringUtil.isNull(email) ? queryBean.geteMail() : email
			};
			Log.i("Servlet", "paramsBirthday=" + params[params.length - 1]);
			baseInfoDao.modify(uid, params);
			responseStr = ResponseDesolve.getInstance().desolve(ResponseCommon.Code.SUCCESS,
					ResponseCommon.Msg.SUCCESS_MODIFY);
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
