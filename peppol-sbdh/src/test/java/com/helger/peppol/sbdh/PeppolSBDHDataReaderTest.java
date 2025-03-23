/*
 * Copyright (C) 2014-2025 Philip Helger
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
package com.helger.peppol.sbdh;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Map;

import javax.annotation.Nonnull;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.helger.commons.collection.impl.CommonsHashMap;
import com.helger.commons.collection.impl.ICommonsMap;
import com.helger.commons.datetime.PDTFactory;
import com.helger.commons.datetime.PDTWebDateHelper;
import com.helger.commons.datetime.XMLOffsetDateTime;
import com.helger.commons.io.resource.ClassPathResource;
import com.helger.commons.io.resource.IReadableResource;
import com.helger.commons.mock.CommonsTestHelper;
import com.helger.peppol.testfiles.sbdh.PeppolSBDHTestFiles;
import com.helger.peppolid.factory.SimpleIdentifierFactory;
import com.helger.peppolid.peppol.PeppolIdentifierHelper;
import com.helger.xml.serialize.read.DOMReader;
import com.helger.xml.serialize.read.DOMReaderSettings;

/**
 * Test class for class {@link PeppolSBDHDataReader}.
 *
 * @author Philip Helger
 */
public final class PeppolSBDHDataReaderTest
{
  private static final ICommonsMap <String, EPeppolSBDHDataError> BAD_CASES = new CommonsHashMap <> ();

  static
  {
    BAD_CASES.put ("bad-no-xml.txt", EPeppolSBDHDataError.INVALID_SBD_XML);
    BAD_CASES.put ("bad-no-sbdh.xml", EPeppolSBDHDataError.MISSING_SBDH);
    BAD_CASES.put ("bad-invalid-header-version.xml", EPeppolSBDHDataError.INVALID_HEADER_VERSION);
    BAD_CASES.put ("bad-too-few-senders.xml", EPeppolSBDHDataError.INVALID_SBD_XML);
    BAD_CASES.put ("bad-too-many-senders.xml", EPeppolSBDHDataError.INVALID_SENDER_COUNT);
    BAD_CASES.put ("bad-invalid-sender-authority.xml", EPeppolSBDHDataError.INVALID_SENDER_AUTHORITY);
    BAD_CASES.put ("bad-invalid-sender-value.xml", EPeppolSBDHDataError.INVALID_SENDER_VALUE);
    BAD_CASES.put ("bad-too-few-receivers.xml", EPeppolSBDHDataError.INVALID_SBD_XML);
    BAD_CASES.put ("bad-too-many-receivers.xml", EPeppolSBDHDataError.INVALID_RECEIVER_COUNT);
    BAD_CASES.put ("bad-invalid-receiver-authority.xml", EPeppolSBDHDataError.INVALID_RECEIVER_AUTHORITY);
    BAD_CASES.put ("bad-invalid-receiver-value.xml", EPeppolSBDHDataError.INVALID_RECEIVER_VALUE);
    BAD_CASES.put ("bad-no-business-scope.xml", EPeppolSBDHDataError.BUSINESS_SCOPE_MISSING);
    BAD_CASES.put ("bad-too-few-scopes.xml", EPeppolSBDHDataError.INVALID_SCOPE_COUNT);
    BAD_CASES.put ("bad-invalid-document-type-identifier.xml", EPeppolSBDHDataError.INVALID_DOCUMENT_TYPE_IDENTIFIER);
    BAD_CASES.put ("bad-invalid-process-identifier.xml", EPeppolSBDHDataError.INVALID_PROCESS_IDENTIFIER);
    BAD_CASES.put ("bad-no-country-c1.xml", EPeppolSBDHDataError.MISSING_COUNTRY_C1);
    BAD_CASES.put ("bad-no-document-type-identifier.xml", EPeppolSBDHDataError.MISSING_DOCUMENT_TYPE_IDENTIFIER);
    BAD_CASES.put ("bad-no-process-identifier.xml", EPeppolSBDHDataError.MISSING_PROCESS_IDENTIFIER);
    BAD_CASES.put ("bad-no-business-message.xml", EPeppolSBDHDataError.INVALID_SBD_XML);
    BAD_CASES.put ("bad-invalid-business-message.xml", EPeppolSBDHDataError.INVALID_SBD_XML);
    BAD_CASES.put ("bad-invalid-standard.xml", EPeppolSBDHDataError.INVALID_STANDARD);
    BAD_CASES.put ("bad-invalid-type-version.xml", EPeppolSBDHDataError.INVALID_TYPE_VERSION);
    BAD_CASES.put ("bad-invalid-type.xml", EPeppolSBDHDataError.INVALID_TYPE);
    BAD_CASES.put ("bad-invalid-instance-identifier.xml", EPeppolSBDHDataError.INVALID_INSTANCE_IDENTIFIER);
    BAD_CASES.put ("bad-invalid-creation-date-and-time.xml", EPeppolSBDHDataError.INVALID_SBD_XML);
  }

