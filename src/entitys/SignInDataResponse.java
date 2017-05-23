package entitys;

import db.BaseDataBase;
import db.LogSignIn;

public class SignInDataResponse extends BaseDataResponse{
	public SignInDataResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SignInDataResponse(LogSignIn dbBean) {
		super(dbBean);
		setSignBean(dbBean);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -5658430071665639311L;
	private LogSignIn signBean;

	public LogSignIn getSignBean() {
		return signBean;
	}

	public void setSignBean(LogSignIn signBean) {
		this.signBean = signBean;
	}

	@Override
	public String toString() {
		return "SignInDataResponse [signBean=" + signBean + "]";
	}
	

}
