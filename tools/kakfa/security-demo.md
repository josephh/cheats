{
    "erpSalesOrderId": "OM-100001-5378a54f-51a4-4522-a9d7-b5f2eb2c8bfe",
    "sourceSystem": "atg",
    "customer": {
        "externalId": "CUS-OM-RANDOM-00001-5378a54f-51a4-4522-a9d7-b5f2eb2c8bfe",
        "firstName": "testFirstName",
        "email": "testuser@test.com",
        "timezone": "Europe/London"
    },
    "tenant": "bquk",
    "totalPrice": "78.86",
    "lineItems": [
        {
            "lineRef": "test123",
            "productRef": "5020024520808",
            "totalPrice": "4.48",
            "price": "2.24",
            "quantity": 2,
            "fulfilmentChoiceRef": "fcr-6c6d2317-10ec-4992-a739-5d4e5ebc3c43",
            "fulfilmentItemRef": "item-ref-3a473ebe-0164-427c-834c-2bf752f5908f"
        }
    ],
    "fulfilmentChoices": [
        {
            "ref": "fcr-6c6d2317-10ec-4992-a739-5d4e5ebc3c43",
            "type": "HD",
            "deliveryType": "STANDARD",
            "fulfilmentPrice": "5",
            "sourceLocationRef": "1260",
            "deliveryAddress": {
                "ref": "add-1",#
                "name": "Test",
                "street": {
                    "string": "Street 1"
                },
                "street2": {
                    "string": "Street 2"
                },
                "city": "City 1",
                "state": {
                    "string": "State 1"
                },
                "postcode": "RG24FG",
                "country": "UK",
                "email": {
                    "string": "test@gmail.com"
                }
            }
        }
    ]
}

{"erpSalesOrderId":"OM-100002-5378a54f-51a4-4522-a9d7-b5f2eb2c8bfe","sourceSystem":"atg","customer":{"externalId":"CUS-OM-RANDOM-00002-5378a54f-51a4-4522-a9d7-b5f2eb2c8bfe","firstName":"testFirstName","email":"testuser@test.com","timezone":"Europe/London"},"tenant":"tpuk","totalPrice":"102","lineItems":[{"lineRef":"test123","productRef":"5020024520808","totalPrice":"4.48","price":"2.24","quantity":2,"fulfilmentChoiceRef":"fcr-cfda9f2b-745a-4d5d-84f9-937fa98842dd","fulfilmentItemRef":"item-ref-d3b98d12-646a-4f90-b1db-b6178e17a6f5"}],"fulfilmentChoices":[{"ref":"fcr-cfda9f2b-745a-4d5d-84f9-937fa98842dd","type":"HD","deliveryType":"STANDARD","fulfilmentPrice":"0","sourceLocationRef":"1260","deliveryAddress":{"ref":"add-1","name":"Test","street":{"string":"Street 1"},"street2":{"string":"Street 2"},"city":"City 1","state":{"string":"State 1"},"postcode":"RG24FG","country":"UK","email":{"string":"test@gmail.com"}}}]}



# create the certification authority key and certificate 
openssl req -new -nodes \
   -x509 \
   -days 365 \
   -newkey rsa:2048 \
   -keyout ~/Kafka/learn-kafka-courses/fund-kafka-security/ca.key \
   -out ~/Kafka/learn-kafka-courses/fund-kafka-security/ca.crt \
   -config ~/Kafka/learn-kafka-courses/fund-kafka-security/ca.cnf
   
   
# convert those files over to a .pem file:
cat ~/Kafka/learn-kafka-courses/fund-kafka-security/ca.crt ~/Kafka/learn-kafka-courses/fund-kafka-security/ca.key > ~/Kafka/learn-kafka-courses/fund-kafka-security/ca.pem

# Create the server key and certificate 
openssl req -new \
    -newkey rsa:2048 \
    -keyout ~/Kafka/learn-kafka-courses/fund-kafka-security/kafka-1-creds/kafka-1.key \
    -out ~/Kafka/learn-kafka-courses/fund-kafka-security/kafka-1-creds/kafka-1.csr \
    -config ~/Kafka/learn-kafka-courses/fund-kafka-security/kafka-1-creds/kafka-1.cnf \
    -nodes

# sign the certificate with the certificate authority    
openssl x509 -req \
    -days 3650 \
    -in ~/Kafka/learn-kafka-courses/fund-kafka-security/kafka-1-creds/kafka-1.csr \
    -CA ~/Kafka/learn-kafka-courses/fund-kafka-security/ca.crt \
    -CAkey ~/Kafka/learn-kafka-courses/fund-kafka-security/ca.key \
    -CAcreateserial \
    -out ~/Kafka/learn-kafka-courses/fund-kafka-security/kafka-1-creds/kafka-1.crt \
    -extfile ~/Kafka/learn-kafka-courses/fund-kafka-security/kafka-1-creds/kafka-1.cnf \
    -extensions v3_req

# convert the server certificate over to the pkcs12 format
openssl pkcs12 -export \
    -in ~/Kafka/learn-kafka-courses/fund-kafka-security/kafka-1-creds/kafka-1.crt \
    -inkey ~/Kafka/learn-kafka-courses/fund-kafka-security/kafka-1-creds/kafka-1.key \
    -chain \
    -CAfile ~/Kafka/learn-kafka-courses/fund-kafka-security/ca.pem \
    -name kafka-1 \
    -out ~/Kafka/learn-kafka-courses/fund-kafka-security/kafka-1-creds/kafka-1.p12 \
    -password pass:confluent
    
# create the broker keystore and import the certificate
keytool -importkeystore \
    -deststorepass confluent \
    -destkeystore ~/Kafka/learn-kafka-courses/fund-kafka-security/kafka-1-creds/kafka.kafka-1.keystore.pkcs12 \
    -srckeystore ~/Kafka/learn-kafka-courses/fund-kafka-security/kafka-1-creds/kafka-1.p12 \
    -deststoretype PKCS12  \
    -srcstoretype PKCS12 \
    -noprompt \
    -srcstorepass confluent
    
# Verify the kafka-1 broker keystore
keytool -list -v \
    -keystore ~/Kafka/learn-kafka-courses/fund-kafka-security/kafka-1-creds/kafka.kafka-1.keystore.pkcs12 \
    -storepass confluent
    
# save the credentials - password prompt is for laptop superuser pwd :-)
sudo tee ~/Kafka/learn-kafka-courses/fund-kafka-security/kafka-1-creds/kafka-1_sslkey_creds << EOF >/dev/null
confluent
EOF    

sudo tee ~/Kafka/learn-kafka-courses/fund-kafka-security/kafka-1-creds/kafka-1_keystore_creds << EOF >/dev/null
confluent
EOF
    
