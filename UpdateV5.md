#Updating to Version 5.x

This document shows what you need to do in order to upgrade from 4.x to 5.x.

##Identifiers
Because in 4.x the identifiers were very complex and no clear separation between PEPPOL and non-PEPPOL requirements was made, I decided (also because of https://github.com/phax/peppol-smp-server/issues/20) to restructure things.

Old: `SimpleDocumentTypeIdentifier`
New: `PeppolDocumentTypeIdentifier`

##SPI
The interface `com.helger.peppol.identifier.validator.IParticipantIdentifierValidatorSPI` was moved to `com.helger.peppol.identifier.**peppol**.validator.IParticipantIdentifierValidatorSPI` so all implementations must be updated - don't forget the the `META-INF/services` filenames itself.
