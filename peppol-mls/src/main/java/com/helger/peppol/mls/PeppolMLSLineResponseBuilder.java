/*
 * Copyright (C) 2025-2026 Philip Helger
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
package com.helger.peppol.mls;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.annotation.Nonempty;
import com.helger.annotation.concurrent.NotThreadSafe;
import com.helger.base.builder.IBuilder;
import com.helger.base.enforce.ValueEnforcer;
import com.helger.base.string.StringHelper;
import com.helger.collection.commons.CommonsArrayList;
import com.helger.collection.commons.ICommonsList;

import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.LineReferenceType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.LineResponseType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.ResponseType;

/**
 * Builder for a single Line Response within a Peppol MLS. Fill all the
 * mandatory fields and call {@link #build()} at the end.
 *
 * @author Philip Helger
 */
@NotThreadSafe
public class PeppolMLSLineResponseBuilder implements IBuilder <LineResponseType>
{
  private static final Logger LOGGER = LoggerFactory.getLogger (PeppolMLSLineResponseBuilder.class);

  private String m_sErrorField;
  private final ICommonsList <ResponseType> m_aResponses = new CommonsArrayList <> ();

  public PeppolMLSLineResponseBuilder ()
  {}

  /**
   * Set the name of the field that is under error. In case of a Schematron
   * failure this should be the "test" of the failed Schematron assertion.
   *
   * @param s
   *        Reference to the field under error
   * @return this for chaining
   */
  @NonNull
  public PeppolMLSLineResponseBuilder errorField (@Nullable final String s)
  {
    m_sErrorField = s;
    return this;
  }

  /**
   * Add a single response object.
   *
   * @param eStatusReasonCode
   *        Status reason code. May be <code>null</code>.
   * @param sDescription
   *        The description reason for this response. May neither be
   *        <code>null</code> nor empty.
   * @return this for chaining
   */
  @NonNull
  public PeppolMLSLineResponseBuilder addResponse (@NonNull final EPeppolMLSStatusReasonCode eStatusReasonCode,
                                                   @NonNull @Nonempty final String sDescription)
  {
    return addResponse (new PeppolMLSLineResponseResponseBuilder ().statusReasonCode (eStatusReasonCode)
                                                                   .description (sDescription));
  }

  /**
   * Add a single response object.
   *
   * @param a
   *        Response object. May be <code>null</code>.
   * @return this for chaining
   */
  @NonNull
  public PeppolMLSLineResponseBuilder addResponse (@Nullable final PeppolMLSLineResponseResponseBuilder a)
  {
    return addResponse (a == null ? null : a.build ());
  }

  /**
   * Add a single response object.
   *
   * @param a
   *        Response object. May be <code>null</code>.
   * @return this for chaining
   */
  @NonNull
  public PeppolMLSLineResponseBuilder addResponse (@Nullable final ResponseType a)
  {
    if (a != null)
      m_aResponses.add (a);
    return this;
  }

  @NonNull
  public PeppolMLSLineResponseBuilder responses (@Nullable final ResponseType... a)
  {
    m_aResponses.setAll (a);
    return this;
  }

  @NonNull
  public PeppolMLSLineResponseBuilder responses (@Nullable final Iterable <? extends ResponseType> a)
  {
    m_aResponses.setAll (a);
    return this;
  }

  public boolean areAllFieldsSet (final boolean bLogDetails)
  {
    // Enforced by XSD
    if (StringHelper.isEmpty (m_sErrorField))
    {
      if (bLogDetails)
        LOGGER.warn ("The LineResponse Error Field is missing");
      return false;
    }

    // Enforced by Schematron
    if (m_aResponses.isEmpty ())
    {
      if (bLogDetails)
        LOGGER.warn ("The LineResponse is missing at least one Response");
      return false;
    }

    return true;
  }

  @NonNull
  public LineResponseType build ()
  {
    if (!areAllFieldsSet (true))
      throw new IllegalStateException ("Not all MLS Line Response mandatory fields are set");

    final LineResponseType ret = new LineResponseType ();

    {
      final LineReferenceType aLineRef = new LineReferenceType ();
      aLineRef.setLineID (m_sErrorField);
      ret.setLineReference (aLineRef);
    }

    for (final var aResponse : m_aResponses)
      ret.addResponse (aResponse);

    return ret;
  }

  @NonNull
  public static PeppolMLSLineResponseBuilder createForLineResponse (@NonNull final LineResponseType aLineResponse)
  {
    ValueEnforcer.notNull (aLineResponse, "LineResponse");

    if (aLineResponse.hasNoResponseEntries ())
      throw new IllegalArgumentException ("No response present in the line response");

    final PeppolMLSLineResponseBuilder ret = new PeppolMLSLineResponseBuilder ().errorField (aLineResponse.getLineReference ()
                                                                                                          .getLineIDValue ());
    for (final var aResponse : aLineResponse.getResponse ())
      ret.addResponse (aResponse);
    return ret;
  }
}
