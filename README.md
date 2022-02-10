# US-06

## Installation

Run the file ProjectIntegratorApplication and the API will be available at port 8085. Make sure you create the database and register the credentials in the application.properties file

## Usage

A exemplo of Postman collection can be found in ./us06-docs.

### /outbound/list

In postman, make a GET request at http://localhost:8085/outbound/list to receive all orders ready for shipment. The response payload is below.

```
[
    id_exemple_1,
    id_exemple_2
]
```

### /outbound/?idCarrinho=id_exemple

In postman, make a GET request at http://localhost:8085/outbound/?idCarrinho=id_exemple to receive all the products in a cart. The lot reported is the one with the closest expiration date and the one most likely to be delivered to avoid waste.

```
{
    "id_order": [
        {
            "seller_id": "foo",
            "product_id": "foo",
            "batch_id": "foo",
            "quantity": "foo",
            "expiration_data": "foo"
        }
    ]
}
```

### /outbound/checkout?idCarrinho=id_exemple

In the postman, make a GET request at http://localhost:8085/checkout?idCarrinho=id_exempleto outbound the request. The pedido_status of the given id will be updated.

```
{
    "id_order": "foo",
    "order_date": "foo",
    "buyer_Id": "foo",
    "statusPedido": {
        "status_id": "foo",
        "statusCode": "OUTBOUND"
    }
}
```
