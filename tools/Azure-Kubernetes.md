## Install Azure cli
`brew install az`
## Install kubectl cli
`brew install az`

* Run VPN
* Run Profixier if Azure dashboard reports "nothing see here"

## Login to azure
`az login` <--- this launches the portal website
## change to preferred azure subscription
1. In azure portal > https://portal.azure.com/#view/HubsExtension/BrowseResource/resourceType/Microsoft.ContainerService%2FmanagedClusters > choose K8s service of interest and then use 'connect' tab in portal page to see commands to set connection (e.g. https://portal.azure.com/#@mbplc.onmicrosoft.com/resource/subscriptions/7dd35a0b-bcfb-444b-9ecb-c7711c2c4e62/resourceGroups/mab-go-eun-stage-rgo-aks/providers/Microsoft.ContainerService/managedClusters/mab-go-eun-aks-stage65/overview).  
e.g.s
    1. `az account set --subscription 7dd35a0b-bcfb-444b-9ecb-c7711c2c4e62`
    1. `az aks get-credentials --resource-group mab-go-eun-stage-rgo-aks --name mab-go-eun-aks-stage65 --overwrite-existing`
    1. `kubelogin convert-kubeconfig -l azurecli` 
## run kubectl commands to administer K8s ("-n" is shorthand for "namespace")
1. `kubectl get deployments --all-namespaces=true`  
1. `kubectl get pods -n mab-go-test  -o wide`
1.  shell into a specific pod, `kubectl exec -it aem-author-0 --namespace mab-go-test /bin/bash`

