package services;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import db.TablePersonal;
import entitys.LoginDataResponse;
import utils.DataSourceManager;
import utils.Log;

public class LoginDaoImp {
	private QueryRunner runner;

	public LoginDaoImp() {
		runner = new QueryRunner(DataSourceManager.getSource());
	}

	public List<LoginDataResponse> queryById(String uid) {
		String sql = String.format("select * from %s where %s=?", TablePersonal.TABLE_NAME, TablePersonal.COLUMN_UID);
		Log.d("LoginDaoImp.queryById", "sql="+sql);
		List<LoginDataResponse> list = null;
		try {
			list = runner.query(sql, new BeanListHandler<LoginDataResponse>(LoginDataResponse.class), uid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
}
