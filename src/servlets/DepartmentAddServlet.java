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
 * url: http://localhost:8080/Xing/DepartmentAddServlet?id=10002&name=技术部门&desc=技术人员所在部门
 */
public class DepartmentAddServlet extends HttpServlet {

	private DepartmentManagerDaoImp departmentDao = null;
	
	
	public DepartmentAddServlet() {
		super();
		departmentDao = new DepartmentManagerDaoImp();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("----------------------");
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		String dId=request.getParameter("id");
		String dName=request.getParameter("name");
		String desc=request.getParameter("desc");
		String responseStr=null;
		if (StringUtil.isNull(dId)||StringUtil.isNull(dName)) {
			responseStr=ResponseDesolve.getInstance().desolve(ResponseCommon.Code.ERROR_PARAMS, ResponseCommon.Msg.ERROR_PARAMS);
		}else {
			List<TableDepartment> queryById = departmentDao.queryById(dId);
			if (queryById==null||queryById.size()<=0) {
				if (StringUtil.isNull(desc)) {
					desc="";
				}
				int position=departmentDao.insert(dId, dName, desc);
				if (position<0) {
					responseStr=ResponseDesolve.getInstance().desolve(ResponseCommon.Code.FAILE, ResponseCommon.Msg.ERROR_NORMAL);
				}else {
					responseStr=ResponseDesolve.getInstance().desolve(ResponseCommon.Code.SUCCESS, ResponseCommon.Msg.SUCCESS_DEPARTMENT_ADD);
				}	
			}else {
				responseStr=ResponseDesolve.getInstance().desolve(ResponseCommon.Code.FAILE, ResponseCommon.Msg.FAILE_DEPARTMENT_ADD);
			}
		}
		ResponseDesolve.getInstance().sendResponse(responseStr, request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
