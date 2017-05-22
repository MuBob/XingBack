package utils;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {
	private static final boolean IS_DEBUG = true;
	private static final boolean IS_INFORMATION = true;
	private static final boolean IS_ERROR = true;

	public final static String currentTime() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	public static void d(String tag, String msg) {
		if (IS_DEBUG) {
			System.out.println(currentTime() + " Debug: " + tag + ": " + msg);
		}
	}

	public static void i(String tag, String msg) {
		if (IS_DEBUG) {
			System.out.println(currentTime() + " Information: " + tag + ": " + msg);
		}
	}

	public static void e(String tag, String msg) {
		if (IS_DEBUG) {
			System.out.println(currentTime() + " Error: " + tag + ": " + msg);
		}
	}

}
