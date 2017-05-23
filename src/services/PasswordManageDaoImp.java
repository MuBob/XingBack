package services;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import db.TablePersonal;
import entitys.LoginDataResponse;
import utils.DataSourceManager;

public class PasswordManageDaoImp {
	private QueryRunner runner;

	public PasswordManageDaoImp() {
		runner = new QueryRunner(DataSourceManager.getSource());
	}

	public List<LoginDataResponse> queryById(String id) {
		String sql = "select * from table_personal where id=?";
		// TODO Auto-generated method stub
		List<LoginDataResponse> list = null;
		try {
			list = runner.query(sql, new BeanListHandler<LoginDataResponse>(LoginDataResponse.class), id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public boolean modify(String id, String pwd) {
		String sql = String.format("update %s set %s=? where %s=?",
				TablePersonal.TABLE_NAME,
				TablePersonal.COLUMN_PASSWORD,
				TablePersonal.COLUMN_UID);
		 try {
			runner.update(sql, pwd, id);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
