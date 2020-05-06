# SALESFORCE _ COMMUNITIES - 03 LIGHTNING WEB COMPONENTS JAVASCRIPT BINDING
## How to link js into html template?
### JS Binding to class properties
In the CLI provided js file for the component, add a property...
```javascript
/* helloWorld.js */

  import { LightningElement } from 'lwc';

  export default class HelloWorld extends LightningElement {
      name = 'joe'  // name property
  }
```
Access the javascript class property using curly braces notation in the html...
```html
<!-- helloWorld.html -->
  <template>
    <p>
      <strong>welcome <em>{name}</em></strong>
    </p>
  </template>

```
### JS Binding to class methods
In the CLI provided js file for the component, add a function to act as an event handler...
```javascript
/* helloEvents.js */

 import { LightningElement } from 'lwc';

 export default class HelloEvents extends LightningElement {
     handleChange() {
       alert('i am function being called!')
     }
 }
```
```html
<!-- helloEvents.html -->
  <template>
    <div class="slds-m-around_small">
      <lightning-input value={name} label="Event Demo"   placeholder="Type your input here..."  onchange={handleChange}>
      </lightning-input>
    </div>
    <p>
      <strong>welcome </strong> {name}
    </p>
  </template>
```
### JS Binding for conditional rendering
Use template built-in features
```javascript
/* ifElseDemo.js */

 import { LightningElement } from 'lwc';

 export default class IfElseDemo extends LightningElement {
     val = 0
     // note this function is not being dynamically re-executed
     isValGreaterThanZero() {
       return val
     }
     incrementVal() {
       val += 1
     }
     decrementVal() {
       val -= 1
     }
 }
```
```html
<!-- ifElseDemo.html -->
<template>
  <p if:true={isValGreaterThanZero}>
    <strong>Some text to display if isValGreaterThanZero = TRUE</strong>
  </p>
  <p if:false={isValGreaterThanZero}>
    <strong>Some text to display if isValGreaterThanZero = FALSE</strong>
  </p>
  <p>
    <strong>Val currently set to: {val}</strong>
  </p>
  <lightning-button label="Increment" onclick={incrementVal}></lightning-button>
  <lightning-button label="Decrement" onclick={decrementVal}></lightning-button>
</template>

```
### JS Binding for lists
Provide an array property in the CLI generated js file,
```javascript
ifElseDemo.js

 import { LightningElement } from 'lwc';

 export default class IfElseDemo extends LightningElement {
     val = 0
     isValGreaterThanZero() {
       return val
     }
     incrementVal() {
       val += 1
     }
     decrementVal() {
       val -= 1
     }
 }
```
Then access the array in the template either using the for:each construct or iterator.  Iterator provides convenient boolean properties like `first` and `last`.
```html
ifElseDemo.html
<template>
  <div class="slds-m-around_small">
    <lightning-card title="For each demo" icon-name="standard:contact">
      <template for:each={contacts} for:item="item" for:index="index">
        <p key={item.id} class="slds-m-around_small">
          {index} - {index.name} &nbsp; {item.email}
        </p>
      </template>
    </lightning-card>
  </div>
  <div class="slds-m-around_small">
    <lightning-card title="Iterator demo" icon-name="standard:contact">
      <ol>
        <template iterator:it={contacts}>
            <li key={it.value.id} class="slds-m-around_small" style="list-style-type: none;">
              <div if:true={it.first} class="list-first">
                (first)
              </div>
              <div if:true={it.last} class="list-last">
                (last)
              </div>
              <div>{it.value.name} &nbsp; {it.value.email}
              </div>
            </li>
        </template>
      </ol>
    </lightning-card>
  </div>
</template>
```
