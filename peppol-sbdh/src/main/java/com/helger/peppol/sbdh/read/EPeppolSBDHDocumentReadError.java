/*
 * Copyright (C) 2014-2024 Philip Helger
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
import com.helger.commons.text.util.TextHelper;
import com.helger.xsds.peppol.id1.ChangeV10;

/**
 * This enum contains all the errors that can occur during SBD reading
 *
 * @author Philip Helger
 */
@ChangeV10 ("Move to package of PeppolSBDHData; rename to EPeppolSBDHDataError")
public enum EPeppolSBDHDocumentReadError implements IHasID <String>
{
  /** Failed to interpret StandardBusinessDocument as XML */
  INVALID_SBD_XML ("invalid-sbd-xml", "Failed to interpret StandardBusinessDocument as XML."),

  /** The StandardBusinessDocumentHeader element is not present */
  MISSING_SBDH ("missing-sbdh", "The ''StandardBusinessDocumentHeader'' element is not present."),

  /** The "HeaderVersion" element has an illegal value */
  INVALID_HEADER_VERSION ("invalid-header-version", "The ''HeaderVersion'' element has the illegal value ''{0}''."),

  /** Not exactly one "Sender" element is present */
  INVALID_SENDER_COUNT ("invalid-sender-count", "Not exactly one ''Sender'' element is present but {0}."),

  /** The "Sender/Identifier/Authority" attribute has an invalid value */
  INVALID_SENDER_AUTHORITY ("invalid-sender-authority",
                            "The ''Sender/Identifier/Authority'' attribute has the invalid value ''{0}''."),

  /** The "Sender/Identifier" has an invalid value */
  INVALID_SENDER_VALUE ("invalid-sender-value", "The ''Sender/Identifier'' has the invalid value ''{0}''."),

  /** Not exactly one "Receiver" element is present */
  INVALID_RECEIVER_COUNT ("invalid-receiver-count", "Not exactly one ''Receiver'' element is present but {0}."),

  /** The "Receiver/Identifier/Authority" attribute has an invalid value */
  INVALID_RECEIVER_AUTHORITY ("invalid-receiver-authority",
                              "The ''Receiver/Identifier/Authority'' attribute has the invalid value ''{0}''."),

  /** The "Receiver/Identifier" has an invalid value */
  INVALID_RECEIVER_VALUE ("invalid-receiver-value", "The ''Receiver/Identifier'' has the invalid value ''{0}''."),

  /** The "BusinessScope" element is missing */
  BUSINESS_SCOPE_MISSING ("business-scope-missing", "The ''BusinessScope'' element is missing."),

  /** At least two "BusinessScope/Scope" elements must be present */
  INVALID_SCOPE_COUNT ("invalid-scope-count",
                       "At least {0} ''BusinessScope/Scope'' elements must be present but found {1}."),

  /** The provided document type identifier is invalid */
  INVALID_DOCUMENT_TYPE_IDENTIFIER ("invalid-document-type-identifier",
                                    "The provided document type identifier ''{0}'' is invalid according to the defined rules."),

  /** The provided process identifier is invalid */
  INVALID_PROCESS_IDENTIFIER ("invalid-process-identifier",
                              "The provided process identifier ''{0}'' is invalid according to the defined rules."),

  /** The provided C1 country code is invalid */
  INVALID_COUNTRY_C1 ("invalid-country-c1",
                      "The provided C1 country code ''{0}'' is invalid according to the defined rules."),

  /** The document type identifier is missing */
  MISSING_DOCUMENT_TYPE_IDENTIFIER ("missing-document-type-identifier", "The document type identifier is missing."),

  /** The process identifier is missing */
  MISSING_PROCESS_IDENTIFIER ("missing-process-identifier", "The process identifier is missing."),

  /** The C1 country code is missing */
  MISSING_COUNTRY_C1 ("missing-country-c1", "The C1 country code is missing."),

  /** The main business message is invalid */
  INVALID_BUSINESS_MESSAGE ("invalid-business-message",
                            "The main business message is invalid according to the defined rules."),

  /** The value of the "DocumentIdentification/Standard" element is invalid */
  INVALID_STANDARD ("invalid-standard",
                    "The ''DocumentIdentification/Standard'' element has the illegal value ''{0}''." +
                                        " It must equal the namespace URI of the business message (''{1}'')." +
                                        " It must also be the beginning of the document type identifier value (''{2}'')."),

  /**
   * The value of the "DocumentIdentification/TypeVersion" element is invalid
   */
  INVALID_TYPE_VERSION ("invalid-type-version",
                        "The ''DocumentIdentification/TypeVersion'' element has the invalid value ''{0}''." +
                                                " It may not colon a colon character and it must be the end of the document type identifier value (''{1}'')."),

  /** The value of the "DocumentIdentification/Type" element is invalid */
  INVALID_TYPE ("invalid-type",
                "The ''DocumentIdentification/Type''  element has the invalid value ''{0}''." +
                                " It must match the local name of the root element of the business message (''{1}'')."),

  /**
   * The value of the "DocumentIdentification/InstanceIdentifier" element is
   * invalid
   */
  INVALID_INSTANCE_IDENTIFIER ("invalid-instance-identifier",
                               "The ''DocumentIdentification/InstanceIdentifier'' element has the invalid value ''{0}'' according to the defined rules."),

  /**
   * The value of the "DocumentIdentification/CreationDateAndTime" element is
   * invalid
   */
  INVALID_CREATION_DATE_TIME ("invalid-creation-date-time",
                              "The ''DocumentIdentification/CreationDateAndTime'' element has the invalid value ''{0}'' according to the defined rules."),
  /**
   * This is a fallback error code, in case the ID cannot be resolved to a
   * proper other error code.
   *
   * @since 9.1.4
   */
  GENERIC_SBDH_ERROR ("generic-sbdh-error", "A generic error occurred.");

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

  /**
   * @param aArgs
   *        The arguments to format with. May neither be <code>null</code> nor
   *        empty.
   * @return The English error message, formatted with parameters.
   */
  @Nonnull
  @Nonempty
  public String getErrorMessage (@Nonnull @Nonempty final Object... aArgs)
  {
    return TextHelper.getFormattedText (m_sErrorMsg, aArgs);
  }

  @Nullable
  public static EPeppolSBDHDocumentReadError getFromIDOrNull (@Nullable final String sID)
  {
    return EnumHelper.getFromIDOrNull (EPeppolSBDHDocumentReadError.class, sID);
  }

  @Nullable
  public static EPeppolSBDHDocumentReadError getFromIDOrDefault (@Nullable final String sID,
                                                                 @Nullable final EPeppolSBDHDocumentReadError eDefault)
  {
    return EnumHelper.getFromIDOrDefault (EPeppolSBDHDocumentReadError.class, sID, eDefault);
  }
}
