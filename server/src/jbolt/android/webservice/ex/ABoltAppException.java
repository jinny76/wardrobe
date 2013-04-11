package jbolt.android.webservice.ex;

public class ABoltAppException extends Exception {

    public ABoltAppException(String detailMessage) {
        super(detailMessage);
    }

    public ABoltAppException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

}
