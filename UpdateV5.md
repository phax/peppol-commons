#Updating to Version 5.x

This document shows what you need to do in order to upgrade from 4.x to 5.x.

##Identifiers
Because in 4.x the identifiers were very complex and no clear separation between PEPPOL and non-PEPPOL requirements was made, I decided (also because of https://github.com/phax/peppol-smp-server/issues/20) to restructure things.

Therefore the PEPPOL specific logic was removed from the `Simple...Identifier` classes and moved to the new `Peppol...Identifier` classes:

Old: `SimpleDocumentTypeIdentifier`
New: `PeppolDocumentTypeIdentifier`

Old: `SimpleProcessIdentifier`
New: `PeppolProcessIdentifier`

Old: `SimpleParticipantIdentifier`
New: `PeppolParticipantIdentifier`

This means that the `Simple...Identifier` classes no longer check for PEPPOL specific rules (like schema and value validity). Instead the now existing `Simple...` classes are the simplest possible implementation and check nothing.

Additionally an `IIdentifierFactory` class for consistently creating identifiers was introduced. It exists in three different implementations for PEPPOL (`PeppolIdentifierFactory`), the unchecked version (`SimpleIdentifierFactory`) and for [BDXR](https://www.oasis-open.org/committees/bdxr/) (`BDXRIdentifierFactory`).  

##SPI
The interface `com.helger.peppol.identifier.validator.IParticipantIdentifierValidatorSPI` was moved to `com.helger.peppol.identifier.**peppol**.validator.IParticipantIdentifierValidatorSPI` so all implementations must be updated - don't forget the  filenames below `META-INF/services` itself.
