/**
 * Copyright (C) 2015-2021 Philip Helger
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
package com.helger.smpclient.peppol;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMValidateContext;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.helger.commons.io.resource.ClassPathResource;
import com.helger.commons.io.stream.StreamHelper;
import com.helger.jaxb.validation.LoggingValidationEventHandler;
import com.helger.smpclient.config.SMPClientConfiguration;
import com.helger.smpclient.peppol.marshal.SMPMarshallerSignedServiceMetadataType;
import com.helger.smpclient.security.TrustStoreBasedX509KeySelector;
import com.helger.xml.serialize.read.DOMReader;
import com.helger.xsds.peppol.smp1.SignedServiceMetadataType;

/**
 * Test class for class {@link SignedServiceMetadataType}.
 *
 * @author Philip Helger
 */
public final class SignedServiceMetadataTypeFuncTest
{
  private static final Logger LOGGER = LoggerFactory.getLogger (SignedServiceMetadataTypeFuncTest.class);

  @Test
  @Ignore ("Certificate expired 2021-03-01")
  public void testReadInvalid () throws Exception
  {
    final SMPMarshallerSignedServiceMetadataType aMarshaller = new SMPMarshallerSignedServiceMetadataType (true);
    aMarshaller.setValidationEventHandlerFactory (x -> new LoggingValidationEventHandler ());

    final byte [] aBytes = StreamHelper.getAllBytes (new ClassPathResource ("smp/signed-service-metadata1.xml"));
    assertNotNull (aBytes);

    final SignedServiceMetadataType aSSM = aMarshaller.read (aBytes);
    assertNotNull (aSSM);

    final Document aDocument = DOMReader.readXMLDOM (aBytes);
    assertNotNull (aDocument);

    // Find Signature element.
    final NodeList aNodeList = aDocument.getElementsByTagNameNS (XMLSignature.XMLNS, "Signature");
    assertNotNull (aNodeList);
    assertTrue (aNodeList.getLength () > 0);

    final TrustStoreBasedX509KeySelector aKeySelector = new TrustStoreBasedX509KeySelector (SMPClientConfiguration.loadTrustStore ());

    // Create a DOMValidateContext and specify a KeySelector
    // and document context.
    // TODO OASIS BDXR SMP v2 can have more than one signature
    final DOMValidateContext aValidateContext = new DOMValidateContext (aKeySelector, aNodeList.item (0));
    final XMLSignatureFactory aSignatureFactory = XMLSignatureFactory.getInstance ("DOM");

    // Unmarshal the XMLSignature.
    final XMLSignature aSignature = aSignatureFactory.unmarshalXMLSignature (aValidateContext);
    assertNotNull (aSignature);

    // Validate the XMLSignature.
    final boolean bCoreValid = aSignature.validate (aValidateContext);
    assertFalse (bCoreValid);

    final boolean bSignatureValueValid = aSignature.getSignatureValue ().validate (aValidateContext);
    if (LOGGER.isInfoEnabled ())
      LOGGER.info ("  Signature value valid: " + bSignatureValueValid);
    if (!bSignatureValueValid)
    {
      // Check the validation status of each Reference.
      int nIndex = 0;
      final Iterator <?> i = aSignature.getSignedInfo ().getReferences ().iterator ();
      while (i.hasNext ())
      {
        final boolean bRefValid = ((Reference) i.next ()).validate (aValidateContext);
        if (LOGGER.isInfoEnabled ())
          LOGGER.info ("  Reference[" + nIndex + "] validity status: " + (bRefValid ? "valid" : "NOT valid!"));
        ++nIndex;
      }
    }
  }

  @Test
  @Ignore ("Certificate expired 2021-03-01")
  public void testReadValid () throws Exception
  {
    final SMPMarshallerSignedServiceMetadataType aMarshaller = new SMPMarshallerSignedServiceMetadataType (true);
    aMarshaller.setValidationEventHandlerFactory (x -> new LoggingValidationEventHandler ());

    final byte [] aBytes = StreamHelper.getAllBytes (new ClassPathResource ("smp/signed-service-metadata2.xml"));
    assertNotNull (aBytes);

    final SignedServiceMetadataType aSSM = aMarshaller.read (aBytes);
    assertNotNull (aSSM);

    final Document aDocument = DOMReader.readXMLDOM (aBytes);
    assertNotNull (aDocument);

    // Find Signature element.
    final NodeList aNodeList = aDocument.getElementsByTagNameNS (XMLSignature.XMLNS, "Signature");
    assertNotNull (aNodeList);
    assertTrue (aNodeList.getLength () > 0);

    final TrustStoreBasedX509KeySelector aKeySelector = new TrustStoreBasedX509KeySelector (SMPClientConfiguration.loadTrustStore ());

    // Create a DOMValidateContext and specify a KeySelector
    // and document context.
    // TODO OASIS BDXR SMP v2 can have more than one signature
    final DOMValidateContext aValidateContext = new DOMValidateContext (aKeySelector, aNodeList.item (0));
    final XMLSignatureFactory aSignatureFactory = XMLSignatureFactory.getInstance ("DOM");

    // Unmarshal the XMLSignature.
    final XMLSignature aSignature = aSignatureFactory.unmarshalXMLSignature (aValidateContext);
    assertNotNull (aSignature);

    // Validate the XMLSignature.
    final boolean bCoreValid = aSignature.validate (aValidateContext);
    assertTrue (bCoreValid);
  }

  @Test
  @Ignore ("Certificate expired on 2020-08-05")
  public void testReadC14NInclusive () throws Exception
  {
    final SMPMarshallerSignedServiceMetadataType aMarshaller = new SMPMarshallerSignedServiceMetadataType (true);
    aMarshaller.setValidationEventHandlerFactory (x -> new LoggingValidationEventHandler ());

    final byte [] aBytes = StreamHelper.getAllBytes (new ClassPathResource ("smp/signed-service-metadata3-c14n-inclusive.xml"));
    assertNotNull (aBytes);

    final SignedServiceMetadataType aSSM = aMarshaller.read (aBytes);
    assertNotNull (aSSM);

    final Document aDocument = DOMReader.readXMLDOM (aBytes);
    assertNotNull (aDocument);

    // Find Signature element.
    final NodeList aNodeList = aDocument.getElementsByTagNameNS (XMLSignature.XMLNS, "Signature");
    assertNotNull (aNodeList);
    assertTrue (aNodeList.getLength () > 0);

    final TrustStoreBasedX509KeySelector aKeySelector = new TrustStoreBasedX509KeySelector (SMPClientConfiguration.loadTrustStore ());

    // Create a DOMValidateContext and specify a KeySelector
    // and document context.
    // TODO OASIS BDXR SMP v2 can have more than one signature
    final DOMValidateContext aValidateContext = new DOMValidateContext (aKeySelector, aNodeList.item (0));
    final XMLSignatureFactory aSignatureFactory = XMLSignatureFactory.getInstance ("DOM");

    // Unmarshal the XMLSignature.
    final XMLSignature aSignature = aSignatureFactory.unmarshalXMLSignature (aValidateContext);
    assertNotNull (aSignature);

    // Validate the XMLSignature.
    final boolean bCoreValid = aSignature.validate (aValidateContext);
    assertTrue (bCoreValid);
  }
}
