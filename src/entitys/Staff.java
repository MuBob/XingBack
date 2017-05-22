package entitys;

public class Staff {
private int id;
private String name;
private String number;
private String password;

public Staff() {
	super();
}
public Staff(String number, String password) {
	super();
	this.number = number;
	this.password = password;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}

public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getNumber() {
	return number;
}
public void setNumber(String number) {
	this.number = number;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
@Override
public String toString() {
	return "Staff [id=" + id + ", number=" + number + ", password=" + password
			+ "]";
}

}
