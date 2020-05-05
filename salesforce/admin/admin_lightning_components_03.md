# SALESFORCE _ ADMIN - 03 LIGHTNING OBJECTS
Custom objects need a custom tab, without one you can’t add a custom object to an app.
## Custom Objects
### New Custom Field
#### Picklist

## Lightning Apps
In Lightning Experience, Lightning apps give your users access to sets of objects, tabs, and other items all in one convenient bundle in the navigation bar.
Lightning apps let you brand your apps with a custom color and logo. You can even include a utility bar and Lightning page tabs in your Lightning app.
### What can go in a Lightning App?
Most standard objects, including Home, the main Chatter feed, Groups, and People
Your org’s custom objects
Visualforce tabs
Lightning component tabs
Canvas apps via Visualforce tabs
Web tabs
### Lightning Experience App Manager
A checkmark in the Visible in Lightning Experience column means that the app is accessible in Lightning Experience via the App Launcher and is fully functional.
* To create a new Lightning app, use setup and quick search for "App Manager", then use the New Lightning App wizard.  

### List Views
From the App Launcher, find and select the required app, pertinent tab, choose the List setup drop down...'new'.  Create a custom list and add filters using the filter dialogue (funnel icon expands and collapses filter dialogue)
### Record editing
You can edit record fields directly from within a list view. Editable cells display a pencil icon when you hover over the cell, while non-editable cells display a lock icon.
### List View Chart
when navigating between the tabs (and their list views) in an app, there are various list views to browse.  To see these, use the top left drop down besides the title of the content in the tab.  E.g. Apps > Sales > Opportunities tab, click the drop down beside "Opportunies | Recently viewed". (Recently viewed list will be empty on initial use of an app!)
### Compact layouts
Compact layouts control which fields your users see in the highlights panel at the top of a record. They also control the fields that appear in the expanded lookup card you see when you hover over a link in record details, and in the details section when you expand an activity in the activity timeline.  
:information_source:
Compact layouts also control how records display in the Salesforce mobile app.  
When you create a custom object, it’s automatically assigned to a system default compact layout, which has only one field on it: the object name.  Configure a compact layout from using the setup > object manager > compact layouts editor > new.  Once a compact layout is created it should be assigned as the default compact layout using 'Edit assignment'.   
### Page Layouts
There are two ways to customize a page in Lightning Experience (these are done with separate tools).
1. You can customize a page’s layout,
1. or customize its contents.

Use page layouts **Setup > Object Manager > _object_ > Page Layouts** to configure the following,
* Control which fields, lists of related records, and custom links users see.
* Customize the order that the fields appear in the page details
* Determine whether fields are visible, read only, or required
* Control which standard and custom buttons appear on records and related lists
* Control which quick actions appear on the page

:information_source: At the moment you need to use the Page Layout Editor (aka page layouts) because Lightning App Builder can't do page layouts yet.  
:information_source: Page Layout Editor is a mix of drag n drop and clicking on invisible wrenches to show edit dialogues etc etc.
Page layouts will not be visible to users until they are assigned to user groups - do this via the Edit Assignment which is shown as an option when initially browsing the page layouts option in Object Manager.
### Custom Buttons and links
:information_source: Visualforce pages are handy for this
#### List button
Appears on a related list on an object record page.
This is typically done by first adding a file to an **app**, then setting that uploaded file to be 'shared' and creating a link to it.  Next from Setup > Object Manager for an object, use **Button, Links, Actions** menu item to create a new button - pasting in the link (created above) for it.  (Logic/ dynamic values can be set for that new button so it can be reused in varying ways, using the SF 'formula' editor).  Buttons are then added to custom page layouts.
####  Detail page link
Appears in the Links section of the record details on an object record page.  Links are added in a similar way to buttons - but referencing a URL rather than a file in SF - then in a custom page layout the link can be found in the **Custom Links** part of the 'palette' and dragged to the custom links section of a page.  Once done, custom links will show in the details section of a record.      
#### Detail page button
Appears in the action menu in the highlights panel of a record page.  Added similar to above - note correction button type should be picked and then found in a custom page layout palette and dragged to the correct page of a page.  
:warning: If you have overridden the default settings of the Salesforce Mobile and Lightning Experience Actions section of a page layout and customized it, the standard and custom buttons in the buttons section aren’t automatically included in the action menu on the page. You must add the buttons to the page layout as actions by dragging them from the Mobile & Lightning Actions category in the palette to the Salesforce Mobile and Lightning Experience Actions section.

### Quick Actions
:information_source: Object-specific actions have automatic relationships to other records and let users quickly create or update records, log calls, send emails, and more, in the context of a particular object.
* Create actions
* Update a Record actions - You can define the fields that are available for update.
* Log a Call actions - let users enter notes about calls, meetings, or other interactions that are related to a specific record.
* Custom actions invoke Lightning components, flows, Visualforce pages, or canvas apps that let users interact with or create records that have a relationship to an object record. (Visualforce pages allow customizations in actions).
* Send Email actions -  available only on cases, give users access to a simplified version of the Case Feed Email action in the Salesforce mobile app. You can use the case-specific Send Email action in Salesforce Classic, Lightning Experience, and the Salesforce mobile app.
Create custom actions using **Object Manager > picking a custom action >
 Buttons, Links, and Actions >  New Action**.
Use page layouts to add the quick action.  :warning: In Salesforce Mobile and Lightning Experience Actions  (rather than SF Classic Publisher) you have to override the defaults.
### Global actions
:information_source: Use global actions to let users log call details, create or update records, or send email - without leaving the page they’re on.
Global actions can be put anywhere actions are supported and are added via the global publisher layout.  They are displayed in the global actions menu in Lightning Experience. Users can access the global actions menu by clicking Global Actions menu icon in the Salesforce header.
If an object page layout isn’t customised with actions, then the actions on those object record pages are inherited from the global publisher layout.  
Create global actions using **Setup > Home tab > find Global Actions in the Quick Find box > New Action**.
Use Publisher Layouts to add the global action > Edit (next to Global Layout) > override global publisher layout (if necessary) > Mobile & Lightning Actions category in the palette > Drag e.g. New Campaign from the palette into the Salesforce Mobile and Lightning Experience Actions section > Save.
