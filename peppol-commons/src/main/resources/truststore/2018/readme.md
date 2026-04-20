## Peppol G2 (2018) - No longer relevant

**For historical reasons only**

The password to access all trust stores is (case-sensitive): `peppol`

The official source of the certificates is https://openpeppol.atlassian.net/wiki/spaces/OPMA/pages/193069072/Introduction+to+the+revised+PKI+Certificate+infrastructure+and+issuing+process

`truststore/2018/pilot-truststore.jks`
* Is the global trust store for OpenPeppol pilot APs
* It is valid from 2018-2028
* The contained aliases are:
    * `peppol root test ca - g2`
    * `peppol access point test ca - g2 (peppol root test ca - g2)`
    * `peppol service metadata publisher test ca - g2 (peppol root test ca - g2)`

`truststore/2018/prod-truststore.jks`
* Is the global trust store for OpenPeppol production APs
* It is valid from 2018-2028
* The contained aliases are:
    * `peppol root ca - g2`
    * `peppol access point ca - g2 (peppol root ca - g2)`
    * `peppol service metadata publisher ca - g2 (peppol root ca - g2)`

`truststore/2018/smp-pilot-truststore.jks` (since 8.6.4)
* Is the global trust store for OpenPeppol pilot SMPs
* It is valid from 2018-2028
* Updated 2024-01-02 removed the old GlobalSign certificates
* Updated in v11.0.6: replaced `r3 (isrg root x1)` with `e5 (isrg root x1)`
* Updated in v12.0.3: added `globalsign atlas r3 ov tls ca 2025 q3 (globalsign)` 
* The contained aliases are:
    * `peppol root test ca - g2`
    * `peppol access point test ca - g2 (peppol root test ca - g2)`
    * `peppol service metadata publisher test ca - g2 (peppol root test ca - g2)`
    * `globalsign`
    * `globalsign rsa ov ssl ca 2018 (globalsign)`
    * `globalsign atlas r3 ov tls ca 2025 q3 (globalsign)`
    * `isrg root x1`
    * `e5 (isrg root x1)`

`truststore/2018/smp-prod-truststore.jks` (since 8.6.4)
* Is the global trust store for OpenPeppol production SMPs
* It is valid from 2018-2028
* Updated 2024-01-02 removed the old GlobalSign certificates
* Updated in v11.0.6: replaced `r3 (isrg root x1)` with `e5 (isrg root x1)`
* Updated in v12.0.3: added `globalsign atlas r3 ov tls ca 2025 q3 (globalsign)` 
* The contained aliases are:
    * `peppol root ca - g2`
    * `peppol access point ca - g2 (peppol root ca - g2)`
    * `peppol service metadata publisher ca - g2 (peppol root ca - g2)`
    * `globalsign`
    * `globalsign rsa ov ssl ca 2018 (globalsign)`
    * `globalsign atlas r3 ov tls ca 2025 q3 (globalsign)`
    * `isrg root x1`
    * `e5 (isrg root x1)`

`truststore/2018/eb2b-ap-pilot-truststore.jks` (since 9.6.0) (deperecated since v10.4.2)
* Is the global trust store for OpenPeppol pilot eB2B APs
* It is valid from 2024-2028
* The contained aliases are:
    * `peppol root ca - g2`
    * `peppol eb2b access point test ca - g2 (peppol root test ca - g2)`
    * `peppol service metadata publisher ca - g2 (peppol root ca - g2)`

`truststore/2018/eb2b-ap-prod-truststore.jks` (since 10.0.1) (deperecated since v10.4.2)
* Is the global trust store for OpenPeppol production eB2B APs
* It is valid from 2024-2028
* The contained aliases are:
    * `peppol root ca - g2`
    * `peppol eb2b access point ca - g2 (peppol root ca - g2)`
    * `peppol service metadata publisher ca - g2 (peppol root ca - g2)`

`truststore/2018/complete-truststore.jks` (deprecated)
* This is the combination of all available truststores with the same aliases!
* Updated in v9.6.0 to include eB2B AP Test CA
* Updated in v8.4.1 (add new) and v8.5.2 (remove old) to reflect the new Let's Encrypt issuing certificate
* Updated 2024-01-02 removed the old GlobalSign certificates
* Updated in v11.0.6: replaced `r3 (isrg root x1)` with `e5 (isrg root x1)`
    * Updated in v12.0.3: added `globalsign atlas r3 ov tls ca 2025 q3 (globalsign)` 
* `peppol root ca - g2`
    * `peppol access point ca - g2 (peppol root ca - g2)`
    * `peppol service metadata publisher ca - g2 (peppol root ca - g2)`
    * `peppol root test ca - g2`
    * `peppol access point test ca - g2 (peppol root test ca - g2)`
    * `peppol eb2b access point test ca - g2 (peppol root test ca - g2)`
    * `peppol service metadata publisher test ca - g2 (peppol root test ca - g2)`
    * `globalsign`
    * `globalsign rsa ov ssl ca 2018 (globalsign)`
    * `globalsign atlas r3 ov tls ca 2025 q3 (globalsign)`
    * `isrg root x1`
    * `e5 (isrg root x1)`

### For Access Points

Peppol APs need the following trust stores:
* Production: `truststore/2018/prod-truststore.jks`
* Test: `truststore/2018/pilot-truststore.jks`

### For SMPs

Peppol SMPs need the following trust stores:
* Production: `truststore/2018/smp-prod-truststore.jks`
* Test: `truststore/2018/smp-pilot-truststore.jks`
