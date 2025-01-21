/*
 * Copyright (C) 2023-2025 Philip Helger
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
package com.helger.peppol.mlr;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.builder.IBuilder;
import com.helger.commons.string.StringHelper;

import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.LineReferenceType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.LineResponseType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.ResponseType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.StatusType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_21.DescriptionType;

/**
 * Builder for a single Line Response within a Peppol MLR. Fill all the
 * mandatory fields and call {@link #build()} at the end.
 *
 * @author Philip Helger
 */
public class PeppolMLRLineResponseBuilder implements IBuilder <LineResponseType>
{
  private static final Logger LOGGER = LoggerFactory.getLogger (PeppolMLRLineResponseBuilder.class);

  private final EPeppolMLRResponseCode m_eResponseCode;
  private String m_sErrorField;
  private String m_sDescription;
  private EPeppolMLRStatusReasonCode m_eStatusReasonCode;

  public PeppolMLRLineResponseBuilder (@Nonnull final EPeppolMLRResponseCode eResponseCode)
  {
    ValueEnforcer.notNull (eResponseCode, "ResponseCode");
    m_eResponseCode = eResponseCode;
  }

  /**
   * Set the name of the field that is under error. In case of a Schematron
   * failure this should be the "test" of the failed Schematron assertion.
   *
   * @param s
   *        Reference to the field under error
   * @return this for chaining
   */
  @Nonnull
  public PeppolMLRLineResponseBuilder errorField (@Nullable final String s)
  {
    m_sErrorField = s;
    return this;
  }

  /**
   * Set the response text for this particular line. This should be a human
   * readable text line the error message referring to a specific field. In case
   * of a Schematron failure, it might be the text of the failed assertion.
   *
   * @param s
   *        Response text.
   * @return this for chaining
   */
  @Nonnull
  public PeppolMLRLineResponseBuilder description (@Nullable final String s)
  {
    m_sDescription = s;
    return this;
  }

  @Nonnull
  public PeppolMLRLineResponseBuilder statusReasonCodeBusinessRuleViolationFatal ()
  {
    return statusReasonCode (EPeppolMLRStatusReasonCode.BUSINESS_RULE_VIOLATION_FATAL);
  }

  @Nonnull
  public PeppolMLRLineResponseBuilder statusReasonCodeBusinessRuleViolationWarning ()
  {
    return statusReasonCode (EPeppolMLRStatusReasonCode.BUSINESS_RULE_VIOLATION_WARNING);
  }

  @Nonnull
  public PeppolMLRLineResponseBuilder statusReasonCodeSyntaxViolation ()
  {
    return statusReasonCode (EPeppolMLRStatusReasonCode.SYNTAX_VIOLATION);
  }

  @Nonnull
  public PeppolMLRLineResponseBuilder statusReasonCode (@Nullable final EPeppolMLRStatusReasonCode e)
  {
    m_eStatusReasonCode = e;
    return this;
  }

  public boolean areAllFieldsSet (final boolean bLogDetails)
  {
    // Enforced by XSD
    if (StringHelper.hasNoText (m_sErrorField))
    {
      if (bLogDetails)
        LOGGER.warn ("The LineResponse Error Field is missing");
      return false;
    }

    // Enforced by Schematron
    if (StringHelper.hasNoText (m_sDescription))
    {
      if (bLogDetails)
        LOGGER.warn ("The LineResponse Description is missing");
      return false;
    }

    // Enforced by Schematron
    if (m_eStatusReasonCode == null)
    {
      if (bLogDetails)
        LOGGER.warn ("The LineResponse Status Reason Code is missing");
      return false;
    }
    return true;
  }

  @Nonnull
  public LineResponseType build ()
  {
    if (!areAllFieldsSet (true))
      throw new IllegalStateException ("Not all MLR Line Response mandatory fields are set");

    final LineResponseType ret = new LineResponseType ();

    {
      final LineReferenceType aLineRef = new LineReferenceType ();
      aLineRef.setLineID (m_sErrorField);
      ret.setLineReference (aLineRef);
    }

    {
      final ResponseType aResponse = new ResponseType ();
      aResponse.setResponseCode (m_eResponseCode.getID ());
      aResponse.addDescription (new DescriptionType (m_sDescription));
      final StatusType aStatus = new StatusType ();
      aStatus.setStatusReasonCode (m_eStatusReasonCode.getID ());
      aResponse.addStatus (aStatus);
      ret.addResponse (aResponse);
    }

    return ret;
  }

  @Nonnull
  public static PeppolMLRLineResponseBuilder acceptance ()
  {
    return new PeppolMLRLineResponseBuilder (EPeppolMLRResponseCode.ACCEPTANCE);
  }

  @Nonnull
  public static PeppolMLRLineResponseBuilder acknowledging ()
  {
    return new PeppolMLRLineResponseBuilder (EPeppolMLRResponseCode.ACKNOWLEDGING);
  }

  @Nonnull
  public static PeppolMLRLineResponseBuilder rejection ()
  {
    return new PeppolMLRLineResponseBuilder (EPeppolMLRResponseCode.REJECTION);
  }
}
