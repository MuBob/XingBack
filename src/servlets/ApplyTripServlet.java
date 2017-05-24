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
import db.LogTrip;
import entitys.LoginDataResponse;
import entitys.SignInDataResponse;
import services.LogTripDaoImp;
import services.LoginDaoImp;
import services.SignInDaoImp;
import utils.DateUtil;
import utils.Log;
import utils.ResponseCommon;
import utils.ResponseDesolve;
import utils.StringUtil;

/**
 *  url: 
 *  http://localhost:8080/Xing/ApplyTripServlet?id=123456780&dayStart=3月7日&dayEnd=3月9日&days=2.5&way=正在进行中&time=1&matters=da&place=didiao
 * 
 * @author WangJinXing
 *
 */
public class ApplyTripServlet extends HttpServlet {

	private LoginDaoImp loginDao = null;
	private LogTripDaoImp tripDao = null;
	private List<LoginDataResponse> lists = null;

	public ApplyTripServlet() {
		super();
		loginDao = new LoginDaoImp();
		tripDao = new LogTripDaoImp();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("-----START-----------------");
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		String id = request.getParameter("id");
		String days = request.getParameter("days");
		String way = request.getParameter("way");
		String time = request.getParameter("time");
		String matters = request.getParameter("matters");
		String dayStart = request.getParameter("dayStart");
		String dayEnd = request.getParameter("dayEnd");
		String responseStr = null;
		Log.i("ApplyTripServlet.doGet", "id="+id);
		if (StringUtil.isNull(time)) {
			time = DateUtil.getCurrentTime();
		}
		if (StringUtil.isNull(id) || StringUtil.isNull(way)||StringUtil.isNull(matters) || StringUtil.isNull(days)) {
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
				List<LogTrip> queryList = tripDao.queryByApplyId(id);
				if (queryList == null || queryList.size() <= 0) {
					isApply = tripDao.insertLog(id, time, dayStart, dayEnd, days, way, matters);
				} else {
					for (int i = 0; i < queryList.size(); i++) {
						LogTrip tripOne = queryList.get(i);
						if (tripOne.getReply() == 0) {
							isApply = tripDao.updateApplyTimeById(tripOne.get_id(), time, way,matters);
							break;
						}
					}
					if (!isApply) {
						isApply = tripDao.insertLog(id, time, dayStart, dayEnd, days, way, matters);
					}
				}
				if (isApply) {
					responseStr = ResponseDesolve.getInstance().desolve(ResponseCommon.Code.SUCCESS,
							ResponseCommon.Msg.SUCCESS_APPLY_TRIP);
				} else {
					responseStr = ResponseDesolve.getInstance().desolve(ResponseCommon.Code.FAILE,
							ResponseCommon.Msg.FAILE_APPLY_TRIP);

				}
			}
		}
		ResponseDesolve.getInstance().sendResponse(responseStr, request, response);
		System.out.println("-------END---------------");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
