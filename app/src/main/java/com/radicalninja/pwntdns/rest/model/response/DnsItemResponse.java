package com.radicalninja.pwntdns.rest.model.response;

import com.google.gson.annotations.Expose;

import java.util.List;

public class DnsItemResponse<T> implements DnsResponse.ResponseModel {

    @Expose
    private T data;

    public T getData() {
        return data;
    }

}
