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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Map;

import org.jspecify.annotations.NonNull;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.helger.collection.commons.CommonsLinkedHashMap;
import com.helger.collection.commons.ICommonsOrderedMap;
import com.helger.datetime.helper.PDTFactory;
import com.helger.datetime.web.PDTWebDateHelper;
import com.helger.datetime.xml.XMLOffsetDateTime;
import com.helger.io.resource.ClassPathResource;
import com.helger.io.resource.IReadableResource;
import com.helger.peppolid.factory.PeppolIdentifierFactory;
import com.helger.peppolid.peppol.PeppolIdentifierHelper;
import com.helger.unittest.support.TestHelper;
import com.helger.xml.serialize.read.DOMReader;
import com.helger.xml.serialize.read.DOMReaderSettings;

/**
 * Test class for class {@link HREDeliverySBDHDataReader}.
 *
 * @author Philip Helger
 */
public final class HREDeliverySBDHDataReaderTest
{
  private static final ICommonsOrderedMap <String, EHREDeliverySBDHDataError> BAD_CASES = new CommonsLinkedHashMap <> ();

  static
  {
    BAD_CASES.put ("bad-invalid-business-message.xml", EHREDeliverySBDHDataError.INVALID_SBD_XML);
    BAD_CASES.put ("bad-invalid-creation-date-and-time.xml", EHREDeliverySBDHDataError.INVALID_SBD_XML);
    BAD_CASES.put ("bad-invalid-header-version.xml", EHREDeliverySBDHDataError.INVALID_HEADER_VERSION);
    BAD_CASES.put ("bad-invalid-instance-identifier.xml", EHREDeliverySBDHDataError.INVALID_INSTANCE_IDENTIFIER);
    BAD_CASES.put ("bad-invalid-receiver-authority.xml", EHREDeliverySBDHDataError.INVALID_RECEIVER_AUTHORITY);
    BAD_CASES.put ("bad-invalid-receiver-value.xml", EHREDeliverySBDHDataError.INVALID_RECEIVER_VALUE);
    BAD_CASES.put ("bad-invalid-sender-authority.xml", EHREDeliverySBDHDataError.INVALID_SENDER_AUTHORITY);
    BAD_CASES.put ("bad-invalid-sender-value.xml", EHREDeliverySBDHDataError.INVALID_SENDER_VALUE);
    BAD_CASES.put ("bad-no-business-message.xml", EHREDeliverySBDHDataError.INVALID_SBD_XML);
    BAD_CASES.put ("bad-no-sbdh.xml", EHREDeliverySBDHDataError.MISSING_SBDH);
    BAD_CASES.put ("bad-no-xml.txt", EHREDeliverySBDHDataError.INVALID_SBD_XML);
    BAD_CASES.put ("bad-too-few-receivers.xml", EHREDeliverySBDHDataError.INVALID_SBD_XML);
    BAD_CASES.put ("bad-too-few-senders.xml", EHREDeliverySBDHDataError.INVALID_SBD_XML);
    BAD_CASES.put ("bad-too-many-receivers.xml", EHREDeliverySBDHDataError.INVALID_RECEIVER_COUNT);
    BAD_CASES.put ("bad-too-many-senders.xml", EHREDeliverySBDHDataError.INVALID_SENDER_COUNT);
  }

  private static final Logger LOGGER = LoggerFactory.getLogger (HREDeliverySBDHDataReaderTest.class);

  @Test
  public void testReadGoodV10AndCheckResults () throws HREDeliverySBDHDataReadException
  {
    // Read good.xml
    final IReadableResource aRes = HREDeliverySBDHTestFiles.getFirstGoodCase ();
    assertTrue (aRes.getPath (), aRes.exists ());

    final HREDeliverySBDHDataReader aReader = new HREDeliverySBDHDataReader (PeppolIdentifierFactory.INSTANCE);
    final HREDeliverySBDHData aData = aReader.extractData (aRes);
    assertNotNull (aData);

    assertTrue (aData.areAllFieldsSet ());
    assertEquals (PeppolIdentifierHelper.DEFAULT_PARTICIPANT_SCHEME, aData.getSenderScheme ());
    assertEquals ("0088:7315458756324", aData.getSenderValue ());
    assertEquals (PeppolIdentifierHelper.DEFAULT_PARTICIPANT_SCHEME, aData.getReceiverScheme ());
    assertEquals ("0088:4562458856624", aData.getReceiverValue ());
    assertEquals ("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", aData.getStandard ());
    assertEquals ("2.1", aData.getTypeVersion ());
    assertEquals ("Invoice", aData.getType ());
    assertEquals ("3739db04-7e4e-4c89-b170-c543dd252249", aData.getInstanceIdentifier ());
    assertEquals ("2013-02-19T05:10:10Z", PDTWebDateHelper.getAsStringXSD (aData.getCreationDateAndTime ()));
    assertTrue (aData.hasBusinessMessage ());
    assertEquals ("Invoice", aData.getBusinessMessage ().getLocalName ());

    TestHelper.testToStringImplementation (aData);
  }

