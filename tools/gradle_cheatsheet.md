# Gradle
## Command line
* -x to exclude tasks from execution, e.g.
`gradle dist -x test` to skip tests
* --continue to run to the end of all tasks (without early exit), e.g.
`gradle clean build --continue`
* task name abbreviation: you only need to give enough of a task name to uniquely identify it, e.g.s
`gradle d` is the same as `gradle dist`.
`gradle compTest` and `gradle cT` are the same as `gradle compileTest`
* list projects
`gradle -q projects`
* list tasks
`gradle -q tasks
* list project dependencies
`gradle -q dependencies eureka-server:dependencies notifications-service:dependencies`
* list project properties
`gradle -q notifications-service:properties`
* profile project while building (and produce html report)
`gradle build --profile` writes report to uild/reports/profile directory
## Gradle wrapper
* add the gradle wrapper to your project and check it in to scm!
The point of the wrapper is it makes sure different versions of the build tool do not affect the build success into the future.  I.e. by locking down the version of gradle that worked when the build scripts were put together.
* create gradlew with the wrapper task, e.g. `gradle wrapper --gradle-version 3.2.1`
## Gradle daemon
* Gradle has a daemon(s) - enabled by default and recommended for use in all 'human-developer' environments - that avoid costly jvm startup and improve build performance by caching build data.
* **Switch off** the daemon on  Continuous Integration and build server environments.
* Look at Gradle daemon info with `gradle --status`
## Dependencies
External dependencies are grouped into configurations.
```groovy
apply plugin: 'java' // the java plugin defines a number of standard configurations, which represent the classpaths used by the java plugin
repositories { // fetch dependencies from...
    mavenCentral()
}
dependencies { // the Java plugin's standard configurations include compile, runtime, testCompile, testRuntime etc.  Standard configurations can include the dependencies declared by other configurations, e.g. runtime dependencies by default also includes the compile time dependencies.
    compile group: 'org.hibernate', name: 'hibernate-core', version: '3.6.7.Final'
    testCompile group: 'junit', name: 'junit', version: '4.+'
}
```
* External dependencies can be specified with full key-value `group:name:version` attributes, as in the above example, or via a shorthand omitting the keys, such as `org.hibernate:hibernate-core:3.6.7.Final`,
## Multi-project builds
* these have a few common characteristics,
  1. A settings.gradle file in the root or master directory of the project
  1. A build.gradle file in the root or master directory
  1. Child directories that have their own *.gradle build files (some multi-project builds may omit child project build scripts)
* Gradle uses the name of the directory where it find settings.gradle as the name of the root project.
