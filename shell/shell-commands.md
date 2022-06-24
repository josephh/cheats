# Commands
## alias
```
alias daem='docker run -d -p 4502:4502 --rm --name aem digiplat.azurecr.io/aem64-author:0.9.2'
daem (to use it)
alias bdaem="mvn clean install -P autoInstallPackage,autoInstallTestsPackage"
bdaem (to use it)
```
## export
` export JAVA_HOME=/usr/local/Cellar/openjdk@11/11.0.12/libexec/openjdk.jdk/Contents/Home\n`
## source
to apply the exports in a profile file, e.g.  ~/.zshrc
`source ~/.zshrc `
