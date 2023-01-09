/*
 * Copyright (C) 2014-2023 Philip Helger
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

import java.util.Map;

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
import com.helger.commons.string.StringHelper;
import com.helger.peppol.sbdh.CPeppolSBDH;
import com.helger.peppol.sbdh.PeppolSBDHDocument;

/**
 * Convert a Peppol SBDH document to a regular SBDH document
 *
 * @author Philip Helger
 */
@NotThreadSafe
public class PeppolSBDHDocumentWriter
{
  public static final boolean DEFAULT_FAVOUR_SPEED = false;

  private String m_sHeaderVersion = CPeppolSBDH.HEADER_VERSION;
  private boolean m_bFavourSpeed = DEFAULT_FAVOUR_SPEED;

  public PeppolSBDHDocumentWriter ()
  {}

  /**
   * @return The SBDH header version to be used. May not be <code>null</code>.
   */
  @Nonnull
  public final String getHeaderVersion ()
  {
    return m_sHeaderVersion;
  }

  /**
   * Set the header version to be used.
   *
   * @param sHeaderVersion
   *        The head version. May not be <code>null</code>.
   * @return this for chaining
   */
  @Nonnull
  public final PeppolSBDHDocumentWriter setHeaderVersion (@Nonnull final String sHeaderVersion)
  {
    ValueEnforcer.notNull (sHeaderVersion, "HeaderVersion");
    m_sHeaderVersion = sHeaderVersion;
    return this;
  }

  /**
   * @return <code>true</code> if speed is favoured, <code>false</code> if not.
   *         Default is {@link #DEFAULT_FAVOUR_SPEED}.
   * @since 8.8.1
   */
  public final boolean isFavourSpeed ()
  {
    return m_bFavourSpeed;
  }

  /**
   * Enable or disable the "favour speed" option. This
   *
   * @param bFavourSpeed
   *        <code>true</code> to favour speed, <code>false</code> to not favour
   *        speed.
   * @return this for chaining
   * @since 8.8.1
   */
  @Nonnull
  public final PeppolSBDHDocumentWriter setFavourSpeed (final boolean bFavourSpeed)
  {
    m_bFavourSpeed = bFavourSpeed;
    return this;
  }

  /**
   * Create a new {@link StandardBusinessDocument} from the specified document
   * data.
   *
   * @param aData
   *        The document data to be used. May not be <code>null</code> and
   *        {@link PeppolSBDHDocument#areAllFieldsSet()} must return true!
   * @return Never <code>null</code>.
   * @throws IllegalArgumentException
   *         if not all document data fields are set!
   */
  @Nonnull
  public StandardBusinessDocument createStandardBusinessDocument (@Nonnull final PeppolSBDHDocument aData)
  {
    ValueEnforcer.notNull (aData, "Data");
    if (!aData.areAllFieldsSet ())
      throw new IllegalArgumentException ("Not all data fields are set!");

    final StandardBusinessDocumentHeader aSBDH = new StandardBusinessDocumentHeader ();
    aSBDH.setHeaderVersion (m_sHeaderVersion);

    // Sender data
    {
      final Partner aSender = new Partner ();
      final PartnerIdentification aSenderID = new PartnerIdentification ();
      aSenderID.setAuthority (aData.getSenderScheme ());
      aSenderID.setValue (aData.getSenderValue ());
      aSender.setIdentifier (aSenderID);
      aSBDH.addSender (aSender);
    }

    // Receiver data
    {
      final Partner aReceiver = new Partner ();
      final PartnerIdentification aReceiverID = new PartnerIdentification ();
      aReceiverID.setAuthority (aData.getReceiverScheme ());
      aReceiverID.setValue (aData.getReceiverValue ());
      aReceiver.setIdentifier (aReceiverID);
      aSBDH.addReceiver (aReceiver);
    }

    // Document identification
    {
      final DocumentIdentification aDI = new DocumentIdentification ();
      aDI.setStandard (aData.getStandard ());
      aDI.setTypeVersion (aData.getTypeVersion ());
      aDI.setType (aData.getType ());
      aDI.setInstanceIdentifier (aData.getInstanceIdentifier ());
      aDI.setCreationDateAndTime (aData.getCreationDateAndTime ());
      aSBDH.setDocumentIdentification (aDI);
    }

    // Business scope
    {
      final BusinessScope aBusinessScope = new BusinessScope ();
      {
        final Scope aScope = new Scope ();
        aScope.setType (CPeppolSBDH.SCOPE_DOCUMENT_TYPE_ID);
        aScope.setInstanceIdentifier (aData.getDocumentTypeValue ());
        // The scheme was added in Spec v1.1
        aScope.setIdentifier (aData.getDocumentTypeScheme ());
        aBusinessScope.addScope (aScope);
      }
      {
        final Scope aScope = new Scope ();
        aScope.setType (CPeppolSBDH.SCOPE_PROCESS_ID);
        aScope.setInstanceIdentifier (aData.getProcessValue ());
        // The scheme was added in Spec v1.1
        aScope.setIdentifier (aData.getProcessScheme ());
        aBusinessScope.addScope (aScope);
      }

      // Add the additional attributes
      for (final Map.Entry <String, String> aEntry : aData.additionalAttributes ().entrySet ())
      {
        final Scope aScope = new Scope ();
        aScope.setType (aEntry.getKey ());
        // XSD requires InstanceIdentifier
        aScope.setInstanceIdentifier (StringHelper.getNotNull (aEntry.getValue ()));
        aBusinessScope.addScope (aScope);
      }

      aSBDH.setBusinessScope (aBusinessScope);
    }

    final StandardBusinessDocument aSBD = new StandardBusinessDocument ();
    aSBD.setStandardBusinessDocumentHeader (aSBDH);
    if (m_bFavourSpeed)
    {
      // Avoid cloning the business message DOM element
      aSBD.setAny (aData.getBusinessMessageNoClone ());
    }
    else
    {
      // getBusinessMessage already returns a cloned node!
      aSBD.setAny (aData.getBusinessMessage ());
    }
    return aSBD;
  }
}
