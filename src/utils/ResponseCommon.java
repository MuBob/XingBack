package utils;

public class ResponseCommon {
	public static class Code{
		public static final int SUCCESS=0;
		public static final int FAILE=-1;
		public static final int ERROR_PARAMS=-5;
	}
	public static class Msg{
		public static final String SUCCESS_MODIFY="�޸ĳɹ�";
		public static final String SUCCESS_SIGN_IN="ǩ���ɹ�";
		public static final String FAILE_SIGN_IN="ǩ��ʧ��";
		public static final String FAILE_SIGN_IN_DUPLIDE="��ǩ���������ظ�ǩ��";
		public static final String SUCCESS_SIGN_OUT="ǩ���ɹ�";
		public static final String FAILE_SIGN_OUT="ǩ��ʧ��";
		public static final String FAILE_SIGN_OUT_DUPLIDE="��ǩ���������ظ�ǩ��";
		
		public static final String ERROR_FAILE_MODIFY="�޸�ʧ��";
		public static final String ERROR_NORMAL="�����������쳣";
		public static final String ERROR_PARAMS="������������쳣";
		public static final String ERROR_FAILE_LOGIN="��½ʧ��";
		public static final String ERROR_FAILE_LOGIN_NO_USER="��ǰ�û�û��ע��";
		public static final String ERROR_FAILE_LOGIN_ERROR_INFO="�û����������";
		
		
		
	}

}
