/**
 * Copyright (C) 2015-2016 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * Version: MPL 1.1/EUPL 1.1
 *
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at:
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * Alternatively, the contents of this file may be used under the
 * terms of the EUPL, Version 1.1 or - as soon they will be approved
 * by the European Commission - subsequent versions of the EUPL
 * (the "Licence"); You may not use this work except in compliance
 * with the Licence.
 * You may obtain a copy of the Licence at:
 * http://joinup.ec.europa.eu/software/page/eupl/licence-eupl
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and
 * limitations under the Licence.
 *
 * If you wish to allow use of your version of this file only
 * under the terms of the EUPL License and not to allow others to use
 * your version of this file under the MPL, indicate your decision by
 * deleting the provisions above and replace them with the notice and
 * other provisions required by the EUPL License. If you do not delete
 * the provisions above, a recipient may use your version of this file
 * under either the MPL or the EUPL License.
 */
package com.helger.peppol.identifier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.helger.peppol.identifier.generic.participant.IParticipantIdentifier;
import com.helger.peppol.identifier.generic.participant.SimpleParticipantIdentifier;
import com.helger.peppol.identifier.peppol.PeppolIdentifierHelper;
import com.helger.peppol.identifier.peppol.doctype.PeppolDocumentTypeIdentifier;
import com.helger.peppol.identifier.peppol.issuingagency.EPredefinedIdentifierIssuingAgency;
import com.helger.peppol.identifier.peppol.participant.PeppolParticipantIdentifier;
import com.helger.peppol.identifier.peppol.process.PeppolProcessIdentifier;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * Test class for class {@link PeppolIdentifierHelper}.
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
public final class IdentifierHelperTest
{

  @Test
  public void testAreIdentifiersEqualPariticpantIdentifier ()
  {
    final PeppolParticipantIdentifier aPI1 = PeppolParticipantIdentifier.createWithDefaultScheme ("0088:123abc");
    final PeppolParticipantIdentifier aPI2 = PeppolParticipantIdentifier.createWithDefaultScheme ("0088:123ABC");
    final PeppolParticipantIdentifier aPI3a = PeppolParticipantIdentifier.createWithDefaultScheme ("0088:123456");
    final PeppolParticipantIdentifier aPI3b = new PeppolParticipantIdentifier ("my-actorid-scheme", "0088:12345");
    assertTrue (IdentifierHelper.areParticipantIdentifiersEqual (aPI1, aPI1));
    assertTrue (IdentifierHelper.areParticipantIdentifiersEqual (aPI1, aPI2));
    assertTrue (IdentifierHelper.areParticipantIdentifiersEqual (aPI2, aPI1));
    assertFalse (IdentifierHelper.areParticipantIdentifiersEqual (aPI1, aPI3a));
    assertFalse (IdentifierHelper.areParticipantIdentifiersEqual (aPI1, aPI3b));
    assertFalse (IdentifierHelper.areParticipantIdentifiersEqual (aPI2, aPI3a));
    assertFalse (IdentifierHelper.areParticipantIdentifiersEqual (aPI2, aPI3b));
    assertFalse (IdentifierHelper.areParticipantIdentifiersEqual (aPI3a, aPI3b));

    try
    {
      IdentifierHelper.areParticipantIdentifiersEqual (aPI1, null);
      fail ("null parameter not allowed");
    }
    catch (final NullPointerException ex)
    {
      // expected
    }

    try
    {
      IdentifierHelper.areParticipantIdentifiersEqual (null, aPI1);
      fail ("null parameter not allowed");
    }
    catch (final NullPointerException ex)
    {
      // expected
    }
  }

  @Test
  public void testAreIdentifiersEqualDocumentIdentifier ()
  {
    final DocumentIdentifierType aDI1 = PeppolDocumentTypeIdentifier.createWithDefaultScheme ("urn:doc:anydoc");
    final DocumentIdentifierType aDI2 = PeppolDocumentTypeIdentifier.createWithDefaultScheme ("urn:doc:anydoc");
    final DocumentIdentifierType aDI3a = PeppolDocumentTypeIdentifier.createWithDefaultScheme ("urn:doc:anyotherdoc");
    final DocumentIdentifierType aDI3b = new PeppolDocumentTypeIdentifier ("my-docid-test", "urn:doc:anyotherdoc");
    assertTrue (IdentifierHelper.areDocumentTypeIdentifiersEqual (aDI1, aDI1));
    assertTrue (IdentifierHelper.areDocumentTypeIdentifiersEqual (aDI1, aDI2));
    assertTrue (IdentifierHelper.areDocumentTypeIdentifiersEqual (aDI2, aDI1));
    assertFalse (IdentifierHelper.areDocumentTypeIdentifiersEqual (aDI1, aDI3a));
    assertFalse (IdentifierHelper.areDocumentTypeIdentifiersEqual (aDI1, aDI3b));
    assertFalse (IdentifierHelper.areDocumentTypeIdentifiersEqual (aDI2, aDI3a));
    assertFalse (IdentifierHelper.areDocumentTypeIdentifiersEqual (aDI2, aDI3b));
    assertFalse (IdentifierHelper.areDocumentTypeIdentifiersEqual (aDI3a, aDI3b));

    try
    {
      IdentifierHelper.areDocumentTypeIdentifiersEqual (aDI1, null);
      fail ("null parameter not allowed");
    }
    catch (final NullPointerException ex)
    {
      // expected
    }

    try
    {
      IdentifierHelper.areDocumentTypeIdentifiersEqual (null, aDI1);
      fail ("null parameter not allowed");
    }
    catch (final NullPointerException ex)
    {
      // expected
    }
  }

