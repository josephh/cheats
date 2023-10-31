# Fluent

## Modules
Modules, including Availability, Inventory, are accessible by Web Apps, which are web-based interfaces and can be extended via SDK.

##APIs

```
export FLUENT_USERNAME="bquk_admin"
printenv
export FLUENT_PASSWORD="xVNWDJ0riDa9zp"
export FLUENT_CLIENT_ID="SITBQUK"
export FLUENT_CLIENT_SECRET="e10bfd07-9c05-4d2b-940e-16ae67a87852"
```

Note - for dev projects (fluent mediator, availability mediator etc) Docker images are created and deployed to the CI/CD environment.  Then containers run-up from those images and reachable in CI/CD-land under their docker image hostname, e.g. http://mock-fluent:8080/... However, running locally via docker compose, each mock is run in its their own container (for the time-being at least), with its http listening port mapped to a port on the target.  E.g. 8080 in docker container (for fluent mock) -> 9060 on the host O/S.    
## auth
**[POST]    /oauth/token?{API Credentials}**    // Authentication Endpoint  
To hit the authentication endpoint with the provided username, password, client ID & client secret.
```
<root_url>/oauth/token?<API Credentials>
API Credentials =
 * key (This API key is to be used in the Store Locator and other Javascript widgets)
 * username
 * password
 * client_id
 * client_secret
 * grant_type  
```
Example  
```
curl --location --request POST 'http://localhost:9060/oauth/token?username=fluent-api&password=fluent-staging&scope=api&client_id=fluent-api&client_secret=ca5ce9a8-2f2e-4b4a-b8da-767f79fc81a9&grant_type=password'

# SIT
curl --location --request POST 'https://sitbquk.sandbox.api.fluentretail.com/oauth/token?username=bquk_admin&password=xVNWDJ0riDa9zp&scope=api&client_id=SITBQUK&client_secret=e10bfd07-9c05-4d2b-940e-16ae67a87852&grant_type=password'

# UAT
curl --location --request POST 'https://uatbquk.sandbox.api.fluentretail.com/oauth/token?username=bquk_admin&password=rKNcYH7riTv3ga&scope=api&client_id=UATBQUK&client_secret=6847fca9-d037-44ac-baad-5270612f0337&grant_type=password'

curl --location --request POST 'https://sitbquk.sandbox.api.fluentretail.com/graphql/token?username=bquk_admin&password=xVNWDJ0riDa9zp&scope=api&client_id=SITBQUK&client_secret=e10bfd07-9c05-4d2b-940e-16ae67a87852&grant_type=password' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer xxxxx' \
--header 'bearer: {{access_token}}' \


curl 'https://sitbquk.sandbox.api.fluentretail.com/api/v4.1/event?eventType=ORCHESTRATION_AUDIT' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer 67d79a07-9da2-435b-b511-32704046187a'


```
## graphQL
```
curl --location --request POST 'http://localhost:9060/graphql' \
  --header 'Content-Type: application/json' \
  --header 'Authorization: Bearer xxxxx' \
  --data-raw '{"query":"mutation createPlan(\r\n  $ref: String!\r\n  $type: String!\r\n  $retailerId: Int!\r\n  $orderType: String\r\n  $locationRef: String\r\n  $trackingCode: String\r\n  $address: CreateFulfilmentOptionAddressInput\r\n  $products: [CreateFulfilmentOptionProductInput]\r\n) {\r\n  createFulfilmentOption(\r\n    input: {\r\n      ref: $ref\r\n      type: $type\r\n      retailerId: $retailerId\r\n      orderType: $orderType\r\n      locationRef: $locationRef\r\n      trackingCode: $trackingCode\r\n      address: $address\r\n      products: $products\r\n    }\r\n    executionMode: AWAIT_ORCHESTRATION\r\n  ) {\r\n    id\r\n    ref\r\n    retailerId\r\n    orderType\r\n    status\r\n    attributes {\r\n      name\r\n      value\r\n      type\r\n    }\r\n    plans {\r\n      edges {\r\n        node {\r\n          ref\r\n          eta\r\n          type\r\n          status\r\n          fulfilments {\r\n            fulfilmentType\r\n            locationRef\r\n            items {\r\n              productRef\r\n              availableQuantity\r\n              requestedQuantity\r\n            }\r\n          }\r\n          splitCount\r\n        }\r\n      }\r\n    }\r\n  }\r\n}\r\n","variables":{"ref":"Test-ref-9f78cf12-75c9-42a0-96db-3f5db34e70bf","type":"ALL-WITH-PROXIMITY","retailerId":1,"orderType":"HD","locationRef":"1360","products":[{"productRef":"5060169655164","catalogueRef":"DEFAULT:MASTER:1","requestedQuantity":1}]}}'
```
