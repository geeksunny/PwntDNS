package com.radicalninja.pwntdns;

import com.radicalninja.pwntdns.rest.api.Dnsimple;
import com.radicalninja.pwntdns.rest.model.DnsZoneRecord;
import com.radicalninja.pwntdns.rest.model.Responses;
import com.radicalninja.pwntdns.rest.model.request.DnsUpdateZoneRecordRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DomainRecordUpdater {

    private final Dnsimple dnsimple;
    private final String ipAddress;
    private final Configuration.DomainConfigList domainConfigs;

    public DomainRecordUpdater(final String ipAddress, final Configuration.DomainConfigList domainConfigs) {
        this.ipAddress = ipAddress;
        this.domainConfigs = domainConfigs;
        dnsimple = new Dnsimple(PwntDns.getApiKey(), PwntDns.getUserId());
    }

    /**
     * Start the record update process. All network calls will be called synchronously
     * so the task can run sequentially from start to finish.
     */
    public boolean run() {
        for (final Configuration.DomainConfig domain : domainConfigs) {
            final boolean domainIsReady = verifyDomain(domain.getName());
            if (domainIsReady) {
                reviewRecords(domain);
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

    private void reviewRecords(final Configuration.DomainConfig domainConfig) {
        final Map<Record.Type, Map<String, DnsZoneRecord>> remoteRecordsByType = getRecordsForZone(domainConfig.getName());
        for (final Map.Entry<Record.Type, List<Record>> localRecords : domainConfig.getRecords().entrySet()) {
            final Map<String, DnsZoneRecord> remoteRecords = remoteRecordsByType.get(localRecords.getKey());
            for (final Record localRecord : localRecords.getValue()) {
                final DnsZoneRecord remoteRecord = remoteRecords.get(localRecord.getName());
                // If remoteRecord is null, we need to create the zone record
                // If not, check the IP address on the record.
                // If no match, update the record.
                // TODO: The logic below should be broken up
                if (null != remoteRecord) {
                    // TODO: Logic here should be improved to allow for multiple entries in content.
                    if (!remoteRecord.getContent().equals(ipAddress)) {
                        updateZoneRecord(remoteRecord);
                    }
                }
            }
        }
    }

    private Map<Record.Type, Map<String, DnsZoneRecord>> getRecordsForZone(final String zoneName) {
        final HashMap<Record.Type, Map<String, DnsZoneRecord>> recordsMap = new HashMap<>();
        try {
            final Responses.ZoneRecordsListResponse response = dnsimple.getZoneRecords(zoneName);
            final List<DnsZoneRecord> zoneRecords = response.getResponseBody().getData();
            for (final DnsZoneRecord zoneRecord : zoneRecords) {
                final Record.Type recordType = zoneRecord.getType();
                if (!recordsMap.containsKey(recordType)) {
                    recordsMap.put(recordType, new HashMap<String, DnsZoneRecord>());
                }
                recordsMap.get(recordType).put(zoneRecord.getName(), zoneRecord);
            }
        } catch (NullPointerException e) { }
        return recordsMap;
    }

    private boolean checkRecord(final Record record, final String ipAddress) {
        return false;
    }

    private boolean updateZoneRecord(final DnsZoneRecord dnsZoneRecord) {
        final DnsUpdateZoneRecordRequest request = new DnsUpdateZoneRecordRequest();
        request.setContent(ipAddress);
        final Responses.UpdateZoneRecordResponse response =
                dnsimple.updateZoneRecord(dnsZoneRecord.getName(), dnsZoneRecord.getId(), request);
        return response.isSuccess();
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