  @Test
  public void testReadGoodAndCheckResults () throws HREDeliverySBDHDataReadException
  {
    // Read good.xml
    final IReadableResource aRes = HREDeliverySBDHTestFiles.getFirstGoodCase ();
    assertTrue (aRes.getPath (), aRes.exists ());

    final HREDeliverySBDHDataReader aReader = new HREDeliverySBDHDataReader (PeppolIdentifierFactory.INSTANCE);
    final HREDeliverySBDHData aData = aReader.extractData (aRes);
    assertNotNull (aData);

    assertTrue (aData.areAllFieldsSet ());
    assertEquals (PeppolIdentifierHelper.DEFAULT_PARTICIPANT_SCHEME, aData.getSenderScheme ());
    assertEquals ("0088:7315458756324", aData.getSenderValue ());
    assertEquals (PeppolIdentifierHelper.DEFAULT_PARTICIPANT_SCHEME, aData.getReceiverScheme ());
    assertEquals ("0088:4562458856624", aData.getReceiverValue ());
    assertEquals ("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", aData.getStandard ());
    assertEquals ("2.1", aData.getTypeVersion ());
    assertEquals ("Invoice", aData.getType ());
    assertEquals ("3739db04-7e4e-4c89-b170-c543dd252249", aData.getInstanceIdentifier ());
    assertEquals ("2013-02-19T05:10:10Z", PDTWebDateHelper.getAsStringXSD (aData.getCreationDateAndTime ()));
    assertTrue (aData.hasBusinessMessage ());
    assertEquals ("Invoice", aData.getBusinessMessage ().getLocalName ());

    TestHelper.testToStringImplementation (aData);
  }

  @Test
  public void testReadGoodResource () throws HREDeliverySBDHDataReadException
  {
    final HREDeliverySBDHDataReader aReader = new HREDeliverySBDHDataReader (PeppolIdentifierFactory.INSTANCE);
    for (final ClassPathResource aRes : HREDeliverySBDHTestFiles.getAllGoodCases ())
    {
      LOGGER.info ("Good (Res): " + aRes.getPath ());
      assertTrue (aRes.getPath (), aRes.exists ());
      final HREDeliverySBDHData aData = aReader.extractData (aRes);
      assertNotNull (aData);
      assertTrue (aData.areAllFieldsSet ());
    }
  }

  @Test
  public void testReadGoodInputStream () throws HREDeliverySBDHDataReadException
  {
    final HREDeliverySBDHDataReader aReader = new HREDeliverySBDHDataReader (PeppolIdentifierFactory.INSTANCE);
    for (final ClassPathResource aRes : HREDeliverySBDHTestFiles.getAllGoodCases ())
    {
      LOGGER.info ("Good (IS): " + aRes.getPath ());
      assertTrue (aRes.getPath (), aRes.exists ());
      final HREDeliverySBDHData aData = aReader.extractData (aRes.getInputStream ());
      assertNotNull (aData);
      assertTrue (aData.areAllFieldsSet ());
    }
  }

  @Test
  public void testReadGoodNode () throws HREDeliverySBDHDataReadException
  {
    final HREDeliverySBDHDataReader aReader = new HREDeliverySBDHDataReader (PeppolIdentifierFactory.INSTANCE);
    for (final ClassPathResource aRes : HREDeliverySBDHTestFiles.getAllGoodCases ())
    {
      LOGGER.info ("Good (Node): " + aRes.getPath ());
      assertTrue (aRes.getPath (), aRes.exists ());
      final HREDeliverySBDHData aData = aReader.extractData (DOMReader.readXMLDOM (aRes));
      assertNotNull (aData);
      assertTrue (aData.areAllFieldsSet ());
    }
  }

  @Test
  public void testReadBadResource ()
  {
    final HREDeliverySBDHDataReader aReader = new HREDeliverySBDHDataReader (PeppolIdentifierFactory.INSTANCE);
    for (final Map.Entry <String, EHREDeliverySBDHDataError> aEntry : BAD_CASES.entrySet ())
    {
      final IReadableResource aRes = new ClassPathResource ("external/sbdh/bad/" + aEntry.getKey ());
      LOGGER.info ("Bad (Res): " + aRes.getPath ());
      assertTrue (aRes.getPath (), aRes.exists ());
      try
      {
        aReader.extractData (aRes);
        fail ();
      }
      catch (final HREDeliverySBDHDataReadException ex)
      {
        // check for expected error code
        assertEquals (aRes.getPath (), aEntry.getValue (), ex.getErrorCode ());
        LOGGER.info (ex.toString ());
      }
    }
  }

