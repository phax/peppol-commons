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
package com.helger.peppolid.bdxr.smp2;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.helger.annotation.Nonempty;
import com.helger.annotation.concurrent.Immutable;
import com.helger.annotation.style.PresentForCodeCoverage;
import com.helger.base.string.StringHelper;
import com.helger.base.url.URLHelper;
import com.helger.peppolid.CIdentifier;
import com.helger.peppolid.simple.doctype.SimpleDocumentTypeIdentifier;
import com.helger.peppolid.simple.participant.SimpleParticipantIdentifier;
import com.helger.peppolid.simple.process.SimpleProcessIdentifier;

/**
 * Helper methods for OASIS BDXR SMP v2 identifiers.
 *
 * @author Philip Helger
 */
@Immutable
public final class BDXR2IdentifierHelper
{
  @PresentForCodeCoverage
  private static final BDXR2IdentifierHelper INSTANCE = new BDXR2IdentifierHelper ();

  private BDXR2IdentifierHelper ()
  {}

  /**
   * Check if the given identifier is valid. It is valid if it is empty or a valid URI.<br>
   * The scheme of the participant identifier MUST be in the form of a URI.<br>
   * The scheme of the document identifier MUST be in the form of a URI.
   *
   * @param sScheme
   *        The scheme to check.
   * @return <code>true</code> if the passed scheme is a valid identifier scheme, <code>false</code>
   *         otherwise.
   */
  public static boolean isValidIdentifierScheme (@Nullable final String sScheme)
  {
    if (StringHelper.isEmpty (sScheme))
      return true;
    return URLHelper.getAsURI (sScheme) != null;
  }

  /**
   * Check if an identifier value is valid. Currently this check always returns true.
   *
   * @param sValue
   *        The value to check. May be <code>null</code>.
   * @return <code>true</code> if the passed value is valid, <code>false</code> otherwise.
   */
  public static boolean isValidIdentifierValue (@Nullable final String sValue)
  {
    return true;
  }

  /**
   * Get the identifier URI encoded (without percent encoding) as in <code>scheme::value</code>.
   *
   * @param aID
   *        The ID to be encoded. May not be <code>null</code>.
   * @return The URI encoded identifier value.
   */
  @NonNull
  @Nonempty
  public static String getURIEncoded (final com.helger.xsds.ccts.cct.schemamodule.@NonNull IdentifierType aID)
  {
    return CIdentifier.getURIEncoded (aID.getSchemeID (), aID.getValue ());
  }

  /**
   * Get the identifier URI and percent encoded (with percent encoding) as in
   * <code>scheme%3A%3Avalue</code>.
   *
   * @param aID
   *        The ID to be encoded. May not be <code>null</code>.
   * @return The URI and percent encoded identifier value.
   */
  @NonNull
  public static String getURIPercentEncoded (final com.helger.xsds.ccts.cct.schemamodule.@NonNull IdentifierType aID)
  {
    return CIdentifier.getURIPercentEncoded (aID.getSchemeID (), aID.getValue ());
  }

  /**
   * Wrap the passed JAXB object into a generic {@link SimpleParticipantIdentifier}.
   *
   * @param aID
   *        The ID to be wrapped. May not be <code>null</code>.
   * @return The wrapped identifier. Never <code>null</code>.
   */
  @NonNull
  public static SimpleParticipantIdentifier wrapAsSimpleParticipantIdentifier (final com.helger.xsds.ccts.cct.schemamodule.@NonNull IdentifierType aID)
  {
    return new SimpleParticipantIdentifier (aID.getSchemeID (), aID.getValue ());
  }

  /**
   * Wrap the passed JAXB object into a generic {@link SimpleDocumentTypeIdentifier}.
   *
   * @param aID
   *        The ID to be wrapped. May not be <code>null</code>.
   * @return The wrapped identifier. Never <code>null</code>.
   */
  @NonNull
  public static SimpleDocumentTypeIdentifier wrapAsSimpleDocumentTypeIdentifier (final com.helger.xsds.ccts.cct.schemamodule.@NonNull IdentifierType aID)
  {
    return new SimpleDocumentTypeIdentifier (aID.getSchemeID (), aID.getValue ());
  }

  /**
   * Wrap the passed JAXB object into a generic {@link SimpleProcessIdentifier}.
   *
   * @param aID
   *        The ID to be wrapped. May not be <code>null</code>.
   * @return The wrapped identifier. Never <code>null</code>.
   */
  @NonNull
  public static SimpleProcessIdentifier wrapAsSimpleProcessIdentifier (final com.helger.xsds.ccts.cct.schemamodule.@NonNull IdentifierType aID)
  {
    return new SimpleProcessIdentifier (aID.getSchemeID (), aID.getValue ());
  }
}
