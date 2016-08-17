package com.radicalninja.pwntdns.rest.model.request;

import com.google.gson.annotations.Expose;
import com.radicalninja.pwntdns.Record;

public class DnsCreateZoneRecordRequest {

    @Expose
    private String name;

    @Expose
    private Record.Type type;

    @Expose
    private String content;

    @Expose
    private Integer ttl;

    @Expose
    private Integer priority;

    public DnsCreateZoneRecordRequest(String name, Record.Type type, String content) {
        this.name = name;
        this.type = type;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Record.Type getType() {
        return type;
    }

    public void setType(Record.Type type) {
        this.type = type;
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
