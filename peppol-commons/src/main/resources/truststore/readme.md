2018/pilot-truststore.jks 
* Is the global trust store for OpenPeppol pilot and works as well for SML, SMP and AP 
* It is valid from 2018-2028
* The contained aliases are:
    * `peppol root test ca - g2`
    * `peppol access point test ca - g2 (peppol root test ca - g2)`
    * `peppol service metadata publisher test ca - g2 (peppol root test ca - g2)`

2018/prod-truststore.jks 
* Is the global trust store for OpenPEPPOL production and works as well for SML, SMP and AP
* It is valid from 2018-2028
* The contained aliases are:
    * `peppol root ca - g2`
    * `peppol access point ca - g2 (peppol root ca - g2)`
    * `peppol service metadata publisher ca - g2 (peppol root ca - g2)`

sml-truststore.jks (since v6.0.4)
* It contains the SSL certificates to access the central PEPPOL SML
* Updated 2019-12-03 for the new SMK certificate chain
* The contained aliases are:
    * `globalsign`
    * `globalsign rsa ov ssl ca 2018 (globalsign)`
    * `globalsign root ca`
    * `globalsign organization validation ca - sha256 - g2 (globalsign root ca)`

directory-truststore.jks (since v6.2.1)
* It contains the SSL certificates to access the central PEPPOL Directory
* Updated in v6.2.4 to reflect the changes for the new PD hosting
* Updated in v8.4.1 (add new) and v8.5.2 (remove old) to reflect the new Let's Encrypt issuing certificate
* The contained aliases are:
    * `isrg root x1`
    * `r3 (isrg root x1)`

complete-truststore.jks 
* This is the combination of all available truststores with the same aliases!
* Updated in v8.4.1 (add new) and v8.5.2 (remove old) to reflect the new Let's Encrypt issuing certificate

The password to access all trust stores is (case-sensitive): `peppol`

The official source of the certificates is https://openpeppol.atlassian.net/wiki/spaces/OPMA/pages/193069072/Introduction+to+the+revised+PKI+Certificate+infrastructure+and+issuing+process
