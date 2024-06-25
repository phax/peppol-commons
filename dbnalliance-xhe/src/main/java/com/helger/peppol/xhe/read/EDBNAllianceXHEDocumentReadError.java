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
package com.helger.peppol.xhe.read;

import com.helger.commons.annotation.Nonempty;
import com.helger.commons.id.IHasID;
import com.helger.commons.lang.EnumHelper;
import com.helger.commons.text.util.TextHelper;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * This enum contains all the errors that can occur during XHE reading
 *
 * @author Robinson Garcia
 */
public enum EDBNAllianceXHEDocumentReadError implements IHasID <String> 
{
  /** Failed to interpret ExchangeHeaderEnvelope as XML */
  INVALID_XHE_XML ("invalid-xhe-xml", "Failed to interpret ExchangeHeaderEnvelope as XML."),

  /** The StandardBusinessDocumentHeader element is not present */
  MISSING_PAYLOADS_PAYLOAD ("missing-payloads-payload", "The ''Payloads'' element is not present."),

  /** The "HeaderVersion" element has an illegal value */
  INVALID_XHE_VERSION_ID ("invalid-xhe-version-id", "The ''VersionID'' element has the illegal value ''{0}''."),
  
  /** The "XHE/CustomizationID" element is missing */
  CUSTOMIZATION_ID_MISSING ("customization-id-missing", "The ''XHE/CustomizationID'' element is missing."),
  
  /** The "XHE/CustomizationID/schemaID" attribute has an invalid value */
  INVALID_CUSTOMIZATION_ID_SCHEMA_ID ("invalid-customization-id-schema-id",
                                      "The ''XHE/CustomizationID/schemaID'' attribute has the invalid value ''{0}''."),

  /** The "XHE/CustomizationID" has an invalid value */
  INVALID_CUSTOMIZATION_ID_VALUE ("invalid-customization-id-value",
                                  "The ''XHE/CustomizationID'' has the invalid value ''{0}''."),
  
  /** The "XHE/ProfileID" element is missing */
  PROFILE_ID_MISSING ("profile-id-missing", "The ''XHE/ProfileID'' element is missing."),
  
  /** The "XHE/ProfileID/schemaID" attribute has an invalid value */
  INVALID_PROFILE_ID_SCHEMA_ID ("invalid-profile-id-schema-id",
                                      "The ''XHE/ProfileID/schemaID'' attribute has the invalid value ''{0}''."),

  /** The "XHE/ProfileID" has an invalid value */
  INVALID_PROFILE_ID_VALUE ("invalid-profile-id-value",
                                  "The ''XHE/ProfileID'' has the invalid value ''{0}''."),
  
  /** The "XHE/Header" element is missing */
  HEADER_MISSING ("xhe-header-missing", "The ''XHE/Header'' element is missing."),
  
  /** The "XHE/Header/ID" has an invalid value */
  INVALID_HEADER_ID ("invalid-header-id-value",
                                  "The ''XHE/Header/ID'' has the invalid value ''{0}''."),
  
  /** The value of the "XHE/Header/CreationDateAndTime" element is invalid */
  INVALID_CREATION_DATE_TIME ("invalid-creation-date-time",
                              "The ''XHE/Header/CreationDateAndTime'' element has the invalid value ''{0}'' according to the defined rules."),

  /** Not exactly one "XHE/Header/FromParty" element is present */
  INVALID_FROM_PARTY_COUNT ("invalid-from-party-count", 
                             "Not exactly one ''FromParty'' element is present but {0}."),

  /** The "XHE/Header/FromParty/PartyIdentification/ID/schemaID" attribute has an invalid value */
  INVALID_FROM_PARTY_SCHEMA_ID ("invalid-from-party-schema-id",
                                "The ''XHE/Header/FromParty/PartyIdentification/ID/schemaID'' attribute has the invalid value ''{0}''."),

  /** The "XHE/Header/FromParty/PartyIdentification/ID" has an invalid value */
  INVALID_FROM_PARTY_VALUE ("invalid-from-party-value", 
                            "The ''XHE/Header/FromParty/PartyIdentification/ID'' has the invalid value ''{0}''."),
  
