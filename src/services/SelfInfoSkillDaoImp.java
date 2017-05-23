package services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import db.TablePersonal;
import db.TableSkill;
import entitys.SelfInfoSkillDataResponse;
import utils.DataSourceManager;
import utils.GsonUtil;
import utils.Log;

public class SelfInfoSkillDaoImp {
	private QueryRunner runner;

	public SelfInfoSkillDaoImp() {
		runner = new QueryRunner(DataSourceManager.getSource());
	}

	public List<TableSkill> queryAllSkillBeans() {
		String sql = String.format("select * from %s;", TableSkill.TABLE_NAME);
		try {
			List<TableSkill> list = runner.query(sql, new BeanListHandler<TableSkill>(TableSkill.class));
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public List<TableSkill> querySkillBeanById(String skill_id) {
		String sql = String.format("select * from %s where %s=?;", TableSkill.TABLE_NAME, TableSkill.COLUMN_ID);
		try {
			List<TableSkill> list = runner.query(sql, new BeanListHandler<TableSkill>(TableSkill.class), skill_id);
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public SelfInfoSkillDataResponse queryById(String id) {
		String sql = String.format("select %s from %s where %s=?", TablePersonal.COLUMN_SKILLS_LEVEL,
				TablePersonal.TABLE_NAME, TablePersonal.COLUMN_UID);
		Log.i("SelfInfoRecordDaoImp", "sql=" + sql);
		try {
			List<TablePersonal> list = runner.query(sql, new BeanListHandler<TablePersonal>(TablePersonal.class), id);
			if (list != null && list.size() > 0) {
				String skillMapStr=list.get(0).getSkill_level();
				Map<String, String> skillsMap=(Map<String, String>) GsonUtil.fromJsonToObject(skillMapStr, Map.class);
				if (skillsMap==null) {
					skillsMap=new HashMap<String, String>();
				}
				return new SelfInfoSkillDataResponse(skillsMap);
			} else {
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("SelfInfoSkillDaoImp.queryById", "Êý¾Ý¿â²éÑ¯Ê§°Ü£¬e="+e.getMessage());
		}
		return null;
	}

	public void modify(String id, String skillLevelStr) {
		String sql = String.format("update %s set %s=? where %s = ?;", TablePersonal.TABLE_NAME,
				TablePersonal.COLUMN_SKILLS_LEVEL, TablePersonal.COLUMN_UID);
		Log.i("SelfInfoRecordDaoImp", "sql=" + sql + "£¬ params=" + skillLevelStr);
		try {
			runner.update(sql, skillLevelStr, id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
