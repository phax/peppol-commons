package com.helger.peppol.sbdh.payload;

import com.helger.jaxb.GenericJAXBMarshaller;
import com.helger.peppol.sbdh.CPeppolSBDH;
import com.helger.peppol.sbdh.spec12.ObjectFactory;
import com.helger.peppol.sbdh.spec12.TextContentType;

/**
 * Special JAXB marshaller for Peppol SBDH Text payload.
 *
 * @author Philip Helger
 * @since 9.0.4
 */
public class PeppolSBDHPayloadTextMarshaller extends GenericJAXBMarshaller <TextContentType>
{
  public PeppolSBDHPayloadTextMarshaller ()
  {
    super (TextContentType.class, CPeppolSBDH.PEPPOL_SPECIAL_PAYLOADS_XSDS, new ObjectFactory ()::createTextContent);
  }
}
