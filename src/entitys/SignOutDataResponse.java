package entitys;

import db.LogSignIn;
import db.LogSignOut;

public class SignOutDataResponse extends BaseDataResponse{
	public SignOutDataResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SignOutDataResponse(LogSignOut dbBean) {
		super(dbBean);
		setSignBean(dbBean);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -5658430071665639311L;
	private LogSignOut signBean;

	public LogSignOut getSignBean() {
		return signBean;
	}

	public void setSignBean(LogSignOut signBean) {
		this.signBean = signBean;
	}

	@Override
	public String toString() {
		return "SignOutDataResponse [signBean=" + signBean + "]";
	}
	

}
