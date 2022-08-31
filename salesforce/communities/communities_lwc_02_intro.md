# SALESFORCE _ COMMUNITIES - 02 LIGHTNING WEB COMPONENTS INTRODUCTION
[Developer documentation](https://developer.salesforce.com/docs/component-library/documentation/en/lwc)  
LWC exploits the following technologies
1. Templates - HTML templates are **inert** - not rendered, nor part of the DOM (and so not accessible to CSS selectors - only accessible via template API).  Templates can go anywhere on a page, order is not significant.
1. Custom elements
1. Shadow DOM
1. HTML5 Imports  

Developers resources also includes a ['playground'](https://developer.salesforce.com/docs/component-library/tools/playground) where code snippets etc can be tried out (and subsequently downloaded)

## Code
A minimal component typically consists of .html, .js, .js-meta.xml, (plus optional css and svg) files.  E.g.s,  
helloWorld.html  
helloWorld.js  
helloWorld.html.js-meta.xml  
helloWorld.css // requires at least one style  
helloWorld.svg // requires an icon or svg element  
### CLI
You need an (aura) lightning app (to use components inside of)...
#### Create Aura app (a.k.a 'lightning app')
`sfdx force:lightning:app:create -n lightningHelloWorld -d aura`
Once an (aura) lightning app exists you can reference components inside its html.  Using `<c:...>` elements will incorporate the component, e.g.
```html
<aura:application>
  <h1>H1 inside aura app</h1>
  <br/>
  <c:helloWorld/>
</aura:application>
```
#### Create Lightning web components
`ERROR running force:lightning:component:create:`  
:warning: Lightning web component source code bundles have to go in a folder named 'lwc'.
```
sfdx force:lightning:component:create --type lwc -n HelloWorld -d force-app/main/default/lwc
target dir = /Users/jjobbings/s/salesforce/exp/force-app/main/default/lwc  

   create force-app/main/default/lwc/helloWorld/helloWorld.js
   create force-app/main/default/lwc/helloWorld/helloWorld.html
   create force-app/main/default/lwc/helloWorld/helloWorld.js-meta.xml
```
#### Deploy Lightning app and Lightning web components
Push code to a cratch org.   
:information_source: if a newly created component bundle is 'empty' it can't be deployed; to fix this, add some content into the html template/ file created for the new lightning web component.

```
PC06015:exp jjobbings$ sfdx force:source:push -u exp1
=== Pushed Source
STATE  FULL NAME                                                     TYPE                      PROJECT PATH
─────  ────────────────────────────────────────────────────────────  ────────────────────────  ────────────────────────────────────────────────────────────────────────────────────────
Add    auraAppHelloWorld/auraAppHelloWorld.app                       AuraDefinitionBundle      force-app/main/default/aura/auraAppHelloWorld/auraAppHelloWorld.app
Add    auraAppHelloWorld/auraAppHelloWorld.app                       AuraDefinitionBundle      force-app/main/default/aura/auraAppHelloWorld/auraAppHelloWorld.app-meta.xml
Add    auraAppHelloWorld/auraAppHelloWorld.auradoc                   AuraDefinitionBundle      force-app/main/default/aura/auraAppHelloWorld/auraAppHelloWorld.auradoc
Add    auraAppHelloWorld/auraAppHelloWorld.css                       AuraDefinitionBundle      force-app/main/default/aura/auraAppHelloWorld/auraAppHelloWorld.css
Add    auraAppHelloWorld/auraAppHelloWorld.svg                       AuraDefinitionBundle      force-app/main/default/aura/auraAppHelloWorld/auraAppHelloWorld.svg
Add    auraAppHelloWorld/auraAppHelloWorldController.js              AuraDefinitionBundle      force-app/main/default/aura/auraAppHelloWorld/auraAppHelloWorldController.js
Add    auraAppHelloWorld/auraAppHelloWorldHelper.js                  AuraDefinitionBundle      force-app/main/default/aura/auraAppHelloWorld/auraAppHelloWorldHelper.js
Add    auraAppHelloWorld/auraAppHelloWorldRenderer.js                AuraDefinitionBundle      force-app/main/default/aura/auraAppHelloWorld/auraAppHelloWorldRenderer.js
...
Add    helloWorld/helloWorld.html                                    LightningComponentBundle  force-app/main/default/lwc/helloWorld/helloWorld.html
Add    helloWorld/helloWorld.js                                      LightningComponentBundle  force-app/main/default/lwc/helloWorld/helloWorld.js
Add    helloWorld/helloWorld.js                                      LightningComponentBundle  force-app/main/default/lwc/helloWorld/helloWorld.js-meta.xml
```
#### Has it deployed Ok?
:information_source: Browse to the developer console from the SF Setup console: quick search to a custom lightning app - **Custom Code > Lightning Components and click on a custom app**. Once you have found the details of the custom app, a 'Developer Console' button is displayed (developer console is **not** available for LWCs).   
Inside the developer console, browse 'File > Open Resources' or 'File > Open Lightning Resources'.  From the developer console, click 'preview' beside the app to open the web page
(you cannot open lightning web components in the developer console in the same way :sob:).
