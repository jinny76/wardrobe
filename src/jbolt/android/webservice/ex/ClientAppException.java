package jbolt.android.webservice.ex;

public class ClientAppException extends Exception {

    public ClientAppException(String detailMessage) {
        super(detailMessage);
    }

    public ClientAppException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

}
