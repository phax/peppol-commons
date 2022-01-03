/*
 * Copyright (C) 2015-2022 Philip Helger
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
      if (aCause instanceof com.helger.peppol.smlclient.bdmsl.BadRequestFault)
        return ((com.helger.peppol.smlclient.bdmsl.BadRequestFault) aCause).getFaultInfo ().getFaultMessage ();

      if (aCause instanceof com.helger.peppol.smlclient.smp.InternalErrorFault)
        return ((com.helger.peppol.smlclient.smp.InternalErrorFault) aCause).getFaultInfo ().getFaultMessage ();
      if (aCause instanceof com.helger.peppol.smlclient.participant.InternalErrorFault)
        return ((com.helger.peppol.smlclient.participant.InternalErrorFault) aCause).getFaultInfo ().getFaultMessage ();
      if (aCause instanceof com.helger.peppol.smlclient.bdmsl.InternalErrorFault)
        return ((com.helger.peppol.smlclient.bdmsl.InternalErrorFault) aCause).getFaultInfo ().getFaultMessage ();

      if (aCause instanceof com.helger.peppol.smlclient.smp.NotFoundFault)
        return ((com.helger.peppol.smlclient.smp.NotFoundFault) aCause).getFaultInfo ().getFaultMessage ();
      if (aCause instanceof com.helger.peppol.smlclient.participant.NotFoundFault)
        return ((com.helger.peppol.smlclient.participant.NotFoundFault) aCause).getFaultInfo ().getFaultMessage ();
      if (aCause instanceof com.helger.peppol.smlclient.bdmsl.NotFoundFault)
        return ((com.helger.peppol.smlclient.bdmsl.NotFoundFault) aCause).getFaultInfo ().getFaultMessage ();

      if (aCause instanceof com.helger.peppol.smlclient.smp.UnauthorizedFault)
        return ((com.helger.peppol.smlclient.smp.UnauthorizedFault) aCause).getFaultInfo ().getFaultMessage ();
      if (aCause instanceof com.helger.peppol.smlclient.participant.UnauthorizedFault)
        return ((com.helger.peppol.smlclient.participant.UnauthorizedFault) aCause).getFaultInfo ().getFaultMessage ();
      if (aCause instanceof com.helger.peppol.smlclient.bdmsl.UnauthorizedFault)
        return ((com.helger.peppol.smlclient.bdmsl.UnauthorizedFault) aCause).getFaultInfo ().getFaultMessage ();
    }

    return null;
  }
}
