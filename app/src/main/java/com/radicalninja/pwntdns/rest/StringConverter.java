package com.radicalninja.pwntdns.rest;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

public class StringConverter extends Converter.Factory {

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new StringResponseBodyConverter();
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return new StringRequestBodyConverter();
    }

    public class StringResponseBodyConverter implements Converter<ResponseBody, String> {
        @Override
        public String convert(ResponseBody value) throws IOException {
            try {
                return value.string();
            } catch (Exception e) {
                return "COULD_NOT_PARSE";
            }
        }
    }

    public class StringRequestBodyConverter implements Converter<Object, RequestBody> {
        @Override
        public RequestBody convert(Object value) throws IOException {
            final MediaType mediaType = MediaType.parse("text/plain; charset=utf-8");
            return RequestBody.create(mediaType, value.toString());
        }
    }

}
