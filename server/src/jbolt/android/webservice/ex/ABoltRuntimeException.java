package jbolt.android.webservice.ex;

/**
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Abolt Team</p>
 *
 * @author Jinni
 */
public class ABoltRuntimeException extends RuntimeException {

    public ABoltRuntimeException() {
    }

    public ABoltRuntimeException(String message) {
        super(message);
    }

    public ABoltRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ABoltRuntimeException(Throwable cause) {
        super(cause);
    }
}
