# SALESFORCE _ COMMUNITIES - 12 LIGHTNING COMPONENTS NAVIGATION SERVICE
## Toast Notifications
Does not work for LWS inside Visual Force page or Aura application.
* sdflksjdlfkjdsf  
## Navigation
NavigationMixin includes 2 methods : `Navigate()` and `GenerateUrl()` both of which take a page reference.
See the developer docs for detail of which page types are supported by the nav service.  
```javascript
import {NavigationMixin}  from 'lightning/navigation'
import {LightningElement} from 'lwc'
export default class NavExample extends NavigationMixin(LightningElement) {
}
```
### PageReference consists of
* type
* state
* attributes
### Navigate() method
FIrst parameter is an object with the PageReference, second parameter is a boolean indicating whether or not to replace URL in the browser or open a new tab
```javascript
\\ snippet...
this.[NavigationMixin.Navigate] ({
  type: 'standard__objectPage',
  attributes: {
    objectApiName: 'Account',
    actionName: 'home'
  },
  state: {
    filterName: 'recent',t

  }
})
```
### GenerateUrl() method
```javascript
\\ snippet...
this.[NavigationMixin.GenerateUrl] ({
  type: 'standard__recordPage',
  attributes: {
    recordId: 'abcd12334567',
    actionName: 'view'
  }
}).then(url => {
  console.log('GeneratedUrl : ', url)
})
```
### CurrentPageReference
...can be used with wire decorator and also provides access to page state
```javascript
// navExample.js
import {NavigationMixin, CurrentPageReference}  from 'lightning/navigation'
import { LightningElement, wire } from 'lwc';

export default class NavExample extends NavigationMixin(LightningElement) {
  @wire(CurrentPageReference) pageRef

  constructor() {
    super()
    console.log(this.pageRef.state.c__showAtt) // component c__ namespace for request attributes
  }

}

```
