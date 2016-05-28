package com.helger.peppol.identifier.factory;

import javax.annotation.Nullable;

import com.helger.peppol.identifier.bdxr.doctype.BDXRDocumentTypeIdentifier;
import com.helger.peppol.identifier.bdxr.participant.BDXRParticipantIdentifier;
import com.helger.peppol.identifier.bdxr.process.BDXRProcessIdentifier;

/**
 * Default implementation of {@link IIdentifierFactory} for BDXR identifiers.
 *
 * @author Philip Helger
 */
public class BDXRIdentifierFactory implements IIdentifierFactory
{
  @Nullable
  public BDXRDocumentTypeIdentifier createDocumentTypeIdentifier (@Nullable final String sScheme,
                                                                  @Nullable final String sValue)
  {
    return new BDXRDocumentTypeIdentifier (sScheme, sValue);
  }

  @Nullable
  public BDXRDocumentTypeIdentifier parseDocumentTypeIdentifier (@Nullable final String sURIEncodedIdentifier)
  {
    return BDXRDocumentTypeIdentifier.createFromURIPartOrNull (sURIEncodedIdentifier);
  }

  @Nullable
  public BDXRParticipantIdentifier createParticipantIdentifier (@Nullable final String sScheme,
                                                                @Nullable final String sValue)
  {
    return new BDXRParticipantIdentifier (sScheme, sValue);
  }

  @Nullable
  public BDXRParticipantIdentifier parseParticipantIdentifier (@Nullable final String sURIEncodedIdentifier)
  {
    return BDXRParticipantIdentifier.createFromURIPartOrNull (sURIEncodedIdentifier);
  }

  @Nullable
  public BDXRProcessIdentifier createProcessIdentifier (@Nullable final String sScheme, @Nullable final String sValue)
  {
    return new BDXRProcessIdentifier (sScheme, sValue);
  }

  @Nullable
  public BDXRProcessIdentifier parseProcessIdentifier (@Nullable final String sURIEncodedIdentifier)
  {
    return BDXRProcessIdentifier.createFromURIPartOrNull (sURIEncodedIdentifier);
  }
}
