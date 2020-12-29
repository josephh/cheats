# SALESFORCE _ COMMUNITIES - 14 PACKAGES
* There are 2 types of packages: 'managed' and 'unlocked'.
(Note there are also 2 Salesforce packaging technologies, 1st and 2nd Gen).

'Unlocked' packages also result in the installation of components into another org; however, they can readily be amended or deleted by that org's administrator.

## Release and Beta
'release' packages CAN be installed in subscriber production orgs and providea an upgrade path from previous releases.  Certain changes are restricted, e.g. you cannot delete the previously released components.
'beta' packages CANNOT be installed in subscriber production orgs but can be installed in sandboxes and scratch orgs and CANNOT be upgraded once installed.
### Release first version of a package
> sfdx force:package:version:create --package 'FormulaForce app' --definitionfile config/project-scratch-def.json --wait 10 --installationkeybypass
```sfdx-project.json has been updated.
Successfully created the package version [08c3z0000008OnIAAU]. Subscriber Package Version Id: 04t3z000001gR9MAAU
Package Installation URL: https://login.salesforce.com/packaging/installPackage.apexp?p0=04t3z000001gR9MAAU
```

:point_up: had to change the install link to be able to deploy to a scratch org,
https://test.salesforce.com/packaging/installPackage.apexp?p0=04t3z000001gR9MAAU ...
then having generated a user password, for the scratch org user,  
> sfdx force:user:password:generate --targetusername test-lgwghsrcdq0v@example.com  

...and got the password back out with,
> sfdx force:user:display -u test-lgwghsrcdq0v@example.com


As an alternative, you can use the "sfdx force:package:install" command.
The above command amends the sfdx-project.json so that it looks like,
```
{
    "packageDirectories": [
        {
            "path": "force-app",
            "package": "FormulaForce app",
            "versionName": "ver 0.1",
            "versionNumber": "0.1.0.NEXT",
            "default": true
        }
    ],
    "namespace": "josephh",
    "sfdcLoginUrl": "https://login.salesforce.com",
    "sourceApiVersion": "50.0",
    "packageAliases": {
        "FormulaForce app": "0Ho3z0000008OQACA2",
        "FormulaForce app@0.1.0-1": "04t3z000001gR8YAAU",
        "FormulaForce app@0.1.0-2": "04t3z000001gR9MAAU"
    }
}
```
## Browse what's just been installed

## Promote from Beta
package versions are _by default_ Beta.  To promote them to Release, run
> sfdx force:package:version:promote --package "FormulaForce App@1.0-
1"
## Package ancestry
...valid upgrade path v1.0 to v1.1 to v1.2
> sfdx force:package:version:report --package "FormulaForce app@0.1.0-1" --verbose

`"ancestorId":` gets added to sfdx-package.json by using the id value output from the above 'force:package:version:report' command.  This controls the lineage of new packages on creation.
If a new version of a package does not have valid ancestry info, Salesforce will prevent that  package (without ancestry) from being installed as an upgrade to an app in an org _that does have ancestry_.  To test out a new version of an app built without valid ancestry, you'll have to use a scratch org or uninstall a previous version of the app to be upgraded.  

## 'noancestors' scratch org
> sfdx force:org:create --noancestors

## global keyword
One project can contain multiple dependent packages, with a different package directory folder for each package. Each package can share the same namespace or use another.  By default, code is not visible between packages unless you explicitly mark it as _global_.  To make code accessible only between your own packages (sharing the same namespace) and 'not your customers', use `@namespaceAccessible` annotations rather than `global`.
## How does Salesforce DX help upgrading versions of your app?
Salesforce DX managed packages have built-in upgrade support, to help you avoid accidental breaking changes.  For example, it will prevent you from deleting custom objects of fields that have been previously included in a managed package, or modifying an Apex global class or method

```
Successfully set the password "*p*Mr4]]C5" for user test-lgwghsrcdq0v@example.com.

```
