[15/10 11:25] Dave Gregory

These are some links, which I sure you have seen, that are relevant to the Benefits (Entitlements) API work.
Link to mock with data.
https://97e625c3-6485-4c07-90a4-b43d5e50055f.mock.pstmn.io/members/149799823/policies/10123456/benefits

Link to postman documentation
https://documenter.getpostman.com/view/2632435/T1DsBcPS

https://documenter.getpostman.com/view/2632435/T1DsBcPS?version=latest#6798e3f5-09ae-4ca5-a2b1-da5e71f62664

Link to component, controller, api interactions diagrams..
https://simplyhealth.atlassian.net/wiki/spaces/SSPSC/pages/235569840/My+Policies+logic

------------------
[08/10 09:37] Helen Woodman

From Tom...
Documentation has been updated to include member service 1.5.0 and claims 1.0.0
https://documenter.getpostman.com/view/2632435/T1DsBcPS
Member service is already in external test (this includes both member and policy endpoints)

Claims is currently available on a mock server if you want it, though we do anticipate having that in external test at some point next week

Health Plan DocumentationSimplyhealth Health Plan APIdocumenter.getpostman.com​[08/10 09:37] Helen Woodman

https://97e625c3-6485-4c07-90a4-b43d5e50055f.mock.pstmn.io

The above is the hostname, you can then add the paths as specified by the documentation

For the mock requests, you can use the following details:
memberId = 149799823
policyId = 10123456
claimId = 12345678

For errors, you can use the error code instead.

E.G GET: https://97e625c3-6485-4c07-90a4-b43d5e50055f.mock.pstmn.io/claims?policyId=500
That will return you a 500 - Internal Server Error response



-------------------
To get an access token
curl --location --request POST 'https://cognito-idp.eu-west-2.amazonaws.com?Version=2015-11-01' \
--header 'Content-Type: application/x-amz-json-1.1' \
--header 'x-amz-target: AWSCognitoIdentityProviderService.InitiateAuth' \
--data-raw '{
	"ClientId": "Talk to Ross about setting up an app client",
	"AuthFlow": "USER_PASSWORD_AUTH",
	"AuthParameters": {
		"USERNAME": "chris@chalke.net",
		"PASSWORD": "Password1"
	}
}'
To call the entitlements end point
curl --location --request GET 'https://test-api.simplyhealth.co.uk/memberservice/v1/members/109368972/policies/18708020/entitlements' \
--header 'Authorization: Bearer access_token_from_previous_call'
8:15
You can then attach the access token to the request to the entitlements endpoint. This https://test-api.simplyhealth.co.uk/memberservice/v1/members/109368972/policies/18708020/entitlements will work with my account above. I've attached a curl request to show this too.
-------------------
[08/09 09:34] Dave Gregory




App Client Name
salesforce-community-app-client

App Client ID
72j31fmhc72h1i4768appaeb2i

App Client Secret
rt53rp4g38da5rds1bpg54aajrrikv73gaupkovbgio1hcptio




Rajiv Kumar new settings for Cognito POC, if you can test these I'll let Tom and co know and existing app client can be reset.


=========== OLD version /benefits
{
    "benefits": [
        {
            "id": "35",
            "name": "Chiropody 75 %",
            "entitlements": [
                {
                    "memberId": "149799823",
                    "limit": 50.0,
                    "used": 0.0,
                    "unit": "CURRENCY",
                    "ratio": 75,
                    "period": {
                        "start": "2020-07-01",
                        "end": "2021-07-01"
                    },
                    "cycle": "CLAIMING_YEAR",
                    "remuneration": "Ratio",
                    "scope": "POLICY_HOLDER"
                },
                {
                    "memberId": "149799824",
                    "limit": 50.0,
                    "used": 0.0,
                    "unit": "CURRENCY",
                    "ratio": 75,
                    "period": {
                        "start": "2020-07-01",
                        "end": "2021-07-01"
                    },
                    "cycle": "CLAIMING_YEAR",
                    "remuneration": "Ratio",
                    "scope": "ADULT_DEPENDANT"
                }
            ],
            "subBenefits": [
                {
                    "id": "52",
                    "name": "Chiropody",
                    "entitlements": [
                        {
                            "memberId": "149799823",
                            "limit": 50.00,
                            "used": 0.00,
                            "unitRate": 0.0,
                            "unit": "CURRENCY",
                            "ratio": 75,
                            "period": {
                                "start": "2020-07-01",
                                "end": "2021-07-01"
                            },
                            "cycle": "CLAIMING_YEAR",
                            "remuneration": "Ratio",
                            "scope": "POLICY_HOLDER"
                        },
                        {
                            "memberId": "149799824",
                            "limit": 50.00,
                            "used": 0.00,
                            "unitRate": 0.0,
                            "unit": "CURRENCY",
                            "ratio": 75,
                            "period": {
                                "start": "2020-07-01",
                                "end": "2021-07-01"
                            },
                            "cycle": "CLAIMING_YEAR",
                            "remuneration": "Ratio",
                            "scope": "ADULT_DEPENDANT"
                        }
                    ]
                }
            ]
        },
        {
            "id": "22",
            "name": "Dental 100 %",
            "entitlements": [
                {
                    "memberId": "149799823",
                    "limit": 120.0,
                    "used": 0.0,
                    "unit": "CURRENCY",
                    "ratio": 100,
                    "period": {
                        "start": "2020-07-01",
                        "end": "2021-07-01"
                    },
                    "cycle": "CLAIMING_YEAR",
                    "remuneration": "Ratio",
                    "scope": "POLICY_HOLDER"
                },
                {
                    "memberId": "149799824",
                    "limit": 120.0,
                    "used": 0.0,
                    "unit": "CURRENCY",
                    "ratio": 100,
                    "period": {
                        "start": "2020-07-01",
                        "end": "2021-07-01"
                    },
                    "cycle": "CLAIMING_YEAR",
                    "remuneration": "Ratio",
                    "scope": "ADULT_DEPENDANT"
                }
            ],
            "subBenefits": [
                {
                    "id": "38",
                    "name": "Dental",
                    "entitlements": [
                        {
                            "memberId": "149799823",
                            "limit": 120.00,
                            "used": 0.00,
                            "unitRate": 0.0,
                            "unit": "CURRENCY",
                            "ratio": 100,
                            "period": {
                                "start": "2020-07-01",
                                "end": "2021-07-01"
                            },
                            "cycle": "CLAIMING_YEAR",
                            "remuneration": "Ratio",
                            "scope": "POLICY_HOLDER"
                        },
                        {
                            "memberId": "149799824",
                            "limit": 120.00,
                            "used": 0.00,
                            "unitRate": 0.0,
                            "unit": "CURRENCY",
                            "ratio": 100,
                            "period": {
                                "start": "2020-07-01",
                                "end": "2021-07-01"
                            },
                            "cycle": "CLAIMING_YEAR",
                            "remuneration": "Ratio",
                            "scope": "ADULT_DEPENDANT"
                        }
                    ],
                    "qualifyingPeriodEndDate": "2017-07-01"
                }
            ]
        }
    ]
}
===========
=========== NEW version /benefits

===========
