# Notes for running postman queries - get order from CREATED, BOOKED, PENDING_PROCESSING, PROCESSING...
1. Postman, under Simpy order folder, run POST oauth retailer BQUK
1. use that variable storing access token to fluent.retailer.access_token to authorize all other events
    1. For order, ensure amounts are specified for an existing prodct ref (with enough stock, for retailer)
    1. ensure customer is existing customer (for retailer)
1. create order CREATE, then PAYMENT_PENDING
1. send in payment, Order should go to BOOKED, fulfilment goes to ASSIGNED
1. issue get fulfilments by ref query, e.g.
query Fulfilments($ref: [String!]) {
    fulfilments(ref: $ref) {
        edges {
            cursor
            node {
                id
                ref
                status
                fulfilmentChoiceRef
                type
                order{
                    ref
                    status
                }
                items{
                    edges{
                        node{
                            ref
                            id
                            orderItem{
                                id
                                ref
                            }
                        }
                    }
                }
            }
        }
    }
}
<<<<<< variables e.g.
{

  "ref" : ["CI_1690547693238-001"]

}
Fulfilment item query result is used in the pick update event payload; the fulfilment item refs are used as the `skuRef` field value in the pick update AND the `fufilmentItemRef` field value. 
Use the note: this is the same as the attribute value added to the create order, with name "fulfilmentItemRef".
1. send pick update, Order should go to PROCESSING, fulfilment goes to PICK_STARTED


----------- act up to cover Stephen
MRs - are they "good enough"
Fluent. work drying up? Manju to cover?
Monitor tech chat role
Unblocking role
Environment issues - deployments

DLT push failure - a schema has gone on to the topic already

priorities for deployments,
1. 
1. connectors - deploy only to lower environments
1. payment translation into Fluent


2270 - Vlad, pick events

store 1002
retailer 11
