package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.LogLeave;
import db.LogTrip;
import entitys.LoginDataResponse;
import entitys.UnReplyPersonalBean;
import entitys.UnReplyPersonalListDataResponse;
import services.LogLeaveDaoImp;
import services.PersonalManagerDaoImp;
import utils.Log;
import utils.ResponseCommon;
import utils.ResponseDesolve;

/**
 * url: http://localhost:8080/Xing/UnReplyLeaveListGetServlet
 * 
 * @author WangJinXing
 *
 */
public class UnReplyLeaveListGetServlet extends HttpServlet {

	private PersonalManagerDaoImp personalDao = null;
	private LogLeaveDaoImp leaveDap = null;
	private List<LoginDataResponse> lists = null;

	public UnReplyLeaveListGetServlet() {
		super();
		personalDao = new PersonalManagerDaoImp();
		leaveDap = new LogLeaveDaoImp();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("-----START-----------------");
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		String responseStr = null;
		List<LogLeave> queryList = leaveDap.queryListByApplyId(null, 0);
		if (queryList != null) {
			List<UnReplyPersonalBean> unReplyBeans = new ArrayList<UnReplyPersonalBean>();
			for (int i = 0; i < queryList.size(); i++) {
				LogLeave logLeave = queryList.get(i);
				UnReplyPersonalBean unReplyBean = new UnReplyPersonalBean(logLeave);
				unReplyBean.setName(personalDao.getNameById(logLeave.getId_apply()));
				if (unReplyBean.getName() == null) {
					Log.i("UnReplyTripListGetServlet", "当前账号的用户不存在, id=" + unReplyBean.getId());
				} else {
					unReplyBeans.add(unReplyBean);
				}
			}
			UnReplyPersonalListDataResponse data = new UnReplyPersonalListDataResponse();
			data.setList(unReplyBeans);
			responseStr = ResponseDesolve.getInstance().desolve(data, ResponseCommon.Msg.ERROR_NORMAL);
		} else {
			responseStr = ResponseDesolve.getInstance().desolve(ResponseCommon.Code.FAILE,
					ResponseCommon.Msg.ERROR_NORMAL);
		}

		ResponseDesolve.getInstance().sendResponse(responseStr, request, response);
		System.out.println("-------END---------------");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
