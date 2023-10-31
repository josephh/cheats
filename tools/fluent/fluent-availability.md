
# Functional Module - Availability course
Fluent definition of availability includes:
What? / When? / How to deliver? / total cost? / Where is it?
* Availability calculation = product + customer location + delivery method.
* Availability ETA = product + customer location + delivery method.
## What attributes are important?
1. accurate Availability, including availability of related products ("up sell") and including store locations + proximity
1. clarity of delivery options - or fulfilment options at PDP (avoid customers abandoning purchase/ promote conversion)
1. reliable postal/ shipping costs  
1. reliable delivery dates (better customer sat and retention)  
1. product options ("variants")
1. "backorders" i.e. purchase an item not currently in stock (but that will be available following restocking) :information: note this is an SDK extension feature
1. "When multiple items are in the cart —  show if there are splits in the fulfilment plan"; "Show aggregated in stock, low stock, or out of stock on the PLP"; "preorder info"; "soft reserves"
## Operation
Availability Module (AM) uses a fulfilment options workflow (w/f), which interacts with Inventory module and sourcing logic to get plans via VC.
### Execution Flow?
1. ecommerce site sends realtime availability call to Fluent for a particular product : Fulfilment Options request.
1. workflow looks up ATS in VC
1. fulfilment plan created -> returned to ecommerce site via API
### Fulfilment options graphql
note in following:
* fulfilment option `type`="HD", and
* `orderType`="HD"
* specifies a product and quantity
* it's home delivery, therefore includes address - allowing for proximity calculations
* `executionMode`="AWAIT_ORCHESTRATION" means do this work in sync with workflow execution
```json  
mutation GetHDFulfilmentOption ($retailerId: Int!, $ref: String!, $productRef: String!, $requestedQty: Int!) {
    createFulfilmentOption (input: {
        ref: $ref,
        type: "HD",
        orderType: "HD",
        retailerId: $retailerId,
        address: {
          	name: "{{$randomFirstName}} {{$randomLastName}}",
        	addressLine1: "24 Pacific Highway",
        	city: "Wahroonga",
        	postcode: "2076",
        	state: "NSW",
        	country: "Australia"
        }
        products: {
          requestedQuantity: $requestedQty,
          productRef: $productRef
        }
    }, executionMode: AWAIT_ORCHESTRATION
    ) {
        id
        status
        plans {
            edges {
                node {
                    ref
                    status
                    eta
                    type
                    fulfilments {
                        fulfilmentType
                        locationRef
                        items {
                            productRef
                            availableQuantity
                            requestedQuantity
                        }
                    }
                }
            }
        }
    }
}
```

```sh
--data-raw '{"query":"mutation GetHDFulfilmentOption ($retailerId: Int!, $ref: String!, $productRef: String!, $requestedQty: Int!) {\n    createFulfilmentOption (input: {\n        ref: $ref, \n        type: \"HD\", \n        orderType: \"HD\", \n        retailerId: $retailerId, \n        address: {\n          \tname: \"Ray Champlin\",\n        \taddressLine1: \"24 Pacific Highway\",\n        \tcity: \"Wahroonga\",\n        \tpostcode: \"2076\",\n        \tstate: \"NSW\",\n        \tcountry: \"Australia\"\n        }\n        products: {\n          requestedQuantity: $requestedQty, \n          productRef: $productRef\n        }\n    }, executionMode: AWAIT_ORCHESTRATION\n    ) {\n        id\n        status\n        plans {\n            edges {\n                node {\n                    ref\n                    status\n                    eta\n                    type\n                    fulfilments {\n                        fulfilmentType\n                        locationRef\n                        items {\n                            productRef\n                            availableQuantity\n                            requestedQuantity\n                        }\n                    }\n                }\n            }\n        }\n    }\n}","variables":{"retailerId":1,"ref":"FO_3793","productRef":"AH8050-001","requestedQty":1}}'
```
Compare with
* fulfilment option `type`="CC", for click n collect
* `orderType`="HD"
* specifies a product and quantity
* it's home delivery, therefore includes address - allowing for proximity calculations
* `executionMode`="AWAIT_ORCHESTRATION" means do this work in sync with workflow execution
