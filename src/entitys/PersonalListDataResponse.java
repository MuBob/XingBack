package entitys;

import java.util.Date;
import java.util.List;

import db.BaseDataBase;
import db.TableDepartment;
import db.TablePersonal;

public class PersonalListDataResponse extends BaseDataResponse {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8454518505513100649L;
	private List<TablePersonal> list;

	public List<TablePersonal> getList() {
		return list;
	}
	public void setList(List<TablePersonal> list) {
		this.list = list;
	}
	@Override
	public String toString() {
		return "PersonalListDataResponse [list=" + list + "]";
	}
	
	
}
