package com.radicalninja.pwntdns.rest.api;

import com.radicalninja.pwntdns.rest.RestAdapter;
import com.radicalninja.pwntdns.rest.StringConverter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;

public class Ipify {

    private final static String API_URL = "https://api.ipify.org/";

    private RestAdapter<Client> adapter;

    private interface Client {
        @GET("?format=text")
        Call<String> queryIpAddress();
    }

    public Ipify() {
        adapter = new RestAdapter<>(API_URL, Client.class, new StringConverter());
    }

    public String queryIpAddress() {
        return adapter.doSynchronousCall(adapter.getClient().queryIpAddress());
    }

    public void queryIpAddress(final Callback<String> callback) {
        adapter.getClient().queryIpAddress().enqueue(callback);
    }

}
