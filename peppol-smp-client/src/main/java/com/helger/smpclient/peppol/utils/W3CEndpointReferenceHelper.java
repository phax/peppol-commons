/*
 * Copyright (C) 2015-2023 Philip Helger
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
package com.helger.smpclient.peppol.utils;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import javax.xml.transform.dom.DOMResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.PresentForCodeCoverage;
import com.helger.commons.collection.impl.CommonsArrayList;
import com.helger.commons.collection.impl.ICommonsList;
import com.helger.xml.ChildElementIterator;
import com.helger.xml.XMLFactory;
import com.helger.xml.XMLHelper;

import jakarta.xml.ws.wsaddressing.W3CEndpointReference;
import jakarta.xml.ws.wsaddressing.W3CEndpointReferenceBuilder;

/**
 * As the default WS-Addressing binding since JAXB 2.1 uses the
 * {@link W3CEndpointReference} class, we must also use this class, otherwise
 * JAXB would complain, that there are 2 contexts for the same namespace+element
 * combination.<br>
 * The issue with {@link W3CEndpointReference} is that it can easily be created
 * using the {@link W3CEndpointReferenceBuilder} class, but it's not possible to
 * extract information from it (get....). This class offers a workaround by
 * using DOM serialization to access the content of a
 * {@link W3CEndpointReference}. In case the serialization tag names of
 * {@link W3CEndpointReference} change, this implementation has to be adopted!
 * <br>
 * The JIRA issue JAX_WS-1132 was filed to help dealing with this issue.
 *
 * @author Philip Helger
 */
@Immutable
public final class W3CEndpointReferenceHelper
{
  @PresentForCodeCoverage
  private static final W3CEndpointReferenceHelper INSTANCE = new W3CEndpointReferenceHelper ();

  private W3CEndpointReferenceHelper ()
  {}

  /**
   * Create a new endpoint reference for the given address without reference
   * parameters.
   *
   * @param sAddress
   *        The address to use. May not be <code>null</code> but may be empty to
   *        .
   * @return The non-<code>null</code> endpoint reference for the given address
   */
  @Nonnull
  public static W3CEndpointReference createEndpointReference (@Nonnull final String sAddress)
  {
    ValueEnforcer.notNull (sAddress, "Address");

    return new W3CEndpointReferenceBuilder ().address (sAddress).build ();
  }

  /**
   * Create a new endpoint reference for the given address, using the specified
   * reference parameters.
   *
   * @param sAddress
   *        The address to use. May not be <code>null</code> but may be empty.
   * @param aReferenceParameters
   *        The non-<code>null</code> list of reference parameters. May not be
   *        <code>null</code>.
   * @return The non-<code>null</code> endpoint reference for the given address
   */
  @Nonnull
  public static W3CEndpointReference createEndpointReference (@Nonnull final String sAddress,
                                                              @Nonnull final Iterable <Element> aReferenceParameters)
  {
    ValueEnforcer.notNull (sAddress, "Address");

    W3CEndpointReferenceBuilder aBuilder = new W3CEndpointReferenceBuilder ().address (sAddress);
    for (final Element aReferenceParameter : aReferenceParameters)
      aBuilder = aBuilder.referenceParameter (aReferenceParameter);
    return aBuilder.build ();
  }

  /**
   * Marshal the passed endpoint reference to a DOM document and return the
   * document element.<br>
   * This is necessary, as {@link W3CEndpointReference} does not provide read
   * access methods.
   *
   * @param aEndpointReference
   *        The endpoint to be marshaled. May not be <code>null</code>.
   * @return The document element called "EndpointReference"
   */
  @Nonnull
  private static Element _convertReferenceToXML (@Nonnull final W3CEndpointReference aEndpointReference)
  {
    final Document aDoc = XMLFactory.newDocument ();
    final DOMResult ret = new DOMResult (aDoc);
    aEndpointReference.writeTo (ret);
    return aDoc.getDocumentElement ();
  }

  /**
   * Get the address contained in the passed endpoint reference.
   *
   * @param aEndpointReference
   *        The endpoint reference to retrieve the address from. May not be
   *        <code>null</code>.
   * @return The contained address.
   */
  @Nullable
  public static String getAddress (@Nonnull final W3CEndpointReference aEndpointReference)
  {
    ValueEnforcer.notNull (aEndpointReference, "EndpointReference");

    final Element eAddress = XMLHelper.getFirstChildElementOfName (_convertReferenceToXML (aEndpointReference),
                                                                   "Address");
    return eAddress == null ? null : eAddress.getTextContent ();
  }

  /**
   * Get a list of all reference parameters contained in the passed endpoint
   * reference.
   *
   * @param aEndpointReference
   *        The endpoint reference to retrieve the reference parameters. May not
   *        be <code>null</code>.
   * @return A mutable element list
   */
  @Nullable
  public static ICommonsList <Element> getReferenceParameters (@Nonnull final W3CEndpointReference aEndpointReference)
  {
    ValueEnforcer.notNull (aEndpointReference, "EndpointReference");

    final Element eRefParams = XMLHelper.getFirstChildElementOfName (_convertReferenceToXML (aEndpointReference),
                                                                     "ReferenceParameters");
    if (eRefParams == null)
      return null;

    // All all child elements of ReferenceParameters :)
    return new CommonsArrayList <> (new ChildElementIterator (eRefParams));
  }

  /**
   * Get the reference parameter at the given index
   *
   * @param aEndpointReference
   *        The object to retrieve the reference parameter from. May not be
   *        <code>null</code>.
   * @param nIndex
   *        The index to retrieve. Must be &ge; 0.
   * @return <code>null</code> if the index is invalid
   */
  @Nullable
  public static Element getReferenceParameter (@Nonnull final W3CEndpointReference aEndpointReference,
                                               @Nonnegative final int nIndex)
  {
    ValueEnforcer.notNull (aEndpointReference, "EndpointReference");
    ValueEnforcer.isGE0 (nIndex, "Index");

    // Get all reference parameters
    final ICommonsList <Element> aReferenceParameters = getReferenceParameters (aEndpointReference);

    // And extract the one at the desired index.
    return aReferenceParameters == null ? null : aReferenceParameters.getAtIndex (nIndex);
  }
}
