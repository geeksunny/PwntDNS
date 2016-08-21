package com.radicalninja.pwntdns.rest.model;

import com.radicalninja.pwntdns.rest.model.response.DnsItemResponse;
import com.radicalninja.pwntdns.rest.model.response.DnsPaginatedListResponse;

public class Responses {

    public static class DomainsListResponse extends DnsPaginatedListResponse<DnsDomain> { }

    public static class CreateDomainResponse extends DnsItemResponse<DnsDomain> { }

    public static class GetDomainResponse extends DnsItemResponse<DnsDomain> { }

    // TODO: DeleteDomainResponse is HTTP 204

    public static class ResetDomainTokenResponse extends DnsItemResponse<DnsDomain> { }

    public static class ZonesListResponse extends DnsPaginatedListResponse<DnsZone> { }

    public static class GetZoneResponse extends DnsItemResponse<DnsZone> { }

    public static class ZoneRecordsListResponse extends DnsPaginatedListResponse<DnsZoneRecord> { }

    public static class CreateZoneRecordResponse extends DnsItemResponse<DnsZoneRecord> { }

    public static class GetZoneRecordResponse extends DnsItemResponse<DnsZoneRecord> { }

    public static class UpdateZoneRecordResponse extends DnsItemResponse<DnsZoneRecord> { }

    // TODO: DeleteZoneRecordResponse is HTTP 204

}
