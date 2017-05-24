package services;

import java.sql.SQLException;
import java.util.List;

import javax.swing.text.AbstractDocument.LeafElement;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import db.LogLeave;
import db.LogSignOut;
import db.LogTrip;
import entitys.LoginDataResponse;
import utils.DataSourceManager;
import utils.Log;
import utils.StringUtil;

public class LogLeaveDaoImp {
	private QueryRunner runner;

	public LogLeaveDaoImp() {
		runner = new QueryRunner(DataSourceManager.getSource());
	}

	public List<LogLeave> queryAll(String id) {
		String sql = String.format("select * from %s",
				LogLeave.TABLE_NAME);
		// TODO Auto-generated method stub
		List<LogLeave> list = null;
		try {
			list = runner.query(sql, new BeanListHandler<LogLeave>(LogLeave.class));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public List<LogLeave> queryByApplyId(String applyId) {
		String sql = String.format("select * from %s where %s=?",
				LogLeave.TABLE_NAME, LogLeave.COLUMN_UID_APPLY);
		// TODO Auto-generated method stub
		List<LogLeave> list = null;
		try {
			list = runner.query(sql, new BeanListHandler<LogLeave>(LogLeave.class), applyId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public List<LogLeave> queryByApplyId(String applyId, String dayStart, String dayEnd) {
		String sql = String.format("select * from %s where %s=? and %s=? and %s=?;",
				LogLeave.TABLE_NAME, LogLeave.COLUMN_UID_APPLY, LogLeave.COLUMN_DAY_START, LogLeave.COLUMN_DAY_END);
		// TODO Auto-generated method stub
		List<LogLeave> list = null;
		try {
			list = runner.query(sql, new BeanListHandler<LogLeave>(LogLeave.class), applyId, dayStart, dayEnd);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public List<LogLeave> queryByReplyId(String replyId) {
		String sql = String.format("select * from %s where %s=?",
				LogLeave.TABLE_NAME, LogLeave.COLUMN_UID_REPLY);
		// TODO Auto-generated method stub
		List<LogLeave> list = null;
		try {
			list = runner.query(sql, new BeanListHandler<LogLeave>(LogLeave.class), replyId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public List<LogLeave> queryById(String id) {
		String sql = String.format("select * from %s where %s=?",
				LogLeave.TABLE_NAME, LogLeave.COLUMN_ID);
		// TODO Auto-generated method stub
		List<LogLeave> list = null;
		try {
			list = runner.query(sql, new BeanListHandler<LogLeave>(LogLeave.class), id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 未回复的列表
	 * @return
	 */
	public List<LogLeave> queryListByApplyId(String applyId, int replyType) {
		String sqlAll = String.format("select * from %s where %s=?;",
				LogLeave.TABLE_NAME, LogLeave.COLUMN_REPLY_TYPE);
		String sqlId = String.format("select * from %s where %s=? and %s=?;",
				LogLeave.TABLE_NAME, LogLeave.COLUMN_UID_APPLY, LogLeave.COLUMN_REPLY_TYPE);
		// TODO Auto-generated method stub
		List<LogLeave> list = null;
		try {
			if (StringUtil.isNull(applyId)) {
				list = runner.query(sqlAll, new BeanListHandler<LogLeave>(LogLeave.class), replyType);
			}else {
			list = runner.query(sqlId, new BeanListHandler<LogLeave>(LogLeave.class),applyId, replyType);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	
	public boolean updateApplyTimeById(String id, String time, String reason) {
		String sql = String.format("update %s set %s=?, %s=? where %s=?", LogLeave.TABLE_NAME,
				LogLeave.COLUMN_TIME_APPLY, LogLeave.COLUMN_REASON, LogLeave.COLUMN_ID);
		Log.i("SignManageDaoImp.updateInLog", "sql="+sql);
		try {
			runner.update(sql, time, reason, id);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}


	public int updateLogById(String id, int reply, String time, String reason) {
		String sql = String.format("update %s set %s=?, %s=?,%s=? where %s=?", LogLeave.TABLE_NAME,
				LogLeave.COLUMN_REPLY_TYPE, LogLeave.COLUMN_REPLY_TIME, LogLeave.COLUMN_REPLY_REASON, LogLeave.COLUMN_ID);
		Log.i("SignManageDaoImp.updateInLog", "sql="+sql);
		try {
			return runner.update(sql, reply, time, reason, id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

	public boolean insertLog(String uid, String time, String dayStart, String dayEnd, String reason, String picture) {
		String sql = String.format("insert into %s(%s, %s, %s, %s, %s, %s,%s) values(?,?,?,?,?,?,?)", LogLeave.TABLE_NAME,
				LogLeave.COLUMN_UID_APPLY, LogLeave.COLUMN_TIME_APPLY, LogLeave.COLUMN_DAY_START, LogLeave.COLUMN_DAY_END, LogLeave.COLUMN_REASON, LogLeave.COLUMN_PICTURE, LogLeave.COLUMN_REPLY_TYPE);
		try {
			runner.update(sql, uid, time, dayStart, dayEnd, reason, picture, 0);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}


}
