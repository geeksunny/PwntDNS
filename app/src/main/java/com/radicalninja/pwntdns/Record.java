package com.radicalninja.pwntdns;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Record {

    public enum Type {
        A,
        AAAA,
        AFSDB,
        APL,
        CAA,
        CDNSKEY,
        CDS,
        CERT,
        CNAME,
        DHCID,
        DLV,
        DNAME,
        DNSKEY,
        DS,
        HIP,
        IPSECKEY,
        KEY,
        KX,
        LOC,
        MX,
        NAPTR,
        NS,
        NSEC,
        NSEC3,
        NSEC3PARAM,
        PTR,
        RRSIG,
        RP,
        SIG,
        SOA,
        SRV,
        SSHFP,
        TA,
        TKEY,
        TLSA,
        TSIG,
        TXT,
        URI
    }

    @Expose
    private String name;
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
