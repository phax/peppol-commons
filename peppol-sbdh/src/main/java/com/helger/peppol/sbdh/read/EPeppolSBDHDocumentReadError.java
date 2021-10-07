/*
 * Copyright (C) 2014-2021 Philip Helger
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
package com.helger.peppol.sbdh.read;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.annotation.Nonempty;
import com.helger.commons.id.IHasID;
import com.helger.commons.lang.EnumHelper;

/**
 * This enum contains all the errors that can occur during SBD reading
 *
 * @author Philip Helger
 */
public enum EPeppolSBDHDocumentReadError implements IHasID <String>
{
  /** Failed to interpret StandardBusinessDocument as XML */
  INVALID_SBD_XML ("invalid-sbd-xml", "Failed to interpret StandardBusinessDocument as XML"),

  /** The StandardBusinessDocumentHeader element is not present */
  MISSING_SBDH ("missing-sbdh", "The StandardBusinessDocumentHeader element is not present"),

  /** The "HeaderVersion" element has an illegal value */
  INVALID_HEADER_VERSION ("invalid-header-version", "The \"HeaderVersion\" element has an illegal value"),

  /** Not exactly one "Sender" element is present */
  INVALID_SENDER_COUNT ("invalid-sender-count", "Not exactly one \"Sender\" element is present"),

  /** The "Sender/Identifier/Authority" attribute has an invalid value */
  INVALID_SENDER_AUTHORITY ("invalid-sender-authority", "The \"Sender/Identifier/Authority\" attribute has an invalid value"),

  /** The "Sender/Identifier" has an invalid value */
  INVALID_SENDER_VALUE ("invalid-sender-value", "The \"Sender/Identifier\" has an invalid value"),

  /** Not exactly one "Receiver" element is present */
  INVALID_RECEIVER_COUNT ("invalid-receiver-count", "Not exactly one \"Receiver\" element is present"),

  /** The "Receiver/Identifier/Authority" attribute has an invalid value */
  INVALID_RECEIVER_AUTHORITY ("invalid-receiver-authority", "The \"Receiver/Identifier/Authority\" attribute has an invalid value"),

  /** The "Receiver/Identifier" has an invalid value */
  INVALID_RECEIVER_VALUE ("invalid-receiver-value", "The \"Receiver/Identifier\" has an invalid value"),

  /** The "BusinessScope" element is missing */
  BUSINESS_SCOPE_MISSING ("business-scope-missing", "The \"BusinessScope\" element is missing"),

  /** At least two "BusinessScope/Scope" elements must be present */
  INVALID_SCOPE_COUNT ("invalid-scope-count", "At least two \"BusinessScope/Scope\" elements must be present"),

  /** The provided document type identifier is invalid */
  INVALID_DOCUMENT_TYPE_IDENTIFIER ("invalid-document-type-identifier", "The provided document type identifier is invalid"),

  /** The provided process identifier is invalid */
  INVALID_PROCESS_IDENTIFIER ("invalid-process-identifier", "The provided process identifier is invalid"),

  /** The document type identifier is missing */
  MISSING_DOCUMENT_TYPE_IDENTIFIER ("missing-document-type-identifier", "The document type identifier is missing"),

  /** The process identifier is missing */
  MISSING_PROCESS_IDENTIFIER ("missing-process-identifier", "The process identifier is missing"),

  /** The main business message must be an XML element */
  INVALID_BUSINESS_MESSAGE_TYPE ("invalid-business-message-type", "The main business message must be an XML element"),

  /** The main business message is invalid */
  INVALID_BUSINESS_MESSAGE ("invalid-business-message", "The main business message is invalid"),

  /** The value of the "DocumentIdentification/Standard" element is invalid */
  INVALID_STANDARD ("invalid-standard", "The value of the \"DocumentIdentification/Standard\" element is invalid"),

  /**
   * The value of the "DocumentIdentification/TypeVersion" element is invalid
   */
  INVALID_TYPE_VERSION ("invalid-type-version", "The value of the \"DocumentIdentification/TypeVersion\" element is invalid"),

  /** The value of the "DocumentIdentification/Type" element is invalid */
  INVALID_TYPE ("invalid-type", "The value of the \"DocumentIdentification/Type\" element is invalid"),

  /**
   * The value of the "DocumentIdentification/InstanceIdentifier" element is
   * invalid
   */
  INVALID_INSTANCE_IDENTIFIER ("invalid-instance-identifier",
                               "The value of the \"DocumentIdentification/InstanceIdentifier\" element is invalid"),

  /**
   * The value of the "DocumentIdentification/CreationDateAndTime" element is
   * invalid
   */
  INVALID_CREATION_DATE_TIME ("invalid-creation-date-time",
                              "The value of the \"DocumentIdentification/CreationDateAndTime\" element is invalid");

  private final String m_sID;
  private final String m_sErrorMsg;

  EPeppolSBDHDocumentReadError (@Nonnull @Nonempty final String sID, @Nonnull @Nonempty final String sErrorMsg)
  {
    m_sID = sID;
    m_sErrorMsg = sErrorMsg;
  }

  @Nonnull
  @Nonempty
  public String getID ()
  {
    return m_sID;
  }

  /**
   * @return The English error message
   */
  @Nonnull
  @Nonempty
  public String getErrorMessage ()
  {
    return m_sErrorMsg;
  }

  @Nullable
  public static EPeppolSBDHDocumentReadError getFromIDOrNull (@Nullable final String sID)
  {
    return EnumHelper.getFromIDOrNull (EPeppolSBDHDocumentReadError.class, sID);
  }
}
