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
package com.helger.peppol.sbdh.read;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;

import org.joda.time.LocalDateTime;
import org.junit.Test;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.helger.commons.callback.exception.DoNothingExceptionCallback;
import com.helger.commons.io.resource.ClassPathResource;
import com.helger.commons.io.resource.IReadableResource;
import com.helger.commons.mock.CommonsTestHelper;
import com.helger.commons.xml.serialize.read.DOMReader;
import com.helger.commons.xml.serialize.read.DOMReaderSettings;
import com.helger.datetime.PDTFactory;
import com.helger.peppol.identifier.CIdentifier;
import com.helger.peppol.sbdh.DocumentData;

/**
 * Test class for class {@link DocumentDataReader}.
 *
 * @author Philip Helger
 */
public final class DocumentDataReaderTest
{
  // good.xml must be the first file!
  private static final String [] GOOD_CASES = new String [] { "good.xml",
                                                              "good-additional-scopes.xml",
                                                              "good-type-version-20.xml" };
  private static final Map <String, EDocumentDataReadError> BAD_CASES = new HashMap <String, EDocumentDataReadError> ();

  static
  {
    BAD_CASES.put ("bad-no-xml.txt", EDocumentDataReadError.INVALID_SBD_XML);
    BAD_CASES.put ("bad-no-sbdh.xml", EDocumentDataReadError.MISSING_SBDH);
    BAD_CASES.put ("bad-invalid-header-version.xml", EDocumentDataReadError.INVALID_HEADER_VERSION);
    BAD_CASES.put ("bad-too-few-senders.xml", EDocumentDataReadError.INVALID_SBD_XML);
    BAD_CASES.put ("bad-too-many-senders.xml", EDocumentDataReadError.INVALID_SENDER_COUNT);
    BAD_CASES.put ("bad-invalid-sender-authority.xml", EDocumentDataReadError.INVALID_SENDER_AUTHORITY);
    BAD_CASES.put ("bad-invalid-sender-value.xml", EDocumentDataReadError.INVALID_SENDER_VALUE);
    BAD_CASES.put ("bad-too-few-receivers.xml", EDocumentDataReadError.INVALID_SBD_XML);
    BAD_CASES.put ("bad-too-many-receivers.xml", EDocumentDataReadError.INVALID_RECEIVER_COUNT);
    BAD_CASES.put ("bad-invalid-receiver-authority.xml", EDocumentDataReadError.INVALID_RECEIVER_AUTHORITY);
    BAD_CASES.put ("bad-invalid-receiver-value.xml", EDocumentDataReadError.INVALID_RECEIVER_VALUE);
    BAD_CASES.put ("bad-no-business-scope.xml", EDocumentDataReadError.BUSINESS_SCOPE_MISSING);
    BAD_CASES.put ("bad-too-few-scopes.xml", EDocumentDataReadError.INVALID_SCOPE_COUNT);
    BAD_CASES.put ("bad-invalid-document-type-identifier.xml", EDocumentDataReadError.INVALID_DOCUMENT_TYPE_IDENTIFIER);
    BAD_CASES.put ("bad-invalid-process-identifier.xml", EDocumentDataReadError.INVALID_PROCESS_IDENTIFIER);
    BAD_CASES.put ("bad-no-document-type-identifier.xml", EDocumentDataReadError.MISSING_DOCUMENT_TYPE_IDENTIFIER);
    BAD_CASES.put ("bad-no-process-identifier.xml", EDocumentDataReadError.MISSING_PROCESS_IDENTIFIER);
    BAD_CASES.put ("bad-no-business-message.xml", EDocumentDataReadError.INVALID_SBD_XML);
    BAD_CASES.put ("bad-invalid-business-message.xml", EDocumentDataReadError.INVALID_SBD_XML);
    BAD_CASES.put ("bad-invalid-standard.xml", EDocumentDataReadError.INVALID_STANDARD);
    BAD_CASES.put ("bad-invalid-type-version.xml", EDocumentDataReadError.INVALID_TYPE_VERSION);
    BAD_CASES.put ("bad-invalid-type.xml", EDocumentDataReadError.INVALID_TYPE);
    BAD_CASES.put ("bad-invalid-instance-identifier.xml", EDocumentDataReadError.INVALID_INSTANCE_IDENTIFIER);
    BAD_CASES.put ("bad-invalid-creation-date-and-time.xml", EDocumentDataReadError.INVALID_SBD_XML);
  }

