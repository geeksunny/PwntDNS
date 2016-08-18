package com.radicalninja.pwntdns;

import com.radicalninja.pwntdns.rest.api.Dnsimple;
import com.radicalninja.pwntdns.rest.model.DnsZone;
import com.radicalninja.pwntdns.rest.model.DnsZoneRecord;
import com.radicalninja.pwntdns.rest.model.Responses;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DomainRecordUpdater {

    private final Dnsimple dnsimple;
    private final String ipAddress;
    private final Map<String, List<Record>> domainRecordMap;

    public DomainRecordUpdater(final String ipAddress, final Map<String, List<Record>> domainRecordMap) {
        this.ipAddress = ipAddress;
        this.domainRecordMap = domainRecordMap;
        dnsimple = new Dnsimple();
    }

    /**
     * Start the record update process. All network calls will be called synchronously
     * so the task can run sequentially from start to finish.
     */
    public boolean run() {
        for (final Map.Entry<String, List<Record>> domain : domainRecordMap.entrySet()) {
            final boolean domainIsReady = verifyDomain(domain.getKey());
            if (domainIsReady) {
                reviewRecords(domain.getValue());
            }
        }

        return false;
    }

    /**
     * Verify that the given domain exists on the API. If not, it will be created.
     * @param domainName The given domain name to check for.
     * @return True if the domain exists on the API and is ready to be used. False if an error has occurred.
     */
    private boolean verifyDomain(final String domainName) {

        final Responses.GetDomainResponse domainRecord = dnsimple.getDomainRecord(domainName);
        if (null == domainRecord) {
            // TODO: Add logging here for the null response, meaning something is wrong with the response parsing.
            return false;
        }
        return domainRecord.isSuccess() || createDomain(domainName);
    }

    private boolean createDomain(final String domainName) {
        final Responses.CreateDomainResponse response = dnsimple.createDomain(domainName);
        return (null != response && response.isSuccess());
    }

    private void reviewRecords(final String zoneName, final List<Record> localRecords) {
        final List<DnsZoneRecord> remoteRecords = getRecordsForZone(zoneName);
        for (final Record record : records) {

        }
    }

    private List<DnsZoneRecord> getRecordsForZone(final String zoneName) {
        final Responses.ZoneRecordsListResponse response = dnsimple.getZoneRecords(zoneName);
        return (null != response && null != response.getResponseBody())
                ? response.getResponseBody().getData()
                : new ArrayList<DnsZoneRecord>();
    }

    private boolean checkRecord(final Record record, final String ipAddress) {

    }

    /*
     * ~~ Process for each domain in the config.
     * 1. Ask API if the domain exists.
     * 1a. If NO, create domain.
     * 2. Cycle over the list of records in the config file.
     * 3. Check if record exists.
     * 3a. If NO, create the record.
     * 3b. If YES, compare the record's IP address against this machine's address.
     * 3ba. If NOT A MATCH, update the record to be correct.
     */

}
