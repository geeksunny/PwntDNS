package com.radicalninja.pwntdns;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Configuration {

    @Expose
    @SerializedName("api")
    private Api apiCredentials;
    @Expose
    @SerializedName("domains")
    private DomainConfigList domainConfigs;

    public Api getApiCredentials() {
        return apiCredentials;
    }

    public DomainConfigList getDomainConfigs() {
        return domainConfigs;
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

    // TODO: Should we move this back to being a map object? Determine use case
    public static class DomainConfigList extends ArrayList<DomainConfig> { }

    public static class DomainConfigListDeserializer implements JsonDeserializer<DomainConfigList> {
        @Override
        public DomainConfigList deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            final Type type =
                    new TypeToken<LinkedTreeMap<String, LinkedTreeMap<Record.Type, List<Record>>>>(){}.getType();
            final LinkedTreeMap<String, LinkedTreeMap<Record.Type, List<Record>>> mapData =
                    context.deserialize(json, type);

            final DomainConfigList configList = new DomainConfigList();
            for (final Map.Entry<String, LinkedTreeMap<Record.Type, List<Record>>> domain : mapData.entrySet()) {
                final DomainConfig config = new DomainConfig(domain.getKey(), domain.getValue());
                configList.add(config);
            }
            return configList;
        }
    }

    public static class DomainConfig {
        private String name;
        private Map<Record.Type, List<Record>> records;

        public DomainConfig(String name, Map<Record.Type, List<Record>> records) {
            this.name = name;
            this.records = records;
        }

        public String getName() {
            return name;
        }

        public Map<Record.Type, List<Record>> getRecords() {
            return records;
        }
    }

}
