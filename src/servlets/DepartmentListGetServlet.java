package servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.TableDepartment;
import entitys.DepartmentListDataResponse;
import entitys.SelfInfoBaseDataResponse;
import services.DepartmentManagerDaoImp;
import services.SelfInfoBaseDaoImp;
import utils.ResponseCommon;
import utils.ResponseDesolve;

/**
 * url: http://localhost:8080/Xing/DepartmentListGetServlet
 */
public class DepartmentListGetServlet extends HttpServlet {

	private DepartmentManagerDaoImp departmentDao = null;
	
	
	public DepartmentListGetServlet() {
		super();
		departmentDao = new DepartmentManagerDaoImp();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("----------------------");
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		String responseStr=null;
		List<TableDepartment> queryAll = departmentDao.queryAll();
		if (queryAll==null) {
			responseStr=ResponseDesolve.getInstance().desolve(ResponseCommon.Code.FAILE, ResponseCommon.Msg.ERROR_NORMAL);
		}else {
			DepartmentListDataResponse departmentListDataResponse = new DepartmentListDataResponse();
			departmentListDataResponse.setList(queryAll);
			responseStr=ResponseDesolve.getInstance().desolve(departmentListDataResponse, ResponseCommon.Msg.ERROR_NORMAL);
		}
		ResponseDesolve.getInstance().sendResponse(responseStr, request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
