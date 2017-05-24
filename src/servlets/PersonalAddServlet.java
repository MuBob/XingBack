package servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.TablePersonal;
import entitys.PersonalListDataResponse;
import entitys.SelfInfoBaseDataResponse;
import services.PersonalManagerDaoImp;
import services.SelfInfoBaseDaoImp;
import utils.ResponseCommon;
import utils.ResponseDesolve;
import utils.StringUtil;

/**
 * url: 
 * http://localhost:8080/Xing/PersonalAddServlet?id=123456788&name=xiao&pwd=123456&sex=男&idCard=1224357941&email=1224357941@qq.com&birthday=12月4日&highRecord=说是&title=职工
 */
public class PersonalAddServlet extends HttpServlet {

	private PersonalManagerDaoImp personalDao = null;
	
	
	public PersonalAddServlet() {
		super();
		personalDao = new PersonalManagerDaoImp();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("----------------------");
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		String dId=request.getParameter("id");
		String dName=request.getParameter("name");
		String pwd=request.getParameter("pwd");
		String sex=request.getParameter("sex");
		String idCard=request.getParameter("idCard");
		String email=request.getParameter("email");
		String birthday=request.getParameter("birthday");
		String highRecord=request.getParameter("highRecord");
		String title=request.getParameter("title");
		String responseStr=null;
		if (StringUtil.isNull(dId)||StringUtil.isNull(dName)||StringUtil.isNull(pwd)||StringUtil.isNull(sex)||StringUtil.isNull(idCard)
				||StringUtil.isNull(email)||StringUtil.isNull(birthday)||StringUtil.isNull(highRecord)||StringUtil.isNull(title)) {
			responseStr=ResponseDesolve.getInstance().desolve(ResponseCommon.Code.ERROR_PARAMS, ResponseCommon.Msg.ERROR_PARAMS);
		}else {
			List<TablePersonal> queryById = personalDao.queryById(dId);
			if (queryById==null||queryById.size()<=0) {
				int position=personalDao.insert(dId,dName, pwd, sex, idCard, email, birthday, highRecord, title);
				if (position<0) {
					responseStr=ResponseDesolve.getInstance().desolve(ResponseCommon.Code.FAILE, ResponseCommon.Msg.ERROR_NORMAL);
				}else {
					responseStr=ResponseDesolve.getInstance().desolve(ResponseCommon.Code.SUCCESS, ResponseCommon.Msg.SUCCESS_PERSONAL_ADD);
				}	
			}else {
				responseStr=ResponseDesolve.getInstance().desolve(ResponseCommon.Code.FAILE, ResponseCommon.Msg.FAILE_PERSONAL_ADD);
			}
		}
		ResponseDesolve.getInstance().sendResponse(responseStr, request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
