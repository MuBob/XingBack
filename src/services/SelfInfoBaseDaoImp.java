package services;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import db.TablePersonal;
import entitys.SelfInfoBaseDataResponse;
import utils.DataSourceManager;
import utils.Log;

public class SelfInfoBaseDaoImp {
	private QueryRunner runner;

	public SelfInfoBaseDaoImp() {
		runner = new QueryRunner(DataSourceManager.getSource());
	}
	public List<SelfInfoBaseDataResponse> queryById(String id) {
		String sql="select * from table_personal where id=?";
		Log.i("TAG", "sql="+sql);
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
		String sql=String.format("update %s set %s =?, %s=?, %s=?, %s=?, %s=? where %s = '%s';",
				TablePersonal.TABLE_NAME,
				TablePersonal.COLUMN_NAME,
				TablePersonal.COLUMN_SEX,
				TablePersonal.COLUMN_IDCARD,
				TablePersonal.COLUMN_BIRTHDAY,
				TablePersonal.COLUMN_EMAIL,
				TablePersonal.COLUMN_UID, id);
		Log.i("TAG", "sql="+sql+"£¬ params="+params);
		try {
			runner.update(sql, params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
