package com.helger.peppol.businesscard.helper;

import org.jspecify.annotations.NonNull;

import com.helger.jaxb.GenericJAXBMarshaller;

/**
 * Business Card Marshaller callback
 *
 * @author Philip Helger
 * @since 12.2.1
 */
public interface IPDBusinessCardMarshallerCustomizer
{
  /**
   * Business Card Marshaller callback
   *
   * @param aMarshaller
   *        The marshaller to be customized. Never <code>null</code>.
   * @param eBCVersion
   *        The Business Card version that was determined. Never <code>null</code>.
   */
  void accept (@NonNull GenericJAXBMarshaller <?> aMarshaller, @NonNull EBusinessCardVersion eBCVersion);
}
