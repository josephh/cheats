
# Functional Module - Inventory course
At the end of this training, participants should be able to:
* Understand the Inventory domain model
* Be familiar with the Product ​&​ Inventory Catalogues module in Fluent OMS
* Be familiar with the Inventory Catalogue workflow features including: Batch Inventory Load, Calculation of Stock On Hand,  and interaction with other workflows
* Understand the concept and usage of Virtual Catalogues, Virtual Positions and how available to sell quantities are calculated and represented in Fluent
* Be familiar with creating or updating safety stock and exclusion controls to modify available to sell inventory

Inventory Batch Process: Updates of last on hand (LOH) (i.e. update from SAP or wherever)
"Rules SDK" = business logic
"Component SDK" = extend the functionality of Fluent web apps

 
## Inventory Catalogue workflow
updates inventory
Deltas and LOHs go to the inventory update workflow.
"Virtual catalogue" is calculated from inventory catalogue + "control groups" (control groups provide buffers and exclusions processed by the virtual catalogue workflow).
Full loads - one offs or periodic reconciliations
Deltas - more regular stock changes

Inventory Catalogue Workflow receives LOH (last on-hand) quantities from inventory source systems.
* Inventory Catalogue workflow receives updates from the Product Catalogue for any active or inactive products and updates from the Order Management Workflow.
* Stock reservations, sales or cancellations for any inflight orders will be sent as Inventory Updates into the Inventory Catalogue Workflow.  

Inventory Catalogue Workflow updates the inventory positions within it based on the information being received into the workflow and uses that to calculate the stock on hand.

For Batch uploads, the DEFAULT inventory catalogue workflow should be used.
Additionally, the Inventory Catalogue reference follows the following convention for the batch support DEFAULT typed catalogues:
```
DEFAULT:[retailer_id] for example, DEFAULT:1 is the default Inventory catalogue owned by Retailer 1. This catalogue can share inventory with other retailers.
```

### Batch
Inventory BATCH API; Inventory type = `LAST_ON_HAND`.   ALWAYS an absolute whole number. e.g.
```
{"locationRef": "LOC_1",  
"skuRef": "SKU_1",  
"qty": 400,  
"correctedQty": 0,  
"retailerId": {{retailer_id}}}
```
### Delta
Inventory type = `DELTA`.  Real-time events for inventory deltas into Fluent using the EVENT API.  When sending real-time Deltas via the Event API the quantity is always reflected in a plus or minus number rather than an absolute value, for example '20'.
```
{ "skuRef": "SKU_1",            
"locationRef": "LOC_1",           
 "quantity": -5    }
 ```

## Virtual catalogue workflows
Virtual Catalogue Workflow calculates virtual inventory positions and updates the Virtual Catalogue (prevents overselling or underselling)

##  Product Catalogues
A retailer can have one (or more) Product Catalogues that will contain all of their products. Each Product Catalogue type has a corresponding workflow, referred to as 'Product Catalogue Workflow' which is used to orchestrate products in the Product Catalogue.
Note: Fluent Order Management is not the master source of product information!  (Fluent Order Management only requires a subset of information to identify and differentiate one product from another).
### Product Catalogue Entities
Retailers decide how they want to "model" their products - matching how they do business up with Fluent's model (the most common scenarios involve 'Standard' and 'Variant' relationships, or 'variant only.' Currently, the reference workflows provided by the inventory module calculate ATS and reserve inventory based on Variant Products.).
* Standard Product - includes: sellable non-variant product; non-sellable base for a variant product; sellable or non-sellable component of a group product.
* Variant Product : An entity that holds additional attributes for variation of a base product such as size, colour, volume, etc. The base product is non-sellable and stored as a Standard Product.
* Group Product: An entity to model bundle products, which is a parent product to a number of standard and/or variant products (like a bill of materials) NOTE  not currently supported by the Inventory Module.
* Categories and Sub-categories: Products can be associated with categories and sub-categories.

