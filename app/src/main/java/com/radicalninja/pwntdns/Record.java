package com.radicalninja.pwntdns;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Record {

    public enum Type {
        A,
        ALIAS,
        CNAME,
        MX,
        SPF,
        URL,
        TXT,
        NS,
        SRV,
        NAPTR,
        PTR,
        AAAA,
        SSHFP,
        HINFO,
        POOL
    }

    @Expose
    private String name;
    @Expose
    private Integer ttl;
    @Expose
    private Integer priority;
    @Expose
    private Type type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
