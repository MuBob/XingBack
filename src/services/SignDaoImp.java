package services;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import utils.DataSourceManager;
import entitys.SignInfo;

/**
 * 为 登录模块提供数据库服务
 * @author WangJinming
 *
 */
public class SignDaoImp {
	private QueryRunner runner = new QueryRunner(DataSourceManager.getSource());
	public List<SignInfo> getAll(String sql, String account,int year,int mouth) {
		// TODO Auto-generated method stub
		List<SignInfo> list = null;
		try {
			list = runner.query(sql,
					new BeanListHandler<SignInfo>(SignInfo.class), account,year,mouth);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public int Savedata(String sql,int Sign){
		try {
			int result = runner.update(sql,Sign);
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
}