## Inventory Module Rules
1. If Product (Standard or Variant) or Location, for the Inventory Position, is INACTIVE, then the status of the Inventory Position will also become INACTIVE.  Only ACTIVE Inventory Quantities will be considered in the onHand calculation (i.e. RESERVE, CORRECTION, DELTA, SALE).
1. **Reservations**.  An event is sent from the ORDER WORKFLOW, to the INVENTORY WORKFLOW.  The standard Inventory Workflow creates an Inventory Reservation for the created FULFILMENT (note: for the fulfilment, not the order!!!). The Fulfilment ID is referenced in the Inventory Reservation record which can be seen against a specific Inventory Position in the Inventory Catalogue. The Inventory Reservation is for a specific inventory position (PRODUCT/LOCATION/QTY) for the Fulfilment quantity and location we created. The Inventory Catalogue Workflow decrements the on-hand inventory quantity by the quantity that has been reserved — this will be reflected in the SOH figure which is recalculated, as is the ATS (available to sell) quantity for the corresponding Virtual Catalogue that a customer can see as ATS on the website for example.  **The TYPE column will show the type as RESERVED at this point.**
  * Status starts off as ACTIVE when an order is created and fulfilment(s) have been created.  
  * Changes to INACTIVE when either Fulfilment to which the Inventory Position applies is fulfilled; or Order/Fulfilment is cancelled.
1. **Sale** when items picked, Order workflow, Fulfilment entity will receive notification event for product/qty.  If all picked successfully, workflow changes status of RESERVATION for the fulfilment to INACTIVE, and then create a new record of type SALE for the same QTY. SOH stays the same but SOH balance perspective, this will remain the same, but there will be a change in the TYPE to acknowledge that the PICK has finished.
Picks can come in either,
  1. If Warehouse pick (or any other external system) does not use Fluent Store, then this will be an event received in via middleware usually.
  1. If this is an In-Store Pick, then the user action in Fluent Store - to confirm pick quantity - will trigger the event to update the status of the fulfilment, and then the inventory catalogue workflow.   
  * ACTIVE Status when the Variant Product in the corresponding fulfilment is successfully fulfilled (I.e. picked and packed)
  * Changed to INACTIVE status when an Inventory Batch is processed that contains the corresponding Inventory Position. Note: delta inventory events have no effect.
1. **Correction** At picking time, if product turns out to be unavailable - this is a "short pick"; in this case Fluent updates inventory to type CORRECTION.  We want to acknowledge the inventory is no longer reserved, or sold, but we also want to make sure that the ATS (available to sell) is decremented to reflect that this stock is no longer available.
  * ACTIVE status when the Variant Product in the corresponding fulfilment is rejected.
  * Changed to INACTIVE status when the correctedQty of the corresponding Inventory Position is set to zero in an Inventory Batch. Note: delta inventory events have no effect.
1. **Delta** Inventory Workflow will receive an event via the event API for a delta inventory change, inventory type = DELTA. This would typically represent an item a store has just sold.   Fluent Order Management will update the SOH in the Inventory Catalogue - decrementing it by -1. Inventory catalogue updates will trigger corresponding updates to virtual catalogues...
  *  ACTIVE status when a delta event is processed that contains the corresponding Inventory Position. Note: a new Inventory Quantity is not created when a subsequent delta is processed. It will accumulate into the same Inventory Quantity.
  * Changed to INACTIVE status when an Inventory Batch is processed that contains the corresponding Inventory Position.

## Virtual Catalogues (continued)
A Virtual Catalogue itself is linked to a Network.
An inventory search for a network will be done against the virtual catalogue.
Retailers can have one (or many) Virtual Catalogues, and use them to segment their inventory to give different views of availability to different channels or customer segments. This can be for several reasons such as segmentation by for example:
* geographical region
* order type (Click and Collect (CC) or Home Delivery (HD))
* by marketplace

