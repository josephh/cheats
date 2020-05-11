# SALESFORCE _ COMMUNITIES - 07 LIGHTNING WEB COMPONENTS COMPOSITION
LWCs can be included inside other LWSs via through 2 notations
`<c-myComponent>` and `<c-my-component>`.
:information_source: note the difference with aura components is they use c + colon, e.g. `<c:myComponent>`
## Examples
```html
<!-- parentDemo.html -->
<template>
  <div class="slds-m-around_large">
    <strong>Parent demo</strong>
  </div>
  <c-child-demo> </c-child-demo>
</template>
<!-- childDemo.html -->
<template>
  <div class="slds-m-around_small">
    <strong>Child demo</strong>
  </div>
</template>
```
## 'Data down'?
Pass values from parent components to children via a combination of public properties, i.e. `@api`. :warning: `@track` can't be applied to a property we want to change externally - '@track' denotes a component-private field.
```javascript
// childDemo.js
import { LightningElement, api } from 'lwc';

export default class HooksDemo extends LightningElement {
  @api name = "joe"
}
```
```html
<!-- parentDemo.html -->
<template>
  <div class="slds-m-around_large">
    <strong>Parent component</strong>
  </div>
  <div class="slds-m-around_small">
    <!-- pass a parameter to set the value of the child's public 'name property' -->
    <c-child-demo name="jack"> </c-child-demo>
  </div>
</template>
<!-- childDemo.html -->
<template>
  <div class="slds-m-around_small">
    <strong>child name is set to {name}</strong>
  </div>
</template>
```
## Slots
Parent components can pass markup into a child component via `<slot></slot>`.  Slots can help a parent component understand when a child component is ready to communicate.
Slots come in 2 flavours - (1) named and (2) unnamed
