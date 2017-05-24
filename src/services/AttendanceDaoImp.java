package services;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import db.TableAttendance;
import db.TablePersonal;
import entitys.SelfInfoBaseDataResponse;
import utils.DataSourceManager;
import utils.Log;

public class AttendanceDaoImp {
	private QueryRunner runner;

	public AttendanceDaoImp() {
		runner = new QueryRunner(DataSourceManager.getSource());
	}
	public List<TableAttendance> queryAll() {
		String sql=String.format("select * from %s", TableAttendance.TABLE_NAME);
		Log.i("TAG", "sql="+sql);
		List<TableAttendance> list = null;
		try {
			list = runner.query(sql,
					new BeanListHandler<TableAttendance>(TableAttendance.class));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public List<TableAttendance> queryById(String uId) {
		String sql=String.format("select * from %s where %s=?", TableAttendance.TABLE_NAME, TableAttendance.COLUMN_UID);
		Log.i("TAG", "sql="+sql);
		List<TableAttendance> list = null;
		try {
			list = runner.query(sql,
					new BeanListHandler<TableAttendance>(TableAttendance.class),uId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public int deleteById(String dId) {
		String sql=String.format("delete from %s where %s=?", TableAttendance.TABLE_NAME, TableAttendance.COLUMN_UID);
		try {
			return runner.update(sql,dId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return-1;
	}

	public int updateById(String uid, double tripDays, double leaveDays, double absenteeismDays) {
		String sql=String.format("replace into %s"
				+ "(%s, %s, %s, %s)"
				+ " values(?,?,?,?);", TableAttendance.TABLE_NAME, TableAttendance.COLUMN_UID,TableAttendance.COLUMN_DAYS_TRIP, TableAttendance.COLUMN_DAYS_LEAVE, TableAttendance.COLUMN_DAYS_ABSENTEEISM);
		try {
			return runner.update(sql,uid, tripDays, leaveDays, absenteeismDays);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
}
