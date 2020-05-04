# SALESFORCE _ COMMUNITIES - LGHTNING COMPONENTS
[Developer ref](https://developer.salesforce.com/docs/component-library/documentation/en/lwc)
Exploits the following technologies
1. Templates - HTML templates are **inert** - not rendered nor part of the DOM (and so not accessible to CSS selectors - only accessible via template API).  Templates can go anywhere on a page, order is not significant.
1. Custom elements
1. Shadow DOM
1. HTML5 Imports
Developers resources also includes a 'playground' where code snippets etc can be tried out (and downloaded) https://developer.salesforce.com/docs/component-library/tools/playground

## Code
A minimal component typically consists of .html, .js, .js-meta.xml, (plus optional css and svg), e.g.s
helloWorld.html
helloWorld.js
helloWorld.html.js-meta.xml
helloWorld.css // requires at least one style
helloWorld.svg // requires an icon or svg element
#### CLI create Lightning web components
ERROR running force:lightning:component:create:  Lightning bundles must have a parent folder named 'lwc'.
```
PC06015:exp jjobbings$ sfdx force:lightning:component:create --type lwc -n HelloWorld -d force-app/main/default/lwc
target dir = /Users/jjobbings/simply/salesforce/exp/force-app/main/default/lwc
   create force-app/main/default/lwc/helloWorld/helloWorld.js
   create force-app/main/default/lwc/helloWorld/helloWorld.html
   create force-app/main/default/lwc/helloWorld/helloWorld.js-meta.xml
```
#### CLI create Aura app (a.k.a 'lightning app')
`sfdx force:lightning:app:create -n lightningHelloWorld -d aura`
The aura app provides a way to use a component - the <c:...> element will incorporate the component, e.g.
```html
<aura:application>
  <h1>H1 inside aura app</h1>
  <br/>
  <c:helloWorld/>
</aura:application>
```
Push code to scratch org. :information: if a newly created component bundle is 'empty' it can't be deployed; to fix this means add some content into the html file created for the new lightning component.
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
Add    auraComponentHelloWorld/auraComponentHelloWorld.auradoc       AuraDefinitionBundle      force-app/main/default/aura/auraComponentHelloWorld/auraComponentHelloWorld.auradoc
Add    auraComponentHelloWorld/auraComponentHelloWorld.cmp           AuraDefinitionBundle      force-app/main/default/aura/auraComponentHelloWorld/auraComponentHelloWorld.cmp
Add    auraComponentHelloWorld/auraComponentHelloWorld.cmp           AuraDefinitionBundle      force-app/main/default/aura/auraComponentHelloWorld/auraComponentHelloWorld.cmp-meta.xml
Add    auraComponentHelloWorld/auraComponentHelloWorld.css           AuraDefinitionBundle      force-app/main/default/aura/auraComponentHelloWorld/auraComponentHelloWorld.css
Add    auraComponentHelloWorld/auraComponentHelloWorld.design        AuraDefinitionBundle      force-app/main/default/aura/auraComponentHelloWorld/auraComponentHelloWorld.design
Add    auraComponentHelloWorld/auraComponentHelloWorld.svg           AuraDefinitionBundle      force-app/main/default/aura/auraComponentHelloWorld/auraComponentHelloWorld.svg
Add    auraComponentHelloWorld/auraComponentHelloWorldController.js  AuraDefinitionBundle      force-app/main/default/aura/auraComponentHelloWorld/auraComponentHelloWorldController.js
Add    auraComponentHelloWorld/auraComponentHelloWorldHelper.js      AuraDefinitionBundle      force-app/main/default/aura/auraComponentHelloWorld/auraComponentHelloWorldHelper.js
Add    auraComponentHelloWorld/auraComponentHelloWorldRenderer.js    AuraDefinitionBundle      force-app/main/default/aura/auraComponentHelloWorld/auraComponentHelloWorldRenderer.js
Add    helloWorld/helloWorld.html                                    LightningComponentBundle  force-app/main/default/lwc/helloWorld/helloWorld.html
Add    helloWorld/helloWorld.js                                      LightningComponentBundle  force-app/main/default/lwc/helloWorld/helloWorld.js
Add    helloWorld/helloWorld.js                                      LightningComponentBundle  force-app/main/default/lwc/helloWorld/helloWorld.js-meta.xml
```
:information_source: One way to get to the developer console from the SF Setup console is to browse with quick search to a custom lightning app: Custom Code > Lightning Components and click on a custom app. Once inspecting the details of the custom app, a 'Developer Console' button is displayed.   
Once code is deployed, use the developer console to browse Aura lightning components (but not lightning web components) 'File > Open Resources' or 'File > Open Lightning Resources'.  From the developer console, click 'preview' beside the app to open the web page
YOu cannot open lightning web components in the developer console in the same way :sob:
