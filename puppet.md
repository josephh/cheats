# Puppet notes - based on https://www.virtualbox.org/, https://puppet.com/download-learning-vm/ and included read me
## Glossary
* OVF = Open Virtualization Format
* OVA = OVF application file extension

## Setup
Import OVA files into your target virtualisation tool, e.g. Virtual Box (rather than trying to open those files directly).  
If you have enough available memory on your host machine, increasing the memory allocation for the VM from the default 3GB to 4GB may improve performance and stability. Memory allocation settings are found by selecting the VM in the VirtualBox Manager window, opening the Settings dialog, and selecting the System section.

Add Host only network - for dev - in Virtual box, this is done via **Global Tools** main option in VirtualBox window.  Local adapter details are populated by default, e.g.
> IPv4 Address: 192.168.56.1  IPv4 Network Mask: 255.255.255.0

Login to Virtual Box - once an image is up and running - either,
* in a browser, e.g. http://192.168.56.101/us_en/quests/welcome.html
* in the mac default terminal app,
* in another terminal, e.g. `ssh root@192.168.56.101`
> Note the Puppet learning image comes with username `root` and password `childs.glynn`.  This password can also be found in /var/local/password

## Puppet language
* declarative
* portable
* centralized - thanks to Puppet agents - which poll for config changes.  But it can also be orchestrated.
* forge repo

## Puppet agent
* needs to runs on every system that you want Puppet to manage.


typos/ duplication
http://192.168.56.101/us_en/quests/welcome.html
> One logging in to the Learning VM,

http://192.168.56.101/us_en/quests/hello_puppet.html
> then automate the process of bringing those systems into your desired state and keeping them there.  
