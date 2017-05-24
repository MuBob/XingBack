package entitys;

import java.util.Date;
import java.util.List;

import db.BaseDataBase;
import db.TableDepartment;
import db.TablePersonal;

public class UnReplyPersonalListDataResponse extends BaseDataResponse {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8454518505513100649L;
	private List<UnReplyPersonalBean> list;

	public List<UnReplyPersonalBean> getList() {
		return list;
	}
	public void setList(List<UnReplyPersonalBean> list) {
		this.list = list;
	}
	@Override
	public String toString() {
		return "UnReplyTripListDataResponse [list=" + list + "]";
	}
	
	
}
