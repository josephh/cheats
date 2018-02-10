# AEM links and notes
java -Xmx1024m -XX:MaxPermSize=256m -agentlib:jdwp=transport=dt_socket,address=8000,server=y,suspend=n -jar cq-author-p4502.jar -v -nofork

## Links
[Adobe Tv](https://tv.adobe.com/videos/omniture/)

[AEM6 docs](https://helpx.adobe.com/support/experience-manager/6-3.html)

[Shortcuts](http://www.aemcq5tutorials.com/tutorials/aem-shortcuts-quick-reference-guide/#aem_useful_links)

### Local Admin/Dev Tooling
[Edit listener afteredit (stack overflow)](https://stackoverflow.com/questions/35186800/adobe-cq-aem-trigger-afteredit-handler)

[Backup](http://localhost:4502/libs/granite/backup/content/admin.html)

[Client libraries](http://localhost:4502/libs/granite/ui/content/dumplibs.html)

[Content fragments](http://localhost:4502/libs/granite/configurations/content/view.html)

[DAM](http://localhost:4502/damadmin)

[Dependency finder](http://localhost:4502/system/console/depfinder)

[Events](https://helpx.adobe.com/experience-manager/using/events.html)

[Infinity/ Depth URLs](http://localhost:4502/content/simplyhealth/jcr:content.infinity.json http://localhost:4502/content/simplyhealth.3.json)

[Logs - using CRXDE console pane](http://localhost:4502/crx/de)

[Log config](http://localhost:4502/system/console/configMgr/org.apache.sling.commons.log.LogManager)

[Query builder](http://localhost:4502/libs/cq/search/content/querydebug.html)

[Search](http://localhost:4502/crx/explorer/ui/search.jsp)

[Tag admin](http://localhost:4502/libs/cq/tagging/content/debug.html)

[Translator admin](http://localhost:4502/libs/cq/i18n/translator.html)

### Development
[AEM6 development](https://helpx.adobe.com/experience-manager/6-3/sites/developing/user-guide.html)

[Developer mode (touch UI)](https://helpx.adobe.com/experience-manager/6-3/sites/developing/using/developer-mode.html)

#### Granite/ GraniteUI
[Granite tag library](https://helpx.adobe.com/experience-manager/6-3/sites/developing/using/taglib.html)

[Granite field component](https://helpx.adobe.com/experience-manager/6-3/sites/developing/using/granite-ui-component.html)

### Deployment/ Management
[Adobe 'Checklist'](https://helpx.adobe.com/experience-manager/6-3/managing/using/best-practices-further-reference.html#KeyPerformanceIndicatorsandTargetMetrics)

[Monitoring & Maintaining](https://helpx.adobe.com/experience-manager/6-3/sites/deploying/using/monitoring-and-maintaining.html#MonitoringPerformance)

[Which AEM version?](http://localhost:4502/system/console/status-productinfo)
_or see welcome.html aem landing page - below AEM menu left panel_

### Testing
[Hobbes intro](https://helpx.adobe.com/experience-manager/6-3/sites/developing/using/hobbes.html)

[Hobbes.js api](https://helpx.adobe.com/experience-manager/6-3/sites/developing/using/reference-materials/test-api/index.html)

[Local aem test console]()

[Bobcat (from Cognifice)](https://github.com/Cognifide/bobcat)

### Analytics
[Omniture](https://marketing.adobe.com/developer/get-started/exchange/c-adobe-analytics-build)

### SEO
[SEO](https://helpx.adobe.com/experience-manager/6-3/managing/using/seo-and-url-management.html)

### Source code
[AEM Marketing cloud code](https://github.com/Adobe-Marketing-Cloud)

### Tagging
[](https://helpx.adobe.com/experience-manager/6-3/sites/developing/using/framework.html)

### OSGI
[OSGI config](https://helpx.adobe.com/experience-manager/6-2/sites/deploying/using/osgi-configuration-settings.html)

### Packages
[Package share](http://localhost:4502/crx/packageshare)

### Dev notes
cq:listeners
```javascript
cqEditConfig
 afterEdit="function() {setTimeout(function(){ns.ContentFrame.reload();}, 3000);}"
// with Coral UI, CQ.WCM.getEditable is not a function -> TypeError: CQ.WCM.getEditable is not a function
```

#### Content explorer
http://stg-author.westeurope.cloudapp.azure.com:4502/crx/explorer/index.jsp

#### AUTHOR/ PUBLISH mode
"author, publish, samplecontent, nosamplecontent" - are the Installation run modes. They can not be changed after installation.

In addition, the "Day CQ WCM Filter" should be set to "edit" on author and "disabled" on publish instances from the OSGi console or through the OSGi configuration file.

#### RUNMODE PREFERENCES ORDER
There are several methods for specifying which run mode to use; the order of resolution is:
1. sling.properties file
1. -r option through command line
1. system properties (-D) in start up script
1. Filename detection/ Jar name

Consult runmode for a running instance, e.g. http://localhost:4502/system/console/status-slingsettings

#### Touch UI
* Coral gui libs
Dialogue - for a cq:Component
cq:dialog sling:resourceType=cq/gui/components/authoring/dialog
|- 'content' node sling:resourceType=granite/ui/components/coral/foundation/container
||- 'items' node jcr:primaryType=nt:unstructured
|||- 'tabs' node sling:resourceType=granite/ui/components/coral/foundation/tabs
||||- 'elementSettings' node jcr:title="Element Title" sling:resourceType=granite/ui/components/coral/foundation/fixedcolumns
|||||- 'items' node
||||||- 'column' node sling:resourceType=granite/ui/components/coral/foundation/container
|||||||- 'items' node

Form,  
* Sightly OOTB
e.g. /libs/wcm/foundation/components/text
* JSPs OOTB
e.g. /libs/foundation/components/text

#### SSL
SSL wizard is accessible at a granite url, e.g.
http://stg-author.westeurope.cloudapp.azure.com:4502/libs/granite/security/content/sslConfig.html

Certificates and trust store details are stored under user management, SSL service user, e.g.
http://stg-author.westeurope.cloudapp.azure.com/libs/granite/security/content/userEditor.html/home/users/system/security/ssl-service

OSGi config (SSL)
http://prd-author.westeurope.cloudapp.azure.com:4502/system/console/configMgr Granite SSL Connector factory

PRD azure public facing IP http://13.94.255.243:443/

local investigation:
There was an error while configuring the SSL listener: Connect to 192.168.140.27:8443 [/192.168.140.27] failed: connect timed out

#### Vault
Using with VLT
To use vlt with this project, first build and install the package to your local CQ instance as described above. Then cd to content/src/main/content/jcr_root and run
```
vlt --credentials admin:admin checkout -f ../META-INF/vault/filter.xml --force http://localhost:4502/crx
```
Once the working copy is created, you can use the normal vlt up and vlt ci commands.