  private static final Logger LOGGER = LoggerFactory.getLogger (PeppolSBDHDataReaderTest.class);

  @Test
  public void testReadGoodV10AndCheckResults () throws PeppolSBDHDataReadException
  {
    // Read good.xml
    final IReadableResource aRes = PeppolSBDHTestFiles.getFirstGoodCase ();
    assertTrue (aRes.getPath (), aRes.exists ());

    final PeppolSBDHDataReader aReader = new PeppolSBDHDataReader (SimpleIdentifierFactory.INSTANCE);
    final PeppolSBDHData aData = aReader.extractData (aRes);
    assertNotNull (aData);

    assertTrue (aData.areAllFieldsSet ());
    assertEquals (PeppolIdentifierHelper.DEFAULT_PARTICIPANT_SCHEME, aData.getSenderScheme ());
    assertEquals ("0088:7315458756324", aData.getSenderValue ());
    assertEquals (PeppolIdentifierHelper.DEFAULT_PARTICIPANT_SCHEME, aData.getReceiverScheme ());
    assertEquals ("0088:4562458856624", aData.getReceiverValue ());
    assertEquals ("AT", aData.getCountryC1 ());
    assertEquals ("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", aData.getStandard ());
    assertEquals ("2.1", aData.getTypeVersion ());
    assertEquals ("Invoice", aData.getType ());
    assertEquals ("3739db04-7e4e-4c89-b170-c543dd252249", aData.getInstanceIdentifier ());
    assertEquals ("2013-02-19T05:10:10Z", PDTWebDateHelper.getAsStringXSD (aData.getCreationDateAndTime ()));
    assertEquals (PeppolIdentifierHelper.DOCUMENT_TYPE_SCHEME_BUSDOX_DOCID_QNS, aData.getDocumentTypeScheme ());
    assertEquals ("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:www.cenbii.eu:transaction:biitrns010:ver2.0:extended:urn:www.peppol.eu:bis:peppol4a:ver2.0::2.1",
                  aData.getDocumentTypeValue ());
    assertEquals (PeppolIdentifierHelper.DEFAULT_PROCESS_SCHEME, aData.getProcessScheme ());
    assertEquals ("urn:www.cenbii.eu:profile:bii04:ver1.0", aData.getProcessValue ());
    assertNotNull (aData.additionalAttributes ());
    assertEquals (0, aData.additionalAttributes ().size ());
    assertTrue (aData.hasBusinessMessage ());
    assertEquals ("Invoice", aData.getBusinessMessage ().getLocalName ());

    CommonsTestHelper.testToStringImplementation (aData);
  }

  @Test
  public void testReadGoodV11AndCheckResults () throws PeppolSBDHDataReadException
  {
    // Read good.xml
    final IReadableResource aRes = PeppolSBDHTestFiles.getFirstGoodCaseV11 ();
    assertTrue (aRes.getPath (), aRes.exists ());

    final PeppolSBDHDataReader aReader = new PeppolSBDHDataReader (SimpleIdentifierFactory.INSTANCE);
    final PeppolSBDHData aData = aReader.extractData (aRes);
    assertNotNull (aData);

    assertTrue (aData.areAllFieldsSet ());
    assertEquals (PeppolIdentifierHelper.DEFAULT_PARTICIPANT_SCHEME, aData.getSenderScheme ());
    assertEquals ("0088:7315458756324", aData.getSenderValue ());
    assertEquals (PeppolIdentifierHelper.DEFAULT_PARTICIPANT_SCHEME, aData.getReceiverScheme ());
    assertEquals ("0088:4562458856624", aData.getReceiverValue ());
    assertEquals ("AT", aData.getCountryC1 ());
    assertEquals ("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", aData.getStandard ());
    assertEquals ("2.1", aData.getTypeVersion ());
    assertEquals ("Invoice", aData.getType ());
    assertEquals ("3739db04-7e4e-4c89-b170-c543dd252249", aData.getInstanceIdentifier ());
    assertEquals ("2013-02-19T05:10:10Z", PDTWebDateHelper.getAsStringXSD (aData.getCreationDateAndTime ()));
    assertEquals ("busdox-docid-qns", aData.getDocumentTypeScheme ());
    assertEquals ("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:cen.eu:en16931:2017#compliant#urn:fdc:peppol.eu:2017:poacc:billing:3.0::2.1",
                  aData.getDocumentTypeValue ());
    assertEquals ("ptype", aData.getProcessScheme ());
    assertEquals ("urn:www.cenbii.eu:profile:bii04:ver1.0", aData.getProcessValue ());
    assertNotNull (aData.additionalAttributes ());
    assertEquals (3, aData.additionalAttributes ().size ());
    assertTrue (aData.additionalAttributes ().containsKey ("AddAttr1"));
    assertEquals ("Value1", aData.additionalAttributes ().get ("AddAttr1"));
    assertTrue (aData.additionalAttributes ().containsKey ("AddAttr2"));
    assertNull (aData.additionalAttributes ().get ("AddAttr2"));
    assertTrue (aData.additionalAttributes ().containsKey ("AddAttr3"));
    assertNull (aData.additionalAttributes ().get ("AddAttr3"));
    assertTrue (aData.hasBusinessMessage ());
    assertEquals ("Invoice", aData.getBusinessMessage ().getLocalName ());

    CommonsTestHelper.testToStringImplementation (aData);
  }

