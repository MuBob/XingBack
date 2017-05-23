package services;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import entitys.SelfInfoBaseDataResponse;
import manager.DataBaseManager;
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
		String sql=String.format("update %s set %s =?, %s=?, %s=?, %s=?, %s=? where %s = %s;",
				DataBaseManager.TablePersonal.TABLE_NAME,
				DataBaseManager.TablePersonal.COLUMN_NAME,
				DataBaseManager.TablePersonal.COLUMN_SEX,
				DataBaseManager.TablePersonal.COLUMN_IDCARD,
				DataBaseManager.TablePersonal.COLUMN_BIRTHDAY,
				DataBaseManager.TablePersonal.COLUMN_EMAIL,
				DataBaseManager.TablePersonal.COLUMN_UID,
				id);
		Log.i("TAG", "sql="+sql+"£¬ params="+params);
		try {
			runner.update(sql, params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
