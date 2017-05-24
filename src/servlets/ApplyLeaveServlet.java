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

import db.LogLeave;
import db.LogSignIn;
import entitys.LoginDataResponse;
import entitys.SignInDataResponse;
import services.LogLeaveDaoImp;
import services.LoginDaoImp;
import services.SignInDaoImp;
import utils.DateUtil;
import utils.Log;
import utils.ResponseCommon;
import utils.ResponseDesolve;
import utils.StringUtil;

/**
 * url: http://localhost:8080/Xing/ApplyLeaveServlet?id=123456789&dayStart=3月7日&
 * dayEnd=3月9日&days=2.5&reason=正在进行中&time=1&picture=http://da.jpg
 * 
 * @author WangJinXing
 *
 */
public class ApplyLeaveServlet extends HttpServlet {

	private LoginDaoImp loginDao = null;
	private LogLeaveDaoImp leaveDao = null;
	private List<LoginDataResponse> lists = null;

	public ApplyLeaveServlet() {
		super();
		loginDao = new LoginDaoImp();
		leaveDao = new LogLeaveDaoImp();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("----------------------");
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		String id = request.getParameter("id");
		String days = request.getParameter("days");
		String reason = request.getParameter("reason");
		String time = request.getParameter("time");
		String picture = request.getParameter("picture");
		String dayStart = request.getParameter("dayStart");
		String dayEnd = request.getParameter("dayEnd");
		String responseStr = null;
		Log.i("ApplyLeaveServlet.doGet", "id=" + id);
		if (StringUtil.isNull(time)) {
			time = DateUtil.getCurrentTime();
		}
		if (StringUtil.isNull(id) || StringUtil.isNull(reason) || StringUtil.isNull(dayStart)
				|| StringUtil.isNull(dayEnd)) {
			responseStr = ResponseDesolve.getInstance().desolve(ResponseCommon.Code.ERROR_PARAMS,
					ResponseCommon.Msg.ERROR_PARAMS);
		} else {
			lists = loginDao.queryById(id);
			if (lists == null || lists.size() == 0) {
				// 没有该账号 创建注册
				responseStr = ResponseDesolve.getInstance().desolve(ResponseCommon.Code.FAILE,
						ResponseCommon.Msg.ERROR_FAILE_LOGIN_NO_USER);
			} else {
				// 账号存在
				boolean isApply = false;
				List<LogLeave> queryList = leaveDao.queryByApplyId(id, dayStart, dayEnd);
				if (queryList == null || queryList.size() <= 0) {
					isApply = leaveDao.insertLog(id, time, dayStart, dayEnd, reason, picture);
				} else {
					for (int i = 0; i < queryList.size(); i++) {
						LogLeave leaveOne = queryList.get(i);
						if (leaveOne.getReply() == 0) {
							isApply = leaveDao.updateApplyTimeById(leaveOne.get_id(), time, reason);
							break;
						}
					}
					if (!isApply) {
						isApply = leaveDao.insertLog(id, time, dayStart, dayEnd, reason, picture);
					}
				}
				if (isApply) {
					responseStr = ResponseDesolve.getInstance().desolve(ResponseCommon.Code.SUCCESS,
							ResponseCommon.Msg.SUCCESS_APPLY_LEAVE);
				} else {
					responseStr = ResponseDesolve.getInstance().desolve(ResponseCommon.Code.FAILE,
							ResponseCommon.Msg.FAILE_APPLY_LEAVE);

				}
			}
		}
		ResponseDesolve.getInstance().sendResponse(responseStr, request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
