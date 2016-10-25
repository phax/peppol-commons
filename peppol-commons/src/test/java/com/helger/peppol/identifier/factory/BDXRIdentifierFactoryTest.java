package com.helger.peppol.identifier.factory;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

/**
 * Test class for class {@link BDXRIdentifierFactory}.
 *
 * @author Philip Helger
 */
public final class BDXRIdentifierFactoryTest
{
  @Test
  public void testDocTypeID ()
  {
    final BDXRIdentifierFactory aIF = BDXRIdentifierFactory.INSTANCE;
    assertNull (aIF.createDocumentTypeIdentifier (null, null));
    assertNull (aIF.createDocumentTypeIdentifier ("bla", null));
    assertNotNull (aIF.createDocumentTypeIdentifier (null, "any"));
    assertNotNull (aIF.createDocumentTypeIdentifier (null, ""));
    assertNotNull (aIF.createDocumentTypeIdentifier ("", ""));
  }
}
