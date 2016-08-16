package com.radicalninja.pwntdns;

import com.radicalninja.pwntdns.rest.api.Dnsimple;
import com.radicalninja.pwntdns.rest.api.Ipify;
import com.radicalninja.pwntdns.rest.model.Responses;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PwntDns {

    private Dnsimple dnsimple;
    private Ipify ipify;


    public static void main(String[] args) {
        // Start main task
        new PwntDns().startPwning();
    }

    private PwntDns() {
        ipify = new Ipify();
        dnsimple = new Dnsimple();
    }

    private void startPwning() {
        System.out.println("Commencing DNS Pwning Operations.");
//        ipify.queryIpAddress(ipCallback);
        dnsimple.getDomainsList(dnsCallback);
    }

    private Callback<Responses.DomainsListResponse> dnsCallback = new Callback<Responses.DomainsListResponse>() {
        @Override
        public void onResponse(Call<Responses.DomainsListResponse> call, Response<Responses.DomainsListResponse> response) {
            System.out.printf("DNS List Response: %s\n", response.body().getData().toString());
            donePwning();
        }

        @Override
        public void onFailure(Call<Responses.DomainsListResponse> call, Throwable t) {
            System.out.printf("ipCallback.onFailure! (%s, %s)\n", call.toString(), t.toString());
            donePwning();
        }
    };

    private Callback<String> ipCallback = new Callback<String>() {
        @Override
        public void onResponse(Call<String> call, Response<String> response) {
            System.out.printf("IP address: %s\n", response.body());
            donePwning();
        }

        @Override
        public void onFailure(Call<String> call, Throwable t) {
            System.out.printf("ipCallback.onFailure! (%s, %s)\n", call.toString(), t.toString());
            donePwning();
        }
    };

    private void donePwning() {
        System.out.println("Pwning Operation Complete.");
    }

}
