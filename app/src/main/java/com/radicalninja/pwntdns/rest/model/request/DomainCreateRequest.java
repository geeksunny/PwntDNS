package com.radicalninja.pwntdns.rest.model.request;

import com.google.gson.annotations.Expose;

public class DomainCreateRequest {

    @Expose
    private String name;

    public DomainCreateRequest(final String name) {
        this.name = name;
    }
}
