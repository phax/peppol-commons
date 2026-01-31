/*
 * Copyright (C) 2024-2026 Philip Helger
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
package com.helger.peppol.xhe.write;

import org.jspecify.annotations.NonNull;

import com.helger.annotation.concurrent.ThreadSafe;
import com.helger.base.enforce.ValueEnforcer;
import com.helger.peppol.xhe.CDBNAllianceXHE;
import com.helger.peppol.xhe.DBNAlliancePayload;
import com.helger.peppol.xhe.DBNAllianceXHEData;
import com.helger.xhe.v10.XHE10XHEType;
import com.helger.xhe.v10.cac.XHE10HeaderType;
import com.helger.xhe.v10.cac.XHE10PartyIdentificationType;
import com.helger.xhe.v10.cac.XHE10PartyType;
import com.helger.xhe.v10.cac.XHE10PayloadContentType;
import com.helger.xhe.v10.cac.XHE10PayloadType;
import com.helger.xhe.v10.cac.XHE10PayloadsType;
import com.helger.xhe.v10.cbc.XHE10ContentTypeCodeType;
import com.helger.xhe.v10.cbc.XHE10CustomizationIDType;
import com.helger.xhe.v10.cbc.XHE10DescriptionType;
import com.helger.xhe.v10.cbc.XHE10IDType;
import com.helger.xhe.v10.cbc.XHE10ProfileIDType;

/**
 * Convert DBNAlliance XHE data to a regular XHE document
 *
 * @author Robinson Garcia
 * @author Philip Helger
 */
@ThreadSafe
public class DBNAllianceXHEDataWriter
{
  DBNAllianceXHEDataWriter ()
  {}

  /**
   * Create a new {@link XHE10XHEType} from the specified document data.
   *
   * @param aData
   *        The document data to be used. May not be <code>null</code> and
   *        {@link DBNAllianceXHEData#areAllFieldsSet()} must return true!
   * @return Never <code>null</code>.
   * @throws IllegalArgumentException
   *         if not all document data fields are set!
   */
  @NonNull
  public static XHE10XHEType createExchangeHeaderEnvelope (@NonNull final DBNAllianceXHEData aData)
  {
    ValueEnforcer.notNull (aData, "Data");
    if (!aData.areAllFieldsSet ())
      throw new IllegalArgumentException ("Not all data fields are set!");

    final XHE10XHEType aXHE = new XHE10XHEType ();
    aXHE.setXHEVersionID (CDBNAllianceXHE.XHE_VERSION_ID);

    // CustomizationID
    {
      final XHE10CustomizationIDType aCustomizationID = new XHE10CustomizationIDType ();
      aCustomizationID.setSchemeID (CDBNAllianceXHE.CUSTOMIZATION_SCHEMA_ID);
      aCustomizationID.setValue (CDBNAllianceXHE.CUSTOMIZATION_ID);
      aXHE.setCustomizationID (aCustomizationID);
    }

    // ProfileID
    aXHE.setProfileID (CDBNAllianceXHE.PROFILE_ID);

    // Header data
    {
      final XHE10HeaderType aHeader = new XHE10HeaderType ();
      aHeader.setID (aData.getID ());
      aHeader.setCreationDateTime (aData.getCreationDateTime ());

      // From Party data
      {
        final XHE10PartyType aFromParty = new XHE10PartyType ();
        final XHE10PartyIdentificationType aPartyID = new XHE10PartyIdentificationType ();
        final XHE10IDType aID = new XHE10IDType ();
        aID.setSchemeID (aData.getFromPartyScheme ());
        aID.setValue (aData.getFromPartyValue ());
        aPartyID.setID (aID);
        aFromParty.addPartyIdentification (aPartyID);
        aHeader.setFromParty (aFromParty);
      }

      // To Party Data
      {
        final XHE10PartyType aToParty = new XHE10PartyType ();
        final XHE10PartyIdentificationType aPartyID = new XHE10PartyIdentificationType ();
        final XHE10IDType aID = new XHE10IDType ();
        aID.setSchemeID (aData.getToPartyScheme ());
        aID.setValue (aData.getToPartyValue ());
        aPartyID.setID (aID);
        aToParty.addPartyIdentification (aPartyID);
        aHeader.addToParty (aToParty);
      }

      aXHE.setHeader (aHeader);
    }

    {
      final XHE10PayloadsType aPayloads = new XHE10PayloadsType ();
      int nPayloadID = 1;
      for (final DBNAlliancePayload aDataPayload : aData.getPayloads ())
      {
        final XHE10PayloadType aPayload = new XHE10PayloadType ();
        // payload IDs MUST be numbered sequentially starting with the number 1.
        aPayload.setID (Integer.toString (nPayloadID++));
        aPayload.setInstanceEncryptionIndicator (aDataPayload.isInstanceEncryptionIndicator ());
        if (aDataPayload.hasInstanceEncryptionMethod ())
          aPayload.setInstanceEncryptionMethod (aDataPayload.getInstanceEncryptionMethod ());

        // Description data
        if (aDataPayload.hasDescription ())
        {
          final XHE10DescriptionType aDescription = new XHE10DescriptionType ();
          aDescription.setValue (aDataPayload.getDescription ());
          aPayload.addDescription (aDescription);
        }

        // Content Type (mandatory)
        {
          final XHE10ContentTypeCodeType aContentTypeCode = new XHE10ContentTypeCodeType ();
          aContentTypeCode.setListID (aDataPayload.getContentTypeCodeListID ());
          aContentTypeCode.setValue (aDataPayload.getContentTypeCode ());
          aPayload.setContentTypeCode (aContentTypeCode);
        }

        // Customization ID data
        if (aDataPayload.hasCustomizationID ())
        {
          final XHE10CustomizationIDType aCustomization = new XHE10CustomizationIDType ();
          aCustomization.setSchemeID (aDataPayload.getCustomizationIDSchemeID ());
          aCustomization.setValue (aDataPayload.getCustomizationID ());
          aPayload.setCustomizationID (aCustomization);
        }

        // Profile ID data
        if (aDataPayload.hasProfileID ())
        {
          final XHE10ProfileIDType aProfile = new XHE10ProfileIDType ();
          aProfile.setSchemeID (aDataPayload.getProfileIDSchemeID ());
          aProfile.setValue (aDataPayload.getProfileID ());
          aPayload.setProfileID (aProfile);
        }

        // Payload content data
        {
          final XHE10PayloadContentType aContent = new XHE10PayloadContentType ();
          aContent.addContent (aDataPayload.getPayloadContent ());
          aPayload.setPayloadContent (aContent);
        }

        aPayloads.addPayload (aPayload);
      }
      aXHE.setPayloads (aPayloads);
    }

    return aXHE;
  }
}
