package com.helger.peppol.identifier.factory;

import javax.annotation.Nullable;

import com.helger.peppol.identifier.peppol.doctype.PeppolDocumentTypeIdentifier;
import com.helger.peppol.identifier.peppol.participant.PeppolParticipantIdentifier;
import com.helger.peppol.identifier.peppol.process.PeppolProcessIdentifier;

/**
 * Default implementation of {@link IIdentifierFactory} for PEPPOL identifiers.
 *
 * @author Philip Helger
 */
public class PeppolIdentifierFactory implements IIdentifierFactory
{
  @Nullable
  public PeppolDocumentTypeIdentifier createDocumentTypeIdentifier (@Nullable final String sScheme,
                                                                    @Nullable final String sValue)
  {
    return PeppolDocumentTypeIdentifier.createIfValid (sScheme, sValue);
  }

  @Nullable
  public PeppolDocumentTypeIdentifier parseDocumentTypeIdentifier (@Nullable final String sURIEncodedIdentifier)
  {
    return PeppolDocumentTypeIdentifier.createFromURIPartOrNull (sURIEncodedIdentifier);
  }

  @Nullable
  public PeppolParticipantIdentifier createParticipantIdentifier (@Nullable final String sScheme,
                                                                  @Nullable final String sValue)
  {
    return PeppolParticipantIdentifier.createIfValid (sScheme, sValue);
  }

  @Nullable
  public PeppolParticipantIdentifier parseParticipantIdentifier (@Nullable final String sURIEncodedIdentifier)
  {
    return PeppolParticipantIdentifier.createFromURIPartOrNull (sURIEncodedIdentifier);
  }

  @Nullable
  public PeppolProcessIdentifier createProcessIdentifier (@Nullable final String sScheme, @Nullable final String sValue)
  {
    return PeppolProcessIdentifier.createIfValid (sScheme, sValue);
  }

  @Nullable
  public PeppolProcessIdentifier parseProcessIdentifier (@Nullable final String sURIEncodedIdentifier)
  {
    return PeppolProcessIdentifier.createFromURIPartOrNull (sURIEncodedIdentifier);
  }
}
