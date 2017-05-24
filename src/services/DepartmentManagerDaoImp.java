package services;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import db.TableDepartment;
import db.TablePersonal;
import entitys.SelfInfoBaseDataResponse;
import utils.DataSourceManager;
import utils.Log;

public class DepartmentManagerDaoImp {
	private QueryRunner runner;

	public DepartmentManagerDaoImp() {
		runner = new QueryRunner(DataSourceManager.getSource());
	}
	public List<TableDepartment> queryAll() {
		String sql=String.format("select * from %s", TableDepartment.TABLE_NAME);
		Log.i("TAG", "sql="+sql);
		List<TableDepartment> list = null;
		try {
			list = runner.query(sql,
					new BeanListHandler<TableDepartment>(TableDepartment.class));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public List<TableDepartment> queryById(String dId) {
		String sql=String.format("select * from %s where %s=?", TableDepartment.TABLE_NAME, TableDepartment.COLUMN_UID);
		Log.i("TAG", "sql="+sql);
		List<TableDepartment> list = null;
		try {
			list = runner.query(sql,
					new BeanListHandler<TableDepartment>(TableDepartment.class),dId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public int insert(String dId, String dName, String desc) {
		String sql=String.format("replace into %s(%s, %s, %s) values(?,?,?);", TableDepartment.TABLE_NAME, TableDepartment.COLUMN_UID, TableDepartment.COLUMN_NAME, TableDepartment.COLUMN_DESC);
		Log.i("DepartmentManagerDaoImp.insert", "sql="+sql);
		try {
			return runner.update(sql,dId,dName, desc);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return-1;
	}
	public int delete(String dId) {
		String sql=String.format("delete from %s where %s=?", TableDepartment.TABLE_NAME, TableDepartment.COLUMN_UID);
		try {
			return runner.update(sql,dId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return-1;
	}

}
