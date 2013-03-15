package jbolt.android.utils;

/**
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Abolt Team</p>
 *
 * @author Jinni
 */
public class Log {

    public static final String A_BOLT_LOG = "----------[jBolt Android Log]----------";

    public static void d(String category, String message) {
        android.util.Log.d(category, A_BOLT_LOG + message);
    }

    public static void d(String category, String message, Throwable throwable) {
        android.util.Log.d(category, A_BOLT_LOG + message, throwable);
    }

    public static void i(String category, String message) {
        android.util.Log.i(category, A_BOLT_LOG + message);
    }

    public static void i(String category, String message, Throwable throwable) {
        android.util.Log.i(category, A_BOLT_LOG + message, throwable);
    }

    public static void w(String category, String message) {
        android.util.Log.w(category, A_BOLT_LOG + message);
    }

    public static void w(String category, String message, Throwable throwable) {
        android.util.Log.w(category, A_BOLT_LOG + message, throwable);
    }

    public static void e(String category, String message) {
        android.util.Log.e(category, A_BOLT_LOG + message);
    }

    public static void e(String category, String message, Throwable throwable) {
        android.util.Log.e(category, A_BOLT_LOG + message, throwable);
    }
}
