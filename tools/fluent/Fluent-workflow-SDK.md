## Workflow customisation with SDK
.m2/repository/settings.xml needs a "mirror"
```xml
<mirror>
    <id>maven-s3-release-repo-unsecure</id>
    <mirrorOf>maven-s3-release-repo</mirrorOf>
    <url>http://mvnrepo.fluentretail.com.s3-website-ap-southeast-2.amazonaws.com/releases</url>
    <blocked>false</blocked>
</mirror>
```
## SDK code project structure
Unzipped sdk provides,
* install scripts
* base maven dependencies (compile and runtime)
* rubix plugin archetype jar
* rubix plugin foundation source code jar
### Build code project with install scripts
* install scripts runs an interactive prompt to do 3 main things via maven commands
```
mvn install:install-file -DgroupId=com.fluentretail -DartifactId=rubix-plugin-archetype  -Dversion=1.2021.3.1 -Dpackaging=jar -Dfile=lib/rubix-plugin-archetype-1.2021.3.1.jar
mvn archetype:generate -DarchetypeGroupId=com.fluentretail -DarchetypeArtifactId=rubix-plugin-archetype  -DarchetypeVersion=1.2021.3.1
```
  1. scaffold out a java project, with maven build instructions in generated pom
  1. populate a postman collection, with environment details populated from previous interactive maven archetype prompts
  1. creates an insomnia directory: with UI elements, workflow definitions and UI json definitions
### Java rules
1. implement com.fluentretail.rubix.v2.rule.Rule interface. Extending `Rule` provides access to,
  1. the execution Context, as a parameter to `run` method.  This provides access to the event (e.g. context.getEvent()), the entity (e.g. context.getEntity()), configured rule parameters (context.getProp()) and the api client for retrieving data (e.g. context.api()), as well as the action factory for producing actions from the rule.
1. provide a `run` concrete method


1. Define additional Rule metadata via the `@EventAttributes` and `@Param*` annotations com.fluentretail.rubix.v2.rule.Rule
1. are annotated with `@RuleInfo`
Available actions for a rule: Rules have Actions as an output.  These Actions are provided by the Rules SDK, and handled in a very specific way by the Workflow engine.  _Actions are not immediately executed synchronously within the Rule's run method_. **They are simply queued, and executed at the end of the execution context**.  `context.action().<ActionName>` provides access to the required actionfactory.
  1. SendEventAction (could be for flow control, notification of other workflows, or future-dated events.)
  1. WebhookAction
  1. MutateAction
  1. LogAction
## @RuleInfo annotation
Class level annotation with properties,
1. name
1. description.  The description must include every parameter from ParamString annotation to be valid.
1. accepts = list of EventInfo annotations of acceptable entity types
1. produces = list of EventInfo annotations to describe output Event output by rule
1. exceptions = list of RuleExecutionException classes
### @EventInfo annotation (property to the RuleInfo annotation)
Annotation property level annotation with properties,
1. eventName
1. entityType
1. entitySubType
1. status = the Status of the Entity of the Event produced by this Rule.
#### Event produced by rule to inherit parent event context:
```Java
produces = {
  @EventInfo (
    eventName = "{" + PROP_EVENT_NAME + "}", entityType = EventInfoVariables.EVENT_TYPE, entitySubType = EventInfoVariables.EVENT_SUBTYPE, status = EventInfoVariables.EVENT_STATUS
  )
}
```
## @EventAttribute
Event attributes are optional - they may be found in an event, or may not.  For example, the SendEvent Rule contains a string parameter named eventName, which is set during the configuration of the Rule within a Ruleset within a Workflow.
## @Param...Type
Are configured for specific instance of a rule


>>>>>>>>
# Important

Actions are not executed immediately and synchronously to the run method execution.

In the case of a MutateAction, it will be executed directly after the rule's run method has been completed.
In the case of any other actions, they are queued and executed at the end of the current execution context.

## Testing
### The TDD approach is also known as Red, Green, Refactor:
1. Step 1. Create a fully Mocked Unit Test (using IDE's Unit Testing capabilities) for your custom Rule. In the Mocked unit test perform one (1) assertion per test case and test both positive and negative paths.
1. Step 2. Test your Rule in the TestExecutor.  This can be viewed as a mini Workflow Engine simulator that runs on your local machine to assert additional behavior.
1. Step 3. Testing your plugin on your Sandbox account.

## Exception handling
There are 2 different ways exceptions are handled by Rubix:
1. Special handling for exceptions of type RuleExecutionException.  Any RuleExecutionException, or subclass thereof, thrown from or bubbled up through a rule back into the Rubix Executor, will be handled as follows:
  1. Rubix engine will stop the execution of the current Ruleset, but continue processing previously queued inline events
  1. Rubix will not process any queued actions
  1. Rubix will log the exception in an orchestration audit Event, without a stack trace
  1. Rubix will mark the Event as a success
  1. Rubix will return a successful response to the UI if the execution was triggered by a User Action
  1. Rubix will create and attempt to execute a Ruleset Exception Event
  1. The name of this new Ruleset Exception Event is the same as the Ruleset that threw the exception and the eventType is set to EXCEPTION
  1. The RuleExecutionEvent message and the cause throwable (if it exists), will be available as part of the Exception Event for use within your rules
1. Default handling for all other Exceptions
  1. Rubix will stop the current execution
  1. Rubix will not process any queued actions
  1. Rubix will log the exception in an orchestration audit Event, including a stack trace
  1. Rubix will mark the Event as failed
  1. Rubix will return an error response to the UI if the execution was triggered by a User Action
  1. Rubix will not create and attempt to execute a Ruleset Exception Event
### Exception best  practices
  1. Do not use try-catch blocks, rather allow exceptions to bubble up to Rubix
  1. If catching any exceptions within a rule, ensure that the exception is either rethrown or included as a  throwable in a new exception
  1. If throwing a new exception from within a rule, ensure you capture as much information as possible within the exception, so that the Audit Events provide rich and useful information
  1. Do not swallow exceptions

## Debug
* Junit debug with TestExecutor or other standard junit
* No remote debug of deployed plugin. Use audit log events
## Logging and Audit
includes...

* Snapshot - A snapshot of the Entity at the time the Orchestration Event is received into the workflow
* Ruleset - An audit of the ruleset executed as a result of an Orchestration Event
* Rule - An audit of a Rule executed within a ruleset as a result of an Orchestration Event
* Action - An audit of an Action executed as a result of an output of a Rule
* Exception - An audit of an Exception that has occurred during the execution of an Orchestration Event
* Custom Logs (LogAction) - The context.action().log() can be used to log custom Orchestration Audit Events.
