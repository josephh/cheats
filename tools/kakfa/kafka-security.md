# Kafka Security
## Tutorial
[Tutorial](https://docs.confluent.io/platform/current/security/security_tutorial.html#overview) uses SASL/PLAIN (or PLAIN), a simple username/password authentication mechanism typically used with TLS encryption to implement secure authentication.
**_For production deployments of Confluent Platform, SASL/GSSAPI (Kerberos) or SASL/SCRAM is recommended._**

:information: If you use a backslash character in property values, you must escape the backslash character when entering a value in a Confluent Platform configuration file or when using Confluent CLI.

## SSL Keys and Certs
### Create certificate authority and a CA-key to sign certificates

Each machine in the cluster has a public-private key pair, and a certificate to identify the machine. The certificate, however, is unsigned, which means that an attacker can create such a certificate to pretend to be any machine...so prevent forged certificates by signing them - via Certificate Authority - for each machine in the cluster.
The keystore stores each machine’s own identity. The truststore stores all the certificates that the machine should trust. Importing a certificate into one’s truststore also means trusting all certificates that are signed by that certificate.

Confluent provide a script that could be adapted...to automate these steps https://github.com/confluentinc/confluent-platform-security-tools/blob/master/kafka-generate-ssl.sh


 PEM pass phrase: 1202tsuguA
 keystore password: 1202tsuguA

Two files were created:
 - truststore/ca-key -- the private key used later to
   sign certificates
 - truststore/ca-cert -- the certificate that will be
   stored in the trust store in a moment and serve as the certificate
   authority (CA). Once this certificate has been stored in the trust
   store, it will be deleted. It can be retrieved from the trust store via:
   ```$ keytool -keystore <trust-store-file> -export -alias CARoot -rfc```

Now the trust store will be generated from the certificate.
...
Continuing with:
 - trust store file:        truststore/kafka.truststore.jks
 - trust store private key: truststore/ca-key

 All done!

Delete intermediate files? They are:
 - 'ca-cert.srl': CA serial number
 - 'cert-file': the keystore's certificate signing request
   (that was fulfilled)
 - 'cert-signed': the keystore's certificate, signed by the CA, and stored back
    into the keystore

### Fetch CA back out of 'truststore' to generate more certs
export the original CA from the truststore - needed to sign other certs
> keytool -keystore truststore/kafka.truststore.jks -export -alias CARoot -rfc
## Generate the key and the certificate for each Kafka broker
> keytool -keystore kafka.server.keystore.jks -alias server -keyalg RSA -genkey


## Turn OFF sever hostname verification
> add to sever.properties: ssl.endpoint.identification.algorithm=
## Generate key and cert for each Kafka broker in cluster
> keytool -keystore kafka.server.keystore.jks -alias localhost -keyalg RSA -genkey


distinguished name? (X500 'dname')
CN=j,OU=it,O=valtech,L=salisbury-,ST=wilts,C=en
