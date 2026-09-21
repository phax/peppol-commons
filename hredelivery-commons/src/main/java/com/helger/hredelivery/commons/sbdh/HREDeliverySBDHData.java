/*
 * Copyright (C) 2025-2026 Philip Helger
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

import java.util.UUID;
import java.util.function.Consumer;

import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.unece.cefact.namespaces.sbdh.StandardBusinessDocument;
import org.w3c.dom.Element;

import com.helger.annotation.Nonempty;
import com.helger.annotation.concurrent.NotThreadSafe;
import com.helger.base.enforce.ValueEnforcer;
import com.helger.base.string.StringHelper;
import com.helger.datetime.helper.PDTFactory;
import com.helger.network.sbdh.AbstractSBDHData;
import com.helger.peppolid.factory.IIdentifierFactory;
import com.helger.peppolid.peppol.PeppolIdentifierHelper;

/**
 * This class contains all the HR eDelivery data per SBDH document in a syntax neutral way. The
 * network neutral parts are contained in {@link AbstractSBDHData}.
 *
 * @author Philip Helger
 */
@NotThreadSafe
public class HREDeliverySBDHData extends AbstractSBDHData <HREDeliverySBDHData>
{
  private static final Logger LOGGER = LoggerFactory.getLogger (HREDeliverySBDHData.class);

  /**
   * Constructor
   *
   * @param aIdentifierFactory
   *        Identifier factory to be used. May not be <code>null</code>.
   */
  public HREDeliverySBDHData (@NonNull final IIdentifierFactory aIdentifierFactory)
  {
    super (aIdentifierFactory);
  }

  /**
   * Set the sender participant identifier value using the default identifier scheme/authority
   * {@link PeppolIdentifierHelper#DEFAULT_PARTICIPANT_SCHEME}.
   *
   * @param sValue
   *        The sender identifier value. May neither be <code>null</code> nor empty. This field is
   *        mapped to <code>StandardBusinessDocumentHeader/Sender/Identifier/</code>.
   * @return this
   */
  @NonNull
  public HREDeliverySBDHData setSenderWithDefaultScheme (@NonNull @Nonempty final String sValue)
  {
    return setSender (PeppolIdentifierHelper.DEFAULT_PARTICIPANT_SCHEME, sValue);
  }

  /**
   * Set the receiver participant identifier value using the default identifier scheme/authority
   * {@link PeppolIdentifierHelper#DEFAULT_PARTICIPANT_SCHEME}.
   *
   * @param sValue
   *        The sender identifier value. May neither be <code>null</code> nor empty. This field is
   *        mapped to <code>StandardBusinessDocumentHeader/Receiver/Identifier/</code>.
   * @return this
   */
  @NonNull
  public HREDeliverySBDHData setReceiverWithDefaultScheme (@NonNull @Nonempty final String sValue)
  {
    return setReceiver (PeppolIdentifierHelper.DEFAULT_PARTICIPANT_SCHEME, sValue);
  }

  /**
   * Check if all mandatory fields are set in the SBDH data.
   *
   * @param bLogMissing
   *        <code>true</code> if log messages should be emitted, <code>false</code> if not
   * @return <code>true</code> if all mandatory fields required for creating an SBDH are present,
   *         <code>false</code> if at least one field is not set.
   */
  public boolean areAllFieldsSet (final boolean bLogMissing)
  {
    return areAllFieldsSet (bLogMissing ? LOGGER::info : x -> {});
  }

