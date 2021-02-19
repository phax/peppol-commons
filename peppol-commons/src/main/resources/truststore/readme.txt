====
    Copyright (C) 2015-2019 Philip Helger (www.helger.com)
    philip[at]helger[dot]com

    The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)

    This Source Code Form is subject to the terms of the Mozilla Public
    License, v. 2.0. If a copy of the MPL was not distributed with this
    file, You can obtain one at http://mozilla.org/MPL/2.0/.
====

2010/pilot-truststore.jks 
  Is the global trust store for OpenPEPPOL pilot and works as well for SML, SMP and AP 
  It is valid from 2010-2020
  The contained aliases are:
  * peppol root test ca
  * peppol access point test ca (peppol root test ca)
  * peppol security token service test ca (peppol root test ca)
  * peppol service metadata publisher test ca (peppol root test ca)

2010/prod-truststore.jks 
  Is the global trust store for OpenPEPPOL production and works as well for SML, SMP and AP
  It is valid from 2010-2020
  The contained aliases are:
  * peppol root ca
  * peppol access point ca (peppol root ca)
  * peppol security token service ca (peppol root ca)
  * peppol service metadata publisher ca (peppol root ca)

2018/pilot-truststore.jks 
  Is the global trust store for OpenPEPPOL pilot and works as well for SML, SMP and AP 
  It is valid from 2018-2028
  The contained aliases are:
  * peppol root test ca - g2
  * peppol access point test ca - g2 (peppol root test ca - g2)
  * peppol service metadata publisher test ca - g2 (peppol root test ca - g2)

2018/prod-truststore.jks 
  Is the global trust store for OpenPEPPOL production and works as well for SML, SMP and AP
  It is valid from 2018-2028
  The contained aliases are:
  * peppol root ca - g2
  * peppol access point ca - g2 (peppol root ca - g2)
  * peppol service metadata publisher ca - g2 (peppol root ca - g2)

sml-truststore.jks (since v6.0.4)
  It contains the SSL certificates to access the central PEPPOL SML
  Updated 2019-12-03 for the new SMK certificate chain

directory-truststore.jks (since v6.2.1)
  It contains the SSL certificates to access the central PEPPOL Directory
  Updated in v6.2.4 to reflect the changes for the new PD hosting
  Updated in v8.4.1 to reflect the new Let's Encrypt issuing certificate

complete-truststore.jks 
  This is the combination of all available truststores with the same aliases!
  Updated in v8.4.1 to reflect the new Let's Encrypt issuing certificate

The password to access all trust stores is (case-sensitive):
peppol

--[EOF]--
