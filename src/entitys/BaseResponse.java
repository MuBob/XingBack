package entitys;


import java.io.Serializable;

public class BaseResponse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3160048072479221220L;
	private String msg;
	private int code;
	private String data;
	
	public BaseResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "BaseResponse [msg=" + msg + ", code=" + code + ", data=" + data + "]";
	}
	
	
}
