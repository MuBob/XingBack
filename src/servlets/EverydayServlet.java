package servlets;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import services.SignDaoImp;
import utils.CommonUtil;

import entitys.CurrentLoginer;
import entitys.SignInfo;

/**
 * 为 日常 模块提供服务
 * url: http://localhost:8080/Xing/EverydayServlet?id=(0,)
 * @author WangJinming
 *
 */
public class EverydayServlet extends HttpServlet {

	private boolean flag;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			switch(id){
			//点击签到时  保存数据
			case 0:
				if(!flag){
					String account = CurrentLoginer.account;
					String name = CurrentLoginer.name;
					Calendar calendar = Calendar.getInstance();
					int year =calendar.get(calendar.YEAR);
					int mouth =calendar.get(calendar.MONTH)+1;
					System.out.println(year+"--"+mouth);
					//取到当前账户 year和mouth时的情况
					SignDaoImp sdImp = new SignDaoImp();
					String sql = "select * from sign where Account=? and Year=? and Mouth=?";
					List<SignInfo> lists = sdImp.getAll(sql, "201310644",year,mouth);
					if(lists.size()==0){
						System.out.println("该账号尚未载入，请联系管理员");
					}else{
						for(SignInfo signInfo : lists){
							signInfo.setSign(signInfo.getSign()+1);
							String sql_save = "update sign set Sign = ?";
							int result = sdImp.Savedata(sql_save, signInfo.getSign());
							if(result>0){
								System.out.println("更新成功");
								data.put("result", "签到成功");
								//把当前的信息存储在CurrentLoginer里面  需要的时候取出来
								CurrentLoginer.count_sign = signInfo.getSign();
								CurrentLoginer.count_leave = signInfo.getLeave();
								CurrentLoginer.count_evection = signInfo.getEvection();
								data.put("sign", CurrentLoginer.count_sign);
								data.put("leave", CurrentLoginer.count_leave);
								data.put("evection", CurrentLoginer.count_evection);
								//防止多次签到
//								flag = !flag;
							}else{
								System.out.println("更新失败");
								data.put("result", "签到失败！");
							}
						}
					}
				}else{
					data.put("result", "已签到");
				}
				break;
			case 1:
				break;
			}
		} catch (Exception e) {
			System.out.println("出现异常");
		}
		CommonUtil.renderJson(response, data);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
