package services;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import entitys.SelfInfoBaseDataResponse;
import utils.DataSourceManager;

public class SelfInfoBaseDaoImp {
	private QueryRunner runner = new QueryRunner(DataSourceManager.getSource());
	public List<SelfInfoBaseDataResponse> queryById(String id) {
		String sql = "select * from table_personal where id=?";
		// TODO Auto-generated method stub
		List<SelfInfoBaseDataResponse> list = null;
		try {
			list = runner.query(sql,
					new BeanListHandler<SelfInfoBaseDataResponse>(SelfInfoBaseDataResponse.class), id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public void modify(String id, Object[] params){
		String sql=String.format("update table_personal set name = ?£¬sex=?, idCard=?, birthday=?  where id = %s;", id);
		try {
			runner.update(sql, params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
