# SALESFORCE _ COMMUNITIES - 05 LIGHTNING WEB COMPONENTS DECORATORS
## How to add additional functionality to a property or function?
### @api
a.k.a 'public property'. Public properties are reactive and trigger a re-render of the component when the property changes.
We can @api annotated properties 'public' because those properties become available for use in Lightning Builder, as well as in parent components.
```javascript
import { LightningElement, api } from 'lwc';

export default class PropsDemo extends LightningElement {

  @api  message1 = 'another'

  handleChange(e) {
    this.message1 = e.target.value
  }
}

```
```html
```
### @track
a.k.a 'private property'. Private properties *can be* reactive and trigger a re-render of the component when the property changes
```javascript
// propsDemo.js
import { LightningElement, track } from 'lwc';

export default class PropsDemo extends LightningElement {
  @track message = 'unset'
  handleChange(event) {
    this.message = event.target.value
  }
}
```
```html
<!-- propsDemo.html -->
<template>
  <div class="slds-m-around_small">
    <lightning-card title="Property Demo" icon-name="custom:custom54">
      <lightning-input
      value={message}
      label="Private property"
      placeholder="Input a new message value here..."
      onchange={handleChange}>
    </lightning-input>
    <div class="slds-m-around_small">
      message = {message}
    </div>
  </lightning-card>
</div>
</template>
```
### @wire
'wire-in' an adapter or Apex method into a JS class.
```javascript
```
```html
```
## Getters
Use getters,
1. to change the value of properties prior to its use in a template, or
1. to avoid creating class properties
```javascript
// getterDemo.js
import { LightningElement, track } from 'lwc';

export default class GetterDemo extends LightningElement {
  get message() {
    return 'foo'
  }
}
```
```html
getterDemo<template>
  <div class="slds-m-around_small">
    <lightning-card title="Getter Demo" icon-name="custom:custom54">
      <p>
        <!-- this executes the corresponding get function  -->
        <strong>if i call get message i get {message}</strong>
      </p>
  </lightning-card>
</div>
</template>
```
