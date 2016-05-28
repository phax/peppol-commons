package com.helger.peppol.identifier.factory;

import javax.annotation.Nullable;

import com.helger.peppol.identifier.generic.doctype.SimpleDocumentTypeIdentifier;
import com.helger.peppol.identifier.generic.participant.SimpleParticipantIdentifier;
import com.helger.peppol.identifier.generic.process.SimpleProcessIdentifier;

/**
 * Default implementation of {@link IIdentifierFactory} for default (simple)
 * identifiers.
 *
 * @author Philip Helger
 */
public class SimpleIdentifierFactory implements IIdentifierFactory
{
  @Nullable
  public SimpleDocumentTypeIdentifier createDocumentTypeIdentifier (@Nullable final String sScheme,
                                                                    @Nullable final String sValue)
  {
    return new SimpleDocumentTypeIdentifier (sScheme, sValue);
  }

  @Nullable
  public SimpleDocumentTypeIdentifier parseDocumentTypeIdentifier (@Nullable final String sURIEncodedIdentifier)
  {
    return SimpleDocumentTypeIdentifier.createFromURIPartOrNull (sURIEncodedIdentifier);
  }

  @Nullable
  public SimpleParticipantIdentifier createParticipantIdentifier (@Nullable final String sScheme,
                                                                  @Nullable final String sValue)
  {
    return new SimpleParticipantIdentifier (sScheme, sValue);
  }

  @Nullable
  public SimpleParticipantIdentifier parseParticipantIdentifier (@Nullable final String sURIEncodedIdentifier)
  {
    return SimpleParticipantIdentifier.createFromURIPartOrNull (sURIEncodedIdentifier);
  }

  @Nullable
  public SimpleProcessIdentifier createProcessIdentifier (@Nullable final String sScheme, @Nullable final String sValue)
  {
    return new SimpleProcessIdentifier (sScheme, sValue);
  }

  @Nullable
  public SimpleProcessIdentifier parseProcessIdentifier (@Nullable final String sURIEncodedIdentifier)
  {
    return SimpleProcessIdentifier.createFromURIPartOrNull (sURIEncodedIdentifier);
  }
}
