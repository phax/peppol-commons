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
package com.helger.peppol.xhe;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.log.ConditionalLogger;
import com.helger.commons.string.StringHelper;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.peppolid.factory.IIdentifierFactory;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class contains all the DBNAlliance data per XHE document in a syntax 
 * neutral way. This class maps to the requirements of the Exchange Header 
 * Envelope (XHE) Version 1.0 specification.
 *
 * @author Robinson Garcia
 */
@NotThreadSafe
public class DBNAllianceXHEData {
  
  private static final Logger LOGGER = LoggerFactory.getLogger (DBNAllianceXHEData.class);

  private final IIdentifierFactory m_aIdentifierFactory;
  // FromParty
  private String m_sFromPartyScheme;
  private String m_sFromPartyValue;
  // ToParty
  private String m_sToPartyScheme;
  private String m_sToPartyValue;
  // Payloads
  private List<DBNAlliancePayload> m_aPayloads = new ArrayList<>();
  
  /**
   * Constructor
   *
   * @param aIdentifierFactory
   *        Identifier factory to be used. May not be <code>null</code>.
   */
  public DBNAllianceXHEData (@Nonnull final IIdentifierFactory aIdentifierFactory) {
    m_aIdentifierFactory = ValueEnforcer.notNull (aIdentifierFactory, "IdentifierFactory");
  }
  
  /**
   * @return The from party participant identifier scheme. May be <code>null</code>
   *         if not initialized. This field is mapped to
   *         <code>XHE/Header/FromParty/PartyIdentification/ID/@schemeID</code>
   *         .
   */
  @Nullable
  public String getFromPartyScheme ()
  {
    return m_sFromPartyScheme;
  }

  /**
   * @return The from party participant identifier value. May be <code>null</code>
   *         if not initialized. This field is mapped to
   *         <code>XHE/Header/FromParty/PartyIdentification/ID/</code>.
   */
  @Nullable
  public String getFromPartyValue ()
  {
    return m_sFromPartyValue;
  }

  /**
   * @return The from party participant identifier as a participant identifier or
   *         <code>null</code> if certain information are missing or are
   *         invalid.
   */
  @Nullable
  public IParticipantIdentifier getFromPartyAsIdentifier ()
  {
    return m_aIdentifierFactory.createParticipantIdentifier (m_sFromPartyScheme, m_sFromPartyValue);
  }

  /**
   * Set the sender participant identifier.
   *
   * @param sScheme
   *        The DBNAlliance identifier scheme. May neither be <code>null</code> 
   *        nor empty. This field is mapped to
   *        <code>XHE/Header/FromParty/PartyIdentification/ID/@schemeID</code>
   *        .
   * @param sValue
   *        The from party identifier value. May neither be <code>null</code> nor
   *        empty. This field is mapped to
   *        <code>XHE/Header/FromParty/PartyIdentification/ID/</code>.
   * @return this
   */
  @Nonnull
  public DBNAllianceXHEData setFromParty (@Nonnull @Nonempty final String sScheme, @Nonnull @Nonempty final String sValue)
  {
    ValueEnforcer.notEmpty (sScheme, "Scheme");
    ValueEnforcer.notEmpty (sValue, "Value");

    m_sFromPartyScheme = sScheme;
    m_sFromPartyValue = sValue;
    return this;
  }
  
  /**
   * Set the from party participant identifier.
   *
   * @param aFromPartyID
   *        The participant identifier to use. May not be <code>null</code>.
   * @return this
   */
  @Nonnull
  public DBNAllianceXHEData setFromParty (@Nonnull final IParticipantIdentifier aFromPartyID)
  {
    ValueEnforcer.notNull (aFromPartyID, "FromPartyID");

    return setFromParty (aFromPartyID.getScheme (), aFromPartyID.getValue ());
  }
  
  /**
   * @return The to party participant identifier scheme. May be
   *         <code>null</code> if not initialized. This field is mapped to
   *         <code>XHE/Header/ToParty/PartyIdentification/ID/@schemeID</code>
   *         .
   */
  @Nullable
  public String getToPartyScheme ()
  {
    return m_sToPartyScheme;
  }

  /**
   * @return The to party participant identifier value. May be <code>null</code>
   *         if not initialized. This field is mapped to
   *         <code>XHE/Header/ToParty/PartyIdentification/ID/</code>.
   */
  @Nullable
  public String getReceiverValue ()
  {
    return m_sToPartyValue;
  }

