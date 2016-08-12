package com.radicalninja.pwntdns.rest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sun.istack.internal.Nullable;

public class Domain {

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

}
