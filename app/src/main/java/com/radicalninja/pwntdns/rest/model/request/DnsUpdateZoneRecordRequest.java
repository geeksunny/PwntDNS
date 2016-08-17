package com.radicalninja.pwntdns.rest.model.request;

import com.google.gson.annotations.Expose;

public class DnsUpdateZoneRecordRequest {

    @Expose
    private String name;

    @Expose
    private String content;

    @Expose
    private Integer ttl;

    @Expose
    private Integer priority;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getTtl() {
        return ttl;
    }

    public void setTtl(Integer ttl) {
        this.ttl = ttl;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }
}
