/*
 * Copyright (C) 2015-2026 Philip Helger
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
package com.helger.edelivery.sbdh;

import java.io.InputStream;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.unece.cefact.namespaces.sbdh.StandardBusinessDocument;
import org.w3c.dom.Node;

import com.helger.annotation.concurrent.NotThreadSafe;
import com.helger.annotation.style.OverrideOnDemand;
import com.helger.base.enforce.ValueEnforcer;
import com.helger.base.io.stream.StreamHelper;
import com.helger.base.trait.IGenericImplTrait;
import com.helger.diagnostics.error.IError;
import com.helger.diagnostics.error.SingleError;
import com.helger.io.resource.IReadableResource;
import com.helger.peppolid.factory.IIdentifierFactory;
import com.helger.sbdh.SBDMarshaller;

/**
 * Base class for reading the data of a Standard Business Document Header. It contains the parts
 * that are independent of the concrete network - the parsing of the document and the handling of
 * the value checks. The rules to be checked are added by the derived classes.
 *
 * @author Philip Helger
 * @param <DATATYPE>
 *        The SBDH data type to be read
 * @param <IMPLTYPE>
 *        The implementation type
 * @since 13.0.0
 */
@NotThreadSafe
public abstract class AbstractSBDHDataReader <DATATYPE extends AbstractSBDHData <DATATYPE>, IMPLTYPE extends AbstractSBDHDataReader <DATATYPE, IMPLTYPE>>
                                             implements
                                             IGenericImplTrait <IMPLTYPE>
{
  public static final boolean DEFAULT_PERFORM_VALUE_CHECKS = true;

  private final IIdentifierFactory m_aIdentifierFactory;
  private boolean m_bPerformValueChecks = DEFAULT_PERFORM_VALUE_CHECKS;

  /**
   * Constructor
   *
   * @param aIdentifierFactory
   *        The identifier factory to be used. May not be <code>null</code>.
   */
  protected AbstractSBDHDataReader (@NonNull final IIdentifierFactory aIdentifierFactory)
  {
    m_aIdentifierFactory = ValueEnforcer.notNull (aIdentifierFactory, "IdentifierFactory");
  }

  /**
   * @return The identifier factory as provided in the constructor. Never <code>null</code>.
   */
  @NonNull
  public final IIdentifierFactory getIdentifierFactory ()
  {
    return m_aIdentifierFactory;
  }

  /**
   * @return <code>true</code> if the value checks are performed, <code>false</code> if not. The
   *         default is {@link #DEFAULT_PERFORM_VALUE_CHECKS}.
   */
  public final boolean isPerformValueChecks ()
  {
    return m_bPerformValueChecks;
  }

  /**
   * Enable or disable the value checks.
   *
   * @param b
   *        <code>true</code> to perform the value checks, <code>false</code> to not do it.
   * @return this for chaining
   */
  @NonNull
  public final IMPLTYPE setPerformValueChecks (final boolean b)
  {
    m_bPerformValueChecks = b;
    return thisAsT ();
  }

  /**
   * @return The marshaller to read the Standard Business Document. Never <code>null</code>.
   */
  @NonNull
  @OverrideOnDemand
  protected SBDMarshaller createSBDMarshaller ()
  {
    final SBDMarshaller ret = new SBDMarshaller ();
    // Simply swallow all error messages where possible
    ret.setValidationEventHandler (null);
    return ret;
  }

  /**
   * Check if the provided header version is valid. By default only
   * {@link CSBDHConstants#HEADER_VERSION} is accepted.
   *
   * @param sHeaderVersion
   *        The value to check. May be <code>null</code>.
   * @return <code>true</code> if the value is valid.
   */
  @OverrideOnDemand
  protected boolean isValidHeaderVersion (@Nullable final String sHeaderVersion)
  {
    return CSBDHConstants.HEADER_VERSION.equals (sHeaderVersion);
  }

  /**
   * Parse the provided input stream into a Standard Business Document. The stream is closed by this
   * method.
   *
   * @param aStandardBusinessDocument
   *        The input stream to read from. May not be <code>null</code>.
   * @return <code>null</code> if the content could not be parsed.
   */
  @Nullable
  protected final StandardBusinessDocument parseSBD (@NonNull final InputStream aStandardBusinessDocument)
  {
    ValueEnforcer.notNull (aStandardBusinessDocument, "StandardBusinessDocument");

    try
    {
      return createSBDMarshaller ().read (aStandardBusinessDocument);
    }
    finally
    {
      StreamHelper.close (aStandardBusinessDocument);
    }
  }

  /**
   * Parse the provided resource into a Standard Business Document.
   *
   * @param aStandardBusinessDocument
   *        The resource to read from. May not be <code>null</code>.
   * @return <code>null</code> if the content could not be parsed.
   */
  @Nullable
  protected final StandardBusinessDocument parseSBD (@NonNull final IReadableResource aStandardBusinessDocument)
  {
    ValueEnforcer.notNull (aStandardBusinessDocument, "StandardBusinessDocument");

    return createSBDMarshaller ().read (aStandardBusinessDocument);
  }

  /**
   * Parse the provided DOM node into a Standard Business Document.
   *
   * @param aStandardBusinessDocument
   *        The DOM node to read from. May not be <code>null</code>.
   * @return <code>null</code> if the content could not be parsed.
   */
  @Nullable
  protected final StandardBusinessDocument parseSBD (@NonNull final Node aStandardBusinessDocument)
  {
    ValueEnforcer.notNull (aStandardBusinessDocument, "StandardBusinessDocument");

    return createSBDMarshaller ().read (aStandardBusinessDocument);
  }

  /**
   * Create an error object from the provided error code.
   *
   * @param sErrorField
   *        The name of the field that is in error. May be <code>null</code>.
   * @param aError
   *        The error code. May not be <code>null</code>.
   * @param aArgs
   *        The arguments of the error message. May be <code>null</code>.
   * @return Never <code>null</code>.
   */
  @NonNull
  protected static IError toError (@Nullable final String sErrorField,
                                   @NonNull final ISBDHDataError aError,
                                   @Nullable final Object... aArgs)
  {
    return SingleError.builderError ()
                      .errorFieldName (sErrorField)
                      .errorID (aError.getID ())
                      .errorText (aArgs == null ? aError.getErrorMessage () : aError.getErrorMessage (aArgs))
                      .build ();
  }

  /**
   * Create a warning object from the provided error code.
   *
   * @param sErrorField
   *        The name of the field that is in error. May be <code>null</code>.
   * @param aError
   *        The error code. May not be <code>null</code>.
   * @param aArgs
   *        The arguments of the error message. May be <code>null</code>.
   * @return Never <code>null</code>.
   */
  @NonNull
  protected static IError toWarn (@Nullable final String sErrorField,
                                  @NonNull final ISBDHDataError aError,
                                  @Nullable final Object... aArgs)
  {
    return SingleError.builderWarn ()
                      .errorFieldName (sErrorField)
                      .errorID (aError.getID ())
                      .errorText (aArgs == null ? aError.getErrorMessage () : aError.getErrorMessage (aArgs))
                      .build ();
  }
}