  @Test
  public void testReadGoodAndCheckResults () throws DocumentDataReadException
  {
    // Read good.xml
    final IReadableResource aRes = new ClassPathResource ("sbdh/good/" + GOOD_CASES[0]);
    assertTrue (aRes.getPath (), aRes.exists ());
    final DocumentDataReader aReader = new DocumentDataReader ();
    final DocumentData aData = aReader.extractData (aRes);
    assertNotNull (aData);
    assertTrue (aData.areAllFieldsSet ());
    assertEquals (CIdentifier.DEFAULT_PARTICIPANT_IDENTIFIER_SCHEME, aData.getSenderScheme ());
    assertEquals ("0088:7315458756324", aData.getSenderValue ());
    assertEquals (CIdentifier.DEFAULT_PARTICIPANT_IDENTIFIER_SCHEME, aData.getReceiverScheme ());
    assertEquals ("0088:4562458856624", aData.getReceiverValue ());
    assertEquals ("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", aData.getStandard ());
    assertEquals ("2.1", aData.getTypeVersion ());
    assertEquals ("Invoice", aData.getType ());
    assertEquals ("123123", aData.getInstanceIdentifier ());
    assertEquals ("2013-02-19T05:10:10.000", aData.getCreationDateAndTime ().toString ());
    assertEquals (CIdentifier.DEFAULT_DOCUMENT_TYPE_IDENTIFIER_SCHEME, aData.getDocumentTypeScheme ());
    assertEquals ("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:www.cenbii.eu:transaction:biitrns010:ver2.0:extended:urn:www.peppol.eu:bis:peppol4a:ver2.0::2.1",
                  aData.getDocumentTypeValue ());
    assertEquals (CIdentifier.DEFAULT_PROCESS_IDENTIFIER_SCHEME, aData.getProcessScheme ());
    assertEquals ("urn:www.cenbii.eu:profile:bii04:ver1.0", aData.getProcessValue ());
    assertTrue (aData.hasBusinessMessage ());
    assertEquals ("Invoice", aData.getBusinessMessage ().getLocalName ());

    CommonsTestHelper.testToStringImplementation (aData);
  }

  @Test
  public void testReadGoodResource () throws DocumentDataReadException
  {
    final DocumentDataReader aReader = new DocumentDataReader ();
    for (final String sFilename : GOOD_CASES)
    {
      final IReadableResource aRes = new ClassPathResource ("sbdh/good/" + sFilename);
      assertTrue (aRes.getPath (), aRes.exists ());
      final DocumentData aData = aReader.extractData (aRes);
      assertNotNull (aData);
      assertTrue (aData.areAllFieldsSet ());
    }
  }

  @Test
  public void testReadGoodInputStream () throws DocumentDataReadException
  {
    final DocumentDataReader aReader = new DocumentDataReader ();
    for (final String sFilename : GOOD_CASES)
    {
      final IReadableResource aRes = new ClassPathResource ("sbdh/good/" + sFilename);
      assertTrue (aRes.getPath (), aRes.exists ());
      final DocumentData aData = aReader.extractData (aRes.getInputStream ());
      assertNotNull (aData);
      assertTrue (aData.areAllFieldsSet ());
    }
  }

  @Test
  public void testReadGoodNode () throws DocumentDataReadException, SAXException
  {
    final DocumentDataReader aReader = new DocumentDataReader ();
    for (final String sFilename : GOOD_CASES)
    {
      final IReadableResource aRes = new ClassPathResource ("sbdh/good/" + sFilename);
      assertTrue (aRes.getPath (), aRes.exists ());
      final DocumentData aData = aReader.extractData (DOMReader.readXMLDOM (aRes));
      assertNotNull (aData);
      assertTrue (aData.areAllFieldsSet ());
    }
  }

