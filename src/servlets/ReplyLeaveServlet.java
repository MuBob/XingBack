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
import db.TablePersonal;
import entitys.LoginDataResponse;
import entitys.SignInDataResponse;
import services.LogLeaveDaoImp;
import services.LogTripDaoImp;
import services.LoginDaoImp;
import services.PersonalManagerDaoImp;
import services.SignInDaoImp;
import utils.DateUtil;
import utils.Log;
import utils.ResponseCommon;
import utils.ResponseDesolve;
import utils.StringUtil;

/**
 * url:
 * http://localhost:8080/Xing/ReplyLeaveServlet?id=123456789&reason=ºÃºÃµÄ&reply=1
 * 
 * @author WangJinXing
 *
 */
public class ReplyLeaveServlet extends HttpServlet {

	private PersonalManagerDaoImp personalManagerDaoImp=null;
	private LogLeaveDaoImp leaveDao = null;
	private List<LoginDataResponse> lists = null;

	public ReplyLeaveServlet() {
		super();
		leaveDao = new LogLeaveDaoImp();
		personalManagerDaoImp=new PersonalManagerDaoImp();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("-----START-----------------");
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		String uid = request.getParameter("id");
		String replyString = request.getParameter("reply");
		String reason = request.getParameter("reason");
		String time = request.getParameter("time");
		String responseStr = null;
		if (StringUtil.isNull(time)) {
			time = DateUtil.getCurrentTime();
		}
		if (StringUtil.isNull(uid) || StringUtil.isNull(replyString) || StringUtil.isNull(reason)) {
			responseStr = ResponseDesolve.getInstance().desolve(ResponseCommon.Code.ERROR_PARAMS,
					ResponseCommon.Msg.ERROR_PARAMS);
		} else {
			List<TablePersonal> user = personalManagerDaoImp.queryById(uid);
			if (user==null||user.size()<=0) {
				responseStr = ResponseDesolve.getInstance().desolve(ResponseCommon.Code.ERROR_OUT,
						ResponseCommon.Msg.ERROR_FAILE_NOUSER);
			}else {
				try {
					Log.i("ReplyTripServlet", "msg");
					List<LogLeave> unReplyOne = leaveDao.queryListByApplyId(uid, 0);
					Log.d("ReplyTripServlet", "unReplyOne=" + unReplyOne);
					if (unReplyOne != null) {
						if (unReplyOne.size() > 0) {

							int reply = Integer.parseInt(replyString);
							int position = -1;
							for (int i = 0; i < unReplyOne.size(); i++) {
								position = leaveDao.updateLogById(unReplyOne.get(i).get_id(), reply, time, reason);
							}
							if (position >= 0) {
								responseStr = ResponseDesolve.getInstance().desolve(ResponseCommon.Code.SUCCESS,
										ResponseCommon.Msg.SUCCESS_REPLY_LEAVE);
							} else {
								responseStr = ResponseDesolve.getInstance().desolve(ResponseCommon.Code.FAILE,
										ResponseCommon.Msg.ERROR_NORMAL);
							}

						} else {
							responseStr = ResponseDesolve.getInstance().desolve(ResponseCommon.Code.FAILE,
									ResponseCommon.Msg.FAILE_REPLY_LEAVE);
						}
					} else {
						responseStr = ResponseDesolve.getInstance().desolve(ResponseCommon.Code.FAILE,
								ResponseCommon.Msg.ERROR_NORMAL);
					}
				} catch (Exception e) {
					// TODO: handle exception
					responseStr = ResponseDesolve.getInstance().desolve(ResponseCommon.Code.ERROR_PARAMS,
							ResponseCommon.Msg.ERROR_PARAMS);
					e.printStackTrace();
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
