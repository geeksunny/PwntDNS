package com.radicalninja.pwntdns.rest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.radicalninja.pwntdns.Record;

import javax.annotation.Nullable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DnsZoneRecord {

    public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    @Expose
    private int id;

    @Expose
    @SerializedName("zone_id")
    private String zoneId;

    @Expose
    @SerializedName("parent_id")
    private Integer parentId;

    @Expose
    private String name;

    @Expose
    private String content;

    @Expose
    private int ttl;

    @Expose
    private Integer priority;

    @Expose
    private Record.Type type;

    @Expose
    @SerializedName("system_record")
    private boolean systemRecord;

    @Expose
    @SerializedName("created_at")
    private String dateCreated;

    @Expose
    @SerializedName("updated_at")
    private String dateLastUpdated;

    public int getId() {
        return id;
    }

    public String getZoneId() {
        return zoneId;
    }

    @Nullable
    public Integer getParentId() {
        return parentId;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public int getTtl() {
        return ttl;
    }

    @Nullable
    public Integer getPriority() {
        return priority;
    }

    public Record.Type getType() {
        return type;
    }

    public boolean isSystemRecord() {
        return systemRecord;
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
