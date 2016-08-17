package com.radicalninja.pwntdns;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.LinkedTreeMap;

import java.util.HashMap;
import java.util.List;

public class Configuration {

    @Expose
    @SerializedName("api")
    private Api apiCredentials;
    @Expose
    @SerializedName("domains")
    private LinkedTreeMap<String, List<Record>> domainRecordMap;

    public Api getApiCredentials() {
        return apiCredentials;
    }

//    public DomainRecordMap getDomainRecordMap() {
//        return domainRecordMap;
//    }
//
//    public void setDomainRecordMap(DomainRecordMap domainRecordMap) {
//        this.domainRecordMap = domainRecordMap;
//    }

    public class Api {
        @Expose
        @SerializedName("api_key")
        private String dnsimpleApiKey;
        @Expose
        @SerializedName("user_id")
        private String dnsimpleUserId;

        public String getDnsimpleApiKey() {
            return dnsimpleApiKey;
        }

        public String getDnsimpleUserId() {
            return dnsimpleUserId;
        }
    }

    public class DomainRecordMap {
        // TODO: Add methods here for handling domain record objects
    }

}
