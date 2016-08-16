package com.radicalninja.pwntdns.rest.model.response;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class DnsResponse<T extends DnsResponse.ResponseModel> {

    private static final String ERROR_KEY = "message";

    public interface ResponseModel { }

    private boolean isSuccess;
    private T responseBody;
    private String errorMessage;

    public DnsResponse() { }

    public DnsResponse(final String errorMessage) {
        this.isSuccess = false;
        this.errorMessage = errorMessage;
    }

    private Type getResponseType() {
        return new TypeToken<T>(){}.getType();
    }

    private void setResponseBody(final T body) {
        this.responseBody = body;
    }

    private void setSuccess(final boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    private void setErrorMessage(final String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public static class DnsResponseDeserializer implements JsonDeserializer<DnsResponse> {
        @Override
        public DnsResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            // TODO: Check for "message" to return it as an error message response.
            //  otherwise, deserialize as itself.
            if (null != json) {
                try {
                    if (json.getAsJsonObject().has(ERROR_KEY)) {
                        final String errorMessage = json.getAsJsonObject().get(ERROR_KEY).getAsString();
                        return new DnsResponse(errorMessage);
                    } else {
                        final DnsResponse response = (DnsResponse) TypeToken.get(typeOfT).getRawType().newInstance();
                        //noinspection unchecked
                        response.setResponseBody(context.deserialize(json, response.getResponseType()));
                        response.setSuccess(true);
                        return response;
                    }
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            return new DnsResponse("Null response from server!");
        }
    }

}
