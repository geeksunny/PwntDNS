package com.radicalninja.pwntdns;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Configuration {

    @Expose
    @SerializedName("api")
    private Api apiCredentials;
    @Expose
    private List<Record> records;

    public Api getApiCredentials() {
        return apiCredentials;
    }

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
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

    public class Record {
        @Expose
        @SerializedName("zone_id")
        private String zoneId;
        @Expose
        private String name;
        @Expose
        private Integer ttl;
        @Expose
        private Integer priority;
        @Expose
        private String type;    // Make into enum

        public String getZoneId() {
            return zoneId;
        }

        public void setZoneId(String zoneId) {
            this.zoneId = zoneId;
        }

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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

}
