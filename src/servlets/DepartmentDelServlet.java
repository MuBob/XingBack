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
import utils.StringUtil;

/**
 * url: http://localhost:8080/Xing/DepartmentDelServlet?id=10002
 */
public class DepartmentDelServlet extends HttpServlet {

	private DepartmentManagerDaoImp departmentDao = null;
	
	
	public DepartmentDelServlet() {
		super();
		departmentDao = new DepartmentManagerDaoImp();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("----------------------");
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		String dId=request.getParameter("id");
		String responseStr=null;
		if (StringUtil.isNull(dId)) {
			responseStr=ResponseDesolve.getInstance().desolve(ResponseCommon.Code.ERROR_PARAMS, ResponseCommon.Msg.ERROR_PARAMS);
		}else {
			List<TableDepartment> queryById = departmentDao.queryById(dId);
			if (queryById==null) {
				responseStr=ResponseDesolve.getInstance().desolve(ResponseCommon.Code.FAILE, ResponseCommon.Msg.ERROR_NORMAL);
			}else if (queryById.size()<=0) {
				responseStr=ResponseDesolve.getInstance().desolve(ResponseCommon.Code.FAILE, ResponseCommon.Msg.FAILE_DEPARTMENT_DEL);
			}else {
				int position=departmentDao.delete(dId);
				if (position<0) {
					responseStr=ResponseDesolve.getInstance().desolve(ResponseCommon.Code.FAILE, ResponseCommon.Msg.ERROR_NORMAL);
				}else {
					responseStr=ResponseDesolve.getInstance().desolve(ResponseCommon.Code.SUCCESS, ResponseCommon.Msg.SUCCESS_DEPARTMENT_DEL);
				}	
			}
		}
		ResponseDesolve.getInstance().sendResponse(responseStr, request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
