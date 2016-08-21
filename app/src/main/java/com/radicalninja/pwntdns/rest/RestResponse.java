package com.radicalninja.pwntdns.rest;

import javax.annotation.Nonnull;
import java.util.Objects;

public class RestResponse<B extends RestResponse.ResponseBody, E extends RestResponse.ResponseError> {

    public interface ResponseBody { }
    public interface ResponseError { }

    private int responseCode;
    private B responseBody;
    private E responseError;

    public RestResponse(int responseCode, @Nonnull B responseBody) throws NullPointerException {
        this.responseCode = responseCode;
        this.responseBody = Objects.requireNonNull(responseBody, "Response body must not be null.");
    }

    public RestResponse(int responseCode, @Nonnull E responseError) throws NullPointerException {
        this.responseCode = responseCode;
        this.responseError = Objects.requireNonNull(responseError, "Response error must not be null.");
    }

    public boolean isSuccess() {
        return null == responseError;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(final int responseCode) {
        this.responseCode = responseCode;
    }

    public B getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(final B responseBody) {
        this.responseBody = responseBody;
    }

    public E getResponseError() {
        return responseError;
    }

    public void setResponseError(final E responseError) {
        this.responseError = responseError;
    }

}
