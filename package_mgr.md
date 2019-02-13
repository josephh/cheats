1. Navigate to staging package manager, and login as an admin: http://stg-author.westeurope.cloudapp.azure.com/crx/packmgr
2. Create package, give it a name in the General tab; add a filter (to choose the image(s) and page(s) location to include) using the Edit > Filters tab.
2.1 Click add and select root path, e.g. /content/simplyhealth/about-us (all child pages are included by default.  Add an exclude rule, if required )
Root path: /content/simplyhealth/about-us
Rules exclude /content/simplyhealth/about-us/legal(/.*)
3. Save, Build then Download.
4. Navigate to production package manager, and login as an admin: https://prd-author.westeurope.cloudapp.azure.com/crx/packmgr
5. Upload the package - N.B. Advanced options allow replace/ merge options - and install
