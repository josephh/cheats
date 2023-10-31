# Glossary
* `Article` = physical parcel.
* `Available to Promise` (ATP).  
* `Available To Sell` (ATS) ATS = SOH + CONTROLS (ATS takes into account buffers and exclusions (see Controls) that have been applied to a specific Virtual position within a Virtual catalogue).  
* `Backorder` is the process of selling inventory that is not in stock.  
* `Buffer Stock` refers to the number of units below which an item at a particular location will be unavailable for online orders.  
* `Capture` - see Payment Capture
* `Component SDK` enables developers to create new components to extend the functionality of the Fluent web app (Fluent Store)
* `Consignment` = when an article needs to go via a carrier service/ courrier.  (used to track the delivery of an article to the customer's delivery address: consignment reference matches the carrier consignment including the tracking reference and URL).
* `consolidated fulfilment` ?
* `CORRECTION`  Correction quantities are created in Fluent Order Management when a location rejects items, during picking using Fluent OMS or Fluent Store or any other pick/pack tools integrated with Fluent OMS.
Quantity of type CORRECTION is created in Fluent Order Management with a value the same as the quantity rejected by the location for the related Inventory Position.  
Default behaviours are to reset that quantity (set status to INACTIVE) for any incoming Full load for which the “correctedQty” value passed in batch is set to '0' for the related position.  
* `Fulfilment` - location picking and packing the items for an order; note if more than one location is needed to fulfil the entire order, multiple fulfilments get created against the order.  
* `Inventory segmentation` is a capability that allows retailers to set aside a certain amount of inventory to cater to the needs of different channels or groups.  
* `Last on Hand` (LOH) stock positions : the quantity received from inventory systems, such as ERP, warehouse management systems (WMS), point-of-sale, and other third-party vendors’ systems as part of the batch process for a particular Inventory position with location and product combination.  
* `Network` logical grouping of physical locations (stores, warehouses etc)
* `Order` created when a customer _completes_ a purchase on a sales channel connected to Fluent.  Once the order is created in Fluent OMS, the relevant Order workflow kicks off, according to the `order.type`
* `partial fulfilment` when we are unable to fulfil the whole order from a single location.
* `Payment capture` aka `Capture`, aka `Settlement`= comes after authorization.  At 'capture' the payment is effectively "settled," and the transaction is over: it's the point at which you get paid for a sale. It occurs when you initiate the process of moving authorized funds from the cardholder's bank to your merchant account.
* `Reject location` is a system location used to assign rejected fulfilments to another location.
* `RESERVED` Reserved quantities are created as part of the fulfilment reservation process and are not modified by either Deltas or Full loads and this is due to the assumption that only Fluent systems are aware of the inventory reservation until the locations complete the picking — then the assumption is that the location inventory systems will be updated.  It’s also at that point that Fluent OMS reference workflows update the RESERVED quantity to status INACTIVE and create appropriate SALE and/or CORRECTION quantities instead.  
* `Rule SDK` Rules SDK allows developers to write custom Rules to extend their business logic.
* `Sale` quantities are created in Fluent Order Management when a user confirms the 'pick', using Fluent Store or Fluent OMS Web Apps or any other pick/pack tools integrated with Fluent OMS.  
* `Settlement` see `Payment Capture`
* `SKU` Stock Keeping Unit
* `Soft Reservation` refers to the temporary booking of an item to hold stock for a particular amount of time.  
* `Sourcing` search or select best available location to fulfil customer order
* `Split fulfilment` fulfil a single order from multiple fulfilment locations. Each additional location can be considered a split.
* `Stock-on-hand` (SOH) SOH value can be calculated as the sum of all active Inventory quantities for that position.  
* `Virtual Catalogue` (VC).  Note, A VC can only have one network.
