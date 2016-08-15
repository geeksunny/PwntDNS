package com.radicalninja.pwntdns.rest.model;

import com.radicalninja.pwntdns.rest.model.response.DnsItemResponse;
import com.radicalninja.pwntdns.rest.model.response.DnsPaginatedListResponse;

public class Responses {

    /*
    curl  -H 'Authorization: Bearer <token>' \
        -H 'Accept: application/json' \
        https://api.dnsimple.com/v2/1010/domains
     */
    public static class DomainsListResponse extends DnsPaginatedListResponse<Domain> { }

    /*
    curl  -H 'Authorization: Bearer <token>' \
      -H 'Accept: application/json' \
      -H 'Content-Type: application/json' \
      -X POST \
      -d '<json>' \
      https://api.dnsimple.com/v2/1010/domains
     */
    public static class CreateDomainResponse extends DnsItemResponse<Domain> { }

    /*
    curl  -H 'Authorization: Bearer <token>' \
      -H 'Accept: application/json' \
      https://api.dnsimple.com/v2/1010/domains/[example.com | 1]
     */
    public static class GetDomainResponse extends DnsItemResponse<Domain> { }

    /*
    curl  -H 'Authorization: Bearer <token>' \
      -H 'Accept: application/json' \
      -H 'Content-Type: application/json' \
      -X DELETE \
      https://api.dnsimple.com/v2/1010/domains/1
     */
    // TODO: DeleteDomainResponse is HTTP 204

    /*
    curl  -H 'Authorization: Bearer <token>' \
      -H 'Accept: application/json' \
      -H 'Content-Type: application/json' \
      -X POST \
      -d '{}' \
      https://api.dnsimple.com/v2/1010/domains/example.com/token
     */
    public static class ResetDomainTokenResponse extends DnsItemResponse<Domain> { }

}
