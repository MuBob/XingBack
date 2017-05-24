package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.TablePersonal;
import entitys.PersonalListDataResponse;
import services.PersonalManagerDaoImp;
import utils.ResponseCommon;
import utils.ResponseDesolve;
import utils.StringUtil;

/**
 * url: http://localhost:8080/Xing/PersonalListGetServlet?departmentId=10001
 */
public class PersonalListGetServlet extends HttpServlet {

	private PersonalManagerDaoImp personalDao = null;

	public PersonalListGetServlet() {
		super();
		personalDao = new PersonalManagerDaoImp();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("----------------------");
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		String dId = request.getParameter("departmentId");
		String responseStr = null;
		List<TablePersonal> queryAll = null;
		if (StringUtil.isNull(dId)) {
			queryAll = personalDao.queryAll();
		} else {
			queryAll = personalDao.queryByDepartmentId(dId);
		}
		if (queryAll == null) {
			responseStr = ResponseDesolve.getInstance().desolve(ResponseCommon.Code.FAILE,
					ResponseCommon.Msg.ERROR_NORMAL);
		} else {
			PersonalListDataResponse personalListDataResponse = new PersonalListDataResponse();
			personalListDataResponse.setList(queryAll);
			responseStr = ResponseDesolve.getInstance().desolve(personalListDataResponse,
					ResponseCommon.Msg.ERROR_NORMAL);
		}
		ResponseDesolve.getInstance().sendResponse(responseStr, request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
