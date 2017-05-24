package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.TableDepartment;
import db.TablePersonal;
import services.LogTurnDepartmentDaoImp;
import services.PersonalManagerDaoImp;
import utils.DateUtil;
import utils.ResponseCommon;
import utils.ResponseDesolve;
import utils.StringUtil;

/**
 * url: 
 * http://localhost:8080/Xing/PersonalTurnDepartmentServlet?id=123456789&departmentId=20002&reason=fda&time=213
 */
public class PersonalTurnDepartmentServlet extends HttpServlet {

	private PersonalManagerDaoImp personalDao = null;
	private LogTurnDepartmentDaoImp departmentTurnDaoImp = null;

	public PersonalTurnDepartmentServlet() {
		super();
		personalDao = new PersonalManagerDaoImp();
		departmentTurnDaoImp = new LogTurnDepartmentDaoImp();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("----------------------");
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		String uId = request.getParameter("id");
		String dId = request.getParameter("departmentId");
		String reason = request.getParameter("reason");
		String time = request.getParameter("time");
		String responseStr = null;
		if (StringUtil.isNull(dId) || StringUtil.isNull(uId) || StringUtil.isNull(reason)) {
			responseStr = ResponseDesolve.getInstance().desolve(ResponseCommon.Code.ERROR_PARAMS,
					ResponseCommon.Msg.ERROR_PARAMS);
		} else {
			if (StringUtil.isNull(time)) {
				time = DateUtil.getCurrentTime();
			}
			List<TablePersonal> queryById = personalDao.queryById(uId);
			if (queryById == null) {
				responseStr = ResponseDesolve.getInstance().desolve(ResponseCommon.Code.FAILE,
						ResponseCommon.Msg.ERROR_NORMAL);
			} else if (queryById.size() <= 0) {
				responseStr = ResponseDesolve.getInstance().desolve(ResponseCommon.Code.FAILE,
						ResponseCommon.Msg.FAILE_PERSONAL_TURN_DEPARTMENT);
			} else {
				int logP = departmentTurnDaoImp.insert(uId, queryById.get(0).getId_department(), dId, reason, time);
				if (logP >= 0) {

					int position = personalDao.updateDepartmentId(uId, dId);
					if (position >= 0) {
						responseStr = ResponseDesolve.getInstance().desolve(ResponseCommon.Code.SUCCESS,
								ResponseCommon.Msg.SUCCESS_PERSONAL_TURN_DEPARTMENT);
					} else {
						responseStr = ResponseDesolve.getInstance().desolve(ResponseCommon.Code.FAILE,
								ResponseCommon.Msg.ERROR_NORMAL);
					}
				} else {
					responseStr = ResponseDesolve.getInstance().desolve(ResponseCommon.Code.FAILE,
							ResponseCommon.Msg.ERROR_NORMAL);
				}
			}
		}
		ResponseDesolve.getInstance().sendResponse(responseStr, request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
