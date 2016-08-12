package com.radicalninja.pwntdns.rest.model;

import com.google.gson.annotations.Expose;

public class DnsErrorResponse {

    @Expose
    private String message;

    public String getMessage() {
        return message;
    }
}
