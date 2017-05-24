package services;

import java.sql.SQLException;
import java.util.List;

import javax.swing.text.AbstractDocument.LeafElement;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import db.LogTrip;
import db.LogSignOut;
import db.LogTrip;
import entitys.LoginDataResponse;
import utils.DataSourceManager;
import utils.Log;
import utils.StringUtil;

public class LogTripDaoImp {
	private QueryRunner runner;

	public LogTripDaoImp() {
		runner = new QueryRunner(DataSourceManager.getSource());
	}

	public List<LogTrip> queryAll() {
		String sql = String.format("select * from %s", LogTrip.TABLE_NAME);
		// TODO Auto-generated method stub
		List<LogTrip> list = null;
		try {
			list = runner.query(sql, new BeanListHandler<LogTrip>(LogTrip.class));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public List<LogTrip> queryByApplyId(String applyId) {
		String sql = String.format("select * from %s where %s=?", LogTrip.TABLE_NAME, LogTrip.COLUMN_UID_APPLY);
		// TODO Auto-generated method stub
		List<LogTrip> list = null;
		try {
			list = runner.query(sql, new BeanListHandler<LogTrip>(LogTrip.class), applyId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public List<LogTrip> queryByApplyId(String applyId, String dayStart, String dayEnd) {
		String sql = String.format("select * from %s where %s=? and %s=? and %s=?;", LogTrip.TABLE_NAME,
				LogTrip.COLUMN_UID_APPLY, LogTrip.COLUMN_DAY_START, LogTrip.COLUMN_DAY_END);
		// TODO Auto-generated method stub
		List<LogTrip> list = null;
		try {
			list = runner.query(sql, new BeanListHandler<LogTrip>(LogTrip.class), applyId, dayStart, dayEnd);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public List<LogTrip> queryByReplyId(String replyId) {
		String sql = String.format("select * from %s where %s=?", LogTrip.TABLE_NAME, LogTrip.COLUMN_UID_REPLY);
		// TODO Auto-generated method stub
		List<LogTrip> list = null;
		try {
			list = runner.query(sql, new BeanListHandler<LogTrip>(LogTrip.class), replyId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public List<LogTrip> queryById(String id) {
		String sql = String.format("select * from %s where %s=?", LogTrip.TABLE_NAME, LogTrip.COLUMN_ID);
		// TODO Auto-generated method stub
		List<LogTrip> list = null;
		try {
			list = runner.query(sql, new BeanListHandler<LogTrip>(LogTrip.class), id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 未回复的列表
	 * 
	 * @return
	 */
	public List<LogTrip> queryListByReplyType(String applyId, int replyType) {
		String sqlAll = String.format("select * from %s where %s=?;", LogTrip.TABLE_NAME, LogTrip.COLUMN_REPLY_TYPE);
		String sqlId = String.format("select * from %s where %s=? and %s=?;", LogTrip.TABLE_NAME,
				LogTrip.COLUMN_UID_APPLY, LogTrip.COLUMN_REPLY_TYPE);
		// TODO Auto-generated method stub
		List<LogTrip> list = null;
		try {
			if (StringUtil.isNull(applyId)) {
				list = runner.query(sqlAll, new BeanListHandler<LogTrip>(LogTrip.class), replyType);
			} else {
				list = runner.query(sqlId, new BeanListHandler<LogTrip>(LogTrip.class), applyId, replyType);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public boolean updateApplyTimeById(String id, String time, String way, String matters) {
		String sql = String.format("update %s set %s=?, %s=? where %s=?", LogTrip.TABLE_NAME, LogTrip.COLUMN_TIME_APPLY,
				LogTrip.COLUMN_WAY, LogTrip.COLUMN_MATTERS, LogTrip.COLUMN_ID);
		Log.i("SignManageDaoImp.updateInLog", "sql=" + sql);
		try {
			runner.update(sql, time, way, matters, id);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public int updateLogById(String id, int reply, String time, String reason) {
		String sql = String.format("update %s set %s=?, %s=?,%s=? where %s=?", LogTrip.TABLE_NAME,
				LogTrip.COLUMN_REPLY_TYPE, LogTrip.COLUMN_REPLY_TIME, LogTrip.COLUMN_REPLY_REASON, LogTrip.COLUMN_ID);
		Log.i("SignManageDaoImp.updateInLog", "sql=" + sql);
		try {
			return runner.update(sql, reply, time, reason, id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

	public boolean insertLog(String uid, String time, String dayStart, String dayEnd, String days, String way,
			String matters) {
		String sql = String.format("insert into %s(%s, %s, %s, %s, %s, %s, %s, &s) values(?, ?, ?,?,?,?,?,?)",
				LogTrip.TABLE_NAME, LogTrip.COLUMN_UID_APPLY, LogTrip.COLUMN_TIME_APPLY, LogTrip.COLUMN_DAY_START,
				LogTrip.COLUMN_DAY_END, LogTrip.COLUMN_DAYS, LogTrip.COLUMN_WAY, LogTrip.COLUMN_MATTERS,
				LogTrip.COLUMN_REPLY_TYPE);
		try {
			runner.update(sql, uid, time, dayStart, dayEnd, days, way, matters, 0);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
