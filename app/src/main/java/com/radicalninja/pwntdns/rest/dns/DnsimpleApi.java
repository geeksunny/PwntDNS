package com.radicalninja.pwntdns.rest.dns;

import com.google.gson.Gson;
import com.radicalninja.pwntdns.rest.RestAdapter;
import retrofit2.converter.gson.GsonConverterFactory;

public class DnsimpleApi {

    // TODO: BuildConfig.DNSIMPLE_API_KEY
    // TODO: BuildConfig.DNSIMPLE_USER_ID
    private static final String API_URL = "https://api.dnsimple.com/v2/";

    private final RestAdapter<DnsimpleClient> adapter;

    private interface DnsimpleClient {

    }

    private static Gson buildGsonClient() {
        // TODO: Any Gson customization happens here.
        return new Gson();
    }

    public DnsimpleApi() {
        adapter = new RestAdapter<>(API_URL, DnsimpleClient.class, GsonConverterFactory.create(buildGsonClient()));
    }
}
