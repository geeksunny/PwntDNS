package com.radicalninja.pwntdns.rest.model.response;

import com.google.gson.annotations.Expose;
import com.radicalninja.pwntdns.StringUtils;
import com.radicalninja.pwntdns.rest.RestResponse;

public class DnsErrorResponse implements RestResponse.ResponseError {

    @Expose
    private String message;

    public boolean isSuccess() {
        return StringUtils.isEmpty(message);
    }

    public String getMessage() {
        return message;
    }

}
