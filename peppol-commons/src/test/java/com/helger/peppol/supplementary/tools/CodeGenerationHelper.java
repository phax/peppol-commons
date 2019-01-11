/**
 * Copyright (C) 2015-2019 Philip Helger (www.helger.com)
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
  private CodeGenerationHelper ()
  {}

  @Nonnull
  @Nonempty
  public static String createShortcutDocumentTypeIDName (@Nonnull final IPeppolDocumentTypeIdentifierParts aDocIDParts)
  {
    // Create a shortcut constant with a more readable name!
    final String sLocalName = aDocIDParts.getLocalName ();
    final String sCustomizationID = aDocIDParts.getCustomizationID ();

    // Invoice
    if ("urn:www.cenbii.eu:transaction:biicoretrdm010:ver1.0:#urn:www.peppol.eu:bis:peppol4a:ver1.0".equals (sCustomizationID))
      return "INVOICE_T010_BIS4A";
    if ("urn:www.cenbii.eu:transaction:biitrns010:ver2.0:extended:urn:www.peppol.eu:bis:peppol4a:ver2.0".equals (sCustomizationID))
      return "INVOICE_T010_BIS4A_V20";
    if ("urn:www.cenbii.eu:transaction:biicoretrdm010:ver1.0:#urn:www.peppol.eu:bis:peppol5a:ver1.0".equals (sCustomizationID))
      return "INVOICE_T010_BIS5A";
    if ("urn:www.cenbii.eu:transaction:biitrns010:ver2.0:extended:urn:www.peppol.eu:bis:peppol5a:ver2.0".equals (sCustomizationID))
      return "INVOICE_T010_BIS5A_V20";
    if ("urn:www.cenbii.eu:transaction:biicoretrdm015:ver1.0:#urn:www.peppol.eu:bis:peppol5a:ver1.0".equals (sCustomizationID))
      return "INVOICE_T015_BIS5A";
    if ("urn:www.cenbii.eu:transaction:biicoretrdm010:ver1.0:#urn:www.peppol.eu:bis:peppol6a:ver1.0".equals (sCustomizationID))
      return "INVOICE_T010_BIS6A";
    if ("urn:www.cenbii.eu:transaction:biicoretrdm015:ver1.0:#urn:www.peppol.eu:bis:peppol6a:ver1.0".equals (sCustomizationID))
      return "INVOICE_T015_BIS6A";
    if ("urn:cen.eu:en16931:2017#compliant#urn:fdc:peppol.eu:2017:poacc:billing:3.0".equals (sCustomizationID))
    {
      if ("Invoice".equals (sLocalName))
        return "INVOICE_EN16931_PEPPOL_V30";
      if ("CreditNote".equals (sLocalName))
        return "CREDITNOTE_EN16931_PEPPOL_V30";
      if ("CrossIndustryInvoice".equals (sLocalName))
        return "INVOICE_CII_EN16931_PEPPOL_V30";
    }

    // CreditNote
    if ("urn:www.cenbii.eu:transaction:biicoretrdm014:ver1.0:#urn:www.peppol.eu:bis:peppol5a:ver1.0".equals (sCustomizationID))
      return "CREDITNOTE_T014_BIS5A";
    if ("urn:www.cenbii.eu:transaction:biitrns014:ver2.0:extended:urn:www.peppol.eu:bis:peppol5a:ver2.0".equals (sCustomizationID))
      return "CREDITNOTE_T014_BIS5A_V20";
    if ("urn:www.cenbii.eu:transaction:biicoretrdm014:ver1.0:#urn:www.peppol.eu:bis:peppol6a:ver1.0".equals (sCustomizationID))
      return "CREDITNOTE_T014_BIS6A";

    // ApplicationResponse
    if ("urn:www.cenbii.eu:transaction:biicoretrdm057:ver1.0:#urn:www.peppol.eu:bis:peppol1a:ver1.0".equals (sCustomizationID))
      return "APPLICATIONRESPONSE_T057_BIS1A";
    if ("urn:www.cenbii.eu:transaction:biicoretrdm058:ver1.0:#urn:www.peppol.eu:bis:peppol1a:ver1.0".equals (sCustomizationID))
      return "APPLICATIONRESPONSE_T058_BIS1A";
    if ("urn:www.cenbii.eu:transaction:biitrns071:ver2.0:extended:urn:www.peppol.eu:bis:peppol36a:ver1.0".equals (sCustomizationID))
      return "APPLICATIONRESPONSE_T071_BIS36A";

    // Catalogue
    if ("urn:www.cenbii.eu:transaction:biitrns019:ver2.0:extended:urn:www.peppol.eu:bis:peppol1a:ver4.0".equals (sCustomizationID))
      return "CATALOGUE_T019_BIS1A_V40";
    if ("urn:www.cenbii.eu:transaction:biicoretrdm019:ver1.0:#urn:www.peppol.eu:bis:peppol1a:ver1.0".equals (sCustomizationID))
      return "CATALOGUE_T019_BIS1A";

    // Order
    if ("urn:www.cenbii.eu:transaction:biicoretrdm001:ver1.0:#urn:www.peppol.eu:bis:peppol3a:ver1.0".equals (sCustomizationID))
      return "ORDER_T001_BIS3A";
    if ("urn:www.cenbii.eu:transaction:biitrns001:ver2.0:extended:urn:www.peppol.eu:bis:peppol3a:ver2.0".equals (sCustomizationID))
      return "ORDER_T001_BIS3A_V20";
    if ("urn:www.cenbii.eu:transaction:biitrns001:ver2.0:extended:urn:www.peppol.eu:bis:peppol03a:ver2.0".equals (sCustomizationID))
      return "ORDER_T001_BIS03A_V20";
    if ("urn:www.cenbii.eu:transaction:biicoretrdm001:ver1.0:#urn:www.peppol.eu:bis:peppol6a:ver1.0".equals (sCustomizationID))
      return "ORDER_T001_BIS6A";
    if ("urn:www.cenbii.eu:transaction:biitrns001:ver2.0:extended:urn:www.peppol.eu:bis:peppol28a:ver1.0".equals (sCustomizationID))
      return "ORDER_T001_BIS28A";
    if ("urn:www.cenbii.eu:transaction:biitrns076:ver2.0:extended:urn:www.peppol.eu:bis:peppol28a:ver1.0".equals (sCustomizationID))
      return "ORDER_T076_BIS28A";

    // OrderResponseSimple
    if ("urn:www.cenbii.eu:transaction:biicoretrdm002:ver1.0:#urn:www.peppol.eu:bis:peppol6a:ver1.0".equals (sCustomizationID))
      return "ORDERRESPONSESIMPLE_T002_BIS6A";
    if ("urn:www.cenbii.eu:transaction:biicoretrdm003:ver1.0:#urn:www.peppol.eu:bis:peppol6a:ver1.0".equals (sCustomizationID))
      return "ORDERRESPONSESIMPLE_T003_BIS6A";

    // DespatchAdvice
    if ("urn:www.cenbii.eu:transaction:biitrns016:ver1.0:extended:urn:www.peppol.eu:bis:peppol30a:ver1.0".equals (sCustomizationID))
      return "DESPATCHADVICE_T016_BIS30A";

    // EHF
    if ("urn:www.cenbii.eu:transaction:biicoretrdm010:ver1.0:#urn:www.peppol.eu:bis:peppol4a:ver1.0#urn:www.difi.no:ehf:faktura:ver1".equals (sCustomizationID))
      return "INVOICE_T010_BIS4A_WWW_DIFI_NO_EHF_FAKTURA_VER1";
    if ("urn:www.cenbii.eu:transaction:biicoretrdm014:ver1.0:#urn:www.cenbii.eu:profile:biixx:ver1.0#urn:www.difi.no:ehf:kreditnota:ver1".equals (sCustomizationID))
      return "CREDITNOTE_T014_WWW_CENBII_EU_PROFILE_BIIXX_VER1_0_WWW_DIFI_NO_EHF_KREDITNOTA_VER1";

    // Other stuff
    if ("urn:www.cenbii.eu:transaction:biicoretrdm993:ver0.1:#urn:www.peppol.eu:bis:peppol993a:ver1.0".equals (sCustomizationID))
      return "CATALOGUETEMPLATE_T993_BIS993A";
    if ("urn:www.cenbii.eu:transaction:biicoretrdm992:ver0.1:#urn:www.peppol.eu:bis:peppol992a:ver1.0".equals (sCustomizationID))
      return "VIRTUALCOMPANYDOSSIERPACKAGE_T992_BIS992A";
    if ("urn:www.cenbii.eu:transaction:biicoretrdm991:ver0.1:#urn:www.peppol.eu:bis:peppol991a:ver1.0".equals (sCustomizationID))
      return "VIRTUALCOMPANYDOSSIER_T991_BIS991A";

    // UK
    if ("urn:www.cenbii.eu:transaction:biitrns001:ver2.0:extended:urn:www.peppol.eu:bis:peppol28a:ver1.0:extended:urn:fdc:peppol-authority.co.uk:spec:ordering:ver1.0".equals (sCustomizationID))
      return "DHSC_CUSTOMIZED_ORDERING_PROFILE_V1_ORDER";
    if ("urn:www.cenbii.eu:transaction:biitrns076:ver2.0:extended:urn:www.peppol.eu:bis:peppol28a:ver1.0:extended:urn:fdc:peppol-authority.co.uk:spec:ordering:ver1.0".equals (sCustomizationID))
      return "DHSC_CUSTOMIZED_ORDERING_PROFILE_V1_ORDER_RESPONSE";

    // Pre-Award
    if ("urn:www.cenbii.eu:transaction:biitrdm081:ver3.0:extended:urn:fdc:peppol.eu:2017:pracc:t001:ver1.0".equals (sCustomizationID))
      return "EXPRESSION_OF_INTEREST_REQUEST_V1";
    if ("urn:www.cenbii.eu:transaction:biitrdm082:ver3.0:extended:urn:fdc:peppol.eu:2017:pracc:t002:ver1.0".equals (sCustomizationID))
      return "EXPRESSION_OF_INTEREST_RESPONSE_V1";
    if ("urn:www.cenbii.eu:transaction:biitrdm097:ver3.0:extended:urn:fdc:peppol.eu:2017:pracc:t003:ver1.0".equals (sCustomizationID))
      return "TENDER_STATUS_REQUEST_V1";
    if ("urn:www.cenbii.eu:transaction:biitrdm083:ver3.0:extended:urn:fdc:peppol.eu:2017:pracc:t004:ver1.0".equals (sCustomizationID))
      return "CALL_FOR_TENDERS_V1";
    if ("urn:www.cenbii.eu:transaction:biitrdm090:ver3.0:extended:urn:fdc:peppol.eu:2017:pracc:t005:ver1.0".equals (sCustomizationID))
      return "TENDER_V1";
    if ("urn:www.cenbii.eu:transaction:biitrdm045:ver3.0:extended:urn:fdc:peppol.eu:2017:pracc:t006:ver1.0".equals (sCustomizationID))
      return "TENDER_RECEIPT_V1";

    // OIO UBL
    if ("OIOUBL-2.02".equals (sCustomizationID))
    {
      if ("UtilityStatement".equals (sLocalName))
        return "OIOUBL_UTILITY_STATEMENT_202";
      if ("Reminder".equals (sLocalName))
        return "OIOUBL_REMINDER_202";
    }

    // XRechnung
    if ("urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_1.1".equals (sCustomizationID))
    {
      if ("Invoice".equals (sLocalName))
        return "XRECHNUNG_INVOICE_UBL_V11";
      if ("CreditNote".equals (sLocalName))
        return "XRECHNUNG_CREDIT_NOTE_UBL_V11";
      if ("CrossIndustryInvoice".equals (sLocalName))
        return "XRECHNUNG_INVOICE_CII_V11";
    }

    if ("urn:cen.eu:en16931:2017#conformant#urn:UBL.BE:1.0.0.20180214".equals (sCustomizationID))
    {
      if ("Invoice".equals (sLocalName))
        return "UBL_BE_INVOICE_UBL_V11";
      if ("CreditNote".equals (sLocalName))
        return "UBL_BE_CREDIT_NOTE_UBL_V11";
    }

    String sExt = sCustomizationID;
    sExt = StringHelper.replaceAll (sExt, "urn:", "");
    sExt = StringHelper.replaceAll (sExt, '.', '_');
    sExt = StringHelper.replaceAll (sExt, ':', '_');
    sExt = StringHelper.replaceAll (sExt, '#', '_');
    sExt = StringHelper.replaceAll (sExt, '-', '_');

    return (aDocIDParts.getLocalName () + "_" + sExt).toUpperCase (Locale.US);
  }

  @Nullable
  @Nonempty
  public static String createShortcutBISIDName (@Nonnull final String sBISID)
  {
    String ret = sBISID;
    ret = StringHelper.removeAll (ret, ' ');
    ret = StringHelper.replaceAll (ret, '.', '_');
    return ret.toUpperCase (Locale.US);
  }

  @Nullable
  @Nonempty
  public static String createShortcutTransportProtocolName (@Nonnull final String sBISID)
  {
    String ret = sBISID;
    ret = StringHelper.removeAll (ret, ' ');
    ret = StringHelper.replaceAll (ret, '.', '_');
    return ret.toUpperCase (Locale.US);
  }
}
