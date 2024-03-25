
## find
find all files recursively from current dir with filename pom.xml
`find . -iname "pom.xml"`
alternatively pipe find into grep to use wilcard/ regex searching, e.g.
`find . -print | grep .pom.xml$` (regex starts with dot as find outputs the full directory path, including subfolders)
e.g.
```
joseph.jobbings@UK-F52KW9W4QT aem-guides-wknd % find . -print | grep .pom.xml$

./ui.frontend/pom.xml
./ui.content/pom.xml
./core/pom.xml
./ui.tests/pom.xml
./dispatcher/pom.xml
./all/pom.xml
./pom.xml
./it.tests/pom.xml
./ui.config/pom.xml
./ui.apps.structure/pom.xml
./ui.apps/pom.xml

```
## grep
grep recursively all files for regex 'bundle'
`grep -r profile .`
grep recursively only files called 'pom.xml' for regex 'bundle'
``
