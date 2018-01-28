# AEM Headless
AEM Headless :
Content fragments +
Experience fragments (encapsulating fragment with specific rendering) +
Content services (read only, lightweight means to access, e.g. using JSON)

AEM6.2 had a 'hidden' feature "Content as a Service"

6.3 Feature Pack
Core Components 1.1.0 - adds content fragments to 1.0.

Start by enabling Content Fragment models in the config GUi (content fragment models are no longer a developer task).

No UI yet(!) for enabling the model location in DAM - do this in CRX/DE to add new conf, this means e.g.
navigate in the content tree to `/content/dam/my-site/jcr:content` and add a property cq:conf String '/conf/my-site' - this means the content fragment model config will get stored in that location, otherwise you will only have access to the general config under '/conf/global'

HTL markup plus sling model implementation

Add or build from https://github.com/Adobe-Marketing-Cloud/aem-core-wcm-components

With a content fragment stored and displayed against a page, the model can then be returned in a friendlier way by appending /.../some-page.model.json to the path when requesting.  The returned json will omit jcr:content... type of values and respect the settings stored by the author when choosing which values in the content model to include and which to not include.

Some components - like responsive Grid - have been enhanced to include styling classes.  This is potentially useful to SPAs that want to benefit from some layout hints, despite getting the data in a 'headless' way.

Helpful data types from Adobe (which could be added to on a per-project basis!) /libs/settings/dam/cfm/models/formbuilderconfig/datatypes

Model config gets stored in the /conf folder at the location specifically configured, e.g. /conf/my-site/settings/dam/cfm/models/.../model/.../items... data itself gets stored /dam/my-site...  
There is a 'master' version of the content fragment data. Plan is to enhance that with 'variations'.    
