# SALESFORCE _ COMMUNITIES - 11 LIGHTNING DATA SERVICE
## Lightning Data Service
Lightning data service allows a way to work with SF data and metadata.
* LDS is client-side (loads data progressively, does caching, invalidates cache when server-side data changes, "batches-up" server calls to increase efficiency)
* LDS communicates with SF via the (server-side) User interface Api
:information_source: Critical information when using this feature is to provide record id as well as object Api Name - to determine the record of interest as well as the fields or properties that are used to capture data for that record.  

### Get current record
```js
// opportunityDetails.js
import { LightningElement, api } from 'lwc';

export default class HooksDemo extends LightningElement {
  @api recordId
  @api objectApiName
  constructor() {
    // needs super if we explicitly use constructor
    super()
    console.log('record id: ', recordId)
    console.log('object api name: ', objectApiName)
  }
```
### Create New Record
#### lightning-record-form
Use `<lightning-record-form>` in read-only, edit or update modes to capture or present info.  Use OOTB fields like recordId in template to fetch respective record data.  `<lightning-record-form>` comes with prescribed styling.
```javascript
// forms.js
import { LightningElement, api } from 'lwc';

export default class LdsViewRecord extends LightningElement {
  @api recordId;
  @api objectApiName;

  constructor() {
    // needs super if we explicitly use constructor
    super()
  }

  handleSuccess() {
    alert('form success')
  }

  handleSubmit() {
    alert('form submit')
  }

  handleError() {
    alert('error')
  }

}

```  
```html
// forms.html
<template>
  <lightning-card title="LDS View Record" icon-name="custom:custom22">
    <div class="slds-m-around_small">
      <lightning-record-view-form record-id={recordId} object-api-name={objectApiName}>
        <lightning-output-field field-name="LastName"></lightning-output-field>
        <lightning-output-field field-name="Phone"></lightning-output-field>
      </lightning-record-view-form>
    </div>
    <div class="slds-m-around_small">
      <lightning-record-form
        record-id={recordId}
        object-api-name={objectApiName}
        layout-type="Full"
        mode="edit"
        columns="2"
        onsuccess={handleSuccess}
        onsubmit={handleSubmit}
        onerror={handleError}
        >
      </lightning-record-form>
    </div>
  </lightning-card>
</template>
```
#### lightning-record-view-form
Use `<lightning-record-view-form>` with nested `<lightning-output-field>` (e.g. `<lightning-output-field-name="LastName"></lightning-output-field-name>`) to show tailored read-only forms.  Developers can provide custom styling for this.
#### lightning-record-edit-form
Also requires nested fields - `<lightning-input-field>` (e.g. `<lightning-input-field-name="LastName"></lightning-input-field-name>`) which match the object api in use.  To make a 'save' button visible, a `<lightning-button type="submit" label="Some Label">`is needed inside the <lightning-record-edit-form> element.
### @wire and Apex classes  
`@wire` annotation is used to fetch data at component annotation time.  :information_source:  This is read-only data.
1. Create an apex class to wire to,
> `sfdx force:apex:class:create --classname CaseController --template DefaultApexClass --outputdir force-app/main/default/classes`  
target dir = /Users/jjobbings/s/salesforce/exp/force-app/main/default/lwc/force-app/main/default/classes  
create force-app/main/default/classes/CaseController.cls  
create force-app/main/default/classes/CaseController.cls-meta.xml
1. Create an LWC to connect up to the Apex class and use the wire method to initialise properties with data.  
```javascript
// wireMethod.js
import { LightningElement, wire, api} from 'lwc';
import getAllCases from '@salesforce/apex/CaseController.getAllCases';  
export default class WireMethod extends LightningElement {

  @wire(getAllCases) cases;  // this will result in a `.data` property and an `.error` property in the case of error.  

  @api records
  @api err
   // we can also write the 'data' and 'error' parameters and write out a more detailed function, combined with public properties
  @wire(getAllCases) wiredCases({ data, error }) {
    if(data) {
      this.records = data;
      this.err = undefined;
    }
    if(error) {
      this.err = error;
      this.records = undefined;
    }
  }
```
```html
<!-- wireMethod.html -->
<template>
  <lightning-card title="Wired 2" icon-name="standard:case">
    <div class="slds-m-around_small">
      <template for:each={records} for:item="r">
        <p key={r.id}>
          {r.Subject} : {r.Status}
        </p>
      </template>
      <template if:true={err}>
        <p>
          An error occurred :-(<br>
        </p>
      </template>
    </div>
  </lightning-card>
  <lightning-card title="Case Details" icon-name="standard:case">
    <div class="slds-m-around_small">
        <template for:each={cases.data} for:item="i">
          <p key={i.id}>
            {i.Subject} : {i.Status}
          </p>
        </template>
        <template if:true={cases.error}>
          <p>
            An error occurred :-(<br>
          </p>
        </template>
    </div>
  </lightning-card>
</template>
```  

