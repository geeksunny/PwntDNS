package com.radicalninja.pwntdns.rest.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.radicalninja.pwntdns.rest.RestAdapter;
import com.radicalninja.pwntdns.rest.model.DnsDomain;
import com.radicalninja.pwntdns.rest.model.Responses;
import com.radicalninja.pwntdns.rest.model.request.DnsCreateZoneRecordRequest;
import com.radicalninja.pwntdns.rest.model.request.DnsUpdateZoneRecordRequest;
import com.radicalninja.pwntdns.rest.model.request.DomainCreateRequest;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.*;

import java.util.ArrayList;
import java.util.List;

public class Dnsimple {

    private static final String API_URL = "https://api.dnsimple.com/v2/";

    private final RestAdapter<Client> adapter;
    private final String apiKey, accountId;

    // TODO: Add an interface method for adding/subtracting any headers for a given request
    private static Gson buildGsonClient() {
        final GsonBuilder builder = new GsonBuilder();
        builder.excludeFieldsWithoutExposeAnnotation();
        return builder.create();
    }

    public Dnsimple(final String apiKey, final String accountId) {
        this.apiKey = apiKey;
        this.accountId = accountId;
        adapter = new RestAdapter<>(API_URL, Client.class, GsonConverterFactory.create(buildGsonClient()));
        setupDefaultHeaders();
    }

    private void setupDefaultHeaders() {
        final String tokenString = String.format("Bearer %s", apiKey);
        adapter.addHeader("Authorization", tokenString);
        adapter.addHeader("Accept", "application/json");
        adapter.addHeader("Content-Type", "application/json");
    }

    private interface Client {
        /* Domains */
        @GET("{accountId}/domains")
        Call<Responses.DomainsListResponse> getDomainsList(@Path("accountId") final String accountId);

        @GET("{accountId}/domains/{domainName}")
        Call<Responses.GetDomainResponse> getDomain(
                @Path("accountId") final String accountId, @Path("domainName") final String domainName);

        @POST("{accountId}/domains")
        Call<Responses.CreateDomainResponse> createDomain(
                @Path("accountId") final String accountId, @Body final DomainCreateRequest request);

        @DELETE("{accountId}/domains/{domainName}")
        Call<Response> deleteDomain(@Path("accountId") final String accountId, @Path("domainName") final String domainName);

        @POST("{accountId}/domains/{domainName}/token")
        Call<Responses.ResetDomainTokenResponse> resetDomainToken(
                @Path("accountId") final String accountId, @Path("domainName") final String domainName);

        /* Zones */
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
        Call<Responses.UpdateZoneRecordResponse> updateZoneRecord(
                @Path("accountId") final String accountId, @Path("zoneName") final String zoneName,
                @Path("recordId") final int recordId, @Body final DnsUpdateZoneRecordRequest request);
    }

    /**
     * Query API synchronously for a list of domains.
     * // TODO: This currently does not support result pagination!!
     * @return The resulting list of domains.
     */
    public List<DnsDomain> getDomainsList() {
        final Responses.DomainsListResponse response =
                adapter.doSynchronousCall(adapter.getClient().getDomainsList(accountId));
        return (null != response)
                ? response.getData()
                : new ArrayList<DnsDomain>();
    }

    public Responses.GetDomainResponse getDomainRecord(final String domainName) {
        return adapter.doSynchronousCall(adapter.getClient().getDomain(accountId, domainName));
    }

    public Responses.CreateDomainResponse createDomain(final String domainName) {
        final DomainCreateRequest request = new DomainCreateRequest(domainName);
        return adapter.doSynchronousCall(adapter.getClient().createDomain(accountId, request));
    }

    public Responses.ZoneRecordsListResponse getZoneRecords(final String zoneName) {
        return adapter.doSynchronousCall(adapter.getClient().getZoneRecords(accountId, zoneName));
    }

    public Responses.CreateZoneRecordResponse createZoneRecord(
            final String zoneName, final DnsCreateZoneRecordRequest request) {
        return adapter.doSynchronousCall(adapter.getClient().createZoneRecord(accountId, zoneName, request));
    }

    public Responses.UpdateZoneRecordResponse updateZoneRecord(final String zoneName, final int recordId,
                                                               final DnsUpdateZoneRecordRequest request) {
        return adapter.doSynchronousCall(adapter.getClient().updateZoneRecord(accountId, zoneName, recordId, request));
    }

}
