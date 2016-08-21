package com.radicalninja.pwntdns.rest.model.response;

import com.google.gson.annotations.Expose;
import com.radicalninja.pwntdns.rest.RestResponse;

public class DnsItemResponse<T> implements RestResponse.ResponseBody {

    @Expose
    private T data;

    public T getData() {
        return data;
    }

}
