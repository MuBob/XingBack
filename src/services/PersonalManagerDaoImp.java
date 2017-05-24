package services;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import db.TablePersonal;
import entitys.SelfInfoBaseDataResponse;
import utils.DataSourceManager;
import utils.Log;

public class PersonalManagerDaoImp {
	private QueryRunner runner;

	public PersonalManagerDaoImp() {
		runner = new QueryRunner(DataSourceManager.getSource());
	}
	public List<TablePersonal> queryAll() {
		String sql=String.format("select * from %s", TablePersonal.TABLE_NAME);
		Log.i("TAG", "sql="+sql);
		List<TablePersonal> list = null;
		try {
			list = runner.query(sql,
					new BeanListHandler<TablePersonal>(TablePersonal.class));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public List<TablePersonal> queryById(String uId) {
		String sql=String.format("select * from %s where %s=?", TablePersonal.TABLE_NAME, TablePersonal.COLUMN_UID);
		Log.i("TAG", "sql="+sql);
		List<TablePersonal> list = null;
		try {
			list = runner.query(sql,
					new BeanListHandler<TablePersonal>(TablePersonal.class),uId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public String getNameById(String uId) {
		List<TablePersonal> queryById = queryById(uId);
		if (queryById!=null&&queryById.size()>0) {
			return queryById.get(0).getName();
		}
		return null;
	}
	public List<TablePersonal> queryByDepartmentId(String dId) {
		String sql=String.format("select * from %s where %s=?", TablePersonal.TABLE_NAME, TablePersonal.COLUMN_IDDEPARTMENT);
		Log.i("TAG", "sql="+sql);
		List<TablePersonal> list = null;
		try {
			list = runner.query(sql,
					new BeanListHandler<TablePersonal>(TablePersonal.class),dId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public int insert(String uId, String name, String pwd, String sex, String idCard, String email, String birthday, String highRecord, String title) {
		String sql=String.format("replace into %s"
				+ "(%s, %s, %s,"
				+ "%s,%s,%s,"
				+ "%s,%s,%s)"
				+ " values(?,?,?,"
				+ "?,?,?,"
				+ "?,?,?);", 
				TablePersonal.TABLE_NAME,
				TablePersonal.COLUMN_UID, TablePersonal.COLUMN_NAME, TablePersonal.COLUMN_PASSWORD,
				TablePersonal.COLUMN_SEX, TablePersonal.COLUMN_IDCARD, TablePersonal.COLUMN_EMAIL,
				TablePersonal.COLUMN_BIRTHDAY,TablePersonal.COLUMN_HIGHRECORD,TablePersonal.COLUMN_TITLE);
		Log.i("DepartmentManagerDaoImp.insert", "sql="+sql);
		try {
			return runner.update(sql,uId, name, pwd,sex,idCard,email,birthday,highRecord,title);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return-1;
	}
	public int delete(String dId) {
		String sql=String.format("delete from %s where %s=?", TablePersonal.TABLE_NAME, TablePersonal.COLUMN_UID);
		try {
			return runner.update(sql,dId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return-1;
	}

	public int updateDepartmentId(String uid, String dId) {
		String sql=String.format("update %s set %s=? where %s=?", TablePersonal.TABLE_NAME, TablePersonal.COLUMN_IDDEPARTMENT, TablePersonal.COLUMN_UID);
		try {
			return runner.update(sql,dId,uid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return-1;
	}
}
