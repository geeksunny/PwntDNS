package com.radicalninja.pwntdns.rest.model.response;

import com.google.gson.annotations.Expose;
import com.radicalninja.pwntdns.rest.RestResponse;

import java.util.List;

public class DnsListResponse<T> implements RestResponse.ResponseBody {

    @Expose
    private List<T> data;

    public List<T> getData() {
        return data;
    }

}
