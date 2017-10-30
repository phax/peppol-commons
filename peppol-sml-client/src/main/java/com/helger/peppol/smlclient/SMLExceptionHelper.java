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
package com.helger.peppol.smlclient;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

/**
 * Utility class to help dealing with SML exception.
 * <p>
 * Note: this class is also licensed under Apache 2 license, as it was not part
 * of the original implementation
 * </p>
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
