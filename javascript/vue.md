# Vue.js
* Promotes incremental adoption.  
* Core focusses on the view.
## Single file components
Vue solves the limitations of global Vue components, which target container elements in the body of _**every**_ page (typically defined using `Vue.component`, followed by `new Vue({ el: '#container' })`) by single-file components, which can take advantage of build tools like webpack and browserify
## Declarative rendering
At the core of Vue.js is a system that enables us to declaratively render data to the DOM using straightforward template syntax
```html
<div id="app">
  {{ message }}
</div>
```
```javascript
el: '#app',
  data: {
    message: 'Hello Vue!'
  }
})
```
The data and the DOM are now linked, and everything is now reactive.
## -v prefixed Directives - binding
-
