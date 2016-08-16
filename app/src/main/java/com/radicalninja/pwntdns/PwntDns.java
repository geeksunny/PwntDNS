package com.radicalninja.pwntdns;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.radicalninja.pwntdns.rest.api.Dnsimple;
import com.radicalninja.pwntdns.rest.api.Ipify;
import com.radicalninja.pwntdns.rest.model.Responses;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class PwntDns {

    private static final String CONFIG_FILE_NAME = "./config.json";

    private static Configuration config;

    private Dnsimple dnsimple;
    private Ipify ipify;


    public static void main(String[] args) {
        // Read in configuration file
        final boolean ready = loadConfig();
        // Start main task
        if (!ready) {
            // TODO: Kill app here
            System.out.println("Error loading configuration! Exiting...");
            return;
        }
        new PwntDns().startPwning();
    }

    private static boolean loadConfig() {
        try {
            final Gson gson = new Gson();
            final FileReader reader = new FileReader(CONFIG_FILE_NAME);
            config = gson.fromJson(reader, Configuration.class);
            return true;
        } catch (FileNotFoundException | JsonIOException | JsonSyntaxException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getApiKey() {
        return config.getApiCredentials().getDnsimpleApiKey();
    }

    public static String getUserId() {
        return config.getApiCredentials().getDnsimpleUserId();
    }

    public static List<Configuration.Record> getUserDnsRecords() {
        return config.getRecords();
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
