/**
 * Copyright (C) 2015-2016 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * Version: MPL 1.1/EUPL 1.1
 *
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at:
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * Alternatively, the contents of this file may be used under the
 * terms of the EUPL, Version 1.1 or - as soon they will be approved
 * by the European Commission - subsequent versions of the EUPL
 * (the "Licence"); You may not use this work except in compliance
 * with the Licence.
 * You may obtain a copy of the Licence at:
 * http://joinup.ec.europa.eu/software/page/eupl/licence-eupl
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and
 * limitations under the Licence.
 *
 * If you wish to allow use of your version of this file only
 * under the terms of the EUPL License and not to allow others to use
 * your version of this file under the MPL, indicate your decision by
 * deleting the provisions above and replace them with the notice and
 * other provisions required by the EUPL License. If you do not delete
 * the provisions above, a recipient may use your version of this file
 * under either the MPL or the EUPL License.
 */
package com.helger.peppol.identifier.peppol.process;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.annotation.CodingStyleguideUnaware;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.collection.CollectionHelper;
import com.helger.commons.collection.ext.ICommonsList;
import com.helger.commons.version.Version;
import com.helger.peppol.identifier.generic.process.IProcessIdentifier;
import com.helger.peppol.identifier.peppol.PeppolIdentifierHelper;
import com.helger.peppol.identifier.peppol.doctype.EPredefinedDocumentTypeIdentifier;
import com.helger.peppol.identifier.peppol.doctype.IPeppolPredefinedDocumentTypeIdentifier;

/**
 * This file is generated. Do NOT edit!
 */
@CodingStyleguideUnaware
public enum EPredefinedProcessIdentifier implements IPeppolPredefinedProcessIdentifier
{

  /**
   * urn:www.cenbii.eu:profile:bii01:ver1.0
   * 
   * @since code list 1.0.0
   */
  urn_www_cenbii_eu_profile_bii01_ver1_0 ("urn:www.cenbii.eu:profile:bii01:ver1.0",
                                          "urn:www.peppol.eu:bis:peppol1a:ver1.0",
                                          new EPredefinedDocumentTypeIdentifier [] { EPredefinedDocumentTypeIdentifier.CATALOGUE_T019_BIS1A,
                                                                                     EPredefinedDocumentTypeIdentifier.APPLICATIONRESPONSE_T057_BIS1A,
                                                                                     EPredefinedDocumentTypeIdentifier.APPLICATIONRESPONSE_T058_BIS1A },
                                          Version.parse ("1.0.0")),

  /**
   * urn:www.cenbii.eu:profile:bii01:ver2.0
   * 
   * @since code list 1.2.0
   */
  urn_www_cenbii_eu_profile_bii01_ver2_0 ("urn:www.cenbii.eu:profile:bii01:ver2.0",
                                          "urn:www.peppol.eu:bis:peppol1a:ver4.0",
                                          new EPredefinedDocumentTypeIdentifier [] { EPredefinedDocumentTypeIdentifier.CATALOGUE_T019_BIS1A_V40 },
                                          Version.parse ("1.2.0")),

  /**
   * urn:www.cenbii.eu:profile:bii03:ver1.0
   * 
   * @since code list 1.0.0
   */
  urn_www_cenbii_eu_profile_bii03_ver1_0 ("urn:www.cenbii.eu:profile:bii03:ver1.0",
                                          "urn:www.peppol.eu:bis:peppol3a:ver1.0",
                                          new EPredefinedDocumentTypeIdentifier [] { EPredefinedDocumentTypeIdentifier.ORDER_T001_BIS3A },
                                          Version.parse ("1.0.0")),

  /**
   * urn:www.cenbii.eu:profile:bii03:ver2.0
   * 
   * @since code list 1.2.0
   */
  urn_www_cenbii_eu_profile_bii03_ver2_0 ("urn:www.cenbii.eu:profile:bii03:ver2.0",
                                          "urn:www.peppol.eu:bis:peppol03a:ver2.0",
                                          new EPredefinedDocumentTypeIdentifier [] { EPredefinedDocumentTypeIdentifier.ORDER_T001_BIS03A_V20 },
                                          Version.parse ("1.2.0")),

  /**
   * urn:www.cenbii.eu:profile:bii04:ver1.0
   * 
   * @since code list 1.0.0
   */
  urn_www_cenbii_eu_profile_bii04_ver1_0 ("urn:www.cenbii.eu:profile:bii04:ver1.0",
                                          "urn:www.peppol.eu:bis:peppol4a:ver1.0",
                                          new EPredefinedDocumentTypeIdentifier [] { EPredefinedDocumentTypeIdentifier.INVOICE_T010_BIS4A },
                                          Version.parse ("1.0.0")),

