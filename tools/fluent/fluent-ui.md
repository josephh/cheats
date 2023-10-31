# Fluent UI module
## Components
* Foundation Components, top level - map to specific URLs - such as Page Components which form the base of a screen.  Page components are the primary foundation component and mobile responsive.
* Content Components for displaying data and media, 'simple card' etc
* Layout Components for structuring pages
* Interaction Components for capturing user input
* Field components CANNOT be configured via manifest, essentially form-based (text box etc) - auto rendered based on the data type to be input/ captured.
* Template helpers - utility functions e.g. for template strings in manifest doc

### Manifest doc
...describes structure and layout of the app, incl navigation and pages.
* Manifest doc is ACCOUNT level setting. e.g. `fc.mystique.manifest.joewebapp.json
* `name` in the manifest determines the URL path to access Fluent, e.g. https://[ACCOUNT].sandbox.apps.fluentcommerce.com/mywebapp
* manifest also determines the context of the web app : either retailer or location
* manifest delivers "role based visibility"  i.e. web app screens can be client specific
* manifest defines data to go to graphql queries

### OMX custom web app
* Create the app via a manifest definition at ACCOUNT level setting. e.g. `fc.mystique.manifest.joewebapp.json
* then once deployed URL will be https://[ACCOUNT].sandbox.apps.fluentcommerce.com/joewebapp
* web app manifest should define
  * name
  * title
  * context (e.g. `{"context": {"level": "location"}}` ) <<<<< i.e. scoped to a specific store
  * set of Routes[] - URL paths, that can be 1. page routes (direct link to page), 2. section routes (navigation grouping header, basically a nav menu item) 3. reference route (ref to another manfest, called a fragment - to split and manage large manifests in separate docs)

### Manifest fragment
Main manifest can set "homePath" - the default page to load on login

### Components and Datasource
1. `data` object on the page route takes a `query` and `variables` object for a GraphQL query.
1. certain context values are available inside the manifest e.g. `activeLocation` with props available on that e.g. `{{activeLocation.ref}}` note double braces.  Then specify a configured data item and within that the query 'nodes' i.e. the elements in the query response to interrogate and display, {{node.status}} etc.

## Add Page
in the manifest add an entry - "type": "page", "route": "homepage/customer/:id" (note colon to denote path parameter), "component": "fc.page".  Use double curly braces in links to use data values in the output, e.g. "link": "#/customer/{{node.id}}"
## Translation

## Custom components
E.g.s 'views' and 'fields' interact with Fluent OMX functionality via webhooks written in React.js: useAuth, useEnv, useSettings, useQuery etc. https://lingo.fluentcommerce.com/extend/component-sdk/hooks/ 
## Utility components
Component 'primitives' such as "card", "children", "Dynamic value", "Loading" https://lingo.fluentcommerce.com/extend/component-sdk/utility-components/
## Registries
before a custom component can be used it has to be registered with one of the following (https://lingo.fluentcommerce.com/extend/component-sdk/registries/)
1. Component registry
1. Field registry
1. Template registry
### Attach local development/ UI customisation to Fluent instance
Use the Fluent admin console to edit the json settings file, e.g.
https://fctrainau1233.sandbox.console.fluentretail.com/#/settings/90.   
edit the file FC.MYSTIQUE.MANIFEST.STORE
```json
"plugins": [
  {
      "type": "url",
      "src": "http://localhost:3001"
  },
```
