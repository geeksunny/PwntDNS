package com.radicalninja.pwntdns.rest.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.radicalninja.pwntdns.PwntDns;
import com.radicalninja.pwntdns.rest.RestAdapter;
import com.radicalninja.pwntdns.rest.model.Responses;
import com.radicalninja.pwntdns.rest.model.request.DnsCreateZoneRecordRequest;
import com.radicalninja.pwntdns.rest.model.response.DnsResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.*;

public class Dnsimple {

    // TODO: BuildConfig.DNSIMPLE_API_KEY
    // TODO: BuildConfig.DNSIMPLE_USER_ID
    private static final String API_URL = "https://api.dnsimple.com/v2/";

    private final RestAdapter<Client> adapter;

    // TODO: Add static map data of required headers for this particular service
    // TODO: Add an interface method for adding/subtracting any headers for a given request
    private static Gson buildGsonClient() {
        // TODO: Any Gson customization happens here.
        final GsonBuilder builder = new GsonBuilder();
        // TODO: disable serializing nulls?
        builder.excludeFieldsWithoutExposeAnnotation();
        builder.registerTypeAdapter(DnsResponse.class, new DnsResponse.DnsResponseDeserializer());
        return new Gson();
    }

    public Dnsimple() {
        adapter = new RestAdapter<>(API_URL, Client.class, GsonConverterFactory.create(buildGsonClient()));
        setupDefaultHeaders();
    }

    private void setupDefaultHeaders() {
        final String tokenString = String.format("Bearer %s", PwntDns.getApiKey());
        adapter.addHeader("Authorization", tokenString);
        adapter.addHeader("Accept", "application/json");
        adapter.addHeader("Content-Type", "application/json");
    }

    private interface Client {
        @GET("{accountId}/domains")
        Call<Responses.DomainsListResponse> getDomainsList(@Path("accountId") final String accountId);

        @GET("{accountId}/zones")
        Call<Responses.GetZoneResponse> getZonesList(@Path("accountId") final String accountId);

        @GET("{accountId}/zones/{zoneName}")
        Call<Responses.GetZoneResponse> getZone(
                @Path("accountId") final String accountId, @Path("zoneName") final String zoneName);

        // TODO: rename to getRecordsForZone? Review the client method names.
        @GET("{accountId}/zones/{zoneName}/records")
        Call<Responses.ZoneRecordsListResponse> getZoneRecords(
                @Path("accountId") final String accountId, @Path("zoneName") final String zoneName);

        @POST("{accountId}/zones/{zoneName}/records")
        Call<Responses.CreateZoneRecordResponse> createZoneRecord(
                @Path("accountId") final String accountId, @Path("zoneName") final String zoneName,
                @Body final DnsCreateZoneRecordRequest request);

        @PATCH("{accountId}/zones/{zoneName}/records/{recordId}")
        Call<Responses.CreateZoneRecordResponse> updateZoneRecord(
                @Path("accountId") final String accountId, @Path("zoneName") final String zoneName,
                @Path("recordId") final int recordId, @Body final DnsCreateZoneRecordRequest request);
    }

    public void getDomainsList(final Callback<Responses.DomainsListResponse> callback) {
        adapter.getClient().getDomainsList(PwntDns.getUserId()).enqueue(callback);
    }

}