### @wire with parameters
1. Apex classes to 'wire-in' should have method signatures that match the client code.  :information_source: note the SF developer console includes a database query tab where you can try out the SF-specific query language and execute them to see what you get, e.g. `Select Id, Subject, Description, Status, Origin, CaseNumber From Case where subject like '%o%'`.  
```cls
// Apex class - CaseController.cls
public with sharing class CaseController {
    public CaseController() { }

    @AuraEnabled(cacheable = true)
    public static List<Case> getAllCases(String subject) {
      String key = '%' + subject + '%';  
      return [Select Id, Subject, Description, Status, Origin From Case WHERE subject like: key];
    }
  }
```  
```javascript
import { LightningElement, wire, api, track} from 'lwc';
import getAllCases from '@salesforce/apex/CaseController.getAllCases';
export default class WireMethod extends LightningElement {

  @track subject // component private field for use with input and query

  handleChange(e) {
    const s = e.target.value
    this.subject = s
  }

  @api records
  @api err  
// note the use of '$...' syntax when passing parameters to Apex class methods  
  @wire(getAllCases, {subject: '$subject'}) wiredCases({ data, error }) {
    if(data) {
      this.records = data;
      this.err = undefined;
    }
    if(error) {
      this.err = error;
      this.records = undefined;
    }
  }
}  
```
```html
<!-- wireMethod.html -->
<template>
  <lightning-card title="Wired 2" icon-name="standard:case">
    <div class="">
      <lightning-input value={subject} label="Search case by subject" onchange={handleChange}></lightning-input>
    </div>
    <div class="slds-m-around_small">
      <template for:each={records} for:item="r">
        <p key={r.id}>
          {r.Subject} : {r.Status}
        </p>
      </template>
      <template if:false={records}>
        <p>
          No matching records.<br>
        </p>
      </template>
      <template if:true={err}>
        <p>
          An error occurred :-(<br>
        </p>
      </template>
    </div>
  </lightning-card>
</template>

```
### Call Apex class methods imperatively
Using the CaseController.cls example apex class above...
```javascript
// getRecord.js
import { LightningElement, wire, api } from 'lwc';
import { getRecord } from 'Lightning/uiRecordApi'

export default class GetRecord extends LightningElement {

  @api recordId

handleChange ........ //TODO

}
```
```html
<!-- apexImperativeCall.html -->
<template>
  <lightning-card title="Apex imp call" icon-name="standard:case">
    <div class="">
      <lightning-input value={subject} label="Search case by input" onchange={handleChange}></lightning-input>
      <lightning-button label="Load Cases" onclick={handleLoad}></lightning-button>
    </div>
    <div class="slds-m-around_small">
      <template for:each={records} for:item="r">
        <p key={r.id}>
          {r.Subject} : {r.Status}
        </p>
      </template>
      <template if:false={records}>
        <p>
          No matching records.<br>
        </p>
      </template>
      <template if:true={error}>
        <p>
          An error occurred :-(<br>
        </p>
      </template>
    </div>
  </lightning-card>
</template>
```
### SF Lightning Ui Record API
get records
```javascript
import { LightningElement, wire, api } from 'lwc'
import { getRecord, createRecord, deleteRecord, updateRecord } from 'lightning/uiRecordApi'

```
### SF Lightning Ui Object API
get object info
get pick list values // picklists help find available options to choose from - in a dynamic way when building GUIs
get pick list values by record types
```javascript
import { LightningElement, wire, api } from 'lwc'
import { getObjectInfo } from 'lightning/uiObjectInfoApi'

```
