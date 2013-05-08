package jbolt.android.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * <p>Title: jbolt.android.webservice.utils</p>
 * <p>Description: jbolt.android.webservice.utils</p>
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: JBolt.com</p>
 *
 * @author Jinni
 */
public class GsonUtil {

    public static Gson getGson() {
        return new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    }

}
