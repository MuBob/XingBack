package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.TableDepartment;
import db.TablePersonal;
import services.PersonalManagerDaoImp;
import utils.ResponseCommon;
import utils.ResponseDesolve;
import utils.StringUtil;

/**
 * url: http://localhost:8080/Xing/PersonalDelServlet?id=123456780
 */
public class PersonalDelServlet extends HttpServlet {

	private PersonalManagerDaoImp personalDao = null;
	
	
	public PersonalDelServlet() {
		super();
		personalDao = new PersonalManagerDaoImp();
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
			List<TablePersonal> queryById = personalDao.queryById(dId);
			if (queryById==null) {
				responseStr=ResponseDesolve.getInstance().desolve(ResponseCommon.Code.FAILE, ResponseCommon.Msg.ERROR_NORMAL);
			}else if (queryById.size()<=0) {
				responseStr=ResponseDesolve.getInstance().desolve(ResponseCommon.Code.FAILE, ResponseCommon.Msg.FAILE_PERSONAL_DEL);
			}else {
				int position=personalDao.delete(dId);
				if (position<0) {
					responseStr=ResponseDesolve.getInstance().desolve(ResponseCommon.Code.FAILE, ResponseCommon.Msg.ERROR_NORMAL);
				}else {
					responseStr=ResponseDesolve.getInstance().desolve(ResponseCommon.Code.SUCCESS, ResponseCommon.Msg.SUCCESS_PERSONAL_DEL);
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
