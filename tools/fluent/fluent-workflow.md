# Fluent workflow module
## Workflow componentts

> Low-Code Conﬁguration –Allows users to view and configure without having to open any Java development environment.   Non-technical users can simply search, find and insert a Rule into a workflow.  Low code configuration also allows users to rapidly test and make changes to workflows without having to go through weeks or months of development cycles and testing cycles.

1. workflow builder (aka workflow modeller or orchestration modeller).  provides UI for the workflow engine.
1. rule library - supports users searching for OOTB rules to incorporate in workflows
1. workflow engine - aka Orchestration Engine (aka Rubix) - runs the workflows, applies rules and logic etc
1. rules SDK - write rules in Java to compile into deployable jars.  (rules are the smallest parts of workflows and ideally should be simple and reusable)
1. design patterns and best practises

## Events
...typically trigger the platform to do something; e.g.s ORCHESTRATION events or ORCHESTRATION_AUDIT events.
## RULE
...represents a single building block for logic and has a single purpose.
## RULESET
...collection of one or more Rules which are combined to form a single task within a process.
## TRIGGER
...exact criteria for which a specific Ruleset within a Workflow will be executed upon a given Event.
## STATUS
...represents a specific condition and state of the entity at any given time within the lifecycle.
(Statuses are configurable, other than the first state of CREATED).
## ACTION
...outcome of a rule.
## USER ACTION
...is a UI interaction such as a button and labels.  It can be integrated with workflows on Fluent Web apps.
# HOW IT WORKS
* All Events enter the OMX Workflow Framework via the Fluent APIs.  The framework processes these Events via the Event Queue matching the correct workflow and Rulesets to execute — this all occurs within the Workflow Engine.
* Outbound (Actions) from the Workflows retrieved from the Rules are returned back and applied via the Fluent APIs
## Workflow phases and states
Each workflow has phases (Status or Categories) that the entity flows through from beginning to completion.
A default Order workflow for example involves 4 phases:
1. Booking
1. Fulfillment
1. Delivery
1. Done/Complete.

## Workflow Builder
1. Add and configure rules, rulesets and triggers.
1. Versioning — each workflow is versioned and saved using the standard format: `<major>.<minor>`

"If there are sub-entities involved as part of the overall lifecycle of the domain root entity, they are also displayed in the swim lanes (vertical columns) below the main flow.
" Here course content (Module 2 Workflow builder, lesson 1 of 2)  introduces terminology "sub entities" and "domain root entity",  not previously seen or explained.  Explain these terms before using them.

Workflow Builder UI
* Rulesets inside of the Status rounded rectangles are triggered on that State.
* Rulesets outside of a Status transition the entity to the state in which its line and arrow indicate.

Each Ruleset displays the following:

Name of the Ruleset
Statuses for which the Ruleset applies
Trigger description
List of Rules and/or User Actions

* Rulesets execute in sequence.
Rulesets should be of a single purpose, such as to:

* Perform a single function (E.g: CancelOrder) OR
* Perform a logical gate for directing execution flow by using If/Else logic OR
* Perform a State Management (E.g: SetAwaitingPayment) OR
* Perform another purpose such as notifying other orchestration contexts or other domains. (E.g.  If an order comes in, notify the Inventory Catalog to reserve the product at that particular location.

### Rules
consist of
1. The Rule Condition
1. The Outcome (Action) - Rules do not have to always produce an Action.

### Configuring user actions
Configuring User Actions involves linking a Button Configuration with a Trigger and a Ruleset.

The Rules in a Ruleset will be executed in the order displayed, from top to bottom.


Users can make changes to the workflow directly from within the Workflow Editor —An Import / Export button is available within the Workflow Editor) allowing users to view, edit, and save changes to the workflow directly via JSON.

### Logic
#### Recommended Practice

* Logical Gates evaluate a simple condition by composition of rules
* Only 1 event (ever) output from the Ruleset
* Used for flow control, no other actions or outcomes should be present

### Managing State / Changing Status of an entity
* Only set a state once all preconditions for that state-change are met; and check all those preconditions in the same ruleset.
* Don't duplicate logic within individual tasks
* __ChangeStateGQL__ must always be used for status change of an entity (otherwise workflow engine will not know about that change).
Note to Developers using the Rules SDK: You should never set a new status on an orchestrateable entity via mutation action. Always let the workflow configurator use the standard change state rule.
#### Recommended Practise
* Only set new Entity States when the State is meaningful
* Don't set the same state from multiple Rulesets
* If and where possible, handle all state logic within a single execution thread, including child actions
* Only Change State using the dedicated ChangeState rule/action, and not together in a different mutation
* Consider using more states to help alleviate the discrepancies in meaning
* Use Ruleset triggers appropriately for helping to guard against illegal actions on states
* Identify Intervention states, such as FRAUD_CHECK, PAYMENT_FAILED, UNFULFILLABLE

