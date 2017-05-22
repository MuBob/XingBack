package servlets;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entitys.Mine_info;

import services.Service_mineinfo;
import utils.CommonUtil;

/**
 * 个人信息界面 数据的请求和提交 url： baseurl+infoself
 * 
 * @author WangJinming
 * 
 */
public class Servlet_infoself extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		int id = Integer.parseInt(request.getParameter("id"));
		Service_mineinfo service_mineinfo = new Service_mineinfo();
		switch (id) {
		case 0:
			//请求数据
			List<Mine_info> mList = service_mineinfo.getAll();
			System.out.println(mList.toString());
			CommonUtil.renderJson(response, mList);
			break;
		case 1:
			//提交数据
			Map<String,String> map = request.getParameterMap();
			System.out.println("=======");
			//【L 应该是个Stirng 对象 导致转换异常
			/*for(Object key : map.keySet()){
				System.out.println(key+"---"+map.get(key));
			}*/
			break;
		default:
			break;
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
