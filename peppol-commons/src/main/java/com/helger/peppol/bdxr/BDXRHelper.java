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
package com.helger.peppol.bdxr;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.helger.commons.annotation.PresentForCodeCoverage;
import com.helger.peppol.identifier.IDocumentTypeIdentifier;
import com.helger.peppol.identifier.IParticipantIdentifier;
import com.helger.peppol.identifier.IProcessIdentifier;
import com.helger.peppol.identifier.doctype.SimpleDocumentTypeIdentifier;
import com.helger.peppol.identifier.participant.SimpleParticipantIdentifier;
import com.helger.peppol.identifier.process.SimpleProcessIdentifier;

/**
 * A special helper class to convert between generic and BDXR identifiers types.
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
@Immutable
public final class BDXRHelper
{
  @PresentForCodeCoverage
  @SuppressWarnings ("unused")
  private static final BDXRHelper s_aInstance = new BDXRHelper ();

  private BDXRHelper ()
  {}

  @Nullable
  public static ParticipantIdentifierType getAsBDXRParticipantIdentifier (@Nullable final IParticipantIdentifier aParticipantID)
  {
    if (aParticipantID == null)
      return null;
    final ParticipantIdentifierType ret = new ParticipantIdentifierType ();
    ret.setScheme (aParticipantID.getScheme ());
    ret.setValue (aParticipantID.getValue ());
    return ret;
  }

  @Nullable
  public static SimpleParticipantIdentifier getAsSMPParticipantIdentifier (@Nullable final ParticipantIdentifierType aParticipantID)
  {
    if (aParticipantID == null)
      return null;
    return new SimpleParticipantIdentifier (aParticipantID);
  }

  @Nullable
  public static DocumentIdentifierType getAsBDXRDocumentTypeIdentifier (@Nullable final IDocumentTypeIdentifier aDocTypeID)
  {
    if (aDocTypeID == null)
      return null;
    final DocumentIdentifierType ret = new DocumentIdentifierType ();
    ret.setScheme (aDocTypeID.getScheme ());
    ret.setValue (aDocTypeID.getValue ());
    return ret;
  }

  @Nullable
  public static SimpleDocumentTypeIdentifier getAsSMPDocumentTypeIdentifier (@Nullable final DocumentIdentifierType aDocTypeID)
  {
    if (aDocTypeID == null)
      return null;
    return new SimpleDocumentTypeIdentifier (aDocTypeID);
  }

  @Nullable
  public static ProcessIdentifierType getAsBDXRProcessIdentifier (@Nullable final IProcessIdentifier aProcessID)
  {
    if (aProcessID == null)
      return null;
    final ProcessIdentifierType ret = new ProcessIdentifierType ();
    ret.setScheme (aProcessID.getScheme ());
    ret.setValue (aProcessID.getValue ());
    return ret;
  }

  @Nullable
  public static SimpleProcessIdentifier getAsSMPProcessIdentifier (@Nullable final ProcessIdentifierType aProcessID)
  {
    if (aProcessID == null)
      return null;
    return new SimpleProcessIdentifier (aProcessID);
  }
}
