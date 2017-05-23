package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.TableSkill;
import entitys.SelfInfoRecordDataResponse;
import entitys.SelfInfoSkillDataResponse;
import services.SelfInfoRecordDaoImp;
import services.SelfInfoSkillDaoImp;
import utils.GsonUtil;
import utils.ResponseCommon;
import utils.ResponseDesolve;
import utils.StringUtil;

/**
 * url:
 * http://localhost:8080/Xing/SelfInfoSkillModifyServlet?id=123456789&accounting
 * =2&computer=1
 */
public class SelfInfoSkillModifyServlet extends HttpServlet {

	private SelfInfoSkillDaoImp infoDao = null;
	private List<SelfInfoSkillDataResponse> lists = null;

	public SelfInfoSkillModifyServlet() {
		super();
		infoDao = new SelfInfoSkillDaoImp();
		lists = new ArrayList();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("----------------------");
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		String uid = request.getParameter("id");
		String accounting = request.getParameter("accounting");
		String computer = request.getParameter("computer");
		String civil = request.getParameter("civil");
		String safety = request.getParameter("safety");
		String responseStr = null;
		SelfInfoSkillDataResponse querySkillById = infoDao.queryById(uid);
		if (querySkillById == null) {
			responseStr = ResponseDesolve.getInstance().desolve(ResponseCommon.Code.FAILE,
					ResponseCommon.Msg.ERROR_FAILE_LOGIN_NO_USER);
		} else {
			Map<String, String> querySkillMap = querySkillById.getMap();
			Set<String> keySet = querySkillMap.keySet();
			Iterator<String> iterator = keySet.iterator();
			Map<String, String> modifyMap = new HashMap<String, String>();
			while (iterator.hasNext()) {
				String nextId = iterator.next();
				// 先获取当前更新中有该条id的更新
				String nextLevel = getLevelById(nextId, accounting, computer, civil, safety);
				// 在当前没有更新的前提下，判断数据库之前是否有这条数据，有就更新获取之前的旧数据
				if (StringUtil.isNull(nextLevel)) {
					nextLevel = querySkillMap.get(nextId);
				}
				modifyMap.put(nextId, nextLevel);
			}
			List<TableSkill> allSkillBeans = infoDao.queryAllSkillBeans();
			for (int i = 0; i < allSkillBeans.size(); i++) {
				TableSkill skillBean = allSkillBeans.get(i);
				String nextLevel = getLevelById(skillBean.get_id(), accounting, computer, civil, safety);
				if (!StringUtil.isNull(nextLevel)) {
					modifyMap.put(skillBean.get_id(), nextLevel);
				}
			}

			String skillLevelMapStr = GsonUtil.toJson(modifyMap);

			infoDao.modify(uid, skillLevelMapStr);
			responseStr = ResponseDesolve.getInstance().desolve(ResponseCommon.Code.SUCCESS,
					ResponseCommon.Msg.SUCCESS_MODIFY);
		}
		// System.out.println(data);
		// CommonUtil.renderJson(response, data);
		ResponseDesolve.getInstance().sendResponse(responseStr, request, response);

	}

	private String getLevelById(String nextId, String accounting, String computer, String civil, String safety) {
		String nextLevel = null;
		if ("1".equals(nextId)) {
			nextLevel = accounting;
		} else if ("2".equals(nextId)) {
			nextLevel = computer;
		} else if ("3".equals(nextId)) {
			nextLevel = civil;
		} else if ("4".equals(nextId)) {
			nextLevel = safety;
		}
		return nextLevel;
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
