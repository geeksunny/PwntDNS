package com.radicalninja.pwntdns.rest.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.Expose;

import java.lang.reflect.Type;
import java.util.List;

public class DnsDataResponse<T> {

    @Expose
    private List<T> data;

    public List<T> getData() {
        return data;
    }

    public static class DnsDataDeserializer<T extends DnsDataResponse> implements JsonDeserializer<T> {
        @Override
        public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            // TODO: Check for "message" to return it as an error message response.
            //  otherwise, deserialize as itself.
            return null;
        }
    }

}
