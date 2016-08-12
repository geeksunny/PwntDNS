package com.radicalninja.pwntdns.rest;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Converter;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RestAdapter<T> {

    private final T client;
    private final Map<String, String> requiredHeaders = new HashMap<>();
    private final Object lock = new Object();

    public RestAdapter(final String apiServerUrl, final Class<T> clientInterface, final Converter.Factory converterFactory) {
        final Retrofit retrofit = buildAdapter(apiServerUrl, converterFactory);
        client = retrofit.create(clientInterface);
    }

    private Retrofit buildAdapter(final String apiServerUrl, final Converter.Factory converterFactory) {
        final Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(apiServerUrl).client(buildHttpClient());
        if (null != converterFactory) {
            builder.addConverterFactory(converterFactory);
        }
        return builder.build();
    }

    private OkHttpClient buildHttpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new RestInterceptor())
                .build();
    }

    public T getClient() {
        return client;
    }

    public void addHeader(final String name, final String value) {
        synchronized (lock) {
            requiredHeaders.put(name, value);
        }
    }

    public boolean removeHeader(final String name) {
        boolean wasRemoved = false;
        synchronized (lock) {
            if (requiredHeaders.containsKey(name)) {
                wasRemoved = true;
                requiredHeaders.remove(name);
            }
        }
        return wasRemoved;
    }

    public void removeAllHeaders() {
        synchronized (lock) {
            requiredHeaders.clear();
        }
    }

    class RestInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            if (requiredHeaders.isEmpty()) {
                return chain.proceed(chain.request());
            }
            final Request baseRequest = chain.request();
            final Request.Builder builder = baseRequest.newBuilder();
            final Map<String, String> headerCopy;
            synchronized (lock) {
                headerCopy = new HashMap<>(requiredHeaders);
            }
            //noinspection ConstantConditions
            if (headerCopy != null) {
                for (final Map.Entry<String, String> header : headerCopy.entrySet()) {
                    builder.addHeader(header.getKey(), header.getValue());
                }
            }
            return chain.proceed(builder.build());
        }
    }

}
