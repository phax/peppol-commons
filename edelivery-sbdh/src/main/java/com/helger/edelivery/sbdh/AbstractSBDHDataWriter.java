/*
 * Copyright (C) 2015-2026 Philip Helger
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
package com.helger.edelivery.sbdh;

import org.jspecify.annotations.NonNull;
import org.unece.cefact.namespaces.sbdh.DocumentIdentification;
import org.unece.cefact.namespaces.sbdh.Partner;
import org.unece.cefact.namespaces.sbdh.PartnerIdentification;
import org.unece.cefact.namespaces.sbdh.StandardBusinessDocument;
import org.unece.cefact.namespaces.sbdh.StandardBusinessDocumentHeader;

import com.helger.annotation.concurrent.NotThreadSafe;
import com.helger.annotation.style.OverrideOnDemand;
import com.helger.base.enforce.ValueEnforcer;
import com.helger.base.string.StringHelper;
import com.helger.base.trait.IGenericImplTrait;

/**
 * Base class to convert the data of a Standard Business Document Header into a regular SBD
 * document. Everything except the business scope is identical in all SBDH based networks.
 *
 * @author Philip Helger
 * @param <DATATYPE>
 *        The SBDH data type to be written
 * @param <IMPLTYPE>
 *        The implementation type
 * @since 13.0.0
 */
@NotThreadSafe
public abstract class AbstractSBDHDataWriter <DATATYPE extends AbstractSBDHData <DATATYPE>, IMPLTYPE extends AbstractSBDHDataWriter <DATATYPE, IMPLTYPE>>
                                             implements
                                             IGenericImplTrait <IMPLTYPE>
{
  public static final boolean DEFAULT_FAVOUR_SPEED = false;

  private String m_sHeaderVersion = CSBDHConstants.HEADER_VERSION;
  private boolean m_bFavourSpeed = DEFAULT_FAVOUR_SPEED;

  protected AbstractSBDHDataWriter ()
  {}

  /**
   * @return The SBDH header version to be used. May not be <code>null</code>.
   */
  @NonNull
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
  @NonNull
  public final IMPLTYPE setHeaderVersion (@NonNull final String sHeaderVersion)
  {
    ValueEnforcer.notNull (sHeaderVersion, "HeaderVersion");
    m_sHeaderVersion = sHeaderVersion;
    return thisAsT ();
  }

  /**
   * @return <code>true</code> if speed is favoured, <code>false</code> if not. Default is
   *         {@link #DEFAULT_FAVOUR_SPEED}.
   */
  public final boolean isFavourSpeed ()
  {
    return m_bFavourSpeed;
  }

  /**
   * Enable or disable the "favour speed" option. If speed is favoured, the business message DOM
   * element is not cloned.
   *
   * @param bFavourSpeed
   *        <code>true</code> to favour speed, <code>false</code> to not favour speed.
   * @return this for chaining
   */
  @NonNull
  public final IMPLTYPE setFavourSpeed (final boolean bFavourSpeed)
  {
    m_bFavourSpeed = bFavourSpeed;
    return thisAsT ();
  }

  /**
   * Check if the provided data may be written. Throw an exception if not.
   *
   * @param aData
   *        The data to be checked. Never <code>null</code>.
   * @throws IllegalArgumentException
   *         if the data is not complete
   */
  @OverrideOnDemand
  protected abstract void checkData (@NonNull DATATYPE aData);

  /**
   * Fill the <code>BusinessScope</code> element of the provided header. This is the only part of
   * the SBDH that differs between the networks.
   *
   * @param aSBDH
   *        The header to be filled. Never <code>null</code>.
   * @param aData
   *        The source data. Never <code>null</code>.
   */
  protected abstract void fillBusinessScope (@NonNull StandardBusinessDocumentHeader aSBDH, @NonNull DATATYPE aData);

  /**
   * Create a new {@link StandardBusinessDocument} from the specified document data.
   *
   * @param aData
   *        The document data to be used. May not be <code>null</code> and must be complete.
   * @return Never <code>null</code>.
   * @throws IllegalArgumentException
   *         if not all document data fields are set
   */
  @NonNull
  public StandardBusinessDocument createStandardBusinessDocument (@NonNull final DATATYPE aData)
  {
    ValueEnforcer.notNull (aData, "Data");
    checkData (aData);

    final StandardBusinessDocumentHeader aSBDH = new StandardBusinessDocumentHeader ();
    aSBDH.setHeaderVersion (StringHelper.trim (m_sHeaderVersion));

    // Sender data
    {
      final Partner aSender = new Partner ();
      final PartnerIdentification aSenderID = new PartnerIdentification ();
      aSenderID.setAuthority (StringHelper.trim (aData.getSenderScheme ()));
      aSenderID.setValue (StringHelper.trim (aData.getSenderValue ()));
      aSender.setIdentifier (aSenderID);
      aSBDH.addSender (aSender);
    }

    // Receiver data
    {
      final Partner aReceiver = new Partner ();
      final PartnerIdentification aReceiverID = new PartnerIdentification ();
      aReceiverID.setAuthority (StringHelper.trim (aData.getReceiverScheme ()));
      aReceiverID.setValue (StringHelper.trim (aData.getReceiverValue ()));
      aReceiver.setIdentifier (aReceiverID);
      aSBDH.addReceiver (aReceiver);
    }

    // Document identification
    {
      final DocumentIdentification aDI = new DocumentIdentification ();
      aDI.setStandard (StringHelper.trim (aData.getStandard ()));
      aDI.setTypeVersion (StringHelper.trim (aData.getTypeVersion ()));
      aDI.setType (StringHelper.trim (aData.getType ()));
      aDI.setInstanceIdentifier (StringHelper.trim (aData.getInstanceIdentifier ()));
      aDI.setCreationDateAndTime (aData.getCreationDateAndTime ());
      aSBDH.setDocumentIdentification (aDI);
    }

    // Business scope - network specific
    fillBusinessScope (aSBDH, aData);

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
