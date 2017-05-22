package entitys;

public class SignInfo {

	private String name;
	private int account;
	private int year;
	private int mouth;
	private int sign;
	private int evection;
	private int leave;
	private String ps;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAccount() {
		return account;
	}
	public void setAccount(int account) {
		this.account = account;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getMouth() {
		return mouth;
	}
	public void setMouth(int mouth) {
		this.mouth = mouth;
	}
	public int getSign() {
		return sign;
	}
	public void setSign(int sign) {
		this.sign = sign;
	}
	public int getEvection() {
		return evection;
	}
	public void setEvection(int evection) {
		this.evection = evection;
	}
	public int getLeave() {
		return leave;
	}
	public void setLeave(int leave) {
		this.leave = leave;
	}
	public String getPs() {
		return ps;
	}
	public void setPs(String ps) {
		this.ps = ps;
	}
	@Override
	public String toString() {
		return "SignInfo [name=" + name + ", account=" + account + ", year="
				+ year + ", mouth=" + mouth + ", sign=" + sign + ", evection="
				+ evection + ", leave=" + leave + ", ps=" + ps + ", getName()="
				+ getName() + ", getAccount()=" + getAccount() + ", getYear()="
				+ getYear() + ", getMouth()=" + getMouth() + ", getSign()="
				+ getSign() + ", getEvection()=" + getEvection()
				+ ", getLeave()=" + getLeave() + ", getPs()=" + getPs()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
	
	
}