  /**
   * Check if all mandatory fields are set in the SBDH data.
   *
   * @param aMissingFieldConsumer
   *        The consumer to be invoked for each missing field. May not be <code>null</code>
   * @return <code>true</code> if all mandatory fields required for creating an SBDH are present,
   *         <code>false</code> if at least one field is not set.
   */
  public boolean areAllFieldsSet (@NonNull final Consumer <String> aMissingFieldConsumer)
  {
    ValueEnforcer.notNull (aMissingFieldConsumer, "MissingFieldConsumer");

    int nMissing = 0;
    if (StringHelper.isEmpty (getSenderScheme ()))
    {
      aMissingFieldConsumer.accept ("HR eDelivery SBDH data - Sender Scheme is missing");
      nMissing++;
    }
    if (StringHelper.isEmpty (getSenderValue ()))
    {
      aMissingFieldConsumer.accept ("HR eDelivery SBDH data - Sender Value is missing");
      nMissing++;
    }

    if (StringHelper.isEmpty (getReceiverScheme ()))
    {
      aMissingFieldConsumer.accept ("HR eDelivery SBDH data - Receiver Scheme is missing");
      nMissing++;
    }
    if (StringHelper.isEmpty (getReceiverValue ()))
    {
      aMissingFieldConsumer.accept ("HR eDelivery SBDH data - Reeiver Value is missing");
      nMissing++;
    }

    if (StringHelper.isEmpty (getStandard ()))
    {
      aMissingFieldConsumer.accept ("HR eDelivery SBDH data - Standard is missing");
      nMissing++;
    }
    if (StringHelper.isEmpty (getTypeVersion ()))
    {
      aMissingFieldConsumer.accept ("HR eDelivery SBDH data - Type Version is missing");
      nMissing++;
    }
    if (StringHelper.isEmpty (getType ()))
    {
      aMissingFieldConsumer.accept ("HR eDelivery SBDH data - Type is missing");
      nMissing++;
    }
    if (StringHelper.isEmpty (getInstanceIdentifier ()))
    {
      aMissingFieldConsumer.accept ("HR eDelivery SBDH data - Instance Identifier is missing");
      nMissing++;
    }
    if (getCreationDateAndTime () == null)
    {
      aMissingFieldConsumer.accept ("HR eDelivery SBDH data - Creation Date and Time is missing");
      nMissing++;
    }
    if (getBusinessMessageNoClone () == null)
    {
      aMissingFieldConsumer.accept ("HR eDelivery SBDH data - Business Message is missing");
      nMissing++;
    }

    return nMissing == 0;
  }

  /**
   * @return <code>true</code> if all mandatory fields required for creating an SBDH are present,
   *         <code>false</code> if at least one field is not set.
   */
  public boolean areAllFieldsSet ()
  {
    return areAllFieldsSet (false);
  }

  /**
   * @return A generic JAXB SBD document of this data. Never <code>null</code>.
   * @see HREDeliverySBDHDataWriter for the main logic
   */
  @NonNull
  public StandardBusinessDocument getAsStandardBusinessDocument ()
  {
    return new HREDeliverySBDHDataWriter ().createStandardBusinessDocument (this);
  }

  /**
   * Create a new {@link HREDeliverySBDHData} object for a business message assuming it is UBL 2.1.
   * The resulting object has all required fields set, except for:
   * <ul>
   * <li>sender ID</li>
   * <li>receiver ID</li>
   * <li>document type ID</li>
   * <li>and process ID</li>
   * </ul>
   *
   * @param aBusinessMessage
   *        The XML business message. May not be <code>null</code>.
   * @param aIdentifierFactory
   *        Identifier factory to be used. May not be <code>null</code>.
   * @return A pre-filled {@link HREDeliverySBDHData} object with some information still missing.
   * @see #setSender(String, String)
   * @see #setReceiver(String, String)
   */
  @NonNull
  public static HREDeliverySBDHData createUBL21 (@NonNull final Element aBusinessMessage,
                                                 @NonNull final IIdentifierFactory aIdentifierFactory)
  {
    ValueEnforcer.notNull (aBusinessMessage, "BusinessMessage");

    final HREDeliverySBDHData ret = new HREDeliverySBDHData (aIdentifierFactory);
    ret.setBusinessMessage (aBusinessMessage);
    // 1. Always use UBL 2.1
    // 2. Use a new UUID as the instance identifier
    // 3. Use the current date time
    ret.setDocumentIdentification (aBusinessMessage.getNamespaceURI (),
                                   CHREDeliverySBDH.TYPE_VERSION_21,
                                   aBusinessMessage.getLocalName (),
                                   UUID.randomUUID ().toString (),
                                   PDTFactory.getCurrentXMLOffsetDateTimeMillisOnly ());
    return ret;
  }
}
