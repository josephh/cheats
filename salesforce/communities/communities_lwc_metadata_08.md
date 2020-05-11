# SALESFORCE _ COMMUNITIES - 08 LIGHTNING WEB COMPONENTS METADATA
## XML Config - fooComp.js-meta.xml
XML elements used in the SF LWC metadata files - typically sat alongside the respective component, include:
* `apiVersion`
* `description`
* `isExposed` // must be true to be used in Communities
* `masterLabel` // provides a canonical name
* `targets` // are used to specify where this component can be used in Communities
* `targetConfigs` // can be used to further narrow the availability of the component, e.g. to only certain SF objects - like lightning__RecordPage and its specific objects like 'Account', 'Case' etc.
* `targetConfig` // the individual entries under targetConfigs allow details to be specified.  They also provide a way to get values into components.  See example below.

```xml
<!-- fooComp.js-meta.xml -->
<?xml version="1.0" encoding="UTF-8"?>
<LightningComponentBundle xmlns="http://soap.sforce.com/2006/04/metadata">
    <apiVersion>48.0</apiVersion>
    <isExposed>true</isExposed>
    <targets>
        <target>lightning__RecordPage</target>
        <target>lightning__AppPage</target>
        <target>lightning__HomePage</target>
        <target>lightningCommunity__Page</target>
        <target>lightningCommunity__Default</target>
    </targets>
</LightningComponentBundle>
```

```xml
<!-- fooComp.js-meta.xml -->
<?xml version="1.0" encoding="UTF-8"?>
<LightningComponentBundle xmlns="http://soap.sforce.com/2006/04/metadata">
    <apiVersion>48.0</apiVersion>
    <isExposed>true</isExposed>
    <targetConfigs>
      <targetConfig targets="lightning__RecordPage, lightningCommunity__Page">
        <property name="message" type="string"></property>
        <objects>
          <object>Opportunity</object>
          <object>Account</object>
        </objects>
      </targetConfig>
    </targetConfigs>
</LightningComponentBundle>
```
```js
// fooComp.js

import { LightningElement, api } from 'lwc';

export default class FooComp extends LightningElement {
  @api message // this allows the value to be set using the property in the meta
}
```
