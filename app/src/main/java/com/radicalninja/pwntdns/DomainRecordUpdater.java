package com.radicalninja.pwntdns;

import com.radicalninja.pwntdns.rest.RestResponse;
import com.radicalninja.pwntdns.rest.api.Dnsimple;
import com.radicalninja.pwntdns.rest.model.DnsZoneRecord;
import com.radicalninja.pwntdns.rest.model.Responses;
import com.radicalninja.pwntdns.rest.model.request.DnsCreateZoneRecordRequest;
import com.radicalninja.pwntdns.rest.model.request.DnsUpdateZoneRecordRequest;
import com.radicalninja.pwntdns.rest.model.response.DnsErrorResponse;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DomainRecordUpdater {

    private enum Result {
        PASS,
        FAIL,
        ERROR
    }

    private final Dnsimple dnsimple;
    private final String ipAddress;
    private final Configuration.DomainConfigList domainConfigs;

    public DomainRecordUpdater(@Nonnull final String ipAddress,
                               @Nonnull final Configuration.DomainConfigList domainConfigs) throws NullPointerException {
        if (null == domainConfigs) {
            throw new NullPointerException("DomainConfgs must not be null.");
        } else if (StringUtils.isEmpty(ipAddress)) {
            throw new NullPointerException("IP Address must not be empty.");
        }
        this.ipAddress = ipAddress;
        this.domainConfigs = domainConfigs;
        dnsimple = new Dnsimple(PwntDns.getApiKey(), PwntDns.getUserId());
    }

    /**
     * Start the record update process. All network calls will be called synchronously
     * so the task can run sequentially from start to finish.
     */
    public boolean run() {
//        boolean encounteredErrors = false;
        for (final Configuration.DomainConfig domain : domainConfigs) {
            final Result domainIsReady = verifyDomain(domain.getName());
            if (domainIsReady.equals(Result.PASS)) {
                reviewRecords(domain);
            } else {
                // TODO: Add logging here for FAIL and ERROR results.
            }
        }

//        return encounteredErrors;
        return true;
    }

    /**
     * Verify that the given domain exists on the API. If not, it will be created.
     * @param domainName The given domain name to check for.
     * @return TODO UPDATE–––True if the domain exists on the API and is ready to be used. False if an error has occurred.
     */
    private Result verifyDomain(final String domainName) {
        final RestResponse<Responses.GetDomainResponse, DnsErrorResponse> domainRecord = dnsimple.getDomainRecord(domainName);
        return Result.FAIL;
//        if (null == domainRecord) {
//            // TODO: Add logging here for the null response, meaning something is wrong with the response parsing.
//            return Result.ERROR;
//        }
//        return (domainRecord.isSuccess() || createDomain(domainName)) ? Result.PASS : Result.FAIL;
    }

    private boolean createDomain(final String domainName) {
        final RestResponse<Responses.CreateDomainResponse, DnsErrorResponse> response = dnsimple.createDomain(domainName);
        // TODO: Add logging here for failure to create a new domain record.
        return (null != response && response.isSuccess());
    }

    private void reviewRecords(final Configuration.DomainConfig domainConfig) {
        final Map<Record.Type, Map<String, DnsZoneRecord>> remoteRecordsByType = getRecordsForZone(domainConfig.getName());
        for (final Map.Entry<Record.Type, List<Record>> localRecords : domainConfig.getRecords().entrySet()) {
            final Map<String, DnsZoneRecord> remoteRecords = remoteRecordsByType.get(localRecords.getKey());
            for (final Record localRecord : localRecords.getValue()) {
                DnsZoneRecord remoteRecord = null;
                if (null != remoteRecords) {
                    remoteRecord = remoteRecords.get(localRecord.getName());
                }
                if (null == remoteRecord) {
                    final Result wasCreated = createZoneRecord(domainConfig.getName(), localRecord, localRecords.getKey());
                    // TODO: Logging result of zone creation
                } else {
                    final Result checkResult = checkRecord(localRecord, remoteRecord);
                    if (checkResult.equals(Result.FAIL)) {
                        final Result wasUpdated = updateZoneRecord(remoteRecord);
                        // TODO: Log failure
                    }
                    // TODO: Logging–"Zone record is up to date"
                }
            }
        }
    }

    private Map<Record.Type, Map<String, DnsZoneRecord>> getRecordsForZone(final String zoneName) {
        final HashMap<Record.Type, Map<String, DnsZoneRecord>> recordsMap = new HashMap<>();
        try {
            final RestResponse<Responses.ZoneRecordsListResponse, DnsErrorResponse> response = dnsimple.getZoneRecords(zoneName);
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

    /**
     * Check if the local and remote records do not have a matching IP address.
     * @param localRecord
     * @param remoteRecord
     * @return {@code Result.PASS} if the records have matching IP addresses, {@code Result.FAIL} if not,
     *          or {@code Result.ERROR} if either record is null.
     */
    private Result checkRecord(final Record localRecord, final DnsZoneRecord remoteRecord) {

        if (null == localRecord || null == remoteRecord) {
            return Result.ERROR;
        }
        return (ipAddress.equals(remoteRecord.getContent())) ? Result.PASS : Result.FAIL;
    }

    /**
     * Returns {@code Result.PASS} if the requested zone record is successfully created.
     * @param zoneName
     * @param localRecord
     * @return
     */
    private Result createZoneRecord(final String zoneName, final Record localRecord, final Record.Type recordType) {
        final DnsCreateZoneRecordRequest request = new DnsCreateZoneRecordRequest(localRecord.getName(), recordType, ipAddress);
        if (null != localRecord.getPriority()) {
            request.setPriority(localRecord.getPriority());
        }
        if (null != localRecord.getTtl()) {
            request.setTtl(localRecord.getTtl());
        }
        final RestResponse<Responses.CreateZoneRecordResponse, DnsErrorResponse> response = dnsimple.createZoneRecord(zoneName, request);
        if (null == response) {
            return Result.ERROR;
        }
        return response.isSuccess() ? Result.PASS : Result.FAIL;
    }

    /**
     * Update the given remote zone record to point at our IP address.
     * @param dnsZoneRecord
     * @return
     */
    private Result updateZoneRecord(final DnsZoneRecord dnsZoneRecord) {
        final DnsUpdateZoneRecordRequest request = new DnsUpdateZoneRecordRequest();
        request.setContent(ipAddress);
        final RestResponse<Responses.UpdateZoneRecordResponse, DnsErrorResponse> response =
                dnsimple.updateZoneRecord(dnsZoneRecord.getName(), dnsZoneRecord.getId(), request);
        if (null == response) {
            return Result.ERROR;
        }
        return response.isSuccess() ? Result.PASS : Result.FAIL;
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
