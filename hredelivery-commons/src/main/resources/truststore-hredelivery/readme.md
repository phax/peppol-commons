# Content of this directory

Each truststore uses the password `hredelivery` (case sensitive).

`truststore-fina-demo.p12`
* Is the trust store for HR eDelivery demo stage
* The contained aliases are:
    * `fina demo root ca` - Fina Demo Root CA
    * `fina demo ca 2020 (fina demo root ca)` - Fina Demo CA 2020

`truststore-fina-prod.p12`
* Is the trust store for HR eDelivery production stage
* The contained aliases are:
    * `fina root ca` - Fina Root CA
    * `fina rdc 2020 (fina root ca)` - Fina RDC 2020
    * `fina rdc 2025 (fina root ca)` - Fina RDC 2025 (since 12.3.1)
    
# Sources

FINA: 
* Test: https://www.fina.hr/finadigicert/certifikati-za-testiranje-i-demonstraciju/fina-demo-ca-certifikati
* Prod: https://www.fina.hr/eng/finadigicert/ca-certificates
