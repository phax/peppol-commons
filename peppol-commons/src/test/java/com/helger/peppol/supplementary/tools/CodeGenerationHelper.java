/**
 * Copyright (C) 2015-2017 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.helger.peppol.supplementary.tools;

import java.util.Locale;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.helger.commons.annotation.Nonempty;
import com.helger.commons.string.StringHelper;
import com.helger.peppol.identifier.peppol.doctype.IPeppolDocumentTypeIdentifierParts;

@Immutable
final class CodeGenerationHelper
{
  private static final String SKIP_TRANSACTION_PREFIX = "urn:www.cenbii.eu:transaction:biicoretrdm";
  private static final String SKIP_TRANSACTION_PREFIX2 = "urn:www.cenbii.eu:transaction:biitrns";
  private static final String SKIP_BIS_PREFIX = "urn:www.peppol.eu:bis:peppol";

  private CodeGenerationHelper ()
  {}

  @Nonnull
  @Nonempty
  public static String createShortcutDocumentTypeIDName (@Nonnull final IPeppolDocumentTypeIdentifierParts aDocIDParts)
  {
    // Create a shortcut constant with a more readable name!
    String sTransactionID = "";
    if (aDocIDParts.getTransactionID ().startsWith (SKIP_TRANSACTION_PREFIX))
    {
      sTransactionID = "_T" + aDocIDParts.getTransactionID ().substring (SKIP_TRANSACTION_PREFIX.length ());
      final int nIndex = sTransactionID.indexOf (':');
      if (nIndex >= 0)
        sTransactionID = sTransactionID.substring (0, nIndex);
    }
    else
      if (aDocIDParts.getTransactionID ().startsWith (SKIP_TRANSACTION_PREFIX2))
      {
        sTransactionID = "_T" + aDocIDParts.getTransactionID ().substring (SKIP_TRANSACTION_PREFIX2.length ());
        final int nIndex = sTransactionID.indexOf (':');
        if (nIndex >= 0)
          sTransactionID = sTransactionID.substring (0, nIndex);
      }

    String sExtensionID = "";
    for (final String sCurExtensionID : aDocIDParts.getExtensionIDs ())
      if (sCurExtensionID.startsWith (SKIP_BIS_PREFIX))
      {
        // BIS extension
        sExtensionID = "_BIS" + sCurExtensionID.substring (SKIP_BIS_PREFIX.length ());
        final int nIndex = sExtensionID.indexOf (":ver");
        if (nIndex >= 0)
        {
          // Add version number
          String sVersion = "_V" +
                            sExtensionID.substring (nIndex + 4, nIndex + 5) +
                            sExtensionID.substring (nIndex + 6, nIndex + 7);
          if (sVersion.equals ("_V10"))
          {
            // For backwards compatibility
            sVersion = "";
          }
          sExtensionID = sExtensionID.substring (0, nIndex) + sVersion;
        }
      }
      else
      {
        // Non-BIS extension
        String sExt = StringHelper.trimStart (sCurExtensionID, "urn:");
        sExt = StringHelper.replaceAll (sExt, '.', '_');
        sExt = StringHelper.replaceAll (sExt, ':', '_');
        sExtensionID += '_';
        sExtensionID += sExt;
      }

    return (aDocIDParts.getLocalName () + sTransactionID + sExtensionID).toUpperCase (Locale.US);
  }

  @Nullable
  @Nonempty
  public static String createShortcutBISIDName (@Nonnull final String sBISID)
  {
    if (!sBISID.startsWith (SKIP_BIS_PREFIX))
      throw new IllegalArgumentException ("Invalid BIS ID: " + sBISID);

    String ret = "BIS" + sBISID.substring (SKIP_BIS_PREFIX.length ());
    final int nIndex = ret.indexOf (":ver");
    if (nIndex >= 0)
    {
      // Add version number
      String sVersion = "_V" + ret.substring (nIndex + 4, nIndex + 5) + ret.substring (nIndex + 6, nIndex + 7);
      if (sVersion.equals ("_V10"))
      {
        // For backwards compatibility
        sVersion = "";
      }
      ret = ret.substring (0, nIndex) + sVersion;
    }
    return ret.toUpperCase (Locale.US);
  }
}
