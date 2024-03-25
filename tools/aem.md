# AEM admin
## Troubleshooting record  
1.  CORS config to only allow connection only from localhost  
This prevented any login or admin via the usual APIs (including not being able to uninstall packages or even save an osgi or CMS amendment with crxde or system/xonsole).
### Resolution
1. connect to K8s pod (e.g. using ```kubectl exec -it aem-author-0 --namespace mab-go-test /bin/bash```).
1. issue a curl command from terminal to sling api.  (This first step - hitting sling directly was needed because using the felix api was failing to update the cors service's configuration).  ```curl -u admin:MxD5ybS3EzFwBETQ -F":operation=delete" http://localhost:4502/apps/mab-go-web/osgiconfig/config.author/com.adobe.granite.cors.impl.CORSPolicyImpl~mab-go-web.cfg.json.config```
1. issue a curl command from terminal to felix api.  ```curl -u admin:MxD5ybS3EzFwBETQ 'http://localhost:4502/system/console/configMgr/com.adobe.granite.cors.impl.CORSPolicyImpl~mab-go-web' --data-raw 'apply=true&action=ajaxConfigManager&%24location=&alloworigin=*&propertylist=alloworigin' -v```
