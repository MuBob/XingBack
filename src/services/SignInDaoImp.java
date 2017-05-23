package services;

import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import db.LogSignIn;
import utils.DataSourceManager;
import utils.DateUtil;
import utils.Log;

public class SignInDaoImp {
	private QueryRunner runner;

	public SignInDaoImp() {
		runner = new QueryRunner(DataSourceManager.getSource());
	}

	public List<LogSignIn> queryById(String id) {
		String sql = String.format("select * from %s where %s=? order by %s desc", LogSignIn.TABLE_NAME,
				LogSignIn.COLUMN_UID, LogSignIn.COLUMN_TIME);
		// TODO Auto-generated method stub
		List<LogSignIn> list = null;
		try {
			list = runner.query(sql, new BeanListHandler<LogSignIn>(LogSignIn.class), id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public int signInToday(String id, String place, String time) {
		int totalSignCount=2;
		LogSignIn signIn = hasFirstLogToday(id, time);
		int count = 0;
		if (signIn == null) {
			// 第一次插入，count=1
			if (insertInLog(id, place, time)) {
				count = 1;
			} else {
				// 插入失败
				count = -1;
			}
		} else {
			count = signIn.getCount();
			if (count < totalSignCount) {
				count++;
				if (!updateInLog(id, place, time, count)) {
					// 更新失败
					count = -2;
				}
			}
		}
		return count;
	}

	public boolean updateInLog(String id, String place, String time, int count) {
		/*String sql = String.format("update %s set %s=?, %s=?,%s=? where %s=?", LogSignIn.TABLE_NAME,
				LogSignIn.COLUMN_PLACE2, place, LogSignIn.COLUMN_TIME2, time, LogSignIn.COLUMN_COUNT, count,
				LogSignIn.COLUMN_UID, id);*/
		String sql = String.format("update %s set %s=?, %s=?,%s=? where %s=?", LogSignIn.TABLE_NAME,
				LogSignIn.COLUMN_PLACE2, LogSignIn.COLUMN_TIME2, LogSignIn.COLUMN_COUNT,
				LogSignIn.COLUMN_UID);
		Log.i("SignManageDaoImp.updateInLog", "sql="+sql);
		try {
			runner.update(sql, place, time, count, id);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public boolean insertInLog(String id, String place, String time) {
		String sql = String.format("insert into %s(%s, %s, %s) values(?,?,?)", LogSignIn.TABLE_NAME,
				LogSignIn.COLUMN_UID, LogSignIn.COLUMN_PLACE, LogSignIn.COLUMN_TIME);
		try {
			runner.update(sql, id, place, time);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public LogSignIn hasFirstLogToday(String id, String time) {
		List<LogSignIn> list = queryById(id);
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				LogSignIn signInBean = list.get(i);
				if (DateUtil.isOneDay(time, signInBean.getTime1()) || DateUtil.isOneDay(time, signInBean.getTime2())) {
					return signInBean;
				}
			}
		}
		return null;
	}

}
