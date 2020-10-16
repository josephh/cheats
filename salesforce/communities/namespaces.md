You can't create a namespace in your devhub
you can only create a namespace in developer edition Orgs
1. created developer edition (free) org josephh@rocketmail.com (devhub) - follow
2. created another developer edition (free) org dorian@namespace.josephh.com
3. created namespace 'josephh' in developer edition dorian@namespace.josephh.com
4. registered My Domain in josephh@rocketmail.com (devhub) - now subdomain of same org is called 'https://josephh-dev-ed.lightning.force.com/'
5. Deploy domain to users (so that the devhub namespace registry can be used to link the josephh namespace to the devhub My Domain (or subdomain https://josephh-dev-ed.lightning.force.com/))
> Why do you need a My Domain? A My Domain adds a subdomain to your Salesforce org URL so that it’s unique. As part of the Namespace Registry linking process, you’ll be logging into two distinct orgs simultaneously (your Dev Hub org and your Developer Edition org), and your browser can’t reliably distinguish between the two without a My Domain.  

6. 'Link Namespace' shows in All Namespace Registry in josephh@rocketmail.com (devhub) org.   (This errored initially - worked the next day)
7.  
