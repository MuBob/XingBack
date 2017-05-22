package services;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import entitys.Mine_info;
import utils.DataSourceManager;
import com.sun.org.apache.bcel.internal.generic.*;


public class Service_mineinfo {
	private QueryRunner runner = new QueryRunner(DataSourceManager.getSource());
	
	public List<Mine_info> getAll() {
		String sql_getall = "select * from mine_info";
		List<Mine_info> list = null;
		try {
			list = runner.query(sql_getall,
					new BeanListHandler<Mine_info>(Mine_info.class));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public boolean saveAll(List<Mine_info> mList){
		String sql_updata;
		boolean flag = false;
		for(Mine_info info : mList){
			sql_updata = "updata mine_info set value=? where key=?";
			try {
				int result = runner.update(sql_updata,info.getValue(),info.getKey());
				if(result>-1){
					flag = true;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return flag;
	}
}
