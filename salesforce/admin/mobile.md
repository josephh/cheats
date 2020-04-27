# SALESFORCE _ ADMIN - MOBILE APP
SF mobile app provides the following benefits
* included with every Salesforce license.
* plug-and-play - users download it from the App Store® or Google Play™— and go. It works out of the box with no setup required.
* cross platform - Android / iOS operating
* offline capabilities.
* works seamlessly with the desktop version of Lightning Experience, so users can switch between the two without missing a beat.
* "isn’t just an app. It’s a platform...infinitely customizable" etc etc
## Metadata
Customisation of SF orgs gets stored as Metadata which can then be used to drive apps
## Quick Actions
* create custom actions tailored to your own business processes and use cases.
* Each action has its own unique page layout, so you can limit the fields to just the ones mobile users need.
* You can prepopulate fields on the page layout to save mobile users some time.
### Add a new global action
1. first, provide a custom field for use from a global action
Object Manager > Find custom object > Fields & Relationships > New + Picklist > > provide Field Labe + "Enter values for the picklist..." > set visibility of field
1. back to: Setup > Global actions > New button > pick target object, e.g. Contact (or whichever Object field was added to)
1. customise layout for new custom action
* Layout editor for the global action can be used to control which fields show
* Clicking on the global action in the list opens an editor which includes fields like "Predefined Field Values" > click the 'New' button to
1. Add to Global Publisher Layout to make the new custom global action available in SF mobile app.
* From Setup > publisher Layouts > Edit link (beside 'Global Layout') > click the 'override the predefined actions' link > select 'Mobile & Lightning Actions' in upper part of palette
