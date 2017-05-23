package services;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import db.TablePersonal;
import entitys.SelfInfoRecordDataResponse;
import utils.DataSourceManager;
import utils.Log;

public class SelfInfoRecordDaoImp {
	private QueryRunner runner;
	
	public SelfInfoRecordDaoImp(){
		runner = new QueryRunner(DataSourceManager.getSource());
	}
	public List<SelfInfoRecordDataResponse> queryById(String id) {
		String sql="select * from table_personal where id=?";
		Log.i("SelfInfoRecordDaoImp", "sql="+sql);
		List<SelfInfoRecordDataResponse> list = null;
		try {
			list = runner.query(sql,
					new BeanListHandler<SelfInfoRecordDataResponse>(SelfInfoRecordDataResponse.class), id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public void modify(String id, Object[] params){
		String sql=String.format("update %s set %s =?, %s=? where %s = '%s';",
				TablePersonal.TABLE_NAME,
				TablePersonal.COLUMN_HIGHRECORD,
				TablePersonal.COLUMN_GRADUTESCHOOL,
				TablePersonal.COLUMN_UID, id);
		Log.i("SelfInfoRecordDaoImp", "sql="+sql+"£¬ params="+params);
		try {
			runner.update(sql, params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
