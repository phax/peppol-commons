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
package com.helger.peppol.xhe.write;

import com.helger.commons.ValueEnforcer;
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
import java.util.List;
import javax.annotation.Nonnull;

/**
 * Convert a DBNAlliance XHE document to a regular XHE document
 *
 * @author Robinson Garcia
 */
public class DBNAllianceXHEDocumentWriter {
  
  /**
   * Create a new {@link XHE} from the specified document
   * data.
   *
   * @param aData
   *        The document data to be used. May not be <code>null</code> and
   *        {@link DBNAllianceXHEData#areAllFieldsSet()} must return true!
   * @return Never <code>null</code>.
   * @throws IllegalArgumentException
   *         if not all document data fields are set!
   */
  @Nonnull
  public XHE10XHEType createExchangeHeaderEnvelope (@Nonnull final DBNAllianceXHEData aData)
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
      
      aXHE.setHeader(aHeader);
    }
    
    {
      final XHE10PayloadsType aPayloads = new XHE10PayloadsType ();
      final List<DBNAlliancePayload> aDataPayloads = aData.getPayloads ();
      int iID = 1;
      for (DBNAlliancePayload aDataPayload : aDataPayloads) {
        
        final XHE10PayloadType aPayload = new XHE10PayloadType ();
        // payload IDs MUST be numbered sequen=ally star=ng with the number 1.
        aPayload.setID (String.valueOf (iID++));
        aPayload.setInstanceEncryptionIndicator (aDataPayload.getInstanceEncryptionIndicator ());
        aPayload.setInstanceEncryptionMethod (aDataPayload.getInstanceEncryptionMethod ());
        
        // Description data
        {
          final XHE10DescriptionType aDescription = new XHE10DescriptionType ();
          aDescription.setValue (aDataPayload.getDescription ());
          aPayload.addDescription (aDescription);
        }
        
        // Content Type data
        {
          final XHE10ContentTypeCodeType aContentTypeCode = new XHE10ContentTypeCodeType ();
          aContentTypeCode.setListID (aDataPayload.getContentTypeCodeListID ());
          aContentTypeCode.setValue (aDataPayload.getContentTypeCodeValue ());
          aPayload.setContentTypeCode(aContentTypeCode);
        }
        
        // Customization ID data
        {
          final XHE10CustomizationIDType aCustomization = new XHE10CustomizationIDType ();
          aCustomization.setSchemeID (aDataPayload.getCustomizationIDScheme ());
          aCustomization.setValue (aDataPayload.getCustomizationIDValue ());
          aPayload.setCustomizationID (aCustomization);
        }
        
        // Profile ID data
        {
          final XHE10ProfileIDType aProfile = new XHE10ProfileIDType ();
          aProfile.setSchemeID (aDataPayload.getProfileIDScheme ());
          aProfile.setValue (aDataPayload.getProfileIDValue ());
          aPayload.setProfileID (aProfile);
        }
        
        // Payload content data
        {
          final XHE10PayloadContentType aContent = new XHE10PayloadContentType ();
          aContent.addContent(aDataPayload.getPayloadContentNoClone());
        }
        
        aPayloads.addPayload (aPayload);
      }
      aXHE.setPayloads (aPayloads);
    }

    return aXHE;
  }
}
