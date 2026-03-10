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

import java.util.function.Consumer;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.annotation.Nonempty;
import com.helger.annotation.concurrent.NotThreadSafe;
import com.helger.annotation.style.ReturnsMutableCopy;
import com.helger.annotation.style.ReturnsMutableObject;
import com.helger.base.builder.IBuilder;
import com.helger.base.enforce.ValueEnforcer;
import com.helger.base.string.StringHelper;
import com.helger.collection.commons.CommonsArrayList;
import com.helger.collection.commons.ICommonsList;

import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.LineReferenceType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.LineResponseType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.ResponseType;

/**
 * Builder for a single Line Response within a Peppol MLS. Fill all the mandatory fields and call
 * {@link #build()} at the end.
 *
 * @author Philip Helger
 */
@NotThreadSafe
public class PeppolMLSLineResponseBuilder implements IBuilder <LineResponseType>
{
  private static final Logger LOGGER = LoggerFactory.getLogger (PeppolMLSLineResponseBuilder.class);

  private String m_sErrorField;
  private final ICommonsList <ResponseType> m_aResponses = new CommonsArrayList <> ();

  /**
   * Constructor.
   */
  public PeppolMLSLineResponseBuilder ()
  {}

  /**
   * @return The error field reference. May be <code>null</code> if not yet set.
   */
  @Nullable
  public String errorField ()
  {
    return m_sErrorField;
  }

  /**
   * Set the name of the field that is under error. In case of a Schematron failure this should be
   * the "test" of the failed Schematron assertion.
   *
   * @param s
   *        Reference to the field under error. May be <code>null</code>.
   * @return this for chaining
   */
  @NonNull
  public PeppolMLSLineResponseBuilder errorField (@Nullable final String s)
  {
    m_sErrorField = s;
    return this;
  }

  /**
   * @return The mutable list of responses. Never <code>null</code>.
   */
  @NonNull
  @ReturnsMutableObject
  public ICommonsList <ResponseType> responses ()
  {
    return m_aResponses;
  }

  /**
   * @return All contained responses as {@link PeppolMLSLineResponseResponseBuilder} objects. Never
   *         <code>null</code>.
   */
  @NonNull
  @ReturnsMutableCopy
  public ICommonsList <PeppolMLSLineResponseResponseBuilder> responsesAsBuilders ()
  {
    return m_aResponses.getAllMapped (PeppolMLSLineResponseResponseBuilder::createForLineResponseResponse);
  }

  /**
   * Add a single response object using a status reason code and a description.
   *
   * @param eStatusReasonCode
   *        Status reason code. May not be <code>null</code>.
   * @param sDescription
   *        The description reason for this response. May neither be <code>null</code> nor empty.
   * @return this for chaining
   */
  @NonNull
  public PeppolMLSLineResponseBuilder addResponse (@NonNull final EPeppolMLSStatusReasonCode eStatusReasonCode,
                                                   @NonNull @Nonempty final String sDescription)
  {
    return addResponse (b -> b.statusReasonCode (eStatusReasonCode).description (sDescription));
  }

  /**
   * Add a single response object using a builder. If the builder is <code>null</code>, the call is
   * ignored.
   *
   * @param a
   *        Response builder consumer. May not be <code>null</code>.
   * @return this for chaining
   * @since 12.3.12
   */
  @NonNull
  public PeppolMLSLineResponseBuilder addResponse (@NonNull final Consumer <? super PeppolMLSLineResponseResponseBuilder> a)
  {
    final PeppolMLSLineResponseResponseBuilder aBuilder = new PeppolMLSLineResponseResponseBuilder ();
    a.accept (aBuilder);
    return addResponse (aBuilder.build ());
  }

  /**
   * Add a single response object using a builder. If the builder is <code>null</code>, the call is
   * ignored.
   *
   * @param a
   *        Response builder. May be <code>null</code>.
   * @return this for chaining
   */
  @NonNull
  public PeppolMLSLineResponseBuilder addResponse (@Nullable final PeppolMLSLineResponseResponseBuilder a)
  {
    return addResponse (a == null ? null : a.build ());
  }

  /**
   * Add a single response object. If the value is <code>null</code>, the call is ignored.
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

  /**
   * Set all responses at once, replacing any previously added ones.
   *
   * @param a
   *        Responses. May be <code>null</code>.
   * @return this for chaining
   */
  @NonNull
  public PeppolMLSLineResponseBuilder responses (@Nullable final ResponseType... a)
  {
    m_aResponses.setAll (a);
    return this;
  }

  /**
   * Set all responses at once, replacing any previously added ones.
   *
   * @param a
   *        Responses. May be <code>null</code>.
   * @return this for chaining
   */
  @NonNull
  public PeppolMLSLineResponseBuilder responses (@Nullable final Iterable <? extends ResponseType> a)
  {
    m_aResponses.setAll (a);
    return this;
  }

  /**
   * Check if all mandatory fields are set. The error field and at least one response are required.
   *
   * @param bLogDetails
   *        <code>true</code> to log warnings for each missing field, <code>false</code> to silently
   *        check.
   * @return <code>true</code> if all mandatory fields are set, <code>false</code> otherwise.
   */
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

  /**
   * Build the {@link LineResponseType} from the current builder state. All mandatory fields must be
   * set before calling this method.
   *
   * @return A new {@link LineResponseType} and never <code>null</code>.
   * @throws IllegalStateException
   *         If not all mandatory fields are set.
   */
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

  /**
   * Create a builder from an existing {@link LineResponseType}, e.g. for round-tripping.
   *
   * @param aLineResponse
   *        The line response to load. May not be <code>null</code>.
   * @return A new {@link PeppolMLSLineResponseBuilder} and never <code>null</code>.
   * @throws IllegalArgumentException
   *         If the given line response contains no response entries.
   */
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
