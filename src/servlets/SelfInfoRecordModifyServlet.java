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

import entitys.SelfInfoRecordDataResponse;
import services.SelfInfoBaseDaoImp;
import services.SelfInfoRecordDaoImp;
import utils.Log;
import utils.ResponseCommon;
import utils.ResponseDesolve;
import utils.StringUtil;

/**
 * url:
 * http://localhost:8080/Xing/SelfInfoRecordModifyServlet?id=123456789&high_record=最高学历
 */
public class SelfInfoRecordModifyServlet extends HttpServlet {

	private SelfInfoRecordDaoImp infoDao = null;
	private List<SelfInfoRecordDataResponse> lists = null;

	public SelfInfoRecordModifyServlet(){
		super();
		infoDao = new SelfInfoRecordDaoImp();
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("----------------------");
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		String uid = request.getParameter("id");
		String highRecord = request.getParameter("high_record");
		String graduateSchool = request.getParameter("graduate_school");
		String responseStr = null;
		lists=infoDao.queryById(uid);
		if (lists==null||lists.size() == 0) {
			responseStr = ResponseDesolve.getInstance().desolve(ResponseCommon.Code.FAILE,
					ResponseCommon.Msg.ERROR_FAILE_LOGIN_NO_USER);
		} else {
			// 账号存在 验证密码
			SelfInfoRecordDataResponse queryBean = lists.get(0);
			String[] params = { StringUtil.isNull(highRecord) ? queryBean.getHighRecord() : highRecord,
					StringUtil.isNull(graduateSchool) ? queryBean.getGraduteSchool() : graduateSchool,
			};
			infoDao.modify(uid, params);
			responseStr = ResponseDesolve.getInstance().desolve(ResponseCommon.Code.SUCCESS,
					ResponseCommon.Msg.SUCCESS_MODIFY);
		}
		// System.out.println(data);
		// CommonUtil.renderJson(response, data);
		ResponseDesolve.getInstance().sendResponse(responseStr, request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
