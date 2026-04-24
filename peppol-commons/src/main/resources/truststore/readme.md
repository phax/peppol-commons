# Content of this directory

## External components

`truststore/sml-truststore.jks` (since v6.0.4)
* It contains the SSL certificates to access the central Peppol SML
* Updated 2019-12-03 for the new SMK certificate chain
* Updated 2024-01-02 removed the old GlobalSign certificates
* Updated in v12.0.3: added `globalsign atlas r3 ov tls ca 2025 q3 (globalsign)` 
* Updated in v12.4.2: added `globalsign gcc r3 dv tls ca 2020 (globalsign)` 
* The contained aliases are:
    * `globalsign`
    * `globalsign rsa ov ssl ca 2018 (globalsign)`
    * `globalsign atlas r3 ov tls ca 2025 q3 (globalsign)`
    * `globalsign gcc r3 dv tls ca 2020 (globalsign)`

`truststore/directory-truststore.jks` (since v6.2.1)
* It contains the SSL certificates to access the central Peppol Directory
* Updated in v6.2.4 to reflect the changes for the new PD hosting
* Updated in v8.4.1 (add new) and v8.5.2 (remove old) to reflect the new Let's Encrypt issuing certificate
* Updated in v11.0.6: replaced `r3 (isrg root x1)` with `e5 (isrg root x1)`
* The contained aliases are:
    * `isrg root x1`
    * `e5 (isrg root x1)`
    
## Peppol G3 (2025)

The password to access all trust stores is (case-sensitive): `peppol`

The official source of the certificates is https://openpeppol.atlassian.net/wiki/spaces/OPMA/pages/4344053761/Peppol+PKI+2025+-+Certificate+Authorities

`truststore/2025/ap-test-truststore.p12` (since 11.0.2)
* Is the global trust store for OpenPeppol pilot APs
* It is valid from 2025-2035
* The contained aliases are:
    * `peppol root test ca - g3`
    * `peppol access point test ca - g3 (peppol root test ca - g3)`
    * `peppol service metadata publisher test ca - g3 (peppol root test ca - g3)`

`truststore/2025/ap-prod-truststore.p12` (since 11.0.2)
* Is the global trust store for OpenPeppol production APs
* It is valid from 2025-2035
* The contained aliases are:
    * `peppol root ca - g3`
    * `peppol access point ca - g3 (peppol root ca - g3)`
    * `peppol service metadata publisher ca - g3 (peppol root ca - g3)`

`truststore/2025/smp-test-truststore.p12` (since 11.0.2)
* Is the global trust store for OpenPeppol pilot SMPs
* It is valid from 2025-2035
* Updated in v11.0.6: replaced `r3 (isrg root x1)` with `e5 (isrg root x1)`
* Updated in v12.0.3: added `globalsign atlas r3 ov tls ca 2025 q3 (globalsign)` 
* Updated in v12.4.2: added `globalsign gcc r3 dv tls ca 2020 (globalsign)` 
* The contained aliases are:
    * `peppol root test ca - g3`
    * `peppol access point test ca - g3 (peppol root test ca - g3)`
    * `peppol service metadata publisher test ca - g3 (peppol root test ca - g3)`
    * `globalsign`
    * `globalsign rsa ov ssl ca 2018 (globalsign)`
    * `globalsign atlas r3 ov tls ca 2025 q3 (globalsign)`
    * `globalsign gcc r3 dv tls ca 2020 (globalsign)`
    * `isrg root x1`
    * `e5 (isrg root x1)`

`truststore/2025/smp-prod-truststore.p12` (since 11.0.2)
* Is the global trust store for OpenPeppol production SMPs
* It is valid from 2025-2035
* Updated in v11.0.6: replaced `r3 (isrg root x1)` with `e5 (isrg root x1)`
* Updated in v12.0.3: added `globalsign atlas r3 ov tls ca 2025 q3 (globalsign)` 
* Updated in v12.4.2: added `globalsign gcc r3 dv tls ca 2020 (globalsign)` 
* The contained aliases are:
    * `peppol root ca - g3`
    * `peppol access point ca - g3 (peppol root ca - g3)`
    * `peppol service metadata publisher ca - g3 (peppol root ca - g3)`
    * `globalsign`
    * `globalsign rsa ov ssl ca 2018 (globalsign)`
    * `globalsign atlas r3 ov tls ca 2025 q3 (globalsign)`
    * `globalsign gcc r3 dv tls ca 2020 (globalsign)`
    * `isrg root x1`
    * `e5 (isrg root x1)`

### For Access Points

Peppol APs need the following trust stores:
* Production: `truststore/2025/ap-prod-truststore.p12`
* Test: `truststore/2025/ap-test-truststore.p12`

### For SMPs

Peppol SMPs need the following trust stores:
* Production: `truststore/2025/smp-prod-truststore.p12`
* Test: `truststore/2025/smp-test-truststore.p12`
