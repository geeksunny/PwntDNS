package com.radicalninja.pwntdns.rest.model;

import com.radicalninja.pwntdns.rest.model.response.DnsItemResponse;
import com.radicalninja.pwntdns.rest.model.response.DnsPaginatedListResponse;
import com.radicalninja.pwntdns.rest.model.response.DnsResponse;

public class Responses {

    public static class DomainsListResponse extends DnsResponse<DnsPaginatedListResponse<DnsDomain>> { }

    public static class CreateDomainResponse extends DnsResponse<DnsItemResponse<DnsDomain>> { }

    public static class GetDomainResponse extends DnsResponse<DnsItemResponse<DnsDomain>> { }

    // TODO: DeleteDomainResponse is HTTP 204

    public static class ResetDomainTokenResponse extends DnsResponse<DnsItemResponse<DnsDomain>> { }

    public static class ZonesListResponse extends DnsResponse<DnsPaginatedListResponse<DnsZone>> { }

    public static class GetZoneResponse extends DnsResponse<DnsItemResponse<DnsZone>> { }

    public static class ZoneRecordsListResponse extends DnsResponse<DnsPaginatedListResponse<DnsZoneRecord>> { }

    public static class CreateZoneRecordResponse extends DnsResponse<DnsItemResponse<DnsZoneRecord>> { }

    public static class GetZoneRecordResponse extends DnsResponse<DnsItemResponse<DnsZoneRecord>> { }

    public static class UpdateZoneRecordResponse extends DnsResponse<DnsItemResponse<DnsZoneRecord>> { }

    // TODO: DeleteZoneRecordResponse is HTTP 204

}
