package com.radicalninja.pwntdns.rest;

import okhttp3.*;
import retrofit2.Call;
import retrofit2.Converter;
import retrofit2.Retrofit;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RestAdapter<T> {

    private final Retrofit retrofit;
    private final T client;
    private final Map<String, String> requiredHeaders = new HashMap<>();
    private final Object lock = new Object();

    public RestAdapter(final String apiServerUrl, final Class<T> clientInterface, final Converter.Factory converterFactory) {
        retrofit = buildAdapter(apiServerUrl, converterFactory);
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

    /**
     * Simple method to synchronously make a call and return the resulting response body.
     * There is no explicit error handling here other than a null response.
     * @param call    A Call object for making your network request.
     * @param <RT>    Your response type class.
     * @return  Returns the response body in the response type class format, or null if there is an error.
     */
    @Nullable
    public <RT> RT doSynchronousCall(final Call<RT> call) {
        try {
            return call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Simple method to synchronously make a call and return the result.
     * The resulting {@link RestResponse} will contain either the response body
     * or an error object depending on the response's outcome.
     * There is no explicit error handling here other than a null response.
     * @param call    A Call object for making your network request.
     * @param <RT>    Your response body type class.
     * @param <E>     Your response error type class
     * @return
     */
    // TODO: This method's name doesn't make sense. Change it.
    @Nullable
    public <RT extends RestResponse.ResponseBody, E extends RestResponse.ResponseError> RestResponse<RT, E> doSynchronousCallWrapped(final Call<RT> call, final Class<E> errorType) {
        try {
            final retrofit2.Response<RT> response = call.execute();
            if (response.isSuccessful()) {
                return new RestResponse<>(response.code(), response.body());
            } else {
                // TODO: Handle IllegalArgumentException on the call below.
                final Converter<ResponseBody, E> converter
                        = retrofit.responseBodyConverter(errorType, errorType.getAnnotations());
//                final String err = response.errorBody().string();
                final E errorBody = converter.convert(response.errorBody());
                return new RestResponse<>(response.code(), errorBody);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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