  @Test
  public void testReadBadInputStream ()
  {
    final HREDeliverySBDHDataReader aReader = new HREDeliverySBDHDataReader (PeppolIdentifierFactory.INSTANCE);
    for (final Map.Entry <String, EHREDeliverySBDHDataError> aEntry : BAD_CASES.entrySet ())
    {
      final IReadableResource aRes = new ClassPathResource ("external/sbdh/bad/" + aEntry.getKey ());
      LOGGER.info ("Reading bad (IS): " + aRes.getPath ());
      assertTrue (aRes.getPath (), aRes.exists ());
      try
      {
        aReader.extractData (aRes.getInputStream ());
        fail ();
      }
      catch (final HREDeliverySBDHDataReadException ex)
      {
        // check for expected error code
        assertEquals (aRes.getPath (), aEntry.getValue (), ex.getErrorCode ());
        LOGGER.info (ex.toString ());
      }
    }
  }

  @Test
  public void testReadBadNode ()
  {
    final HREDeliverySBDHDataReader aReader = new HREDeliverySBDHDataReader (PeppolIdentifierFactory.INSTANCE);
    final DOMReaderSettings aSettings = new DOMReaderSettings ();
    aSettings.exceptionCallbacks ().removeAll ();
    for (final Map.Entry <String, EHREDeliverySBDHDataError> aEntry : BAD_CASES.entrySet ())
    {
      final IReadableResource aRes = new ClassPathResource ("external/sbdh/bad/" + aEntry.getKey ());
      LOGGER.info ("Reading bad (Node): " + aRes.getPath ());
      assertTrue (aRes.getPath (), aRes.exists ());

      final Document aDoc = DOMReader.readXMLDOM (aRes, aSettings);
      if (aDoc == null)
      {
        // May only occur if an "invalid-sbd-xml" error is expected
        assertEquals (aRes.getPath (), EHREDeliverySBDHDataError.INVALID_SBD_XML, aEntry.getValue ());
      }
      else
        try
        {
          aReader.extractData (aDoc);
          fail ();
        }
        catch (final HREDeliverySBDHDataReadException ex)
        {
          // check for expected error code
          assertEquals (aRes.getPath (), aEntry.getValue (), ex.getErrorCode ());
          LOGGER.info (ex.toString ());
        }
    }
  }

  @Test
  public void testReadGoodAsBad1 ()
  {
    // Always fails
    final HREDeliverySBDHDataReader aReader = new HREDeliverySBDHDataReader (PeppolIdentifierFactory.INSTANCE)
    {
      @Override
      protected boolean isValidBusinessMessage (@NonNull final Element aBusinessMessage)
      {
        return "OrderXYZ".equals (aBusinessMessage.getLocalName ());
      }
    };
    for (final ClassPathResource aRes : HREDeliverySBDHTestFiles.getAllGoodCases ())
    {
      assertTrue (aRes.getPath (), aRes.exists ());
      try
      {
        aReader.extractData (DOMReader.readXMLDOM (aRes));
        fail ();
      }
      catch (final HREDeliverySBDHDataReadException ex)
      {
        // check for expected error code
        LOGGER.info (ex.toString ());
      }
    }
  }

  @Test
  public void testReadGoodAsBad2 ()
  {
    // Always fails
    final HREDeliverySBDHDataReader aReader = new HREDeliverySBDHDataReader (PeppolIdentifierFactory.INSTANCE)
    {
      @Override
      protected boolean isValidCreationDateTime (@NonNull final XMLOffsetDateTime aCreationDateTime)
      {
        // Should fail
        return aCreationDateTime.isAfter (PDTFactory.getCurrentXMLOffsetDateTime ());
      }
    };
    for (final ClassPathResource aRes : HREDeliverySBDHTestFiles.getAllGoodCases ())
    {
      assertTrue (aRes.getPath (), aRes.exists ());
      try
      {
        aReader.extractData (DOMReader.readXMLDOM (aRes));
        fail ();
      }
      catch (final HREDeliverySBDHDataReadException ex)
      {
        // check for expected error code
        LOGGER.info (ex.toString ());
      }
    }
  }

  @Test
  @Ignore
  public void testReadSpontanuousXML () throws HREDeliverySBDHDataReadException
  {
    // TODO fill in XML here
    final String s = "";

    final Document doc = DOMReader.readXMLDOM (s);
    assertNotNull (doc);

    final HREDeliverySBDHDataReader aReader = new HREDeliverySBDHDataReader (PeppolIdentifierFactory.INSTANCE);
    final HREDeliverySBDHData aData = aReader.extractData (doc);
    assertNotNull (aData);
    assertTrue (aData.areAllFieldsSet ());
  }
}