  @Test
  public void testAreIdentifiersEqualProcessIdentifier ()
  {
    final PeppolProcessIdentifier aDI1 = PeppolProcessIdentifier.createWithDefaultScheme ("urn:doc:anydoc");
    final PeppolProcessIdentifier aDI2 = PeppolProcessIdentifier.createWithDefaultScheme ("urn:doc:anydoc");
    final PeppolProcessIdentifier aDI3a = PeppolProcessIdentifier.createWithDefaultScheme ("urn:doc:anyotherdoc");
    final PeppolProcessIdentifier aDI3b = new PeppolProcessIdentifier ("my-procid-test", "urn:doc:anyotherdoc");
    assertTrue (IdentifierHelper.areProcessIdentifiersEqual (aDI1, aDI1));
    assertTrue (IdentifierHelper.areProcessIdentifiersEqual (aDI1, aDI2));
    assertTrue (IdentifierHelper.areProcessIdentifiersEqual (aDI2, aDI1));
    assertFalse (IdentifierHelper.areProcessIdentifiersEqual (aDI1, aDI3a));
    assertFalse (IdentifierHelper.areProcessIdentifiersEqual (aDI1, aDI3b));
    assertFalse (IdentifierHelper.areProcessIdentifiersEqual (aDI2, aDI3a));
    assertFalse (IdentifierHelper.areProcessIdentifiersEqual (aDI2, aDI3b));
    assertFalse (IdentifierHelper.areProcessIdentifiersEqual (aDI3a, aDI3b));

    try
    {
      IdentifierHelper.areProcessIdentifiersEqual (aDI1, null);
      fail ("null parameter not allowed");
    }
    catch (final NullPointerException ex)
    {
      // expected
    }

    try
    {
      IdentifierHelper.areProcessIdentifiersEqual (null, aDI1);
      fail ("null parameter not allowed");
    }
    catch (final NullPointerException ex)
    {
      // expected
    }
  }

  @Test
  @SuppressFBWarnings ("NP_NONNULL_PARAM_VIOLATION")
  public void getIdentifierURIEncoded ()
  {
    final IParticipantIdentifier aPI = PeppolParticipantIdentifier.createWithDefaultScheme ("0088:123abc");
    assertEquals ("iso6523-actorid-upis::0088:123abc", aPI.getURIEncoded ());

    final DocumentIdentifierType aDI = PeppolDocumentTypeIdentifier.createWithDefaultScheme ("urn:doc:anydoc");
    assertEquals ("busdox-docid-qns::urn:doc:anydoc", aDI.getURIEncoded ());

    assertEquals ("::value", new SimpleParticipantIdentifier (null, "value").getURIEncoded ());

    try
    {
      new SimpleParticipantIdentifier ("scheme", null).getURIEncoded ();
      fail ("null value should trigger an error!");
    }
    catch (final IllegalArgumentException ex)
    {
      // expected
    }
  }

  @Test
  public void testGetIdentifierURIPercentEncoded ()
  {
    IParticipantIdentifier aPI = PeppolParticipantIdentifier.createWithDefaultScheme ("0088:123abc");
    assertEquals ("iso6523-actorid-upis%3A%3A0088%3A123abc", aPI.getURIPercentEncoded ());
    aPI = PeppolParticipantIdentifier.createWithDefaultScheme (EPredefinedIdentifierIssuingAgency.GLN.createIdentifierValue ("123abc"));
    assertEquals ("iso6523-actorid-upis%3A%3A0088%3A123abc", aPI.getURIPercentEncoded ());
    aPI = EPredefinedIdentifierIssuingAgency.GLN.createParticipantIdentifier ("123abc");
    assertEquals ("iso6523-actorid-upis%3A%3A0088%3A123abc", aPI.getURIPercentEncoded ());

    // Different value
    aPI = PeppolParticipantIdentifier.createWithDefaultScheme ("0088/123abc");
    assertEquals ("iso6523-actorid-upis%3A%3A0088%2F123abc", aPI.getURIPercentEncoded ());
  }
}
