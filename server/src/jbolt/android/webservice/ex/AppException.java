package jbolt.android.webservice.ex;

public class AppException extends Exception {

    public AppException(String detailMessage) {
        super(detailMessage);
    }

    public AppException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

}
