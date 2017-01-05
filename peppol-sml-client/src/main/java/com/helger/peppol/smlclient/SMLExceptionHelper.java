/**
 * Copyright (C) 2015-2017 Philip Helger (www.helger.com)
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
package com.helger.peppol.smlclient;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

/**
 * Utility class to help dealing with SML exception.
 *
 * @author Philip Helger
 */
@Immutable
public final class SMLExceptionHelper
{
  private SMLExceptionHelper ()
  {}

  /**
   * Get the fault message from an SML client exception
   *
   * @param aCause
   *        Exception to be handled. May be <code>null</code>.
   * @return <code>null</code> if a non-SML exception was thrown or no fault
   *         message was contained
   */
  @Nullable
  public static String getFaultMessage (@Nullable final Throwable aCause)
  {
    if (aCause != null)
    {
      if (aCause instanceof com.helger.peppol.smlclient.smp.BadRequestFault)
        return ((com.helger.peppol.smlclient.smp.BadRequestFault) aCause).getFaultInfo ().getFaultMessage ();
      if (aCause instanceof com.helger.peppol.smlclient.participant.BadRequestFault)
        return ((com.helger.peppol.smlclient.participant.BadRequestFault) aCause).getFaultInfo ().getFaultMessage ();
      if (aCause instanceof com.helger.peppol.smlclient.bdmslcipa.BadRequestFault)
        return ((com.helger.peppol.smlclient.bdmslcipa.BadRequestFault) aCause).getFaultInfo ().getFaultMessage ();

      if (aCause instanceof com.helger.peppol.smlclient.smp.InternalErrorFault)
        return ((com.helger.peppol.smlclient.smp.InternalErrorFault) aCause).getFaultInfo ().getFaultMessage ();
      if (aCause instanceof com.helger.peppol.smlclient.participant.InternalErrorFault)
        return ((com.helger.peppol.smlclient.participant.InternalErrorFault) aCause).getFaultInfo ().getFaultMessage ();
      if (aCause instanceof com.helger.peppol.smlclient.bdmslcipa.InternalErrorFault)
        return ((com.helger.peppol.smlclient.bdmslcipa.InternalErrorFault) aCause).getFaultInfo ().getFaultMessage ();

      if (aCause instanceof com.helger.peppol.smlclient.smp.NotFoundFault)
        return ((com.helger.peppol.smlclient.smp.NotFoundFault) aCause).getFaultInfo ().getFaultMessage ();
      if (aCause instanceof com.helger.peppol.smlclient.participant.NotFoundFault)
        return ((com.helger.peppol.smlclient.participant.NotFoundFault) aCause).getFaultInfo ().getFaultMessage ();
      if (aCause instanceof com.helger.peppol.smlclient.bdmslcipa.NotFoundFault)
        return ((com.helger.peppol.smlclient.bdmslcipa.NotFoundFault) aCause).getFaultInfo ().getFaultMessage ();

      if (aCause instanceof com.helger.peppol.smlclient.smp.UnauthorizedFault)
        return ((com.helger.peppol.smlclient.smp.UnauthorizedFault) aCause).getFaultInfo ().getFaultMessage ();
      if (aCause instanceof com.helger.peppol.smlclient.participant.UnauthorizedFault)
        return ((com.helger.peppol.smlclient.participant.UnauthorizedFault) aCause).getFaultInfo ().getFaultMessage ();
      if (aCause instanceof com.helger.peppol.smlclient.bdmslcipa.UnauthorizedFault)
        return ((com.helger.peppol.smlclient.bdmslcipa.UnauthorizedFault) aCause).getFaultInfo ().getFaultMessage ();
    }

    return null;
  }

}
