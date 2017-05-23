package utils;

public class ResponseCommon {
	public static class Code{
		public static final int SUCCESS=0;
		public static final int FAILE=-1;
		public static final int ERROR_PARAMS=-5;
	}
	public static class Msg{
		public static final String SUCCESS_MODIFY="修改成功";
		public static final String SUCCESS_SIGN_IN="签到成功";
		public static final String FAILE_SIGN_IN="签到失败";
		public static final String FAILE_SIGN_IN_DUPLIDE="已签到，不可重复签到";
		public static final String SUCCESS_SIGN_OUT="签到成功";
		public static final String FAILE_SIGN_OUT="签到失败";
		public static final String FAILE_SIGN_OUT_DUPLIDE="已签到，不可重复签到";
		
		public static final String ERROR_FAILE_MODIFY="修改失败";
		public static final String ERROR_NORMAL="服务器出现异常";
		public static final String ERROR_PARAMS="请求参数出现异常";
		public static final String ERROR_FAILE_LOGIN="登陆失败";
		public static final String ERROR_FAILE_LOGIN_NO_USER="当前用户没有注册";
		public static final String ERROR_FAILE_LOGIN_ERROR_INFO="用户或密码错误";
		
		
		
	}

}
