# Fluent store module
## Store fulfilment
Store Fulfilment module enables stores as fulfilment locations, by bringing Pick, Pack and Dispatch capabilities to the store context.  It also allows store staff to manage collections and arrivals in the store.
### Features
* Pick, Pack and Dispatch
* Arrivals
* Customer Collections
* Carrier (or Courier) Collections
* Store fulfilment options
* Store-to-Store transfers
* Ship-to-Store
* Fulfil-from-Store
* Ship-from-Store (or Home Delivery)
* In-store Pick-up (or Click and collect)
## Fluent store
'Fluent Store' is the in-store responsive web application...
## Entities
* Fulfilment
* Wave
* Article
* Consignment
### Pick, pack and dispatch wave process
▶ Wave prioritisation criteria prioritise fulfilments due to 'expire soon'

Waves can be created with a specified number of orders (fulfilments), and selected based on various criteria. The sample Location workflow prioritises fulfilments that are due to expire soon to ensure that the order gets fulfilled by the location within the specified time limit.

▶ What happens if the expiry time is reached before the store has allocated the fulfilment to a Wave?

If the expiry time is reached before the store has allocated the fulfilment to a wave for picking, the Order workflow would source a new fulfilment location for those items.

Once a Wave is created a Store Assistant can begin picking...
### Picking
1. fulfilments are assigned to various locations based on the sourcing logic in the Order workflow — that is, they are assigned to stores that show available stock at the time of assignment.
1. Once a fulfilment is assigned to a store, that fulfilment shows up in that store's 'ORDERS AWAITING PICK' screen — the fulfilment is now ready to be assigned to a Wave for picking.
    1. When the Store Associate creates the Wave, Order status will be updated to PICK_PACK and respective fulfilments will be in ASSIGNED status.
    1. Inventory Quantity type will still be in RESERVED.
### Short pick
1. Order status remains in PICK_PACK and respective fulfilments will be in PARTIALLY_FULFILLED  stats.
1. A new Inventory Quantity type SALE is created and will be in ACTIVE status.
1. A new Inventory Quantity type CORRECTION will be created and will be in ACTIVE status. — this is for recording what has been short-picked.
1. Inventory quantity type RESERVED will be moved to INACTIVE status.
1. In the reference workflow, a new fulfilment will be created for the short picked item with status either ESCALATED (Rejected Location) or ASSIGN / AWAITING_WAVE (next available location).
### Packing
1. Once packing is complete, Order status remain in PICK_PACK and respective fulfilments will be in FULFILLED  status
1. Packing slip will be generated and the order is ready for dispatch.
### Dispatch
1. Status of fulfilment while in the storage area waiting for collection: AWAITING_CUTOMER_COLLECTION
### Carrier Collection
1. Once the carrier has acknowledged receiving the consignment details Fulfilment Status changes to: AWAITING_COURIER_COLECTION
1. Consignment Status: ACTIVE_LODGED
1. Wave workflow can update the Wave status to Complete.
1. Fulfilment has now moved to FULFILLED status — awaiting carrier to accept the request
## Status updates
1.  Create Wave step is complete
    1. When the Store Associate creates the Wave, the Order status will be updated to PICK_PACK and respective fulfilments will be in ASSIGNED status.
    1. Inventory Quantity type will still be in RESERVED status.
1.  Pick step is complete:
    1. When the Store Associate completely picks the items on the order Order status remains in PICK_PACK and respective fulfilments will be in FULFILLED  status.
    1. Inventory Quantity type will move to SALE and will be in ACTIVE status.
    1. Inventory quantity type RESERVED will be moved to INACTIVE status.
1. Pack step is complete
    1. When the Store Associate packs the order, Order status remains in PICK_PACK and respective fulfilments will be in FULFILLED status.
    1. Inventory Quantity type will move to SALE and will be in ACTIVE status.
    1. Inventory quantity type RESERVED will be moved to INACTIVE status.
    1. Packing slip will be generated and the order is ready to dispatch.
1. Once the order items are packed and the Store Associate clicks on BOOK Courier,  if the order type is:
    1. Click and Collect (CC), the Parcel (or Article) will be moved to the pick-up storage area.
    1. Home Delivery (HD), the parcel (or Article) will be moved to the courier collection storage area.
## Arrivals
Article (or Parcel)
Storage Area
Label
Collection Point / Pickup Location
Fulfilment Location
Consolidation Location
Arrivals





Fulfil CC Order with Store-to-Store transfer


Create a Wave to fulfil the order at North Sydney - how do i do that?!


The Customer Collection feature is NOT applicable to Click & Collect order flow. Store users are required to mark off each order collected by customers in a sheet
