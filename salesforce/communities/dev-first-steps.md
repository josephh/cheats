# SALESFORCE COMMUNITIES - DEVELOPMENT GETTING STARTED
1. SF cli installs `sfdx` command.  (Use it to create lightning web components;
authorize an org; create apex classes; create project with manifest etc).
 1. Show sfdx command namespaces with  
 > `sfdx force --help`
 1. Create project with manifest (package.xml)  
 > `sfdx force:project:create -n exp -s jltd -x` (`-n` is project name; `-s` is namespace; `-x` is to create a manifest (for changeset based development)).  
1. Set a default user that is then used - in the absence of overriding the user - to execute operations against orgs.
1. 'Authorize' a SF org or a dev hub.  'Authorize' in this context means, "allow connections to the app from an external client".  This can be done through various means, including pointing to a file that contains an auth url (e.g.  `sfdx force:auth:sfdxurl:store -f ./auth -s -a exp`: a non-interactive way to login to an org).
Once authorised, auth details can be queried using the cli,
> `sfdx force:org:display -u exp1 --verbose`  
Using specified username test-ynotl2emsewb@example.com  
=== Org Description  
KEY VALUE
───────────────  ─────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────
Access Token     00D26000000EhGq!AR4AQCpsr5etp96LdPLO76vdOPluNEdU2qOmQBeO3sWCDDgmOakQFGzCmPQ1TntzQtGlHpOhAKMzJqtix3G6d6oHbw8kWulv  
Alias            exp1  
Client Id        PlatformCLI  
Created By       josephh@rocketmail.com  
Created Date     2020-04-29T13:14:53.000+0000  
Dev Hub Id       josephh@rocketmail.com  
Edition          Developer  
Expiration Date  2020-05-29  
Id               00D26000000EhGqEAK  
Instance Url     https://flow-ruby-2402-dev-ed.cs81.my.salesforce.com  
Org Name         jjobbings company  
Sfdx Auth Url    force://PlatformCLI::5Aep861g6cwPKmRDWbAQIVN9XtITPuXPHD2nJCPv.NahoSkNWkkCQcv_EEBDT4.bF8UM9cR5lOWynrV3.XZ8DyZ@flow-ruby-2402-dev-ed.cs81.my.salesforce.com  
Status           Active  
Username         test-ynotl2emsewb@example.com  
1. Other sfdx commands include showing what you are connected to,
> `sfdx force:auth:list`  
`sfdx store:auth:logout -a` (logs out of all salesforce instances).
1.  Enable scratch orgs via the SF gui:  Setup > Development > Dev hub...
("Scratch orgs are disposable Salesforce orgs that are used to support development and testing.").  In the dev hub settings, as well as enabling scratch orgs, also enable "Packaging..." and "Einstein".  (Searching for 'active' in object manager can be used to confirm the status of an 'ActiveScratchOrg'.  Searching for 'scratch' in object manager will confirm the status of all scratch orgs.)
 1. 'Packaging' allows developers to develop and distribute unlocked packages for Salesforce customers. (Unlocked packages are created by developers yet provide admins the flexibility to make changes directly in the production org).   Packaging also allows developers to develop and distribute second-generation managed packages for Salesforce partners.
 1. 'Einstein' is a way to provide bots etc.  

### Scratch Orgs
Scratch orgs are part of SF dev workflow and help in situations such as where there is a shortage of licenses for sandboxes, where new projects are just starting up and where temporary test environments are needed, such as for automated testing. (Enabled community for default scratch org: sandbox-flow-ruby-2402-dev-ed-171c613730b.cs81.force.com)
* scratch orgs have a **30 day max** lifetime - they are auto-deactivated after that and become inaccessible.
* scratch orgs can't be created from sandbox - only from a free developer account OR a production environment.

#### Scratch org config
Newly created code project scaffolding will include a **config/project-scratch-def.json** file, e.g.
```
{
  "orgName": "jjobbings company",
  "edition": "Developer",
  "features": []
}
```
amend the project-scratch-def.json file, for example, add "Communities" to the 'features' array.  
1. Create a scratch org from the command line I had to set 'no namespace' (presumably because of competition with previous project create operations (?)))
>`sfdx force:org:create -f config/project-scratch-def.json -a exp -v josephh@rocketmail.com  -d 30`  
ERROR running force:org:create:  We don’t recognize this namespace: jltd. Did you register it in your Dev Hub org?  
`sfdx force:org:create -f config/project-scratch-def.json -a exp1 -v josephh@rocketmail.com -d 30 -n`  
Successfully created scratch org: 00D26000000EhC0EAK, username: test-uckkbfalq9ws@example.com
1. Once scratch org is created, open it
1. Query the org statuses and connection statuses via,
> `sfdx force:org:list --verbose`
=== Orgs
  ALIAS  USERNAME                ORG ID              CONNECTED STATUS
  ─────  ──────────────────────  ──────────────────  ────────────────
         josephh@rocketmail.com  00D3z000002NREREA4  Connected

  ALIAS  SCRATCH ORG NAME   USERNAME                       ORG ID              STATUS  DEV HUB             CREATED DATE                  INSTANCE URL                                           EXPIRATION DATE
  ─────  ─────────────────  ─────────────────────────────  ──────────────────  ──────  ──────────────────  ────────────────────────────  ─────────────────────────────────────────────────────  ───────────────
  exp1   jjobbings company  test-ynotl2emsewb@example.com  00D26000000EhGqEAK  Active  00D3z000002NREREA4  2020-04-29T13:14:53.000+0000  https://flow-ruby-2402-dev-ed.cs81.my.salesforce.com/  2020-05-29
1. Import/ export source code
> `sfdx force:source:pull`  
`sfdx force:source:push`  
`sfdx force:mdapi:deploy`

### Create components
1. `sfdx force:lightning:component:create -n mycomp --type lwc -d lwc`  note directory is important.
