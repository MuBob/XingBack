package entitys;

import java.util.Date;

public class SelfInfoBaseDataResponse extends BaseDataResponse {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1562951043899664433L;
	private String id;
	private String name;
	private int sex;
	private String idCard;
	private Date birthday;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	@Override
	public String toString() {
		return "SelfInfoBaseDataResponse [id=" + id + ", name=" + name + ", sex=" + sex + ", idCard=" + idCard
				+ ", birthday=" + birthday + "]";
	}
	
}