  @Test
  public void testReadGoodV20AndCheckResults () throws PeppolSBDHDataReadException
  {
    // Read good.xml
    final IReadableResource aRes = PeppolSBDHTestFiles.getFirstGoodCaseV20 ();
    assertTrue (aRes.getPath (), aRes.exists ());

    final PeppolSBDHDataReader aReader = new PeppolSBDHDataReader (SimpleIdentifierFactory.INSTANCE);
    final PeppolSBDHData aData = aReader.extractData (aRes);
    assertNotNull (aData);

    assertTrue (aData.areAllFieldsSet ());
    assertEquals (PeppolIdentifierHelper.DEFAULT_PARTICIPANT_SCHEME, aData.getSenderScheme ());
    assertEquals ("0088:7315458756324", aData.getSenderValue ());
    assertEquals (PeppolIdentifierHelper.DEFAULT_PARTICIPANT_SCHEME, aData.getReceiverScheme ());
    assertEquals ("0088:4562458856624", aData.getReceiverValue ());
    assertEquals ("AT", aData.getCountryC1 ());
    assertEquals ("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", aData.getStandard ());
    assertEquals ("2.1", aData.getTypeVersion ());
    assertEquals ("Invoice", aData.getType ());
    assertEquals ("a593a0aa-6ff7-48b0-8906-5534fa5212e0", aData.getInstanceIdentifier ());
    assertEquals ("2019-04-25T15:46:10Z", PDTWebDateHelper.getAsStringXSD (aData.getCreationDateAndTime ()));
    assertEquals ("busdox-docid-qns", aData.getDocumentTypeScheme ());
    assertEquals ("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:cen.eu:en16931:2017#compliant#urn:fdc:peppol.eu:2017:poacc:billing:3.0::2.1",
                  aData.getDocumentTypeValue ());
    assertEquals ("cenbii-procid-ubl", aData.getProcessScheme ());
    assertEquals ("urn:fdc:peppol.eu:2017:poacc:billing:01:1.0", aData.getProcessValue ());
    assertNotNull (aData.additionalAttributes ());
    assertEquals (0, aData.additionalAttributes ().size ());
    assertTrue (aData.hasBusinessMessage ());
    assertEquals ("Invoice", aData.getBusinessMessage ().getLocalName ());

    CommonsTestHelper.testToStringImplementation (aData);
  }

  @Test
  public void testReadGoodResource () throws PeppolSBDHDataReadException
  {
    final PeppolSBDHDataReader aReader = new PeppolSBDHDataReader (SimpleIdentifierFactory.INSTANCE);
    for (final ClassPathResource aRes : PeppolSBDHTestFiles.getAllGoodCases ())
    {
      LOGGER.info ("Good (Res): " + aRes.getPath ());
      assertTrue (aRes.getPath (), aRes.exists ());
      final PeppolSBDHData aData = aReader.extractData (aRes);
      assertNotNull (aData);
      assertTrue (aData.areAllFieldsSet ());
    }
  }

  @Test
  public void testReadGoodInputStream () throws PeppolSBDHDataReadException
  {
    final PeppolSBDHDataReader aReader = new PeppolSBDHDataReader (SimpleIdentifierFactory.INSTANCE);
    for (final ClassPathResource aRes : PeppolSBDHTestFiles.getAllGoodCases ())
    {
      LOGGER.info ("Good (IS): " + aRes.getPath ());
      assertTrue (aRes.getPath (), aRes.exists ());
      final PeppolSBDHData aData = aReader.extractData (aRes.getInputStream ());
      assertNotNull (aData);
      assertTrue (aData.areAllFieldsSet ());
    }
  }

  @Test
  public void testReadGoodNode () throws PeppolSBDHDataReadException
  {
    final PeppolSBDHDataReader aReader = new PeppolSBDHDataReader (SimpleIdentifierFactory.INSTANCE);
    for (final ClassPathResource aRes : PeppolSBDHTestFiles.getAllGoodCases ())
    {
      LOGGER.info ("Good (Node): " + aRes.getPath ());
      assertTrue (aRes.getPath (), aRes.exists ());
      final PeppolSBDHData aData = aReader.extractData (DOMReader.readXMLDOM (aRes));
      assertNotNull (aData);
      assertTrue (aData.areAllFieldsSet ());
    }
  }

