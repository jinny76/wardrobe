package com.abolt.exceptions;

public class ABoltAppException extends Exception {

    public ABoltAppException(String detailMessage) {
        super(detailMessage);
    }

    public ABoltAppException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

}
