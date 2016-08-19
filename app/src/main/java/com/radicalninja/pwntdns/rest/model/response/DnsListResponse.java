package com.radicalninja.pwntdns.rest.model.response;

import com.google.gson.annotations.Expose;

import java.util.List;

public class DnsListResponse<T> extends DnsResponse {

    @Expose
    private List<T> data;

    public List<T> getData() {
        return data;
    }

}
