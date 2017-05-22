package services;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import entitys.LoginDataResponse;
import utils.DataSourceManager;

public class SelfInfoBaseDaoImp {
	private QueryRunner runner = new QueryRunner(DataSourceManager.getSource());
	public List<LoginDataResponse> queryById(String id) {
		String sql = "select * from table_personal where id=?";
		// TODO Auto-generated method stub
		List<LoginDataResponse> list = null;
		try {
			list = runner.query(sql,
					new BeanListHandler<LoginDataResponse>(LoginDataResponse.class), id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public List<LoginDataResponse> getAll(String sql, String id) {
		// TODO Auto-generated method stub
		List<LoginDataResponse> list = null;
		try {
			list = runner.query(sql,
					new BeanListHandler<LoginDataResponse>(LoginDataResponse.class), id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

}
