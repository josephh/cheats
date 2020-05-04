1. install SF cli.  Command is called `sfdx`
To show sfdx commands namespace prefixes  `sfdx force --help`
1. Salesforce extension pack - for VS code - need the equivalent for atom!
Need to be able to,
create lightning web components,
authorize an org
create apex class
create project with manifest, etc...
presumably all these map to `sfdx` commands
e.g.s
`sfdx force:apex:class:create`
`sfdx force:lightning:app:create`
`sfdx force:lightning:component:create`
`sfdx force:lightning:event:create`
`sfdx force:lightning:interface:create`
`sfdx force:lightning:lint`
`sfdx force:lightning:test:create`
`sfdx force:lightning:test:install`
`sfdx force:lightning:test:run`
1. `sfdx force:project:create -n exp -s jltd -x`
-n is project name; -s is namespace; -x is create a manifest (for changeset based development)
1. next need to 'authorize' either an 'org' or a 'dev hub'.  'authorize' in this context means, 'allow connections to the app from an external client'.  We do that here
using sfdx (the CLI) you can do this in a number of ways
`sfdx force:auth:web:login` opens a web browser with login details
once logged in you can query the details of that 'authorised org', :information: note without the verbose you will not see the sfdx authorisation URL e.g.
```
=== Org Description
KEY               VALUE
────────────────  ───────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────
Access Token      00D3z000002NRER!AR4AQNjcS0Y2XbWhXnu6jFSThsFI4Z61UCp.sModpUi09_oBTilKuE2NSoQhIc4aUP9xyeXMmaEAIb4YbRzi4Mzm5LcimOVh
Client Id         PlatformCLI
Connected Status  Connected
Id                00D3z000002NREREA4
Instance Url      https://um1.salesforce.com
Sfdx Auth Url     force://PlatformCLI::5Aep861ikeX7i6YENcP.SSPjIqWpAxMtM6cHw3jMv8f193c6mImTv5XhJfWXSNsNftXxn.Vgp6xMIN2hGuqchjW@um1.salesforce.com
Username          josephh@rocketmail.com  
```
these details can be used in conjunction with other logged-in details (which are written to hidden files in the home directory) to create a URL that can be used by the `sfdx force:auth:sfdxurl:store -f ./auth -s -a exp` - a non-interactive way to login to an org (-f switch denotes the file containing a salesforce URL; -s sets the default user for running operations and -a sets an org alias).
Having logged in successfully, e.g. using `sfdx force:auth:web:login`, logged-in details are written to ~/.sfdx, e.g.
```
}PC06015:.sfdx jjobbings$ cat josephh\@rocketmail.com.json
{
    "orgId": "00D3z000002NREREA4",
    "accessToken": "31ce6b17e5e574eb0f3a532c87fb7bb026525c0a86de97c7ffe7b1687d4dd3a7ea1e3ba91f428f3d8aa405551724517e86fd9bc56b94be61b404cddd372b6c0d1bf05b7a2030418ea65a1ff1178fc59560a4581a14a697c08b84bc20d4dc64bc4ab89ac6f5f2e99c7d5108689c95dafc62e1b5bc819e:1819c7e6953c9a796d34210da42ba28c",
    "refreshToken": "be63aacda668cc49d056664669d2651a18de6c7e6728124d80bf03f619a72f845914cb6565ec95e56b10c1267e09c76481f1727c97dbff500ef8d48d1ac1069e69417b314399adab70237da4344fea2488026ed76c4c04ebd4a54608d5:538297cee2bf8cb62688c4dfc1eb8c86",
    "instanceUrl": "https://um1.salesforce.com",
    "loginUrl": "https://login.salesforce.com",
    "username": "josephh@rocketmail.com",
    "clientId": "PlatformCLI"
```
other commands show what you are connected to, e.g.  `sfdx force:auth:list`.  `sfdx store:auth:logout -a` with the -a switch logs out of all salesforce.
note you can set a default user that is then the user that is used to execute operations against orgs.
In SF itself, enable scratch orgs: Setup > Development > Dev hub...
("Scratch orgs are disposable Salesforce orgs that are used to support development and testing.")
In the dev hub settings, as well as enabling scratch orgs, also enable "Packaging..." and "Einstein"
1.
* Packaging: allows developers to develop and distribute unlocked packages for Salesforce customers. (Unlocked packages are created by developers yet provide admins the flexibility to make changes directly in the production org).
* Packaging also: allows developers to develop and distribute second-generation managed packages for Salesforce partners.
1. Einstein
* Bots etc
Searching for 'active' in object manager should then be used to confirm the status of an 'ActiveScratchOrg'.  Searching for 'scratch' in object manager should then be used to confirm the status of all scratch orgs.
### Scratch Orgs
Scratch orgs are part of SF dev workflow and help in situations such as where there is a shortage of licenses for sandboxes, where new projects are just starting up, where temp test environments are needed, automated testing
* scratch orgs have a 30 day max lifetime - they are auto-deactivated after that and become inaccessible
* scratch orgs can't be created from sandbox - only from a free developer account OR a production environment
#### Scratch org config
Newly created code project scaffolding will include a /config/project-scratch-def.json file, e.g.
```
{
  "orgName": "jjobbings company",
  "edition": "Developer",
  "features": []
}
```
amend the project-scratch-def.json file, for example, add "Communities" to the 'features' array.
create a scratch org from the command line - see below - I had to set 'no namespace' (presumably because of competition with previous project create operations - which created a sfdx-)
```
PC06015:exp jjobbings$ sfdx force:org:create -f config/project-scratch-def.json -a exp -v josephh@rocketmail.com  -d 30
ERROR running force:org:create:  We don’t recognize this namespace: jltd. Did you register it in your Dev Hub org?
PC06015:exp jjobbings$ sfdx force:org:create -f config/project-scratch-def.json -a exp1 -v josephh@rocketmail.com -d 30 -n
Successfully created scratch org: 00D26000000EhC0EAK, username: test-uckkbfalq9ws@example.com
```
once scratch org is created, open it:
```
PC06015:~ jjobbings$ sfdx force:auth:list
=== authenticated orgs
ALIAS  USERNAME                       ORG ID              INSTANCE URL                                                   OAUTH METHOD
─────  ─────────────────────────────  ──────────────────  ─────────────────────────────────────────────────────────────  ────────────
       josephh@rocketmail.com         00D3z000002NREREA4  https://um1.salesforce.com                                     web
exp    test-uckkbfalq9ws@example.com  00D26000000EhC0EAK  https://momentum-business-5741-dev-ed.cs81.my.salesforce.com/  web
```
Query the org statuses and connection statuses via,
```
PC06015:exp jjobbings$ sfdx force:org:list --verbose
=== Orgs
  ALIAS  USERNAME                ORG ID              CONNECTED STATUS
  ─────  ──────────────────────  ──────────────────  ────────────────
         josephh@rocketmail.com  00D3z000002NREREA4  Connected

  ALIAS  SCRATCH ORG NAME   USERNAME                       ORG ID              STATUS  DEV HUB             CREATED DATE                  INSTANCE URL                                           EXPIRATION DATE
  ─────  ─────────────────  ─────────────────────────────  ──────────────────  ──────  ──────────────────  ────────────────────────────  ─────────────────────────────────────────────────────  ───────────────
  exp1   jjobbings company  test-ynotl2emsewb@example.com  00D26000000EhGqEAK  Active  00D3z000002NREREA4  2020-04-29T13:14:53.000+0000  https://flow-ruby-2402-dev-ed.cs81.my.salesforce.com/  2020-05-29
```
```
PC06015:exp jjobbings$ sfdx force:org:display -u exp1
=== Org Description
KEY              VALUE
───────────────  ────────────────────────────────────────────────────────────────────────────────────────────────────────────────
Access Token     00D26000000EhGq!AR4AQOnZT2bDLNyaIBEL_JzeH87nmIpLjRF0NoFz5RoxjRzwyt7DkkCHD2THu5WpLJ8YPTo55cQxcYNS6anCz1GxgcCB8E3J
Alias            exp1
Client Id        PlatformCLI
Created By       josephh@rocketmail.com
Created Date     2020-04-29T13:14:53.000+0000
Dev Hub Id       josephh@rocketmail.com
Edition          Developer
Expiration Date  2020-05-29
Id               00D26000000EhGqEAK
Instance Url     https://flow-ruby-2402-dev-ed.cs81.my.salesforce.com/
Org Name         jjobbings company
Status           Active
Username         test-ynotl2emsewb@example.com
```
#### Import/ export source code
`sfdx force:source:pull`
`sfdx force:source:push`
`sfdx force:mdapi:deploy`
### Create components
`sfdx force:lightning:component:create -n mycomp --type lwc -d lwc`
