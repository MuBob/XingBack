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
import entitys.UnReplyPersonalBean;
import entitys.UnReplyPersonalListDataResponse;
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
 * url: http://localhost:8080/Xing/UnReplyTripListGetServlet
 * 
 * @author WangJinXing
 *
 */
public class UnReplyTripListGetServlet extends HttpServlet {

	private PersonalManagerDaoImp personalDao = null;
	private LogTripDaoImp tripDao = null;
	private List<LoginDataResponse> lists = null;

	public UnReplyTripListGetServlet() {
		super();
		personalDao = new PersonalManagerDaoImp();
		tripDao = new LogTripDaoImp();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("-----START-----------------");
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		String responseStr = null;
		List<LogTrip> queryList = tripDao.queryListByReplyType(null, 0);
		if (queryList != null) {
			List<UnReplyPersonalBean> unReplyTripBeans = new ArrayList<UnReplyPersonalBean>();
			for (int i = 0; i < queryList.size(); i++) {
				LogTrip logTrip = queryList.get(i);
				UnReplyPersonalBean unReplyTripBean = new UnReplyPersonalBean(logTrip);
				unReplyTripBean.setName(personalDao.getNameById(logTrip.getId_apply()));
				if (unReplyTripBean.getName() == null) {
					Log.i("UnReplyTripListGetServlet", "当前账号的用户不存在, id=" + unReplyTripBean.getId());
				} else {
					unReplyTripBeans.add(unReplyTripBean);
				}
			}
			UnReplyPersonalListDataResponse data = new UnReplyPersonalListDataResponse();
			data.setList(unReplyTripBeans);
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
