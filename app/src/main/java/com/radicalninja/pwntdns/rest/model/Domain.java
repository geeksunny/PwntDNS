package com.radicalninja.pwntdns.rest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import javax.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Domain {

    public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    @Expose
    private int id;

    @Expose
    @SerializedName("account_id")
    private int accountId;

    @Expose
    @SerializedName("registrant_id")
    @Nullable
    private Integer registrantId;

    @Expose
    private String name;

    @Expose
    @SerializedName("unicode_name")
    private String unicodeName;

    @Expose
    private String token;

    @Expose
    // TODO: enum for this field?
    private String state;

    @Expose
    @SerializedName("auto_renew")
    private boolean autoRenew;

    @Expose
    @SerializedName("private_whois")
    private boolean privateWhois;

    @Expose
    @SerializedName("expires_on")
    @Nullable
    private String expiresOn;

    // TODO: Add in parsing for date/time values below.

    @Expose
    @SerializedName("created_at")
    private String dateCreated;

    @Expose
    @SerializedName("updated_at")
    private String dateLastUpdated;

    public int getId() {
        return id;
    }

    public int getAccountId() {
        return accountId;
    }

    public Integer getRegistrantId() {
        return registrantId;
    }

    public String getName() {
        return name;
    }

    public String getUnicodeName() {
        return unicodeName;
    }

    public String getToken() {
        return token;
    }

    public String getState() {
        return state;
    }

    public boolean isAutoRenew() {
        return autoRenew;
    }

    public boolean isPrivateWhois() {
        return privateWhois;
    }

    @Nullable
    public String getExpiresOn() {
        // TODO: should we add Date object parsing to this string? [2015-12-06]
        return expiresOn;
    }

    @Nullable
    public Date getDateCreated() {
        return parseDateString(dateCreated);
    }

    @Nullable
    public Date getDateLastUpdated() {
        return parseDateString(dateLastUpdated);
    }

    private Date parseDateString(final String dateString) {
        try {
            final SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
            return format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
