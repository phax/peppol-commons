/*
 * Copyright (C) 2025 Philip Helger
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.annotation.concurrent.NotThreadSafe;
import com.helger.base.builder.IBuilder;
import com.helger.base.enforce.ValueEnforcer;
import com.helger.base.string.StringHelper;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.ResponseType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.StatusType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_21.DescriptionType;

/**
 * Builder for a single Line Response/Response within a Peppol MLS. Fill all the mandatory fields
 * and call {@link #build()} at the end.
 *
 * @author Philip Helger
 * @since 11.0.0
 */
@NotThreadSafe
public class PeppolMLSLineResponseResponseBuilder implements IBuilder <ResponseType>
{
  private static final Logger LOGGER = LoggerFactory.getLogger (PeppolMLSLineResponseResponseBuilder.class);

  private String m_sDescription;
  private EPeppolMLSStatusReasonCode m_eStatusReasonCode;

  public PeppolMLSLineResponseResponseBuilder ()
  {}

  /**
   * Set the response text for this particular line. This should be a human readable text line the
   * error message referring to a specific field. In case of a Schematron failure, it might be the
   * text of the failed assertion.
   *
   * @param s
   *        Response text.
   * @return this for chaining
   */
  @Nonnull
  public PeppolMLSLineResponseResponseBuilder description (@Nullable final String s)
  {
    m_sDescription = s;
    return this;
  }

  @Nonnull
  public PeppolMLSLineResponseResponseBuilder statusReasonCodeBusinessRuleViolationFatal ()
  {
    return statusReasonCode (EPeppolMLSStatusReasonCode.BUSINESS_RULE_VIOLATION_FATAL);
  }

  @Nonnull
  public PeppolMLSLineResponseResponseBuilder statusReasonCodeBusinessRuleViolationWarning ()
  {
    return statusReasonCode (EPeppolMLSStatusReasonCode.BUSINESS_RULE_VIOLATION_WARNING);
  }

  @Nonnull
  public PeppolMLSLineResponseResponseBuilder statusReasonCodeFailureOfDelivery ()
  {
    return statusReasonCode (EPeppolMLSStatusReasonCode.FAILURE_OF_DELIVERY);
  }

  @Nonnull
  public PeppolMLSLineResponseResponseBuilder statusReasonCodeSyntaxViolation ()
  {
    return statusReasonCode (EPeppolMLSStatusReasonCode.SYNTAX_VIOLATION);
  }

  @Nonnull
  public PeppolMLSLineResponseResponseBuilder statusReasonCode (@Nullable final EPeppolMLSStatusReasonCode e)
  {
    m_eStatusReasonCode = e;
    return this;
  }

  public boolean areAllFieldsSet (final boolean bLogDetails)
  {
    // Enforced by Schematron
    if (StringHelper.isEmpty (m_sDescription))
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
  public ResponseType build ()
  {
    if (!areAllFieldsSet (true))
      throw new IllegalStateException ("Not all MLS Line Response/Response mandatory fields are set");

    final ResponseType ret = new ResponseType ();
    ret.addDescription (new DescriptionType (m_sDescription));

    final StatusType aStatus = new StatusType ();
    aStatus.setStatusReasonCode (m_eStatusReasonCode.getID ());
    ret.addStatus (aStatus);

    return ret;
  }

  @Nonnull
  public static PeppolMLSLineResponseResponseBuilder createForLineResponseResponse (@Nonnull final ResponseType aResponse)
  {
    ValueEnforcer.notNull (aResponse, "Response");

    if (aResponse.hasNoStatusEntries ())
      throw new IllegalArgumentException ("Line Response/Response has no status present");
    if (aResponse.hasNoDescriptionEntries ())
      throw new IllegalArgumentException ("Line Response/Response has no description entries");

    final EPeppolMLSStatusReasonCode eStatusReasonCode = EPeppolMLSStatusReasonCode.getFromIDOrThrow (aResponse.getStatusAtIndex (0)
                                                                                                               .getStatusReasonCodeValue ());

    return new PeppolMLSLineResponseResponseBuilder ().description (aResponse.getDescriptionAtIndex (0).getValue ())
                                                      .statusReasonCode (eStatusReasonCode);
  }
}
