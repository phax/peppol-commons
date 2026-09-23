/*
 * Copyright (C) 2015-2026 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.helger.peppolid.factory;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.helger.base.tostring.ToStringGenerator;
import com.helger.peppolid.bdxr.smp2.BDXR2IdentifierHelper;
import com.helger.peppolid.bdxr.smp2.CBDXR2Identifier;
import com.helger.peppolid.bdxr.smp2.doctype.BDXR2DocumentTypeIdentifier;
import com.helger.peppolid.bdxr.smp2.participant.BDXR2ParticipantIdentifier;
import com.helger.peppolid.bdxr.smp2.process.BDXR2ProcessIdentifier;

/**
 * Implementation of {@link IIdentifierFactory} for the DBNAlliance network.
 * <p>
 * It uses the same identifier classes as {@link BDXR2IdentifierFactory}, but treats document type
 * and process identifiers as case insensitive regardless of the scheme. This matches the
 * DBNAlliance interpretation of the OASIS BDXR SMP v2.0 specification, which states that all
 * identifier schemes are case insensitive unless explicitly stated otherwise (see
 * <a href="https://github.com/phax/peppol-commons/issues/71">issue #71</a>).
 * </p>
 * <p>
 * This class deliberately does not extend {@link BDXR2IdentifierFactory} - the networks are
 * independent of each other and a change in one of them must not silently change the other.
 * </p>
 *
 * @author Philip Helger
 * @since 12.5.2
 */
public class DBNAllianceIdentifierFactory implements IIdentifierFactory
{
  /** Global instance to be used. */
  public static final DBNAllianceIdentifierFactory INSTANCE = new DBNAllianceIdentifierFactory ();

  public DBNAllianceIdentifierFactory ()
  {}

  @NonNull
  @Override
  public String getDefaultDocumentTypeIdentifierScheme ()
  {
    return CBDXR2Identifier.DEFAULT_DOCUMENT_TYPE_IDENTIFIER_SCHEME;
  }

  @Override
  public boolean isDocumentTypeIdentifierCaseInsensitive (@Nullable final String sScheme)
  {
    // DBNAlliance: case insensitive, no matter what the scheme is
    return true;
  }

  @Override
  public boolean isDocumentTypeIdentifierSchemeValid (@Nullable final String sScheme)
  {
    return BDXR2IdentifierHelper.isValidIdentifierScheme (sScheme);
  }

  @Override
  public boolean isDocumentTypeIdentifierValueValid (@Nullable final String sScheme, @Nullable final String sValue)
  {
    return BDXR2IdentifierHelper.isValidIdentifierValue (sValue);
  }

  @Nullable
  public BDXR2DocumentTypeIdentifier createDocumentTypeIdentifier (@Nullable final String sScheme,
                                                                   @Nullable final String sValue)
  {
    final String sRealScheme = nullNotEmpty (sScheme);
    final String sRealValue = nullNotEmpty (isDocumentTypeIdentifierCaseInsensitive (sRealScheme) ? getUnifiedValue (sValue)
                                                                                                  : sValue);
    if (isDocumentTypeIdentifierSchemeValid (sRealScheme) &&
        isDocumentTypeIdentifierValueValid (sRealScheme, sRealValue))
      return new BDXR2DocumentTypeIdentifier (sRealScheme, sRealValue);
    return null;
  }

  // No default participant identifier scheme

  @Override
  public boolean isParticipantIdentifierCaseInsensitive (@Nullable final String sScheme)
  {
    return CBDXR2Identifier.PARTICIPANT_SCHEME_ISO6523_ACTORID_UPIS.equals (sScheme);
  }

  @Override
  public boolean isParticipantIdentifierSchemeValid (@Nullable final String sScheme)
  {
    return BDXR2IdentifierHelper.isValidIdentifierScheme (sScheme);
  }

  @Override
  public boolean isParticipantIdentifierValueValid (@Nullable final String sScheme, @Nullable final String sValue)
  {
    return BDXR2IdentifierHelper.isValidIdentifierValue (sValue);
  }

  @Nullable
  public BDXR2ParticipantIdentifier createParticipantIdentifier (@Nullable final String sScheme,
                                                                 @Nullable final String sValue)
  {
    final String sRealScheme = nullNotEmpty (sScheme);
    final String sRealValue = nullNotEmpty (isParticipantIdentifierCaseInsensitive (sRealScheme) ? getUnifiedValue (sValue)
                                                                                                 : sValue);
    if (isParticipantIdentifierSchemeValid (sRealScheme) && isParticipantIdentifierValueValid (sRealScheme, sRealValue))
      return new BDXR2ParticipantIdentifier (sRealScheme, sRealValue);
    return null;
  }

  @NonNull
  @Override
  public String getDefaultProcessIdentifierScheme ()
  {
    return CBDXR2Identifier.DEFAULT_PROCESS_IDENTIFIER_SCHEME;
  }

  @Override
  public boolean isProcessIdentifierCaseInsensitive (@Nullable final String sScheme)
  {
    // DBNAlliance: case insensitive, no matter what the scheme is
    return true;
  }

  @Override
  public boolean isProcessIdentifierValueValid (@Nullable final String sScheme, @Nullable final String sValue)
  {
    return BDXR2IdentifierHelper.isValidIdentifierValue (sValue);
  }

  @Nullable
  public BDXR2ProcessIdentifier createProcessIdentifier (@Nullable final String sScheme, @Nullable final String sValue)
  {
    final String sRealScheme = nullNotEmpty (sScheme);
    final String sRealValue = nullNotEmpty (isProcessIdentifierCaseInsensitive (sRealScheme) ? getUnifiedValue (sValue)
                                                                                             : sValue);

    if (isProcessIdentifierSchemeValid (sRealScheme) && isProcessIdentifierValueValid (sRealScheme, sRealValue))
      return new BDXR2ProcessIdentifier (sRealScheme, sRealValue);
    return null;
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).getToString ();
  }
}
