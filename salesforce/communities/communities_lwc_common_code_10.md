# SALESFORCE _ COMMUNITIES - 08 LIGHTNING WEB COMPONENTS 'COMMON' CODE
## Additional JS files alongside LWC component files
```js
// lwc/foo/shared.js
const m = (i, n) => return i * n
```
```js
// lwc/foo/fooClient.js

import { LightningElement } from 'lwc';
import { Shared } from './shared.js';

export default class FooClient extends LightningElement {
  // use function from sibling file
  alert(Shared.m(23, 4))
}
```
## Stand-alone component provides shared js code
The component providing the shared code is referenced from a relative 'c/...' directory
```js
// utility/utility.js
const m = (i, n) => return i * n
```  
```js
// lwc/foo/fooClient.js

import { LightningElement } from 'lwc';
// note the folder name
import { Utility } from 'c/utility.js';

export default class FooClient extends LightningElement {
  // use function from sibling file
  alert(Utility.m(23, 4))
}
```
