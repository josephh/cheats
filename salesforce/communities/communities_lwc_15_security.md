# SALESFORCE _ COMMUNITIES - 15 SECURITY
* There are 2 common approaches to security: 'functional' and 'data'.

## Functional security
Traditionally 'profiles' are a common way to control who can do what - such as an administrator role.  Permission sets are more concerned with application features.  Profiles are commonly understood but problematic because of the overhead they incur as the number of different profiles needed grows (and profiles are copied or cloned), along with tweaks and amendments to those profiles (do all cloned profiles need changing when the clone 'parent' is updated?). So Salesforce introduced 'permission sets' as a more granular way of expressing security controls.
### Permission sets
Permission sets can be packaged along with code and even be upgraded automatically.  Permission sets can be assigned after package installation (unlike profiles which can only be assigned on install) and administrators cannot edit permission sets).
### Permission set strategy
1. think features, not roles.  E.g. prefer 'update policy end date' within a permission set over 'policy administrator' role.  This permission is then assigned to appropriate user(s).

## Data security
Some Salesforce features auto enforce field level and object level security (https://developer.salesforce.com/wiki/enforcing_crud_and_fls).  Some code however will require programmatic enforcement of security.  The `Apex Describe` facility can specifically check user profile or permission sets and error or raise exceptions when appropriate.
:warning: It is a common misconception that applying `with sharing` or `inherited sharing` will auto-enforce CRUD security or FLS but it does not!  These keywords SOLELY CONTROL THE OBJECTS RETURNED WHEN QUERYING RECORDS.
### Data security - access control
Object visibility in Salesforce is determined via a combination of the Owner field on records - by default the user that created the record - alongside `sharing rules`.  Sharing rules are set to `Public Read/Write` access by default (confirm this!?).  Custom objects may be set to `Public Read/Write`, `Public Read Only`, `Private`.  
If sharing is set to `Private` user will only be able to see the records they created, or records created by other users below them in the **role hierarchy**, if that is enabled.
