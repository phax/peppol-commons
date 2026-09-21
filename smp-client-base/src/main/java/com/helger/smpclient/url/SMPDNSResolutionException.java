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
package com.helger.smpclient.url;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.helger.annotation.Nonempty;
import com.helger.base.enforce.ValueEnforcer;
import com.helger.base.id.IHasID;
import com.helger.base.lang.EnumHelper;
import com.helger.base.tostring.ToStringGenerator;

/**
 * Checked exception to be thrown if DNS resolution fails.
 *
 * @author Philip Helger
 * @since 8.2.0
 */
public class SMPDNSResolutionException extends Exception
{
  /**
   * Contains the allowed error codes for this exception
   *
   * @author Philip Helger
   * @since 12.3.2
   */
  public enum EErrorCode implements IHasID <String>
  {
    /**
     * The provided DNS domain name is invalid.
     */
    DOMAIN_NAME_SYNTAX_ERROR ("dname-err"),
    /**
     * Generic DNS resolution failure. Retained for backwards compatibility; new code should prefer
     * one of the more specific codes {@link #DNS_TECHNICAL_FAILURE},
     * {@link #PARTICIPANT_NOT_REGISTERED} or {@link #NO_MATCHING_SMP_SERVICE}.
     */
    @Deprecated (forRemoval = true, since = "12.5.0")
    DNS_RESOLVING_ERROR("dnsresolve-err"),
    /**
     * The resolved SMP URI is invalid.
     */
    RESOLVED_URI_SYNTAX_ERROR ("resolveduri-err"),
    /**
     * The DNS lookup failed for technical reasons (DNS server unreachable, transient network error,
     * server-side data error). The participant may or may not be registered — the lookup could not
     * determine that. Retrying the lookup later may succeed.
     *
     * @since 12.5.0
     */
    DNS_TECHNICAL_FAILURE ("dns-tech-err"),
    /**
     * The participant's DNS name is not registered (NXDOMAIN) or has no NAPTR records (NODATA).
     * This is a "functional not-found" — the participant is genuinely not registered in the SML.
     * Retrying the lookup is unlikely to help.
     *
     * @since 12.5.0
     */
    PARTICIPANT_NOT_REGISTERED ("participant-not-found"),
    /**
     * The participant is registered (NAPTR records exist) but none of the records match the
     * required U-NAPTR service name (e.g. <code>Meta:SMP</code>). The participant is not configured
     * for this service profile.
     *
     * @since 12.5.0
     */
    NO_MATCHING_SMP_SERVICE ("no-smp-service");

    @NonNull
    private final String m_sID;

    EErrorCode (@NonNull @Nonempty final String sID)
    {
      m_sID = sID;
    }

    @NonNull
    @Nonempty
    public String getID ()
    {
      return m_sID;
    }

    /**
     * @return <code>true</code> if the underlying failure is potentially transient and the caller
     *         could retry the lookup. Currently <code>true</code> only for
     *         {@link #DNS_TECHNICAL_FAILURE}.
     * @since 12.5.0
     */
    public boolean isRetryable ()
    {
      return this == DNS_TECHNICAL_FAILURE;
    }

    /**
     * @return <code>true</code> if the failure indicates that the participant is genuinely not
     *         registered in the SML. Currently <code>true</code> only for
     *         {@link #PARTICIPANT_NOT_REGISTERED}.
     * @since 12.5.0
     */
    public boolean isParticipantUnknown ()
    {
      return this == PARTICIPANT_NOT_REGISTERED;
    }

    @Nullable
    public static EErrorCode getFromIDOrNull (@Nullable final String sID)
    {
      return EnumHelper.getFromIDOrNull (EErrorCode.class, sID);
    }
  }

  private final @NonNull EErrorCode m_eErrorCode;

  public SMPDNSResolutionException (@NonNull final EErrorCode eErrorCode, @NonNull final String sMessage)
  {
    super (sMessage);
    m_eErrorCode = ValueEnforcer.notNull (eErrorCode, "ErrorCode");
  }

  public SMPDNSResolutionException (@NonNull final EErrorCode eErrorCode,
                                    @NonNull final String sMessage,
                                    @Nullable final Throwable aCause)
  {
    super (sMessage, aCause);
    m_eErrorCode = ValueEnforcer.notNull (eErrorCode, "ErrorCode");
  }

  /**
   * @return The reason for the exception. Never <code>null</code>.
   * @since 12.3.2
   */
  @NonNull
  public final EErrorCode getErrorCode ()
  {
    return m_eErrorCode;
  }

  @Override
  public String toString ()
  {
    return ToStringGenerator.getDerived (super.toString ()).append ("ErrorCode", m_eErrorCode).getToString ();
  }
}
