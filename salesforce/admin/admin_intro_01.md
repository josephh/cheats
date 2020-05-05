# SALESFORCE _ ADMIN - 01 OBJECTS, RECORDS & FIELDS
## Data modelling
| RDBMS term | Salesforce |
|-|-|
| Table | Object |
| Column | Field |
| Row/ Record | Record |

## Field types - standard on all SF objects
### Identity
A **15-character, case-sensitive** field that’s automatically generated for every record. You can find a record’s ID in its URL.	An account ID looks like 0015000000Gv7qJ.
### System
**Read-only** fields that provide **information about a record** from the system, like when the record was created or when it was last changed.	CreatedDate, LastModifiedById, and LastModifiedDate.
### Name
All records need names so you can distinguish between them. You can use text names or auto-numbered names that automatically increment every time you create a record.	A contact’s name can be Julie Bean. A support case’s name can be CA-1024.
## Data types
Every field has a data type.
### Checkbox
### Date or DateTime
### Formula

## SF Objects
Objects are info containers but also provide for-free features like automatic page layouts
### standard objects
Standard OOTB. Common business objects like Account, Contact, Lead, and Opportunity are all standard objects.
### custom objects
### external objects
### platform events
### and BigObjects

## Object Relationships
...are a special field type
### Lookup
A lookup relationship essentially links two objects together: can be 1-to-1 or 1-to-many.  **use lookup relationships when objects are only related in some cases.**  Objects in lookup relationships usually work as stand-alone objects and have their own tabs in the user interface.
### Master-detail
The master object controls certain behaviours of the detail object, like who can view the detail’s data.  Deleting a master will cause a cascading delete.  
In a master-detail relationship, the detail object isn't an independent, stand-alone entity.   When creating master-detail relationships, the relationship field is always created on the detail object.
### Hierarchical
...are **only available on the User object**. You can use them for things like creating management chains between users.

## Schema Builder
(In SF setup, search for and click *Schema Builder* in the search box top of web page.  Select items to show and click *Auto-layout*)
* Use schema builder to manage field permissions in the builder (right-click field -> manage field permissions)
* Create objects in the schema builder