  @Test
  public void testReadBadResource ()
  {
    final PeppolSBDHDataReader aReader = new PeppolSBDHDataReader (SimpleIdentifierFactory.INSTANCE);
    for (final Map.Entry <String, EPeppolSBDHDataError> aEntry : BAD_CASES.entrySet ())
    {
      final IReadableResource aRes = new ClassPathResource ("external/sbdh/bad/" + aEntry.getKey ());
      LOGGER.info ("Bad (Res): " + aRes.getPath ());
      assertTrue (aRes.getPath (), aRes.exists ());
      try
      {
        aReader.extractData (aRes);
        fail ();
      }
      catch (final PeppolSBDHDataReadException ex)
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
    final PeppolSBDHDataReader aReader = new PeppolSBDHDataReader (SimpleIdentifierFactory.INSTANCE);
    for (final Map.Entry <String, EPeppolSBDHDataError> aEntry : BAD_CASES.entrySet ())
    {
      final IReadableResource aRes = new ClassPathResource ("external/sbdh/bad/" + aEntry.getKey ());
      LOGGER.info ("Bad (IS): " + aRes.getPath ());
      assertTrue (aRes.getPath (), aRes.exists ());
      try
      {
        aReader.extractData (aRes.getInputStream ());
        fail ();
      }
      catch (final PeppolSBDHDataReadException ex)
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
    final PeppolSBDHDataReader aReader = new PeppolSBDHDataReader (SimpleIdentifierFactory.INSTANCE);
    final DOMReaderSettings aSettings = new DOMReaderSettings ();
    aSettings.exceptionCallbacks ().removeAll ();
    for (final Map.Entry <String, EPeppolSBDHDataError> aEntry : BAD_CASES.entrySet ())
    {
      final IReadableResource aRes = new ClassPathResource ("external/sbdh/bad/" + aEntry.getKey ());
      LOGGER.info ("Bad (Node): " + aRes.getPath ());
      assertTrue (aRes.getPath (), aRes.exists ());

      final Document aDoc = DOMReader.readXMLDOM (aRes, aSettings);
      if (aDoc == null)
      {
        // May only occur if an "invalid-sbd-xml" error is expected
        assertEquals (aRes.getPath (), EPeppolSBDHDataError.INVALID_SBD_XML, aEntry.getValue ());
      }
      else
        try
        {
          aReader.extractData (aDoc);
          fail ();
        }
        catch (final PeppolSBDHDataReadException ex)
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
    final PeppolSBDHDataReader aReader = new PeppolSBDHDataReader (SimpleIdentifierFactory.INSTANCE)
    {
      @Override
      protected boolean isValidBusinessMessage (@Nonnull final Element aBusinessMessage)
      {
        return "OrderXYZ".equals (aBusinessMessage.getLocalName ());
      }
    };
    for (final ClassPathResource aRes : PeppolSBDHTestFiles.getAllGoodCases ())
    {
      assertTrue (aRes.getPath (), aRes.exists ());
      try
      {
        aReader.extractData (DOMReader.readXMLDOM (aRes));
        fail ();
      }
      catch (final PeppolSBDHDataReadException ex)
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
    final PeppolSBDHDataReader aReader = new PeppolSBDHDataReader (SimpleIdentifierFactory.INSTANCE)
    {
      @Override
      protected boolean isValidCreationDateTime (@Nonnull final XMLOffsetDateTime aCreationDateTime)
      {
        // Should fail
        return aCreationDateTime.isAfter (PDTFactory.getCurrentXMLOffsetDateTime ());
      }
    };
    for (final ClassPathResource aRes : PeppolSBDHTestFiles.getAllGoodCases ())
    {
      assertTrue (aRes.getPath (), aRes.exists ());
      try
      {
        aReader.extractData (DOMReader.readXMLDOM (aRes));
        fail ();
      }
      catch (final PeppolSBDHDataReadException ex)
      {
        // check for expected error code
        LOGGER.info (ex.toString ());
      }
    }
  }

  @Test
  @Ignore
  public void testReadSpontanuousXML () throws PeppolSBDHDataReadException
  {
    // TODO fill in XML here
    final String s = "";

    final Document doc = DOMReader.readXMLDOM (s);
    assertNotNull (doc);

    final PeppolSBDHDataReader aReader = new PeppolSBDHDataReader (SimpleIdentifierFactory.INSTANCE);
    final PeppolSBDHData aData = aReader.extractData (doc);
    assertNotNull (aData);
    assertTrue (aData.areAllFieldsSet ());
  }
}
