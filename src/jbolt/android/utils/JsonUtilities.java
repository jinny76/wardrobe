package jbolt.android.utils;

/**
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Abolt Team</p>
 *
 * @author Jinni
 */
public class JsonUtilities {

    public static Long getLongValue(String jsonString) {
        return Long.parseLong(StringUtilities.splitString2Array(jsonString.substring(1, jsonString.length() - 1), ":")[1].trim());
    }

    public static Integer getIntegerValue(String jsonString) {
        return Integer.parseInt(StringUtilities.splitString2Array(jsonString.substring(1, jsonString.length() - 1), ":")[1].trim());
    }

    public static String getStringValue(String jsonString) {
        String result = StringUtilities.splitString2Array(jsonString.substring(1, jsonString.length() - 1), ":")[1].trim();
        return result.substring(1, result.length() - 1);
    }

}
