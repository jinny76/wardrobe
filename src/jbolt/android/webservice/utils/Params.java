package jbolt.android.webservice.utils;

import java.util.HashMap;

/**
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Abolt Team</p>
 *
 * @author Jinni
 */
public class Params extends HashMap<String, String> {

    public Params(String... params) {
        for (int i = 0; i < params.length / 2; i++) {
            put(params[i], params[i + 1]);
        }
    }

    public Params add(String key, String value) {
        put(key, value);
        return this;
    }
}