  /**
   * urn:www.cenbii.eu:profile:bii04:ver2.0
   * 
   * @since code list 1.2.0
   */
  urn_www_cenbii_eu_profile_bii04_ver2_0 ("urn:www.cenbii.eu:profile:bii04:ver2.0",
                                          "urn:www.peppol.eu:bis:peppol4a:ver2.0",
                                          new EPredefinedDocumentTypeIdentifier [] { EPredefinedDocumentTypeIdentifier.INVOICE_T010_BIS4A_V20 },
                                          Version.parse ("1.2.0")),

  /**
   * urn:www.cenbii.eu:profile:bii05:ver1.0
   * 
   * @since code list 1.1.0
   */
  urn_www_cenbii_eu_profile_bii05_ver1_0 ("urn:www.cenbii.eu:profile:bii05:ver1.0",
                                          "urn:www.peppol.eu:bis:peppol5a:ver1.0",
                                          new EPredefinedDocumentTypeIdentifier [] { EPredefinedDocumentTypeIdentifier.INVOICE_T010_BIS5A,
                                                                                     EPredefinedDocumentTypeIdentifier.CREDITNOTE_T014_BIS5A,
                                                                                     EPredefinedDocumentTypeIdentifier.INVOICE_T015_BIS5A },
                                          Version.parse ("1.1.0")),

  /**
   * urn:www.cenbii.eu:profile:bii05:ver2.0
   * 
   * @since code list 1.2.0
   */
  urn_www_cenbii_eu_profile_bii05_ver2_0 ("urn:www.cenbii.eu:profile:bii05:ver2.0",
                                          "urn:www.peppol.eu:bis:peppol5a:ver2.0",
                                          new EPredefinedDocumentTypeIdentifier [] { EPredefinedDocumentTypeIdentifier.INVOICE_T010_BIS5A_V20,
                                                                                     EPredefinedDocumentTypeIdentifier.CREDITNOTE_T014_BIS5A_V20 },
                                          Version.parse ("1.2.0")),

  /**
   * urn:www.cenbii.eu:profile:bii06:ver1.0
   * 
   * @since code list 1.0.0
   */
  urn_www_cenbii_eu_profile_bii06_ver1_0 ("urn:www.cenbii.eu:profile:bii06:ver1.0",
                                          "urn:www.peppol.eu:bis:peppol6a:ver1.0",
                                          new EPredefinedDocumentTypeIdentifier [] { EPredefinedDocumentTypeIdentifier.ORDER_T001_BIS6A,
                                                                                     EPredefinedDocumentTypeIdentifier.ORDERRESPONSESIMPLE_T002_BIS6A,
                                                                                     EPredefinedDocumentTypeIdentifier.ORDERRESPONSESIMPLE_T003_BIS6A,
                                                                                     EPredefinedDocumentTypeIdentifier.INVOICE_T010_BIS6A,
                                                                                     EPredefinedDocumentTypeIdentifier.CREDITNOTE_T014_BIS6A,
                                                                                     EPredefinedDocumentTypeIdentifier.INVOICE_T015_BIS6A },
                                          Version.parse ("1.0.0")),

  /**
   * urn:www.cenbii.eu:profile:bii28:ver2.0
   * 
   * @since code list 1.2.0
   */
  urn_www_cenbii_eu_profile_bii28_ver2_0 ("urn:www.cenbii.eu:profile:bii28:ver2.0",
                                          "urn:www.peppol.eu:bis:peppol28a:ver1.0",
                                          new EPredefinedDocumentTypeIdentifier [] { EPredefinedDocumentTypeIdentifier.ORDER_T001_BIS28A,
                                                                                     EPredefinedDocumentTypeIdentifier.ORDER_T076_BIS28A },
                                          Version.parse ("1.2.0")),

  /**
   * urn:www.cenbii.eu:profile:bii30:ver2.0
   * 
   * @since code list 1.2.0
   */
  urn_www_cenbii_eu_profile_bii30_ver2_0 ("urn:www.cenbii.eu:profile:bii30:ver2.0",
                                          "urn:www.peppol.eu:bis:peppol30a:ver1.0",
                                          new EPredefinedDocumentTypeIdentifier [] { EPredefinedDocumentTypeIdentifier.DESPATCHADVICE_T016_BIS30A },
                                          Version.parse ("1.2.0")),