  /**
   * @return The to party participant identifier as a participant identifier or
   *         <code>null</code> if certain information are missing or are
   *         invalid.
   */
  @Nullable
  public IParticipantIdentifier getToPartyAsIdentifier ()
  {
    return m_aIdentifierFactory.createParticipantIdentifier (m_sToPartyScheme, m_sToPartyValue);
  }

  /**
   * Set the to party participant identifier.
   *
   * @param sScheme
   *        The DBNAlliance identifier scheme. May neither be <code>null</code> 
   *        nor empty. This field is mapped to
   *        <code>XHE/Header/ToParty/PartyIdentification/ID/@schemeID</code>
   *        .
   * @param sValue
   *        The to party identifier value. May neither be <code>null</code> nor
   *        empty. This field is mapped to
   *        <code>XHE/Header/ToParty/PartyIdentification/ID/</code>.
   * @return this
   */
  @Nonnull
  public DBNAllianceXHEData setToParty (@Nonnull @Nonempty final String sScheme, @Nonnull @Nonempty final String sValue)
  {
    ValueEnforcer.notEmpty (sScheme, "Scheme");
    ValueEnforcer.notEmpty (sValue, "Value");

    m_sToPartyScheme = sScheme;
    m_sToPartyValue = sValue;
    return this;
  }

  /**
   * Set the to party participant identifier.
   *
   * @param aToPartyID
   *        The participant identifier to use. May not be <code>null</code>.
   * @return this
   */
  @Nonnull
  public DBNAllianceXHEData setReceiver (@Nonnull final IParticipantIdentifier aToPartyID)
  {
    ValueEnforcer.notNull (aToPartyID, "ToPartyID");

    return setToParty (aToPartyID.getScheme (), aToPartyID.getValue ());
  }
  
  /**
   * Get the list of payloads of the exchange header envelope. 
   * This field is mapped to
   * <code>XHE/Payloads</code>
   * .
   *
   * @return The payloads list .
   */
  @Nullable
  public List<DBNAlliancePayload> getPayloads()
  {
    return m_aPayloads;
  }
  
  /**
   * Set the list of payloads of the exchange header envelope (XHE).
   *
   * @param aPayloads
   *        The list of payloads to be set. May not be <code>null</code>.
   * @return this
   */
  @Nonnull
  public DBNAllianceXHEData setPayloads (@Nonnull final List<DBNAlliancePayload> aPayloads)
  {
    ValueEnforcer.notNull (aPayloads, "Payloads");

    m_aPayloads = aPayloads;
    return this;
  }
  
  /**
   * Set an individual payload.
   *
   * @param aPayload
   *        An individual payload to be added to the list of payloads. 
   *        May not be <code>null</code>.
   * @return this
   */
  @Nonnull
  public DBNAllianceXHEData addPayload (@Nonnull final DBNAlliancePayload aPayload)
  {
    ValueEnforcer.notNull (aPayload, "Payload");

    m_aPayloads.add(aPayload);
    return this;
  }
  
  /**
   * @param bLogMissing
   *        <code>true</code> if log messages should be emitted,
   *        <code>false</code> if not
   * @return <code>true</code> if all mandatory fields required for creating an
   *         XHE are present, <code>false</code> if at least one field is not
   *         set.
   */
  public boolean areAllFieldsSet (final boolean bLogMissing)
  {
    final ConditionalLogger aCondLog = new ConditionalLogger (LOGGER, bLogMissing);
    if (StringHelper.hasNoText (m_sFromPartyScheme))
    {
      aCondLog.info ("DBNAlliance XHE data - From Party Scheme is missing");
      return false;
    }
    if (StringHelper.hasNoText (m_sFromPartyValue))
    {
      aCondLog.info ("DBNAlliance XHE data - From Party Value is missing");
      return false;
    }

    if (StringHelper.hasNoText (m_sToPartyScheme))
    {
      aCondLog.info ("DBNAlliance XHE data - To Party Scheme is missing");
      return false;
    }
    if (StringHelper.hasNoText (m_sToPartyValue))
    {
      aCondLog.info ("DBNAlliance XHE data - To Party Value is missing");
      return false;
    }

    if (m_aPayloads.isEmpty())
    {
      aCondLog.info ("DBNAlliance XHE data - An envelope MUST contain at least one business document.");
      return false;
    }

    return true;
  }

  /**
   * @return <code>true</code> if all mandatory fields required for creating an
   *         XHE are present, <code>false</code> if at least one field is not
   *         set.
   */
  public boolean areAllFieldsSet ()
  {
    return areAllFieldsSet (false);
  }
}
