package com.helger.xsds.peppol.smp1;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import com.helger.commons.collection.impl.CommonsArrayList;
import com.helger.commons.collection.impl.ICommonsList;
import com.helger.commons.io.resource.ClassPathResource;
import com.helger.xsds.peppol.id1.CPeppolID;
import com.helger.xsds.wsaddr.CWSAddr;
import com.helger.xsds.xmldsig.CXMLDSig;

/**
 * Constants for the Peppol SMP data types
 *
 * @author Philip Helger
 */
@Immutable
public final class CSMPDataTypes
{
  private CSMPDataTypes ()
  {}

  @Nonnull
  public static ClassPathResource getXSDResourcePeppolSMP ()
  {
    return new ClassPathResource ("/schemas/peppol-smp-types-v1-ext.xsd", CSMPDataTypes.class.getClassLoader ());
  }

  @Nonnull
  public static ICommonsList <ClassPathResource> getAllXSDResources ()
  {
    return new CommonsArrayList <> (CXMLDSig.getXSDResource (),
                                    CPeppolID.getXSDResourcePeppolIdentifiers (),
                                    CWSAddr.getXSDResource (),
                                    getXSDResourcePeppolSMP ());
  }
}
