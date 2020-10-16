# SALESFORCE _ COMMUNITIES - 08 LIGHTNING WEB COMPONENTS EVENTS
## Browser Events
How to act on general changes or interaction with the browser?  Key presses, mouse move, page load etc
```html
<!-- browserEvent.html -->
<template>
  <div>
    <lightning-button label="On Click Event" onclick={handleEvent}>
    </lightning-button>
  </div>
</template>
```
```js
// browserEvent.js

import { LightningElement } from 'lwc';

export default class BrowserEvent extends LightningElement {

  handleEvent(e) {
   alert('handled event from browser with label, ' + e.target.label)
  }
}
```
## Simple Events
Create an event in a child component, to be handled by a parent component.
```html
<!-- childDemo.html -->
<template>
  <div>
    <lightning-button label="Create Event" onclick={handleEvent}>
    </lightning-button>
  </div>
</template>
```
```js
// childDemo.js
import { LightningElement, api } from 'lwc';

export default class ChildDemo extends LightningElement {

  handleEvent() {
    const e = new CustomEvent('simple')
    this.dispatchEvent(e)
  }
}
```
```html
<!-- parentDemo.html -->
<template>
  <div class="slds-m-around_large">
    <strong>Parent component</strong>
  </div>
  <div class="slds-m-around_small">
    <c-child-demo onsimple={handleSimpleEvent}> </c-child-demo>
  </div>
</template>
```
```js
// parentDemo.js
import { LightningElement } from 'lwc';

export default class ParentDemo extends LightningElement {
  handleSimpleEvent() {
    alert('simple event being handled by parent comp')
  }
}
```
## Custom Events with Data
Create an event in a child component, to be handled by a parent component.
```js
// customEventWithData.js
import { LightningElement, api } from 'lwc';

export default class CustomEventWithData extends LightningElement {

  handleEvent() {
    const selectedEvent = new CustomEvent('selected', {detail: 'details in this object...'})
    this.dispatchEvent(e)
  }
}
```
```html
<!-- customEventWithDataParent.html -->
<template>
  <div class="slds-m-around_large">
    <strong>Parent component</strong>
  </div>
  <div class="slds-m-around_small">
    <c-child-demo onselected={handleSimpleEvent}> </c-child-demo>
  </div>
</template>
```
```js
// customEventWithDataParent.js
import { LightningElement, api } from 'lwc';

export default class CustomEventWithDataParent extends LightningElement {

  handleSelectedEvent(e) {
    const d = e.detail
    alert('received ' + d)
  }
}
```
## Bubble Events
Emit an event to be handled by multiple ancestor components.
```xml
<!-- fooComp.js-meta.xml -->
```
```js
// fooComp.js

import { LightningElement, api } from 'lwc';

export default class FooComp extends LightningElement {
  @api message // this allows the value to be set using the property in the meta
}
```
```html
<!-- hooksDemo.html -->
<template>
</template>
```
## Composed Events
```xml
<!-- fooComp.js-meta.xml -->
```
```js
// fooComp.js

import { LightningElement, api } from 'lwc';

export default class FooComp extends LightningElement {
  @api message // this allows the value to be set using the property in the meta
}
```
```html
<!-- hooksDemo.html -->
<template>
</template>
```
## Pub Sub Events
```xml
<!-- fooComp.js-meta.xml -->
```
```js
// fooComp.js

import { LightningElement, api } from 'lwc';

export default class FooComp extends LightningElement {
  @api message // this allows the value to be set using the property in the meta
}
```
```html
<!-- hooksDemo.html -->
<template>
</template>
```
