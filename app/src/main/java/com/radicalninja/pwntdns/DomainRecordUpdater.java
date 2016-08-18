package com.radicalninja.pwntdns;

import com.radicalninja.pwntdns.rest.api.Dnsimple;
import com.radicalninja.pwntdns.rest.model.Responses;

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
        for (final String domain : domainRecordMap.keySet()) {
            final boolean domainIsReady = verifyDomain(domain);
        }

        return false;
    }

    /**
     * Verify that the given domain exists on the API. If not, it will be created.
     * @param domainName The given domain name to check for.
     * @return True if the domain exists on the API and is ready to be used. False if an error has occurred.
     */
    private boolean verifyDomain(final String domainName) {

        // TODO: Check domain here
        final Responses.GetDomainResponse domainRecord = dnsimple.getDomainRecord(domainName);
        // TODO: Add in calls for creating domain if it does not exist
        return (null != domainRecord) && (domainRecord.isSuccess());
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
