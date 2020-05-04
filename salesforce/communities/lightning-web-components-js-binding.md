# SALESFORCE _ COMMUNITIES - LGHTNING WEB COMPONENTS JAVASCRIPT BINDING
## How to link js into html template?
### JS Binding - Class properties
1. In the CLI provided js file for the component, set some values in the class, in the below example we add a 'name' property.
```javascript
 helloWorld.js

  import { LightningElement } from 'lwc';

  export default class HelloWorld extends LightningElement {
      name = 'joe'
  }
```
Access the javascript class field/ property via curly braces notation,
e.g.
```html
helloWorld.html
  <template>
    <p>
      <strong>welcome </strong> {name}
    </p>
  </template>

```
### JS Binding - Class methods as event listeners
```javascript
helloEvents.js

 import { LightningElement } from 'lwc';

 export default class HelloEvents extends LightningElement {
     handleChange() {
       alert('i am a change event being handled!')
     }
 }
```
```html
helloEvents.html
  <template>
    <div class="slds-m-around_small">
      <lightning-input value={name} label="Event Demo" placeholder="Type your input here..." onchange={handleChange}>
      </lightning-input>
    </div>
    <p>
      <strong>welcome </strong> {name}
    </p>
  </template>
```
### JS Binding - Conditional rendering
Use template built-in features
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
```html
ifElseDemo.html
  <template if:true={isGreaterThanZero}>
    <p>
      <strong>Some text to display following truthiness test</strong>
    </p>
  </template>
```

### JS Binding - Conditional rendering
Use template built-in features
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
```html
ifElseDemo.html
  <template if:true={isGreaterThanZero}>
    <p>
      <strong>Some text to display following truthiness test</strong>
    </p>
  </template>
```˜˜
