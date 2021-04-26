# SALESFORCE _ COMMUNITIES - 00 SFDX COMMANDS crib sheet

1st free Developer account
josephh@rocketmail.com
March2021
https://um1.lightning.force.com/one
my domain registered : josephh-dev-ed.my.salesforce.com

2nd free Developer account
dorian@namespace.josephh.com
April2015
https://um6.lightning.force.com/
to connect : don't specify an "-r org URL", just do `sfdx force:auth:web:login -a namespace` (then input username and password)

to deploy single files or folders
`sfdx force:source:deploy --sourcepath /PATH/TO/YOUR/COMPONENT --json --loglevel fatal`

to connect to **develop** - as default devhub
`sfdx force:auth:web:login -r https://test.salesforce.com/ -a develop -d`
joseph.jobbings@simplyhealth.co.uk.develop
December2020

to connect to **devcomm1**
`sfdx force:auth:web:login -r https://test.salesforce.com/ -a devcomm1 -s`
joseph.jobbings@simplyhealth.co.uk.corp.devcomm1
February2021

to connect to **devcomm2**
`sfdx force:auth:web:login -r https://test.salesforce.com/ -a devcomm2 -s`
joseph.jobbings@simplyhealth.co.uk.corp.devcomm2
March2021

to connect to **my devhub**
`sfdx force:auth:web:login -r https://test.salesforce.com/ -a exp -s`
josephh@rocketmail.com
March2020

to connect to **testing** - and set as defaultusername
`sfdx force:auth:web:login -r https://test.salesforce.com/ -a testing -s`
joseph.jobbings@simplyhealth.co.uk.testing
April2021

to connect to **joncommdev** - and set as defaultusername
`sfdx force:auth:web:login -r https://simplycorporate--testing.lightning.force.com -a testing -s`
joseph.jobbings@simplyhealth.co.uk.joncommdev
November2020

to connect to **devcomm2**
`sfdx force:auth:web:login -r https://test.salesforce.com/ -a devcomm2 -s`
joseph.jobbings@simplyhealth.co.uk.corp.devcomm2
April2021

to connect to **devcomm3**
`sfdx force:auth:web:login -r https://test.salesforce.com/ -a devcomm3 -s`
joseph.jobbings@simplyhealth.co.uk.corp.devcomm3
???



## Create or work with scratch org
### (assuming devhub exists) Authorize your dev hub
> sfdx force:auth:web:login --setdefaultdevhubusername --setalias exp
### Create a scratch org (based on devhub). (the -s parameter makes the new scratch org the default one to run commands - like source code push - against)
> sfdx force:org:create -s -f config/project-scratch-def.json -a "exp-scratch1"
```
=== Orgs
     ALIAS  USERNAME                ORG ID              CONNECTED STATUS
───  ─────  ──────────────────────  ──────────────────  ────────────────
(D)  exp    josephh@rocketmail.com  00D3z000002NREREA4  Connected
     ALIAS          USERNAME                       ORG ID              STATUS  DEV HUB             CREATED DATE                  INSTANCE URL                                                 EXPIRATION DATE
───  ─────────────  ─────────────────────────────  ──────────────────  ──────  ──────────────────  ────────────────────────────  ───────────────────────────────────────────────────────────  ───────────────
(U)  exp-scratch12  test-3ll2vmhv5bfe@example.com  00D7E0000001cc3UAA  Active  00D3z000002NREREA4  2020-06-03T14:06:53.000+0000  https://ability-agility-6168-dev-ed.cs86.my.salesforce.com/  2020-06-10
```  
### Create a scratch org with debug on
`> sfdx force:org:create -s -f config/project-scratch-def.json -a "exp-scratch1" UserPreferencesUserDebugModePref=true`
### View connections to all orgs
> sfdx force:org:list --verbose --all
### View more details for a specific  org...
> sfdx force:org:display --verbose -u exp
### Authorise the new scratch or with ...sfdxurl:store the (uses the auth url returned from the 'display' command)...
> sfdx force:auth:sfdxurl:store -f  /Users/jjobbings/simply/salesforce/exp/auth -s -a exp-scratch1
```
=== Orgs
     ALIAS         USERNAME                       ORG ID              CONNECTED STATUS
───  ────────────  ─────────────────────────────  ──────────────────  ────────────────
(D)  exp           josephh@rocketmail.com         00D3z000002NREREA4  Connected
(U)  exp-scratch1  test-mo4rm5oyyfdx@example.com  00D7E0000001cbUUAQ  Connected
```
### ...or authorise the org via web browser (no password required)
sfdx force:org:open -u exp-scratch1
### open the default username 'All Communities' SetupNetworksPage'
sfdx force:org:open -p /lightning/setup/SetupNetworks/home
### open the default username 'schema builder Page'
sfdx force:org:open -p /lightning/setup/SchemaBuilder/home

## Develop
### Start the local dev server
> sfdx force:lightning:lwc:start
### logout of a scratch org
> sfdx force:auth:logout -u exp-scratch1
### delete a scratch org
> sfdx force:org:delete -u exp-scratch1 -p

## Communities
### Create a new community
> sfdx force:community:create -n 'feature/DIG-123_entitlements' -t 'Build Your Own' -p plan  
```
=== Create Community Result
NAME                          MESSAGE                           ACTION
────────────────────────────  ────────────────────────────────  ───────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────
feature/DIG-123_entitlements  Your community is being created.  We’re creating your community. Run sfdx force:org:open -p _ui/networks/setup/SetupNetworksPage to view a list of your communities, and to confirm when this community is ready.
```
## Default Users
When logging in and out of salesforce orgs, change the default orgs etc, using commands like,
> sfdx force:alias:set my-scratch-org=test-wvkpnfm5z113@example.com  

The alias can then be used to set the default org, (username can be supplied to the command as well as the org alias)
> sfdx force:config:set defaultusername=my-scratch-org

## Fetch zip file of meta
> `sfdx force:mdapi:retrieve --targetusername develop --retrievetargetdir mdapi --unpackaged mdapi/package.xml --verbose`

## Push directory of meta as source
> `sfdx force:mdapi:deploy --targetusername feature/SAL-300_direct_credit_form --testlevel NoTestRun --deploydir ./mdapi --wait 3`
