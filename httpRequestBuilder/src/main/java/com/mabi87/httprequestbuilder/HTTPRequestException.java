package com.mabi87.httprequestbuilder;

/**
 * Created by mabibak on 2015. 5. 13..
 */
public class HTTPRequestException extends Exception {

    private int responseCode;

    public HTTPRequestException(String message, int responseCode) {
        super(message);

        this.responseCode = responseCode;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

}
