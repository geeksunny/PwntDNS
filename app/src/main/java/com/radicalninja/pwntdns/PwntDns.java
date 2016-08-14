package com.radicalninja.pwntdns;

import com.radicalninja.pwntdns.rest.api.Ipify;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PwntDns {


    private Ipify ipify;


    public static void main(String[] args) {
        // Start main task
        new PwntDns().startPwning();
    }

    private PwntDns() {
        ipify = new Ipify();
    }

    private void startPwning() {
        System.out.println("Commencing DNS Pwning Operations.");
        ipify.queryIpAddress(ipCallback);
    }

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
