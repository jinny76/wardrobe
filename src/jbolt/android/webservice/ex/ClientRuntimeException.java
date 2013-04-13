package jbolt.android.webservice.ex;

import java.lang.RuntimeException;

/**
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Abolt Team</p>
 *
 * @author Jinni
 */
public class ClientRuntimeException extends RuntimeException {

    public ClientRuntimeException() {
    }

    public ClientRuntimeException(String message) {
        super(message);
    }

    public ClientRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClientRuntimeException(Throwable cause) {
        super(cause);
    }
}