  /** Not exactly one "XHE/Header/ToParty" element is present */
  INVALID_TO_PARTY_COUNT ("invalid-to-party-count", 
                          "Not exactly one ''ToParty'' element is present but {0}."),
  
  /** Not exactly one "XHE/Header/ToParty" element is present */
  INVALID_TO_PARTY_IDENTIFICATION_COUNT ("invalid-to-party-identification-count", 
                                         "Not exactly one ''ToParty/PartyIdentification'' element is present but {0}."),

  /** The "XHE/Header/ToParty/PartyIdentification/ID/schemaID" attribute has an invalid value */
  INVALID_TO_PARTY_SCHEMA_ID ("invalid-to-party-schema-id",
                              "The ''XHE/Header/ToParty/PartyIdentification/ID/schemaID'' attribute has the invalid value ''{0}''."),

  /** The "XHE/Header/FromParty/PartyIdentification/ID" has an invalid value */
  INVALID_TO_PARTY_VALUE ("invalid-from-party-value", 
                          "The ''XHE/Header/ToParty/PartyIdentification/ID'' has the invalid value ''{0}''."),
  
  /** The "XHE/Payloads/Payload/ID" has an invalid value */
  INVALID_PAYLOAD_ID_VALUE ("invalid-payload-id-value", 
                            "The ''XHE/Payloads/Payload/ID'' has the invalid value ''{0}''."),
  
  /** The "XHE/Payloads/Payload/ContentTypeCode/listID" has an invalid value */
  INVALID_CONTENT_TYPE_CODE_LIST_ID ("invalid-content-type-code-list-id", 
                                     "The ''XHE/Payloads/Payload/ContentTypeCode/listID'' has the invalid value ''{0}''."),
  
  /** The "XHE/Payloads/Payload/ContentTypeCode" has an invalid value */
  INVALID_CONTENT_TYPE_CODE_VALUE ("invalid-content-type-code-value", 
                                   "The ''XHE/Payloads/Payload/ContentTypeCode'' has the invalid value ''{0}''."),
  
  /** The "XHE/Payloads/Payload/InstanceEncryptionIndicator" has an invalid value */
  INVALID_INSTANCE_ENCRYPTION_INDICATOR_VALUE ("invalid-instance-encryption-indicator-value", 
                                               "The ''XHE/Payloads/Payload/InstanceEncryptionIndicator'' has the invalid value ''{0}''."),

  /** The "XHE/Payloads/Payload/InstanceEncryptionIndicator" has an invalid value */
  INSTANCE_ENCRYPTION_INDICATOR_MISSING ("instance-encryption-indicator-missing", 
                                         "The ''XHE/Payloads/Payload/InstanceEncryptionIndicator'' element is missing."),
  
  /** The main business message is invalid */
  INVALID_BUSINESS_MESSAGE ("invalid-business-message",
                            "The main business message is invalid according to the defined rules."),

  /**
   * This is a fallback error code, in case the ID cannot be resolved to a
   * proper other error code.
   *
   */
  GENERIC_XHE_ERROR ("generic-xhe-error", "A generic error occurred.");

  private final String m_sID;
  private final String m_sErrorMsg;

  EDBNAllianceXHEDocumentReadError (@Nonnull @Nonempty final String sID, @Nonnull @Nonempty final String sErrorMsg)
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
  public static EDBNAllianceXHEDocumentReadError getFromIDOrNull (@Nullable final String sID)
  {
    return EnumHelper.getFromIDOrNull (EDBNAllianceXHEDocumentReadError.class, sID);
  }

  @Nullable
  public static EDBNAllianceXHEDocumentReadError getFromIDOrDefault (@Nullable final String sID,
                                                                 @Nullable final EDBNAllianceXHEDocumentReadError eDefault)
  {
    return EnumHelper.getFromIDOrDefault (EDBNAllianceXHEDocumentReadError.class, sID, eDefault);
  }
}
