package com.radicalninja.pwntdns.rest.ip;

import com.radicalninja.pwntdns.rest.RestAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;

public class IpifyApi {

    private final static String API_URL = "https://api.ipify.org?format=text";

    private RestAdapter<IpifyClient> adapter;

    private interface IpifyClient {
        @GET
        Call<String> queryIpAddress();
    }

    public IpifyApi() {
        adapter = new RestAdapter<>(API_URL, IpifyClient.class, new StringConverter());
    }

    public void queryIpAddress(final Callback<String> callback) {
        adapter.getClient().queryIpAddress().enqueue(callback);
    }

}
