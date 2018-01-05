====
    Copyright (C) 2015-2018 Philip Helger (www.helger.com)
    philip[at]helger[dot]com

    The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)

    This Source Code Form is subject to the terms of the Mozilla Public
    License, v. 2.0. If a copy of the MPL was not distributed with this
    file, You can obtain one at http://mozilla.org/MPL/2.0/.
====

global-truststore.jks 
  Is the global trust store for OpenPEPPOL production and works as well for SML, SMP and AP 
  The contained aliases are:
  * peppol root ca
  * peppol access point ca (peppol root ca)
  * peppol security token service ca (peppol root ca)
  * peppol service metadata publisher ca (peppol root ca)

pilot-truststore.jks 
  Is the global trust store for OpenPEPPOL pilot and works as well for SML, SMP and AP 
  The contained aliases are:
  * peppol root test ca
  * peppol access point test ca (peppol root test ca)
  * peppol security token service test ca (peppol root test ca)
  * peppol service metadata publisher test ca (peppol root test ca)

complete-truststore.jks 
  This is the combination of global-truststore.jks and pilot-truststore.jks

The password to access all trust stores is:
peppol

--[EOF]--