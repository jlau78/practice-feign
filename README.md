# PracticeFeign Service

Initial practice application to study Feign clients.

Added RestTemplate client to test the difference between Feign and RestTemplate calls to remote services.

## RestTemplate requirements

- The PracticeApplication initialises the RestTemplate bean. This in turn requires the HttpComponentsClientHttpRequestFactory. 
- The HttpComponentsClientHttpRequestFactory requires the org.apache.httpcomponents:httpclient dependency.

## Swagger-ui

**TODO** 
1. Migrate to org.springdoc.springdoc-openapi-ui
2. This is required as io.springfox:springfox-swagger2 and Swagger 3 is not compatible with springboot:2.6.x


## Running the application

### Coingecko API requires TLS v1.2
1. You need to run this application with the `-Dhttps.protocols=TLSv1.2` VM argument as the coingecko API requires v1.2 TLS and the JVM uses an older version by default.