### Process Flows
For each process (a process is a business logic within the workflow) in the workflow  - one or more rulesets should be executed inline within the execution context.

#### Recommended Practise
* For each event received in the workflow engine, all rulesets triggered-off that event for the same context should be executed "inline". * Rather than breaking the flow for a specific set of automated tasks in a process, they should be completed as part of a single transaction.
* Business logic should be visible in the workflow
* States should be meaningful and used appropriately
* Use meaningful Ruleset names and Rule names.
* Make sure Logical Gates are in the workflow, not in the rules
* Don't build cyclic flows (loops) into your workflow
* There is no parallel processing control. Don't Split and Merge within a single event or controlled transaction. Each rule and ruleset is executed in series on a single thread, and each action is applied in the order it was produced.

Composing logic in the workflow (rather than code), allows clients and partners more control over their business processes, and reduces expensive and time consuming change requests to development teams

## Summary
A workflow in the workflow framework is named by its Root Entity (1) and includes the lifecycles of its Sub-Entities (2):

Orchestrateable entities and contexts

A workflow in the workflow framework is named by its Root Entity (1) and includes the lifecycles of its Sub-Entities (2):

| Root Entities (1)|Sub-Entities (2)| Event Reference (3)|Platform CREATE Event? (4)|
|---|---|---|---|
|Location| Location / Wave|LOCATION / WAVE| ❌ ✅|
|Order|Order / Fulfilment / Article / Consignment| ORDER / FULFILMENT / ARTICLE / CONSIGNMENT| ✅ ✅ ✅ ✅|
|Fulfilment Options| Fulfilment Options Fulfilment Plan| FULFILMENT_OPTIONS FULFILMENT_PLAN| ✅ ✅|
|Product Catalogue| Product Catalogue Standard Product Variant Product Group Product Category| PRODUCT_CATALOGUE PRODUCT PRODUCT PRODUCT CATEGORY| ✅ ✅ ✅ ✅ ✅|
|Inventory Catalogue| Inventory Catalogue Inventory Position Inventory Quantity| INVENTORY_CATALOGUE INVENTORY_POSITION INVENTORY_QUANTITY| ✅ ✅ ✅|
|Virtual Catalogue| Virtual Catalogue Virtual Position| VIRTUAL_CATALOGUE VIRTUAL_POSITION| ❌ ❌|
|Control Group|	Control Group Control| CONTROL_GROUP CONTROL| ❌ ❌|
|Return Order|	Return Order Return Fulfilment| RETURN_ORDER RETURN_FULFILMENT| ✅ ✅|
|Billing Account|Billing Account Credit Memo Invoice Payment Payment Transaction|BILLING_ACCOUNT CREDIT_MEMO INVOICE PAYMENT PAYMENT_TRANSACTION| ✅ ✅ ✅ ✅ ✅|

Not all entities have a platform-generated CREATE event, however if it is an orchestrated entity, an event can be generated by an external system to trigger the workflow.

#### Recommended approaches

1. For creating or updating sub-entities, the recommended approach is to send an event to the root entity context on the workflow, and allow this to upsert the entity, and handle any pre and post-condition logic.
1. For updating root entities, the recommended approach is to send an event, rather than use the update mutation directly. This is so that you can cater for any other orchestrated business logic inside the workflow as part of an update.

##### Worked example
```json
{
    "name":"CancelOrder",
    "entityRef":"ON-1234",
    "retailerId":"1",
    "entityType":"ORDER",
    "entityId":"1234",
    "rootEntityType":"ORDER",
    "rootEntityId":"1234",
    "attributes":{
        "cancelReason":"Stolen credit card."
    }
}
```
What happens when an orchestration event is received?

When an orchestration event is received, the Workflow Engine loads the workflow associated with the entity defined in the event context.  Next, it searches for a ruleset using the primary Ruleset matching criteria, as follows:

Event Name = Ruleset Name
Entity Type - e.g: Type = ORDER
Entity Subtype - e.g: Order.type = HD
Entity Status - e.g: Order.status = BOOKED

Once the Ruleset is matched, the Workflow Engine will execute the remaining rulesets in a sequence, one by one.  The rules may produce an additional flow control event — we refer to this as inline events, to in-turn trigger additional Rulesets as part of the same action execution thread. These additional Rulesets will be matched and queued for execution in sequence after the initial Rulesets have been completed.

Note: Rules within a Ruleset are executed sequentially

