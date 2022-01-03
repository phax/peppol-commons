/*
 * Copyright (C) 2015-2022 Philip Helger
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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.Month;

import org.junit.Test;
import org.w3c.dom.Document;

import com.helger.commons.datetime.PDTFactory;
import com.helger.commons.io.resource.ClassPathResource;
import com.helger.commons.io.stream.StreamHelper;
import com.helger.commons.state.ESuccess;
import com.helger.jaxb.validation.LoggingValidationEventHandler;
import com.helger.smpclient.config.SMPClientConfiguration;
import com.helger.smpclient.httpclient.SMPHttpResponseHandlerSigned;
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
  @Test
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

    final TrustStoreBasedX509KeySelector aKeySelector = new TrustStoreBasedX509KeySelector (SMPClientConfiguration.loadTrustStore ());

    // Certificate expired 2021-03-01
    aKeySelector.setValidationDateTime (PDTFactory.createLocalDateTime (2021, Month.JANUARY, 1));

    final ESuccess eSuccess = SMPHttpResponseHandlerSigned.checkSignature (aDocument, aKeySelector);
    assertTrue (eSuccess.isFailure ());
  }

  @Test
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

    final TrustStoreBasedX509KeySelector aKeySelector = new TrustStoreBasedX509KeySelector (SMPClientConfiguration.loadTrustStore ());

    // Certificate expired 2021-03-01
    aKeySelector.setValidationDateTime (PDTFactory.createLocalDateTime (2021, Month.JANUARY, 1));

    final ESuccess eSuccess = SMPHttpResponseHandlerSigned.checkSignature (aDocument, aKeySelector);
    assertTrue (eSuccess.isSuccess ());
  }

  @Test
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

    final TrustStoreBasedX509KeySelector aKeySelector = new TrustStoreBasedX509KeySelector (SMPClientConfiguration.loadTrustStore ());

    // Certificate expired 2020-08-05
    aKeySelector.setValidationDateTime (PDTFactory.createLocalDateTime (2020, Month.AUGUST, 1));

    final ESuccess eSuccess = SMPHttpResponseHandlerSigned.checkSignature (aDocument, aKeySelector);
    assertTrue (eSuccess.isSuccess ());
  }
}
