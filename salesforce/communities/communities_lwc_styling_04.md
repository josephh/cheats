# SALESFORCE _ COMMUNITIES - 04 LGHTNING WEB COMPONENTS STYLING
## How to add Styling?
### CSS
1. Provide a css file for the component, e.g. `force-app/main/default/lwc/helloWorld/helloWorld.css`.  Provide css to target the component in that file.
1. For Lightning aura components, include an `extends` element attribute specifying the Salesforce Lightning Design System "force:slds".
> "SLDS provides a look and feel that’s consistent with Lightning Experience. Use Lightning Design System styles to give your custom stand-alone Lightning applications a UI that is consistent with Salesforce, without having to reverse-engineer our styles."
```html
<aura:application extends="force:slds">
```
