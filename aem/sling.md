# Apache sling notes from https://sling.apache.org/documentation
## Resource Resolution

* mappings go in tree beneath /etc/map (though this location is configurable, best left alone in AEM)

### Resolution properties
1. sling:match
when this property is set on a node in the mapping tree (under etc/map), it defines a _partial_ regular expression, which is used **instead of** the node's name to match the incoming request.  This property is only needed when the regex includes characters which are not valid JCR name characters, such as '/', ':', '*' (i.e. common regex tokens).
1. sling:redirect
causes a redirect response to be sent to the client, with the value of this property sent back as the value of Location response header.
1. sling:status
sets the value of the response code corresponding to sling:redirect (deafults to 302)
1. sling:internalRedirect
when set on a node under etc/map cause the current path to be modified internally to continue with resource resolution.  This multi-value property allows trying of one path after another, until a match is found.
1. sling:alias
allows an alias to be specified **on any resource**, so that that resource may also be addressed at that path (For example the resource /content/visitors may have the sling:alias property set to besucher allowing the resource to be addressed in an URL as /content/besucher).
