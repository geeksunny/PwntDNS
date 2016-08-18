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

    public LinkedTreeMap<String, List<Record>> getDomainRecordMap() {
        return domainRecordMap;
    }

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

}
