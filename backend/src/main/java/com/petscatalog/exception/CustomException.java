package com.petscatalog.exception;

/**
 * Created by Tlegen on 11.01.2018.
 */
public class CustomException extends Exception{
    private int errCode;

    public CustomException(String message, int errCode) {
        super(message);
        this.errCode = errCode;
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }
}