## Control Groups & Controls
Control Groups are 'containers' for Controls (buffers and exclusions). A Control Group can be associated with a Virtual Catalogue.  A 'Control' represents a particular buffer or exclusion which help determine what inventory is available in a Virtual Catalogue
-- examples of Control Group types = 'QUANTITY_BUFFERS', 'EXCLUSION'
* 'Buffer Stock' refers (aka "safety stock") to the number of units below which an item at a particular location will be unavailable for online orders.   
* 'Exclusions' let you specify which inventory positions should be excluded from a Virtual Catalog (e.g.s Limit the range of products you offer for sale on a marketplace.  Expand into new geographic regions with a limited product range.)
### Controls
1. 'Product buffer' is applied for a particular Product across all locations
1. 'Product-Location buffer is applied at a Product and location level
1. 'Category buffers' are applied against products that belong to a specific category
1. 'Location buffers' are applied for all products at a particular location only
1. 'Product Exclusions' are the highest specific control that can be applied to a Product.
1. 'Category Exclusions' are the highest specific control that can be applied to a Category  
Controls are applied via an integer value priority.  LOWEST value integer is determined as 'highest priority' and is the first eligible control to be applied.  
If there is more than one matching control during a virtual position update (e.g. a matching product buffer and a location buffer) only the control with the lowest execution order value is applied.

## Fulfilment Reservation=When orders are placed from an e-commerce platform and sent to Fluent OMS
When orders are placed from an e-commerce platform and sent to Fluent OMS, inventory will be reserved for the ordered products based on designed orchestration logic.  Both the Order workflow and the fulfilment entity send an event to the Inventory workflow to reserve the inventory.  
* Inventory quantity of RESERVED will be created for the available quantity and STOCK_ON_HAND is re-calculated — this then sends the inline event to the Virtual Catalogue for AVAILABLE_TO_SELL calculation.

## Fulfilment Confirmation=When orders are picked and packed in stores based on the availability of inventory.
* Store Fulfillment module sends an inline event (UPDATE_INVENTORY) to Inventory Catalogue for confirmation of the ordered quantity.
* Inventory quantity of SALE will be created for the available quantity.
STOCK_ON_HAND (SOH) is calculated which sends the inline event to the Virtual Catalogue for AVAILABLE_TO_SELL (ATS) calculation.
* If Fulfilment confirmation happens within Fluent Store there is a user action to confirm what was picked — this event is sent from the Location --> Store workflow -->  Inventory Catalogue workflow.
* If Fulfilment Confirmation does not happen within Fluent Store, an external event, called a 'Pick Confirmation' is sent into the Order Workflow to confirm what has been picked — this then triggers an event to be sent to the Inventory Catalogue Workflow.

## Fulfilment Cancellation=when a whole order is cancelled
All of the associated fulfilments for the order will be cancelled and the inventory reservations will be reset with ATS updated, in order to free up the available stock for future orders placed.
* The Store Fulfilment module sends a reference event (UPDATE_INVENTORY) to Inventory Catalogue for confirmation of the ordered quantity.
* The inventory quantity of RESERVED will be updated to INACTIVE for the entire or partial quantity and SALE will be created for the confirmed (partially fulfilled) quantity.
* STOCK_ON_HAND (SOH) is calculated which sends typically sends an event to notify the Virtual Catalogue(s) to recalculate the affected virtual positions, thereby updating the ATS.

# Modelling
## Inventory Segmentation=virtual division of inventory
helps set aside a certain amount of inventory to cater to the needs of different channels or groups...ensures that each channel has a sufficient amount of inventory to satisfy demand.
* Use VC to implement segmentation
* Control Groups associated with each Virtual Catalogue can also be created, in addition to Controls of type 'percentage buffer'  to segment the inventory.  E.g. 50% for EComm; 20% for Retail; 30% for Wholesale.
## overselling
To reduce the likelihood of overselling due to stock inaccuracies, a Control can be used to reduce the quantity visible as ATS (available-to-sell) and prevent overselling.

Reference Controls can be applied at different levels of hierarchy depending on the retailer's requirement.

### Practical - load full inventory BATCH
In this lab, you will load an inventory file via the Fluent Batch API.

The Workflow Engine will process the batch, by sending events to the Inventory Catalogue workflow.

Subsequently, the Inventory Workflow will create or update the Inventory Position, the Last on Hand (LOH) quantity, calculate the SOH, and fire an event to notify the Virtual Catalogue workflow to process.

The Virtual Catalogue Workflow will then create or update the Virtual Position, apply Controls (Buffers & Exclusions), and calculate the ATS.

Learn how an inventory batch load gets processed by the Inventory workflows to create or update inventory positions, re/calculate the SOH, create or update associated virtual positions in all related virtual catalogues and re/calculate the ATS.
