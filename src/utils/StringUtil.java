package utils;

public class StringUtil {
	private static final String GET="get";
	private static final String SET="set";
	public static boolean isNull(CharSequence str) {
		if (str == null)
			return true;
		if (str.equals(""))
			return true;
		return false;
	}

	// Ê××ÖÄ¸´óÐ´
	public static String captureName(String name) {
		if (!isNull(name)) {
			name = name.substring(0, 1).toUpperCase() + name.substring(1);
		}
		return name;
	}

	public static String getGetMethodName(String fieldName){
		return GET+captureName(fieldName);
	}
	public static String getSetMethodName(String fieldName){
		return SET+captureName(fieldName);
	}
}
