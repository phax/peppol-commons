package com.helger.peppol.mlr;

import com.helger.ubl21.UBL21Marshaller;
import com.helger.ubl21.UBL21Marshaller.UBL21JAXBMarshaller;

import oasis.names.specification.ubl.schema.xsd.applicationresponse_21.ApplicationResponseType;

/**
 * Special Peppol MLR Marshaller that does the same as the UBL 2.1 marshaller
 *
 * @author Philip Helger
 */
public class PeppolMLRMarshaller extends UBL21JAXBMarshaller <ApplicationResponseType>
{
  public PeppolMLRMarshaller ()
  {
    super (ApplicationResponseType.class,
           UBL21Marshaller.getAllApplicationResponseXSDs (),
           oasis.names.specification.ubl.schema.xsd.applicationresponse_21.ObjectFactory._ApplicationResponse_QNAME);
  }
}
