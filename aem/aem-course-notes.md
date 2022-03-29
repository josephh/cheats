# AEM developer course
## Questions
### “Multi-tenancy” - vanity URLs and caching - are duplicate vanity URLs allowed?
Yes.  Possibly consider - via the page properties authoring dialog - preventing vanity url duplicates.  
### Migration - component properties change, what to do?  Mix of static and editable templates: how to manage?
Classic to Touch UI migration tool might be usable.
Alternativley, roll-your-own scripts implemented as servlets: provide a structured file (e.g. csv) of pages to update and other instructions about node processing.  Issue the commands (e.g. curl) into an AEM instance hosting those scripts.  Perform the migration on a non-production server.
### Image component
cq:dropTargets/image - propertyName can be set here.  This could be used to help when updating the other simplyhealth components - that store a DAM image reference - so that we can continue to use the same field name for the image.    
### Search
Server-side includes - see Apache SDI (contributed by Cognifide) - can help with partial caching of dynamic content.  E.g. news feed or article categories.
OAK index definitions - see CRX/DE /oak:index
lucene = full text indexing
property = JCR property name indexing

Goto AEM : operations > diagnosis > explain query.  Use 'explain query' tool to see exactly what happens when a query executes and what the performance was.

oakutils.appspot.com has an index generator - i.e. rules you can add to your Oak instance

Also - see the osgi console apache OAK solr embedded server.  This is for development use only.  Setup your own production solr server if needed, say, when indexing externally generated content.
### Apache sling rendering - can this be used as a way to ‘parameterise’ a path?
Advised no.  Selector suffixes (e.g. `/simply/foo.html/a/b`) are not recommended at all for round-trip page rendering.  Sling selectors not recommended as a way to parameterise a page request.  Prefer `/simply/foo.html?a=b`.
### Template types - are these ignored once the template is created?
yes
### Core components - how to avoid having to write sling models that are just accessors/ json exporters? Is that possible, e.g. with Java reflection lib?  
Consider Sling Models delegation and `@via` amnotation as a means to reuse a resource super type's model (but override accessors on an _adhoc_ basis as necessary)   
### How to create a site?
Possibly use a blueprint based on an existing site.  Otherwise create the site root as a developer.
### Caching/ update of client side libraries?
For clientlibs, when deploying an app to author via the package manager - and then replicating it - updating clientlib files are not reactivated, which means the new versions of clientlibs are not activated and dispatcher cache flush.
### Workflows - both 1. Form submit handling and 2. managing the authoring process, e.g. page activation
create a new workflow by editing an existing example.  Clicking the 'edit' button will copy the example under the /conf folder.  Copy workflow models from /conf/global to context aware workflow location, e.g. conf/simplyhealth and any necessary rep:policy nodes as well.

Workflow launcher - listen for repo changes.
1. created
1. nt:unstructured node types - with any subpath name
1. write your own java service interface implementations to be workflow steps.

Create a 'Project' to allow use of project steps in  workflows.

### Data store architecture?
Avoid binaries in Mongo!  

### Copy of live content to staging
### Component hierarchy?  What are the options in AEM?
1. resource type (and super resource type)
1. container hierarchy (a container component is the parent for another component)
1. include hierarchy (a script is included by a parent script)

### Translations
use the dictionary creation stuff - see course notes

### Testing
wcm.io - for Sling mocks.
Hobbes not well used :unamused:

### Testing of Forms
Calvin.js (an extension of Hobbes.js)

### SPAs / Headless CMS
consider creating/ extending a custom Sling exporter, to fetch Content fragments directly from the DAM - for example, to expose `/content/dam/path/*.asset.json` or a more specific resource type e.g. `/content/dam/simplyhealth/products/cashplan.plan.json`
