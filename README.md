# PwntDNS

Because DynDns is for suckers.

PwntDNS is a program written in Java to keep your domain names directed to a server hosted on a DHCP leased IP address.
It uses [Dnsimple](https://dnsimple.com/) as the back-end service for DNS management. It was written to provide a
cheaper alternative to services offered by [DynDns](http://dyn.com/).

The functionality is very basic. It will keep your domain records pointed to your IP address and that is it. If the domain
or record does not exist, it will be created.

## Configuration

To set up, rename `config.json.example` to `config.json`. Replace the dummy API values with your Dnsimple API credentials.

Refer to the sample below for configuring your domain records. Records are grouped on their record type.
The values for `ttl` and `priority` are Integers. Omitting these values will use the server's default values.

    {
      "api": {
        "api_key": "abcdefg1234567",
        "user_id": "12345"
      },
      "domains": {
        "example.com": {
          "A": [
            {
              "name": "",
              "ttl": 86400,
              "priority": null
            },
            {
              "name": "www"
            }
          ],
          "CNAME": [
            {
              "name": "sample"
            }
          ]
        },
        "sample.net": [
          "A": [
            {
              "name": "",
            },
            {
              "name": "www"
            }
          ]
        ]
      }
    }

## Use

While you can execute this program at any time, it works best if scheduled as a repeating task. For best results,
its recommended to run once every 24 hours.

## Requirements

* An account with [Dnsimple](https://dnsimple.com/)
* Java 1.7+

## Libraries Used

* [OkHttp](http://square.github.io/okhttp/)
* [Retrofit](http://square.github.io/retrofit/)
* [Gson](https://github.com/google/gson)
* [FindBugs](http://findbugs.sourceforge.net/)