  /**
   * urn:www.cenbii.eu:profile:bii36:ver2.0
   * 
   * @since code list 1.2.0
   */
  urn_www_cenbii_eu_profile_bii36_ver2_0 ("urn:www.cenbii.eu:profile:bii36:ver2.0",
                                          "urn:www.peppol.eu:bis:peppol36a:ver1.0",
                                          new EPredefinedDocumentTypeIdentifier [] { EPredefinedDocumentTypeIdentifier.APPLICATIONRESPONSE_T071_BIS36A },
                                          Version.parse ("1.2.0"));
  /**
   * Same as {@link #urn_www_cenbii_eu_profile_bii01_ver1_0}
   */
  public final static EPredefinedProcessIdentifier BIS1A = urn_www_cenbii_eu_profile_bii01_ver1_0;
  /**
   * Same as {@link #urn_www_cenbii_eu_profile_bii01_ver2_0}
   */
  public final static EPredefinedProcessIdentifier BIS1A_V40 = urn_www_cenbii_eu_profile_bii01_ver2_0;
  /**
   * Same as {@link #urn_www_cenbii_eu_profile_bii03_ver1_0}
   */
  public final static EPredefinedProcessIdentifier BIS3A = urn_www_cenbii_eu_profile_bii03_ver1_0;
  /**
   * Same as {@link #urn_www_cenbii_eu_profile_bii03_ver2_0}
   */
  public final static EPredefinedProcessIdentifier BIS03A_V20 = urn_www_cenbii_eu_profile_bii03_ver2_0;
  /**
   * Same as {@link #urn_www_cenbii_eu_profile_bii04_ver1_0}
   */
  public final static EPredefinedProcessIdentifier BIS4A = urn_www_cenbii_eu_profile_bii04_ver1_0;
  /**
   * Same as {@link #urn_www_cenbii_eu_profile_bii04_ver2_0}
   */
  public final static EPredefinedProcessIdentifier BIS4A_V20 = urn_www_cenbii_eu_profile_bii04_ver2_0;
  /**
   * Same as {@link #urn_www_cenbii_eu_profile_bii05_ver1_0}
   */
  public final static EPredefinedProcessIdentifier BIS5A = urn_www_cenbii_eu_profile_bii05_ver1_0;
  /**
   * Same as {@link #urn_www_cenbii_eu_profile_bii05_ver2_0}
   */
  public final static EPredefinedProcessIdentifier BIS5A_V20 = urn_www_cenbii_eu_profile_bii05_ver2_0;
  /**
   * Same as {@link #urn_www_cenbii_eu_profile_bii06_ver1_0}
   */
  public final static EPredefinedProcessIdentifier BIS6A = urn_www_cenbii_eu_profile_bii06_ver1_0;
  /**
   * Same as {@link #urn_www_cenbii_eu_profile_bii28_ver2_0}
   */
  public final static EPredefinedProcessIdentifier BIS28A = urn_www_cenbii_eu_profile_bii28_ver2_0;
  /**
   * Same as {@link #urn_www_cenbii_eu_profile_bii30_ver2_0}
   */
  public final static EPredefinedProcessIdentifier BIS30A = urn_www_cenbii_eu_profile_bii30_ver2_0;
  /**
   * Same as {@link #urn_www_cenbii_eu_profile_bii36_ver2_0}
   */
  public final static EPredefinedProcessIdentifier BIS36A = urn_www_cenbii_eu_profile_bii36_ver2_0;
  private final String m_sID;
  private final String m_sBISID;
  private final EPredefinedDocumentTypeIdentifier [] m_aDocIDs;
  private final Version m_aSince;

  private EPredefinedProcessIdentifier (@Nonnull @Nonempty final String sID,
                                        @Nonnull @Nonempty final String sBISID,
                                        @Nonnull @Nonempty final EPredefinedDocumentTypeIdentifier [] aDocIDs,
                                        @Nonnull final Version aSince)
  {
    m_sID = sID;
    m_sBISID = sBISID;
    m_aDocIDs = aDocIDs;
    m_aSince = aSince;
  }

  @Nonnull
  @Nonempty
  public String getScheme ()
  {
    return PeppolIdentifierHelper.DEFAULT_PROCESS_SCHEME;
  }

  @Nonnull
  @Nonempty
  public String getValue ()
  {
    return m_sID;
  }

  @Nonnull
  @Nonempty
  public String getBISID ()
  {
    return m_sBISID;
  }

  @Nonnull
  @ReturnsMutableCopy
  public ICommonsList <? extends IPeppolPredefinedDocumentTypeIdentifier> getDocumentTypeIdentifiers ()
  {
    return CollectionHelper.newList (m_aDocIDs);
  }

  @Nonnull
  public PeppolProcessIdentifier getAsProcessIdentifier ()
  {
    return new PeppolProcessIdentifier (this);
  }

  @Nonnull
  public Version getSince ()
  {
    return m_aSince;
  }

  public boolean isDefaultScheme ()
  {
    return true;
  }

  @Nullable
  public static EPredefinedProcessIdentifier getFromProcessIdentifierOrNull (@Nullable final IProcessIdentifier aProcessID)
  {
    if ((aProcessID != null) && aProcessID.hasScheme (PeppolIdentifierHelper.DEFAULT_PROCESS_SCHEME))
    {
      for (EPredefinedProcessIdentifier e : EPredefinedProcessIdentifier.values ())
      {
        if (e.getValue ().equals (aProcessID.getValue ()))
        {
          return e;
        }
      }
    }
    return null;
  }
}
