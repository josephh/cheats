# SALESFORCE _ COMMUNITIES - DEVELOPMENT
* Create a Developer Org.  This happens automatically following [signup](https://developer.salesforce.com/signup).
* Enable communities in the developer org. **Setup > Settings > Communities > Community Settings**.  (domain name = jltd-developer-edition.um1.force.com)
* Create community (I used blank template)  

## Community Workspaces
On creation of a new community SF opens the 'Workspaces view'.  Workspaces provides various admin features.
* Use 'CMS Connect' to pull external styling into SF.  
* Use 'Administration' to config welcome email template details; authentication etc. providers
* Use 'Builder' to provide the gui.
** add pages
** add components to pages
** set up themes
** export a configured community as a Lightning Community template.
** configure guest user access  

## Pages
'Standard' pages (which provide among other things, grid layout and can receive an '_API Name_  - a unique name that developers can use to navigate between pages - may be specified or auto-generated) or 'object' (for custom objects, which triggers auto-generation of 3 pages: 'detail', 'list' and 'related list')
1. Use __Settings > Navigation__ to configure **menu** contents
1. Add a component to a page, e.g. tile menu and choose a menu - configured in previous step - to show there (e.g. default menu)
1. Publish the community so that a new pages are visible in the navigation  

## Themes
set fonts, colours etc
## Developer
* Get to the browser console from Communities Builder >  Settings > Developer Console : 'IDE in a browser'
* bundle up config / themes / pages from Communities Builder > Settings  

### Create Lightning Components
**Communities Builder >  Settings > Developer Console > File > New > Lightning Component**  
A new Lightning component is made up of
1. Component
1. Controller - does the 'routing'
1. Helper - does the processing.   Typically a lightning web component only talks to 'Apex' to read/ write data.  
1. Style  

#### Component (html)
'aura:' is the custom element namespace that brings in functionality to make execute dynamic, server-side html.  
##### Variables
Swap values in dynamically...  
```html
<aura:component>
    <aura:attribute name="userName" type="String" />
    <!-- dynamic values use curly brace syntax '{}' and values prefixed '!v.' to denote a variable value  -->
    <h2>Hello {!v.userName}</h2>
</aura:component>
```  
##### Handlers
Do work - like initialising a component, reading values into scope...
```html
<aura:component>
    <!-- handler -->
    <!-- name="init" is standard.  anything starting '!c.' means controller - so there should be a corresponding init method in the controller bundled alongside this component. -->
    <aura:handler name="init" value="{!this}" action="{!c.doInit}" />
    <!-- custom event handler -->
    <aura:handler name="helloNameEvent" event="c:helloNameEvent" action="{!c.logSomething}" />
</aura:component>
```
