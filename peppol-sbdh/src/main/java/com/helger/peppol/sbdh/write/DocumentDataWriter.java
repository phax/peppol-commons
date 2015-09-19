/**
 * Copyright (C) 2014-2015 Philip Helger
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
package com.helger.peppol.sbdh.write;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;

import org.unece.cefact.namespaces.sbdh.BusinessScope;
import org.unece.cefact.namespaces.sbdh.DocumentIdentification;
import org.unece.cefact.namespaces.sbdh.Partner;
import org.unece.cefact.namespaces.sbdh.PartnerIdentification;
import org.unece.cefact.namespaces.sbdh.Scope;
import org.unece.cefact.namespaces.sbdh.StandardBusinessDocument;
import org.unece.cefact.namespaces.sbdh.StandardBusinessDocumentHeader;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.OverrideOnDemand;
import com.helger.datetime.util.PDTXMLConverter;
import com.helger.peppol.sbdh.CPeppolSBDH;
import com.helger.peppol.sbdh.DocumentData;

@NotThreadSafe
public class DocumentDataWriter
{
  public DocumentDataWriter ()
  {}

  /**
   * Override this method to customize the created header version
   *
   * @return The SBDH header version to be used. May not be <code>null</code>.
   */
  @Nonnull
  @OverrideOnDemand
  protected String getHeaderVersion ()
  {
    return CPeppolSBDH.HEADER_VERSION;
  }

  /**
   * Create a new {@link StandardBusinessDocument} from the specified document
   * data.
   *
   * @param aData
   *        The document data to be used. May not be <code>null</code> and
   *        {@link DocumentData#areAllFieldsSet()} must return true!
   * @return Never <code>null</code>.
   * @throws IllegalArgumentException
   *         if not all document data fields are set!
   */
  @Nonnull
  public StandardBusinessDocument createStandardBusinessDocument (@Nonnull final DocumentData aData)
  {
    ValueEnforcer.notNull (aData, "Data");
    if (!aData.areAllFieldsSet ())
      throw new IllegalArgumentException ("Not all data fields are set!");

    final StandardBusinessDocument aSBD = new StandardBusinessDocument ();
    {
      final StandardBusinessDocumentHeader aSBDH = new StandardBusinessDocumentHeader ();
      aSBDH.setHeaderVersion (getHeaderVersion ());

      // Sender data
      {
        final Partner aSender = new Partner ();
        final PartnerIdentification aSenderID = new PartnerIdentification ();
        aSenderID.setAuthority (aData.getSenderScheme ());
        aSenderID.setValue (aData.getSenderValue ());
        aSender.setIdentifier (aSenderID);
        aSBDH.getSender ().add (aSender);

      }
      // Receiver data
      {
        final Partner aReceiver = new Partner ();
        final PartnerIdentification aReceiverID = new PartnerIdentification ();
        aReceiverID.setAuthority (aData.getReceiverScheme ());
        aReceiverID.setValue (aData.getReceiverValue ());
        aReceiver.setIdentifier (aReceiverID);
        aSBDH.getReceiver ().add (aReceiver);
      }

      // Document identification
      {
        final DocumentIdentification aDI = new DocumentIdentification ();
        aDI.setStandard (aData.getStandard ());
        aDI.setTypeVersion (aData.getTypeVersion ());
        aDI.setType (aData.getType ());
        aDI.setInstanceIdentifier (aData.getInstanceIdentifier ());
        aDI.setCreationDateAndTime (PDTXMLConverter.getXMLCalendar (aData.getCreationDateAndTime ()));
        aSBDH.setDocumentIdentification (aDI);
      }

      // Business scope
      {
        final BusinessScope aBusinessScope = new BusinessScope ();
        Scope aScope = new Scope ();
        aScope.setType (CPeppolSBDH.SCOPE_DOCUMENT_TYPE_ID);
        // The scheme is currently not part of the specs!
        aScope.setInstanceIdentifier (aData.getDocumentTypeValue ());
        aBusinessScope.getScope ().add (aScope);

        aScope = new Scope ();
        aScope.setType (CPeppolSBDH.SCOPE_PROCESS_ID);
        // The scheme is currently not part of the specs!
        aScope.setInstanceIdentifier (aData.getProcessValue ());
        aBusinessScope.getScope ().add (aScope);
        aSBDH.setBusinessScope (aBusinessScope);
      }
      aSBD.setStandardBusinessDocumentHeader (aSBDH);
    }
    // getBusinessMessage already returns a cloned node!
    aSBD.setAny (aData.getBusinessMessage ());
    return aSBD;
  }
}
