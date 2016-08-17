package com.radicalninja.pwntdns.rest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Nullable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DnsZone {

    public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    @Expose
    private int id;

    @Expose
    @SerializedName("account_id")
    private int accountId;

    @Expose
    private String name;

    @Expose
    private boolean reverse;

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

    public String getName() {
        return name;
    }

    public boolean isReverse() {
        return reverse;
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
