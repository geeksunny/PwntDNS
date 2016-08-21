package com.radicalninja.pwntdns;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.radicalninja.pwntdns.rest.api.Ipify;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class PwntDns {

    private static final String CONFIG_FILE_NAME = "./config.json";

    private static Configuration config;

    private Ipify ipify;


    public static void main(String[] args) {
        //setupDebugProxy();

        // Read in configuration file
        final boolean ready = loadConfig();
        // Start main task
        if (!ready) {
            System.out.println("Error loading configuration! Exiting...");
            return;
        }
        new PwntDns().startPwning();
    }

    private static boolean loadConfig() {
        try {
            final GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(
                    Configuration.DomainConfigList.class, new Configuration.DomainConfigListDeserializer());
            final FileReader reader = new FileReader(CONFIG_FILE_NAME);
            config = gsonBuilder.create().fromJson(reader, Configuration.class);
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

    private PwntDns() {
        ipify = new Ipify();
    }

    private void startPwning() {
        System.out.println("Commencing DNS Pwning Operations.");

        final String ipAddress = ipify.queryIpAddress();
        final DomainRecordUpdater updater = new DomainRecordUpdater(ipAddress, config.getDomainConfigs());
        final boolean updaterWasSuccessful = updater.run();

        System.out.println("Pwning Operation Complete.");
    }

    private static void setupDebugProxy() {
        System.setProperty("http.proxyHost", "127.0.0.1");
        System.setProperty("http.proxyPort", "8888");
        System.setProperty("https.proxyHost", "127.0.0.1");
        System.setProperty("https.proxyPort", "8888");
    }

}
