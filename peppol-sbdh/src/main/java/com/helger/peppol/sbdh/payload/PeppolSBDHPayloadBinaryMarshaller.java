package com.helger.peppol.sbdh.payload;

import com.helger.jaxb.GenericJAXBMarshaller;
import com.helger.peppol.sbdh.CPeppolSBDH;
import com.helger.peppol.sbdh.spec12.BinaryContentType;
import com.helger.peppol.sbdh.spec12.ObjectFactory;

/**
 * Special JAXB marshaller for Peppol SBDH Binary payload.
 *
 * @author Philip Helger
 * @since 9.0.4
 */
public class PeppolSBDHPayloadBinaryMarshaller extends GenericJAXBMarshaller <BinaryContentType>
{
  public PeppolSBDHPayloadBinaryMarshaller ()
  {
    super (BinaryContentType.class,
           CPeppolSBDH.PEPPOL_SPECIAL_PAYLOADS_XSDS,
           new ObjectFactory ()::createBinaryContent);
  }
}
