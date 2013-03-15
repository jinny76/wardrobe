package jbolt.android.base.exception;

/**
 * <p>Title: DeviceRuntimeException</p>
 * <p>Description: DeviceRuntimeException</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: IPACS e-Solutions (S) Pte Ltd</p>
 *
 * @author feng.xie
 */
public class DeviceRuntimeException extends Exception {

    public DeviceRuntimeException() {
    }

    public DeviceRuntimeException(String detailMessage) {
        super(detailMessage);
    }

    public DeviceRuntimeException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public DeviceRuntimeException(Throwable throwable) {
        super(throwable);
    }
}
