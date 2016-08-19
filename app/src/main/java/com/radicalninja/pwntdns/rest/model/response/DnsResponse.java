package com.radicalninja.pwntdns.rest.model.response;

import com.google.gson.annotations.Expose;
import com.radicalninja.pwntdns.StringUtils;

public class DnsResponse {

    private static final String ERROR_KEY = "message";

    @Expose
    private String error;

    public boolean isSuccess() {
        return StringUtils.isEmpty(error);
    }

    public String getError() {
        return error;
    }

}
