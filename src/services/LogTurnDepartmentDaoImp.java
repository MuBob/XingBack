package services;

import java.sql.SQLException;
import java.util.List;

import javax.swing.text.AbstractDocument.LeafElement;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import db.LogLeave;
import db.LogSignOut;
import db.LogTurnDepartment;
import db.TableDepartment;
import entitys.LoginDataResponse;
import utils.DataSourceManager;
import utils.Log;
import utils.StringUtil;

public class LogTurnDepartmentDaoImp {
	private QueryRunner runner;

	public LogTurnDepartmentDaoImp() {
		runner = new QueryRunner(DataSourceManager.getSource());
	}

	public List<LogTurnDepartment> queryAll() {
		String sql = String.format("select * from %s",
				LogTurnDepartment.TABLE_NAME);
		// TODO Auto-generated method stub
		List<LogTurnDepartment> list = null;
		try {
			list = runner.query(sql, new BeanListHandler<LogTurnDepartment>(LogTurnDepartment.class));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public List<LogTurnDepartment> queryByUId(String uId) {
		String sql = String.format("select * from %s where %s=?",
				LogTurnDepartment.TABLE_NAME, LogTurnDepartment.COLUMN_UID);
		// TODO Auto-generated method stub
		List<LogTurnDepartment> list = null;
		try {
			list = runner.query(sql, new BeanListHandler<LogTurnDepartment>(LogTurnDepartment.class), uId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public List<LogTurnDepartment> queryByDIdFrom(String dFromId) {
		String sql = String.format("select * from %s where %s=?",
				LogTurnDepartment.TABLE_NAME, LogTurnDepartment.COLUMN_DID_FROM);
		// TODO Auto-generated method stub
		List<LogTurnDepartment> list = null;
		try {
			list = runner.query(sql, new BeanListHandler<LogTurnDepartment>(LogTurnDepartment.class), dFromId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public List<LogTurnDepartment> queryByDIdTo(String dToId) {
		String sql = String.format("select * from %s where %s=?",
				LogTurnDepartment.TABLE_NAME, LogTurnDepartment.COLUMN_DID_TO);
		// TODO Auto-generated method stub
		List<LogTurnDepartment> list = null;
		try {
			list = runner.query(sql, new BeanListHandler<LogTurnDepartment>(LogTurnDepartment.class), dToId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public int insert(String uid, String fromDId, String toDId, String reason, String time) {
		String sql = String.format("insert into %s(%s, %s, %s, %s, %s) values(?,?,?,?,?)", LogTurnDepartment.TABLE_NAME,
				LogTurnDepartment.COLUMN_UID, LogTurnDepartment.COLUMN_DID_FROM, LogTurnDepartment.COLUMN_DID_TO,
				LogTurnDepartment.COLUMN_REASON, LogTurnDepartment.COLUMN_TIME);
		try {
			return runner.update(sql, uid, fromDId, toDId, reason, time);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	
	public int delete(String id) {
		String sql=String.format("delete from %s where %s=?", LogTurnDepartment.TABLE_NAME, LogTurnDepartment.COLUMN_ID);
		try {
			return runner.update(sql,id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return-1;
	}

}
