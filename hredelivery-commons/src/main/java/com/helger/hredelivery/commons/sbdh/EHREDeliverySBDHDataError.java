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
package com.helger.hredelivery.commons.sbdh;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.helger.annotation.Nonempty;
import com.helger.base.id.IHasID;
import com.helger.base.lang.EnumHelper;
import com.helger.base.text.TextFormatter;

/**
 * This enum contains all the errors that can occur during HR eDelivery SBD reading
 *
 * @author Philip Helger
 */
public enum EHREDeliverySBDHDataError implements IHasID <String>
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

  /** The main business message is invalid */
  INVALID_BUSINESS_MESSAGE ("invalid-business-message",
                            "The main business message is invalid according to the defined rules."),

  /**
   * The value of the "DocumentIdentification/InstanceIdentifier" element is invalid
   */
  INVALID_INSTANCE_IDENTIFIER ("invalid-instance-identifier",
                               "The ''DocumentIdentification/InstanceIdentifier'' element has the invalid value ''{0}'' according to the defined rules."),

  /**
   * The value of the "DocumentIdentification/CreationDateAndTime" element is invalid
   */
  INVALID_CREATION_DATE_TIME ("invalid-creation-date-time",
                              "The ''DocumentIdentification/CreationDateAndTime'' element has the invalid value ''{0}'' according to the defined rules."),
  /**
   * This is a fallback error code, in case the ID cannot be resolved to a proper other error code.
   */
  GENERIC_SBDH_ERROR ("generic-sbdh-error", "A generic error occurred.");

  private final String m_sID;
  private final String m_sErrorMsg;

  EHREDeliverySBDHDataError (@NonNull @Nonempty final String sID, @NonNull @Nonempty final String sErrorMsg)
  {
    m_sID = sID;
    m_sErrorMsg = sErrorMsg;
  }

  @NonNull
  @Nonempty
  public String getID ()
  {
    return m_sID;
  }

  /**
   * @return The English error message
   */
  @NonNull
  @Nonempty
  public String getErrorMessage ()
  {
    return m_sErrorMsg;
  }

  /**
   * @param aArgs
   *        The arguments to format with. May neither be <code>null</code> nor empty.
   * @return The English error message, formatted with parameters.
   */
  @NonNull
  @Nonempty
  public String getErrorMessage (@NonNull @Nonempty final Object... aArgs)
  {
    return TextFormatter.getFormattedText (m_sErrorMsg, aArgs);
  }

  @Nullable
  public static EHREDeliverySBDHDataError getFromIDOrNull (@Nullable final String sID)
  {
    return EnumHelper.getFromIDOrNull (EHREDeliverySBDHDataError.class, sID);
  }

  @Nullable
  public static EHREDeliverySBDHDataError getFromIDOrDefault (@Nullable final String sID,
                                                              @Nullable final EHREDeliverySBDHDataError eDefault)
  {
    return EnumHelper.getFromIDOrDefault (EHREDeliverySBDHDataError.class, sID, eDefault);
  }
}
