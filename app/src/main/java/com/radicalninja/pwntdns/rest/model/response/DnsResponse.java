package com.radicalninja.pwntdns.rest.model.response;

import com.google.gson.annotations.Expose;
import com.radicalninja.pwntdns.StringUtils;

public class DnsResponse {

    private static final String ERROR_KEY = "message";

    @Expose
    private String errorMessage;

    public boolean isSuccess() {
        return StringUtils.isEmpty(errorMessage);
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
