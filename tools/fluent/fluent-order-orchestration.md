
# Functional - Order Orchestration course
## Multiple fulfilments - order sourcing and locations
It is possible for a retailer to have a single order with multiple fulfilments.  Being able to track this information is key.

* Statuses are available in the standard order workflows. These statuses can be changed to match the terminology of the retailer.
* Status information can be reviewed within the UI under Order Management or when reviewing the Workflow Builder.
## Handle rejected requests
* whole order can't be fulfilled from a single location, and
* split limit is reached, or
* splits not allowed, or
* no stock... actions are taken from the ESCALATED status to notify the customer
## Order Cancellation
Fluent Order Management has a ruleset that contains logic for Order Cancellation.
When a whole order is cancelled all of the associated fulfilments for the order will be cancelled and the inventory reservations will be reset with available-to-sell (ATS) updated in order to free up the available stock for future orders placed.
## Order returns
Fluent Order Management has a Return Order workflow to support this capability. Returns can be triggered:
1. from an external system sending a return into Fluent OrderManagement via an event into the workflow.
1. by Fluent Order Management which can create returns from the Fluent OMS and the Fluent Store Web Apps.
## Order Revisions
... can be orchestrated within the workflow using statuses.
When a whole order is cancelled all of the associated fulfilments for the order will be cancelled and the inventory reservations will be reset with available-to-sell (ATS) updated in order to free up the available stock for future orders placed.
## Order Revisions
... can be orchestrated within the workflow using statuses.
## Management fulfilment SLAs
## Handle fulfilment exceptions
## Click n Collect
The customer journey begins with the customer adding products into the shopping cart and checking pick-up location ETAs:
* Fluent Order Management checks availability and ETAs using the fulfilment option request via the Availability module.

Customer selects pick-up location: A Click and Collect Order is placed from the e-commerce site to Fluent Order Management and becomes available for viewing on Fluent OMS.

Fluent checks the inventory availability of all the items in the Order:
* Upon checking that inventory item are available,  Fluent creates the relevant fulfilment and subsequently reserve those items at the Store.
* The fulfilment is then able to be viewed in Fluent Store and the store staff is then able to pick, pack and label the order.
* After the labelling process has been completed the customer is notified that their order is now ready for collection.
* The customer can then walk into the store and show their notification to the store staff who can then mark the order as Collected.
* This collection process enables the Order to be marked as Completed.

### Domain Model
1. Order is the Root Entity within the Order Management Domain
* Fulfilment (1-to-many with **order** (i.e. 1 or many locations are picking items for the order))
* Article (1-to-many with **fulfilment**; many-to-many with **consignment**)
* Consignment
* Return Order - this is an optional feature in Fluent; therefore an optional link between order and return order
* Consignment
* Customer (1-to-1 with **order**; can't have an order without a customer.  customer links order management domain to billing account domain)

### Root Order entity workflow
1. order.type - Home Delivery (HD) workflow type
1. order.type - Click and Collect (CC) workflow type

Once an order is created in Fluent, the associated order workflow (determined by the order.type (e.g HD) kicks in.

### Sourcing strategies
1. Proximity (uses longitude and latitude to determine the closest location to the delivery address.)
1. Speed (Sometimes the nearest location to a customer in proximity is not always the quickest for dispatch. There may be a Warehouse dedicated to online orders that is able to dispatch items quicker than the local stores.  etc...)
1. Cost ('cost to serve' as a metric for how much it costs for them to fulfil an order to a customer address. For some businesses, their own Warehouse is the cheapest way...)

#### Standard Sourcing Logic in Fluent

1. We start at the Order entity with the first box - Can I fulfil ALL order items from a single location?
1. If YES, we Create the Fulfilment and Reserve the Inventory.  Note: This is our standard 'happy path scenario'.
1. If NO, we're unable to fulfil all order items from a single location because it does not hold all the stock — We now do the following:
    1. We first check if our Split Limit is reached.   If it has, we are unable to create additional fulfilments and so we must reject the unfulfilled items.
    1. When we do this, we create a Rejected Fulfilment against a Rejection Location for the unfulfilled items. The status of the Fulfilment is updated to ESCALATED.
    1. If we have not reached the Split Limit, can we fulfil ANY of the order items from our locations within VC and Network combination.
    1. This will loop around until we reach the split limit, or if there is no available inventory in our locations within our VC and Network combination.
    1. At this point, we know we cannot fulfil these items, so we follow the Rejected Fulfilment steps. The status of the Fulfilment is updated to ESCALATED

##### HD vs CC logic
The main difference is the Virtual Catalogues and Networks used to search for the best location to fulfil the order. You can keep these exactly the same for HD and CC flows, or have specific ones for each.

##### short-picking
You might run the sourcing logic and find a location to fulfil the order. However, when the location attempts to pick the order the stock might be unavailable — at this point, this inventory would be recorded as 'short-picked'.  Fluent OrderManagement would update the inventory to type CORRECTION.
At this point, the standard flow would attempt to fulfil the short-picked items from another fulfilment location — this is known as 'reassignment logic' and it re-starts from the Partial Fulfilment part of the flow.

### Inventory reservation
Using the 'best location' example:
* Once this location has been determined, one (or more) fulfilments are created — these are child entities of the original order we created.
* The Fulfilment is fulfilling specific Product quantity in a Fulfilment location. An event is sent from the Order workflow to the Inventory workflow.
* The standard Inventory Workflow creates an Inventory Reservation for the created Fulfilment.
* The Fulfilment ID is referenced in the Inventory Reservation record which can be seen against a specific Inventory Position in the Inventory Catalogue.
* The Inventory Reservation is for a specific inventory position (PRODUCT/LOCATION/QTY) for the Fulfilment quantity and location we created.
* The Inventory Catalogue workflow decrements the on-hand inventory quantity by the quantity that has been reserved — this is reflected in the SOH figure which is recalculated in addition to the Available-to-sell (ATS) quantity for the corresponding Virtual Catalogue which allows a customer can see as ATS on an e-commerce site for example.
* The TYPE column will show the type = RESERVED at this point.
#### Inventory reservation -> to SALE
* If we assume all items were picked successfully, the workflow will change the status of the RESERVATION for the fulfilment to INACTIVE, and then create a new record of type SALE for the same QTY.
* From a SOH balance perspective, this will remain the same except for changing the TYPE to acknowledge that the PICK has finished. There is a slight difference in what triggers the sending of the pick event into Fluent Order Management:
  1. From a Warehouse (or any other external system) which doesn't use Fluent Store — this will be an event received via middleware usually.
  1. If this is an In-Store Pick, then the user action in Fluent Store to confirm pick quantity, will trigger the event to update the status of the fulfilment, and then the inventory catalogue workflow. SALE type and qty will always represent the number of items that were picked.
## Webhooks
**All outbound communication to other systems from Fluent workflows is via a webhook.  The avoid the need for external systems to poll Fluent to be notified of Fluent status changes**
Webhooks are added as rules to certain points in the workflow.
Fluent Orchestration Engine -> posts an event to a configured endpoint, e.g. order status update, shipping notification etc.
* `sendWebhook` rule = lightweight webhook that contains very minimal information such as 'entity' and 'status'.
### Payment capture
If payment capture is happening after a particular event or status in Fluent, e.g. when an order has been dispatched, then Fluent will trigger a webhook out to a middleware/payment service provider to trigger the payment capture process.
### Notifications
Often in the customer journey, there will be touch points where we might want to contact the customer to provide them with information about their order when it is in a certain status. A notification webhook could be triggered from the Order Workflow when we know the order is Ready for Collection.

