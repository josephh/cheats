# SALESFORCE _ COMMUNITIES - 06 LIGHTNING WEB COMPONENTS LIFECYCLE HOOKS
These are part of the `LightningElement` JS class (?).  
Flow of lifecycle is:
* (parent) **constructor()** >
* pending public properties are updated >
* parent inserted into DOM >
* (parent) **connectedCallback()** >
* (parent) **render()** >
 * (child) **constructor()** >
 * (child) pending public properties are updated >
 * (child) inserted into DOM >
 * (child) **connectedCallback()** >
 * (child) **render()** >
* (parent) **renderedCallback()**

## constructor()
`constructor() // component init`
## connectedCallback()
`connectedCallback() // callback on inserting element into the DOM`
## disconnectedCallback()
`disconnectedCallback() // callback on removing element from the DOM (including "disconnecting" child components)`
## render()
`render() // Conditionally render components; must return valid html; parent renders before children`
## renderedCallback()
`renderedCallback() // callback following element rendering`
## errorCallback(error, stack)
`errorCallback(error, stack) // callback following error`
### Examples
```javascript
// hooksDemo.js
import { LightningElement } from 'lwc';

export default class HooksDemo extends LightningElement {
  name = "joe"
  constructor() {
    // needs super if we explicitly use constructor
    super()
    console.log('inside constructor')
  }
  connectedCallback() {
    console.log('inside connectedCallback')
  }
  disconnectedCallback() {
    console.log('inside disconnectedCallback')
  }
  renderedCallback() {
    console.log('inside renderedCallback')
  }
  render() {
    // commented because it needs to return html template
  }
  errorCallback(error, stack) {
    console.error(error);
  }
}
```
```html
<!-- hooksDemo.html -->
<template>
  <div class="slds-m-around_large">
    <strong>HOOKS demo</strong>
  </div>
  <div class="slds-m-around_small">
    <strong>
    Name ? {name}</strong>
  </div>
</template>

```
