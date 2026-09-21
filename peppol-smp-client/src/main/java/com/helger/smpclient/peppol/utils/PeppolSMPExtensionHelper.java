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
package com.helger.smpclient.peppol.utils;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.annotation.concurrent.Immutable;
import com.helger.annotation.style.PresentForCodeCoverage;
import com.helger.annotation.style.ReturnsMutableCopy;
import com.helger.smpclient.extension.SMPExtension;
import com.helger.smpclient.extension.SMPExtensionList;
import com.helger.xsds.peppol.smp1.ExtensionType;

/**
 * Convert the network neutral {@link SMPExtension} and {@link SMPExtensionList} to the Peppol SMP
 * v1 data model. The conversions for OASIS BDXR SMP v1 and v2 are contained in the classes
 * themselves - only the Peppol SMP v1 data model is specific enough to be kept out of the generic
 * module.
 *
 * @author Philip Helger
 * @since 13.0.0
 */
@Immutable
public final class PeppolSMPExtensionHelper
{
  private static final Logger LOGGER = LoggerFactory.getLogger (PeppolSMPExtensionHelper.class);

  @PresentForCodeCoverage
  private static final PeppolSMPExtensionHelper INSTANCE = new PeppolSMPExtensionHelper ();

  private PeppolSMPExtensionHelper ()
  {}

  /**
   * Convert a single extension to the Peppol SMP v1 data model.
   *
   * @param aExtension
   *        The extension to be converted. May not be <code>null</code>.
   * @return <code>null</code> if the extension contains no XML element.
   */
  @Nullable
  @ReturnsMutableCopy
  public static ExtensionType getAsPeppolExtension (@NonNull final SMPExtension aExtension)
  {
    if (aExtension.getAny () == null)
      return null;

    // Use only the XML element of the extension
    final ExtensionType ret = new ExtensionType ();
    ret.setAny (aExtension.getAny ());
    return ret;
  }

  /**
   * Convert an extension list to the Peppol SMP v1 data model. The Peppol data model only knows a
   * single extension, so only the first one is converted.
   *
   * @param aExtensions
   *        The extension list to be converted. May not be <code>null</code>.
   * @return <code>null</code> if the list is empty or the first extension contains no XML element.
   */
  @Nullable
  @ReturnsMutableCopy
  public static ExtensionType getAsPeppolExtension (@NonNull final SMPExtensionList aExtensions)
  {
    if (aExtensions.extensions ().isEmpty ())
      return null;

    if (aExtensions.extensions ().size () > 1)
      LOGGER.warn ("The Peppol data model only knows 1 extension. You have " +
                   aExtensions.extensions ().size () +
                   " extension");
    return getAsPeppolExtension (aExtensions.extensions ().getFirstOrNull ());
  }
}