  @Test
  public void testReadBadResource ()
  {
    final DocumentDataReader aReader = new DocumentDataReader ();
    for (final Map.Entry <String, EDocumentDataReadError> aEntry : BAD_CASES.entrySet ())
    {
      final IReadableResource aRes = new ClassPathResource ("sbdh/bad/" + aEntry.getKey ());
      assertTrue (aRes.getPath (), aRes.exists ());
      try
      {
        aReader.extractData (aRes);
        fail ();
      }
      catch (final DocumentDataReadException ex)
      {
        // check for expected error code
        assertEquals (aRes.getPath (), aEntry.getValue (), ex.getErrorCode ());
      }
    }
  }

  @Test
  public void testReadBadInputStream ()
  {
    final DocumentDataReader aReader = new DocumentDataReader ();
    for (final Map.Entry <String, EDocumentDataReadError> aEntry : BAD_CASES.entrySet ())
    {
      final IReadableResource aRes = new ClassPathResource ("sbdh/bad/" + aEntry.getKey ());
      assertTrue (aRes.getPath (), aRes.exists ());
      try
      {
        aReader.extractData (aRes.getInputStream ());
        fail ();
      }
      catch (final DocumentDataReadException ex)
      {
        // check for expected error code
        assertEquals (aRes.getPath (), aEntry.getValue (), ex.getErrorCode ());
      }
    }
  }

  @Test
  public void testReadBadNode ()
  {
    final DocumentDataReader aReader = new DocumentDataReader ();
    final DOMReaderSettings aSettings = new DOMReaderSettings ().setExceptionHandler (new DoNothingExceptionCallback ());
    for (final Map.Entry <String, EDocumentDataReadError> aEntry : BAD_CASES.entrySet ())
    {
      final IReadableResource aRes = new ClassPathResource ("sbdh/bad/" + aEntry.getKey ());
      assertTrue (aRes.getPath (), aRes.exists ());
      try
      {
        aReader.extractData (DOMReader.readXMLDOM (aRes, aSettings));
        fail ();
      }
      catch (final SAXException ex)
      {
        // May only occur if an "invalid-sbd-xml" error is expected
        assertEquals (aRes.getPath (), EDocumentDataReadError.INVALID_SBD_XML, aEntry.getValue ());
      }
      catch (final DocumentDataReadException ex)
      {
        // check for expected error code
        assertEquals (aRes.getPath (), aEntry.getValue (), ex.getErrorCode ());
      }
    }
  }

  @Test
  public void testReadGoodAsBad1 () throws SAXException
  {
    // Always fails
    final DocumentDataReader aReader = new DocumentDataReader ()
    {
      @Override
      protected boolean isValidBusinessMessage (@Nonnull final Element aBusinessMessage)
      {
        return "Order".equals (aBusinessMessage.getLocalName ());
      }
    };
    for (final String sFilename : GOOD_CASES)
    {
      final IReadableResource aRes = new ClassPathResource ("sbdh/good/" + sFilename);
      assertTrue (aRes.getPath (), aRes.exists ());
      try
      {
        aReader.extractData (DOMReader.readXMLDOM (aRes));
        fail ();
      }
      catch (final DocumentDataReadException ex)
      {
        // check for expected error code
      }
    }
  }

  @Test
  public void testReadGoodAsBad2 () throws SAXException
  {
    // Always fails
    final DocumentDataReader aReader = new DocumentDataReader ()
    {
      @Override
      protected boolean isValidCreationDateTime (@Nonnull final LocalDateTime aCreationDateTime)
      {
        return aCreationDateTime.isAfter (PDTFactory.getCurrentLocalDateTime ());
      }
    };
    for (final String sFilename : GOOD_CASES)
    {
      final IReadableResource aRes = new ClassPathResource ("sbdh/good/" + sFilename);
      assertTrue (aRes.getPath (), aRes.exists ());
      try
      {
        aReader.extractData (DOMReader.readXMLDOM (aRes));
        fail ();
      }
      catch (final DocumentDataReadException ex)
      {
        // check for expected error code
      }
    }
  }
}
